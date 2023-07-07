package com.brinkcommerce.api.management.product.addon;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record BrinkProductAddon(
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
    String id,
    Instant updated,
    Map<String, String> customAttributes) {

  private BrinkProductAddon(final BrinkProductAddonBuilder builder) {
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
        builder.id,
        builder.updated,
        builder.customAttributes
        );
  }

  public static BrinkProductAddonBuilder builder() {
    return new BrinkProductAddonBuilder();
  }

  public static class BrinkProductAddonBuilder {

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
    private String id;
    private Instant updated;
    private Map<String, String> customAttributes;

    public BrinkProductAddonBuilder() {
    }

    public BrinkProductAddonBuilder withIsArchived(final Boolean isArchived) {
      this.isArchived = isArchived;
      return this;
    }

    public BrinkProductAddonBuilder withCreated(final Instant created) {
      this.created = created;
      return this;
    }

    public BrinkProductAddonBuilder withDisplayNames(
        final Map<String, String> displayNames) {
      this.displayNames = displayNames;
      return this;
    }

    public BrinkProductAddonBuilder withDescription(final String description) {
      this.description = description;
      return this;
    }

    public BrinkProductAddonBuilder withDisplayDescriptions(
        final Map<String, String> displayDescriptions) {
      this.displayDescriptions = displayDescriptions;
      return this;
    }

    public BrinkProductAddonBuilder withIsActive(final Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public BrinkProductAddonBuilder withTags(
        final Map<String, List<String>> tags) {
      this.tags = tags;
      return this;
    }

    public BrinkProductAddonBuilder withTaxGroupId(final String taxGroupId) {
      this.taxGroupId = taxGroupId;
      return this;
    }

    public BrinkProductAddonBuilder withImageUrl(final String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    public BrinkProductAddonBuilder withName(final String name) {
      this.name = name;
      return this;
    }

    public BrinkProductAddonBuilder withId(final String id) {
      this.id = id;
      return this;
    }

    public BrinkProductAddonBuilder withUpdated(final Instant updated) {
      this.updated = updated;
      return this;
    }

    public BrinkProductAddonBuilder withCustomAttributes(
        final Map<String, String> customAttributes) {
      this.customAttributes = customAttributes;
      return this;
    }

    public BrinkProductAddon build() {
      return new BrinkProductAddon(this);
    }


  }
}
