package com.brinkcommerce.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpClient;

public class BrinkMgmtConfigBuilder {
  private String host;
  private String shopperHostUrl;
  private Integer timeout;
  private ObjectMapper mapper;
  private HttpClient httpClient;
  private HttpClient authHttpClient;
  private BrinkAuthentication authenticationConfiguration;

  private BrinkMgmtConfigBuilder() {}

  public static BrinkMgmtConfigBuilder create() {
    return new BrinkMgmtConfigBuilder();
  }

  public ManagementConfiguration build() {
    return ManagementConfiguration.of(
        this.host,
        this.timeout,
        this.mapper,
        this.httpClient,
        this.authHttpClient, this.authenticationConfiguration);
  }

  public BrinkMgmtConfigBuilder withMapper(ObjectMapper mapper) {
    this.mapper = mapper;
    return this;
  }

  public BrinkMgmtConfigBuilder withTimeoutInSeconds(Integer timeout) {
    this.timeout = timeout;
    return this;
  }

  public BrinkMgmtConfigBuilder withHost(String url) {
    this.host = url;
    return this;
  }

  @Deprecated
  public BrinkMgmtConfigBuilder withShopperHost(String url) {
    this.shopperHostUrl = url;
    return this;
  }

  public BrinkMgmtConfigBuilder withHttpClient(final HttpClient httpClient) {
    this.httpClient = httpClient;
    return this;
  }

  public BrinkMgmtConfigBuilder withAuthHttpClient(final HttpClient authHttpClient) {
    this.httpClient = authHttpClient;
    return this;
  }

  public BrinkMgmtConfigBuilder withAuthenticationConfiguration(
      final BrinkAuthentication authenticationConfiguration) {
    this.authenticationConfiguration = authenticationConfiguration;
    return this;
  }
}
