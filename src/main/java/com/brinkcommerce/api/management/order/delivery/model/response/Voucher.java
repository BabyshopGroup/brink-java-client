package com.brinkcommerce.api.management.order.delivery.model.response;

public record Voucher(
        String voucherId,
        Double amount,
        String currencyCode
) {}
