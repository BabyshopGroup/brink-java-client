package com.brinkcommerce.api.management.price.variant.model;

import com.brinkcommerce.api.management.store.BrinkCountryCode;

public record BrinkPriceVariantWithCountry(
    Long basePriceAmount,
    Long salePriceAmount,
    BrinkCountryCode countryCode,
    Long referencePriceAmount
    ) {


  private BrinkPriceVariantWithCountry(final BrinkPriceVariantBuilder builder) {
    this(
        builder.basePriceAmount,
        builder.salePriceAmount,
        builder.countryCode,
        builder.referencePriceAmount
        );
  }

  public static BrinkPriceVariantBuilder builder() {
    return new BrinkPriceVariantBuilder();
  }

  public static class BrinkPriceVariantBuilder {

    private Long basePriceAmount;
    private Long salePriceAmount;
    private BrinkCountryCode countryCode;
    private Long referencePriceAmount;

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

    public BrinkPriceVariantBuilder withReferencePriceAmount(
        final Long referencePriceAmount) {
      this.referencePriceAmount = referencePriceAmount;
      return this;
    }

    public BrinkPriceVariantWithCountry build() {
      return new BrinkPriceVariantWithCountry(this);
    }
  }
}
