package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test void rotationExposesEveryContractAcrossRunHistory() {
        EnumSet<RunModifierContext.Modifier> seen = EnumSet.noneOf(RunModifierContext.Modifier.class);
        for (int ordinal = 0; ordinal < 80; ordinal++) {
            RunStageContext.begin(9, ordinal);
            RunModifierContext.begin();
            seen.add(RunModifierContext.modifier());
            RunModifierContext.end();
        }
        assertEquals(EnumSet.allOf(RunModifierContext.Modifier.class), seen);
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
