package com.brinkcommerce.api.shopper.stocks;

import java.util.List;

public record BrinkProductVariantStock(
    Boolean validateStock,
    Long availableQuantity,
    Boolean isAvailable,
    Long reservedQuantity,
    List<BrinkProductVariantInventoryStock> inventories,
    Long stockQuantity,
    String id) {}
