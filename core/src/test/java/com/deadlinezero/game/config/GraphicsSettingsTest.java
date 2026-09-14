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

    @Test void qualityNavigationClampsAtEnds() {
        assertEquals(GraphicsSettings.Quality.LOW, GraphicsSettings.Quality.LOW.next(-1));
        assertEquals(GraphicsSettings.Quality.MEDIUM, GraphicsSettings.Quality.LOW.next(1));
        assertEquals(GraphicsSettings.Quality.ULTRA, GraphicsSettings.Quality.ULTRA.next(1));
        assertEquals(GraphicsSettings.Quality.HIGH, GraphicsSettings.Quality.ULTRA.next(-1));
    }

    @Test void frameRateTargetsAreExplicitAndClampAtEnds() {
        var values = GraphicsSettings.FrameRate.values();
        assertEquals(3, values.length);
        assertEquals(60, values[0].target);
        assertEquals(90, values[1].target);
        assertEquals(120, values[2].target);
        assertEquals(GraphicsSettings.FrameRate.FPS_60, GraphicsSettings.FrameRate.FPS_60.next(-1));
        assertEquals(GraphicsSettings.FrameRate.FPS_90, GraphicsSettings.FrameRate.FPS_60.next(1));
        assertEquals(GraphicsSettings.FrameRate.FPS_120, GraphicsSettings.FrameRate.FPS_120.next(1));
        assertEquals(GraphicsSettings.FrameRate.FPS_90, GraphicsSettings.FrameRate.FPS_120.next(-1));
    }
}
