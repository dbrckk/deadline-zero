package com.deadlinezero.game.meta;

/** Pure completion rule for the non-blocking combat tutorial. */
public final class OnboardingCompletionPolicy {
    private OnboardingCompletionPolicy() { }

    public static boolean completed(boolean movementSeen, boolean dashSeen, boolean upgradeSeen, boolean bossSeen) {
        return movementSeen && dashSeen && upgradeSeen && bossSeen;
    }
}
