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
import com.deadlinezero.game.meta.RunResult;

public final class VictoryScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final RunResult result;
    private final boolean firstClear;
    private final long bonusCredits;
    private final int bonusGems;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();

    public VictoryScreen(DeadlineZeroGame game, RunResult result, boolean firstClear, long bonusCredits, int bonusGems) {
        this.game = game;
        this.result = result;
        this.firstClear = firstClear;
        this.bonusCredits = bonusCredits;
        this.bonusGems = bonusGems;
    }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(.008f, .026f, .03f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.04f, .15f, .16f, 1f); shapes.rect(w * .10f, h * .12f, w * .80f, h * .76f);
        shapes.setColor(.10f, .95f, .68f, .16f); shapes.rect(w * .14f, h * .69f, w * .72f, h * .10f);
        shapes.setColor(.05f, .42f, .38f, .85f); shapes.rect(w * .24f, h * .18f, w * .22f, 56f);
        shapes.setColor(.08f, .62f, .82f, .85f); shapes.rect(w * .54f, h * .18f, w * .22f, 56f);
        shapes.end();

        batch.begin();
        font.getData().setScale(1.7f); font.setColor(Color.WHITE);
        font.draw(batch, "PROTOCOL CLEARED", 0, h * .78f, w, Align.center, false);
        font.getData().setScale(.72f); font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Stage " + result.stage() + "  •  Kills " + result.kills() + "  •  " + formatTime(result.secondsSurvived()), 0, h * .65f, w, Align.center, false);
        font.getData().setScale(.56f); font.setColor(Color.ORANGE);
        font.draw(batch, "CONTRACT  " + result.contractTitle() + "  •  +" + result.contractBonusPercent() + "% REWARD", 0, h * .605f, w, Align.center, false);
        font.getData().setScale(.72f); font.setColor(Color.GOLD);
        font.draw(batch, "+" + result.rewards().credits() + " Credits", 0, h * .54f, w, Align.center, false);
        font.setColor(Color.CYAN);
        font.draw(batch, "+" + result.rewards().gems() + " Gems   +" + result.rewards().accountXp() + " Account XP", 0, h * .48f, w, Align.center, false);
        if (firstClear) {
            font.setColor(Color.LIME);
            font.draw(batch, "FIRST CLEAR  +" + bonusCredits + " Credits  +" + bonusGems + " Gems", 0, h * .40f, w, Align.center, false);
        }
        if (result.drop() != null) {
            font.setColor(Color.WHITE);
            font.draw(batch, "DROP: " + result.drop().rarity.name() + " " + result.drop().name + " Lv." + result.drop().level, 0, h * .33f, w, Align.center, false);
        }
        font.setColor(Color.WHITE);
        font.draw(batch, "BASE", w * .24f, h * .18f + 36f, w * .22f, Align.center, false);
        font.draw(batch, "NEXT STAGE", w * .54f, h * .18f + 36f, w * .22f, Align.center, false);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) game.showMenu();
        if (Gdx.input.isKeyJustPressed(Input.Keys.R) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) game.startRun();
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            if (x < w * .5f) game.showMenu(); else game.startRun();
        }
    }

    private static String formatTime(float seconds) {
        int total = Math.max(0, (int)seconds);
        return String.format("%02d:%02d", total / 60, total % 60);
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
