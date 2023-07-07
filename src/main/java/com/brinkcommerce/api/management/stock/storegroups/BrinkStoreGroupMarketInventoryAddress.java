package com.brinkcommerce.api.management.stock.storegroups;

import com.brinkcommerce.api.management.stock.inventories.BrinkAddress;

import java.time.Instant;

public record BrinkStoreGroupMarketInventoryAddress(
    BrinkAddress address,
    Instant created,
    String name,
    String description,
    String id,
    Instant updated) {}
