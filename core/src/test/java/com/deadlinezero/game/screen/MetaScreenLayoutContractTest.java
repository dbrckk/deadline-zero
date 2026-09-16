package com.deadlinezero.game.screen;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.Rectangle;
import com.deadlinezero.game.ui.MetaLayout;
import com.deadlinezero.game.ui.UiLayout;
import org.junit.jupiter.api.Test;

final class MetaScreenLayoutContractTest {
    @Test
    void sharedMetaLayoutKeepsPanelsAndActionsInsideSafeFrame() {
        int[][] sizes = {{1280, 720}, {1536, 691}};
        for (int[] size : sizes) {
            UiLayout.Metrics metrics = UiLayout.compute(size[0], size[1]);
            MetaLayout.Layout layout = MetaLayout.compute(metrics);

            assertContained(layout.header(), metrics);
            assertContained(layout.content(), metrics);
            assertContained(layout.footer(), metrics);
            assertContained(layout.back(), metrics);

            Rectangle[] columns = MetaLayout.columns(layout.content(), 3, 18f);
            for (Rectangle column : columns) assertContained(column, metrics);
            assertFalse(columns[0].overlaps(columns[1]));
            assertFalse(columns[1].overlaps(columns[2]));

            Rectangle[] actions = MetaLayout.actions(layout.footer(), 4, 12f);
            for (Rectangle action : actions) {
                assertTrue(action.height >= metrics.touchTarget());
                assertContained(action, metrics);
            }
        }
    }

    @Test
    void cardRowsStayTouchSafe() {
        UiLayout.Metrics metrics = UiLayout.compute(1536, 691);
        MetaLayout.Layout layout = MetaLayout.compute(metrics);
        Rectangle[] rows = MetaLayout.rows(layout.content(), 5, 12f);
        for (Rectangle row : rows) assertTrue(row.height >= metrics.touchTarget());
    }

    private static void assertContained(Rectangle r, UiLayout.Metrics m) {
        assertTrue(r.x >= m.safeLeft() - .01f);
        assertTrue(r.y >= m.safeBottom() - .01f);
        assertTrue(r.x + r.width <= m.safeRight() + .01f);
        assertTrue(r.y + r.height <= m.safeTop() + .01f);
    }
}
