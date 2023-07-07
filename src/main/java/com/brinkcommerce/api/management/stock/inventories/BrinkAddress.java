package com.brinkcommerce.api.management.stock.inventories;

public record BrinkAddress(
    String country, String city, String streetAddress, String postalCode, String region) {}
