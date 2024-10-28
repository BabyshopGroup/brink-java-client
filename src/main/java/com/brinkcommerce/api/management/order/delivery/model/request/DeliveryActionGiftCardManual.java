package com.brinkcommerce.api.management.order.delivery.model.request;

import java.util.List;

public record DeliveryActionGiftCardManual(
        List<GiftCardManualRedeemed> giftCards
) implements DeliveryActionGiftCard {
    public ActionType actionType() {
        return ActionType.MANUAL;
    }

    public static DeliveryActionGiftCardManualBuilder builder() {
        return new DeliveryActionGiftCardManualBuilder();
    }

    public static final class DeliveryActionGiftCardManualBuilder {
        private List<GiftCardManualRedeemed> giftCards;

        public DeliveryActionGiftCardManualBuilder() {
        }

        public DeliveryActionGiftCardManualBuilder withGiftCards(List<GiftCardManualRedeemed> giftCards) {
            this.giftCards = giftCards;
            return this;
        }

        public DeliveryActionGiftCardManual build() {
            return new DeliveryActionGiftCardManual(giftCards);
        }
    }
}
