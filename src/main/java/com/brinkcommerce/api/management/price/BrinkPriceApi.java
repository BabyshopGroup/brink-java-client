package com.brinkcommerce.api.management.price;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.price.addon.BrinkPriceAddonApi;
import com.brinkcommerce.api.management.price.variant.BrinkPriceVariantApi;

public class BrinkPriceApi {

  private static final String SCOPES = "price-management/price:read price-management/price:write";
  private final BrinkPriceVariantApi priceVariantApi;
  private final BrinkPriceAddonApi priceAddonApi;

  private BrinkPriceApi(final ManagementConfiguration config) {
    final AuthenticationHandler authenticationHandler =
        AuthenticationHandler.builder()
            .withBrinkAuthenticationConfig(config.authenticationConfiguration())
            .withHttpClient(config.authenticationHttpClient())
            .withObjectMapper(config.mapper())
            .withScopes(SCOPES)
            .build();

    this.priceVariantApi = BrinkPriceVariantApi.init(config, authenticationHandler);
    this.priceAddonApi = BrinkPriceAddonApi.init(config, authenticationHandler);
  }

  public static BrinkPriceApi init(final ManagementConfiguration config) {
    return new BrinkPriceApi(config);
  }

  public BrinkPriceVariantApi variant() {
    return this.priceVariantApi;
  }

  public BrinkPriceAddonApi addon() {
    return this.priceAddonApi;
  }
}
