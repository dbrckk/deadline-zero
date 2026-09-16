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
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.meta.MasteryRunNotice;
import com.deadlinezero.game.meta.RunResult;
import com.deadlinezero.game.meta.RunShareText;
import com.deadlinezero.game.meta.ThreatMilestoneRewardCatalog;
import com.deadlinezero.game.ui.MetaLayout;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.VisualTheme;

public final class VictoryScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final RunResult result;
    private final boolean firstClear;
    private final long bonusCredits;
    private final int bonusGems;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private UiLayout.Metrics metrics;
    private MetaLayout.Layout layout;
    private Rectangle hero;
    private Rectangle[] rewardCards;
    private Rectangle noticePanel;
    private Rectangle[] actions;
    private float visualTime;

    public VictoryScreen(DeadlineZeroGame game, RunResult result, boolean firstClear, long bonusCredits, int bonusGems) {
        this.game = game;
        this.result = result;
        this.firstClear = firstClear;
        this.bonusCredits = bonusCredits;
        this.bonusGems = bonusGems;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        layout = MetaLayout.compute(metrics);
        Rectangle c = layout.content();
        hero = new Rectangle(c.x, c.y + c.height * .60f, c.width, c.height * .40f);
        Rectangle rewards = new Rectangle(c.x, c.y + c.height * .34f, c.width, c.height * .20f);
        rewardCards = MetaLayout.columns(rewards, 3, 16f);
        noticePanel = new Rectangle(c.x, c.y, c.width, c.height * .28f);
        actions = MetaLayout.actions(layout.footer(), 3, 14f);
    }

    @Override public void render(float delta) {
        visualTime += Math.max(0f, delta);
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);
        boolean canShare = game.services.share.available();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, visualTime);
        UiRenderer.card(shapes, hero.x, hero.y, hero.width, hero.height, true, true);
        for (int i = 0; i < rewardCards.length; i++) {
            Rectangle r = rewardCards[i];
            UiRenderer.card(shapes, r.x, r.y, r.width, r.height, false, i == 0);
        }
        UiRenderer.panel(shapes, noticePanel.x, noticePanel.y, noticePanel.width, noticePanel.height);
        UiRenderer.button(shapes, actions[0].x, actions[0].y, actions[0].width, actions[0].height, UiRenderer.ButtonState.NORMAL);
        UiRenderer.button(shapes, actions[1].x, actions[1].y, actions[1].width, actions[1].height,
            canShare ? UiRenderer.ButtonState.NORMAL : UiRenderer.ButtonState.DISABLED);
        UiRenderer.button(shapes, actions[2].x, actions[2].y, actions[2].width, actions[2].height, UiRenderer.ButtonState.SELECTED);
        shapes.end();

        batch.begin();
        drawHero();
        drawRewards();
        drawNotice();
        drawActions(canShare);
        batch.end();

        handleInput(canShare);
    }

    private void drawHero() {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.DISPLAY));
        font.setColor(VisualTheme.positive());
        font.draw(batch, t("victory.title"), hero.x + 20f, hero.y + hero.height * .75f,
            hero.width - 40f, Align.center, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, f("result.summary", result.stage(), result.kills(), formatTime(result.secondsSurvived())),
            hero.x + 20f, hero.y + hero.height * .48f, hero.width - 40f, Align.center, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.GOLD);
        font.draw(batch, f("result.contract", result.contractTitle(), result.contractBonusPercent()),
            hero.x + 30f, hero.y + hero.height * .25f, hero.width * .46f, Align.center, false);
        font.setColor(result.threatTier() > 0 ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
        font.draw(batch, f("result.threat", result.threatTier(), result.threatBonusPercent()),
            hero.x + hero.width * .51f, hero.y + hero.height * .25f, hero.width * .45f, Align.center, false);
    }

    private void drawRewards() {
        drawMetric(rewardCards[0], "CREDITS", String.valueOf(result.rewards().credits()), VisualTheme.GOLD);
        drawMetric(rewardCards[1], "GEMS", String.valueOf(result.rewards().gems()), VisualTheme.accent());
        drawMetric(rewardCards[2], "ACCOUNT XP", String.valueOf(result.rewards().accountXp()), VisualTheme.VIOLET);
    }

    private void drawMetric(Rectangle r, String label, String value, Color accent) {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, label, r.x + 10f, r.y + r.height - 18f, r.width - 20f, Align.center, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
        font.setColor(accent);
        font.draw(batch, value, r.x + 10f, r.y + r.height * .43f, r.width - 20f, Align.center, false);
    }

    private void drawNotice() {
        float left = noticePanel.x + 24f;
        float top = noticePanel.y + noticePanel.height - 20f;
        float width = noticePanel.width - 48f;

        if (result.unlockedThreatTier() > 0) {
            font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
            font.setColor(VisualTheme.GOLD);
            String milestone = result.threatMilestoneGems() > 0 ? f("victory.milestone", result.threatMilestoneGems()) : "";
            font.draw(batch, f("victory.threatUnlocked", result.unlockedThreatTier(), milestone), left, top, width, Align.center, true);
            EquipmentItem exclusive = ThreatMilestoneRewardCatalog.forTier(result.unlockedThreatTier());
            if (exclusive != null) {
                font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
                font.setColor(Color.MAGENTA);
                font.draw(batch, f("victory.mythic", localizedName(exclusive).toUpperCase(java.util.Locale.ROOT)),
                    left, top - 36f, width, Align.center, false);
            }
        } else if (firstClear) {
            font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
            font.setColor(VisualTheme.positive());
            font.draw(batch, f("victory.firstClear", bonusCredits, bonusGems), left, top, width, Align.center, false);
        }

        drawMasteryNotice(left, noticePanel.y + noticePanel.height * .43f, width);
        if (result.drop() != null) {
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
            font.setColor(VisualTheme.TEXT_STRONG);
            font.draw(batch, f("result.drop", t(result.drop().rarityKey()), localizedName(result.drop()), result.drop().level),
                left, noticePanel.y + 22f, width, Align.center, false);
        }
    }

    private void drawActions(boolean canShare) {
        String[] labels = {t("victory.base"), canShare ? t("victory.share") : t("victory.shareDisabled"), t("victory.nextStage")};
        for (int i = 0; i < actions.length; i++) {
            Rectangle r = actions[i];
            font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
            font.setColor(i == 1 && !canShare ? VisualTheme.MUTED : i == 2 ? VisualTheme.TEXT_STRONG : VisualTheme.TEXT);
            font.draw(batch, labels[i], r.x + 8f, r.y + r.height * .60f, r.width - 16f, Align.center, false);
        }
    }

    private void handleInput(boolean canShare) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) { game.showMenu(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { game.startRun(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) { share(); return; }
        if (!Gdx.input.justTouched()) return;
        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (actions[0].contains(touch)) game.showMenu();
        else if (actions[1].contains(touch) && canShare) share();
        else if (actions[2].contains(touch)) game.startRun();
    }

    private void share() {
        if (!game.services.share.available()) return;
        game.services.share.shareText(RunShareText.format(result, game.i18n));
    }

    private void drawMasteryNotice(float x, float y, float width) {
        MasteryRunNotice.Notice notice = MasteryRunNotice.current();
        if (notice == null || !notice.visible()) return;
        StringBuilder detail = new StringBuilder();
        if (notice.weaponRankedUp()) detail.append(f("victory.masteryWeapon",
            localizedNoticeName(notice.weaponNameKey(), notice.weaponName()).toUpperCase(java.util.Locale.ROOT), notice.weaponRank()));
        if (notice.biomeRankedUp()) {
            if (notice.weaponRankedUp()) detail.append("  •  ");
            detail.append(f("victory.masteryBiome", localizedNoticeName(notice.biomeNameKey(), notice.biomeName()), notice.biomeRank()));
        }
        String text = f("victory.mastery", detail.toString(), notice.creditsReward(), notice.gemsReward());
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(new Color(.72f, .58f, 1f, 1f));
        font.draw(batch, text, x, y, width, Align.center, true);
    }

    private static String formatTime(float seconds) {
        int total = Math.max(0, (int) seconds);
        return String.format(java.util.Locale.ROOT, "%02d:%02d", total / 60, total % 60);
    }
    private String localizedNoticeName(String key, String fallback) { return key == null || key.isBlank() ? fallback : t(key); }
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
