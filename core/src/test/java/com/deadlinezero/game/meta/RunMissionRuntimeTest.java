package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class RunMissionRuntimeTest {
    @AfterEach void cleanup() { RunMissionRuntime.end(); }

    @Test void standardMissionSignalsVictoryAfterOneBoss() {
        AtomicInteger callbacks = new AtomicInteger();
        RunMissionRuntime.begin(callbacks::incrementAndGet, 1);
        RunMissionRuntime.signalBossDefeated();
        assertEquals(1, RunMissionRuntime.bossKills());
        assertEquals(1, callbacks.get());
        RunMissionRuntime.signalBossDefeated();
        assertEquals(1, callbacks.get());
    }

    @Test void twinMissionWaitsForSecondBossAndSignalsOnce() {
        AtomicInteger callbacks = new AtomicInteger();
        RunMissionRuntime.begin(callbacks::incrementAndGet, 2);
        RunMissionRuntime.signalBossDefeated();
        assertEquals(1, RunMissionRuntime.bossKills());
        assertEquals(0, callbacks.get());
        RunMissionRuntime.signalBossDefeated();
        assertEquals(2, RunMissionRuntime.bossKills());
        assertEquals(1, callbacks.get());
        RunMissionRuntime.signalBossDefeated();
        assertEquals(1, callbacks.get());
    }

    @Test void requiredBossCountIsSanitized() {
        RunMissionRuntime.begin(() -> {}, 0);
        assertEquals(1, RunMissionRuntime.requiredBossKills());
    }
}
