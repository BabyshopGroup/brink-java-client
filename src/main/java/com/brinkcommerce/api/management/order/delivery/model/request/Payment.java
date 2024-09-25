package com.brinkcommerce.api.management.order.delivery.model.request;

public record Payment(ActionType actionType) {
    private Payment(final PaymentBuilder builder) {
        this(builder.actionType);
    }

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    public static class PaymentBuilder {
        private ActionType actionType;

        public PaymentBuilder() {
        }

        public PaymentBuilder withActionType(final ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
