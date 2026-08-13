package com.deadlinezero.game.services;

public final class GameServices {
    public final AdsService ads;
    public final BillingService billing;
    public final PrivacyService privacy;

    public GameServices(AdsService ads, BillingService billing) {
        this(ads, billing, PrivacyService.noOp());
    }

    public GameServices(AdsService ads, BillingService billing, PrivacyService privacy) {
        this.ads = ads;
        this.billing = billing;
        this.privacy = privacy == null ? PrivacyService.noOp() : privacy;
    }

    public static GameServices noOp() {
        return new GameServices(new AdsService() {
            public boolean isRewardedReady() { return false; }
            public void showRewarded(Reward reward, Runnable earned, Runnable unavailable) { unavailable.run(); }
        }, new BillingService() {
            public void initialize() {}
            public boolean owns(String id) { return false; }
            public void purchase(String id, Runnable success, Runnable failure) { failure.run(); }
            public void restore() {}
        }, PrivacyService.noOp());
    }
}
