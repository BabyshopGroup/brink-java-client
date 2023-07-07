package com.brinkcommerce.api.management.tax.taxgroup;

import java.time.Instant;

public record BrinkTaxGroup(
    String name, String description, String id, Instant created, Instant updated) {

  private BrinkTaxGroup(BrinkTaxGroupBuilder builder) {
    this(builder.name, builder.description, builder.id, builder.created, builder.updated);
  }

  public static BrinkTaxGroupBuilder builder() {
    return new BrinkTaxGroupBuilder();
  }

  public static BrinkTaxGroupBuilder builder(final BrinkTaxGroup obj) {
    return new BrinkTaxGroupBuilder(obj);
  }

  public static class BrinkTaxGroupBuilder {

    private String name;
    private String description;
    private String id;
    private Instant created;
    private Instant updated;

    public BrinkTaxGroupBuilder() {}

    public BrinkTaxGroupBuilder(final BrinkTaxGroup obj) {
      copy(obj);
    }

    public BrinkTaxGroupBuilder withName(final String name) {
      this.name = name;
      return this;
    }

    public BrinkTaxGroupBuilder withDescription(final String description) {
      this.description = description;
      return this;
    }

    public BrinkTaxGroupBuilder withId(final String taxGroupId) {
      this.id = taxGroupId;
      return this;
    }

    private BrinkTaxGroupBuilder withCreated(final Instant created) {
      this.created = created;
      return this;
    }

    private BrinkTaxGroupBuilder withUpdated(final Instant updated) {
      this.updated = updated;
      return this;
    }

    public BrinkTaxGroup build() {
      return new BrinkTaxGroup(this);
    }

    private BrinkTaxGroupBuilder copy(final BrinkTaxGroup obj) {
      withName(obj.name());
      withDescription(obj.description());
      withId(obj.id());
      withCreated(obj.created());
      withUpdated(obj.updated());
      return this;
    }
  }
}
