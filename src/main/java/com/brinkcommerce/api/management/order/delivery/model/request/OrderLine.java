package com.brinkcommerce.api.management.order.delivery.model.request;

public record OrderLine(
        String orderLineId,
        int quantity
) {
}
