package com.brinkcommerce.api.management.order.delivery.model.response;

public record GiftCard(
        String giftCardId,
        Integer amount,
        String currencyCode,
        String status,
        String reservationId,
        String transactionId

) {}
