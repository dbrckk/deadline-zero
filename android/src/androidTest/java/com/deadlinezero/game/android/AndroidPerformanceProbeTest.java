package com.deadlinezero.game.android;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Projectile;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.perf.PerformanceTelemetry;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.util.Pools;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Emulator-safe runtime performance probe. It validates telemetry integrity under deterministic
 * gameplay load without asserting hardware-specific 60/90/120 FPS throughput.
 */
@RunWith(AndroidJUnit4.class)
public final class AndroidPerformanceProbeTest {
    @Test
    public void recordsLoadedGameplayPerformanceTelemetry() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for performance probe", game.getScreen() instanceof GameScreen);
                injectLoad((GameScreen) game.getScreen());
            });

            Thread.sleep(2600L);

            AtomicReference<PerformanceTelemetry.Snapshot> snapshotRef = new AtomicReference<>();
            AtomicReference<Integer> targetRef = new AtomicReference<>();
            AtomicReference<String> thermalRef = new AtomicReference<>();
            AtomicReference<Float> fxQualityRef = new AtomicReference<>();
            AtomicReference<Integer> enemyCountRef = new AtomicReference<>();
            AtomicReference<Integer> projectileCountRef = new AtomicReference<>();
            AtomicReference<Integer> activeSpatialBucketsRef = new AtomicReference<>();
            AtomicReference<Integer> retainedSpatialBucketsRef = new AtomicReference<>();
            runOnGameThread(activity, () -> {
                GameScreen screen = (GameScreen) game(activity).getScreen();
                snapshotRef.set(screen.performanceSnapshot());
                targetRef.set(screen.effectiveFrameRateTarget());
                thermalRef.set(screen.thermalLevel().name());
                fxQualityRef.set(screen.effectiveFxQuality());
                enemyCountRef.set(screen.activeEnemyCount());
                projectileCountRef.set(screen.activeProjectileCount());
                activeSpatialBucketsRef.set(screen.activeSpatialBucketCount());
                retainedSpatialBucketsRef.set(screen.retainedSpatialBucketCount());
            });

            PerformanceTelemetry.Snapshot snapshot = snapshotRef.get();
            assertNotNull("performance snapshot missing", snapshot);
            assertNotNull("effective FPS target missing", targetRef.get());
            assertTrue("performance probe did not collect enough frames", snapshot.averageFps() > 5f);
            assertTrue("p95 frame time must be finite and positive",
                Float.isFinite(snapshot.p95FrameMs()) && snapshot.p95FrameMs() > 0f && snapshot.p95FrameMs() <= 250f);
            assertTrue("p99 must not be below p95", snapshot.p99FrameMs() >= snapshot.p95FrameMs());
            assertTrue("jank ratio out of bounds", snapshot.jankRatio() >= 0f && snapshot.jankRatio() <= 1f);
            assertTrue("unexpected effective FPS tier",
                targetRef.get() == 60 || targetRef.get() == 90 || targetRef.get() == 120);

            writeBenchmarkJson(
                "performance-probe.json",
                "loaded-40-enemy-ring",
                targetRef.get(),
                snapshot,
                thermalRef.get(),
                fxQualityRef.get(),
                enemyCountRef.get(),
                projectileCountRef.get(),
                activeSpatialBucketsRef.get(),
                retainedSpatialBucketsRef.get()
            );
        }
    }

    @Test
    public void recordsHordeProjectileStressTelemetry() throws Exception {
        try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
            AndroidLauncher activity = activity(scenario);
            runOnGameThread(activity, () -> {
                DeadlineZeroGame game = game(activity);
                game.startRun();
                game.startRunWithContract(RunModifierContext.offers()[0]);
                assertTrue("expected GameScreen for stress probe", game.getScreen() instanceof GameScreen);
                injectStressLoad((GameScreen) game.getScreen());
            });

            Thread.sleep(3200L);

            AtomicReference<PerformanceTelemetry.Snapshot> snapshotRef = new AtomicReference<>();
            AtomicReference<Integer> targetRef = new AtomicReference<>();
            AtomicReference<String> thermalRef = new AtomicReference<>();
            AtomicReference<Float> fxQualityRef = new AtomicReference<>();
            AtomicReference<Integer> enemyCountRef = new AtomicReference<>();
            AtomicReference<Integer> projectileCountRef = new AtomicReference<>();
            AtomicReference<Integer> activeSpatialBucketsRef = new AtomicReference<>();
            AtomicReference<Integer> retainedSpatialBucketsRef = new AtomicReference<>();
            runOnGameThread(activity, () -> {
                GameScreen screen = (GameScreen) game(activity).getScreen();
                snapshotRef.set(screen.performanceSnapshot());
                targetRef.set(screen.effectiveFrameRateTarget());
                thermalRef.set(screen.thermalLevel().name());
                fxQualityRef.set(screen.effectiveFxQuality());
                enemyCountRef.set(screen.activeEnemyCount());
                projectileCountRef.set(screen.activeProjectileCount());
                activeSpatialBucketsRef.set(screen.activeSpatialBucketCount());
                retainedSpatialBucketsRef.set(screen.retainedSpatialBucketCount());
            });

            PerformanceTelemetry.Snapshot snapshot = snapshotRef.get();
            assertNotNull("stress performance snapshot missing", snapshot);
            assertTrue("stress probe did not collect enough frames", snapshot.averageFps() > 5f);
            assertTrue("stress p95 frame time must be finite and positive",
                Float.isFinite(snapshot.p95FrameMs()) && snapshot.p95FrameMs() > 0f && snapshot.p95FrameMs() <= 250f);
            assertTrue("stress p99 must not be below p95", snapshot.p99FrameMs() >= snapshot.p95FrameMs());
            assertTrue("stress jank ratio out of bounds", snapshot.jankRatio() >= 0f && snapshot.jankRatio() <= 1f);
            assertTrue("stress workload lost too many enemies", enemyCountRef.get() >= 140);
            assertTrue("stress workload lost too many projectiles", projectileCountRef.get() >= 140);

            writeBenchmarkJson(
                "performance-stress.json",
                "horde-160-projectile-180",
                targetRef.get(),
                snapshot,
                thermalRef.get(),
                fxQualityRef.get(),
                enemyCountRef.get(),
                projectileCountRef.get(),
                activeSpatialBucketsRef.get(),
                retainedSpatialBucketsRef.get()
            );
        }
    }


    private static void writeBenchmarkJson(
        String filename,
        String scenario,
        int target,
        PerformanceTelemetry.Snapshot snapshot,
        String thermalLevel,
        float fxQuality,
        int activeEnemies,
        int activeProjectiles,
        int activeSpatialBuckets,
        int retainedSpatialBuckets
    ) throws Exception {
        File root = new File(
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getExternalFilesDir(null),
            "qa"
        );
        assertTrue("unable to create performance QA output directory", root.isDirectory() || root.mkdirs());
        File output = new File(root, filename);
        try (FileWriter writer = new FileWriter(output, false)) {
            writer.write("{\n");
            writer.write("  \"scenario\": \"" + scenario + "\",\n");
            writer.write("  \"targetFps\": " + target + ",\n");
            writer.write("  \"averageFps\": " + snapshot.averageFps() + ",\n");
            writer.write("  \"p95FrameMs\": " + snapshot.p95FrameMs() + ",\n");
            writer.write("  \"p99FrameMs\": " + snapshot.p99FrameMs() + ",\n");
            writer.write("  \"jankRatio\": " + snapshot.jankRatio() + ",\n");
            writer.write("  \"stable\": " + snapshot.stable() + ",\n");
            writer.write("  \"thermalLevel\": \"" + thermalLevel + "\",\n");
            writer.write("  \"effectiveFxQuality\": " + fxQuality + ",\n");
            writer.write("  \"activeEnemies\": " + activeEnemies + ",\n");
            writer.write("  \"activeProjectiles\": " + activeProjectiles + ",\n");
            writer.write("  \"activeSpatialBuckets\": " + activeSpatialBuckets + ",\n");
            writer.write("  \"retainedSpatialBuckets\": " + retainedSpatialBuckets + "\n");
            writer.write("}\n");
        }
        assertTrue("performance benchmark JSON was not written", output.isFile() && output.length() > 40L);
    }

    @SuppressWarnings("unchecked")
    private static void injectLoad(GameScreen screen) {
        try {
            Field field = GameScreen.class.getDeclaredField("enemies");
            field.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
            Enemy.Type[] types = {
                Enemy.Type.SHAMBLER, Enemy.Type.RUNNER, Enemy.Type.BRUTE, Enemy.Type.RANGED,
                Enemy.Type.ELITE, Enemy.Type.SHIELDED, Enemy.Type.REGENERATOR, Enemy.Type.PHANTOM
            };
            for (int i = 0; i < 40; i++) {
                float angle = (float)(i * (Math.PI * 2.0 / 40.0));
                float radius = 5.5f + (i % 5) * .55f;
                Enemy.Type type = types[i % types.length];
                enemies.add(new Enemy(
                    type,
                    (float)Math.cos(angle) * radius,
                    (float)Math.sin(angle) * radius,
                    type == Enemy.Type.ELITE ? 250_000f : 75_000f,
                    .04f + (i % 3) * .01f,
                    type == Enemy.Type.BRUTE ? .66f : .48f,
                    0f,
                    1
                ));
            }
            assertTrue("performance probe failed to inject deterministic load", enemies.size >= 40);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject deterministic performance load", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static void injectStressLoad(GameScreen screen) {
        try {
            Field enemiesField = GameScreen.class.getDeclaredField("enemies");
            enemiesField.setAccessible(true);
            Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
            Enemy.Type[] types = {
                Enemy.Type.SHAMBLER, Enemy.Type.RUNNER, Enemy.Type.BRUTE, Enemy.Type.RANGED,
                Enemy.Type.ELITE, Enemy.Type.SHIELDED, Enemy.Type.REGENERATOR, Enemy.Type.PHANTOM
            };
            for (int i = 0; i < 160; i++) {
                float angle = (float)(i * (Math.PI * 2.0 / 160.0));
                float radius = 6.0f + (i % 8) * .42f;
                Enemy.Type type = types[i % types.length];
                enemies.add(new Enemy(
                    type,
                    (float)Math.cos(angle) * radius,
                    (float)Math.sin(angle) * radius,
                    type == Enemy.Type.ELITE ? 500_000f : 150_000f,
                    .02f + (i % 4) * .005f,
                    type == Enemy.Type.BRUTE ? .66f : .46f,
                    0f,
                    1
                ));
            }

            Field poolsField = GameScreen.class.getDeclaredField("pools");
            poolsField.setAccessible(true);
            Pools pools = (Pools) poolsField.get(screen);
            int projectileTarget = Math.min(180, pools.projectiles.size);
            for (int i = 0; i < projectileTarget; i++) {
                Projectile projectile = pools.projectiles.get(i);
                float angle = (float)(i * (Math.PI * 2.0 / projectileTarget));
                float radius = 19f + (i % 5) * .35f;
                projectile.position.set((float)Math.cos(angle) * radius, (float)Math.sin(angle) * radius);
                projectile.velocity.setZero();
                projectile.damage = 0f;
                projectile.life = 30f;
                projectile.radius = .08f;
                projectile.penetrationRemaining = 0;
                projectile.lastHit = null;
                projectile.active = true;
            }

            assertTrue("stress probe failed to inject horde", enemies.size >= 160);
            assertTrue("stress probe projectile pool too small", projectileTarget >= 140);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("unable to inject deterministic stress load", exception);
        }
    }

    private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
        AtomicReference<AndroidLauncher> reference = new AtomicReference<>();
        scenario.onActivity(reference::set);
        AndroidLauncher activity = reference.get();
        assertNotNull("Android launcher unavailable", activity);
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
            try { action.run(); } catch (Throwable throwable) { failure.set(throwable); } finally { done.countDown(); }
        });
        assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
        if (failure.get() != null) throw new AssertionError("Android performance probe failed on game thread", failure.get());
    }
}
