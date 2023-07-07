package com.brinkcommerce.api.management.tax.market;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.CONTENT_TYPE;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.httpRequestBuilderWithAuthentication;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.brinkcommerce.api.management.tax.BrinkTaxException;
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

public class BrinkTaxMarketApi {

  private static final String PATH = "/tax/tax-groups";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;
  private final URI taxGroupUrl;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;

  private BrinkTaxMarketApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.taxGroupUrl = URI.create(String.format("%s%s", config.host(), PATH));
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
  }

  public static BrinkTaxMarketApi init(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    return new BrinkTaxMarketApi(config, authenticationHandler);
  }

  /**
   * Creates and returns a com.brinkcommerce.api.Brink tax group market. HTTP method: PUT. URI:
   * /tax-groups/{taxGroupId}/market/{countryCode}
   *
   * @param brinkTaxMarket is a com.brinkcommerce.api.Brink tax group market, used as URI
   * @param taxGroupId id of the com.brinkcommerce.api.Brink tax group, used as URI
   * @return BrinkTaxMarket is a market of a com.brinkcommerce.api.Brink Tax group
   * @throws BrinkTaxException if an error occurs
   */
  public BrinkTaxMarket create(final String taxGroupId, final BrinkTaxMarket brinkTaxMarket) {
    Objects.requireNonNull(brinkTaxMarket, "com.brinkcommerce.api.Brink tax market cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format(
                          "%s/%s/markets/%s/",
                          this.taxGroupUrl.toString(), taxGroupId, brinkTaxMarket.countryCode())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .PUT(BodyPublishers.ofString(this.mapper.writeValueAsString(brinkTaxMarket)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkTaxMarket) this.brinkHttpUtil.handleResponse(response, BrinkTaxMarket.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkTaxException(
          String.format(
              "Failed to create tax market with country code %s.", brinkTaxMarket.countryCode()),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkTaxException(
          String.format(
              "Failed to create tax market with country code %s.", brinkTaxMarket.countryCode()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkTaxException(
          String.format(
              "Failed to create tax market with country code %s.", brinkTaxMarket.countryCode()),
          e,
          null);
    }
  }

  /**
   * Returns all markets for a com.brinkcommerce.api.Brink tax group. HTTP method: GET. URI:
   * /tax-groups/{taxGroupId}/market/
   *
   * @param taxGroupId is a com.brinkcommerce.api.Brink tax group id, used as URI
   * @return BrinkTaxMarket is a market of a com.brinkcommerce.api.Brink Tax group
   * @throws BrinkTaxException if an error occurs
   */
  public List<BrinkTaxMarket> getAll(final String taxGroupId) {
    Objects.requireNonNull(taxGroupId, "Tax group id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s/markets", this.taxGroupUrl.toString(), taxGroupId)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      record ResponseWrapper(List<BrinkTaxMarket> taxMarkets) {}
      final ResponseWrapper result =
          (ResponseWrapper) this.brinkHttpUtil.handleResponse(response, ResponseWrapper.class);

      return result.taxMarkets();
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkTaxException(
          String.format("Failed to get tax group markets with tax group id %s.", taxGroupId),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkTaxException(
          String.format("Failed to get tax group markets with tax group id %s.", taxGroupId),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkTaxException(
          String.format("Failed to get tax group markets with tax group id %s.", taxGroupId),
          e,
          null);
    }
  }

  /**
   * Deletes market by country code for a specific tax group. HTTP method: DELETE. URI:
   * /tax-groups/{taxGroupId}/market/{countryCode}
   *
   * @param taxGroupId id of the tax-group, used as URI
   * @param countryCode country code for the specific market
   * @throws BrinkTaxException if an error occurs
   */
  public void delete(final String taxGroupId, final BrinkCountryCode countryCode) {
    Objects.requireNonNull(taxGroupId, "Request cannot be null.");
    Objects.requireNonNull(countryCode, "Request cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format(
                          "%s/%s/markets/%s/",
                          this.taxGroupUrl.toString(), taxGroupId, countryCode)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .DELETE()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkTaxException(
          String.format("Failed to delete tax group market with country code %s.", countryCode),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkTaxException(
          String.format("Failed to delete tax group market with country code %s.", countryCode),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkTaxException(
          String.format("Failed to delete tax group market with country code %s.", countryCode),
          e,
          null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
