package com.brinkcommerce.api.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BrinkAuthenticationResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("token_type") String tokenType) {}
