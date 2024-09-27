package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

// Sub-records for the provider fields
public record PaymentProvider(
        String providerName,
        String providerId
) {
    public static WithStatus<PaymentProvider> withStatus(PaymentProvider provider, String current, List<History> history) {
        return new WithStatus<>(provider, current, history);
    }
}
