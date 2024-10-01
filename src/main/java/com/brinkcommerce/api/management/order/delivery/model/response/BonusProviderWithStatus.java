package com.brinkcommerce.api.management.order.delivery.model.response;

public record BonusProviderWithStatus(
        String providerId,
        String providerName,
        ProviderStatus status
) {
}
