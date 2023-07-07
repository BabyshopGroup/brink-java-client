package com.brinkcommerce.api.management.stock.productvariants;

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

public class BrinkProductVariantsApi {
  private static final String PATH = "stock/product-variants/";
  private final ObjectMapper objectMapper;
  private final HttpClient httpClient;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;
  private final URI productVariantsUrl;

  private BrinkProductVariantsApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.objectMapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.objectMapper);
    this.productVariantsUrl = URI.create(String.format("%s/%s", config.host(), PATH));
  }

  public static BrinkProductVariantsApi init(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    return new BrinkProductVariantsApi(config, authenticationHandler);
  }

  /**
   * Updates inventory for product. HTTP method: PUT. URI:
   * /stock/product-variants/{variantId}/inventories/{inventoryId}
   *
   * @param brinkVariantInventory which holds the request
   * @return status of product inventory after update
   * @throws BrinkStockException if an error occurs
   */
  public BrinkVariantInventory update(
      final BrinkVariantInventory brinkVariantInventory) {
    Objects.requireNonNull(brinkVariantInventory, "com.brinkcommerce.api.Brink product inventory cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  this.productVariantsUrl.resolve(
                      String.format(
                          "./%s/inventories/%s",
                          brinkVariantInventory.variantId(),
                          brinkVariantInventory.inventoryId())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .PUT(
                  HttpRequest.BodyPublishers.ofString(
                      this.objectMapper.writeValueAsString(brinkVariantInventory)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkVariantInventory)
          this.brinkHttpUtil.handleResponse(response, BrinkVariantInventory.class);
    } catch (final InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new BrinkStockException(
          String.format(
              "Failed to update inventory for product %s in inventory %s.",
              brinkVariantInventory.variantId(),
              brinkVariantInventory.inventoryId()),
          exception,
          null);
    } catch (final BrinkIntegrationException exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to update inventory for product %s in inventory %s.",
              brinkVariantInventory.variantId(),
              brinkVariantInventory.inventoryId()),
          exception,
          exception.brinkHttpCode(),
          exception.requestId());
    } catch (final Exception exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to update inventory for product %s in inventory %s.",
              brinkVariantInventory.variantId(),
              brinkVariantInventory.inventoryId()),
          exception,
          null);
    }
  }

  /**
   * Updates product. HTTP method: PUT. URI: /stock/product-variants/{variantId}
   *
   * @param brinkProductVariantStock which holds the request
   * @return status of product after update
   * @throws BrinkStockException if an error occurs
   */
  public BrinkProductVariantStock update(final BrinkProductVariantStock brinkProductVariantStock) {
    Objects.requireNonNull(brinkProductVariantStock, "com.brinkcommerce.api.Brink product cannot be null");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  this.productVariantsUrl.resolve(
                      String.format("./%s", brinkProductVariantStock.productVariantId())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .PUT(
                  HttpRequest.BodyPublishers.ofString(
                      this.objectMapper.writeValueAsString(brinkProductVariantStock)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductVariantStock)
          this.brinkHttpUtil.handleResponse(response, BrinkProductVariantStock.class);
    } catch (final InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new BrinkStockException(
          String.format(
              "Failed to update product %s.", brinkProductVariantStock.productVariantId()),
          exception,
          null);
    } catch (final BrinkIntegrationException exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to update product %s.", brinkProductVariantStock.productVariantId()),
          exception,
          exception.brinkHttpCode(),
          exception.requestId());
    } catch (final Exception exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to update product %s.", brinkProductVariantStock.productVariantId()),
          exception,
          null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
