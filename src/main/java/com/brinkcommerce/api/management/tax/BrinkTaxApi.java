package com.brinkcommerce.api.management.tax;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.tax.market.BrinkTaxMarketApi;
import com.brinkcommerce.api.management.tax.taxgroup.BrinkTaxGroupApi;

public class BrinkTaxApi {

  public static final String SCOPES = "tax-management/tax:read tax-management/tax:write";
  private final BrinkTaxGroupApi taxGroupApi;
  private final BrinkTaxMarketApi taxMarketApi;

  private BrinkTaxApi(final ManagementConfiguration config) {
    final AuthenticationHandler authenticationHandler =
        AuthenticationHandler.builder()
            .withBrinkAuthenticationConfig(config.authenticationConfiguration())
            .withHttpClient(config.authenticationHttpClient())
            .withObjectMapper(config.mapper())
            .withScopes(SCOPES)
            .build();
    this.taxGroupApi = BrinkTaxGroupApi.init(config, authenticationHandler);
    this.taxMarketApi = BrinkTaxMarketApi.init(config, authenticationHandler);
  }

  public static BrinkTaxApi init(final ManagementConfiguration config) {
    return new BrinkTaxApi(config);
  }

  public BrinkTaxGroupApi group() {
    return this.taxGroupApi;
  }

  public BrinkTaxMarketApi market() {
    return this.taxMarketApi;
  }
}
