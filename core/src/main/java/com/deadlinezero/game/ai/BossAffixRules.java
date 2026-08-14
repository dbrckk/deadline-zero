package com.deadlinezero.game.ai;

import com.deadlinezero.game.meta.ThreatTierRules;

/** Deterministic endgame boss affixes derived from stage and active Threat Tier. */
public final class BossAffixRules {
    public enum Affix {
        NONE("NONE", 1f, 1f, 1f, 1f, 1f, 1f, 0, 0, 0),
        FRENZY("FRENZY", 1.06f, 1.10f, 1.12f, .78f, 1f, .90f, 0, 2, 0),
        COMMANDER("COMMANDER", 1.12f, 1.02f, 1.08f, .94f, .68f, .92f, 2, 0, 0),
        ARTILLERY("ARTILLERY", 1.08f, 1.04f, 1.10f, .92f, .92f, .66f, 0, 6, 1),
        APOCALYPSE("APOCALYPSE", 1.22f, 1.10f, 1.18f, .72f, .64f, .58f, 3, 8, 1);

        public final String title;
        public final float hp;
        public final float speed;
        public final float damage;
        public final float chargeCooldown;
        public final float summonCooldown;
        public final float pulseCooldown;
        public final int summonBonus;
        public final int enrageShotBonus;
        public final int explosiveDensityBonus;

        Affix(String title, float hp, float speed, float damage, float chargeCooldown,
              float summonCooldown, float pulseCooldown, int summonBonus,
              int enrageShotBonus, int explosiveDensityBonus) {
            this.title = title;
            this.hp = hp;
            this.speed = speed;
            this.damage = damage;
            this.chargeCooldown = chargeCooldown;
            this.summonCooldown = summonCooldown;
            this.pulseCooldown = pulseCooldown;
            this.summonBonus = summonBonus;
            this.enrageShotBonus = enrageShotBonus;
            this.explosiveDensityBonus = explosiveDensityBonus;
        }
    }

    private BossAffixRules() {}

    public static Affix forRun(int stage, int threatTier) {
        int tier = ThreatTierRules.sanitizeTier(threatTier);
        if (tier <= 0) return Affix.NONE;
        if (tier >= ThreatTierRules.MAX_TIER) return Affix.APOCALYPSE;
        Affix[] rotation = { Affix.FRENZY, Affix.COMMANDER, Affix.ARTILLERY };
        int index = Math.floorMod(Math.max(1, stage) * 7 + tier * 5, rotation.length);
        return rotation[index];
    }
}
