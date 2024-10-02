package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

public record ProviderStatus(
        String current,
        List<History> history
) {
}