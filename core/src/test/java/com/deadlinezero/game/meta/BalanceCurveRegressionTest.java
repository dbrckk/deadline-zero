package com.deadlinezero.game.meta;


/** Release gates for campaign/endgame pacing and reward curves. */
public final class BalanceCurveRegressionTest {
    public static void bossArrivalPacingIsBoundedAndMonotonic() {
        float previous = 0f;
        for (int stage = 1; stage <= 40; stage++) {
            float seconds = StageMissionRules.bossArrivalSeconds(stage);
            check("boss arrival regressed at stage " + stage, seconds >= previous, "balance invariant failed");
            check(seconds <= 600f, "boss arrival exceeds ten-minute first-playable ceiling");
            previous = seconds;
        }
        near(StageMissionRules.bossArrivalSeconds(1), 360f, "StageMissionRules.bossArrivalSeconds(1)");
        near(StageMissionRules.bossArrivalSeconds(17), 600f, "StageMissionRules.bossArrivalSeconds(17)");
    }

    public static void threatCurveRaisesRiskAndRewardsWithoutSpeedRunaway() {
        float previousHp = 0f, previousDamage = 0f, previousReward = 0f;
        for (int tier = 0; tier <= ThreatTierRules.MAX_TIER; tier++) {
            float hp = ThreatTierRules.enemyHpMultiplier(tier);
            float damage = ThreatTierRules.enemyDamageMultiplier(tier);
            float speed = ThreatTierRules.enemySpeedMultiplier(tier);
            float spawn = ThreatTierRules.spawnIntervalMultiplier(tier);
            float reward = ThreatTierRules.rewardMultiplier(tier);
            check(hp >= previousHp, "balance invariant failed");
            check(damage >= previousDamage, "balance invariant failed");
            check(reward >= previousReward, "balance invariant failed");
            check(speed <= 1.28f, "threat speed exceeds readability ceiling");
            check(spawn >= .72f, "spawn interval falls below density floor");
            previousHp = hp; previousDamage = damage; previousReward = reward;
        }
        near(ThreatTierRules.enemyHpMultiplier(20), 4.0f, "ThreatTierRules.enemyHpMultiplier(20)");
        near(ThreatTierRules.enemyDamageMultiplier(20), 2.10f, "ThreatTierRules.enemyDamageMultiplier(20)");
        near(ThreatTierRules.rewardMultiplier(20), 2.50f, "ThreatTierRules.rewardMultiplier(20)");
    }

    public static void campaignBaseCurveIsStrictlyProgressive() {
        RunStageContext.begin(1, 0, 0);
        RunModifierContext.end();
        float hp = 0f, damage = 0f, reward = 0f;
        for (int stage = 1; stage <= 20; stage++) {
            float nextHp = StageRules.enemyHpMultiplier(stage);
            float nextDamage = StageRules.enemyDamageMultiplier(stage);
            float nextReward = StageRules.rewardMultiplier(stage);
            check(nextHp > hp, "balance invariant failed");
            check(nextDamage > damage, "balance invariant failed");
            check(nextReward > reward, "balance invariant failed");
            check(StageRules.enemySpeedMultiplier(stage) <= 1.78f, "campaign speed exceeds global ceiling");
            hp = nextHp; damage = nextDamage; reward = nextReward;
        }
    }

    public static void firstClearRewardsRemainProgressive() {
        long credits = 0;
        int gems = 0;
        for (int stage = 1; stage <= 20; stage++) {
            long nextCredits = StageMissionRules.firstClearCredits(stage);
            int nextGems = StageMissionRules.firstClearGems(stage);
            check(nextCredits > credits, "balance invariant failed");
            check(nextGems >= gems, "balance invariant failed");
            credits = nextCredits; gems = nextGems;
        }
        check(StageMissionRules.firstClearGems(20) <= 30, "first-clear gems must stay bounded");
    }
    private static void near(float actual, float expected, String label) {
        check(Math.abs(actual - expected) <= .001f, label + ": expected " + expected + ", got " + actual);
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        bossArrivalPacingIsBoundedAndMonotonic();
        threatCurveRaisesRiskAndRewardsWithoutSpeedRunaway();
        campaignBaseCurveIsStrictlyProgressive();
        firstClearRewardsRemainProgressive();
    }
}
