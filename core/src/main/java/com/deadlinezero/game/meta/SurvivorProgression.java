package com.deadlinezero.game.meta;

import java.util.EnumMap;

/** Persistent per-survivor unlock and XP state. */
public final class SurvivorProgression {
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
    public long xpForNext(SurvivorCatalog.Survivor survivor) { return 180L + (long)(level(survivor) - 1) * 85L; }

    public void setState(SurvivorCatalog.Survivor survivor, int level, long currentXp, boolean isUnlocked) {
        levels.put(survivor, Math.max(1, level));
        xp.put(survivor, Math.max(0L, currentXp));
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
        long value = xp(survivor) + amount;
        int level = level(survivor);
        while (value >= (180L + (long)(level - 1) * 85L)) {
            value -= 180L + (long)(level - 1) * 85L;
            level++;
        }
        levels.put(survivor, level);
        xp.put(survivor, value);
    }

    public float levelPowerMultiplier(SurvivorCatalog.Survivor survivor) {
        return 1f + Math.min(0.30f, Math.max(0, level(survivor) - 1) * .012f);
    }
}
