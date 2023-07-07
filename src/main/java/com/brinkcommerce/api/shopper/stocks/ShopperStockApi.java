package com.brinkcommerce.api.shopper.stocks;

import static java.util.Objects.requireNonNull;

import com.brinkcommerce.api.configuration.ManagementConfiguration;
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
   * @param productParentId id of product parent
   * @param storeGroupId id of store group
   * @param countryCode code for given market
   * @return List<BrinkProductParentStock> list of stock levels
   * @throws BrinkStocksException if an error occurs
   */
  public BrinkProductParentStock get(
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
                          this.stocksUrl.toString(),
                          STOCKS_PATH,
                          PRODUCT_PARENT_PATH,
                          productParentId,
                          STORE_GROUP_PATH,
                          storeGroupId,
                          MARKET_PATH,
                          countryCode)))
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductParentStock)
          this.httpUtil.handleResponse(response, BrinkProductParentStock.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStocksException(
          String.format(ERROR_MESSAGE, productParentId, storeGroupId, countryCode), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStocksException(
          String.format(ERROR_MESSAGE, productParentId, storeGroupId, countryCode),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStocksException(
          String.format(ERROR_MESSAGE, productParentId, storeGroupId, countryCode), e, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.client.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
