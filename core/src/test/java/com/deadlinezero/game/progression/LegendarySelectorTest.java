package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Player;
import org.junit.jupiter.api.Test;

public final class LegendarySelectorTest {
    @Test public void offersBeginAtLevelEight() {
        Player p = new Player(0f, 0f);
        p.level = 7;
        assertFalse(LegendarySelector.shouldOffer(p));
        p.level = 8;
        assertTrue(LegendarySelector.shouldOffer(p));
    }

    @Test public void selectorNeverReturnsOwnedChoice() {
        Player p = new Player(0f, 0f);
        p.level = 16;
        assertTrue(LegendaryEffects.applyOverdrive(p));
        LegendaryChoice[] choices = new LegendaryChoice[3];
        int count = LegendarySelector.fillChoices(p, choices);
        assertEquals(2, count);
        for (int i = 0; i < count; i++) {
            assertNotNull(choices[i]);
            assertTrue(choices[i] != LegendaryChoice.OVERDRIVE);
        }
    }

    @Test public void allOwnedStopsFutureOffers() {
        Player p = new Player(0f, 0f);
        p.level = 20;
        assertTrue(LegendaryEffects.applyOverdrive(p));
        assertTrue(LegendaryEffects.applySingularity(p));
        assertTrue(LegendaryEffects.applyApex(p));
        assertFalse(LegendarySelector.shouldOffer(p));
    }
}
