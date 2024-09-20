package com.brinkcommerce.api.management.order.delivery.model.request;

public record GiftCardProduct(
        String giftCardProductId
) {

    private GiftCardProduct(final GiftCardProductBuilder builder) {
        this.giftCardProductId = builder.giftCardProductId;
    }

    public static GiftCardProductBuilder builder() {
        return new GiftCardProductBuilder();
    }

    public static class GiftCardProductBuilder {
        private String giftCardProductId;

        public GiftCardProductBuilder() {
        }

        public GiftCardProductBuilder withGiftCardProductId(final String giftCardProductId) {
            this.giftCardProductId = giftCardProductId;
            return this;
        }

        public GiftCardProduct build() {
            return new GiftCardProduct(this);
        }
    }
}
