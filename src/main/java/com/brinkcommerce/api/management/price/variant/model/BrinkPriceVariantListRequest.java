package com.brinkcommerce.api.management.price.variant.model;

import java.util.List;

public record BrinkPriceVariantListRequest(
    List<BrinkPriceVariant> productVariantPrices,
    String productVariantId,
    String storeGroupId) implements VariantRequest {


  private BrinkPriceVariantListRequest(final
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

    private List<BrinkPriceVariant> productVariantPrices;
    private String productVariantId;
    private String storeGroupId;

    public BrinkPriceVariantListRequestBuilder() {
    }

    public BrinkPriceVariantListRequestBuilder withProductVariantPrices(
        final List<BrinkPriceVariant> productVariantPrices) {
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

    public BrinkPriceVariantListRequest build() {
      return new BrinkPriceVariantListRequest(this);
    }


  }
}
