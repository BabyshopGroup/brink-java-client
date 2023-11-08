package com.brinkcommerce.api.management.stock.productvariants;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record BrinkVariantInventory(
    Integer quantity,
    @JsonProperty("productVariantId") String variantId,
    String inventoryId) {
  public BrinkVariantInventory {
    Objects.requireNonNull(quantity, "A Variant Inventory can not have quantity = null");
    Objects.requireNonNull(variantId, "A Variant Inventory can not have variantId = null");
    Objects.requireNonNull(inventoryId, "A Variant Inventory can not have inventoryId = null");
    if(0 > quantity) {
      throw new IllegalArgumentException("Stock level must be natural number");
    }
  }

}
