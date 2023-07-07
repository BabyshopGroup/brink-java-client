package com.brinkcommerce.api.management.store.storegroup;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.CONTENT_TYPE;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.httpRequestBuilderWithAuthentication;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.store.BrinkStoreException;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Objects;

public class BrinkStoreGroupApi {

  private static final String PATH = "/store-groups";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;
  private final URI storeGroupUrl;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;

  private BrinkStoreGroupApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.storeGroupUrl = URI.create(String.format("%s%s", config.host(), PATH));
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
  }

  public static BrinkStoreGroupApi init(
        final ManagementConfiguration config, AuthenticationHandler authenticationHandler) {
    return new BrinkStoreGroupApi(config, authenticationHandler);
  }

  /**
   * Creates and returns a com.brinkcommerce.api.Brink store group. HTTP method: PUT. URI: /store-groups/{storeGroupId}
   *
   * @param brinkStoreGroup is a com.brinkcommerce.api.Brink store group, used as URI
   * @return BrinkStoreGroup is a Store group object
   * @throws BrinkStoreException if an error occurs
   */
  public BrinkStoreGroup create(final BrinkStoreGroup brinkStoreGroup) {
    Objects.requireNonNull(brinkStoreGroup, "com.brinkcommerce.api.Brink store group cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s", this.storeGroupUrl.toString(), brinkStoreGroup.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .PUT(BodyPublishers.ofString(this.mapper.writeValueAsString(brinkStoreGroup)))
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkStoreGroup) this.brinkHttpUtil.handleResponse(response, BrinkStoreGroup.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStoreException(
          String.format("Failed to create store group with id %s.", brinkStoreGroup.id()),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStoreException(
          String.format("Failed to create store group with id %s.", brinkStoreGroup.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStoreException(
          String.format("Failed to create store group with id %s.", brinkStoreGroup.id()), e, null);
    }
  }

  /**
   * Returns com.brinkcommerce.api.Brink store group for a specific id. HTTP method: GET. URI:
   * /store-groups/{storeGroupId}
   *
   * @param storeGroupId is a com.brinkcommerce.api.Brink store group id, used as URI
   * @return BrinkStoreGroup is a Store group object
   * @throws BrinkStoreException if an error occurs
   */
  public BrinkStoreGroup get(final String storeGroupId) {
    Objects.requireNonNull(storeGroupId, "Store group id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.storeGroupUrl.toString(), storeGroupId)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkStoreGroup) this.brinkHttpUtil.handleResponse(response, BrinkStoreGroup.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStoreException(
          String.format("Failed to get store group with id %s.", storeGroupId), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStoreException(
          String.format("Failed to get store group with id %s.", storeGroupId),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStoreException(
          String.format("Failed to get store group with id %s.", storeGroupId), e, null);
    }
  }

  /**
   * Deletes com.brinkcommerce.api.Brink store group for a specific id. HTTP method: DELETE. URI:
   * /store-groups/{storeGroupId}
   *
   * @param storeGroupId is a com.brinkcommerce.api.Brink store group id, used as URI
   * @throws BrinkStoreException if an error occurs
   */

  public void delete(BrinkStoreGroup storeGroup) {
    this.delete(storeGroup.id());
  }

  public void delete(final String storeGroupId) {
    Objects.requireNonNull(storeGroupId, "Store group id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.storeGroupUrl.toString(), storeGroupId)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .DELETE()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStoreException(
          String.format("Failed to delete store group with id %s.", storeGroupId), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStoreException(
          String.format("Failed to delete store group with id %s.", storeGroupId),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStoreException(
          String.format("Failed to delete store group with id %s.", storeGroupId), e, null);
    }
  }

  /**
   * Partially updates and returns the updated com.brinkcommerce.api.Brink store group. HTTP method: PATCH. URI:
   * /store-groups/{storeGroupId}
   *
   * @param brinkStoreGroup is a com.brinkcommerce.api.Brink store group, used as URI
   * @return BrinkStoreGroup is a Store group object
   * @throws BrinkStoreException if an error occurs
   */
  public BrinkStoreGroup update(final BrinkStoreGroup brinkStoreGroup) {
    Objects.requireNonNull(brinkStoreGroup, "com.brinkcommerce.api.Brink Store group cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s", this.storeGroupUrl.toString(), brinkStoreGroup.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .method(
                  "PATCH", BodyPublishers.ofString(this.mapper.writeValueAsString(brinkStoreGroup)))
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkStoreGroup) this.brinkHttpUtil.handleResponse(response, BrinkStoreGroup.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkStoreException(
          String.format("Failed to update store group with id %s.", brinkStoreGroup.id()),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkStoreException(
          String.format("Failed to update store group with id %s.", brinkStoreGroup.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkStoreException(
          String.format("Failed to update store group with id %s.", brinkStoreGroup.id()), e, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }


}
