package com.brinkcommerce.api.management.order.delivery;

import com.brinkcommerce.api.configuration.BrinkHttpCode;
import com.brinkcommerce.api.exception.BrinkException;

public class BrinkDeliveryException extends BrinkException {
    public BrinkDeliveryException(final String message, final Exception e, final String requestId) {
        super(message, e, requestId);
    }

    public BrinkDeliveryException(
            final String message,
            final Exception e,
            final BrinkHttpCode httpCode,
            final String requestId) {
        super(message, e, httpCode, requestId);
    }
}
