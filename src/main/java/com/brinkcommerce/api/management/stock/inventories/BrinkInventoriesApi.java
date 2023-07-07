package com.brinkcommerce.api.management.stock.inventories;

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

public class BrinkInventoriesApi {
  private static final String PATH = "stock/inventories/";
  private final ObjectMapper objectMapper;
  private final HttpClient httpClient;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;
  private final URI inventoriesUrl;

  private BrinkInventoriesApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.objectMapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.objectMapper);
    this.inventoriesUrl = URI.create(String.format("%s/%s", config.host(), PATH));
  }

  public static BrinkInventoriesApi init(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    return new BrinkInventoriesApi(config, authenticationHandler);
  }

  /**
   * Gets inventories. HTTP method: GET. URI: /stock/inventories
   *
   * @return inventories
   * @throws BrinkStockException if an error occurs
   */
  public BrinkInventoriesInventories get() {
    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(this.inventoriesUrl.toString().replaceFirst("/*$", "")),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkInventoriesInventories)
          this.brinkHttpUtil.handleResponse(response, BrinkInventoriesInventories.class);
    } catch (final InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new BrinkStockException("Failed to get inventories.", exception, null);
    } catch (final BrinkIntegrationException exception) {
      throw new BrinkStockException(
          "Failed to get inventories.",
          exception,
          exception.brinkHttpCode(),
          exception.requestId());
    } catch (final Exception exception) {
      throw new BrinkStockException("Failed to get inventories.", exception, null);
    }
  }

  /**
   * Gets inventory for inventory id. HTTP method: GET. URI: /stock/inventories/{inventoryId}
   *
   * @param inventoryId id of the inventory
   * @return inventory with inventory id
   * @throws BrinkStockException if an error occurs
   */
  public BrinkInventory get(final String inventoryId) {
    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  this.inventoriesUrl.resolve(String.format("./%s", inventoryId)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkInventory)
          this.brinkHttpUtil.handleResponse(response, BrinkInventory.class);
    } catch (final InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new BrinkStockException(
          String.format("Failed to get inventory with id %s.", inventoryId), exception, null);
    } catch (final BrinkIntegrationException exception) {
      throw new BrinkStockException(
          String.format("Failed to get inventory with id %s.", inventoryId),
          exception,
          exception.brinkHttpCode(),
          exception.requestId());
    } catch (final Exception exception) {
      throw new BrinkStockException(
          String.format("Failed to get inventory with id %s.", inventoryId), exception, null);
    }
  }

  /**
   * Creates inventory. HTTP method: PUT. URI: /stock/inventories/{inventoryId}
   *
   * @param brinkInventoriesInventoryRequest inventory to create
   * @return inventory
   * @throws BrinkStockException if an error occurs
   */
  public BrinkInventory create(
      final BrinkInventory brinkInventoriesInventoryRequest) {
    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  this.inventoriesUrl.resolve(
                      String.format("./%s", brinkInventoriesInventoryRequest.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .PUT(
                  HttpRequest.BodyPublishers.ofString(
                      this.objectMapper.writeValueAsString(brinkInventoriesInventoryRequest)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkInventory)
          this.brinkHttpUtil.handleResponse(response, BrinkInventory.class);
    } catch (final InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new BrinkStockException(
          String.format(
              "Failed to create inventory with id %s.", brinkInventoriesInventoryRequest.id()),
          exception,
          null);
    } catch (final BrinkIntegrationException exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to create inventory with id %s.", brinkInventoriesInventoryRequest.id()),
          exception,
          exception.brinkHttpCode(),
          exception.requestId());
    } catch (final Exception exception) {
      throw new BrinkStockException(
          String.format(
              "Failed to create inventory with id %s.", brinkInventoriesInventoryRequest.id()),
          exception,
          null);
    }
  }

  /**
   * Deletes inventory for inventory id. HTTP method: DELETE. URI: /stock/inventories/{inventoryId}
   *
   * @param inventoryId id of the inventory
   * @throws BrinkStockException if an error occurs
   */
  public void delete(final String inventoryId) {
    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  this.inventoriesUrl.resolve(String.format("./%s", inventoryId)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .DELETE()
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException exception) {
      Thread.currentThread().interrupt();
      throw new BrinkStockException(
          String.format("Failed to delete inventory with id %s.", inventoryId), exception, null);
    } catch (final BrinkIntegrationException exception) {
      throw new BrinkStockException(
          String.format("Failed to delete inventory with id %s.", inventoryId),
          exception,
          exception.brinkHttpCode(),
          exception.requestId());
    } catch (final Exception exception) {
      throw new BrinkStockException(
          String.format("Failed to delete inventory with id %s.", inventoryId), exception, null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
