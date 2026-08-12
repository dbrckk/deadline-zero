package com.deadlinezero.game.android;

import android.app.Activity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.deadlinezero.game.services.AdsService;

public final class AndroidAdsService implements AdsService {
    private static final String TEST_REWARDED="ca-app-pub-3940256099942544/5224354917";
    private final Activity activity; private RewardedAd ad; private boolean loading;
    public AndroidAdsService(Activity activity){this.activity=activity;}
    @Override public void preload(){load();}
    private void load(){if(loading||ad!=null)return;loading=true;activity.runOnUiThread(()->RewardedAd.load(activity,TEST_REWARDED,new AdRequest.Builder().build(),new RewardedAdLoadCallback(){@Override public void onAdLoaded(RewardedAd a){ad=a;loading=false;}@Override public void onAdFailedToLoad(LoadAdError e){ad=null;loading=false;}}));}
    @Override public boolean isRewardedReady(){return ad!=null;}
    @Override public void showRewarded(Reward reward,Runnable earned,Runnable unavailable){activity.runOnUiThread(()->{if(ad==null){load();unavailable.run();return;}RewardedAd showing=ad;ad=null;showing.setFullScreenContentCallback(new FullScreenContentCallback(){@Override public void onAdDismissedFullScreenContent(){load();}});showing.show(activity,item->earned.run());});}
}
