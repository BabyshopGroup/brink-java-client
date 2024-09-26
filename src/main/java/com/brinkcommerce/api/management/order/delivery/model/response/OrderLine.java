package com.brinkcommerce.api.management.order.delivery.model.response;

public record OrderLine(
        String orderLineId,
        Integer quantity,
        Long taxPercentage,
        Integer taxPercentageDecimals,
        String currencyCode,
        Long totalAmount,
        Long totalTaxAmount,
        Long totalDiscountAmount
) {
}
