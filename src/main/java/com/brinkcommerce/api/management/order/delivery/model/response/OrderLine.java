package com.brinkcommerce.api.management.order.delivery.model.response;

public record OrderLine(
        String orderLineId,
        Integer quantity,
        Long taxPercentage,
        Long taxPercentageDecimals,
        String currencyCode,
        Long totalAmount,
        Long totalTaxAmount,
        Long totalDiscountAmount
) {
    private OrderLine(final OrderLineResponseBuilder builder) {
        this(
            builder.orderLineId,
            builder.quantity,
            builder.taxPercentage,
            builder.taxPercentageDecimals,
            builder.currencyCode,
            builder.totalAmount,
            builder.totalTaxAmount,
            builder.totalDiscountAmount
        );
    }

    public static OrderLineResponseBuilder builder() {
        return new OrderLineResponseBuilder();
    }

    public static class OrderLineResponseBuilder {
        private String orderLineId;
        private Integer quantity;
        private Long taxPercentage;
        private Long taxPercentageDecimals;
        private String currencyCode;
        private Long totalAmount;
        private Long totalTaxAmount;
        private Long totalDiscountAmount;

        public OrderLineResponseBuilder() {
        }

        public OrderLineResponseBuilder withOrderLineId(final String orderLineId) {
            this.orderLineId = orderLineId;
            return this;
        }

        public OrderLineResponseBuilder withQuantity(final Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public OrderLineResponseBuilder withTaxPercentage(final Long taxPercentage) {
            this.taxPercentage = taxPercentage;
            return this;
        }

        public OrderLineResponseBuilder withTaxPercentageDecimals(final Long taxPercentageDecimals) {
            this.taxPercentageDecimals = taxPercentageDecimals;
            return this;
        }

        public OrderLineResponseBuilder withCurrencyCode(final String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public OrderLineResponseBuilder withTotalAmount(final Long totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public OrderLineResponseBuilder withTotalTaxAmount(final Long totalTaxAmount) {
            this.totalTaxAmount = totalTaxAmount;
            return this;
        }

        public OrderLineResponseBuilder withTotalDiscountAmount(final Long totalDiscountAmount) {
            this.totalDiscountAmount = totalDiscountAmount;
            return this;
        }

        public OrderLine build() {
            return new OrderLine(this);
        }
    }
}
