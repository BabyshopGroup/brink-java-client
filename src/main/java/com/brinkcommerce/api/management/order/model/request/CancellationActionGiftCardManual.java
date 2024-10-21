package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;

public record CancellationActionGiftCardManual() implements CancellationActionGiftCard {
    public ActionType getActionType() {
        return ActionType.MANUAL;
    }
}
