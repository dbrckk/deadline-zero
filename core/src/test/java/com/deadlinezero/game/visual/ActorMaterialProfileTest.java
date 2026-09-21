package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Enemy;
import org.junit.jupiter.api.Test;

final class ActorMaterialProfileTest {
    @Test void standardCrowdEnemiesStaySingleDraw() {
        assertFalse(ActorMaterialProfile.enemy(Enemy.Type.SHAMBLER, Enemy.Variant.NORMAL).outline());
        assertFalse(ActorMaterialProfile.enemy(Enemy.Type.RUNNER, Enemy.Variant.NORMAL).outline());
        assertFalse(ActorMaterialProfile.enemy(Enemy.Type.BRUTE, Enemy.Variant.NORMAL).outline());
        assertFalse(ActorMaterialProfile.enemy(Enemy.Type.RANGED, Enemy.Variant.NORMAL).outline());
    }

    @Test void priorityActorsReceiveSilhouetteReinforcement() {
        assertTrue(ActorMaterialProfile.player().outline());
        assertTrue(ActorMaterialProfile.enemy(Enemy.Type.BOSS, Enemy.Variant.NORMAL).outline());
        assertTrue(ActorMaterialProfile.enemy(Enemy.Type.ELITE, Enemy.Variant.NORMAL).outline());
        assertTrue(ActorMaterialProfile.enemy(Enemy.Type.SHIELDED, Enemy.Variant.NORMAL).outline());
        assertTrue(ActorMaterialProfile.enemy(Enemy.Type.REGENERATOR, Enemy.Variant.NORMAL).outline());
        assertTrue(ActorMaterialProfile.enemy(Enemy.Type.PHANTOM, Enemy.Variant.NORMAL).outline());
        assertTrue(ActorMaterialProfile.enemy(Enemy.Type.SHAMBLER, Enemy.Variant.FERAL).outline());
    }

    @Test void reinforcementStaysSubtle() {
        for (ActorMaterialProfile.Profile p : new ActorMaterialProfile.Profile[] {
            ActorMaterialProfile.player(),
            ActorMaterialProfile.enemy(Enemy.Type.BOSS, Enemy.Variant.NORMAL),
            ActorMaterialProfile.enemy(Enemy.Type.ELITE, Enemy.Variant.NORMAL),
            ActorMaterialProfile.enemy(Enemy.Type.SHAMBLER, Enemy.Variant.SWIFT)
        }) {
            assertTrue(p.scale() >= 1f && p.scale() <= 1.08f);
            assertTrue(p.alpha() >= 0f && p.alpha() <= .80f);
        }
    }
}
