package com.brinkcommerce.api.management.order.delivery.model.response;

public record GiftCardProduct(
        String giftCardProductId,
        String currencyCode,
        Long priceAmount,
        String status,
        String createTransactionId,
        String createFailedReason,
        String createId
) {}
