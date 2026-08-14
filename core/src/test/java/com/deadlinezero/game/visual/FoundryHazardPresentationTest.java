package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.world.ArenaHazardRuntime;

final class FoundryHazardPresentationTest {
    @Test void foundryTypesRouteToDistinctProfilesAndCues() {
        FoundryHazardPresentation.Profile lava = FoundryHazardPresentation.forType(ArenaHazardRuntime.Type.LAVA_VENT);
        FoundryHazardPresentation.Profile steam = FoundryHazardPresentation.forType(ArenaHazardRuntime.Type.STEAM_JET);
        FoundryHazardPresentation.Profile heat = FoundryHazardPresentation.forType(ArenaHazardRuntime.Type.HEAT_LINE);

        assertEquals(AudioDirector.Cue.FOUNDRY_LAVA, lava.cue);
        assertEquals(AudioDirector.Cue.FOUNDRY_STEAM, steam.cue);
        assertEquals(AudioDirector.Cue.FOUNDRY_HEAT, heat.cue);
        assertNotEquals(lava.pulseSpeed, steam.pulseSpeed);
        assertNotEquals(steam.pulseSpeed, heat.pulseSpeed);
        assertTrue(lava.spokes > 0);
        assertTrue(steam.spokes > 0);
        assertTrue(heat.spokes > 0);
    }

    @Test void onlyBiomeSpecificTypesAreClassifiedAsFoundry() {
        assertTrue(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.LAVA_VENT));
        assertTrue(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.STEAM_JET));
        assertTrue(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.HEAT_LINE));
        assertFalse(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.ORBITAL_STRIKE));
        assertFalse(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.DEATH_BURST));
    }
}
