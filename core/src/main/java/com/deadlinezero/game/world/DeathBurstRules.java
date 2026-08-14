package com.deadlinezero.game.world;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.ThreatTierRules;

/** Deterministic endgame rule for enemies that leave a delayed hostile death burst. */
public final class DeathBurstRules {
    private DeathBurstRules() {}

    public static boolean enabled(Enemy.Type type, int threatTier) {
        if (type == null || type == Enemy.Type.BOSS) return false;
        int tier = ThreatTierRules.sanitizeTier(threatTier);
        if (tier < 8) return false;
        if (type == Enemy.Type.BRUTE || type == Enemy.Type.ELITE || type == Enemy.Type.SHIELDED) return true;
        return tier >= 15 && (type == Enemy.Type.REGENERATOR || type == Enemy.Type.PHANTOM);
    }

    public static float radius(Enemy.Type type, int threatTier) {
        int tier = ThreatTierRules.sanitizeTier(threatTier);
        float base = type == Enemy.Type.ELITE ? 2.55f : type == Enemy.Type.BRUTE ? 2.25f : 2.05f;
        return base + Math.max(0, tier - 8) * .025f;
    }

    public static float damage(Enemy.Type type, int threatTier) {
        int tier = ThreatTierRules.sanitizeTier(threatTier);
        float base = type == Enemy.Type.ELITE ? 24f : type == Enemy.Type.BRUTE ? 20f : 18f;
        return base + tier * 1.05f;
    }
}
