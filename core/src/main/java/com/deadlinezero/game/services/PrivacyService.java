package com.deadlinezero.game.services;

/** Platform privacy controls exposed to the shared game UI. */
public interface PrivacyService {
    boolean optionsRequired();
    void showOptions(Runnable onDismissed);

    static PrivacyService noOp() {
        return new PrivacyService() {
            @Override public boolean optionsRequired() { return false; }
            @Override public void showOptions(Runnable onDismissed) {
                if (onDismissed != null) onDismissed.run();
            }
        };
    }
}
