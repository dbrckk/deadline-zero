package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import com.deadlinezero.game.services.BillingService;

public final class PurchaseGrantServiceTest {
    @Test public void removeAdsIsPermanentAndIdempotent() {
        PlayerProfile profile = new PlayerProfile();
        assertTrue(PurchaseGrantService.grant(profile, BillingService.REMOVE_ADS));
        assertTrue(profile.removeAdsPurchased);
        assertFalse(PurchaseGrantService.grant(profile, BillingService.REMOVE_ADS));
    }

    @Test public void starterPackPaysExactlyOnce() {
        PlayerProfile profile = new PlayerProfile();
        assertTrue(PurchaseGrantService.grant(profile, BillingService.STARTER_PACK));
        assertEquals(5_000L, profile.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(250L, profile.currency(PlayerProfile.Currency.GEMS));

        assertFalse(PurchaseGrantService.grant(profile, BillingService.STARTER_PACK));
        assertEquals(5_000L, profile.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(250L, profile.currency(PlayerProfile.Currency.GEMS));
    }

    @Test public void gemProductsRemainConsumable() {
        PlayerProfile profile = new PlayerProfile();
        assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL));
        assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL));
        assertEquals(500L, profile.currency(PlayerProfile.Currency.GEMS));

        assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_LARGE));
        assertEquals(1_700L, profile.currency(PlayerProfile.Currency.GEMS));
    }

    @Test public void sameConsumableReceiptCanOnlyBeGrantedOnce() {
        PlayerProfile profile = new PlayerProfile();
        String receipt = "play-token-123";

        assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, receipt));
        assertEquals(250L, profile.currency(PlayerProfile.Currency.GEMS));
        assertTrue(profile.hasDeliveredPurchaseReceipt(receipt));

        assertFalse(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, receipt));
        assertEquals(250L, profile.currency(PlayerProfile.Currency.GEMS));
    }

    @Test public void differentConsumableReceiptsStillStack() {
        PlayerProfile profile = new PlayerProfile();
        assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, "receipt-a"));
        assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, "receipt-b"));
        assertEquals(500L, profile.currency(PlayerProfile.Currency.GEMS));
    }

    @Test public void restoreOnlyRehydratesPermanentEntitlements() {
        PlayerProfile profile = new PlayerProfile();
        FakeBilling billing = new FakeBilling(BillingService.REMOVE_ADS, BillingService.STARTER_PACK, BillingService.GEMS_LARGE);

        assertTrue(PurchaseGrantService.syncPermanent(profile, billing));
        assertTrue(profile.removeAdsPurchased);
        assertTrue(profile.starterPackGranted);
        assertEquals(5_000L, profile.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(250L, profile.currency(PlayerProfile.Currency.GEMS));
        assertFalse(PurchaseGrantService.syncPermanent(profile, billing));
        assertEquals(250L, profile.currency(PlayerProfile.Currency.GEMS));
    }

    @Test public void unknownProductCannotMutateProfile() {
        PlayerProfile profile = new PlayerProfile();
        assertFalse(PurchaseGrantService.grant(profile, "unknown_product"));
        assertEquals(0L, profile.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(0L, profile.currency(PlayerProfile.Currency.GEMS));
    }

    private static final class FakeBilling implements BillingService {
        private final Set<String> owned = new HashSet<>();
        FakeBilling(String... ids) { for (String id : ids) owned.add(id); }
        @Override public void initialize() { }
        @Override public boolean owns(String productId) { return owned.contains(productId); }
        @Override public void purchase(String productId, Runnable onSuccess, Runnable onFailure) { onFailure.run(); }
        @Override public void restore() { }
    }
}
