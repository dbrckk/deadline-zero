package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BalanceTelemetryRuntimeTest {
    @Test void runAccumulatorCapturesCombatAndResetsBetweenRuns() {
        BalanceTelemetryRuntime.begin(12, 7, 5);
        BalanceTelemetryRuntime.setContract("BLOOD MOON");
        BalanceTelemetryRuntime.recordDamageDealt(120f);
        BalanceTelemetryRuntime.recordDamageDealt(30f);
        BalanceTelemetryRuntime.recordDamageReceived(40f);

        BalanceRunSample first = BalanceTelemetryRuntime.settle(true, 60f, 24);
        assertEquals(12, first.stage());
        assertEquals(5, first.threatTier());
        assertEquals(7, first.runOrdinal());
        assertEquals("BLOOD MOON", first.contract());
        assertEquals(150f, first.damageDealt(), .001f);
        assertEquals(40f, first.damageReceived(), .001f);
        assertEquals(120f, first.maxHitDealt(), .001f);
        assertEquals(40f, first.maxHitReceived(), .001f);
        assertEquals(2.5f, first.dps(), .001f);
        assertTrue(first.victory());
        assertFalse(BalanceTelemetryRuntime.active());

        BalanceTelemetryRuntime.begin(2, 8, 0);
        BalanceRunSample second = BalanceTelemetryRuntime.settle(false, 30f, 2);
        assertEquals(0f, second.damageDealt(), .001f);
        assertEquals(0f, second.damageReceived(), .001f);
        assertEquals("STANDARD", second.contract());
        assertFalse(second.victory());
    }

    @Test void invalidDamageNeverPoisonsTelemetry() {
        BalanceTelemetryRuntime.begin(1, 0, 0);
        BalanceTelemetryRuntime.recordDamageDealt(Float.NaN);
        BalanceTelemetryRuntime.recordDamageDealt(Float.POSITIVE_INFINITY);
        BalanceTelemetryRuntime.recordDamageReceived(-50f);
        BalanceRunSample sample = BalanceTelemetryRuntime.settle(false, Float.NaN, -9);
        assertEquals(0f, sample.damageDealt(), .001f);
        assertEquals(0f, sample.damageReceived(), .001f);
        assertEquals(0f, sample.seconds(), .001f);
        assertEquals(0, sample.kills());
    }
}
