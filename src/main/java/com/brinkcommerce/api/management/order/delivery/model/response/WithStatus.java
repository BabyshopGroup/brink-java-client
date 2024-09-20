package com.brinkcommerce.api.management.order.delivery.model.response;

import java.util.List;

public record WithStatus<T>(
        T value,
        String current,
        List<History> history
) {
}