package com.brinkcommerce.api.management.stock.inventories;

import java.time.Instant;

public record BrinkInventory(
    BrinkAddress address,

    String name,
    String description,
    String id,
    Instant created,
    Instant updated) {

  public static BrinkInventory of(final BrinkAddress address, final String name, final String description, final String id) {
    return BrinkInventory.of(address,name,description,id,null,null);
  }

  public static BrinkInventory of(final BrinkAddress address, final String name, final String description, final String id, final Instant created, final Instant updated) {
    return new BrinkInventory(address,name,description,id,created,updated);
  }
}
