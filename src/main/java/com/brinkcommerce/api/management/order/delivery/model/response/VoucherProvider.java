package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

public record VoucherProvider(
        String providerId,
        String providerName
) {
    public static WithStatus<VoucherProvider> withStatus(VoucherProvider provider, String current, List<History> history) {
        return new WithStatus<>(provider, current, history);
    }
}
