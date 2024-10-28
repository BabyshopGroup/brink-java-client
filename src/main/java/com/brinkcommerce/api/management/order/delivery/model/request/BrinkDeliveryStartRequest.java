package com.brinkcommerce.api.management.order.delivery.model.request;

public record BrinkDeliveryStartRequest(
        DeliveryActionPayment payment,
        DeliveryActionShipping shipping,
        DeliveryActionGiftCard giftCard,
        DeliveryActionBonus bonus
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
        private DeliveryActionPayment payment;
        private DeliveryActionShipping shipping;
        private DeliveryActionGiftCard giftCard;
        private DeliveryActionBonus bonus;

        public BrinkDeliveryStartRequestBuilder() {
        }

        public BrinkDeliveryStartRequestBuilder withPayment(final DeliveryActionPayment payment) {
            this.payment = payment;
            return this;
        }

        public BrinkDeliveryStartRequestBuilder withShipping(final DeliveryActionShipping shipping) {
            this.shipping = shipping;
            return this;
        }

        public BrinkDeliveryStartRequestBuilder withGiftCard(final DeliveryActionGiftCard giftCard) {
            this.giftCard = giftCard;
            return this;
        }

        public BrinkDeliveryStartRequestBuilder withBonus(final DeliveryActionBonus bonus) {
            this.bonus = bonus;
            return this;
        }

        public BrinkDeliveryStartRequest build() {
            return new BrinkDeliveryStartRequest(this);
        }
    }
}

