package com.deadlinezero.game.android;

import android.app.Activity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.deadlinezero.game.services.AdsService;
import java.util.concurrent.atomic.AtomicBoolean;

public final class AndroidAdsService implements AdsService {
    private final Activity activity;
    private final AndroidConsentManager consent;
    private RewardedAd ad;
    private boolean loading;

    public AndroidAdsService(Activity activity) {
        this(activity, null);
    }

    public AndroidAdsService(Activity activity, AndroidConsentManager consent) {
        this.activity = activity;
        this.consent = consent;
    }

    @Override public void preload() {
        load();
    }

    private boolean canRequestAds() {
        return consent == null || consent.canRequestAds();
    }

    private void load() {
        if (!canRequestAds() || loading || ad != null) return;
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
        return canRequestAds() && ad != null;
    }

    @Override public void showRewarded(Reward reward, Runnable earned, Runnable unavailable) {
        activity.runOnUiThread(() -> {
            if (!canRequestAds() || ad == null) {
                load();
                unavailable.run();
                return;
            }

            RewardedAd showing = ad;
            ad = null;
            AtomicBoolean completed = new AtomicBoolean(false);

            showing.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override public void onAdDismissedFullScreenContent() {
                    load();
                    if (completed.compareAndSet(false, true)) unavailable.run();
                }

                @Override public void onAdFailedToShowFullScreenContent(AdError error) {
                    load();
                    if (completed.compareAndSet(false, true)) unavailable.run();
                }
            });

            showing.show(activity, item -> {
                if (completed.compareAndSet(false, true)) earned.run();
            });
        });
    }
}
