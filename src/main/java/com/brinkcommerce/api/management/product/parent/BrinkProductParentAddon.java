package com.brinkcommerce.api.management.product.parent;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record BrinkProductParentAddon(
    Boolean isArchived,
    Instant created,
    Map<String, String> displayNames,
    String description,
    Map<String, String> displayDescriptions,
    Boolean isActive,
    Map<String, List<String>> tags,
    String taxGroupId,
    String imageUrl,
    String name,
    String addonId,
    String productParentId,
    Instant updated,
    Map<String, String> customAttributes) {

  private BrinkProductParentAddon(final BrinkProductParentAddonBuilder builder) {
    this(
        builder.isArchived,
        builder.created,
        builder.displayNames,
        builder.description,
        builder.displayDescriptions,
        builder.isActive,
        builder.tags,
        builder.taxGroupId,
        builder.imageUrl,
        builder.name,
        builder.addonId,
        builder.productParentId,
        builder.updated,
        builder.customAttributes
        );
  }

  public static BrinkProductParentAddonBuilder builder() {
    return new BrinkProductParentAddonBuilder();
  }

  public static class BrinkProductParentAddonBuilder {

    private Boolean isArchived;
    private Instant created;
    private Map<String, String> displayNames;
    private String description;
    private Map<String, String> displayDescriptions;
    private Boolean isActive;
    private Map<String, List<String>> tags;
    private String taxGroupId;
    private String imageUrl;
    private String name;
    private String addonId;
    private String productParentId;
    private Instant updated;
    private Map<String, String> customAttributes;

    public BrinkProductParentAddonBuilder() {
    }

    public BrinkProductParentAddonBuilder withIsArchived(final Boolean isArchived) {
      this.isArchived = isArchived;
      return this;
    }

    public BrinkProductParentAddonBuilder withCreated(final Instant created) {
      this.created = created;
      return this;
    }

    public BrinkProductParentAddonBuilder withDisplayNames(
        final Map<String, String> displayNames) {
      this.displayNames = displayNames;
      return this;
    }

    public BrinkProductParentAddonBuilder withDescription(final String description) {
      this.description = description;
      return this;
    }

    public BrinkProductParentAddonBuilder withDisplayDescriptions(
        final Map<String, String> displayDescriptions) {
      this.displayDescriptions = displayDescriptions;
      return this;
    }

    public BrinkProductParentAddonBuilder withIsActive(final Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public BrinkProductParentAddonBuilder withTags(
        final Map<String, List<String>> tags) {
      this.tags = tags;
      return this;
    }

    public BrinkProductParentAddonBuilder withTaxGroupId(final String taxGroupId) {
      this.taxGroupId = taxGroupId;
      return this;
    }

    public BrinkProductParentAddonBuilder withImageUrl(final String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    public BrinkProductParentAddonBuilder withName(final String name) {
      this.name = name;
      return this;
    }

    public BrinkProductParentAddonBuilder withAddonId(final String addonId) {
      this.addonId = addonId;
      return this;
    }

    public BrinkProductParentAddonBuilder withProductParentId(
        final String productParentId) {
      this.productParentId = productParentId;
      return this;
    }

    public BrinkProductParentAddonBuilder withUpdated(final Instant updated) {
      this.updated = updated;
      return this;
    }

    public BrinkProductParentAddonBuilder withCustomAttributes(
        final Map<String, String> customAttributes) {
      this.customAttributes = customAttributes;
      return this;
    }

    public BrinkProductParentAddon build() {
      return new BrinkProductParentAddon(this);
    }
  }
}
