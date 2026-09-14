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
            case VOLATILE -> new RangedPattern(2, 7f, .96f, .72f, true, 1.35f);
            case JUGGERNAUT -> new RangedPattern(1, 0f, .82f, 1.48f, true, 1.75f);
            case RAVAGER -> new RangedPattern(4, 10f, 1.10f, .66f, false, 0f);
            case AEGIS -> new RangedPattern(2, 5f, .94f, .78f, false, 0f);
            case HUNTER -> new RangedPattern(3, 3.5f, 1.18f, .82f, false, 0f);
            default -> new RangedPattern(1, 0f, 1f, 1f, false, 0f);
        };
    }

    private static RangedPattern cinderGunner(Enemy.Variant variant) {
        return switch (variant) {
            case SWIFT -> new RangedPattern(4, 6.0f, 1.02f, .29f, true, 1.10f);
            case ARMORED -> new RangedPattern(2, 4.0f, .88f, .50f, true, 1.40f);
            case FERAL -> new RangedPattern(5, 8.0f, .98f, .23f, true, 1.05f);
            case VOLATILE -> new RangedPattern(4, 7.0f, .98f, .26f, true, 1.35f);
            case JUGGERNAUT -> new RangedPattern(2, 4.5f, .82f, .48f, true, 1.55f);
            case RAVAGER -> new RangedPattern(6, 9.0f, 1.04f, .20f, true, 1.05f);
            case AEGIS -> new RangedPattern(3, 5.0f, .94f, .31f, true, 1.20f);
            case HUNTER -> new RangedPattern(4, 3.0f, 1.10f, .30f, true, 1.10f);
            default -> new RangedPattern(3, 6.5f, .96f, .34f, true, 1.15f);
        };
    }

    private static RangedPattern staticSeer(Enemy.Variant variant) {
        return switch (variant) {
            case SWIFT -> new RangedPattern(6, 13.0f, 1.22f, .18f, false, 0f);
            case ARMORED -> new RangedPattern(3, 18.0f, 1.02f, .32f, false, 0f);
            case FERAL -> new RangedPattern(7, 15.0f, 1.16f, .16f, false, 0f);
            case VOLATILE -> new RangedPattern(5, 12.0f, 1.12f, .20f, true, 1.20f);
            case JUGGERNAUT -> new RangedPattern(3, 16.0f, .96f, .34f, true, 1.45f);
            case RAVAGER -> new RangedPattern(8, 17.0f, 1.20f, .14f, false, 0f);
            case AEGIS -> new RangedPattern(5, 13.0f, 1.08f, .22f, false, 0f);
            case HUNTER -> new RangedPattern(4, 6.0f, 1.28f, .26f, false, 0f);
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
        } else if (variant == Enemy.Variant.VOLATILE) {
            damage *= 1.18f;
            radius *= 1.16f;
            recovery *= .82f;
        } else if (variant == Enemy.Variant.JUGGERNAUT) {
            damage *= 1.35f;
            radius *= 1.22f;
            knockback *= 1.32f;
            recovery *= 1.18f;
        } else if (variant == Enemy.Variant.RAVAGER) {
            damage *= 1.42f;
            knockback *= 1.25f;
            recovery *= .68f;
        } else if (variant == Enemy.Variant.AEGIS) {
            damage *= 1.08f;
            radius *= 1.08f;
            recovery *= 1.06f;
        } else if (variant == Enemy.Variant.HUNTER) {
            damage *= 1.16f;
            knockback *= 1.08f;
            recovery *= .76f;
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
