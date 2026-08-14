package com.deadlinezero.game.meta;

/** Overflow-safe arithmetic for persistent profile counters, currencies and rewards. */
public final class ProfileCounterMath {
    private ProfileCounterMath() { }

    public static long addNonNegative(long current, long amount) {
        long safe = Math.max(0L, current);
        long add = Math.max(0L, amount);
        return Long.MAX_VALUE - safe < add ? Long.MAX_VALUE : safe + add;
    }

    public static int incrementNonNegative(int current) {
        int safe = Math.max(0, current);
        return safe == Integer.MAX_VALUE ? Integer.MAX_VALUE : safe + 1;
    }

    public static long addKills(long current, int kills) {
        return addNonNegative(current, Math.max(0, kills));
    }

    /** Scales a non-negative long without narrowing through Math.round(float). */
    public static long scaleNonNegative(long value, float multiplier) {
        long safe = Math.max(0L, value);
        if (safe == 0L || Float.isNaN(multiplier) || multiplier <= 0f) return 0L;
        if (multiplier == Float.POSITIVE_INFINITY) return Long.MAX_VALUE;
        double scaled = safe * (double) multiplier;
        if (!Double.isFinite(scaled) || scaled >= Long.MAX_VALUE) return Long.MAX_VALUE;
        return Math.max(0L, Math.round(scaled));
    }
}
