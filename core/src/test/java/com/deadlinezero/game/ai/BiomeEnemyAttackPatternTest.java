package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.RunStageContext;

public final class BiomeEnemyAttackPatternTest {
    @AfterEach void resetStage() { RunStageContext.begin(1); }

    @Test public void cinderGunnerUsesExplosiveBurstInsteadOfSingleShot() {
        RunStageContext.begin(10);
        var p = EnemyPatternCatalog.ranged(Enemy.Variant.NORMAL);
        assertEquals(3, p.shots());
        assertEquals(6.5f, p.spreadDegrees(), .001f);
        assertTrue(p.explosive());
        assertEquals(1.15f, p.explosionRadius(), .001f);
        assertTrue(p.shots() * p.damageMultiplier() <= 1.05f);
    }

    @Test public void staticSeerUsesFastWideZoningFan() {
        RunStageContext.begin(20);
        var p = EnemyPatternCatalog.ranged(Enemy.Variant.NORMAL);
        assertEquals(5, p.shots());
        assertEquals(15f, p.spreadDegrees(), .001f);
        assertTrue(p.speedMultiplier() > 1.1f);
        assertFalse(p.explosive());
        assertEquals(1f, p.shots() * p.damageMultiplier(), .001f);
    }

    @Test public void forgeHoundPounceIsFastRecoveryAndCompact() {
        RunStageContext.begin(10);
        var p = EnemyPatternCatalog.charge(Enemy.Type.RUNNER, Enemy.Variant.NORMAL);
        assertTrue(p.recoveryMultiplier() < .7f);
        assertTrue(p.impactRadius() < 1f);
        assertTrue(p.impactDamageMultiplier() > 1.2f);
    }

    @Test public void slagGuardRamIsHeavyAndWide() {
        RunStageContext.begin(10);
        var p = EnemyPatternCatalog.charge(Enemy.Type.SHIELDED, Enemy.Variant.NORMAL);
        assertTrue(p.impactDamageMultiplier() > 1.7f);
        assertTrue(p.impactRadius() > 1.3f);
        assertTrue(p.knockbackStrength() > 1.4f);
        assertTrue(p.recoveryMultiplier() > 1.2f);
    }

    @Test public void quarantinePatternsRemainHistorical() {
        RunStageContext.begin(1);
        var ranged = EnemyPatternCatalog.ranged(Enemy.Variant.NORMAL);
        assertEquals(1, ranged.shots());
        assertEquals(1f, ranged.damageMultiplier(), .001f);
        assertFalse(ranged.explosive());
        var charge = EnemyPatternCatalog.charge(Enemy.Type.BRUTE, Enemy.Variant.NORMAL);
        assertEquals(1.38f, charge.impactDamageMultiplier(), .001f);
        assertEquals(1.12f, charge.impactRadius(), .001f);
    }
}
