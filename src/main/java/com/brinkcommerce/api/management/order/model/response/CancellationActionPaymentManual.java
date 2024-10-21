package com.brinkcommerce.api.management.order.model.response;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;

public record CancellationActionPaymentManual(
        String reference
)  implements CancellationActionPayment {
    public ActionType getActionType() {
        return ActionType.MANUAL;
    }
}
