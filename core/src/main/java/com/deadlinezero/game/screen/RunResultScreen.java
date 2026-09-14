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
import com.deadlinezero.game.meta.RunRecoveryAdvice;
import com.deadlinezero.game.meta.RunResult;
import com.deadlinezero.game.services.AdsService;

public final class RunResultScreen extends ScreenAdapter {
    private static final Color COACHING_ACCENT = new Color(.78f, .64f, 1f, 1f);

    private final DeadlineZeroGame game;
    private final RunResult result;
    private final RunRecoveryAdvice.Advice advice;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private boolean bonusClaimed;

    public RunResultScreen(DeadlineZeroGame game, RunResult result) {
        this.game = game;
        this.result = result;
        this.advice = RunRecoveryAdvice.forResult(result);
    }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(.012f, .017f, .025f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.035f, .055f, .075f, 1f);
        shapes.rect(w * .12f, h * .13f, w * .76f, h * .72f);
        shapes.setColor(.08f, .72f, 1f, .18f);
        shapes.rect(w * .15f, h * .20f, w * .70f, h * .10f);
        shapes.setColor(.25f, .16f, .38f, .42f);
        shapes.rect(w * .18f, h * .305f, w * .64f, h * .105f);
        shapes.end();

        batch.begin();
        font.getData().setScale(1.6f);
        font.setColor(Color.WHITE);
        font.draw(batch, t("result.complete"), 0, h * .78f, w, Align.center, false);
        font.getData().setScale(.78f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, f("result.summary", result.stage(), result.kills(), formatTime(result.secondsSurvived())), 0, h * .68f, w, Align.center, false);
        font.getData().setScale(.54f);
        font.setColor(Color.ORANGE);
        font.draw(batch, f("result.contract", result.contractTitle(), result.contractBonusPercent()), 0, h * .625f, w, Align.center, false);
        font.setColor(result.threatTier() > 0 ? Color.GOLD : Color.LIGHT_GRAY);
        font.draw(batch, f("result.threat", result.threatTier(), result.threatBonusPercent()), 0, h * .585f, w, Align.center, false);
        font.getData().setScale(.78f);
        font.setColor(Color.GOLD);
        font.draw(batch, f("result.credits", result.rewards().credits()), 0, h * .52f, w, Align.center, false);
        font.setColor(Color.CYAN);
        font.draw(batch, f("result.gemsXp", result.rewards().gems(), result.rewards().accountXp()), 0, h * .46f, w, Align.center, false);
        if (result.drop() != null) {
            font.getData().setScale(.58f);
            font.setColor(Color.WHITE);
            font.draw(batch, f("result.drop", result.drop().rarity.name(), result.drop().name, result.drop().level), 0, h * .415f, w, Align.center, false);
        }

        font.getData().setScale(.52f);
        font.setColor(COACHING_ACCENT);
        font.draw(batch, advice.headline(), 0, h * .375f, w, Align.center, false);
        font.getData().setScale(.40f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, advice.detail(), w * .20f, h * .338f, w * .60f, Align.center, true);

        font.getData().setScale(.54f);
        font.setColor(bonusClaimed ? Color.GRAY : Color.LIME);
        font.draw(batch, bonusClaimed ? t("result.doubleClaimed") : t("result.doubleOffer"), 0, h * .26f, w, Align.center, false);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, t("result.footer"), 0, h * .19f, w, Align.center, false);
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

    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() {
        batch.dispose();
        font.dispose();
        shapes.dispose();
    }
}
