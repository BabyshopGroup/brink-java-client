package com.brinkcommerce.api.management.order.delivery.model.request;

public record DeliveryActionShippingManual(
    String trackingReference,
    String trackingUrl,
    String shippingMethod,
    String shippingCompany
) implements DeliveryActionShipping {
    public ActionType getActionType() {
        return ActionType.MANUAL;
    }

    public static DeliveryActionShippingManualBuilder builder() {
        return new DeliveryActionShippingManualBuilder();
    }

    public static final class DeliveryActionShippingManualBuilder {
        private String trackingReference;
        private String trackingUrl;
        private String shippingMethod;
        private String shippingCompany;

        public DeliveryActionShippingManualBuilder() {
        }

        public DeliveryActionShippingManualBuilder withTrackingReference(String trackingReference) {
            this.trackingReference = trackingReference;
            return this;
        }

        public DeliveryActionShippingManualBuilder withTrackingUrl(String trackingUrl) {
            this.trackingUrl = trackingUrl;
            return this;
        }

        public DeliveryActionShippingManualBuilder withShippingMethod(String shippingMethod) {
            this.shippingMethod = shippingMethod;
            return this;
        }

        public DeliveryActionShippingManualBuilder withShippingCompany(String shippingCompany) {
            this.shippingCompany = shippingCompany;
            return this;
        }

        public DeliveryActionShippingManual build() {
            return new DeliveryActionShippingManual(trackingReference, trackingUrl, shippingMethod, shippingCompany);
        }
    }
}
