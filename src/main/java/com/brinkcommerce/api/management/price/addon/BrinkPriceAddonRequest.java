package com.brinkcommerce.api.management.price.addon;

import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.fasterxml.jackson.annotation.JsonProperty;

public record BrinkPriceAddonRequest(
    String storeGroupId, BrinkCountryCode countryCode, @JsonProperty("addonId") String id)
    implements AddonRequest {

  private BrinkPriceAddonRequest(final BrinkPriceAddonRequestBuilder builder) {
    this(builder.storeGroupId, builder.countryCode, builder.id);
  }

  public static BrinkPriceAddonRequestBuilder builder() {
    return new BrinkPriceAddonRequestBuilder();
  }

  public static BrinkPriceAddonRequestBuilder builder(final BrinkPriceAddonRequest request) {
    return new BrinkPriceAddonRequestBuilder(request);
  }

  public static class BrinkPriceAddonRequestBuilder {

    private String storeGroupId;
    private BrinkCountryCode countryCode;
    private String id;

    public BrinkPriceAddonRequestBuilder() {}

    public BrinkPriceAddonRequestBuilder(final BrinkPriceAddonRequest request) {
      copy(request);
    }

    public BrinkPriceAddonRequestBuilder withStoreGroupId(final String storeGroupId) {
      this.storeGroupId = storeGroupId;
      return this;
    }

    public BrinkPriceAddonRequestBuilder withCountryCode(final BrinkCountryCode countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public BrinkPriceAddonRequestBuilder withAddonId(final String addonId) {
      this.id = addonId;
      return this;
    }

    public BrinkPriceAddonRequest build() {
      return new BrinkPriceAddonRequest(this);
    }

    private BrinkPriceAddonRequestBuilder copy(final BrinkPriceAddonRequest request) {
      withStoreGroupId(request.storeGroupId());
      withCountryCode(request.countryCode());
      withAddonId(request.id());
      return this;
    }
  }
}
