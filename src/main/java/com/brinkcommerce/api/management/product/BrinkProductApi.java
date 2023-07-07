package com.brinkcommerce.api.management.product;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.product.addon.BrinkProductAddonApi;
import com.brinkcommerce.api.management.product.parent.BrinkProductParentApi;
import com.brinkcommerce.api.management.product.variant.BrinkProductVariantApi;

public class BrinkProductApi {

  private static final String SCOPES =
      "product-management/product:read product-management/addon:read product-management/addon:write product-management/product:write";

  private final BrinkProductParentApi parentApi;
  private final BrinkProductVariantApi variantApi;
  private final BrinkProductAddonApi addonApi;

  private BrinkProductApi(final ManagementConfiguration config) {
    final AuthenticationHandler authenticationHandler =
        AuthenticationHandler.builder()
            .withBrinkAuthenticationConfig(config.authenticationConfiguration())
            .withHttpClient(config.authenticationHttpClient())
            .withObjectMapper(config.mapper())
            .withScopes(SCOPES)
            .build();

    this.addonApi = BrinkProductAddonApi.init(config, authenticationHandler);
    this.parentApi = BrinkProductParentApi.init(config, authenticationHandler);
    this.variantApi = BrinkProductVariantApi.init(config, authenticationHandler);
  }

  public static BrinkProductApi init(final ManagementConfiguration config) {
    return new BrinkProductApi(config);
  }

  public BrinkProductParentApi parent() {
    return this.parentApi;
  }

  public BrinkProductVariantApi variant() {
    return this.variantApi;
  }

  public BrinkProductAddonApi addon() {
    return this.addonApi;
  }
}
