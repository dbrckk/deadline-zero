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
import com.deadlinezero.game.ai.BossAffixRules;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.ThreatTierRules;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;
import com.deadlinezero.game.visual.VisualTheme;

/** Three-card pre-run risk/reward selection. No persistent state is mutated until combat settles. */
public final class RunContractScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final RunModifierContext.Modifier[] offers = RunModifierContext.offers();
    private float time;

    public RunContractScreen(DeadlineZeroGame game) { this.game = game; }

    @Override public void render(float delta) {
        time += Math.min(.05f, Math.max(0f, delta));
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float gap = Math.max(16f, w * .022f);
        float margin = Math.max(24f, w * .055f);
        float cardW = (w - margin * 2f - gap * 2f) / 3f;
        float cardH = Math.min(h * .50f, 370f);
        float cardY = h * .23f;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(VisualTheme.PANEL);
        shapes.rect(18f, h - 94f, w - 36f, 64f);
        for (int i = 0; i < offers.length; i++) {
            float x = margin + i * (cardW + gap);
            float pulse = .50f + .50f * (float)Math.sin(time * (offers[i].legendary() ? 4.2f : 2.5f) + i * .8f);
            shapes.setColor(VisualTheme.PANEL_ALT);
            shapes.rect(x, cardY, cardW, cardH);
            Color accent = accent(offers[i]);
            float glow = offers[i].legendary() ? .28f : .16f;
            shapes.setColor(accent.r, accent.g, accent.b, glow + pulse * .06f);
            shapes.rect(x, cardY + cardH - (offers[i].legendary() ? 12f : 8f), cardW, offers[i].legendary() ? 12f : 8f);
            shapes.rect(x, cardY, offers[i].legendary() ? 7f : 4f, cardH);
            shapes.setColor(accent.r, accent.g, accent.b, (offers[i].legendary() ? .13f : .07f) + pulse * .03f);
            shapes.circle(x + cardW * .5f, cardY + cardH * .58f, Math.min(cardW, cardH) * (offers[i].legendary() ? .43f : .38f), 48);
            shapes.setColor(accent.r, accent.g, accent.b, .90f);
            shapes.rect(x + cardW * .12f, cardY + 24f, cardW * .76f, 46f);
        }
        shapes.end();

        batch.begin();
        font.getData().setScale(.92f);
        font.setColor(VisualTheme.TEXT);
        font.draw(batch, "SELECT RUN CONTRACT", 0f, h - 50f, w, Align.center, false);
        font.getData().setScale(.43f);
        font.setColor(RunStageContext.threatTier() > 0 ? VisualTheme.GOLD : VisualTheme.MUTED);
        BossAffixRules.Affix bossAffix = BossAffixRules.forRun(RunStageContext.stage(), RunStageContext.threatTier());
        font.draw(batch, "STAGE " + RunStageContext.stage()
            + "  •  THREAT " + RunStageContext.threatTier()
            + "  •  +" + ThreatTierRules.rewardBonusPercent(RunStageContext.threatTier())
            + "% ASCENSION"
            + (bossAffix == BossAffixRules.Affix.NONE ? "" : "  •  BOSS AFFIX " + bossAffix.title),
            0f, h - 76f, w, Align.center, false);

        int tier = RunStageContext.threatTier();
        float hazardY = h - 104f;
        if (EnvironmentBiomeRules.isFoundry(RunStageContext.stage())) {
            font.getData().setScale(.34f);
            font.setColor(VisualTheme.GOLD);
            font.draw(batch, "CINDER FOUNDRY  •  LAVA VENTS  •  STEAM JETS  •  HEAT LINES",
                0f, hazardY, w, Align.center, false);
            hazardY -= 20f;
        }
        if (tier >= 5) {
            font.getData().setScale(.34f);
            font.setColor(tier >= 8 ? VisualTheme.RED : VisualTheme.GOLD);
            String endgame = tier >= 8
                ? "ENDGAME HAZARDS  •  TELEGRAPHED ORBITAL STRIKES  •  VOLATILE HEAVIES"
                : "ENDGAME HAZARDS  •  TELEGRAPHED ORBITAL STRIKES";
            font.draw(batch, endgame, 0f, hazardY, w, Align.center, false);
        }

        for (int i = 0; i < offers.length; i++) {
            RunModifierContext.Modifier m = offers[i];
            float x = margin + i * (cardW + gap);
            float center = x + cardW * .5f;
            Color accent = accent(m);

            font.getData().setScale(.33f);
            font.setColor(m.legendary() ? VisualTheme.GOLD : VisualTheme.MUTED);
            font.draw(batch, m.legendary() ? "LEGENDARY CONTRACT" : "STANDARD CONTRACT",
                x + 8f, cardY + cardH - 24f, cardW - 16f, Align.center, false);

            font.getData().setScale(.64f);
            font.setColor(accent);
            font.draw(batch, "[" + (i + 1) + "]  " + m.title, x + 8f, cardY + cardH - 52f, cardW - 16f, Align.center, false);

            font.getData().setScale(.40f);
            font.setColor(VisualTheme.TEXT);
            font.draw(batch, m.description, x + cardW * .10f, cardY + cardH - 95f, cardW * .80f, Align.center, true);

            font.getData().setScale(.37f);
            font.setColor(VisualTheme.MUTED);
            font.draw(batch,
                "HP  x" + oneDecimal(m.enemyHp) + "\n" +
                "SPEED  x" + oneDecimal(m.enemySpeed) + "\n" +
                "DAMAGE  x" + oneDecimal(m.enemyDamage) + "\n" +
                "SPAWN  x" + oneDecimal(m.spawnInterval),
                x + cardW * .13f, cardY + cardH * .55f, cardW * .74f, Align.center, true);

            font.getData().setScale(.43f);
            font.setColor(accent);
            font.draw(batch, "THREAT  " + m.threatPercent() + "%", x, cardY + 105f, cardW, Align.center, false);

            font.getData().setScale(.62f);
            font.setColor(Color.WHITE);
            font.draw(batch, "+" + m.rewardBonusPercent() + "% REWARDS", center - cardW * .38f,
                cardY + 54f, cardW * .76f, Align.center, false);
        }

        font.getData().setScale(.36f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, "TAP A CARD / 1-3 TO DEPLOY   •   ESC TO CANCEL", 0f, 34f, w, Align.center, false);
        batch.end();

        handleInput(cardY, cardW, cardH, margin, gap);
    }

    private void handleInput(float cardY, float cardW, float cardH, float margin, float gap) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
            game.showMenu();
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) { choose(0); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) { choose(1); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) { choose(2); return; }
        if (!Gdx.input.justTouched()) return;
        float x = Gdx.input.getX();
        float y = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (y < cardY || y > cardY + cardH) return;
        for (int i = 0; i < offers.length; i++) {
            float left = margin + i * (cardW + gap);
            if (x >= left && x <= left + cardW) { choose(i); return; }
        }
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
            case BLOOD_MOON -> VisualTheme.RED;
            case ELITE_HUNT -> VisualTheme.VIOLET;
            case REDLINE -> VisualTheme.GOLD;
            default -> VisualTheme.CYAN_SOFT;
        };
    }

    @Override public void dispose() {
        batch.dispose();
        font.dispose();
        shapes.dispose();
    }
}
