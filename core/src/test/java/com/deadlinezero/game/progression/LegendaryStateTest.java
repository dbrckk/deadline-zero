package com.deadlinezero.game.progression;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class LegendaryStateTest {
    @Test public void grantsAreOneShot() {
        LegendaryState state = new LegendaryState();
        assertFalse(state.hasAny());
        assertTrue(state.grantOverdrive());
        assertFalse(state.grantOverdrive());
        assertTrue(state.hasOverdrive());
        assertTrue(state.hasAny());
    }

    @Test public void flagsRemainIndependent() {
        LegendaryState state = new LegendaryState();
        assertTrue(state.grantSingularity());
        assertTrue(state.grantApex());
        assertFalse(state.hasOverdrive());
        assertTrue(state.hasSingularity());
        assertTrue(state.hasApex());
    }
}
