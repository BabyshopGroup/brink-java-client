package com.brinkcommerce.api.shopper.stocks;

import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkException;

public class BrinkStocksException extends BrinkException {

  public BrinkStocksException(final String message, final Exception e, final String requestId) {
    super(message, e, requestId);
  }

  public BrinkStocksException(
      final String message,
      final Exception e,
      final BrinkHttpCode httpCode,
      final String requestId) {
    super(message, e, httpCode, requestId);
  }
}
