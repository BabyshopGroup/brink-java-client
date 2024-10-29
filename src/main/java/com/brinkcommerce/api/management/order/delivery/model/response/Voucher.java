package com.brinkcommerce.api.management.order.delivery.model.response;

public record Voucher(
        String voucherId,
        Long amount,
        String currencyCode
) {
}
