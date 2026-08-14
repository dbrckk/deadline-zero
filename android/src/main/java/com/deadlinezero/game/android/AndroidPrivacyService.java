package com.deadlinezero.game.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.deadlinezero.game.services.PrivacyService;

/** Android bridge from shared privacy controls to Google UMP and the published privacy policy. */
public final class AndroidPrivacyService implements PrivacyService {
    private final Activity activity;
    private final AndroidConsentManager consent;

    public AndroidPrivacyService(Activity activity, AndroidConsentManager consent) {
        this.activity = activity;
        this.consent = consent;
    }

    @Override public boolean optionsRequired() {
        return consent != null && consent.privacyOptionsRequired();
    }

    @Override public void showOptions(Runnable onDismissed) {
        if (consent == null) {
            if (onDismissed != null) onDismissed.run();
            return;
        }
        consent.showPrivacyOptions(onDismissed);
    }

    @Override public boolean policyAvailable() {
        String url = BuildConfig.PRIVACY_POLICY_URL;
        return activity != null && url != null && url.startsWith("https://") && url.length() > 8;
    }

    @Override public void openPolicy() {
        if (!policyAvailable()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PRIVACY_POLICY_URL));
        activity.startActivity(intent);
    }
}
