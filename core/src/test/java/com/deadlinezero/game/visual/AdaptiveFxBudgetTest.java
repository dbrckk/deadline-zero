package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

final class AdaptiveFxBudgetTest {
    @Test void externalCeilingCanReduceQualityImmediately() {
        AdaptiveFxBudget budget = new AdaptiveFxBudget();
        budget.setExternalCeiling(.58f);
        assertEquals(.58f, budget.quality(), .0001f);
    }

    @Test void externalCeilingIsClampedToSupportedRange() {
        AdaptiveFxBudget budget = new AdaptiveFxBudget();
        budget.setExternalCeiling(.10f);
        assertEquals(.40f, budget.quality(), .0001f);
        budget.setExternalCeiling(2f);
        assertEquals(1f, budget.quality(), .0001f);
    }
}
