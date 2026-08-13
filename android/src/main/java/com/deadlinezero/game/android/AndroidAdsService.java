package com.deadlinezero.game.android;

import android.app.Activity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.deadlinezero.game.services.AdsService;

public final class AndroidAdsService implements AdsService {
    private final Activity activity;
    private RewardedAd ad;
    private boolean loading;

    public AndroidAdsService(Activity activity) {
        this.activity = activity;
    }

    @Override public void preload() {
        load();
    }

    private void load() {
        if (loading || ad != null) return;
        loading = true;
        activity.runOnUiThread(() -> RewardedAd.load(
            activity,
            BuildConfig.ADMOB_REWARDED_ID,
            new AdRequest.Builder().build(),
            new RewardedAdLoadCallback() {
                @Override public void onAdLoaded(RewardedAd loaded) {
                    ad = loaded;
                    loading = false;
                }

                @Override public void onAdFailedToLoad(LoadAdError error) {
                    ad = null;
                    loading = false;
                }
            }
        ));
    }

    @Override public boolean isRewardedReady() {
        return ad != null;
    }

    @Override public void showRewarded(Reward reward, Runnable earned, Runnable unavailable) {
        activity.runOnUiThread(() -> {
            if (ad == null) {
                load();
                unavailable.run();
                return;
            }
            RewardedAd showing = ad;
            ad = null;
            showing.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override public void onAdDismissedFullScreenContent() {
                    load();
                }
            });
            showing.show(activity, item -> earned.run());
        });
    }
}
