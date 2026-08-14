package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Projectile;

final class SingularityCoreRuntimeTest {
    @AfterEach void cleanup() { SingularityCoreRuntime.begin(false); }

    @Test void disabledRuntimeNeverMarksShots() {
        SingularityCoreRuntime.begin(false);
        for (int i = 0; i < 12; i++) assertFalse(SingularityCoreRuntime.consumeShotMark());
        assertEquals(0L, SingularityCoreRuntime.shotSequence());
    }

    @Test void activeRuntimeMarksExactlyEverySixthShot() {
        SingularityCoreRuntime.begin(true);
        for (int i = 1; i <= 18; i++) {
            assertEquals(i % SingularityCoreRules.SHOT_INTERVAL == 0, SingularityCoreRuntime.consumeShotMark());
        }
        assertEquals(18L, SingularityCoreRuntime.shotSequence());
    }

    @Test void beginResetsCadenceForEveryRun() {
        SingularityCoreRuntime.begin(true);
        for (int i = 0; i < 5; i++) assertFalse(SingularityCoreRuntime.consumeShotMark());
        SingularityCoreRuntime.begin(true);
        for (int i = 0; i < 5; i++) assertFalse(SingularityCoreRuntime.consumeShotMark());
        assertTrue(SingularityCoreRuntime.consumeShotMark());
    }

    @Test void sixthProjectileBecomesSingularityShockRound() {
        SingularityCoreRuntime.begin(true);
        Projectile projectile = null;
        for (int i = 0; i < 6; i++) {
            projectile = new Projectile().spawn(0f, 0f, 10f, 0f, 100f, false, 1, 2f, DamageElement.KINETIC);
        }
        assertTrue(projectile.singularity);
        assertEquals(135f, projectile.damage, .0001f);
        assertEquals(3, projectile.penetrationRemaining);
        assertEquals(3.6f, projectile.knockback, .0001f);
        assertEquals(.16f, projectile.radius, .0001f);
        assertEquals(DamageElement.SHOCK, projectile.element);
    }

    @Test void ordinaryProjectilesKeepOriginalCombatProfile() {
        SingularityCoreRuntime.begin(true);
        Projectile projectile = new Projectile().spawn(0f, 0f, 10f, 0f, 100f, true, 1, 2f, DamageElement.FIRE);
        assertFalse(projectile.singularity);
        assertEquals(100f, projectile.damage, .0001f);
        assertEquals(1, projectile.penetrationRemaining);
        assertEquals(2f, projectile.knockback, .0001f);
        assertEquals(.11f, projectile.radius, .0001f);
        assertEquals(DamageElement.FIRE, projectile.element);
    }
}
