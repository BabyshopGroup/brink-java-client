package com.brinkcommerce.api.management.price.variant.model;

import java.time.Instant;

public record BrinkPriceVariantResponse(
  long basePriceAmount,
  String productVariantId,
  String storeGroupId,
  String countryCode,
  Instant created,
  long salePriceAmount,
  long discountAmount,
  String storeName,
  String currencyCode,
  Instant updated,
  Long referencePriceAmount
) {

}