package com.deadlinezero.game.android;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.meta.RunMissionRuntime;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.screen.ArsenalScreen;
import com.deadlinezero.game.screen.CloudSaveScreen;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.screen.GearScreen;
import com.deadlinezero.game.screen.MenuScreen;
import com.deadlinezero.game.screen.MissionsScreen;
import com.deadlinezero.game.screen.RunContractScreen;
import com.deadlinezero.game.screen.RunResultScreen;
import com.deadlinezero.game.screen.SettingsScreen;
import com.deadlinezero.game.screen.ShopScreen;
import com.deadlinezero.game.screen.SurvivorScreen;
import com.deadlinezero.game.screen.VictoryScreen;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Phone-aspect visual gate for the responsive UI system.
 *
 * The dedicated workflow opts into this probe after forcing the emulator to the physical-device
 * aspect that exposed the original mixed pixel/logical-coordinate bug. Ordinary connected-device
 * suites skip it explicitly so a conventional 16:9 emulator cannot fail this dedicated visual gate.
 */
@RunWith(AndroidJUnit4.class)
public final class AndroidResponsiveUiVisualProbeTest {
    private static final float MIN_WIDE_ASPECT = 2.05f;
    private static final String WIDE_PROBE_ARGUMENT = "deadlinezero.wideProbe";

    @Test
    public void capturesWidePhoneMetaAndCombatScreens() throws Exception {
        assumeTrue("wide-phone visual probe only runs in the dedicated workflow", dedicatedWideProbe());
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            Thread.sleep(650L);
            assertWidePhoneAspect();

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.profile.accountLevel = Math.max(10, game.profile.accountLevel);
                game.profile.highestStage = Math.max(8, game.profile.highestStage);
                game.profile.selectedStage = Math.min(5, game.profile.highestStage);
                game.profile.survivors.refreshUnlocks(game.profile);
                game.showMenu();
                assertTrue("expected MenuScreen for wide-phone visual probe", game.getScreen() instanceof MenuScreen);
            });
            settleAndCapture("responsive-1536x691-home.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showSurvivors();
                assertTrue("expected SurvivorScreen for wide-phone visual probe",
                    game.getScreen() instanceof SurvivorScreen);
            });
            settleAndCapture("responsive-1536x691-survivors.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showArsenal();
                assertTrue("expected ArsenalScreen for wide-phone visual probe",
                    game.getScreen() instanceof ArsenalScreen);
            });
            settleAndCapture("responsive-1536x691-arsenal.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showGear();
                assertTrue("expected GearScreen for wide-phone visual probe",
                    game.getScreen() instanceof GearScreen);
            });
            settleAndCapture("responsive-1536x691-gear.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showMissions();
                assertTrue("expected MissionsScreen for wide-phone visual probe",
                    game.getScreen() instanceof MissionsScreen);
            });
            settleAndCapture("responsive-1536x691-missions.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showShop();
                assertTrue("expected ShopScreen for wide-phone visual probe",
                    game.getScreen() instanceof ShopScreen);
            });
            settleAndCapture("responsive-1536x691-shop.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showCloudSave();
                assertTrue("expected CloudSaveScreen for wide-phone visual probe",
                    game.getScreen() instanceof CloudSaveScreen);
            });
            settleAndCapture("responsive-1536x691-cloud-save.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showSettings();
                assertTrue("expected SettingsScreen for wide-phone visual probe",
                    game.getScreen() instanceof SettingsScreen);
            });
            settleAndCapture("responsive-1536x691-settings.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showMenu();
                game.startRun();
                assertTrue("expected RunContractScreen for wide-phone visual probe",
                    game.getScreen() instanceof RunContractScreen);
            });
            settleAndCapture("responsive-1536x691-contract.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for wide-phone visual probe", game.getScreen() instanceof GameScreen);
            });
            settleAndCapture("responsive-1536x691-combat.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.finishRun(420, 155f, false, 0);
                assertTrue("expected RunResultScreen for wide-phone visual probe",
                    game.getScreen() instanceof RunResultScreen);
            });
            settleAndCapture("responsive-1536x691-run-result.png");

            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.showMenu();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen before victory visual probe",
                    game.getScreen() instanceof GameScreen);
                RunMissionRuntime.signalBossDefeated();
            });
            Thread.sleep(350L);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                assertTrue("expected VictoryScreen for wide-phone visual probe",
                    game.getScreen() instanceof VictoryScreen);
            });
            settleAndCapture("responsive-1536x691-victory.png");
        }
    }

    private static boolean dedicatedWideProbe() {
        Bundle arguments = InstrumentationRegistry.getArguments();
        return "true".equalsIgnoreCase(arguments.getString(WIDE_PROBE_ARGUMENT, "false"));
    }

    private static void assertWidePhoneAspect() {
        Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        assertNotNull("Android UiAutomation did not return an aspect probe screenshot", bitmap);
        try {
            float aspect = bitmap.getWidth() / (float) Math.max(1, bitmap.getHeight());
            assertTrue("dedicated wide-phone probe did not reach aspect > " + MIN_WIDE_ASPECT + ", was " + aspect,
                aspect > MIN_WIDE_ASPECT);
        } finally {
            bitmap.recycle();
        }
    }

    private static void settleAndCapture(String name) throws Exception {
        Thread.sleep(650L);
        captureWide(name);
    }

    private static void captureWide(String name) throws Exception {
        File root = new File(
            InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null),
            "qa"
        );
        assertTrue("unable to create responsive QA output directory", root.isDirectory() || root.mkdirs());
        File output = new File(root, name);

        long size = 0L;
        for (int attempt = 1; attempt <= 3; attempt++) {
            Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
            assertNotNull("Android UiAutomation did not return a responsive screenshot", bitmap);
            try {
                float aspect = bitmap.getWidth() / (float) Math.max(1, bitmap.getHeight());
                assertTrue("responsive visual probe did not run at wide-phone aspect: " + aspect,
                    aspect > MIN_WIDE_ASPECT);
                try (FileOutputStream stream = new FileOutputStream(output, false)) {
                    assertTrue("unable to encode responsive QA screenshot",
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
                }
            } finally {
                bitmap.recycle();
            }

            size = output.length();
            if (size > 10_000L) return;
            if (attempt < 3) Thread.sleep(300L);
        }
        assertTrue("responsive QA screenshot is unexpectedly small after retries: " + size, size > 10_000L);
    }

    private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
        AtomicReference<AndroidLauncher> reference = new AtomicReference<>();
        scenario.onActivity(reference::set);
        AndroidLauncher activity = reference.get();
        assertNotNull("Android launcher was not available to responsive visual probe", activity);
        return activity;
    }

    private static DeadlineZeroGame game(AndroidLauncher activity) {
        Object listener = activity.getApplicationListener();
        assertTrue("Android launcher is not hosting DeadlineZeroGame", listener instanceof DeadlineZeroGame);
        return (DeadlineZeroGame) listener;
    }

    private static void runOnGameThread(AndroidLauncher activity, Runnable action) throws Exception {
        CountDownLatch done = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        activity.postRunnable(() -> {
            try {
                action.run();
            } catch (Throwable throwable) {
                failure.set(throwable);
            } finally {
                done.countDown();
            }
        });
        assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
        if (failure.get() != null) {
            throw new AssertionError("Responsive Android visual probe failed on libGDX game thread", failure.get());
        }
    }
}
