package com.brinkcommerce.api.management.order.delivery.model.response;

public record OrderLine(
        String orderLineId,
        Long quantity,
        Long taxPercentage,
        Long taxPercentageDecimals,
        String currencyCode,
        Long totalAmount,
        Long totalTaxAmount,
        Long totalDiscountAmount
) {
}
