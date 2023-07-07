package com.brinkcommerce.api.management.price.variant;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.CONTENT_TYPE;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.buildURI;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.httpRequestBuilderWithAuthentication;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.price.BrinkPriceException;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantDeleteRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantListRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantListResponse;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantRequest;
import com.brinkcommerce.api.utils.BrinkHttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class BrinkPriceVariantApi {

  private static final String PATH = "price";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;
  private final URI priceVariantUrl;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;

  public BrinkPriceVariantApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.priceVariantUrl = URI.create(String.format("%s/%s", config.host(), PATH));
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
  }

  public static BrinkPriceVariantApi init(
        final ManagementConfiguration config, AuthenticationHandler authenticationHandler) {
    return new BrinkPriceVariantApi(config, authenticationHandler);
  }

  /**
   * Creates and returns a com.brinkcommerce.api.Brink price for a com.brinkcommerce.api.Brink product variant in a market of a store-group.
   * HTTP method: PUT. URI:
   * /store-groups/{storeGroupId}/markets/{countryCode}/product-variants/{variantId}/price
   *
   * @param brinkPriceVariantListRequest includes com.brinkcommerce.api.Brink prices for a com.brinkcommerce.api.Brink product variants.
   * @return BrinkPriceVariantList a com.brinkcommerce.api.Brink price for a com.brinkcommerce.api.Brink product variant.
   * @throws BrinkPriceException if an error occurs
   */
  public BrinkPriceVariantListResponse create(
      final BrinkPriceVariantListRequest brinkPriceVariantListRequest) {
    Objects.requireNonNull(brinkPriceVariantListRequest, "com.brinkcommerce.api.Brink price variant list cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
              URI.create(
                  BrinkHttpUtil.buildURI(brinkPriceVariantListRequest,
                      this.priceVariantUrl.toString())),
              this.authenticationHandler.getToken(),
              this.authenticationHandler.getApiKey())
              .PUT(
                  HttpRequest.BodyPublishers.ofString(
                      this.mapper.writeValueAsString(brinkPriceVariantListRequest)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkPriceVariantListResponse)
          this.brinkHttpUtil.handleResponse(response, BrinkPriceVariantListResponse.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkPriceException(
          String.format("Failed to create price with variant variantId %s.",
              brinkPriceVariantListRequest.productVariantId()),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkPriceException(
          String.format("Failed to create price with variant variantId %s.",
              brinkPriceVariantListRequest.productVariantId()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkPriceException(
          String.format("Failed to create price with variant variantId %s.",
              brinkPriceVariantListRequest.productVariantId()),
          e,
          null);
    }
  }

  /**
   * Returns com.brinkcommerce.api.Brink price for a com.brinkcommerce.api.Brink product variant in a market of a store-group. HTTP method: GET.
   * URI:
   * /store-groups/{storeGroupId}/markets/{countryCode}/product-variants/{variantId}/price
   *
   * @param request which holds the request
   * @return BrinkPrice a price object
   * @throws BrinkPriceException if an error occurs
   */
  public BrinkPriceVariantListResponse get(final BrinkPriceVariantRequest request) {
    Objects.requireNonNull(request, "com.brinkcommerce.api.Brink price variant cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
              URI.create(BrinkHttpUtil.buildURI(request, this.priceVariantUrl.toString())),
              this.authenticationHandler.getToken(),
              this.authenticationHandler.getApiKey())
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkPriceVariantListResponse)
          this.brinkHttpUtil.handleResponse(response, BrinkPriceVariantListResponse.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkPriceException(
          String.format("Failed to get price with variant variantId %s.", request.productVariantId()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkPriceException(
          String.format("Failed to get price with variant variantId %s.", request.productVariantId()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkPriceException(
          String.format("Failed to get price with variant variantId %s.", request.productVariantId()), e, null);
    }
  }

  /**
   * Deletes a com.brinkcommerce.api.Brink price for a com.brinkcommerce.api.Brink product variant in a market of a store-group. HTTP method:
   * DELETE. URI:
   * /store-groups/{storeGroupId}/markets/{countryCode}/product-variants/{variantId}/price
   *
   * @param request which holds the request
   * @throws BrinkPriceException if an error occurs
   */
  public void delete(final BrinkPriceVariantDeleteRequest request) {
    Objects.requireNonNull(request, "com.brinkcommerce.api.Brink price variant cannot be null");

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
          String.format("Failed to delete price with variant variantId %s.", request.productVariantId()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkPriceException(
          String.format("Failed to delete price with variant variantId %s.", request.productVariantId()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkPriceException(
          String.format("Failed to delete price with variant variantId %s.", request.productVariantId()), e, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
