package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class CombatHudLayoutTest {
    @Test
    void hudStaysNonOverlappingAcrossTargetFormatsAndUiScales() {
        int[][] sizes = {{1280,720},{1536,691},{1920,1080},{2400,1080},{2560,1600}};
        float[] scales = {.85f, 1f, 1.3f};
        for (int[] size : sizes) {
            for (float scale : scales) {
                for (boolean boss : new boolean[] {false, true}) {
                    CombatHudLayout.Layout layout = CombatHudLayout.compute(size[0], size[1], scale, boss);
                    assertTrue(layout.hp().width > 0f);
                    assertTrue(layout.xp().width > 0f);
                    assertFalse(layout.hp().overlaps(layout.xp()));
                    assertFalse(layout.timeline().overlaps(layout.hp()));
                    assertFalse(layout.timeline().overlaps(layout.xp()));
                    if (boss) {
                        assertTrue(layout.boss() != null);
                        assertFalse(layout.boss().overlaps(layout.timeline()));
                    }
                    assertTrue(layout.dashRadius() * 2f >= 56f);
                    assertTrue(layout.logicalWidth() >= 1280f);
                    assertTrue(layout.logicalHeight() >= 720f);
                }
            }
        }
    }

    @Test
    void physicalControlCoordinatesMapIntoLogicalHudSpace() {
        CombatHudLayout.Layout wide = CombatHudLayout.compute(1536, 691, 1f, false);
        float dashPhysicalX = 1536f - 58f;
        float dashPhysicalY = 62f;
        assertTrue(Math.abs(wide.toLogicalX(dashPhysicalX) - wide.dashX()) < 1f);
        assertTrue(Math.abs(wide.toLogicalY(dashPhysicalY) - wide.dashY()) < 1f);
    }
}
