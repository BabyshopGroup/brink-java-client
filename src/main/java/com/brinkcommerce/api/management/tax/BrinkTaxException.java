package com.brinkcommerce.api.management.tax;

import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkException;

public class BrinkTaxException extends BrinkException {

  public BrinkTaxException(final String message, final Exception e, final String requestId) {
    super(message, e, requestId);
  }

  public BrinkTaxException(
      final String message,
      final Exception e,
      final BrinkHttpCode httpCode,
      final String requestId) {
    super(message, e, httpCode, requestId);
  }
}
