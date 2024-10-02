package com.brinkcommerce.api.management.order.delivery.model.response;

public record GiftCardProviderWithStatus(
        String providerId,
        String providerName,
        ProviderStatus status
) {
}
