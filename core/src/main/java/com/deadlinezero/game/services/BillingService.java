package com.deadlinezero.game.services;

import java.util.Set;

public interface BillingService {
    String REMOVE_ADS = "remove_ads_lifetime";
    String STARTER_PACK = "starter_pack_01";
    String GEMS_SMALL = "gems_250";
    String GEMS_LARGE = "gems_1200";

    Set<String> PRODUCTS = Set.of(REMOVE_ADS, STARTER_PACK, GEMS_SMALL, GEMS_LARGE);

    enum State {
        UNAVAILABLE,
        CONNECTING,
        READY,
        PURCHASE_IN_PROGRESS,
        PURCHASE_PENDING
    }

    record PurchaseReceipt(String productId, String receiptId) {}

    @FunctionalInterface
    interface PurchaseReceiptListener {
        void onPurchased(PurchaseReceipt receipt);
    }

    void initialize();
    boolean owns(String productId);
    void purchase(String productId, Runnable onSuccess, Runnable onFailure);
    void restore();

    /** Current store lifecycle state. Implementations without a platform store remain READY/no-op. */
    default State state() { return State.READY; }

    /** True only when owns() reflects a completed authoritative store query. */
    default boolean authoritativeEntitlements() { return false; }

    /** Product associated with an active or pending purchase, or an empty string when none exists. */
    default String activeProductId() { return ""; }

    /**
     * Receipt-aware purchase path used by consumables so the profile can persist an idempotency key
     * before Google Play consumption. Implementations that do not support receipts fall back to the
     * legacy purchase contract, which keeps desktop/no-op implementations source compatible.
     */
    default void purchaseWithReceipt(String productId, PurchaseReceiptListener onSuccess, Runnable onFailure) {
        purchase(productId, () -> onSuccess.onPurchased(new PurchaseReceipt(productId, "")), onFailure);
    }

    /** Replays purchased but not yet consumed items. */
    default void restoreConsumables(PurchaseReceiptListener listener) {}

    /** Consumes a previously delivered Play purchase only after the profile grant has been persisted. */
    default void finishConsumable(String receiptId, Runnable onSuccess, Runnable onFailure) { onSuccess.run(); }

    static boolean isKnownProduct(String productId) {
        return productId != null && PRODUCTS.contains(productId);
    }

    static boolean isConsumable(String productId) {
        return GEMS_SMALL.equals(productId) || GEMS_LARGE.equals(productId);
    }

    static boolean isDurable(String productId) {
        return REMOVE_ADS.equals(productId) || STARTER_PACK.equals(productId);
    }
}
