package com.brinkcommerce.api.shopper.prices;

import java.util.List;

public record BrinkProductParentPricesProductParent(
    Boolean isTaxIncludedInPrice,
    String storeGroupId,
    String countryCode,
    List<BrinkProductVariantPrice> productVariants,
    String id,
    String currencyCode
) {}
