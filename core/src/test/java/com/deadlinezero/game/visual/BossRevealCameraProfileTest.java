package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BossRevealCameraProfileTest {
    @Test void revealEnvelopeStartsAndEndsAtRest() {
        assertEquals(0f, BossRevealCameraProfile.envelope(BossRevealCameraProfile.DURATION), .0001f);
        assertEquals(0f, BossRevealCameraProfile.envelope(0f), .0001f);
        float midpoint = BossRevealCameraProfile.envelope(BossRevealCameraProfile.DURATION * .5f);
        assertTrue(midpoint > .98f);
    }

    @Test void revealNeverExceedsComfortBounds() {
        for (int i = 0; i <= 20; i++) {
            float remaining = BossRevealCameraProfile.DURATION * i / 20f;
            float e = BossRevealCameraProfile.envelope(remaining);
            assertTrue(e >= 0f && e <= 1.001f);
            assertTrue(BossRevealCameraProfile.focusWeight(e, false)
                <= BossRevealCameraProfile.MAX_FOCUS_WEIGHT + .0001f);
            assertTrue(BossRevealCameraProfile.zoom(.88f, e, false)
                <= .88f + BossRevealCameraProfile.MAX_ZOOM_OUT + .0001f);
        }
    }

    @Test void reducedMotionDisablesSpecialCameraMovement() {
        assertEquals(0f, BossRevealCameraProfile.focusWeight(1f, true), .0001f);
        assertEquals(.88f, BossRevealCameraProfile.zoom(.88f, 1f, true), .0001f);
    }
}
