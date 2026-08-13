package com.deadlinezero.game.ai;

import com.deadlinezero.game.entities.Enemy;

/** Pure combat pattern tuning kept outside GameScreen so enemy attacks remain testable and data-driven. */
public final class EnemyPatternCatalog {
    public record RangedPattern(int shots, float spreadDegrees, float speedMultiplier,
                                float damageMultiplier, boolean explosive, float explosionRadius) { }

    public record ChargePattern(float impactDamageMultiplier, float impactRadius,
                                float knockbackStrength, float recoveryMultiplier) { }

    private EnemyPatternCatalog() { }

    public static RangedPattern ranged(Enemy.Variant variant) {
        if (variant == null) variant = Enemy.Variant.NORMAL;
        return switch (variant) {
            case SWIFT -> new RangedPattern(3, 7.5f, 1.14f, .68f, false, 0f);
            case ARMORED -> new RangedPattern(1, 0f, .88f, 1.34f, true, 1.65f);
            case FERAL -> new RangedPattern(5, 12f, 1.04f, .54f, false, 0f);
            default -> new RangedPattern(1, 0f, 1f, 1f, false, 0f);
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
        return new ChargePattern(damage, radius, knockback, recovery);
    }
}
