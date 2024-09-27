package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

public record PromotionProvider(
        String providerId,
        String description
) {
    public static WithStatus<PromotionProvider> withStatus(PromotionProvider provider, String current, List<History> history) {
        return new WithStatus<>(provider, current, history);
    }
}
