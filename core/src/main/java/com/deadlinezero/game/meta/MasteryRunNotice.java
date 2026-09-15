package com.deadlinezero.game.meta;

import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;

/** Run-local presentation snapshot for mastery rank-ups; never persisted. */
public final class MasteryRunNotice {
    public record Notice(String weaponName, String weaponNameKey, int weaponRank,
                         String biomeName, String biomeNameKey, int biomeRank,
                         int creditsReward, int gemsReward) {
        public boolean weaponRankedUp() { return weaponRank > 0; }
        public boolean biomeRankedUp() { return biomeRank > 0; }
        public boolean visible() { return weaponRankedUp() || biomeRankedUp(); }
    }

    private static Notice current;

    private MasteryRunNotice() { }

    public static void clear() { current = null; }

    public static void capture(MasteryProgress.Gain gain, WeaponDefinition weapon, EnvironmentBiomeRules.Biome biome) {
        if (gain == null || !gain.rankedUp()) {
            clear();
            return;
        }
        int weaponRank = gain.weaponRankAfter() > gain.weaponRankBefore() ? gain.weaponRankAfter() : 0;
        int biomeRank = gain.biomeRankAfter() > gain.biomeRankBefore() ? gain.biomeRankAfter() : 0;
        current = new Notice(
            weapon == null ? "WEAPON" : weapon.displayName,
            weapon == null ? null : weapon.displayNameKey(),
            weaponRank,
            biome == null ? "BIOME" : biome.label,
            biome == null ? null : biome.labelKey(),
            biomeRank,
            Math.max(0, gain.creditsReward()), Math.max(0, gain.gemsReward()));
    }

    public static Notice current() { return current; }
}
