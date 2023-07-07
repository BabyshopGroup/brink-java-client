package com.brinkcommerce.api.management.stock.storegroups;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.CONTENT_TYPE;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.httpRequestBuilderWithAuthentication;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.stock.BrinkStockException;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class BrinkStoreGroupsApi {
  private static final String PATH = "stock/store-groups/";
  private final ObjectMapper objectMapper;
  private final HttpClient httpClient;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;
  private final URI storeGroupsUrl;

  private BrinkStoreGroupsApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.objectMapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.objectMapper);
    this.storeGroupsUrl = URI.create(String.format("%s/%s", config.host(), PATH));
  }

  public static BrinkStoreGroupsApi init(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    return new BrinkStoreGroupsApi(config, authenticationHandler);
  }

  /**
   * Updates inventory ids for store-group market. HTTP method: PUT. URI:
   * /stock/store-groups/{storeGroupId}/markets/{countryCode}/inventories
   *
   * @param brinkStoreGroupMarketInventoryIds which holds the request
   * @return inventory ids of the store-market
   * @throws BrinkStockException if an error occurs
   */
  public BrinkStoreGroupMarketInventoryIds update(
      final BrinkStoreGroupMarketInventoryIds brinkStoreGroupMarketInventoryIds) {
    Objects.requireNonNull(
        brinkStoreGroupMarketInventoryIds, "com.brinkcommerce.api.Brink markets inventories cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  this.storeGroupsUrl.resolve(
                      String.format(
                          "./%s/markets/%s/inventories",
                          brinkStoreGroupMarketInventoryIds.storeGroupId(),
                          brinkStoreGroupMarketInventoryIds.countryCode())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .PUT(
                  HttpRequest.BodyPublishers.ofString(
                      this.objectMapper.writeValueAsString(brinkStoreGroupMarketInventoryIds)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkStoreGroupMarketInventoryIds)
          this.brinkHttpUtil.handleResponse(response, BrinkStoreGroupMarketInventoryIds.class);
    } catch (final InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new BrinkStockException(
          String.format(
              "Failed to update inventory ids for store-market %s.",
              brinkStoreGroupMarketInventoryIds.storeGroupId()),
          exception,
          null);
    } catch (final BrinkIntegrationException exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to update inventory ids for store-group %s and country code %s.",
              brinkStoreGroupMarketInventoryIds.storeGroupId(),
              brinkStoreGroupMarketInventoryIds.countryCode()),
          exception,
          exception.brinkHttpCode(),
          exception.requestId());
    } catch (final Exception exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to update inventory ids for store-group %s and country code %s.",
              brinkStoreGroupMarketInventoryIds.storeGroupId(),
              brinkStoreGroupMarketInventoryIds.countryCode()),
          exception,
          null);
    }
  }

  /**
   * Gets inventories for store-group market. HTTP method: GET. URI:
   * /stock/store-groups/{storeGroupId}/markets/{countryCode}
   *
   * @param storeGroupId store-market id
   * @param countryCode country code
   * @return inventories of the store-market
   * @throws BrinkStockException if an error occurs
   */
  public BrinkStoreGroupMarketInventories get(final String storeGroupId, final String countryCode) {
    Objects.requireNonNull(storeGroupId, "Store market id cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  this.storeGroupsUrl.resolve(
                      String.format("./%s/markets/%s", storeGroupId, countryCode)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkStoreGroupMarketInventories)
          this.brinkHttpUtil.handleResponse(response, BrinkStoreGroupMarketInventories.class);
    } catch (final InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new BrinkStockException(
          String.format("Failed to get inventories for store-market %s.", storeGroupId),
          exception,
          null);
    } catch (final BrinkIntegrationException exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to get inventories for store-group %s and country code %s.",
              storeGroupId, countryCode),
          exception,
          exception.brinkHttpCode(),
          exception.requestId());
    } catch (final Exception exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to get inventories for store-group %s and country code %s.",
              storeGroupId, countryCode),
          exception,
          null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
