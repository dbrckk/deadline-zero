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
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.visual.VisualTheme;

/** Lightweight production settings screen with persistent accessibility, audio and privacy controls. */
public final class SettingsScreen extends ScreenAdapter {
    private static final int PRIVACY_ROW = 11;
    private static final int LAST_ROW = PRIVACY_ROW;

    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private int row;

    public SettingsScreen(DeadlineZeroGame game) { this.game = game; }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        AccessibilitySettings s = game.accessibility;
        boolean privacyRequired = game.services.privacy.optionsRequired();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(VisualTheme.PANEL); shapes.rect(w * .14f, h * .10f, w * .72f, h * .75f);
        shapes.setColor(VisualTheme.CYAN); shapes.rect(w * .14f, h * .84f, w * .72f, 3f);
        float startY = h * .72f;
        float step = h * .055f;
        shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .13f);
        shapes.rect(w * .18f, startY - row * step - 24f, w * .64f, 36f);
        shapes.end();

        String[] labels = {
            "Screen shake", "Shake strength", "Hit stop", "Damage flash", "High contrast telegraphs",
            "Reduce flashes", "Haptics", "UI scale", "Master volume", "SFX volume", "Music volume",
            "Privacy choices"
        };
        String[] values = {
            onOff(s.screenShake), pct(s.screenShakeStrength), onOff(s.hitStop), onOff(s.damageFlash),
            onOff(s.highContrastTelegraphs), onOff(s.reduceFlashes), onOff(s.haptics), pct(s.uiScale),
            pct(s.masterVolume), pct(s.sfxVolume), pct(s.musicVolume),
            privacyRequired ? "OPEN" : "NOT REQUIRED"
        };

        batch.begin();
        font.getData().setScale(1.45f); font.setColor(VisualTheme.TEXT);
        font.draw(batch, "SETTINGS", 0, h * .91f, w, Align.center, false);
        font.getData().setScale(.55f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, "TAP TO ADJUST  •  TAP TOP-LEFT TO GO BACK", 0, h * .855f, w, Align.center, false);

        for (int i = 0; i < labels.length; i++) {
            float y = startY - i * step;
            boolean disabled = i == PRIVACY_ROW && !privacyRequired;
            font.setColor(disabled ? VisualTheme.MUTED : (i == row ? VisualTheme.CYAN : VisualTheme.TEXT));
            font.draw(batch, labels[i], w * .20f, y);
            font.setColor(disabled ? VisualTheme.MUTED : (i == row ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED));
            font.draw(batch, values[i], w * .58f, y, w * .20f, Align.right, false);
        }
        batch.end();
        handleInput(s, privacyRequired, w, h, startY, step);
    }

    private void handleInput(AccessibilitySettings s, boolean privacyRequired,
                             float w, float h, float startY, float step) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { saveAndBack(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) row = Math.max(0, row - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) row = Math.min(LAST_ROW, row + 1);

        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = h - Gdx.input.getY();
            if (x <= w * .24f && y >= h * .86f) {
                saveAndBack();
                return;
            }
            int touchedRow = Math.round((startY - y) / step);
            if (touchedRow >= 0 && touchedRow <= LAST_ROW
                && Math.abs(y - (startY - touchedRow * step)) <= Math.max(22f, step * .48f)) {
                row = touchedRow;
                if (row == PRIVACY_ROW) {
                    if (privacyRequired) openPrivacy();
                    return;
                }
                if (isSliderRow(row)) {
                    setSliderFromTouch(s, row, x, w);
                } else {
                    applyAdjustment(s, 1f);
                }
                persistSettings(s);
                return;
            }
        }

        boolean left = Gdx.input.isKeyJustPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
        if (!left && !right) return;

        if (row == PRIVACY_ROW) {
            if (right && privacyRequired) openPrivacy();
            return;
        }

        applyAdjustment(s, right ? 1f : -1f);
        persistSettings(s);
    }

    private void openPrivacy() {
        AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
        game.services.privacy.showOptions(() -> Gdx.app.postRunnable(
            () -> AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK)
        ));
    }

    private void setSliderFromTouch(AccessibilitySettings s, int targetRow, float x, float w) {
        float left = w * .55f;
        float right = w * .80f;
        float t = clamp((x - left) / Math.max(1f, right - left), 0f, 1f);
        switch (targetRow) {
            case 1 -> s.screenShakeStrength = t;
            case 7 -> s.uiScale = .85f + t * .50f;
            case 8 -> s.masterVolume = t;
            case 9 -> s.sfxVolume = t;
            case 10 -> s.musicVolume = t;
            default -> { }
        }
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
            case 7 -> s.uiScale = clamp(s.uiScale + dir * .05f, .85f, 1.35f);
            case 8 -> s.masterVolume = clamp(s.masterVolume + dir * .05f, 0f, 1f);
            case 9 -> s.sfxVolume = clamp(s.sfxVolume + dir * .05f, 0f, 1f);
            case 10 -> s.musicVolume = clamp(s.musicVolume + dir * .05f, 0f, 1f);
            default -> { }
        }
    }

    private void persistSettings(AccessibilitySettings s) {
        s.save();
        game.audio.setVolumes(s.masterVolume, s.sfxVolume, s.musicVolume);
        AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
    }

    private static boolean isSliderRow(int value) {
        return value == 1 || value == 7 || value == 8 || value == 9 || value == 10;
    }

    private void saveAndBack() {
        game.accessibility.save();
        AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
        game.showMenu();
    }

    private static String onOff(boolean value) { return value ? "ON" : "OFF"; }
    private static String pct(float value) { return Math.round(value * 100f) + "%"; }
    private static float clamp(float v, float min, float max) { return Math.max(min, Math.min(max, v)); }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
