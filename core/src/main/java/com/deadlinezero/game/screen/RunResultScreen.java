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
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.RunRecoveryAdvice;
import com.deadlinezero.game.meta.RunResult;
import com.deadlinezero.game.services.AdsService;
import com.deadlinezero.game.ui.MetaLayout;
import com.deadlinezero.game.ui.UiIconRenderer;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.VisualTheme;

public final class RunResultScreen extends ScreenAdapter {
    private static final Color COACHING_ACCENT = new Color(.78f, .64f, 1f, 1f);

    private final DeadlineZeroGame game;
    private final RunResult result;
    private final RunRecoveryAdvice.Advice advice;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private UiLayout.Metrics metrics;
    private MetaLayout.Layout layout;
    private Rectangle hero;
    private Rectangle[] metricsCards;
    private Rectangle coaching;
    private Rectangle[] actions;
    private boolean bonusClaimed;
    private float visualTime;

    public RunResultScreen(DeadlineZeroGame game, RunResult result) {
        this.game = game;
        this.result = result;
        this.advice = RunRecoveryAdvice.forResult(result);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        layout = MetaLayout.compute(metrics);
        Rectangle c = layout.content();
        hero = new Rectangle(c.x, c.y + c.height * .61f, c.width, c.height * .39f);
        Rectangle metricArea = new Rectangle(c.x, c.y + c.height * .32f, c.width, c.height * .23f);
        metricsCards = MetaLayout.columns(metricArea, 3, 16f);
        coaching = new Rectangle(c.x, c.y, c.width, c.height * .26f);
        actions = MetaLayout.actions(layout.footer(), 3, 14f);
    }

    @Override public void render(float delta) {
        visualTime += Math.max(0f, delta);
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, visualTime);
        UiRenderer.premiumPanel(shapes, hero.x, hero.y, hero.width, hero.height, VisualTheme.accent(), true);
        shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .22f);
        shapes.rect(hero.x + 6f, hero.y + hero.height - 6f, hero.width - 12f, 4f);
        drawResultHeroMark(shapes);

        Color[] rewardAccents = {VisualTheme.GOLD, VisualTheme.accent(), VisualTheme.VIOLET};
        for (int i = 0; i < metricsCards.length; i++) {
            Rectangle r = metricsCards[i];
            UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, rewardAccents[i], false, i == 0, false);
            Color accent = rewardAccents[i];
            shapes.setColor(accent.r, accent.g, accent.b, .90f);
            shapes.rect(r.x, r.y + r.height - 4f, r.width, 4f);
            drawRewardIcon(shapes, r, i, accent);
        }
        UiRenderer.premiumPanel(shapes, coaching.x, coaching.y, coaching.width, coaching.height, COACHING_ACCENT, false);
        shapes.setColor(COACHING_ACCENT.r, COACHING_ACCENT.g, COACHING_ACCENT.b, .72f);
        shapes.rect(coaching.x, coaching.y, 4f, coaching.height);
        if (result.drop() != null) {
            Color dropAccent = VisualTheme.equipmentRarity(result.drop().rarity);
            float splitX = coaching.x + coaching.width * .61f;
            shapes.setColor(dropAccent.r, dropAccent.g, dropAccent.b, .18f);
            shapes.rect(splitX, coaching.y + 8f, coaching.width * .37f - 8f, coaching.height - 16f);
            shapes.setColor(dropAccent.r, dropAccent.g, dropAccent.b, .90f);
            shapes.rect(splitX, coaching.y + coaching.height - 4f, coaching.width * .37f - 8f, 4f);
            float gearSize = Math.min(38f, coaching.height * .25f);
            UiIconRenderer.draw(shapes, UiIconRenderer.Icon.GEAR,
                splitX + 16f, coaching.y + coaching.height - gearSize - 16f,
                gearSize, dropAccent, .88f);
        }
        UiRenderer.premiumButton(shapes, actions[0].x, actions[0].y, actions[0].width, actions[0].height, VisualTheme.GOLD, UiRenderer.ButtonState.SELECTED);
        UiRenderer.premiumButton(shapes, actions[1].x, actions[1].y, actions[1].width, actions[1].height, VisualTheme.CYAN_SOFT, UiRenderer.ButtonState.NORMAL);
        UiRenderer.premiumButton(shapes, actions[2].x, actions[2].y, actions[2].width, actions[2].height, VisualTheme.VIOLET,
            bonusClaimed ? UiRenderer.ButtonState.DISABLED : UiRenderer.ButtonState.NORMAL);
        shapes.end();

        batch.begin();
        drawHero();
        drawMetrics();
        drawCoaching();
        drawActions();
        batch.end();

        handleInput();
    }

    private void drawResultHeroMark(ShapeRenderer shapes) {
        float size = Math.min(52f, hero.height * .30f);
        float x = hero.x + 26f;
        float y = hero.y + hero.height * .5f - size * .5f;
        UiRenderer.iconBadge(shapes, x - 7f, y - 7f, size + 14f, VisualTheme.accent(), true);
        UiIconRenderer.draw(shapes, UiIconRenderer.Icon.STAGE, x, y, size, VisualTheme.accent(), .94f);
    }

    private void drawRewardIcon(ShapeRenderer shapes, Rectangle r, int index, Color accent) {
        UiIconRenderer.Icon icon = index == 0 ? UiIconRenderer.Icon.CREDITS
            : index == 1 ? UiIconRenderer.Icon.GEMS : UiIconRenderer.Icon.LEVEL;
        float size = Math.min(28f, r.height * .24f);
        UiIconRenderer.draw(shapes, icon, r.x + 16f, r.y + r.height - size - 14f, size, accent, .88f);
    }

    private void drawHero() {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.DISPLAY));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("result.complete"), hero.x + 24f, hero.y + hero.height * .73f,
            hero.width - 48f, Align.center, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("result.summary", result.stage(), result.kills(), formatTime(result.secondsSurvived())),
            hero.x + 24f, hero.y + hero.height * .45f, hero.width - 48f, Align.center, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        font.setColor(VisualTheme.GOLD);
        font.draw(batch, f("result.contract", result.contractTitle(), result.contractBonusPercent()),
            hero.x + 90f, hero.y + hero.height * .20f, hero.width * .40f, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(result.threatTier() > 0 ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
        font.draw(batch, f("result.threat", result.threatTier(), result.threatBonusPercent()),
            hero.x + hero.width * .50f, hero.y + hero.height * .25f, hero.width * .46f, Align.center, false);
    }

    private void drawMetrics() {
        drawMetric(metricsCards[0], t("result.credits"), String.valueOf(result.rewards().credits()), VisualTheme.GOLD);
        drawMetric(metricsCards[1], "GEMS", String.valueOf(result.rewards().gems()), VisualTheme.accent());
        drawMetric(metricsCards[2], "ACCOUNT XP", String.valueOf(result.rewards().accountXp()), VisualTheme.VIOLET);
    }

    private void drawMetric(Rectangle r, String label, String value, Color accent) {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, label, r.x + 12f, r.y + r.height - 18f, r.width - 24f, Align.center, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
        font.setColor(accent);
        font.draw(batch, value, r.x + 12f, r.y + r.height * .43f, r.width - 24f, Align.center, false);
    }

    private void drawCoaching() {
        float y = coaching.y + coaching.height - 20f;
        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
        font.setColor(COACHING_ACCENT);
        font.draw(batch, t(advice.headlineKey()), coaching.x + 24f, y, coaching.width * .58f, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, t(advice.detailKey()), coaching.x + 24f, y - 30f, coaching.width * .58f, Align.left, true);

        if (result.drop() != null) {
            Color rarity = VisualTheme.equipmentRarity(result.drop().rarity);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
            font.setColor(rarity);
            font.draw(batch, t(result.drop().rarityKey()),
                coaching.x + coaching.width * .63f, y + 2f, coaching.width * .33f, Align.right, false);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
            font.setColor(VisualTheme.TEXT_STRONG);
            font.draw(batch, f("result.drop", t(result.drop().rarityKey()), localizedName(result.drop()), result.drop().level),
                coaching.x + coaching.width * .63f, y - 18f, coaching.width * .33f, Align.right, true);
        }
    }

    private void drawActions() {
        String[] labels = {"BASE", "RETRY", bonusClaimed ? t("result.doubleClaimed") : t("result.doubleOffer")};
        for (int i = 0; i < actions.length; i++) {
            Rectangle r = actions[i];
            font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
            font.setColor(i == 2 && bonusClaimed ? VisualTheme.MUTED : i == 0 ? VisualTheme.TEXT_STRONG : VisualTheme.TEXT);
            font.draw(batch, labels[i], r.x + 8f, r.y + r.height * .60f, r.width - 16f, Align.center, true);
        }
    }

    private void handleInput() {
        if (!bonusClaimed && Gdx.input.isKeyJustPressed(Input.Keys.D)) { claimDoubleCredits(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { game.startRun(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { game.showMenu(); return; }
        if (!Gdx.input.justTouched()) return;
        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (actions[0].contains(touch)) game.showMenu();
        else if (actions[1].contains(touch)) game.startRun();
        else if (actions[2].contains(touch) && !bonusClaimed) claimDoubleCredits();
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
        int total = Math.max(0, (int) seconds);
        return String.format(java.util.Locale.ROOT, "%02d:%02d", total / 60, total % 60);
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
