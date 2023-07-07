package com.brinkcommerce.api.management.price.addon;

import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.fasterxml.jackson.annotation.JsonProperty;

public record BrinkPriceAddon(
    Long basePriceAmount,
    Long salePriceAmount,
    BrinkCountryCode countryCode,
    @JsonProperty("addonId") String id,
    String storeGroupId)
    implements AddonRequest {

  public static BrinkPriceAddonBuilder builder() {
    return new BrinkPriceAddonBuilder();
  }

  public static class BrinkPriceAddonBuilder {

    private Long basePrice;
    private Long salePrice;
    private String id;
    private String storeGroupId;
    private BrinkCountryCode countryCode;

    public BrinkPriceAddonBuilder withBasePriceAmount(final Long basePriceAmount) {
      this.basePrice = basePriceAmount;
      return this;
    }

    public BrinkPriceAddonBuilder withSalePriceAmount(final Long salePriceAmount) {
      this.salePrice = salePriceAmount;
      return this;
    }

    public BrinkPriceAddonBuilder withAddonId(final String addonId) {
      this.id = addonId;
      return this;
    }

    public BrinkPriceAddonBuilder withStoreGroupId(final String storeGroupId) {
      this.storeGroupId = storeGroupId;
      return this;
    }

    public BrinkPriceAddonBuilder withCountryCode(final BrinkCountryCode countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public BrinkPriceAddon build() {
      return new BrinkPriceAddon(
          this.basePrice, this.salePrice, this.countryCode, this.id, this.storeGroupId);
    }
  }
}
