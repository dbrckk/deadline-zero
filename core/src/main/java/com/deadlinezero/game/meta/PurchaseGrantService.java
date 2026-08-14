package com.deadlinezero.game.meta;

import com.deadlinezero.game.services.BillingService;

/** Applies client-side purchase entitlements exactly once where required. */
public final class PurchaseGrantService {
    private PurchaseGrantService() {}

    public static boolean grant(PlayerProfile profile, String productId) {
        return grant(profile, productId, null);
    }

    public static boolean grant(PlayerProfile profile, String productId, String receiptId) {
        if (profile == null || productId == null) return false;
        if (BillingService.isConsumable(productId) && receiptId != null && !receiptId.isBlank()) {
            if (profile.hasDeliveredPurchaseReceipt(receiptId)) return false;
            boolean granted = grantProduct(profile, productId);
            if (granted) profile.recordDeliveredPurchaseReceipt(receiptId);
            return granted;
        }
        return grantProduct(profile, productId);
    }

    private static boolean grantProduct(PlayerProfile profile, String productId) {
        return switch (productId) {
            case BillingService.REMOVE_ADS -> {
                boolean changed = !profile.removeAdsPurchased;
                profile.removeAdsPurchased = true;
                yield changed;
            }
            case BillingService.STARTER_PACK -> {
                if (profile.starterPackGranted) yield false;
                profile.starterPackGranted = true;
                profile.addCurrency(PlayerProfile.Currency.CREDITS, 5_000);
                profile.addCurrency(PlayerProfile.Currency.GEMS, 250);
                if (!profile.inventory.full()) profile.inventory.add(EquipmentDropTable.roll(Math.max(3, profile.highestStage), true));
                if (!profile.inventory.full()) profile.inventory.add(EquipmentDropTable.roll(Math.max(3, profile.highestStage), true));
                yield true;
            }
            case BillingService.GEMS_SMALL -> {
                profile.addCurrency(PlayerProfile.Currency.GEMS, 250);
                yield true;
            }
            case BillingService.GEMS_LARGE -> {
                profile.addCurrency(PlayerProfile.Currency.GEMS, 1_200);
                yield true;
            }
            default -> false;
        };
    }

    /** Rehydrates permanent purchases and reconciles revocable ad-free entitlement once the store snapshot is authoritative. */
    public static boolean syncPermanent(PlayerProfile profile, BillingService billing) {
        if (profile == null || billing == null) return false;
        boolean changed = false;
        if (billing.owns(BillingService.REMOVE_ADS)) {
            changed |= grant(profile, BillingService.REMOVE_ADS);
        } else if (billing.authoritativeEntitlements() && profile.removeAdsPurchased) {
            profile.removeAdsPurchased = false;
            changed = true;
        }
        if (billing.owns(BillingService.STARTER_PACK)) changed |= grant(profile, BillingService.STARTER_PACK);
        return changed;
    }
}
