package com.deadlinezero.game.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class BillingServiceStateTest {
    @Test void legacyImplementationsDefaultToReadyAndNoActiveProduct() {
        BillingService service = new BillingService() {
            @Override public void initialize() {}
            @Override public boolean owns(String productId) { return false; }
            @Override public void purchase(String productId, Runnable onSuccess, Runnable onFailure) {}
            @Override public void restore() {}
        };

        assertEquals(BillingService.State.READY, service.state());
        assertEquals("", service.activeProductId());
    }

    @Test void pendingStateRemainsDistinctFromInProgress() {
        assertEquals("PURCHASE_IN_PROGRESS", BillingService.State.PURCHASE_IN_PROGRESS.name());
        assertEquals("PURCHASE_PENDING", BillingService.State.PURCHASE_PENDING.name());
    }
}
