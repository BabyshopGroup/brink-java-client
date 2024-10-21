package com.brinkcommerce.api.management.order.model.response;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;

public record CancellationActionBonusManual() implements CancellationActionBonus {
    public ActionType actionType() {
        return ActionType.MANUAL;
    }
}
