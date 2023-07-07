package com.brinkcommerce.api.management.product;

import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkException;

public class BrinkProductException extends BrinkException {

  public BrinkProductException(final String message, final Exception e, final String requestId) {
    super(message, e, requestId);
  }

  public BrinkProductException(
      final String message,
      final Exception e,
      final BrinkHttpCode httpCode,
      final String requestId) {
    super(message, e, httpCode, requestId);
  }
}
