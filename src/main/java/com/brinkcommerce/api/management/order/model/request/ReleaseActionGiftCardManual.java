package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ReleaseActionGiftCardManual(
        String giftCardId,
        List<GiftCardManualReleased> giftCards
) implements ReleaseActionGiftCard {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.MANUAL;
    }

    public static ReleaseActionGiftCardManualBuilder builder() {
        return new ReleaseActionGiftCardManualBuilder();
    }

    public static class ReleaseActionGiftCardManualBuilder {
        private String giftCardId;
        private List<GiftCardManualReleased> giftCards;

        public ReleaseActionGiftCardManualBuilder withGiftCardId(String giftCardId) {
            this.giftCardId = giftCardId;
            return this;
        }

        public ReleaseActionGiftCardManualBuilder withGiftCards(List<GiftCardManualReleased> giftCards) {
            this.giftCards = giftCards;
            return this;
        }

        public ReleaseActionGiftCardManual build() {
            return new ReleaseActionGiftCardManual(giftCardId, giftCards);
        }
    }
}
