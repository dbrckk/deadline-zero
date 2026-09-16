package com.deadlinezero.game.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Test;

final class ResponsiveGridTest {
    @Test
    void baselineUsesTwoColumnsAndWidePhoneUsesThree() {
        UiLayout.Metrics baseline = UiLayout.compute(1280, 720);
        UiLayout.Metrics wide = UiLayout.compute(1536, 691);

        ResponsiveGrid.Spec baseGrid = ResponsiveGrid.compute(baseline.contentWidth(), 420f, 3, 16f);
        ResponsiveGrid.Spec wideGrid = ResponsiveGrid.compute(wide.contentWidth(), 420f, 3, 16f);

        assertEquals(2, baseGrid.columns());
        assertEquals(3, wideGrid.columns());
        assertTrue(baseGrid.cardWidth() >= 300f);
        assertTrue(wideGrid.cardWidth() >= 300f);
    }

    @Test
    void targetFormatsProduceTouchSafeNonOverlappingCards() {
        int[][] sizes = {{1280,720},{1536,691},{1920,1080},{2400,1080},{2560,1600}};
        for (int[] size : sizes) {
            UiLayout.Metrics m = UiLayout.compute(size[0], size[1]);
            ResponsiveGrid.Spec spec = ResponsiveGrid.compute(m.contentWidth(), 420f, 3, 16f);
            Rectangle a = ResponsiveGrid.cardBounds(0, m.safeLeft(), m.contentTop(), 108f, spec);
            Rectangle b = ResponsiveGrid.cardBounds(1, m.safeLeft(), m.contentTop(), 108f, spec);
            assertTrue(a.width >= 300f);
            assertTrue(a.height >= m.touchTarget());
            assertTrue(b.x >= a.x + a.width + 15.9f);
            assertTrue(b.x + b.width <= m.safeRight() + .01f);
        }
    }
}
