package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class EndgameMutatorRulesTest {
    @AfterEach void reset() {
        RunModifierContext.end();
        RunStageContext.begin(1, 0, 0);
    }

    @Test void mutatorsStayDisabledBelowThreatThree() {
        for (int threat = 0; threat < 3; threat++) {
            RunStageContext.begin(20, 7, threat);
            assertEquals(EndgameMutatorRules.Mutator.NONE, EndgameMutatorRules.current());
            assertFalse(EndgameMutatorRules.active());
            assertEquals(1f, EndgameMutatorRules.rewardMultiplier());
        }
    }

    @Test void sameRunIdentityAlwaysProducesSameMutator() {
        RunStageContext.begin(24, 13, 5);
        EndgameMutatorRules.Mutator first = EndgameMutatorRules.current();
        RunStageContext.begin(24, 13, 5);
        assertEquals(first, EndgameMutatorRules.current());
        assertNotEquals(EndgameMutatorRules.Mutator.NONE, first);
    }

    @Test void highThreatRotationExposesMultiplePressureProfiles() {
        boolean[] seen = new boolean[EndgameMutatorRules.Mutator.values().length];
        int count = 0;
        for (int ordinal = 0; ordinal < 12; ordinal++) {
            RunStageContext.begin(22, ordinal, 5);
            int index = EndgameMutatorRules.current().ordinal();
            if (!seen[index]) { seen[index] = true; count++; }
        }
        assertTrue(count >= 4);
    }

    @Test void everyMutatorStaysInsideCombatAndEconomyBudget() {
        for (EndgameMutatorRules.Mutator mutator : EndgameMutatorRules.Mutator.values()) {
            assertTrue(mutator.enemyHp >= .90f && mutator.enemyHp <= 1.10f);
            assertTrue(mutator.enemySpeed >= .98f && mutator.enemySpeed <= 1.05f);
            assertTrue(mutator.enemyDamage >= 1f && mutator.enemyDamage <= 1.10f);
            assertTrue(mutator.spawnInterval >= .88f && mutator.spawnInterval <= 1f);
            assertTrue(mutator.reward >= 1f && mutator.reward <= 1.08f);
        }
    }

    @Test void runModifierScalingIncludesCurrentMutatorWithoutChangingLowThreatRuns() {
        RunStageContext.begin(10, 2, 0);
        RunModifierContext.begin();
        float lowHp = RunModifierContext.modifier().enemyHp;
        float lowReward = RunModifierContext.modifier().reward;
        assertEquals(lowHp, RunModifierContext.enemyHpMultiplier(), .0001f);
        assertEquals(lowReward, RunModifierContext.rewardMultiplier(), .0001f);

        RunModifierContext.end();
        RunStageContext.begin(10, 2, 4);
        RunModifierContext.begin();
        assertEquals(RunModifierContext.modifier().enemyHp * EndgameMutatorRules.enemyHpMultiplier(),
            RunModifierContext.enemyHpMultiplier(), .0001f);
        assertEquals(RunModifierContext.modifier().reward * EndgameMutatorRules.rewardMultiplier(),
            RunModifierContext.rewardMultiplier(), .0001f);
    }

    @Test void activeRunTitleSurfacesMutatorWithoutChangingContractIdentity() {
        RunStageContext.begin(20, 9, 6);
        RunModifierContext.begin();
        String contract = RunModifierContext.modifier().title;
        assertTrue(RunModifierContext.title().startsWith(contract + " • "));
        assertTrue(RunModifierContext.title().endsWith(EndgameMutatorRules.label()));

        BalanceTelemetryRuntime.setContract(contract);
        BalanceRunSample sample = BalanceTelemetryRuntime.settle(false, 30f, 4);
        assertEquals(contract, sample.contract());
        assertEquals(EndgameMutatorRules.label(), sample.mutator());
    }
}
