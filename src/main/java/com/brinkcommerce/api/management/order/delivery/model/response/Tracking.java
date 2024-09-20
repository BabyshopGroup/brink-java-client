package com.brinkcommerce.api.management.order.delivery.model.response;

public record Tracking(
        String reference,
        String url,
        String shippingMethod,
        String shippingCompany
) {}
