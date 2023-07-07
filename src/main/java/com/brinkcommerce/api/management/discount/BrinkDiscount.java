package com.brinkcommerce.api.management.discount;

import java.time.Instant;

public record BrinkDiscount(String code, boolean isUnlimited, int usageLimit, Instant created, Instant updated, String discountCodeRuleId, BrinkDateRange dateRange) {


    public static BrinkDiscount createRequest(final String code, final boolean isUnlimited, final int usageLimit, final String discountCodeRuleId, final BrinkDateRange dateRange) {
        return new BrinkDiscount(code, isUnlimited, usageLimit, null, null, discountCodeRuleId, dateRange);
    }

    public static BrinkDiscount createRequest(final String code, final boolean isUnlimited, final int usageLimit, final String discountCodeRuleId, final Instant from, final Instant to) {
        return new BrinkDiscount(code, isUnlimited, usageLimit, null, null, discountCodeRuleId, new BrinkDateRange(from, to));
    }

}