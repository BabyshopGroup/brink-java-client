package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

public record BonusProvider(
        String providerId,
        String providerName
) {
    public static WithStatus<BonusProvider> withStatus(BonusProvider provider, String current, List<History> history) {
        return new WithStatus<>(provider, current, history);
    }
}
