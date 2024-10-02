package com.brinkcommerce.api.management.order.delivery.model.response;

public record ShippingProviderWithStatus(
        String carrierName,
        String providerId,
        ProviderStatus status
) {
}
