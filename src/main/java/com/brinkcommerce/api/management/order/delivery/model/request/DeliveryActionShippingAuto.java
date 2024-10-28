package com.brinkcommerce.api.management.order.delivery.model.request;

import java.util.Objects;

public record DeliveryActionShippingAuto(
        String trackingReference
) implements DeliveryActionShipping {
    public ActionType actionType() {
        return ActionType.AUTO;
    }

    public static DeliveryActionShippingAutoBuilder builder() {
        return new DeliveryActionShippingAutoBuilder();
    }


    public static final class DeliveryActionShippingAutoBuilder {
        private String trackingReference;

        public DeliveryActionShippingAutoBuilder() {
        }

        public DeliveryActionShippingAutoBuilder withTrackingReference(String trackingReference) {
            Objects.requireNonNull(trackingReference, "trackingReference cannot be null");
            return this;
        }

        public DeliveryActionShippingAuto build() {
            return new DeliveryActionShippingAuto(trackingReference);
        }
    }
}