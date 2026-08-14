package com.deadlinezero.game.meta;

import java.math.BigInteger;
import java.util.EnumMap;

/** Persistent per-survivor unlock and XP state. */
public final class SurvivorProgression {
    private static final BigInteger BI_85 = BigInteger.valueOf(85L);
    private static final BigInteger BI_2 = BigInteger.valueOf(2L);

    private final EnumMap<SurvivorCatalog.Survivor, Integer> levels = new EnumMap<>(SurvivorCatalog.Survivor.class);
    private final EnumMap<SurvivorCatalog.Survivor, Long> xp = new EnumMap<>(SurvivorCatalog.Survivor.class);
    private final EnumMap<SurvivorCatalog.Survivor, Boolean> unlocked = new EnumMap<>(SurvivorCatalog.Survivor.class);

    public SurvivorProgression() {
        for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
            levels.put(survivor, 1);
            xp.put(survivor, 0L);
            unlocked.put(survivor, survivor == SurvivorCatalog.Survivor.REX);
        }
    }

    public int level(SurvivorCatalog.Survivor survivor) { return levels.getOrDefault(survivor, 1); }
    public long xp(SurvivorCatalog.Survivor survivor) { return xp.getOrDefault(survivor, 0L); }
    public boolean unlocked(SurvivorCatalog.Survivor survivor) { return unlocked.getOrDefault(survivor, false); }
    public long xpForNext(SurvivorCatalog.Survivor survivor) { return xpForLevel(level(survivor)); }

    public void setState(SurvivorCatalog.Survivor survivor, int level, long currentXp, boolean isUnlocked) {
        if (survivor == null) return;
        LevelProgress normalized = advance(level, currentXp, 0L);
        levels.put(survivor, normalized.level());
        xp.put(survivor, normalized.xp());
        unlocked.put(survivor, isUnlocked || survivor == SurvivorCatalog.Survivor.REX);
    }

    public boolean unlock(SurvivorCatalog.Survivor survivor) {
        if (survivor == null || unlocked(survivor)) return false;
        unlocked.put(survivor, true);
        return true;
    }

    public void refreshUnlocks(PlayerProfile profile) {
        if (profile == null) return;
        unlock(SurvivorCatalog.Survivor.REX);
        if (profile.accountLevel >= 3) unlock(SurvivorCatalog.Survivor.NYX);
        if (profile.highestStage >= 3) unlock(SurvivorCatalog.Survivor.BASTION);
        if (profile.highestStage >= 5) unlock(SurvivorCatalog.Survivor.VOLT);
        if (profile.accountLevel >= 8 || profile.highestStage >= 7) unlock(SurvivorCatalog.Survivor.WRAITH);
    }

    public void addXp(SurvivorCatalog.Survivor survivor, long amount) {
        if (survivor == null || amount <= 0) return;
        LevelProgress progress = advance(level(survivor), xp(survivor), amount);
        levels.put(survivor, progress.level());
        xp.put(survivor, progress.xp());
    }

    public float levelPowerMultiplier(SurvivorCatalog.Survivor survivor) {
        return 1f + Math.min(0.30f, Math.max(0, level(survivor) - 1) * .012f);
    }

    private record LevelProgress(int level, long xp) { }

    /** Advances affine XP thresholds in O(log n), safely handling corrupted or extreme saves. */
    private static LevelProgress advance(int currentLevel, long currentXp, long amount) {
        int level = Math.max(1, currentLevel);
        long totalXp = ProfileCounterMath.addNonNegative(currentXp, amount);
        if (level == Integer.MAX_VALUE) {
            return new LevelProgress(level, Math.min(totalXp, xpForLevel(level) - 1L));
        }

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

    private static long xpForLevel(int level) {
        int safeLevel = Math.max(1, level);
        return 180L + (long)(safeLevel - 1) * 85L;
    }

    private static BigInteger xpCost(int level, int levelsToAdvance) {
        if (levelsToAdvance <= 0) return BigInteger.ZERO;
        BigInteger n = BigInteger.valueOf(levelsToAdvance);
        BigInteger first = BigInteger.valueOf(180L + (long)(Math.max(1, level) - 1) * 85L);
        BigInteger staircase = BI_85.multiply(n).multiply(n.subtract(BigInteger.ONE)).divide(BI_2);
        return first.multiply(n).add(staircase);
    }
}
