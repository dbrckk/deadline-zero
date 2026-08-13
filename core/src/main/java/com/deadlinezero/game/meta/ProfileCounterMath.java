package com.deadlinezero.game.meta;

/** Overflow-safe arithmetic for persistent profile counters and currencies. */
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
}
