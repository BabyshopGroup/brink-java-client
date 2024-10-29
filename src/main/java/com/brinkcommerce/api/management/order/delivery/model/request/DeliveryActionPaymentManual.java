package com.brinkcommerce.api.management.order.delivery.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record DeliveryActionPaymentManual(
        String captureReference
) implements DeliveryActionPayment {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.MANUAL;
    }

    public static DeliveryActionPaymentManualBuilder builder() {
        return new DeliveryActionPaymentManualBuilder();
    }

    public static final class DeliveryActionPaymentManualBuilder {
        private String captureReference;

        public DeliveryActionPaymentManualBuilder() {
        }

        public DeliveryActionPaymentManualBuilder withCaptureReference(String captureReference) {
            Objects.requireNonNull(captureReference, "captureReference cannot be null");
            return this;
        }

        public DeliveryActionPaymentManual build() {
            return new DeliveryActionPaymentManual(captureReference);
        }
    }
}
