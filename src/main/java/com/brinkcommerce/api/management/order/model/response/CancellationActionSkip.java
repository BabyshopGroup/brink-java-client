package com.brinkcommerce.api.management.order.model.response;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;

public record CancellationActionSkip() implements CancellationActionPayment {
    public ActionType getActionType() {
        return ActionType.SKIP;
    }
}
