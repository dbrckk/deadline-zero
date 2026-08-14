package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Enemy;

final class BiomeEnemyRosterTest {
    @Test void quarantineKeepsStandardPopulation() {
        assertEquals(BiomeEnemyRoster.Identity.NONE, BiomeEnemyRoster.identityFor(9, Enemy.Type.RUNNER));
        assertEquals(Enemy.Type.SHAMBLER, BiomeEnemyRoster.remap(9, .1f, Enemy.Type.SHAMBLER));
    }

    @Test void foundryMapsThreeSignatureEnemies() {
        assertEquals(BiomeEnemyRoster.Identity.FORGE_HOUND, BiomeEnemyRoster.identityFor(10, Enemy.Type.RUNNER));
        assertEquals(BiomeEnemyRoster.Identity.CINDER_GUNNER, BiomeEnemyRoster.identityFor(15, Enemy.Type.RANGED));
        assertEquals(BiomeEnemyRoster.Identity.SLAG_GUARD, BiomeEnemyRoster.identityFor(19, Enemy.Type.SHIELDED));
        assertEquals(Enemy.Type.RUNNER, BiomeEnemyRoster.remap(10, .10f, Enemy.Type.SHAMBLER));
        assertEquals(Enemy.Type.RANGED, BiomeEnemyRoster.remap(10, .25f, Enemy.Type.SHAMBLER));
        assertEquals(Enemy.Type.SHIELDED, BiomeEnemyRoster.remap(10, .40f, Enemy.Type.SHAMBLER));
    }

    @Test void nullSectorMapsThreeSignatureEnemies() {
        assertEquals(BiomeEnemyRoster.Identity.PHASE_STALKER, BiomeEnemyRoster.identityFor(20, Enemy.Type.PHANTOM));
        assertEquals(BiomeEnemyRoster.Identity.STATIC_SEER, BiomeEnemyRoster.identityFor(25, Enemy.Type.RANGED));
        assertEquals(BiomeEnemyRoster.Identity.NULL_WARD, BiomeEnemyRoster.identityFor(30, Enemy.Type.REGENERATOR));
        assertEquals(Enemy.Type.PHANTOM, BiomeEnemyRoster.remap(20, .10f, Enemy.Type.SHAMBLER));
        assertEquals(Enemy.Type.RANGED, BiomeEnemyRoster.remap(20, .30f, Enemy.Type.SHAMBLER));
        assertEquals(Enemy.Type.REGENERATOR, BiomeEnemyRoster.remap(20, .48f, Enemy.Type.SHAMBLER));
    }

    @Test void elementalResistanceProfilesAreBoundedAndSpecific() {
        assertEquals(.62f, BiomeEnemyRoster.elementalDamageMultiplier(10, Enemy.Type.RUNNER, DamageElement.FIRE), .0001f);
        assertEquals(1f, BiomeEnemyRoster.elementalDamageMultiplier(10, Enemy.Type.RUNNER, DamageElement.SHOCK), .0001f);
        assertEquals(.58f, BiomeEnemyRoster.elementalDamageMultiplier(20, Enemy.Type.RANGED, DamageElement.SHOCK), .0001f);
        assertEquals(.66f, BiomeEnemyRoster.elementalDamageMultiplier(20, Enemy.Type.REGENERATOR, DamageElement.FROST), .0001f);
        for (BiomeEnemyRoster.Identity identity : BiomeEnemyRoster.Identity.values()) {
            assertTrue(identity.resistanceMultiplier > 0f && identity.resistanceMultiplier <= 1f);
        }
    }

    @Test void bossesAreNeverRemappedOrAssignedARegularIdentity() {
        assertEquals(Enemy.Type.BOSS, BiomeEnemyRoster.remap(25, .1f, Enemy.Type.BOSS));
        assertEquals(BiomeEnemyRoster.Identity.NONE, BiomeEnemyRoster.identityFor(25, Enemy.Type.BOSS));
    }
}
