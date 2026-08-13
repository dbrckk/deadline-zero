package com.deadlinezero.game.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.services.GameServices;
import com.google.android.gms.ads.MobileAds;

public final class AndroidLauncher extends AndroidApplication {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidConsentManager consent = new AndroidConsentManager(this);
        AndroidAdsService ads = new AndroidAdsService(this, consent);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        config.numSamples = 2;

        initialize(
            new DeadlineZeroGame(new GameServices(
                ads,
                new AndroidBillingService(this),
                new AndroidPrivacyService(consent)
            )),
            config
        );

        consent.gatherConsent(() -> {
            if (!consent.canRequestAds()) return;
            MobileAds.initialize(this, status -> ads.preload());
        });
    }
}
