package com.deadlinezero.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.meta.EquipmentDropTable;
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.ProfileStore;
import com.deadlinezero.game.meta.RunResult;
import com.deadlinezero.game.meta.RunRewardCalculator;
import com.deadlinezero.game.meta.RunSettlement;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.screen.MenuScreen;
import com.deadlinezero.game.screen.RunResultScreen;
import com.deadlinezero.game.services.GameServices;

public final class DeadlineZeroGame extends Game {
    public final GameServices services;
    public PlayerProfile profile;

    public DeadlineZeroGame(GameServices services) {
        this.services = services == null ? GameServices.noOp() : services;
    }

    @Override public void create() {
        profile = ProfileStore.load();
        services.billing.initialize();
        services.ads.preload();
        showMenu();
    }

    public void showMenu() { setScreen(new MenuScreen(this)); }
    public void startRun() { setScreen(new GameScreen(this)); }

    public void finishRun(int kills, float secondsSurvived, boolean bossKilled, int stage) {
        int safeStage = Math.max(1, stage);
        RunRewardCalculator.Rewards rewards = RunSettlement.apply(profile, kills, secondsSurvived, bossKilled, safeStage);
        EquipmentItem drop = null;
        if (!profile.inventory.full() && (bossKilled || MathUtils.randomBoolean(.55f))) {
            drop = EquipmentDropTable.roll(safeStage, bossKilled);
            profile.inventory.add(drop);
        }
        saveProfile();
        setScreen(new RunResultScreen(this, new RunResult(kills, secondsSurvived, bossKilled, safeStage, rewards, drop)));
    }

    public void saveProfile() { ProfileStore.save(profile); }

    @Override public void pause() { saveProfile(); }
    @Override public void dispose() { saveProfile(); super.dispose(); }

    @Override public void setScreen(com.badlogic.gdx.Screen screen) {
        if (getScreen() != null) getScreen().dispose();
        super.setScreen(screen);
    }
}
