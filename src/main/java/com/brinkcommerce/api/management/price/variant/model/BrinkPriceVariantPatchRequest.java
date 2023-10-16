package com.brinkcommerce.api.management.price.variant.model;

import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public record BrinkPriceVariantPatchRequest(
    @JsonInclude(JsonInclude.Include.ALWAYS) //Required to be included in Map to enable deletion of markets
    Map<BrinkCountryCode, BrinkPriceVariant> productVariantPrices,
    String productVariantId,
    String storeGroupId) implements VariantRequest {


  private BrinkPriceVariantPatchRequest(final
      BrinkPriceVariantListRequestBuilder builder) {
    this(
        builder.productVariantPrices,
        builder.productVariantId,
        builder.storeGroupId
        );
  }

  public static BrinkPriceVariantListRequestBuilder builder() {
    return new BrinkPriceVariantListRequestBuilder();
  }

  public static class BrinkPriceVariantListRequestBuilder {

    private Map<BrinkCountryCode, BrinkPriceVariant> productVariantPrices;
    private String productVariantId;
    private String storeGroupId;

    public BrinkPriceVariantListRequestBuilder() {
    }

    public BrinkPriceVariantListRequestBuilder withProductVariantPrices(
        final Map<BrinkCountryCode, BrinkPriceVariant> productVariantPrices) {
      this.productVariantPrices = productVariantPrices;
      return this;
    }

    public BrinkPriceVariantListRequestBuilder withProductVariantId(
        final String productVariantId) {
      this.productVariantId = productVariantId;
      return this;
    }

    public BrinkPriceVariantListRequestBuilder withStoreGroupId(
        final String storeGroupId) {
      this.storeGroupId = storeGroupId;
      return this;
    }

    public BrinkPriceVariantPatchRequest build() {
      return new BrinkPriceVariantPatchRequest(this);
    }


  }
}
