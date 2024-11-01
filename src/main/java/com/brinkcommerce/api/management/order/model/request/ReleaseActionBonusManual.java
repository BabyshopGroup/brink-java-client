package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ReleaseActionBonusManual(
        String transactionId
) implements ReleaseActionBonus {
    @JsonProperty("actionType")
    public ActionType actionType() {
        return ActionType.MANUAL;
    }

    public static ReleaseActionBonusManualBuilder builder() {
        return new ReleaseActionBonusManualBuilder();
    }

    public static class ReleaseActionBonusManualBuilder {
        private String transactionId;

        public ReleaseActionBonusManualBuilder withTransactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public ReleaseActionBonusManual build() {
            return new ReleaseActionBonusManual(transactionId);
        }
    }
}
