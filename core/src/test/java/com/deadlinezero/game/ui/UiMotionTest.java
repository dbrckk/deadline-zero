package com.deadlinezero.game.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class UiMotionTest {
    @Test
    void timingConstantsStayWithinInteractionBudget() {
        assertTrue(UiMotion.PRESS_SECONDS >= .07f && UiMotion.PRESS_SECONDS <= .10f);
        assertTrue(UiMotion.FOCUS_SECONDS >= .10f && UiMotion.FOCUS_SECONDS <= .14f);
        assertTrue(UiMotion.REVEAL_SECONDS >= .16f && UiMotion.REVEAL_SECONDS <= .22f);
    }

    @Test
    void reducedMotionResolvesImmediatelyToStableEndState() {
        assertEquals(1f, UiMotion.progress(0f, UiMotion.REVEAL_SECONDS, true), .0001f);
        assertEquals(1f, UiMotion.progress(.01f, UiMotion.PRESS_SECONDS, true), .0001f);
    }

    @Test
    void progressIsClampedAndMonotonic() {
        float a = UiMotion.progress(0f, UiMotion.REVEAL_SECONDS, false);
        float b = UiMotion.progress(UiMotion.REVEAL_SECONDS * .5f, UiMotion.REVEAL_SECONDS, false);
        float c = UiMotion.progress(UiMotion.REVEAL_SECONDS, UiMotion.REVEAL_SECONDS, false);
        assertEquals(0f, a, .0001f);
        assertTrue(b > a && b < c);
        assertEquals(1f, c, .0001f);
        assertEquals(1f, UiMotion.progress(99f, UiMotion.REVEAL_SECONDS, false), .0001f);
    }
}
