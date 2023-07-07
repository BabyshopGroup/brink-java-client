package com.brinkcommerce.api;

import com.brinkcommerce.api.configuration.ManagementConfiguration;
import com.brinkcommerce.api.management.BrinkManagement;
import com.brinkcommerce.api.shopper.BrinkShopper;
import com.brinkcommerce.api.shopper.ShopperConfiguration;

public class BrinkCommerce {

  public static BrinkManagement management(final ManagementConfiguration configuration) {
    return BrinkManagement.init(configuration);
  }

  public static BrinkShopper shopper(final ShopperConfiguration configuration) {
    return BrinkShopper.init(configuration);
  }

}
