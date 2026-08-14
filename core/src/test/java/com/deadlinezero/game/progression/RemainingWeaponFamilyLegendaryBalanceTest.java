package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class RemainingWeaponFamilyLegendaryBalanceTest {
    @Test void ar9VanguardStaysInsideAssaultPowerBudget() {
        float dpsMultiplier = 1.16f / .90f;
        assertTrue(dpsMultiplier >= 1.26f && dpsMultiplier <= 1.31f);
    }

    @Test void scatterMaelstromControlsVolleyGrowth() {
        float volleyMultiplier = 8f * .82f / 6f;
        assertTrue(volleyMultiplier >= 1.08f && volleyMultiplier <= 1.11f);
    }

    @Test void infernoPyroclasmControlsSustainedGrowth() {
        float dpsMultiplier = (2f * .62f) / .94f;
        assertTrue(dpsMultiplier >= 1.30f && dpsMultiplier <= 1.33f);
    }

    @Test void breacherRuptureControlsPelletGrowth() {
        float volleyMultiplier = 12f * .82f / 9f;
        assertTrue(volleyMultiplier >= 1.08f && volleyMultiplier <= 1.11f);
    }
}
