package com.deadlinezero.game.android;

import android.app.Activity;
import com.android.billingclient.api.*;
import com.deadlinezero.game.services.BillingService;
import com.deadlinezero.game.services.SingleFlightGate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** Client-side Play Billing bridge with crash-safe consumable delivery and explicit pending-state handling. */
public final class AndroidBillingService implements BillingService, PurchasesUpdatedListener {
    private final Activity activity;
    private BillingClient client;
    private final Set<String> owned = ConcurrentHashMap.newKeySet();
    private final SingleFlightGate purchaseGate = new SingleFlightGate();
    private Runnable success, failure;
    private PurchaseReceiptListener receiptSuccess;
    private PurchaseReceiptListener restoreReceiptListener;
    private boolean receiptAwarePending;
    private volatile State billingState = State.UNAVAILABLE;
    private volatile String pendingProductId;
    private volatile boolean entitlementSnapshotAuthoritative;

    public AndroidBillingService(Activity activity) { this.activity = activity; }

    @Override public void initialize() {
        billingState = State.CONNECTING;
        entitlementSnapshotAuthoritative = false;
        client = BillingClient.newBuilder(activity)
            .setListener(this)
            .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
            .enableAutoServiceReconnection()
            .build();
        client.startConnection(new BillingClientStateListener() {
            @Override public void onBillingSetupFinished(BillingResult result) {
                if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    billingState = State.READY;
                    restore();
                    PurchaseReceiptListener deferred = restoreReceiptListener;
                    if (deferred != null) restoreConsumables(deferred);
                } else {
                    billingState = State.UNAVAILABLE;
                    entitlementSnapshotAuthoritative = false;
                }
            }
            @Override public void onBillingServiceDisconnected() {
                billingState = State.CONNECTING;
                entitlementSnapshotAuthoritative = false;
            }
        });
    }

    @Override public State state() { return billingState; }
    @Override public boolean authoritativeEntitlements() { return entitlementSnapshotAuthoritative; }
    @Override public String activeProductId() { return pendingProductId == null ? "" : pendingProductId; }
    @Override public boolean owns(String id) { return owned.contains(id); }

    @Override public void restore() {
        if (client == null || !client.isReady()) return;
        entitlementSnapshotAuthoritative = false;
        client.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build(),
            (result, purchases) -> {
                if (result.getResponseCode() != BillingClient.BillingResponseCode.OK) return;
                owned.clear();
                boolean foundPending = false;
                for (Purchase purchase : purchases) {
                    if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                        foundPending = true;
                        if (!purchase.getProducts().isEmpty()) pendingProductId = purchase.getProducts().get(0);
                        continue;
                    }
                    if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED) continue;
                    if (!containsConsumable(purchase)) processPurchase(purchase, false);
                }
                entitlementSnapshotAuthoritative = true;
                if (foundPending) billingState = State.PURCHASE_PENDING;
                else if (!purchaseGate.active()) {
                    billingState = State.READY;
                    pendingProductId = null;
                }
            });
    }

    @Override public void restoreConsumables(PurchaseReceiptListener listener) {
        if (listener == null) return;
        restoreReceiptListener = listener;
        if (client == null || !client.isReady()) return;
        client.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build(),
            (result, purchases) -> {
                if (result.getResponseCode() != BillingClient.BillingResponseCode.OK) return;
                for (Purchase purchase : purchases) {
                    if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                        billingState = State.PURCHASE_PENDING;
                        if (!purchase.getProducts().isEmpty()) pendingProductId = purchase.getProducts().get(0);
                        continue;
                    }
                    if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED || !containsConsumable(purchase)) continue;
                    for (String id : purchase.getProducts()) {
                        if (BillingService.isConsumable(id)) {
                            listener.onPurchased(new PurchaseReceipt(id, purchase.getPurchaseToken()));
                            break;
                        }
                    }
                }
            });
    }

    @Override public void purchase(String productId, Runnable onSuccess, Runnable onFailure) {
        beginPurchase(productId, null, onSuccess, onFailure, false);
    }

    @Override public void purchaseWithReceipt(String productId, PurchaseReceiptListener onSuccess, Runnable onFailure) {
        beginPurchase(productId, onSuccess, null, onFailure, true);
    }

    private void beginPurchase(String productId, PurchaseReceiptListener receiptCallback, Runnable legacySuccess,
                               Runnable onFailure, boolean receiptAware) {
        if (productId == null || productId.isBlank() || client == null || !client.isReady()
            || billingState == State.PURCHASE_PENDING) {
            if (onFailure != null) onFailure.run();
            return;
        }
        if (!purchaseGate.tryBegin()) {
            if (onFailure != null) onFailure.run();
            return;
        }

        this.success = legacySuccess;
        this.receiptSuccess = receiptCallback;
        this.failure = onFailure;
        this.receiptAwarePending = receiptAware;
        this.pendingProductId = productId;
        this.billingState = State.PURCHASE_IN_PROGRESS;

        QueryProductDetailsParams.Product product = QueryProductDetailsParams.Product.newBuilder()
            .setProductId(productId)
            .setProductType(BillingClient.ProductType.INAPP)
            .build();

        client.queryProductDetailsAsync(
            QueryProductDetailsParams.newBuilder().setProductList(Collections.singletonList(product)).build(),
            (result, detailsResult) -> {
                if (result.getResponseCode() != BillingClient.BillingResponseCode.OK || detailsResult.getProductDetailsList().isEmpty()) {
                    failPending();
                    return;
                }
                ProductDetails details = detailsResult.getProductDetailsList().get(0);
                BillingFlowParams.ProductDetailsParams params = BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(details)
                    .build();
                BillingResult launch = client.launchBillingFlow(activity,
                    BillingFlowParams.newBuilder().setProductDetailsParamsList(Collections.singletonList(params)).build());
                if (launch.getResponseCode() != BillingClient.BillingResponseCode.OK) failPending();
            });
    }

    @Override public void onPurchasesUpdated(BillingResult result, List<Purchase> purchases) {
        if (result.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            boolean handled = false;
            boolean pending = false;
            for (Purchase purchase : purchases) {
                if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                    if (pendingProductId == null || purchase.getProducts().contains(pendingProductId)) {
                        pending = true;
                        handled = true;
                    }
                    continue;
                }
                handled |= processPurchase(purchase, true);
            }
            if (pending) {
                billingState = State.PURCHASE_PENDING;
                return;
            }
            if (!handled) failPending();
        } else {
            failPending();
        }
    }

    private boolean processPurchase(Purchase purchase, boolean notifyPending) {
        if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED) return false;
        if (notifyPending && pendingProductId != null && !purchase.getProducts().contains(pendingProductId)) return false;

        String consumableId = firstConsumable(purchase);
        if (consumableId != null) {
            if (!notifyPending) return true;
            PurchaseReceipt receipt = new PurchaseReceipt(consumableId, purchase.getPurchaseToken());
            if (receiptAwarePending) {
                PurchaseReceiptListener callback = receiptSuccess;
                clearPending();
                if (callback != null) callback.onPurchased(receipt);
            } else if (!purchaseGate.active()) {
                PurchaseReceiptListener callback = restoreReceiptListener;
                billingState = State.READY;
                pendingProductId = null;
                if (callback != null) callback.onPurchased(receipt);
            } else {
                finishConsumable(purchase.getPurchaseToken(), this::succeedPending, this::failPending);
            }
            return true;
        }

        if (purchase.isAcknowledged()) {
            grantDurableProducts(purchase);
            if (notifyPending) succeedPending();
            return true;
        }

        AcknowledgePurchaseParams params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.getPurchaseToken()).build();
        client.acknowledgePurchase(params, result -> {
            if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                grantDurableProducts(purchase);
                if (notifyPending) succeedPending();
            } else if (notifyPending) {
                failPending();
            }
        });
        return true;
    }

    @Override public void finishConsumable(String receiptId, Runnable onSuccess, Runnable onFailure) {
        if (receiptId == null || receiptId.isBlank() || client == null || !client.isReady()) {
            if (onFailure != null) onFailure.run();
            return;
        }
        ConsumeParams params = ConsumeParams.newBuilder().setPurchaseToken(receiptId).build();
        client.consumeAsync(params, (result, token) -> {
            if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                if (onSuccess != null) onSuccess.run();
            } else if (onFailure != null) {
                onFailure.run();
            }
        });
    }

    private boolean containsConsumable(Purchase purchase) { return firstConsumable(purchase) != null; }

    private String firstConsumable(Purchase purchase) {
        for (String id : purchase.getProducts()) if (BillingService.isConsumable(id)) return id;
        return null;
    }

    private void grantDurableProducts(Purchase purchase) {
        for (String id : purchase.getProducts()) {
            if (!BillingService.isConsumable(id)) owned.add(id);
        }
    }

    private void succeedPending() {
        Runnable callback = success;
        clearPending();
        if (callback != null) callback.run();
    }

    private void failPending() {
        Runnable callback = failure;
        clearPending();
        if (callback != null) callback.run();
    }

    private void clearPending() {
        success = null;
        receiptSuccess = null;
        failure = null;
        receiptAwarePending = false;
        pendingProductId = null;
        purchaseGate.end();
        billingState = client != null && client.isReady() ? State.READY : State.CONNECTING;
    }
}
