package com.deadlinezero.game.world;

/** Pure tactical tuning for biome-signature enemies. */
public final class BiomeEnemyBehaviorRules {
    public record Profile(
        float speedMultiplier,
        float burstMultiplier,
        float tacticCooldownMultiplier,
        float strafeStrengthMultiplier,
        float chargeStrengthMultiplier,
        boolean aggressiveCharge,
        boolean evasiveStrafe,
        float recoveryMultiplier
    ) { }

    private static final Profile DEFAULT = new Profile(1f, 1f, 1f, 1f, 1f, false, false, 1f);

    private BiomeEnemyBehaviorRules() { }

    public static Profile forIdentity(BiomeEnemyRoster.Identity identity) {
        if (identity == null) return DEFAULT;
        return switch (identity) {
            case FORGE_HOUND -> new Profile(1.12f, 1.34f, .72f, .80f, 1.28f, true, false, 1f);
            case CINDER_GUNNER -> new Profile(.96f, 1.05f, .72f, 1.32f, .85f, false, true, 1f);
            case SLAG_GUARD -> new Profile(.88f, 1.14f, .78f, .72f, 1.38f, true, false, 1f);
            case PHASE_STALKER -> new Profile(1.08f, 1.48f, .66f, 1.42f, 1.05f, false, true, 1f);
            case STATIC_SEER -> new Profile(.92f, 1.08f, .62f, 1.48f, .80f, false, true, 1f);
            case NULL_WARD -> new Profile(.82f, 1.02f, .90f, .88f, .92f, false, false, 1.55f);
            default -> DEFAULT;
        };
    }
}
