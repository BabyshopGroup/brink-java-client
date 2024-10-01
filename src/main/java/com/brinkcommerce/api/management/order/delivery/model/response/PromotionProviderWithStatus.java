package com.brinkcommerce.api.management.order.delivery.model.response;

public record PromotionProviderWithStatus(
        String providerId,
        String description,
        ProviderStatus status
) {
}
