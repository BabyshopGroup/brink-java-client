package com.brinkcommerce.api.management.order.delivery.model.request;

public record BrinkDeliveryGetRequest(
        String deliveryId
) {

    private BrinkDeliveryGetRequest(final BrinkDeliveryGetRequestBuilder builder) {
        this.deliveryId = builder.deliveryId;
    }

    public static BrinkDeliveryGetRequestBuilder builder() {
        return new BrinkDeliveryGetRequestBuilder();
    }

    public static class BrinkDeliveryGetRequestBuilder {
        private String deliveryId;

        BrinkDeliveryGetRequestBuilder() {
        }

        public BrinkDeliveryGetRequestBuilder deliveryId(String deliveryId) {
            this.deliveryId = deliveryId;
            return this;
        }

        public BrinkDeliveryGetRequest build() {
            return new BrinkDeliveryGetRequest(this);
        }
    }
}
