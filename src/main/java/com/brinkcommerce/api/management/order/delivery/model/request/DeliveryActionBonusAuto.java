package com.brinkcommerce.api.management.order.delivery.model.request;

public record DeliveryActionBonusAuto() implements DeliveryActionBonus {
    public ActionType actionType() {
        return ActionType.AUTO;
    }
}
