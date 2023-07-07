package com.brinkcommerce.api.management.product.variant;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record BrinkProductVariant(
    String taxGroupId,
    Boolean isArchived,
    Instant created,
    Map<String, String> displayNames,
    String description,
    Map<String, String> displayDescriptions,
    Boolean isActive,
    String imageUrl,
    String name,
    String id,
    Instant updated,
    String slug,
    String productParentId,
    Map<String, String> customAttributes,
    Map<String, List<String>> tags,
    Integer weight,
    BrinkDimensions dimensions) {

  private BrinkProductVariant(final BrinkProductVariantBuilder builder) {
    this(
        builder.taxGroupId,
        builder.isArchived,
        builder.created,
        builder.displayNames,
        builder.description,
        builder.displayDescriptions,
        builder.isActive,
        builder.imageUrl,
        builder.name,
        builder.id,
        builder.updated,
        builder.slug,
        builder.productParentId,
        builder.customAttributes,
        builder.tags,
        builder.weight,
        builder.dimensions
        );
  }

  public static BrinkProductVariantBuilder builder() {
    return new BrinkProductVariantBuilder();
  }

  public static class BrinkProductVariantBuilder {

    private String taxGroupId;
    private Boolean isArchived;
    private Instant created;
    private Map<String, String> displayNames;
    private String description;
    private Map<String, String> displayDescriptions;
    private Boolean isActive;
    private String imageUrl;
    private String name;
    private String id;
    private Instant updated;
    private String slug;
    private String productParentId;
    private Map<String, String> customAttributes;
    private Map<String, List<String>> tags;
    private Integer weight;
    private BrinkDimensions dimensions;

    public BrinkProductVariantBuilder() {
    }

    public BrinkProductVariantBuilder withTaxGroupId(final String taxGroupId) {
      this.taxGroupId = taxGroupId;
      return this;
    }

    public BrinkProductVariantBuilder withIsArchived(final Boolean isArchived) {
      this.isArchived = isArchived;
      return this;
    }

    public BrinkProductVariantBuilder withCreated(final Instant created) {
      this.created = created;
      return this;
    }

    public BrinkProductVariantBuilder withDisplayNames(
        final Map<String, String> displayNames) {
      this.displayNames = displayNames;
      return this;
    }

    public BrinkProductVariantBuilder withDescription(final String description) {
      this.description = description;
      return this;
    }

    public BrinkProductVariantBuilder withDisplayDescriptions(
        final Map<String, String> displayDescriptions) {
      this.displayDescriptions = displayDescriptions;
      return this;
    }

    public BrinkProductVariantBuilder withIsActive(final Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public BrinkProductVariantBuilder withImageUrl(final String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    public BrinkProductVariantBuilder withName(final String name) {
      this.name = name;
      return this;
    }

    public BrinkProductVariantBuilder withId(final String id) {
      this.id = id;
      return this;
    }

    public BrinkProductVariantBuilder withUpdated(final Instant updated) {
      this.updated = updated;
      return this;
    }

    public BrinkProductVariantBuilder withSlug(final String slug) {
      this.slug = slug;
      return this;
    }

    public BrinkProductVariantBuilder withProductParentId(final String productParentId) {
      this.productParentId = productParentId;
      return this;
    }

    public BrinkProductVariantBuilder withCustomAttributes(
        final Map<String, String> customAttributes) {
      this.customAttributes = customAttributes;
      return this;
    }

    public BrinkProductVariantBuilder withTags(
        final Map<String, List<String>> tags) {
      this.tags = tags;
      return this;
    }

    public BrinkProductVariantBuilder withWeight(final Integer weight) {
      this.weight = weight;
      return this;
    }

    public BrinkProductVariantBuilder withDimensions(
        final BrinkDimensions dimensions) {
      this.dimensions = dimensions;
      return this;
    }

    public BrinkProductVariant build() {
      return new BrinkProductVariant(this);
    }
  }
}
