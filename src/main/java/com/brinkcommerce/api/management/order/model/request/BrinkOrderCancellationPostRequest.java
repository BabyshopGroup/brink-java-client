package com.brinkcommerce.api.management.order.model.request;

public record BrinkOrderCancellationPostRequest(
        Reason reason
) {

    public static BrinkOrderCancellationPostRequestBuilder builder() {
        return new BrinkOrderCancellationPostRequestBuilder();
    }

    public static final class BrinkOrderCancellationPostRequestBuilder {
        private Reason reason;

        public BrinkOrderCancellationPostRequestBuilder() {
        }

        public BrinkOrderCancellationPostRequestBuilder withReason(Reason reason) {
            this.reason = reason;
            return this;
        }

        public BrinkOrderCancellationPostRequest build() {
            return new BrinkOrderCancellationPostRequest(reason);
        }
    }
}
