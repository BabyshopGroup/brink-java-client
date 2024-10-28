package com.brinkcommerce.api.management.order.delivery.model.request;

public record DeliveryActionPaymentSkip() implements DeliveryActionPayment {
    public ActionType actionType() {
        return ActionType.SKIP;
    }
}
