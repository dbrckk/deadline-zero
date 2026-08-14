package com.deadlinezero.game.services;

public interface BillingService {
    String REMOVE_ADS = "remove_ads_lifetime";
    String STARTER_PACK = "starter_pack_01";
    String GEMS_SMALL = "gems_250";
    String GEMS_LARGE = "gems_1200";

    record PurchaseReceipt(String productId, String receiptId) {}

    @FunctionalInterface
    interface PurchaseReceiptListener {
        void onPurchased(PurchaseReceipt receipt);
    }

    void initialize();
    boolean owns(String productId);
    void purchase(String productId, Runnable onSuccess, Runnable onFailure);
    void restore();

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

    static boolean isConsumable(String productId) {
        return GEMS_SMALL.equals(productId) || GEMS_LARGE.equals(productId);
    }
}
