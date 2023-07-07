package com.brinkcommerce.api.exception;

import com.brinkcommerce.api.configuration.BrinkHttpCode;

public class BrinkIntegrationException extends RuntimeException implements BrinkExceptionInt {

  private final String requestId;
  private final BrinkHttpCode brinkHttpCode;

  public BrinkIntegrationException(
      final String message, final String requestId, final BrinkHttpCode brinkHttpCode) {
    super(message);
    this.requestId = requestId;
    this.brinkHttpCode = brinkHttpCode;
  }

  @Override
  public String requestId() {
    return requestId;
  }

  public BrinkHttpCode brinkHttpCode() {
    return brinkHttpCode;
  }
}
