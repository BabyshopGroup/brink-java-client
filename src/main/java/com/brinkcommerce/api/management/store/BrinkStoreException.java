package com.brinkcommerce.api.management.store;

import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkException;

public class BrinkStoreException extends BrinkException {
  public BrinkStoreException(final String message, final Exception e, final String requestId) {
    super(message, e, requestId);
  }

  public BrinkStoreException(
      final String message,
      final Exception e,
      final BrinkHttpCode httpCode,
      final String requestId) {
    super(message, e, httpCode, requestId);
  }
}
