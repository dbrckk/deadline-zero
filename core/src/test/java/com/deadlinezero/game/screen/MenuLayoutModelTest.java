package com.deadlinezero.game.screen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.Rectangle;
import com.deadlinezero.game.ui.UiLayout;
import org.junit.jupiter.api.Test;

final class MenuLayoutModelTest {
    @Test
    void homeLayoutIsContainedAndNonOverlappingAtBaselineAndWidePhone() {
        int[][] sizes = {{1280, 720}, {1536, 691}};
        for (int[] size : sizes) {
            UiLayout.Metrics metrics = UiLayout.compute(size[0], size[1]);
            MenuLayoutModel.Layout layout = MenuLayoutModel.layout(metrics);

            assertContained(layout.survivorCard(), metrics);
            assertContained(layout.loadoutCard(), metrics);
            assertContained(layout.threatCard(), metrics);
            assertContained(layout.deploy(), metrics);
            assertTrue(layout.deploy().height >= metrics.touchTarget());
            assertFalse(layout.survivorCard().overlaps(layout.deploy()));
            assertFalse(layout.loadoutCard().overlaps(layout.deploy()));
            assertFalse(layout.threatCard().overlaps(layout.deploy()));
        }
    }

    @Test
    void bottomTabsAreEvenAndTouchSafe() {
        UiLayout.Metrics metrics = UiLayout.compute(1536, 691);
        MenuLayoutModel.Layout layout = MenuLayoutModel.layout(metrics);
        Rectangle[] tabs = layout.bottomTabs();

        assertEquals(6, tabs.length);
        float width = tabs[0].width;
        for (Rectangle tab : tabs) {
            assertEquals(width, tab.width, .01f);
            assertTrue(tab.width >= metrics.touchTarget());
            assertTrue(tab.height >= metrics.touchTarget());
            assertTrue(layout.bottomNav().contains(tab));
        }
    }

    private static void assertContained(Rectangle r, UiLayout.Metrics m) {
        assertTrue(r.x >= m.safeLeft() - .01f);
        assertTrue(r.y >= m.safeBottom() - .01f);
        assertTrue(r.x + r.width <= m.safeRight() + .01f);
        assertTrue(r.y + r.height <= m.safeTop() + .01f);
    }
}
