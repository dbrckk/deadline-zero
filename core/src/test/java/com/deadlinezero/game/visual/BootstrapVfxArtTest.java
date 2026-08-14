package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BootstrapVfxArtTest {
    private static final String[] EFFECTS = {
        "fx/muzzle_fire",
        "fx/dash",
        "fx/level_up",
        "fx/impact_energy",
        "fx/impact_fire",
        "fx/impact_frost",
        "fx/impact_shock",
        "fx/impact_kill",
        "fx/boss_explosion",
        "fx/legendary_overdrive",
        "fx/legendary_singularity",
        "fx/legendary_apex"
    };

    @Test void everyRequiredEffectHasMultipleFrames() {
        assertEquals(ArtManifest.REQUIRED_FX.length, EFFECTS.length);
        for (int i = 0; i < EFFECTS.length; i++) {
            String key = EFFECTS[i];
            assertEquals(i, BootstrapVfxArt.effectIndex(key), key);
            assertTrue(BootstrapVfxArt.firstTile(key) >= 0, key);
            assertTrue(BootstrapVfxArt.frameCount(key) >= 4, key);
            assertEquals(ArtManifest.REQUIRED_FX[i], key);
        }
    }

    @Test void effectBlocksAreContiguousAndNonOverlapping() {
        int previousLast = -1;
        for (String effect : EFFECTS) {
            int first = BootstrapVfxArt.firstTile(effect);
            assertEquals(previousLast + 1, first);
            previousLast = first + BootstrapVfxArt.FRAMES_PER_EFFECT - 1;
        }
        assertEquals(BootstrapVfxArt.TOTAL_TILES - 1, previousLast);
        assertEquals(EFFECTS.length * BootstrapVfxArt.FRAMES_PER_EFFECT, BootstrapVfxArt.TOTAL_TILES);
    }

    @Test void sheetStaysSmallAndMobileSafe() {
        assertTrue(BootstrapVfxArt.width() <= 1024);
        assertTrue(BootstrapVfxArt.height() <= 1024);
        assertEquals(384, BootstrapVfxArt.width());
        assertEquals(576, BootstrapVfxArt.height());
    }

    @Test void malformedOrUnknownEffectsAreRejected() {
        assertEquals(-1, BootstrapVfxArt.effectIndex(null));
        assertEquals(-1, BootstrapVfxArt.firstTile("fx/unknown"));
        assertEquals(0, BootstrapVfxArt.frameCount("fx/unknown"));
        assertEquals(-1, BootstrapVfxArt.firstTile("muzzle_fire"));
    }
}
