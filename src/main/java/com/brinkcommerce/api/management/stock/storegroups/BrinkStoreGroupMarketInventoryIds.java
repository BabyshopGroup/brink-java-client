package com.brinkcommerce.api.management.stock.storegroups;

import java.util.List;

public record BrinkStoreGroupMarketInventoryIds(
    String storeGroupId, String countryCode, List<String> inventoryIds) {}
