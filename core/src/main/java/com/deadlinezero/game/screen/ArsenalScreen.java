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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.meta.ProfileStore;
import com.deadlinezero.game.meta.WeaponProgression;
import com.deadlinezero.game.meta.WeaponSynergyRules;
import com.deadlinezero.game.ui.ResponsiveGrid;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.VisualTheme;

/** Responsive production Arsenal with clear focus/equipped/locked states. */
public final class ArsenalScreen extends ScreenAdapter {
    private static final int PAGE_SIZE = 8;
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final BitmapFont font = new BitmapFont();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private final Rectangle[] cardBounds = new Rectangle[PAGE_SIZE];
    private UiLayout.Metrics metrics;
    private ResponsiveGrid.Spec grid;
    private Rectangle detail;
    private Rectangle back;
    private Rectangle previousPage;
    private Rectangle nextPage;
    private float cardHeight;
    private float visualTime;
    private int focus;

    public ArsenalScreen(DeadlineZeroGame game) {
        this.game = game;
        WeaponDefinition selected = WeaponCatalog.byId(game.profile.selectedWeaponId);
        WeaponDefinition[] all = WeaponCatalog.all();
        for (int i = 0; i < all.length; i++) if (all[i].id.equals(selected.id)) focus = i;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        grid = ResponsiveGrid.compute(metrics.contentWidth(), 420f, 3, 16f);
        cardHeight = grid.columns() >= 3 ? 84f : 64f;
        int rows = (PAGE_SIZE + grid.columns() - 1) / grid.columns();
        float gridHeight = rows * cardHeight + Math.max(0, rows - 1) * grid.gap();
        float gridBottom = metrics.contentTop() - gridHeight;
        detail = new Rectangle(metrics.safeLeft(), metrics.contentBottom(), metrics.contentWidth(),
            Math.max(112f, gridBottom - metrics.contentBottom() - 20f));
        for (int i = 0; i < cardBounds.length; i++) {
            cardBounds[i] = ResponsiveGrid.cardBounds(i, metrics.safeLeft(), metrics.contentTop(), cardHeight, grid);
        }
        float headerH = metrics.safeTop() - metrics.headerBottom();
        back = new Rectangle(metrics.safeLeft(), metrics.headerBottom(), 128f, headerH);
        nextPage = new Rectangle(metrics.safeRight() - 64f, metrics.headerBottom() + (headerH - 56f) * .5f, 64f, 56f);
        previousPage = new Rectangle(nextPage.x - 76f, nextPage.y, 64f, 56f);
    }

    @Override public void render(float delta) {
        visualTime += Math.max(0f, delta);
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);

        WeaponDefinition[] all = WeaponCatalog.all();
        focus = MathUtils.clamp(focus, 0, Math.max(0, all.length - 1));
        int pageStart = (focus / PAGE_SIZE) * PAGE_SIZE;
        int pageEnd = Math.min(all.length, pageStart + PAGE_SIZE);
        int pageCount = Math.max(1, (all.length + PAGE_SIZE - 1) / PAGE_SIZE);
        int page = pageStart / PAGE_SIZE;
        WeaponDefinition focusedWeapon = all[focus];
        WeaponDefinition equipped = WeaponCatalog.byId(game.profile.selectedWeaponId);

        drawShapes(all, pageStart, pageEnd, page, pageCount, focusedWeapon, equipped);
        drawText(all, pageStart, pageEnd, page, pageCount, focusedWeapon, equipped);
        handleInput(all, pageStart, pageEnd);
    }

    private void drawShapes(WeaponDefinition[] all, int pageStart, int pageEnd, int page, int pageCount,
                            WeaponDefinition focusedWeapon, WeaponDefinition equipped) {
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, visualTime);
        UiRenderer.topRail(shapes, metrics);
        UiRenderer.panel(shapes, detail.x, detail.y, detail.width, detail.height);
        drawDetailChrome(shapes, focusedWeapon, equipped);

        for (int i = pageStart; i < pageEnd; i++) {
            int local = i - pageStart;
            Rectangle r = cardBounds[local];
            WeaponDefinition weapon = all[i];
            boolean selected = weapon.id.equals(game.profile.selectedWeaponId);
            boolean unlocked = WeaponProgression.unlocked(game.profile, weapon);
            UiRenderer.card(shapes, r.x, r.y, r.width, r.height, i == focus, selected);
            drawWeaponCardChrome(shapes, r, weapon, i == focus, selected, unlocked);
            if (!unlocked) {
                shapes.setColor(0f, 0f, 0f, .28f);
                shapes.rect(r.x + 3f, r.y + 3f, r.width - 6f, r.height - 6f);
                shapes.setColor(VisualTheme.GOLD);
                shapes.rect(r.x + 10f, r.y + r.height - 5f, Math.min(56f, r.width * .18f), 2f);
            }
        }

        if (pageCount > 1) {
            UiRenderer.button(shapes, previousPage.x, previousPage.y, previousPage.width, previousPage.height,
                page > 0 ? UiRenderer.ButtonState.NORMAL : UiRenderer.ButtonState.DISABLED);
            UiRenderer.button(shapes, nextPage.x, nextPage.y, nextPage.width, nextPage.height,
                page < pageCount - 1 ? UiRenderer.ButtonState.NORMAL : UiRenderer.ButtonState.DISABLED);
            drawChevron(previousPage, false, page > 0);
            drawChevron(nextPage, true, page < pageCount - 1);
        }

        drawStatBars(focusedWeapon, equipped);
        shapes.end();
    }

    private void drawWeaponCardChrome(ShapeRenderer shapes, Rectangle r, WeaponDefinition weapon,
                                      boolean focused, boolean equipped, boolean unlocked) {
        Color accent = unlocked ? elementColor(weapon) : VisualTheme.MUTED;
        float alpha = !unlocked ? .18f : equipped ? .96f : focused ? .78f : .42f;

        shapes.setColor(accent.r, accent.g, accent.b, alpha);
        shapes.rect(r.x + 6f, r.y + 6f, 4f, Math.max(0f, r.height - 12f));
        shapes.rect(r.x + 10f, r.y + r.height - 5f, Math.max(0f, r.width - 16f), 3f);

        if (focused || equipped) {
            shapes.setColor(accent.r, accent.g, accent.b, equipped ? .095f : .060f);
            shapes.rect(r.x + 10f, r.y + 8f, Math.max(0f, r.width - 18f), Math.max(0f, r.height - 16f));
        }

        if (equipped) {
            float markerW = Math.min(64f, r.width * .20f);
            shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .92f);
            shapes.rect(r.x + r.width - markerW - 8f, r.y + 8f, markerW, 3f);
        }
    }

    private void drawDetailChrome(ShapeRenderer shapes, WeaponDefinition weapon, WeaponDefinition equipped) {
        Color accent = elementColor(weapon);
        float dpsDelta = paperDps(weapon) - paperDps(equipped);
        Color compare = Math.abs(dpsDelta) < .05f ? VisualTheme.TEXT_DIM
            : dpsDelta > 0f ? VisualTheme.positive() : VisualTheme.danger();

        shapes.setColor(accent.r, accent.g, accent.b, .76f);
        shapes.rect(detail.x + 5f, detail.y + detail.height - 5f, Math.max(0f, detail.width - 10f), 3f);

        float previewX = detail.x + 12f;
        float previewW = detail.width * .39f - 18f;
        shapes.setColor(accent.r, accent.g, accent.b, .06f);
        shapes.rect(previewX, detail.y + 10f, Math.max(0f, previewW), Math.max(0f, detail.height - 20f));

        float splitX = detail.x + detail.width * .40f;
        shapes.setColor(VisualTheme.BORDER.r, VisualTheme.BORDER.g, VisualTheme.BORDER.b, .48f);
        shapes.rect(splitX, detail.y + 12f, 2f, Math.max(0f, detail.height - 24f));

        shapes.setColor(compare.r, compare.g, compare.b, .68f);
        shapes.rect(splitX + 12f, detail.y + 9f, Math.max(0f, detail.width - (splitX - detail.x) - 24f), 3f);
    }

    private void drawChevron(Rectangle bounds, boolean right, boolean enabled) {
        float cx = bounds.x + bounds.width * .5f;
        float cy = bounds.y + bounds.height * .5f;
        float half = Math.min(bounds.width, bounds.height) * .15f;
        float tip = right ? cx + half : cx - half;
        float tail = right ? cx - half : cx + half;
        shapes.setColor(enabled ? VisualTheme.TEXT_STRONG : VisualTheme.MUTED);
        float stroke = Math.max(3f, half * .30f);
        shapes.rectLine(tail, cy + half, tip, cy, stroke);
        shapes.rectLine(tip, cy, tail, cy - half, stroke);
    }

    private void drawText(WeaponDefinition[] all, int pageStart, int pageEnd, int page, int pageCount,
                          WeaponDefinition focusedWeapon, WeaponDefinition equipped) {
        batch.begin();
        drawHeader(page, pageCount);
        for (int i = pageStart; i < pageEnd; i++) drawCard(all[i], i, pageStart);
        drawDetail(focusedWeapon, equipped);
        batch.end();
    }

    private void drawHeader(int page, int pageCount) {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, t("shop.back"), back.x + 10f, back.y + back.height * .57f, back.width - 14f, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("arsenal.title"), metrics.safeLeft() + 142f, metrics.headerBottom() + 55f,
            metrics.contentWidth() - 430f, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, t("arsenal.subtitle"), metrics.safeLeft() + 144f, metrics.headerBottom() + 29f,
            metrics.contentWidth() - 430f, Align.left, false);

        if (pageCount > 1) {
            font.setColor(VisualTheme.TEXT_DIM);
            font.draw(batch, f("arsenal.page", page + 1, pageCount), previousPage.x - 142f,
                previousPage.y + 35f, 130f, Align.right, false);
        }
    }

    private void drawCard(WeaponDefinition weapon, int absoluteIndex, int pageStart) {
        Rectangle r = cardBounds[absoluteIndex - pageStart];
        boolean selected = weapon.id.equals(game.profile.selectedWeaponId);
        boolean unlocked = WeaponProgression.unlocked(game.profile, weapon);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        font.setColor(unlocked ? VisualTheme.TEXT_STRONG : VisualTheme.MUTED);
        font.draw(batch, t(weapon.displayNameKey()).toUpperCase(), r.x + 14f, r.y + r.height - 14f,
            r.width - 28f, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(unlocked ? elementColor(weapon) : VisualTheme.MUTED);
        font.draw(batch, pair(role(weapon), elementName(weapon)), r.x + 14f, r.y + r.height - 36f,
            r.width - 28f, Align.left, false);

        String status;
        if (!unlocked) status = f("arsenal.locked", WeaponProgression.unlockAccountLevel(weapon));
        else if (selected) status = t("arsenal.equipped");
        else if (absoluteIndex == focus) status = t("arsenal.select");
        else status = t("arsenal.available");
        font.setColor(!unlocked ? VisualTheme.GOLD : selected ? VisualTheme.accent() : VisualTheme.TEXT_DIM);
        font.draw(batch, status, r.x + 14f, r.y + 15f, r.width - 28f, Align.right, false);

        if (r.height >= 78f && unlocked) {
            font.setColor(VisualTheme.TEXT_DIM);
            font.draw(batch, f("arsenal.cardStats", Math.round(paperDps(weapon)), Math.round(weapon.damage), weapon.projectileCount),
                r.x + 14f, r.y + 15f, r.width * .62f, Align.left, false);
        }
    }

    private void drawDetail(WeaponDefinition weapon, WeaponDefinition equipped) {
        float leftW = detail.width * .40f;
        float textX = detail.x + leftW + 24f;
        float textW = detail.width - leftW - 48f;
        float top = detail.y + detail.height - 20f;

        drawAuthoredPreview(weapon, detail.x + 18f, detail.y + 20f, leftW * .42f, detail.height - 38f);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t(weapon.displayNameKey()).toUpperCase(), textX, top, textW, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(elementColor(weapon));
        font.draw(batch, pair(role(weapon), elementName(weapon)), textX, top - 26f, textW, Align.left, false);

        float dps = paperDps(weapon), equippedDps = paperDps(equipped);
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("arsenal.detailDps", Math.round(dps), deltaText(dps - equippedDps),
            String.format(java.util.Locale.US, "%.2f", weapon.fireInterval), Math.round(weapon.critChance * 100f)),
            textX, top - 50f, textW, Align.left, false);
        font.draw(batch, f("arsenal.detailPen", weapon.penetration, deltaText(weapon.penetration - equipped.penetration),
            oneDecimal(weapon.knockback), deltaText(weapon.knockback - equipped.knockback), weapon.projectileCount,
            deltaText(weapon.projectileCount - equipped.projectileCount)), textX, top - 72f, textW, Align.left, false);

        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, description(weapon), textX, top - 94f, textW, Align.left, true);
        WeaponSynergyRules.Synergy synergy = WeaponSynergyRules.resolve(game.profile.selectedSurvivor, weapon);
        if (synergy != WeaponSynergyRules.Synergy.NONE) {
            font.setColor(VisualTheme.GOLD);
            font.draw(batch, f("arsenal.synergy", synergy.displayName), textX, detail.y + 18f, textW, Align.left, false);
        }
    }

    private void drawStatBars(WeaponDefinition weapon, WeaponDefinition equipped) {
        float x = detail.x + detail.width * .19f;
        float width = detail.width * .18f;
        float baseY = detail.y + 26f;
        float gap = Math.max(18f, (detail.height - 44f) / 4f);
        drawStatBar(x, baseY, width, normalizeDps(weapon), normalizeDps(equipped), VisualTheme.accent());
        drawStatBar(x, baseY + gap, width, MathUtils.clamp((1f / weapon.fireInterval) / 8f, 0f, 1f),
            MathUtils.clamp((1f / equipped.fireInterval) / 8f, 0f, 1f), VisualTheme.CYAN_SOFT);
        drawStatBar(x, baseY + gap * 2f, width, MathUtils.clamp(weapon.penetration / 5f, 0f, 1f),
            MathUtils.clamp(equipped.penetration / 5f, 0f, 1f), VisualTheme.GOLD);
        drawStatBar(x, baseY + gap * 3f, width, MathUtils.clamp(weapon.knockback / 5f, 0f, 1f),
            MathUtils.clamp(equipped.knockback / 5f, 0f, 1f), VisualTheme.VIOLET);
    }

    private void drawStatBar(float x, float y, float width, float value, float baseline, Color color) {
        shapes.setColor(VisualTheme.SURFACE_0);
        shapes.rect(x, y, width, 7f);
        shapes.setColor(VisualTheme.MUTED.r, VisualTheme.MUTED.g, VisualTheme.MUTED.b, .55f);
        shapes.rect(x, y, width * baseline, 7f);
        shapes.setColor(color);
        shapes.rect(x, y + 2f, width * value, 3f);
    }

    private void drawAuthoredPreview(WeaponDefinition weapon, float x, float y, float maxW, float maxH) {
        if (game.art == null || !game.art.authoredAvailable()) return;
        TextureRegion region = game.art.regionOrNull("weapon/" + weapon.id);
        if (region == null) return;
        float aspect = region.getRegionWidth() / (float) Math.max(1, region.getRegionHeight());
        float drawW = maxW;
        float drawH = drawW / Math.max(.01f, aspect);
        if (drawH > maxH) { drawH = maxH; drawW = drawH * aspect; }
        batch.setColor(Color.WHITE);
        batch.draw(region, x + (maxW - drawW) * .5f, y + (maxH - drawH) * .5f, drawW, drawH);
    }

    private void handleInput(WeaponDefinition[] all, int pageStart, int pageEnd) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
            game.showMenu();
            return;
        }
        int columns = grid.columns();
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) focus = Math.max(0, focus - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) focus = Math.min(all.length - 1, focus + 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) focus = Math.max(0, focus - columns);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) focus = Math.min(all.length - 1, focus + columns);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) select(all[focus]);
        if (!Gdx.input.justTouched()) return;

        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (back.contains(touch)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); return; }
        if (previousPage.contains(touch) && pageStart > 0) {
            focus = Math.max(0, pageStart - PAGE_SIZE);
            AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
            return;
        }
        if (nextPage.contains(touch) && pageEnd < all.length) {
            focus = pageEnd;
            AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
            return;
        }
        for (int i = pageStart; i < pageEnd; i++) {
            Rectangle r = cardBounds[i - pageStart];
            if (!r.contains(touch)) continue;
            focus = i;
            select(all[i]);
            return;
        }
    }

    private void select(WeaponDefinition weapon) {
        if (!WeaponProgression.unlocked(game.profile, weapon)) {
            AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
            return;
        }
        if (game.profile.selectWeapon(weapon)) {
            ProfileStore.save(game.profile);
            AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
        }
    }

    private float normalizeDps(WeaponDefinition weapon) { return MathUtils.clamp(paperDps(weapon) / 240f, 0f, 1f); }
    private float paperDps(WeaponDefinition weapon) { return weapon.damage * weapon.projectileCount / Math.max(.05f, weapon.fireInterval); }
    private String role(WeaponDefinition weapon) { return t("weapon.role." + weapon.id); }
    private String elementName(WeaponDefinition weapon) { return t("weapon.element." + weapon.element.name().toLowerCase(java.util.Locale.ROOT)); }
    private String description(WeaponDefinition weapon) { return t("weapon.description." + weapon.id); }
    private String pair(String left, String right) { return left + "  |  " + right; }
    private String deltaText(float delta) { if (Math.abs(delta) < .05f) return ""; return delta > 0f ? "  +" + Math.round(delta) : "  " + Math.round(delta); }
    private String deltaText(int delta) { if (delta == 0) return ""; return delta > 0 ? "  +" + delta : "  " + delta; }
    private String oneDecimal(float value) { return String.format(java.util.Locale.US, "%.1f", value); }
    private Color elementColor(WeaponDefinition weapon) { return switch (weapon.element) { case FIRE -> Color.ORANGE; case FROST -> VisualTheme.CYAN; case SHOCK -> VisualTheme.VIOLET; default -> VisualTheme.CYAN_SOFT; }; }
    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
}
