package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.EnemyProjectile;
import com.deadlinezero.game.meta.RunStageContext;

final class HostileProjectilePresentationTest {
    @AfterEach void reset() { RunStageContext.begin(1, 0, 0); }

    @Test void cinderGunnerAndStaticSeerUseDistinctSourceStyles() {
        RunStageContext.begin(12, 4, 0);
        Enemy cinder = new Enemy(Enemy.Type.RANGED, 0f, 0f, 50f, 2f, .4f, 10f, 5);
        assertEquals(EnemyProjectile.Style.CINDER, HostileProjectilePresentation.styleFor(cinder));

        RunStageContext.begin(22, 4, 0);
        Enemy seer = new Enemy(Enemy.Type.RANGED, 0f, 0f, 50f, 2f, .4f, 10f, 5);
        assertEquals(EnemyProjectile.Style.STATIC, HostileProjectilePresentation.styleFor(seer));
    }

    @Test void nullSupportAndPhantomUseVoidIdentity() {
        RunStageContext.begin(22, 5, 0);
        Enemy ward = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 80f, 2f, .45f, 10f, 5);
        Enemy stalker = new Enemy(Enemy.Type.PHANTOM, 0f, 0f, 60f, 3f, .4f, 10f, 5);
        assertEquals(EnemyProjectile.Style.NULL, HostileProjectilePresentation.styleFor(ward));
        assertEquals(EnemyProjectile.Style.NULL, HostileProjectilePresentation.styleFor(stalker));
    }

    @Test void ordinaryEarlyGameEnemyRemainsDefault() {
        RunStageContext.begin(4, 2, 0);
        Enemy ranged = new Enemy(Enemy.Type.RANGED, 0f, 0f, 50f, 2f, .4f, 10f, 5);
        assertEquals(EnemyProjectile.Style.DEFAULT, HostileProjectilePresentation.styleFor(ranged));
    }

    @Test void presentationMultipliersNeverChangeCollisionRadiusContract() {
        for (EnemyProjectile.Style style : EnemyProjectile.Style.values()) {
            float multiplier = HostileProjectilePresentation.coreRadiusMultiplier(style);
            assertTrue(multiplier >= .80f && multiplier <= 1.25f);
        }
    }
}
