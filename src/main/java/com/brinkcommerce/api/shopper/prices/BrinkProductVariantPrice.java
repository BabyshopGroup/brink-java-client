package com.brinkcommerce.api.shopper.prices;

public record BrinkProductVariantPrice(
    Long basePriceAmount,
    Long salePriceAmount,
    Long discountAmount,
    Long referencePriceAmount,
    Long taxAmount,
    Long taxPercentage,
    Long taxPercentageDecimals,
    String id) {}
