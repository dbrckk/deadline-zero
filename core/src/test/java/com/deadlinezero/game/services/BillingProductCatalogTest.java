package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

final class BillingProductCatalogTest {
    @Test void catalogContainsExactlyTheExpectedProducts() {
        assertEquals(Set.of(
            BillingService.REMOVE_ADS,
            BillingService.STARTER_PACK,
            BillingService.GEMS_SMALL,
            BillingService.GEMS_LARGE
        ), BillingService.PRODUCTS);
    }

    @Test void productIdsArePlaySafeAndUnique() {
        assertEquals(4, BillingService.PRODUCTS.size());
        for (String id : BillingService.PRODUCTS) {
            assertTrue(id.matches("[a-z0-9_]{3,64}"), () -> "Invalid Play product id: " + id);
            assertFalse(id.startsWith("test_"), () -> "Test product leaked into production catalog: " + id);
            assertFalse(id.contains("placeholder"), () -> "Placeholder product leaked into production catalog: " + id);
        }
    }

    @Test void durableAndConsumableClassificationIsCompleteAndExclusive() {
        for (String id : BillingService.PRODUCTS) {
            assertTrue(BillingService.isDurable(id) ^ BillingService.isConsumable(id),
                () -> "Product must be exactly one of durable or consumable: " + id);
            assertTrue(BillingService.isKnownProduct(id));
        }
        assertFalse(BillingService.isKnownProduct("unknown_product"));
        assertFalse(BillingService.isDurable("unknown_product"));
        assertFalse(BillingService.isConsumable("unknown_product"));
    }
}
