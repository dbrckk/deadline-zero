package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deadlinezero.game.meta.RunStageContext;
import org.junit.jupiter.api.Test;

final class EnemyProjectileStyleTest {
    @Test void defaultStyleTracksActiveBiome() {
        RunStageContext.begin(1, 0, 0);
        assertEquals(EnemyProjectile.Style.DEFAULT, EnemyProjectile.defaultStyleForActiveBiome());

        RunStageContext.begin(10, 0, 0);
        assertEquals(EnemyProjectile.Style.CINDER, EnemyProjectile.defaultStyleForActiveBiome());

        RunStageContext.begin(20, 0, 0);
        assertEquals(EnemyProjectile.Style.NULL, EnemyProjectile.defaultStyleForActiveBiome());
    }

    @Test void nullSectorThinNonExplosiveVolleyUsesStaticIdentity() {
        RunStageContext.begin(20, 0, 0);
        assertEquals(EnemyProjectile.Style.STATIC,
            EnemyProjectile.defaultStyleForActiveContext(.18f, false));
        assertEquals(EnemyProjectile.Style.NULL,
            EnemyProjectile.defaultStyleForActiveContext(.22f, false));
        assertEquals(EnemyProjectile.Style.NULL,
            EnemyProjectile.defaultStyleForActiveContext(.18f, true));
    }

    @Test void explicitSourceStyleOverridesBiomeFallback() {
        RunStageContext.begin(20, 0, 0);
        EnemyProjectile projectile = new EnemyProjectile().spawn(
            0f, 0f, 1f, 0f, 10f, .2f, 2f, false, 0f, EnemyProjectile.Style.CINDER);
        assertEquals(EnemyProjectile.Style.CINDER, projectile.style);
    }
}
