package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.meta.SurvivorCatalog;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.GameArt;
import com.deadlinezero.game.visual.VisualTheme;

/** Responsive survivor roster with authored-art focus and explicit mobile actions. */
public final class SurvivorScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final BitmapFont font = new BitmapFont();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private UiLayout.Metrics metrics;
    private SurvivorLayoutModel.Layout layout;
    private int index;
    private String status = "";
    private float artTime;

    public SurvivorScreen(DeadlineZeroGame game) {
        this.game = game;
        SurvivorCatalog.Survivor[] values = SurvivorCatalog.Survivor.values();
        for (int i = 0; i < values.length; i++) if (values[i] == game.profile.selectedSurvivor) index = i;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        layout = SurvivorLayoutModel.layout(metrics);
    }

    @Override public void render(float delta) {
        artTime += Math.max(0f, delta);
        handleInput();
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);

        SurvivorCatalog.Survivor survivor = SurvivorCatalog.Survivor.values()[index];
        boolean unlocked = game.profile.survivors.unlocked(survivor);
        int level = game.profile.survivors.level(survivor);
        long xp = game.profile.survivors.xp(survivor);
        long next = game.profile.survivors.xpForNext(survivor);
        float progress = next <= 0 ? 1f : Math.min(1f, xp / (float) next);

        drawShapes(unlocked, progress);
        drawContent(survivor, unlocked, level, xp, next);
    }

    private void drawShapes(boolean unlocked, float progress) {
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, artTime);
        UiRenderer.topRail(shapes, metrics);
        UiRenderer.premiumCard(shapes, layout.card().x, layout.card().y, layout.card().width, layout.card().height,
            unlocked ? VisualTheme.accent() : VisualTheme.MUTED, true, false, !unlocked);
        UiRenderer.premiumPanel(shapes, layout.portrait().x, layout.portrait().y, layout.portrait().width, layout.portrait().height,
            unlocked ? VisualTheme.accent() : VisualTheme.MUTED, true);
        UiRenderer.premiumPanel(shapes, layout.stats().x, layout.stats().y, layout.stats().width, layout.stats().height,
            unlocked ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED, false);
        drawSurvivorChrome(shapes, unlocked);
        UiRenderer.progress(shapes, layout.xpBar().x, layout.xpBar().y, layout.xpBar().width, layout.xpBar().height,
            progress, VisualTheme.VIOLET);
        UiRenderer.premiumButton(shapes, layout.previous().x, layout.previous().y, layout.previous().width, layout.previous().height,
            VisualTheme.CYAN_SOFT, UiRenderer.ButtonState.NORMAL);
        UiRenderer.premiumButton(shapes, layout.next().x, layout.next().y, layout.next().width, layout.next().height,
            VisualTheme.CYAN_SOFT, UiRenderer.ButtonState.NORMAL);
        drawChevron(layout.previous(), false);
        drawChevron(layout.next(), true);

        UiRenderer.ButtonState ctaState;
        if (!unlocked) ctaState = UiRenderer.ButtonState.DISABLED;
        else if (game.profile.selectedSurvivor == SurvivorCatalog.Survivor.values()[index]) ctaState = UiRenderer.ButtonState.SELECTED;
        else ctaState = UiRenderer.ButtonState.NORMAL;
        UiRenderer.premiumButton(shapes, layout.cta().x, layout.cta().y, layout.cta().width, layout.cta().height,
            game.profile.selectedSurvivor == SurvivorCatalog.Survivor.values()[index] ? VisualTheme.positive() : VisualTheme.GOLD, ctaState);

        if (!game.art.authoredAvailable()) {
            Rectangle p = layout.portrait();
            shapes.setColor(unlocked ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
            shapes.circle(p.x + p.width * .5f, p.y + p.height * .52f, Math.min(p.width, p.height) * .17f, 36);
        }
        shapes.end();
    }

    private void drawSurvivorChrome(ShapeRenderer shapes, boolean unlocked) {
        SurvivorCatalog.Survivor survivor = SurvivorCatalog.Survivor.values()[index];
        boolean selected = game.profile.selectedSurvivor == survivor;
        Color accent = unlocked ? VisualTheme.accent() : VisualTheme.MUTED;

        Rectangle card = layout.card();
        shapes.setColor(accent.r, accent.g, accent.b, unlocked ? .56f : .18f);
        shapes.rect(card.x + 6f, card.y + card.height - 5f, Math.max(0f, card.width - 12f), 3f);

        Rectangle portrait = layout.portrait();
        shapes.setColor(accent.r, accent.g, accent.b, unlocked ? .085f : .035f);
        shapes.rect(portrait.x + 8f, portrait.y + 8f, Math.max(0f, portrait.width - 16f), Math.max(0f, portrait.height - 16f));
        shapes.setColor(accent.r, accent.g, accent.b, unlocked ? .72f : .24f);
        shapes.rect(portrait.x + 8f, portrait.y + portrait.height - 5f, Math.max(0f, portrait.width - 16f), 3f);

        Rectangle stats = layout.stats();
        shapes.setColor(selected ? VisualTheme.positive().r : accent.r,
            selected ? VisualTheme.positive().g : accent.g,
            selected ? VisualTheme.positive().b : accent.b,
            selected ? .86f : unlocked ? .46f : .18f);
        shapes.rect(stats.x + 7f, stats.y + stats.height - 4f, Math.max(0f, stats.width - 14f), 3f);

        Rectangle cta = layout.cta();
        if (selected) {
            shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, .10f);
            shapes.rect(cta.x + 6f, cta.y + 6f, Math.max(0f, cta.width - 12f), Math.max(0f, cta.height - 12f));
            shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, .92f);
            shapes.rect(cta.x + 10f, cta.y + cta.height - 4f, Math.max(0f, cta.width - 20f), 3f);
        }
    }

    private void drawChevron(Rectangle bounds, boolean right) {
        float cx = bounds.x + bounds.width * .5f;
        float cy = bounds.y + bounds.height * .5f;
        float half = Math.min(bounds.width, bounds.height) * .15f;
        float tip = right ? cx + half : cx - half;
        float tail = right ? cx - half : cx + half;
        float stroke = Math.max(3f, half * .30f);
        shapes.setColor(VisualTheme.TEXT_STRONG);
        shapes.rectLine(tail, cy + half, tip, cy, stroke);
        shapes.rectLine(tip, cy, tail, cy - half, stroke);
    }

    private void drawContent(SurvivorCatalog.Survivor survivor, boolean unlocked, int level, long xp, long next) {
        batch.begin();
        drawHeader();
        drawPortrait(survivor, unlocked);
        drawStats(survivor, unlocked, level, xp, next);
        drawCta(survivor, unlocked);
        batch.end();
    }

    private void drawHeader() {
        Rectangle back = layout.back();
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, t("shop.back"), back.x + 12f, back.y + back.height * .56f, back.width - 20f, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("survivor.title"), metrics.safeLeft(), metrics.headerBottom() + 53f,
            metrics.contentWidth(), Align.center, false);

        if (!status.isEmpty()) {
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
            font.setColor(VisualTheme.accent());
            font.draw(batch, status, metrics.safeRight() - 360f, metrics.headerBottom() + 46f,
                340f, Align.right, false);
        }
    }

    private void drawPortrait(SurvivorCatalog.Survivor survivor, boolean unlocked) {
        Rectangle p = layout.portrait();
        if (game.art.authoredAvailable()) {
            TextureRegion portrait = game.art.survivor(survivor, GameArt.Motion.IDLE, artTime);
            float aspect = portrait.getRegionWidth() / (float) Math.max(1, portrait.getRegionHeight());
            float maxW = p.width * .78f;
            float maxH = p.height * .78f;
            float drawH = maxH;
            float drawW = drawH * aspect;
            if (drawW > maxW) {
                drawW = maxW;
                drawH = drawW / Math.max(.01f, aspect);
            }
            float px = p.x + (p.width - drawW) * .5f;
            float py = p.y + (p.height - drawH) * .43f;
            if (unlocked) batch.setColor(Color.WHITE);
            else batch.setColor(.38f, .42f, .46f, 1f);
            batch.draw(portrait, px, py, drawW, drawH);
            batch.setColor(Color.WHITE);
        }

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(unlocked ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
        font.draw(batch, unlocked ? "ACTIVE OPERATIVE" : "LOCKED OPERATIVE",
            p.x + 18f, p.y + p.height - 22f, p.width - 36f, Align.center, false);
    }

    private void drawStats(SurvivorCatalog.Survivor survivor, boolean unlocked, int level, long xp, long next) {
        Rectangle s = layout.stats();
        float pad = 24f;
        float x = s.x + pad;
        float w = s.width - pad * 2f;

        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
        font.setColor(unlocked ? VisualTheme.TEXT_STRONG : VisualTheme.MUTED);
        font.draw(batch, t(survivor.displayNameKey()).toUpperCase(), x, s.y + s.height - 30f, w, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY));
        font.setColor(unlocked ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
        font.draw(batch, f("survivor.roleLevel", t(survivor.roleKey()).toUpperCase(java.util.Locale.ROOT), level),
            x, s.y + s.height - 66f, w, Align.left, false);

        float metricTop = s.y + s.height - 112f;
        float colW = w * .5f;
        drawMetric("HP", "x" + fmt(survivor.hpMultiplier), x, metricTop, colW,
            survivor.hpMultiplier >= 1f ? VisualTheme.positive() : VisualTheme.TEXT_STRONG);
        drawMetric("DAMAGE", "x" + fmt(survivor.weaponMultiplier), x + colW, metricTop, colW,
            survivor.weaponMultiplier >= 1f ? VisualTheme.GOLD : VisualTheme.TEXT_STRONG);
        drawMetric("SPEED", "x" + fmt(survivor.speedMultiplier), x, metricTop - 58f, colW,
            survivor.speedMultiplier >= 1f ? VisualTheme.CYAN : VisualTheme.TEXT_STRONG);
        drawMetric("CRIT", "+" + Math.round(survivor.critBonus * 100f) + "%", x + colW, metricTop - 58f, colW,
            survivor.critBonus > 0f ? VisualTheme.VIOLET : VisualTheme.TEXT_STRONG);
        drawMetric("ABILITY", "+" + Math.round(survivor.abilityBonus * 100f) + "%", x, metricTop - 116f, colW,
            survivor.abilityBonus > 0f ? VisualTheme.accent() : VisualTheme.TEXT_STRONG);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("survivor.xp", xp, next), layout.xpBar().x, layout.xpBar().y + 36f,
            layout.xpBar().width, Align.left, false);
    }

    private void drawMetric(String label, String value, float x, float y, float width, Color valueColor) {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, label, x, y, width, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.METRIC));
        font.setColor(valueColor);
        font.draw(batch, value, x, y - 25f, width, Align.left, false);
    }

    private void drawCta(SurvivorCatalog.Survivor survivor, boolean unlocked) {
        Rectangle c = layout.cta();
        String label;
        if (!unlocked) label = unlockText(survivor);
        else if (game.profile.selectedSurvivor == survivor) label = t("survivor.selected");
        else label = t("survivor.select");

        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        font.setColor(unlocked ? VisualTheme.TEXT_STRONG : VisualTheme.MUTED);
        font.draw(batch, label, c.x + 12f, c.y + c.height * .62f, c.width - 24f, Align.center, false);
    }

    private void handleInput() {
        SurvivorCatalog.Survivor[] values = SurvivorCatalog.Survivor.values();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            backCue();
            game.showMenu();
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) { move(-1, values); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) { move(1, values); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            select(values[index]);
            return;
        }
        if (!Gdx.input.justTouched()) return;

        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (layout.back().contains(touch)) { backCue(); game.showMenu(); return; }
        if (layout.previous().contains(touch)) { move(-1, values); return; }
        if (layout.next().contains(touch)) { move(1, values); return; }
        if (layout.cta().contains(touch)) select(values[index]);
    }

    private void move(int delta, SurvivorCatalog.Survivor[] values) {
        index = (index + delta + values.length) % values.length;
        status = "";
        AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
    }

    private void select(SurvivorCatalog.Survivor survivor) {
        if (!game.profile.selectSurvivor(survivor)) {
            status = t("survivor.locked");
            AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
            return;
        }
        status = f("survivor.selectedStatus", t(survivor.displayNameKey()));
        game.saveProfile();
        AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
    }

    private void backCue() { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); }

    private String unlockText(SurvivorCatalog.Survivor survivor) {
        return switch (survivor) {
            case REX -> t("survivor.default");
            case NYX -> t("survivor.unlockNyx");
            case BASTION -> t("survivor.unlockBastion");
            case VOLT -> t("survivor.unlockVolt");
            case WRAITH -> t("survivor.unlockWraith");
        };
    }

    private String fmt(float value) { return String.format(java.util.Locale.ROOT, "%.2f", value); }
    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
}