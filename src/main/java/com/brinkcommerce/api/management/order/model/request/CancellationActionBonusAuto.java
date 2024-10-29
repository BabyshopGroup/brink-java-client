package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CancellationActionBonusAuto() implements CancellationActionBonus {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.AUTO;
    }
}
