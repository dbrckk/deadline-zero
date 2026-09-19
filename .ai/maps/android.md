This file is a merged representation of a subset of the codebase, containing specifically included files and files not matching ignore patterns, combined into a single document by Repomix.
The content has been processed where content has been compressed (code blocks are separated by ⋮---- delimiter).

# File Summary

## Purpose
This file contains a packed representation of a subset of the repository's contents that is considered the most important context.
It is designed to be easily consumable by AI systems for analysis, code review,
or other automated processes.

## File Format
The content is organized as follows:
1. This summary section
2. Repository information
3. Directory structure
4. Repository files (if enabled)
5. Multiple file entries, each consisting of:
  a. A header with the file path (## File: path/to/file)
  b. The full contents of the file in a code block

## Usage Guidelines
- This file should be treated as read-only. Any changes should be made to the
  original repository files, not this packed version.
- When processing this file, use the file path to distinguish
  between different files in the repository.
- Be aware that this file may contain sensitive information. Handle it with
  the same level of security as you would the original repository.

## Notes
- Some files may have been excluded based on .gitignore rules and Repomix's configuration
- Binary files are not included in this packed representation. Please refer to the Repository Structure section for a complete list of file paths, including binary files
- Only files matching these patterns are included: **/*.{py,js,mjs,cjs,ts,tsx,jsx,java,kt,kts,gd,groovy,gradle,toml,json,yaml,yml,sql,sh}
- Files matching these patterns are excluded: .ai/**, **/node_modules/**, **/.gradle/**, **/build/**, **/dist/**, **/.venv/**, **/__pycache__/**, **/.pytest_cache/**, **/.git/**, **/coverage/**, **/*.lock, **/*.min.js, **/*.map, assets/**, art/**, art_sources/**, marketing/**, colab/**, kaggle/**, discovery-cache.json, health-snapshot.json, history.json
- Files matching patterns in .gitignore are excluded
- Files matching default ignore patterns are excluded
- Content has been compressed - code blocks are separated by ⋮---- delimiter
- Files are sorted by Git change count (files with more changes are at the bottom)

# Directory Structure
```
src/
  androidTest/
    java/
      com/
        deadlinezero/
          game/
            android/
              AndroidFirstPlayableJourneyTest.java
              AndroidGameplayVisualProbeTest.java
              AndroidLauncherSmokeTest.java
              AndroidPerformanceProbeTest.java
              AndroidProcessPersistenceTest.java
              AndroidResponsiveUiVisualProbeTest.java
              AndroidVerticalSliceTest.java
  main/
    java/
      com/
        deadlinezero/
          game/
            android/
              AndroidAdsService.java
              AndroidBillingService.java
              AndroidConsentManager.java
              AndroidHapticsService.java
              AndroidLauncher.java
              AndroidPlayGamesCloudSaveAdapter.java
              AndroidPrivacyService.java
              AndroidReviewService.java
              AndroidShareService.java
              AndroidThermalService.java
build.gradle
```

# Files

## File: src/androidTest/java/com/deadlinezero/game/android/AndroidFirstPlayableJourneyTest.java
```java
public final class AndroidFirstPlayableJourneyTest {
⋮----
public void traversesContractCombatDefeatAndVictorySettlement() throws Exception {
try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
AndroidLauncher activity = activity(scenario);
run(activity, () -> {
DeadlineZeroGame game = game(activity);
⋮----
game.startRun();
assertTrue("startRun must open contract selection", game.getScreen() instanceof RunContractScreen);
⋮----
game.startRunWithContract(RunModifierContext.offers()[0]);
assertTrue("contract selection must enter combat", game.getScreen() instanceof GameScreen);
⋮----
game.finishRun(12, 45f, false, 0);
assertTrue("defeat settlement must open run result", game.getScreen() instanceof RunResultScreen);
assertEquals("defeat must settle exactly one run", runsBefore + 1, game.profile.totalRuns);
⋮----
assertTrue(game.getScreen() instanceof RunContractScreen);
⋮----
assertTrue(game.getScreen() instanceof GameScreen);
⋮----
// The public finishRun boundary derives victory from RunMissionRuntime boss progress.
// This journey deliberately validates a second complete settlement without forging
// private mission-runtime state; boss-victory semantics remain covered by runtime tests.
game.finishRun(80, 180f, false, 0);
assertTrue("second settlement must open run result", game.getScreen() instanceof RunResultScreen);
assertEquals("second settlement must count exactly one additional run", runsBefore + 2, game.profile.totalRuns);
⋮----
game.saveProfile();
⋮----
private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
⋮----
scenario.onActivity(ref::set);
assertNotNull("Android launcher unavailable", ref.get());
return ref.get();
⋮----
private static DeadlineZeroGame game(AndroidLauncher activity) {
assertTrue(activity.getApplicationListener() instanceof DeadlineZeroGame);
DeadlineZeroGame game = (DeadlineZeroGame) activity.getApplicationListener();
assertNotNull("profile unavailable", game.profile);
⋮----
private static void run(AndroidLauncher activity, Runnable action) throws Exception {
CountDownLatch done = new CountDownLatch(1);
⋮----
activity.postRunnable(() -> {
try { action.run(); } catch (Throwable t) { failure.set(t); } finally { done.countDown(); }
⋮----
assertTrue("game thread timeout", done.await(10, TimeUnit.SECONDS));
if (failure.get() != null) throw new AssertionError("first-playable journey failed", failure.get());
```

## File: src/androidTest/java/com/deadlinezero/game/android/AndroidGameplayVisualProbeTest.java
```java
/** Captures deterministic phone-scale gameplay frames for human visual QA in CI artifacts. */
⋮----
public final class AndroidGameplayVisualProbeTest {
⋮----
public void capturesWeeklyMissionsScreen() throws Exception {
try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
AndroidLauncher activity = activity(scenario);
runOnGameThread(activity, () -> {
DeadlineZeroGame game = game(activity);
⋮----
game.profile.achievements.markClaimed(com.deadlinezero.game.meta.AchievementService.Achievement.FIRST_DEPLOYMENT);
game.showMissions();
assertTrue("expected MissionsScreen for weekly visual probe", game.getScreen() instanceof MissionsScreen);
⋮----
Thread.sleep(700L);
capture("weekly-missions.png");
⋮----
public void capturesGraphicsQualitySettings() throws Exception {
⋮----
com.deadlinezero.game.config.GraphicsSettings.set(
⋮----
game.showSettings();
assertTrue("expected SettingsScreen for graphics-quality visual probe",
game.getScreen() instanceof com.deadlinezero.game.screen.SettingsScreen);
⋮----
Thread.sleep(600L);
capture("graphics-settings.png");
⋮----
public void capturesCloudSaveScreen() throws Exception {
⋮----
game.showCloudSave();
assertTrue("expected CloudSaveScreen for visual probe", game.getScreen() instanceof CloudSaveScreen);
⋮----
Thread.sleep(500L);
capture("cloud-save.png");
⋮----
public void capturesChampionVariantCrowd() throws Exception {
⋮----
game.startRun();
game.startRunWithContract(RunModifierContext.offers()[0]);
assertTrue("expected GameScreen for champion visual probe", game.getScreen() instanceof GameScreen);
RunStageContext.begin(1);
injectChampionVariantCrowd((GameScreen) game.getScreen());
⋮----
Thread.sleep(900L);
capture("champion-variants.png");
⋮----
private static void injectChampionVariantCrowd(GameScreen screen) {
⋮----
Field field = GameScreen.class.getDeclaredField("enemies");
field.setAccessible(true);
Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
⋮----
Enemy enemy = new Enemy(Enemy.Type.ELITE, positions[i][0], positions[i][1],
⋮----
// Instrumentation-only deterministic visual state; runtime selection is tested separately.
⋮----
enemies.add(enemy);
⋮----
throw new AssertionError("unable to inject champion variants for visual QA", exception);
⋮----
public void capturesExpandedUpgradePoolOverlay() throws Exception {
⋮----
assertTrue("expected GameScreen for upgrade visual probe", game.getScreen() instanceof GameScreen);
injectUpgradeChoices((GameScreen) game.getScreen());
⋮----
capture("upgrade-pool.png");
⋮----
private static void injectUpgradeChoices(GameScreen screen) {
⋮----
Field choicesField = GameScreen.class.getDeclaredField("choices");
choicesField.setAccessible(true);
Upgrade[] choices = (Upgrade[]) choicesField.get(screen);
⋮----
Field choosingUpgrade = GameScreen.class.getDeclaredField("choosingUpgrade");
choosingUpgrade.setAccessible(true);
choosingUpgrade.setBoolean(screen, true);
⋮----
Field choosingLegendary = GameScreen.class.getDeclaredField("choosingLegendary");
choosingLegendary.setAccessible(true);
choosingLegendary.setBoolean(screen, false);
⋮----
throw new AssertionError("unable to inject expanded upgrade choices for visual QA", exception);
⋮----
public void capturesRexGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for Rex visual probe", game.getScreen() instanceof GameScreen);
assertTrue("visual probe must run the production REX selection",
⋮----
Thread.sleep(2200L);
capture("rex-gameplay.png");
⋮----
runOnGameThread(activity, () -> injectAuthoredEnemyCrowd((GameScreen) game(activity).getScreen()));
⋮----
capture("rex-shambler-crowd.png");
⋮----
runOnGameThread(activity, CombatVisualEvents::markPlayerShot);
Thread.sleep(80L);
capture("rex-attack.png");
⋮----
public void capturesWraithGameplayAndAttackFrames() throws Exception {
⋮----
// Exercise WRAITH through the real selected-survivor runtime path. Direct assignment is
// instrumentation-only so the probe does not depend on account unlock progression.
⋮----
assertTrue("expected GameScreen for visual probe", game.getScreen() instanceof GameScreen);
assertTrue("visual probe must run the production WRAITH selection",
⋮----
capture("wraith-gameplay.png");
⋮----
// Deterministic authored-enemy composition. Reflection stays instrumentation-only so
// production GameScreen does not gain QA API surface.
⋮----
capture("wraith-crowd.png");
⋮----
capture("wraith-attack.png");
⋮----
public void capturesRevenantGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for REVENANT visual probe", game.getScreen() instanceof GameScreen);
RunStageContext.begin(4);
injectRevenantBoss((GameScreen) game.getScreen());
⋮----
Thread.sleep(1800L);
capture("revenant-gameplay.png");
⋮----
capture("revenant-crowd.png");
⋮----
capture("revenant-attack.png");
⋮----
public void capturesWardenGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for WARDEN visual probe", game.getScreen() instanceof GameScreen);
RunStageContext.begin(7);
injectWardenBoss((GameScreen) game.getScreen());
⋮----
capture("warden-gameplay.png");
⋮----
capture("warden-crowd.png");
⋮----
capture("warden-attack.png");
⋮----
public void capturesHarvesterGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for HARVESTER visual probe", game.getScreen() instanceof GameScreen);
RunStageContext.begin(6);
injectHarvesterBoss((GameScreen) game.getScreen());
⋮----
capture("harvester-gameplay.png");
⋮----
capture("harvester-crowd.png");
⋮----
capture("harvester-attack.png");
⋮----
public void capturesFrostColossusGameplay() throws Exception {
⋮----
assertTrue("expected GameScreen for FROST COLOSSUS visual probe", game.getScreen() instanceof GameScreen);
RunStageContext.begin(40);
assertTrue("stage 40 must route to FROST COLOSSUS",
com.deadlinezero.game.ai.BossIdentity.forStage(40)
⋮----
injectFrostColossusBoss((GameScreen) game.getScreen());
⋮----
Thread.sleep(1500L);
capture("frost-colossus-gameplay.png");
⋮----
private static void injectFrostColossusBoss(GameScreen screen) {
⋮----
enemies.add(new Enemy(Enemy.Type.BOSS, 0f, 3.8f, 500_000f, .015f, 1.05f, 0f, 2));
enemies.add(new Enemy(Enemy.Type.SHIELDED, -4.5f, 1.8f, 500_000f, .02f, .58f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.BRUTE, 4.6f, 1.5f, 500_000f, .02f, .62f, 0f, 1));
⋮----
throw new AssertionError("unable to inject FROST COLOSSUS boss for visual QA", exception);
⋮----
public void capturesCryogenicDepthsGameplay() throws Exception {
⋮----
assertTrue("expected GameScreen for CRYOGENIC DEPTHS visual probe", game.getScreen() instanceof GameScreen);
⋮----
assertTrue("stage 40 must route to CRYOGENIC DEPTHS",
com.deadlinezero.game.visual.EnvironmentBiomeRules.forStage(40)
⋮----
injectCryogenicDepthsCrowd((GameScreen) game.getScreen());
⋮----
Thread.sleep(1200L);
capture("cryogenic-depths-gameplay.png");
⋮----
private static void injectCryogenicDepthsCrowd(GameScreen screen) {
⋮----
enemies.add(new Enemy(Enemy.Type.REGENERATOR, -5.2f, 3.2f, 500_000f, .02f, .58f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.RANGED, 0.6f, 4.4f, 500_000f, .02f, .46f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.ELITE, 5.4f, 2.8f, 500_000f, .02f, .62f, 0f, 1));
⋮----
throw new AssertionError("unable to inject CRYOGENIC DEPTHS crowd for visual QA", exception);
⋮----
public void capturesCryoVaultGameplay() throws Exception {
⋮----
assertTrue("expected GameScreen for CRYO VAULT visual probe", game.getScreen() instanceof GameScreen);
RunStageContext.begin(30);
assertTrue("stage 30 must route to CRYO VAULT",
com.deadlinezero.game.visual.EnvironmentBiomeRules.forStage(30)
⋮----
injectCryoVaultCrowd((GameScreen) game.getScreen());
⋮----
capture("cryo-vault-gameplay.png");
⋮----
private static void injectCryoVaultCrowd(GameScreen screen) {
⋮----
enemies.add(new Enemy(Enemy.Type.SHIELDED, -4.8f, 3.0f, 500_000f, .02f, .58f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.PHANTOM, 0.4f, 4.2f, 500_000f, .02f, .44f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.BRUTE, 5.2f, 2.6f, 500_000f, .02f, .62f, 0f, 1));
⋮----
throw new AssertionError("unable to inject CRYO VAULT crowd for visual QA", exception);
⋮----
public void capturesCinderGunnerGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for CINDER GUNNER visual probe", game.getScreen() instanceof GameScreen);
RunStageContext.begin(10);
injectCinderGunner((GameScreen) game.getScreen());
⋮----
capture("cinder-gunner-gameplay.png");
⋮----
capture("cinder-gunner-crowd.png");
⋮----
runOnGameThread(activity, () -> forceCinderGunnerAttack((GameScreen) game(activity).getScreen()));
⋮----
capture("cinder-gunner-attack.png");
⋮----
private static void injectCinderGunner(GameScreen screen) {
⋮----
Enemy gunner = new Enemy(Enemy.Type.RANGED, 0f, 3.2f, 500_000f, .02f, .50f, 0f, 1);
assertTrue("stage 10 RANGED must resolve to CINDER GUNNER",
gunner.biomeIdentity() == BiomeEnemyRoster.Identity.CINDER_GUNNER);
enemies.add(gunner);
⋮----
throw new AssertionError("unable to inject CINDER GUNNER for visual QA", exception);
⋮----
private static void forceCinderGunnerAttack(GameScreen screen) {
⋮----
Field enemiesField = GameScreen.class.getDeclaredField("enemies");
enemiesField.setAccessible(true);
Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
⋮----
if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.CINDER_GUNNER) {
⋮----
assertNotNull("CINDER GUNNER missing before attack capture", gunner);
Field stateField = gunner.attack.getClass().getDeclaredField("state");
Field timerField = gunner.attack.getClass().getDeclaredField("timer");
stateField.setAccessible(true);
timerField.setAccessible(true);
stateField.set(gunner.attack, EnemyState.TELEGRAPHING);
timerField.setFloat(gunner.attack, 10f);
⋮----
throw new AssertionError("unable to force CINDER GUNNER attack animation for visual QA", exception);
⋮----
public void capturesNullWardGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for NULL WARD visual probe", game.getScreen() instanceof GameScreen);
RunStageContext.begin(20);
injectNullWard((GameScreen) game.getScreen());
⋮----
capture("null-ward-gameplay.png");
⋮----
capture("null-ward-crowd.png");
runOnGameThread(activity, () -> forceNullWardAttack((GameScreen) game(activity).getScreen()));
⋮----
capture("null-ward-attack.png");
⋮----
private static void injectNullWard(GameScreen screen) {
⋮----
Enemy ward = new Enemy(Enemy.Type.REGENERATOR, 0f, 3.2f, 500_000f, .02f, .56f, 0f, 1);
assertTrue("stage 20 REGENERATOR must resolve to NULL WARD",
ward.biomeIdentity() == BiomeEnemyRoster.Identity.NULL_WARD);
enemies.add(ward);
⋮----
throw new AssertionError("unable to inject NULL WARD for visual QA", exception);
⋮----
private static void forceNullWardAttack(GameScreen screen) {
⋮----
if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.NULL_WARD) {
⋮----
assertNotNull("NULL WARD missing before attack capture", ward);
Field stateField = ward.attack.getClass().getDeclaredField("state");
Field timerField = ward.attack.getClass().getDeclaredField("timer");
⋮----
stateField.set(ward.attack, EnemyState.TELEGRAPHING);
timerField.setFloat(ward.attack, 10f);
⋮----
throw new AssertionError("unable to force NULL WARD attack animation for visual QA", exception);
⋮----
public void capturesStaticSeerGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for STATIC SEER visual probe", game.getScreen() instanceof GameScreen);
⋮----
injectStaticSeer((GameScreen) game.getScreen());
⋮----
capture("static-seer-gameplay.png");
⋮----
capture("static-seer-crowd.png");
runOnGameThread(activity, () -> forceStaticSeerAttack((GameScreen) game(activity).getScreen()));
⋮----
capture("static-seer-attack.png");
⋮----
private static void injectStaticSeer(GameScreen screen) {
⋮----
Enemy seer = new Enemy(Enemy.Type.RANGED, 0f, 3.2f, 500_000f, .02f, .56f, 0f, 1);
assertTrue("stage 20 RANGED must resolve to STATIC SEER",
seer.biomeIdentity() == BiomeEnemyRoster.Identity.STATIC_SEER);
enemies.add(seer);
⋮----
throw new AssertionError("unable to inject STATIC SEER for visual QA", exception);
⋮----
private static void forceStaticSeerAttack(GameScreen screen) {
⋮----
if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.STATIC_SEER) {
⋮----
assertNotNull("STATIC SEER missing before attack capture", seer);
Field stateField = seer.attack.getClass().getDeclaredField("state");
Field timerField = seer.attack.getClass().getDeclaredField("timer");
⋮----
stateField.set(seer.attack, EnemyState.TELEGRAPHING);
timerField.setFloat(seer.attack, 10f);
⋮----
throw new AssertionError("unable to force STATIC SEER attack animation for visual QA", exception);
⋮----
public void capturesPhaseStalkerGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for PHASE STALKER visual probe", game.getScreen() instanceof GameScreen);
⋮----
injectPhaseStalker((GameScreen) game.getScreen());
⋮----
capture("phase-stalker-gameplay.png");
⋮----
capture("phase-stalker-crowd.png");
runOnGameThread(activity, () -> forcePhaseStalkerAttack((GameScreen) game(activity).getScreen()));
⋮----
capture("phase-stalker-attack.png");
⋮----
private static void injectPhaseStalker(GameScreen screen) {
⋮----
Enemy stalker = new Enemy(Enemy.Type.PHANTOM, 0f, 3.2f, 500_000f, .02f, .56f, 0f, 1);
assertTrue("stage 20 PHANTOM must resolve to PHASE STALKER",
stalker.biomeIdentity() == BiomeEnemyRoster.Identity.PHASE_STALKER);
enemies.add(stalker);
⋮----
throw new AssertionError("unable to inject PHASE STALKER for visual QA", exception);
⋮----
private static void forcePhaseStalkerAttack(GameScreen screen) {
⋮----
if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.PHASE_STALKER) {
⋮----
assertNotNull("PHASE STALKER missing before attack capture", stalker);
Field stateField = stalker.attack.getClass().getDeclaredField("state");
Field timerField = stalker.attack.getClass().getDeclaredField("timer");
⋮----
stateField.set(stalker.attack, EnemyState.TELEGRAPHING);
timerField.setFloat(stalker.attack, 10f);
⋮----
throw new AssertionError("unable to force PHASE STALKER attack animation for visual QA", exception);
⋮----
public void capturesSlagGuardGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for SLAG GUARD visual probe", game.getScreen() instanceof GameScreen);
⋮----
injectSlagGuard((GameScreen) game.getScreen());
⋮----
capture("slag-guard-gameplay.png");
⋮----
capture("slag-guard-crowd.png");
runOnGameThread(activity, () -> forceSlagGuardAttack((GameScreen) game(activity).getScreen()));
⋮----
capture("slag-guard-attack.png");
⋮----
private static void injectSlagGuard(GameScreen screen) {
⋮----
Enemy guard = new Enemy(Enemy.Type.SHIELDED, 0f, 3.2f, 500_000f, .02f, .56f, 0f, 1);
assertTrue("stage 10 SHIELDED must resolve to SLAG GUARD",
guard.biomeIdentity() == BiomeEnemyRoster.Identity.SLAG_GUARD);
enemies.add(guard);
⋮----
throw new AssertionError("unable to inject SLAG GUARD for visual QA", exception);
⋮----
private static void forceSlagGuardAttack(GameScreen screen) {
⋮----
if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.SLAG_GUARD) {
⋮----
assertNotNull("SLAG GUARD missing before attack capture", guard);
Field stateField = guard.attack.getClass().getDeclaredField("state");
Field timerField = guard.attack.getClass().getDeclaredField("timer");
⋮----
stateField.set(guard.attack, EnemyState.TELEGRAPHING);
timerField.setFloat(guard.attack, 10f);
⋮----
throw new AssertionError("unable to force SLAG GUARD attack animation for visual QA", exception);
⋮----
public void capturesForgeHoundGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for FORGE HOUND visual probe", game.getScreen() instanceof GameScreen);
⋮----
injectForgeHound((GameScreen) game.getScreen());
⋮----
capture("forge-hound-gameplay.png");
⋮----
capture("forge-hound-crowd.png");
⋮----
runOnGameThread(activity, () -> forceForgeHoundAttack((GameScreen) game(activity).getScreen()));
⋮----
capture("forge-hound-attack.png");
⋮----
private static void injectForgeHound(GameScreen screen) {
⋮----
Enemy hound = new Enemy(Enemy.Type.RUNNER, 0f, 3.2f, 500_000f, .02f, .50f, 0f, 1);
assertTrue("stage 10 RUNNER must resolve to FORGE HOUND",
hound.biomeIdentity() == BiomeEnemyRoster.Identity.FORGE_HOUND);
enemies.add(hound);
⋮----
throw new AssertionError("unable to inject FORGE HOUND for visual QA", exception);
⋮----
private static void forceForgeHoundAttack(GameScreen screen) {
⋮----
if (enemy.alive && enemy.biomeIdentity() == BiomeEnemyRoster.Identity.FORGE_HOUND) {
⋮----
assertNotNull("FORGE HOUND missing before attack capture", hound);
Field stateField = hound.attack.getClass().getDeclaredField("state");
Field timerField = hound.attack.getClass().getDeclaredField("timer");
⋮----
stateField.set(hound.attack, EnemyState.TELEGRAPHING);
timerField.setFloat(hound.attack, 10f);
⋮----
throw new AssertionError("unable to force FORGE HOUND attack animation for visual QA", exception);
⋮----
public void capturesNullArchonGameplayAndAttackFrames() throws Exception {
⋮----
assertTrue("expected GameScreen for NULL ARCHON visual probe", game.getScreen() instanceof GameScreen);
⋮----
injectNullArchonBoss((GameScreen) game.getScreen());
⋮----
capture("null-archon-gameplay.png");
⋮----
capture("null-archon-crowd.png");
⋮----
capture("null-archon-attack.png");
⋮----
private static void injectNullArchonBoss(GameScreen screen) {
⋮----
enemies.add(new Enemy(Enemy.Type.BOSS, 0f, 3.8f, 500_000f, .015f, .78f, 0f, 2));
⋮----
throw new AssertionError("unable to inject NULL ARCHON boss for visual QA", exception);
⋮----
private static void injectHarvesterBoss(GameScreen screen) {
⋮----
throw new AssertionError("unable to inject HARVESTER boss for visual QA", exception);
⋮----
private static void injectWardenBoss(GameScreen screen) {
⋮----
throw new AssertionError("unable to inject WARDEN boss for visual QA", exception);
⋮----
private static void injectRevenantBoss(GameScreen screen) {
⋮----
throw new AssertionError("unable to inject REVENANT boss for visual QA", exception);
⋮----
private static void injectAuthoredEnemyCrowd(GameScreen screen) {
⋮----
enemies.add(new Enemy(Enemy.Type.SHAMBLER, 5.2f, 0f, 50_000f, .08f, .50f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.SHAMBLER, -5.2f, 0f, 50_000f, .08f, .50f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.SHAMBLER, 0f, 4.2f, 50_000f, .08f, .50f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.SHAMBLER, 0f, -4.2f, 50_000f, .08f, .50f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.RUNNER, 3.7f, 3.0f, 50_000f, .22f, .46f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.RUNNER, -3.7f, -3.0f, 50_000f, .22f, .46f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.BRUTE, -3.7f, 3.0f, 50_000f, .06f, .62f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.BRUTE, 3.7f, -3.0f, 50_000f, .06f, .62f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.RANGED, 6.2f, 2.0f, 50_000f, .05f, .46f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.RANGED, -6.2f, -2.0f, 50_000f, .05f, .46f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.ELITE, 2.1f, 5.0f, 50_000f, .07f, .54f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.ELITE, -2.1f, -5.0f, 50_000f, .07f, .54f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.SHIELDED, 7.0f, -0.9f, 50_000f, .05f, .56f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.SHIELDED, -7.0f, 0.9f, 50_000f, .05f, .56f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.REGENERATOR, 5.5f, -4.8f, 50_000f, .05f, .54f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.REGENERATOR, -5.5f, 4.8f, 50_000f, .05f, .54f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.PHANTOM, 6.6f, 4.3f, 50_000f, .05f, .50f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.PHANTOM, -6.6f, -4.3f, 50_000f, .05f, .50f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.BOSS, 0.9f, 6.7f, 500_000f, .015f, .78f, 0f, 1));
enemies.add(new Enemy(Enemy.Type.BOSS, -0.9f, -6.7f, 500_000f, .015f, .78f, 0f, 1));
⋮----
assertTrue("visual probe failed to inject authored enemy crowd", enemies.size >= before + 20);
⋮----
throw new AssertionError("unable to access GameScreen enemy collection for visual QA", exception);
⋮----
private static void capture(String name) throws Exception {
File root = new File(InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null), "qa");
assertTrue("unable to create gameplay QA output directory", root.isDirectory() || root.mkdirs());
File output = new File(root, name);
⋮----
Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
assertNotNull("Android UiAutomation did not return a screenshot", bitmap);
try (FileOutputStream stream = new FileOutputStream(output, false)) {
assertTrue("unable to encode gameplay QA screenshot", bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
⋮----
bitmap.recycle();
⋮----
size = output.length();
⋮----
// Android Emulator occasionally exposes a stale/broken color buffer for a single frame.
// Retry the capture, but keep the exact same semantic size gate for the final artifact.
if (attempt < 3) Thread.sleep(300L);
⋮----
assertTrue("gameplay QA screenshot is unexpectedly small after retries: " + size, size > 10_000L);
⋮----
private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
⋮----
scenario.onActivity(reference::set);
AndroidLauncher activity = reference.get();
assertNotNull("Android launcher was not available to visual probe", activity);
⋮----
private static DeadlineZeroGame game(AndroidLauncher activity) {
Object listener = activity.getApplicationListener();
assertTrue("Android launcher is not hosting DeadlineZeroGame", listener instanceof DeadlineZeroGame);
⋮----
private static void runOnGameThread(AndroidLauncher activity, Runnable action) throws Exception {
CountDownLatch done = new CountDownLatch(1);
⋮----
activity.postRunnable(() -> {
try { action.run(); } catch (Throwable throwable) { failure.set(throwable); } finally { done.countDown(); }
⋮----
assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
if (failure.get() != null) throw new AssertionError("Android visual probe failed on libGDX game thread", failure.get());
```

## File: src/androidTest/java/com/deadlinezero/game/android/AndroidLauncherSmokeTest.java
```java
public final class AndroidLauncherSmokeTest {
⋮----
public void launcherCreatesRealLibgdxSurfaceAndStaysAlive() {
try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
assertAttachedAndAlive(scenario);
⋮----
public void launcherSurvivesBackgroundForegroundAndActivityRecreation() {
⋮----
scenario.moveToState(Lifecycle.State.CREATED);
scenario.moveToState(Lifecycle.State.RESUMED);
⋮----
scenario.recreate();
⋮----
private static void assertAttachedAndAlive(ActivityScenario<AndroidLauncher> scenario) {
scenario.onActivity(activity -> {
assertFalse("Android launcher finished during runtime smoke", activity.isFinishing());
View content = activity.findViewById(android.R.id.content);
assertTrue("Android launcher content view is missing", content != null);
assertTrue("Android launcher window has no attached content", content.isAttachedToWindow());
```

## File: src/androidTest/java/com/deadlinezero/game/android/AndroidPerformanceProbeTest.java
```java
/**
 * Emulator-safe runtime performance probe. It validates telemetry integrity under deterministic
 * gameplay load without asserting hardware-specific 60/90/120 FPS throughput.
 */
⋮----
public final class AndroidPerformanceProbeTest {
⋮----
public void recordsLoadedGameplayPerformanceTelemetry() throws Exception {
try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
AndroidLauncher activity = activity(scenario);
runOnGameThread(activity, () -> {
DeadlineZeroGame game = game(activity);
game.startRun();
game.startRunWithContract(RunModifierContext.offers()[0]);
assertTrue("expected GameScreen for performance probe", game.getScreen() instanceof GameScreen);
injectLoad((GameScreen) game.getScreen());
⋮----
Thread.sleep(2600L);
⋮----
GameScreen screen = (GameScreen) game(activity).getScreen();
snapshotRef.set(screen.performanceSnapshot());
targetRef.set(screen.effectiveFrameRateTarget());
thermalRef.set(screen.thermalLevel().name());
fxQualityRef.set(screen.effectiveFxQuality());
enemyCountRef.set(screen.activeEnemyCount());
projectileCountRef.set(screen.activeProjectileCount());
activeSpatialBucketsRef.set(screen.activeSpatialBucketCount());
retainedSpatialBucketsRef.set(screen.retainedSpatialBucketCount());
⋮----
PerformanceTelemetry.Snapshot snapshot = snapshotRef.get();
assertNotNull("performance snapshot missing", snapshot);
assertNotNull("effective FPS target missing", targetRef.get());
assertTrue("performance probe did not collect enough frames", snapshot.averageFps() > 5f);
assertTrue("p95 frame time must be finite and positive",
Float.isFinite(snapshot.p95FrameMs()) && snapshot.p95FrameMs() > 0f && snapshot.p95FrameMs() <= 250f);
assertTrue("p99 must not be below p95", snapshot.p99FrameMs() >= snapshot.p95FrameMs());
assertTrue("jank ratio out of bounds", snapshot.jankRatio() >= 0f && snapshot.jankRatio() <= 1f);
assertTrue("unexpected effective FPS tier",
targetRef.get() == 60 || targetRef.get() == 90 || targetRef.get() == 120);
⋮----
writeBenchmarkJson(
⋮----
targetRef.get(),
⋮----
thermalRef.get(),
fxQualityRef.get(),
enemyCountRef.get(),
projectileCountRef.get(),
activeSpatialBucketsRef.get(),
retainedSpatialBucketsRef.get()
⋮----
public void recordsHordeProjectileStressTelemetry() throws Exception {
⋮----
assertTrue("expected GameScreen for stress probe", game.getScreen() instanceof GameScreen);
injectStressLoad((GameScreen) game.getScreen());
⋮----
Thread.sleep(3200L);
⋮----
assertNotNull("stress performance snapshot missing", snapshot);
assertTrue("stress probe did not collect enough frames", snapshot.averageFps() > 5f);
assertTrue("stress p95 frame time must be finite and positive",
⋮----
assertTrue("stress p99 must not be below p95", snapshot.p99FrameMs() >= snapshot.p95FrameMs());
assertTrue("stress jank ratio out of bounds", snapshot.jankRatio() >= 0f && snapshot.jankRatio() <= 1f);
assertTrue("stress workload lost too many enemies", enemyCountRef.get() >= 140);
assertTrue("stress workload lost too many projectiles", projectileCountRef.get() >= 140);
⋮----
private static void writeBenchmarkJson(
⋮----
File root = new File(
androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
.getTargetContext().getExternalFilesDir(null),
⋮----
assertTrue("unable to create performance QA output directory", root.isDirectory() || root.mkdirs());
File output = new File(root, filename);
try (FileWriter writer = new FileWriter(output, false)) {
writer.write("{\n");
writer.write("  \"scenario\": \"" + scenario + "\",\n");
writer.write("  \"targetFps\": " + target + ",\n");
writer.write("  \"averageFps\": " + snapshot.averageFps() + ",\n");
writer.write("  \"p95FrameMs\": " + snapshot.p95FrameMs() + ",\n");
writer.write("  \"p99FrameMs\": " + snapshot.p99FrameMs() + ",\n");
writer.write("  \"jankRatio\": " + snapshot.jankRatio() + ",\n");
writer.write("  \"stable\": " + snapshot.stable() + ",\n");
writer.write("  \"thermalLevel\": \"" + thermalLevel + "\",\n");
writer.write("  \"effectiveFxQuality\": " + fxQuality + ",\n");
writer.write("  \"activeEnemies\": " + activeEnemies + ",\n");
writer.write("  \"activeProjectiles\": " + activeProjectiles + ",\n");
writer.write("  \"activeSpatialBuckets\": " + activeSpatialBuckets + ",\n");
writer.write("  \"retainedSpatialBuckets\": " + retainedSpatialBuckets + "\n");
writer.write("}\n");
⋮----
assertTrue("performance benchmark JSON was not written", output.isFile() && output.length() > 40L);
⋮----
private static void injectLoad(GameScreen screen) {
⋮----
Field field = GameScreen.class.getDeclaredField("enemies");
field.setAccessible(true);
Array<Enemy> enemies = (Array<Enemy>) field.get(screen);
⋮----
enemies.add(new Enemy(
⋮----
(float)Math.cos(angle) * radius,
(float)Math.sin(angle) * radius,
⋮----
assertTrue("performance probe failed to inject deterministic load", enemies.size >= 40);
⋮----
throw new AssertionError("unable to inject deterministic performance load", exception);
⋮----
private static void injectStressLoad(GameScreen screen) {
⋮----
Field enemiesField = GameScreen.class.getDeclaredField("enemies");
enemiesField.setAccessible(true);
Array<Enemy> enemies = (Array<Enemy>) enemiesField.get(screen);
⋮----
Field poolsField = GameScreen.class.getDeclaredField("pools");
poolsField.setAccessible(true);
Pools pools = (Pools) poolsField.get(screen);
int projectileTarget = Math.min(180, pools.projectiles.size);
⋮----
Projectile projectile = pools.projectiles.get(i);
⋮----
projectile.position.set((float)Math.cos(angle) * radius, (float)Math.sin(angle) * radius);
projectile.velocity.setZero();
⋮----
assertTrue("stress probe failed to inject horde", enemies.size >= 160);
assertTrue("stress probe projectile pool too small", projectileTarget >= 140);
⋮----
throw new AssertionError("unable to inject deterministic stress load", exception);
⋮----
private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
⋮----
scenario.onActivity(reference::set);
AndroidLauncher activity = reference.get();
assertNotNull("Android launcher unavailable", activity);
⋮----
private static DeadlineZeroGame game(AndroidLauncher activity) {
Object listener = activity.getApplicationListener();
assertTrue("Android launcher is not hosting DeadlineZeroGame", listener instanceof DeadlineZeroGame);
⋮----
private static void runOnGameThread(AndroidLauncher activity, Runnable action) throws Exception {
CountDownLatch done = new CountDownLatch(1);
⋮----
activity.postRunnable(() -> {
try { action.run(); } catch (Throwable throwable) { failure.set(throwable); } finally { done.countDown(); }
⋮----
assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
if (failure.get() != null) throw new AssertionError("Android performance probe failed on game thread", failure.get());
```

## File: src/androidTest/java/com/deadlinezero/game/android/AndroidProcessPersistenceTest.java
```java
public final class AndroidProcessPersistenceTest {
⋮----
public void seedProfileBeforeExternalProcessDeath() throws Exception {
Assume.assumeTrue("seed".equals(phase()));
try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
AndroidLauncher activity = activity(scenario);
runOnGameThread(activity, () -> {
DeadlineZeroGame game = game(activity);
⋮----
assertTrue("Persistence probe receipt was already present", game.profile.recordDeliveredPurchaseReceipt(EXPECTED_RECEIPT));
game.saveProfile();
⋮----
public void verifyProfileAfterExternalProcessRestart() throws Exception {
Assume.assumeTrue("verify".equals(phase()));
⋮----
assertEquals("totalRuns did not survive process death", EXPECTED_RUNS, game.profile.totalRuns);
assertEquals("totalKills did not survive process death", EXPECTED_KILLS, game.profile.totalKills);
assertEquals("highestStage did not survive process death", EXPECTED_HIGHEST_STAGE, game.profile.highestStage);
assertEquals("selectedStage did not survive process death", EXPECTED_SELECTED_STAGE, game.profile.selectedStage);
assertTrue("purchase receipt did not survive process death", game.profile.hasDeliveredPurchaseReceipt(EXPECTED_RECEIPT));
⋮----
private static String phase() {
return InstrumentationRegistry.getArguments().getString("persistencePhase", "");
⋮----
private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
⋮----
scenario.onActivity(reference::set);
AndroidLauncher activity = reference.get();
assertNotNull("Android launcher was not available to persistence probe", activity);
⋮----
private static DeadlineZeroGame game(AndroidLauncher activity) {
Object listener = activity.getApplicationListener();
assertTrue("Android launcher is not hosting DeadlineZeroGame", listener instanceof DeadlineZeroGame);
⋮----
assertNotNull("Deadline Zero profile was not initialized", game.profile);
⋮----
private static void runOnGameThread(AndroidLauncher activity, Runnable action) throws Exception {
CountDownLatch done = new CountDownLatch(1);
⋮----
activity.postRunnable(() -> {
⋮----
action.run();
⋮----
failure.set(throwable);
⋮----
done.countDown();
⋮----
assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
if (failure.get() != null) throw new AssertionError("Persistence probe failed on libGDX game thread", failure.get());
```

## File: src/androidTest/java/com/deadlinezero/game/android/AndroidResponsiveUiVisualProbeTest.java
```java
/**
 * Phone-aspect visual gate for the responsive UI system.
 *
 * The dedicated workflow opts into this probe after forcing the emulator to the physical-device
 * aspect that exposed the original mixed pixel/logical-coordinate bug. Ordinary connected-device
 * suites skip it explicitly so a conventional 16:9 emulator cannot fail this dedicated visual gate.
 */
⋮----
public final class AndroidResponsiveUiVisualProbeTest {
⋮----
public void capturesWidePhoneMetaAndCombatScreens() throws Exception {
assumeTrue("wide-phone visual probe only runs in the dedicated workflow", dedicatedWideProbe());
try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
AndroidLauncher activity = activity(scenario);
Thread.sleep(650L);
assertWidePhoneAspect();
⋮----
runOnGameThread(activity, () -> {
DeadlineZeroGame game = game(activity);
game.profile.accountLevel = Math.max(10, game.profile.accountLevel);
game.profile.highestStage = Math.max(8, game.profile.highestStage);
game.profile.selectedStage = Math.min(5, game.profile.highestStage);
game.profile.survivors.refreshUnlocks(game.profile);
game.showMenu();
assertTrue("expected MenuScreen for wide-phone visual probe", game.getScreen() instanceof MenuScreen);
⋮----
settleAndCapture("responsive-1536x691-home.png");
⋮----
game.showSurvivors();
assertTrue("expected SurvivorScreen for wide-phone visual probe",
game.getScreen() instanceof SurvivorScreen);
⋮----
settleAndCapture("responsive-1536x691-survivors.png");
⋮----
game.showArsenal();
assertTrue("expected ArsenalScreen for wide-phone visual probe",
game.getScreen() instanceof ArsenalScreen);
⋮----
settleAndCapture("responsive-1536x691-arsenal.png");
⋮----
game.showSettings();
assertTrue("expected SettingsScreen for wide-phone visual probe",
game.getScreen() instanceof SettingsScreen);
⋮----
settleAndCapture("responsive-1536x691-settings.png");
⋮----
game.startRun();
assertTrue("expected RunContractScreen for wide-phone visual probe",
game.getScreen() instanceof RunContractScreen);
⋮----
settleAndCapture("responsive-1536x691-contract.png");
⋮----
game.startRunWithContract(RunModifierContext.offers()[0]);
assertTrue("expected GameScreen for wide-phone visual probe", game.getScreen() instanceof GameScreen);
⋮----
settleAndCapture("responsive-1536x691-combat.png");
⋮----
private static boolean dedicatedWideProbe() {
Bundle arguments = InstrumentationRegistry.getArguments();
return "true".equalsIgnoreCase(arguments.getString(WIDE_PROBE_ARGUMENT, "false"));
⋮----
private static void assertWidePhoneAspect() {
Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
assertNotNull("Android UiAutomation did not return an aspect probe screenshot", bitmap);
⋮----
float aspect = bitmap.getWidth() / (float) Math.max(1, bitmap.getHeight());
assertTrue("dedicated wide-phone probe did not reach aspect > " + MIN_WIDE_ASPECT + ", was " + aspect,
⋮----
bitmap.recycle();
⋮----
private static void settleAndCapture(String name) throws Exception {
⋮----
captureWide(name);
⋮----
private static void captureWide(String name) throws Exception {
File root = new File(
InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null),
⋮----
assertTrue("unable to create responsive QA output directory", root.isDirectory() || root.mkdirs());
File output = new File(root, name);
⋮----
assertNotNull("Android UiAutomation did not return a responsive screenshot", bitmap);
⋮----
assertTrue("responsive visual probe did not run at wide-phone aspect: " + aspect,
⋮----
try (FileOutputStream stream = new FileOutputStream(output, false)) {
assertTrue("unable to encode responsive QA screenshot",
bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
⋮----
size = output.length();
⋮----
if (attempt < 3) Thread.sleep(300L);
⋮----
assertTrue("responsive QA screenshot is unexpectedly small after retries: " + size, size > 10_000L);
⋮----
private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
⋮----
scenario.onActivity(reference::set);
AndroidLauncher activity = reference.get();
assertNotNull("Android launcher was not available to responsive visual probe", activity);
⋮----
private static DeadlineZeroGame game(AndroidLauncher activity) {
Object listener = activity.getApplicationListener();
assertTrue("Android launcher is not hosting DeadlineZeroGame", listener instanceof DeadlineZeroGame);
⋮----
private static void runOnGameThread(AndroidLauncher activity, Runnable action) throws Exception {
CountDownLatch done = new CountDownLatch(1);
⋮----
activity.postRunnable(() -> {
⋮----
action.run();
⋮----
failure.set(throwable);
⋮----
done.countDown();
⋮----
assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
if (failure.get() != null) {
throw new AssertionError("Responsive Android visual probe failed on libGDX game thread", failure.get());
```

## File: src/androidTest/java/com/deadlinezero/game/android/AndroidVerticalSliceTest.java
```java
public final class AndroidVerticalSliceTest {
⋮----
public void realAndroidRuntimeCompletesM0VerticalSliceAndPersistsSettlement() throws Exception {
try (ActivityScenario<AndroidLauncher> scenario = ActivityScenario.launch(AndroidLauncher.class)) {
AndroidLauncher activity = activity(scenario);
⋮----
runOnGameThread(activity, () -> {
DeadlineZeroGame game = game(activity);
assertTrue("expected menu after Android launcher create", game.getScreen() instanceof MenuScreen);
runsBefore.set(game.profile.totalRuns);
⋮----
allowFrames();
⋮----
game.startRun();
assertTrue("expected contract screen after startRun", game.getScreen() instanceof RunContractScreen);
⋮----
game.startRunWithContract(RunModifierContext.offers()[0]);
assertTrue("expected GameScreen after contract selection", game.getScreen() instanceof GameScreen);
⋮----
game.finishRun(12, 30f, false, 1);
assertTrue("expected RunResultScreen after settlement", game.getScreen() instanceof RunResultScreen);
assertEquals("settlement must increment totalRuns exactly once", runsBefore.get().intValue() + 1, game.profile.totalRuns);
assertEquals("settlement must survive immediate Android profile reload", game.profile.totalRuns, ProfileStore.load().totalRuns);
⋮----
game.showMenu();
assertTrue("expected menu after leaving result screen", game.getScreen() instanceof MenuScreen);
assertEquals("returning to menu must not duplicate settlement", runsBefore.get().intValue() + 1, game.profile.totalRuns);
⋮----
public void interruptedAndroidRunReturnsToMenuWithoutSettlementOrRewards() throws Exception {
⋮----
assertTrue("expected menu before interrupted run", game.getScreen() instanceof MenuScreen);
⋮----
killsBefore.set(game.profile.totalKills);
creditsBefore.set(game.profile.currency(PlayerProfile.Currency.CREDITS));
gemsBefore.set(game.profile.currency(PlayerProfile.Currency.GEMS));
⋮----
assertTrue("expected GameScreen before interruption", game.getScreen() instanceof GameScreen);
⋮----
assertTrue("interrupted run must return to menu", game.getScreen() instanceof MenuScreen);
assertEquals("interrupted run must not increment totalRuns", runsBefore.get().intValue(), game.profile.totalRuns);
assertEquals("interrupted run must not add settled kills", killsBefore.get().longValue(), game.profile.totalKills);
assertEquals("interrupted run must not grant credits", creditsBefore.get().longValue(), game.profile.currency(PlayerProfile.Currency.CREDITS));
assertEquals("interrupted run must not grant gems", gemsBefore.get().longValue(), game.profile.currency(PlayerProfile.Currency.GEMS));
game.saveProfile();
⋮----
PlayerProfile reloaded = ProfileStore.load();
assertEquals("interrupted run count changed after reload", runsBefore.get().intValue(), reloaded.totalRuns);
assertEquals("interrupted run kills changed after reload", killsBefore.get().longValue(), reloaded.totalKills);
assertEquals("interrupted run credits changed after reload", creditsBefore.get().longValue(), reloaded.currency(PlayerProfile.Currency.CREDITS));
assertEquals("interrupted run gems changed after reload", gemsBefore.get().longValue(), reloaded.currency(PlayerProfile.Currency.GEMS));
⋮----
private static AndroidLauncher activity(ActivityScenario<AndroidLauncher> scenario) {
⋮----
scenario.onActivity(reference::set);
AndroidLauncher activity = reference.get();
assertNotNull("Android launcher was not available to vertical-slice probe", activity);
⋮----
private static DeadlineZeroGame game(AndroidLauncher activity) {
Object listener = activity.getApplicationListener();
assertTrue("Android launcher is not hosting DeadlineZeroGame", listener instanceof DeadlineZeroGame);
⋮----
assertNotNull("Deadline Zero profile was not initialized", game.profile);
⋮----
private static void runOnGameThread(AndroidLauncher activity, Runnable action) throws Exception {
CountDownLatch done = new CountDownLatch(1);
⋮----
activity.postRunnable(() -> {
⋮----
action.run();
⋮----
failure.set(throwable);
⋮----
done.countDown();
⋮----
assertTrue("Timed out waiting for libGDX game thread", done.await(10, TimeUnit.SECONDS));
if (failure.get() != null) throw new AssertionError("Android vertical-slice probe failed on libGDX game thread", failure.get());
⋮----
private static void allowFrames() throws InterruptedException {
Thread.sleep(250L);
```

## File: src/main/java/com/deadlinezero/game/android/AndroidAdsService.java
```java
public final class AndroidAdsService implements AdsService {
⋮----
@Override public void preload() {
load();
⋮----
@Override public void setFullscreenListener(FullscreenListener listener) {
⋮----
private boolean canRequestAds() {
return consent == null || consent.canRequestAds();
⋮----
private void load() {
if (!canRequestAds() || loading || ad != null) return;
⋮----
activity.runOnUiThread(() -> RewardedAd.load(
⋮----
new AdRequest.Builder().build(),
new RewardedAdLoadCallback() {
@Override public void onAdLoaded(RewardedAd loaded) {
⋮----
@Override public void onAdFailedToLoad(LoadAdError error) {
⋮----
@Override public boolean isRewardedReady() {
return canRequestAds() && ad != null;
⋮----
@Override public void showRewarded(Reward reward, Runnable earned, Runnable unavailable) {
activity.runOnUiThread(() -> {
if (!canRequestAds() || ad == null) {
⋮----
dispatchToGameThread(unavailable);
⋮----
AtomicBoolean completed = new AtomicBoolean(false);
AtomicBoolean presentationClosed = new AtomicBoolean(false);
dispatchFullscreenOpening();
⋮----
showing.setFullScreenContentCallback(new FullScreenContentCallback() {
@Override public void onAdDismissedFullScreenContent() {
⋮----
dispatchFullscreenClosedOnce(presentationClosed);
if (completed.compareAndSet(false, true)) dispatchToGameThread(unavailable);
⋮----
@Override public void onAdFailedToShowFullScreenContent(AdError error) {
⋮----
showing.show(activity, item -> {
if (completed.compareAndSet(false, true)) dispatchToGameThread(earned);
⋮----
private void dispatchFullscreenOpening() {
⋮----
if (listener != null) dispatchToGameThread(listener::onOpening);
⋮----
private void dispatchFullscreenClosedOnce(AtomicBoolean closed) {
if (!closed.compareAndSet(false, true)) return;
⋮----
if (listener != null) dispatchToGameThread(listener::onClosed);
⋮----
private void dispatchToGameThread(Runnable callback) {
⋮----
Gdx.app.postRunnable(callback);
⋮----
activity.runOnUiThread(callback);
```

## File: src/main/java/com/deadlinezero/game/android/AndroidBillingService.java
```java
/** Client-side Play Billing bridge with crash-safe consumable delivery and explicit pending-state handling. */
public final class AndroidBillingService implements BillingService, PurchasesUpdatedListener {
⋮----
private final Set<String> owned = ConcurrentHashMap.newKeySet();
private final SingleFlightGate purchaseGate = new SingleFlightGate();
⋮----
@Override public void initialize() {
⋮----
client = BillingClient.newBuilder(activity)
.setListener(this)
.enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
.enableAutoServiceReconnection()
.build();
client.startConnection(new BillingClientStateListener() {
@Override public void onBillingSetupFinished(BillingResult result) {
if (result.getResponseCode() == BillingClient.BillingResponseCode.OK) {
⋮----
restore();
⋮----
if (deferred != null) restoreConsumables(deferred);
⋮----
@Override public void onBillingServiceDisconnected() {
⋮----
@Override public State state() { return billingState; }
@Override public boolean authoritativeEntitlements() { return entitlementSnapshotAuthoritative; }
@Override public String activeProductId() { return pendingProductId == null ? "" : pendingProductId; }
@Override public boolean owns(String id) { return BillingService.isKnownProduct(id) && owned.contains(id); }
⋮----
@Override public void restore() {
if (client == null || !client.isReady()) return;
⋮----
client.queryPurchasesAsync(
QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build(),
⋮----
if (result.getResponseCode() != BillingClient.BillingResponseCode.OK) return;
owned.clear();
⋮----
if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
String knownPending = firstKnownProduct(purchase);
⋮----
if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED) continue;
if (!containsConsumable(purchase)) processPurchase(purchase, false);
⋮----
else if (!purchaseGate.active()) {
⋮----
@Override public void restoreConsumables(PurchaseReceiptListener listener) {
⋮----
if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED || !containsConsumable(purchase)) continue;
for (String id : purchase.getProducts()) {
if (BillingService.isConsumable(id)) {
listener.onPurchased(new PurchaseReceipt(id, purchase.getPurchaseToken()));
⋮----
@Override public void purchase(String productId, Runnable onSuccess, Runnable onFailure) {
beginPurchase(productId, null, onSuccess, onFailure, false);
⋮----
@Override public void purchaseWithReceipt(String productId, PurchaseReceiptListener onSuccess, Runnable onFailure) {
beginPurchase(productId, onSuccess, null, onFailure, true);
⋮----
private void beginPurchase(String productId, PurchaseReceiptListener receiptCallback, Runnable legacySuccess,
⋮----
if (!BillingService.isKnownProduct(productId) || client == null || !client.isReady()
⋮----
if (onFailure != null) onFailure.run();
⋮----
if (!purchaseGate.tryBegin()) {
⋮----
QueryProductDetailsParams.Product product = QueryProductDetailsParams.Product.newBuilder()
.setProductId(productId)
.setProductType(BillingClient.ProductType.INAPP)
⋮----
client.queryProductDetailsAsync(
QueryProductDetailsParams.newBuilder().setProductList(Collections.singletonList(product)).build(),
⋮----
if (result.getResponseCode() != BillingClient.BillingResponseCode.OK || detailsResult.getProductDetailsList().isEmpty()) {
failPending();
⋮----
ProductDetails details = detailsResult.getProductDetailsList().get(0);
BillingFlowParams.ProductDetailsParams params = BillingFlowParams.ProductDetailsParams.newBuilder()
.setProductDetails(details)
⋮----
BillingResult launch = client.launchBillingFlow(activity,
BillingFlowParams.newBuilder().setProductDetailsParamsList(Collections.singletonList(params)).build());
if (launch.getResponseCode() != BillingClient.BillingResponseCode.OK) failPending();
⋮----
@Override public void onPurchasesUpdated(BillingResult result, List<Purchase> purchases) {
if (result.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
⋮----
if (pendingProductId != null && purchase.getProducts().contains(pendingProductId)) {
⋮----
handled |= processPurchase(purchase, true);
⋮----
if (!handled) failPending();
⋮----
private boolean processPurchase(Purchase purchase, boolean notifyPending) {
if (purchase.getPurchaseState() != Purchase.PurchaseState.PURCHASED) return false;
if (notifyPending && pendingProductId != null && !purchase.getProducts().contains(pendingProductId)) return false;
⋮----
String consumableId = firstConsumable(purchase);
⋮----
PurchaseReceipt receipt = new PurchaseReceipt(consumableId, purchase.getPurchaseToken());
⋮----
clearPending();
if (callback != null) callback.onPurchased(receipt);
} else if (!purchaseGate.active()) {
⋮----
finishConsumable(purchase.getPurchaseToken(), this::succeedPending, this::failPending);
⋮----
if (!containsDurable(purchase)) return false;
if (purchase.isAcknowledged()) {
grantDurableProducts(purchase);
if (notifyPending) succeedPending();
⋮----
AcknowledgePurchaseParams params = AcknowledgePurchaseParams.newBuilder()
.setPurchaseToken(purchase.getPurchaseToken()).build();
client.acknowledgePurchase(params, result -> {
⋮----
@Override public void finishConsumable(String receiptId, Runnable onSuccess, Runnable onFailure) {
if (receiptId == null || receiptId.isBlank() || client == null || !client.isReady()) {
⋮----
ConsumeParams params = ConsumeParams.newBuilder().setPurchaseToken(receiptId).build();
client.consumeAsync(params, (result, token) -> {
⋮----
if (onSuccess != null) onSuccess.run();
⋮----
onFailure.run();
⋮----
private boolean containsConsumable(Purchase purchase) { return firstConsumable(purchase) != null; }
⋮----
private String firstConsumable(Purchase purchase) {
for (String id : purchase.getProducts()) if (BillingService.isConsumable(id)) return id;
⋮----
private boolean containsDurable(Purchase purchase) {
for (String id : purchase.getProducts()) if (BillingService.isDurable(id)) return true;
⋮----
private String firstKnownProduct(Purchase purchase) {
for (String id : purchase.getProducts()) if (BillingService.isKnownProduct(id)) return id;
⋮----
private void grantDurableProducts(Purchase purchase) {
⋮----
if (BillingService.isDurable(id)) owned.add(id);
⋮----
private void succeedPending() {
⋮----
if (callback != null) callback.run();
⋮----
private void failPending() {
⋮----
private void clearPending() {
⋮----
purchaseGate.end();
billingState = client != null && client.isReady() ? State.READY : State.CONNECTING;
```

## File: src/main/java/com/deadlinezero/game/android/AndroidConsentManager.java
```java
/** Handles UMP consent refresh at launch and exposes a single ad-request gate. */
public final class AndroidConsentManager {
⋮----
this.consentInformation = UserMessagingPlatform.getConsentInformation(activity);
⋮----
public void gatherConsent(Runnable onComplete) {
AtomicBoolean completed = new AtomicBoolean(false);
ConsentRequestParameters params = new ConsentRequestParameters.Builder().build();
consentInformation.requestConsentInfoUpdate(
⋮----
() -> UserMessagingPlatform.loadAndShowConsentFormIfRequired(
⋮----
formError -> completeOnce(completed, onComplete)
⋮----
requestError -> completeOnce(completed, onComplete)
⋮----
public boolean canRequestAds() {
return consentInformation.canRequestAds();
⋮----
public boolean privacyOptionsRequired() {
return consentInformation.getPrivacyOptionsRequirementStatus()
⋮----
public void showPrivacyOptions(Runnable onDismissed) {
UserMessagingPlatform.showPrivacyOptionsForm(activity, formError -> {
if (onDismissed != null) onDismissed.run();
⋮----
private static void completeOnce(AtomicBoolean completed, Runnable onComplete) {
if (onComplete != null && completed.compareAndSet(false, true)) onComplete.run();
```

## File: src/main/java/com/deadlinezero/game/android/AndroidHapticsService.java
```java
/** Android haptic implementation with short event-specific pulses. */
public final class AndroidHapticsService implements HapticsService {
⋮----
@Override public void dash() {
pulse(18L, 72);
⋮----
@Override public void damage() {
pulse(32L, 150);
⋮----
@Override public void bossKill() {
pulse(55L, 210);
⋮----
private void pulse(long durationMs, int amplitude) {
⋮----
activity.runOnUiThread(() -> {
Vibrator vibrator = vibrator();
if (vibrator == null || !vibrator.hasVibrator()) return;
vibrator.vibrate(VibrationEffect.createOneShot(durationMs, amplitude));
⋮----
private Vibrator vibrator() {
⋮----
VibratorManager manager = (VibratorManager) activity.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
return manager == null ? null : manager.getDefaultVibrator();
⋮----
return (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
```

## File: src/main/java/com/deadlinezero/game/android/AndroidLauncher.java
```java
public final class AndroidLauncher extends AndroidApplication {
@Override protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
⋮----
AndroidConsentManager consent = new AndroidConsentManager(this);
AndroidAdsService ads = new AndroidAdsService(this, consent);
⋮----
CloudSaveAdapter cloudSave = CloudSaveAdapter.unavailable();
⋮----
PlayGamesSdk.initialize(this);
cloudSave = new AndroidPlayGamesCloudSaveAdapter(this);
⋮----
AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
⋮----
initialize(
new DeadlineZeroGame(new GameServices(
⋮----
new AndroidBillingService(this),
new AndroidPrivacyService(this, consent),
new AndroidShareService(this),
new AndroidHapticsService(this),
⋮----
new AndroidThermalService(this),
com.deadlinezero.game.services.OfferConfigService.safeLocal(),
new AndroidReviewService(this)
⋮----
consent.gatherConsent(() -> {
if (!consent.canRequestAds()) return;
MobileAds.initialize(this, status -> ads.preload());
```

## File: src/main/java/com/deadlinezero/game/android/AndroidPlayGamesCloudSaveAdapter.java
```java
/** Google Play Games v2 Saved Games backend. Calls must run off the Android main thread. */
public final class AndroidPlayGamesCloudSaveAdapter implements CloudSaveAdapter {
⋮----
if (activity == null) throw new IllegalArgumentException("activity");
⋮----
@Override public boolean supportsAuthentication() { return true; }
⋮----
@Override public void authenticate() throws Exception {
requireWorkerThread();
var result = Tasks.await(
PlayGames.getGamesSignInClient(activity).signIn(),
⋮----
if (result == null || !result.isAuthenticated()) throw new CloudAuthenticationRequiredException();
⋮----
@Override public RemoteBackup read() throws Exception {
⋮----
ensureAuthenticated();
⋮----
Snapshot snapshot = open(false);
⋮----
RemoteBackup backup = toRemoteBackup(snapshot);
PlayGames.getSnapshotsClient(activity).discardAndClose(snapshot);
return backup.payload().isBlank() ? null : backup;
⋮----
@Override public void write(String payload) throws Exception {
⋮----
if (payload == null || payload.isBlank()) throw new IllegalArgumentException("payload");
⋮----
Snapshot snapshot = open(true);
if (snapshot == null) throw new IllegalStateException("Unable to open Play Games snapshot");
commitPayload(snapshot, payload);
⋮----
@Override public void writeIfUnchanged(String payload, RemoteBackup expectedRemote) throws Exception {
⋮----
RemoteBackup current = toRemoteBackup(snapshot);
⋮----
? current.payload().isBlank()
: current.modifiedAtEpochMillis() == expectedRemote.modifiedAtEpochMillis()
&& current.payload().equals(expectedRemote.payload());
⋮----
throw new CloudRemoteChangedException();
⋮----
private void commitPayload(Snapshot snapshot, String payload) throws Exception {
byte[] bytes = payload.getBytes(StandardCharsets.UTF_8);
if (!snapshot.getSnapshotContents().writeBytes(bytes)) {
⋮----
throw new IllegalStateException("Unable to write Play Games snapshot contents");
⋮----
.setDescription("DEADLINE ZERO profile backup")
.build();
Tasks.await(
PlayGames.getSnapshotsClient(activity).commitAndClose(snapshot, metadata),
⋮----
@Override public ProviderConflict pendingConflict() {
⋮----
@Override public void resolvePendingConflict(ConflictChoice choice) throws Exception {
⋮----
if (choice == null) throw new IllegalArgumentException("choice");
⋮----
throw new IllegalStateException("No Play Games snapshot conflict is pending");
⋮----
? nativeConflict.getSnapshot()
: nativeConflict.getConflictingSnapshot();
⋮----
SnapshotsClient.DataOrConflict<Snapshot> result = Tasks.await(
PlayGames.getSnapshotsClient(activity).resolveConflict(nativeConflict.getConflictId(), chosen),
⋮----
if (result.isConflict()) {
captureConflict(result.getConflict());
throw new CloudProviderConflictException(pendingPublicConflict);
⋮----
Snapshot resolved = result.getData();
clearConflict();
if (resolved != null) PlayGames.getSnapshotsClient(activity).discardAndClose(resolved);
⋮----
private Snapshot open(boolean createIfMissing) throws Exception {
⋮----
PlayGames.getSnapshotsClient(activity).open(
⋮----
return result.getData();
⋮----
Throwable cause = e.getCause();
⋮----
&& api.getStatusCode() == GamesClientStatusCodes.SNAPSHOT_NOT_FOUND) {
⋮----
private void captureConflict(SnapshotsClient.SnapshotConflict conflict) throws Exception {
if (conflict == null) throw new IllegalStateException("Play Games returned an empty conflict");
⋮----
pendingPublicConflict = new ProviderConflict(
toRemoteBackup(conflict.getSnapshot()),
toRemoteBackup(conflict.getConflictingSnapshot())
⋮----
private void clearConflict() {
⋮----
private static RemoteBackup toRemoteBackup(Snapshot snapshot) throws Exception {
if (snapshot == null || snapshot.getSnapshotContents() == null) {
throw new IllegalStateException("Play Games conflict snapshot is missing contents");
⋮----
byte[] bytes = snapshot.getSnapshotContents().readFully();
String payload = new String(bytes, StandardCharsets.UTF_8);
long modifiedAt = snapshot.getMetadata().getLastModifiedTimestamp();
return new RemoteBackup(payload, modifiedAt);
⋮----
private void ensureAuthenticated() throws Exception {
⋮----
PlayGames.getGamesSignInClient(activity).isAuthenticated(),
⋮----
private static void requireWorkerThread() {
if (Looper.myLooper() == Looper.getMainLooper()) {
throw new IllegalStateException("Cloud save must not block the Android main thread");
```

## File: src/main/java/com/deadlinezero/game/android/AndroidPrivacyService.java
```java
/** Android bridge from shared privacy controls to Google UMP and the published privacy policy. */
public final class AndroidPrivacyService implements PrivacyService {
⋮----
@Override public boolean optionsRequired() {
return consent != null && consent.privacyOptionsRequired();
⋮----
@Override public void showOptions(Runnable onDismissed) {
⋮----
if (onDismissed != null) onDismissed.run();
⋮----
consent.showPrivacyOptions(onDismissed);
⋮----
@Override public boolean policyAvailable() {
⋮----
return activity != null && url != null && url.startsWith("https://") && url.length() > 8;
⋮----
@Override public void openPolicy() {
if (!policyAvailable()) return;
Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PRIVACY_POLICY_URL));
activity.startActivity(intent);
```

## File: src/main/java/com/deadlinezero/game/android/AndroidReviewService.java
```java
public final class AndroidReviewService implements ReviewService {
⋮----
this.manager = ReviewManagerFactory.create(activity);
⋮----
@Override public void requestReview() {
⋮----
manager.requestReviewFlow().addOnCompleteListener(request -> {
if (!request.isSuccessful()) {
⋮----
manager.launchReviewFlow(activity, request.getResult()).addOnCompleteListener(flow -> {
```

## File: src/main/java/com/deadlinezero/game/android/AndroidShareService.java
```java
/** Android ACTION_SEND bridge. Shares only text explicitly requested by the player. */
public final class AndroidShareService implements ShareService {
⋮----
@Override public boolean available() { return activity != null; }
⋮----
@Override public void shareText(String text) {
if (activity == null || text == null || text.isBlank()) return;
activity.runOnUiThread(() -> {
Intent send = new Intent(Intent.ACTION_SEND);
send.setType("text/plain");
send.putExtra(Intent.EXTRA_TEXT, text);
Intent chooser = Intent.createChooser(send, "Share Deadline: Zero run");
activity.startActivity(chooser);
```

## File: src/main/java/com/deadlinezero/game/android/AndroidThermalService.java
```java
/** Android thermal-status bridge. Polling is cheap and avoids lifecycle-sensitive listener bookkeeping. */
public final class AndroidThermalService implements ThermalService {
⋮----
: (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
⋮----
@Override public Level level() {
⋮----
return switch (powerManager.getCurrentThermalStatus()) {
```

## File: build.gradle
```
plugins { id 'com.android.application' }

def testAdmobAppId = 'ca-app-pub-3940256099942544~3347511713'
def testRewardedId = 'ca-app-pub-3940256099942544/5224354917'
def admobAppId = providers.gradleProperty('admobAppId').orElse(testAdmobAppId).get()
def admobRewardedId = providers.gradleProperty('admobRewardedId').orElse(testRewardedId).get()
def privacyPolicyUrl = providers.gradleProperty('privacyPolicyUrl').orElse('').get()
def playGamesAppId = providers.gradleProperty('playGamesAppId').orElse('0').get()
def allowTestAds = providers.gradleProperty('allowTestAds').map { it.toBoolean() }.orElse(false)
def allowPlaceholderAssets = providers.gradleProperty('allowPlaceholderAssets').map { it.toBoolean() }.orElse(false)
def appVersion = providers.gradleProperty('appVersion').get()
def appVersionCode = providers.gradleProperty('appVersionCode').get().toInteger()
def validAdmobAppId = { String value -> value ==~ /ca-app-pub-\d{16}~\d{10}/ }
def validRewardedId = { String value -> value ==~ /ca-app-pub-\d{16}\/\d{10}/ }
def validPlayVersionCode = { int value -> value >= 1 && value <= 2100000000 }
def validPlayGamesAppId = { String value -> value != null && value ==~ /[1-9][0-9]{4,}/ }
def validPrivacyPolicyUrl = { String value ->
    value != null && value ==~ /https:\/\/[A-Za-z0-9.-]+(?:\:[0-9]+)?(?:\/[^\s]*)?/
        && !value.toLowerCase(Locale.ROOT).contains('placeholder')
        && !value.toLowerCase(Locale.ROOT).contains('example.com')
}
def releaseValue = { String propertyName, String envName ->
    providers.gradleProperty(propertyName).orElse(providers.environmentVariable(envName)).getOrElse('')
}
def releaseStorePath = releaseValue('releaseStoreFile', 'DEADLINE_ZERO_KEYSTORE')
def releaseStorePassword = releaseValue('releaseStorePassword', 'DEADLINE_ZERO_STORE_PASSWORD')
def releaseKeyAlias = releaseValue('releaseKeyAlias', 'DEADLINE_ZERO_KEY_ALIAS')
def releaseKeyPassword = releaseValue('releaseKeyPassword', 'DEADLINE_ZERO_KEY_PASSWORD')
def releaseStoreExists = !releaseStorePath.isBlank() && file(releaseStorePath).isFile()
def hasReleaseSigning = releaseStoreExists && !releaseStorePassword.isBlank() && !releaseKeyAlias.isBlank() && !releaseKeyPassword.isBlank()
def productionAssetPaths = [
    'art/game.atlas',
    'audio/music/combat.ogg',
    'audio/music/combat_pressure.ogg',
    'audio/music/combat_apex.ogg',
    'audio/sfx/shot.ogg',
    'audio/sfx/crit.ogg',
    'audio/sfx/hit.ogg',
    'audio/sfx/kill.ogg',
    'audio/sfx/boss_hit.ogg',
    'audio/sfx/boss_phase.ogg',
    'audio/sfx/boss_kill.ogg',
    'audio/sfx/dash.ogg',
    'audio/sfx/level_up.ogg',
    'audio/sfx/ui_select.ogg',
    'audio/sfx/ui_back.ogg'
]
def missingProductionAssets = {
    productionAssetPaths.findAll { !file("../assets/${it}").isFile() }
}
def playStoreDir = file('../play/store')
def readStoreImage = { File source ->
    if (!source.isFile()) throw new GradleException("Play Store asset missing: ${source}")
    def image = javax.imageio.ImageIO.read(source)
    if (image == null) throw new GradleException("Play Store asset is not a readable PNG/JPEG image: ${source}")
    image
}
def requireNoAlpha = { File source, image ->
    if (image.colorModel.hasAlpha()) {
        throw new GradleException("Play Store asset must not contain an alpha channel: ${source}")
    }
}

android {
    namespace 'com.deadlinezero.game.android'
    compileSdk 36

    defaultConfig {
        applicationId 'com.deadlinezero.game'
        minSdk 26
        targetSdk 36
        versionCode appVersionCode
        versionName appVersion
        multiDexEnabled true
        testInstrumentationRunner 'androidx.test.runner.AndroidJUnitRunner'
        manifestPlaceholders = [admobAppId: admobAppId]
        resValue 'string', 'play_games_app_id', playGamesAppId
        buildConfigField 'String', 'ADMOB_REWARDED_ID', '"' + admobRewardedId + '"'
        buildConfigField 'String', 'PRIVACY_POLICY_URL', '"' + privacyPolicyUrl.replace('\\', '\\\\').replace('"', '\\"') + '"'
        buildConfigField 'boolean', 'PLAY_GAMES_CONFIGURED', validPlayGamesAppId(playGamesAppId).toString()
    }

    if (hasReleaseSigning) {
        signingConfigs {
            release {
                storeFile file(releaseStorePath)
                storePassword releaseStorePassword
                keyAlias releaseKeyAlias
                keyPassword releaseKeyPassword
            }
        }
    }

    buildTypes {
        release {
            if (hasReleaseSigning) signingConfig signingConfigs.release
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }

    sourceSets { main { assets.srcDirs = ['../assets'] } }
    packagingOptions { resources { excludes += ['/META-INF/{AL2.0,LGPL2.1}'] } }
    buildFeatures { buildConfig true }
}

configurations { natives }

dependencies {
    implementation project(':core')
    implementation "com.badlogicgames.gdx:gdx-backend-android:${gdxVersion}"
    natives "com.badlogicgames.gdx:gdx-platform:${gdxVersion}:natives-arm64-v8a"
    natives "com.badlogicgames.gdx:gdx-platform:${gdxVersion}:natives-armeabi-v7a"
    natives "com.badlogicgames.gdx:gdx-platform:${gdxVersion}:natives-x86_64"
    implementation 'com.google.android.gms:play-services-ads:25.4.0'
    implementation 'com.google.android.ump:user-messaging-platform:4.0.0'
    implementation 'com.android.billingclient:billing:9.1.0'
    implementation 'com.google.android.gms:play-services-games-v2:22.0.0'
    implementation 'com.google.android.play:review:2.0.2'

    androidTestImplementation 'androidx.test:core:1.6.1'
    androidTestImplementation 'androidx.test:runner:1.6.2'
    androidTestImplementation 'androidx.test.ext:junit:1.2.1'
}


tasks.register('copyAndroidNatives') {
    doFirst {
        file("src/main/jniLibs/armeabi-v7a").mkdirs()
        file("src/main/jniLibs/arm64-v8a").mkdirs()
        file("src/main/jniLibs/x86_64").mkdirs()
        configurations.natives.files.each { jar ->
            def outputDir = null
            if (jar.name.endsWith("natives-armeabi-v7a.jar")) outputDir = file("src/main/jniLibs/armeabi-v7a")
            if (jar.name.endsWith("natives-arm64-v8a.jar")) outputDir = file("src/main/jniLibs/arm64-v8a")
            if (jar.name.endsWith("natives-x86_64.jar")) outputDir = file("src/main/jniLibs/x86_64")
            if (outputDir != null) copy { from zipTree(jar); into outputDir; include "*.so" }
        }
    }
}

tasks.register('verifyProductionAssets') {
    group = 'verification'
    description = 'Fails unless the authored art and audio required for a production release are present.'
    doLast {
        def missing = missingProductionAssets()
        if (!missing.isEmpty()) {
            throw new GradleException('Production release blocked: missing authored assets: ' + missing.join(', '))
        }
    }
}

tasks.register('verifyLocalizationReleaseContract') {
    group = 'verification'
    description = 'Requires the localization readiness contract for production release.'
    doLast {
        File contract = new File(playStoreDir, 'LOCALIZATION.md')
        if (!contract.isFile()) throw new GradleException('Play release blocked: play/store/LOCALIZATION.md is missing.')
        String text = contract.getText('UTF-8')
        ['Current status', 'Minimum localization architecture', 'Release QA', 'Release rule'].each { required ->
            if (!text.contains('## ' + required)) throw new GradleException('Localization contract missing required section: ' + required)
        }
        if (!text.contains('English-only')) {
            throw new GradleException('Localization contract must explicitly state the current English-only release rule.')
        }
    }
}

tasks.register('verifyAccessibilityReleaseContract') {
    group = 'verification'
    description = 'Requires the accessibility/comfort release contract for production.'
    doLast {
        File contract = new File(playStoreDir, 'ACCESSIBILITY.md')
        if (!contract.isFile()) throw new GradleException('Play release blocked: play/store/ACCESSIBILITY.md is missing.')
        String text = contract.getText('UTF-8')
        ['Implemented controls', 'Release QA', 'Change-control rule'].each { required ->
            if (!text.contains('## ' + required)) throw new GradleException('Accessibility contract missing required section: ' + required)
        }
        ['screen shake', 'high-contrast telegraphs', 'reduced flashes', 'reduced-motion', 'UI scale', 'haptics'].each { required ->
            if (!text.toLowerCase().contains(required.toLowerCase())) throw new GradleException('Accessibility contract missing control: ' + required)
        }
    }
}

tasks.register('verifyPlayReleaseReadinessContract') {
    group = 'verification'
    description = 'Requires the final Play release-readiness matrix and key manual gate sections.'
    doLast {
        File matrix = new File(playStoreDir, 'RELEASE_READINESS.md')
        if (!matrix.isFile()) throw new GradleException('Play release blocked: play/store/RELEASE_READINESS.md is missing.')
        String text = matrix.getText('UTF-8')
        ['Automated gates', 'Manual platform gates', 'Release states', 'Production-ready', 'Rule'].each { required ->
            if (!text.contains('## ' + required) && !text.contains('### ' + required)) {
                throw new GradleException('Play release readiness matrix missing required section: ' + required)
            }
        }
    }
}

tasks.register('verifyPlayReleaseNotes') {
    group = 'verification'
    description = 'Requires versioned Google Play release notes for the exact appVersion.'
    doLast {
        File notes = new File(playStoreDir, 'RELEASE_NOTES.md')
        if (!notes.isFile()) throw new GradleException('Play release blocked: play/store/RELEASE_NOTES.md is missing.')
        String text = notes.getText('UTF-8')
        String marker = '## ' + appVersion
        int start = text.indexOf(marker)
        if (start < 0) throw new GradleException('Play release notes missing section for appVersion ' + appVersion)
        start += marker.length()
        int end = text.indexOf('\n## ', start)
        if (end < 0) end = text.length()
        String body = text.substring(start, end).trim()
        if (body.isBlank()) throw new GradleException('Play release notes are empty for appVersion ' + appVersion)
        if (body.codePointCount(0, body.length()) > 500) throw new GradleException('Play release notes exceed 500 characters for appVersion ' + appVersion)
    }
}

tasks.register('verifyPlayConsoleContract') {
    group = 'verification'
    description = 'Requires the repository-side Play Console declaration checklist for production release.'
    doLast {
        File contract = new File(playStoreDir, 'PLAY_CONSOLE.md')
        if (!contract.isFile()) throw new GradleException('Play release blocked: play/store/PLAY_CONSOLE.md is missing.')
        String text = contract.getText('UTF-8')
        ['App access', 'Ads', 'Target audience and content', 'Content rating', 'Data Safety and privacy', 'Monetization', 'Store presence', 'Release and device coverage', 'Final rollout gate'].each { required ->
            if (!text.contains('## ' + required)) throw new GradleException('Play Console contract missing required section: ' + required)
        }
    }
}

tasks.register('verifyPlayDataSafetyContract') {
    group = 'verification'
    description = 'Requires the repository-side Play Data Safety review contract for production release.'
    doLast {
        File contract = new File(playStoreDir, 'DATA_SAFETY.md')
        if (!contract.isFile()) throw new GradleException('Play release blocked: play/store/DATA_SAFETY.md is missing.')
        String text = contract.getText('UTF-8')
        ['Google Mobile Ads SDK', 'Google User Messaging Platform (UMP)', 'Google Play Billing', 'Google Play Games Services', 'Google Play In-App Review', 'Change-control rule'].each { required ->
            if (!text.contains(required)) throw new GradleException('Play Data Safety contract missing required section: ' + required)
        }
    }
}

tasks.register('verifyPlayStoreListing') {
    group = 'verification'
    description = 'Validates canonical Google Play listing metadata limits and required sections.'
    doLast {
        File listing = new File(playStoreDir, 'LISTING.md')
        if (!listing.isFile()) throw new GradleException('Play release blocked: play/store/LISTING.md is missing.')
        String text = listing.getText('UTF-8')
        def section = { String heading ->
            String marker = '## ' + heading
            int start = text.indexOf(marker)
            if (start < 0) throw new GradleException('Play listing missing section: ' + heading)
            start += marker.length()
            int end = text.indexOf('\n## ', start)
            if (end < 0) end = text.length()
            text.substring(start, end).trim()
        }
        String appName = section('App name')
        String shortDescription = section('Short description')
        String fullDescription = section('Full description')
        if (appName != 'Deadline: Zero') throw new GradleException("Play listing app name must be exactly 'Deadline: Zero'.")
        File stringsFile = file('src/main/res/values/strings.xml')
        if (!stringsFile.isFile() || !stringsFile.getText('UTF-8').contains('<string name="app_name">' + appName + '</string>')) {
            throw new GradleException('Play listing app name must match Android @string/app_name.')
        }
        if (appName.codePointCount(0, appName.length()) > 30) throw new GradleException('Play listing app name exceeds 30 characters.')
        if (shortDescription.isBlank() || shortDescription.codePointCount(0, shortDescription.length()) > 80) throw new GradleException('Play listing short description must contain 1..80 characters.')
        if (fullDescription.isBlank() || fullDescription.codePointCount(0, fullDescription.length()) > 4000) throw new GradleException('Play listing full description must contain 1..4000 characters.')
    }
}

tasks.register('verifyPlayStoreAssets') {
    group = 'verification'
    description = 'Validates the final Google Play icon, feature graphic and recommendation-grade phone screenshots.'
    doLast {
        File icon = new File(playStoreDir, 'icon.png')
        def iconImage = readStoreImage(icon)
        if (icon.length() > 1024L * 1024L) {
            throw new GradleException('Play Store icon must be <= 1 MiB: ' + icon)
        }
        if (iconImage.width != 512 || iconImage.height != 512) {
            throw new GradleException("Play Store icon must be exactly 512x512: ${iconImage.width}x${iconImage.height}")
        }

        File feature = new File(playStoreDir, 'feature-graphic.png')
        def featureImage = readStoreImage(feature)
        if (featureImage.width != 1024 || featureImage.height != 500) {
            throw new GradleException("Play feature graphic must be exactly 1024x500: ${featureImage.width}x${featureImage.height}")
        }
        requireNoAlpha(feature, featureImage)

        File screenshotsDir = new File(playStoreDir, 'phone-screenshots')
        if (!screenshotsDir.isDirectory()) {
            throw new GradleException('Play release blocked: play/store/phone-screenshots/ is missing.')
        }
        def screenshots = screenshotsDir.listFiles()?.findAll {
            it.isFile() && it.name.toLowerCase(Locale.ROOT) ==~ /.+\.(png|jpe?g)/
        }?.sort { it.name } ?: []
        if (screenshots.size() < 3) {
            throw new GradleException('Play release blocked: at least 3 recommendation-grade gameplay screenshots are required.')
        }
        screenshots.each { File screenshot ->
            if (screenshot.length() > 8L * 1024L * 1024L) {
                throw new GradleException('Play screenshot must be <= 8 MiB: ' + screenshot)
            }
            def image = readStoreImage(screenshot)
            requireNoAlpha(screenshot, image)
            if (image.width < 1920 || image.height < 1080 || image.width > 3840 || image.height > 3840) {
                throw new GradleException("Play game screenshot must be between 1920x1080 and the Play 3840px maximum dimension: ${screenshot} is ${image.width}x${image.height}")
            }
            if ((long) image.width * 9L != (long) image.height * 16L) {
                throw new GradleException("Play game screenshot must be exact 16:9 landscape: ${screenshot} is ${image.width}x${image.height}")
            }
        }
    }
}

tasks.register('verifyPlayReleaseConfig') {
    group = 'verification'
    description = 'Fails unless the build is configured as a publishable Google Play release.'
    dependsOn 'verifyProductionAssets', 'verifyPlayStoreAssets', 'verifyPlayStoreListing', 'verifyPlayDataSafetyContract', 'verifyPlayConsoleContract', 'verifyPlayReleaseNotes', 'verifyPlayReleaseReadinessContract', 'verifyAccessibilityReleaseContract', 'verifyLocalizationReleaseContract'
    doLast {
        boolean usingTestIds = admobAppId == testAdmobAppId || admobRewardedId == testRewardedId
        if (usingTestIds) {
            throw new GradleException('Play release blocked: production AdMob app and rewarded IDs are required.')
        }
        if (!validAdmobAppId(admobAppId) || !validRewardedId(admobRewardedId)) {
            throw new GradleException('Play release blocked: malformed AdMob IDs. Expected ca-app-pub-################~########## for the app and ca-app-pub-################/########## for rewarded.')
        }
        if (!validPrivacyPolicyUrl(privacyPolicyUrl)) {
            throw new GradleException('Play release blocked: configure -PprivacyPolicyUrl with the real public HTTPS privacy-policy URL used by the in-app Settings screen and Play Console.')
        }
        if (!validPlayGamesAppId(playGamesAppId)) {
            throw new GradleException('Play release blocked: configure -PplayGamesAppId with the numeric Google Play Games Services application ID.')
        }
        if (!releaseStoreExists) {
            throw new GradleException('Play release blocked: upload keystore is missing. Configure -PreleaseStoreFile or DEADLINE_ZERO_KEYSTORE.')
        }
        if (!hasReleaseSigning) {
            throw new GradleException('Play release blocked: upload signing credentials are incomplete. Configure store password, key alias, and key password via Gradle properties or DEADLINE_ZERO_* environment variables.')
        }
        if (!validPlayVersionCode(appVersionCode) || appVersion.isBlank()) {
            throw new GradleException('Play release blocked: appVersionCode must be between 1 and 2100000000 and appVersion must be non-empty.')
        }
    }
}

tasks.register('writePlayReleaseEvidence') {
    group = 'verification'
    description = 'Writes a machine-readable manifest describing the exact Play release configuration.'
    dependsOn 'verifyPlayReleaseConfig'
    doLast {
        File outDir = file("$buildDir/play-release")
        outDir.mkdirs()
        File evidence = new File(outDir, 'release-evidence.json')
        def payload = [
            applicationId: android.defaultConfig.applicationId,
            versionCode: appVersionCode,
            versionName: appVersion,
            minSdk: android.defaultConfig.minSdk.apiLevel,
            targetSdk: android.defaultConfig.targetSdk.apiLevel,
            compileSdk: android.compileSdk,
            playGamesConfigured: validPlayGamesAppId(playGamesAppId),
            privacyPolicyConfigured: validPrivacyPolicyUrl(privacyPolicyUrl),
            productionAdsConfigured: admobAppId != testAdmobAppId && admobRewardedId != testRewardedId,
            releaseSigningConfigured: hasReleaseSigning,
            listingContract: new File(playStoreDir, 'LISTING.md').isFile(),
            dataSafetyContract: new File(playStoreDir, 'DATA_SAFETY.md').isFile(),
            playConsoleContract: new File(playStoreDir, 'PLAY_CONSOLE.md').isFile(),
            generatedAtUtc: java.time.Instant.now().toString()
        ]
        evidence.setText(groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(payload)) + '\n', 'UTF-8')
        logger.lifecycle('Play release evidence: ' + evidence)
    }
}

tasks.register('bundlePlayRelease') {
    group = 'build'
    description = 'Runs core tests, release lint, production and Play Store validation, then builds the signed Google Play AAB.'
    dependsOn ':core:test', 'lintRelease', 'writePlayReleaseEvidence', 'bundleRelease'
}

tasks.configureEach { task ->
    if (task.name.contains("merge") && task.name.contains("JniLibFolders")) task.dependsOn copyAndroidNatives
    if (task.name == 'bundleRelease') task.mustRunAfter 'verifyPlayReleaseConfig'
    if (task.name in ['bundleRelease', 'assembleRelease']) {
        task.doFirst {
            boolean usingTestIds = admobAppId == testAdmobAppId || admobRewardedId == testRewardedId
            if (usingTestIds && !allowTestAds.get()) {
                throw new GradleException('Release build blocked: configure -PadmobAppId and -PadmobRewardedId with production AdMob IDs. Use -PallowTestAds=true only for CI build verification.')
            }
            if (!validAdmobAppId(admobAppId) || !validRewardedId(admobRewardedId)) {
                throw new GradleException('Release build blocked: malformed AdMob ID format.')
            }
            if (!validPlayVersionCode(appVersionCode)) {
                throw new GradleException('Release build blocked: appVersionCode must be between 1 and 2100000000.')
            }
            def missing = missingProductionAssets()
            if (!missing.isEmpty() && !allowPlaceholderAssets.get()) {
                throw new GradleException('Release build blocked: authored production assets are missing: ' + missing.join(', ') + '. Use -PallowPlaceholderAssets=true only for CI build verification.')
            }
        }
    }
}
```
