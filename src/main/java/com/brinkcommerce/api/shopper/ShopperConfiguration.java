package com.brinkcommerce.api.shopper;

import com.brinkcommerce.api.configuration.BrinkMgmtConfigBuilder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;

public record ShopperConfiguration(
      String host,
      ObjectMapper mapper,
      HttpClient httpClient) {

  public static ShopperConfiguration of(
        final String host,
        final Integer timeoutInSeconds,
        final ObjectMapper objectMapper,
        final HttpClient httpClient) {

    HttpClient client = Objects.requireNonNullElse(httpClient, newHttpClient(timeoutInSeconds));

    return new ShopperConfiguration (
          Objects.requireNonNull(host, "Host must be set"),
          Objects.requireNonNullElse(objectMapper, newObjectMapper()),
          client);
  }

  private static HttpClient newHttpClient(final Integer timeoutInSeconds) {
    return  HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_1_1)
          .followRedirects(HttpClient.Redirect.NORMAL)
          .connectTimeout(
                Duration.ofSeconds(Objects.requireNonNullElse(timeoutInSeconds, 20)))
          .proxy(ProxySelector.getDefault())
          .build();
  }

  private static ObjectMapper newObjectMapper() {
    return new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
          .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static BrinkMgmtConfigBuilder builder() {
    return BrinkMgmtConfigBuilder.create();
  }
}