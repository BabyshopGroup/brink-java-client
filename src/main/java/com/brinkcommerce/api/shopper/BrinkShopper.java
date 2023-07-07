package com.brinkcommerce.api.shopper;

import com.brinkcommerce.api.shopper.prices.ShopperPriceApi;
import com.brinkcommerce.api.shopper.stocks.ShopperStockApi;

public class BrinkShopper {

  private final ShopperStockApi stocksApi;
  private final ShopperPriceApi pricesApi;

  private BrinkShopper(final ShopperConfiguration config) {
    this.stocksApi = ShopperStockApi.init(config);
    this.pricesApi = ShopperPriceApi.init(config);
  }

  public static BrinkShopper init(final ShopperConfiguration config) {
    return new BrinkShopper(config);
  }

  public ShopperStockApi stocks() {
    return this.stocksApi;
  }

  public ShopperPriceApi prices() {
    return this.pricesApi;
  }
}
