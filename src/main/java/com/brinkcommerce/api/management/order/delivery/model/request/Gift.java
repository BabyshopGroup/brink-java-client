package com.brinkcommerce.api.management.order.delivery.model.request;

public record Gift(
        String giftId,
        Long quantity
) {

    private Gift(final RequestGiftBuilder builder) {
        this(
                builder.giftId,
                builder.quantity
        );
    }

    public static RequestGiftBuilder builder() {
        return new RequestGiftBuilder();
    }

    public static class RequestGiftBuilder {
        private String giftId;
        private Long quantity;

        public RequestGiftBuilder() {
        }

        public RequestGiftBuilder withGiftId(final String giftId) {
            this.giftId = giftId;
            return this;
        }

        public RequestGiftBuilder withQuantity(final Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public Gift build() {
            return new Gift(this);
        }
    }
}
