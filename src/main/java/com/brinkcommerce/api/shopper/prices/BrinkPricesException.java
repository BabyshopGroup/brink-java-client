package com.brinkcommerce.api.shopper.prices;

import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkException;

public class BrinkPricesException extends BrinkException {

  public BrinkPricesException(final String message, final Exception e, final String requestId) {
    super(message, e, requestId);
  }

  public BrinkPricesException(
      final String message,
      final Exception e,
      final BrinkHttpCode httpCode,
      final String requestId) {
    super(message, e, httpCode, requestId);
  }
}
