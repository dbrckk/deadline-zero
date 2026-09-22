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
import com.deadlinezero.game.meta.ReviewPromptPolicy;
import com.deadlinezero.game.meta.RunShareText;
import com.deadlinezero.game.meta.ThreatMilestoneRewardCatalog;
import com.deadlinezero.game.ui.MetaLayout;
import com.deadlinezero.game.ui.UiIconRenderer;
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
        boolean reviewAttempted = game.profile != null && game.profile.reviewPromptAttempted;
        if (ReviewPromptPolicy.eligible(firstClear, result.stage(), reviewAttempted)) {
            game.profile.reviewPromptAttempted = true;
            game.saveProfile();
            game.services.review.requestReview();
        }
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
        UiRenderer.premiumPanel(shapes, hero.x, hero.y, hero.width, hero.height, VisualTheme.GOLD, true);
        drawCelebrationBackdrop(shapes);
        for (int i = 0; i < rewardCards.length; i++) {
            Rectangle r = rewardCards[i];
            Color rewardAccent = i == 0 ? VisualTheme.GOLD : i == 1 ? VisualTheme.accent() : VisualTheme.VIOLET;
            UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, rewardAccent, false, i == 0, false);
            drawRewardIcon(shapes, r, i, rewardAccent);
        }
        Color noticeAccent = result.unlockedThreatTier() > 0 ? VisualTheme.GOLD : firstClear ? VisualTheme.positive() : VisualTheme.VIOLET;
        UiRenderer.premiumPanel(shapes, noticePanel.x, noticePanel.y, noticePanel.width, noticePanel.height, noticeAccent, false);
        drawNoticeShowcase(shapes);
        drawVictoryChrome(shapes);
        UiRenderer.premiumButton(shapes, actions[0].x, actions[0].y, actions[0].width, actions[0].height, VisualTheme.CYAN_SOFT, UiRenderer.ButtonState.NORMAL);
        UiRenderer.premiumButton(shapes, actions[1].x, actions[1].y, actions[1].width, actions[1].height, VisualTheme.VIOLET,
            canShare ? UiRenderer.ButtonState.NORMAL : UiRenderer.ButtonState.DISABLED);
        UiRenderer.premiumButton(shapes, actions[2].x, actions[2].y, actions[2].width, actions[2].height, VisualTheme.GOLD, UiRenderer.ButtonState.SELECTED);
        shapes.end();

        batch.begin();
        drawHero();
        drawRewards();
        drawNotice();
        drawActions(canShare);
        batch.end();

        handleInput(canShare);
    }

    private void drawCelebrationBackdrop(ShapeRenderer shapes) {
        float cx = hero.x + hero.width * .5f;
        float cy = hero.y + hero.height * .50f;
        float pulse = .5f + .5f * (float)Math.sin(visualTime * 1.8f);
        float outer = Math.min(hero.width * .18f, hero.height * .72f);
        for (int i = 0; i < 10; i++) {
            float a0 = (float)Math.toRadians(i * 36f - 5f);
            float a1 = (float)Math.toRadians(i * 36f + 5f);
            float inner = outer * .42f;
            shapes.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, .035f + pulse * .018f);
            shapes.triangle(
                cx + (float)Math.cos(a0) * inner, cy + (float)Math.sin(a0) * inner,
                cx + (float)Math.cos(a1) * inner, cy + (float)Math.sin(a1) * inner,
                cx + (float)Math.cos((a0 + a1) * .5f) * outer, cy + (float)Math.sin((a0 + a1) * .5f) * outer);
        }
        float trophySize = Math.min(54f, hero.height * .30f);
        float tx = cx - trophySize * .5f;
        float ty = hero.y + hero.height * .55f;
        UiRenderer.iconBadge(shapes, tx - 8f, ty - 8f, trophySize + 16f, VisualTheme.GOLD, true);
        UiIconRenderer.draw(shapes, UiIconRenderer.Icon.TROPHY, tx, ty, trophySize, VisualTheme.GOLD, .96f);
    }

    private void drawRewardIcon(ShapeRenderer shapes, Rectangle r, int index, Color accent) {
        UiIconRenderer.Icon icon = index == 0 ? UiIconRenderer.Icon.CREDITS
            : index == 1 ? UiIconRenderer.Icon.GEMS : UiIconRenderer.Icon.LEVEL;
        float size = Math.min(28f, r.height * .26f);
        UiIconRenderer.draw(shapes, icon, r.x + 16f, r.y + r.height - size - 14f, size, accent, .92f);
    }

    private void drawNoticeShowcase(ShapeRenderer shapes) {
        if (result.drop() == null) return;
        Color rarity = VisualTheme.equipmentRarity(result.drop().rarity);
        float cardW = noticePanel.width * .31f;
        float cardH = noticePanel.height - 24f;
        float x = noticePanel.x + noticePanel.width - cardW - 14f;
        float y = noticePanel.y + 12f;
        UiRenderer.premiumCard(shapes, x, y, cardW, cardH, rarity, true, false, false);
        float badge = Math.min(58f, cardH * .54f);
        UiRenderer.iconBadge(shapes, x + 14f, y + cardH * .5f - badge * .5f, badge, rarity, true);
        UiIconRenderer.draw(shapes, UiIconRenderer.Icon.GEAR,
            x + 22f, y + cardH * .5f - badge * .5f + 8f,
            badge - 16f, rarity, .94f);
        shapes.setColor(rarity.r, rarity.g, rarity.b, .88f);
        shapes.rect(x + 7f, y + cardH - 4f, Math.max(0f, cardW - 14f), 3f);
    }

    private void drawVictoryChrome(ShapeRenderer shapes) {
        float pulse = .72f + .18f * (float)Math.sin(visualTime * 2.2f);
        shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, .14f);
        shapes.rect(hero.x + 8f, hero.y + 8f, Math.max(0f, hero.width - 16f), Math.max(0f, hero.height - 16f));
        shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, pulse);
        shapes.rect(hero.x + 10f, hero.y + hero.height - 6f, Math.max(0f, hero.width - 20f), 4f);

        Color[] rewardAccents = {VisualTheme.GOLD, VisualTheme.accent(), VisualTheme.VIOLET};
        for (int i = 0; i < rewardCards.length; i++) {
            Rectangle r = rewardCards[i];
            Color accent = rewardAccents[i];
            shapes.setColor(accent.r, accent.g, accent.b, .68f);
            shapes.rect(r.x + 6f, r.y + r.height - 4f, Math.max(0f, r.width - 12f), 3f);
            shapes.setColor(accent.r, accent.g, accent.b, .055f);
            shapes.rect(r.x + 7f, r.y + 7f, Math.max(0f, r.width - 14f), Math.max(0f, r.height - 14f));
        }

        Color noticeAccent = result.unlockedThreatTier() > 0 ? VisualTheme.GOLD
            : firstClear ? VisualTheme.positive() : VisualTheme.VIOLET;
        shapes.setColor(noticeAccent.r, noticeAccent.g, noticeAccent.b, .56f);
        shapes.rect(noticePanel.x + 7f, noticePanel.y + noticePanel.height - 4f,
            Math.max(0f, noticePanel.width - 14f), 3f);

        Rectangle next = actions[2];
        shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .10f);
        shapes.rect(next.x + 6f, next.y + 6f, Math.max(0f, next.width - 12f), Math.max(0f, next.height - 12f));
        shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .88f);
        shapes.rect(next.x + 10f, next.y + next.height - 4f, Math.max(0f, next.width - 20f), 3f);
    }

    private void drawHero() {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.DISPLAY));
        font.setColor(VisualTheme.positive());
        font.draw(batch, t("victory.title"), hero.x + 20f, hero.y + hero.height * .84f,
            hero.width - 40f, Align.center, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, f("result.summary", result.stage(), result.kills(), formatTime(result.secondsSurvived())),
            hero.x + 20f, hero.y + hero.height * .42f, hero.width - 40f, Align.center, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.GOLD);
        font.draw(batch, f("result.contract", result.contractTitle(), result.contractBonusPercent()),
            hero.x + 30f, hero.y + hero.height * .18f, hero.width * .46f, Align.center, false);
        font.setColor(result.threatTier() > 0 ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
        font.draw(batch, f("result.threat", result.threatTier(), result.threatBonusPercent()),
            hero.x + hero.width * .51f, hero.y + hero.height * .18f, hero.width * .45f, Align.center, false);
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

        float masteryWidth = result.drop() == null ? width : noticePanel.width * .60f;
        drawMasteryNotice(left, noticePanel.y + noticePanel.height * .43f, masteryWidth);
        if (result.drop() != null) {
            float cardW = noticePanel.width * .31f;
            float x = noticePanel.x + noticePanel.width - cardW - 14f;
            Color rarity = VisualTheme.equipmentRarity(result.drop().rarity);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
            font.setColor(rarity);
            font.draw(batch, t(result.drop().rarityKey()),
                x + 78f, noticePanel.y + noticePanel.height * .64f, cardW - 92f, Align.left, false);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
            font.setColor(VisualTheme.TEXT_STRONG);
            font.draw(batch, f("result.drop", t(result.drop().rarityKey()), dropDisplayName(result.drop()), result.drop().level),
                x + 78f, noticePanel.y + noticePanel.height * .42f, cardW - 92f, Align.left, true);
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

    private String dropDisplayName(com.deadlinezero.game.meta.EquipmentItem item) {
        if (item == null) return "";
        String key = item.nameKey();
        return key != null ? t(key) : t(item.slotKey());
    }
    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
