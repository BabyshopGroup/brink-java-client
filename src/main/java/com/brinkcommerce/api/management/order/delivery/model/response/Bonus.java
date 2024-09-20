package com.brinkcommerce.api.management.order.delivery.model.response;

public record Bonus(
        String transactionId,
        String reservationId,
        Double amount,
        String currencyCode
) {}
