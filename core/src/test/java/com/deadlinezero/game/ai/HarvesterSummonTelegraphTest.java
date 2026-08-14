package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class HarvesterSummonTelegraphTest {
    @Test void exposesDeterministicWarningBeforeSummon() {
        BossCombatRuntime runtime = new BossCombatRuntime(BossIdentity.HARVESTER);
        assertFalse(runtime.summonTelegraphing(2));
        runtime.update(7.40f, 2);
        assertTrue(runtime.summonTelegraphing(2));
        float progress = runtime.summonTelegraphProgress(2);
        assertTrue(progress > 0f && progress < 1f);
        runtime.update(.61f, 2);
        assertFalse(runtime.summonTelegraphing(2));
        assertTrue(runtime.consumeSummon(2));
        assertFalse(runtime.summonTelegraphing(2));
    }

    @Test void phaseOneNeverTelegraphsSummons() {
        BossCombatRuntime runtime = new BossCombatRuntime(BossIdentity.HARVESTER);
        runtime.update(8f, 1);
        assertFalse(runtime.summonTelegraphing(1));
        assertTrue(runtime.summonTelegraphProgress(1) == 0f);
    }

    @Test void warningWindowIsLongEnoughToRead() {
        assertTrue(BossCombatRuntime.summonTelegraphSeconds() >= .65f);
        assertTrue(BossCombatRuntime.summonTelegraphSeconds() <= .90f);
    }
}
