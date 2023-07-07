package com.brinkcommerce.api.shopper.prices;

import static java.util.Objects.requireNonNull;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.shopper.ShopperConfiguration;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
      final String productParentId, final String storeGroupId, final String countryCode) {
    Objects.requireNonNull(productParentId, "Product parent id cannot be null.");
    Objects.requireNonNull(storeGroupId, "Store group id cannot be null.");
    Objects.requireNonNull(countryCode, "Country code variant cannot be null.");

    try {
      final HttpRequest httpRequest =
          HttpRequest.newBuilder()
              .uri(
                  URI.create(
                      String.format(
                          "%s/%s/%s/%s/%s/%s/%s/%s",
                          this.pricesUrl.toString(),
                          PRICES_PATH,
                          PRODUCT_PARENT_PATH,
                          productParentId,
                          STORE_GROUP_PATH,
                          storeGroupId,
                          MARKET_PATH,
                          countryCode)))
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductParentPrices)
          this.httpUtil.handleResponse(response, BrinkProductParentPrices.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkPricesException(
          String.format(ERROR_MESSAGE, productParentId, storeGroupId, countryCode), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkPricesException(
          String.format(ERROR_MESSAGE, productParentId, storeGroupId, countryCode),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkPricesException(
          String.format(ERROR_MESSAGE, productParentId, storeGroupId, countryCode), e, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.client.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
