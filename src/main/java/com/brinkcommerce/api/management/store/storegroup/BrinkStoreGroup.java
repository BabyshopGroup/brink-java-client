package com.brinkcommerce.api.management.store.storegroup;

import java.time.Instant;

public record BrinkStoreGroup(
    String id, String name, String description, Instant created, Instant updated) {

  private BrinkStoreGroup(final BrinkStoreGroupBuilder builder) {
    this(builder.id, builder.name, builder.description, builder.created, builder.updated);
  }

  public static BrinkStoreGroupBuilder builder() {
    return new BrinkStoreGroupBuilder();
  }

  public static BrinkStoreGroupBuilder builder(final BrinkStoreGroup brinkStoreGroup) {
    return new BrinkStoreGroupBuilder(brinkStoreGroup);
  }

  public static class BrinkStoreGroupBuilder {

    private String id;
    private String name;
    private String description;
    private Instant created;
    private Instant updated;

    public BrinkStoreGroupBuilder() {}

    public BrinkStoreGroupBuilder(final BrinkStoreGroup brinkStoreGroup) {
      copy(brinkStoreGroup);
    }

    public BrinkStoreGroupBuilder withId(final String id) {
      this.id = id;
      return this;
    }

    public BrinkStoreGroupBuilder withName(final String name) {
      this.name = name;
      return this;
    }

    public BrinkStoreGroupBuilder withDescription(final String description) {
      this.description = description;
      return this;
    }

    private BrinkStoreGroupBuilder withCreated(final Instant created) {
      this.created = created;
      return this;
    }

    private BrinkStoreGroupBuilder withUpdated(final Instant updated) {
      this.updated = updated;
      return this;
    }

    public BrinkStoreGroup build() {
      return new BrinkStoreGroup(this);
    }

    private BrinkStoreGroupBuilder copy(final BrinkStoreGroup brinkStoreGroup) {
      withId(brinkStoreGroup.id());
      withName(brinkStoreGroup.name());
      withDescription(brinkStoreGroup.description());
      withCreated(brinkStoreGroup.created());
      withUpdated(brinkStoreGroup.updated());
      return this;
    }
  }
}
