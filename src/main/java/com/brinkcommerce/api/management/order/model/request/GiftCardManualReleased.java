package com.brinkcommerce.api.management.order.model.request;

public record GiftCardManualReleased(
        String giftCardId,
        String transcationId
) {
    public static GiftCardManualReleasedBuilder builder() {
        return new GiftCardManualReleasedBuilder();
    }

    public static class GiftCardManualReleasedBuilder {
        private String giftCardId;
        private String transactionId;

        public GiftCardManualReleasedBuilder withGiftCardId(String giftCardId) {
            this.giftCardId = giftCardId;
            return this;
        }

        public GiftCardManualReleasedBuilder withTransactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public GiftCardManualReleased build() {
            return new GiftCardManualReleased(giftCardId, transactionId);
        }
    }
}
