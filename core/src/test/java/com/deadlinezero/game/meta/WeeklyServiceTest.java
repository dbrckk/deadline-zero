package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class WeeklyServiceTest {
    @Test public void utcWeeksRollOverOnMonday() {
        assertEquals(0L, WeeklyService.weekIndexForEpochDay(0L));
        assertEquals(0L, WeeklyService.weekIndexForEpochDay(3L));
        assertEquals(1L, WeeklyService.weekIndexForEpochDay(4L));
        assertEquals(-1L, WeeklyService.weekIndexForEpochDay(-4L));
    }

    @Test public void sameWeekKeepsProgressAndNextWeekResets() {
        PlayerProfile profile = new PlayerProfile();
        WeeklyService.refresh(profile, 100L);
        WeeklyService.recordRun(profile, 250, true);
        long week = profile.weekly.weekIndex;

        WeeklyService.refresh(profile, 101L);
        assertEquals(week, profile.weekly.weekIndex);
        assertEquals(250, profile.weekly.kills);
        assertEquals(1, profile.weekly.runs);
        assertEquals(1, profile.weekly.bosses);

        WeeklyService.refresh(profile, 107L);
        assertTrue(profile.weekly.weekIndex > week);
        assertEquals(0, profile.weekly.kills);
        assertEquals(0, profile.weekly.runs);
        assertEquals(0, profile.weekly.bosses);
    }

    @Test public void weeklyClaimsRequireTargetsAndCannotDoublePay() {
        PlayerProfile profile = new PlayerProfile();
        WeeklyService.refresh(profile, 200L);

        for (int i = 0; i < WeeklyService.RUN_TARGET; i++) {
            WeeklyService.recordRun(profile, i == 0 ? WeeklyService.KILL_TARGET : 0, i < WeeklyService.BOSS_TARGET);
        }
        long creditsBefore = profile.currency(PlayerProfile.Currency.CREDITS);
        long gemsBefore = profile.currency(PlayerProfile.Currency.GEMS);

        assertTrue(WeeklyService.claimKillMission(profile));
        assertTrue(WeeklyService.claimRunMission(profile));
        assertTrue(WeeklyService.claimBossMission(profile));
        assertEquals(creditsBefore + 6000L, profile.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(gemsBefore + 12L, profile.currency(PlayerProfile.Currency.GEMS));

        assertFalse(WeeklyService.claimKillMission(profile));
        assertFalse(WeeklyService.claimRunMission(profile));
        assertFalse(WeeklyService.claimBossMission(profile));
    }

    @Test public void countersClampInsteadOfOverflowing() {
        PlayerProfile profile = new PlayerProfile();
        profile.weekly.kills = Integer.MAX_VALUE - 2;
        profile.weekly.runs = Integer.MAX_VALUE;
        profile.weekly.bosses = Integer.MAX_VALUE;
        WeeklyService.recordRun(profile, 50, true);
        assertEquals(Integer.MAX_VALUE, profile.weekly.kills);
        assertEquals(Integer.MAX_VALUE, profile.weekly.runs);
        assertEquals(Integer.MAX_VALUE, profile.weekly.bosses);
    }
}
