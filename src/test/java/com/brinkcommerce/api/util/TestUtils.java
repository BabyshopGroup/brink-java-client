package com.brinkcommerce.api.util;

import com.brinkcommerce.api.configuration.BrinkAuthentication;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.shopper.ShopperConfiguration;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;
import javax.net.ssl.SSLSession;

public class TestUtils {

  private static final ObjectMapper objectMapper =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  public static <T> HttpResponse<String> mockHttpResponse(
      final T returnObject, final int httpStatus) {

    return new HttpResponse<String>() {
      @Override
      public int statusCode() {
        return httpStatus;
      }

      @Override
      public HttpRequest request() {
        return null;
      }

      @Override
      public Optional<HttpResponse<String>> previousResponse() {
        return Optional.empty();
      }

      @Override
      public HttpHeaders headers() {
        return null;
      }

      @Override
      public String body() {
        try {
          if (Objects.isNull(returnObject)) {
            return "";
          }

          return objectMapper.writeValueAsString(returnObject);
        } catch (final JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      }

      @Override
      public Optional<SSLSession> sslSession() {
        return Optional.empty();
      }

      @Override
      public URI uri() {
        return null;
      }

      @Override
      public Version version() {
        return null;
      }
    };
  }

  public static ShopperConfiguration mockBrinkShopperConfiguration(final HttpClient httpClient) {
    return new ShopperConfiguration(
          "http://mockserver.com",
          new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL),
          httpClient);
  }

  public static ManagementConfiguration mockBrinkConfiguration(
        final BrinkAuthentication authenticationConfiguration, final HttpClient httpClient) {
    return new ManagementConfiguration(
        "http://mockserver.com",
        new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL),
        httpClient,
        httpClient, authenticationConfiguration);
  }

  public static BrinkAuthentication mockAuthenticationConfiguration() {
    return new BrinkAuthentication(
        "Mock", "Mock", URI.create("http://www.mockurl.com"), 5, "Mock");
  }
}
