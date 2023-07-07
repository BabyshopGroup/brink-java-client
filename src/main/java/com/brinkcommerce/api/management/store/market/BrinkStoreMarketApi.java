package com.brinkcommerce.api.management.store.market;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.CONTENT_TYPE;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.httpRequestBuilderWithAuthentication;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.brinkcommerce.api.management.store.BrinkStoreException;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class BrinkStoreMarketApi {

  private static final String PATH = "/store-groups";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;
  private final URI storeGroupUrl;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;

  private BrinkStoreMarketApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.storeGroupUrl = URI.create(String.format("%s%s", config.host(), PATH));
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
  }

  public static BrinkStoreMarketApi init(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    return new BrinkStoreMarketApi(config, authenticationHandler);
  }

  /**
   * Creates and returns a com.brinkcommerce.api.Brink store group market. HTTP method: PUT. URI:
   * /store-groups/{storeGroupId}/market/{countryCode}
   *
   * @param brinkStoreMarket is a com.brinkcommerce.api.Brink store group market, used as URI
   * @return BrinkStoreMarket is a market of a com.brinkcommerce.api.Brink Store group
   * @throws BrinkStoreException if an error occurs
   */
  public BrinkStoreMarket create(final BrinkStoreMarket brinkStoreMarket) {
    Objects.requireNonNull(brinkStoreMarket, "com.brinkcommerce.api.Brink store market cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      BrinkHttpUtil.buildURI(brinkStoreMarket, this.storeGroupUrl.toString())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .PUT(BodyPublishers.ofString(this.mapper.writeValueAsString(brinkStoreMarket)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkStoreMarket) this.brinkHttpUtil.handleResponse(response, BrinkStoreMarket.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStoreException(
          String.format(
              "Failed to create store market with country code %s.",
              brinkStoreMarket.countryCode()),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStoreException(
          String.format(
              "Failed to create store market with country code %s.",
              brinkStoreMarket.countryCode()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStoreException(
          String.format(
              "Failed to create store market with country code %s.",
              brinkStoreMarket.countryCode()),
          e,
          null);
    }
  }

  /**
   * Returns all markets for a com.brinkcommerce.api.Brink store group. HTTP method: GET. URI:
   * /store-groups/{storeGroupId}/market/
   *
   * @param storeGroupId is a com.brinkcommerce.api.Brink store group id, used as URI
   * @return BrinkStoreMarket is a market of a com.brinkcommerce.api.Brink Store group
   * @throws BrinkStoreException if an error occurs
   */
  public List<BrinkStoreMarket> get(final String storeGroupId) {
    Objects.requireNonNull(storeGroupId, "Store group id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s/markets", this.storeGroupUrl.toString(), storeGroupId)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);
      record ResponseWrapper(List<BrinkStoreMarket> storeMarkets) {}
      final ResponseWrapper result =
          (ResponseWrapper) this.brinkHttpUtil.handleResponse(response, ResponseWrapper.class);

      return result.storeMarkets();
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStoreException(
          String.format("Failed to get store group markets with store group id %s.", storeGroupId),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStoreException(
          String.format("Failed to get store group markets with store group id %s.", storeGroupId),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStoreException(
          String.format("Failed to get store group markets with store group id %s.", storeGroupId),
          e,
          null);
    }
  }

  /**
   * Deletes market by country code for a specific store group. HTTP method: DELETE. URI:
   * /store-groups/{storeGroupId}/market/{countryCode}
   *
   * @param storeGroupId id of the store-group, used as URI
   * @param countryCode country code for the specific market
   * @throws BrinkStoreException if an error occurs
   */
  public void delete(final String storeGroupId, final BrinkCountryCode countryCode) {
    Objects.requireNonNull(storeGroupId, "Request cannot be null.");
    Objects.requireNonNull(countryCode, "Request cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format(
                          "%s/%s/markets/%s/",
                          this.storeGroupUrl.toString(), storeGroupId, countryCode)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .DELETE()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStoreException(
          String.format("Failed to delete store group market with country code %s.", countryCode),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStoreException(
          String.format("Failed to delete store group market with country code %s.", countryCode),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStoreException(
          String.format("Failed to delete store group market with country code %s.", countryCode),
          e,
          null);
    }
  }

  /**
   * Partially updates and returns a com.brinkcommerce.api.Brink store group market. HTTP method: PATCH. URI:
   * /store-groups/{storeGroupId}/market/{countryCode}
   *
   * @param brinkStoreMarket is a com.brinkcommerce.api.Brink store group market, used as URI
   * @return BrinkStoreMarket is a market of a com.brinkcommerce.api.Brink Store group
   * @throws BrinkStoreException if an error occurs
   */
  public BrinkStoreMarket update(final BrinkStoreMarket brinkStoreMarket) {
    Objects.requireNonNull(brinkStoreMarket, "com.brinkcommerce.api.Brink store market cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      BrinkHttpUtil.buildURI(brinkStoreMarket, this.storeGroupUrl.toString())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .method(
                  "PATCH",
                  BodyPublishers.ofString(this.mapper.writeValueAsString(brinkStoreMarket)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkStoreMarket) this.brinkHttpUtil.handleResponse(response, BrinkStoreMarket.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStoreException(
          String.format(
              "Failed to update store market with country code %s.",
              brinkStoreMarket.countryCode()),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStoreException(
          String.format(
              "Failed to update store market with country code %s.",
              brinkStoreMarket.countryCode()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStoreException(
          String.format(
              "Failed to update store market with country code %s.",
              brinkStoreMarket.countryCode()),
          e,
          null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
