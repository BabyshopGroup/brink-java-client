package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

public record GiftCardProvider(
        String providerId,
        String providerName
) {
    public static WithStatus<GiftCardProvider> withStatus(GiftCardProvider provider, String current, List<History> history) {
        return new WithStatus<>(provider, current, history);
    }
}
