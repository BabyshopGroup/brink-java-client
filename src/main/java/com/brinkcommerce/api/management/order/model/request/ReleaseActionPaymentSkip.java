package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ReleaseActionPaymentSkip() implements ReleaseActionPayment {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.SKIP;
    }
}
