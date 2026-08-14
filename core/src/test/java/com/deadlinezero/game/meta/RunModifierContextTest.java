package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

final class RunModifierContextTest {
    @AfterEach void cleanup() { RunModifierContext.end(); }

    @Test void selectionIsDeterministicForSameStageAndRunOrdinal() {
        RunStageContext.begin(7, 12);
        RunModifierContext.begin();
        RunModifierContext.Modifier first = RunModifierContext.modifier();
        RunModifierContext.end();

        RunStageContext.begin(7, 12);
        RunModifierContext.begin();
        assertEquals(first, RunModifierContext.modifier());
    }

    @Test void consecutiveRunsNeverRepeatTheSameContract() {
        RunModifierContext.Modifier previous = null;
        for (int ordinal = 0; ordinal < 40; ordinal++) {
            RunStageContext.begin(9, ordinal);
            RunModifierContext.begin();
            RunModifierContext.Modifier current = RunModifierContext.modifier();
            if (previous != null) assertNotEquals(previous, current);
            previous = current;
            RunModifierContext.end();
        }
    }

    @Test void rotationExposesEveryContractWithinFiveRuns() {
        EnumSet<RunModifierContext.Modifier> seen = EnumSet.noneOf(RunModifierContext.Modifier.class);
        for (int ordinal = 0; ordinal < 5; ordinal++) {
            RunStageContext.begin(9, ordinal);
            RunModifierContext.begin();
            seen.add(RunModifierContext.modifier());
            RunModifierContext.end();
        }
        assertEquals(EnumSet.allOf(RunModifierContext.Modifier.class), seen);
    }

    @Test void offerSetContainsThreeUniqueDeterministicContracts() {
        RunStageContext.begin(11, 27);
        RunModifierContext.Modifier[] first = RunModifierContext.offers();
        RunModifierContext.Modifier[] second = RunModifierContext.offers();

        assertEquals(3, first.length);
        assertEquals(first[0], second[0]);
        assertEquals(first[1], second[1]);
        assertEquals(first[2], second[2]);
        assertEquals(3, EnumSet.of(first[0], first[1], first[2]).size());
    }

    @Test void onlyOfferedContractsCanBeActivated() {
        RunStageContext.begin(4, 2);
        RunModifierContext.Modifier[] offers = RunModifierContext.offers();
        assertTrue(RunModifierContext.choose(offers[1]));
        assertEquals(offers[1], RunModifierContext.modifier());

        RunModifierContext.end();
        RunModifierContext.Modifier outsider = null;
        for (RunModifierContext.Modifier candidate : RunModifierContext.Modifier.values()) {
            if (candidate != offers[0] && candidate != offers[1] && candidate != offers[2]) {
                outsider = candidate;
                break;
            }
        }
        assertNotNull(outsider);
        assertFalse(RunModifierContext.choose(outsider));
        assertFalse(RunModifierContext.active());
    }

    @Test void activeContractAlwaysPaysARewardPremium() {
        RunStageContext.begin(6, 3);
        RunModifierContext.begin();
        assertNotNull(RunModifierContext.modifier());
        assertTrue(RunModifierContext.rewardMultiplier() > 1f);
        assertTrue(RunModifierContext.rewardBonusPercent() >= 18);
    }

    @Test void rewardCalculatorIncludesActiveContractPremium() {
        RunModifierContext.end();
        RunRewardCalculator.Rewards baseline = RunRewardCalculator.calculate(120, 240f, true, 6);

        RunStageContext.begin(6, 18);
        RunModifierContext.begin();
        RunRewardCalculator.Rewards contracted = RunRewardCalculator.calculate(120, 240f, true, 6);

        assertTrue(contracted.credits() > baseline.credits());
        assertTrue(contracted.accountXp() > baseline.accountXp());
        assertEquals(baseline.gems(), contracted.gems());
    }

    @Test void stageRulesApplyCombatContractMultipliers() {
        RunModifierContext.end();
        float hp = StageRules.enemyHpMultiplier(8);
        float speed = StageRules.enemySpeedMultiplier(8);
        float damage = StageRules.enemyDamageMultiplier(8);

        RunStageContext.begin(8, 5);
        RunModifierContext.begin();
        RunModifierContext.Modifier modifier = RunModifierContext.modifier();

        assertEquals(hp * modifier.enemyHp, StageRules.enemyHpMultiplier(8), .0001f);
        assertEquals(Math.min(1.60f, speed * modifier.enemySpeed), StageRules.enemySpeedMultiplier(8), .0001f);
        assertEquals(damage * modifier.enemyDamage, StageRules.enemyDamageMultiplier(8), .0001f);
    }
}
