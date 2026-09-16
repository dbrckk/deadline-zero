package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Test;

final class CombatUpgradeLayoutTest {
    @Test
    void threeChoiceOverlayIsCompactTouchSafeAndReadableAcrossPhoneFormats() {
        int[][] sizes = {{1280,720},{1536,691},{1920,1080},{2560,1600}};
        for (int[] size : sizes) {
            CombatUpgradeLayout.Layout layout = CombatUpgradeLayout.compute(size[0], size[1]);
            assertTrue(layout.panel().width <= layout.logicalWidth() * .82f);
            assertTrue(layout.panel().height <= layout.logicalHeight() * .58f);
            assertTrue(layout.cards().length == 3);
            for (Rectangle card : layout.cards()) {
                assertTrue(card.width >= 250f);
                assertTrue(card.height >= 210f);
                assertTrue(card.height <= layout.logicalHeight() * .42f);
                assertTrue(layout.panel().contains(card));
            }
            assertFalse(layout.cards()[0].overlaps(layout.cards()[1]));
            assertFalse(layout.cards()[1].overlaps(layout.cards()[2]));
            assertTrue(layout.footer().height >= 42f);
            assertTrue(layout.panel().contains(layout.footer()));
        }
    }
}
