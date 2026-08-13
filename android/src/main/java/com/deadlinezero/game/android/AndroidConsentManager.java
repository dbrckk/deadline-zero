package com.deadlinezero.game.android;

import android.app.Activity;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import java.util.concurrent.atomic.AtomicBoolean;

/** Handles UMP consent refresh at launch and exposes a single ad-request gate. */
public final class AndroidConsentManager {
    private final Activity activity;
    private final ConsentInformation consentInformation;
    private final AtomicBoolean completed = new AtomicBoolean(false);

    public AndroidConsentManager(Activity activity) {
        this.activity = activity;
        this.consentInformation = UserMessagingPlatform.getConsentInformation(activity);
    }

    public void gatherConsent(Runnable onComplete) {
        ConsentRequestParameters params = new ConsentRequestParameters.Builder().build();
        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            () -> UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                activity,
                formError -> completeOnce(onComplete)
            ),
            requestError -> completeOnce(onComplete)
        );
    }

    public boolean canRequestAds() {
        return consentInformation.canRequestAds();
    }

    public boolean privacyOptionsRequired() {
        return consentInformation.getPrivacyOptionsRequirementStatus()
            == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED;
    }

    public void showPrivacyOptions(Runnable onDismissed) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity, formError -> {
            if (onDismissed != null) onDismissed.run();
        });
    }

    private void completeOnce(Runnable onComplete) {
        if (onComplete != null && completed.compareAndSet(false, true)) onComplete.run();
    }
}
