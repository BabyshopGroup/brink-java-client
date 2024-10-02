package com.brinkcommerce.api.management.order.delivery.model.request;

public record Shipping(ActionType actionType, String trackingReference) {
    private Shipping(final ShippingBuilder builder) {
        this(builder.actionType, builder.trackingReference);
    }

    public static ShippingBuilder builder() {
        return new ShippingBuilder();
    }

    public static class ShippingBuilder {
        private ActionType actionType;
        private String trackingReference;

        public ShippingBuilder() {
        }

        public ShippingBuilder withActionType(final ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        public ShippingBuilder withTrackingReference(final String trackingReference) {
            this.trackingReference = trackingReference;
            return this;
        }

        public Shipping build() {
            return new Shipping(this);
        }
    }
}
