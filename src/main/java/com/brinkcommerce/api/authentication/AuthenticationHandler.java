package com.brinkcommerce.api.authentication;

import com.auth0.jwt.JWT;
import com.brinkcommerce.api.configuration.BrinkAuthentication;
import com.brinkcommerce.api.exception.BrinkAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class AuthenticationHandler {

  private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
  private static final String GRANT_TYPE = "grant_type=client_credentials";
  private static final String AUTHENTICATION_TYPE = "Basic";

  private final BrinkAuthentication authenticationConfig;

  private final HttpClient authHttpClient;

  private final ObjectMapper objectMapper;

  private final String scopes;

  private BrinkAuthenticationToken authenticationToken;

  private AuthenticationHandler(
      final BrinkAuthentication authenticationConfig,
      final HttpClient httpClient,
      final HttpClient authHttpClient,
      final ObjectMapper objectMapper,
      final String scopes) {
    this.authenticationConfig =
        Objects.requireNonNull(authenticationConfig, "AuthenticationConfiguration cannot be null");
    this.authHttpClient = Objects.requireNonNullElse(authHttpClient, httpClient);
    this.objectMapper = Objects.requireNonNull(objectMapper, "ObjectMapper cannot be null.");
    this.scopes = scopes;
  }

  public String getToken() {
    return authenticationToken().accessToken();
  }

  public String getApiKey() {
    return this.authenticationConfig.apiKey();
  }

  public String overrideToken() {
    return authenticationToken().accessToken();
  }

  private BrinkAuthenticationToken authenticationToken() {
    if (Objects.isNull(this.authenticationToken) || tokenHasExpired()) {
      generateToken();
    }
    return this.authenticationToken;
  }

  private boolean tokenHasExpired() {
    return Objects.nonNull(this.authenticationToken)
        && this.authenticationToken.expirationEpochTime().get() - Instant.now().getEpochSecond()
            < this.authenticationConfig.tokenRefreshDeadline();
  }

  private void generateToken() {
    try {
      final HttpResponse<String> response = makeAuthenticationRequest();

      final BrinkAuthenticationResponse brinkAuthenticationResponse =
          Objects.requireNonNull(
              this.objectMapper.readValue(response.body(), BrinkAuthenticationResponse.class),
              "Empty response from com.brinkcommerce.api.Brink authentication server.");

      setAuthenticationToken(brinkAuthenticationResponse);
    } catch (final InterruptedException ignored) {
      Thread.currentThread().interrupt();
    } catch (final Exception e) {
      throw new BrinkAuthenticationException("Failed to authenticate against com.brinkcommerce.api.Brink.", e);
    }
  }

  private HttpResponse<String> makeAuthenticationRequest()
      throws IOException, InterruptedException {
    final HttpRequest request =
        HttpRequest.newBuilder()
            .uri(this.authenticationConfig.tokenUrl())
            .headers(
                "Authorization",
                String.format("%s %s", AUTHENTICATION_TYPE, base64EncodedClientCredentials()))
            .headers("Content-type", CONTENT_TYPE)
            .headers("scope", this.scopes)
            .POST(HttpRequest.BodyPublishers.ofString(GRANT_TYPE))
            .build();

    return this.authHttpClient.send(request, HttpResponse.BodyHandlers.ofString());
  }

  private String base64EncodedClientCredentials() {
    return Base64.getEncoder()
        .encodeToString(
            String.format(
                    "%s:%s",
                    this.authenticationConfig.clientId(), this.authenticationConfig.clientSecret())
                .getBytes());
  }

  private void setAuthenticationToken(
      final BrinkAuthenticationResponse brinkAuthenticationResponse) {
    this.authenticationToken =
        BrinkAuthenticationToken.builder()
            .withAccessToken(brinkAuthenticationResponse.accessToken())
            .withTokenType(brinkAuthenticationResponse.tokenType())
            .withExpirationTime(
                new AtomicLong(
                    JWT.decode(brinkAuthenticationResponse.accessToken()).getClaim("exp").asLong()))
            .build();
  }

  public static AuthenticationHandlerBuilder builder() {
    return new AuthenticationHandlerBuilder();
  }

  public static final class AuthenticationHandlerBuilder {

    private BrinkAuthentication brinkAuthenticationConfig;
    private HttpClient httpClient;
    private HttpClient authHttpClient;
    private ObjectMapper objectMapper;
    private String scopes;

    private AuthenticationHandlerBuilder() {}

    public AuthenticationHandlerBuilder withBrinkAuthenticationConfig(
        final BrinkAuthentication brinkAuthenticationConfig) {
      this.brinkAuthenticationConfig = brinkAuthenticationConfig;
      return this;
    }

    public AuthenticationHandlerBuilder withHttpClient(final HttpClient httpClient) {
      this.httpClient = httpClient;
      return this;
    }

    public AuthenticationHandlerBuilder withAuthHttpClient(final HttpClient authHttpClient) {
      this.authHttpClient = authHttpClient;
      return this;
    }

    public AuthenticationHandlerBuilder withObjectMapper(final ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
      return this;
    }

    public AuthenticationHandlerBuilder withScopes(final String scopes) {
      this.scopes = scopes;
      return this;
    }

    public AuthenticationHandler build() {
      return new AuthenticationHandler(
          brinkAuthenticationConfig, httpClient, authHttpClient, objectMapper, scopes);
    }
  }
}
