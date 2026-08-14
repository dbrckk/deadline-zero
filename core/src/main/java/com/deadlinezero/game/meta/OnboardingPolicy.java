package com.deadlinezero.game.meta;

/** First-run onboarding policy that never interrupts established players after an update. */
public final class OnboardingPolicy {
    private OnboardingPolicy() { }

    public static boolean shouldShow(PlayerProfile profile) {
        return profile != null && !profile.onboardingCompleted && profile.totalRuns <= 0;
    }
}
