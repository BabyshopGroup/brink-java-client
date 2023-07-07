package com.brinkcommerce.api.configuration;

import java.net.URI;
import java.util.Objects;

public record BrinkAuthentication(
    String clientId,
    String clientSecret,
    URI tokenUrl,
    Integer tokenRefreshDeadline,
    String apiKey) {
  private static BrinkAuthentication of(
      final String clientId,
      final String clientSecret,
      final URI tokenUrl,
      final Integer tokenRefreshDeadline,
      final String apiKey) {
    return new BrinkAuthentication(
        Objects.requireNonNull(clientId, "Client id cannot be null."),
        Objects.requireNonNull(clientSecret, "Client secret cannot be null."),
        Objects.requireNonNull(tokenUrl, "Token  URL cannot be null."),
        Objects.requireNonNullElse(tokenRefreshDeadline, 5),
        Objects.requireNonNullElse(apiKey, "BrinkCommerceDefaultApiKey"));
  }

  public static BrinkAuthenticationConfigurationBuilder builder() {
    return new BrinkAuthenticationConfigurationBuilder();
  }

  public static final class BrinkAuthenticationConfigurationBuilder {

    private String clientId;
    private String clientSecret;
    private URI tokenUrl;
    private Integer tokenRefreshDeadline;
    private String apiKey;

    private BrinkAuthenticationConfigurationBuilder() {}

    public BrinkAuthenticationConfigurationBuilder withClientId(final String clientId) {
      this.clientId = clientId;
      return this;
    }

    public BrinkAuthenticationConfigurationBuilder withClientSecret(final String clientSecret) {
      this.clientSecret = clientSecret;
      return this;
    }

    public BrinkAuthenticationConfigurationBuilder withTokenUrl(final URI tokenUrl) {
      this.tokenUrl = tokenUrl;
      return this;
    }

    public BrinkAuthenticationConfigurationBuilder withTokenRefreshDeadline(
        final Integer tokenRefreshDeadline) {
      this.tokenRefreshDeadline = tokenRefreshDeadline;
      return this;
    }

    public BrinkAuthenticationConfigurationBuilder withApiKey(final String apiKey) {
      this.apiKey = apiKey;
      return this;
    }

    public BrinkAuthentication build() {
      return BrinkAuthentication.of(
          this.clientId, this.clientSecret, this.tokenUrl, this.tokenRefreshDeadline, this.apiKey);
    }
  }
}
