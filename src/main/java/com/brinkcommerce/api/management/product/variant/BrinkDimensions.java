package com.brinkcommerce.api.management.product.variant;

public record BrinkDimensions(Integer width, Integer length, Integer height) {

  private BrinkDimensions(final BrinkDimensionsBuilder builder) {
    this(
        builder.width,
        builder.length,
        builder.height
        );
  }

  public static BrinkDimensionsBuilder builder() {
    return new BrinkDimensionsBuilder();
  }

  public static class BrinkDimensionsBuilder {

    private int width;
    private int length;
    private int height;

    public BrinkDimensionsBuilder() {
    }

    public BrinkDimensionsBuilder withWidth(final int width) {
      this.width = width;
      return this;
    }

    public BrinkDimensionsBuilder withLength(final int length) {
      this.length = length;
      return this;
    }

    public BrinkDimensionsBuilder withHeight(final int height) {
      this.height = height;
      return this;
    }

    public BrinkDimensions build() {
      return new BrinkDimensions(this);
    }


  }
}
