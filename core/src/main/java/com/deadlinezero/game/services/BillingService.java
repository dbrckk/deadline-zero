package com.deadlinezero.game.services;

public interface BillingService {
    String REMOVE_ADS = "remove_ads_lifetime";
    String STARTER_PACK = "starter_pack_01";
    void initialize();
    boolean owns(String productId);
    void purchase(String productId, Runnable onSuccess, Runnable onFailure);
    void restore();
}
