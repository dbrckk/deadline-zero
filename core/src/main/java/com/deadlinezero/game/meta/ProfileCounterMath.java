package com.deadlinezero.game.meta;

import java.math.BigInteger;

/** Overflow-safe arithmetic for persistent profile counters, currencies and rewards. */
public final class ProfileCounterMath {
    private static final BigInteger BI_55 = BigInteger.valueOf(55L);
    private static final BigInteger BI_85 = BigInteger.valueOf(85L);
    private static final BigInteger BI_110 = BigInteger.valueOf(110L);

    private ProfileCounterMath() { }

    public record LevelProgress(int level, long xp) { }

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

    /** Advances linear account-level thresholds in O(log n), even for corrupted/extreme saves. */
    public static LevelProgress advanceAccountXp(int currentLevel, long currentXp, long amount) {
        int level = Math.max(1, currentLevel);
        long totalXp = addNonNegative(currentXp, amount);
        if (level == Integer.MAX_VALUE) return new LevelProgress(level, Math.min(totalXp, xpForLevel(level) - 1L));

        int room = Integer.MAX_VALUE - level;
        int low = 0;
        int high = room;
        BigInteger budget = BigInteger.valueOf(totalXp);
        while (low < high) {
            int mid = low + (int)(((long)high - low + 1L) / 2L);
            if (xpCost(level, mid).compareTo(budget) <= 0) low = mid;
            else high = mid - 1;
        }

        BigInteger spent = xpCost(level, low);
        int nextLevel = level + low;
        long remainder = budget.subtract(spent).longValue();
        if (nextLevel == Integer.MAX_VALUE) remainder = Math.min(remainder, xpForLevel(nextLevel) - 1L);
        return new LevelProgress(nextLevel, Math.max(0L, remainder));
    }

    public static long xpForLevel(int level) {
        int safeLevel = Math.max(1, level);
        return 250L + (long)(safeLevel - 1) * 110L;
    }

    private static BigInteger xpCost(int level, int levelsToAdvance) {
        if (levelsToAdvance <= 0) return BigInteger.ZERO;
        BigInteger n = BigInteger.valueOf(levelsToAdvance);
        BigInteger linear = BI_110.multiply(BigInteger.valueOf(Math.max(1, level))).add(BI_85);
        return BI_55.multiply(n).multiply(n).add(linear.multiply(n));
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
