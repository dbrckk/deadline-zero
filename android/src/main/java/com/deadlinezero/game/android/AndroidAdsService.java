package com.deadlinezero.game.android;

import android.app.Activity;
import com.badlogic.gdx.Gdx;
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
    private volatile RewardedAd ad;
    private volatile boolean loading;
    private volatile FullscreenListener fullscreenListener;

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

    @Override public void setFullscreenListener(FullscreenListener listener) {
        fullscreenListener = listener;
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
                dispatchToGameThread(unavailable);
                return;
            }

            RewardedAd showing = ad;
            ad = null;
            AtomicBoolean completed = new AtomicBoolean(false);
            AtomicBoolean presentationClosed = new AtomicBoolean(false);
            dispatchFullscreenOpening();

            showing.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override public void onAdDismissedFullScreenContent() {
                    load();
                    dispatchFullscreenClosedOnce(presentationClosed);
                    if (completed.compareAndSet(false, true)) dispatchToGameThread(unavailable);
                }

                @Override public void onAdFailedToShowFullScreenContent(AdError error) {
                    load();
                    dispatchFullscreenClosedOnce(presentationClosed);
                    if (completed.compareAndSet(false, true)) dispatchToGameThread(unavailable);
                }
            });

            showing.show(activity, item -> {
                if (completed.compareAndSet(false, true)) dispatchToGameThread(earned);
            });
        });
    }

    private void dispatchFullscreenOpening() {
        FullscreenListener listener = fullscreenListener;
        if (listener != null) dispatchToGameThread(listener::onOpening);
    }

    private void dispatchFullscreenClosedOnce(AtomicBoolean closed) {
        if (!closed.compareAndSet(false, true)) return;
        FullscreenListener listener = fullscreenListener;
        if (listener != null) dispatchToGameThread(listener::onClosed);
    }

    private void dispatchToGameThread(Runnable callback) {
        if (callback == null) return;
        if (Gdx.app != null) {
            Gdx.app.postRunnable(callback);
        } else {
            activity.runOnUiThread(callback);
        }
    }
}
