package com.brinkcommerce.api.management.order.model.request;

public record GiftCardProductManualReleased(
        String giftCardProductId,
        String transactionId
) {
    public static GiftCardProductManualReleasedBuilder builder() {
        return new GiftCardProductManualReleasedBuilder();
    }

    public static class GiftCardProductManualReleasedBuilder {
        private String giftCardProductId;
        private String transactionId;

        public GiftCardProductManualReleasedBuilder withGiftCardProductId(String giftCardProductId) {
            this.giftCardProductId = giftCardProductId;
            return this;
        }

        public GiftCardProductManualReleasedBuilder withTransactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public GiftCardProductManualReleased build() {
            return new GiftCardProductManualReleased(giftCardProductId, transactionId);
        }
    }
}
