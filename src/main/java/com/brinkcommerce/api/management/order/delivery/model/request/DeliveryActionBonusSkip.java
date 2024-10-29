package com.brinkcommerce.api.management.order.delivery.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeliveryActionBonusSkip() implements DeliveryActionBonus {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.SKIP;
    }
}
