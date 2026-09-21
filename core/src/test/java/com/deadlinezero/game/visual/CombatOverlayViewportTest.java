package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class CombatOverlayViewportTest {
    @Test void normalizesWidePhoneHiDpiSurfaceToLogicalHudSpace() {
        CombatOverlayViewport.Viewport v = CombatOverlayViewport.compute(2880, 1620);
        assertEquals(1280f, v.width(), .001f);
        assertEquals(720f, v.height(), .001f);
        assertEquals(1280f / 2880f, v.scaleX(), .0001f);
        assertEquals(720f / 1620f, v.scaleY(), .0001f);
    }

    @Test void threeChoiceCentersStayInsideLogicalViewport() {
        CombatOverlayViewport.Viewport v = CombatOverlayViewport.compute(2880, 1620);
        for (int i = 0; i < 3; i++) {
            float center = v.width() * ((i + 1f) / 4f);
            assertTrue(center > 0f && center < v.width());
        }
        assertEquals(960f, v.width() * .75f, .001f);
    }

    @Test void physicalTouchMappingPreservesChoiceColumns() {
        CombatOverlayViewport.Viewport v = CombatOverlayViewport.compute(2880, 1620);
        float physicalX = 2160f; // 75% of the reported Android surface.
        float logicalX = v.toLogicalX(physicalX);
        assertEquals(960f, logicalX, .001f);
        assertEquals(2, Math.min(2, (int)(logicalX / v.width() * 3f)));
    }
}
