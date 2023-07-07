package com.brinkcommerce.api.management.price.variant.model;

public record BrinkPriceVariantRequest(
    String storeGroupId,
    String productVariantId) implements VariantRequest {



  private BrinkPriceVariantRequest(final
      BrinkPriceVariantRequestBuilder builder) {
    this(
        builder.storeGroupId,
        builder.productVariantId
        );
  }

  public static BrinkPriceVariantRequestBuilder builder() {
    return new BrinkPriceVariantRequestBuilder();
  }

  public static class BrinkPriceVariantRequestBuilder {

    private String storeGroupId;
    private String productVariantId;

    public BrinkPriceVariantRequestBuilder() {
    }

    public BrinkPriceVariantRequestBuilder withStoreGroupId(final String storeGroupId) {
      this.storeGroupId = storeGroupId;
      return this;
    }

    public BrinkPriceVariantRequestBuilder withProductVariantId(
        final String productVariantId) {
      this.productVariantId = productVariantId;
      return this;
    }

    public BrinkPriceVariantRequest build() {
      return new BrinkPriceVariantRequest(this);
    }
  }
}
