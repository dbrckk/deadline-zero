package com.deadlinezero.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.meta.DailyService;
import com.deadlinezero.game.meta.EquipmentDropTable;
import com.deadlinezero.game.meta.EquipmentItem;
import com.deadlinezero.game.meta.PlayerProfile;
import com.deadlinezero.game.meta.ProfileStore;
import com.deadlinezero.game.meta.RunEncounterRuntime;
import com.deadlinezero.game.meta.RunLoadoutContext;
import com.deadlinezero.game.meta.RunMissionRuntime;
import com.deadlinezero.game.meta.RunResult;
import com.deadlinezero.game.meta.RunRewardCalculator;
import com.deadlinezero.game.meta.RunSettlement;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.StageMissionRules;
import com.deadlinezero.game.meta.StageRules;
import com.deadlinezero.game.screen.ArsenalScreen;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.screen.GearScreen;
import com.deadlinezero.game.screen.MenuScreen;
import com.deadlinezero.game.screen.MissionsScreen;
import com.deadlinezero.game.screen.RunResultScreen;
import com.deadlinezero.game.screen.SettingsScreen;
import com.deadlinezero.game.screen.ShopScreen;
import com.deadlinezero.game.screen.SurvivorScreen;
import com.deadlinezero.game.screen.VictoryScreen;
import com.deadlinezero.game.services.GameServices;
import com.deadlinezero.game.visual.GameArt;

public final class DeadlineZeroGame extends Game {
    private static final long DAY_MS = 86_400_000L;
    public final GameServices services;
    public PlayerProfile profile;
    public GameArt art;
    public AudioDirector audio;
    public AccessibilitySettings accessibility;

    public DeadlineZeroGame(GameServices services) { this.services = services == null ? GameServices.noOp() : services; }

    @Override public void create() {
        art = new GameArt();
        accessibility = AccessibilitySettings.load();
        audio = new AudioDirector();
        audio.setVolumes(accessibility.masterVolume, accessibility.sfxVolume, accessibility.musicVolume);
        profile = ProfileStore.load();
        DailyService.refresh(profile, System.currentTimeMillis() / DAY_MS);
        profile.survivors.refreshUnlocks(profile);
        services.billing.initialize();
        services.ads.preload();
        saveProfile();
        showMenu();
    }

    public void showMenu() { RunMissionRuntime.end(); RunEncounterRuntime.end(); if (audio != null) audio.stopCombatMusic(); setScreen(new MenuScreen(this)); }
    public void showGear() { setScreen(new GearScreen(this)); }
    public void showArsenal() { setScreen(new ArsenalScreen(this)); }
    public void showMissions() { setScreen(new MissionsScreen(this)); }
    public void showShop() { setScreen(new ShopScreen(this)); }
    public void showSurvivors() { setScreen(new SurvivorScreen(this)); }
    public void showSettings() { setScreen(new SettingsScreen(this)); }

    public void startRun() {
        RunStageContext.begin(profile == null ? 1 : profile.selectedStage);
        RunLoadoutContext.begin(profile);
        RunEncounterRuntime.begin();
        RunMissionRuntime.begin(() -> Gdx.app.postRunnable(() -> finishVictory()));
        if (audio != null) audio.startCombatMusic(RunStageContext.stage());
        setScreen(new GameScreen(this));
    }

    private void finishVictory() {
        if (!(getScreen() instanceof GameScreen)) return;
        finishRunInternal(RunMissionRuntime.kills(), RunMissionRuntime.elapsed(), true, true);
    }

    public void finishRun(int kills, float secondsSurvived, boolean bossKilled, int ignoredStage) {
        finishRunInternal(kills, secondsSurvived, bossKilled, false);
    }

    private void finishRunInternal(int kills, float secondsSurvived, boolean bossKilled, boolean victorySignal) {
        int safeStage = RunStageContext.stage();
        boolean firstClear = bossKilled && safeStage >= profile.highestStage;
        long firstClearCredits = firstClear ? StageMissionRules.firstClearCredits(safeStage) : 0L;
        int firstClearGems = firstClear ? StageMissionRules.firstClearGems(safeStage) : 0;

        RunRewardCalculator.Rewards rewards = RunSettlement.apply(profile, kills, secondsSurvived, bossKilled, safeStage);
        long encounterCredits = RunEncounterRuntime.consumeBonusCredits();
        if (encounterCredits > 0L) profile.addCurrency(PlayerProfile.Currency.CREDITS, encounterCredits);
        DailyService.refresh(profile, System.currentTimeMillis() / DAY_MS);
        DailyService.recordRun(profile, kills, bossKilled);
        long survivorXp = 35L + Math.max(0, kills) / 4L + safeStage * 12L + (bossKilled ? 80L : 0L);
        profile.survivors.addXp(profile.selectedSurvivor, survivorXp);

        if (firstClear) {
            profile.addCurrency(PlayerProfile.Currency.CREDITS, firstClearCredits);
            profile.addCurrency(PlayerProfile.Currency.GEMS, firstClearGems);
            profile.highestStage = StageRules.nextStage(safeStage);
            profile.selectedStage = profile.highestStage;
            profile.survivors.refreshUnlocks(profile);
        }

        EquipmentItem drop = null;
        if (!profile.inventory.full() && (bossKilled || MathUtils.randomBoolean(.55f))) {
            drop = EquipmentDropTable.roll(safeStage, bossKilled);
            profile.inventory.add(drop);
        }
        RunMissionRuntime.end();
        RunEncounterRuntime.end();
        if (audio != null) audio.stopCombatMusic();
        saveProfile();
        RunResult result = new RunResult(kills, secondsSurvived, bossKilled, safeStage, rewards, drop);
        if (bossKilled || victorySignal) setScreen(new VictoryScreen(this, result, firstClear, firstClearCredits, firstClearGems));
        else setScreen(new RunResultScreen(this, result));
    }

    public void saveProfile() { ProfileStore.save(profile); if (accessibility != null) accessibility.save(); }
    @Override public void pause() {
        if (audio != null) audio.suspend();
        saveProfile();
        super.pause();
    }
    @Override public void resume() {
        super.resume();
        if (audio != null) audio.resume();
    }
    @Override public void dispose() {
        RunMissionRuntime.end();
        RunEncounterRuntime.end();
        saveProfile();
        super.dispose();
        if (audio != null) audio.dispose();
        if (art != null) art.dispose();
    }
    @Override public void setScreen(com.badlogic.gdx.Screen screen) { if (getScreen() != null) getScreen().dispose(); super.setScreen(screen); }
}
