package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.SurvivorCatalog;
import org.junit.jupiter.api.Test;

final class AnimationProfileCatalogTest {
    @Test
    void fastArchetypesAnimateFasterThanHeavyArchetypes() {
        var wraith = AnimationProfileCatalog.survivor(SurvivorCatalog.Survivor.WRAITH);
        var bastion = AnimationProfileCatalog.survivor(SurvivorCatalog.Survivor.BASTION);
        assertTrue(wraith.run() < bastion.run());
        assertTrue(wraith.attack() < bastion.attack());

        var runner = AnimationProfileCatalog.enemy(Enemy.Type.RUNNER);
        var brute = AnimationProfileCatalog.enemy(Enemy.Type.BRUTE);
        assertTrue(runner.run() < brute.run());
        assertTrue(runner.attack() < brute.attack());
    }

    @Test
    void everyProductionTimingIsSafeForRealtimeAnimation() {
        for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
            assertSafe(AnimationProfileCatalog.survivor(survivor));
        }
        for (Enemy.Type type : Enemy.Type.values()) {
            assertSafe(AnimationProfileCatalog.enemy(type));
        }
    }

    @Test
    void transientHitAndDeathAnimationsNeverLoop() {
        assertTrue(AnimationProfileCatalog.loops(GameArt.Motion.IDLE));
        assertTrue(AnimationProfileCatalog.loops(GameArt.Motion.RUN));
        assertTrue(AnimationProfileCatalog.loops(GameArt.Motion.ATTACK));
        assertFalse(AnimationProfileCatalog.loops(GameArt.Motion.HIT));
        assertFalse(AnimationProfileCatalog.loops(GameArt.Motion.DEATH));
    }

    private static void assertSafe(AnimationProfileCatalog.Profile profile) {
        assertRange(profile.idle());
        assertRange(profile.run());
        assertRange(profile.attack());
        assertRange(profile.hit());
        assertRange(profile.death());
    }

    private static void assertRange(float value) {
        assertTrue(value >= .04f && value <= .20f, "unsafe animation frame duration: " + value);
    }
}
