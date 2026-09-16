package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.meta.AchievementService;
import com.deadlinezero.game.meta.DailyService;
import com.deadlinezero.game.meta.MasteryProgress;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.WeeklyService;
import com.deadlinezero.game.ui.MetaLayout;
import com.deadlinezero.game.ui.UiLayout;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.ui.UiTypography;
import com.deadlinezero.game.ui.UiViewport;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;
import com.deadlinezero.game.visual.VisualTheme;

/** Responsive daily/weekly missions plus permanent non-FOMO mastery progression. */
public final class MissionsScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final UiViewport viewport = new UiViewport();
    private final Vector2 touch = new Vector2();
    private final Rectangle[] dailyRows = new Rectangle[4];
    private final Rectangle[] weeklyRows = new Rectangle[3];
    private final Rectangle[] achievementRows = new Rectangle[AchievementService.Achievement.values().length];
    private UiLayout.Metrics metrics;
    private MetaLayout.Layout layout;
    private Rectangle dailyPanel;
    private Rectangle weeklyPanel;
    private Rectangle progressPanel;
    private Rectangle masteryPanel;
    private Rectangle achievementsPanel;
    private float visualTime;

    public MissionsScreen(DeadlineZeroGame game) {
        this.game = game;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void resize(int width, int height) {
        viewport.resize(width, height);
        metrics = UiLayout.compute(width, height);
        layout = MetaLayout.compute(metrics);
        Rectangle[] columns = MetaLayout.columns(layout.content(), 3, 18f);
        dailyPanel = columns[0];
        weeklyPanel = columns[1];
        progressPanel = columns[2];

        Rectangle dailyInner = inset(dailyPanel, 16f, 48f, 16f, 14f);
        Rectangle[] d = MetaLayout.rows(dailyInner, 4, 10f);
        System.arraycopy(d, 0, dailyRows, 0, dailyRows.length);
        Rectangle weeklyInner = inset(weeklyPanel, 16f, 48f, 16f, 14f);
        Rectangle[] w = MetaLayout.rows(weeklyInner, 3, 12f);
        System.arraycopy(w, 0, weeklyRows, 0, weeklyRows.length);

        float masteryH = Math.min(142f, progressPanel.height * .32f);
        masteryPanel = new Rectangle(progressPanel.x + 14f, progressPanel.y + progressPanel.height - masteryH - 46f,
            progressPanel.width - 28f, masteryH);
        achievementsPanel = new Rectangle(progressPanel.x + 14f, progressPanel.y + 14f,
            progressPanel.width - 28f, masteryPanel.y - progressPanel.y - 28f);
        float gap = 8f;
        int cols = 2;
        int rows = (achievementRows.length + 1) / 2;
        float cellW = (achievementsPanel.width - gap) / cols;
        float cellH = (achievementsPanel.height - gap * Math.max(0, rows - 1)) / rows;
        for (int i = 0; i < achievementRows.length; i++) {
            int c = i % cols;
            int r = i / cols;
            achievementRows[i] = new Rectangle(achievementsPanel.x + c * (cellW + gap),
                achievementsPanel.y + achievementsPanel.height - (r + 1) * cellH - r * gap, cellW, cellH);
        }
    }

    @Override public void render(float delta) {
        visualTime += Math.max(0f, delta);
        handleInput();
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply(batch, shapes);
        PlayerProfile p = game.profile;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        UiRenderer.background(shapes, metrics, visualTime);
        UiRenderer.topRail(shapes, metrics);
        UiRenderer.panel(shapes, dailyPanel.x, dailyPanel.y, dailyPanel.width, dailyPanel.height);
        UiRenderer.panel(shapes, weeklyPanel.x, weeklyPanel.y, weeklyPanel.width, weeklyPanel.height);
        UiRenderer.panel(shapes, progressPanel.x, progressPanel.y, progressPanel.width, progressPanel.height);
        for (Rectangle r : dailyRows) UiRenderer.card(shapes, r.x, r.y, r.width, r.height, false, false);
        for (Rectangle r : weeklyRows) UiRenderer.card(shapes, r.x, r.y, r.width, r.height, false, false);
        UiRenderer.card(shapes, masteryPanel.x, masteryPanel.y, masteryPanel.width, masteryPanel.height, false, true);
        for (int i = 0; i < achievementRows.length; i++) {
            AchievementService.Achievement a = AchievementService.Achievement.values()[i];
            boolean unlocked = AchievementService.unlocked(p, a);
            boolean claimed = p.achievements.claimed(a);
            UiRenderer.card(shapes, achievementRows[i].x, achievementRows[i].y, achievementRows[i].width,
                achievementRows[i].height, unlocked && !claimed, claimed);
        }
        shapes.end();

        batch.begin();
        drawHeader();
        drawDaily(p);
        drawWeekly(p);
        drawProgress(p);
        batch.end();
    }

    private void drawHeader() {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.CYAN_SOFT);
        font.draw(batch, t("shop.back"), layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
            layout.back().width - 16f, Align.left, false);
        font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, t("missions.title"), metrics.safeLeft() + 138f, metrics.headerBottom() + 55f,
            metrics.contentWidth() - 276f, Align.center, false);
    }

    private void drawDaily(PlayerProfile p) {
        heading(t("missions.daily"), dailyPanel, VisualTheme.GOLD);
        drawClaimRow(dailyRows[0], f("missions.login", p.daily.loginStreak,
            p.daily.loginClaimed ? t("missions.loginClaimed") : t("missions.loginClaim")),
            p.daily.loginClaimed, !p.daily.loginClaimed);
        drawClaimRow(dailyRows[1], progressText(t("missions.dailyKills"), p.daily.killsToday, 100),
            p.daily.killMissionClaimed, p.daily.killsToday >= 100);
        drawClaimRow(dailyRows[2], progressText(t("missions.dailyRuns"), p.daily.runsToday, 3),
            p.daily.runMissionClaimed, p.daily.runsToday >= 3);
        drawClaimRow(dailyRows[3], progressText(t("missions.dailyBoss"), p.daily.bossesToday, 1),
            p.daily.bossMissionClaimed, p.daily.bossesToday >= 1);
    }

    private void drawWeekly(PlayerProfile p) {
        heading(t("missions.weekly"), weeklyPanel, VisualTheme.VIOLET);
        drawClaimRow(weeklyRows[0], progressText(f("missions.weeklyKills", WeeklyService.KILL_TARGET), p.weekly.kills, WeeklyService.KILL_TARGET),
            p.weekly.killMissionClaimed, p.weekly.kills >= WeeklyService.KILL_TARGET);
        drawClaimRow(weeklyRows[1], progressText(f("missions.weeklyRuns", WeeklyService.RUN_TARGET), p.weekly.runs, WeeklyService.RUN_TARGET),
            p.weekly.runMissionClaimed, p.weekly.runs >= WeeklyService.RUN_TARGET);
        drawClaimRow(weeklyRows[2], progressText(f("missions.weeklyBoss", WeeklyService.BOSS_TARGET), p.weekly.bosses, WeeklyService.BOSS_TARGET),
            p.weekly.bossMissionClaimed, p.weekly.bosses >= WeeklyService.BOSS_TARGET);
    }

    private void drawProgress(PlayerProfile p) {
        heading(t("missions.mastery"), progressPanel, VisualTheme.accent());
        WeaponDefinition weapon = p.selectedWeapon();
        EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(p.selectedStage);
        int weaponRank = p.mastery.weaponRank(weapon.id);
        int biomeRank = p.mastery.biomeRank(biome);
        int weaponNext = p.mastery.winsForNextWeaponRank(weapon.id);
        int biomeNext = p.mastery.winsForNextBiomeRank(biome);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, f("missions.masteryLine", t(weapon.displayNameKey()), weaponRank, MasteryProgress.MAX_RANK,
            t("mastery.rank." + weaponRank), nextLabel(weaponNext)), masteryPanel.x + 12f,
            masteryPanel.y + masteryPanel.height - 25f, masteryPanel.width - 24f, Align.left, true);
        font.setColor(VisualTheme.VIOLET);
        font.draw(batch, f("missions.masteryLine", t(biome.labelKey()), biomeRank, MasteryProgress.MAX_RANK,
            t("mastery.rank." + biomeRank), nextLabel(biomeNext)), masteryPanel.x + 12f,
            masteryPanel.y + masteryPanel.height * .48f, masteryPanel.width - 24f, Align.left, true);

        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, t("missions.achievements"), achievementsPanel.x, achievementsPanel.y + achievementsPanel.height + 18f,
            achievementsPanel.width, Align.left, false);
        AchievementService.Achievement[] all = AchievementService.Achievement.values();
        for (int i = 0; i < all.length; i++) {
            Rectangle r = achievementRows[i];
            boolean unlocked = AchievementService.unlocked(p, all[i]);
            boolean claimed = p.achievements.claimed(all[i]);
            font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .90f);
            font.setColor(claimed ? VisualTheme.MUTED : unlocked ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
            font.draw(batch, t(all[i].titleKey()), r.x + 8f, r.y + r.height * .67f, r.width - 16f, Align.center, true);
            font.setColor(claimed ? VisualTheme.MUTED : unlocked ? VisualTheme.accent() : VisualTheme.MUTED);
            font.draw(batch, claimed ? t("missions.claimed") : unlocked ? t("common.open") : t("missions.locked"),
                r.x + 8f, r.y + 17f, r.width - 16f, Align.center, false);
        }
    }

    private void heading(String text, Rectangle panel, com.badlogic.gdx.graphics.Color color) {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
        font.setColor(color);
        font.draw(batch, text, panel.x + 16f, panel.y + panel.height - 16f, panel.width - 32f, Align.left, false);
    }

    private void drawClaimRow(Rectangle r, String text, boolean claimed, boolean ready) {
        font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
        font.setColor(claimed ? VisualTheme.MUTED : VisualTheme.TEXT_STRONG);
        font.draw(batch, text, r.x + 12f, r.y + r.height * .64f, r.width - 24f, Align.left, true);
        font.setColor(claimed ? VisualTheme.MUTED : ready ? VisualTheme.positive() : VisualTheme.TEXT_DIM);
        font.draw(batch, claimed ? t("missions.claimed") : ready ? t("common.open") : "…",
            r.x + 12f, r.y + 17f, r.width - 24f, Align.right, false);
    }

    private String progressText(String title, int progress, int target) {
        return f("missions.progress", title, Math.min(progress, target), target, "");
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { game.showMenu(); return; }
        boolean changed = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) changed = DailyService.claimLogin(game.profile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) changed |= DailyService.claimKillMission(game.profile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) changed |= DailyService.claimRunMission(game.profile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) changed |= DailyService.claimBossMission(game.profile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) changed |= WeeklyService.claimKillMission(game.profile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) changed |= WeeklyService.claimRunMission(game.profile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) changed |= WeeklyService.claimBossMission(game.profile);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) changed |= claimAchievement(0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) changed |= claimAchievement(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) changed |= claimAchievement(2);
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) changed |= claimAchievement(3);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) changed |= claimAchievement(4);
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) changed |= claimAchievement(5);

        if (Gdx.input.justTouched()) {
            viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
            if (layout.back().contains(touch)) { game.showMenu(); return; }
            if (dailyRows[0].contains(touch)) changed |= DailyService.claimLogin(game.profile);
            else if (dailyRows[1].contains(touch)) changed |= DailyService.claimKillMission(game.profile);
            else if (dailyRows[2].contains(touch)) changed |= DailyService.claimRunMission(game.profile);
            else if (dailyRows[3].contains(touch)) changed |= DailyService.claimBossMission(game.profile);
            else if (weeklyRows[0].contains(touch)) changed |= WeeklyService.claimKillMission(game.profile);
            else if (weeklyRows[1].contains(touch)) changed |= WeeklyService.claimRunMission(game.profile);
            else if (weeklyRows[2].contains(touch)) changed |= WeeklyService.claimBossMission(game.profile);
            else {
                for (int i = 0; i < achievementRows.length; i++) if (achievementRows[i].contains(touch)) { changed |= claimAchievement(i); break; }
            }
        }
        if (changed) game.saveProfile();
    }

    private boolean claimAchievement(int index) {
        AchievementService.Achievement[] all = AchievementService.Achievement.values();
        if (index < 0 || index >= all.length) return false;
        return AchievementService.claim(game.profile, all[index]);
    }

    private String nextLabel(int winsNeeded) {
        if (winsNeeded <= 0) return t("missions.nextMax");
        return winsNeeded == 1 ? f("missions.nextOne", winsNeeded) : f("missions.nextMany", winsNeeded);
    }

    private static Rectangle inset(Rectangle r, float left, float top, float right, float bottom) {
        return new Rectangle(r.x + left, r.y + bottom, Math.max(1f, r.width - left - right), Math.max(1f, r.height - top - bottom));
    }

    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
}
