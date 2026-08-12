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
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.meta.PlayerProfile;

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
        Gdx.gl.glClearColor(.012f, .018f, .027f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 14; i++) {
            float y = (i + 1) * h / 15f;
            float a = .025f + .015f * (float)Math.sin(t * 1.3f + i);
            shapes.setColor(.15f, .7f, .9f, a); shapes.rect(0, y, w, 1.5f);
        }
        shapes.setColor(.05f, .75f, 1f, .12f); shapes.circle(w * .5f, h * .48f, Math.min(w, h) * .24f, 96);
        shapes.setColor(.02f, .035f, .05f, .92f); shapes.rect(18, h - 92, w - 36, 62);
        shapes.setColor(.025f, .05f, .075f, .95f); shapes.rect(18, 18, w - 36, 72);
        shapes.setColor(.07f, .62f, .82f, .95f); shapes.rect(w * .30f, h * .255f, w * .40f, 64);
        shapes.end();

        PlayerProfile p = game.profile;
        batch.begin();
        font.setColor(Color.WHITE); font.draw(batch, GameConfig.TITLE, 0, h * .68f, w, Align.center, false);
        font.getData().setScale(.72f); font.setColor(.52f, .9f, 1f, 1f);
        font.draw(batch, "SURVIVE THE LAST PROTOCOL", 0, h * .59f, w, Align.center, false);
        font.getData().setScale(.58f);
        font.setColor(Color.WHITE); font.draw(batch, "LV " + p.accountLevel, 34, h - 52);
        font.setColor(Color.GOLD); font.draw(batch, "CREDITS  " + p.currency(PlayerProfile.Currency.CREDITS), w * .28f, h - 52);
        font.setColor(Color.CYAN); font.draw(batch, "GEMS  " + p.currency(PlayerProfile.Currency.GEMS), w * .58f, h - 52);
        font.setColor(Color.LIGHT_GRAY); font.draw(batch, "STAGE  " + p.selectedStage + "/" + p.highestStage, w - 165, h - 52);
        font.setColor(Color.CYAN);
        font.draw(batch, p.selectedSurvivor.displayName + " • " + p.selectedSurvivor.role + " [R]", 0, h * .49f, w, Align.center, false);
        font.getData().setScale(.84f); font.setColor(Color.WHITE);
        font.draw(batch, "DEPLOY", w * .30f, h * .255f + 42, w * .40f, Align.center, false);
        font.getData().setScale(.48f); font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "TAP • SPACE • ENTER", w * .30f, h * .255f + 17, w * .40f, Align.center, false);
        font.getData().setScale(.46f); font.setColor(Color.GRAY);
        font.draw(batch, "BASE", 28, 58); font.draw(batch, "GEAR [G]", w * .22f, 58);
        font.draw(batch, "MISSIONS [M]", w * .47f, 58); font.draw(batch, "SHOP [S]", w * .79f, 58);
        font.getData().setScale(2.2f); batch.end();
        handleInput(w, h);
    }

    private void handleInput(float w, float h) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) { game.showGear(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) { game.showMissions(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) { game.showShop(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { game.showSurvivors(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) { game.profile.selectStage(Math.max(1, game.profile.selectedStage - 1)); game.saveProfile(); }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) { game.profile.selectStage(Math.min(game.profile.highestStage, game.profile.selectedStage + 1)); game.saveProfile(); }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) { game.startRun(); return; }
        if (!Gdx.input.justTouched()) return;
        float x = Gdx.input.getX(), y = h - Gdx.input.getY();
        if (y <= 95f) {
            if (x >= w * .18f && x < w * .43f) game.showGear();
            else if (x >= w * .43f && x < w * .72f) game.showMissions();
            else if (x >= w * .72f) game.showShop();
            return;
        }
        if (y >= h * .43f && y <= h * .54f) { game.showSurvivors(); return; }
        if (x >= w * .30f && x <= w * .70f && y >= h * .255f && y <= h * .255f + 64f) game.startRun();
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
