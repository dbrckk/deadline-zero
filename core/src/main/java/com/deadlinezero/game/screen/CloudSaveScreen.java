package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.services.CloudAuthenticationRequiredException;
import com.deadlinezero.game.services.CloudProviderConflictException;
import com.deadlinezero.game.services.CloudRemoteChangedException;
import com.deadlinezero.game.services.CloudSaveAdapter;
import com.deadlinezero.game.services.CloudSaveService;
import com.deadlinezero.game.ui.MetaLayout;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
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
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private final ExecutorService worker = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "deadline-zero-cloud-save");
        t.setDaemon(true);
        return t;
    });

    private UiLayout.Metrics metrics;
    private MetaLayout.Layout layout;
    private Rectangle statusPanel;
    private Rectangle warningPanel;
    private Rectangle[] actions;
    private volatile boolean busy;
    private volatile boolean disposed;
    private CloudSaveService.Comparison comparison;
    private CloudSaveService.ConflictState conflict;
    private String status;
    private boolean confirmUpload;
    private boolean confirmDownload;
    private boolean providerConflict;
    private boolean authRequired;
    private float visualTime;

    public CloudSaveScreen(DeadlineZeroGame game) {
        this.game = game;
        this.cloud = new CloudSaveService(game.services.cloudSave);
        status = cloud.available() ? t("cloud.checking") : t("cloud.notConfigured");
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (cloud.available()) refresh();
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        layout = MetaLayout.compute(metrics);
        Rectangle c = layout.content();
        statusPanel = new Rectangle(c.x + 28f, c.y + c.height * .54f, c.width - 56f, c.height * .39f);
        warningPanel = new Rectangle(c.x + 28f, c.y + c.height * .34f, c.width - 56f, c.height * .15f);
        Rectangle actionArea = new Rectangle(c.x + 28f, c.y + 24f, c.width - 56f, Math.max(78f, c.height * .22f));
        actions = MetaLayout.columns(actionArea, 3, 18f);
    }

    @Override public void render(float delta) {
        visualTime += Math.max(0f, delta);
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);

        boolean available = cloud.available();
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, visualTime);
        UiRenderer.topRail(shapes, metrics);
        UiRenderer.card(shapes, statusPanel.x, statusPanel.y, statusPanel.width, statusPanel.height, false,
            conflict == CloudSaveService.ConflictState.DIVERGED || providerConflict);
        UiRenderer.panel(shapes, warningPanel.x, warningPanel.y, warningPanel.width, warningPanel.height);
        for (int i = 0; i < actions.length; i++) {
            UiRenderer.ButtonState state = (!available || busy) ? UiRenderer.ButtonState.DISABLED : UiRenderer.ButtonState.NORMAL;
            if ((i == 1 && confirmUpload) || (i == 2 && confirmDownload)) state = UiRenderer.ButtonState.DANGER;
            UiRenderer.button(shapes, actions[i].x, actions[i].y, actions[i].width, actions[i].height, state);
        }
        shapes.end();

        batch.begin();
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, t("cloud.back"), layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
            layout.back().width - 16f, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("cloud.title"), metrics.safeLeft() + 138f, metrics.headerBottom() + 54f,
            metrics.contentWidth() - 276f, Align.center, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
        font.setColor(colorForState());
        font.draw(batch, status, statusPanel.x + 28f, statusPanel.y + statusPanel.height * .67f,
            statusPanel.width - 56f, Align.center, true);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, stateSummary(), statusPanel.x + 28f, statusPanel.y + statusPanel.height * .30f,
            statusPanel.width - 56f, Align.center, true);

        font.setColor(VisualTheme.GOLD);
        font.draw(batch, t("cloud.manualWarning"), warningPanel.x + 22f,
            warningPanel.y + warningPanel.height * .62f, warningPanel.width - 44f, Align.center, true);

        String[] labels = {primaryLabel(), providerConflict ? t("cloud.useServer") : t("cloud.uploadLocal"),
            providerConflict ? t("cloud.useOther") : t("cloud.downloadCloud")};
        for (int i = 0; i < actions.length; i++) {
            Rectangle a = actions[i];
            font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
            font.setColor(!available || busy ? VisualTheme.MUTED : i == 1 ? VisualTheme.GOLD : i == 2 ? VisualTheme.TEXT : VisualTheme.CYAN_SOFT);
            font.draw(batch, labels[i], a.x + 10f, a.y + a.height * .60f, a.width - 20f, Align.center, true);
        }
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(confirmUpload || confirmDownload ? VisualTheme.danger() : VisualTheme.TEXT_DIM);
        font.draw(batch, confirmationLine(), layout.footer().x + 20f, layout.footer().y + layout.footer().height * .56f,
            layout.footer().width - 40f, Align.center, true);
        batch.end();

        handleInput();
    }

    private String stateSummary() {
        if (!cloud.available()) return t("cloud.configure");
        if (authRequired) return t("cloud.authHelp");
        if (providerConflict) return t("cloud.conflictHelp");
        if (comparison == null) return busy ? t("cloud.inProgress") : t("cloud.refreshHelp");
        return t("cloud.versionBound");
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { game.showSettings(); return; }
        if (!busy && cloud.available()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { primaryAction(); return; }
            if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
                if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.SERVER); else requestUpload();
                return;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.CONFLICTING); else requestDownload();
                return;
            }
        }
        if (!Gdx.input.justTouched()) return;
        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (layout.back().contains(touch)) { game.showSettings(); return; }
        if (busy || !cloud.available()) return;
        if (actions[0].contains(touch)) { primaryAction(); return; }
        if (actions[1].contains(touch)) {
            if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.SERVER); else requestUpload();
            return;
        }
        if (actions[2].contains(touch)) {
            if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.CONFLICTING); else requestDownload();
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
            post(() -> { authRequired = false; applyComparison(next); });
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
            post(() -> { resetConfirmations(); applyComparison(next); });
        });
    }

    private void requestUpload() {
        CloudSaveService.Comparison expected = comparison;
        if (expected == null) { resetConfirmations(); status = t("cloud.refreshUpload"); return; }
        boolean risky = expected.state() == CloudSaveService.ConflictState.REMOTE_AHEAD || expected.state() == CloudSaveService.ConflictState.DIVERGED;
        if (risky && !confirmUpload) { confirmUpload = true; confirmDownload = false; status = t("cloud.confirmUpload"); return; }
        resetConfirmations();
        runAsync(t("cloud.revalidateUpload"), () -> {
            cloud.uploadIfUnchanged(expected);
            CloudSaveService.Comparison next = inspectComparisonOrConflict();
            post(() -> { status = t("cloud.uploadComplete"); applyComparison(next); });
        });
    }

    private void requestDownload() {
        CloudSaveService.Comparison expected = comparison;
        if (expected == null) { resetConfirmations(); status = t("cloud.refreshDownload"); return; }
        boolean risky = expected.state() == CloudSaveService.ConflictState.LOCAL_AHEAD || expected.state() == CloudSaveService.ConflictState.DIVERGED;
        if (risky && !confirmDownload) { confirmDownload = true; confirmUpload = false; status = t("cloud.confirmDownload"); return; }
        resetConfirmations();
        runAsync(t("cloud.revalidateDownload"), () -> {
            CloudSaveAdapter.RemoteBackup remote = cloud.revalidateRemote(expected);
            post(() -> {
                if (remote == null) { comparison = null; conflict = null; status = t("cloud.changed"); return; }
                try {
                    CloudSaveService.RestoreResult result = cloud.applyRemote(remote.payload());
                    if (result.result() == CloudSaveService.DownloadResult.APPLIED) {
                        if (!game.applyCloudRestore(result)) status = t("cloud.restoreFailed");
                    } else status = t("cloud.newerVersion");
                } catch (RuntimeException e) {
                    comparison = null; conflict = null; status = f("cloud.restoreError", safeMessage(e));
                }
            });
        });
    }

    private void runAsync(String runningStatus, ThrowingAction action) {
        busy = true;
        status = runningStatus;
        worker.submit(() -> {
            try { action.run(); }
            catch (CloudAuthenticationRequiredException e) {
                post(() -> { comparison = null; conflict = null; providerConflict = false; authRequired = true; resetConfirmations(); status = t("cloud.signInRequired"); });
            } catch (CloudProviderConflictException e) {
                post(() -> { comparison = null; providerConflict = true; authRequired = false; conflict = CloudSaveService.ConflictState.DIVERGED; resetConfirmations(); status = t("cloud.providerConflict"); });
            } catch (CloudRemoteChangedException e) {
                post(() -> { comparison = null; conflict = null; providerConflict = false; resetConfirmations(); status = t("cloud.changedOther"); });
            } catch (Exception e) {
                post(() -> { comparison = null; conflict = null; providerConflict = false; resetConfirmations(); status = f("cloud.error", safeMessage(e)); });
            } finally { post(() -> busy = false); }
        });
    }

    private void post(Runnable action) {
        if (disposed) return;
        Gdx.app.postRunnable(() -> { if (!disposed) action.run(); });
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
        if (conflict == CloudSaveService.ConflictState.DIVERGED) return VisualTheme.danger();
        if (conflict == CloudSaveService.ConflictState.REMOTE_AHEAD) return VisualTheme.GOLD;
        return VisualTheme.accent();
    }

    private void resetConfirmations() { confirmUpload = false; confirmDownload = false; }
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
