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
import com.deadlinezero.game.visual.EnvironmentBiomeRules;

/** Functional daily missions plus permanent non-FOMO mastery progression. */
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

        batch.begin();
        font.getData().setScale(1.15f); font.setColor(Color.WHITE);
        font.draw(batch, "MISSIONS", 0, h - 42, w, Align.center, false);
        font.getData().setScale(.58f);
        font.setColor(Color.GOLD);
        font.draw(batch, "DAILY LOGIN  Streak " + p.daily.loginStreak + "   " + (p.daily.loginClaimed ? "CLAIMED" : "[L] CLAIM"), 40, h - 100);

        font.setColor(Color.WHITE);
        drawMission("[1] Eliminate 100 hostiles", p.daily.killsToday, 100, p.daily.killMissionClaimed, 40, h - 170);
        drawMission("[2] Complete 3 runs", p.daily.runsToday, 3, p.daily.runMissionClaimed, 40, h - 235);
        drawMission("[3] Defeat 1 boss", p.daily.bossesToday, 1, p.daily.bossMissionClaimed, 40, h - 300);

        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Rewards: 350 Credits • 450 Credits • 3 Gems", 40, h - 365);
        drawMastery(p, h);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "ESC / BACK to return to Base", 40, 48);
        batch.end();
    }

    private void drawMastery(PlayerProfile p, float h) {
        WeaponDefinition weapon = p.selectedWeapon();
        EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(p.selectedStage);
        int weaponRank = p.mastery.weaponRank(weapon.id);
        int biomeRank = p.mastery.biomeRank(biome);
        int weaponNext = p.mastery.winsForNextWeaponRank(weapon.id);
        int biomeNext = p.mastery.winsForNextBiomeRank(biome);

        font.setColor(Color.CYAN);
        font.getData().setScale(.62f);
        font.draw(batch, "PERMANENT MASTERY", 40, h - 425);
        font.getData().setScale(.54f);
        font.setColor(Color.WHITE);
        font.draw(batch, weapon.name + "   RANK " + weaponRank + "/" + MasteryProgress.MAX_RANK
            + "   " + nextLabel(weaponNext), 40, h - 470);
        font.setColor(new Color(.72f, .58f, 1f, 1f));
        font.draw(batch, biome.label + "   RANK " + biomeRank + "/" + MasteryProgress.MAX_RANK
            + "   " + nextLabel(biomeNext), 40, h - 510);
        font.setColor(Color.LIGHT_GRAY);
        font.draw(batch, "Victories persist forever • rank-ups auto-award Credits + Gems", 40, h - 550);
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
        if (changed) game.saveProfile();
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); }
}
