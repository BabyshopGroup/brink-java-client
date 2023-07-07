package com.brinkcommerce.api.management.stock.storegroups;

import java.time.Instant;
import java.util.List;

public record BrinkStoreGroupMarketInventories(
    String storeDescription,
    String storeGroupId,
    List<BrinkStoreGroupMarketInventory> inventories,
    String countryCode,
    Instant created,
    String storeName,
    Instant updated) {}
