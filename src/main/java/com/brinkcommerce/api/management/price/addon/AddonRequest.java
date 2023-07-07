package com.brinkcommerce.api.management.price.addon;

import com.brinkcommerce.api.management.store.BrinkCountryCode;

public interface AddonRequest {
  String storeGroupId();

  BrinkCountryCode countryCode();

  String id();
}
