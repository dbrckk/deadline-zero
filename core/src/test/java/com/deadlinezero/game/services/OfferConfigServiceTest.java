package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

final class OfferConfigServiceTest {
    @Test void defaultsKeepEveryKnownProductAvailable() {
        OfferConfigService.Snapshot snapshot = OfferConfigService.safeDefaults();
        for (String productId : BillingService.PRODUCTS) assertTrue(snapshot.enabled(productId));
        assertTrue(snapshot.featured(BillingService.STARTER_PACK));
    }

    @Test void missingOrFullyInvalidRemoteConfigFallsBackSafely() {
        assertEquals(BillingService.PRODUCTS, OfferConfigService.sanitize(null, null).enabledProducts());
        assertEquals(BillingService.PRODUCTS, OfferConfigService.sanitize(Set.of(), "").enabledProducts());
        assertEquals(BillingService.PRODUCTS,
            OfferConfigService.sanitize(Set.of("unknown_offer"), "unknown_offer").enabledProducts());
    }

    @Test void remoteConfigCanOnlyExposeKnownProducts() {
        OfferConfigService.Snapshot snapshot = OfferConfigService.sanitize(
            Set.of(BillingService.GEMS_SMALL, "not_a_play_product"),
            BillingService.GEMS_SMALL
        );
        assertEquals(Set.of(BillingService.GEMS_SMALL), snapshot.enabledProducts());
        assertTrue(snapshot.enabled(BillingService.GEMS_SMALL));
        assertTrue(snapshot.featured(BillingService.GEMS_SMALL));
        assertFalse(snapshot.enabled("not_a_play_product"));
    }

    @Test void featuredOfferMustAlsoBeEnabled() {
        OfferConfigService.Snapshot snapshot = OfferConfigService.sanitize(
            Set.of(BillingService.GEMS_SMALL),
            BillingService.STARTER_PACK
        );
        assertEquals("", snapshot.featuredProductId());
        assertFalse(snapshot.featured(BillingService.STARTER_PACK));
    }
}
