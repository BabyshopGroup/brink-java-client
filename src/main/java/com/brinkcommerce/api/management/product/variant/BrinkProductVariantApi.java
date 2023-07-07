package com.brinkcommerce.api.management.product.variant;

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

public class BrinkProductVariantApi {
  private static final String PATH = "product/product-variants";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;
  private final URI productVariantUrl;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;

  private BrinkProductVariantApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.productVariantUrl = URI.create(String.format("%s/%s", config.host(), PATH));
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
  }

  public static BrinkProductVariantApi init(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    return new BrinkProductVariantApi(config, authenticationHandler);
  }

  /**
   * Partially updates a given com.brinkcommerce.api.Brink product variant. HTTP method: PATCH. URI:
   * /products-variants/{variantId}.
   *
   * @param request com.brinkcommerce.api.Brink product variant to be created, used as URI
   * @return BrinkProductVariant the updated product variant
   * @throws BrinkProductException if an error occurs
   */
  public BrinkProductVariant update(final BrinkProductVariant request) {
    Objects.requireNonNull(request, "Product variant id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s", this.productVariantUrl.toString(), request.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .method("PATCH", BodyPublishers.ofString(this.mapper.writeValueAsString(request)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductVariant)
          this.brinkHttpUtil.handleResponse(response, BrinkProductVariant.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to update product variant id %s.", request.id()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to update product variant id %s.", request.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to update product variant id %s.", request.id()), e, null);
    }
  }

  /**
   * Deletes a given com.brinkcommerce.api.Brink product variant. HTTP method: DELETE. URI: /product-variants/{addonId}
   *
   * @param id id of com.brinkcommerce.api.Brink product variant, used as URI
   * @throws BrinkProductException if an error occurs
   */
  public void delete(final String id) {
    Objects.requireNonNull(id, "Product variant id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.productVariantUrl.toString(), id)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .DELETE()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to delete product variant id %s.", id), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to delete product variant id %s.", id),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to delete product variant id %s.", id), e, null);
    }
  }

  /**
   * Creates com.brinkcommerce.api.Brink product variant for a given com.brinkcommerce.api.Brink product parent. HTTP method: PUT. URI:
   * /product-variants/{addonId}
   *
   * @param request com.brinkcommerce.api.Brink product variant that is being created, used also as URI
   * @return BrinkProductVariant from the created product variant for the given product parent
   * @throws BrinkProductException if an error occurs
   */
  public BrinkProductVariant create(final BrinkProductVariant request) {
    Objects.requireNonNull(request, "Product variant cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s", this.productVariantUrl.toString(), request.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .PUT(BodyPublishers.ofString(this.mapper.writeValueAsString(request)))
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductVariant)
          this.brinkHttpUtil.handleResponse(response, BrinkProductVariant.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to create product variant id %s.", request.id()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to create product variant id %s.", request.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to create product variant id %s.", request.id()), e, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }


}
