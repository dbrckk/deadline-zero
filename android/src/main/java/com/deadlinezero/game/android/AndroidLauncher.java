package com.deadlinezero.game.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.MobileAds;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.services.GameServices;

public final class AndroidLauncher extends AndroidApplication {
    @Override protected void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);MobileAds.initialize(this, status -> {});AndroidApplicationConfiguration c=new AndroidApplicationConfiguration();c.useImmersiveMode=true;c.useAccelerometer=false;c.useCompass=false;c.useGyroscope=false;c.numSamples=2;initialize(new DeadlineZeroGame(new GameServices(new AndroidAdsService(this),new AndroidBillingService(this))),c);}
}
