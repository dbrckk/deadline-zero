package com.deadlinezero.game.android;

import com.deadlinezero.game.services.PrivacyService;

/** Android bridge from shared privacy controls to Google UMP. */
public final class AndroidPrivacyService implements PrivacyService {
    private final AndroidConsentManager consent;

    public AndroidPrivacyService(AndroidConsentManager consent) {
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
}
