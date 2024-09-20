package com.brinkcommerce.api.management.order;

import com.brinkcommerce.api.authentication.AuthenticationHandler;
import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.order.delivery.BrinkDeliveryApi;

public class BrinkOrderApi {
    private static final String SCOPES = "order-management/order:read order-management/order:write";
    private final BrinkDeliveryApi deliveryApi;

    private BrinkOrderApi(final ManagementConfiguration config) {
        final AuthenticationHandler authenticationHandler =
                AuthenticationHandler.builder()
                        .withBrinkAuthenticationConfig(config.authenticationConfiguration())
                        .withHttpClient(config.authenticationHttpClient())
                        .withObjectMapper(config.mapper())
                        .withScopes(SCOPES)
                        .build();
        this.deliveryApi = BrinkDeliveryApi.init(config, authenticationHandler);
    }

    public static BrinkOrderApi init(final ManagementConfiguration config) {
        return new BrinkOrderApi(config);
    }

    public BrinkDeliveryApi delivery() {
        return this.deliveryApi;
    }
}
