package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class RunRecoveryAdviceTest {
    @Test void earlyHighThreatFailureRecommendsSurvivability() {
        RunResult result = result(20, 6, 20f, 8);
        RunRecoveryAdvice.Advice advice = RunRecoveryAdvice.forResult(result);
        assertEquals(RunRecoveryAdvice.Focus.SURVIVABILITY, advice.focus());
        assertTrue(advice.detail().contains("Threat"));
    }

    @Test void slowMidRunClearRecommendsOffense() {
        RunResult result = result(10, 0, 80f, 8);
        RunRecoveryAdvice.Advice advice = RunRecoveryAdvice.forResult(result);
        assertEquals(RunRecoveryAdvice.Focus.OFFENSE, advice.focus());
    }

    @Test void lateFailureWithGoodClearSpeedRecommendsFinalDefense() {
        int stage = 12;
        float nearBoss = StageMissionRules.bossArrivalSeconds(stage) * .90f;
        RunResult result = result(stage, 2, nearBoss, 80);
        RunRecoveryAdvice.Advice advice = RunRecoveryAdvice.forResult(result);
        assertEquals(RunRecoveryAdvice.Focus.ENDGAME_DEFENSE, advice.focus());
    }

    @Test void adviceIsPureAndNullSafe() {
        assertEquals(RunRecoveryAdvice.Focus.BALANCED, RunRecoveryAdvice.forResult(null).focus());
    }

    private static RunResult result(int stage, int threat, float seconds, int kills) {
        return new RunResult(kills, seconds, false, stage,
            new RunRewardCalculator.Rewards(100, 50, 0), null,
            "STANDARD", 0, threat, 0, 0, 0);
    }
}
