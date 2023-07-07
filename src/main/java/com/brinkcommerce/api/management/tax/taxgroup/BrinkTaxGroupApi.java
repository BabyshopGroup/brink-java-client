package com.brinkcommerce.api.management.tax.taxgroup;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.CONTENT_TYPE;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.httpRequestBuilderWithAuthentication;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.tax.BrinkTaxException;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Objects;

public class BrinkTaxGroupApi {

  private static final String PATH = "/tax/tax-groups";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;
  private final URI taxGroupUrl;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;

  private BrinkTaxGroupApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.taxGroupUrl = URI.create(String.format("%s%s", config.host(), PATH));
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
  }

  public static BrinkTaxGroupApi init(
        ManagementConfiguration config, AuthenticationHandler authenticationHandler) {
    return new BrinkTaxGroupApi(config, authenticationHandler);
  }

  /**
   * Creates and returns a com.brinkcommerce.api.Brink tax group. HTTP method: PUT. URI: /tax-groups/{id}
   *
   * @param brinkTaxGroup is a com.brinkcommerce.api.Brink tax group with id, name and description, used as URI
   * @return BrinkTaxGroup is a Store group object
   * @throws BrinkTaxException if an error occurs
   */
  public BrinkTaxGroup create(final BrinkTaxGroup brinkTaxGroup) {
    Objects.requireNonNull(brinkTaxGroup, "com.brinkcommerce.api.Brink tax group cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s", this.taxGroupUrl.toString(), brinkTaxGroup.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .PUT(BodyPublishers.ofString(this.mapper.writeValueAsString(brinkTaxGroup)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkTaxGroup) this.brinkHttpUtil.handleResponse(response, BrinkTaxGroup.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkTaxException(
          String.format("Failed to create tax group with id %s.", brinkTaxGroup.id()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkTaxException(
          String.format("Failed to create tax group with id %s.", brinkTaxGroup.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkTaxException(
          String.format("Failed to create tax group with id %s.", brinkTaxGroup.id()), e, null);
    }
  }

  /**
   * Returns com.brinkcommerce.api.Brink tax group for a specific id. HTTP method: GET. URI: /tax-groups/{id}
   *
   * @param taxGroupId is a com.brinkcommerce.api.Brink tax group id, used as URI
   * @return BrinkTaxGroup is a com.brinkcommerce.api.Brink tax group object
   * @throws BrinkTaxException if an error occurs
   */
  public BrinkTaxGroup get(final String taxGroupId) {
    Objects.requireNonNull(taxGroupId, "Tax group id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.taxGroupUrl.toString(), taxGroupId)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkTaxGroup) this.brinkHttpUtil.handleResponse(response, BrinkTaxGroup.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkTaxException(
          String.format("Failed to get tax group with id %s.", taxGroupId), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkTaxException(
          String.format("Failed to get tax group with id %s.", taxGroupId),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkTaxException(
          String.format("Failed to get tax group with id %s.", taxGroupId), e, null);
    }
  }

  /**
   * Deletes com.brinkcommerce.api.Brink tax group for a specific id. HTTP method: DELETE. URI: /tax-groups/{id}
   *
   * @param taxGroupId is a com.brinkcommerce.api.Brink tax group id, used as URI
   * @throws BrinkTaxException if an error occurs
   */
  public void delete(final String taxGroupId) {
    Objects.requireNonNull(taxGroupId, "Tax group id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.taxGroupUrl.toString(), taxGroupId)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .DELETE()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkTaxException(
          String.format("Failed to delete tax group with id %s.", taxGroupId), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkTaxException(
          String.format("Failed to delete tax group with id %s.", taxGroupId),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkTaxException(
          String.format("Failed to delete tax group with id %s.", taxGroupId), e, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
