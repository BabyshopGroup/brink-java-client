package com.brinkcommerce.api.shopper.prices;

import java.time.Instant;

public record BrinkLowestPrice(Long salePriceAmount, Instant endDate) {
}
