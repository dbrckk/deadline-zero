package com.deadlinezero.game.services;

public interface BillingService {
    String REMOVE_ADS = "remove_ads_lifetime";
    String STARTER_PACK = "starter_pack_01";
    String GEMS_SMALL = "gems_250";
    String GEMS_LARGE = "gems_1200";

    void initialize();
    boolean owns(String productId);
    void purchase(String productId, Runnable onSuccess, Runnable onFailure);
    void restore();

    static boolean isConsumable(String productId) {
        return GEMS_SMALL.equals(productId) || GEMS_LARGE.equals(productId);
    }
}
