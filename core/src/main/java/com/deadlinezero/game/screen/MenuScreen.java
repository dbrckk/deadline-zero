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
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.meta.BalanceTelemetryReport;
import com.deadlinezero.game.meta.BalanceTelemetryStore;
import com.deadlinezero.game.meta.BalanceTelemetrySummary;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.ThreatTierRules;
import com.deadlinezero.game.ui.UiIconRenderer;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.GameArt;
import com.deadlinezero.game.visual.VisualTheme;

/** Responsive premium Base/Home shell with explicit mobile interaction regions. */
public final class MenuScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private UiLayout.Metrics metrics;
    private MenuLayoutModel.Layout layout;
    private float t;
    private boolean showBalance;
    private BalanceTelemetrySummary.Summary balanceSummary = BalanceTelemetrySummary.summarize(null);
    private BalanceTelemetryReport.Report balanceReport = BalanceTelemetryReport.analyze(null);

    public MenuScreen(DeadlineZeroGame game) {
        this.game = game;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        layout = MenuLayoutModel.layout(metrics);
    }

    @Override public void render(float delta) {
        t += Math.max(0f, delta);
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);

        PlayerProfile p = game.profile;
        drawShapes(p);
        drawContent(p);
        handleInput();
    }

    private void drawShapes(PlayerProfile p) {
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, t);
        UiRenderer.topRail(shapes, metrics);
        UiRenderer.bottomNav(shapes, metrics);
        UiRenderer.card(shapes, layout.survivorCard().x, layout.survivorCard().y,
            layout.survivorCard().width, layout.survivorCard().height, true, true);
        UiRenderer.card(shapes, layout.loadoutCard().x, layout.loadoutCard().y,
            layout.loadoutCard().width, layout.loadoutCard().height, false, false);
        UiRenderer.card(shapes, layout.threatCard().x, layout.threatCard().y,
            layout.threatCard().width, layout.threatCard().height, false, p.selectedThreatTier > 0);
        float deployPulse = .5f + .5f * (float)Math.sin(t * 2.4f);
        UiRenderer.premiumCta(shapes, layout.deploy().x, layout.deploy().y,
            layout.deploy().width, layout.deploy().height, VisualTheme.accent(), deployPulse);
        drawHomeChrome(shapes, p);
        drawHomeIcons(shapes, p);

        Rectangle[] tabs = layout.bottomTabs();
        for (int i = 0; i < tabs.length; i++) {
            Rectangle tab = tabs[i];
            if (i == 0) {
                shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .14f);
                shapes.rect(tab.x + 4f, tab.y + 5f, tab.width - 8f, tab.height - 10f);
                shapes.setColor(VisualTheme.accent());
                shapes.rect(tab.x + 16f, tab.y + 4f, Math.max(0f, tab.width - 32f), 3f);
            }
            if (i > 0) {
                shapes.setColor(VisualTheme.DIVIDER);
                shapes.rect(tab.x, tab.y + 18f, 1f, Math.max(0f, tab.height - 36f));
            }
        }
        shapes.end();
    }

    private void drawHomeChrome(ShapeRenderer shapes, PlayerProfile p) {
        Rectangle survivor = layout.survivorCard();
        // The survivor is the hero, not a full-screen cyan slab: frame the portrait instead.
        shapes.setColor(VisualTheme.SURFACE_2);
        shapes.rect(survivor.x + 10f, survivor.y + 10f, Math.max(0f, survivor.width - 20f), Math.max(0f, survivor.height - 20f));
        shapes.setColor(VisualTheme.accent());
        shapes.rect(survivor.x + 10f, survivor.y + 10f, 5f, Math.max(0f, survivor.height - 20f));
        shapes.rect(survivor.x + 10f, survivor.y + survivor.height - 5f, Math.max(0f, survivor.width * .34f), 3f);
        shapes.setColor(VisualTheme.BORDER);
        shapes.rect(survivor.x + survivor.width * .46f, survivor.y + 24f, 1f, Math.max(0f, survivor.height - 48f));

        Rectangle loadout = layout.loadoutCard();
        Color weaponAccent = VisualTheme.CYAN_SOFT;
        shapes.setColor(weaponAccent.r, weaponAccent.g, weaponAccent.b, .46f);
        shapes.rect(loadout.x + 6f, loadout.y + loadout.height - 4f, Math.max(0f, loadout.width - 12f), 3f);

        Rectangle threat = layout.threatCard();
        Color threatAccent = p.selectedThreatTier > 0 ? VisualTheme.GOLD : VisualTheme.CYAN_SOFT;
        shapes.setColor(threatAccent.r, threatAccent.g, threatAccent.b, p.selectedThreatTier > 0 ? .82f : .38f);
        shapes.rect(threat.x + 6f, threat.y + threat.height - 4f, Math.max(0f, threat.width - 12f), 3f);
        if (p.selectedThreatTier > 0) {
            shapes.setColor(threatAccent.r, threatAccent.g, threatAccent.b, .08f);
            shapes.rect(threat.x + 7f, threat.y + 7f, Math.max(0f, threat.width - 14f), Math.max(0f, threat.height - 14f));
        }

    }

    private void drawHomeIcons(ShapeRenderer shapes, PlayerProfile p) {
        Rectangle top = layout.topRail();
        float col = top.width / 4f;
        float icon = 22f;
        float iy = top.y + top.height - icon - 12f;
        UiIconRenderer.Icon[] topIcons = {
            UiIconRenderer.Icon.LEVEL,
            UiIconRenderer.Icon.CREDITS,
            UiIconRenderer.Icon.GEMS,
            UiIconRenderer.Icon.STAGE
        };
        Color[] topColors = {
            VisualTheme.CYAN_SOFT,
            VisualTheme.GOLD,
            VisualTheme.accent(),
            VisualTheme.TEXT_DIM
        };
        for (int i = 0; i < topIcons.length; i++) {
            float ix = top.x + col * i + col * .5f - icon * .5f;
            UiRenderer.iconBadge(shapes, ix - 4f, iy - 4f, icon + 8f, topColors[i], i != 3 || p.highestStage > 1);
            UiIconRenderer.draw(shapes, topIcons[i], ix, iy, icon, topColors[i], .95f);
        }

        Rectangle[] tabs = layout.bottomTabs();
        UiIconRenderer.Icon[] navIcons = {
            UiIconRenderer.Icon.BASE,
            UiIconRenderer.Icon.ARSENAL,
            UiIconRenderer.Icon.GEAR,
            UiIconRenderer.Icon.MISSIONS,
            UiIconRenderer.Icon.SHOP,
            UiIconRenderer.Icon.SETTINGS
        };
        for (int i = 0; i < tabs.length; i++) {
            Rectangle tab = tabs[i];
            float size = 22f;
            float x = tab.x + tab.width * .5f - size * .5f;
            float y = tab.y + tab.height * .54f;
            Color color = i == 0 ? VisualTheme.accent() : VisualTheme.TEXT_DIM;
            UiIconRenderer.draw(shapes, navIcons[i], x, y, size, color, i == 0 ? 1f : .72f);
        }

        Rectangle loadout = layout.loadoutCard();
        UiIconRenderer.draw(shapes, UiIconRenderer.Icon.ARSENAL,
            loadout.x + loadout.width - 54f, loadout.y + loadout.height - 52f, 26f, VisualTheme.CYAN_SOFT, .72f);

        Rectangle threat = layout.threatCard();
        UiIconRenderer.draw(shapes,
            ThreatTierRules.unlocked(p) ? UiIconRenderer.Icon.STAGE : UiIconRenderer.Icon.LOCK,
            threat.x + 18f, threat.y + threat.height * .5f - 12f, 24f,
            p.selectedThreatTier > 0 ? VisualTheme.GOLD : VisualTheme.MUTED, .78f);
    }

    private void drawContent(PlayerProfile p) {
        batch.begin();
        drawTopRail(p);
        drawSurvivorCard(p);
        drawLoadout(p);
        drawThreat(p);
        drawDeploy(p);
        drawBottomNav();
        drawBalanceDebug();
        batch.end();
    }

    private void drawTopRail(PlayerProfile p) {
        Rectangle r = layout.topRail();
        float baseline = r.y + r.height * .30f;
        float col = r.width / 4f;

        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("menu.level", p.accountLevel), r.x + 18f, baseline, col - 24f, Align.left, false);
        font.setColor(VisualTheme.GOLD);
        font.draw(batch, f("menu.credits", p.currency(PlayerProfile.Currency.CREDITS)), r.x + col, baseline, col, Align.center, false);
        font.setColor(VisualTheme.accent());
        font.draw(batch, f("menu.gems", p.currency(PlayerProfile.Currency.GEMS)), r.x + col * 2f, baseline, col, Align.center, false);
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("menu.stage", p.selectedStage, p.highestStage), r.x + col * 3f, baseline, col - 18f, Align.right, false);
    }

    private void drawSurvivorCard(PlayerProfile p) {
        Rectangle r = layout.survivorCard();
        float pad = 28f;

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, GameConfig.TITLE, r.x + pad, r.y + r.height - 30f);
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, t("menu.tagline"), r.x + pad, r.y + r.height - 56f);

        if (game.art.authoredAvailable()) {
            TextureRegion portrait = game.art.survivor(p.selectedSurvivor, GameArt.Motion.IDLE, t);
            float maxH = Math.min(330f, r.height * .70f);
            float maxW = r.width * .44f;
            float aspect = portrait.getRegionWidth() / (float) Math.max(1, portrait.getRegionHeight());
            float drawH = maxH;
            float drawW = drawH * aspect;
            if (drawW > maxW) {
                drawW = maxW;
                drawH = drawW / Math.max(.01f, aspect);
            }
            float px = r.x + r.width * .25f - drawW * .5f;
            float py = r.y + Math.max(44f, (r.height - drawH) * .38f);
            batch.setColor(Color.WHITE);
            batch.draw(portrait, px, py, drawW, drawH);
        }

        float tx = r.x + r.width * .49f;
        float tw = r.width * .46f;
        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE) * 1.12f);
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, p.selectedSurvivor.displayName.toUpperCase(), tx, r.y + r.height * .64f, tw, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY));
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, p.selectedSurvivor.role.toUpperCase(), tx, r.y + r.height * .54f, tw, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, t("menu.changeSurvivor"), tx, r.y + r.height * .43f, tw, Align.left, true);
        font.setColor(VisualTheme.accent());
        font.draw(batch, t("survivor.title"), tx, r.y + 34f, tw, Align.left, false);
    }

    private void drawLoadout(PlayerProfile p) {
        Rectangle r = layout.loadoutCard();
        float pad = 24f;
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, t("arsenal.title"), r.x + pad, r.y + r.height - 26f);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, WeaponCatalog.byId(p.selectedWeaponId).displayName.toUpperCase(),
            r.x + pad, r.y + r.height - 64f, r.width - pad * 2f, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, f("menu.deployStage", p.selectedStage), r.x + pad, r.y + r.height - 88f, r.width - pad * 2f, Align.left, false);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.GOLD);
        font.draw(batch, t("menu.arsenal"), r.x + pad, r.y + 28f);
    }

    private void drawThreat(PlayerProfile p) {
        Rectangle r = layout.threatCard();
        font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
        if (ThreatTierRules.unlocked(p)) {
            font.setColor(p.selectedThreatTier > 0 ? VisualTheme.GOLD : VisualTheme.CYAN_SOFT);
            font.draw(batch, f("menu.threat", p.selectedThreatTier, p.highestThreatTier,
                ThreatTierRules.rewardBonusPercent(p.selectedThreatTier)),
                r.x + 18f, r.y + r.height * .60f, r.width - 36f, Align.center, false);
        } else {
            font.setColor(VisualTheme.MUTED);
            font.draw(batch, f("menu.threatLocked", ThreatTierRules.UNLOCK_STAGE),
                r.x + 18f, r.y + r.height * .60f, r.width - 36f, Align.center, false);
        }
    }

    private void drawDeploy(PlayerProfile p) {
        Rectangle r = layout.deploy();
        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION) * 1.12f);
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("menu.deploy"), r.x, r.y + r.height * .64f, r.width, Align.center, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT);
        font.draw(batch, f("menu.deployStage", p.selectedStage), r.x, r.y + r.height * .32f, r.width, Align.center, false);
    }

    private void drawBottomNav() {
        String[] labels = {
            t("menu.base"), t("menu.arsenal"), t("menu.gear"),
            t("menu.missions"), t("menu.shop"), t("menu.settings")
        };
        Rectangle[] tabs = layout.bottomTabs();
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        for (int i = 0; i < tabs.length; i++) {
            Rectangle tab = tabs[i];
            font.setColor(i == 0 ? VisualTheme.accent() : VisualTheme.TEXT_DIM);
            font.draw(batch, labels[i], tab.x + 6f, tab.y + tab.height * .30f, tab.width - 12f, Align.center, false);
        }
    }

    private void drawBalanceDebug() {
        if (!showBalance) return;
        Rectangle r = layout.loadoutCard();
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .78f);
        font.setColor(VisualTheme.GOLD);
        String line = String.format(java.util.Locale.ROOT,
            "BALANCE %d • WIN %.0f%% • AVG %.0fs • DPS %.1f • DMG/M %.1f • K/M %.1f",
            balanceSummary.runs(), balanceSummary.winRate() * 100f, balanceSummary.averageSeconds(),
            balanceSummary.averageDps(), balanceSummary.averageDamageTakenPerMinute(), balanceSummary.averageKillsPerMinute());
        font.draw(batch, line, r.x + 20f, r.y + 54f, r.width - 40f, Align.left, true);
        BalanceTelemetryReport.Outlier outlier = balanceReport.worstOutlier();
        if (outlier != null) {
            font.setColor(VisualTheme.danger());
            String diagnostic = String.format(java.util.Locale.ROOT, "%s %s", outlier.dimension(), outlier.key());
            font.draw(batch, diagnostic, r.x + 20f, r.y + 76f, r.width - 40f, Align.left, false);
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            showBalance = !showBalance;
            if (showBalance) {
                var samples = BalanceTelemetryStore.loadRecent();
                balanceSummary = BalanceTelemetrySummary.summarize(samples);
                balanceReport = BalanceTelemetryReport.analyze(samples);
            }
            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) { selectCue(); game.showArsenal(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) { selectCue(); game.showGear(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) { selectCue(); game.showMissions(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) { selectCue(); game.showShop(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { selectCue(); game.showSurvivors(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) { selectCue(); game.showSettings(); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) { game.profile.selectStage(Math.max(1, game.profile.selectedStage - 1)); game.saveProfile(); }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) { game.profile.selectStage(Math.min(game.profile.highestStage, game.profile.selectedStage + 1)); game.saveProfile(); }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) { changeThreat(-1); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) { changeThreat(1); return; }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            selectCue();
            game.startRun();
            return;
        }
        if (!Gdx.input.justTouched()) return;

        viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
        if (layout.survivorCard().contains(touch)) { selectCue(); game.showSurvivors(); return; }
        if (layout.loadoutCard().contains(touch)) { selectCue(); game.showArsenal(); return; }
        if (layout.threatCard().contains(touch)) {
            changeThreat(touch.x < layout.threatCard().x + layout.threatCard().width * .5f ? -1 : 1);
            return;
        }
        if (layout.deploy().contains(touch)) { selectCue(); game.startRun(); return; }

        Rectangle[] tabs = layout.bottomTabs();
        for (int i = 1; i < tabs.length; i++) {
            if (!tabs[i].contains(touch)) continue;
            selectCue();
            switch (i) {
                case 1 -> game.showArsenal();
                case 2 -> game.showGear();
                case 3 -> game.showMissions();
                case 4 -> game.showShop();
                case 5 -> game.showSettings();
                default -> { }
            }
            return;
        }
    }

    private void changeThreat(int delta) {
        if (!ThreatTierRules.unlocked(game.profile)) return;
        int next = Math.max(0, Math.min(game.profile.highestThreatTier, game.profile.selectedThreatTier + delta));
        if (game.profile.selectThreatTier(next)) {
            selectCue();
            game.saveProfile();
        }
    }

    private void selectCue() { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); }
    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
