package com.brinkcommerce.api.management.discount;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;

public class BrinkDiscountApi {

  private static final String SCOPES = "discount-management/discount:write";

  private final BrinkDiscountCodeApi discountCodeApi;

  private BrinkDiscountApi(final ManagementConfiguration config) {
    final AuthenticationHandler authenticationHandler =
        AuthenticationHandler.builder()
            .withBrinkAuthenticationConfig(config.authenticationConfiguration())
            .withHttpClient(config.authenticationHttpClient())
            .withObjectMapper(config.mapper())
            .withScopes(SCOPES)
            .build();

    this.discountCodeApi = BrinkDiscountCodeApi.init(config, authenticationHandler);
  }

  public static BrinkDiscountApi init(final ManagementConfiguration config) {
    return new BrinkDiscountApi(config);
  }

  public BrinkDiscountCodeApi code() {
    return this.discountCodeApi;
  }


}
