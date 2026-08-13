package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class StageRulesTest {
    @Test
    void stageOneUsesNeutralMultipliers() {
        assertEquals(1f, StageRules.enemyHpMultiplier(1), 0.0001f);
        assertEquals(1f, StageRules.enemyDamageMultiplier(1), 0.0001f);
        assertEquals(1f, StageRules.enemySpeedMultiplier(1), 0.0001f);
        assertEquals(1f, StageRules.rewardMultiplier(1), 0.0001f);
    }

    @Test
    void invalidStagesClampToStageOne() {
        assertEquals(StageRules.enemyHpMultiplier(1), StageRules.enemyHpMultiplier(-12), 0.0001f);
        assertEquals(StageRules.enemyDamageMultiplier(1), StageRules.enemyDamageMultiplier(0), 0.0001f);
        assertEquals(2, StageRules.nextStage(0));
    }

    @Test
    void scalingRemainsMonotonicAcrossCampaignRange() {
        float previousHp = StageRules.enemyHpMultiplier(1);
        float previousDamage = StageRules.enemyDamageMultiplier(1);
        float previousReward = StageRules.rewardMultiplier(1);
        for (int stage = 2; stage <= 100; stage++) {
            float hp = StageRules.enemyHpMultiplier(stage);
            float damage = StageRules.enemyDamageMultiplier(stage);
            float reward = StageRules.rewardMultiplier(stage);
            assertTrue(hp > previousHp);
            assertTrue(damage > previousDamage);
            assertTrue(reward > previousReward);
            previousHp = hp;
            previousDamage = damage;
            previousReward = reward;
        }
    }

    @Test
    void enemySpeedHasHardSafetyCap() {
        assertTrue(StageRules.enemySpeedMultiplier(1000) <= 1.42f);
        assertEquals(1.42f, StageRules.enemySpeedMultiplier(1000), 0.0001f);
    }

    @Test
    void nextStageAlwaysAdvancesExactlyOneFromClampedInput() {
        assertEquals(2, StageRules.nextStage(1));
        assertEquals(26, StageRules.nextStage(25));
    }
}
