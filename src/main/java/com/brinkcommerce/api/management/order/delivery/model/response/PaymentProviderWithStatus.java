package com.brinkcommerce.api.management.order.delivery.model.response;

// Sub-records for the provider fields
public record PaymentProviderWithStatus(
        String providerName,
        String providerId,
        ProviderStatus status
) {
}
