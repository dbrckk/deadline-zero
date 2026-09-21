package com.deadlinezero.game.visual;

/** Pure display policy for non-blocking combat onboarding hints. */
public final class OnboardingHintPolicy {
    public static final float MAX_VISIBLE_SECONDS = 3.6f;
    public static final int NONE = -1;

    private OnboardingHintPolicy() { }

    public static int step(boolean movementSeen, boolean dashSeen, boolean upgradeSeen, boolean bossSeen) {
        if (!movementSeen) return 0;
        if (!dashSeen) return 1;
        if (!upgradeSeen) return 2;
        if (!bossSeen) return 3;
        return NONE;
    }

    public static boolean visible(boolean completed, int step, float ageSeconds) {
        if (completed || step == NONE) return false;
        return ageSeconds >= 0f && ageSeconds < MAX_VISIBLE_SECONDS;
    }
}
