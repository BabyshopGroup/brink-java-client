package com.brinkcommerce.api.management.store;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.store.market.BrinkStoreMarketApi;
import com.brinkcommerce.api.management.store.storegroup.BrinkStoreGroupApi;

public class BrinkStoreApi {

  public static final String SCOPES = "store-management/store:read store-management/store:write";
  private final BrinkStoreGroupApi storeGroupApi;
  private final BrinkStoreMarketApi storeMarketApi;

  private BrinkStoreApi(final ManagementConfiguration config) {
    final AuthenticationHandler authenticationHandler =
        AuthenticationHandler.builder()
            .withBrinkAuthenticationConfig(config.authenticationConfiguration())
            .withHttpClient(config.authenticationHttpClient())
            .withObjectMapper(config.mapper())
            .withScopes(SCOPES)
            .build();

    this.storeGroupApi = BrinkStoreGroupApi.init(config, authenticationHandler);
    this.storeMarketApi = BrinkStoreMarketApi.init(config, authenticationHandler);
  }

  public static BrinkStoreApi init(final ManagementConfiguration config) {
    return new BrinkStoreApi(config);
  }

  public BrinkStoreGroupApi group() {
    return this.storeGroupApi;
  }

  public BrinkStoreMarketApi market() {
    return this.storeMarketApi;
  }
}
