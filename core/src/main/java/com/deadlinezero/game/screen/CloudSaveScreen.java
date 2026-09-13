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
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.services.CloudSaveService;
import com.deadlinezero.game.visual.VisualTheme;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Explicit cloud-save management. Never resolves divergent progress automatically. */
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
    private CloudSaveService.ConflictState conflict;
    private String status;
    private boolean confirmUpload;
    private boolean confirmDownload;

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
        shapes.end();

        batch.begin();
        font.getData().setScale(1.65f);
        font.setColor(VisualTheme.TEXT);
        font.draw(batch, "CLOUD SAVE", 0, h * .76f, w, Align.center, false);

        font.getData().setScale(.78f);
        font.setColor(colorForState());
        font.draw(batch, status, w * .20f, h * .64f, w * .60f, Align.center, true);

        font.getData().setScale(.58f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, "Cloud actions are manual. Divergent progress is never overwritten automatically.",
            w * .20f, h * .55f, w * .60f, Align.center, true);

        font.getData().setScale(.72f);
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, "[R] REFRESH", w * .22f, h * .40f);
        font.setColor(VisualTheme.GOLD);
        font.draw(batch, "[U] UPLOAD LOCAL", w * .42f, h * .40f);
        font.setColor(VisualTheme.TEXT);
        font.draw(batch, "[D] DOWNLOAD CLOUD", w * .64f, h * .40f);

        font.getData().setScale(.52f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, confirmationLine(), w * .20f, h * .30f, w * .60f, Align.center, true);
        font.draw(batch, "ESC / BACK  •  SETTINGS", w * .20f, h * .20f, w * .60f, Align.center, false);
        batch.end();

        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.showSettings();
            return;
        }
        if (busy || !cloud.available()) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { resetConfirmations(); refresh(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) { requestUpload(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) { requestDownload(); }
    }

    private void refresh() {
        runAsync("CHECKING CLOUD...", () -> {
            CloudSaveService.ConflictState next = cloud.compareRemoteToLocal();
            post(() -> {
                conflict = next;
                status = switch (next) {
                    case EQUAL -> "LOCAL AND CLOUD ARE IDENTICAL";
                    case LOCAL_AHEAD -> "LOCAL PROGRESS IS AHEAD";
                    case REMOTE_AHEAD -> "CLOUD PROGRESS IS AHEAD";
                    case DIVERGED -> "CONFLICT: PROGRESS HAS DIVERGED";
                };
            });
        });
    }

    private void requestUpload() {
        boolean risky = conflict == CloudSaveService.ConflictState.REMOTE_AHEAD
            || conflict == CloudSaveService.ConflictState.DIVERGED;
        if (risky && !confirmUpload) {
            confirmUpload = true;
            confirmDownload = false;
            status = "UPLOAD WOULD REPLACE CLOUD PROGRESS — PRESS U AGAIN TO CONFIRM";
            return;
        }
        resetConfirmations();
        runAsync("UPLOADING LOCAL PROFILE...", () -> {
            cloud.uploadLocal();
            post(() -> {
                status = "UPLOAD COMPLETE";
                conflict = CloudSaveService.ConflictState.EQUAL;
            });
        });
    }

    private void requestDownload() {
        boolean risky = conflict == CloudSaveService.ConflictState.LOCAL_AHEAD
            || conflict == CloudSaveService.ConflictState.DIVERGED;
        if (risky && !confirmDownload) {
            confirmDownload = true;
            confirmUpload = false;
            status = "DOWNLOAD WOULD REPLACE LOCAL PROGRESS — PRESS D AGAIN TO CONFIRM";
            return;
        }
        resetConfirmations();
        runAsync("DOWNLOADING CLOUD PROFILE...", () -> {
            CloudSaveService.RestoreResult result = cloud.downloadRemote();
            post(() -> {
                if (result.result() == CloudSaveService.DownloadResult.APPLIED) {
                    if (!game.applyCloudRestore(result)) status = "RESTORE FAILED TO APPLY";
                } else if (result.result() == CloudSaveService.DownloadResult.EMPTY_REMOTE) {
                    status = "NO CLOUD SAVE FOUND";
                } else {
                    status = "CLOUD SAVE REQUIRES A NEWER APP VERSION";
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
            } catch (Exception e) {
                post(() -> status = "CLOUD ERROR: " + safeMessage(e));
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

    private String confirmationLine() {
        if (!cloud.available()) return "Configure Play Games Services in the production Android build.";
        if (confirmUpload) return "Safety confirmation armed for UPLOAD.";
        if (confirmDownload) return "Safety confirmation armed for DOWNLOAD.";
        return busy ? "Operation in progress..." : "Refresh before choosing a direction when using multiple devices.";
    }

    private com.badlogic.gdx.graphics.Color colorForState() {
        if (!cloud.available()) return VisualTheme.MUTED;
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
