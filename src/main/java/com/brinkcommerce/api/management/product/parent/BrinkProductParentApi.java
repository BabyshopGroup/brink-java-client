package com.brinkcommerce.api.management.product.parent;

import static com.brinkcommerce.api.utils.BrinkHttpUtil.APPLICATION_JSON;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.CONTENT_TYPE;
import static com.brinkcommerce.api.utils.BrinkHttpUtil.httpRequestBuilderWithAuthentication;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.product.BrinkProductException;
import com.brinkcommerce.api.management.product.variant.BrinkProductVariant;
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

public class BrinkProductParentApi {
  private static final String VARIANTS_URI = "variants";
  private static final String ADDONS_URI = "addons";
  private static final String ARCHIVE_URI = "archive";
  private static final String PATH = "product/product-parents";

  private final HttpClient httpClient;
  private final ObjectMapper mapper;
  private final URI productParentUrl;
  private final AuthenticationHandler authenticationHandler;
  private final BrinkHttpUtil brinkHttpUtil;

  private BrinkProductParentApi(
        final ManagementConfiguration config, final AuthenticationHandler authenticationHandler) {
    Objects.requireNonNull(config, "Configuration cannot be null.");
    Objects.requireNonNull(config.host(), "Management Host URL cannot be null.");
    this.mapper = Objects.requireNonNull(config.mapper(), "ObjectMapper cannot be null.");
    this.httpClient = Objects.requireNonNull(config.httpClient(), "HttpClient cannot be null.");
    this.productParentUrl = URI.create(String.format("%s/%s", config.host(), PATH));
    this.authenticationHandler = authenticationHandler;
    this.brinkHttpUtil = BrinkHttpUtil.create(this.mapper);
  }

  public static BrinkProductParentApi init(
        final ManagementConfiguration config, final AuthenticationHandler authHandler) {
    return new BrinkProductParentApi(config, authHandler);
  }

  /**
   * Creates a com.brinkcommerce.api.Brink product parent HTTP method: PUT. URI: /product-parents/{productParentId}.
   *
   * @param request request body with product parent data
   * @return BrinkProductParent the generated product parent
   * @throws BrinkProductException if an error occurs
   */
  public BrinkProductParent create(BrinkProductParent request) {
    Objects.requireNonNull(request, "Product parent cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s", this.productParentUrl.toString(), request.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .PUT(BodyPublishers.ofString(this.mapper.writeValueAsString((request))))
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductParent)
          this.brinkHttpUtil.handleResponse(response, BrinkProductParent.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to get product parent with id %s.", request.id()), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to get product parent with id %s.", request.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to get product parent with id %s.", request.id()), e, null);
    }
  }

  /**
   * Returns a com.brinkcommerce.api.Brink product parent HTTP method: GET. URI: /product-parents/{addonId}.
   *
   * @param id id of product parent to be fetched, used as URI
   * @return BrinkProductParent the requested com.brinkcommerce.api.Brink product parent
   * @throws BrinkProductException if an error occurs
   */
  public BrinkProductParent get(final String id) {
    Objects.requireNonNull(id, "Product parent id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.productParentUrl.toString(), id)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductParent)
          this.brinkHttpUtil.handleResponse(response, BrinkProductParent.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to get product parent with id %s.", id), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to get product parent with id %s.", id),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to get product parent with id %s.", id), e, null);
    }
  }

  /**
   * Partially updates a com.brinkcommerce.api.Brink product parent HTTP method: PATCH. URI:
   * /product-parents/{productParentId}.
   *
   * @param request request body that contains fields to be updated
   * @return BrinkProductParent the updated product parent
   * @throws BrinkProductException if an error occurs
   */
  public BrinkProductParent update(final BrinkProductParent request) {
    Objects.requireNonNull(request, "Update request cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s", this.productParentUrl.toString(), request.id())),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .method("PATCH", BodyPublishers.ofString(this.mapper.writeValueAsString(request)))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkProductParent)
          this.brinkHttpUtil.handleResponse(response, BrinkProductParent.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to partially update product parent with id %s.", request.id()),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to partially update product parent with id %s.", request.id()),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to partially update product parent with id %s.", request.id()),
          e,
          null);
    }
  }

  /**
   * Deletes a com.brinkcommerce.api.Brink product parent HTTP method: DELETE. URI: /product-parents/{productParentId}.
   *
   * @param id id of product parent to be deleted, used as URI
   * @throws BrinkProductException if an error occurs
   */
  public void delete(final String id) {
    Objects.requireNonNull(id, "Product parent id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(String.format("%s/%s", this.productParentUrl.toString(), id)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .DELETE()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to delete product parent with id %s.", id), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to get product parent with id %s.", id),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to delete product parent with id %s.", id), e, null);
    }
  }

  /**
   * Archives a com.brinkcommerce.api.Brink product parent HTTP method: POST. URI:
   * /product-parents/{productParentId}/archive.
   *
   * @param id id of product parent to be archived, used as URI
   * @param includeProductVariants which determines whether the product variants also should be
   *     archived
   * @throws BrinkProductException if an error occurs
   */
  public void archiveBrinkProductParent(final String id, final Boolean includeProductVariants) {
    Objects.requireNonNull(id, "Product parent id cannot be null.");

    try {

      record RequestWrapper(Boolean includeProductVariants) {}

      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s/%s", this.productParentUrl.toString(), id, ARCHIVE_URI)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .POST(
                  BodyPublishers.ofString(
                      this.mapper.writeValueAsString(new RequestWrapper(includeProductVariants))))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to archive product parent with id %s.", id), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to archive product parent with id %s.", id),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to archive product parent with id %s.", id), e, null);
    }
  }

  /**
   * Returns addons for a given com.brinkcommerce.api.Brink product parent HTTP method: GET. URI:
   * /product-parents/{productParentId}/addons.
   *
   * @param id id of product parent, used as URI
   * @return List<BrinkProductParentAddon> all addons for the given productParentId
   * @throws BrinkProductException if an error occurs
   */
  public List<BrinkProductParentAddon> getAddons(final String id) {
    Objects.requireNonNull(id, "Product parent id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format("%s/%s/%s", this.productParentUrl.toString(), id, ADDONS_URI)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      record ResponseWrapper(List<BrinkProductParentAddon> productAddons) {}

      final ResponseWrapper responseWrapper =
          (ResponseWrapper) this.brinkHttpUtil.handleResponse(response, ResponseWrapper.class);

      return responseWrapper.productAddons();
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to get product addons for product parent id %s.", id), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to get product addons for product parent id %s.", id),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to get product addons for product parent id %s.", id), e, null);
    }
  }

  /**
   * Adds a com.brinkcommerce.api.Brink product addon to a given product parent HTTP method: POST. URI:
   * /product-parents/{productParentId}/addons.
   *
   * @param productParentId id of product parent, used as URI
   * @param addonId id of the addon that should be added to the given product parent
   * @return BrinkProductAddonId id of the added addon
   * @throws BrinkProductException if an error occurs
   */
  public BrinkResponseWrapper addAddon(final String productParentId, final String addonId) {
    Objects.requireNonNull(productParentId, "Product parent id cannot be null.");
    Objects.requireNonNull(addonId, "Product addon id cannot be null.");

    try {
      record RequestWrapper(String addonId) {}

      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format(
                          "%s/%s/%s",
                          this.productParentUrl.toString(), productParentId, ADDONS_URI)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .POST(
                  BodyPublishers.ofString(
                      this.mapper.writeValueAsString(new RequestWrapper(addonId))))
              .header(CONTENT_TYPE, APPLICATION_JSON)
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      return (BrinkResponseWrapper)
          this.brinkHttpUtil.handleResponse(response, BrinkResponseWrapper.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to create product addons for product parent id %s.", addonId),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to create product addons for product parent id %s.", addonId),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to create product addons for product parent id %s.", addonId),
          e,
          null);
    }
  }

  /**
   * Returns com.brinkcommerce.api.Brink product variants for a given com.brinkcommerce.api.Brink product parent HTTP method: GET. URI:
   * /product-parents/{productParentId}/variants.
   *
   * @param id id of product parent, used as URI
   * @return List<BrinkProductVariant> with all com.brinkcommerce.api.Brink product variants for the given product parent
   * @throws BrinkProductException if an error occurs
   */
  public List<BrinkProductVariant> getProductVariants(final String id) {
    Objects.requireNonNull(id, "Product parent id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format(
                          "%s/%s/%s", this.productParentUrl.toString(), id, VARIANTS_URI)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .GET()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);
      record ResponseWrapper(List<BrinkProductVariant> productVariants) {}

      final ResponseWrapper responseWrapper =
          (ResponseWrapper) this.brinkHttpUtil.handleResponse(response, ResponseWrapper.class);

      return responseWrapper.productVariants();
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to get product variants for product parent id %s.", id), ie, null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to get product variants for product parent id %s.", id),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to get product variants for product parent id %s.", id), e, null);
    }
  }

  /**
   * Deletes an addon for a com.brinkcommerce.api.Brink product parent HTTP method: DELETE. URI:
   * /product-parents/{productParentId}/addons/{addonId}.
   *
   * @param id id of product parent, used as URI
   * @param addonId id of addon, used as URI
   * @throws BrinkProductException if an error occurs
   */
  public void deleteProductParentAddons(final String id, final String addonId) {
    Objects.requireNonNull(id, "Product parent id cannot be null.");
    Objects.requireNonNull(addonId, "Addon id cannot be null.");

    try {
      final HttpRequest httpRequest =
          httpRequestBuilderWithAuthentication(
                  URI.create(
                      String.format(
                          "%s/%s/%s/%s",
                          this.productParentUrl.toString(), id, ADDONS_URI, addonId)),
                  this.authenticationHandler.getToken(),
                  this.authenticationHandler.getApiKey())
              .DELETE()
              .build();

      final HttpResponse<String> response = makeRequest(httpRequest);

      this.brinkHttpUtil.handleResponse(response, Void.class);
    } catch (final InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new BrinkProductException(
          String.format("Failed to delete addon %s for product parent id %s.", addonId, id),
          ie,
          null);
    } catch (final BrinkIntegrationException e) {
      throw new BrinkProductException(
          String.format("Failed to delete addon id %s for product parent id %s.", addonId, id),
          e,
          e.brinkHttpCode(),
          e.requestId());
    } catch (final Exception e) {
      throw new BrinkProductException(
          String.format("Failed to delete addon id %s for product parent id %s.", addonId, id),
          e,
          null);
    }
  }

  private HttpResponse<String> makeRequest(final HttpRequest request)
      throws IOException, InterruptedException {
    return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }
}
