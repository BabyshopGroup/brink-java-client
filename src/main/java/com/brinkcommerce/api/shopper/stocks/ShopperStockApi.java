package com.brinkcommerce.api.shopper.stocks;

import static java.util.Objects.requireNonNull;

import com.brinkcommerce.api.configuration.ManagementConfiguration;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShopperStockApi {

  private static final String STOCKS_PATH = "stocks";
  private static final String PRODUCT_PARENT_PATH = "product-parents";
  private static final String STORE_GROUP_PATH = "store-groups";
  private static final String MARKET_PATH = "markets";
  private static final String ERROR_MESSAGE =
      "Failed to get stock levels for productParentId: %s, storeGroupId %s , countryCode: %s.";

  private final URI stocksUrl;
  private final HttpClient client;
  private final ObjectMapper mapper;
  private final BrinkHttpUtil httpUtil;

  private ShopperStockApi(final ShopperConfiguration config) {
    requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Shopper Host URL cannot be null.");
    this.mapper = requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.client = requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.httpUtil = BrinkHttpUtil.create(this.mapper);
    this.stocksUrl = URI.create(config.host());
  }

  public static ShopperStockApi init(final ShopperConfiguration config) {
    return new ShopperStockApi(config);
  }

  /**
   * Get stock level. HTTP method: GET. URI:
   * /stocks/product-parents/{productParentId}/store-groups/{storeGroupId}/markets/{countryCode}.
   *
   * @param request BrinkShopperRequest
   * @return List<BrinkProductParentStock> list of stock levels
   * @throws BrinkStocksException if an error occurs
   */
  public BrinkProductParentStock get(
          final BrinkShopperRequest request) {
    try {
      HttpRequest.Builder requestBuilder =
          HttpRequest.newBuilder()
              .uri(
                  URI.create(
                      String.format(
                          "%s/%s/%s/%s/%s/%s/%s/%s",
                          this.stocksUrl.toString(),
                          STOCKS_PATH,
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

      return (BrinkProductParentStock)
          this.httpUtil.handleResponse(response, BrinkProductParentStock.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStocksException(
          String.format(ERROR_MESSAGE, request.productParentId(), request.storeGroupId(), request.countryCode()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStocksException(
          String.format(ERROR_MESSAGE, request.productParentId(), request.storeGroupId(), request.countryCode()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStocksException(
          String.format(ERROR_MESSAGE, request.productParentId(), request.storeGroupId(), request.countryCode()), e, null);
    }
  }

  private URI buildURI(final BrinkShopperRequest request) {
    return URI.create(
            String.format(
                    "%s/%s/%s/%s/%s/%s/%s/%s",
                    this.stocksUrl.toString(),
                    STOCKS_PATH,
                    PRODUCT_PARENT_PATH,
                    request.productParentId(),
                    STORE_GROUP_PATH,
                    request.storeGroupId(),
                    MARKET_PATH,
                    request.countryCode()));
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.client.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
