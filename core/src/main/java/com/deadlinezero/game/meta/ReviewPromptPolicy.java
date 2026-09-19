package com.deadlinezero.game.meta;

/** Conservative eligibility for requesting a store-managed review prompt after a successful run. */
public final class ReviewPromptPolicy {
    private ReviewPromptPolicy() {}

    public static boolean eligible(boolean firstClear, int stage) {
        return firstClear && stage >= 3;
    }
}
