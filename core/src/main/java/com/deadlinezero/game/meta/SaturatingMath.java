package com.deadlinezero.game.meta;

/** Small overflow-safe helpers for persistent progression counters. */
public final class SaturatingMath {
    private SaturatingMath() { }

    public static long addPositive(long current, long amount) {
        long safe = Math.max(0L, current);
        if (amount <= 0L) return safe;
        return Long.MAX_VALUE - safe < amount ? Long.MAX_VALUE : safe + amount;
    }

    public static int increment(int current) {
        return current == Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.max(0, current) + 1;
    }
}
