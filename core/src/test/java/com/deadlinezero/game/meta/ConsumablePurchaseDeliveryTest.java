package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.services.BillingService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

final class ConsumablePurchaseDeliveryTest {
    @Test void grantIsPersistedBeforePlayConsumption() {
        PlayerProfile profile = new PlayerProfile();
        RecordingBilling billing = new RecordingBilling();
        List<String> events = new ArrayList<>();
        BillingService.PurchaseReceipt receipt = new BillingService.PurchaseReceipt(BillingService.GEMS_SMALL, "token-1");

        boolean granted = ConsumablePurchaseDelivery.deliver(profile, billing, receipt,
            () -> {
                assertEquals(250L, profile.currency(PlayerProfile.Currency.GEMS));
                assertTrue(profile.hasDeliveredPurchaseReceipt("token-1"));
                events.add("persist");
            },
            firstDelivery -> {
                assertTrue(firstDelivery);
                events.add("finalized");
            },
            () -> events.add("failure"));

        assertTrue(granted);
        assertEquals(List.of("persist", "consume", "finalized"), events);
    }

    @Test void replayedReceiptDoesNotDuplicateCurrencyButIsStillFinalized() {
        PlayerProfile profile = new PlayerProfile();
        RecordingBilling billing = new RecordingBilling();
        BillingService.PurchaseReceipt receipt = new BillingService.PurchaseReceipt(BillingService.GEMS_LARGE, "token-replay");

        assertTrue(ConsumablePurchaseDelivery.deliver(profile, billing, receipt, () -> {}, ignored -> {}, () -> {}));
        assertEquals(1_200L, profile.currency(PlayerProfile.Currency.GEMS));

        List<String> events = new ArrayList<>();
        billing.events = events;
        boolean grantedAgain = ConsumablePurchaseDelivery.deliver(profile, billing, receipt,
            () -> events.add("persist"),
            firstDelivery -> {
                assertFalse(firstDelivery);
                events.add("finalized");
            },
            () -> events.add("failure"));

        assertFalse(grantedAgain);
        assertEquals(1_200L, profile.currency(PlayerProfile.Currency.GEMS));
        assertEquals(List.of("persist", "consume", "finalized"), events);
    }

    @Test void invalidReceiptNeverMutatesPersistsOrConsumes() {
        PlayerProfile profile = new PlayerProfile();
        RecordingBilling billing = new RecordingBilling();
        List<String> events = new ArrayList<>();
        billing.events = events;

        assertFalse(ConsumablePurchaseDelivery.deliver(profile, billing,
            new BillingService.PurchaseReceipt(BillingService.GEMS_SMALL, ""),
            () -> events.add("persist"), ignored -> events.add("finalized"), () -> events.add("failure")));

        assertEquals(0L, profile.currency(PlayerProfile.Currency.GEMS));
        assertTrue(events.isEmpty());
    }

    private static final class RecordingBilling implements BillingService {
        List<String> events = new ArrayList<>();

        @Override public void initialize() {}
        @Override public boolean owns(String productId) { return false; }
        @Override public void purchase(String productId, Runnable onSuccess, Runnable onFailure) { onFailure.run(); }
        @Override public void restore() {}
        @Override public void finishConsumable(String receiptId, Runnable onSuccess, Runnable onFailure) {
            events.add("consume");
            onSuccess.run();
        }
    }
}
