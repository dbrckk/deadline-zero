package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.world.ArenaHazardRuntime;

final class NullHazardPresentationTest {
    @Test void nullTypesRouteToDistinctProfilesAndCues() {
        NullHazardPresentation.Profile rift = NullHazardPresentation.forType(ArenaHazardRuntime.Type.VOID_RIFT);
        NullHazardPresentation.Profile statik = NullHazardPresentation.forType(ArenaHazardRuntime.Type.STATIC_BURST);
        NullHazardPresentation.Profile beam = NullHazardPresentation.forType(ArenaHazardRuntime.Type.NULL_BEAM);

        assertEquals(AudioDirector.Cue.NULL_RIFT, rift.cue);
        assertEquals(AudioDirector.Cue.NULL_STATIC, statik.cue);
        assertEquals(AudioDirector.Cue.NULL_BEAM, beam.cue);
        assertNotEquals(rift.pulseSpeed, statik.pulseSpeed);
        assertNotEquals(statik.pulseSpeed, beam.pulseSpeed);
        assertTrue(rift.spokes > 0);
        assertTrue(statik.spokes > 0);
        assertTrue(beam.spokes > 0);
    }

    @Test void nullClassificationDoesNotCaptureOtherBiomeHazards() {
        assertTrue(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.VOID_RIFT));
        assertTrue(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.STATIC_BURST));
        assertTrue(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.NULL_BEAM));
        assertFalse(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.LAVA_VENT));
        assertFalse(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.ORBITAL_STRIKE));
    }
}
