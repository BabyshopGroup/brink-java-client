package com.brinkcommerce.api.management.price.variant.model;

import com.brinkcommerce.api.management.store.BrinkCountryCode;

public record BrinkPriceVariantDeleteRequest(
    String storeGroupId,
    BrinkCountryCode countryCode,
    String productVariantId) {


  private BrinkPriceVariantDeleteRequest(
      BrinkPriceVariantDeleteRequestBuilder builder) {
    this(
        builder.storeGroupId,
        builder.countryCode,
        builder.productVariantId
        );
  }

  public static BrinkPriceVariantDeleteRequestBuilder builder() {
    return new BrinkPriceVariantDeleteRequestBuilder();
  }

  public static class BrinkPriceVariantDeleteRequestBuilder {

    private String storeGroupId;
    private BrinkCountryCode countryCode;
    private String productVariantId;

    public BrinkPriceVariantDeleteRequestBuilder() {
    }

    public BrinkPriceVariantDeleteRequestBuilder withStoreGroupId(
        final String storeGroupId) {
      this.storeGroupId = storeGroupId;
      return this;
    }

    public BrinkPriceVariantDeleteRequestBuilder withCountryCode(
        final BrinkCountryCode countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public BrinkPriceVariantDeleteRequestBuilder withProductVariantId(
        final String productVariantId) {
      this.productVariantId = productVariantId;
      return this;
    }

    public BrinkPriceVariantDeleteRequest build() {
      return new BrinkPriceVariantDeleteRequest(this);
    }


  }
}
