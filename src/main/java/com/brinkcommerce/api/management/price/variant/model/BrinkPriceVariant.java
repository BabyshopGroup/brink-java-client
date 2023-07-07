package com.brinkcommerce.api.management.price.variant.model;

import com.brinkcommerce.api.management.store.BrinkCountryCode;

public record BrinkPriceVariant(
    Long basePriceAmount,
    Long salePriceAmount,
    BrinkCountryCode countryCode
    ) {


  private BrinkPriceVariant(final BrinkPriceVariantBuilder builder) {
    this(
        builder.basePriceAmount,
        builder.salePriceAmount,
        builder.countryCode
        );
  }

  public static BrinkPriceVariantBuilder builder() {
    return new BrinkPriceVariantBuilder();
  }

  public static class BrinkPriceVariantBuilder {

    private Long basePriceAmount;
    private Long salePriceAmount;
    private BrinkCountryCode countryCode;

    public BrinkPriceVariantBuilder() {
    }

    public BrinkPriceVariantBuilder withBasePriceAmount(final Long basePriceAmount) {
      this.basePriceAmount = basePriceAmount;
      return this;
    }

    public BrinkPriceVariantBuilder withSalePriceAmount(final long salePriceAmount) {
      this.salePriceAmount = salePriceAmount;
      return this;
    }

    public BrinkPriceVariantBuilder withCountryCode(
        final BrinkCountryCode countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public BrinkPriceVariant build() {
      return new BrinkPriceVariant(this);
    }
  }
}
