package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.entities.Enemy;

final class SpecialistPresentationTest {
    @Test void specialistProfilesArePurposefullyDistinct() {
        var shambler = ArtProfileCatalog.enemy(Enemy.Type.SHAMBLER);
        var shielded = ArtProfileCatalog.enemy(Enemy.Type.SHIELDED);
        var regenerator = ArtProfileCatalog.enemy(Enemy.Type.REGENERATOR);
        var phantom = ArtProfileCatalog.enemy(Enemy.Type.PHANTOM);

        assertTrue(shielded.height() > shambler.height());
        assertTrue(regenerator.height() > shambler.height());
        assertTrue(phantom.height() > 0f);
        assertNotEquals(shambler, shielded);
        assertNotEquals(shambler, regenerator);
        assertNotEquals(shambler, phantom);
    }
}
