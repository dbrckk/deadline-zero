package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.visual.VisualTheme;

/** Production-shaped Base/Home shell with functional navigation. */
public final class MenuScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private float t;

    public MenuScreen(DeadlineZeroGame game) { this.game = game; font.getData().setScale(2.2f); }

    @Override public void render(float delta) {
        t += delta;
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 16; i++) {
            float y = (i + 1) * h / 17f;
            float a = .018f + .012f * (float)Math.sin(t * 1.25f + i * .7f);
            shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, a);
            shapes.rect(0, y, w, 1.2f);
        }
        shapes.setColor(VisualTheme.PANEL); shapes.rect(18, h - 94, w - 36, 64);
        shapes.setColor(VisualTheme.PANEL); shapes.rect(18, 18, w - 36, 74);
        shapes.setColor(VisualTheme.PANEL_ALT); shapes.rect(w * .18f, h * .40f, w * .64f, h * .16f);
        shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .12f);
        shapes.circle(w * .5f, h * .48f, Math.min(w, h) * .25f, 96);
        shapes.setColor(VisualTheme.CYAN); shapes.rect(w * .30f, h * .255f, w * .40f, 64);
        shapes.setColor(VisualTheme.CYAN_SOFT); shapes.rect(w * .30f, h * .255f, w * .40f, 3f);
        shapes.end();

        PlayerProfile p = game.profile;
        batch.begin();
        font.setColor(VisualTheme.TEXT); font.draw(batch, GameConfig.TITLE, 0, h * .71f, w, Align.center, false);
        font.getData().setScale(.67f); font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, "SURVIVE THE LAST PROTOCOL", 0, h * .635f, w, Align.center, false);

        font.getData().setScale(.55f);
        font.setColor(VisualTheme.TEXT); font.draw(batch, "LV " + p.accountLevel, 34, h - 53);
        font.setColor(VisualTheme.GOLD); font.draw(batch, "CREDITS  " + p.currency(PlayerProfile.Currency.CREDITS), w * .27f, h - 53);
        font.setColor(VisualTheme.CYAN); font.draw(batch, "GEMS  " + p.currency(PlayerProfile.Currency.GEMS), w * .57f, h - 53);
        font.setColor(VisualTheme.MUTED); font.draw(batch, "STAGE  " + p.selectedStage + "/" + p.highestStage, w - 165, h - 53);

        font.getData().setScale(.70f); font.setColor(VisualTheme.TEXT);
        font.draw(batch, p.selectedSurvivor.displayName.toUpperCase(), 0, h * .515f, w, Align.center, false);
        font.getData().setScale(.47f); font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, p.selectedSurvivor.role + "  •  TAP / R TO CHANGE", 0, h * .465f, w, Align.center, false);

        font.getData().setScale(.82f); font.setColor(Color.WHITE);
        font.draw(batch, "DEPLOY", w * .30f, h * .255f + 42, w * .40f, Align.center, false);
        font.getData().setScale(.43f); font.setColor(new Color(.86f, .95f, 1f, 1f));
        font.draw(batch, "STAGE " + p.selectedStage + "  •  TAP / SPACE / ENTER", w * .30f, h * .255f + 17, w * .40f, Align.center, false);

        font.getData().setScale(.41f); font.setColor(VisualTheme.MUTED);
        font.draw(batch, "BASE", 28, 59);
        font.draw(batch, "GEAR [G]", w * .18f, 59);
        font.draw(batch, "MISSIONS [M]", w * .40f, 59);
        font.draw(batch, "SHOP [S]", w * .66f, 59);
        font.draw(batch, "SETTINGS [O]", w * .82f, 59);
        font.getData().setScale(2.2f);
        batch.end();
        handleInput(w, h);
    }

    private void handleInput(float w, float h) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); game.showGear(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); game.showMissions(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); game.showShop(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); game.showSurvivors(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); game.showSettings(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) { game.profile.selectStage(Math.max(1, game.profile.selectedStage - 1)); game.saveProfile(); }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) { game.profile.selectStage(Math.min(game.profile.highestStage, game.profile.selectedStage + 1)); game.saveProfile(); }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); game.startRun(); return; }
        if (!Gdx.input.justTouched()) return;
        float x = Gdx.input.getX(), y = h - Gdx.input.getY();
        if (y <= 95f) {
            if (x >= w * .15f && x < w * .37f) game.showGear();
            else if (x >= w * .37f && x < w * .62f) game.showMissions();
            else if (x >= w * .62f && x < w * .80f) game.showShop();
            else if (x >= w * .80f) game.showSettings();
            return;
        }
        if (y >= h * .40f && y <= h * .56f) { game.showSurvivors(); return; }
        if (x >= w * .30f && x <= w * .70f && y >= h * .255f && y <= h * .255f + 64f) game.startRun();
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
