package com.deadlinezero.game.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class GraphicsSettingsTest {
    @Test void profilesAreStrictlyOrderedAndBounded() {
        var values = GraphicsSettings.Quality.values();
        assertEquals(4, values.length);
        for (int i = 0; i < values.length; i++) {
            assertTrue(values[i].fxCeiling >= .40f && values[i].fxCeiling <= 1f);
            if (i > 0) assertTrue(values[i].fxCeiling > values[i - 1].fxCeiling);
        }
    }

    @Test void navigationClampsAtEnds() {
        assertEquals(GraphicsSettings.Quality.LOW, GraphicsSettings.Quality.LOW.next(-1));
        assertEquals(GraphicsSettings.Quality.MEDIUM, GraphicsSettings.Quality.LOW.next(1));
        assertEquals(GraphicsSettings.Quality.ULTRA, GraphicsSettings.Quality.ULTRA.next(1));
        assertEquals(GraphicsSettings.Quality.HIGH, GraphicsSettings.Quality.ULTRA.next(-1));
    }
}
