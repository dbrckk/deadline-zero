package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;

public final class EnemyPatternCatalogTest {
    @Test public void rangedVariantsHaveDistinctThreatProfiles() {
        var normal = EnemyPatternCatalog.ranged(Enemy.Variant.NORMAL);
        var swift = EnemyPatternCatalog.ranged(Enemy.Variant.SWIFT);
        var armored = EnemyPatternCatalog.ranged(Enemy.Variant.ARMORED);
        var feral = EnemyPatternCatalog.ranged(Enemy.Variant.FERAL);

        assertTrue(swift.shots() > normal.shots());
        assertTrue(feral.shots() > swift.shots());
        assertTrue(armored.explosive());
        assertFalse(normal.explosive());
        assertTrue(armored.damageMultiplier() > normal.damageMultiplier());
    }

    @Test public void eliteChargeIsMoreThreateningThanBruteCharge() {
        var brute = EnemyPatternCatalog.charge(Enemy.Type.BRUTE, Enemy.Variant.NORMAL);
        var elite = EnemyPatternCatalog.charge(Enemy.Type.ELITE, Enemy.Variant.NORMAL);
        assertTrue(elite.impactDamageMultiplier() > brute.impactDamageMultiplier());
        assertTrue(elite.impactRadius() > brute.impactRadius());
        assertTrue(elite.knockbackStrength() > brute.knockbackStrength());
    }

    @Test public void feralChargeTradesRecoveryForAggression() {
        var normal = EnemyPatternCatalog.charge(Enemy.Type.BRUTE, Enemy.Variant.NORMAL);
        var feral = EnemyPatternCatalog.charge(Enemy.Type.BRUTE, Enemy.Variant.FERAL);
        assertTrue(feral.impactDamageMultiplier() > normal.impactDamageMultiplier());
        assertTrue(feral.recoveryMultiplier() < normal.recoveryMultiplier());
    }
}
