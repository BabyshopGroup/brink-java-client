package com.brinkcommerce.api.management.order.delivery.model.response;

public record GiftCardProductProviderWithStatus(
        String providerId,
        String providerName,
        ProviderStatus status
) {
}
