package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;

public record CancellationActionBonusAuto() implements CancellationActionBonus{
    public ActionType actionType() {
        return ActionType.AUTO;
    }
}
