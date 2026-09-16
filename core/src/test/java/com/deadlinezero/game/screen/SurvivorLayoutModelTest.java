package com.deadlinezero.game.screen;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.Rectangle;
import com.deadlinezero.game.ui.UiLayout;
import org.junit.jupiter.api.Test;

final class SurvivorLayoutModelTest {
    @Test
    void rosterLayoutStaysReadableAtBaselineAndWidePhone() {
        int[][] sizes = {{1280, 720}, {1536, 691}};
        for (int[] size : sizes) {
            UiLayout.Metrics metrics = UiLayout.compute(size[0], size[1]);
            SurvivorLayoutModel.Layout layout = SurvivorLayoutModel.layout(metrics);

            assertContained(layout.card(), metrics);
            assertContained(layout.portrait(), metrics);
            assertContained(layout.stats(), metrics);
            assertContained(layout.xpBar(), metrics);
            assertContained(layout.cta(), metrics);
            assertTrue(layout.cta().height >= metrics.touchTarget());
            assertTrue(layout.previous().width >= metrics.touchTarget());
            assertTrue(layout.next().width >= metrics.touchTarget());
            assertFalse(layout.portrait().overlaps(layout.stats()));
            assertFalse(layout.stats().overlaps(layout.cta()));
            assertFalse(layout.xpBar().overlaps(layout.cta()));
        }
    }

    @Test
    void navigationAndSelectionAreExplicitTargets() {
        UiLayout.Metrics metrics = UiLayout.compute(1536, 691);
        SurvivorLayoutModel.Layout layout = SurvivorLayoutModel.layout(metrics);
        Rectangle empty = new Rectangle(layout.card().x + layout.card().width * .45f,
            layout.card().y + 12f, 30f, 30f);

        assertFalse(layout.previous().overlaps(layout.next()));
        assertFalse(layout.cta().overlaps(layout.previous()));
        assertFalse(layout.cta().overlaps(layout.next()));
        assertFalse(layout.cta().overlaps(empty));
    }

    private static void assertContained(Rectangle r, UiLayout.Metrics m) {
        assertTrue(r.x >= m.safeLeft() - .01f);
        assertTrue(r.y >= m.safeBottom() - .01f);
        assertTrue(r.x + r.width <= m.safeRight() + .01f);
        assertTrue(r.y + r.height <= m.safeTop() + .01f);
    }
}
