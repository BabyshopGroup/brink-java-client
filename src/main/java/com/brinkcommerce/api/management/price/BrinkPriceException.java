package com.brinkcommerce.api.management.price;

import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkException;

public class BrinkPriceException extends BrinkException {

  public BrinkPriceException(final String message, final Exception e, final String requestId) {
    super(message, e, requestId);
  }

  public BrinkPriceException(
      final String message,
      final Exception e,
      final BrinkHttpCode httpCode,
      final String requestId) {
    super(message, e, httpCode, requestId);
  }
}
