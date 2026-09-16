package com.deadlinezero.game.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class UiLayoutTest {
    @Test
    void widePhoneExtendsHorizontallyWithoutShrinkingLogicalHeight() {
        UiLayout.Metrics m = UiLayout.compute(1536, 691);

        assertEquals(720f, m.height(), 0.01f);
        assertTrue(m.width() > 1280f);
        assertTrue(m.safeLeft() >= 24f);
        assertTrue(m.safeRight() <= m.width() - 24f);
        assertTrue(m.contentWidth() >= 1180f);
    }

    @Test
    void requiredFormatsKeepSafeFrameAndTouchTargets() {
        int[][] sizes = {
            {1280, 720},
            {1536, 691},
            {1920, 1080},
            {2400, 1080},
            {2560, 1600}
        };

        for (int[] size : sizes) {
            UiLayout.Metrics m = UiLayout.compute(size[0], size[1]);
            assertEquals(720f, m.height(), 0.01f);
            assertTrue(m.contentWidth() > 0f);
            assertTrue(m.touchTarget() >= 56f);
            assertTrue(m.safeBottom() < m.safeTop());
            assertTrue(m.headerBottom() <= m.safeTop());
            assertTrue(m.footerTop() >= m.safeBottom());
            assertTrue(m.contentBottom() < m.contentTop());
        }
    }

    @Test
    void invalidPhysicalDimensionsFallBackToBaseline() {
        UiLayout.Metrics m = UiLayout.compute(0, 0);
        assertEquals(1280f, m.width(), 0.01f);
        assertEquals(720f, m.height(), 0.01f);
    }
}
