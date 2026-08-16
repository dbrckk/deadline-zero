package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class StageCombatPressureAuditTest {
    @Test void lateGamePressureIsStrictlyMonotonic() {
        float previous = StageCombatPressureAudit.snapshot(9).compositePressure();
        for (int stage = 10; stage <= 30; stage++) {
            float current = StageCombatPressureAudit.snapshot(stage).compositePressure();
            assertTrue(current > previous, "stage " + stage + " pressure must exceed previous stage");
            previous = current;
        }
    }

    @Test void foundryBoundaryAddsContentWithoutAnUnfairSpike() {
        float jump = StageCombatPressureAudit.relativeJump(9, 10);
        assertTrue(jump > .20f, "Foundry should feel materially harder than stage 9");
        assertTrue(jump < .27f, "Foundry boundary pressure spike too large: " + jump);
    }

    @Test void nullSectorBoundaryRemainsReadable() {
        float jump = StageCombatPressureAudit.relativeJump(19, 20);
        assertTrue(jump > .10f, "Null Sector should introduce a meaningful pressure step");
        assertTrue(jump < .15f, "Null Sector boundary pressure spike too large: " + jump);
    }

    @Test void postBoundaryStageStepsStayControlled() {
        for (int stage = 11; stage <= 30; stage++) {
            if (stage == 20) continue;
            float jump = StageCombatPressureAudit.relativeJump(stage - 1, stage);
            assertTrue(jump < .21f, "stage " + stage + " pressure jump too large: " + jump);
        }
    }

    @Test void biomeHazardPressureIsAbsentThenExplicit() {
        assertTrue(!Float.isFinite(StageCombatPressureAudit.snapshot(9).hazardInterval()));
        assertTrue(Float.isFinite(StageCombatPressureAudit.snapshot(10).hazardInterval()));
        assertTrue(Float.isFinite(StageCombatPressureAudit.snapshot(20).hazardInterval()));
        assertTrue(StageCombatPressureAudit.snapshot(20).nominalHazardDamage()
            > StageCombatPressureAudit.snapshot(10).nominalHazardDamage());
    }
}
