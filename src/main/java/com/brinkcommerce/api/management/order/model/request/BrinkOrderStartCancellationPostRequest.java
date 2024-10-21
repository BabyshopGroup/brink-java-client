package com.brinkcommerce.api.management.order.model.request;

public record BrinkOrderStartCancellationPostRequest(
        Reason reason
) {

    public static BrinkOrderStartCancellationPostRequestBuilder builder() {
        return new BrinkOrderStartCancellationPostRequestBuilder();
    }

    public static final class BrinkOrderStartCancellationPostRequestBuilder {
        private Reason reason;

        public BrinkOrderStartCancellationPostRequestBuilder withReason(Reason reason) {
            this.reason = reason;
            return this;
        }

        public BrinkOrderStartCancellationPostRequest build() {
            return new BrinkOrderStartCancellationPostRequest(reason);
        }
    }
}
