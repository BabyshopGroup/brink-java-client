package com.brinkcommerce.api.exception;

import com.brinkcommerce.api.configuration.BrinkHttpCode;

public class BrinkException extends RuntimeException implements BrinkExceptionInt {

  public final BrinkHttpCode httpCode;
  public final String requestId;

  public BrinkException(final String message) {
    super(message);
    this.httpCode = null;
    this.requestId = null;
  }

  public BrinkException(final String message, final Exception e) {
    super(message, e);
    this.httpCode = null;
    this.requestId = null;
  }

  public BrinkException(final String message, final Exception e, final BrinkHttpCode httpCode) {
    super(String.format("%s. Http code: %d", message, httpCode.statusCode), e);
    this.httpCode = httpCode;
    this.requestId = null;
  }

  public BrinkException(final String message, final Exception e, final String requestId) {
    super(message, e);
    this.httpCode = null;
    this.requestId = requestId;
  }

  public BrinkException(
      final String message,
      final Exception e,
      final BrinkHttpCode httpCode,
      final String requestId) {
    super(String.format("%s. Http code: %d", message, httpCode.statusCode), e);
    this.httpCode = httpCode;
    this.requestId = requestId;
  }

  @Override
  public String requestId() {
    return this.requestId;
  }
}
