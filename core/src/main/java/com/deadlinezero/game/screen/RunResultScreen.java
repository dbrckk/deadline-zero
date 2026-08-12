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
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.RunResult;
import com.deadlinezero.game.services.AdsService;

public final class RunResultScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final RunResult result;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private boolean bonusClaimed;

    public RunResultScreen(DeadlineZeroGame game, RunResult result) {
        this.game = game;
        this.result = result;
    }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(.012f, .017f, .025f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.035f, .055f, .075f, 1f);
        shapes.rect(w * .12f, h * .16f, w * .76f, h * .68f);
        shapes.setColor(.08f, .72f, 1f, .18f);
        shapes.rect(w * .15f, h * .21f, w * .70f, h * .12f);
        shapes.end();

        batch.begin();
        font.getData().setScale(1.6f);
        font.setColor(Color.WHITE);
        font.draw(batch, "RUN COMPLETE", 0, h * .78f, w, Align.center, false);
        font.getData().setScale(.78f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Stage " + result.stage() + "  •  Kills " + result.kills() + "  •  " + formatTime(result.secondsSurvived()), 0, h * .68f, w, Align.center, false);
        font.setColor(Color.GOLD);
        font.draw(batch, "+" + result.rewards().credits() + " Credits", 0, h * .57f, w, Align.center, false);
        font.setColor(Color.CYAN);
        font.draw(batch, "+" + result.rewards().gems() + " Gems   +" + result.rewards().accountXp() + " Account XP", 0, h * .51f, w, Align.center, false);
        if (result.drop() != null) {
            font.setColor(Color.WHITE);
            font.draw(batch, "DROP: " + result.drop().rarity.name() + " " + result.drop().name + "  Lv." + result.drop().level, 0, h * .43f, w, Align.center, false);
        }
        font.setColor(bonusClaimed ? Color.GRAY : Color.LIME);
        font.draw(batch, bonusClaimed ? "2X LOOT CLAIMED" : "[D] WATCH REWARDED AD: +100% CREDITS", 0, h * .29f, w, Align.center, false);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "[ENTER] BASE     [R] DEPLOY AGAIN", 0, h * .22f, w, Align.center, false);
        batch.end();

        if (!bonusClaimed && Gdx.input.isKeyJustPressed(Input.Keys.D)) claimDoubleCredits();
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) game.startRun();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) game.showMenu();
    }

    private void claimDoubleCredits() {
        game.services.ads.showRewarded(AdsService.Reward.DOUBLE_LOOT, () -> {
            if (bonusClaimed) return;
            bonusClaimed = true;
            game.profile.addCurrency(PlayerProfile.Currency.CREDITS, result.rewards().credits());
            game.saveProfile();
            game.services.ads.preload();
        }, game.services.ads::preload);
    }

    private static String formatTime(float seconds) {
        int total = Math.max(0, (int)seconds);
        return String.format("%02d:%02d", total / 60, total % 60);
    }

    @Override public void dispose() {
        batch.dispose();
        font.dispose();
        shapes.dispose();
    }
}
