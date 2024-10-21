package com.brinkcommerce.api.management.order.model.request;

public record BrinkOrderStartCancellationPostRequest(
        CancellationActionBonus bonus,
        CancellationActionGiftCard giftCard,
        CancellationActionGiftCardProduct giftCardProduct,
        CancellationActionPayment payment
) {

    public static BrinkOrderStartCancellationPostRequestBuilder builder() {
        return new BrinkOrderStartCancellationPostRequestBuilder();
    }

    public static final class BrinkOrderStartCancellationPostRequestBuilder {
        private CancellationActionBonus bonus;
        private CancellationActionGiftCard giftCard;
        private CancellationActionGiftCardProduct giftCardProduct;
        private CancellationActionPayment payment;

        public BrinkOrderStartCancellationPostRequestBuilder() {
        }

        public BrinkOrderStartCancellationPostRequestBuilder withBonus(CancellationActionBonus bonus) {
            this.bonus = bonus;
            return this;
        }

        public BrinkOrderStartCancellationPostRequestBuilder withGiftCard(CancellationActionGiftCard giftCard) {
            this.giftCard = giftCard;
            return this;
        }

        public BrinkOrderStartCancellationPostRequestBuilder withGiftCardProduct(CancellationActionGiftCardProduct giftCardProduct) {
            this.giftCardProduct = giftCardProduct;
            return this;
        }

        public BrinkOrderStartCancellationPostRequestBuilder withPayment(CancellationActionPayment payment) {
            this.payment = payment;
            return this;
        }

        public BrinkOrderStartCancellationPostRequest build() {
            return new BrinkOrderStartCancellationPostRequest(bonus, giftCard, giftCardProduct, payment);
        }
    }
}
