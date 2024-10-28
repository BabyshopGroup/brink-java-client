package com.brinkcommerce.api.management.order.delivery.model.request;

public record DeliveryActionGiftCardSkip() implements DeliveryActionGiftCard {
    public ActionType actionType() {
        return ActionType.SKIP;
    }
}
