package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class DirectionalGpuMemoryBudgetTest {
    private static final long MIB = 1024L * 1024L;
    private static final long BYTES_PER_RGBA8888_PIXEL = 4L;

    @Test void directionalLayersStayInsideTwentyMiBGpuBudget() {
        long baseDirectional = rgbaBytes(768, 1792);
        long highResCore = rgbaBytes(HighResDirectionalBootstrapArt.width(), HighResDirectionalBootstrapArt.height());
        long highResBoss = rgbaBytes(HighResBossDirectionalArt.width(), HighResBossDirectionalArt.height());
        long biomeDirectional = rgbaBytes(640, 896);

        assertTrue(baseDirectional <= 6L * MIB, "base directional sheet exceeded 6 MiB");
        assertTrue(highResCore <= 6L * MIB, "48px core sheet exceeded 6 MiB");
        assertTrue(highResBoss <= 6L * MIB, "64px boss sheet exceeded 6 MiB");
        assertTrue(biomeDirectional <= 3L * MIB, "biome directional sheet exceeded 3 MiB");

        long peakDirectionalBytes = baseDirectional + highResCore + highResBoss + biomeDirectional;
        assertTrue(peakDirectionalBytes <= 20L * MIB,
            "directional art peak exceeded 20 MiB: " + peakDirectionalBytes + " bytes");
    }

    private static long rgbaBytes(int width, int height) {
        return (long)width * height * BYTES_PER_RGBA8888_PIXEL;
    }
}
