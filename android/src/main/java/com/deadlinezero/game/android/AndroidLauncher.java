package com.deadlinezero.game.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.services.CloudSaveAdapter;
import com.deadlinezero.game.services.GameServices;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.games.PlayGamesSdk;

public final class AndroidLauncher extends AndroidApplication {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidConsentManager consent = new AndroidConsentManager(this);
        AndroidAdsService ads = new AndroidAdsService(this, consent);

        CloudSaveAdapter cloudSave = CloudSaveAdapter.unavailable();
        if (BuildConfig.PLAY_GAMES_CONFIGURED) {
            PlayGamesSdk.initialize(this);
            cloudSave = new AndroidPlayGamesCloudSaveAdapter(this);
        }

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
                new AndroidPrivacyService(this, consent),
                new AndroidShareService(this),
                new AndroidHapticsService(this),
                cloudSave,
                new AndroidThermalService(this)
            )),
            config
        );

        consent.gatherConsent(() -> {
            if (!consent.canRequestAds()) return;
            MobileAds.initialize(this, status -> ads.preload());
        });
    }
}
