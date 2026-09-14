package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.services.CloudAuthenticationRequiredException;
import com.deadlinezero.game.services.CloudProviderConflictException;
import com.deadlinezero.game.services.CloudRemoteChangedException;
import com.deadlinezero.game.services.CloudSaveAdapter;
import com.deadlinezero.game.services.CloudSaveService;
import com.deadlinezero.game.visual.VisualTheme;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Explicit cloud-save management. Never resolves divergent or stale progress automatically. */
public final class CloudSaveScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final CloudSaveService cloud;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final ExecutorService worker = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "deadline-zero-cloud-save");
        t.setDaemon(true);
        return t;
    });

    private volatile boolean busy;
    private volatile boolean disposed;
    private CloudSaveService.Comparison comparison;
    private CloudSaveService.ConflictState conflict;
    private String status;
    private boolean confirmUpload;
    private boolean confirmDownload;
    private boolean providerConflict;
    private boolean authRequired;

    public CloudSaveScreen(DeadlineZeroGame game) {
        this.game = game;
        this.cloud = new CloudSaveService(game.services.cloudSave);
        status = cloud.available() ? t("cloud.checking") : t("cloud.notConfigured");
        if (cloud.available()) refresh();
    }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(VisualTheme.PANEL);
        shapes.rect(w * .15f, h * .14f, w * .70f, h * .70f);
        shapes.setColor(VisualTheme.CYAN);
        shapes.rect(w * .15f, h * .83f, w * .70f, 3f);
        shapes.setColor(VisualTheme.PANEL_ALT);
        shapes.rect(w * .20f, h * .34f, w * .17f, h * .10f);
        shapes.rect(w * .415f, h * .34f, w * .17f, h * .10f);
        shapes.rect(w * .63f, h * .34f, w * .17f, h * .10f);
        shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .12f);
        shapes.rect(w * .18f, h * .16f, w * .18f, h * .08f);
        shapes.end();

        boolean available = cloud.available();
        batch.begin();
        font.getData().setScale(1.95f);
        font.setColor(VisualTheme.TEXT);
        font.draw(batch, t("cloud.title"), 0, h * .76f, w, Align.center, false);

        font.getData().setScale(1.05f);
        font.setColor(colorForState());
        font.draw(batch, status, w * .20f, h * .64f, w * .60f, Align.center, true);

        font.getData().setScale(.82f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, t("cloud.manualWarning"),
            w * .20f, h * .55f, w * .60f, Align.center, true);

        font.getData().setScale(1.02f);
        font.setColor(available ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
        font.draw(batch, primaryLabel(), w * .20f, h * .398f, w * .17f, Align.center, false);
        font.setColor(available ? VisualTheme.GOLD : VisualTheme.MUTED);
        font.draw(batch, providerConflict ? t("cloud.useServer") : t("cloud.uploadLocal"), w * .415f, h * .398f, w * .17f, Align.center, false);
        font.setColor(available ? VisualTheme.TEXT : VisualTheme.MUTED);
        font.draw(batch, providerConflict ? t("cloud.useOther") : t("cloud.downloadCloud"), w * .63f, h * .398f, w * .17f, Align.center, false);

        font.getData().setScale(.74f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, confirmationLine(), w * .20f, h * .30f, w * .60f, Align.center, true);
        font.draw(batch, t("cloud.back"), w * .18f, h * .205f, w * .18f, Align.center, false);
        batch.end();

        handleInput(w, h);
    }

    private void handleInput(float w, float h) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.showSettings();
            return;
        }

        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = h - Gdx.input.getY();
            if (x >= w * .18f && x <= w * .36f && y >= h * .16f && y <= h * .24f) {
                game.showSettings();
                return;
            }
            if (y >= h * .34f && y <= h * .44f) {
                if (x >= w * .20f && x <= w * .37f) {
                    if (!busy && cloud.available()) primaryAction();
                    return;
                }
                if (x >= w * .415f && x <= w * .585f) {
                    if (!busy && cloud.available()) {
                        if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.SERVER);
                        else requestUpload();
                    }
                    return;
                }
                if (x >= w * .63f && x <= w * .80f) {
                    if (!busy && cloud.available()) {
                        if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.CONFLICTING);
                        else requestDownload();
                    }
                    return;
                }
            }
        }

        if (busy || !cloud.available()) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { primaryAction(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.SERVER);
            else requestUpload();
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.CONFLICTING);
            else requestDownload();
        }
    }

    private void primaryAction() {
        resetConfirmations();
        if (authRequired && cloud.supportsAuthentication()) authenticateAndRefresh();
        else refresh();
    }

    private void authenticateAndRefresh() {
        comparison = null;
        conflict = null;
        providerConflict = false;
        runAsync(t("cloud.signingIn"), () -> {
            cloud.authenticate();
            CloudSaveService.Comparison next = inspectComparisonOrConflict();
            post(() -> {
                authRequired = false;
                applyComparison(next);
            });
        });
    }

    private void refresh() {
        comparison = null;
        conflict = null;
        providerConflict = false;
        resetConfirmations();
        runAsync(t("cloud.checking"), () -> {
            CloudSaveService.Comparison next = inspectComparisonOrConflict();
            post(() -> applyComparison(next));
        });
    }

    private CloudSaveService.Comparison inspectComparisonOrConflict() throws Exception {
        CloudSaveAdapter.ProviderConflict pending = cloud.pendingProviderConflict();
        if (pending != null) throw new CloudProviderConflictException(pending);
        CloudSaveService.Comparison next = cloud.inspectAgainstLocal();
        pending = cloud.pendingProviderConflict();
        if (pending != null) throw new CloudProviderConflictException(pending);
        return next;
    }

    private void applyComparison(CloudSaveService.Comparison next) {
        comparison = next;
        providerConflict = false;
        authRequired = false;
        conflict = next.state();
        status = switch (next.state()) {
            case EQUAL -> t("cloud.identical");
            case LOCAL_AHEAD -> next.remote() == null ? t("cloud.noSave") : t("cloud.localAhead");
            case REMOTE_AHEAD -> t("cloud.remoteAhead");
            case DIVERGED -> t("cloud.diverged");
        };
    }

    private void resolveProviderConflict(CloudSaveAdapter.ConflictChoice choice) {
        runAsync(t("cloud.resolving"), () -> {
            cloud.resolveProviderConflict(choice);
            CloudSaveService.Comparison next = inspectComparisonOrConflict();
            post(() -> {
                resetConfirmations();
                applyComparison(next);
            });
        });
    }

    private void requestUpload() {
        CloudSaveService.Comparison expected = comparison;
        if (expected == null) {
            resetConfirmations();
            status = t("cloud.refreshUpload");
            return;
        }
        boolean risky = expected.state() == CloudSaveService.ConflictState.REMOTE_AHEAD
            || expected.state() == CloudSaveService.ConflictState.DIVERGED;
        if (risky && !confirmUpload) {
            confirmUpload = true;
            confirmDownload = false;
            status = t("cloud.confirmUpload");
            return;
        }
        resetConfirmations();
        runAsync(t("cloud.revalidateUpload"), () -> {
            cloud.uploadIfUnchanged(expected);
            CloudSaveService.Comparison next = inspectComparisonOrConflict();
            post(() -> {
                status = t("cloud.uploadComplete");
                applyComparison(next);
            });
        });
    }

    private void requestDownload() {
        CloudSaveService.Comparison expected = comparison;
        if (expected == null) {
            resetConfirmations();
            status = t("cloud.refreshDownload");
            return;
        }
        boolean risky = expected.state() == CloudSaveService.ConflictState.LOCAL_AHEAD
            || expected.state() == CloudSaveService.ConflictState.DIVERGED;
        if (risky && !confirmDownload) {
            confirmDownload = true;
            confirmUpload = false;
            status = t("cloud.confirmDownload");
            return;
        }
        resetConfirmations();
        runAsync(t("cloud.revalidateDownload"), () -> {
            CloudSaveAdapter.RemoteBackup remote = cloud.revalidateRemote(expected);
            post(() -> {
                if (remote == null) {
                    comparison = null;
                    conflict = null;
                    status = t("cloud.changed");
                    return;
                }
                try {
                    CloudSaveService.RestoreResult result = cloud.applyRemote(remote.payload());
                    if (result.result() == CloudSaveService.DownloadResult.APPLIED) {
                        if (!game.applyCloudRestore(result)) status = t("cloud.restoreFailed");
                    } else {
                        status = t("cloud.newerVersion");
                    }
                } catch (RuntimeException e) {
                    comparison = null;
                    conflict = null;
                    status = f("cloud.restoreError", safeMessage(e));
                }
            });
        });
    }

    private void runAsync(String runningStatus, ThrowingAction action) {
        busy = true;
        status = runningStatus;
        worker.submit(() -> {
            try {
                action.run();
            } catch (CloudAuthenticationRequiredException e) {
                post(() -> {
                    comparison = null;
                    conflict = null;
                    providerConflict = false;
                    authRequired = true;
                    resetConfirmations();
                    status = t("cloud.signInRequired");
                });
            } catch (CloudProviderConflictException e) {
                post(() -> {
                    comparison = null;
                    providerConflict = true;
                    authRequired = false;
                    conflict = CloudSaveService.ConflictState.DIVERGED;
                    resetConfirmations();
                    status = t("cloud.providerConflict");
                });
            } catch (CloudRemoteChangedException e) {
                post(() -> {
                    comparison = null;
                    conflict = null;
                    providerConflict = false;
                    resetConfirmations();
                    status = t("cloud.changedOther");
                });
            } catch (Exception e) {
                post(() -> {
                    comparison = null;
                    conflict = null;
                    providerConflict = false;
                    resetConfirmations();
                    status = f("cloud.error", safeMessage(e));
                });
            } finally {
                post(() -> busy = false);
            }
        });
    }

    private void post(Runnable action) {
        if (disposed) return;
        Gdx.app.postRunnable(() -> {
            if (!disposed) action.run();
        });
    }

    private String primaryLabel() {
        if (authRequired && cloud.supportsAuthentication()) return t("cloud.signIn");
        return providerConflict ? t("cloud.recheck") : t("cloud.refresh");
    }

    private String confirmationLine() {
        if (!cloud.available()) return t("cloud.configure");
        if (authRequired) return t("cloud.authHelp");
        if (providerConflict) return t("cloud.conflictHelp");
        if (comparison == null && !busy) return t("cloud.refreshHelp");
        if (confirmUpload) return t("cloud.uploadArmed");
        if (confirmDownload) return t("cloud.downloadArmed");
        return busy ? t("cloud.inProgress") : t("cloud.versionBound");
    }

    private com.badlogic.gdx.graphics.Color colorForState() {
        if (!cloud.available()) return VisualTheme.MUTED;
        if (authRequired) return VisualTheme.GOLD;
        if (conflict == CloudSaveService.ConflictState.DIVERGED) return VisualTheme.RED;
        if (conflict == CloudSaveService.ConflictState.REMOTE_AHEAD) return VisualTheme.GOLD;
        return VisualTheme.CYAN;
    }

    private void resetConfirmations() {
        confirmUpload = false;
        confirmDownload = false;
    }

    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    private static String safeMessage(Exception e) {
        String message = e.getMessage();
        if (message == null || message.isBlank()) return e.getClass().getSimpleName();
        return message.length() > 96 ? message.substring(0, 96) : message;
    }

    @Override public void dispose() {
        disposed = true;
        worker.shutdownNow();
        batch.dispose();
        font.dispose();
        shapes.dispose();
    }

    @FunctionalInterface private interface ThrowingAction { void run() throws Exception; }
}
