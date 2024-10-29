package com.brinkcommerce.api.management.order.delivery.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeliveryActionBonusAuto() implements DeliveryActionBonus {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.AUTO;
    }
}
