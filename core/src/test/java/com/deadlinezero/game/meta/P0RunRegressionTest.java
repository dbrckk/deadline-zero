package com.deadlinezero.game.meta;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Cross-system P0 regressions for run settlement, persistence and interrupted-run safety. */
final class P0RunRegressionTest {
    private static HeadlessApplication app;

    @BeforeAll
    static void startHeadlessGdx() {
        app = new HeadlessApplication(new ApplicationAdapter());
    }

    @AfterAll
    static void stopHeadlessGdx() {
        if (app != null) app.exit();
    }

    @BeforeEach
    void resetState() {
        Gdx.app.getPreferences("deadline-zero-profile-v1").clear().flush();
        RunMissionRuntime.end();
        RunModifierContext.end();
        RunEncounterRuntime.end();
        RunLoadoutContext.end();
        RunStageContext.begin(1, 0, 0);
    }

    @Test
    void repeatedVictorySignalSettlesOnlyOnce() {
        PlayerProfile profile = ProfileStore.load();
        long creditsBefore = profile.currency(PlayerProfile.Currency.CREDITS);
        int runsBefore = profile.totalRuns;

        RunMissionRuntime.begin(() -> RunSettlement.apply(profile, 20, 180f, true, 1));
        RunMissionRuntime.update(180f, 20);

        RunMissionRuntime.signalBossDefeated();
        long creditsAfterFirstSignal = profile.currency(PlayerProfile.Currency.CREDITS);
        int runsAfterFirstSignal = profile.totalRuns;

        RunMissionRuntime.signalBossDefeated();

        assertEquals(runsBefore + 1, runsAfterFirstSignal);
        assertEquals(runsAfterFirstSignal, profile.totalRuns);
        assertTrue(creditsAfterFirstSignal > creditsBefore);
        assertEquals(creditsAfterFirstSignal, profile.currency(PlayerProfile.Currency.CREDITS));
    }

    @Test
    void completedSettlementSurvivesSaveReloadWithoutDuplication() {
        PlayerProfile profile = ProfileStore.load();
        profile.addCurrency(PlayerProfile.Currency.CREDITS, 100L);

        RunSettlement.apply(profile, 17, 125f, false, 1);
        long expectedCredits = profile.currency(PlayerProfile.Currency.CREDITS);
        long expectedGems = profile.currency(PlayerProfile.Currency.GEMS);
        long expectedXp = profile.accountXp;
        int expectedRuns = profile.totalRuns;
        long expectedKills = profile.totalKills;

        ProfileStore.save(profile);
        PlayerProfile restored = ProfileStore.load();

        assertEquals(expectedCredits, restored.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(expectedGems, restored.currency(PlayerProfile.Currency.GEMS));
        assertEquals(expectedXp, restored.accountXp);
        assertEquals(expectedRuns, restored.totalRuns);
        assertEquals(expectedKills, restored.totalKills);

        ProfileStore.save(restored);
        PlayerProfile restoredAgain = ProfileStore.load();
        assertEquals(expectedCredits, restoredAgain.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(expectedGems, restoredAgain.currency(PlayerProfile.Currency.GEMS));
        assertEquals(expectedXp, restoredAgain.accountXp);
        assertEquals(expectedRuns, restoredAgain.totalRuns);
        assertEquals(expectedKills, restoredAgain.totalKills);
    }

    @Test
    void interruptedRunEndsWithoutGrantingSettlementRewards() {
        PlayerProfile profile = ProfileStore.load();
        long creditsBefore = profile.currency(PlayerProfile.Currency.CREDITS);
        long gemsBefore = profile.currency(PlayerProfile.Currency.GEMS);
        long xpBefore = profile.accountXp;
        int runsBefore = profile.totalRuns;
        long killsBefore = profile.totalKills;

        RunMissionRuntime.begin(() -> RunSettlement.apply(profile, 50, 300f, true, 1));
        RunMissionRuntime.update(240f, 39);
        RunMissionRuntime.end();

        assertEquals(creditsBefore, profile.currency(PlayerProfile.Currency.CREDITS));
        assertEquals(gemsBefore, profile.currency(PlayerProfile.Currency.GEMS));
        assertEquals(xpBefore, profile.accountXp);
        assertEquals(runsBefore, profile.totalRuns);
        assertEquals(killsBefore, profile.totalKills);
    }
}
