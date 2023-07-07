package com.brinkcommerce.api.management.store.market;

import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.brinkcommerce.api.management.store.BrinkCurrencyCode;
import com.brinkcommerce.api.management.store.BrinkLanguageCode;
import java.time.Instant;
import java.util.List;

public record BrinkStoreMarket(
    List<BrinkLanguageCode> languageCodes,
    Boolean isTaxIncludedInPrice,
    String storeGroupId,
    BrinkCountryCode countryCode,
    Boolean isArchived,
    Instant created,
    String name,
    String description,
    BrinkCurrencyCode currencyCode,
    Instant updated) {

  private BrinkStoreMarket(final BrinkStoreMarketBuilder builder) {
    this(
        builder.languageCodes,
        builder.isTaxIncludedInPrice,
        builder.storeGroupId,
        builder.countryCode,
        builder.isArchived,
        builder.created,
        builder.name,
        builder.description,
        builder.currencyCode,
        builder.updated);
  }

  public static BrinkStoreMarketBuilder builder() {
    return new BrinkStoreMarketBuilder();
  }

  public static BrinkStoreMarketBuilder builder(final BrinkStoreMarket brinkStoreMarket) {
    return new BrinkStoreMarketBuilder(brinkStoreMarket);
  }

  public static class BrinkStoreMarketBuilder {

    private List<BrinkLanguageCode> languageCodes;
    private Boolean isTaxIncludedInPrice;
    private String storeGroupId;
    private BrinkCountryCode countryCode;
    private Boolean isArchived;
    private Instant created;
    private String name;
    private String description;
    private BrinkCurrencyCode currencyCode;
    private Instant updated;

    public BrinkStoreMarketBuilder() {}

    public BrinkStoreMarketBuilder(final BrinkStoreMarket brinkStoreMarket) {
      copy(brinkStoreMarket);
    }

    public BrinkStoreMarketBuilder withLanguageCodes(final List<BrinkLanguageCode> languageCodes) {
      this.languageCodes = languageCodes;
      return this;
    }

    public BrinkStoreMarketBuilder withIsTaxIncludedInPrice(final Boolean isTaxIncludedInPrice) {
      this.isTaxIncludedInPrice = isTaxIncludedInPrice;
      return this;
    }

    public BrinkStoreMarketBuilder withStoreGroupId(final String storeGroupId) {
      this.storeGroupId = storeGroupId;
      return this;
    }

    public BrinkStoreMarketBuilder withCountryCode(final BrinkCountryCode countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    private BrinkStoreMarketBuilder withIsArchived(final Boolean isArchived) {
      this.isArchived = isArchived;
      return this;
    }

    private BrinkStoreMarketBuilder withCreated(final Instant created) {
      this.created = created;
      return this;
    }

    private BrinkStoreMarketBuilder withName(final String name) {
      this.name = name;
      return this;
    }

    private BrinkStoreMarketBuilder withDescription(final String description) {
      this.description = description;
      return this;
    }

    public BrinkStoreMarketBuilder withCurrencyCode(final BrinkCurrencyCode currencyCode) {
      this.currencyCode = currencyCode;
      return this;
    }

    private BrinkStoreMarketBuilder withUpdated(final Instant updated) {
      this.updated = updated;
      return this;
    }

    public BrinkStoreMarket build() {
      return new BrinkStoreMarket(this);
    }

    private BrinkStoreMarketBuilder copy(final BrinkStoreMarket market) {
      withLanguageCodes(market.languageCodes());
      withIsTaxIncludedInPrice(market.isTaxIncludedInPrice());
      withStoreGroupId(market.storeGroupId());
      withCountryCode(market.countryCode());
      withIsArchived(market.isArchived());
      withCreated(market.created());
      withName(market.name());
      withDescription(market.description());
      withCurrencyCode(market.currencyCode());
      withUpdated(market.updated());
      return this;
    }
  }
}
