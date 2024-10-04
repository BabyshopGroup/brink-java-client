package com.brinkcommerce.api.management.order.delivery.model.request;

public record OrderLine(
        String orderLineId,
        Long quantity
) {

        private OrderLine(final OrderLineBuilder builder) {
            this(
                builder.orderLineId,
                builder.quantity
            );
        }

        public static OrderLineBuilder builder() {
            return new OrderLineBuilder();
        }

        public static class OrderLineBuilder {
            private String orderLineId;
            private Long quantity;

            public OrderLineBuilder() {
            }

            public OrderLineBuilder withOrderLineId(final String orderLineId) {
                this.orderLineId = orderLineId;
                return this;
            }

            public OrderLineBuilder withQuantity(final Long quantity) {
                this.quantity = quantity;
                return this;
            }

            public OrderLine build() {
                return new OrderLine(this);
            }
        }
}
