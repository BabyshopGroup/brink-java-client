package com.brinkcommerce.api.management.price.addon;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.CONTENT_TYPE;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.buildURI;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.httpRequestBuilderWithAuthentication;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.price.BrinkPriceException;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class BrinkPriceAddonApi {
  private static final String PATH = "price";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;
  private final URI priceVariantUrl;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;

  private BrinkPriceAddonApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.priceVariantUrl = URI.create(String.format("%s/%s", config.host(), PATH));
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
  }

  public static BrinkPriceAddonApi init(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    return new BrinkPriceAddonApi(config, authenticationHandler);
  }

  /**
   * Creates price for a com.brinkcommerce.api.Brink Addon and returns the price object. HTTP method: PUT. URI:
   * /store-groups/{storeGroupId}/markets/{countryCode}/addons/{addonId}/price
   *
   * @param brinkPriceAddon adds a price to an addon
   * @return BrinkPriceAddon a com.brinkcommerce.api.Brink addon price
   * @throws BrinkPriceException if an error occurs
   */
  public BrinkPriceAddon create(final BrinkPriceAddon brinkPriceAddon) {

    Objects.requireNonNull(brinkPriceAddon, "com.brinkcommerce.api.Brink price addon cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
              URI.create(buildURI(brinkPriceAddon, this.priceVariantUrl.toString())),
              this.authenticationHandler.getToken(),
              this.authenticationHandler.getApiKey())
              .PUT(
                  HttpRequest.BodyPublishers.ofString(
                      this.mapper.writeValueAsString(brinkPriceAddon)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkPriceAddon) this.brinkHttpUtil.handleResponse(response, BrinkPriceAddon.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkPriceException(
          String.format("Failed to create price with addon id %s.", brinkPriceAddon.id()),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkPriceException(
          String.format("Failed to create price with addon id %s.", brinkPriceAddon.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkPriceException(
          String.format("Failed to create price with addon id %s.", brinkPriceAddon.id()), e, null);
    }
  }

  /**
   * Updates price for a com.brinkcommerce.api.Brink Addon and returns the price object. HTTP method: PATCH. URI:
   * /store-groups/{storeGroupId}/markets/{countryCode}/addons/{addonId}/price
   *
   * @param brinkPriceAddon adds a price to an addon
   * @return BrinkPriceAddon a com.brinkcommerce.api.Brink addon price
   * @throws BrinkPriceException if an error occurs
   */
  public BrinkPriceAddon update(final BrinkPriceAddon brinkPriceAddon) {

    Objects.requireNonNull(brinkPriceAddon, "com.brinkcommerce.api.Brink price addon cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
              URI.create(buildURI(brinkPriceAddon, this.priceVariantUrl.toString())),
              this.authenticationHandler.getToken(),
              this.authenticationHandler.getApiKey())
              .method("PATCH",
                  HttpRequest.BodyPublishers.ofString(
                      this.mapper.writeValueAsString(brinkPriceAddon)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkPriceAddon) this.brinkHttpUtil.handleResponse(response, BrinkPriceAddon.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkPriceException(
          String.format("Failed to update price with addon id %s.", brinkPriceAddon.id()),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkPriceException(
          String.format("Failed to update price with addon id %s.", brinkPriceAddon.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkPriceException(
          String.format("Failed to update price with addon id %s.", brinkPriceAddon.id()), e, null);
    }
  }

  /**
   * Returns a com.brinkcommerce.api.Brink price addon for a specific addon store-group id, country code and addon id.
   * HTTP method: GET. URI:
   * /store-groups/{storeGroupId}/markets/{countryCode}/addons/{addonId}/price
   *
   * @param request object with request variables
   * @return BrinkPriceAddon a com.brinkcommerce.api.Brink price for an addon
   * @throws BrinkPriceException if an error occurs
   */
  public BrinkPriceAddon get(final BrinkPriceAddonRequest request) {

    Objects.requireNonNull(request, "com.brinkcommerce.api.Brink price addon request cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(buildURI(request, this.priceVariantUrl.toString())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkPriceAddon) this.brinkHttpUtil.handleResponse(response, BrinkPriceAddon.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkPriceException(
          String.format("Failed to get price with addon id %s.", request.id()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkPriceException(
          String.format("Failed to get price with addon id %s.", request.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkPriceException(
          String.format("Failed to get price with addon id %s.", request.id()), e, null);
    }
  }

  /**
   * Deletes a com.brinkcommerce.api.Brink price addon for a specific addon id, store-group and market. HTTP method:
   * DELETE. URI: /store-groups/{storeGroupId}/markets/{countryCode}/addons/{addonId}/price
   *
   * @param request holds the request variables
   * @throws BrinkPriceException if an error occurs
   */
  public void delete(final BrinkPriceAddonRequest request) {

    Objects.requireNonNull(request, "com.brinkcommerce.api.Brink price addon cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(buildURI(request, this.priceVariantUrl.toString())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .DELETE()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkPriceException(
          String.format("Failed to delete price with addon id %s.", request.id()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkPriceException(
          String.format("Failed to delete price with addon id %s.", request.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkPriceException(
          String.format("Failed to delete price with addon id %s.", request.id()), e, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
