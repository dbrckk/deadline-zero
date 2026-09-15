package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.meta.AchievementService;
import com.deadlinezero.game.meta.DailyService;
import com.deadlinezero.game.meta.MasteryProgress;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.WeeklyService;
import com.deadlinezero.game.visual.EnvironmentBiomeRules;

/** Functional daily/weekly missions plus permanent non-FOMO mastery progression. */
public final class MissionsScreen extends ScreenAdapter {
    private final DeadlineZeroGame game;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();

    public MissionsScreen(DeadlineZeroGame game) { this.game = game; }

    @Override public void render(float delta) {
        handleInput();
        Gdx.gl.glClearColor(.012f, .018f, .027f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        PlayerProfile p = game.profile;

        float margin = Math.max(44f, w * .045f);
        float gutter = Math.max(38f, w * .035f);
        float columnWidth = (w - margin * 2f - gutter) * .5f;
        float leftX = margin;
        float rightX = margin + columnWidth + gutter;

        batch.begin();
        font.getData().setScale(2.05f);
        font.setColor(Color.WHITE);
        font.draw(batch, t("missions.title"), 0, h - 44f, w, Align.center, false);

        font.getData().setScale(1.42f);
        font.setColor(Color.GOLD);
        font.draw(batch, t("missions.daily"), leftX, h - 118f);
        font.setColor(new Color(.72f, .58f, 1f, 1f));
        font.draw(batch, t("missions.weekly"), rightX, h - 118f);

        font.getData().setScale(1.10f);
        font.setColor(Color.GOLD);
        font.draw(batch, f("missions.login", p.daily.loginStreak,
            p.daily.loginClaimed ? t("missions.loginClaimed") : t("missions.loginClaim")), leftX, h - 158f);

        font.getData().setScale(1.16f);
        drawMission(t("missions.dailyKills"), p.daily.killsToday, 100,
            p.daily.killMissionClaimed, leftX, h - 210f);
        drawMission(t("missions.dailyRuns"), p.daily.runsToday, 3,
            p.daily.runMissionClaimed, leftX, h - 258f);
        drawMission(t("missions.dailyBoss"), p.daily.bossesToday, 1,
            p.daily.bossMissionClaimed, leftX, h - 306f);

        drawMission(f("missions.weeklyKills", WeeklyService.KILL_TARGET),
            p.weekly.kills, WeeklyService.KILL_TARGET, p.weekly.killMissionClaimed, rightX, h - 210f);
        drawMission(f("missions.weeklyRuns", WeeklyService.RUN_TARGET),
            p.weekly.runs, WeeklyService.RUN_TARGET, p.weekly.runMissionClaimed, rightX, h - 258f);
        drawMission(f("missions.weeklyBoss", WeeklyService.BOSS_TARGET),
            p.weekly.bosses, WeeklyService.BOSS_TARGET, p.weekly.bossMissionClaimed, rightX, h - 306f);

        font.getData().setScale(.96f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, t("missions.dailyRewards"), leftX, h - 354f, columnWidth, Align.left, false);
        font.draw(batch, t("missions.weeklyRewards"), rightX, h - 354f, columnWidth, Align.left, false);

        drawMastery(p, w, h, margin);
        font.getData().setScale(.96f);
        font.setColor(Color.LIGHT_GRAY);
        font.setColor(Color.GOLD);
        font.getData().setScale(.92f);
        font.draw(batch, t("missions.achievements"), rightX, h - 438f);
        font.getData().setScale(.94f);
        int achievementY = (int) (h - 482f);
        int key = 7;
        for (AchievementService.Achievement achievement : AchievementService.Achievement.values()) {
            boolean unlocked = AchievementService.unlocked(p, achievement);
            boolean claimed = p.achievements.claimed(achievement);
            font.setColor(claimed ? Color.GRAY : unlocked ? Color.GOLD : Color.LIGHT_GRAY);
            String state = claimed ? t("missions.claimed") : unlocked ? f("missions.achievementClaim", key) : t("missions.locked");
            font.draw(batch, f("missions.achievementLine", t(achievement.titleKey()), t(achievement.descriptionKey()), state),
                rightX, achievementY, columnWidth, Align.left, false);
            achievementY -= 42;
            key++;
        }

        font.getData().setScale(.62f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, t("missions.footer"), margin, 44f);
        batch.end();
    }

    private void drawMastery(PlayerProfile p, float w, float h, float margin) {
        WeaponDefinition weapon = p.selectedWeapon();
        EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(p.selectedStage);
        int weaponRank = p.mastery.weaponRank(weapon.id);
        int biomeRank = p.mastery.biomeRank(biome);
        int weaponNext = p.mastery.winsForNextWeaponRank(weapon.id);
        int biomeNext = p.mastery.winsForNextBiomeRank(biome);

        float y = h - 438f;
        font.setColor(Color.CYAN);
        font.getData().setScale(1.28f);
        font.draw(batch, t("missions.mastery"), margin, y);

        font.getData().setScale(1.05f);
        font.setColor(Color.WHITE);
        font.draw(batch, f("missions.masteryLine", t(weapon.displayNameKey()), weaponRank, MasteryProgress.MAX_RANK,
            t("mastery.rank." + weaponRank), nextLabel(weaponNext)),
            margin, y - 52f, w - margin * 2f, Align.left, false);

        font.setColor(new Color(.72f, .58f, 1f, 1f));
        font.draw(batch, f("missions.masteryLine", t(biome.labelKey()), biomeRank, MasteryProgress.MAX_RANK,
            t("mastery.rank." + biomeRank), nextLabel(biomeNext)),
            margin, y - 100f, w - margin * 2f, Align.left, false);

        font.getData().setScale(.90f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, t("missions.masteryInfo"),
            margin, y - 146f, w - margin * 2f, Align.left, false);
    }

    private String nextLabel(int winsNeeded) {
        if (winsNeeded <= 0) return t("missions.nextMax");
        return winsNeeded == 1 ? f("missions.nextOne", winsNeeded) : f("missions.nextMany", winsNeeded);
    }

    private void drawMission(String title, int progress, int target, boolean claimed, float x, float y) {
        font.setColor(claimed ? Color.LIME : Color.WHITE);
        font.draw(batch, f("missions.progress", title, Math.min(progress, target), target,
            claimed ? t("missions.progressClaimed") : ""), x, y);
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) changed |= AchievementService.claim(game.profile, AchievementService.Achievement.FIRST_DEPLOYMENT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) changed |= AchievementService.claim(game.profile, AchievementService.Achievement.FIELD_VETERAN);
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) changed |= AchievementService.claim(game.profile, AchievementService.Achievement.EXTERMINATOR);
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) changed |= AchievementService.claim(game.profile, AchievementService.Achievement.FIRST_CLEAR);
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) changed |= AchievementService.claim(game.profile, AchievementService.Achievement.DEEP_STRIKE);
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) changed |= AchievementService.claim(game.profile, AchievementService.Achievement.ACCOUNT_TEN);
        if (changed) game.saveProfile();
    }

    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void dispose() { batch.dispose(); font.dispose(); }
}
