package com.brinkcommerce.api.management.order.delivery.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeliveryActionBonusManual(
        String transactionId
) implements DeliveryActionBonus {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.MANUAL;
    }

    public static DeliveryActionBonusManualBuilder builder() {
        return new DeliveryActionBonusManualBuilder();
    }

    public static class DeliveryActionBonusManualBuilder {
        private String transactionId;

        public DeliveryActionBonusManualBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public DeliveryActionBonusManual build() {
            return new DeliveryActionBonusManual(transactionId);
        }
    }
}
