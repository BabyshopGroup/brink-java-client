package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

public record GiftCardProductProvider(
        String providerId,
        String providerName
) {
    public static WithStatus<GiftCardProductProvider> withStatus(GiftCardProductProvider provider, String current, List<History> history) {
        return new WithStatus<>(provider, current, history);
    }
}
