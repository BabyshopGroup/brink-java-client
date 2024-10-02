package com.brinkcommerce.api.management.order.delivery.model.response;


import java.time.Instant;

public record History(
        String type,
        String status,
        String message,
        String errorMessage,
        Instant timestamp
) {
}