package com.deadlinezero.game.android;

import android.app.Activity;
import com.android.billingclient.api.*;
import com.deadlinezero.game.services.BillingService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** Client-side Play Billing bridge. Production purchase verification should move server-side before launch. */
public final class AndroidBillingService implements BillingService, PurchasesUpdatedListener {
    private final Activity activity;
    private BillingClient client;
    private final Set<String> owned = ConcurrentHashMap.newKeySet();
    private Runnable success, failure;

    public AndroidBillingService(Activity activity) { this.activity = activity; }

    @Override public void initialize() {
        client = BillingClient.newBuilder(activity)
            .setListener(this)
            .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
            .enableAutoServiceReconnection()
            .build();
        client.startConnection(new BillingClientStateListener() {
            @Override public void onBillingSetupFinished(BillingResult result) {
                if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) restore();
            }
            @Override public void onBillingServiceDisconnected() { }
        });
    }

    @Override public boolean owns(String id) { return owned.contains(id); }

    @Override public void restore() {
        if (client == null || !client.isReady()) return;
        client.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build(),
            (result, purchases) -> {
                if (result.getResponseCode() != BillingClient.BillingResponseCode.OK) return;
                for (Purchase purchase : purchases) processPurchase(purchase, false);
            });
    }

    @Override public void purchase(String productId, Runnable onSuccess, Runnable onFailure) {
        if (client == null || !client.isReady()) { onFailure.run(); return; }
        this.success = onSuccess;
        this.failure = onFailure;

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
            for (Purchase purchase : purchases) handled |= processPurchase(purchase, true);
            if (!handled) failPending();
        } else {
            failPending();
        }
    }

    private boolean processPurchase(Purchase purchase, boolean notifyPending) {
        if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED) return false;

        boolean consumable = false;
        for (String id : purchase.getProducts()) {
            if (BillingService.isConsumable(id)) {
                consumable = true;
                break;
            }
        }

        if (consumable) {
            ConsumeParams params = ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();
            client.consumeAsync(params, (result, token) -> {
                if (!notifyPending) return;
                if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) succeedPending();
                else failPending();
            });
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

    private void grantDurableProducts(Purchase purchase) {
        for (String id : purchase.getProducts()) {
            if (!BillingService.isConsumable(id)) owned.add(id);
        }
    }

    private void succeedPending() {
        Runnable callback = success;
        success = null;
        failure = null;
        if (callback != null) callback.run();
    }

    private void failPending() {
        Runnable callback = failure;
        success = null;
        failure = null;
        if (callback != null) callback.run();
    }
}
