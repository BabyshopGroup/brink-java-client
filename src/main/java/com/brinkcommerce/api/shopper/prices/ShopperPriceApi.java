package com.brinkcommerce.api.shopper.prices;

import static java.util.Objects.requireNonNull;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.shopper.BrinkShopperRequest;
import com.brinkcommerce.api.shopper.ShopperConfiguration;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Objects;

public class ShopperPriceApi {

  private static final String PRICES_PATH = "prices";
  private static final String PRODUCT_PARENT_PATH = "product-parents";
  private static final String STORE_GROUP_PATH = "store-groups";
  private static final String MARKET_PATH = "markets";
  private static final String ERROR_MESSAGE =
      "Failed to get prices for productParentId: %s, storeGroupId %s , countryCode: %s.";

  private final URI pricesUrl;
  private final HttpClient client;
  private final ObjectMapper mapper;
  private final BrinkHttpUtil httpUtil;

  private ShopperPriceApi(final ShopperConfiguration config) {
    requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Shopper Host URL cannot be null.");
    this.mapper = requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.client = requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.httpUtil = BrinkHttpUtil.create(this.mapper);
    this.pricesUrl = URI.create(config.host());
  }

  public static ShopperPriceApi init(final ShopperConfiguration config) {
    return new ShopperPriceApi(config);
  }

  public BrinkProductParentPrices get(
          final BrinkShopperRequest request) {

    try {
      HttpRequest.Builder requestBuilder =
          HttpRequest.newBuilder()
              .uri(
                  URI.create(
                      String.format(
                          "%s/%s/%s/%s/%s/%s/%s/%s",
                          this.pricesUrl.toString(),
                          PRICES_PATH,
                          PRODUCT_PARENT_PATH,
                          request.productParentId(),
                          STORE_GROUP_PATH,
                          request.storeGroupId(),
                          MARKET_PATH,
                          request.countryCode())))
              .GET();

      for(Map.Entry<String, String> entry : request.headers().entrySet()) {
        requestBuilder = requestBuilder.header(entry.getKey(), entry.getValue());
      }

      final HttpResponse<String> response = makeRequest(requestBuilder.build());

      return (BrinkProductParentPrices)
          this.httpUtil.handleResponse(response, BrinkProductParentPrices.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkPricesException(
          String.format(ERROR_MESSAGE, request.productParentId(), request.storeGroupId(), request.countryCode()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkPricesException(
          String.format(ERROR_MESSAGE, request.productParentId(), request.storeGroupId(), request.countryCode()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkPricesException(
          String.format(ERROR_MESSAGE, request.productParentId(), request.storeGroupId(), request.countryCode()), e, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.client.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
