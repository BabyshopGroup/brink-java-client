package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;

public record CancellationActionGiftCardProductManual() implements CancellationActionGiftCardProduct {
    public ActionType getActionType() {
        return ActionType.MANUAL;
    }


}
