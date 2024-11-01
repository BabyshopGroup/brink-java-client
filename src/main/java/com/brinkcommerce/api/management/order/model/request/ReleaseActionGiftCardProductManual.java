package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ReleaseActionGiftCardProductManual(
        String giftCardId,
        List<GiftCardProductManualReleased> giftCardProducts
) implements ReleaseActionGiftCardProduct {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.MANUAL;
    }

    public static ReleaseActionGiftCardProductManualBuilder builder() {
        return new ReleaseActionGiftCardProductManualBuilder();
    }

    public static class ReleaseActionGiftCardProductManualBuilder {
        private String giftCardId;
        private List<GiftCardProductManualReleased> giftCardProducts;

        public ReleaseActionGiftCardProductManualBuilder withGiftCardId(String giftCardId) {
            this.giftCardId = giftCardId;
            return this;
        }

        public ReleaseActionGiftCardProductManualBuilder withGiftCardProducts(List<GiftCardProductManualReleased> giftCardProducts) {
            this.giftCardProducts = giftCardProducts;
            return this;
        }

        public ReleaseActionGiftCardProductManual build() {
            return new ReleaseActionGiftCardProductManual(giftCardId, giftCardProducts);
        }
    }
}
