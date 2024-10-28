package com.brinkcommerce.api.management.order.delivery.model.request;

import java.util.List;

public record GiftCardManualRedeemed(
        String giftCardId,
        String transactionId
) implements DeliveryActionGiftCard {
    public static GiftCardManualRedeemedBuilder builder() {
        return new GiftCardManualRedeemedBuilder();
    }

    public static final class GiftCardManualRedeemedBuilder {
        private String giftCardId;
        private String transactionId;

        public GiftCardManualRedeemedBuilder() {
        }

        public GiftCardManualRedeemedBuilder withGiftCardId(String giftCardId) {
            this.giftCardId = giftCardId;
            return this;
        }

        public GiftCardManualRedeemedBuilder withTransactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public GiftCardManualRedeemed build() {
            return new GiftCardManualRedeemed(giftCardId, transactionId);
        }
    }
}

