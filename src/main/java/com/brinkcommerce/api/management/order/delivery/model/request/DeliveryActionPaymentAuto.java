package com.brinkcommerce.api.management.order.delivery.model.request;

import java.util.Objects;

public record DeliveryActionPaymentAuto() implements DeliveryActionPayment {
    public ActionType actionType() {
        return ActionType.AUTO;
    }
}

