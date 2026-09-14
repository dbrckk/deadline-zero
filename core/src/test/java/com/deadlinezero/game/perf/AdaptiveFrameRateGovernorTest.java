package com.deadlinezero.game.perf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class AdaptiveFrameRateGovernorTest {
    @Test void startsAtSelectedTarget() {
        AdaptiveFrameRateGovernor governor = new AdaptiveFrameRateGovernor();
        governor.reset(120);
        assertEquals(120, governor.effectiveTarget());
    }

    @Test void sustainedInstabilityStepsDownOneTier() {
        AdaptiveFrameRateGovernor governor = new AdaptiveFrameRateGovernor();
        governor.reset(120);
        PerformanceTelemetry.Snapshot bad = new PerformanceTelemetry.Snapshot(120, 80f, 20f, 25f, .20f, false);
        governor.update(120, bad);
        governor.update(120, bad);
        assertEquals(120, governor.effectiveTarget());
        governor.update(120, bad);
        assertEquals(90, governor.effectiveTarget());
    }

    @Test void recoveryIsSlowerThanDegradation() {
        AdaptiveFrameRateGovernor governor = new AdaptiveFrameRateGovernor();
        governor.reset(120);
        PerformanceTelemetry.Snapshot bad120 = new PerformanceTelemetry.Snapshot(120, 80f, 20f, 25f, .20f, false);
        for (int i = 0; i < 3; i++) governor.update(120, bad120);
        assertEquals(90, governor.effectiveTarget());

        PerformanceTelemetry.Snapshot stable90 = new PerformanceTelemetry.Snapshot(90, 90f, 11f, 12f, 0f, true);
        for (int i = 0; i < 7; i++) governor.update(120, stable90);
        assertEquals(90, governor.effectiveTarget());
        governor.update(120, stable90);
        assertEquals(120, governor.effectiveTarget());
    }

    @Test void userCeilingAlwaysWinsImmediately() {
        AdaptiveFrameRateGovernor governor = new AdaptiveFrameRateGovernor();
        governor.reset(120);
        PerformanceTelemetry.Snapshot stable120 = new PerformanceTelemetry.Snapshot(120, 120f, 8f, 9f, 0f, true);
        assertEquals(60, governor.update(60, stable120));
    }

    @Test void normalizesUnsupportedTargets() {
        AdaptiveFrameRateGovernor governor = new AdaptiveFrameRateGovernor();
        governor.reset(75);
        assertEquals(60, governor.effectiveTarget());
        governor.reset(100);
        assertEquals(90, governor.effectiveTarget());
        governor.reset(144);
        assertEquals(120, governor.effectiveTarget());
    }
}
