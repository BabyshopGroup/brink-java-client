package com.brinkcommerce.api.shopper.stocks;

public record BrinkProductVariantInventoryStock(
    Long availableQuantity,
    Boolean isAvailable,
    Long reservedQuantity,
    Long stockQuantity,
    String id) {}
