package com.deadlinezero.game.meta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** Small persistent onboarding state. Tutorial hints never block gameplay and disappear permanently once learned. */
public final class OnboardingState {
    private static final String PREFS = "deadline-zero-onboarding";
    private static OnboardingState active;
    private final Preferences prefs;

    private boolean movementSeen;
    private boolean dashSeen;
    private boolean upgradeSeen;
    private boolean bossSeen;
    private boolean completed;

    private OnboardingState(Preferences prefs) {
        this.prefs = prefs;
        movementSeen = prefs.getBoolean("movementSeen", false);
        dashSeen = prefs.getBoolean("dashSeen", false);
        upgradeSeen = prefs.getBoolean("upgradeSeen", false);
        bossSeen = prefs.getBoolean("bossSeen", false);
        completed = prefs.getBoolean("completed", false);
    }

    public static OnboardingState load() {
        active = new OnboardingState(Gdx.app.getPreferences(PREFS));
        return active;
    }

    public static OnboardingState active() {
        if (active == null) active = load();
        return active;
    }

    public boolean completed() { return completed; }
    public boolean movementSeen() { return movementSeen; }
    public boolean dashSeen() { return dashSeen; }
    public boolean upgradeSeen() { return upgradeSeen; }
    public boolean bossSeen() { return bossSeen; }

    public void markMovementSeen() { if (!movementSeen) { movementSeen = true; persist(); } }
    public void markDashSeen() { if (!dashSeen) { dashSeen = true; persist(); } }
    public void markUpgradeSeen() { if (!upgradeSeen) { upgradeSeen = true; persist(); } }
    public void markBossSeen() { if (!bossSeen) { bossSeen = true; persist(); } }

    public void refreshCompletion() {
        if (movementSeen && dashSeen && upgradeSeen && bossSeen) completed = true;
        persist();
    }

    public void reset() {
        movementSeen = dashSeen = upgradeSeen = bossSeen = completed = false;
        persist();
    }

    private void persist() {
        prefs.putBoolean("movementSeen", movementSeen)
            .putBoolean("dashSeen", dashSeen)
            .putBoolean("upgradeSeen", upgradeSeen)
            .putBoolean("bossSeen", bossSeen)
            .putBoolean("completed", completed)
            .flush();
    }
}
