package com.brinkcommerce.api.management.order.model.response;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;

public record CancellationActionGiftCardAuto() implements CancellationActionGiftCard{
    public ActionType actionType() {
        return ActionType.AUTO;
    }
}
