package com.brinkcommerce.api.management.order.delivery.model.request;

public record DeliveryActionGiftCardAuto() implements DeliveryActionGiftCard {
    public ActionType actionType() {
        return ActionType.AUTO;
    }
}
