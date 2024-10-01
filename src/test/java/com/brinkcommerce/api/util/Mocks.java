package com.brinkcommerce.api.util;

import com.brinkcommerce.api.management.order.delivery.model.request.Gift;
import com.brinkcommerce.api.management.order.delivery.model.request.GiftCardProduct;
import com.brinkcommerce.api.management.order.delivery.model.request.OrderLine;
import com.brinkcommerce.api.management.order.delivery.model.request.*;
import com.brinkcommerce.api.management.order.delivery.model.response.Bonus;
import com.brinkcommerce.api.management.order.delivery.model.response.GiftCard;
import com.brinkcommerce.api.management.order.delivery.model.response.*;
import com.brinkcommerce.api.management.price.addon.BrinkPriceAddon;
import com.brinkcommerce.api.management.price.addon.BrinkPriceAddonRequest;
import com.brinkcommerce.api.management.price.variant.model.*;
import com.brinkcommerce.api.management.product.addon.BrinkProductAddon;
import com.brinkcommerce.api.management.product.parent.BrinkProductParent;
import com.brinkcommerce.api.management.product.parent.BrinkProductParentAddon;
import com.brinkcommerce.api.management.product.variant.BrinkDimensions;
import com.brinkcommerce.api.management.product.variant.BrinkProductVariant;
import com.brinkcommerce.api.management.stock.inventories.BrinkAddress;
import com.brinkcommerce.api.management.stock.inventories.BrinkInventoriesInventories;
import com.brinkcommerce.api.management.stock.inventories.BrinkInventory;
import com.brinkcommerce.api.management.stock.productvariants.BrinkProductVariantStock;
import com.brinkcommerce.api.management.stock.productvariants.BrinkVariantInventory;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventories;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventory;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventoryAddress;
import com.brinkcommerce.api.management.stock.storegroups.BrinkStoreGroupMarketInventoryIds;
import com.brinkcommerce.api.management.store.BrinkCountryCode;
import com.brinkcommerce.api.management.store.BrinkCurrencyCode;
import com.brinkcommerce.api.management.store.BrinkLanguageCode;
import com.brinkcommerce.api.management.store.market.BrinkStoreMarket;
import com.brinkcommerce.api.management.store.storegroup.BrinkStoreGroup;
import com.brinkcommerce.api.management.tax.market.BrinkTaxMarket;
import com.brinkcommerce.api.management.tax.taxgroup.BrinkTaxGroup;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mocks {

    public static BrinkDeliveryPostRequest mockOrderDeliveryPostRequest() {
        return BrinkDeliveryPostRequest.builder()
                .withGiftCardProducts(List.of(
                        GiftCardProduct.builder().withGiftCardProductId("gift-card-product-id-1").build(),
                        GiftCardProduct.builder().withGiftCardProductId("gift-card-product-id-2").build()
                ))
                .withOrderLines(
                        List.of(
                                OrderLine.builder().withOrderLineId("order-line-id-1").withQuantity(1).build(),
                                OrderLine.builder().withOrderLineId("order-line-id-2").withQuantity(2).build()
                        ))
                .withShippingFees(
                        List.of("shipping-fee-1", "shipping-fee-2")
                )
                .withGifts(
                        List.of(
                                Gift.builder().withGiftId("gift-id-1").withQuantity(1).build(),
                                Gift.builder().withGiftId("gift-id-2").withQuantity(2).build()
                        )
                )
                .build();
    }

    public static BrinkDeliveryPostResponse mockOrderDeliveryPostResponse() {
        return new BrinkDeliveryPostResponse(
                "delivery-id-1",
                "order-id-1",
                "order-reference-1",
                new PaymentProvider("payment-provider-name-1", "payment-provider-id-1"),
                new ShippingProvider("shipping-provider-name-1", "shipping-provider-id-1"),
                new PromotionProvider("promotion-provider-name-1", "promotion-provider-id-1"),
                new GiftCardProvider("gift-card-provider-name-1", "gift-card-provider-id-1"),
                new GiftCardProductProvider("gift-card-product-provider-name-1", "gift-card-product-provider-id-1"),
                new VoucherProvider("voucher-provider-name-1", "voucher-provider-id-1"),
                new BonusProvider("bonus-provider-name-1", "bonus-provider-id-1"),
                List.of(
                        new com.brinkcommerce.api.management.order.delivery.model.response.OrderLine(
                                "order-line-id-1",
                                1,
                                0L,
                                3,
                                "SEK",
                                0L,
                                0L,
                                0L
                        )
                ),
                List.of(
                        new ShippingFee(
                                "shipping-fee-1",
                                0L,
                                3,
                                "SEK",
                                0L,
                                0L,
                                0L
                        )
                ),
                List.of(
                        new com.brinkcommerce.api.management.order.delivery.model.response.Gift("gift-id-1", 1)
                ),
                List.of(
                        new GiftCard(
                                "gift-card-id-1",
                                0L,
                                "SEK",
                                "unknown string",
                                "unknown string",
                                "unknown string"
                        )
                ),
                List.of(
                        new com.brinkcommerce.api.management.order.delivery.model.response.GiftCardProduct(
                                "gift-card-product-id-1",
                                "SEK",
                                0L,
                                "unknown string",
                                "unknown string",
                                "unknown string",
                                "create-id-1"
                        )
                ),
                List.of(
                        new Voucher(
                                "voucher-id-1",
                                10L,
                                "SEK"
                        )
                ),
                new Bonus(
                        "bonus-id-1",
                        "reservation-id-1",
                        100L,
                        "SEK"
                ),
                new Tracking(
                        "tracking-id-1",
                        "tracking-url-1",
                        "shipping-method-1",
                        "shipping-company-1"
                ),
                new CapturedPayment(
                        "captured-payment-reference-1"
                ),
                new Totals(
                        100L,
                        0L,
                        0L,
                        0L,
                        0L,
                        0L
                ),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                1,
                Instant.now(),
                Instant.now(),
                1
        );
    }

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

    public static BrinkPriceVariantPutRequest mockPriceVariantPutRequest() {
        return BrinkPriceVariantPutRequest.builder()
                .withProductVariantId("123654_100")
                .withProductVariantPrices(List.of(BrinkPriceVariantWithCountry.builder()
                        .withCountryCode(BrinkCountryCode.SE)
                        .withSalePriceAmount(100L)
                        .withBasePriceAmount(80L)
                        .withReferencePriceAmount(80L)
                        .build()))
                .withStoreGroupId("BABYSHOP")
                .build();
    }

    public static BrinkPriceVariantPatchRequest mockPriceVariantPatchRequest() {
        return BrinkPriceVariantPatchRequest.builder()
                .withProductVariantId("123654_100")
                .withStoreGroupId("BABYSHOP")
                .withProductVariantPrices(Map.of(
                        BrinkCountryCode.SE,
                        BrinkPriceVariant.builder()
                                .withSalePriceAmount(100L)
                                .withBasePriceAmount(90L)
                                .withReferencePriceAmount(80L)
                                .build())
                ).build();
    }

    public static BrinkPriceVariantPatchRequest mockEmptyPriceVariantPatchRequest() {
        Map<BrinkCountryCode, BrinkPriceVariant> myMap = new HashMap<>();
        myMap.put(BrinkCountryCode.SE, null);
        myMap.put(BrinkCountryCode.NO, null);
        return BrinkPriceVariantPatchRequest.builder()
                .withProductVariantId("123654_100")
                .withStoreGroupId("BABYSHOP")
                .withProductVariantPrices(myMap).build();
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
                        null,
                        80L))
        );
    }

    public static BrinkDeliveryGetRequest mockOrderDeliveryGetRequest() {
        return BrinkDeliveryGetRequest.builder()
                .withDeliveryId("delivery-id-1")
                .build();
    }

    public static BrinkDeliveryStartRequest mockOrderDeliveryStartRequest() {
        return BrinkDeliveryStartRequest.builder()
                .withBonus(com.brinkcommerce.api.management.order.delivery.model.request.Bonus.builder().withActionType(ActionType.AUTO).build())
                .withGiftCard(com.brinkcommerce.api.management.order.delivery.model.request.GiftCard.builder().withActionType(ActionType.AUTO).build())
                .withPayment(com.brinkcommerce.api.management.order.delivery.model.request.Payment.builder().withActionType(ActionType.AUTO).build())
                .withShipping(com.brinkcommerce.api.management.order.delivery.model.request.Shipping.builder().withActionType(ActionType.AUTO).build())
                .build();
    }

    public static BrinkDeliveryGetResponse mockOrderDeliveryGetResponse() {
        return new BrinkDeliveryGetResponse(
                "delivery-id-1",
                "order-id-1",
                "order-reference-1",

                List.of(
                        ShippingProvider.withStatus(
                                new ShippingProvider("shipping-provider-name-1", "shipping-provider-id-1"),
                                "history-id-1",
                                List.of(new History("history-id-1", "status-1", "message-1", "error-mesage", Instant.now()))
                        )
                ),
                List.of(PaymentProvider.withStatus(
                        new PaymentProvider(
                                "payment-provider-name-1",
                                "payment-provider-id-1"
                        ),
                        "history-id-1",
                        List.of(new History("history-id-1", "status-1", "message-1", "error-mesage", Instant.now()))
                )),
                List.of(
                        VoucherProvider.withStatus(
                                new VoucherProvider("voucher-provider-name-1", "voucher-provider-id-1"),
                                "history-id-1",
                                List.of(new History("history-id-1", "status-1", "message-1", "error-mesage", Instant.now()))
                        )
                ),
                List.of(
                        GiftCardProvider.withStatus(
                                new GiftCardProvider("gift-card-provider-name-1", "gift-card-provider-id-1"),
                                "history-id-1",
                                List.of(new History("history-id-1", "status-1", "message-1", "error-mesage", Instant.now()))
                        )
                ),
                List.of(
                        GiftCardProductProvider.withStatus(
                                new GiftCardProductProvider("gift-card-product-provider-name-1", "gift-card-product-provider-id-1"),
                                "history-id-1",
                                List.of(new History("history-id-1", "status-1", "message-1", "error-mesage", Instant.now()))
                        )
                ),
                List.of(
                        PromotionProvider.withStatus(
                                new PromotionProvider("promotion-provider-name-1", "promotion-provider-id-1"),
                                "history-id-1",
                                List.of(new History("history-id-1", "status-1", "message-1", "error-mesage", Instant.now()))
                        )
                ),
                List.of(
                        BonusProvider.withStatus(
                                new BonusProvider("bonus-provider-name-1", "bonus-provider-id-1"),
                                "history-id-1",
                                List.of(new History("history-id-1", "status-1", "message-1", "error-mesage", Instant.now()))
                        )
                ),

                List.of(
                        new com.brinkcommerce.api.management.order.delivery.model.response.OrderLine(
                                "order-line-id-1",
                                1,
                                0L,
                                3,
                                "SEK",
                                0L,
                                0L,
                                0L
                        )
                ),
                List.of(
                        new ShippingFee(
                                "shipping-fee-1",
                                0L,
                                3,
                                "SEK",
                                0L,
                                0L,
                                0L
                        )
                ),
                List.of(
                        new com.brinkcommerce.api.management.order.delivery.model.response.Gift("gift-id-1", 1)
                ),
                List.of(
                        new GiftCard(
                                "gift-card-id-1",
                                0L,
                                "SEK",
                                "unknown string",
                                "unknown string",
                                "unknown string"
                        )
                ),
                List.of(
                        new com.brinkcommerce.api.management.order.delivery.model.response.GiftCardProduct(
                                "gift-card-product-id-1",
                                "SEK",
                                0L,
                                "unknown string",
                                "unknown string",
                                "unknown string",
                                "create-id-1"
                        )
                ),
                List.of(
                        new Voucher(
                                "voucher-id-1",
                                10L,
                                "SEK"
                        )
                ),

                List.of(new Bonus(
                        "bonus-id-1",
                        "reservation-id-1",
                        100L,
                        "SEK"
                )),
                List.of(new Tracking(
                        "tracking-id-1",
                        "tracking-url-1",
                        "shipping-method-1",
                        "shipping-company-1"
                )),
                List.of(new CapturedPayment(
                        "captured-payment-reference-1"
                )),
                List.of(new Totals(
                        100L,
                        0L,
                        0L,
                        0L,
                        0L,
                        0L
                )),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                Instant.now(),
                1,
                Instant.now(),
                Instant.now(),
                1
        );
    }

    public static BrinkPriceVariantListResponse mockEmptyPriceVariantListResponse() {
        return new BrinkPriceVariantListResponse(List.of());
    }

    public static BrinkPriceVariantRequest getPriceVariantRequest() {
        return new BrinkPriceVariantRequest("BABYSHOP", "123654_100");
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
                .withDimensions(new BrinkDimensions(55, 55, 55))
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
                productVariant.dimensions(),
                productVariant.shippingAttributes());
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
