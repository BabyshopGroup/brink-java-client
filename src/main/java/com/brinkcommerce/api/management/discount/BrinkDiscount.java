package com.brinkcommerce.api.management.discount;

import java.time.Instant;

public record BrinkDiscount(String code, boolean isUnlimited, int usageLimit, Instant created, Instant updated, String discountCodeRuleId, BrinkDateRange validDateRange) {

    private BrinkDiscount(final BrinkDiscountBuilder builder) {
        this(
            builder.code,
            builder.isUnlimited,
            builder.usageLimit,
            builder.created,
            builder.updated,
            builder.discountCodeRuleId,
            builder.validDateRange
        );
    }

    public static BrinkDiscountBuilder builder() {
        return new BrinkDiscountBuilder();
    }

    public static class BrinkDiscountBuilder {
        private String code;
        private boolean isUnlimited;
        private int usageLimit;
        private Instant created;
        private Instant updated;
        private String discountCodeRuleId;
        private BrinkDateRange validDateRange;

        public BrinkDiscountBuilder() {
        }

        public BrinkDiscountBuilder withCode(final String code) {
            this.code = code;
            return this;
        }

        public BrinkDiscountBuilder withIsUnlimited(final boolean isUnlimited) {
            this.isUnlimited = isUnlimited;
            return this;
        }

        public BrinkDiscountBuilder withUsageLimit(final int usageLimit) {
            this.usageLimit = usageLimit;
            return this;
        }

        public BrinkDiscountBuilder withCreated(final Instant created) {
            this.created = created;
            return this;
        }

        public BrinkDiscountBuilder withUpdated(final Instant updated) {
            this.updated = updated;
            return this;
        }

        public BrinkDiscountBuilder withDiscountCodeRuleId(final String discountCodeRuleId) {
            this.discountCodeRuleId = discountCodeRuleId;
            return this;
        }

        public BrinkDiscountBuilder withValidDateRange(final BrinkDateRange validDateRange) {
            this.validDateRange = validDateRange;
            return this;
        }

        public BrinkDiscount build() {
            return new BrinkDiscount(this);
        }
    }
}