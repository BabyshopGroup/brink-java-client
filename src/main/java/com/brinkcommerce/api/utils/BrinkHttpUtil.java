package com.brinkcommerce.api.utils;

import com.brinkcommerce.api.common.BrinkHttpErrorMessage;
import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkIntegrationException;
import com.brinkcommerce.api.management.price.addon.AddonRequest;
import com.brinkcommerce.api.management.price.variant.model.VariantRequest;
import com.brinkcommerce.api.management.store.market.BrinkStoreMarket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.Objects;
import org.apache.http.HttpStatus;


public class BrinkHttpUtil {

  private final ObjectMapper mapper;

  public BrinkHttpUtil(final ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public static BrinkHttpUtil create(final ObjectMapper mapper) {
    return new BrinkHttpUtil(mapper);
  }

  public static BrinkHttpUtil init() {
    return null;
  };

  private static final String BEARER = "Bearer";
  private static final String AUTHORIZATION = "Authorization";
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String APPLICATION_JSON = "application/json";
  public static final String ACCEPT = "accept";
  public static final String X_API_KEY = "x-api-key";

  public static Builder httpRequestBuilderWithAuthentication(
      final URI uri, final String token, final String apiKey) {
    return HttpRequest.newBuilder()
        .uri(uri)
        .header(AUTHORIZATION, String.format("%s %s", BEARER, token))
        .header(X_API_KEY, String.format("%s", apiKey));
  }

  public <T> Object handleResponse(final HttpResponse<String> response, final Class<T> typeParameterClass)
      throws JsonProcessingException {

    if (response.statusCode() == HttpStatus.SC_OK) {
      return Objects.requireNonNull(
          this.mapper.readValue(response.body(), typeParameterClass),
          "Empty response from com.brinkcommerce.api.Brink API.");
    } else if (response.statusCode() == HttpStatus.SC_NO_CONTENT) {
      return "";
    } else if (response.statusCode() == HttpStatus.SC_ACCEPTED) {
      // brittle clause to handle start delivery response
      return "";
    } else {
      final BrinkHttpErrorMessage errorMessage =
          this.mapper.readValue(response.body(), BrinkHttpErrorMessage.class);

      throw new BrinkIntegrationException(
          response.body(),
          errorMessage.requestId(),
          BrinkHttpCode.fromHttpCode(response.statusCode()));
    }
  }

  @Deprecated
  public String buildURI(final String baseUrl) {
    return new StringBuilder()
            .append(String.format("%s/", baseUrl))
            .append("discounts/codes)")
            .toString();
  }

  public static String buildURI(final VariantRequest request, final String baseUrl) {
    return new StringBuilder()
        .append(String.format("%s/", baseUrl))
        .append(String.format("store-groups/%s/", request.storeGroupId()))
        .append(String.format("product-variants/%s/", request.productVariantId()))
        .append("prices")
        .toString();
  }

  public static String buildURI(final AddonRequest request, final String baseUrl) {
    return new StringBuilder()
        .append(String.format("%s/", baseUrl))
        .append(String.format("store-groups/%s/", request.storeGroupId()))
        .append(String.format("markets/%s/", request.countryCode()))
        .append(String.format("addons/%s/", request.id()))
        .append("price")
        .toString();
  }

  public static String buildURI(final BrinkStoreMarket request, final String storeGroupUrl) {
    return new StringBuilder()
        .append(String.format("%s/", storeGroupUrl))
        .append(String.format("%s/", request.storeGroupId()))
        .append(String.format("markets/%s/", request.countryCode()))
        .toString();
  }
}
