package com.deadlinezero.game.meta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** Release gates for campaign/endgame pacing and reward curves. */
public final class BalanceCurveRegressionTest {
    @Test public void bossArrivalPacingIsBoundedAndMonotonic() {
        float previous = 0f;
        for (int stage = 1; stage <= 40; stage++) {
            float seconds = StageMissionRules.bossArrivalSeconds(stage);
            assertTrue("boss arrival regressed at stage " + stage, seconds >= previous);
            assertTrue("boss arrival exceeds ten-minute first-playable ceiling", seconds <= 600f);
            previous = seconds;
        }
        assertEquals(360f, StageMissionRules.bossArrivalSeconds(1), .001f);
        assertEquals(600f, StageMissionRules.bossArrivalSeconds(17), .001f);
    }

    @Test public void threatCurveRaisesRiskAndRewardsWithoutSpeedRunaway() {
        float previousHp = 0f, previousDamage = 0f, previousReward = 0f;
        for (int tier = 0; tier <= ThreatTierRules.MAX_TIER; tier++) {
            float hp = ThreatTierRules.enemyHpMultiplier(tier);
            float damage = ThreatTierRules.enemyDamageMultiplier(tier);
            float speed = ThreatTierRules.enemySpeedMultiplier(tier);
            float spawn = ThreatTierRules.spawnIntervalMultiplier(tier);
            float reward = ThreatTierRules.rewardMultiplier(tier);
            assertTrue(hp >= previousHp);
            assertTrue(damage >= previousDamage);
            assertTrue(reward >= previousReward);
            assertTrue("threat speed exceeds readability ceiling", speed <= 1.28f);
            assertTrue("spawn interval falls below density floor", spawn >= .72f);
            previousHp = hp; previousDamage = damage; previousReward = reward;
        }
        assertEquals(4.0f, ThreatTierRules.enemyHpMultiplier(20), .001f);
        assertEquals(2.10f, ThreatTierRules.enemyDamageMultiplier(20), .001f);
        assertEquals(2.50f, ThreatTierRules.rewardMultiplier(20), .001f);
    }

    @Test public void campaignBaseCurveIsStrictlyProgressive() {
        RunStageContext.begin(1, 0, 0);
        RunModifierContext.end();
        float hp = 0f, damage = 0f, reward = 0f;
        for (int stage = 1; stage <= 20; stage++) {
            float nextHp = StageRules.enemyHpMultiplier(stage);
            float nextDamage = StageRules.enemyDamageMultiplier(stage);
            float nextReward = StageRules.rewardMultiplier(stage);
            assertTrue(nextHp > hp);
            assertTrue(nextDamage > damage);
            assertTrue(nextReward > reward);
            assertTrue("campaign speed exceeds global ceiling", StageRules.enemySpeedMultiplier(stage) <= 1.78f);
            hp = nextHp; damage = nextDamage; reward = nextReward;
        }
    }

    @Test public void firstClearRewardsRemainProgressive() {
        long credits = 0;
        int gems = 0;
        for (int stage = 1; stage <= 20; stage++) {
            long nextCredits = StageMissionRules.firstClearCredits(stage);
            int nextGems = StageMissionRules.firstClearGems(stage);
            assertTrue(nextCredits > credits);
            assertTrue(nextGems >= gems);
            credits = nextCredits; gems = nextGems;
        }
        assertTrue("first-clear gems must stay bounded", StageMissionRules.firstClearGems(20) <= 30);
    }
}
