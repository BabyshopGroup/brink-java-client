package com.brinkcommerce.api.management.order.delivery.model.request;

public record GiftCard(ActionType actionType) {
    private GiftCard(final GiftCardBuilder builder) {
        this(builder.actionType);
    }

    public static GiftCardBuilder builder() {
        return new GiftCardBuilder();
    }

    public static class GiftCardBuilder {
        private ActionType actionType;

        public GiftCardBuilder() {
        }

        public GiftCardBuilder withActionType(final ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        public GiftCard build() {
            return new GiftCard(this);
        }
    }
}
