package com.brinkcommerce.api.management.order.delivery.model.response;

public record Totals(
        Long subTotal,
        Long shippingTotal,
        Long voucherTotal,
        Long giftCardTotal,
        Long bonusTotal,
        Long grandTotal
) {}
