package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class NullBootstrapVfxArtTest {
    @Test void exposesEveryNullArchonEffectAsEightFrameAnimation() {
        String[] keys = {
            "fx/null_archon_aura",
            "fx/null_archon_portal",
            "fx/null_archon_fracture"
        };
        for (int i = 0; i < keys.length; i++) {
            assertEquals(i * NullBootstrapVfxArt.FRAMES_PER_EFFECT, NullBootstrapVfxArt.firstTile(keys[i]));
        }
        assertEquals(24, NullBootstrapVfxArt.TOTAL_TILES);
    }

    @Test void rejectsUnknownEffects() {
        assertEquals(-1, NullBootstrapVfxArt.firstTile("fx/null_archon_unknown"));
        assertTrue(NullBootstrapVfxArt.firstTile(null) < 0);
    }
}
