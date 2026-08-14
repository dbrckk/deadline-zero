package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class PlayerProfileSafetyTest {
    @Test void currenciesSaturateInsteadOfWrapping() {
        PlayerProfile profile = new PlayerProfile();
        profile.addCurrency(PlayerProfile.Currency.CREDITS, Long.MAX_VALUE - 2L);
        profile.addCurrency(PlayerProfile.Currency.CREDITS, 10L);
        assertEquals(Long.MAX_VALUE, profile.currency(PlayerProfile.Currency.CREDITS));
    }

    @Test void runCountersSaturateAndIgnoreNegativeKills() {
        PlayerProfile profile = new PlayerProfile();
        profile.totalRuns = Integer.MAX_VALUE;
        profile.totalKills = Long.MAX_VALUE - 2L;
        profile.recordRun(10, 4);
        assertEquals(Integer.MAX_VALUE, profile.totalRuns);
        assertEquals(Long.MAX_VALUE, profile.totalKills);

        profile.totalKills = 25L;
        profile.recordRun(-100, 4);
        assertEquals(25L, profile.totalKills);
    }

    @Test void accountXpPreservesNormalThresholds() {
        PlayerProfile profile = new PlayerProfile();
        profile.addAccountXp(249L);
        assertEquals(1, profile.accountLevel);
        assertEquals(249L, profile.accountXp);

        profile.addAccountXp(1L);
        assertEquals(2, profile.accountLevel);
        assertEquals(0L, profile.accountXp);

        profile.addAccountXp(360L);
        assertEquals(3, profile.accountLevel);
        assertEquals(0L, profile.accountXp);
    }

    @Test void extremeAccountXpAdvancesWithoutLinearLevelLoop() {
        ProfileCounterMath.LevelProgress progress = ProfileCounterMath.advanceAccountXp(1, 0L, Long.MAX_VALUE);
        assertTrue(progress.level() > 1_000_000);
        assertTrue(progress.level() < Integer.MAX_VALUE);
        assertTrue(progress.xp() >= 0L);
        assertTrue(progress.xp() < ProfileCounterMath.xpForLevel(progress.level()));
    }

    @Test void maxLevelNeverCarriesUnboundedXp() {
        ProfileCounterMath.LevelProgress progress = ProfileCounterMath.advanceAccountXp(
            Integer.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, progress.level());
        assertEquals(ProfileCounterMath.xpForLevel(Integer.MAX_VALUE) - 1L, progress.xp());
    }
}
