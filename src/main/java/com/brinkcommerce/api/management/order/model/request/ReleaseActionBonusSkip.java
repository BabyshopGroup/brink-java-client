package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ReleaseActionBonusSkip() implements ReleaseActionBonus {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.SKIP;
    }
}
