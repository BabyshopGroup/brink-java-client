package com.brinkcommerce.api.management.order.delivery.model.request;

import java.util.List;

public record BrinkDeliveryPostRequest(
        List<OrderLine> orderLines,
        List<String> shippingFees,
        List<Gift> gifts,
        List<GiftCardProduct> giftCardProducts
) {

    private BrinkDeliveryPostRequest(final BrinkDeliveryPostRequestBuilder builder) {
        this(
                builder.orderLines,
                builder.shippingFees,
                builder.gifts,
                builder.giftCardProducts
        );
    }

    public static BrinkDeliveryPostRequestBuilder builder() {
        return new BrinkDeliveryPostRequestBuilder();
    }

    public static class BrinkDeliveryPostRequestBuilder {
        private List<OrderLine> orderLines;
        private List<String> shippingFees;
        private List<Gift> gifts;
        private List<GiftCardProduct> giftCardProducts;

        public BrinkDeliveryPostRequestBuilder() {
        }

        public BrinkDeliveryPostRequestBuilder withOrderLines(final List<OrderLine> orderLines) {
            this.orderLines = orderLines;
            return this;
        }

        public BrinkDeliveryPostRequestBuilder withShippingFees(final List<String> shippingFees) {
            this.shippingFees = shippingFees;
            return this;
        }

        public BrinkDeliveryPostRequestBuilder withGifts(final List<Gift> gifts) {
            this.gifts = gifts;
            return this;
        }

        public BrinkDeliveryPostRequestBuilder withGiftCardProducts(final List<GiftCardProduct> giftCardProducts) {
            this.giftCardProducts = giftCardProducts;
            return this;
        }

        public BrinkDeliveryPostRequest build() {
            return new BrinkDeliveryPostRequest(this);
        }
    }
}


