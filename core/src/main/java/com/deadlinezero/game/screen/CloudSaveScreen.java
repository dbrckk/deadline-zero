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
        status = cloud.available() ? "CHECKING CLOUD..." : "CLOUD PROVIDER NOT CONFIGURED";
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
        font.draw(batch, "CLOUD SAVE", 0, h * .76f, w, Align.center, false);

        font.getData().setScale(1.05f);
        font.setColor(colorForState());
        font.draw(batch, status, w * .20f, h * .64f, w * .60f, Align.center, true);

        font.getData().setScale(.82f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, "Cloud actions are manual. Divergent or changed progress is never overwritten automatically.",
            w * .20f, h * .55f, w * .60f, Align.center, true);

        font.getData().setScale(1.02f);
        font.setColor(available ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
        font.draw(batch, primaryLabel(), w * .20f, h * .398f, w * .17f, Align.center, false);
        font.setColor(available ? VisualTheme.GOLD : VisualTheme.MUTED);
        font.draw(batch, providerConflict ? "USE SERVER" : "UPLOAD LOCAL", w * .415f, h * .398f, w * .17f, Align.center, false);
        font.setColor(available ? VisualTheme.TEXT : VisualTheme.MUTED);
        font.draw(batch, providerConflict ? "USE OTHER" : "DOWNLOAD CLOUD", w * .63f, h * .398f, w * .17f, Align.center, false);

        font.getData().setScale(.74f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, confirmationLine(), w * .20f, h * .30f, w * .60f, Align.center, true);
        font.draw(batch, "BACK  •  SETTINGS", w * .18f, h * .205f, w * .18f, Align.center, false);
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
        runAsync("SIGNING IN TO PLAY GAMES...", () -> {
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
        runAsync("CHECKING CLOUD...", () -> {
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
            case EQUAL -> "LOCAL AND CLOUD ARE IDENTICAL";
            case LOCAL_AHEAD -> next.remote() == null ? "NO CLOUD SAVE YET — LOCAL READY TO UPLOAD" : "LOCAL PROGRESS IS AHEAD";
            case REMOTE_AHEAD -> "CLOUD PROGRESS IS AHEAD";
            case DIVERGED -> "CONFLICT: PROGRESS HAS DIVERGED";
        };
    }

    private void resolveProviderConflict(CloudSaveAdapter.ConflictChoice choice) {
        runAsync("RESOLVING PLAY GAMES SNAPSHOT CONFLICT...", () -> {
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
            status = "REFRESH REQUIRED BEFORE UPLOAD";
            return;
        }
        boolean risky = expected.state() == CloudSaveService.ConflictState.REMOTE_AHEAD
            || expected.state() == CloudSaveService.ConflictState.DIVERGED;
        if (risky && !confirmUpload) {
            confirmUpload = true;
            confirmDownload = false;
            status = "UPLOAD WOULD REPLACE CLOUD PROGRESS — SELECT UPLOAD AGAIN TO CONFIRM";
            return;
        }
        resetConfirmations();
        runAsync("REVALIDATING CLOUD BEFORE UPLOAD...", () -> {
            cloud.uploadIfUnchanged(expected);
            CloudSaveService.Comparison next = inspectComparisonOrConflict();
            post(() -> {
                status = "UPLOAD COMPLETE";
                applyComparison(next);
            });
        });
    }

    private void requestDownload() {
        CloudSaveService.Comparison expected = comparison;
        if (expected == null) {
            resetConfirmations();
            status = "REFRESH REQUIRED BEFORE DOWNLOAD";
            return;
        }
        boolean risky = expected.state() == CloudSaveService.ConflictState.LOCAL_AHEAD
            || expected.state() == CloudSaveService.ConflictState.DIVERGED;
        if (risky && !confirmDownload) {
            confirmDownload = true;
            confirmUpload = false;
            status = "DOWNLOAD WOULD REPLACE LOCAL PROGRESS — SELECT DOWNLOAD AGAIN TO CONFIRM";
            return;
        }
        resetConfirmations();
        runAsync("REVALIDATING CLOUD BEFORE DOWNLOAD...", () -> {
            CloudSaveAdapter.RemoteBackup remote = cloud.revalidateRemote(expected);
            post(() -> {
                if (remote == null) {
                    comparison = null;
                    conflict = null;
                    status = "CLOUD CHANGED — REFRESH REQUIRED";
                    return;
                }
                try {
                    CloudSaveService.RestoreResult result = cloud.applyRemote(remote.payload());
                    if (result.result() == CloudSaveService.DownloadResult.APPLIED) {
                        if (!game.applyCloudRestore(result)) status = "RESTORE FAILED TO APPLY";
                    } else {
                        status = "CLOUD SAVE REQUIRES A NEWER APP VERSION";
                    }
                } catch (RuntimeException e) {
                    comparison = null;
                    conflict = null;
                    status = "RESTORE ERROR: " + safeMessage(e);
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
                    status = "PLAY GAMES SIGN-IN REQUIRED";
                });
            } catch (CloudProviderConflictException e) {
                post(() -> {
                    comparison = null;
                    providerConflict = true;
                    authRequired = false;
                    conflict = CloudSaveService.ConflictState.DIVERGED;
                    resetConfirmations();
                    status = "PLAY GAMES HAS TWO CLOUD VERSIONS — CHOOSE SERVER OR OTHER";
                });
            } catch (CloudRemoteChangedException e) {
                post(() -> {
                    comparison = null;
                    conflict = null;
                    providerConflict = false;
                    resetConfirmations();
                    status = "CLOUD CHANGED ON ANOTHER DEVICE — REFRESH REQUIRED";
                });
            } catch (Exception e) {
                post(() -> {
                    comparison = null;
                    conflict = null;
                    providerConflict = false;
                    resetConfirmations();
                    status = "CLOUD ERROR: " + safeMessage(e);
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
        if (authRequired && cloud.supportsAuthentication()) return "SIGN IN";
        return providerConflict ? "RECHECK" : "REFRESH";
    }

    private String confirmationLine() {
        if (!cloud.available()) return "Configure Play Games Services in the production Android build.";
        if (authRequired) return "Sign in to Play Games before reading or writing cloud progress.";
        if (providerConflict) return "Resolve the provider conflict first; local progress is untouched.";
        if (comparison == null && !busy) return "Refresh successfully before any upload or download.";
        if (confirmUpload) return "Safety confirmation armed for UPLOAD.";
        if (confirmDownload) return "Safety confirmation armed for DOWNLOAD.";
        return busy ? "Operation in progress..." : "Every transfer is bound to the exact cloud version you reviewed.";
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
