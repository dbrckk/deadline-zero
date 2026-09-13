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
        font.getData().setScale(1.55f);
        font.setColor(Color.WHITE);
        font.draw(batch, "MISSIONS", 0, h - 44f, w, Align.center, false);

        font.getData().setScale(.92f);
        font.setColor(Color.GOLD);
        font.draw(batch, "DAILY OPERATIONS", leftX, h - 118f);
        font.setColor(new Color(.72f, .58f, 1f, 1f));
        font.draw(batch, "WEEKLY OPERATIONS", rightX, h - 118f);

        font.getData().setScale(.72f);
        font.setColor(Color.GOLD);
        font.draw(batch, "LOGIN  •  STREAK " + p.daily.loginStreak + "  •  "
            + (p.daily.loginClaimed ? "CLAIMED" : "[L] CLAIM"), leftX, h - 158f);

        font.getData().setScale(.74f);
        drawMission("[1] Eliminate 100 hostiles", p.daily.killsToday, 100,
            p.daily.killMissionClaimed, leftX, h - 210f);
        drawMission("[2] Complete 3 runs", p.daily.runsToday, 3,
            p.daily.runMissionClaimed, leftX, h - 258f);
        drawMission("[3] Defeat 1 boss", p.daily.bossesToday, 1,
            p.daily.bossMissionClaimed, leftX, h - 306f);

        drawMission("[4] Eliminate " + WeeklyService.KILL_TARGET + " hostiles",
            p.weekly.kills, WeeklyService.KILL_TARGET, p.weekly.killMissionClaimed, rightX, h - 210f);
        drawMission("[5] Complete " + WeeklyService.RUN_TARGET + " runs",
            p.weekly.runs, WeeklyService.RUN_TARGET, p.weekly.runMissionClaimed, rightX, h - 258f);
        drawMission("[6] Defeat " + WeeklyService.BOSS_TARGET + " bosses",
            p.weekly.bosses, WeeklyService.BOSS_TARGET, p.weekly.bossMissionClaimed, rightX, h - 306f);

        font.getData().setScale(.62f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "350 Credits  •  450 Credits  •  3 Gems", leftX, h - 354f, columnWidth, Align.left, false);
        font.draw(batch, "2500 Credits  •  3500 Credits  •  12 Gems", rightX, h - 354f, columnWidth, Align.left, false);

        drawMastery(p, w, h, margin);
        font.getData().setScale(.62f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "ESC / BACK  •  RETURN TO BASE", margin, 44f);
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
        font.getData().setScale(.86f);
        font.draw(batch, "PERMANENT MASTERY", margin, y);

        font.getData().setScale(.70f);
        font.setColor(Color.WHITE);
        font.draw(batch, weapon.displayName + "  •  RANK " + weaponRank + "/" + MasteryProgress.MAX_RANK
            + "  •  " + MasteryProgress.rankTitle(weaponRank) + "  •  " + nextLabel(weaponNext),
            margin, y - 52f, w - margin * 2f, Align.left, false);

        font.setColor(new Color(.72f, .58f, 1f, 1f));
        font.draw(batch, biome.label + "  •  RANK " + biomeRank + "/" + MasteryProgress.MAX_RANK
            + "  •  " + MasteryProgress.rankTitle(biomeRank) + "  •  " + nextLabel(biomeNext),
            margin, y - 100f, w - margin * 2f, Align.left, false);

        font.getData().setScale(.60f);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Victories persist forever  •  rank-ups award Credits + Gems  •  titles are cosmetic",
            margin, y - 146f, w - margin * 2f, Align.left, false);
    }

    private static String nextLabel(int winsNeeded) {
        return winsNeeded <= 0 ? "MAX" : winsNeeded + (winsNeeded == 1 ? " WIN TO NEXT" : " WINS TO NEXT");
    }

    private void drawMission(String title, int progress, int target, boolean claimed, float x, float y) {
        font.setColor(claimed ? Color.LIME : Color.WHITE);
        font.draw(batch, title + "   " + Math.min(progress, target) + "/" + target + (claimed ? "  CLAIMED" : ""), x, y);
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
        if (changed) game.saveProfile();
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); }
}
