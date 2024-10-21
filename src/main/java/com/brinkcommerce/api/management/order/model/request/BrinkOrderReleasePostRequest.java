package com.brinkcommerce.api.management.order.model.request;

public record BrinkOrderReleasePostRequest(
        Reason reason
) {

    public static BrinkOrderReleasePostRequestBuilder builder() {
        return new BrinkOrderReleasePostRequestBuilder();
    }

    public static final class BrinkOrderReleasePostRequestBuilder {
        private Reason reason;

        public BrinkOrderReleasePostRequestBuilder() {
        }

        public BrinkOrderReleasePostRequestBuilder withReason(Reason reason) {
            this.reason = reason;
            return this;
        }

        public BrinkOrderReleasePostRequest build() {
            return new BrinkOrderReleasePostRequest(reason);
        }
    }
}
