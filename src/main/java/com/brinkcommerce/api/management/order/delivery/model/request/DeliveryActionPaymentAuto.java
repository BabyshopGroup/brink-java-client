package com.brinkcommerce.api.management.order.delivery.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeliveryActionPaymentAuto() implements DeliveryActionPayment {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.AUTO;
    }
}

