package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.config.GraphicsSettings;
import com.deadlinezero.game.ui.MetaLayout;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.VisualTheme;

/** Responsive production settings with large touch targets and persistent accessibility controls. */
public final class SettingsScreen extends ScreenAdapter {
    private static final int COLOR_VISION_ROW = 7;
    private static final int REDUCED_MOTION_ROW = 8;
    private static final int UI_SCALE_ROW = 9;
    private static final int MASTER_VOLUME_ROW = 10;
    private static final int SFX_VOLUME_ROW = 11;
    private static final int MUSIC_VOLUME_ROW = 12;
    private static final int GRAPHICS_ROW = 13;
    private static final int FRAME_RATE_ROW = 14;
    private static final int PRIVACY_ROW = 15;
    private static final int POLICY_ROW = 16;
    private static final int CLOUD_ROW = 17;
    private static final int LAST_ROW = CLOUD_ROW;
    private static final int ROWS_PER_COLUMN = 6;

    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private final Rectangle[] rows = new Rectangle[LAST_ROW + 1];
    private UiLayout.Metrics metrics;
    private MetaLayout.Layout layout;
    private int row;
    private float visualTime;

    public SettingsScreen(DeadlineZeroGame game) {
        this.game = game;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        layout = MetaLayout.compute(metrics);
        Rectangle[] columns = MetaLayout.columns(layout.content(), 3, 18f);
        for (int c = 0; c < 3; c++) {
            Rectangle[] columnRows = MetaLayout.rows(columns[c], ROWS_PER_COLUMN, 8f);
            for (int r = 0; r < ROWS_PER_COLUMN; r++) rows[c * ROWS_PER_COLUMN + r] = columnRows[r];
        }
    }

    @Override public void render(float delta) {
        visualTime += Math.max(0f, delta);
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);

        AccessibilitySettings s = game.accessibility;
        boolean privacyRequired = game.services.privacy.optionsRequired();
        boolean policyAvailable = game.services.privacy.policyAvailable();
        String[] labels = labels();
        String[] values = values(s, privacyRequired, policyAvailable);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, visualTime);
        UiRenderer.topRail(shapes, metrics);
        drawSettingsGroupFrames(shapes);
        for (int i = 0; i < rows.length; i++) {
            Rectangle r = rows[i];
            boolean disabled = isDisabled(i, privacyRequired, policyAvailable);
            Color accent = settingsAccent(i);
            UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, accent, i == row, false, disabled);
            if (disabled) {
                shapes.setColor(0f, 0f, 0f, .22f);
                shapes.rect(r.x + 3f, r.y + 3f, r.width - 6f, r.height - 6f);
            } else if (isSliderRow(i)) {
                float value = sliderValue(s, i);
                UiRenderer.segmentedTrack(shapes, r.x + r.width * .56f, r.y + 10f, r.width * .38f, 7f, value, 8, accent);
            }
        }
        shapes.end();

        batch.begin();
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * 1.05f);
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, t("shop.back"), layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
            layout.back().width - 16f, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("settings.title"), metrics.safeLeft() + 136f, metrics.headerBottom() + 56f,
            metrics.contentWidth() - 272f, Align.center, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, t("settings.subtitle"), metrics.safeLeft() + 136f, metrics.headerBottom() + 28f,
            metrics.contentWidth() - 272f, Align.center, false);

        for (int i = 0; i < rows.length; i++) drawRow(i, rows[i], labels[i], values[i], isDisabled(i, privacyRequired, policyAvailable));
        batch.end();

        handleInput(s, privacyRequired, policyAvailable);
    }

    private void drawSettingsGroupFrames(ShapeRenderer shapes) {
        Color[] accents = {VisualTheme.CYAN_SOFT, VisualTheme.VIOLET, VisualTheme.GOLD};
        for (int group = 0; group < 3; group++) {
            Rectangle top = rows[group * ROWS_PER_COLUMN];
            Rectangle bottom = rows[group * ROWS_PER_COLUMN + ROWS_PER_COLUMN - 1];
            float x = top.x - 5f;
            float y = bottom.y - 5f;
            float w = top.width + 10f;
            float h = top.y + top.height - bottom.y + 10f;
            UiRenderer.premiumPanel(shapes, x, y, w, h, accents[group], false);
            UiRenderer.sectionPlate(shapes, x + 6f, y + h - 12f, w - 12f, 8f, accents[group], true);
        }
    }

    private Color settingsAccent(int index) {
        if (index <= 5) return VisualTheme.CYAN_SOFT;
        if (index <= 11) return VisualTheme.VIOLET;
        return index <= 14 ? VisualTheme.GOLD : VisualTheme.accent();
    }

    private void drawRow(int index, Rectangle r, String label, String value, boolean disabled) {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(disabled ? VisualTheme.MUTED : index == row ? VisualTheme.accent() : VisualTheme.TEXT_DIM);
        font.draw(batch, label, r.x + 14f, r.y + r.height - 17f, r.width - 28f, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL) * 1.04f);
        font.setColor(disabled ? VisualTheme.MUTED : index == row ? VisualTheme.TEXT_STRONG : VisualTheme.TEXT);
        font.draw(batch, value, r.x + 14f, r.y + 23f, r.width - 28f, Align.right, false);
    }

    private void handleInput(AccessibilitySettings s, boolean privacyRequired, boolean policyAvailable) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { saveAndBack(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) row = Math.max(0, row - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) row = Math.min(LAST_ROW, row + 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !isSliderRow(row)) { adjustOrOpen(s, privacyRequired, policyAvailable, -1f); return; }
        if ((Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) && !isSliderRow(row)) {
            adjustOrOpen(s, privacyRequired, policyAvailable, 1f); return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && isSliderRow(row)) { applyAdjustment(s, -1f); persistSettings(s); return; }
        if ((Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) && isSliderRow(row)) {
            applyAdjustment(s, 1f); persistSettings(s); return;
        }

        if (!Gdx.input.justTouched()) return;
        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (layout.back().contains(touch)) { saveAndBack(); return; }
        for (int i = 0; i < rows.length; i++) {
            Rectangle r = rows[i];
            if (!r.contains(touch)) continue;
            row = i;
            if (row == PRIVACY_ROW) { if (privacyRequired) openPrivacy(); return; }
            if (row == POLICY_ROW) { if (policyAvailable) openPolicy(); return; }
            if (row == CLOUD_ROW) { openCloud(); return; }
            if (row == GRAPHICS_ROW) { GraphicsSettings.set(GraphicsSettings.active().next(1)); GraphicsSettings.save(); selectCue(); return; }
            if (row == FRAME_RATE_ROW) { GraphicsSettings.setFrameRate(GraphicsSettings.frameRate().next(1)); GraphicsSettings.save(); selectCue(); return; }
            if (isSliderRow(row)) setSliderFromTouch(s, row, touch.x, r);
            else applyAdjustment(s, 1f);
            persistSettings(s);
            return;
        }
    }

    private void adjustOrOpen(AccessibilitySettings s, boolean privacyRequired, boolean policyAvailable, float dir) {
        if (row == PRIVACY_ROW) { if (dir > 0f && privacyRequired) openPrivacy(); return; }
        if (row == POLICY_ROW) { if (dir > 0f && policyAvailable) openPolicy(); return; }
        if (row == CLOUD_ROW) { if (dir > 0f) openCloud(); return; }
        if (row == GRAPHICS_ROW) { GraphicsSettings.set(GraphicsSettings.active().next(dir > 0f ? 1 : -1)); GraphicsSettings.save(); selectCue(); return; }
        if (row == FRAME_RATE_ROW) { GraphicsSettings.setFrameRate(GraphicsSettings.frameRate().next(dir > 0f ? 1 : -1)); GraphicsSettings.save(); selectCue(); return; }
        applyAdjustment(s, dir);
        persistSettings(s);
    }

    private String[] labels() {
        return new String[] {
            t("settings.screenShake"), t("settings.shakeStrength"), t("settings.hitStop"), t("settings.damageFlash"),
            t("settings.highContrastTelegraphs"), t("settings.reduceFlashes"), t("settings.haptics"), t("settings.colorVision"),
            t("settings.reducedMotion"), t("settings.uiScale"), t("settings.masterVolume"), t("settings.sfxVolume"),
            t("settings.musicVolume"), t("settings.graphicsQuality"), t("settings.frameRate"),
            t("settings.privacyChoices"), t("settings.privacyPolicy"), t("settings.cloudSave")
        };
    }

    private String[] values(AccessibilitySettings s, boolean privacyRequired, boolean policyAvailable) {
        return new String[] {
            onOff(s.screenShake), pct(s.screenShakeStrength), onOff(s.hitStop), onOff(s.damageFlash),
            onOff(s.highContrastTelegraphs), onOff(s.reduceFlashes), onOff(s.haptics), s.colorVisionMode.label,
            onOff(s.reducedMotion), pct(s.uiScale), pct(s.masterVolume), pct(s.sfxVolume), pct(s.musicVolume),
            GraphicsSettings.active().name(), GraphicsSettings.frameRate().label,
            privacyRequired ? t("common.open") : t("common.notRequired"),
            policyAvailable ? t("common.open") : t("common.unavailable"),
            game.services.cloudSave.available() ? t("common.open") : t("common.notConfigured")
        };
    }

    private boolean isDisabled(int i, boolean privacyRequired, boolean policyAvailable) {
        return (i == PRIVACY_ROW && !privacyRequired) || (i == POLICY_ROW && !policyAvailable);
    }

    private void openPrivacy() {
        selectCue();
        game.services.privacy.showOptions(() -> Gdx.app.postRunnable(() -> AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK)));
    }
    private void openCloud() { selectCue(); game.showCloudSave(); }
    private void openPolicy() { selectCue(); game.services.privacy.openPolicy(); }

    private void setSliderFromTouch(AccessibilitySettings s, int targetRow, float x, Rectangle r) {
        float left = r.x + r.width * .56f;
        float right = r.x + r.width * .94f;
        float value = clamp((x - left) / Math.max(1f, right - left), 0f, 1f);
        switch (targetRow) {
            case 1 -> s.screenShakeStrength = value;
            case UI_SCALE_ROW -> s.uiScale = .85f + value * .50f;
            case MASTER_VOLUME_ROW -> s.masterVolume = value;
            case SFX_VOLUME_ROW -> s.sfxVolume = value;
            case MUSIC_VOLUME_ROW -> s.musicVolume = value;
            default -> { }
        }
    }

    private float sliderValue(AccessibilitySettings s, int targetRow) {
        return switch (targetRow) {
            case 1 -> s.screenShakeStrength;
            case UI_SCALE_ROW -> (s.uiScale - .85f) / .50f;
            case MASTER_VOLUME_ROW -> s.masterVolume;
            case SFX_VOLUME_ROW -> s.sfxVolume;
            case MUSIC_VOLUME_ROW -> s.musicVolume;
            default -> 0f;
        };
    }

    private void applyAdjustment(AccessibilitySettings s, float dir) {
        switch (row) {
            case 0 -> s.screenShake = !s.screenShake;
            case 1 -> s.screenShakeStrength = clamp(s.screenShakeStrength + dir * .1f, 0f, 1f);
            case 2 -> s.hitStop = !s.hitStop;
            case 3 -> s.damageFlash = !s.damageFlash;
            case 4 -> s.highContrastTelegraphs = !s.highContrastTelegraphs;
            case 5 -> s.reduceFlashes = !s.reduceFlashes;
            case 6 -> s.haptics = !s.haptics;
            case COLOR_VISION_ROW -> s.colorVisionMode = s.colorVisionMode.next(dir > 0f ? 1 : -1);
            case REDUCED_MOTION_ROW -> s.setReducedMotion(!s.reducedMotion);
            case UI_SCALE_ROW -> s.uiScale = clamp(s.uiScale + dir * .05f, .85f, 1.35f);
            case MASTER_VOLUME_ROW -> s.masterVolume = clamp(s.masterVolume + dir * .05f, 0f, 1f);
            case SFX_VOLUME_ROW -> s.sfxVolume = clamp(s.sfxVolume + dir * .05f, 0f, 1f);
            case MUSIC_VOLUME_ROW -> s.musicVolume = clamp(s.musicVolume + dir * .05f, 0f, 1f);
            default -> { }
        }
    }

    private void persistSettings(AccessibilitySettings s) {
        s.save();
        game.audio.setVolumes(s.masterVolume, s.sfxVolume, s.musicVolume);
        selectCue();
    }
    private static boolean isSliderRow(int value) { return value == 1 || value == UI_SCALE_ROW || value == MASTER_VOLUME_ROW || value == SFX_VOLUME_ROW || value == MUSIC_VOLUME_ROW; }
    private void saveAndBack() { game.accessibility.save(); AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); }
    private void selectCue() { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); }
    private String onOff(boolean value) { return value ? t("common.on") : t("common.off"); }
    private String t(String key) { return game.i18n.text(key); }
    private static String pct(float value) { return Math.round(value * 100f) + "%"; }
    private static float clamp(float v, float min, float max) { return Math.max(min, Math.min(max, v)); }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
