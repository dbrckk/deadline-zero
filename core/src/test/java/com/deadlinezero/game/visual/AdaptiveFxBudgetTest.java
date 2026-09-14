package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class AdaptiveFxBudgetTest {
    @Test void externalCeilingCanReduceQualityImmediately() {
        AdaptiveFxBudget budget = new AdaptiveFxBudget();
        budget.setExternalCeiling(.58f);
        assertEquals(.58f, budget.quality(), .0001f);
    }

    @Test void recoveryIsGradualToAvoidThermalOscillation() {
        AdaptiveFxBudget budget = new AdaptiveFxBudget();
        budget.setExternalCeiling(.58f);
        assertEquals(.58f, budget.quality(), .0001f);
        budget.setExternalCeiling(1f);
        assertEquals(.58f, budget.quality(), .0001f);
        budget.advanceExternalCeiling(.1f);
        assertTrue(budget.quality() > .58f);
        assertTrue(budget.quality() < 1f);
    }

    @Test void externalCeilingIsClampedToSupportedRange() {
        AdaptiveFxBudget budget = new AdaptiveFxBudget();
        budget.setExternalCeiling(.10f);
        assertEquals(.40f, budget.quality(), .0001f);
        budget.setExternalCeiling(2f);
        for (int i = 0; i < 400; i++) budget.advanceExternalCeiling(.1f);
        assertEquals(1f, budget.quality(), .003f);
    }
}
