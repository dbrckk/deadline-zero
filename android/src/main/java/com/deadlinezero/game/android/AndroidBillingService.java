package com.deadlinezero.game.android;

import android.app.Activity;
import com.android.billingclient.api.*;
import com.deadlinezero.game.services.BillingService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class AndroidBillingService implements BillingService, PurchasesUpdatedListener {
    private final Activity activity; private BillingClient client; private final Set<String> owned=ConcurrentHashMap.newKeySet(); private ProductDetails pendingDetails; private Runnable success, failure;
    public AndroidBillingService(Activity activity){this.activity=activity;}
    @Override public void initialize(){client=BillingClient.newBuilder(activity).setListener(this).enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()).enableAutoServiceReconnection().build();client.startConnection(new BillingClientStateListener(){public void onBillingSetupFinished(BillingResult r){if(r.getResponseCode()==BillingClient.BillingResponseCode.OK)restore();}public void onBillingServiceDisconnected(){}});}
    @Override public boolean owns(String id){return owned.contains(id);}
    @Override public void restore(){if(client==null||!client.isReady())return;client.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build(),(r,list)->{if(r.getResponseCode()==BillingClient.BillingResponseCode.OK)for(Purchase p:list)for(String id:p.getProducts())owned.add(id);});}
    @Override public void purchase(String productId,Runnable onSuccess,Runnable onFailure){this.success=onSuccess;this.failure=onFailure;QueryProductDetailsParams.Product p=QueryProductDetailsParams.Product.newBuilder().setProductId(productId).setProductType(BillingClient.ProductType.INAPP).build();client.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(Collections.singletonList(p)).build(),(r,result)->{if(r.getResponseCode()!=BillingClient.BillingResponseCode.OK||result.getProductDetailsList().isEmpty()){onFailure.run();return;}pendingDetails=result.getProductDetailsList().get(0);BillingFlowParams.ProductDetailsParams pd=BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(pendingDetails).build();client.launchBillingFlow(activity,BillingFlowParams.newBuilder().setProductDetailsParamsList(Collections.singletonList(pd)).build());});}
    @Override public void onPurchasesUpdated(BillingResult r,List<Purchase> purchases){if(r.getResponseCode()==BillingClient.BillingResponseCode.OK&&purchases!=null){for(Purchase p:purchases){for(String id:p.getProducts())owned.add(id);if(p.getPurchaseState()==Purchase.PurchaseState.PURCHASED&&!p.isAcknowledged())client.acknowledgePurchase(AcknowledgePurchaseParams.newBuilder().setPurchaseToken(p.getPurchaseToken()).build(),x->{});}if(success!=null)success.run();}else if(failure!=null)failure.run();}
}
