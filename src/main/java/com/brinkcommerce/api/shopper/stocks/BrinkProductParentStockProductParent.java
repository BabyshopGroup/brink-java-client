package com.brinkcommerce.api.shopper.stocks;

import java.util.List;

public record BrinkProductParentStockProductParent(
    List<BrinkProductVariantStock> productVariants,
    String id
) {}
