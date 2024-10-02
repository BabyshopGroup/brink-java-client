package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

public record ShippingProvider(
        String carrierName,
        String providerId
) {
}
