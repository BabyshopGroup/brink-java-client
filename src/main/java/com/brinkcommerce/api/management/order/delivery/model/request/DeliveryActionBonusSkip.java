package com.brinkcommerce.api.management.order.delivery.model.request;

public record DeliveryActionBonusSkip() implements DeliveryActionBonus {
    public ActionType actionType() {
        return ActionType.SKIP;
    }
}
