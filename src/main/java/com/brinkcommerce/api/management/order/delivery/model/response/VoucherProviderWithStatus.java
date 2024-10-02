package com.brinkcommerce.api.management.order.delivery.model.response;

public record VoucherProviderWithStatus(
        String providerId,
        String providerName,
        ProviderStatus status
) {
}
