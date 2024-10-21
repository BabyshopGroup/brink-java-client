package com.brinkcommerce.api.management.order.model.request;

import com.brinkcommerce.api.management.order.delivery.model.request.ActionType;

public record CancellationActionPaymentManual(
        String reference
)  implements CancellationActionPayment {
    public ActionType getActionType() {
        return ActionType.MANUAL;
    }


    public static final class CancellationActionPaymentManualBuilder {
        private String reference;

        public CancellationActionPaymentManualBuilder() {
        }

        public CancellationActionPaymentManualBuilder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public CancellationActionPaymentManual build() {
            return new CancellationActionPaymentManual(reference);
        }
    }
}
