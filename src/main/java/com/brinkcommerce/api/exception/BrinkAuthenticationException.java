package com.brinkcommerce.api.exception;

public class BrinkAuthenticationException extends RuntimeException {

  public BrinkAuthenticationException(final String message) {
    super(message);
  }

  public BrinkAuthenticationException(final String message, final Exception e) {
    super(message, e);
  }
}
