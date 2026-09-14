package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;

final class EnemyVariantBalanceTest {
    @Test void allChampionRangedPatternsStayInsideProductionBounds() {
        for (Enemy.Variant variant : Enemy.Variant.values()) {
            if (variant == Enemy.Variant.NORMAL) continue;
            var p = EnemyPatternCatalog.ranged(variant);
            assertTrue(p.shots() >= 1 && p.shots() <= 8, variant + " shots");
            assertTrue(p.spreadDegrees() >= 0f && p.spreadDegrees() <= 20f, variant + " spread");
            assertTrue(p.speedMultiplier() >= .80f && p.speedMultiplier() <= 1.35f, variant + " speed");
            assertTrue(p.damageMultiplier() >= .10f && p.damageMultiplier() <= 1.55f, variant + " damage");
            if (p.explosive()) assertTrue(p.explosionRadius() >= 1f && p.explosionRadius() <= 2f, variant + " radius");
        }
    }

    @Test void allChampionChargePatternsStayInsideProductionBounds() {
        for (Enemy.Variant variant : Enemy.Variant.values()) {
            if (variant == Enemy.Variant.NORMAL) continue;
            var p = EnemyPatternCatalog.charge(Enemy.Type.ELITE, variant);
            assertTrue(p.impactDamageMultiplier() >= .75f && p.impactDamageMultiplier() <= 3f, variant + " damage");
            assertTrue(p.impactRadius() >= .8f && p.impactRadius() <= 2f, variant + " radius");
            assertTrue(p.knockbackStrength() >= .7f && p.knockbackStrength() <= 2.5f, variant + " knockback");
            assertTrue(p.recoveryMultiplier() >= .45f && p.recoveryMultiplier() <= 1.6f, variant + " recovery");
        }
    }
}
