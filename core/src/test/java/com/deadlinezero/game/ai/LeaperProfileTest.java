package com.deadlinezero.game.ai;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class LeaperProfileTest {
    @Test void leapRangeIsReadableAndBounded() {
        assertFalse(LeaperProfile.inLeapRange(1.5f));
        assertTrue(LeaperProfile.inLeapRange(2.2f));
        assertTrue(LeaperProfile.inLeapRange(6f));
        assertTrue(LeaperProfile.inLeapRange(8.6f));
        assertFalse(LeaperProfile.inLeapRange(9f));
    }

    @Test void leapTimingRemainsTelegraphable() {
        assertTrue(LeaperProfile.LEAP_WINDUP >= .16f);
        assertTrue(LeaperProfile.LEAP_IMPACT_WINDOW <= .25f);
        assertTrue(LeaperProfile.LEAP_COOLDOWN_MIN >= 1.25f);
        assertTrue(LeaperProfile.LEAP_COOLDOWN_MAX > LeaperProfile.LEAP_COOLDOWN_MIN);
    }

    @Test void baseProfileOccupiesFastSkirmisherBand() {
        assertTrue(LeaperProfile.BASE_HP >= 35f && LeaperProfile.BASE_HP <= 70f);
        assertTrue(LeaperProfile.BASE_SPEED >= 2.8f && LeaperProfile.BASE_SPEED <= 3.8f);
        assertTrue(LeaperProfile.LEAP_IMPULSE > LeaperProfile.BASE_SPEED);
    }
}
