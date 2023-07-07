package com.brinkcommerce.api.management.product.parent;


import java.time.Instant;
import java.util.List;
import java.util.Map;

public record BrinkProductParent(
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
    Map<String, String> customAttributes,
    Map<String, List<String>> tags) {

  private BrinkProductParent(final BrinkProductParentBuilder builder) {
    this(
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
        builder.customAttributes,
        builder.tags
        );
  }

  public static BrinkProductParentBuilder builder() {
    return new BrinkProductParentBuilder();
  }

  public static class BrinkProductParentBuilder {

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
    private Map<String, String> customAttributes;
    private Map<String, List<String>> tags;

    public BrinkProductParentBuilder() {
    }

    public BrinkProductParentBuilder withIsArchived(final Boolean isArchived) {
      this.isArchived = isArchived;
      return this;
    }

    public BrinkProductParentBuilder withCreated(final Instant created) {
      this.created = created;
      return this;
    }

    public BrinkProductParentBuilder withDisplayNames(
        final Map<String, String> displayNames) {
      this.displayNames = displayNames;
      return this;
    }

    public BrinkProductParentBuilder withDescription(final String description) {
      this.description = description;
      return this;
    }

    public BrinkProductParentBuilder withDisplayDescriptions(
        final Map<String, String> displayDescriptions) {
      this.displayDescriptions = displayDescriptions;
      return this;
    }

    public BrinkProductParentBuilder withIsActive(final Boolean isActive) {
      this.isActive = isActive;
      return this;
    }

    public BrinkProductParentBuilder withImageUrl(final String imageUrl) {
      this.imageUrl = imageUrl;
      return this;
    }

    public BrinkProductParentBuilder withName(final String name) {
      this.name = name;
      return this;
    }

    public BrinkProductParentBuilder withId(final String id) {
      this.id = id;
      return this;
    }

    public BrinkProductParentBuilder withUpdated(final Instant updated) {
      this.updated = updated;
      return this;
    }

    public BrinkProductParentBuilder withSlug(final String slug) {
      this.slug = slug;
      return this;
    }

    public BrinkProductParentBuilder withCustomAttributes(final Map<String, String> customAttributes) {
      this.customAttributes = customAttributes;
      return this;
    }

    public BrinkProductParentBuilder withTags(
        final Map<String, List<String>> tags) {
      this.tags = tags;
      return this;
    }

    public BrinkProductParent build() {
      return new BrinkProductParent(this);
    }
  }
}

