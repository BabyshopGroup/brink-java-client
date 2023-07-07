package com.brinkcommerce.api.management.stock;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.stock.inventories.BrinkInventoriesApi;
import com.brinkcommerce.api.management.stock.productvariants.BrinkProductVariantsApi;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupsApi;

public class BrinkStockApi {
  private static final String SCOPES = "stock-management/stock:read stock-management/stock:write";
  private final BrinkStoreGroupsApi storeGroupsApi;
  private final BrinkProductVariantsApi productVariantsApi;
  private final BrinkInventoriesApi inventoriesApi;

  private BrinkStockApi(final ManagementConfiguration config) {
    final AuthenticationHandler authenticationHandler =
        AuthenticationHandler.builder()
            .withBrinkAuthenticationConfig(config.authenticationConfiguration())
            .withHttpClient(config.authenticationHttpClient())
            .withObjectMapper(config.mapper())
            .withScopes(SCOPES)
            .build();

    this.storeGroupsApi = BrinkStoreGroupsApi.init(config, authenticationHandler);
    this.productVariantsApi = BrinkProductVariantsApi.init(config, authenticationHandler);
    this.inventoriesApi = BrinkInventoriesApi.init(config, authenticationHandler);
  }

  public static BrinkStockApi init(final ManagementConfiguration config) {
    return new BrinkStockApi(config);
  }

  public BrinkStoreGroupsApi storeGroup() {
    return this.storeGroupsApi;
  }

  public BrinkProductVariantsApi variant() {
    return this.productVariantsApi;
  }

  public BrinkInventoriesApi inventory() {
    return this.inventoriesApi;
  }
}
