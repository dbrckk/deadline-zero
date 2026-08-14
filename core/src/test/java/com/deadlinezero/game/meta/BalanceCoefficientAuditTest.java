package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class BalanceCoefficientAuditTest {
    @Test void stageCurveStaysMonotonicWithoutSpikes() {
        assertTrue(BalanceCoefficientAudit.stageCurvesHealthy());
    }

    @Test void threatCurveStaysMonotonicAndRewarded() {
        assertTrue(BalanceCoefficientAudit.threatCurvesHealthy());
    }
}
