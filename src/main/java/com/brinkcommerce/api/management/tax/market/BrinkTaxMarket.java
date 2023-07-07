package com.brinkcommerce.api.management.tax.market;

import com.brinkcommerce.api.management.store.BrinkCountryCode;
import java.time.Instant;

public record BrinkTaxMarket(
    BrinkCountryCode countryCode,
    Long taxPercentage,
    Long taxPercentageDecimals,
    Instant created,
    Instant updated) {

  private BrinkTaxMarket(final BrinkTaxMarketBuilder builder) {
    this(
        builder.countryCode,
        builder.taxPercentage,
        builder.taxPercentageDecimals,
        builder.created,
        builder.updated);
  }

  public static BrinkTaxMarketBuilder builder() {
    return new BrinkTaxMarketBuilder();
  }

  public static BrinkTaxMarketBuilder builder(final BrinkTaxMarket obj) {
    return new BrinkTaxMarketBuilder(obj);
  }

  public static class BrinkTaxMarketBuilder {

    private BrinkCountryCode countryCode;
    private Long taxPercentage;
    private Long taxPercentageDecimals;
    private Instant created;
    private Instant updated;

    public BrinkTaxMarketBuilder() {}

    public BrinkTaxMarketBuilder(final BrinkTaxMarket obj) {
      copy(obj);
    }

    public BrinkTaxMarketBuilder withCountryCode(final BrinkCountryCode countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public BrinkTaxMarketBuilder withTaxPercentage(final Long taxPercentage) {
      this.taxPercentage = taxPercentage;
      return this;
    }

    public BrinkTaxMarketBuilder withTaxPercentageDecimals(final Long taxPercentageDecimals) {
      this.taxPercentageDecimals = taxPercentageDecimals;
      return this;
    }

    private BrinkTaxMarketBuilder withCreated(final Instant created) {
      this.created = created;
      return this;
    }

    private BrinkTaxMarketBuilder withUpdated(final Instant updated) {
      this.updated = updated;
      return this;
    }

    public BrinkTaxMarket build() {
      return new BrinkTaxMarket(this);
    }

    private BrinkTaxMarketBuilder copy(final BrinkTaxMarket market) {
      withCountryCode(market.countryCode());
      withTaxPercentage(market.taxPercentage());
      withTaxPercentageDecimals(market.taxPercentageDecimals());
      withCreated(market.created());
      withUpdated(market.updated());
      return this;
    }
  }
}
