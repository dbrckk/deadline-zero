package com.deadlinezero.game.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.world.BiomeEnemyRoster;

final class EnemyContentScaleTest {
    @Test void p5EnemyAndEliteProfileTargetsAreExplicitlyMet() {
        int baseEnemyArchetypes = Enemy.Type.values().length - 1; // BOSS counted separately.
        int biomeSignatureProfiles = BiomeEnemyRoster.Identity.values().length - 1; // NONE is fallback.
        int championProfiles = Enemy.Variant.values().length - 1; // NORMAL is baseline.

        assertEquals(8, baseEnemyArchetypes);
        assertEquals(6, biomeSignatureProfiles);
        assertEquals(8, championProfiles);
        assertTrue(baseEnemyArchetypes + biomeSignatureProfiles + championProfiles >= 20,
            "gameplay enemy profile count regressed below P5 20+ target");
        assertTrue(championProfiles >= 8, "champion/elite profile count regressed below P5 8+ target");
    }

    @Test void championRollCoversAllEightProfilesWithStableBoundaries() {
        assertEquals(Enemy.Variant.SWIFT, Enemy.variantForRoll(0f));
        assertEquals(Enemy.Variant.ARMORED, Enemy.variantForRoll(.125f));
        assertEquals(Enemy.Variant.FERAL, Enemy.variantForRoll(.25f));
        assertEquals(Enemy.Variant.VOLATILE, Enemy.variantForRoll(.375f));
        assertEquals(Enemy.Variant.JUGGERNAUT, Enemy.variantForRoll(.5f));
        assertEquals(Enemy.Variant.RAVAGER, Enemy.variantForRoll(.625f));
        assertEquals(Enemy.Variant.AEGIS, Enemy.variantForRoll(.75f));
        assertEquals(Enemy.Variant.HUNTER, Enemy.variantForRoll(.875f));
        assertEquals(Enemy.Variant.HUNTER, Enemy.variantForRoll(1f));
        assertEquals(Enemy.Variant.SWIFT, Enemy.variantForRoll(-1f));
    }
}
