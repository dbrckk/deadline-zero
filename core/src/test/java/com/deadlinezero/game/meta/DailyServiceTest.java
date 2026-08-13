package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class DailyServiceTest {
    @Test public void loginRewardCannotBeClaimedTwice() {
        PlayerProfile profile = new PlayerProfile();
        DailyService.refresh(profile, 100L);

        assertTrue(DailyService.claimLogin(profile));
        long credits = profile.currency(PlayerProfile.Currency.CREDITS);
        long gems = profile.currency(PlayerProfile.Currency.GEMS);

        assertFalse(DailyService.claimLogin(profile));
        assertEquals(credits, profile.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(gems, profile.currency(PlayerProfile.Currency.GEMS));
    }

    @Test public void consecutiveDaysIncreaseStreakAndThirdDayGrantsGems() {
        PlayerProfile profile = new PlayerProfile();
        DailyService.refresh(profile, 200L);
        assertTrue(DailyService.claimLogin(profile));
        DailyService.refresh(profile, 201L);
        assertTrue(DailyService.claimLogin(profile));
        DailyService.refresh(profile, 202L);

        long gemsBefore = profile.currency(PlayerProfile.Currency.GEMS);
        assertEquals(3, profile.daily.loginStreak);
        assertTrue(DailyService.claimLogin(profile));
        assertEquals(gemsBefore + 2L, profile.currency(PlayerProfile.Currency.GEMS));
    }

    @Test public void missionsRequireThresholdAndCannotDoublePay() {
        PlayerProfile profile = new PlayerProfile();
        DailyService.refresh(profile, 300L);

        DailyService.recordRun(profile, 99, false);
        assertFalse(DailyService.claimKillMission(profile));
        assertFalse(DailyService.claimRunMission(profile));
        assertFalse(DailyService.claimBossMission(profile));

        DailyService.recordRun(profile, 1, true);
        DailyService.recordRun(profile, 0, false);
        long creditsBefore = profile.currency(PlayerProfile.Currency.CREDITS);
        long gemsBefore = profile.currency(PlayerProfile.Currency.GEMS);

        assertTrue(DailyService.claimKillMission(profile));
        assertTrue(DailyService.claimRunMission(profile));
        assertTrue(DailyService.claimBossMission(profile));
        assertEquals(creditsBefore + 800L, profile.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(gemsBefore + 3L, profile.currency(PlayerProfile.Currency.GEMS));

        assertFalse(DailyService.claimKillMission(profile));
        assertFalse(DailyService.claimRunMission(profile));
        assertFalse(DailyService.claimBossMission(profile));
    }

    @Test public void missedDayResetsStreak() {
        PlayerProfile profile = new PlayerProfile();
        DailyService.refresh(profile, 400L);
        DailyService.refresh(profile, 401L);
        DailyService.refresh(profile, 405L);
        assertEquals(1, profile.daily.loginStreak);
    }
}
