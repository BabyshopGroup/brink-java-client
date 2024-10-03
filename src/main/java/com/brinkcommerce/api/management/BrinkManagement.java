package com.brinkcommerce.api.management;

import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.discount.BrinkDiscountApi;
import com.brinkcommerce.api.management.order.BrinkOrderApi;
import com.brinkcommerce.api.management.price.BrinkPriceApi;
import com.brinkcommerce.api.management.product.BrinkProductApi;
import com.brinkcommerce.api.management.stock.BrinkStockApi;
import com.brinkcommerce.api.management.store.BrinkStoreApi;
import com.brinkcommerce.api.management.tax.BrinkTaxApi;

public class BrinkManagement {


  private final BrinkProductApi productApi;
  private final BrinkPriceApi priceApi;
  private final BrinkStoreApi storeApi;
  private final BrinkTaxApi taxApi;
  private final BrinkStockApi stockApi;
  private final BrinkDiscountApi discountApi;
  private final BrinkOrderApi orderApi;

  private BrinkManagement(final ManagementConfiguration config) {
    this.productApi = BrinkProductApi.init(config);
    this.priceApi = BrinkPriceApi.init(config);
    this.storeApi = BrinkStoreApi.init(config);
    this.taxApi = BrinkTaxApi.init(config);
    this.stockApi = BrinkStockApi.init(config);
    this.discountApi = BrinkDiscountApi.init(config);
    this.orderApi = BrinkOrderApi.init(config);
  }

  public static BrinkManagement init(final ManagementConfiguration config) {
    return new BrinkManagement(config);
  }

  public BrinkProductApi product() {
    return this.productApi;
  }

  public BrinkPriceApi price() {
    return this.priceApi;
  }

  public BrinkStoreApi store() {
    return this.storeApi;
  }

  public BrinkTaxApi tax() {
    return this.taxApi;
  }

  public BrinkStockApi stock() {
    return this.stockApi;
  }

  public BrinkDiscountApi discount() {
    return this.discountApi;
  }

  public BrinkOrderApi order() {
    return this.orderApi;
  }

}
