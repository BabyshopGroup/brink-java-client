
package com.brinkcommerce.api.management.order.delivery.model.response;

public record ShippingFee(
        String shippingFeeId,
        Long taxPercentage,
        Integer taxPercentageDecimals,
        String currencyCode,
        Long totalAmount,
        Long totalTaxAmount,
        Long totalDiscountAmount
) {}
