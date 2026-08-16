package com.deadlinezero.game.ai;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.world.BiomeEnemyRoster;

/** Pure combat pattern tuning kept outside GameScreen so enemy attacks remain testable and data-driven. */
public final class EnemyPatternCatalog {
    public record RangedPattern(int shots, float spreadDegrees, float speedMultiplier,
                                float damageMultiplier, boolean explosive, float explosionRadius) { }

    public record ChargePattern(float impactDamageMultiplier, float impactRadius,
                                float knockbackStrength, float recoveryMultiplier) { }

    private EnemyPatternCatalog() { }

    public static RangedPattern ranged(Enemy.Variant variant) {
        if (variant == null) variant = Enemy.Variant.NORMAL;
        BiomeEnemyRoster.Identity identity = BiomeEnemyRoster.identityFor(RunStageContext.stage(), Enemy.Type.RANGED);
        if (identity == BiomeEnemyRoster.Identity.CINDER_GUNNER) return cinderGunner(variant);
        if (identity == BiomeEnemyRoster.Identity.STATIC_SEER) return staticSeer(variant);
        return baseRanged(variant);
    }

    private static RangedPattern baseRanged(Enemy.Variant variant) {
        return switch (variant) {
            case SWIFT -> new RangedPattern(3, 7.5f, 1.14f, .68f, false, 0f);
            case ARMORED -> new RangedPattern(1, 0f, .88f, 1.34f, true, 1.65f);
            case FERAL -> new RangedPattern(5, 12f, 1.04f, .54f, false, 0f);
            default -> new RangedPattern(1, 0f, 1f, 1f, false, 0f);
        };
    }

    private static RangedPattern cinderGunner(Enemy.Variant variant) {
        return switch (variant) {
            case SWIFT -> new RangedPattern(4, 6.0f, 1.02f, .29f, true, 1.10f);
            case ARMORED -> new RangedPattern(2, 4.0f, .88f, .50f, true, 1.40f);
            case FERAL -> new RangedPattern(5, 8.0f, .98f, .23f, true, 1.05f);
            default -> new RangedPattern(3, 6.5f, .96f, .34f, true, 1.15f);
        };
    }

    private static RangedPattern staticSeer(Enemy.Variant variant) {
        return switch (variant) {
            case SWIFT -> new RangedPattern(6, 13.0f, 1.22f, .18f, false, 0f);
            case ARMORED -> new RangedPattern(3, 18.0f, 1.02f, .32f, false, 0f);
            case FERAL -> new RangedPattern(7, 15.0f, 1.16f, .16f, false, 0f);
            default -> new RangedPattern(5, 15.0f, 1.14f, .20f, false, 0f);
        };
    }

    public static ChargePattern charge(Enemy.Type type, Enemy.Variant variant) {
        float damage = type == Enemy.Type.ELITE ? 1.72f : 1.38f;
        float radius = type == Enemy.Type.ELITE ? 1.35f : 1.12f;
        float knockback = type == Enemy.Type.ELITE ? 1.35f : 1.08f;
        float recovery = type == Enemy.Type.ELITE ? .82f : 1f;
        if (variant == Enemy.Variant.SWIFT) {
            damage *= .88f;
            recovery *= .78f;
        } else if (variant == Enemy.Variant.ARMORED) {
            damage *= 1.22f;
            radius *= 1.10f;
            recovery *= 1.16f;
        } else if (variant == Enemy.Variant.FERAL) {
            damage *= 1.30f;
            knockback *= 1.18f;
            recovery *= .72f;
        }

        BiomeEnemyRoster.Identity identity = BiomeEnemyRoster.identityFor(RunStageContext.stage(), type);
        if (identity == BiomeEnemyRoster.Identity.FORGE_HOUND) {
            damage *= .95f;
            radius *= .82f;
            knockback *= .95f;
            recovery *= .68f;
        } else if (identity == BiomeEnemyRoster.Identity.SLAG_GUARD) {
            damage *= 1.28f;
            radius *= 1.18f;
            knockback *= 1.32f;
            recovery *= 1.24f;
        }
        return new ChargePattern(damage, radius, knockback, recovery);
    }
}
