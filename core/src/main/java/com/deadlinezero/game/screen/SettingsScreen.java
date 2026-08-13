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

/** Lightweight production settings screen with persistent accessibility and audio controls. */
public final class SettingsScreen extends ScreenAdapter {
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

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(VisualTheme.PANEL); shapes.rect(w * .14f, h * .13f, w * .72f, h * .72f);
        shapes.setColor(VisualTheme.CYAN); shapes.rect(w * .14f, h * .84f, w * .72f, 3f);
        float startY = h * .71f;
        float step = h * .065f;
        shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .13f);
        shapes.rect(w * .18f, startY - row * step - 24f, w * .64f, 36f);
        shapes.end();

        String[] labels = {
            "Screen shake", "Shake strength", "Hit stop", "Damage flash", "High contrast telegraphs",
            "Reduce flashes", "UI scale", "Master volume", "SFX volume", "Music volume"
        };
        String[] values = {
            onOff(s.screenShake), pct(s.screenShakeStrength), onOff(s.hitStop), onOff(s.damageFlash),
            onOff(s.highContrastTelegraphs), onOff(s.reduceFlashes), pct(s.uiScale), pct(s.masterVolume),
            pct(s.sfxVolume), pct(s.musicVolume)
        };

        batch.begin();
        font.getData().setScale(1.45f); font.setColor(VisualTheme.TEXT);
        font.draw(batch, "SETTINGS", 0, h * .91f, w, Align.center, false);
        font.getData().setScale(.55f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, "UP/DOWN SELECT  •  LEFT/RIGHT ADJUST  •  ESC BACK", 0, h * .855f, w, Align.center, false);

        for (int i = 0; i < labels.length; i++) {
            float y = startY - i * step;
            font.setColor(i == row ? VisualTheme.CYAN : VisualTheme.TEXT);
            font.draw(batch, labels[i], w * .20f, y);
            font.setColor(i == row ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
            font.draw(batch, values[i], w * .58f, y, w * .20f, Align.right, false);
        }
        batch.end();
        handleInput(s);
    }

    private void handleInput(AccessibilitySettings s) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { saveAndBack(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) row = Math.max(0, row - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) row = Math.min(9, row + 1);
        boolean left = Gdx.input.isKeyJustPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
        if (!left && !right) return;
        float dir = right ? 1f : -1f;
        switch (row) {
            case 0 -> s.screenShake = !s.screenShake;
            case 1 -> s.screenShakeStrength = clamp(s.screenShakeStrength + dir * .1f, 0f, 1f);
            case 2 -> s.hitStop = !s.hitStop;
            case 3 -> s.damageFlash = !s.damageFlash;
            case 4 -> s.highContrastTelegraphs = !s.highContrastTelegraphs;
            case 5 -> s.reduceFlashes = !s.reduceFlashes;
            case 6 -> s.uiScale = clamp(s.uiScale + dir * .05f, .85f, 1.35f);
            case 7 -> s.masterVolume = clamp(s.masterVolume + dir * .05f, 0f, 1f);
            case 8 -> s.sfxVolume = clamp(s.sfxVolume + dir * .05f, 0f, 1f);
            case 9 -> s.musicVolume = clamp(s.musicVolume + dir * .05f, 0f, 1f);
        }
        s.save();
        game.audio.setVolumes(s.masterVolume, s.sfxVolume, s.musicVolume);
        AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
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
