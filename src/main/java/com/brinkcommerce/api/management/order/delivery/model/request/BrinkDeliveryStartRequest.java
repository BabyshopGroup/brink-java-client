package com.brinkcommerce.api.management.order.delivery.model.request;

public record BrinkDeliveryStartRequest(
        Payment payment,
        Shipping shipping,
        GiftCard giftCard,
        Bonus bonus
) {
    private BrinkDeliveryStartRequest(final BrinkDeliveryStartRequestBuilder builder) {
        this(
            builder.payment,
            builder.shipping,
            builder.giftCard,
            builder.bonus
        );
    }

    public static BrinkDeliveryStartRequestBuilder builder() {
        return new BrinkDeliveryStartRequestBuilder();
    }

    public static class BrinkDeliveryStartRequestBuilder {
        private Payment payment;
        private Shipping shipping;
        private GiftCard giftCard;
        private Bonus bonus;

        public BrinkDeliveryStartRequestBuilder() {
        }

        public BrinkDeliveryStartRequestBuilder withPayment(final Payment payment) {
            this.payment = payment;
            return this;
        }

        public BrinkDeliveryStartRequestBuilder withShipping(final Shipping shipping) {
            this.shipping = shipping;
            return this;
        }

        public BrinkDeliveryStartRequestBuilder withGiftCard(final GiftCard giftCard) {
            this.giftCard = giftCard;
            return this;
        }

        public BrinkDeliveryStartRequestBuilder withBonus(final Bonus bonus) {
            this.bonus = bonus;
            return this;
        }

        public BrinkDeliveryStartRequest build() {
            return new BrinkDeliveryStartRequest(this);
        }
    }
}

