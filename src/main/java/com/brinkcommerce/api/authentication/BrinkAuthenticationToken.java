package com.brinkcommerce.api.authentication;

import java.util.concurrent.atomic.AtomicLong;

public record BrinkAuthenticationToken(
    String accessToken, String tokenType, AtomicLong expirationEpochTime) {

  public static BrinkAuthenticationTokenBuilder builder() {
    return new BrinkAuthenticationTokenBuilder();
  }

  public static final class BrinkAuthenticationTokenBuilder {

    private String accessToken;
    private String tokenType;
    private AtomicLong expirationEpochTime;

    private BrinkAuthenticationTokenBuilder() {}

    public BrinkAuthenticationTokenBuilder withAccessToken(final String accessToken) {
      this.accessToken = accessToken;
      return this;
    }

    public BrinkAuthenticationTokenBuilder withTokenType(final String tokenType) {
      this.tokenType = tokenType;
      return this;
    }

    public BrinkAuthenticationTokenBuilder withExpirationTime(
        final AtomicLong expirationEpochTime) {
      this.expirationEpochTime = expirationEpochTime;
      return this;
    }

    public BrinkAuthenticationToken build() {
      return new BrinkAuthenticationToken(accessToken, tokenType, expirationEpochTime);
    }
  }
}
