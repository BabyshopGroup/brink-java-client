package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ReleaseActionPaymentManual(
        String reference
) implements ReleaseActionPayment {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.MANUAL;
    }

    public static ReleaseActionPaymentManualBuilder builder() {
        return new ReleaseActionPaymentManualBuilder();
    }

    public static class ReleaseActionPaymentManualBuilder {
        private String reference;

        public ReleaseActionPaymentManualBuilder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public ReleaseActionPaymentManual build() {
            return new ReleaseActionPaymentManual(reference);
        }
    }
}
