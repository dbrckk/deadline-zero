package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Test;

final class CombatHudLayoutTest {
    @Test
    void hudV2StaysReadableAndNonOverlappingAcrossTargetFormats() {
        int[][] sizes = {{1280,720},{1536,691},{1920,1080},{1280,800}};
        float[] scales = {.85f, 1f, 1.3f};
        for (int[] size : sizes) {
            for (float scale : scales) {
                for (boolean boss : new boolean[] {false, true}) {
                    CombatHudLayout.Layout layout = CombatHudLayout.compute(size[0], size[1], scale, boss);
                    assertTrue(layout.survival().width >= 240f);
                    assertTrue(layout.levelBadge().width >= 72f);
                    assertTrue(layout.xpRail().height <= 14f);
                    assertTrue(layout.hordeStatus().width >= 220f);
                    assertFalse(layout.survival().overlaps(layout.hordeStatus()));
                    assertFalse(layout.toast().overlaps(playerSafeZone(layout)));
                    assertTrue(layout.toast().width <= layout.logicalWidth() * .30f);
                    assertTrue(layout.toast().height <= 44f * Math.max(1f, scale));
                    if (boss) {
                        assertNotNull(layout.boss());
                        assertFalse(layout.boss().overlaps(layout.toast()));
                        assertTrue(layout.boss().height <= 18f * Math.max(1f, scale));
                    }
                    assertTrue(layout.dashRadius() >= 32f);
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

    private static Rectangle playerSafeZone(CombatHudLayout.Layout layout) {
        float width = layout.logicalWidth() * .34f;
        float height = layout.logicalHeight() * .42f;
        return new Rectangle(
            (layout.logicalWidth() - width) * .5f,
            (layout.logicalHeight() - height) * .5f,
            width,
            height
        );
    }
}
