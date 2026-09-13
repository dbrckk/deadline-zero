package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public final class AchievementServiceTest {
    @Test void lifetimeThresholdsUnlockDeterministically() {
        PlayerProfile p = new PlayerProfile();
        assertFalse(AchievementService.unlocked(p, AchievementService.Achievement.FIRST_DEPLOYMENT));
        p.totalRuns = 25;
        p.totalKills = 5_000L;
        p.highestStage = 5;
        p.accountLevel = 10;
        for (AchievementService.Achievement achievement : AchievementService.Achievement.values()) {
            assertTrue(AchievementService.unlocked(p, achievement), achievement.name());
        }
    }

    @Test void claimPaysExactlyOnce() {
        PlayerProfile p = new PlayerProfile();
        p.totalRuns = 1;
        long before = p.currency(PlayerProfile.Currency.CREDITS);
        assertTrue(AchievementService.claim(p, AchievementService.Achievement.FIRST_DEPLOYMENT));
        assertEquals(before + 500L, p.currency(PlayerProfile.Currency.CREDITS));
        assertTrue(p.achievements.claimed(AchievementService.Achievement.FIRST_DEPLOYMENT));
        assertFalse(AchievementService.claim(p, AchievementService.Achievement.FIRST_DEPLOYMENT));
        assertEquals(before + 500L, p.currency(PlayerProfile.Currency.CREDITS));
    }

    @Test void lockedAchievementCannotBeClaimed() {
        PlayerProfile p = new PlayerProfile();
        assertFalse(AchievementService.claim(p, AchievementService.Achievement.DEEP_STRIKE));
        assertFalse(p.achievements.claimed(AchievementService.Achievement.DEEP_STRIKE));
    }
}
