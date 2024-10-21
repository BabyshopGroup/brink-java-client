package com.brinkcommerce.api.management.order.model.request;

public record Reason(
        String code,
        String cause
) {

    public static ReasonBuilder builder() {
        return new ReasonBuilder();
    }

    public static final class ReasonBuilder {
        private String code;
        private String cause;

        public ReasonBuilder() {
        }

        public ReasonBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public ReasonBuilder withCause(String cause) {
            this.cause = cause;
            return this;
        }

        public Reason build() {
            return new Reason(code, cause);
        }
    }
}
