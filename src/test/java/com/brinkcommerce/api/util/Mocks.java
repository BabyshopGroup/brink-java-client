package com.brinkcommerce.api.util;

import com.brinkcommerce.api.management.price.addon.BrinkPriceAddon;
import com.brinkcommerce.api.management.price.addon.BrinkPriceAddonRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariant;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantListRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantListResponse;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantDeleteRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantRequest;
import com.brinkcommerce.api.management.price.variant.model.BrinkPriceVariantResponse;
import com.brinkcommerce.api.management.product.addon.BrinkProductAddon;
import com.brinkcommerce.api.management.product.parent.BrinkProductParent;
import com.brinkcommerce.api.management.product.parent.BrinkProductParentAddon;
import com.brinkcommerce.api.management.product.variant.BrinkDimensions;
import com.brinkcommerce.api.management.product.variant.BrinkProductVariant;
import com.brinkcommerce.api.management.stock.inventories.BrinkInventoriesInventories;
import com.brinkcommerce.api.management.stock.inventories.BrinkInventory;
import com.brinkcommerce.api.management.stock.inventories.BrinkAddress;
import com.brinkcommerce.api.management.stock.productvariants.BrinkVariantInventory;
import com.brinkcommerce.api.management.stock.productvariants.BrinkProductVariantStock;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventories;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventory;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventoryAddress;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventoryAddressAddress;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventoryIds;
import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.brinkcommerce.api.management.store.BrinkCurrencyCode;
import com.brinkcommerce.api.management.store.BrinkLanguageCode;
import com.brinkcommerce.api.management.store.market.BrinkStoreMarket;
import com.brinkcommerce.api.management.store.storegroup.BrinkStoreGroup;
import com.brinkcommerce.api.management.tax.market.BrinkTaxMarket;
import com.brinkcommerce.api.management.tax.taxgroup.BrinkTaxGroup;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class Mocks {

  public static BrinkStoreMarket mockStoreMarket() {
    return BrinkStoreMarket.builder()
        .withCountryCode(BrinkCountryCode.SE)
        .withStoreGroupId("BABYSHOP")
        .withLanguageCodes(List.of(BrinkLanguageCode.SE, BrinkLanguageCode.SV))
        .withIsTaxIncludedInPrice(true)
        .build();
  }

  public static BrinkStoreGroup mockStoreGroup() {
    return BrinkStoreGroup.builder()
        .withId("BABYSHOP")
        .withDescription("This is Babyshop")
        .withName("Babyshop")
        .build();
  }

  public static BrinkTaxGroup mockTaxGroup() {
    return BrinkTaxGroup.builder()
        .withId("BABYSHOP")
        .withName("Babyshop")
        .withDescription("Babyshop Tax Group")
        .build();
  }

  public static BrinkTaxMarket mockTaxMarket() {
    return BrinkTaxMarket.builder()
        .withCountryCode(BrinkCountryCode.SE)
        .withTaxPercentage(2500L)
        .withTaxPercentageDecimals(2L)
        .build();
  }

  public static BrinkPriceVariantListRequest mockPriceVariantList() {
    return BrinkPriceVariantListRequest.builder()
        .withProductVariantId("123654_100")
        .withProductVariantPrices(List.of(BrinkPriceVariant.builder()
            .withCountryCode(BrinkCountryCode.SE)
            .withSalePriceAmount(100L)
            .withBasePriceAmount(80L)
            .build()))
        .withStoreGroupId("BABYSHOP")
        .build();
  }

  public static BrinkPriceVariantListResponse mockPriceVariantListResponse() {
    return new BrinkPriceVariantListResponse(List.of(
        new BrinkPriceVariantResponse(
            80L,
            null,
            null,
            "SE",
            null,
            100L,
            0L,
            null,
            null,
            null))
    );
  }

  public static BrinkPriceVariantRequest getPriceVariantRequest() {
    return new BrinkPriceVariantRequest("BABYSHOP", "123654_100");
  }

  public static BrinkPriceVariantDeleteRequest getPriceVariantMarket() {
    return new BrinkPriceVariantDeleteRequest("BABYSHOP", BrinkCountryCode.SE, "123654_100");
  }

  public static BrinkPriceAddonRequest getPriceAddonRequest() {
    return new BrinkPriceAddonRequest("BABYSHOP", BrinkCountryCode.SE, "123654_100");
  }

  public static BrinkPriceAddon mockPriceAddon() {
    return BrinkPriceAddon.builder()
        .withAddonId("123654_100")
        .withSalePriceAmount(100L)
        .withBasePriceAmount(80L)
        .withStoreGroupId("BABYSHOP")
        .withCountryCode(BrinkCountryCode.SE)
        .build();
  }

  public static BrinkProductParent mockProductParent() {
    return BrinkProductParent.builder()
        .withId("123543")
        .withCustomAttributes(Map.of())
        .withDescription("The best bunny")
        .withDisplayDescriptions(Map.of())
        .withDisplayNames(Map.of())
        .withImageUrl("http://www.google.com")
        .withIsActive(true)
        .withName("Fluffy Bunny")
        .withSlug("/bunny")
        .withTags(Map.of())
        .build();
  }

  public static BrinkProductVariant mockProductVariant() {
    return BrinkProductVariant.builder()
        .withId("123543_100")
        .withCustomAttributes(Map.of())
        .withDescription("Size 100")
        .withProductParentId("123543")
        .withDisplayDescriptions(Map.of())
        .withDisplayNames(Map.of())
        .withImageUrl("http://www.google.com")
        .withIsActive(true)
        .withName("FluffyBunny 100")
        .withSlug("/Fluffy100")
        .withTags(Map.of())
        .withTaxGroupId("BABYTAX")
        .withWeight(55)
        .withDimensions(new BrinkDimensions(55,55,55))
        .build();
  }

  public static BrinkProductAddon mockProductAddon() {
    return BrinkProductAddon.builder()
        .withId("addon-01")
        .withName("Addon 1")
        .withImageUrl("http:www.google.se")
        .withTaxGroupId("BABYTAX")
        .withTags(Map.of())
        .withDisplayNames(Map.of())
        .withDisplayDescriptions(Map.of())
        .withDescription("Super Addon 1")
        .withCustomAttributes(Map.of())
        .withIsActive(true)
        .build();
  }

  public static BrinkProductParentAddon mockProductParentAddon() {
    return new BrinkProductParentAddon(
        true,
        Instant.now(),
        Map.of(),
        "Super addon",
        Map.of(),
        true,
        Map.of(),
        "BABYTAX",
        "www.google.se",
        "Addon 1",
        null,
        null,
        Instant.now(),
        Map.of());
  }

  public static BrinkProductVariantStock mockStockProduct() {
    return new BrinkProductVariantStock(true, "123654");
  }

  public static BrinkVariantInventory mockStockProductInventory() {
    return new BrinkVariantInventory(55, "998877", "inventory-id-1");
  }

  public static BrinkInventoriesInventories mockStockInventories() {
    return new BrinkInventoriesInventories(List.of(mockStockInventory()));
  }

  public static BrinkInventory mockStockInventory() {
    return new BrinkInventory(
        new BrinkAddress(
            "SE", "Stockholm", "Stockholmsvägen 1", "111 11", "Stockholmsregionen"),
        "Stockholmslagret",
        "The warehouse in Stockholm",
        "inventory-id-1",
          Instant.parse("2022-01-01T01:01:01Z"),
        Instant.parse("2022-05-01T01:01:01Z"));
  }

  public static BrinkStoreGroupMarketInventoryIds mockStockMarketInventoryIds() {
    return new BrinkStoreGroupMarketInventoryIds(
        "store-group-id-1", "SE", List.of("inventory-id-1"));
  }

  public static BrinkStoreGroupMarketInventories mockStockMarket() {
    return new BrinkStoreGroupMarketInventories(
        "stock-market-description-1",
        "stock-market-id-1",
        List.of(
            new BrinkStoreGroupMarketInventory(
                new BrinkStoreGroupMarketInventoryAddress(
                    new BrinkAddress(
                        "Stockholm",
                        "Stockholmsvägen 1",
                        "111 11",
                        "Stockholmsregionen",
                        "Stockholmsregionen"),
                    Instant.parse("2022-03-01T01:01:01Z"),
                    "address-name-1",
                    "address-description-1",
                    "address-id-1",
                    Instant.parse("2022-04-01T01:01:01Z")),
                "Stockholmslagret",
                "The warehouse in Stockholm",
                "inventory-id-1")),
        "SE",
        Instant.parse("2022-01-01T01:01:01Z"),
        "Babyshop",
        Instant.parse("2022-02-01T01:01:01Z"));
  }

  public static BrinkProductParentAddon createProductParentAddonResponse(
      final String productParentId, final String addonId) {
    return new BrinkProductParentAddon(
        true,
        Instant.now(),
        Map.of(),
        "Super addon",
        Map.of(),
        true,
        Map.of(),
        "BABYTAX",
        "www.google.se",
        "Addon 1",
        addonId,
        productParentId,
        Instant.now(),
        Map.of());
  }

  public static BrinkStoreMarket createStoreMarketResponse(final BrinkStoreMarket storeMarket) {
    return getBrinkStoreMarket(storeMarket);
  }

  public static List<BrinkStoreMarket> createStoreMarketResponse(
      final List<BrinkStoreMarket> storeMarket) {
    return storeMarket.stream().map(Mocks::getBrinkStoreMarket).toList();
  }

  public static BrinkStoreGroup createStoreGroupResponse(final BrinkStoreGroup storeGroup) {
    return new BrinkStoreGroup(
        storeGroup.id(), storeGroup.name(), storeGroup.description(), Instant.now(), Instant.now());
  }

  public static BrinkTaxMarket createTaxMarketResponse(final BrinkTaxMarket taxMarket) {
    return getBrinkTaxMarket(taxMarket);
  }

  public static BrinkTaxGroup createTaxGroupResponse(final BrinkTaxGroup taxGroup) {
    return new BrinkTaxGroup(
        taxGroup.name(), taxGroup.description(), taxGroup.id(), Instant.now(), Instant.now());
  }

  public static List<BrinkTaxMarket> createTaxMarketResponse(
      final List<BrinkTaxMarket> taxMarketList) {
    return taxMarketList.stream().map(Mocks::getBrinkTaxMarket).toList();
  }

  public static BrinkProductParent createProductParentResponse(
      final BrinkProductParent productParent) {
    return new BrinkProductParent(
        false,
        Instant.now(),
        productParent.displayNames(),
        productParent.description(),
        productParent.displayDescriptions(),
        productParent.isActive(),
        productParent.imageUrl(),
        productParent.name(),
        productParent.id(),
        Instant.now(),
        productParent.slug(),
        productParent.customAttributes(),
        productParent.tags());
  }

  public static BrinkProductVariant createProductVariantResponse(
      final BrinkProductVariant productVariant) {
    return getBrinkProductVariant(productVariant);
  }

  public static List<BrinkProductVariant> createProductVariantResponse(
      final List<BrinkProductVariant> productVariantList) {
    return productVariantList.stream().map(Mocks::getBrinkProductVariant).toList();
  }

  public static BrinkProductAddon createProductAddonResponse(final BrinkProductAddon productAddon) {
    return getBrinkProductAddon(productAddon);
  }

  public static List<BrinkProductAddon> createProductAddonResponse(
      final List<BrinkProductAddon> productAddonList) {
    return productAddonList.stream().map(Mocks::getBrinkProductAddon).toList();
  }

  public static BrinkProductVariantStock updateStockProductResponse(
      final BrinkProductVariantStock brinkProductVariantStock) {
    return getStockProduct(brinkProductVariantStock);
  }

  public static BrinkVariantInventory updateStockProductInventoryResponse(
      final BrinkVariantInventory brinkVariantInventory) {
    return getStockProductInventory(brinkVariantInventory);
  }

  @Deprecated
  public static BrinkInventory createStockInventoryRequest() {
    return mockStockInventory();
  }

  public static BrinkStoreGroupMarketInventoryIds updateMarketInventoryIdsResponse(
      final BrinkStoreGroupMarketInventoryIds brinkStoreGroupMarketInventoryIds) {
    return new BrinkStoreGroupMarketInventoryIds(
        brinkStoreGroupMarketInventoryIds.storeGroupId(),
        brinkStoreGroupMarketInventoryIds.countryCode(),
        brinkStoreGroupMarketInventoryIds.inventoryIds());
  }

  private static BrinkStoreMarket getBrinkStoreMarket(final BrinkStoreMarket storeMarket) {
    return new BrinkStoreMarket(
        storeMarket.languageCodes(),
        storeMarket.isTaxIncludedInPrice(),
        storeMarket.storeGroupId(),
        storeMarket.countryCode(),
        false,
        Instant.now(),
        "Babyshop",
        "This is Babyshop",
        BrinkCurrencyCode.SEK,
        Instant.now());
  }

  private static BrinkTaxMarket getBrinkTaxMarket(final BrinkTaxMarket taxMarket) {
    return new BrinkTaxMarket(
        taxMarket.countryCode(),
        taxMarket.taxPercentage(),
        taxMarket.taxPercentageDecimals(),
        Instant.now(),
        Instant.now());
  }

  private static BrinkProductVariant getBrinkProductVariant(
      final BrinkProductVariant productVariant) {
    return new BrinkProductVariant(
        productVariant.taxGroupId(),
        productVariant.isArchived(),
        Instant.now(),
        productVariant.displayNames(),
        productVariant.description(),
        productVariant.displayDescriptions(),
        productVariant.isActive(),
        productVariant.imageUrl(),
        productVariant.name(),
        productVariant.id(),
        Instant.now(),
        productVariant.slug(),
        productVariant.productParentId(),
        productVariant.customAttributes(),
        productVariant.tags(),
        productVariant.weight(),
        productVariant.dimensions());
  }

  private static BrinkProductAddon getBrinkProductAddon(final BrinkProductAddon productAddon) {
    return new BrinkProductAddon(
        productAddon.isArchived(),
        Instant.now(),
        productAddon.displayNames(),
        productAddon.description(),
        productAddon.displayDescriptions(),
        productAddon.isActive(),
        productAddon.tags(),
        productAddon.taxGroupId(),
        productAddon.imageUrl(),
        productAddon.name(),
        productAddon.id(),
        Instant.now(),
        productAddon.customAttributes());
  }

  private static BrinkProductVariantStock getStockProduct(
      final BrinkProductVariantStock brinkProductVariantStock) {
    return new BrinkProductVariantStock(
        brinkProductVariantStock.validateStock(), brinkProductVariantStock.productVariantId());
  }

  private static BrinkVariantInventory getStockProductInventory(
      final BrinkVariantInventory brinkVariantInventory) {
    return new BrinkVariantInventory(
        brinkVariantInventory.quantity(),
        brinkVariantInventory.variantId(),
        brinkVariantInventory.inventoryId());
  }
}
