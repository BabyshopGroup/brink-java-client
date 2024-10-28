package com.brinkcommerce.api.management.order.delivery.model.request;

public record DeliveryActionShippingSkip() implements DeliveryActionShipping {
    public ActionType getActionType() {
        return ActionType.SKIP;
    }
}
