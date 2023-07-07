package com.brinkcommerce.api.management.product.addon;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.CONTENT_TYPE;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.httpRequestBuilderWithAuthentication;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.product.BrinkProductException;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Objects;

public class BrinkProductAddonApi {
  private static final String PATH = "product/addons";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;
  private final URI productAddonUrl;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;

  private BrinkProductAddonApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null");
    this.productAddonUrl = URI.create(String.format("%s/%s", config.host(), PATH));
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
  }

  public static BrinkProductAddonApi init(
        final ManagementConfiguration config, final AuthenticationHandler authHandler) {
    return new BrinkProductAddonApi(config, authHandler);
  }

  /**
   * Partially updates of an addon for a com.brinkcommerce.api.Brink product. HTTP method: PATCH. URI: /addons/{addonId}.
   *
   * @param request which holds the updated fields of the addon
   * @return BrinkProductAddon an addon
   * @throws BrinkProductException if an error occurs
   */
  public BrinkProductAddon update(final BrinkProductAddon request) {
    Objects.requireNonNull(request, "Addon update request cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.productAddonUrl.toString(), request.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .method("PATCH", BodyPublishers.ofString(this.mapper.writeValueAsString(request)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductAddon)
          this.brinkHttpUtil.handleResponse(response, BrinkProductAddon.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to delete addon %s.", request.id()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to delete addon %s.", request.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to delete addon %s.", request.id()), e, null);
    }
  }

  /**
   * Deletes an addon for a com.brinkcommerce.api.Brink product. HTTP method: DELETE. URI: /addons/{addonId}.
   *
   * @param id id of addon, used as URI
   * @throws BrinkProductException if an error occurs
   */
  public void delete(final String id) {
    Objects.requireNonNull(id, "Addon id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.productAddonUrl.toString(), id)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .DELETE()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(String.format("Failed to delete addon %s.", id), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to delete addon %s.", id), e, e.brinkHttpCode(), e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(String.format("Failed to delete addon %s.", id), e, null);
    }
  }

  /**
   * Full update of an addon for a com.brinkcommerce.api.Brink product. HTTP method: POST. URI: /addons/{addonId}.
   *
   * @param request which holds the addon request
   * @return BrinkProductAddon an addon
   * @throws BrinkProductException if an error occurs
   */
  public BrinkProductAddon create(final BrinkProductAddon request) {
    Objects.requireNonNull(request, "Update request body cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.productAddonUrl.toString(), request.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .PUT(BodyPublishers.ofString(this.mapper.writeValueAsString(request)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductAddon)
          this.brinkHttpUtil.handleResponse(response, BrinkProductAddon.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to update addon %s.", request.id()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to update addon %s.", request.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to update addon %s.", request.id()), e, null);
    }
  }

  /**
   * Returns an addon for a com.brinkcommerce.api.Brink product. HTTP method: GET. URI: /addons/{addonId}.
   *
   * @param id id of addon, used as URI
   * @return BrinkProductAddon an addon
   * @throws BrinkProductException if an error occurs
   */
  public BrinkProductAddon get(final String id) {
    Objects.requireNonNull(id, "Addon id cannot be null.");

    try {
      final HttpRequest request =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.productAddonUrl.toString(), id)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(request);

      return (BrinkProductAddon)
          this.brinkHttpUtil.handleResponse(response, BrinkProductAddon.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(String.format("Failed to get addon %s.", id), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to get addon %s.", id), e, e.brinkHttpCode(), e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(String.format("Failed to get addon %s.", id), e, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
