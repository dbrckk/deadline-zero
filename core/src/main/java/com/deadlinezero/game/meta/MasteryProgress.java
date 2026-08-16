package com.deadlinezero.game.meta;

import java.util.HashMap;
import java.util.Map;

import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;

/** Permanent non-FOMO mastery earned only from completed victories. */
public final class MasteryProgress {
    public record Gain(int weaponRankBefore, int weaponRankAfter,
                       int biomeRankBefore, int biomeRankAfter,
                       int creditsReward, int gemsReward) {
        public boolean rankedUp() { return weaponRankAfter > weaponRankBefore || biomeRankAfter > biomeRankBefore; }
    }

    private static final int[] RANK_THRESHOLDS = {0, 1, 3, 7, 15, 30};
    public static final int MAX_RANK = RANK_THRESHOLDS.length - 1;
    private final Map<String, Integer> weaponWins = new HashMap<>();
    private final Map<EnvironmentBiomeRules.Biome, Integer> biomeWins = new HashMap<>();

    public int weaponWins(String weaponId) {
        WeaponDefinition weapon = WeaponCatalog.byId(weaponId);
        return Math.max(0, weaponWins.getOrDefault(weapon.id, 0));
    }

    public int biomeWins(EnvironmentBiomeRules.Biome biome) {
        if (biome == null) return 0;
        return Math.max(0, biomeWins.getOrDefault(biome, 0));
    }

    public int weaponRank(String weaponId) { return rankForWins(weaponWins(weaponId)); }
    public int biomeRank(EnvironmentBiomeRules.Biome biome) { return rankForWins(biomeWins(biome)); }

    public int winsForNextWeaponRank(String weaponId) { return winsForNextRank(weaponWins(weaponId)); }
    public int winsForNextBiomeRank(EnvironmentBiomeRules.Biome biome) { return winsForNextRank(biomeWins(biome)); }

    public Gain recordVictory(String weaponId, int stage) {
        WeaponDefinition weapon = WeaponCatalog.byId(weaponId);
        EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(stage);
        int weaponBefore = weaponRank(weapon.id);
        int biomeBefore = biomeRank(biome);
        weaponWins.put(weapon.id, safeIncrement(weaponWins(weapon.id)));
        biomeWins.put(biome, safeIncrement(biomeWins(biome)));
        int weaponAfter = weaponRank(weapon.id);
        int biomeAfter = biomeRank(biome);
        int weaponRanks = weaponAfter - weaponBefore;
        int biomeRanks = biomeAfter - biomeBefore;
        int credits = weaponRanks * 180 + biomeRanks * 260;
        int gems = weaponRanks * 2 + biomeRanks * 3;
        return new Gain(weaponBefore, weaponAfter, biomeBefore, biomeAfter, credits, gems);
    }

    public void setWeaponWins(String weaponId, int wins) {
        WeaponDefinition weapon = WeaponCatalog.byId(weaponId);
        weaponWins.put(weapon.id, sanitizeWins(wins));
    }

    public void setBiomeWins(EnvironmentBiomeRules.Biome biome, int wins) {
        if (biome != null) biomeWins.put(biome, sanitizeWins(wins));
    }

    public static int rankForWins(int wins) {
        int safe = sanitizeWins(wins);
        int rank = 0;
        for (int i = 1; i < RANK_THRESHOLDS.length; i++) {
            if (safe < RANK_THRESHOLDS[i]) break;
            rank = i;
        }
        return rank;
    }

    public static int winsForNextRank(int wins) {
        int rank = rankForWins(wins);
        if (rank >= MAX_RANK) return 0;
        return Math.max(0, RANK_THRESHOLDS[rank + 1] - sanitizeWins(wins));
    }

    private static int safeIncrement(int value) {
        int safe = sanitizeWins(value);
        return safe == Integer.MAX_VALUE ? Integer.MAX_VALUE : safe + 1;
    }

    private static int sanitizeWins(int wins) { return Math.max(0, wins); }
}
