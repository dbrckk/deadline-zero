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
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.meta.MasteryRunNotice;
import com.deadlinezero.game.meta.RunResult;
import com.deadlinezero.game.meta.RunShareText;
import com.deadlinezero.game.meta.ThreatMilestoneRewardCatalog;

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
        boolean canShare = game.services.share.available();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(.04f, .15f, .16f, 1f); shapes.rect(w * .10f, h * .12f, w * .80f, h * .76f);
        shapes.setColor(.10f, .95f, .68f, .16f); shapes.rect(w * .14f, h * .69f, w * .72f, h * .10f);
        shapes.setColor(.05f, .42f, .38f, .85f); shapes.rect(w * .13f, h * .18f, w * .20f, 56f);
        shapes.setColor(canShare ? new Color(.34f, .20f, .62f, .90f) : new Color(.12f, .14f, .17f, .75f));
        shapes.rect(w * .40f, h * .18f, w * .20f, 56f);
        shapes.setColor(.08f, .62f, .82f, .85f); shapes.rect(w * .67f, h * .18f, w * .20f, 56f);
        shapes.end();

        batch.begin();
        font.getData().setScale(1.7f); font.setColor(Color.WHITE);
        font.draw(batch, t("victory.title"), 0, h * .78f, w, Align.center, false);
        font.getData().setScale(.72f); font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, f("result.summary", result.stage(), result.kills(), formatTime(result.secondsSurvived())), 0, h * .65f, w, Align.center, false);
        font.getData().setScale(.52f); font.setColor(Color.ORANGE);
        font.draw(batch, f("result.contract", result.contractTitle(), result.contractBonusPercent()), 0, h * .605f, w, Align.center, false);
        font.setColor(result.threatTier() > 0 ? Color.GOLD : Color.LIGHT_GRAY);
        font.draw(batch, f("result.threat", result.threatTier(), result.threatBonusPercent()), 0, h * .565f, w, Align.center, false);
        font.getData().setScale(.72f); font.setColor(Color.GOLD);
        font.draw(batch, f("result.credits", result.rewards().credits()), 0, h * .51f, w, Align.center, false);
        font.setColor(Color.CYAN);
        font.draw(batch, f("result.gemsXp", result.rewards().gems(), result.rewards().accountXp()), 0, h * .455f, w, Align.center, false);
        if (result.unlockedThreatTier() > 0) {
            font.getData().setScale(.60f);
            font.setColor(Color.GOLD);
            String milestone = result.threatMilestoneGems() > 0 ? f("victory.milestone", result.threatMilestoneGems()) : "";
            font.draw(batch, f("victory.threatUnlocked", result.unlockedThreatTier(), milestone), 0, h * .405f, w, Align.center, false);
            EquipmentItem exclusive = ThreatMilestoneRewardCatalog.forTier(result.unlockedThreatTier());
            if (exclusive != null) {
                font.getData().setScale(.52f);
                font.setColor(Color.MAGENTA);
                font.draw(batch, f("victory.mythic", localizedName(exclusive).toUpperCase(java.util.Locale.ROOT)), 0, h * .365f, w, Align.center, false);
            }
        } else if (firstClear) {
            font.setColor(Color.LIME);
            font.draw(batch, f("victory.firstClear", bonusCredits, bonusGems), 0, h * .40f, w, Align.center, false);
        }
        drawMasteryNotice(w, h);
        if (result.drop() != null) {
            font.getData().setScale(.62f);
            font.setColor(Color.WHITE);
            font.draw(batch, f("result.drop", t(result.drop().rarityKey()), localizedName(result.drop()), result.drop().level), 0, h * .275f, w, Align.center, false);
        }
        font.setColor(Color.WHITE);
        font.draw(batch, t("victory.base"), w * .13f, h * .18f + 36f, w * .20f, Align.center, false);
        font.setColor(canShare ? new Color(.86f, .78f, 1f, 1f) : Color.DARK_GRAY);
        font.draw(batch, canShare ? t("victory.share") : t("victory.shareDisabled"), w * .40f, h * .18f + 36f, w * .20f, Align.center, false);
        font.setColor(Color.WHITE);
        font.draw(batch, t("victory.nextStage"), w * .67f, h * .18f + 36f, w * .20f, Align.center, false);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) game.showMenu();
        if (Gdx.input.isKeyJustPressed(Input.Keys.R) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) game.startRun();
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) share();
        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = h - Gdx.input.getY();
            if (y >= h * .16f && y <= h * .18f + 72f) {
                if (x >= w * .13f && x <= w * .33f) game.showMenu();
                else if (x >= w * .40f && x <= w * .60f) share();
                else if (x >= w * .67f && x <= w * .87f) game.startRun();
            }
        }
    }

    private void share() {
        if (!game.services.share.available()) return;
        game.services.share.shareText(RunShareText.format(result, game.i18n));
    }

    private void drawMasteryNotice(float w, float h) {
        MasteryRunNotice.Notice notice = MasteryRunNotice.current();
        if (notice == null || !notice.visible()) return;
        StringBuilder detail = new StringBuilder();
        if (notice.weaponRankedUp()) detail.append(f("victory.masteryWeapon", notice.weaponName().toUpperCase(), notice.weaponRank()));
        if (notice.biomeRankedUp()) {
            if (notice.weaponRankedUp()) detail.append("  •  ");
            detail.append(f("victory.masteryBiome", notice.biomeName(), notice.biomeRank()));
        }
        String text = f("victory.mastery", detail.toString(), notice.creditsReward(), notice.gemsReward());
        font.getData().setScale(.50f);
        font.setColor(new Color(.72f, .58f, 1f, 1f));
        font.draw(batch, text, 0, h * .325f, w, Align.center, false);
    }

    private static String formatTime(float seconds) {
        int total = Math.max(0, (int)seconds);
        return String.format("%02d:%02d", total / 60, total % 60);
    }

    private String localizedName(com.deadlinezero.game.meta.EquipmentItem item) {
        if (item == null) return "";
        String key = item.nameKey();
        if (key != null) return t(key);
        return f("equipment.generatedName", t(item.rarityKey()), t(item.slotKey()));
    }

    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
