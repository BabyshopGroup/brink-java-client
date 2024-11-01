package com.brinkcommerce.api.management.order.model.request;

public record BrinkStartReleaseRequest(
        ReleaseActionBonus bonus,
        ReleaseActionGiftCard giftCard,
        ReleaseActionGiftCardProduct giftCardProduct,
        ReleaseActionPayment payment
) {
    public static BrinkStartReleaseRequestBuilder builder() {
        return new BrinkStartReleaseRequestBuilder();
    }

    public static class BrinkStartReleaseRequestBuilder {
        private ReleaseActionBonus bonus;
        private ReleaseActionGiftCard giftCard;
        private ReleaseActionGiftCardProduct giftCardProduct;
        private ReleaseActionPayment payment;

        public BrinkStartReleaseRequestBuilder withBonus(ReleaseActionBonus bonus) {
            this.bonus = bonus;
            return this;
        }

        public BrinkStartReleaseRequestBuilder withGiftCard(ReleaseActionGiftCard giftCard) {
            this.giftCard = giftCard;
            return this;
        }

        public BrinkStartReleaseRequestBuilder withGiftCardProduct(ReleaseActionGiftCardProduct giftCardProduct) {
            this.giftCardProduct = giftCardProduct;
            return this;
        }

        public BrinkStartReleaseRequestBuilder withPayment(ReleaseActionPayment payment) {
            this.payment = payment;
            return this;
        }

        public BrinkStartReleaseRequest build() {
            return new BrinkStartReleaseRequest(bonus, giftCard, giftCardProduct, payment);
        }
    }
}
