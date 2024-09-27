package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

public record ShippingProvider(
        String carrierName,
        String providerId
) {
    public static WithStatus<ShippingProvider> withStatus(ShippingProvider provider, String current, List<History> history) {
        return new WithStatus<>(provider, current, history);
    }
}
