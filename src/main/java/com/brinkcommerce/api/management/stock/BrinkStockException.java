package com.brinkcommerce.api.management.stock;

import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkException;

public class BrinkStockException extends BrinkException {
  public BrinkStockException(final String message, final Exception e, final String requestId) {
    super(message, e, requestId);
  }

  public BrinkStockException(
      final String message,
      final Exception e,
      final BrinkHttpCode httpCode,
      final String requestId) {
    super(message, e, httpCode, requestId);
  }
}
