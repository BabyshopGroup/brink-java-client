package com.brinkcommerce.api.management.order.delivery.model.response;

public record ShippingProvider(
        String carrierName,
        String providerId
) {
}
