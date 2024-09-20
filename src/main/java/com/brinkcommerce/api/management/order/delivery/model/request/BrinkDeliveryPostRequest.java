package com.brinkcommerce.api.management.order.delivery.model.request;

import com.brinkcommerce.api.management.order.delivery.DeliveryRequest;

import java.util.List;

public record BrinkDeliveryPostRequest(
    String orderId,
    List<OrderLine> orderLines,
    List<String> shippingFees,
    List<Gift> gifts,
    List<GiftCardProduct> giftCardProducts
) implements DeliveryRequest {

    private BrinkDeliveryPostRequest(final BrinkDeliveryPostRequestBuilder builder) {
        this(
            builder.orderId,
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
        private String orderId;
        private List<OrderLine> orderLines;
        private List<String> shippingFees;
        private List<Gift> gifts;
        private List<GiftCardProduct> giftCardProducts;

        public BrinkDeliveryPostRequestBuilder() {
        }

        public BrinkDeliveryPostRequestBuilder withOrderId(final String orderId) {
            this.orderId = orderId;
            return this;
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


