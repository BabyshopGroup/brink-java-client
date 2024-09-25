package com.brinkcommerce.api.management.order.delivery.model.request;

public record Bonus(ActionType actionType) {
    private Bonus(final BonusBuilder builder) {
        this(builder.actionType);
    }

    public static BonusBuilder builder() {
        return new BonusBuilder();
    }

    public static class BonusBuilder {
        private ActionType actionType;

        public BonusBuilder() {
        }

        public BonusBuilder withActionType(final ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        public Bonus build() {
            return new Bonus(this);
        }
    }
}
