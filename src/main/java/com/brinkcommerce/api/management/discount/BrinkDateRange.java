package com.brinkcommerce.api.management.discount;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public record BrinkDateRange(String from, String to) {
  private BrinkDateRange(final BrinkDateRangeBuilder builder) {
    this(
        builder.from,
        builder.to
    );
  }

  public static BrinkDateRangeBuilder builder() {
    return new BrinkDateRangeBuilder();
  }

  public static class BrinkDateRangeBuilder {
    private String from;
    private String to;

    public BrinkDateRangeBuilder() {
    }

    public BrinkDateRangeBuilder withFrom(final Instant from) {
      this.from = from.truncatedTo(ChronoUnit.SECONDS).toString();
      return this;
    }

    public BrinkDateRangeBuilder withTo(final Instant to) {
      this.to = to.truncatedTo(ChronoUnit.SECONDS).toString();
      return this;
    }

    public BrinkDateRange build() {
      return new BrinkDateRange(this);
    }
  }
}
