package com.brinkcommerce.api.management.discount;

import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkException;

public class BrinkDiscountException extends BrinkException {

    public BrinkDiscountException(final String message, final Exception e, final String requestId) {
        super(message, e, requestId);
    }

    public BrinkDiscountException(
            final String message,
            final Exception e,
            final BrinkHttpCode httpCode,
            final String requestId) {
        super(message, e, httpCode, requestId);
    }
}

