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
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.meta.EquipmentService;
import com.deadlinezero.game.meta.EquipmentUpgradeService;
import com.deadlinezero.game.meta.ThreatMilestoneRewardCatalog;
import com.deadlinezero.game.meta.ThreatSetBonusRules;
import com.deadlinezero.game.ui.ResponsiveGrid;
import com.deadlinezero.game.ui.UiIconRenderer;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.VisualTheme;

/** Responsive gear inventory with explicit equip/upgrade/fuse actions. */
public final class GearScreen extends ScreenAdapter {
    private static final int PAGE_SIZE = 6;
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final BitmapFont font = new BitmapFont();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private final Rectangle[] cardBounds = new Rectangle[PAGE_SIZE];
    private final Rectangle[] actions = new Rectangle[4];
    private UiLayout.Metrics metrics;
    private ResponsiveGrid.Spec grid;
    private Rectangle detail;
    private float cardHeight;
    private float visualTime;
    private int index;
    private String status = "";

    public GearScreen(DeadlineZeroGame game) {
        this.game = game;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        grid = ResponsiveGrid.compute(metrics.contentWidth(), 420f, 3, 16f);
        cardHeight = grid.columns() >= 3 ? 92f : 72f;
        int rows = (PAGE_SIZE + grid.columns() - 1) / grid.columns();
        float gridHeight = rows * cardHeight + Math.max(0, rows - 1) * grid.gap();
        float gridBottom = metrics.contentTop() - gridHeight;
        detail = new Rectangle(metrics.safeLeft(), metrics.contentBottom(), metrics.contentWidth(),
            Math.max(130f, gridBottom - metrics.contentBottom() - 20f));
        for (int i = 0; i < cardBounds.length; i++) {
            cardBounds[i] = ResponsiveGrid.cardBounds(i, metrics.safeLeft(), metrics.contentTop(), cardHeight, grid);
        }
        float actionW = metrics.contentWidth() / actions.length;
        float actionH = Math.max(metrics.touchTarget(), metrics.footerTop() - metrics.safeBottom() - 12f);
        for (int i = 0; i < actions.length; i++) {
            actions[i] = new Rectangle(metrics.safeLeft() + i * actionW + 4f, metrics.safeBottom() + 6f,
                actionW - 8f, actionH);
        }
    }

    @Override public void render(float delta) {
        visualTime += Math.max(0f, delta);
        handleInput();
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);

        int size = game.profile.inventory.size();
        if (size > 0) index = Math.max(0, Math.min(index, size - 1));
        else index = 0;
        int pageStart = size == 0 ? 0 : (index / PAGE_SIZE) * PAGE_SIZE;
        int pageEnd = Math.min(size, pageStart + PAGE_SIZE);

        drawShapes(size, pageStart, pageEnd);
        drawText(size, pageStart, pageEnd);
    }

    private void drawShapes(int size, int pageStart, int pageEnd) {
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, visualTime);
        UiRenderer.topRail(shapes, metrics);
        UiRenderer.bottomNav(shapes, metrics);
        UiRenderer.premiumPanel(shapes, detail.x, detail.y, detail.width, detail.height,
            size > 0 ? rarityColor(game.profile.inventory.items().get(index).rarity) : VisualTheme.CYAN_SOFT, true);
        if (size > 0) drawDetailChrome(shapes, game.profile.inventory.items().get(index));
        else drawEmptyGearState(shapes);

        for (int i = pageStart; i < pageEnd; i++) {
            Rectangle r = cardBounds[i - pageStart];
            EquipmentItem item = game.profile.inventory.items().get(i);
            EquipmentItem equipped = game.profile.equipped(item.slot);
            boolean isEquipped = equipped != null && equipped.id.equals(item.id);
            UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, rarityColor(item.rarity), i == index, isEquipped, false);
            drawGearCardChrome(shapes, r, item, i == index, isEquipped);
        }

        UiRenderer.premiumButton(shapes, actions[0].x, actions[0].y, actions[0].width, actions[0].height, VisualTheme.CYAN_SOFT, UiRenderer.ButtonState.NORMAL);
        UiRenderer.premiumButton(shapes, actions[1].x, actions[1].y, actions[1].width, actions[1].height, VisualTheme.GOLD,
            size > 0 ? UiRenderer.ButtonState.SELECTED : UiRenderer.ButtonState.DISABLED);
        UiRenderer.premiumButton(shapes, actions[2].x, actions[2].y, actions[2].width, actions[2].height, VisualTheme.CYAN_SOFT,
            size > 0 ? UiRenderer.ButtonState.NORMAL : UiRenderer.ButtonState.DISABLED);
        UiRenderer.premiumButton(shapes, actions[3].x, actions[3].y, actions[3].width, actions[3].height, VisualTheme.VIOLET,
            size > 0 ? UiRenderer.ButtonState.NORMAL : UiRenderer.ButtonState.DISABLED);
        shapes.end();
    }

    private void drawEmptyGearState(ShapeRenderer shapes) {
        float size = Math.min(118f, detail.height * .42f);
        float x = detail.x + detail.width * .5f - size * .5f;
        float y = detail.y + detail.height * .53f - size * .5f;
        UiRenderer.iconBadge(shapes, x - 12f, y - 12f, size + 24f, VisualTheme.CYAN_SOFT, false);
        UiIconRenderer.draw(shapes, UiIconRenderer.Icon.GEAR, x, y, size, VisualTheme.CYAN_SOFT, .72f);

        float railW = Math.min(detail.width * .48f, 520f);
        float railX = detail.x + (detail.width - railW) * .5f;
        float railY = detail.y + detail.height * .23f;
        UiRenderer.segmentedTrack(shapes, railX, railY, railW, 8f, .18f, 10, VisualTheme.CYAN_SOFT);
    }

    private void drawGearCardChrome(ShapeRenderer shapes, Rectangle r, EquipmentItem item,
                                    boolean selected, boolean equipped) {
        Color rarity = rarityColor(item.rarity);
        float railAlpha = equipped ? .98f : selected ? .82f : .54f;

        shapes.setColor(rarity.r, rarity.g, rarity.b, railAlpha);
        shapes.rect(r.x + 6f, r.y + 6f, 4f, Math.max(0f, r.height - 12f));
        shapes.rect(r.x + 10f, r.y + r.height - 5f, Math.max(0f, r.width - 16f), 3f);

        if (selected || equipped) {
            shapes.setColor(rarity.r, rarity.g, rarity.b, equipped ? .10f : .065f);
            shapes.rect(r.x + 10f, r.y + 8f, Math.max(0f, r.width - 18f), Math.max(0f, r.height - 16f));
        }

        if (equipped) {
            float chipW = Math.min(58f, r.width * .18f);
            shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, .92f);
            shapes.rect(r.x + r.width - chipW - 8f, r.y + 8f, chipW, 3f);
        }
    }

    private void drawDetailChrome(ShapeRenderer shapes, EquipmentItem item) {
        Color rarity = rarityColor(item.rarity);
        EquipmentItem equipped = game.profile.equipped(item.slot);
        float itemScore = EquipmentService.score(item);
        float equippedScore = EquipmentService.score(equipped);
        float delta = itemScore - equippedScore;
        Color compare = equipped != null && equipped.id.equals(item.id)
            ? VisualTheme.positive()
            : delta >= 0f ? VisualTheme.positive() : VisualTheme.danger();

        shapes.setColor(rarity.r, rarity.g, rarity.b, .78f);
        shapes.rect(detail.x + 5f, detail.y + detail.height - 5f, Math.max(0f, detail.width - 10f), 3f);

        float splitX = detail.x + detail.width * .52f;
        shapes.setColor(VisualTheme.BORDER.r, VisualTheme.BORDER.g, VisualTheme.BORDER.b, .46f);
        shapes.rect(splitX, detail.y + 12f, 2f, Math.max(0f, detail.height - 24f));

        shapes.setColor(compare.r, compare.g, compare.b, .08f);
        shapes.rect(detail.x + 8f, detail.y + 8f, Math.max(0f, detail.width * .50f - 12f),
            Math.max(0f, detail.height - 16f));
        shapes.setColor(compare.r, compare.g, compare.b, .72f);
        shapes.rect(detail.x + 8f, detail.y + 8f, Math.max(0f, detail.width * .50f - 12f), 3f);
    }

    private void drawText(int size, int pageStart, int pageEnd) {
        batch.begin();
        int ascensionPieces = ThreatSetBonusRules.equippedPieces(game.profile);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("gear.title"), metrics.safeLeft() + 18f, metrics.headerBottom() + 56f,
            metrics.contentWidth() * .38f, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("gear.power", String.format(java.util.Locale.ROOT, "%.3f", game.profile.aggregatePowerMultiplier())),
            metrics.safeLeft() + metrics.contentWidth() * .40f, metrics.headerBottom() + 48f,
            metrics.contentWidth() * .25f, Align.center, false);
        font.setColor(ascensionPieces >= 2 ? VisualTheme.GOLD : VisualTheme.MUTED);
        font.draw(batch, ThreatSetBonusRules.summary(ascensionPieces), metrics.safeLeft() + metrics.contentWidth() * .64f,
            metrics.headerBottom() + 48f, metrics.contentWidth() * .34f, Align.right, false);

        if (size == 0) {
            font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION) * 1.08f);
            font.setColor(VisualTheme.TEXT_STRONG);
            font.draw(batch, t("gear.empty"), detail.x + 20f, detail.y + detail.height * .29f,
                detail.width - 40f, Align.center, false);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
            font.setColor(VisualTheme.CYAN_SOFT);
            font.draw(batch, "EQUIPMENT BAY  •  AWAITING RECOVERED GEAR", detail.x + 20f,
                detail.y + detail.height * .18f, detail.width - 40f, Align.center, false);
        } else {
            for (int i = pageStart; i < pageEnd; i++) drawCard(game.profile.inventory.items().get(i), i, pageStart);
            drawDetail(game.profile.inventory.items().get(index), size);
        }
        drawActions(size);
        batch.end();
    }

    private void drawCard(EquipmentItem item, int absoluteIndex, int pageStart) {
        Rectangle r = cardBounds[absoluteIndex - pageStart];
        EquipmentItem equipped = game.profile.equipped(item.slot);
        boolean isEquipped = equipped != null && equipped.id.equals(item.id);
        boolean exclusive = ThreatMilestoneRewardCatalog.isExclusiveId(item.id);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        font.setColor(rarityColor(item.rarity));
        font.draw(batch, localizedName(item), r.x + 16f, r.y + r.height - 15f, r.width - 32f, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(exclusive ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
        font.draw(batch, t(item.slotKey()) + "  •  " + t(item.rarityKey()) + "  •  Lv " + item.level,
            r.x + 16f, r.y + r.height - 39f, r.width - 32f, Align.left, false);
        font.setColor(isEquipped ? VisualTheme.positive() : absoluteIndex == index ? VisualTheme.accent() : VisualTheme.TEXT_DIM);
        font.draw(batch, isEquipped ? t("gear.equipped") : t("gear.unequipped"),
            r.x + 16f, r.y + 16f, r.width - 32f, Align.right, false);
        if (absoluteIndex == index && !isEquipped) {
            font.setColor(rarityColor(item.rarity));
            font.draw(batch, t(item.rarityKey()), r.x + 16f, r.y + 16f, r.width * .45f, Align.left, false);
        }
    }

    private void drawDetail(EquipmentItem item, int size) {
        EquipmentItem equipped = game.profile.equipped(item.slot);
        boolean isEquipped = equipped != null && equipped.id.equals(item.id);
        float itemScore = EquipmentService.score(item);
        float equippedScore = EquipmentService.score(equipped);
        float scoreDelta = itemScore - equippedScore;
        float x = detail.x + 24f;
        float top = detail.y + detail.height - 22f;
        float width = detail.width - 48f;

        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
        font.setColor(rarityColor(item.rarity));
        font.draw(batch, localizedName(item), x, top, width * .48f, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("gear.itemStats", t(item.slotKey()), item.level, Math.round(item.powerBonus * 1000f) / 10f),
            x, top - 28f, width * .48f, Align.left, false);
        font.setColor(isEquipped ? VisualTheme.positive() : scoreDelta >= 0f ? VisualTheme.positive() : VisualTheme.danger());
        String compare = isEquipped ? t("gear.equipped") : equipped == null ? t("gear.noEquipped") :
            f("gear.compare", String.format(java.util.Locale.ROOT, "%+.1f", equippedScore <= 0f ? 100f : (scoreDelta / equippedScore) * 100f));
        font.draw(batch, compare, x, top - 54f, width * .48f, Align.left, false);

        float rightX = detail.x + detail.width * .54f;
        font.setColor(VisualTheme.GOLD);
        font.draw(batch, f("gear.upgrade", EquipmentUpgradeService.cost(item)), rightX, top, detail.width * .42f, Align.left, false);
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("gear.index", index + 1, size), rightX, top - 28f, detail.width * .42f, Align.left, false);
        if (!status.isEmpty()) {
            font.setColor(VisualTheme.accent());
            font.draw(batch, status, rightX, top - 56f, detail.width * .42f, Align.left, true);
        }
    }

    private void drawActions(int size) {
        String[] labels = {t("gear.back"), t("gear.equip"), t("gear.upgradeButton"), t("gear.fuse")};
        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        for (int i = 0; i < actions.length; i++) {
            font.setColor(size == 0 && i > 0 ? VisualTheme.MUTED : i == 1 ? VisualTheme.TEXT_STRONG : VisualTheme.TEXT);
            Rectangle r = actions[i];
            font.draw(batch, labels[i], r.x + 8f, r.y + r.height * .60f, r.width - 16f, Align.center, false);
        }
    }

    private void handleInput() {
        int size = game.profile.inventory.size();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
            game.showMenu();
            return;
        }
        if (size > 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) { move(-1, size); return; }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) { move(1, size); return; }
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) { toggleEquip(); return; }
            if (Gdx.input.isKeyJustPressed(Input.Keys.U)) { upgradeSelected(); return; }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) { fuseSelected(game.profile.inventory.items().get(index)); return; }
        }
        if (!Gdx.input.justTouched()) return;

        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (actions[0].contains(touch)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); return; }
        if (size == 0) return;
        if (actions[1].contains(touch)) { toggleEquip(); return; }
        if (actions[2].contains(touch)) { upgradeSelected(); return; }
        if (actions[3].contains(touch)) { fuseSelected(game.profile.inventory.items().get(index)); return; }

        int pageStart = (index / PAGE_SIZE) * PAGE_SIZE;
        int pageEnd = Math.min(size, pageStart + PAGE_SIZE);
        for (int i = pageStart; i < pageEnd; i++) {
            if (!cardBounds[i - pageStart].contains(touch)) continue;
            index = i;
            status = "";
            AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
            return;
        }
    }

    private void move(int delta, int size) {
        index = (index + delta + size) % size;
        status = "";
        AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
    }

    private void toggleEquip() {
        if (game.profile.inventory.size() == 0) return;
        EquipmentItem item = game.profile.inventory.items().get(index);
        EquipmentItem current = game.profile.equipped(item.slot);
        if (current != null && current.id.equals(item.id)) EquipmentService.unequip(game.profile, item.slot);
        else EquipmentService.equip(game.profile, item.id);
        status = t("gear.loadoutUpdated");
        game.saveProfile();
        AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
    }

    private void upgradeSelected() {
        if (game.profile.inventory.size() == 0) return;
        EquipmentItem item = game.profile.inventory.items().get(index);
        if (EquipmentService.upgrade(game.profile, item.id)) {
            status = t("gear.upgraded");
            game.saveProfile();
            AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
        } else {
            status = t("gear.upgradeUnavailable");
            AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
        }
    }

    private void fuseSelected(EquipmentItem selected) {
        if (selected.rarity == EquipmentItem.Rarity.MYTHIC) { status = t("gear.mythicNoFuse"); return; }
        EquipmentItem second = null, third = null;
        for (EquipmentItem candidate : game.profile.inventory.items()) {
            if (candidate.id.equals(selected.id) || candidate.slot != selected.slot || candidate.rarity != selected.rarity) continue;
            if (second == null) second = candidate;
            else { third = candidate; break; }
        }
        if (second == null || third == null) { status = t("gear.needThree"); AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); return; }
        EquipmentItem merged = EquipmentService.mergeThree(game.profile, selected.id, second.id, third.id);
        if (merged == null) { status = t("gear.fusionFailed"); AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); return; }
        status = f("gear.created", t(merged.rarityKey()), localizedName(merged));
        index = Math.max(0, game.profile.inventory.size() - 1);
        game.saveProfile();
        AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
    }

    private Color rarityColor(EquipmentItem.Rarity rarity) {
        return switch (rarity) {
            case COMMON -> VisualTheme.TEXT_DIM;
            case RARE -> VisualTheme.CYAN;
            case EPIC -> VisualTheme.VIOLET;
            case LEGENDARY -> VisualTheme.GOLD;
            case MYTHIC -> Color.MAGENTA;
        };
    }

    private String localizedName(EquipmentItem item) {
        if (item == null) return "";
        String key = item.nameKey();
        if (key != null) return t(key);
        return f("equipment.generatedName", t(item.rarityKey()), t(item.slotKey()));
    }

    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
}
