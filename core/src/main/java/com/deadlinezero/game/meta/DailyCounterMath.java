package com.deadlinezero.game.meta;

/** Overflow-safe arithmetic for daily mission counters. */
public final class DailyCounterMath {
    private DailyCounterMath() { }

    public static int increment(int current) {
        return current >= Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.max(0, current) + 1;
    }

    public static int addKills(int current, int kills) {
        int safe = Math.max(0, current);
        int add = Math.max(0, kills);
        return Integer.MAX_VALUE - safe < add ? Integer.MAX_VALUE : safe + add;
    }
}
