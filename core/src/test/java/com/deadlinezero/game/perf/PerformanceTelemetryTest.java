package com.deadlinezero.game.perf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class PerformanceTelemetryTest {
    @Test void stableSixtyFpsWindowPasses() {
        PerformanceTelemetry telemetry = new PerformanceTelemetry();
        for (int i = 0; i < 120; i++) telemetry.record(1f / 60f, 60);
        PerformanceTelemetry.Snapshot s = telemetry.snapshot(60);
        assertEquals(60, s.targetFps());
        assertTrue(s.averageFps() >= 59.9f);
        assertTrue(s.p95FrameMs() < 17f);
        assertEquals(0f, s.jankRatio(), .0001f);
        assertTrue(s.stable());
    }

    @Test void sustainedSlowFramesFailTarget() {
        PerformanceTelemetry telemetry = new PerformanceTelemetry();
        for (int i = 0; i < 120; i++) telemetry.record(1f / 45f, 60);
        PerformanceTelemetry.Snapshot s = telemetry.snapshot(60);
        assertTrue(s.averageFps() < 50f);
        assertFalse(s.stable());
    }

    @Test void jankBurstsAreDetected() {
        PerformanceTelemetry telemetry = new PerformanceTelemetry();
        for (int i = 0; i < 110; i++) telemetry.record(1f / 60f, 60);
        for (int i = 0; i < 10; i++) telemetry.record(.050f, 60);
        PerformanceTelemetry.Snapshot s = telemetry.snapshot(60);
        assertTrue(s.jankRatio() > .05f);
        assertTrue(s.p95FrameMs() >= 50f);
        assertFalse(s.stable());
    }

    @Test void invalidSamplesAreIgnored() {
        PerformanceTelemetry telemetry = new PerformanceTelemetry();
        telemetry.record(0f, 60);
        telemetry.record(Float.NaN, 60);
        telemetry.record(-1f, 60);
        assertEquals(0, telemetry.sampleCount());
        assertFalse(telemetry.snapshot(60).stable());
    }

    @Test void rollingWindowIsBounded() {
        PerformanceTelemetry telemetry = new PerformanceTelemetry();
        for (int i = 0; i < 1000; i++) telemetry.record(1f / 120f, 120);
        assertEquals(240, telemetry.sampleCount());
        assertTrue(telemetry.snapshot(120).stable());
    }
}
