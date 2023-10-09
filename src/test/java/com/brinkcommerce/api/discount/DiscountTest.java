package com.brinkcommerce.api.discount;


import com.brinkcommerce.api.management.discount.BrinkDateRange;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DiscountTest {

    @Test
    void shouldFormatDateRangeCorrectly() {

        final Instant from = Instant.parse("2021-01-01T00:00:00.081029563Z");
        final Instant to = Instant.parse("2021-01-01T00:00:00.081029Z");

        final BrinkDateRange dateRange = BrinkDateRange.builder()
            .withFrom(from)
            .withTo(to)
            .build();

        assertEquals("2021-01-01T00:00:00Z", dateRange.from(), "from date should be formatted correctly");
        assertEquals("2021-01-01T00:00:00Z", dateRange.to(), "to date should be formatted correctly");

    }
}
