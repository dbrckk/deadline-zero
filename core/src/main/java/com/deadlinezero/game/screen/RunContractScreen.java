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
import com.deadlinezero.game.ai.BossAffixRules;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.meta.EndgameMutatorRules;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.ThreatTierRules;
import com.deadlinezero.game.ui.MetaLayout;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;
import com.deadlinezero.game.visual.VisualTheme;

/** Responsive three-card pre-run risk/reward selection. */
public final class RunContractScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private final RunModifierContext.Modifier[] offers = RunModifierContext.offers();
    private UiLayout.Metrics metrics;
    private MetaLayout.Layout layout;
    private Rectangle[] cards;
    private float time;

    public RunContractScreen(DeadlineZeroGame game) {
        this.game = game;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        layout = MetaLayout.compute(metrics);
        cards = MetaLayout.columns(layout.content(), offers.length, 20f);
    }

    @Override public void render(float delta) {
        time += Math.min(.05f, Math.max(0f, delta));
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, time);
        UiRenderer.topRail(shapes, metrics);
        for (int i = 0; i < offers.length; i++) {
            Rectangle r = cards[i];
            RunModifierContext.Modifier offer = offers[i];
            UiRenderer.card(shapes, r.x, r.y, r.width, r.height, offer.legendary(), offer.legendary());
            Color accent = accent(offer);
            float pulse = .80f + .20f * (float) Math.sin(time * (offer.legendary() ? 4.2f : 2.4f) + i * .7f);
            shapes.setColor(accent.r, accent.g, accent.b, .12f + pulse * .04f);
            shapes.rect(r.x + 8f, r.y + r.height * .47f, r.width - 16f, r.height * .25f);
            Rectangle cta = cta(r);
            UiRenderer.button(shapes, cta.x, cta.y, cta.width, cta.height,
                offer.legendary() ? UiRenderer.ButtonState.SELECTED : UiRenderer.ButtonState.NORMAL);
        }
        shapes.end();

        batch.begin();
        drawHeader();
        for (int i = 0; i < offers.length; i++) drawCard(i, offers[i], cards[i]);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, t("contract.footer"), layout.footer().x + 16f,
            layout.footer().y + layout.footer().height * .56f, layout.footer().width - 32f, Align.center, false);
        batch.end();

        handleInput();
    }

    private void drawHeader() {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, "‹  BASE", layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
            layout.back().width - 16f, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("contract.title"), metrics.safeLeft() + 138f, metrics.headerBottom() + 59f,
            metrics.contentWidth() - 276f, Align.center, false);

        BossAffixRules.Affix bossAffix = BossAffixRules.forRun(RunStageContext.stage(), RunStageContext.threatTier());
        String mutator = EndgameMutatorRules.active() ? f("contract.mutator", EndgameMutatorRules.label()) : "";
        String bossAffixText = bossAffix == BossAffixRules.Affix.NONE ? "" : f("contract.bossAffix", bossAffix.title);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(RunStageContext.threatTier() > 0 ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
        font.draw(batch, f("contract.header", RunStageContext.stage(), RunStageContext.threatTier(),
            ThreatTierRules.rewardBonusPercent(RunStageContext.threatTier()), mutator, bossAffixText),
            metrics.safeLeft() + 138f, metrics.headerBottom() + 28f, metrics.contentWidth() - 276f, Align.center, false);
    }

    private void drawCard(int index, RunModifierContext.Modifier m, Rectangle r) {
        Color accent = accent(m);
        float pad = 20f;
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(m.legendary() ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
        font.draw(batch, m.legendary() ? t("contract.legendary") : t("contract.standard"),
            r.x + pad, r.y + r.height - 22f, r.width - pad * 2f, Align.center, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
        font.setColor(accent);
        font.draw(batch, f("contract.cardTitle", index + 1, t(m.titleKey())),
            r.x + pad, r.y + r.height - 58f, r.width - pad * 2f, Align.center, true);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t(m.descriptionKey()), r.x + 28f, r.y + r.height - 112f,
            r.width - 56f, Align.center, true);

        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("contract.stats",
            oneDecimal(m.enemyHp * EndgameMutatorRules.enemyHpMultiplier()),
            oneDecimal(m.enemySpeed * EndgameMutatorRules.enemySpeedMultiplier()),
            oneDecimal(m.enemyDamage * EndgameMutatorRules.enemyDamageMultiplier()),
            oneDecimal(m.spawnInterval * EndgameMutatorRules.spawnIntervalMultiplier())),
            r.x + 28f, r.y + r.height * .54f, r.width - 56f, Align.center, true);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        font.setColor(accent);
        font.draw(batch, f("contract.threat", m.threatPercent()), r.x + pad, r.y + r.height * .31f,
            r.width - pad * 2f, Align.center, false);

        Rectangle cta = cta(r);
        int totalRewardBonus = Math.round((m.reward * EndgameMutatorRules.rewardMultiplier() - 1f) * 100f);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, f("contract.rewards", totalRewardBonus), cta.x + 8f, cta.y + cta.height * .61f,
            cta.width - 16f, Align.center, false);

        String hazard = hazardText();
        if (!hazard.isEmpty()) {
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .90f);
            font.setColor(RunStageContext.threatTier() >= 8 ? VisualTheme.danger() : VisualTheme.GOLD);
            font.draw(batch, hazard, r.x + 16f, r.y + 18f, r.width - 32f, Align.center, true);
        }
    }

    private String hazardText() {
        int stage = RunStageContext.stage();
        int tier = RunStageContext.threatTier();
        String biome = EnvironmentBiomeRules.isNullSector(stage) ? t("contract.nullSector")
            : EnvironmentBiomeRules.isFoundry(stage) ? t("contract.foundry") : "";
        String endgame = tier >= 8 ? t("contract.endgameHeavy") : tier >= 5 ? t("contract.endgame") : "";
        if (biome.isEmpty()) return endgame;
        if (endgame.isEmpty()) return biome;
        return biome + " • " + endgame;
    }

    private Rectangle cta(Rectangle card) {
        return new Rectangle(card.x + 24f, card.y + 46f, card.width - 48f, 66f);
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
            game.showMenu();
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) { choose(0); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) { choose(1); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) { choose(2); return; }
        if (!Gdx.input.justTouched()) return;
        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (layout.back().contains(touch)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); return; }
        for (int i = 0; i < cards.length; i++) if (cards[i].contains(touch)) { choose(i); return; }
    }

    private void choose(int index) {
        if (index < 0 || index >= offers.length) return;
        AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
        game.startRunWithContract(offers[index]);
    }

    private String oneDecimal(float value) { return String.format(java.util.Locale.ROOT, "%.2f", value); }
    private Color accent(RunModifierContext.Modifier modifier) {
        if (modifier.legendary()) return VisualTheme.GOLD;
        return switch (modifier) {
            case GLASS_HORDE -> VisualTheme.CYAN;
            case BLOOD_MOON -> VisualTheme.danger();
            case ELITE_HUNT -> VisualTheme.VIOLET;
            case REDLINE -> VisualTheme.GOLD;
            default -> VisualTheme.CYAN_SOFT;
        };
    }
    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
