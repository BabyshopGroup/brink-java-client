package com.brinkcommerce.api.management.price.variant.model;

import com.brinkcommerce.api.management.store.BrinkCountryCode;

public interface VariantRequest {
  String storeGroupId();

  String productVariantId();
}
