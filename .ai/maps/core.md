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
  main/
    java/
      com/
        deadlinezero/
          game/
            abilities/
              AbilityLoadout.java
              AbilityRuntime.java
              AbilitySynergyUnlockDetector.java
              AbilitySystem.java
              AbilityType.java
              AbilityUpgradeGuidance.java
              DroneDoctrine.java
              DroneDoctrineRules.java
            ai/
              AttackController.java
              BossAffixRules.java
              BossAttackPatternCatalog.java
              BossCombatRuntime.java
              BossIdentity.java
              BossPhaseController.java
              BossVariantStats.java
              EnemyArchetype.java
              EnemyPatternCatalog.java
              EnemyState.java
              FrostColossusBossProfile.java
              HarvesterBossProfile.java
              LeaperProfile.java
              LeaperRegistry.java
              LeaperRuntime.java
              LeaperSharedRuntime.java
              NullArchonBossProfile.java
              RevenantBossProfile.java
              WardenBossProfile.java
            audio/
              AudioCueLimiter.java
              AudioDirector.java
              MusicProfileSelector.java
            combat/
              DamageElement.java
              WeaponCatalog.java
              WeaponDefinition.java
              WeaponRuntime.java
              WeaponSignatureRuntime.java
            config/
              AccessibilitySettings.java
              GameConfig.java
              GraphicsSettings.java
              Localization.java
            entities/
              ActorState.java
              Enemy.java
              EnemyProjectile.java
              HomingMissile.java
              Player.java
              Projectile.java
            fx/
              ArcFx.java
              DamageNumber.java
              DeathFx.java
              ImpactFx.java
            input/
              MobileCombatInput.java
              VirtualStick.java
            meta/
              AchievementProgress.java
              AchievementService.java
              BalanceCoefficientAudit.java
              BalanceHealthRules.java
              BalanceRunSample.java
              BalanceTelemetryReport.java
              BalanceTelemetryRuntime.java
              BalanceTelemetrySegments.java
              BalanceTelemetryStore.java
              BalanceTelemetrySummary.java
              ChestService.java
              ConsumablePurchaseDelivery.java
              DailyCounterMath.java
              DailyProgress.java
              DailyService.java
              EndgameMutatorRules.java
              EntitlementStore.java
              EquipmentDropTable.java
              EquipmentItem.java
              EquipmentService.java
              EquipmentUpgradeService.java
              Inventory.java
              MasteryProgress.java
              MasteryRunNotice.java
              OnboardingCompletionPolicy.java
              OnboardingState.java
              PlayerProfile.java
              ProfileBackupCodec.java
              ProfileBackupSummary.java
              ProfileCounterMath.java
              ProfileSchema.java
              ProfileStore.java
              PurchaseGrantService.java
              ReviewPromptPolicy.java
              RunEncounterRuntime.java
              RunLoadoutContext.java
              RunMissionRuntime.java
              RunModifierContext.java
              RunRecoveryAdvice.java
              RunResult.java
              RunRewardCalculator.java
              RunSettlement.java
              RunShareText.java
              RunStageContext.java
              SaturatingMath.java
              SingularityCoreRules.java
              SingularityCoreRuntime.java
              StageMissionRules.java
              StageRules.java
              SurvivorCatalog.java
              SurvivorProgression.java
              ThreatMilestoneRewardCatalog.java
              ThreatProgressionService.java
              ThreatSetBonusRules.java
              ThreatTierRules.java
              WeaponProgression.java
              WeaponSynergyRules.java
              WeeklyProgress.java
              WeeklyService.java
            perf/
              AdaptiveFrameRateGovernor.java
              PerformanceTelemetry.java
              ThermalBudgetPolicy.java
            progression/
              CombatProtocolState.java
              LegendaryChoice.java
              LegendaryEffects.java
              LegendarySelector.java
              LegendaryState.java
              ProtocolUpgradeGuidance.java
              Upgrade.java
              UpgradeDraftPolicy.java
              UpgradeRarity.java
              UpgradeSelector.java
            screen/
              ArsenalScreen.java
              CloudSaveScreen.java
              GameScreen.java
              GearScreen.java
              MenuLayoutModel.java
              MenuScreen.java
              MissionsScreen.java
              RunContractScreen.java
              RunResultScreen.java
              SettingsScreen.java
              ShopScreen.java
              SurvivorLayoutModel.java
              SurvivorScreen.java
              VictoryScreen.java
            services/
              AdsService.java
              BillingService.java
              CloudAuthenticationRequiredException.java
              CloudProviderConflictException.java
              CloudRemoteChangedException.java
              CloudSaveAdapter.java
              CloudSaveService.java
              GameServices.java
              HapticsService.java
              OfferConfigService.java
              PrivacyService.java
              ReviewService.java
              ShareService.java
              SingleFlightGate.java
              ThermalService.java
            ui/
              MetaLayout.java
              ResponsiveGrid.java
              UiIconRenderer.java
              UiLayout.java
              UiMotion.java
              UiRenderer.java
              UiTypography.java
              UiViewport.java
            util/
              Pools.java
            visual/
              ActiveBuildStatus.java
              ActorMaterialProfile.java
              AdaptiveFxBudget.java
              AnimationProfileCatalog.java
              ArtManifest.java
              ArtProfileCatalog.java
              AudioManifest.java
              AuthoredCoreDirectionalArt.java
              AuthoredVfxRenderer.java
              BiomeDirectionalBootstrapArt.java
              BootstrapArtCatalog.java
              BootstrapEnvironmentArt.java
              BootstrapVfxArt.java
              BossPhaseTransitionProfile.java
              BossRevealCameraProfile.java
              ChampionBadgeRenderer.java
              ChampionVariantPresentation.java
              CharacterSpriteRenderer.java
              CombatAudioLayer.java
              CombatFeel.java
              CombatHudLayout.java
              CombatHudRenderer.java
              CombatOverlayViewport.java
              CombatPolishController.java
              CombatSpritePass.java
              CombatVisualEvents.java
              CompanionRenderer.java
              DeathFxRenderer.java
              Direction8.java
              DirectionalBootstrapArt.java
              EnemyHealthBarPresentation.java
              EnvironmentArtCatalog.java
              EnvironmentBiomeRules.java
              EnvironmentRenderer.java
              FinalArtContract.java
              FoundryHazardPresentation.java
              GameArt.java
              GraphicsQuality.java
              HighResBossDirectionalArt.java
              HighResDirectionalBootstrapArt.java
              HostileProjectilePresentation.java
              LeaperPresentationProfile.java
              LegendaryFxRenderer.java
              LocalLightRenderer.java
              NullBootstrapVfxArt.java
              NullHazardPresentation.java
              OnboardingHintPolicy.java
              PlayerProjectilePresentation.java
              PostFxShader.java
              ProductionAtlasAudit.java
              SingularityImpactTracker.java
              UpgradeIconRenderer.java
              UpgradePresentation.java
              VisualTheme.java
              WeaponLegendaryPresentation.java
              WeaponRenderer.java
              WorldFxRenderer.java
            world/
              ArenaHazardRuntime.java
              BiomeEnemyBehaviorRules.java
              BiomeEnemyRoster.java
              DeathBurstRules.java
              EndgameWaveCompositionRules.java
              LeaperSpawnRules.java
              RunEncounterDirector.java
              SpatialHash.java
              StageCombatPressureAudit.java
              WaveDirector.java
            DeadlineZeroGame.java
  test/
    java/
      com/
        deadlinezero/
          game/
            abilities/
              AbilityLoadoutTierTest.java
              AbilitySynergyUnlockDetectorTest.java
              AbilityUpgradeGuidanceTest.java
              DroneDoctrineRulesTest.java
            ai/
              AttackControllerCadenceTest.java
              BiomeEnemyAttackPatternTest.java
              BossAffixRulesTest.java
              BossAttackPatternCatalogTest.java
              BossCombatSecondaryTuningTest.java
              BossCombatVariantTest.java
              BossIdentityTest.java
              BossVariantStatsContractTest.java
              EnemyPatternCatalogTest.java
              EnemyVariantBalanceTest.java
              FrostColossusBossProfileTest.java
              HarvesterBossProfileTest.java
              HarvesterSummonTelegraphTest.java
              LeaperProfileTest.java
              LeaperRegistryTest.java
              LeaperRuntimeTest.java
              NullArchonBossProfileTest.java
              RevenantBossProfileTest.java
              WardenBossProfileTest.java
            audio/
              AudioCueLimiterTest.java
              AudioDirectorFallbackTest.java
              AudioDirectorVolumeTest.java
              FoundryHazardAudioContractTest.java
              MusicProfileSelectorTest.java
              NullHazardAudioContractTest.java
              WeaponSignatureAudioContractTest.java
            combat/
              EndgameWeaponArchetypeTest.java
              WeaponCatalogTest.java
              WeaponSignatureBalanceTest.java
              WeaponSignatureRuntimeTest.java
            config/
              AccessibilityColorVisionTest.java
              AccessibilitySettingsTest.java
              GraphicsSettingsTest.java
              LocalizationCatalogGuardTest.java
              LocalizationGlyphSanitizerTest.java
              LocalizationReleaseContractTest.java
              MobileRuntimeBudgetTest.java
            entities/
              EnemyBiomeElementResistanceTest.java
              EnemyBiomeTacticsTest.java
              EnemyChargeImpactTest.java
              EnemyContentScaleTest.java
              EnemyElementReactionTest.java
              EnemyProjectileStyleTest.java
              EnemySpecialistTest.java
              EnemyTacticsTest.java
              EnemyVariantTest.java
              NullWardSupportTest.java
            fx/
              DamageNumberTest.java
            meta/
              AchievementServiceTest.java
              BalanceCoefficientAuditTest.java
              BalanceCurveRegressionTest.java
              BalanceHealthRulesTest.java
              BalanceTelemetryReportTest.java
              BalanceTelemetryRuntimeTest.java
              BalanceTelemetrySegmentsTest.java
              BalanceTelemetrySummaryTest.java
              ConsumablePurchaseDeliveryTest.java
              CounterSafetyTest.java
              DailyServiceTest.java
              EndgameMutatorRulesTest.java
              EquipmentUpgradeSafetyTest.java
              InventoryRestoreTest.java
              MasteryEconomyGuardrailTest.java
              MasteryProgressTest.java
              MasteryRunNoticeTest.java
              OnboardingCompletionPolicyTest.java
              P0RunRegressionTest.java
              PlayerProfileSafetyTest.java
              ProfileBackupCodecTest.java
              ProfileBackupSummaryTest.java
              ProfileMigrationDocumentationTest.java
              ProfileSchemaTest.java
              ProfileStoreBackupTest.java
              PurchaseGrantServiceTest.java
              ReviewPromptPolicyTest.java
              RunLoadoutContextResetTest.java
              RunMissionRuntimeTest.java
              RunModifierContextTest.java
              RunRecoveryAdviceTest.java
              RunRewardCalculatorTest.java
              RunShareTextTest.java
              SingularityCoreRuntimeTest.java
              StageRulesTest.java
              SurvivorProgressionSafetyTest.java
              ThreatProgressionServiceTest.java
              ThreatSetBonusRulesTest.java
              ThreatTierRulesTest.java
              WeaponProgressionTest.java
              WeaponSynergyRulesTest.java
              WeeklyServiceTest.java
            perf/
              AdaptiveFrameRateGovernorTest.java
              PerformanceTelemetryTest.java
              ThermalBudgetPolicyTest.java
            progression/
              CombatProtocolStateTest.java
              LegendarySelectorTest.java
              LegendaryStateTest.java
              ProtocolUpgradeGuidanceTest.java
              RemainingWeaponFamilyLegendaryBalanceTest.java
              UpgradeDraftPolicyTest.java
              UpgradePoolTest.java
              WeaponFamilyLegendaryBalanceTest.java
              WeaponFamilyLegendaryTest.java
            screen/
              GameScreenBossSummonRosterTest.java
              MenuLayoutModelTest.java
              MetaScreenLayoutContractTest.java
              SurvivorLayoutModelTest.java
            services/
              BillingProductCatalogTest.java
              BillingServiceStateTest.java
              CloudSaveServiceTest.java
              OfferConfigServiceTest.java
              SingleFlightGateTest.java
              ThermalServiceTest.java
            ui/
              ResponsiveGridTest.java
              UiLayoutTest.java
              UiMotionTest.java
              UiRendererStateTest.java
            visual/
              ActiveBuildStatusTest.java
              ActorMaterialProfileTest.java
              AdaptiveFxBudgetTest.java
              AnimationProfileCatalogTest.java
              ArtProfileCatalogPhoneReadabilityTest.java
              ArtProfileCatalogTest.java
              AuthoredCoreDirectionalArtTest.java
              BiomeDirectionalBootstrapArtTest.java
              BootstrapArtAssetTest.java
              BootstrapArtCatalogTest.java
              BootstrapEnvironmentArtTest.java
              BootstrapVfxArtTest.java
              BossIdentityArtRoutingTest.java
              BossPhaseTransitionProfileTest.java
              BossRevealCameraProfileTest.java
              ChampionVariantPresentationTest.java
              CharacterSpriteFacingTest.java
              CombatHudLayoutTest.java
              CombatOverlayViewportTest.java
              CombatVisualEventsProtocolTest.java
              CompanionRendererTest.java
              Direction8Test.java
              DirectionalBootstrapArtTest.java
              DirectionalBootstrapLazyLoadTest.java
              DirectionalGpuMemoryBudgetTest.java
              EnemyHealthBarPresentationTest.java
              EnvironmentArtCatalogTest.java
              EnvironmentBiomeRulesTest.java
              FinalArtContractTest.java
              FinalArtLayoutContractTest.java
              FoundryHazardPresentationTest.java
              HighResBossDirectionalArtTest.java
              HighResDirectionalBootstrapArtTest.java
              HostileProjectilePresentationTest.java
              NullBootstrapVfxArtTest.java
              NullHazardPresentationTest.java
              OnboardingHintPolicyTest.java
              PlayerProjectilePresentationTest.java
              SingularityImpactTrackerTest.java
              SpecialistPresentationTest.java
              UpgradePresentationTest.java
              WeaponLegendaryPresentationTest.java
            world/
              ArenaHazardRuntimeTest.java
              BiomeEnemyBehaviorRulesTest.java
              BiomeEnemyRosterTest.java
              EndgameWaveCompositionRulesTest.java
              FoundryHazardActivationCueTest.java
              RunEncounterDirectorTest.java
              SpatialHashTest.java
              StageCombatPressureAuditTest.java
              WaveDirectorTest.java
build.gradle
```

# Files

## File: src/main/java/com/deadlinezero/game/abilities/AbilityLoadout.java
```java
/** Persistent-in-run ability levels plus deterministic evolution/synergy contracts. */
public final class AbilityLoadout {
⋮----
public int level(AbilityType type) { return levels.getOrDefault(type, 0); }
public boolean unlocked(AbilityType type) { return level(type) > 0; }
⋮----
/** Tier 0=locked, 1=levels 1-2, 2=levels 3-4, 3=level 5 evolution tier. */
public int tier(AbilityType type) {
int level = level(type);
⋮----
public boolean evolved(AbilityType type) { return tier(type) >= 3; }
⋮----
public DroneDoctrine droneDoctrine() { return droneDoctrine; }
public boolean hasDroneDoctrine() { return droneDoctrine != DroneDoctrine.NONE; }
⋮----
public boolean chooseDroneDoctrine(DroneDoctrine doctrine) {
if (doctrine == null || doctrine == DroneDoctrine.NONE || hasDroneDoctrine()) return false;
if (tier(AbilityType.DRONE) < 2) return false;
⋮----
public int upgrade(AbilityType type) {
int next = Math.min(MAX_LEVEL, level(type) + 1);
levels.put(type, next);
⋮----
/** Arc Reactor: evolved Tesla + established Drone turns both into a shock network. */
public boolean hasTeslaEvolution() {
return evolved(AbilityType.TESLA_ORB) && tier(AbilityType.DRONE) >= 2;
⋮----
/** Cryo Barrage: mature Cryo + Missile trees convert missiles into enlarged frost payloads. */
public boolean hasCryoMissileEvolution() {
return tier(AbilityType.CRYO_NOVA) >= 2 && tier(AbilityType.MISSILE_SWARM) >= 2;
⋮----
/** Superconductor: mature Tesla + Cryo primes targets for elemental overload reactions. */
public boolean hasSuperconductorSynergy() {
return tier(AbilityType.TESLA_ORB) >= 2 && tier(AbilityType.CRYO_NOVA) >= 2;
⋮----
/** Target Network: mature Drone + Missile trees improve volley density and target pressure. */
public boolean hasTargetNetworkSynergy() {
return tier(AbilityType.DRONE) >= 2 && tier(AbilityType.MISSILE_SWARM) >= 2;
⋮----
/** Permafrost Blades: mature Orbital + Cryo turns the blade into a close-range frost applicator. */
public boolean hasPermafrostBladeSynergy() {
return tier(AbilityType.ORBITAL_BLADE) >= 2 && tier(AbilityType.CRYO_NOVA) >= 2;
⋮----
/** Storm Blade: evolved Orbital + evolved Tesla turns close-range hits into shock pressure. */
public boolean hasStormBladeSynergy() {
return evolved(AbilityType.ORBITAL_BLADE) && evolved(AbilityType.TESLA_ORB);
```

## File: src/main/java/com/deadlinezero/game/abilities/AbilityRuntime.java
```java
public final class AbilityRuntime {
⋮----
public void update(float dt) {
teslaTimer = Math.max(0f, teslaTimer - dt);
missileTimer = Math.max(0f, missileTimer - dt);
cryoTimer = Math.max(0f, cryoTimer - dt);
droneTimer = Math.max(0f, droneTimer - dt);
orbitalTimer = Math.max(0f, orbitalTimer - dt);
⋮----
public boolean readyTesla() { return teslaTimer <= 0f; }
public boolean readyMissile() { return missileTimer <= 0f; }
public boolean readyCryo() { return cryoTimer <= 0f; }
public boolean readyDrone() { return droneTimer <= 0f; }
public boolean readyOrbital() { return orbitalTimer <= 0f; }
⋮----
public void resetTesla(int level) { teslaTimer = Math.max(.75f, 2.7f - level * .28f); }
public void resetMissile(int level) { missileTimer = Math.max(1.1f, 4.4f - level * .42f); }
public void resetCryo(int level) { cryoTimer = Math.max(2.2f, 7.2f - level * .55f); }
public void resetDrone(int level) { droneTimer = Math.max(.35f, 1.1f - level * .10f); }
public void resetOrbital(int level) { orbitalTimer = Math.max(.18f, .46f - level * .045f); }
```

## File: src/main/java/com/deadlinezero/game/abilities/AbilitySynergyUnlockDetector.java
```java
/** Detects which ability synergy became active after an upgrade without duplicating synergy rules. */
public final class AbilitySynergyUnlockDetector {
⋮----
public static Snapshot snapshot(AbilityLoadout a) {
return new Snapshot(
a.hasTeslaEvolution(),
a.hasCryoMissileEvolution(),
a.hasSuperconductorSynergy(),
a.hasTargetNetworkSynergy(),
a.hasPermafrostBladeSynergy(),
a.hasStormBladeSynergy());
⋮----
public static Synergy newlyActivated(Snapshot before, AbilityLoadout after) {
⋮----
if (!before.stormBlade() && after.hasStormBladeSynergy()) return Synergy.STORM_BLADE;
if (!before.arcReactor() && after.hasTeslaEvolution()) return Synergy.ARC_REACTOR;
if (!before.cryoBarrage() && after.hasCryoMissileEvolution()) return Synergy.CRYO_BARRAGE;
if (!before.superconductor() && after.hasSuperconductorSynergy()) return Synergy.SUPERCONDUCTOR;
if (!before.targetNetwork() && after.hasTargetNetworkSynergy()) return Synergy.TARGET_NETWORK;
if (!before.permafrostBlades() && after.hasPermafrostBladeSynergy()) return Synergy.PERMAFROST_BLADES;
⋮----
public static String hudKey(Synergy synergy) {
```

## File: src/main/java/com/deadlinezero/game/abilities/AbilitySystem.java
```java
/** Executes passive player abilities without allocating during the frame loop. */
public final class AbilitySystem {
public interface Listener { void onKilled(Enemy enemy); }
⋮----
private static final Color CRYO_IMPACT = new Color(.25f, .8f, 1f, 1f);
private static final Color FROST_DAMAGE = new Color(.55f, .9f, 1f, 1f);
private static final Color FROST_BLAST = new Color(.35f, .8f, 1f, 1f);
⋮----
private final AbilityRuntime runtime = new AbilityRuntime();
private final LeaperRuntime leapers = LeaperSharedRuntime.get();
⋮----
this(player, enemies, pools, new SpatialHash(2.2f), listener);
this.spatial.rebuild(enemies);
⋮----
this.abilityPower = RunLoadoutContext.abilityPowerMultiplier();
for (Enemy enemy : enemies) onEnemySpawned(enemy);
⋮----
public AbilityRuntime runtime() { return runtime; }
⋮----
public void update(float dt) {
runtime.update(dt);
updateLeapers(dt);
updateHomingMissiles(dt);
updateTesla();
updateMissiles();
updateCryo();
updateDrone();
updateOrbital();
⋮----
public void onEnemySpawned(Enemy enemy) {
⋮----
if (MathUtils.random() >= currentLeaperChance()) return;
leapers.register(enemy);
activeLeapers.add(enemy);
⋮----
private float currentLeaperChance() {
int stage = RunStageContext.stage();
float arrival = Math.max(1f, StageMissionRules.bossArrivalSeconds(stage));
float progress = MathUtils.clamp(RunMissionRuntime.elapsed() / arrival, 0f, 1f);
⋮----
return LeaperSpawnRules.share(stage, band);
⋮----
private void updateLeapers(float dt) {
⋮----
Enemy e = activeLeapers.get(i);
⋮----
activeLeapers.removeIndex(i);
⋮----
float distance = (float)Math.sqrt(Math.max(.0001f, distance2));
leapers.update(e, dt, distance, dx / distance, dy / distance);
⋮----
private void updateTesla() {
int level = player.abilities.level(AbilityType.TESLA_ORB);
if (level <= 0 || !runtime.readyTesla()) return;
Enemy target = nearest(player.position.x, player.position.y, 10f, null);
⋮----
int tier = player.abilities.tier(AbilityType.TESLA_ORB);
⋮----
if (player.abilities.evolved(AbilityType.TESLA_ORB)) {
⋮----
if (player.abilities.hasTeslaEvolution()) {
⋮----
boolean superconductor = player.abilities.hasSuperconductorSynergy();
⋮----
arc(originX, originY, current.position.x, current.position.y, .13f);
if (superconductor && current.alive) current.applyElement(DamageElement.FROST, damage * .18f);
damageEnemy(current, damage, DamageElement.SHOCK, Color.CYAN, .65f);
⋮----
current = nearest(previous.position.x, previous.position.y, tier >= 3 ? 5.0f : 4.2f, previous);
⋮----
runtime.resetTesla(level);
⋮----
private void updateMissiles() {
int level = player.abilities.level(AbilityType.MISSILE_SWARM);
if (level <= 0 || !runtime.readyMissile()) return;
⋮----
int tier = player.abilities.tier(AbilityType.MISSILE_SWARM);
⋮----
boolean cryoEvolution = player.abilities.hasCryoMissileEvolution();
⋮----
if (player.abilities.hasTargetNetworkSynergy()) {
⋮----
Enemy target = nearest(player.position.x, player.position.y, 18f, null);
⋮----
HomingMissile missile = pools.homingMissile();
⋮----
missile.spawn(player.position.x, player.position.y, target,
⋮----
runtime.resetMissile(level);
⋮----
private void updateHomingMissiles(float dt) {
⋮----
missile.target = nearest(missile.position.x, missile.position.y, 12f, null);
⋮----
missile.update(dt);
⋮----
explode(missile.position.x, missile.position.y, missile.explosionRadius,
⋮----
private void updateCryo() {
int level = player.abilities.level(AbilityType.CRYO_NOVA);
if (level <= 0 || !runtime.readyCryo()) return;
int tier = player.abilities.tier(AbilityType.CRYO_NOVA);
⋮----
impact(player.position.x, player.position.y, radius, .36f, CRYO_IMPACT);
⋮----
spatial.query(player.position.x, player.position.y, radius, spatialCandidates);
⋮----
if (!e.alive || e.position.dst2(player.position) > r2) continue;
damageEnemy(e, damage, DamageElement.FROST, FROST_DAMAGE, .35f);
⋮----
runtime.resetCryo(level);
⋮----
private void updateDrone() {
int level = player.abilities.level(AbilityType.DRONE);
if (level <= 0 || !runtime.readyDrone()) return;
int tier = player.abilities.tier(AbilityType.DRONE);
DroneDoctrine doctrine = player.abilities.droneDoctrine();
⋮----
float x = player.position.x + MathUtils.cosDeg(angle) * 1.8f;
float y = player.position.y + MathUtils.sinDeg(angle) * 1.8f;
⋮----
if (doctrine == DroneDoctrine.SENTINEL) interceptHostileProjectile(x, y);
⋮----
Enemy target = nearest(x, y, tier >= 2 ? 12.5f : 11f, null);
⋮----
damage *= DroneDoctrineRules.damageMultiplier(doctrine);
⋮----
DamageElement element = player.abilities.hasTeslaEvolution() ? DamageElement.SHOCK : DamageElement.KINETIC;
if (element == DamageElement.SHOCK) arc(x, y, target.position.x, target.position.y, .10f);
damageEnemy(target, damage, element, element == DamageElement.SHOCK ? Color.CYAN : Color.LIME, .42f);
⋮----
float secondaryMultiplier = DroneDoctrineRules.secondaryTargetMultiplier(
doctrine, player.abilities.hasTargetNetworkSynergy());
⋮----
Enemy second = nearest(x, y, 10f, target);
⋮----
arc(x, y, second.position.x, second.position.y, .08f);
damageEnemy(second, damage * secondaryMultiplier, DamageElement.KINETIC, Color.LIME, .30f);
⋮----
impact(x, y, .25f, .08f, Color.LIME);
⋮----
runtime.resetDrone(level);
⋮----
private boolean interceptHostileProjectile(float droneX, float droneY) {
float range = DroneDoctrineRules.interceptionRange(player.abilities.droneDoctrine());
⋮----
arc(droneX, droneY, best.position.x, best.position.y, .10f);
impact(best.position.x, best.position.y, .42f, .12f, Color.CYAN);
CombatVisualEvents.markSentinelIntercept();
AudioDirector.playGlobal(AudioDirector.Cue.SENTINEL_BLOCK);
⋮----
private void updateOrbital() {
int level = player.abilities.level(AbilityType.ORBITAL_BLADE);
if (level <= 0 || !runtime.readyOrbital()) return;
int tier = player.abilities.tier(AbilityType.ORBITAL_BLADE);
⋮----
float x = player.position.x + MathUtils.cosDeg(runtime.orbitalAngle) * orbit;
float y = player.position.y + MathUtils.sinDeg(runtime.orbitalAngle) * orbit;
⋮----
DamageElement element = player.abilities.hasStormBladeSynergy()
⋮----
: (player.abilities.hasPermafrostBladeSynergy() ? DamageElement.FROST : DamageElement.KINETIC);
⋮----
spatial.query(x, y, radius, spatialCandidates);
⋮----
damageEnemy(e, damage, element, color, .28f);
⋮----
runtime.resetOrbital(level);
⋮----
private void explode(float x, float y, float radius, float damage, DamageElement element) {
⋮----
impact(x, y, radius, .28f, blast);
⋮----
damageEnemy(e, damage, element, blast, .45f);
⋮----
private Enemy nearest(float x, float y, float range, Enemy exclude) {
return spatial.nearestWithin(x, y, range, exclude, null);
⋮----
private void damageEnemy(Enemy e, float damage, DamageElement element, Color color, float fxSize) {
⋮----
e.damage(damage);
⋮----
e.applyElement(element, damage);
DamageNumber n = pools.damageNumber();
if (n != null) n.spawn(e.position.x, e.position.y + e.radius, damage, false, color);
impact(e.position.x, e.position.y, fxSize, .14f, color);
if (wasAlive && !e.alive) listener.onKilled(e);
⋮----
private void impact(float x, float y, float size, float duration, Color color) {
ImpactFx fx = pools.impact();
if (fx != null) fx.spawn(x, y, size, duration, color);
⋮----
private void arc(float x1, float y1, float x2, float y2, float duration) {
ArcFx fx = pools.arc();
if (fx != null) fx.spawn(x1, y1, x2, y2, duration);
```

## File: src/main/java/com/deadlinezero/game/abilities/AbilityType.java
```java

```

## File: src/main/java/com/deadlinezero/game/abilities/AbilityUpgradeGuidance.java
```java
/** Pure presentation policy describing what an ability upgrade choice would unlock next. */
public final class AbilityUpgradeGuidance {
⋮----
public static String key(Player player, Upgrade upgrade) {
AbilityType type = abilityType(upgrade);
⋮----
int current = loadout.level(type);
⋮----
String synergy = activatingSynergy(loadout, type, next);
⋮----
private static String activatingSynergy(AbilityLoadout a, AbilityType type, int next) {
⋮----
if (next >= 5 && a.tier(AbilityType.DRONE) >= 2 && !a.hasTeslaEvolution())
⋮----
if (next >= 3 && a.tier(AbilityType.CRYO_NOVA) >= 2 && !a.hasSuperconductorSynergy())
⋮----
if (next >= 5 && a.evolved(AbilityType.ORBITAL_BLADE) && !a.hasStormBladeSynergy())
⋮----
if (next >= 3 && a.tier(AbilityType.CRYO_NOVA) >= 2 && !a.hasCryoMissileEvolution())
⋮----
if (next >= 3 && a.tier(AbilityType.DRONE) >= 2 && !a.hasTargetNetworkSynergy())
⋮----
if (next >= 3 && a.tier(AbilityType.MISSILE_SWARM) >= 2 && !a.hasCryoMissileEvolution())
⋮----
if (next >= 3 && a.tier(AbilityType.TESLA_ORB) >= 2 && !a.hasSuperconductorSynergy())
⋮----
if (next >= 3 && a.tier(AbilityType.ORBITAL_BLADE) >= 2 && !a.hasPermafrostBladeSynergy())
⋮----
if (next >= 3 && a.evolved(AbilityType.TESLA_ORB) && !a.hasTeslaEvolution())
⋮----
if (next >= 3 && a.tier(AbilityType.MISSILE_SWARM) >= 2 && !a.hasTargetNetworkSynergy())
⋮----
if (next >= 3 && a.tier(AbilityType.CRYO_NOVA) >= 2 && !a.hasPermafrostBladeSynergy())
⋮----
if (next >= 5 && a.evolved(AbilityType.TESLA_ORB) && !a.hasStormBladeSynergy())
⋮----
public static AbilityType abilityType(Upgrade upgrade) {
```

## File: src/main/java/com/deadlinezero/game/abilities/DroneDoctrine.java
```java

```

## File: src/main/java/com/deadlinezero/game/abilities/DroneDoctrineRules.java
```java
/** Pure doctrine tuning shared by runtime and tests. */
public final class DroneDoctrineRules {
⋮----
public static float damageMultiplier(DroneDoctrine doctrine) {
⋮----
public static float secondaryTargetMultiplier(DroneDoctrine doctrine, boolean targetNetwork) {
⋮----
public static float interceptionRange(DroneDoctrine doctrine) {
```

## File: src/main/java/com/deadlinezero/game/ai/AttackController.java
```java
/** Allocation-free attack timing state machine. Rendering/gameplay consume the state transitions. */
public final class AttackController {
⋮----
public EnemyState state() { return state; }
public EnemyArchetype archetype() { return archetype; }
public float timer() { return timer; }
public float cooldownMultiplier() { return cooldownMultiplier; }
public float telegraphMultiplier() { return telegraphMultiplier; }
⋮----
public void setCadence(float cooldownMultiplier, float telegraphMultiplier, float recoveryMultiplier) {
⋮----
? BossIdentity.forStage(RunStageContext.stage()) : BossIdentity.ALPHA;
⋮----
this.cooldownMultiplier = clamp(cooldownMultiplier * bossCooldown, .45f, 1.8f);
this.telegraphMultiplier = clamp(telegraphMultiplier * bossTelegraph, .45f, 1.8f);
this.recoveryMultiplier = clamp(recoveryMultiplier * bossRecovery, .45f, 1.8f);
⋮----
public void update(float dt, float distance) {
⋮----
public boolean consumeAttack() {
⋮----
public void forceStunned(float duration) {
⋮----
timer = Math.max(timer, duration);
⋮----
public void updateStun(float dt) {
⋮----
public void markDead() { state = EnemyState.DEAD; }
⋮----
private static float clamp(float v, float min, float max) { return Math.max(min, Math.min(max, v)); }
```

## File: src/main/java/com/deadlinezero/game/ai/BossAffixRules.java
```java
/** Deterministic endgame boss affixes derived from stage and active Threat Tier. */
public final class BossAffixRules {
⋮----
public static Affix forRun(int stage, int threatTier) {
int tier = ThreatTierRules.sanitizeTier(threatTier);
⋮----
int index = Math.floorMod(Math.max(1, stage) * 7 + tier * 5, rotation.length);
```

## File: src/main/java/com/deadlinezero/game/ai/BossAttackPatternCatalog.java
```java
/** Data-only projectile pattern catalog for all boss identities. */
public final class BossAttackPatternCatalog {
⋮----
/** Compatibility overload retained for existing callers. */
public static Pattern forPhase(boolean revenant, int phase) {
BossIdentity identity = revenant ? BossIdentity.REVENANT : BossIdentity.forStage(RunStageContext.stage());
return forPhase(identity, phase);
⋮----
public static Pattern forPhase(BossIdentity identity, int phase) {
⋮----
int safePhase = Math.max(1, Math.min(3, phase));
⋮----
case 1 -> new Pattern(7, 8f, 1.16f, .70f, 0, 0f, false);
case 2 -> new Pattern(12, 30f, 1.10f, .64f, 4, 1.7f, true);
default -> new Pattern(18, 20f, 1.14f, .60f, 3, 2.0f, true);
⋮----
case 1 -> new Pattern(3, 17f, .82f, .96f, 0, 0f, false);
case 2 -> new Pattern(8, 45f, .78f, .82f, 2, 2.45f, true);
default -> new Pattern(10, 36f, .76f, .78f, 2, 2.85f, true);
⋮----
case 1 -> new Pattern(9, 7f, 1.18f, .58f, 0, 0f, false);
case 2 -> new Pattern(16, 22.5f, 1.12f, .54f, 5, 1.65f, true);
default -> new Pattern(24, 15f, 1.20f, .50f, 4, 1.85f, true);
⋮----
case 1 -> new Pattern(8, 9f, 1.22f, .58f, 0, 0f, false);
case 2 -> new Pattern(18, 20f, 1.18f, .52f, 6, 1.55f, true);
default -> new Pattern(30, 12f, 1.26f, .46f, 5, 1.75f, true);
⋮----
case 1 -> new Pattern(4, 14f, .80f, 1.02f, 0, 0f, false);
case 2 -> new Pattern(9, 40f, .74f, .88f, 3, 2.55f, true);
default -> new Pattern(14, 26f, .78f, .82f, 2, 3.05f, true);
⋮----
case 1 -> new Pattern(5, 11f, 1f, .72f, 0, 0f, false);
case 2 -> new Pattern(10, 36f, .89f, .62f, 0, 0f, true);
default -> new Pattern(14, 360f / 14f, .94f, .58f, 3, 2.2f, true);
```

## File: src/main/java/com/deadlinezero/game/ai/BossCombatRuntime.java
```java
/** Runtime timers and phase-gated decisions for advanced boss actions. */
public final class BossCombatRuntime {
⋮----
public BossCombatRuntime() { this(BossIdentity.forStage(RunStageContext.stage())); }
⋮----
this.affix = BossAffixRules.forRun(RunStageContext.stage(), RunStageContext.threatTier());
⋮----
public void update(float dt, int phase) {
⋮----
chargeDuration = Math.max(0f, chargeDuration - dt);
⋮----
public boolean consumeCharge(int phase) {
⋮----
public boolean consumeSummon(int phase) {
⋮----
/** True during the final pre-summon window, used by boss portal telegraphs. */
public boolean summonTelegraphing(int phase) {
⋮----
/** 0 at telegraph start and 1 immediately before the summon fires. */
public float summonTelegraphProgress(int phase) {
if (!summonTelegraphing(phase)) return 0f;
return Math.max(0f, Math.min(1f, 1f - summonTimer / SUMMON_TELEGRAPH_SECONDS));
⋮----
public static float summonTelegraphSeconds() { return SUMMON_TELEGRAPH_SECONDS; }
⋮----
public boolean consumeEnragePulse(int phase) {
⋮----
public int summonCount(int phase) {
⋮----
public int enrageShots() {
⋮----
public float enrageProjectileSpeed() {
⋮----
public int enrageExplosiveEvery() {
⋮----
return Math.max(1, base - affix.explosiveDensityBonus);
⋮----
public float enrageExplosionRadius() {
⋮----
public boolean charging() { return chargeDuration > 0f; }
public float chargeDuration() { return chargeDuration; }
public BossIdentity identity() { return identity; }
public BossAffixRules.Affix affix() { return affix; }
public boolean revenant() { return identity == BossIdentity.REVENANT; }
public boolean warden() { return identity == BossIdentity.WARDEN; }
public boolean harvester() { return identity == BossIdentity.HARVESTER; }
public boolean nullArchon() { return identity == BossIdentity.NULL_ARCHON; }
public boolean frostColossus() { return identity == BossIdentity.FROST_COLOSSUS; }
```

## File: src/main/java/com/deadlinezero/game/ai/BossIdentity.java
```java
/** Deterministic boss identity selection that preserves existing rotations and adds Null milestones. */
⋮----
public static BossIdentity forStage(int stage) {
int safeStage = Math.max(1, stage);
if (safeStage >= 40 && Math.floorMod(safeStage - 40, 10) == 0) return FROST_COLOSSUS;
if (safeStage >= 20 && Math.floorMod(safeStage - 20, 5) == 0) return NULL_ARCHON;
⋮----
if (RevenantBossProfile.useForStage(safeStage)) return REVENANT;
```

## File: src/main/java/com/deadlinezero/game/ai/BossPhaseController.java
```java
/** Health-threshold phase controller. Phase transitions are edge-triggered. */
public final class BossPhaseController {
⋮----
public int phase() { return phase; }
public boolean consumePhaseChanged() {
⋮----
public void update(float hpRatio) {
⋮----
public float speedMultiplier() {
⋮----
public float cooldownMultiplier() {
```

## File: src/main/java/com/deadlinezero/game/ai/BossVariantStats.java
```java
/** Applies deterministic stat selection for all boss identities and active endgame affix. */
public final class BossVariantStats {
⋮----
public static Stats forStage(int stage, float baseHp, float baseSpeed, float baseDamage) {
Stats identityStats = forIdentity(BossIdentity.forStage(stage), baseHp, baseSpeed, baseDamage);
BossAffixRules.Affix affix = BossAffixRules.forRun(stage, RunStageContext.threatTier());
return new Stats(identityStats.hp() * affix.hp, identityStats.speed() * affix.speed,
identityStats.damage() * affix.damage);
⋮----
public static Stats forIdentity(BossIdentity identity, float baseHp, float baseSpeed, float baseDamage) {
⋮----
case REVENANT -> new Stats(baseHp * RevenantBossProfile.HP_MULTIPLIER,
⋮----
case WARDEN -> new Stats(baseHp * WardenBossProfile.HP_MULTIPLIER,
⋮----
case HARVESTER -> new Stats(baseHp * HarvesterBossProfile.HP_MULTIPLIER,
⋮----
case NULL_ARCHON -> new Stats(baseHp * NullArchonBossProfile.HP_MULTIPLIER,
⋮----
case FROST_COLOSSUS -> new Stats(baseHp * FrostColossusBossProfile.HP_MULTIPLIER,
⋮----
default -> new Stats(baseHp, baseSpeed, baseDamage);
```

## File: src/main/java/com/deadlinezero/game/ai/EnemyArchetype.java
```java
/** Immutable behavior tuning shared by enemies of the same combat role. */
public final class EnemyArchetype {
⋮----
public static final EnemyArchetype MELEE = new EnemyArchetype(Role.MELEE, 0f, 0.9f, 0.8f, 0.18f, 0.22f);
public static final EnemyArchetype RANGED = new EnemyArchetype(Role.RANGED, 7.5f, 9.5f, 1.8f, 0.65f, 0.45f);
public static final EnemyArchetype BOSS = new EnemyArchetype(Role.BOSS, 5.5f, 11f, 2.6f, 0.9f, 0.7f);
```

## File: src/main/java/com/deadlinezero/game/ai/EnemyPatternCatalog.java
```java
/** Pure combat pattern tuning kept outside GameScreen so enemy attacks remain testable and data-driven. */
public final class EnemyPatternCatalog {
⋮----
public static RangedPattern ranged(Enemy.Variant variant) {
⋮----
BiomeEnemyRoster.Identity identity = BiomeEnemyRoster.identityFor(RunStageContext.stage(), Enemy.Type.RANGED);
if (identity == BiomeEnemyRoster.Identity.CINDER_GUNNER) return cinderGunner(variant);
if (identity == BiomeEnemyRoster.Identity.STATIC_SEER) return staticSeer(variant);
return baseRanged(variant);
⋮----
private static RangedPattern baseRanged(Enemy.Variant variant) {
⋮----
case SWIFT -> new RangedPattern(3, 7.5f, 1.14f, .68f, false, 0f);
case ARMORED -> new RangedPattern(1, 0f, .88f, 1.34f, true, 1.65f);
case FERAL -> new RangedPattern(5, 12f, 1.04f, .54f, false, 0f);
case VOLATILE -> new RangedPattern(2, 7f, .96f, .72f, true, 1.35f);
case JUGGERNAUT -> new RangedPattern(1, 0f, .82f, 1.48f, true, 1.75f);
case RAVAGER -> new RangedPattern(4, 10f, 1.10f, .66f, false, 0f);
case AEGIS -> new RangedPattern(2, 5f, .94f, .78f, false, 0f);
case HUNTER -> new RangedPattern(3, 3.5f, 1.18f, .82f, false, 0f);
default -> new RangedPattern(1, 0f, 1f, 1f, false, 0f);
⋮----
private static RangedPattern cinderGunner(Enemy.Variant variant) {
⋮----
case SWIFT -> new RangedPattern(4, 6.0f, 1.02f, .29f, true, 1.10f);
case ARMORED -> new RangedPattern(2, 4.0f, .88f, .50f, true, 1.40f);
case FERAL -> new RangedPattern(5, 8.0f, .98f, .23f, true, 1.05f);
case VOLATILE -> new RangedPattern(4, 7.0f, .98f, .26f, true, 1.35f);
case JUGGERNAUT -> new RangedPattern(2, 4.5f, .82f, .48f, true, 1.55f);
case RAVAGER -> new RangedPattern(6, 9.0f, 1.04f, .20f, true, 1.05f);
case AEGIS -> new RangedPattern(3, 5.0f, .94f, .31f, true, 1.20f);
case HUNTER -> new RangedPattern(4, 3.0f, 1.10f, .30f, true, 1.10f);
default -> new RangedPattern(3, 6.5f, .96f, .34f, true, 1.15f);
⋮----
private static RangedPattern staticSeer(Enemy.Variant variant) {
⋮----
case SWIFT -> new RangedPattern(6, 13.0f, 1.22f, .18f, false, 0f);
case ARMORED -> new RangedPattern(3, 18.0f, 1.02f, .32f, false, 0f);
case FERAL -> new RangedPattern(7, 15.0f, 1.16f, .16f, false, 0f);
case VOLATILE -> new RangedPattern(5, 12.0f, 1.12f, .20f, true, 1.20f);
case JUGGERNAUT -> new RangedPattern(3, 16.0f, .96f, .34f, true, 1.45f);
case RAVAGER -> new RangedPattern(8, 17.0f, 1.20f, .14f, false, 0f);
case AEGIS -> new RangedPattern(5, 13.0f, 1.08f, .22f, false, 0f);
case HUNTER -> new RangedPattern(4, 6.0f, 1.28f, .26f, false, 0f);
default -> new RangedPattern(5, 15.0f, 1.14f, .20f, false, 0f);
⋮----
public static ChargePattern charge(Enemy.Type type, Enemy.Variant variant) {
⋮----
BiomeEnemyRoster.Identity identity = BiomeEnemyRoster.identityFor(RunStageContext.stage(), type);
⋮----
return new ChargePattern(damage, radius, knockback, recovery);
```

## File: src/main/java/com/deadlinezero/game/ai/EnemyState.java
```java
/** Runtime state used by enemy AI and boss phase controllers. */
```

## File: src/main/java/com/deadlinezero/game/ai/FrostColossusBossProfile.java
```java
/** Stage-40 Cryogenic Depths apex boss. Heavy, deliberate area denial with compact explosive lattices. */
public final class FrostColossusBossProfile {
```

## File: src/main/java/com/deadlinezero/game/ai/HarvesterBossProfile.java
```java
/** Deterministic tuning contract for the pressure-oriented HARVESTER boss identity. */
public final class HarvesterBossProfile {
```

## File: src/main/java/com/deadlinezero/game/ai/LeaperProfile.java
```java
/** Data-only tuning contract for the upcoming LEAPER combat archetype. */
public final class LeaperProfile {
⋮----
public static boolean inLeapRange(float distance) {
```

## File: src/main/java/com/deadlinezero/game/ai/LeaperRegistry.java
```java
/** Run-local identity registry for specialized LEAPER enemies. */
public final class LeaperRegistry {
⋮----
public void register(Enemy enemy) {
if (enemy != null) entries.put(enemy, Boolean.TRUE);
⋮----
public boolean contains(Enemy enemy) {
return enemy != null && entries.containsKey(enemy);
```

## File: src/main/java/com/deadlinezero/game/ai/LeaperRuntime.java
```java
/** External LEAPER behavior runtime so specialized runners can leap without modifying Enemy.Type. */
public final class LeaperRuntime {
private static final class State {
float cooldown = MathUtils.random(LeaperProfile.LEAP_COOLDOWN_MIN, LeaperProfile.LEAP_COOLDOWN_MAX);
⋮----
public void register(Enemy enemy) {
if (enemy != null) states.put(enemy, new State());
⋮----
public boolean contains(Enemy enemy) {
return enemy != null && states.containsKey(enemy);
⋮----
public void update(Enemy enemy, float dt, float distanceToPlayer, float dirX, float dirY) {
State state = states.get(enemy);
⋮----
state.cooldown = Math.max(0f, state.cooldown - dt);
state.impactWindow = Math.max(0f, state.impactWindow - dt);
⋮----
state.windup = Math.max(0f, state.windup - dt);
⋮----
enemy.addImpulse(dirX * LeaperProfile.LEAP_IMPULSE, dirY * LeaperProfile.LEAP_IMPULSE);
⋮----
state.cooldown = MathUtils.random(LeaperProfile.LEAP_COOLDOWN_MIN, LeaperProfile.LEAP_COOLDOWN_MAX);
⋮----
if (state.cooldown <= 0f && LeaperProfile.inLeapRange(distanceToPlayer)) {
⋮----
public boolean telegraphing(Enemy enemy) {
⋮----
public boolean consumeImpact(Enemy enemy) {
```

## File: src/main/java/com/deadlinezero/game/ai/LeaperSharedRuntime.java
```java
/** Shared run-local LEAPER runtime used by simulation and presentation hooks. */
public final class LeaperSharedRuntime {
private static final LeaperRuntime INSTANCE = new LeaperRuntime();
⋮----
public static LeaperRuntime get() { return INSTANCE; }
```

## File: src/main/java/com/deadlinezero/game/ai/NullArchonBossProfile.java
```java
/** Late-game Null Sector boss tuning. Fast phase pressure with dense but compact projectile lattices. */
public final class NullArchonBossProfile {
```

## File: src/main/java/com/deadlinezero/game/ai/RevenantBossProfile.java
```java
/** Deterministic tuning contract for the faster REVENANT boss variant. */
public final class RevenantBossProfile {
⋮----
public static boolean unlocked(int stage) { return stage >= MIN_STAGE; }
⋮----
/** Alternates boss identities after unlock so runs gain predictable variety without RNG streaks. */
public static boolean useForStage(int stage) {
return unlocked(stage) && stage % 2 == 0;
```

## File: src/main/java/com/deadlinezero/game/ai/WardenBossProfile.java
```java
/** Deterministic tuning contract for the heavy WARDEN boss identity. */
public final class WardenBossProfile {
```

## File: src/main/java/com/deadlinezero/game/audio/AudioCueLimiter.java
```java
/**
 * Allocation-free per-cue rate limiter for combat audio. Prevents dense combat events from
 * spawning excessive overlapping voices while preserving high-priority feedback.
 */
public final class AudioCueLimiter {
private final long[] lastPlayedNanos = new long[AudioDirector.Cue.values().length];
⋮----
public boolean allow(AudioDirector.Cue cue, long nowNanos) {
⋮----
int index = cue.ordinal();
long minInterval = minIntervalNanos(cue);
⋮----
static long minIntervalNanos(AudioDirector.Cue cue) {
⋮----
public void reset() {
```

## File: src/main/java/com/deadlinezero/game/audio/AudioDirector.java
```java
/** Resilient production audio gateway. Missing files degrade to safe fallbacks or silence instead of breaking the game. */
public final class AudioDirector {
⋮----
private final AudioCueLimiter limiter = new AudioCueLimiter();
⋮----
load(Cue.SHOT, "audio/sfx/shot.ogg");
load(Cue.CRIT, "audio/sfx/crit.ogg");
load(Cue.HIT, "audio/sfx/hit.ogg");
load(Cue.KILL, "audio/sfx/kill.ogg");
load(Cue.BOSS_HIT, "audio/sfx/boss_hit.ogg");
load(Cue.BOSS_PHASE, "audio/sfx/boss_phase.ogg");
load(Cue.BOSS_KILL, "audio/sfx/boss_kill.ogg");
load(Cue.DASH, "audio/sfx/dash.ogg");
load(Cue.LEVEL_UP, "audio/sfx/level_up.ogg");
load(Cue.UI_SELECT, "audio/sfx/ui_select.ogg");
load(Cue.UI_BACK, "audio/sfx/ui_back.ogg");
load(Cue.SINGULARITY, "audio/sfx/singularity.ogg");
load(Cue.ION_OVERCHARGE, "audio/sfx/ion_overcharge.ogg");
load(Cue.CINDER_OVERHEAT, "audio/sfx/cinder_overheat.ogg");
load(Cue.FOUNDRY_LAVA, "audio/sfx/foundry_lava.ogg");
load(Cue.FOUNDRY_STEAM, "audio/sfx/foundry_steam.ogg");
load(Cue.FOUNDRY_HEAT, "audio/sfx/foundry_heat.ogg");
load(Cue.NULL_RIFT, "audio/sfx/null_rift.ogg");
load(Cue.NULL_STATIC, "audio/sfx/null_static.ogg");
load(Cue.NULL_BEAM, "audio/sfx/null_beam.ogg");
for (MusicProfileSelector.Profile profile : MusicProfileSelector.Profile.values()) loadMusic(profile, MusicProfileSelector.assetPath(profile));
⋮----
private void load(Cue cue, String path) {
FileHandle file = Gdx.files.internal(path);
if (file.exists()) sounds.put(cue, Gdx.audio.newSound(file));
⋮----
private void loadMusic(MusicProfileSelector.Profile profile, String path) {
⋮----
if (!file.exists()) return;
Music track = Gdx.audio.newMusic(file);
track.setLooping(true);
track.setVolume(master * music);
combatMusic.put(profile, track);
⋮----
static Cue fallbackCue(Cue cue) {
⋮----
private Sound resolveSound(Cue cue) {
Sound sound = sounds.get(cue);
⋮----
Cue fallback = fallbackCue(cue);
⋮----
Sound fallbackSound = sounds.get(fallback);
⋮----
Cue secondFallback = fallbackCue(fallback);
return secondFallback == null ? null : sounds.get(secondFallback);
⋮----
private Music resolveMusic(MusicProfileSelector.Profile profile) {
Music track = combatMusic.get(profile);
⋮----
return combatMusic.get(MusicProfileSelector.Profile.SURVIVAL);
⋮----
public static void playGlobal(Cue cue) { if (active != null) active.play(cue); }
public static void playGlobal(Cue cue, float pitch, float pan) { if (active != null) active.play(cue, pitch, pan); }
⋮----
public void play(Cue cue) {
Sound sound = resolveSound(cue);
if (sound == null || !limiter.allow(cue, TimeUtils.nanoTime())) return;
sound.play(master * sfx);
⋮----
public void play(Cue cue, float pitch, float pan) {
⋮----
sound.play(master * sfx, Math.max(.5f, Math.min(2f, pitch)), Math.max(-1f, Math.min(1f, pan)));
⋮----
public void startCombatMusic() { startCombatMusic(1); }
public void startCombatMusic(int stage) {
Music next = resolveMusic(MusicProfileSelector.forStage(stage));
⋮----
if (activeCombatMusic != null && activeCombatMusic != next && activeCombatMusic.isPlaying()) activeCombatMusic.stop();
⋮----
activeCombatMusic.setVolume(master * music);
⋮----
if (activeCombatMusic.isPlaying()) activeCombatMusic.pause();
⋮----
if (!activeCombatMusic.isPlaying()) activeCombatMusic.play();
⋮----
public void stopCombatMusic() {
if (activeCombatMusic != null) activeCombatMusic.stop();
⋮----
public void suspend() {
⋮----
resumeAfterSuspension = activeCombatMusic.isPlaying();
if (resumeAfterSuspension) activeCombatMusic.pause();
⋮----
public void resume() {
⋮----
public void setVolumes(float master, float sfx, float music) {
this.master = normalizeVolume(master);
this.sfx = normalizeVolume(sfx);
this.music = normalizeVolume(music);
applyMusicVolume();
⋮----
private void applyMusicVolume() {
⋮----
for (Music track : combatMusic.values()) track.setVolume(volume);
⋮----
static float normalizeVolume(float value) {
if (!Float.isFinite(value)) return 0f;
return Math.max(0f, Math.min(1f, value));
⋮----
public void dispose() {
⋮----
limiter.reset();
for (Sound sound : sounds.values()) sound.dispose();
sounds.clear();
for (Music track : combatMusic.values()) track.dispose();
combatMusic.clear();
```

## File: src/main/java/com/deadlinezero/game/audio/MusicProfileSelector.java
```java
/** Deterministic combat music profile selection from run stage. */
public final class MusicProfileSelector {
⋮----
public static Profile forStage(int stage) {
int safeStage = Math.max(1, stage);
⋮----
public static String assetPath(Profile profile) {
```

## File: src/main/java/com/deadlinezero/game/combat/DamageElement.java
```java

```

## File: src/main/java/com/deadlinezero/game/combat/WeaponCatalog.java
```java
public final class WeaponCatalog {
⋮----
public static final WeaponDefinition AR9 = new WeaponDefinition(
⋮----
public static final WeaponDefinition SCATTERGUN = new WeaponDefinition(
⋮----
public static final WeaponDefinition RAIL_RIFLE = new WeaponDefinition(
⋮----
public static final WeaponDefinition INFERNO_SMG = new WeaponDefinition(
⋮----
public static final WeaponDefinition CRYO_LANCE = new WeaponDefinition(
⋮----
public static final WeaponDefinition ARC_CARBINE = new WeaponDefinition(
⋮----
public static final WeaponDefinition BREACHER = new WeaponDefinition(
⋮----
/** Endgame precision needle: extremely fast, crit-heavy and naturally pierces clustered specialists. */
public static final WeaponDefinition ION_NEEDLE = new WeaponDefinition(
⋮----
/** Heavy incendiary cannon: deliberately slow cadence, massive impact and persistent burn pressure. */
public static final WeaponDefinition CINDER_CANNON = new WeaponDefinition(
⋮----
/** Endgame shock burst rifle: controlled three-round fan with moderate penetration. */
public static final WeaponDefinition TEMPEST_BURST = new WeaponDefinition(
⋮----
/** Endgame frost scatter rifle: four dense shards trade cadence for control and stagger. */
public static final WeaponDefinition WHITEOUT_SHARD = new WeaponDefinition(
⋮----
/** Endgame incendiary repeater: accurate sustained pressure between SMG and cannon extremes. */
public static final WeaponDefinition PHOENIX_REPEATER = new WeaponDefinition(
⋮----
public static WeaponDefinition[] all() { return ALL.clone(); }
public static WeaponDefinition byId(String id) {
⋮----
for (WeaponDefinition definition : ALL) if (definition.id.equalsIgnoreCase(id)) return definition;
⋮----
public static float paperDps(WeaponDefinition weapon) {
⋮----
return weapon.damage * Math.max(1, weapon.projectileCount) / Math.max(.04f, weapon.fireInterval);
```

## File: src/main/java/com/deadlinezero/game/combat/WeaponDefinition.java
```java
public final class WeaponDefinition {
⋮----
public String displayNameKey() { return "weapon." + id.toLowerCase(java.util.Locale.ROOT) + ".name"; }
```

## File: src/main/java/com/deadlinezero/game/combat/WeaponRuntime.java
```java
public final class WeaponRuntime {
⋮----
reset();
⋮----
public void reset() {
```

## File: src/main/java/com/deadlinezero/game/combat/WeaponSignatureRuntime.java
```java
/**
 * Per-run deterministic signature passives for late-game weapons.
 * Kept data-only so projectile decoration remains testable and independent from screens.
 */
public final class WeaponSignatureRuntime {
⋮----
static ShotModifier none() { return new ShotModifier(Kind.NONE, false, false, 1f, 0, 1f, .11f); }
⋮----
public static void begin(WeaponDefinition definition) {
⋮----
weaponCritMultiplier = Math.max(1f, safe.critMultiplier);
⋮----
public static void enableIonCascade() {
if (!"ion_needle".equals(weaponId)) return;
⋮----
public static void enableCinderFurnace() {
if (!"cinder_cannon".equals(weaponId)) return;
⋮----
/** Called exactly once per spawned player projectile. */
public static ShotModifier consumeShot(boolean alreadyCritical) {
⋮----
if ("ion_needle".equals(weaponId) && shotIndex % ionCadence == 0) {
⋮----
return new ShotModifier(Kind.ION_OVERCHARGE, true, true,
⋮----
if ("cinder_cannon".equals(weaponId) && shotIndex % cinderCadence == 0) {
return new ShotModifier(Kind.CINDER_OVERHEAT, true, false,
⋮----
// Tempest fires three-projectile bursts: every second burst ends in a penetrating surge.
if ("tempest_burst".equals(weaponId) && shotIndex % 6 == 0) {
return new ShotModifier(Kind.TEMPEST_SURGE, true, false,
⋮----
// Whiteout fires four shards: every second volley lands one oversized control shard.
if ("whiteout_shard".equals(weaponId) && shotIndex % 8 == 0) {
return new ShotModifier(Kind.WHITEOUT_SHATTER, true, false,
⋮----
// Phoenix is a precision repeater: every fifth round becomes a heavier ignition shot.
if ("phoenix_repeater".equals(weaponId) && shotIndex % 5 == 0) {
return new ShotModifier(Kind.PHOENIX_IGNITION, true, false,
⋮----
return ShotModifier.none();
⋮----
public static int shotIndex() { return shotIndex; }
public static String weaponId() { return weaponId; }
public static boolean ionCascadeEnabled() { return ionCascade; }
public static boolean cinderFurnaceEnabled() { return cinderFurnace; }
```

## File: src/main/java/com/deadlinezero/game/config/AccessibilitySettings.java
```java
/** Persistent comfort/accessibility options kept independent from gameplay balance. */
public final class AccessibilitySettings {
⋮----
public static AccessibilitySettings load() {
AccessibilitySettings s = new AccessibilitySettings();
Preferences p = Gdx.app.getPreferences(PREFS);
s.screenShake = p.getBoolean("screenShake", true);
s.screenShakeStrength = p.getFloat("screenShakeStrength", 1f);
s.hitStop = p.getBoolean("hitStop", true);
s.damageFlash = p.getBoolean("damageFlash", true);
s.highContrastTelegraphs = p.getBoolean("highContrastTelegraphs", false);
s.reduceFlashes = p.getBoolean("reduceFlashes", false);
s.reducedMotion = p.getBoolean("reducedMotion", false);
s.haptics = p.getBoolean("haptics", true);
s.colorVisionMode = ColorVisionMode.fromStored(p.getString("colorVisionMode", ColorVisionMode.STANDARD.name()));
s.uiScale = p.getFloat("uiScale", 1f);
s.masterVolume = p.getFloat("masterVolume", 1f);
s.sfxVolume = p.getFloat("sfxVolume", .85f);
s.musicVolume = p.getFloat("musicVolume", .65f);
s.normalize();
⋮----
public static AccessibilitySettings active() {
if (active == null) active = new AccessibilitySettings();
⋮----
/** Keeps all scalar comfort settings inside the ranges supported by runtime render/audio systems. */
public void normalize() {
screenShakeStrength = clampFinite(screenShakeStrength, 0f, 1f, 1f);
uiScale = clampFinite(uiScale, .85f, 1.35f, 1f);
masterVolume = clampFinite(masterVolume, 0f, 1f, 1f);
sfxVolume = clampFinite(sfxVolume, 0f, 1f, .85f);
musicVolume = clampFinite(musicVolume, 0f, 1f, .65f);
if (reducedMotion) enforceReducedMotion();
⋮----
/** One-switch comfort preset. Audio/UI preferences are intentionally untouched. */
public void setReducedMotion(boolean enabled) {
⋮----
enforceReducedMotion();
⋮----
private void enforceReducedMotion() {
⋮----
public boolean motionControlLocked() { return reducedMotion; }
public boolean allowsScreenShake() { return !reducedMotion && screenShake && screenShakeStrength > 0f; }
public boolean allowsHitStop() { return !reducedMotion && hitStop; }
public boolean minimizesFlashes() { return reducedMotion || reduceFlashes; }
⋮----
public void save() {
normalize();
⋮----
Gdx.app.getPreferences(PREFS)
.putBoolean("screenShake", screenShake)
.putFloat("screenShakeStrength", screenShakeStrength)
.putBoolean("hitStop", hitStop)
.putBoolean("damageFlash", damageFlash)
.putBoolean("highContrastTelegraphs", highContrastTelegraphs)
.putBoolean("reduceFlashes", reduceFlashes)
.putBoolean("reducedMotion", reducedMotion)
.putBoolean("haptics", haptics)
.putString("colorVisionMode", colorVisionMode.name())
.putFloat("uiScale", uiScale)
.putFloat("masterVolume", masterVolume)
.putFloat("sfxVolume", sfxVolume)
.putFloat("musicVolume", musicVolume)
.flush();
⋮----
public ColorVisionMode next(int direction) {
ColorVisionMode[] modes = values();
int index = (ordinal() + (direction >= 0 ? 1 : -1) + modes.length) % modes.length;
⋮----
static ColorVisionMode fromStored(String value) {
try { return value == null ? STANDARD : valueOf(value); }
⋮----
private static float clampFinite(float v, float min, float max, float fallback) {
return Float.isFinite(v) ? Math.max(min, Math.min(max, v)) : fallback;
```

## File: src/main/java/com/deadlinezero/game/config/GameConfig.java
```java
public final class GameConfig {
⋮----
// Hard safety caps, not encounter targets. Keep enough headroom for endgame builds while
// preventing pathological pool scans/collision work from dominating a 60 FPS mobile frame.
```

## File: src/main/java/com/deadlinezero/game/config/GraphicsSettings.java
```java
/** Persistent user-selected rendering ceiling and best-effort frame-rate target. */
public final class GraphicsSettings {
⋮----
public Quality next(int direction) {
Quality[] values = values();
int index = Math.max(0, Math.min(values.length - 1, ordinal() + Integer.signum(direction)));
⋮----
public FrameRate next(int direction) {
FrameRate[] values = values();
⋮----
public static Quality load() {
Preferences prefs = Gdx.app.getPreferences(PREFS);
String rawQuality = prefs.getString("quality", Quality.ULTRA.name());
String rawFrameRate = prefs.getString("frameRate", FrameRate.FPS_60.name());
try { active = Quality.valueOf(rawQuality); }
⋮----
try { frameRate = FrameRate.valueOf(rawFrameRate); }
⋮----
applyFrameRate();
⋮----
public static Quality active() { return active; }
public static FrameRate frameRate() { return frameRate; }
⋮----
public static void set(Quality quality) {
⋮----
public static void setFrameRate(FrameRate next) {
⋮----
public static void save() {
Gdx.app.getPreferences(PREFS)
.putString("quality", active.name())
.putString("frameRate", frameRate.name())
.flush();
⋮----
public static float fxCeiling() { return active.fxCeiling; }
⋮----
public static void applyFrameRate() {
if (Gdx.graphics != null) Gdx.graphics.setForegroundFPS(frameRate.target);
```

## File: src/main/java/com/deadlinezero/game/config/Localization.java
```java
/**
 * Centralized runtime localization facade.
 *
 * The initial shipping catalog is English-only, but all callers use stable keys so additional
 * locales can be added without changing screen logic.
 */
public final class Localization {
⋮----
public static Localization loadEnglish() {
I18NBundle bundle = I18NBundle.createBundle(
Gdx.files.internal("i18n/messages"),
⋮----
return new Localization(bundle);
⋮----
public String text(String key) {
if (key == null || key.isBlank()) return "";
⋮----
return sanitizeForBitmapFont(bundle.get(key));
⋮----
return sanitizeForBitmapFont(key);
⋮----
public String format(String key, Object... args) {
⋮----
return sanitizeForBitmapFont(bundle.format(key, args));
⋮----
/**
     * Normalizes punctuation that is not present in libGDX's bundled default BitmapFont and keeps
     * accidental unresolved MessageFormat tokens from leaking into visible UI copy.
     */
public static String sanitizeForBitmapFont(String value) {
if (value == null || value.isEmpty()) return "";
⋮----
.replace("•", "|")
.replace("‹", "<")
.replace("›", ">")
.replace("←", "<-")
.replace("→", "->")
.replace("↑", "^")
.replace("↓", "v")
.replace("–", "-")
.replace("—", "-")
.replace("…", "...");
sanitized = sanitized.replaceAll("\\{\\d+\\}\\s*\\|\\s*", "");
sanitized = sanitized.replaceAll("\\{\\d+\\}", "");
sanitized = sanitized.replaceFirst("^ESC\\s*\\|\\s*", "");
return sanitized.trim();
```

## File: src/main/java/com/deadlinezero/game/entities/ActorState.java
```java
public abstract class ActorState {
public final Vector2 position = new Vector2();
public final Vector2 velocity = new Vector2();
⋮----
position.set(x, y); this.radius = radius; this.hp = hp; this.maxHp = hp;
⋮----
public void damage(float amount) {
if (!alive || amount <= 0f || !Float.isFinite(amount)) return;
⋮----
float effective = Math.max(0f, before - hp);
if (this instanceof Enemy) BalanceTelemetryRuntime.recordDamageDealt(effective);
else if (this instanceof Player) BalanceTelemetryRuntime.recordDamageReceived(effective);
```

## File: src/main/java/com/deadlinezero/game/entities/Enemy.java
```java
public final class Enemy extends ActorState {
⋮----
public final Vector2 impulse = new Vector2();
⋮----
super(x, y, radius, hp * StageRules.enemyHpMultiplier(RunStageContext.stage()));
int stage = RunStageContext.stage();
⋮----
this.speed = speed * StageRules.enemySpeedMultiplier(stage);
this.contactDamage = damage * StageRules.enemyDamageMultiplier(stage);
this.xpValue = Math.max(1, Math.round(xp * (1f + (stage - 1) * .035f)));
applyTypeProfile();
⋮----
this.attack = new AttackController(archetype);
this.bossPhases = type == Type.BOSS ? new BossPhaseController() : null;
this.bossCombat = type == Type.BOSS ? new BossCombatRuntime() : null;
configureAttackCadence();
⋮----
float chance = MathUtils.clamp(.02f + (stage - 1) * .018f, .02f, .20f);
if (MathUtils.random() < chance) applyVariant(variantForRoll(MathUtils.random()));
⋮----
configureSpecialTrait();
ACTIVE_ENEMIES.put(this, Boolean.TRUE);
⋮----
private void applyTypeProfile() {
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.65f));
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.48f));
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.58f));
⋮----
private void configureSpecialTrait() {
⋮----
/** Stable equal-width selector for the eight production champion variants. */
public static Variant variantForRoll(float roll) {
float safe = MathUtils.clamp(roll, 0f, .999999f);
⋮----
/** Applies a champion variant once, preserving the base archetype while changing combat priorities. */
public void applyVariant(Variant next) {
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.20f));
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.55f));
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.45f));
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.40f));
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.78f));
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.66f));
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.62f));
⋮----
xpValue = Math.max(1, Math.round(xpValue * 1.50f));
⋮----
private void configureAttackCadence() {
⋮----
attack.setCadence(cooldown, telegraph, recovery);
⋮----
@Override public void damage(float amount) {
if (!alive || amount <= 0f || !Float.isFinite(amount)) return;
⋮----
float absorbed = Math.min(shieldHp, remaining);
⋮----
hitFlash = Math.max(hitFlash, .45f);
⋮----
if (type == Type.PHANTOM && phased()) remaining *= .22f;
⋮----
super.damage(remaining);
if (wasAlive && !alive && type == Type.BOSS) RunMissionRuntime.signalBossDefeated();
⋮----
/**
     * Applies elemental status and resolves deterministic cross-element reactions.
     * Biome signature enemies attenuate their resisted element here, once, before status/reaction math.
     */
public void applyElement(DamageElement element, float power) {
⋮----
float resistance = BiomeEnemyRoster.elementalDamageMultiplier(RunStageContext.stage(), type, element);
float safePower = Math.max(0f, power) * resistance;
⋮----
damage(safePower * .34f);
⋮----
triggerReaction(ElementReaction.THERMAL_SHOCK);
⋮----
burnTimer = Math.max(burnTimer, 2.4f * resistance);
burnDps = Math.max(burnDps, safePower * .22f);
⋮----
damage(safePower * .28f);
⋮----
triggerReaction(ElementReaction.STEAM_BURST);
⋮----
slowTimer = Math.max(slowTimer, 1.6f * resistance);
slowMultiplier = Math.min(slowMultiplier, MathUtils.lerp(1f, .62f, resistance));
⋮----
damage(safePower * (burning && frozen ? .34f : .22f));
⋮----
triggerReaction(ElementReaction.OVERLOAD);
⋮----
shockTimer = Math.max(shockTimer, stun);
attack.forceStunned(stun);
⋮----
private void triggerReaction(ElementReaction reaction) {
⋮----
hitFlash = Math.max(hitFlash, .72f);
⋮----
public void addImpulse(float x, float y) {
⋮----
impulse.add(x * resistance, y * resistance);
⋮----
public void updateStatus(float dt) {
⋮----
attack.markDead();
⋮----
float safeDt = Math.max(0f, dt);
⋮----
specialRecoveryDelay = Math.max(0f, specialRecoveryDelay - safeDt);
supportBuffTimer = Math.max(0f, supportBuffTimer - safeDt);
supportHealLockout = Math.max(0f, supportHealLockout - safeDt);
supportPulseFlash = Math.max(0f, supportPulseFlash - safeDt);
BiomeEnemyBehaviorRules.Profile behavior = biomeBehavior();
⋮----
shieldHp = Math.min(shieldMaxHp, shieldHp + shieldMaxHp * .18f * safeDt);
⋮----
hp = Math.min(maxHp, hp + maxHp * .035f * behavior.recoveryMultiplier() * safeDt);
⋮----
updateNullWardSupport(safeDt);
tacticalCooldown = Math.max(0f, tacticalCooldown - dt);
tacticalWindup = Math.max(0f, tacticalWindup - dt);
chargeImpactWindow = Math.max(0f, chargeImpactWindow - dt);
reactionFlash = Math.max(0f, reactionFlash - dt);
⋮----
damage(burnDps * dt);
⋮----
attack.updateStun(dt);
hitFlash = Math.max(0f, hitFlash - dt * 6f);
⋮----
float damping = MathUtils.clamp(1f - dt * (heavy ? 12f : 8f), 0f, 1f);
impulse.scl(damping);
⋮----
bossPhases.update(maxHp <= 0f ? 0f : hp / maxHp);
bossCombat.update(dt, bossPhases.phase());
⋮----
private void updateNullWardSupport(float dt) {
if (biomeIdentity() != BiomeEnemyRoster.Identity.NULL_WARD) return;
⋮----
for (Enemy ally : ACTIVE_ENEMIES.keySet()) {
⋮----
if (position.dst2(ally.position) > radius2) continue;
⋮----
ally.hp = Math.min(ally.maxHp, ally.hp + ally.maxHp * NULL_WARD_HEAL_FRACTION);
⋮----
ally.supportBuffTimer = Math.max(ally.supportBuffTimer, NULL_WARD_BUFF_SECONDS);
⋮----
public void updateAi(float dt, float distanceToPlayer) {
if (!alive || attack.state() == EnemyState.STUNNED) return;
attack.update(dt, distanceToPlayer);
updateTactics(distanceToPlayer);
⋮----
private void updateTactics(float distanceToPlayer) {
⋮----
executePendingTactic();
⋮----
boolean canStrafe = type == Type.RANGED || behavior.evasiveStrafe();
boolean tacticalState = attack.state() == EnemyState.CHASING
|| (canStrafe && attack.state() == EnemyState.HOLDING_RANGE);
⋮----
cadence *= behavior.tacticCooldownMultiplier();
⋮----
tacticalCooldown = (1.45f + MathUtils.random(.35f)) * cadence;
tacticSide = MathUtils.randomBoolean() ? 1f : -1f;
} else if ((type == Type.BRUTE || type == Type.ELITE || type == Type.SHIELDED || behavior.aggressiveCharge())
⋮----
private void executePendingTactic() {
float len = velocity.len();
⋮----
float strength = variantStrength * behavior.strafeStrengthMultiplier();
impulse.add(-ny * strength * tacticSide, nx * strength * tacticSide);
⋮----
strength *= behavior.chargeStrengthMultiplier();
impulse.add(nx * strength, ny * strength);
⋮----
public Tactic pendingTactic() { return pendingTactic; }
public boolean tacticalTelegraph() { return pendingTactic != Tactic.NONE && tacticalWindup > 0f; }
public boolean chargeImpactActive() { return chargeImpactWindow > 0f && !chargeImpactConsumed; }
public boolean consumeChargeImpact() {
if (!chargeImpactActive()) return false;
⋮----
public boolean phased() {
⋮----
public float shieldFraction() {
return shieldMaxHp <= 0f ? 0f : MathUtils.clamp(shieldHp / shieldMaxHp, 0f, 1f);
⋮----
public BiomeEnemyRoster.Identity biomeIdentity() {
return BiomeEnemyRoster.identityFor(RunStageContext.stage(), type);
⋮----
public BiomeEnemyBehaviorRules.Profile biomeBehavior() {
return BiomeEnemyBehaviorRules.forIdentity(biomeIdentity());
⋮----
public boolean supportBuffed() { return supportBuffTimer > 0f; }
public float supportPulseFlash() { return supportPulseFlash; }
public static float nullWardPulseInterval() { return NULL_WARD_PULSE_INTERVAL; }
public static float nullWardPulseRadius() { return NULL_WARD_PULSE_RADIUS; }
⋮----
public float effectiveSpeed() {
if (attack.state() == EnemyState.STUNNED) return 0f;
float phaseMultiplier = bossPhases == null ? 1f : bossPhases.speedMultiplier();
float chargeMultiplier = bossCombat != null && bossCombat.charging() ? 3.4f : 1f;
⋮----
if (phased()) variantMultiplier *= 1.36f;
⋮----
if (behavior.burstMultiplier() > 1f && variantTime % 3.6f < .42f) burst = behavior.burstMultiplier();
⋮----
* behavior.speedMultiplier() * burst * support;
```

## File: src/main/java/com/deadlinezero/game/entities/EnemyProjectile.java
```java
/** Pooled hostile projectile used by ranged enemies and boss patterns. */
public final class EnemyProjectile {
⋮----
public final Vector2 position = new Vector2();
public final Vector2 velocity = new Vector2();
⋮----
public EnemyProjectile spawn(float x, float y, float vx, float vy, float damage,
⋮----
return spawn(x, y, vx, vy, damage, radius, life, explosive, explosionRadius,
defaultStyleForActiveContext(radius, explosive));
⋮----
position.set(x, y);
velocity.set(vx, vy);
⋮----
this.style = style == null ? defaultStyleForActiveContext(radius, explosive) : style;
⋮----
public static Style defaultStyleForActiveBiome() {
return defaultStyleForActiveContext(.24f, false);
⋮----
/**
     * Default presentation when the caller does not provide a source identity. In Null Sector,
     * thin non-explosive hostile volleys are the Static Seer pattern; boss/large shots remain NULL.
     */
public static Style defaultStyleForActiveContext(float radius, boolean explosive) {
return switch (EnvironmentBiomeRules.forStage(RunStageContext.stage())) {
```

## File: src/main/java/com/deadlinezero/game/entities/HomingMissile.java
```java
/** Pooled player missile with allocation-free steering. */
public final class HomingMissile {
public final Vector2 position = new Vector2();
public final Vector2 velocity = new Vector2();
⋮----
public HomingMissile spawn(float x, float y, Enemy target, float speed, float turnRateDeg,
⋮----
position.set(x, y);
⋮----
if (target != null) velocity.set(target.position).sub(position).nor().scl(speed);
else velocity.set(speed, 0f);
⋮----
public void update(float dt) {
⋮----
float desired = MathUtils.atan2(target.position.y - position.y, target.position.x - position.x) * MathUtils.radiansToDegrees;
float current = velocity.angleDeg();
⋮----
current += MathUtils.clamp(delta, -maxTurn, maxTurn);
velocity.set(speed, 0f).setAngleDeg(current);
⋮----
position.mulAdd(velocity, dt);
```

## File: src/main/java/com/deadlinezero/game/entities/Player.java
```java
public final class Player extends ActorState {
⋮----
public float moveSpeed = GameConfig.PLAYER_SPEED * RunLoadoutContext.moveSpeedMultiplier();
public float dashCooldown = 3.2f * RunLoadoutContext.dashCooldownMultiplier();
⋮----
public final WeaponRuntime weapon = new WeaponRuntime(RunLoadoutContext.weaponDefinition());
public final AbilityLoadout abilities = new AbilityLoadout();
public final LegendaryState legendary = new LegendaryState();
public final CombatProtocolState protocols = new CombatProtocolState();
private final MobileCombatInput mobileCombatInput = new MobileCombatInput();
private final Vector2 dashDirection = new Vector2();
⋮----
super(x, y, 0.42f, GameConfig.PLAYER_MAX_HP * RunLoadoutContext.maxHpMultiplier());
weapon.damage *= RunLoadoutContext.weaponDamageMultiplier();
weapon.critChance = Math.min(.75f, weapon.critChance + RunLoadoutContext.critChanceBonus());
weapon.critMultiplier += RunLoadoutContext.critDamageBonus();
for (int i = 0; i < RunLoadoutContext.startingTeslaLevel(); i++) abilities.upgrade(AbilityType.TESLA_ORB);
⋮----
public boolean canDash() { return dashTimer <= 0f; }
⋮----
public void triggerDash() {
⋮----
invulnerabilityTimer = Math.max(invulnerabilityTimer, RunLoadoutContext.dashInvulnerabilitySeconds());
⋮----
public boolean invulnerable() { return invulnerabilityTimer > 0f; }
⋮----
public void updateRuntime(float dt) {
dashTimer = Math.max(0f, dashTimer - dt);
invulnerabilityTimer = Math.max(0f, invulnerabilityTimer - dt);
visualHitTimer = Math.max(0f, visualHitTimer - dt);
⋮----
AccessibilitySettings settings = AccessibilitySettings.active();
if (canDash() && velocity.len2() > .08f && mobileCombatInput.dashJustPressed(settings.uiScale)) {
dashDirection.set(velocity).nor();
position.mulAdd(dashDirection, 4.8f);
triggerDash();
CombatVisualEvents.markDash();
AudioDirector.playGlobal(AudioDirector.Cue.DASH);
⋮----
try { Gdx.input.vibrate(18); } catch (Throwable ignored) { }
⋮----
@Override public void damage(float amount) {
if (invulnerable()) return;
⋮----
super.damage(amount * RunLoadoutContext.damageTakenMultiplier());
if (hp < before) visualHitTimer = Math.max(visualHitTimer, .16f);
⋮----
public boolean addXp(int amount) {
⋮----
xpNext = Math.round(xpNext * 1.32f + 8);
CombatVisualEvents.markLevelUp();
```

## File: src/main/java/com/deadlinezero/game/entities/Projectile.java
```java
public final class Projectile {
public final Vector2 position = new Vector2();
public final Vector2 velocity = new Vector2();
⋮----
public Projectile spawn(float x, float y, float vx, float vy, float damage, boolean critical,
⋮----
position.set(x, y);
velocity.set(vx, vy);
WeaponSignatureRuntime.ShotModifier signature = WeaponSignatureRuntime.consumeShot(critical);
singularity = SingularityCoreRuntime.consumeShotMark();
weaponSignature = signature.active();
weaponSignatureKind = signature.kind();
float signatureDamage = damage * signature.damageMultiplier();
⋮----
this.critical = critical || signature.forceCritical();
this.penetrationRemaining = penetration + signature.penetrationBonus() + (singularity ? 2 : 0);
float signatureKnockback = knockback * signature.knockbackMultiplier();
⋮----
this.radius = singularity ? Math.max(.16f, signature.radius()) : signature.radius();
⋮----
case ION_OVERCHARGE -> AudioDirector.playGlobal(AudioDirector.Cue.ION_OVERCHARGE, 1.08f, 0f);
case CINDER_OVERHEAT -> AudioDirector.playGlobal(AudioDirector.Cue.CINDER_OVERHEAT, .82f, 0f);
case TEMPEST_SURGE -> AudioDirector.playGlobal(AudioDirector.Cue.TEMPEST_SURGE, 1.18f, 0f);
case WHITEOUT_SHATTER -> AudioDirector.playGlobal(AudioDirector.Cue.WHITEOUT_SHATTER, .92f, 0f);
case PHOENIX_IGNITION -> AudioDirector.playGlobal(AudioDirector.Cue.PHOENIX_IGNITION, 1.02f, 0f);
```

## File: src/main/java/com/deadlinezero/game/fx/ArcFx.java
```java
/** Short-lived pooled electric arc used for Tesla/SHOCK presentation. */
public final class ArcFx {
⋮----
public ArcFx spawn(float x1, float y1, float x2, float y2, float duration) {
⋮----
this.maxLife = Math.max(.04f, duration);
⋮----
this.expiresAtNanos = TimeUtils.nanoTime() + (long)(this.maxLife * 1_000_000_000L);
⋮----
public void update(float dt) {
⋮----
/** Keeps pooled arcs safe even when the simulation update is paused during overlays. */
public boolean refreshFromClock() {
⋮----
long remaining = expiresAtNanos - TimeUtils.nanoTime();
⋮----
life = Math.min(maxLife, remaining / 1_000_000_000f);
```

## File: src/main/java/com/deadlinezero/game/fx/DamageNumber.java
```java
/** Allocation-free floating combat text entry. */
public final class DamageNumber {
⋮----
public final Color color = new Color(Color.WHITE);
⋮----
public DamageNumber spawn(float x, float y, float value, boolean critical, Color color) {
⋮----
this.text = Integer.toString(Math.max(1, Math.round(value)));
⋮----
this.color.set(color);
⋮----
public void update(float dt) {
```

## File: src/main/java/com/deadlinezero/game/fx/DeathFx.java
```java
/** Long-lived pooled death presentation generated when enemies die. */
public final class DeathFx {
⋮----
public void spawn(Enemy.Type type, float x, float y, float angleDeg, float radius, float duration) {
⋮----
public void update(float dt) {
⋮----
float step = Math.max(0f, dt);
```

## File: src/main/java/com/deadlinezero/game/fx/ImpactFx.java
```java
public final class ImpactFx {
public final Vector2 position = new Vector2();
⋮----
public final Color color = new Color();
⋮----
public ImpactFx spawn(float x, float y, float size, float duration, Color c) {
position.set(x,y); this.size=size; this.life=this.maxLife=duration; this.color.set(c); active=true; return this;
```

## File: src/main/java/com/deadlinezero/game/input/MobileCombatInput.java
```java
/** Multi-touch combat actions kept separate from movement stick ownership. */
public final class MobileCombatInput {
⋮----
public boolean dashJustPressed(float uiScale) {
float w = Gdx.graphics.getWidth();
float h = Gdx.graphics.getHeight();
float s = Math.max(.85f, Math.min(1.35f, uiScale));
⋮----
if (!Gdx.input.isTouched(pointer)) continue;
float x = Gdx.input.getX(pointer);
float y = h - Gdx.input.getY(pointer);
⋮----
public static boolean dashDown() { return dashDownGlobal; }
```

## File: src/main/java/com/deadlinezero/game/input/VirtualStick.java
```java
/** Floating left-side movement stick with keyboard fallback and stable multi-touch ownership. */
public final class VirtualStick {
⋮----
private final Vector2 value = new Vector2();
private final Vector2 origin = new Vector2();
⋮----
public Vector2 update(float worldW, float worldH) {
⋮----
if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) x--;
if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x++;
if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) y++;
if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) y--;
⋮----
return value.set(x, y).nor();
⋮----
final float screenW = Gdx.graphics.getWidth();
final float screenH = Gdx.graphics.getHeight();
⋮----
if (pointer >= 0 && !Gdx.input.isTouched(pointer)) pointer = -1;
⋮----
if (!Gdx.input.isTouched(p)) continue;
float sx = Gdx.input.getX(p);
⋮----
origin.set(sx, screenH - Gdx.input.getY(p));
⋮----
if (pointer < 0) return value.setZero();
⋮----
float sx = Gdx.input.getX(pointer);
float sy = screenH - Gdx.input.getY(pointer);
value.set(sx - origin.x, sy - origin.y);
⋮----
float max = Math.max(64f, screenH * .12f);
⋮----
float length = value.len();
if (length <= deadZone) return value.setZero();
if (length > max) value.setLength(max);
⋮----
float normalized = MathUtils.clamp((Math.min(length, max) - deadZone) / (max - deadZone), 0f, 1f);
return value.nor().scl(normalized);
⋮----
public boolean active() { return pointer >= 0; }
public float originX() { return origin.x; }
public float originY() { return origin.y; }
public float valueX() { return value.x; }
public float valueY() { return value.y; }
⋮----
public static boolean hudActive() { return activeInstance != null && activeInstance.active(); }
public static float hudOriginX() { return activeInstance == null ? 0f : activeInstance.originX(); }
public static float hudOriginY() { return activeInstance == null ? 0f : activeInstance.originY(); }
public static float hudValueX() { return activeInstance == null ? 0f : activeInstance.valueX(); }
public static float hudValueY() { return activeInstance == null ? 0f : activeInstance.valueY(); }
```

## File: src/main/java/com/deadlinezero/game/meta/AchievementProgress.java
```java
/** Persistent one-time achievement state. */
public final class AchievementProgress {
private final EnumSet<AchievementService.Achievement> claimed = EnumSet.noneOf(AchievementService.Achievement.class);
⋮----
public boolean claimed(AchievementService.Achievement achievement) {
return achievement != null && claimed.contains(achievement);
⋮----
public boolean markClaimed(AchievementService.Achievement achievement) {
return achievement != null && claimed.add(achievement);
⋮----
public Set<AchievementService.Achievement> claimed() { return Set.copyOf(claimed); }
```

## File: src/main/java/com/deadlinezero/game/meta/AchievementService.java
```java
/** Permanent account achievements derived from canonical lifetime progression. */
public final class AchievementService {
⋮----
public String titleKey() { return "achievement." + name().toLowerCase(java.util.Locale.ROOT) + ".title"; }
public String descriptionKey() { return "achievement." + name().toLowerCase(java.util.Locale.ROOT) + ".description"; }
⋮----
public static boolean unlocked(PlayerProfile profile, Achievement achievement) {
⋮----
public static boolean claim(PlayerProfile profile, Achievement achievement) {
if (!unlocked(profile, achievement) || profile.achievements.claimed(achievement)) return false;
if (!profile.achievements.markClaimed(achievement)) return false;
profile.addCurrency(PlayerProfile.Currency.CREDITS, achievement.credits);
profile.addCurrency(PlayerProfile.Currency.GEMS, achievement.gems);
```

## File: src/main/java/com/deadlinezero/game/meta/BalanceCoefficientAudit.java
```java
/** Static guardrails for deterministic difficulty curves. Used by tests to catch accidental balance spikes. */
public final class BalanceCoefficientAudit {
⋮----
public static boolean stageCurvesHealthy() {
RunModifierContext.end();
⋮----
RunStageContext.begin(stage - 1, 0, 0);
float previousHp = StageRules.enemyHpMultiplier(stage - 1);
float previousDamage = StageRules.enemyDamageMultiplier(stage - 1);
float previousReward = StageRules.rewardMultiplier(stage - 1);
RunStageContext.begin(stage, 0, 0);
float hp = StageRules.enemyHpMultiplier(stage);
float damage = StageRules.enemyDamageMultiplier(stage);
float reward = StageRules.rewardMultiplier(stage);
if (!stepHealthy(previousHp, hp, MAX_STAGE_HP_STEP)
|| !stepHealthy(previousDamage, damage, MAX_STAGE_DAMAGE_STEP)
|| !stepHealthy(previousReward, reward, MAX_STAGE_REWARD_STEP)) return false;
⋮----
public static boolean threatCurvesHealthy() {
⋮----
if (!stepHealthy(ThreatTierRules.enemyHpMultiplier(tier - 1), ThreatTierRules.enemyHpMultiplier(tier), MAX_THREAT_HP_STEP)
|| !stepHealthy(ThreatTierRules.enemyDamageMultiplier(tier - 1), ThreatTierRules.enemyDamageMultiplier(tier), MAX_THREAT_DAMAGE_STEP)
|| !stepHealthy(ThreatTierRules.rewardMultiplier(tier - 1), ThreatTierRules.rewardMultiplier(tier), MAX_THREAT_REWARD_STEP)) return false;
⋮----
float ratio = ThreatTierRules.rewardMultiplier(ThreatTierRules.MAX_TIER) / ThreatTierRules.rewardMultiplier(0);
⋮----
private static boolean stepHealthy(float previous, float current, float maxRatio) {
if (!Float.isFinite(previous) || !Float.isFinite(current) || previous <= 0f || current < previous) return false;
```

## File: src/main/java/com/deadlinezero/game/meta/BalanceHealthRules.java
```java
/** Conservative health classification for local balance telemetry segments. */
public final class BalanceHealthRules {
⋮----
public static Assessment assess(BalanceTelemetrySummary.Summary summary) {
if (summary == null || summary.runs() < MIN_CONFIDENT_RUNS) {
int runs = summary == null ? 0 : summary.runs();
return new Assessment(Status.LOW_SAMPLE, "Need " + Math.max(0, MIN_CONFIDENT_RUNS - runs) + " more runs");
⋮----
if (summary.winRate() < HARD_WIN_RATE) {
return new Assessment(Status.TOO_HARD, "Win rate " + percent(summary.winRate()) + " < " + percent(HARD_WIN_RATE));
⋮----
if (summary.winRate() > EASY_WIN_RATE) {
return new Assessment(Status.TOO_EASY, "Win rate " + percent(summary.winRate()) + " > " + percent(EASY_WIN_RATE));
⋮----
if (summary.averageSeconds() < MIN_HEALTHY_SECONDS) {
return new Assessment(Status.TOO_SHORT, "Average run below " + Math.round(MIN_HEALTHY_SECONDS) + "s");
⋮----
if (summary.averageSeconds() > MAX_HEALTHY_SECONDS) {
return new Assessment(Status.TOO_LONG, "Average run above " + Math.round(MAX_HEALTHY_SECONDS) + "s");
⋮----
return new Assessment(Status.HEALTHY, "Within target envelope");
⋮----
private static String percent(float value) { return Math.round(value * 100f) + "%"; }
```

## File: src/main/java/com/deadlinezero/game/meta/BalanceRunSample.java
```java
/** Immutable, non-identifying local run telemetry used only for game balancing. */
⋮----
sequence = Math.max(0L, sequence);
stage = Math.max(1, stage);
threatTier = ThreatTierRules.sanitizeTier(threatTier);
runOrdinal = Math.max(0, runOrdinal);
seconds = finiteNonNegative(seconds);
kills = Math.max(0, kills);
damageDealt = finiteNonNegative(damageDealt);
damageReceived = finiteNonNegative(damageReceived);
maxHitDealt = finiteNonNegative(maxHitDealt);
maxHitReceived = finiteNonNegative(maxHitReceived);
contract = safeText(contract, "STANDARD");
mutator = safeText(mutator, "STANDARD PRESSURE");
survivor = safeText(survivor, "REX");
weaponId = safeText(weaponId, "ar9");
ascensionSetPieces = Math.max(0, Math.min(4, ascensionSetPieces));
⋮----
public float dps() { return seconds <= .001f ? 0f : damageDealt / seconds; }
public float damageTakenPerMinute() { return seconds <= .001f ? 0f : damageReceived * 60f / seconds; }
public float killsPerMinute() { return seconds <= .001f ? 0f : kills * 60f / seconds; }
⋮----
private static float finiteNonNegative(float value) {
return Float.isFinite(value) ? Math.max(0f, value) : 0f;
⋮----
private static String safeText(String value, String fallback) {
if (value == null || value.isBlank()) return fallback;
String trimmed = value.trim();
return trimmed.length() > 48 ? trimmed.substring(0, 48) : trimmed;
```

## File: src/main/java/com/deadlinezero/game/meta/BalanceTelemetryReport.java
```java
/** Produces actionable, read-only balance diagnostics from recent local telemetry. */
public final class BalanceTelemetryReport {
⋮----
public static Report analyze(Array<BalanceRunSample> samples) {
BalanceTelemetrySummary.Summary overall = BalanceTelemetrySummary.summarize(samples);
BalanceHealthRules.Assessment overallHealth = BalanceHealthRules.assess(overall);
⋮----
for (BalanceTelemetrySegments.Dimension dimension : BalanceTelemetrySegments.Dimension.values()) {
List<BalanceTelemetrySegments.Segment> segments = BalanceTelemetrySegments.group(samples, dimension);
⋮----
BalanceHealthRules.Assessment assessment = BalanceHealthRules.assess(segment.summary());
int rank = severity(assessment.status());
if (rank > worstRank || (rank == worstRank && worseEvidence(segment.summary(), worst == null ? null : worst.summary()))) {
⋮----
worst = new Outlier(dimension, segment.key(), segment.summary(), assessment);
⋮----
if (worst != null && worst.assessment().status() == BalanceHealthRules.Status.HEALTHY) worst = null;
return new Report(overall, overallHealth, worst);
⋮----
private static int severity(BalanceHealthRules.Status status) {
⋮----
private static boolean worseEvidence(BalanceTelemetrySummary.Summary candidate, BalanceTelemetrySummary.Summary current) {
⋮----
if (candidate.runs() != current.runs()) return candidate.runs() > current.runs();
return Math.abs(candidate.winRate() - .53f) > Math.abs(current.winRate() - .53f);
```

## File: src/main/java/com/deadlinezero/game/meta/BalanceTelemetryRuntime.java
```java
/** In-memory accumulator for one run. No network access and no player-identifying data. */
public final class BalanceTelemetryRuntime {
⋮----
public static void begin(int nextStage, int nextRunOrdinal, int nextThreatTier) {
⋮----
stage = Math.max(1, nextStage);
runOrdinal = Math.max(0, nextRunOrdinal);
threatTier = ThreatTierRules.sanitizeTier(nextThreatTier);
⋮----
mutator = EndgameMutatorRules.label();
⋮----
public static void setContract(String title) {
if (active && title != null && !title.isBlank()) contract = title;
⋮----
public static void recordDamageDealt(float amount) {
float safe = safeDamage(amount);
⋮----
damageDealt = safeAdd(damageDealt, safe);
maxHitDealt = Math.max(maxHitDealt, safe);
⋮----
public static void recordDamageReceived(float amount) {
⋮----
damageReceived = safeAdd(damageReceived, safe);
maxHitReceived = Math.max(maxHitReceived, safe);
⋮----
public static BalanceRunSample settle(boolean victory, float seconds, int kills) {
if (!active) begin(RunStageContext.stage(), RunStageContext.runOrdinal(), RunStageContext.threatTier());
BalanceRunSample sample = new BalanceRunSample(
⋮----
RunLoadoutContext.survivor().name(),
RunLoadoutContext.weaponDefinition().id,
RunLoadoutContext.ascensionSetPieces(),
RunLoadoutContext.zeroDayCoreEquipped()
⋮----
public static boolean active() { return active; }
⋮----
private static float safeDamage(float amount) {
return Float.isFinite(amount) ? Math.max(0f, amount) : 0f;
⋮----
private static float safeAdd(float a, float b) {
⋮----
return Float.isFinite(result) ? Math.max(0f, result) : Float.MAX_VALUE;
```

## File: src/main/java/com/deadlinezero/game/meta/BalanceTelemetrySegments.java
```java
/** Pure segmentation of local run telemetry for balance analysis. */
public final class BalanceTelemetrySegments {
⋮----
public static List<Segment> group(Array<BalanceRunSample> samples, Dimension dimension) {
if (samples == null || samples.size == 0 || dimension == null) return List.of();
⋮----
String key = key(sample, dimension);
buckets.computeIfAbsent(key, ignored -> new Array<>()).add(sample);
⋮----
List<Segment> result = new ArrayList<>(buckets.size());
for (Map.Entry<String, Array<BalanceRunSample>> entry : buckets.entrySet()) {
result.add(new Segment(entry.getKey(), BalanceTelemetrySummary.summarize(entry.getValue())));
⋮----
result.sort(segmentComparator(dimension));
return List.copyOf(result);
⋮----
private static String key(BalanceRunSample sample, Dimension dimension) {
⋮----
case STAGE -> Integer.toString(sample.stage());
case THREAT -> Integer.toString(sample.threatTier());
case CONTRACT -> sample.contract();
case MUTATOR -> sample.mutator();
case SURVIVOR -> sample.survivor();
case WEAPON -> sample.weaponId();
⋮----
private static Comparator<Segment> segmentComparator(Dimension dimension) {
⋮----
return Comparator.comparingInt(segment -> parseInt(segment.key()));
⋮----
return Comparator.comparing(Segment::key);
⋮----
private static int parseInt(String value) {
try { return Integer.parseInt(value); }
```

## File: src/main/java/com/deadlinezero/game/meta/BalanceTelemetryStore.java
```java
/** Local-only ring buffer for recent balancing samples. Never transmits data. */
public final class BalanceTelemetryStore {
⋮----
public static void append(BalanceRunSample sample) {
⋮----
Preferences p = Gdx.app.getPreferences(PREFS);
int cursor = Math.floorMod(p.getInteger("cursor", 0), CAPACITY);
⋮----
p.putLong(key + "seq", sample.sequence());
p.putInteger(key + "stage", sample.stage());
p.putInteger(key + "threat", sample.threatTier());
p.putInteger(key + "ordinal", sample.runOrdinal());
p.putBoolean(key + "victory", sample.victory());
p.putFloat(key + "seconds", sample.seconds());
p.putInteger(key + "kills", sample.kills());
p.putFloat(key + "dealt", sample.damageDealt());
p.putFloat(key + "received", sample.damageReceived());
p.putFloat(key + "maxDealt", sample.maxHitDealt());
p.putFloat(key + "maxReceived", sample.maxHitReceived());
p.putString(key + "contract", sample.contract());
p.putString(key + "mutator", sample.mutator());
p.putString(key + "survivor", sample.survivor());
p.putString(key + "weapon", sample.weaponId());
p.putInteger(key + "set", sample.ascensionSetPieces());
p.putBoolean(key + "zeroDay", sample.zeroDayCore());
p.putInteger("cursor", (cursor + 1) % CAPACITY);
p.putInteger("count", Math.min(CAPACITY, Math.max(0, p.getInteger("count", 0)) + 1));
p.flush();
⋮----
// Unit tests/headless tools may not have a libGDX application backend.
⋮----
public static Array<BalanceRunSample> loadRecent() {
⋮----
int count = Math.min(CAPACITY, Math.max(0, p.getInteger("count", 0)));
⋮----
int start = Math.floorMod(cursor - count, CAPACITY);
⋮----
out.add(new BalanceRunSample(
p.getLong(key + "seq", 0L),
p.getInteger(key + "stage", 1),
p.getInteger(key + "threat", 0),
p.getInteger(key + "ordinal", 0),
p.getBoolean(key + "victory", false),
p.getFloat(key + "seconds", 0f),
p.getInteger(key + "kills", 0),
p.getFloat(key + "dealt", 0f),
p.getFloat(key + "received", 0f),
p.getFloat(key + "maxDealt", 0f),
p.getFloat(key + "maxReceived", 0f),
p.getString(key + "contract", "STANDARD"),
p.getString(key + "mutator", "STANDARD PRESSURE"),
p.getString(key + "survivor", "REX"),
p.getString(key + "weapon", "ar9"),
p.getInteger(key + "set", 0),
p.getBoolean(key + "zeroDay", false)
```

## File: src/main/java/com/deadlinezero/game/meta/BalanceTelemetrySummary.java
```java
/** Pure aggregation helpers for recent local balancing telemetry. */
public final class BalanceTelemetrySummary {
⋮----
public static Summary summarize(Array<BalanceRunSample> samples) {
if (samples == null || samples.size == 0) return new Summary(0, 0, 0f, 0f, 0f, 0f, 0f);
⋮----
if (sample.victory()) wins++;
seconds += sample.seconds();
dps += sample.dps();
takenPerMinute += sample.damageTakenPerMinute();
killsPerMinute += sample.killsPerMinute();
⋮----
if (runs == 0) return new Summary(0, 0, 0f, 0f, 0f, 0f, 0f);
return new Summary(runs, wins, wins / (float)runs,
finite(seconds / runs), finite(dps / runs), finite(takenPerMinute / runs), finite(killsPerMinute / runs));
⋮----
private static float finite(double value) {
if (!Double.isFinite(value) || value <= 0d) return 0f;
```

## File: src/main/java/com/deadlinezero/game/meta/ChestService.java
```java
/** First chest economy. Purchases are explicit and always return an equipment item or null. */
public final class ChestService {
⋮----
public static EquipmentItem openCreditChest(PlayerProfile profile) {
if (profile == null || profile.inventory.full()) return null;
if (!profile.spend(PlayerProfile.Currency.CREDITS, CREDIT_CHEST_COST)) return null;
EquipmentItem item = EquipmentDropTable.roll(Math.max(1, profile.highestStage), false);
profile.inventory.add(item);
⋮----
public static EquipmentItem openGemChest(PlayerProfile profile) {
⋮----
if (!profile.spend(PlayerProfile.Currency.GEMS, GEM_CHEST_COST)) return null;
⋮----
EquipmentItem candidate = EquipmentDropTable.roll(Math.max(3, profile.highestStage + 2), true);
if (best == null || candidate.rarity.ordinal() > best.rarity.ordinal() ||
⋮----
profile.inventory.add(best);
```

## File: src/main/java/com/deadlinezero/game/meta/ConsumablePurchaseDelivery.java
```java
/** Crash-safe consumable delivery: grant and persist before asking the platform store to consume. */
public final class ConsumablePurchaseDelivery {
⋮----
public interface Persistence {
void save();
⋮----
public static boolean deliver(PlayerProfile profile, BillingService billing, BillingService.PurchaseReceipt receipt,
⋮----
if (!BillingService.isConsumable(receipt.productId())) return false;
if (receipt.receiptId() == null || receipt.receiptId().isBlank()) return false;
⋮----
boolean granted = PurchaseGrantService.grant(profile, receipt.productId(), receipt.receiptId());
persistence.save();
billing.finishConsumable(receipt.receiptId(),
() -> { if (onFinalized != null) onFinalized.accept(granted); },
```

## File: src/main/java/com/deadlinezero/game/meta/DailyCounterMath.java
```java
/** Overflow-safe arithmetic for daily mission counters. */
public final class DailyCounterMath {
⋮----
public static int increment(int current) {
return current >= Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.max(0, current) + 1;
⋮----
public static int addKills(int current, int kills) {
int safe = Math.max(0, current);
int add = Math.max(0, kills);
```

## File: src/main/java/com/deadlinezero/game/meta/DailyProgress.java
```java
/** Persistent daily progression snapshot. Date is stored as UTC epoch day. */
public final class DailyProgress {
⋮----
public void resetForDay(long day) {
⋮----
if (epochDay == day - 1L) loginStreak = Math.min(30, loginStreak + 1);
```

## File: src/main/java/com/deadlinezero/game/meta/DailyService.java
```java
/** Daily login and mission reward rules. */
public final class DailyService {
⋮----
public static void refresh(PlayerProfile profile, long epochDay) {
⋮----
profile.daily.resetForDay(epochDay);
⋮----
public static boolean claimLogin(PlayerProfile profile) {
⋮----
int streak = Math.max(1, profile.daily.loginStreak);
profile.addCurrency(PlayerProfile.Currency.CREDITS, 120L + streak * 35L);
if (streak % 3 == 0) profile.addCurrency(PlayerProfile.Currency.GEMS, 2L);
⋮----
public static void recordRun(PlayerProfile profile, int kills, boolean bossKilled) {
⋮----
profile.daily.runsToday = DailyCounterMath.increment(profile.daily.runsToday);
profile.daily.killsToday = DailyCounterMath.addKills(profile.daily.killsToday, kills);
if (bossKilled) profile.daily.bossesToday = DailyCounterMath.increment(profile.daily.bossesToday);
⋮----
public static boolean claimKillMission(PlayerProfile profile) {
⋮----
profile.addCurrency(PlayerProfile.Currency.CREDITS, 350L);
⋮----
public static boolean claimRunMission(PlayerProfile profile) {
⋮----
profile.addCurrency(PlayerProfile.Currency.CREDITS, 450L);
⋮----
public static boolean claimBossMission(PlayerProfile profile) {
⋮----
profile.addCurrency(PlayerProfile.Currency.GEMS, 3L);
```

## File: src/main/java/com/deadlinezero/game/meta/EndgameMutatorRules.java
```java
/**
 * Deterministic high-Threat run mutators. These are intentionally modest overlays on top of
 * contracts and Threat scaling: they create different pressure profiles without producing
 * abrupt stat spikes or requiring network state.
 */
public final class EndgameMutatorRules {
⋮----
/** Mutators begin at Threat 3 and are stable for the same run identity. */
public static Mutator current() {
int threat = RunStageContext.threatTier();
⋮----
int index = Math.floorMod(
RunStageContext.stage() * 7 + RunStageContext.runOrdinal() * 11 + threat * 13,
⋮----
public static boolean active() { return current() != Mutator.NONE; }
public static String label() { return current().label; }
public static float enemyHpMultiplier() { return current().enemyHp; }
public static float enemySpeedMultiplier() { return current().enemySpeed; }
public static float enemyDamageMultiplier() { return current().enemyDamage; }
public static float spawnIntervalMultiplier() { return current().spawnInterval; }
public static float rewardMultiplier() { return current().reward; }
```

## File: src/main/java/com/deadlinezero/game/meta/EntitlementStore.java
```java
/**
 * Device-local cache for store-owned entitlements that must not be restored from Android backup.
 * Google Play remains authoritative; this cache only preserves offline UX after a successful sync.
 */
public final class EntitlementStore {
⋮----
public static void loadInto(PlayerProfile profile) {
⋮----
Preferences prefs = Gdx.app.getPreferences(PREFS);
profile.removeAdsPurchased = prefs.getBoolean(REMOVE_ADS, false);
⋮----
public static void save(PlayerProfile profile) {
⋮----
Gdx.app.getPreferences(PREFS)
.putBoolean(REMOVE_ADS, profile.removeAdsPurchased)
.flush();
```

## File: src/main/java/com/deadlinezero/game/meta/EquipmentDropTable.java
```java
/** Deterministic rules for equipment drop rarity and slot generation. */
public final class EquipmentDropTable {
⋮----
public static EquipmentItem roll(int stage, boolean bossKilled) {
PlayerProfile.EquipmentSlot[] slots = PlayerProfile.EquipmentSlot.values();
PlayerProfile.EquipmentSlot slot = slots[MathUtils.random(slots.length - 1)];
EquipmentItem.Rarity rarity = rollRarity(stage, bossKilled);
int level = Math.max(1, stage + MathUtils.random(-1, 2));
⋮----
String id = "eq-" + (++sequence) + "-" + slot.name().toLowerCase();
String name = rarity.name() + " " + prettySlot(slot);
return new EquipmentItem(id, name, slot, rarity, level, power);
⋮----
private static EquipmentItem.Rarity rollRarity(int stage, boolean bossKilled) {
float bonus = Math.min(.16f, Math.max(1, stage) * .006f) + (bossKilled ? .08f : 0f);
float r = MathUtils.random();
⋮----
private static String prettySlot(PlayerProfile.EquipmentSlot slot) {
```

## File: src/main/java/com/deadlinezero/game/meta/EquipmentItem.java
```java
public final class EquipmentItem {
⋮----
public String rarityKey() { return "equipment.rarity." + rarity.name().toLowerCase(java.util.Locale.ROOT); }
public String slotKey() { return "equipment.slot." + slot.name().toLowerCase(java.util.Locale.ROOT); }
public String nameKey() {
return ThreatMilestoneRewardCatalog.isExclusiveId(id) ? "equipment." + id + ".name" : null;
⋮----
this.level = Math.max(1, level);
this.powerBonus = Float.isFinite(powerBonus) ? Math.max(0f, powerBonus) : 0f;
```

## File: src/main/java/com/deadlinezero/game/meta/EquipmentService.java
```java
/** Centralized equip/upgrade/merge rules for persistent gear. */
public final class EquipmentService {
⋮----
public static boolean equip(PlayerProfile profile, String itemId) {
⋮----
EquipmentItem item = profile.inventory.find(itemId);
⋮----
profile.equip(item);
⋮----
public static boolean unequip(PlayerProfile profile, PlayerProfile.EquipmentSlot slot) {
if (profile == null || slot == null || profile.equipped(slot) == null) return false;
profile.unequip(slot);
⋮----
public static EquipmentItem bestForSlot(PlayerProfile profile, PlayerProfile.EquipmentSlot slot) {
⋮----
for (EquipmentItem item : profile.inventory.items()) {
⋮----
if (best == null || score(item) > score(best)) best = item;
⋮----
public static boolean upgrade(PlayerProfile profile, String itemId) {
⋮----
EquipmentItem current = profile.inventory.find(itemId);
⋮----
EquipmentItem upgraded = EquipmentUpgradeService.upgrade(profile, current);
⋮----
boolean equipped = profile.equipped(current.slot) != null && itemId.equals(profile.equipped(current.slot).id);
profile.inventory.replace(upgraded);
if (equipped) profile.equip(upgraded);
⋮----
/** Merge three same-slot/same-rarity items into one item of the next rarity. */
public static EquipmentItem mergeThree(PlayerProfile profile, String aId, String bId, String cId) {
⋮----
EquipmentItem a = profile.inventory.find(aId);
EquipmentItem b = profile.inventory.find(bId);
EquipmentItem c = profile.inventory.find(cId);
⋮----
EquipmentItem.Rarity next = nextRarity(a.rarity);
⋮----
int level = Math.max(a.level, Math.max(b.level, c.level));
float power = Math.max(a.powerBonus, Math.max(b.powerBonus, c.powerBonus)) * 1.62f;
EquipmentItem merged = new EquipmentItem("merge-" + System.nanoTime(), next.name() + " " + pretty(a.slot), a.slot, next, level, power);
profile.inventory.remove(a.id);
profile.inventory.remove(b.id);
profile.inventory.remove(c.id);
profile.inventory.add(merged);
profile.equip(merged);
⋮----
public static float score(EquipmentItem item) {
⋮----
private static EquipmentItem.Rarity nextRarity(EquipmentItem.Rarity rarity) {
⋮----
private static String pretty(PlayerProfile.EquipmentSlot slot) {
String s = slot.name().toLowerCase();
return Character.toUpperCase(s.charAt(0)) + s.substring(1);
```

## File: src/main/java/com/deadlinezero/game/meta/EquipmentUpgradeService.java
```java
/** Credit-based equipment leveling. Items are immutable; upgrades return replacements. */
public final class EquipmentUpgradeService {
⋮----
public static long cost(EquipmentItem item) {
⋮----
if (!Double.isFinite(raw) || raw >= Long.MAX_VALUE) return Long.MAX_VALUE;
return Math.max(40L, Math.round(raw));
⋮----
public static EquipmentItem upgrade(PlayerProfile profile, EquipmentItem item) {
⋮----
double multiplier = 1.075d + Math.min(.025d, (item.level + 1d) * .001d);
⋮----
if (!Double.isFinite(projectedPower) || projectedPower > Float.MAX_VALUE) return item;
long cost = cost(item);
if (!profile.spend(PlayerProfile.Currency.CREDITS, cost)) return item;
⋮----
return new EquipmentItem(item.id, item.name, item.slot, item.rarity, nextLevel, (float) projectedPower);
```

## File: src/main/java/com/deadlinezero/game/meta/Inventory.java
```java
/** Persistent equipment inventory with stable IDs for serialization and UI selection. */
public final class Inventory {
⋮----
public Array<EquipmentItem> items() { return items; }
public int size() { return items.size; }
/** Normal drops stop at 120 so four milestone rewards can never be capacity-blocked. */
public boolean full() { return normalItemCount() >= NORMAL_CAPACITY; }
⋮----
public boolean add(EquipmentItem item) {
if (item == null || full() || find(item.id) != null) return false;
items.add(item);
⋮----
/** Adds only catalogued Threat milestone gear into the reserved capacity. */
public boolean addExclusive(EquipmentItem item) {
if (item == null || !ThreatMilestoneRewardCatalog.isExclusiveId(item.id)
|| items.size >= MAX_ITEMS || find(item.id) != null) return false;
⋮----
/** Restores persisted items while preserving the reserved capacity contract for exclusive gear. */
boolean restore(EquipmentItem item) {
⋮----
return ThreatMilestoneRewardCatalog.isExclusiveId(item.id) ? addExclusive(item) : add(item);
⋮----
private int normalItemCount() {
⋮----
if (!ThreatMilestoneRewardCatalog.isExclusiveId(item.id)) count++;
⋮----
public EquipmentItem find(String id) {
⋮----
for (EquipmentItem item : items) if (id.equals(item.id)) return item;
⋮----
public boolean replace(EquipmentItem replacement) {
⋮----
if (items.get(i).id.equals(replacement.id)) {
items.set(i, replacement);
⋮----
public boolean remove(String id) {
⋮----
if (items.get(i).id.equals(id)) {
items.removeIndex(i);
```

## File: src/main/java/com/deadlinezero/game/meta/MasteryProgress.java
```java
/** Permanent non-FOMO mastery earned only from completed victories. */
public final class MasteryProgress {
⋮----
public boolean rankedUp() { return weaponRankAfter > weaponRankBefore || biomeRankAfter > biomeRankBefore; }
⋮----
public int weaponWins(String weaponId) {
WeaponDefinition weapon = WeaponCatalog.byId(weaponId);
return Math.max(0, weaponWins.getOrDefault(weapon.id, 0));
⋮----
public int biomeWins(EnvironmentBiomeRules.Biome biome) {
⋮----
return Math.max(0, biomeWins.getOrDefault(biome, 0));
⋮----
public int weaponRank(String weaponId) { return rankForWins(weaponWins(weaponId)); }
public int biomeRank(EnvironmentBiomeRules.Biome biome) { return rankForWins(biomeWins(biome)); }
⋮----
public int winsForNextWeaponRank(String weaponId) { return winsForNextRank(weaponWins(weaponId)); }
public int winsForNextBiomeRank(EnvironmentBiomeRules.Biome biome) { return winsForNextRank(biomeWins(biome)); }
⋮----
public Gain recordVictory(String weaponId, int stage) {
⋮----
EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(stage);
int weaponBefore = weaponRank(weapon.id);
int biomeBefore = biomeRank(biome);
weaponWins.put(weapon.id, safeIncrement(weaponWins(weapon.id)));
biomeWins.put(biome, safeIncrement(biomeWins(biome)));
int weaponAfter = weaponRank(weapon.id);
int biomeAfter = biomeRank(biome);
⋮----
int gems = weaponRanks * weaponGemsPerRank(weapon) + biomeRanks * 3;
return new Gain(weaponBefore, weaponAfter, biomeBefore, biomeAfter, credits, gems);
⋮----
public void setWeaponWins(String weaponId, int wins) {
⋮----
weaponWins.put(weapon.id, sanitizeWins(wins));
⋮----
public void setBiomeWins(EnvironmentBiomeRules.Biome biome, int wins) {
if (biome != null) biomeWins.put(biome, sanitizeWins(wins));
⋮----
public static int rankForWins(int wins) {
int safe = sanitizeWins(wins);
⋮----
public static String rankTitle(int rank) {
int safe = Math.max(0, Math.min(MAX_RANK, rank));
⋮----
/**
     * Endgame arsenal expansion stays non-pay-to-win by making late mastery ranks credit-only.
     * The original nine weapons retain their established gem rewards.
     */
public static int weaponGemsPerRank(WeaponDefinition weapon) {
⋮----
return WeaponProgression.unlockAccountLevel(weapon) <= 16 ? 2 : 0;
⋮----
public static int winsForNextRank(int wins) {
int rank = rankForWins(wins);
⋮----
return Math.max(0, RANK_THRESHOLDS[rank + 1] - sanitizeWins(wins));
⋮----
private static int safeIncrement(int value) {
int safe = sanitizeWins(value);
⋮----
private static int sanitizeWins(int wins) { return Math.max(0, wins); }
```

## File: src/main/java/com/deadlinezero/game/meta/MasteryRunNotice.java
```java
/** Run-local presentation snapshot for mastery rank-ups; never persisted. */
public final class MasteryRunNotice {
⋮----
public boolean weaponRankedUp() { return weaponRank > 0; }
public boolean biomeRankedUp() { return biomeRank > 0; }
public boolean visible() { return weaponRankedUp() || biomeRankedUp(); }
⋮----
public static void clear() { current = null; }
⋮----
/** Compatibility overload for headless tests and callers that only have the canonical label. */
public static void capture(MasteryProgress.Gain gain, String weaponName, EnvironmentBiomeRules.Biome biome) {
captureInternal(gain, weaponName, null, biome);
⋮----
public static void capture(MasteryProgress.Gain gain, WeaponDefinition weapon, EnvironmentBiomeRules.Biome biome) {
captureInternal(gain, weapon == null ? "WEAPON" : weapon.displayName,
weapon == null ? null : weapon.displayNameKey(), biome);
⋮----
private static void captureInternal(MasteryProgress.Gain gain, String weaponName, String weaponNameKey,
⋮----
if (gain == null || !gain.rankedUp()) {
clear();
⋮----
int weaponRank = gain.weaponRankAfter() > gain.weaponRankBefore() ? gain.weaponRankAfter() : 0;
int biomeRank = gain.biomeRankAfter() > gain.biomeRankBefore() ? gain.biomeRankAfter() : 0;
current = new Notice(
⋮----
biome == null ? null : biome.labelKey(),
⋮----
Math.max(0, gain.creditsReward()), Math.max(0, gain.gemsReward()));
⋮----
public static Notice current() { return current; }
```

## File: src/main/java/com/deadlinezero/game/meta/OnboardingCompletionPolicy.java
```java
/** Pure completion rule for the non-blocking combat tutorial. */
public final class OnboardingCompletionPolicy {
⋮----
public static boolean completed(boolean movementSeen, boolean dashSeen, boolean upgradeSeen, boolean bossSeen) {
```

## File: src/main/java/com/deadlinezero/game/meta/OnboardingState.java
```java
/** Small persistent onboarding state. Tutorial hints never block gameplay and disappear permanently once learned. */
public final class OnboardingState {
⋮----
movementSeen = prefs.getBoolean("movementSeen", false);
dashSeen = prefs.getBoolean("dashSeen", false);
upgradeSeen = prefs.getBoolean("upgradeSeen", false);
bossSeen = prefs.getBoolean("bossSeen", false);
completed = prefs.getBoolean("completed", false);
⋮----
public static OnboardingState load() {
active = new OnboardingState(Gdx.app.getPreferences(PREFS));
⋮----
public static OnboardingState active() {
if (active == null) active = load();
⋮----
public boolean completed() { return completed; }
public boolean movementSeen() { return movementSeen; }
public boolean dashSeen() { return dashSeen; }
public boolean upgradeSeen() { return upgradeSeen; }
public boolean bossSeen() { return bossSeen; }
⋮----
public void markMovementSeen() { if (!movementSeen) { movementSeen = true; persist(); } }
public void markDashSeen() { if (!dashSeen) { dashSeen = true; persist(); } }
public void markUpgradeSeen() { if (!upgradeSeen) { upgradeSeen = true; persist(); } }
public void markBossSeen() { if (!bossSeen) { bossSeen = true; persist(); } }
⋮----
/** Called every HUD frame; persistence only occurs on the one transition into completed. */
public void refreshCompletion() {
⋮----
if (!OnboardingCompletionPolicy.completed(movementSeen, dashSeen, upgradeSeen, bossSeen)) return;
⋮----
persist();
⋮----
public void reset() {
⋮----
private void persist() {
prefs.putBoolean("movementSeen", movementSeen)
.putBoolean("dashSeen", dashSeen)
.putBoolean("upgradeSeen", upgradeSeen)
.putBoolean("bossSeen", bossSeen)
.putBoolean("completed", completed)
.flush();
```

## File: src/main/java/com/deadlinezero/game/meta/PlayerProfile.java
```java
/** Long-term account progression. Storage is intentionally separate from gameplay runtime. */
public final class PlayerProfile {
⋮----
public final Inventory inventory = new Inventory();
public final DailyProgress daily = new DailyProgress();
public final WeeklyProgress weekly = new WeeklyProgress();
public final AchievementProgress achievements = new AchievementProgress();
public final SurvivorProgression survivors = new SurvivorProgression();
public final MasteryProgress mastery = new MasteryProgress();
⋮----
public PlayerProfile() { for (Currency currency : Currency.values()) currencies.put(currency, 0L); }
public long currency(Currency currency) { return Math.max(0L, currencies.getOrDefault(currency, 0L)); }
public void addCurrency(Currency currency, long amount) {
⋮----
currencies.put(currency, ProfileCounterMath.addNonNegative(currency(currency), amount));
⋮----
public boolean spend(Currency currency, long amount) {
if (currency == null || amount <= 0 || currency(currency) < amount) return false;
currencies.put(currency, currency(currency) - amount);
⋮----
public long xpForNextLevel() { return ProfileCounterMath.xpForLevel(accountLevel); }
public void addAccountXp(long amount) {
⋮----
ProfileCounterMath.LevelProgress progress = ProfileCounterMath.advanceAccountXp(accountLevel, accountXp, amount);
accountLevel = progress.level();
accountXp = progress.xp();
survivors.refreshUnlocks(this);
validateSelectedWeapon();
⋮----
/** Canonicalizes fields restored from persistence before gameplay consumes them. */
public void normalizeLoadedState() {
ProfileCounterMath.LevelProgress progress = ProfileCounterMath.advanceAccountXp(accountLevel, accountXp, 0L);
⋮----
highestStage = Math.max(1, highestStage);
selectedStage = Math.min(highestStage, Math.max(1, selectedStage));
highestThreatTier = ThreatTierRules.sanitizeTier(highestThreatTier);
selectedThreatTier = Math.min(highestThreatTier, ThreatTierRules.sanitizeTier(selectedThreatTier));
if (!ThreatTierRules.unlocked(this)) {
⋮----
totalRuns = Math.max(0, totalRuns);
totalKills = Math.max(0L, totalKills);
⋮----
if (!survivors.unlocked(selectedSurvivor)) selectedSurvivor = SurvivorCatalog.Survivor.REX;
⋮----
public void recordRun(int kills, int stage) {
totalRuns = ProfileCounterMath.incrementNonNegative(totalRuns);
totalKills = ProfileCounterMath.addKills(totalKills, kills);
highestStage = Math.max(Math.max(1, highestStage), Math.max(1, stage));
selectedStage = Math.min(Math.max(1, selectedStage), highestStage);
⋮----
public EquipmentItem equipped(EquipmentSlot slot) { return equipped.get(slot); }
public void equip(EquipmentItem item) { if (item != null) { equipped.put(item.slot, item); if (inventory.find(item.id) == null) inventory.add(item); } }
public void unequip(EquipmentSlot slot) { if (slot != null) equipped.remove(slot); }
public boolean selectStage(int stage) { if (stage < 1 || stage > highestStage) return false; selectedStage = stage; return true; }
public boolean selectThreatTier(int tier) {
int safe = ThreatTierRules.sanitizeTier(tier);
if (!ThreatTierRules.unlocked(this) && safe > 0) return false;
⋮----
public boolean unlockNextThreatTier() {
if (!ThreatTierRules.unlocked(this) || highestThreatTier >= ThreatTierRules.MAX_TIER) return false;
⋮----
public boolean selectSurvivor(SurvivorCatalog.Survivor survivor) { if (survivor == null || !survivors.unlocked(survivor)) return false; selectedSurvivor = survivor; return true; }
public WeaponDefinition selectedWeapon() { return WeaponCatalog.byId(selectedWeaponId); }
public boolean selectWeapon(WeaponDefinition weapon) {
if (weapon == null || !WeaponProgression.unlocked(this, weapon)) return false;
⋮----
public void validateSelectedWeapon() {
WeaponDefinition selected = WeaponCatalog.byId(selectedWeaponId);
if (!WeaponProgression.unlocked(this, selected)) selectedWeaponId = WeaponCatalog.AR9.id;
⋮----
public float aggregatePowerMultiplier() { float bonus = 0f; for (EquipmentItem item : equipped.values()) if (item != null) bonus += item.powerBonus; return 1f + bonus; }
⋮----
public boolean hasDeliveredPurchaseReceipt(String receiptId) {
return receiptId != null && !receiptId.isBlank() && deliveredPurchaseReceipts.contains(receiptId);
⋮----
/** Returns true only for the first delivery of this Play receipt. */
public boolean recordDeliveredPurchaseReceipt(String receiptId) {
if (receiptId == null || receiptId.isBlank() || deliveredPurchaseReceipts.contains(receiptId)) return false;
deliveredPurchaseReceipts.add(receiptId);
while (deliveredPurchaseReceipts.size() > MAX_PURCHASE_RECEIPTS) {
String oldest = deliveredPurchaseReceipts.iterator().next();
deliveredPurchaseReceipts.remove(oldest);
⋮----
public Set<String> deliveredPurchaseReceipts() { return Set.copyOf(deliveredPurchaseReceipts); }
```

## File: src/main/java/com/deadlinezero/game/meta/ProfileBackupCodec.java
```java
/** Deterministic, checksummed serialization for complete libGDX Preferences profile backups. */
public final class ProfileBackupCodec {
⋮----
public static String encode(Map<String, ?> values) {
if (values == null) throw new IllegalArgumentException("values");
List<Map.Entry<String, ?>> entries = new ArrayList<>(values.entrySet());
entries.sort(Comparator.comparing(Map.Entry::getKey));
⋮----
StringBuilder payload = new StringBuilder();
⋮----
String key = entry.getKey();
Object value = entry.getValue();
⋮----
char type = typeOf(value);
payload.append(type).append('\t')
.append(b64(key)).append('\t')
.append(b64(String.valueOf(value))).append('\n');
⋮----
String body = payload.toString();
String backup = MAGIC + "\n" + sha256(body) + "\n" + body;
if (backup.length() > MAX_BACKUP_CHARS) throw new IllegalArgumentException("profile backup exceeds size limit");
⋮----
public static Map<String, Object> decode(String backup) {
if (backup == null || backup.isBlank()) throw new IllegalArgumentException("backup");
⋮----
int first = backup.indexOf('\n');
int second = first < 0 ? -1 : backup.indexOf('\n', first + 1);
if (first < 0 || second < 0 || !MAGIC.equals(backup.substring(0, first))) {
throw new IllegalArgumentException("invalid profile backup header");
⋮----
String expectedHash = backup.substring(first + 1, second);
String body = backup.substring(second + 1);
if (!sha256(body).equals(expectedHash)) throw new IllegalArgumentException("profile backup checksum mismatch");
⋮----
if (body.isEmpty()) return values;
for (String line : body.split("\n")) {
if (line.isEmpty()) continue;
String[] parts = line.split("\t", -1);
if (parts.length != 3 || parts[0].length() != 1) throw new IllegalArgumentException("invalid profile backup entry");
String key = fromB64(parts[1]);
if (key.isBlank() || values.containsKey(key)) throw new IllegalArgumentException("invalid or duplicate profile key");
String raw = fromB64(parts[2]);
values.put(key, parse(parts[0].charAt(0), raw));
⋮----
public static int schemaVersion(Map<String, ?> values) {
⋮----
Object raw = values.get(ProfileSchema.VERSION_KEY);
return raw instanceof Number n ? ProfileSchema.sanitizedVersion(n.intValue()) : ProfileSchema.LEGACY_UNVERSIONED;
⋮----
private static char typeOf(Object value) {
⋮----
throw new IllegalArgumentException("unsupported profile value type: " + value.getClass().getName());
⋮----
private static Object parse(char type, String raw) {
⋮----
if (!"true".equals(raw) && !"false".equals(raw)) throw new IllegalArgumentException("invalid boolean");
yield Boolean.parseBoolean(raw);
⋮----
case 'i' -> Integer.parseInt(raw);
case 'l' -> Long.parseLong(raw);
⋮----
float value = Float.parseFloat(raw);
if (!Float.isFinite(value)) throw new IllegalArgumentException("invalid float");
⋮----
default -> throw new IllegalArgumentException("unsupported profile value type");
⋮----
throw new IllegalArgumentException("invalid profile value", e);
⋮----
private static String b64(String value) {
return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
⋮----
private static String fromB64(String value) {
⋮----
return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
⋮----
throw new IllegalArgumentException("invalid base64 profile value", e);
⋮----
private static String sha256(String value) {
⋮----
byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
StringBuilder out = new StringBuilder(digest.length * 2);
for (byte b : digest) out.append(String.format("%02x", b));
return out.toString();
⋮----
throw new IllegalStateException("SHA-256 unavailable", e);
```

## File: src/main/java/com/deadlinezero/game/meta/ProfileBackupSummary.java
```java
/** Monotone progression vector used only when every non-monotone persisted field is identical. */
⋮----
public static final Set<String> MONOTONE_KEYS = Set.of(
⋮----
public static ProfileBackupSummary from(Map<String, ?> values) {
if (values == null) return new ProfileBackupSummary(1, 1, 0, 0L, 0);
return new ProfileBackupSummary(
Math.max(1, intValue(values.get("highestStage"), 1)),
Math.max(1, intValue(values.get("accountLevel"), 1)),
Math.max(0, intValue(values.get("totalRuns"), 0)),
Math.max(0L, longValue(values.get("totalKills"), 0L)),
Math.max(0, intValue(values.get("threat.highest"), 0))
⋮----
public boolean dominates(ProfileBackupSummary other) {
⋮----
private static int intValue(Object value, int fallback) {
return value instanceof Number n ? n.intValue() : fallback;
⋮----
private static long longValue(Object value, long fallback) {
return value instanceof Number n ? n.longValue() : fallback;
```

## File: src/main/java/com/deadlinezero/game/meta/ProfileCounterMath.java
```java
/** Overflow-safe arithmetic for persistent profile counters, currencies and rewards. */
public final class ProfileCounterMath {
private static final BigInteger BI_55 = BigInteger.valueOf(55L);
private static final BigInteger BI_85 = BigInteger.valueOf(85L);
private static final BigInteger BI_110 = BigInteger.valueOf(110L);
⋮----
public static long addNonNegative(long current, long amount) {
long safe = Math.max(0L, current);
long add = Math.max(0L, amount);
⋮----
public static int incrementNonNegative(int current) {
int safe = Math.max(0, current);
⋮----
public static long addKills(long current, int kills) {
return addNonNegative(current, Math.max(0, kills));
⋮----
/** Advances linear account-level thresholds in O(log n), even for corrupted/extreme saves. */
public static LevelProgress advanceAccountXp(int currentLevel, long currentXp, long amount) {
int level = Math.max(1, currentLevel);
long totalXp = addNonNegative(currentXp, amount);
if (level == Integer.MAX_VALUE) return new LevelProgress(level, Math.min(totalXp, xpForLevel(level) - 1L));
⋮----
BigInteger budget = BigInteger.valueOf(totalXp);
⋮----
if (xpCost(level, mid).compareTo(budget) <= 0) low = mid;
⋮----
BigInteger spent = xpCost(level, low);
⋮----
long remainder = budget.subtract(spent).longValue();
if (nextLevel == Integer.MAX_VALUE) remainder = Math.min(remainder, xpForLevel(nextLevel) - 1L);
return new LevelProgress(nextLevel, Math.max(0L, remainder));
⋮----
public static long xpForLevel(int level) {
int safeLevel = Math.max(1, level);
⋮----
private static BigInteger xpCost(int level, int levelsToAdvance) {
⋮----
BigInteger n = BigInteger.valueOf(levelsToAdvance);
BigInteger linear = BI_110.multiply(BigInteger.valueOf(Math.max(1, level))).add(BI_85);
return BI_55.multiply(n).multiply(n).add(linear.multiply(n));
⋮----
/** Scales a non-negative long without narrowing through Math.round(float). */
public static long scaleNonNegative(long value, float multiplier) {
long safe = Math.max(0L, value);
if (safe == 0L || Float.isNaN(multiplier) || multiplier <= 0f) return 0L;
⋮----
if (!Double.isFinite(scaled) || scaled >= Long.MAX_VALUE) return Long.MAX_VALUE;
return Math.max(0L, Math.round(scaled));
```

## File: src/main/java/com/deadlinezero/game/meta/ProfileSchema.java
```java
/** Versioned, idempotent migration pipeline for the persistent player profile. */
final class ProfileSchema {
⋮----
interface Store {
int getInteger(String key, int defaultValue);
void putInteger(String key, int value);
void flush();
⋮----
static final class PreferencesStore implements Store {
⋮----
if (preferences == null) throw new IllegalArgumentException("preferences");
⋮----
@Override public int getInteger(String key, int defaultValue) {
return preferences.getInteger(key, defaultValue);
⋮----
@Override public void putInteger(String key, int value) {
preferences.putInteger(key, value);
⋮----
@Override public void flush() {
preferences.flush();
⋮----
/**
     * Migrates a supported profile to the current schema. Returns false for a profile created by a
     * newer app version so callers can preserve it without overwriting it during a downgrade.
     */
static boolean migrate(Store store) {
⋮----
int version = sanitizedVersion(store.getInteger(VERSION_KEY, LEGACY_UNVERSIONED));
⋮----
version = migrateOne(store, version);
⋮----
if (changed) store.flush();
⋮----
static void stampCurrent(Store store) {
⋮----
store.putInteger(VERSION_KEY, CURRENT_VERSION);
⋮----
static int sanitizedVersion(int rawVersion) {
return Math.max(LEGACY_UNVERSIONED, rawVersion);
⋮----
private static int migrateOne(Store store, int fromVersion) {
⋮----
case LEGACY_UNVERSIONED -> migrateLegacyToV1(store);
case 1 -> migrateV1ToV2(store);
case 2 -> migrateV2ToV3(store);
default -> throw new IllegalStateException("Unsupported profile migration from schema " + fromVersion);
⋮----
/**
     * Legacy saves already use the v1 field layout. The first migration therefore records the
     * schema marker without rewriting progression data, making the operation lossless and idempotent.
     */
private static int migrateLegacyToV1(Store store) {
store.putInteger(VERSION_KEY, 1);
⋮----
/** Weekly mission fields are additive and use safe defaults, so v2 only advances the schema marker. */
private static int migrateV1ToV2(Store store) {
store.putInteger(VERSION_KEY, 2);
⋮----
/** Achievement claims are additive booleans with safe false defaults. */
private static int migrateV2ToV3(Store store) {
store.putInteger(VERSION_KEY, 3);
```

## File: src/main/java/com/deadlinezero/game/meta/ProfileStore.java
```java
/** Persistent account storage backed by libGDX Preferences on Android/Desktop. */
public final class ProfileStore {
⋮----
public static PlayerProfile load() {
Preferences p = Gdx.app.getPreferences(PREFS);
persistenceWritable = ProfileSchema.migrate(new ProfileSchema.PreferencesStore(p));
PlayerProfile profile = new PlayerProfile();
profile.accountLevel = Math.max(1, p.getInteger("accountLevel", 1));
profile.accountXp = Math.max(0L, p.getLong("accountXp", 0L));
profile.highestStage = Math.max(1, p.getInteger("highestStage", 1));
profile.selectedStage = Math.min(profile.highestStage, Math.max(1, p.getInteger("selectedStage", 1)));
profile.highestThreatTier = ThreatTierRules.sanitizeTier(p.getInteger("threat.highest", 0));
profile.selectedThreatTier = ThreatTierRules.sanitizeTier(p.getInteger("threat.selected", 0));
profile.totalRuns = Math.max(0, p.getInteger("totalRuns", 0));
profile.totalKills = Math.max(0L, p.getLong("totalKills", 0L));
profile.removeAdsPurchased = p.getBoolean("purchase.removeAds", false);
profile.starterPackGranted = p.getBoolean("purchase.starterPackGranted", false);
profile.reviewPromptAttempted = p.getBoolean("review.promptAttempted", false);
int receiptCount = Math.min(MAX_PURCHASE_RECEIPTS, Math.max(0, p.getInteger("purchase.receipt.count", 0)));
for (int i = 0; i < receiptCount; i++) profile.recordDeliveredPurchaseReceipt(p.getString("purchase.receipt." + i, ""));
profile.selectedSurvivor = SurvivorCatalog.byName(p.getString("survivor.selected", SurvivorCatalog.Survivor.REX.name()));
profile.selectedWeaponId = WeaponCatalog.byId(p.getString("weapon.selected", WeaponCatalog.AR9.id)).id;
profile.addCurrency(PlayerProfile.Currency.CREDITS, Math.max(0L, p.getLong("credits", 0L)));
profile.addCurrency(PlayerProfile.Currency.GEMS, Math.max(0L, p.getLong("gems", 0L)));
⋮----
for (WeaponDefinition weapon : WeaponCatalog.all()) {
profile.mastery.setWeaponWins(weapon.id, p.getInteger("mastery.weapon." + weapon.id + ".wins", 0));
⋮----
for (EnvironmentBiomeRules.Biome biome : EnvironmentBiomeRules.Biome.values()) {
profile.mastery.setBiomeWins(biome, p.getInteger("mastery.biome." + biome.name() + ".wins", 0));
⋮----
for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
String key = "survivor." + survivor.name() + ".";
profile.survivors.setState(survivor,
Math.max(1, p.getInteger(key + "level", 1)),
Math.max(0L, p.getLong(key + "xp", 0L)),
p.getBoolean(key + "unlocked", survivor == SurvivorCatalog.Survivor.REX));
⋮----
profile.daily.epochDay = p.getLong("daily.epochDay", -1L);
profile.daily.loginStreak = Math.max(0, p.getInteger("daily.loginStreak", 0));
profile.daily.loginClaimed = p.getBoolean("daily.loginClaimed", false);
profile.daily.rewardedChestClaimed = p.getBoolean("daily.rewardedChestClaimed", false);
profile.daily.killsToday = Math.max(0, p.getInteger("daily.kills", 0));
profile.daily.runsToday = Math.max(0, p.getInteger("daily.runs", 0));
profile.daily.bossesToday = Math.max(0, p.getInteger("daily.bosses", 0));
profile.daily.killMissionClaimed = p.getBoolean("daily.killClaimed", false);
profile.daily.runMissionClaimed = p.getBoolean("daily.runClaimed", false);
profile.daily.bossMissionClaimed = p.getBoolean("daily.bossClaimed", false);
⋮----
profile.weekly.weekIndex = p.getLong("weekly.weekIndex", Long.MIN_VALUE);
profile.weekly.kills = Math.max(0, p.getInteger("weekly.kills", 0));
profile.weekly.runs = Math.max(0, p.getInteger("weekly.runs", 0));
profile.weekly.bosses = Math.max(0, p.getInteger("weekly.bosses", 0));
profile.weekly.killMissionClaimed = p.getBoolean("weekly.killClaimed", false);
profile.weekly.runMissionClaimed = p.getBoolean("weekly.runClaimed", false);
profile.weekly.bossMissionClaimed = p.getBoolean("weekly.bossClaimed", false);
⋮----
for (AchievementService.Achievement achievement : AchievementService.Achievement.values()) {
if (p.getBoolean("achievement." + achievement.name() + ".claimed", false)) profile.achievements.markClaimed(achievement);
⋮----
int itemCount = Math.min(Inventory.MAX_ITEMS, Math.max(0, p.getInteger("inventory.count", 0)));
⋮----
String id = p.getString(key + "id", "");
if (id.isEmpty()) continue;
EquipmentItem item = new EquipmentItem(id, p.getString(key + "name", "Equipment"),
PlayerProfile.EquipmentSlot.valueOf(p.getString(key + "slot", "WEAPON")),
EquipmentItem.Rarity.valueOf(p.getString(key + "rarity", "COMMON")),
Math.max(1, p.getInteger(key + "level", 1)), p.getFloat(key + "power", 0f));
profile.inventory.restore(item);
⋮----
for (PlayerProfile.EquipmentSlot slot : PlayerProfile.EquipmentSlot.values()) {
String id = p.getString("equipped." + slot.name(), "");
EquipmentItem item = profile.inventory.find(id);
if (item != null) profile.equip(item);
⋮----
profile.normalizeLoadedState();
⋮----
public static String exportBackup() {
⋮----
return ProfileBackupCodec.encode(p.get());
⋮----
public static PlayerProfile importBackup(String backup) {
Map<String, Object> values = ProfileBackupCodec.decode(backup);
int schemaVersion = ProfileBackupCodec.schemaVersion(values);
⋮----
Map<String, ?> original = new HashMap<>(p.get());
⋮----
p.clear();
p.put(values);
p.flush();
boolean migrated = ProfileSchema.migrate(new ProfileSchema.PreferencesStore(p));
⋮----
rollbackImport(p, original, originalWritable);
⋮----
// A checksum only proves byte integrity. Reload every typed field before accepting the
// transaction so a validly encoded but type-poisoned backup cannot brick future starts.
PlayerProfile restored = load();
⋮----
private static void rollbackImport(Preferences p, Map<String, ?> original, boolean writable) {
⋮----
p.put(original);
⋮----
public static void save(PlayerProfile profile) {
⋮----
ProfileSchema.stampCurrent(new ProfileSchema.PreferencesStore(p));
p.putInteger("accountLevel", profile.accountLevel);
p.putLong("accountXp", profile.accountXp);
p.putInteger("highestStage", profile.highestStage);
p.putInteger("selectedStage", profile.selectedStage);
p.putInteger("threat.highest", ThreatTierRules.sanitizeTier(profile.highestThreatTier));
p.putInteger("threat.selected", ThreatTierRules.sanitizeTier(profile.selectedThreatTier));
p.putInteger("totalRuns", profile.totalRuns);
p.putLong("totalKills", profile.totalKills);
p.putBoolean("purchase.removeAds", profile.removeAdsPurchased);
p.putBoolean("purchase.starterPackGranted", profile.starterPackGranted);
p.putBoolean("review.promptAttempted", profile.reviewPromptAttempted);
⋮----
for (String receipt : profile.deliveredPurchaseReceipts()) {
⋮----
p.putString("purchase.receipt." + receiptIndex++, receipt);
⋮----
p.putInteger("purchase.receipt.count", receiptIndex);
p.putString("survivor.selected", profile.selectedSurvivor.name());
p.putString("weapon.selected", profile.selectedWeapon().id);
⋮----
p.putInteger(key + "level", profile.survivors.level(survivor));
p.putLong(key + "xp", profile.survivors.xp(survivor));
p.putBoolean(key + "unlocked", profile.survivors.unlocked(survivor));
⋮----
p.putLong("credits", profile.currency(PlayerProfile.Currency.CREDITS));
p.putLong("gems", profile.currency(PlayerProfile.Currency.GEMS));
⋮----
p.putInteger("mastery.weapon." + weapon.id + ".wins", profile.mastery.weaponWins(weapon.id));
⋮----
p.putInteger("mastery.biome." + biome.name() + ".wins", profile.mastery.biomeWins(biome));
⋮----
p.putLong("daily.epochDay", profile.daily.epochDay);
p.putInteger("daily.loginStreak", profile.daily.loginStreak);
p.putBoolean("daily.loginClaimed", profile.daily.loginClaimed);
p.putBoolean("daily.rewardedChestClaimed", profile.daily.rewardedChestClaimed);
p.putInteger("daily.kills", profile.daily.killsToday);
p.putInteger("daily.runs", profile.daily.runsToday);
p.putInteger("daily.bosses", profile.daily.bossesToday);
p.putBoolean("daily.killClaimed", profile.daily.killMissionClaimed);
p.putBoolean("daily.runClaimed", profile.daily.runMissionClaimed);
p.putBoolean("daily.bossClaimed", profile.daily.bossMissionClaimed);
⋮----
p.putLong("weekly.weekIndex", profile.weekly.weekIndex);
p.putInteger("weekly.kills", profile.weekly.kills);
p.putInteger("weekly.runs", profile.weekly.runs);
p.putInteger("weekly.bosses", profile.weekly.bosses);
p.putBoolean("weekly.killClaimed", profile.weekly.killMissionClaimed);
p.putBoolean("weekly.runClaimed", profile.weekly.runMissionClaimed);
p.putBoolean("weekly.bossClaimed", profile.weekly.bossMissionClaimed);
⋮----
p.putBoolean("achievement." + achievement.name() + ".claimed", profile.achievements.claimed(achievement));
⋮----
int count = Math.min(profile.inventory.size(), Inventory.MAX_ITEMS);
p.putInteger("inventory.count", count);
⋮----
EquipmentItem item = profile.inventory.items().get(i);
⋮----
p.putString(key + "id", item.id);
p.putString(key + "name", item.name);
p.putString(key + "slot", item.slot.name());
p.putString(key + "rarity", item.rarity.name());
p.putInteger(key + "level", item.level);
p.putFloat(key + "power", item.powerBonus);
⋮----
EquipmentItem item = profile.equipped(slot);
p.putString("equipped." + slot.name(), item == null ? "" : item.id);
```

## File: src/main/java/com/deadlinezero/game/meta/PurchaseGrantService.java
```java
/** Applies client-side purchase entitlements exactly once where required. */
public final class PurchaseGrantService {
⋮----
public static boolean grant(PlayerProfile profile, String productId) {
return grant(profile, productId, null);
⋮----
public static boolean grant(PlayerProfile profile, String productId, String receiptId) {
⋮----
if (BillingService.isConsumable(productId)) {
if (receiptId == null || receiptId.isBlank()) return false;
if (profile.hasDeliveredPurchaseReceipt(receiptId)) return false;
boolean granted = grantProduct(profile, productId);
if (granted) profile.recordDeliveredPurchaseReceipt(receiptId);
⋮----
return grantProduct(profile, productId);
⋮----
private static boolean grantProduct(PlayerProfile profile, String productId) {
⋮----
profile.addCurrency(PlayerProfile.Currency.CREDITS, 5_000);
profile.addCurrency(PlayerProfile.Currency.GEMS, 250);
if (!profile.inventory.full()) profile.inventory.add(EquipmentDropTable.roll(Math.max(3, profile.highestStage), true));
⋮----
profile.addCurrency(PlayerProfile.Currency.GEMS, 1_200);
⋮----
/** Rehydrates permanent purchases and reconciles revocable ad-free entitlement once the store snapshot is authoritative. */
public static boolean syncPermanent(PlayerProfile profile, BillingService billing) {
⋮----
if (billing.owns(BillingService.REMOVE_ADS)) {
changed |= grant(profile, BillingService.REMOVE_ADS);
} else if (billing.authoritativeEntitlements() && profile.removeAdsPurchased) {
⋮----
if (billing.owns(BillingService.STARTER_PACK)) changed |= grant(profile, BillingService.STARTER_PACK);
```

## File: src/main/java/com/deadlinezero/game/meta/ReviewPromptPolicy.java
```java
/** Conservative eligibility for requesting a store-managed review prompt after a successful run. */
public final class ReviewPromptPolicy {
⋮----
public static boolean eligible(boolean firstClear, int stage, boolean alreadyAttempted) {
```

## File: src/main/java/com/deadlinezero/game/meta/RunEncounterRuntime.java
```java
/** Tracks deterministic bonus rewards earned from optional run encounters. */
public final class RunEncounterRuntime {
⋮----
public static void begin() {
⋮----
public static void award(long credits) {
⋮----
public static long bonusCredits() { return bonusCredits; }
public static int completedEncounters() { return completedEncounters; }
⋮----
public static long consumeBonusCredits() {
⋮----
public static void end() {
```

## File: src/main/java/com/deadlinezero/game/meta/RunLoadoutContext.java
```java
/** Immutable snapshot of gear, weapon and survivor bonuses for the active run. */
public final class RunLoadoutContext {
⋮----
public static void begin(PlayerProfile profile) {
⋮----
weaponDefinition = profile == null ? WeaponCatalog.AR9 : profile.selectedWeapon();
weaponSynergy = WeaponSynergyRules.resolve(survivor, weaponDefinition);
WeaponSignatureRuntime.begin(weaponDefinition);
float levelPower = profile == null ? 1f : profile.survivors.levelPowerMultiplier(survivor);
ascensionSetPieces = ThreatSetBonusRules.equippedPieces(profile);
zeroDayCoreEquipped = SingularityCoreRules.equipped(profile);
SingularityCoreRuntime.begin(zeroDayCoreEquipped);
⋮----
EquipmentItem weaponItem = profile.equipped(PlayerProfile.EquipmentSlot.WEAPON);
EquipmentItem armor = profile.equipped(PlayerProfile.EquipmentSlot.ARMOR);
EquipmentItem helmet = profile.equipped(PlayerProfile.EquipmentSlot.HELMET);
EquipmentItem boots = profile.equipped(PlayerProfile.EquipmentSlot.BOOTS);
EquipmentItem gloves = profile.equipped(PlayerProfile.EquipmentSlot.GLOVES);
EquipmentItem core = profile.equipped(PlayerProfile.EquipmentSlot.CORE);
⋮----
* ThreatSetBonusRules.hpMultiplier(ascensionSetPieces);
⋮----
* ThreatSetBonusRules.moveSpeedMultiplier(ascensionSetPieces);
dashCooldownMultiplier = Math.max(.60f, (1f - dash) * (survivor == SurvivorCatalog.Survivor.WRAITH ? .76f : 1f));
⋮----
+ ThreatSetBonusRules.dashInvulnerabilityBonus(ascensionSetPieces);
⋮----
* ThreatSetBonusRules.weaponMultiplier(ascensionSetPieces) * weaponSynergy.weaponDamageMultiplier;
critChanceBonus = Math.min(.25f, crit + survivor.critBonus + (survivor == SurvivorCatalog.Survivor.NYX ? .04f : 0f)
⋮----
* ThreatSetBonusRules.abilityMultiplier(ascensionSetPieces) * weaponSynergy.abilityPowerMultiplier;
⋮----
* ThreatSetBonusRules.damageTakenMultiplier(ascensionSetPieces) * weaponSynergy.damageTakenMultiplier;
⋮----
/** Clears all ephemeral run-derived state before returning to a durable menu state. */
public static void end() {
⋮----
WeaponSignatureRuntime.begin(WeaponCatalog.AR9);
SingularityCoreRuntime.begin(false);
⋮----
public static float maxHpMultiplier() { return maxHpMultiplier; }
public static float moveSpeedMultiplier() { return moveSpeedMultiplier; }
public static float dashCooldownMultiplier() { return dashCooldownMultiplier; }
public static float dashInvulnerabilitySeconds() { return dashInvulnerabilitySeconds; }
public static float weaponDamageMultiplier() { return weaponDamageMultiplier; }
public static float critChanceBonus() { return critChanceBonus; }
public static float critDamageBonus() { return critDamageBonus; }
public static float abilityPowerMultiplier() { return abilityPowerMultiplier; }
public static float damageTakenMultiplier() { return damageTakenMultiplier; }
public static int startingTeslaLevel() { return startingTeslaLevel; }
public static int ascensionSetPieces() { return ascensionSetPieces; }
public static boolean zeroDayCoreEquipped() { return zeroDayCoreEquipped; }
public static SurvivorCatalog.Survivor survivor() { return survivor; }
public static WeaponDefinition weaponDefinition() { return weaponDefinition; }
public static WeaponSynergyRules.Synergy weaponSynergy() { return weaponSynergy; }
```

## File: src/main/java/com/deadlinezero/game/meta/RunMissionRuntime.java
```java
/** Lightweight active-run telemetry and deferred victory signal. */
public final class RunMissionRuntime {
⋮----
public static void begin(Runnable callback) { begin(callback, 1); }
⋮----
public static void begin(Runnable callback, int requiredBossDefeats) {
⋮----
requiredBossKills = Math.max(1, requiredBossDefeats);
⋮----
public static void update(float seconds, int killCount) {
elapsed = Math.max(0f, seconds);
kills = Math.max(0, killCount);
⋮----
public static int kills() { return kills; }
public static float elapsed() { return elapsed; }
public static int bossKills() { return bossKills; }
public static int requiredBossKills() { return requiredBossKills; }
⋮----
public static void signalBossDefeated() {
⋮----
bossKills = Math.min(requiredBossKills, bossKills + 1);
⋮----
if (victoryCallback != null) victoryCallback.run();
⋮----
public static void end() {
```

## File: src/main/java/com/deadlinezero/game/meta/RunModifierContext.java
```java
/** Active run-wide risk/reward contract plus deterministic pre-run offer generation. */
public final class RunModifierContext {
⋮----
public String titleKey() { return "runModifier." + name().toLowerCase(java.util.Locale.ROOT) + ".title"; }
public String descriptionKey() { return "runModifier." + name().toLowerCase(java.util.Locale.ROOT) + ".description"; }
⋮----
public int rewardBonusPercent() { return Math.round((reward - 1f) * 100f); }
public boolean legendary() { return rarity == Rarity.LEGENDARY; }
⋮----
/** Compact relative threat score used only for presentation, not combat math. */
public int threatPercent() {
float durability = Math.max(.72f, enemyHp);
float tempo = enemySpeed / Math.max(.55f, spawnInterval);
⋮----
if (legendary()) pressure *= 1.22f;
return Math.max(100, Math.round(pressure * 100f));
⋮----
private static int standardBaseIndex() {
int stageOffset = Math.floorMod(RunStageContext.stage() * 2, STANDARD.length);
int ordinalOffset = Math.floorMod(RunStageContext.runOrdinal() * 3, STANDARD.length);
⋮----
/** Legendary contracts enter one deterministic offer slot roughly every fourth run from stage 3 onward. */
public static boolean legendaryOfferAvailable() {
return RunStageContext.stage() >= 3 && Math.floorMod(RunStageContext.stage() + RunStageContext.runOrdinal(), 4) == 0;
⋮----
/** Three unique offers, stable for the same stage/run ordinal. */
public static Modifier[] offers() {
int base = standardBaseIndex();
⋮----
if (legendaryOfferAvailable()) {
int legendaryIndex = Math.floorMod(RunStageContext.stage() * 5 + RunStageContext.runOrdinal(), LEGENDARY.length);
⋮----
/** Legacy/direct-run fallback: activates the first deterministic offer. */
public static void begin() {
active = offers()[0];
BalanceTelemetryRuntime.setContract(active.title);
⋮----
/** Activates only a contract that belongs to the current run's offer set. */
public static boolean choose(Modifier selection) {
⋮----
for (Modifier offered : offers()) {
⋮----
public static void end() { active = null; }
public static boolean active() { return active != null; }
public static Modifier modifier() { return active; }
public static String title() {
⋮----
return EndgameMutatorRules.active() ? contract + " • " + EndgameMutatorRules.label() : contract;
⋮----
public static String description() {
⋮----
public static float enemyHpMultiplier() {
return (active == null ? 1f : active.enemyHp) * EndgameMutatorRules.enemyHpMultiplier();
⋮----
public static float enemySpeedMultiplier() {
return (active == null ? 1f : active.enemySpeed) * EndgameMutatorRules.enemySpeedMultiplier();
⋮----
public static float enemyDamageMultiplier() {
return (active == null ? 1f : active.enemyDamage) * EndgameMutatorRules.enemyDamageMultiplier();
⋮----
public static float spawnIntervalMultiplier() {
⋮----
* EndgameMutatorRules.spawnIntervalMultiplier()
* ThreatTierRules.spawnIntervalMultiplier(RunStageContext.threatTier());
⋮----
public static float rewardMultiplier() {
return (active == null ? 1f : active.reward) * EndgameMutatorRules.rewardMultiplier();
⋮----
public static int rewardBonusPercent() { return Math.round((rewardMultiplier() - 1f) * 100f); }
public static boolean eliteHunt() { return active == Modifier.ELITE_HUNT; }
public static boolean phantomEclipse() { return active == Modifier.PHANTOM_ECLIPSE; }
public static boolean twinApex() { return active == Modifier.TWIN_APEX; }
public static boolean specialistSiege() { return active == Modifier.SPECIALIST_SIEGE; }
public static int requiredBossKills() { return twinApex() ? 2 : 1; }
```

## File: src/main/java/com/deadlinezero/game/meta/RunRecoveryAdvice.java
```java
/** Pure post-run coaching. It never changes difficulty or profile state. */
public final class RunRecoveryAdvice {
⋮----
public static Advice forResult(RunResult result) {
if (result == null) return balanced();
float seconds = Math.max(0f, result.secondsSurvived());
float target = Math.max(1f, StageMissionRules.bossArrivalSeconds(Math.max(1, result.stage())));
⋮----
float killsPerMinute = seconds <= .001f ? 0f : Math.max(0, result.kills()) * 60f / seconds;
⋮----
String detail = result.threatTier() >= 3
⋮----
return new Advice(Focus.SURVIVABILITY, "SURVIVE THE OPENING", detail,
⋮----
result.threatTier() >= 3 ? "recovery.survivabilityThreat.detail" : "recovery.survivability.detail");
⋮----
return new Advice(Focus.OFFENSE, "RAISE CLEAR SPEED",
⋮----
return new Advice(Focus.ENDGAME_DEFENSE, "HOLD THE FINAL PRESSURE",
⋮----
return balanced();
⋮----
private static Advice balanced() {
return new Advice(Focus.BALANCED, "REFINE THE BUILD",
```

## File: src/main/java/com/deadlinezero/game/meta/RunResult.java
```java
/** Immutable payload passed from combat to result screens, including settled contract and ascension state. */
⋮----
/** Compatibility constructor for tests and non-contracted result creation. */
⋮----
/** Compatibility constructor for contracted results created before ascension was added. */
⋮----
if (contractTitle == null || contractTitle.isBlank()) contractTitle = "STANDARD";
contractBonusPercent = Math.max(0, contractBonusPercent);
threatTier = ThreatTierRules.sanitizeTier(threatTier);
threatBonusPercent = Math.max(0, threatBonusPercent);
unlockedThreatTier = ThreatTierRules.sanitizeTier(unlockedThreatTier);
threatMilestoneGems = Math.max(0, threatMilestoneGems);
```

## File: src/main/java/com/deadlinezero/game/meta/RunRewardCalculator.java
```java
/** Centralizes soft-currency rewards so balancing can evolve without touching combat code. */
public final class RunRewardCalculator {
⋮----
public static Rewards calculate(int kills, float secondsSurvived, boolean bossKilled, int stage) {
int safeStage = Math.max(1, stage);
long credits = Math.max(0, kills) * 2L + (long)(Math.max(0f, secondsSurvived) / 6f);
long xp = Math.max(0, kills) + (long)(Math.max(0f, secondsSurvived) / 4f);
⋮----
credits = ProfileCounterMath.addNonNegative(credits, 180L + safeStage * 25L);
xp = ProfileCounterMath.addNonNegative(xp, 90L + safeStage * 15L);
⋮----
float multiplier = StageRules.rewardMultiplier(safeStage);
credits = ProfileCounterMath.scaleNonNegative(credits, multiplier);
xp = ProfileCounterMath.scaleNonNegative(xp, 1f + (multiplier - 1f) * .65f);
int gems = bossKilled ? Math.min(12, 1 + safeStage / 3) : 0;
return new Rewards(credits, xp, gems);
```

## File: src/main/java/com/deadlinezero/game/meta/RunSettlement.java
```java
/** Applies one completed run to the persistent account profile. */
public final class RunSettlement {
⋮----
public static RunRewardCalculator.Rewards apply(PlayerProfile profile, int kills, float secondsSurvived,
⋮----
RunRewardCalculator.Rewards rewards = RunRewardCalculator.calculate(kills, secondsSurvived, bossKilled, stage);
profile.addCurrency(PlayerProfile.Currency.CREDITS, rewards.credits());
profile.addCurrency(PlayerProfile.Currency.GEMS, rewards.gems());
profile.addAccountXp(rewards.accountXp());
profile.recordRun(kills, stage);
MasteryRunNotice.clear();
⋮----
var weapon = RunLoadoutContext.weaponDefinition();
MasteryProgress.Gain mastery = profile.mastery.recordVictory(weapon.id, stage);
profile.addCurrency(PlayerProfile.Currency.CREDITS, mastery.creditsReward());
profile.addCurrency(PlayerProfile.Currency.GEMS, mastery.gemsReward());
MasteryRunNotice.capture(mastery, weapon, EnvironmentBiomeRules.forStage(stage));
⋮----
BalanceRunSample sample = BalanceTelemetryRuntime.settle(bossKilled, secondsSurvived, kills);
BalanceTelemetryStore.append(sample);
```

## File: src/main/java/com/deadlinezero/game/meta/RunShareText.java
```java
/** Deterministic, truthful share copy built only from the settled run result. */
public final class RunShareText {
⋮----
/** Legacy deterministic English formatter retained for headless tests and non-UI callers. */
public static String format(RunResult result) {
⋮----
int seconds = Math.max(0, (int)result.secondsSurvived());
StringBuilder text = new StringBuilder(192);
text.append("DEADLINE: ZERO\n")
.append("Protocol cleared — Stage ").append(Math.max(1, result.stage()));
if (result.threatTier() > 0) text.append(" • Threat ").append(result.threatTier());
text.append(" • ").append(Math.max(0, result.kills())).append(" kills")
.append(" • ").append(String.format(java.util.Locale.ROOT, "%02d:%02d", seconds / 60, seconds % 60))
.append('\n')
.append("Contract: ").append(safeEnglish(result.contractTitle()))
.append("\nCan you clear it?\n")
.append(PLAY_URL);
return text.toString();
⋮----
public static String format(RunResult result, Localization i18n) {
if (i18n == null) throw new IllegalArgumentException("i18n");
if (result == null) return i18n.format("share.fallback", PLAY_URL);
⋮----
text.append(i18n.text("share.header")).append('\n')
.append(i18n.format("share.cleared", Math.max(1, result.stage())));
if (result.threatTier() > 0) text.append(i18n.format("share.threat", result.threatTier()));
text.append(i18n.format("share.kills", Math.max(0, result.kills())))
⋮----
.append(i18n.format("share.contract", safe(result.contractTitle(), i18n)))
.append('\n').append(i18n.text("share.challenge")).append('\n')
⋮----
private static String safeEnglish(String value) {
if (value == null || value.isBlank()) return "STANDARD";
String normalized = value.replace('\n', ' ').replace('\r', ' ').trim();
return normalized.length() > 72 ? normalized.substring(0, 72) : normalized;
⋮----
private static String safe(String value, Localization i18n) {
if (value == null || value.isBlank()) return i18n.text("share.standard");
```

## File: src/main/java/com/deadlinezero/game/meta/RunStageContext.java
```java
/** Snapshot of the selected stage, run ordinal and endgame threat tier used by the active run. */
public final class RunStageContext {
⋮----
public static void begin(int selectedStage) {
begin(selectedStage, 0, 0);
⋮----
public static void begin(int selectedStage, int runOrdinal) {
begin(selectedStage, runOrdinal, 0);
⋮----
public static void begin(int selectedStage, int runOrdinal, int threatTier) {
activeStage = Math.max(1, selectedStage);
activeRunOrdinal = Math.max(0, runOrdinal);
activeThreatTier = ThreatTierRules.sanitizeTier(threatTier);
BalanceTelemetryRuntime.begin(activeStage, activeRunOrdinal, activeThreatTier);
⋮----
public static int stage() { return activeStage; }
public static int runOrdinal() { return activeRunOrdinal; }
public static int threatTier() { return activeThreatTier; }
⋮----
/** Stable per-run seed suitable for deterministic encounter planning, not security. */
public static int encounterSeed() {
```

## File: src/main/java/com/deadlinezero/game/meta/SaturatingMath.java
```java
/** Small overflow-safe helpers for persistent progression counters. */
public final class SaturatingMath {
⋮----
public static long addPositive(long current, long amount) {
long safe = Math.max(0L, current);
⋮----
public static int increment(int current) {
return current == Integer.MAX_VALUE ? Integer.MAX_VALUE : Math.max(0, current) + 1;
```

## File: src/main/java/com/deadlinezero/game/meta/SingularityCoreRules.java
```java
/** Deterministic combat signature unlocked by the Threat 20 Zero-Day Singularity Core. */
public final class SingularityCoreRules {
⋮----
public static boolean equipped(PlayerProfile profile) {
⋮----
EquipmentItem core = profile.equipped(PlayerProfile.EquipmentSlot.CORE);
return core != null && CORE_ID.equals(core.id);
⋮----
/** Sequence is one-based: 6, 12, 18... become Singularity shots. */
public static boolean markedShot(long sequence) {
```

## File: src/main/java/com/deadlinezero/game/meta/SingularityCoreRuntime.java
```java
/** Per-run deterministic cadence for the Zero-Day Singularity Core projectile passive. */
public final class SingularityCoreRuntime {
⋮----
public static void begin(boolean enabled) {
⋮----
public static boolean consumeShotMark() {
⋮----
return SingularityCoreRules.markedShot(shotSequence);
⋮----
public static boolean active() { return active; }
public static long shotSequence() { return shotSequence; }
```

## File: src/main/java/com/deadlinezero/game/meta/StageMissionRules.java
```java
/** Centralized rules for finite campaign missions. */
public final class StageMissionRules {
⋮----
public static float bossArrivalSeconds(int stage) {
int s = Math.max(1, stage) - 1;
return Math.min(600f, 360f + s * 15f);
⋮----
public static long firstClearCredits(int stage) {
return 900L + Math.max(1, stage) * 220L;
⋮----
public static int firstClearGems(int stage) {
return 8 + Math.min(22, Math.max(1, stage) * 2);
```

## File: src/main/java/com/deadlinezero/game/meta/StageRules.java
```java
/** Centralized deterministic stage + endgame scaling. Keeps combat/reward tuning out of screens. */
public final class StageRules {
⋮----
public static float enemyHpMultiplier(int stage) {
int s = Math.max(1, stage) - 1;
⋮----
* RunModifierContext.enemyHpMultiplier()
* ThreatTierRules.enemyHpMultiplier(RunStageContext.threatTier());
⋮----
public static float enemyDamageMultiplier(int stage) {
⋮----
* RunModifierContext.enemyDamageMultiplier()
* ThreatTierRules.enemyDamageMultiplier(RunStageContext.threatTier());
⋮----
public static float enemySpeedMultiplier(int stage) {
⋮----
float stageSpeed = Math.min(1.42f, 1f + s * .018f);
return Math.min(1.78f, stageSpeed
* RunModifierContext.enemySpeedMultiplier()
* ThreatTierRules.enemySpeedMultiplier(RunStageContext.threatTier()));
⋮----
public static float rewardMultiplier(int stage) {
⋮----
* RunModifierContext.rewardMultiplier()
* ThreatTierRules.rewardMultiplier(RunStageContext.threatTier());
⋮----
public static int nextStage(int completedStage) {
return Math.max(1, completedStage) + 1;
```

## File: src/main/java/com/deadlinezero/game/meta/SurvivorCatalog.java
```java
/** Static playable survivor definitions. */
public final class SurvivorCatalog {
⋮----
public String displayNameKey() { return "survivor." + name().toLowerCase(java.util.Locale.ROOT) + ".name"; }
public String roleKey() { return "survivor." + name().toLowerCase(java.util.Locale.ROOT) + ".role"; }
⋮----
public static Survivor byName(String name) {
⋮----
try { return Survivor.valueOf(name); }
```

## File: src/main/java/com/deadlinezero/game/meta/SurvivorProgression.java
```java
/** Persistent per-survivor unlock and XP state. */
public final class SurvivorProgression {
private static final BigInteger BI_85 = BigInteger.valueOf(85L);
private static final BigInteger BI_2 = BigInteger.valueOf(2L);
⋮----
for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
levels.put(survivor, 1);
xp.put(survivor, 0L);
unlocked.put(survivor, survivor == SurvivorCatalog.Survivor.REX);
⋮----
public int level(SurvivorCatalog.Survivor survivor) { return levels.getOrDefault(survivor, 1); }
public long xp(SurvivorCatalog.Survivor survivor) { return xp.getOrDefault(survivor, 0L); }
public boolean unlocked(SurvivorCatalog.Survivor survivor) { return unlocked.getOrDefault(survivor, false); }
public long xpForNext(SurvivorCatalog.Survivor survivor) { return xpForLevel(level(survivor)); }
⋮----
public void setState(SurvivorCatalog.Survivor survivor, int level, long currentXp, boolean isUnlocked) {
⋮----
LevelProgress normalized = advance(level, currentXp, 0L);
levels.put(survivor, normalized.level());
xp.put(survivor, normalized.xp());
unlocked.put(survivor, isUnlocked || survivor == SurvivorCatalog.Survivor.REX);
⋮----
public boolean unlock(SurvivorCatalog.Survivor survivor) {
if (survivor == null || unlocked(survivor)) return false;
unlocked.put(survivor, true);
⋮----
public void refreshUnlocks(PlayerProfile profile) {
⋮----
unlock(SurvivorCatalog.Survivor.REX);
if (profile.accountLevel >= 3) unlock(SurvivorCatalog.Survivor.NYX);
if (profile.highestStage >= 3) unlock(SurvivorCatalog.Survivor.BASTION);
if (profile.highestStage >= 5) unlock(SurvivorCatalog.Survivor.VOLT);
if (profile.accountLevel >= 8 || profile.highestStage >= 7) unlock(SurvivorCatalog.Survivor.WRAITH);
⋮----
public void addXp(SurvivorCatalog.Survivor survivor, long amount) {
⋮----
LevelProgress progress = advance(level(survivor), xp(survivor), amount);
levels.put(survivor, progress.level());
xp.put(survivor, progress.xp());
⋮----
public float levelPowerMultiplier(SurvivorCatalog.Survivor survivor) {
return 1f + Math.min(0.30f, Math.max(0, level(survivor) - 1) * .012f);
⋮----
/** Advances affine XP thresholds in O(log n), safely handling corrupted or extreme saves. */
private static LevelProgress advance(int currentLevel, long currentXp, long amount) {
int level = Math.max(1, currentLevel);
long totalXp = ProfileCounterMath.addNonNegative(currentXp, amount);
⋮----
return new LevelProgress(level, Math.min(totalXp, xpForLevel(level) - 1L));
⋮----
BigInteger budget = BigInteger.valueOf(totalXp);
⋮----
if (xpCost(level, mid).compareTo(budget) <= 0) low = mid;
⋮----
BigInteger spent = xpCost(level, low);
⋮----
long remainder = budget.subtract(spent).longValue();
if (nextLevel == Integer.MAX_VALUE) remainder = Math.min(remainder, xpForLevel(nextLevel) - 1L);
return new LevelProgress(nextLevel, Math.max(0L, remainder));
⋮----
private static long xpForLevel(int level) {
int safeLevel = Math.max(1, level);
⋮----
private static BigInteger xpCost(int level, int levelsToAdvance) {
⋮----
BigInteger n = BigInteger.valueOf(levelsToAdvance);
BigInteger first = BigInteger.valueOf(180L + (long)(Math.max(1, level) - 1) * 85L);
BigInteger staircase = BI_85.multiply(n).multiply(n.subtract(BigInteger.ONE)).divide(BI_2);
return first.multiply(n).add(staircase);
```

## File: src/main/java/com/deadlinezero/game/meta/ThreatMilestoneRewardCatalog.java
```java
/** Unique Mythic equipment earned only from Threat 5/10/15/20 clears. */
public final class ThreatMilestoneRewardCatalog {
⋮----
public static EquipmentItem forTier(int tier) {
return switch (ThreatTierRules.sanitizeTier(tier)) {
case 5 -> new EquipmentItem("threat_05_helmet", "Aegis Protocol Helm",
⋮----
case 10 -> new EquipmentItem("threat_10_gloves", "Warden Breaker Gauntlets",
⋮----
case 15 -> new EquipmentItem("threat_15_armor", "Revenant Null Carapace",
⋮----
case 20 -> new EquipmentItem("threat_20_core", "Zero-Day Singularity Core",
⋮----
public static boolean isExclusiveId(String id) {
⋮----
return id.equals("threat_05_helmet") || id.equals("threat_10_gloves")
|| id.equals("threat_15_armor") || id.equals("threat_20_core");
```

## File: src/main/java/com/deadlinezero/game/meta/ThreatProgressionService.java
```java
/** Applies one successful endgame clear to persistent threat progression. */
public final class ThreatProgressionService {
⋮----
public static UnlockResult none() { return new UnlockResult(false, 0, 0); }
⋮----
public static UnlockResult applyBossClear(PlayerProfile profile, int stage, int clearedThreatTier) {
if (profile == null || stage < ThreatTierRules.UNLOCK_STAGE) return UnlockResult.none();
int safeTier = ThreatTierRules.sanitizeTier(clearedThreatTier);
if (safeTier != profile.highestThreatTier) return UnlockResult.none();
if (!profile.unlockNextThreatTier()) return UnlockResult.none();
⋮----
int milestoneGems = ThreatTierRules.milestoneGemReward(unlockedTier);
if (milestoneGems > 0) profile.addCurrency(PlayerProfile.Currency.GEMS, milestoneGems);
⋮----
EquipmentItem milestoneGear = ThreatMilestoneRewardCatalog.forTier(unlockedTier);
if (milestoneGear != null && profile.inventory.find(milestoneGear.id) == null) {
profile.inventory.addExclusive(milestoneGear);
⋮----
return new UnlockResult(true, unlockedTier, milestoneGems);
```

## File: src/main/java/com/deadlinezero/game/meta/ThreatSetBonusRules.java
```java
/** Pure rules for the four-piece Ascension Mythic equipment set. */
public final class ThreatSetBonusRules {
⋮----
public static int equippedPieces(PlayerProfile profile) {
⋮----
for (PlayerProfile.EquipmentSlot slot : PlayerProfile.EquipmentSlot.values()) {
EquipmentItem item = profile.equipped(slot);
if (item != null && ThreatMilestoneRewardCatalog.isExclusiveId(item.id)) count++;
⋮----
return Math.min(4, count);
⋮----
public static float weaponMultiplier(int pieces) { return pieces >= 2 ? 1.08f : 1f; }
public static float abilityMultiplier(int pieces) { return pieces >= 2 ? 1.08f : 1f; }
public static float hpMultiplier(int pieces) { return pieces >= 3 ? 1.05f : 1f; }
public static float moveSpeedMultiplier(int pieces) { return pieces >= 3 ? 1.06f : 1f; }
public static float damageTakenMultiplier(int pieces) { return pieces >= 4 ? .90f : 1f; }
public static float dashInvulnerabilityBonus(int pieces) { return pieces >= 4 ? .06f : 0f; }
⋮----
public static String summary(int pieces) {
int safe = Math.max(0, Math.min(4, pieces));
```

## File: src/main/java/com/deadlinezero/game/meta/ThreatTierRules.java
```java
/** Persistent endgame difficulty ladder layered on top of stage progression. */
public final class ThreatTierRules {
⋮----
public static boolean unlocked(PlayerProfile profile) {
⋮----
public static int sanitizeTier(int tier) {
return Math.min(MAX_TIER, Math.max(0, tier));
⋮----
public static float enemyHpMultiplier(int tier) {
int t = sanitizeTier(tier);
⋮----
public static float enemyDamageMultiplier(int tier) {
return 1f + sanitizeTier(tier) * .055f;
⋮----
public static float enemySpeedMultiplier(int tier) {
return Math.min(1.28f, 1f + sanitizeTier(tier) * .014f);
⋮----
public static float spawnIntervalMultiplier(int tier) {
return Math.max(.72f, 1f - sanitizeTier(tier) * .014f);
⋮----
public static float rewardMultiplier(int tier) {
⋮----
public static int rewardBonusPercent(int tier) {
return Math.round((rewardMultiplier(tier) - 1f) * 100f);
⋮----
/** One-time premium reward when a 5-tier ascension milestone is first unlocked. */
public static int milestoneGemReward(int newlyUnlockedTier) {
int tier = sanitizeTier(newlyUnlockedTier);
```

## File: src/main/java/com/deadlinezero/game/meta/WeaponProgression.java
```java
/** Deterministic, non-paywalled weapon unlock progression. */
public final class WeaponProgression {
⋮----
public static boolean unlocked(PlayerProfile profile, WeaponDefinition weapon) {
⋮----
int level = Math.max(1, profile.accountLevel);
int stage = Math.max(1, profile.highestStage);
⋮----
public static int unlockAccountLevel(WeaponDefinition weapon) {
```

## File: src/main/java/com/deadlinezero/game/meta/WeaponSynergyRules.java
```java
/** Deterministic survivor x weapon build synergies applied once when a run loadout is snapshotted. */
public final class WeaponSynergyRules {
⋮----
public static Synergy resolve(SurvivorCatalog.Survivor survivor, WeaponDefinition weapon) {
```

## File: src/main/java/com/deadlinezero/game/meta/WeeklyProgress.java
```java
/** Persistent weekly progression snapshot keyed by deterministic UTC week index. */
public final class WeeklyProgress {
⋮----
public void resetForWeek(long week) {
```

## File: src/main/java/com/deadlinezero/game/meta/WeeklyService.java
```java
/** Weekly mission rules. Weeks are deterministic UTC Monday-based buckets. */
public final class WeeklyService {
⋮----
/** 1970-01-01 was Thursday; +3 aligns bucket boundaries to Monday. */
public static long weekIndexForEpochDay(long epochDay) {
return Math.floorDiv(epochDay + 3L, 7L);
⋮----
public static void refresh(PlayerProfile profile, long epochDay) {
⋮----
profile.weekly.resetForWeek(weekIndexForEpochDay(epochDay));
⋮----
public static void recordRun(PlayerProfile profile, int kills, boolean bossKilled) {
⋮----
profile.weekly.runs = DailyCounterMath.increment(profile.weekly.runs);
profile.weekly.kills = DailyCounterMath.addKills(profile.weekly.kills, kills);
if (bossKilled) profile.weekly.bosses = DailyCounterMath.increment(profile.weekly.bosses);
⋮----
public static boolean claimKillMission(PlayerProfile profile) {
⋮----
profile.addCurrency(PlayerProfile.Currency.CREDITS, 2500L);
⋮----
public static boolean claimRunMission(PlayerProfile profile) {
⋮----
profile.addCurrency(PlayerProfile.Currency.CREDITS, 3500L);
⋮----
public static boolean claimBossMission(PlayerProfile profile) {
⋮----
profile.addCurrency(PlayerProfile.Currency.GEMS, 12L);
```

## File: src/main/java/com/deadlinezero/game/perf/AdaptiveFrameRateGovernor.java
```java
/**
 * Runtime-only FPS governor. The user's selected frame rate is a ceiling; sustained instability
 * can temporarily reduce the effective cap. Recovery is deliberately slower than degradation.
 */
public final class AdaptiveFrameRateGovernor {
⋮----
public int effectiveTarget() { return effectiveTarget; }
⋮----
public void reset(int selectedTarget) {
effectiveTarget = normalize(selectedTarget);
⋮----
public int update(int selectedTarget, PerformanceTelemetry.Snapshot snapshot) {
int selected = normalize(selectedTarget);
⋮----
if (snapshot == null || snapshot.targetFps() != effectiveTarget || !snapshot.stable()) {
⋮----
int lower = lowerTarget(effectiveTarget);
⋮----
effectiveTarget = Math.min(selected, higherTarget(effectiveTarget));
⋮----
static int normalize(int target) {
⋮----
private static int lowerTarget(int target) {
⋮----
private static int higherTarget(int target) {
```

## File: src/main/java/com/deadlinezero/game/perf/PerformanceTelemetry.java
```java
/**
 * Lightweight rolling frame-time telemetry for launch-quality validation.
 * Keeps gameplay behavior untouched while exposing objective stability metrics.
 */
public final class PerformanceTelemetry {
⋮----
public void record(float deltaSeconds, int targetFps) {
if (!Float.isFinite(deltaSeconds) || deltaSeconds <= 0f) return;
float ms = Math.min(250f, deltaSeconds * 1000f);
⋮----
public int sampleCount() { return count; }
⋮----
public Snapshot snapshot(int targetFps) {
int safeTarget = Math.max(1, targetFps);
if (count == 0) return new Snapshot(safeTarget, 0f, 0f, 0f, 0f, false);
⋮----
Arrays.sort(sorted);
⋮----
float p95 = percentile(sorted, .95f);
float p99 = percentile(sorted, .99f);
⋮----
return new Snapshot(safeTarget, averageFps, p95, p99, jankRatio, stable);
⋮----
private static float percentile(float[] sorted, float percentile) {
⋮----
int index = Math.min(sorted.length - 1,
Math.max(0, (int)Math.ceil(percentile * sorted.length) - 1));
```

## File: src/main/java/com/deadlinezero/game/perf/ThermalBudgetPolicy.java
```java
/** Single source of truth for thermal performance ceilings used by runtime FPS and FX budgets. */
public final class ThermalBudgetPolicy {
⋮----
public static int allowedFps(int userTarget, ThermalService.Level level) {
⋮----
return Math.min(normalizeUserTarget(userTarget), safe.fpsCeiling);
⋮----
public static float fxCeiling(ThermalService.Level level) {
⋮----
private static int normalizeUserTarget(int target) {
```

## File: src/main/java/com/deadlinezero/game/progression/CombatProtocolState.java
```java
/** Allocation-free run-local event protocol state. */
public final class CombatProtocolState {
⋮----
static final VolleyModifier NONE = new VolleyModifier(1f, false, 0);
⋮----
public void enableRhythm() { rhythmEnabled = true; }
public void enableKillchain() { killchainEnabled = true; }
public void enableReactionCore() { reactionEnabled = true; }
public void evolveRhythm() { if (rhythmEnabled) rhythmEvolved = true; }
public void evolveKillchain() { if (killchainEnabled) killchainEvolved = true; }
public void evolveReactionCore() { if (reactionEnabled) reactionEvolved = true; }
⋮----
public boolean rhythmEnabled() { return rhythmEnabled; }
public boolean killchainEnabled() { return killchainEnabled; }
public boolean reactionEnabled() { return reactionEnabled; }
public boolean rhythmEvolved() { return rhythmEvolved; }
public boolean killchainEvolved() { return killchainEvolved; }
public boolean reactionEvolved() { return reactionEvolved; }
public boolean killchainArmed() { return killchainArmed; }
⋮----
public VolleyModifier onVolley() {
⋮----
? Math.min(2.05f, rhythmDamage + killDamage - 1f)
⋮----
return new VolleyModifier(damage, rhythmProc, penetration);
⋮----
public boolean onKill() {
⋮----
public float reactionBonus(float triggeringDamage, Enemy.ElementReaction reaction) {
⋮----
return Math.max(0f, triggeringDamage) * (reactionEvolved ? .55f : .35f);
```

## File: src/main/java/com/deadlinezero/game/progression/LegendaryChoice.java
```java
/** Standalone run-local legendary choices, intentionally separate from standard Upgrade. */
⋮----
@Override public boolean available(Player p) { return !p.legendary.hasOverdrive(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyOverdrive(p); }
⋮----
@Override public boolean available(Player p) { return !p.legendary.hasSingularity(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applySingularity(p); }
⋮----
@Override public boolean available(Player p) { return !p.legendary.hasApex(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyApex(p); }
⋮----
@Override public boolean available(Player p) { return weapon(p, "ar9") && !p.legendary.hasVanguardProtocol(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyVanguardProtocol(p); }
⋮----
@Override public boolean available(Player p) { return weapon(p, "scattergun") && !p.legendary.hasScatterMaelstrom(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyScatterMaelstrom(p); }
⋮----
@Override public boolean available(Player p) { return weapon(p, "inferno_smg") && !p.legendary.hasInfernoPyroclasm(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyInfernoPyroclasm(p); }
⋮----
@Override public boolean available(Player p) { return weapon(p, "breacher") && !p.legendary.hasBreacherRupture(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyBreacherRupture(p); }
⋮----
@Override public boolean available(Player p) { return weapon(p, "ion_needle") && !p.legendary.hasIonCascade(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyIonCascade(p); }
⋮----
@Override public boolean available(Player p) { return weapon(p, "cinder_cannon") && !p.legendary.hasCinderFurnace(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyCinderFurnace(p); }
⋮----
@Override public boolean available(Player p) { return weapon(p, "rail_rifle") && !p.legendary.hasRailPhaseLance(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyRailPhaseLance(p); }
⋮----
@Override public boolean available(Player p) { return weapon(p, "cryo_lance") && !p.legendary.hasCryoPrism(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyCryoPrism(p); }
⋮----
@Override public boolean available(Player p) { return weapon(p, "arc_carbine") && !p.legendary.hasArcOverload(); }
@Override public boolean apply(Player p) { return LegendaryEffects.applyArcOverload(p); }
⋮----
public String titleKey() { return "legendary." + name().toLowerCase(java.util.Locale.ROOT) + ".title"; }
public String descriptionKey() { return "legendary." + name().toLowerCase(java.util.Locale.ROOT) + ".description"; }
⋮----
public final boolean eligible(Player player) { return player != null && player.level >= minimumLevel && available(player); }
private static boolean weapon(Player player, String id) {
return player != null && player.weapon != null && player.weapon.definition != null && id.equals(player.weapon.definition.id);
⋮----
public abstract boolean available(Player player);
public abstract boolean apply(Player player);
```

## File: src/main/java/com/deadlinezero/game/progression/LegendaryEffects.java
```java
/** Applies one-shot legendary transformations while LegendaryState owns eligibility. */
public final class LegendaryEffects {
⋮----
public static boolean applyOverdrive(Player player) {
if (!player.legendary.grantOverdrive()) return false;
⋮----
player.weapon.fireInterval = Math.max(.045f, player.weapon.fireInterval * .76f);
⋮----
player.dashCooldown = Math.max(1.1f, player.dashCooldown * .72f);
⋮----
public static boolean applySingularity(Player player) {
if (!player.legendary.grantSingularity()) return false;
⋮----
public static boolean applyApex(Player player) {
if (!player.legendary.grantApex()) return false;
for (AbilityType type : AbilityType.values()) while (player.abilities.level(type) < 3) player.abilities.upgrade(type);
⋮----
public static boolean applyVanguardProtocol(Player player) {
if (!player.legendary.grantVanguardProtocol()) return false;
⋮----
player.weapon.fireInterval = Math.max(.055f, player.weapon.fireInterval * .90f);
⋮----
public static boolean applyScatterMaelstrom(Player player) {
if (!player.legendary.grantScatterMaelstrom()) return false;
⋮----
public static boolean applyInfernoPyroclasm(Player player) {
if (!player.legendary.grantInfernoPyroclasm()) return false;
⋮----
player.weapon.fireInterval = Math.max(.055f, player.weapon.fireInterval * .94f);
⋮----
public static boolean applyBreacherRupture(Player player) {
if (!player.legendary.grantBreacherRupture()) return false;
⋮----
public static boolean applyIonCascade(Player player) {
if (!player.legendary.grantIonCascade()) return false;
WeaponSignatureRuntime.enableIonCascade();
player.weapon.critChance = Math.min(.75f, player.weapon.critChance + .04f);
⋮----
public static boolean applyCinderFurnace(Player player) {
if (!player.legendary.grantCinderFurnace()) return false;
WeaponSignatureRuntime.enableCinderFurnace();
player.weapon.fireInterval = Math.max(.30f, player.weapon.fireInterval * .94f);
⋮----
public static boolean applyRailPhaseLance(Player player) {
if (!player.legendary.grantRailPhaseLance()) return false;
⋮----
player.weapon.critChance = Math.min(.75f, player.weapon.critChance + .08f);
⋮----
public static boolean applyCryoPrism(Player player) {
if (!player.legendary.grantCryoPrism()) return false;
⋮----
public static boolean applyArcOverload(Player player) {
if (!player.legendary.grantArcOverload()) return false;
```

## File: src/main/java/com/deadlinezero/game/progression/LegendarySelector.java
```java
/** Allocation-light selector for exceptional legendary offers. */
public final class LegendarySelector {
private static final LegendaryChoice[] ALL = LegendaryChoice.values();
⋮----
public static int fillChoices(Player player, LegendaryChoice[] out) {
⋮----
if (choice.eligible(player)) ELIGIBLE[count++] = choice;
⋮----
int written = Math.min(out.length, count);
⋮----
int pick = MathUtils.random(i, count - 1);
⋮----
/** Rare enough to feel exceptional while guaranteeing exposure in longer runs. */
public static boolean shouldOffer(Player player) {
⋮----
for (LegendaryChoice choice : ALL) if (choice.eligible(player)) return true;
```

## File: src/main/java/com/deadlinezero/game/progression/LegendaryState.java
```java
/** Run-local ownership flags for one-shot legendary upgrades. */
public final class LegendaryState {
⋮----
public boolean hasOverdrive() { return overdrive; }
public boolean hasSingularity() { return singularity; }
public boolean hasApex() { return apex; }
public boolean hasIonCascade() { return ionCascade; }
public boolean hasCinderFurnace() { return cinderFurnace; }
public boolean hasRailPhaseLance() { return railPhaseLance; }
public boolean hasCryoPrism() { return cryoPrism; }
public boolean hasArcOverload() { return arcOverload; }
public boolean hasVanguardProtocol() { return vanguardProtocol; }
public boolean hasScatterMaelstrom() { return scatterMaelstrom; }
public boolean hasInfernoPyroclasm() { return infernoPyroclasm; }
public boolean hasBreacherRupture() { return breacherRupture; }
public boolean hasAny() {
⋮----
public boolean grantOverdrive() { if (overdrive) return false; overdrive = true; return true; }
public boolean grantSingularity() { if (singularity) return false; singularity = true; return true; }
public boolean grantApex() { if (apex) return false; apex = true; return true; }
public boolean grantIonCascade() { if (ionCascade) return false; ionCascade = true; return true; }
public boolean grantCinderFurnace() { if (cinderFurnace) return false; cinderFurnace = true; return true; }
public boolean grantRailPhaseLance() { if (railPhaseLance) return false; railPhaseLance = true; return true; }
public boolean grantCryoPrism() { if (cryoPrism) return false; cryoPrism = true; return true; }
public boolean grantArcOverload() { if (arcOverload) return false; arcOverload = true; return true; }
public boolean grantVanguardProtocol() { if (vanguardProtocol) return false; vanguardProtocol = true; return true; }
public boolean grantScatterMaelstrom() { if (scatterMaelstrom) return false; scatterMaelstrom = true; return true; }
public boolean grantInfernoPyroclasm() { if (infernoPyroclasm) return false; infernoPyroclasm = true; return true; }
public boolean grantBreacherRupture() { if (breacherRupture) return false; breacherRupture = true; return true; }
```

## File: src/main/java/com/deadlinezero/game/progression/ProtocolUpgradeGuidance.java
```java
/** Presentation-only guidance for the two-stage combat protocol build paths. */
public final class ProtocolUpgradeGuidance {
⋮----
public static String key(Player player, Upgrade upgrade) {
⋮----
player.protocols.rhythmEnabled() && !player.protocols.rhythmEvolved()
⋮----
player.protocols.killchainEnabled() && !player.protocols.killchainEvolved()
⋮----
player.protocols.reactionEnabled() && !player.protocols.reactionEvolved()
```

## File: src/main/java/com/deadlinezero/game/progression/Upgrade.java
```java
/**
 * Run-local upgrade catalog.
 *
 * Keep effects inside explicit mobile-safe caps so long runs cannot create pathological projectile,
 * movement or fire-rate values. Legendary transformations remain separate in LegendaryChoice.
 */
⋮----
public void apply(Player p) { p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .82f); }
⋮----
public void apply(Player p) { p.weapon.damage = damage(p.weapon.damage * 1.25f); }
⋮----
public void apply(Player p) { p.moveSpeed = moveSpeed(p.moveSpeed * 1.14f); }
⋮----
public void apply(Player p) { increaseMaxHp(p, 25f, 35f); }
⋮----
public void apply(Player p) { p.weapon.projectileCount = Math.min(MAX_PROJECTILES, p.weapon.projectileCount + 1); }
⋮----
public void apply(Player p) { p.weapon.critChance = critChance(p.weapon.critChance + .08f); }
⋮----
public void apply(Player p) { p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.22f); }
⋮----
public void apply(Player p) { p.weapon.penetration = Math.min(MAX_PENETRATION, p.weapon.penetration + 1); }
⋮----
public void apply(Player p) { p.weapon.knockback = knockback(p.weapon.knockback * 1.35f); }
⋮----
public void apply(Player p) {
⋮----
p.weapon.damage = damage(p.weapon.damage * 1.14f);
p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * 1.06f);
⋮----
p.weapon.knockback = knockback(p.weapon.knockback * 1.18f);
p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.10f);
⋮----
p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .90f);
p.weapon.damage = damage(p.weapon.damage * .94f);
⋮----
public void apply(Player p) { p.weapon.critMultiplier = critMultiplier(p.weapon.critMultiplier + .25f); }
⋮----
p.weapon.damage = damage(p.weapon.damage * 1.18f);
⋮----
p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * 1.08f);
⋮----
p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.35f);
p.weapon.damage = damage(p.weapon.damage * .95f);
⋮----
p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .88f);
p.weapon.knockback = knockback(p.weapon.knockback * 1.15f);
⋮----
p.weapon.damage = damage(p.weapon.damage * 1.20f);
p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees * .88f);
⋮----
p.weapon.damage = damage(p.weapon.damage * 1.32f);
reduceMaxHp(p, .88f);
⋮----
p.weapon.critChance = critChance(p.weapon.critChance + .05f);
p.weapon.critMultiplier = critMultiplier(p.weapon.critMultiplier + .30f);
⋮----
p.weapon.penetration = Math.min(MAX_PENETRATION, p.weapon.penetration + 1);
p.weapon.damage = damage(p.weapon.damage * 1.12f);
p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * 1.10f);
⋮----
p.weapon.projectileCount = Math.min(MAX_PROJECTILES, p.weapon.projectileCount + 1);
p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees + 2.5f);
⋮----
p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees * .72f);
p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.08f);
⋮----
p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.45f);
p.weapon.knockback = knockback(p.weapon.knockback * 1.10f);
⋮----
p.weapon.knockback = knockback(p.weapon.knockback * 1.55f);
p.weapon.damage = damage(p.weapon.damage * 1.08f);
⋮----
p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .91f);
p.weapon.critChance = critChance(p.weapon.critChance + .04f);
⋮----
p.weapon.damage = damage(p.weapon.damage * 1.24f);
p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees * 1.10f + .4f);
p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * .94f);
⋮----
p.moveSpeed = moveSpeed(p.moveSpeed * 1.10f);
p.dashCooldown = dashCooldown(p.dashCooldown * .92f);
⋮----
increaseMaxHp(p, 35f, 20f);
p.moveSpeed = moveSpeed(p.moveSpeed * .96f);
⋮----
increaseMaxHp(p, 18f, 18f);
p.moveSpeed = moveSpeed(p.moveSpeed * 1.07f);
⋮----
p.dashCooldown = dashCooldown(p.dashCooldown * .86f);
p.moveSpeed = moveSpeed(p.moveSpeed * 1.04f);
⋮----
p.moveSpeed = moveSpeed(p.moveSpeed * 1.16f);
reduceMaxHp(p, .94f);
⋮----
increaseMaxHp(p, 30f, 12f);
p.weapon.knockback = knockback(p.weapon.knockback * 1.12f);
⋮----
p.weapon.critChance = critChance(p.weapon.critChance + .06f);
reduceMaxHp(p, .92f);
⋮----
public void apply(Player p) { increaseMaxHp(p, 20f, 45f); }
⋮----
p.weapon.critChance = critChance(p.weapon.critChance + .03f);
⋮----
p.weapon.knockback = knockback(p.weapon.knockback * 1.22f);
p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .95f);
⋮----
p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .92f);
⋮----
p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.20f);
⋮----
p.weapon.knockback = knockback(p.weapon.knockback * 1.30f);
⋮----
p.weapon.projectileSpeed = projectileSpeed(p.weapon.projectileSpeed * 1.18f);
⋮----
p.weapon.damage = damage(p.weapon.damage * 1.10f);
⋮----
p.weapon.critMultiplier = critMultiplier(p.weapon.critMultiplier + .20f);
p.weapon.spreadDegrees = spread(p.weapon.spreadDegrees * .85f);
⋮----
p.weapon.fireInterval = fireInterval(p.weapon.fireInterval * .94f);
p.weapon.damage = damage(p.weapon.damage * .92f);
⋮----
p.weapon.damage = damage(p.weapon.damage * 1.16f);
p.weapon.knockback = knockback(p.weapon.knockback * 1.16f);
⋮----
p.moveSpeed = moveSpeed(p.moveSpeed * 1.09f);
p.weapon.knockback = knockback(p.weapon.knockback * 1.20f);
⋮----
increaseMaxHp(p, 12f, 12f);
⋮----
public void apply(Player p) { p.abilities.upgrade(AbilityType.TESLA_ORB); }
⋮----
public void apply(Player p) { p.abilities.upgrade(AbilityType.MISSILE_SWARM); }
⋮----
public void apply(Player p) { p.abilities.upgrade(AbilityType.CRYO_NOVA); }
⋮----
public void apply(Player p) { p.abilities.upgrade(AbilityType.DRONE); }
⋮----
public void apply(Player p) { p.abilities.chooseDroneDoctrine(DroneDoctrine.HUNTER); }
⋮----
public void apply(Player p) { p.abilities.chooseDroneDoctrine(DroneDoctrine.SENTINEL); }
⋮----
public void apply(Player p) { p.abilities.upgrade(AbilityType.ORBITAL_BLADE); }
⋮----
public void apply(Player p) { p.protocols.enableRhythm(); }
⋮----
public void apply(Player p) { p.protocols.evolveRhythm(); }
⋮----
public void apply(Player p) { p.protocols.enableKillchain(); }
⋮----
public void apply(Player p) { p.protocols.evolveKillchain(); }
⋮----
public void apply(Player p) { p.protocols.enableReactionCore(); }
⋮----
public void apply(Player p) { p.protocols.evolveReactionCore(); }
⋮----
public void apply(Player p) { p.dashCooldown = dashCooldown(p.dashCooldown * .82f); }
⋮----
public String titleKey() { return "upgrade." + name().toLowerCase(java.util.Locale.ROOT) + ".title"; }
public String descriptionKey() { return "upgrade." + name().toLowerCase(java.util.Locale.ROOT) + ".description"; }
⋮----
public abstract void apply(Player player);
⋮----
static float damage(float value) { return Math.max(.1f, Math.min(MAX_DAMAGE, value)); }
static float fireInterval(float value) { return Math.max(MIN_FIRE_INTERVAL, value); }
static float moveSpeed(float value) { return Math.max(1f, Math.min(MAX_MOVE_SPEED, value)); }
static float projectileSpeed(float value) { return Math.max(1f, Math.min(MAX_PROJECTILE_SPEED, value)); }
static float spread(float value) { return Math.max(0f, Math.min(MAX_SPREAD_DEGREES, value)); }
static float critChance(float value) { return Math.max(0f, Math.min(MAX_CRIT_CHANCE, value)); }
static float critMultiplier(float value) { return Math.max(1f, Math.min(MAX_CRIT_MULTIPLIER, value)); }
static float knockback(float value) { return Math.max(0f, Math.min(MAX_KNOCKBACK, value)); }
static float dashCooldown(float value) { return Math.max(MIN_DASH_COOLDOWN, value); }
⋮----
static void increaseMaxHp(Player p, float amount, float heal) {
p.maxHp = Math.min(MAX_HP, Math.max(MIN_HP, p.maxHp + Math.max(0f, amount)));
p.hp = Math.min(p.maxHp, p.hp + Math.max(0f, heal));
⋮----
static void reduceMaxHp(Player p, float multiplier) {
p.maxHp = Math.max(MIN_HP, Math.min(MAX_HP, p.maxHp * multiplier));
p.hp = Math.min(p.hp, p.maxHp);
```

## File: src/main/java/com/deadlinezero/game/progression/UpgradeDraftPolicy.java
```java
/**
 * Build-affinity policy for level-up drafts.
 *
 * Once a run has established an elemental or ability identity, one draft slot stays relevant to
 * that identity while the remaining slots preserve broad roguelite discovery.
 */
final class UpgradeDraftPolicy {
⋮----
static boolean hasEstablishedBuild(Player player) {
⋮----
if (player.protocols.rhythmEnabled() || player.protocols.killchainEnabled() || player.protocols.reactionEnabled()) return true;
for (AbilityType type : AbilityType.values()) {
if (player.abilities.level(type) >= 2) return true;
⋮----
static boolean isFocusedCandidate(Player player, Upgrade upgrade) {
return affinityMultiplier(player, upgrade) >= 1.50f;
⋮----
static float affinityMultiplier(Player player, Upgrade upgrade) {
⋮----
float multiplier = elementalAffinity(player, upgrade);
multiplier *= abilityAffinity(player, upgrade);
multiplier *= protocolAffinity(player, upgrade);
return Math.max(.45f, Math.min(3.25f, multiplier));
⋮----
private static float elementalAffinity(Player player, Upgrade upgrade) {
⋮----
DamageElement family = elementFamily(upgrade);
⋮----
private static DamageElement elementFamily(Upgrade upgrade) {
⋮----
private static float protocolAffinity(Player player, Upgrade upgrade) {
⋮----
case RHYTHM_ACCELERATOR -> player.protocols.rhythmEnabled() ? 2.85f : 1f;
case KILLCHAIN_OVERCHARGE -> player.protocols.killchainEnabled() ? 2.85f : 1f;
case REACTION_CASCADE -> player.protocols.reactionEnabled() ? 2.85f : 1f;
⋮----
private static float abilityAffinity(Player player, Upgrade upgrade) {
AbilityType offered = abilityType(upgrade);
⋮----
int ownLevel = player.abilities.level(offered);
⋮----
case TESLA_ORB -> partnerBoost(player, AbilityType.CRYO_NOVA, AbilityType.DRONE, AbilityType.ORBITAL_BLADE);
case MISSILE_SWARM -> partnerBoost(player, AbilityType.CRYO_NOVA, AbilityType.DRONE);
case CRYO_NOVA -> partnerBoost(player, AbilityType.TESLA_ORB, AbilityType.MISSILE_SWARM, AbilityType.ORBITAL_BLADE);
case DRONE -> partnerBoost(player, AbilityType.TESLA_ORB, AbilityType.MISSILE_SWARM);
case ORBITAL_BLADE -> partnerBoost(player, AbilityType.CRYO_NOVA, AbilityType.TESLA_ORB);
⋮----
private static float partnerBoost(Player player, AbilityType... partners) {
⋮----
for (AbilityType partner : partners) strongest = Math.max(strongest, player.abilities.level(partner));
⋮----
private static AbilityType abilityType(Upgrade upgrade) {
```

## File: src/main/java/com/deadlinezero/game/progression/UpgradeRarity.java
```java

```

## File: src/main/java/com/deadlinezero/game/progression/UpgradeSelector.java
```java
/** Weighted, allocation-light upgrade selection with max-level filtering. */
public final class UpgradeSelector {
private static final Upgrade[] ALL = Upgrade.values();
⋮----
public static void fillChoices(Player player, Upgrade[] out) {
boolean focusedDraft = UpgradeDraftPolicy.hasEstablishedBuild(player);
⋮----
int count = collectEligible(player, out, slot, focusedSlot);
if (count == 0 && focusedSlot) count = collectEligible(player, out, slot, false);
⋮----
float roll = MathUtils.random(total);
⋮----
private static int collectEligible(Player player, Upgrade[] chosen, int chosenCount, boolean focusedOnly) {
⋮----
if (!isAvailable(player, upgrade)) continue;
if (focusedOnly && !UpgradeDraftPolicy.isFocusedCandidate(player, upgrade)) continue;
⋮----
WEIGHTS[count] = rarityWeight(upgrade.rarity) * UpgradeDraftPolicy.affinityMultiplier(player, upgrade);
⋮----
public static boolean isBuildFocusedChoice(Player player, Upgrade upgrade) {
return UpgradeDraftPolicy.hasEstablishedBuild(player)
&& UpgradeDraftPolicy.isFocusedCandidate(player, upgrade);
⋮----
static boolean isAvailable(Player p, Upgrade u) {
⋮----
case TESLA_ORB -> p.abilities.level(AbilityType.TESLA_ORB) < 5;
case MISSILE_SWARM -> p.abilities.level(AbilityType.MISSILE_SWARM) < 5;
case CRYO_NOVA -> p.abilities.level(AbilityType.CRYO_NOVA) < 5;
case DRONE -> p.abilities.level(AbilityType.DRONE) < 5;
⋮----
p.abilities.tier(AbilityType.DRONE) >= 2 && !p.abilities.hasDroneDoctrine();
case ORBITAL -> p.abilities.level(AbilityType.ORBITAL_BLADE) < 5;
case RHYTHM_DRIVER -> !p.protocols.rhythmEnabled();
case RHYTHM_ACCELERATOR -> p.protocols.rhythmEnabled() && !p.protocols.rhythmEvolved();
case KILLCHAIN_CAPACITOR -> !p.protocols.killchainEnabled();
case KILLCHAIN_OVERCHARGE -> p.protocols.killchainEnabled() && !p.protocols.killchainEvolved();
case REACTION_CORE -> !p.protocols.reactionEnabled();
case REACTION_CASCADE -> p.protocols.reactionEnabled() && !p.protocols.reactionEvolved();
⋮----
private static float rarityWeight(UpgradeRarity rarity) {
```

## File: src/main/java/com/deadlinezero/game/screen/ArsenalScreen.java
```java
/** Responsive production Arsenal with clear focus/equipped/locked states. */
public final class ArsenalScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final ShapeRenderer shapes = new ShapeRenderer();
private final BitmapFont font = new BitmapFont();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
⋮----
WeaponDefinition selected = WeaponCatalog.byId(game.profile.selectedWeaponId);
WeaponDefinition[] all = WeaponCatalog.all();
for (int i = 0; i < all.length; i++) if (all[i].id.equals(selected.id)) focus = i;
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
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
⋮----
cardBounds[i] = ResponsiveGrid.cardBounds(i, metrics.safeLeft(), metrics.contentTop(), cardHeight, grid);
⋮----
float headerH = metrics.safeTop() - metrics.headerBottom();
back = new Rectangle(metrics.safeLeft(), metrics.headerBottom(), 128f, headerH);
nextPage = new Rectangle(metrics.safeRight() - 64f, metrics.headerBottom() + (headerH - 56f) * .5f, 64f, 56f);
previousPage = new Rectangle(nextPage.x - 76f, nextPage.y, 64f, 56f);
⋮----
@Override public void render(float delta) {
visualTime += Math.max(0f, delta);
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
focus = MathUtils.clamp(focus, 0, Math.max(0, all.length - 1));
⋮----
int pageEnd = Math.min(all.length, pageStart + PAGE_SIZE);
int pageCount = Math.max(1, (all.length + PAGE_SIZE - 1) / PAGE_SIZE);
⋮----
WeaponDefinition equipped = WeaponCatalog.byId(game.profile.selectedWeaponId);
⋮----
drawShapes(all, pageStart, pageEnd, page, pageCount, focusedWeapon, equipped);
drawText(all, pageStart, pageEnd, page, pageCount, focusedWeapon, equipped);
handleInput(all, pageStart, pageEnd);
⋮----
private void drawShapes(WeaponDefinition[] all, int pageStart, int pageEnd, int page, int pageCount,
⋮----
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, visualTime);
UiRenderer.topRail(shapes, metrics);
UiRenderer.premiumPanel(shapes, detail.x, detail.y, detail.width, detail.height, elementColor(focusedWeapon), true);
drawDetailChrome(shapes, focusedWeapon, equipped);
⋮----
boolean selected = weapon.id.equals(game.profile.selectedWeaponId);
boolean unlocked = WeaponProgression.unlocked(game.profile, weapon);
UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, elementColor(weapon), i == focus, selected, !unlocked);
drawWeaponCardChrome(shapes, r, weapon, i == focus, selected, unlocked);
⋮----
shapes.setColor(0f, 0f, 0f, .28f);
shapes.rect(r.x + 3f, r.y + 3f, r.width - 6f, r.height - 6f);
shapes.setColor(VisualTheme.GOLD);
shapes.rect(r.x + 10f, r.y + r.height - 5f, Math.min(56f, r.width * .18f), 2f);
⋮----
UiRenderer.premiumButton(shapes, previousPage.x, previousPage.y, previousPage.width, previousPage.height, VisualTheme.CYAN_SOFT,
⋮----
UiRenderer.premiumButton(shapes, nextPage.x, nextPage.y, nextPage.width, nextPage.height, VisualTheme.CYAN_SOFT,
⋮----
drawChevron(previousPage, false, page > 0);
drawChevron(nextPage, true, page < pageCount - 1);
⋮----
drawStatBars(focusedWeapon, equipped);
shapes.end();
⋮----
private void drawWeaponCardChrome(ShapeRenderer shapes, Rectangle r, WeaponDefinition weapon,
⋮----
Color accent = unlocked ? elementColor(weapon) : VisualTheme.MUTED;
⋮----
shapes.setColor(accent.r, accent.g, accent.b, alpha);
shapes.rect(r.x + 6f, r.y + 6f, 4f, Math.max(0f, r.height - 12f));
shapes.rect(r.x + 10f, r.y + r.height - 5f, Math.max(0f, r.width - 16f), 3f);
⋮----
shapes.setColor(accent.r, accent.g, accent.b, equipped ? .095f : .060f);
shapes.rect(r.x + 10f, r.y + 8f, Math.max(0f, r.width - 18f), Math.max(0f, r.height - 16f));
⋮----
float markerW = Math.min(64f, r.width * .20f);
shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .92f);
shapes.rect(r.x + r.width - markerW - 8f, r.y + 8f, markerW, 3f);
⋮----
private void drawDetailChrome(ShapeRenderer shapes, WeaponDefinition weapon, WeaponDefinition equipped) {
Color accent = elementColor(weapon);
float dpsDelta = paperDps(weapon) - paperDps(equipped);
Color compare = Math.abs(dpsDelta) < .05f ? VisualTheme.TEXT_DIM
: dpsDelta > 0f ? VisualTheme.positive() : VisualTheme.danger();
⋮----
shapes.setColor(accent.r, accent.g, accent.b, .76f);
shapes.rect(detail.x + 5f, detail.y + detail.height - 5f, Math.max(0f, detail.width - 10f), 3f);
⋮----
shapes.setColor(accent.r, accent.g, accent.b, .06f);
shapes.rect(previewX, detail.y + 10f, Math.max(0f, previewW), Math.max(0f, detail.height - 20f));
⋮----
shapes.setColor(VisualTheme.BORDER.r, VisualTheme.BORDER.g, VisualTheme.BORDER.b, .48f);
shapes.rect(splitX, detail.y + 12f, 2f, Math.max(0f, detail.height - 24f));
⋮----
shapes.setColor(compare.r, compare.g, compare.b, .68f);
shapes.rect(splitX + 12f, detail.y + 9f, Math.max(0f, detail.width - (splitX - detail.x) - 24f), 3f);
⋮----
private void drawChevron(Rectangle bounds, boolean right, boolean enabled) {
⋮----
float half = Math.min(bounds.width, bounds.height) * .15f;
⋮----
shapes.setColor(enabled ? VisualTheme.TEXT_STRONG : VisualTheme.MUTED);
float stroke = Math.max(3f, half * .30f);
shapes.rectLine(tail, cy + half, tip, cy, stroke);
shapes.rectLine(tip, cy, tail, cy - half, stroke);
⋮----
private void drawText(WeaponDefinition[] all, int pageStart, int pageEnd, int page, int pageCount,
⋮----
batch.begin();
drawHeader(page, pageCount);
for (int i = pageStart; i < pageEnd; i++) drawCard(all[i], i, pageStart);
drawDetail(focusedWeapon, equipped);
batch.end();
⋮----
private void drawHeader(int page, int pageCount) {
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, t("shop.back"), back.x + 10f, back.y + back.height * .57f, back.width - 14f, Align.left, false);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t("arsenal.title"), metrics.safeLeft() + 142f, metrics.headerBottom() + 55f,
metrics.contentWidth() - 430f, Align.left, false);
⋮----
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, t("arsenal.subtitle"), metrics.safeLeft() + 144f, metrics.headerBottom() + 29f,
⋮----
font.draw(batch, f("arsenal.page", page + 1, pageCount), previousPage.x - 142f,
⋮----
private void drawCard(WeaponDefinition weapon, int absoluteIndex, int pageStart) {
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
font.setColor(unlocked ? VisualTheme.TEXT_STRONG : VisualTheme.MUTED);
font.draw(batch, t(weapon.displayNameKey()).toUpperCase(), r.x + 14f, r.y + r.height - 14f,
⋮----
font.setColor(unlocked ? elementColor(weapon) : VisualTheme.MUTED);
font.draw(batch, pair(role(weapon), elementName(weapon)), r.x + 14f, r.y + r.height - 36f,
⋮----
if (!unlocked) status = f("arsenal.locked", WeaponProgression.unlockAccountLevel(weapon));
else if (selected) status = t("arsenal.equipped");
else if (absoluteIndex == focus) status = t("arsenal.select");
else status = t("arsenal.available");
font.setColor(!unlocked ? VisualTheme.GOLD : selected ? VisualTheme.accent() : VisualTheme.TEXT_DIM);
font.draw(batch, status, r.x + 14f, r.y + 15f, r.width - 28f, Align.right, false);
⋮----
font.draw(batch, f("arsenal.cardStats", Math.round(paperDps(weapon)), Math.round(weapon.damage), weapon.projectileCount),
⋮----
private void drawDetail(WeaponDefinition weapon, WeaponDefinition equipped) {
⋮----
drawAuthoredPreview(weapon, detail.x + 22f, detail.y + 76f, leftW * .84f, detail.height * .58f);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
⋮----
font.draw(batch, t(weapon.displayNameKey()).toUpperCase(), textX, top, textW, Align.left, false);
⋮----
font.setColor(elementColor(weapon));
font.draw(batch, pair(role(weapon), elementName(weapon)), textX, top - 26f, textW, Align.left, false);
⋮----
float dps = paperDps(weapon), equippedDps = paperDps(equipped);
⋮----
font.draw(batch, f("arsenal.detailDps", Math.round(dps), deltaText(dps - equippedDps),
String.format(java.util.Locale.US, "%.2f", weapon.fireInterval), Math.round(weapon.critChance * 100f)),
⋮----
font.draw(batch, f("arsenal.detailPen", weapon.penetration, deltaText(weapon.penetration - equipped.penetration),
oneDecimal(weapon.knockback), deltaText(weapon.knockback - equipped.knockback), weapon.projectileCount,
deltaText(weapon.projectileCount - equipped.projectileCount)), textX, top - 72f, textW, Align.left, false);
⋮----
font.draw(batch, description(weapon), textX, top - 94f, textW, Align.left, true);
WeaponSynergyRules.Synergy synergy = WeaponSynergyRules.resolve(game.profile.selectedSurvivor, weapon);
⋮----
font.setColor(VisualTheme.GOLD);
font.draw(batch, f("arsenal.synergy", synergy.displayName), textX, detail.y + 18f, textW, Align.left, false);
⋮----
private void drawStatBars(WeaponDefinition weapon, WeaponDefinition equipped) {
⋮----
drawStatBar(x, baseY, width, normalizeDps(weapon), normalizeDps(equipped), VisualTheme.accent());
drawStatBar(x, baseY + gap, width, MathUtils.clamp((1f / weapon.fireInterval) / 8f, 0f, 1f),
MathUtils.clamp((1f / equipped.fireInterval) / 8f, 0f, 1f), VisualTheme.CYAN_SOFT);
drawStatBar(x, baseY + gap * 2f, width, MathUtils.clamp(weapon.penetration / 5f, 0f, 1f),
MathUtils.clamp(equipped.penetration / 5f, 0f, 1f), VisualTheme.GOLD);
drawStatBar(x, baseY + gap * 3f, width, MathUtils.clamp(weapon.knockback / 5f, 0f, 1f),
MathUtils.clamp(equipped.knockback / 5f, 0f, 1f), VisualTheme.VIOLET);
⋮----
private void drawStatBar(float x, float y, float width, float value, float baseline, Color color) {
shapes.setColor(VisualTheme.SURFACE_0);
shapes.rect(x, y, width, 6f);
shapes.setColor(VisualTheme.MUTED.r, VisualTheme.MUTED.g, VisualTheme.MUTED.b, .35f);
shapes.rect(x, y, width * baseline, 6f);
shapes.setColor(color);
shapes.rect(x, y + 1f, width * value, 4f);
⋮----
private void drawAuthoredPreview(WeaponDefinition weapon, float x, float y, float maxW, float maxH) {
if (game.art == null || !game.art.authoredAvailable()) return;
TextureRegion region = game.art.regionOrNull("weapon/" + weapon.id);
⋮----
float aspect = region.getRegionWidth() / (float) Math.max(1, region.getRegionHeight());
⋮----
float drawH = drawW / Math.max(.01f, aspect);
⋮----
batch.setColor(Color.WHITE);
batch.draw(region, x + (maxW - drawW) * .5f, y + (maxH - drawH) * .5f, drawW, drawH);
⋮----
private void handleInput(WeaponDefinition[] all, int pageStart, int pageEnd) {
if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
game.showMenu();
⋮----
int columns = grid.columns();
if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.A)) focus = Math.max(0, focus - 1);
if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.D)) focus = Math.min(all.length - 1, focus + 1);
if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) focus = Math.max(0, focus - columns);
if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) focus = Math.min(all.length - 1, focus + columns);
if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) select(all[focus]);
if (!Gdx.input.justTouched()) return;
⋮----
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (back.contains(touch)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); return; }
if (previousPage.contains(touch) && pageStart > 0) {
focus = Math.max(0, pageStart - PAGE_SIZE);
AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
⋮----
if (nextPage.contains(touch) && pageEnd < all.length) {
⋮----
if (!r.contains(touch)) continue;
⋮----
select(all[i]);
⋮----
private void select(WeaponDefinition weapon) {
if (!WeaponProgression.unlocked(game.profile, weapon)) {
⋮----
if (game.profile.selectWeapon(weapon)) {
ProfileStore.save(game.profile);
⋮----
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
⋮----
@Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/screen/CloudSaveScreen.java
```java
/** Explicit cloud-save management. Never resolves divergent or stale progress automatically. */
public final class CloudSaveScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final BitmapFont font = new BitmapFont();
private final ShapeRenderer shapes = new ShapeRenderer();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
private final ExecutorService worker = Executors.newSingleThreadExecutor(r -> {
Thread t = new Thread(r, "deadline-zero-cloud-save");
t.setDaemon(true);
⋮----
this.cloud = new CloudSaveService(game.services.cloudSave);
status = cloud.available() ? t("cloud.checking") : t("cloud.notConfigured");
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
if (cloud.available()) refresh();
⋮----
@Override public void resize(int width, int height) {
viewport.resize(width, height);
metrics = UiLayout.compute(width, height);
layout = MetaLayout.compute(metrics);
Rectangle c = layout.content();
statusPanel = new Rectangle(c.x + 28f, c.y + c.height * .54f, c.width - 56f, c.height * .39f);
warningPanel = new Rectangle(c.x + 28f, c.y + c.height * .34f, c.width - 56f, c.height * .15f);
Rectangle actionArea = new Rectangle(c.x + 28f, c.y + 24f, c.width - 56f, Math.max(78f, c.height * .22f));
actions = MetaLayout.columns(actionArea, 3, 18f);
⋮----
@Override public void render(float delta) {
visualTime += Math.max(0f, delta);
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
boolean available = cloud.available();
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, visualTime);
UiRenderer.topRail(shapes, metrics);
UiRenderer.premiumCard(shapes, statusPanel.x, statusPanel.y, statusPanel.width, statusPanel.height,
colorForState(), false, conflict == CloudSaveService.ConflictState.DIVERGED || providerConflict, !available);
UiRenderer.premiumPanel(shapes, warningPanel.x, warningPanel.y, warningPanel.width, warningPanel.height, VisualTheme.GOLD, false);
float cloudIcon = Math.min(90f, statusPanel.height * .34f);
⋮----
UiRenderer.iconBadge(shapes, cloudX - 10f, cloudY - 10f, cloudIcon + 20f, colorForState(), cloud.available());
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.CLOUD, cloudX, cloudY, cloudIcon, colorForState(),
cloud.available() ? .92f : .34f);
⋮----
UiRenderer.premiumButton(shapes, actions[i].x, actions[i].y, actions[i].width, actions[i].height,
state == UiRenderer.ButtonState.DANGER ? VisualTheme.danger() : i == 1 ? VisualTheme.GOLD : i == 2 ? VisualTheme.CYAN_SOFT : VisualTheme.accent(), state);
⋮----
shapes.end();
⋮----
batch.begin();
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, t("cloud.back"), layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
layout.back().width - 16f, Align.left, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t("cloud.title"), metrics.safeLeft() + 138f, metrics.headerBottom() + 54f,
metrics.contentWidth() - 276f, Align.center, false);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
font.setColor(colorForState());
font.draw(batch, status, statusPanel.x + 28f, statusPanel.y + statusPanel.height * .44f,
⋮----
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, stateSummary(), statusPanel.x + 28f, statusPanel.y + statusPanel.height * .22f,
⋮----
font.setColor(VisualTheme.GOLD);
font.draw(batch, t("cloud.manualWarning"), warningPanel.x + 22f,
⋮----
String[] labels = {primaryLabel(), providerConflict ? t("cloud.useServer") : t("cloud.uploadLocal"),
providerConflict ? t("cloud.useOther") : t("cloud.downloadCloud")};
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
font.setColor(!available || busy ? VisualTheme.MUTED : i == 1 ? VisualTheme.GOLD : i == 2 ? VisualTheme.TEXT : VisualTheme.CYAN_SOFT);
font.draw(batch, labels[i], a.x + 10f, a.y + a.height * .60f, a.width - 20f, Align.center, true);
⋮----
font.setColor(confirmUpload || confirmDownload ? VisualTheme.danger() : VisualTheme.TEXT_DIM);
font.draw(batch, confirmationLine(), layout.footer().x + 20f, layout.footer().y + layout.footer().height * .56f,
layout.footer().width - 40f, Align.center, true);
batch.end();
⋮----
handleInput();
⋮----
private String stateSummary() {
if (!cloud.available()) return t("cloud.configure");
if (authRequired) return t("cloud.authHelp");
if (providerConflict) return t("cloud.conflictHelp");
if (comparison == null) return busy ? t("cloud.inProgress") : t("cloud.refreshHelp");
return t("cloud.versionBound");
⋮----
private void handleInput() {
if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { game.showSettings(); return; }
if (!busy && cloud.available()) {
if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { primaryAction(); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.SERVER); else requestUpload();
⋮----
if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
if (providerConflict) resolveProviderConflict(CloudSaveAdapter.ConflictChoice.CONFLICTING); else requestDownload();
⋮----
if (!Gdx.input.justTouched()) return;
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (layout.back().contains(touch)) { game.showSettings(); return; }
if (busy || !cloud.available()) return;
if (actions[0].contains(touch)) { primaryAction(); return; }
if (actions[1].contains(touch)) {
⋮----
if (actions[2].contains(touch)) {
⋮----
private void primaryAction() {
resetConfirmations();
if (authRequired && cloud.supportsAuthentication()) authenticateAndRefresh();
else refresh();
⋮----
private void authenticateAndRefresh() {
⋮----
runAsync(t("cloud.signingIn"), () -> {
cloud.authenticate();
CloudSaveService.Comparison next = inspectComparisonOrConflict();
post(() -> { authRequired = false; applyComparison(next); });
⋮----
private void refresh() {
⋮----
runAsync(t("cloud.checking"), () -> {
⋮----
post(() -> applyComparison(next));
⋮----
private CloudSaveService.Comparison inspectComparisonOrConflict() throws Exception {
CloudSaveAdapter.ProviderConflict pending = cloud.pendingProviderConflict();
if (pending != null) throw new CloudProviderConflictException(pending);
CloudSaveService.Comparison next = cloud.inspectAgainstLocal();
pending = cloud.pendingProviderConflict();
⋮----
private void applyComparison(CloudSaveService.Comparison next) {
⋮----
conflict = next.state();
status = switch (next.state()) {
case EQUAL -> t("cloud.identical");
case LOCAL_AHEAD -> next.remote() == null ? t("cloud.noSave") : t("cloud.localAhead");
case REMOTE_AHEAD -> t("cloud.remoteAhead");
case DIVERGED -> t("cloud.diverged");
⋮----
private void resolveProviderConflict(CloudSaveAdapter.ConflictChoice choice) {
runAsync(t("cloud.resolving"), () -> {
cloud.resolveProviderConflict(choice);
⋮----
post(() -> { resetConfirmations(); applyComparison(next); });
⋮----
private void requestUpload() {
⋮----
if (expected == null) { resetConfirmations(); status = t("cloud.refreshUpload"); return; }
boolean risky = expected.state() == CloudSaveService.ConflictState.REMOTE_AHEAD || expected.state() == CloudSaveService.ConflictState.DIVERGED;
if (risky && !confirmUpload) { confirmUpload = true; confirmDownload = false; status = t("cloud.confirmUpload"); return; }
⋮----
runAsync(t("cloud.revalidateUpload"), () -> {
cloud.uploadIfUnchanged(expected);
⋮----
post(() -> { status = t("cloud.uploadComplete"); applyComparison(next); });
⋮----
private void requestDownload() {
⋮----
if (expected == null) { resetConfirmations(); status = t("cloud.refreshDownload"); return; }
boolean risky = expected.state() == CloudSaveService.ConflictState.LOCAL_AHEAD || expected.state() == CloudSaveService.ConflictState.DIVERGED;
if (risky && !confirmDownload) { confirmDownload = true; confirmUpload = false; status = t("cloud.confirmDownload"); return; }
⋮----
runAsync(t("cloud.revalidateDownload"), () -> {
CloudSaveAdapter.RemoteBackup remote = cloud.revalidateRemote(expected);
post(() -> {
if (remote == null) { comparison = null; conflict = null; status = t("cloud.changed"); return; }
⋮----
CloudSaveService.RestoreResult result = cloud.applyRemote(remote.payload());
if (result.result() == CloudSaveService.DownloadResult.APPLIED) {
if (!game.applyCloudRestore(result)) status = t("cloud.restoreFailed");
} else status = t("cloud.newerVersion");
⋮----
comparison = null; conflict = null; status = f("cloud.restoreError", safeMessage(e));
⋮----
private void runAsync(String runningStatus, ThrowingAction action) {
⋮----
worker.submit(() -> {
try { action.run(); }
⋮----
post(() -> { comparison = null; conflict = null; providerConflict = false; authRequired = true; resetConfirmations(); status = t("cloud.signInRequired"); });
⋮----
post(() -> { comparison = null; providerConflict = true; authRequired = false; conflict = CloudSaveService.ConflictState.DIVERGED; resetConfirmations(); status = t("cloud.providerConflict"); });
⋮----
post(() -> { comparison = null; conflict = null; providerConflict = false; resetConfirmations(); status = t("cloud.changedOther"); });
⋮----
post(() -> { comparison = null; conflict = null; providerConflict = false; resetConfirmations(); status = f("cloud.error", safeMessage(e)); });
} finally { post(() -> busy = false); }
⋮----
private void post(Runnable action) {
⋮----
Gdx.app.postRunnable(() -> { if (!disposed) action.run(); });
⋮----
private String primaryLabel() {
if (authRequired && cloud.supportsAuthentication()) return t("cloud.signIn");
return providerConflict ? t("cloud.recheck") : t("cloud.refresh");
⋮----
private String confirmationLine() {
⋮----
if (comparison == null && !busy) return t("cloud.refreshHelp");
if (confirmUpload) return t("cloud.uploadArmed");
if (confirmDownload) return t("cloud.downloadArmed");
return busy ? t("cloud.inProgress") : t("cloud.versionBound");
⋮----
private com.badlogic.gdx.graphics.Color colorForState() {
if (!cloud.available()) return VisualTheme.MUTED;
⋮----
if (conflict == CloudSaveService.ConflictState.DIVERGED) return VisualTheme.danger();
⋮----
return VisualTheme.accent();
⋮----
private void resetConfirmations() { confirmUpload = false; confirmDownload = false; }
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
private static String safeMessage(Exception e) {
String message = e.getMessage();
if (message == null || message.isBlank()) return e.getClass().getSimpleName();
return message.length() > 96 ? message.substring(0, 96) : message;
⋮----
@Override public void dispose() {
⋮----
worker.shutdownNow();
batch.dispose();
font.dispose();
shapes.dispose();
⋮----
@FunctionalInterface private interface ThrowingAction { void run() throws Exception; }
```

## File: src/main/java/com/deadlinezero/game/screen/GameScreen.java
```java
public final class GameScreen extends ScreenAdapter {
private static final Color ENEMY_RUNNER = new Color(.95f, .35f, .25f, 1f);
private static final Color ENEMY_BRUTE = new Color(.58f, .10f, .15f, 1f);
private static final Color ENEMY_RANGED = new Color(.95f, .62f, .16f, 1f);
private static final Color ENEMY_ELITE = new Color(.76f, .18f, .86f, 1f);
private static final Color ENEMY_DEFAULT = new Color(.30f, .70f, .39f, 1f);
⋮----
private final OrthographicCamera cam = new OrthographicCamera(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
private final ShapeRenderer shapes = new ShapeRenderer();
private final SpriteBatch batch = new SpriteBatch();
private final BitmapFont font = new BitmapFont();
private final Player player = new Player(0, 0);
⋮----
private final Pools pools = new Pools();
private final WaveDirector director = new WaveDirector();
private final SpatialHash spatial = new SpatialHash(2.2f);
private final VirtualStick stick = new VirtualStick();
private final Vector2 aim = new Vector2();
private final Vector2 shotVelocity = new Vector2();
⋮----
private final WorldFxRenderer worldFx = new WorldFxRenderer();
private final PerformanceTelemetry performanceTelemetry = new PerformanceTelemetry();
private final AdaptiveFrameRateGovernor frameRateGovernor = new AdaptiveFrameRateGovernor();
⋮----
this.combatHud = new CombatHudRenderer(game.i18n);
⋮----
this.abilitySystem = new AbilitySystem(player, enemies, pools, spatial, this::onEnemyKilled);
this.spritePass = new CombatSpritePass(game.art);
this.polish = new CombatPolishController(game.art, game.accessibility, game.services.thermal);
frameRateGovernor.reset(GraphicsSettings.frameRate().target);
float gearPower = game.profile == null ? 1f : game.profile.aggregatePowerMultiplier();
⋮----
cam.position.set(0, 0, 0);
cam.update();
font.getData().setScale(.75f);
⋮----
@Override public void render(float delta) {
performanceTelemetry.record(delta, frameRateGovernor.effectiveTarget());
performanceEvaluationTimer += Math.min(delta, .25f);
if (performanceEvaluationTimer >= 2f && performanceTelemetry.sampleCount() >= 60) {
⋮----
int before = frameRateGovernor.effectiveTarget();
int userTarget = GraphicsSettings.frameRate().target;
int allowedTarget = ThermalBudgetPolicy.allowedFps(userTarget, game.services.thermal.level());
int after = frameRateGovernor.update(
⋮----
performanceTelemetry.snapshot(before)
⋮----
if (after != before) Gdx.graphics.setForegroundFPS(after);
⋮----
delta = Math.min(delta, .05f);
⋮----
combatHud.update(delta);
polish.updateVisual(delta);
float simulationScale = polish.simulationScale(delta);
spritePass.update(delta * simulationScale);
⋮----
if (!choosingUpgrade && !choosingLegendary && !gameOver) update(GameConfig.FIXED_STEP);
⋮----
draw();
handleOverlayInput();
⋮----
public PerformanceTelemetry.Snapshot performanceSnapshot() {
return performanceTelemetry.snapshot(frameRateGovernor.effectiveTarget());
⋮----
public int effectiveFrameRateTarget() {
return frameRateGovernor.effectiveTarget();
⋮----
public com.deadlinezero.game.services.ThermalService.Level thermalLevel() {
return game.services.thermal.level();
⋮----
public float effectiveFxQuality() {
return polish.fxQuality();
⋮----
public int activeEnemyCount() {
⋮----
public int activeProjectileCount() {
⋮----
public int activeSpatialBucketCount() {
return spatial.activeBucketCount();
⋮----
public int retainedSpatialBucketCount() {
return spatial.retainedBucketCount();
⋮----
private void update(float dt) {
director.update(dt);
player.updateRuntime(dt);
⋮----
Vector2 move = stick.update(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
player.velocity.set(move).scl(player.moveSpeed);
if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.canDash() && move.len2() > .08f) {
player.position.mulAdd(move, 4.8f);
player.triggerDash();
CombatVisualEvents.markDash();
if (game.accessibility != null && game.accessibility.haptics) game.services.haptics.dash();
addCameraShake(.12f);
impact(player.position.x, player.position.y, .9f, .16f, VisualTheme.CYAN);
⋮----
player.position.mulAdd(player.velocity, dt);
player.position.x = MathUtils.clamp(player.position.x, -31, 31);
player.position.y = MathUtils.clamp(player.position.y, -17, 17);
⋮----
if (director.shouldSpawn() && enemies.size < GameConfig.MAX_ENEMIES) {
spawnEnemy();
director.onSpawn();
⋮----
// Reuse the index produced at the end of the previous simulation tick.
Enemy target = spatial.nearest(player.position.x, player.position.y);
⋮----
fire(target);
⋮----
updateEnemies(dt);
// Enemy movement invalidates the pre-update index; rebuild for collision/ability queries.
spatial.rebuild(enemies);
abilitySystem.update(dt);
updatePlayerProjectiles(dt);
updateHostileProjectiles(dt);
⋮----
for (DamageNumber n : pools.damageNumbers) n.update(dt);
for (ArcFx arc : pools.arcs) arc.update(dt);
polish.updateSimulation(dt, pools);
for (int i = enemies.size - 1; i >= 0; i--) if (!enemies.get(i).alive) enemies.removeIndex(i);
⋮----
if (game.accessibility != null && game.accessibility.allowsScreenShake() && cameraShake > .0001f) {
cam.position.x += MathUtils.random(-1f, 1f) * cameraShake;
cam.position.y += MathUtils.random(-1f, 1f) * cameraShake;
cameraShake = Math.max(0f, cameraShake - dt * 2.7f);
⋮----
// Mobile survivor-shooter framing: keep the operative readable and let the arena move around
// them. A small velocity look-ahead preserves anticipation without making the camera floaty.
⋮----
bossRevealTimer = Math.max(0f, bossRevealTimer - dt);
⋮----
float reveal = BossRevealCameraProfile.envelope(bossRevealTimer);
float focus = BossRevealCameraProfile.focusWeight(reveal, reducedMotion);
// Bias the cinematic framing toward the boss so its full authored silhouette sits
// below the top HUD/boss bar instead of being clipped behind it.
float revealX = MathUtils.lerp(player.position.x, bossRevealTarget.position.x, .54f);
// Raising the camera in world space moves the rendered boss downward on screen,
// preserving a HUD-safe lane below the persistent boss bar.
float revealY = MathUtils.lerp(player.position.y, bossRevealTarget.position.y, .50f)
⋮----
cameraTargetX = MathUtils.lerp(cameraTargetX, revealX, focus);
cameraTargetY = MathUtils.lerp(cameraTargetY, revealY, focus);
cameraZoomTarget = BossRevealCameraProfile.zoom(COMBAT_CAMERA_ZOOM, reveal, reducedMotion);
⋮----
cam.position.x = MathUtils.lerp(cam.position.x, cameraTargetX, bossRevealTimer > 0f ? .15f : .11f);
cam.position.y = MathUtils.lerp(cam.position.y, cameraTargetY, bossRevealTimer > 0f ? .15f : .11f);
cam.zoom = MathUtils.lerp(cam.zoom, cameraZoomTarget, .12f);
polish.applyCameraRecoil(cam);
⋮----
private void updateEnemies(float dt) {
⋮----
float distance = (float)Math.sqrt(Math.max(len2, .0001f));
e.updateAi(dt, distance);
⋮----
int phase = e.bossPhases.phase();
if (e.bossCombat.consumeCharge(phase)) {
addCameraShake(.16f);
impact(e.position.x, e.position.y, 2.8f, .24f, VisualTheme.RED);
⋮----
if (e.bossCombat.consumeSummon(phase)) spawnBossMinions(e, phase);
if (e.bossCombat.consumeEnragePulse(phase)) bossEnragePulse(e);
⋮----
float speed = e.effectiveSpeed();
⋮----
if (e.type == Enemy.Type.RANGED && distance < e.attack.archetype().preferredRange) direction = -0.65f;
if (e.attack.state() == EnemyState.TELEGRAPHING || e.attack.state() == EnemyState.RECOVERING) speed *= 0.22f;
e.velocity.set(dx * inv * speed * direction, dy * inv * speed * direction).add(e.impulse);
⋮----
e.position.mulAdd(e.velocity, dt);
⋮----
if (e.attack.consumeAttack()) resolveEnemyAttack(e);
⋮----
e.updateStatus(dt);
if (aliveBeforeStatus && !e.alive) onEnemyKilled(e);
⋮----
if (e.consumeChargeImpact()) {
EnemyPatternCatalog.ChargePattern charge = EnemyPatternCatalog.charge(e.type, e.variant);
damagePlayer(e.contactDamage * charge.impactDamageMultiplier(), .58f);
⋮----
player.position.x += dx * inv * charge.knockbackStrength();
player.position.y += dy * inv * charge.knockbackStrength();
⋮----
impact(e.position.x, e.position.y, charge.impactRadius(), .26f, VisualTheme.GOLD);
addCameraShake(e.type == Enemy.Type.ELITE ? .52f : .38f);
contactTimer = Math.max(.30f, .42f * charge.recoveryMultiplier());
⋮----
damagePlayer(e.contactDamage, .35f);
⋮----
static Enemy.Type bossSummonType(boolean nullArchon, boolean revenant, int phase, int index) {
return bossSummonType(nullArchon ? BossIdentity.NULL_ARCHON
⋮----
static Enemy.Type bossSummonType(BossIdentity identity, int phase, int index) {
⋮----
int safeIndex = Math.max(0, index);
⋮----
private void spawnBossMinions(Enemy boss, int phase) {
int count = boss.bossCombat == null ? (phase >= 3 ? 6 : 3) : boss.bossCombat.summonCount(phase);
BossIdentity identity = boss.bossCombat == null ? BossIdentity.ALPHA : boss.bossCombat.identity();
⋮----
float angle = i * (MathUtils.PI2 / count) + MathUtils.random(-.18f, .18f);
float dist = 2.6f + MathUtils.random(0f, 1.2f);
float x = boss.position.x + MathUtils.cos(angle) * dist;
float y = boss.position.y + MathUtils.sin(angle) * dist;
float scale = 1f + director.elapsed() / 210f;
Enemy.Type type = bossSummonType(identity, phase, i);
⋮----
case RANGED -> new Enemy(type, x, y, 68f * scale, 2.2f, .42f, 12f, 10);
case PHANTOM -> new Enemy(type, x, y, 54f * scale, 3.25f, .40f, 11f, 10);
case REGENERATOR -> new Enemy(type, x, y, 92f * scale, 2.05f, .48f, 11f, 12);
case SHIELDED -> new Enemy(type, x, y, 125f * scale, 1.72f, .64f, 14f, 16);
case BRUTE -> new Enemy(type, x, y, 118f * scale, 1.82f, .68f, 16f, 16);
default -> new Enemy(type, x, y, 30f * scale, 4.35f, .34f, 8f, 5);
⋮----
enemies.add(minion);
spatial.add(minion);
abilitySystem.onEnemySpawned(minion);
impact(x, y, nullArchon ? .82f : frostColossus ? .78f : .65f,
⋮----
addCameraShake(nullArchon ? .22f : frostColossus ? .24f : (revenant ? .18f : .12f));
⋮----
private void bossEnragePulse(Enemy boss) {
int shots = boss.bossCombat == null ? 20 : boss.bossCombat.enrageShots();
float speed = boss.bossCombat == null ? 8.2f : boss.bossCombat.enrageProjectileSpeed();
int explosiveEvery = boss.bossCombat == null ? 4 : boss.bossCombat.enrageExplosiveEvery();
float explosionRadius = boss.bossCombat == null ? 2.0f : boss.bossCombat.enrageExplosionRadius();
⋮----
spawnHostileShot(boss, boss.position.x, boss.position.y, i * (360f / shots), speed,
⋮----
impact(boss.position.x, boss.position.y,
⋮----
addCameraShake(frostColossus ? .50f : revenant ? .46f : .38f);
⋮----
private void updatePlayerProjectiles(float dt) {
⋮----
p.position.mulAdd(p.velocity, dt);
⋮----
spatial.query(p.position.x, p.position.y, 1.35f, collisionCandidates);
⋮----
if (p.position.dst2(e.position) > rr * rr) continue;
⋮----
e.damage(p.damage);
⋮----
e.applyElement(p.element, p.damage);
float reactionBonus = player.protocols.reactionBonus(p.damage, e.lastReaction);
⋮----
e.damage(reactionBonus);
CombatVisualEvents.markProtocol(CombatVisualEvents.ProtocolCue.REACTION);
⋮----
damageNumber(e.position.x, e.position.y + e.radius, p.damage + reactionBonus, p.critical,
⋮----
float vlen = p.velocity.len();
if (vlen > .001f) e.addImpulse(p.velocity.x / vlen * p.knockback, p.velocity.y / vlen * p.knockback);
PlayerProjectilePresentation.Profile projectileVisual = PlayerProjectilePresentation.profile(p);
Color impactColor = p.weaponSignature ? projectileVisual.accent() : projectileVisual.color();
float impactScale = projectileVisual.impactScale();
impact(p.position.x, p.position.y,
⋮----
if (p.weaponSignature) addCameraShake(.095f);
else if (p.critical) addCameraShake(.075f);
polish.onProjectileHit(p.critical);
if (p.element == DamageElement.SHOCK && e.alive) chainShock(e, p.damage * .42f, 3);
if (wasAlive && !e.alive) onEnemyKilled(e);
⋮----
private void updateHostileProjectiles(float dt) {
⋮----
if (p.position.dst2(player.position) <= rr * rr) {
⋮----
impact(p.position.x, p.position.y, p.explosionRadius, .32f, VisualTheme.GOLD);
if (p.position.dst2(player.position) <= p.explosionRadius * p.explosionRadius) damagePlayer(p.damage, .48f);
} else damagePlayer(p.damage, .22f);
⋮----
private void resolveEnemyAttack(Enemy e) {
aim.set(player.position).sub(e.position);
if (aim.len2() < .0001f) aim.set(1f, 0f); else aim.nor();
⋮----
EnemyPatternCatalog.RangedPattern pattern = EnemyPatternCatalog.ranged(e.variant);
float base = aim.angleDeg();
for (int i = 0; i < pattern.shots(); i++) {
float spread = (i - (pattern.shots() - 1) / 2f) * pattern.spreadDegrees();
spawnHostileShot(e, e.position.x, e.position.y, base + spread,
8.5f * pattern.speedMultiplier(),
e.contactDamage * pattern.damageMultiplier(),
pattern.explosive() ? .24f : .18f,
pattern.explosive(), pattern.explosionRadius());
⋮----
impact(e.position.x, e.position.y, pattern.explosive() ? .85f : .46f, .12f,
pattern.explosive() ? VisualTheme.GOLD : VisualTheme.RED);
⋮----
int phase = e.bossPhases == null ? 1 : e.bossPhases.phase();
BossIdentity identity = e.bossCombat == null ? BossIdentity.ALPHA : e.bossCombat.identity();
⋮----
BossAttackPatternCatalog.Pattern pattern = BossAttackPatternCatalog.forPhase(identity, phase);
⋮----
float angle = pattern.radial()
? i * pattern.spreadDegrees()
: base + (i - (pattern.shots() - 1) / 2f) * pattern.spreadDegrees();
boolean explosive = pattern.explosiveEvery() > 0 && i % pattern.explosiveEvery() == 0;
spawnHostileShot(e, e.position.x, e.position.y, angle,
7.2f * pattern.speedMultiplier(),
⋮----
explosive, explosive ? pattern.explosionRadius() : 0f);
⋮----
impact(e.position.x, e.position.y,
⋮----
addCameraShake(.18f + phase * .05f + (revenant ? .04f : 0f) + (frostColossus ? .06f : 0f));
⋮----
private void spawnHostileShot(Enemy source, float x, float y, float angle, float speed, float damage,
⋮----
EnemyProjectile p = pools.hostileProjectile();
⋮----
shotVelocity.set(speed, 0f).setAngleDeg(angle);
p.spawn(x, y, shotVelocity.x, shotVelocity.y, damage, radius, 4.5f, explosive, explosionRadius,
HostileProjectilePresentation.styleFor(source));
⋮----
private void chainShock(Enemy source, float damage, int maxChains) {
⋮----
Enemy nearest = spatial.nearestWithin(
⋮----
nearest.damage(damage);
nearest.applyElement(DamageElement.SHOCK, damage);
ArcFx arc = pools.arc();
if (arc != null) arc.spawn(fromX, fromY, nearest.position.x, nearest.position.y, .11f);
damageNumber(nearest.position.x, nearest.position.y + nearest.radius, damage, false, VisualTheme.CYAN);
impact(nearest.position.x, nearest.position.y, .62f, .16f, VisualTheme.CYAN);
if (wasAlive && !nearest.alive) onEnemyKilled(nearest);
⋮----
private void damagePlayer(float damage, float shake) {
⋮----
player.damage(damage);
⋮----
if (game.accessibility != null && game.accessibility.haptics) game.services.haptics.damage();
combatHud.triggerDamageFlash();
addCameraShake(shake);
impact(player.position.x, player.position.y, 1.1f, .18f, VisualTheme.RED);
damageNumber(player.position.x, player.position.y + player.radius, damage, false, VisualTheme.RED);
⋮----
private void damageNumber(float x, float y, float value, boolean critical, Color color) {
DamageNumber n = pools.damageNumber();
if (n != null) n.spawn(x, y, value, critical, color);
⋮----
private void onEnemyKilled(Enemy e) {
⋮----
if (game.accessibility != null && game.accessibility.haptics) game.services.haptics.bossKill();
⋮----
polish.onEnemyKilled(e, pools);
if (player.protocols.onKill()) {
CombatVisualEvents.markProtocol(CombatVisualEvents.ProtocolCue.KILLCHAIN_ARMED);
⋮----
director.onKill();
if (player.addXp(e.xpValue)) prepareUpgrade();
⋮----
impact(e.position.x, e.position.y, e.radius * killScale, killDuration, killColor);
⋮----
impact(e.position.x, e.position.y, e.radius * 2.2f, .68f, Color.WHITE);
⋮----
addCameraShake(killShake);
⋮----
private void spawnEnemy() {
Enemy.Type t = director.chooseType();
float angle = MathUtils.random(MathUtils.PI2);
float dist = t == Enemy.Type.BOSS ? 15f : MathUtils.random(13f, 19f);
float x = player.position.x + MathUtils.cos(angle) * dist;
float y = player.position.y + MathUtils.sin(angle) * dist;
float scale = 1f + director.elapsed() / 180f;
⋮----
case RUNNER -> new Enemy(t, x, y, 28 * scale, 4.2f, .34f, 8, 6);
case BRUTE -> new Enemy(t, x, y, 145 * scale, 1.6f, .72f, 18, 15);
case RANGED -> new Enemy(t, x, y, 72 * scale, 2.15f, .42f, 13, 12);
case ELITE -> new Enemy(t, x, y, 420 * scale, 2.1f, 1.05f, 28, 42);
⋮----
BossVariantStats.Stats stats = BossVariantStats.forStage(RunStageContext.stage(),
⋮----
yield new Enemy(t, x, y, stats.hp(), stats.speed(), 1.65f, stats.damage(), 280);
⋮----
default -> new Enemy(t, x, y, 52 * scale, 2.55f, .46f, 10, 8);
⋮----
enemies.add(e);
spatial.add(e);
abilitySystem.onEnemySpawned(e);
⋮----
director.onBossSpawned();
⋮----
private void fire(Enemy target) {
aim.set(target.position).sub(player.position).nor();
⋮----
com.deadlinezero.game.progression.CombatProtocolState.VolleyModifier protocol = player.protocols.onVolley();
if (protocol.damageMultiplier() > 1f) {
CombatVisualEvents.ProtocolCue cue = protocol.forcedCrit() && protocol.bonusPenetration() > 0
⋮----
: protocol.forcedCrit() ? CombatVisualEvents.ProtocolCue.RHYTHM
⋮----
CombatVisualEvents.markProtocol(cue);
AudioDirector.playGlobal(AudioDirector.Cue.PROTOCOL_PROC);
⋮----
shotVelocity.set(player.weapon.projectileSpeed, 0f).setAngleDeg(base + spread);
boolean crit = protocol.forcedCrit() || MathUtils.random() < player.weapon.critChance;
Projectile p = pools.projectile();
if (p != null) p.spawn(player.position.x, player.position.y, shotVelocity.x, shotVelocity.y,
player.weapon.damage * protocol.damageMultiplier() * (crit ? player.weapon.critMultiplier : 1f), crit,
Math.min(Upgrade.MAX_PENETRATION, player.weapon.penetration + protocol.bonusPenetration()),
⋮----
polish.onShot(base);
addCameraShake(count > 1 ? .052f : .043f);
⋮----
private void addCameraShake(float amount) {
if (amount <= 0f || game.accessibility == null || !game.accessibility.allowsScreenShake()) return;
cameraShake = Math.max(cameraShake, amount * game.accessibility.screenShakeStrength);
⋮----
private void impact(float x, float y, float s, float d, Color c) {
ImpactFx f = pools.impact();
if (f != null) f.spawn(x, y, s, d, c);
⋮----
private void prepareUpgrade() {
if (LegendarySelector.shouldOffer(player)) {
legendaryChoiceCount = LegendarySelector.fillChoices(player, legendaryChoices);
⋮----
prepareStandardUpgrade();
⋮----
private void prepareStandardUpgrade() {
⋮----
UpgradeSelector.fillChoices(player, choices);
⋮----
private void finishRun() {
⋮----
game.finishRun(director.kills(), director.elapsed(), bossKilledThisRun, 0);
⋮----
private void draw() {
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
// Combat uses translucent shadows, telegraphs, impacts and modal overlays extensively.
// ShapeRenderer does not enable alpha blending itself, so make the world pipeline explicit.
Gdx.gl.glEnable(GL20.GL_BLEND);
Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
boolean authored = spritePass.authoredAvailable();
⋮----
batch.setProjectionMatrix(cam.combined);
if (authored) spritePass.renderEnvironmentFloor(batch);
⋮----
shapes.setProjectionMatrix(cam.combined);
shapes.begin(ShapeRenderer.ShapeType.Filled);
⋮----
shapes.setColor(.018f, .030f, .040f, 1f);
shapes.rect(-40, -24, 80, 48);
shapes.setColor(.06f, .14f, .17f, .38f);
for (int x = -40; x < 40; x += 2) shapes.rect(x, -24, .02f, 48);
for (int y = -24; y < 24; y += 2) shapes.rect(-40, y, 80, .02f);
⋮----
polish.drawWorldUnderlay(shapes, player, enemies, pools, visualTime);
worldFx.drawGroundShadows(shapes, player, enemies);
shapes.end();
⋮----
if (authored) spritePass.renderEnvironmentDressing(batch);
⋮----
worldFx.drawProjectileTrails(shapes, pools.projectiles, pools.hostileProjectiles, pools.homingMissiles);
worldFx.drawElectricArcs(shapes, pools.arcs, visualTime);
⋮----
float a = MathUtils.clamp(f.life / f.maxLife, 0f, 1f);
shapes.setColor(f.color.r, f.color.g, f.color.b, a * .12f);
shapes.circle(f.position.x, f.position.y, f.size * (1.45f - a * .32f), 24);
shapes.setColor(f.color.r, f.color.g, f.color.b, a * .50f);
shapes.circle(f.position.x, f.position.y, f.size * (1f - a * .42f), 20);
shapes.setColor(1f, 1f, 1f, a * .42f);
shapes.circle(f.position.x, f.position.y, Math.max(.06f, f.size * .18f * a), 12);
⋮----
PlayerProjectilePresentation.Profile visual = PlayerProjectilePresentation.profile(p);
Color core = visual.color();
float radius = p.radius * visual.coreScale();
if (visual.signature()) {
Color accent = visual.accent();
shapes.setColor(accent.r, accent.g, accent.b, .20f);
shapes.circle(p.position.x, p.position.y, radius * 1.75f, 16);
⋮----
shapes.setColor(core);
shapes.circle(p.position.x, p.position.y, radius, visual.signature() ? 16 : 12);
if (visual.style() == PlayerProjectilePresentation.Style.RAIL
|| (visual.signature() && visual.style() == PlayerProjectilePresentation.Style.TEMPEST)) {
shapes.setColor(1f, 1f, 1f, .84f);
shapes.circle(p.position.x, p.position.y, Math.max(.035f, radius * .38f), 9);
⋮----
float visualRadius = p.radius * HostileProjectilePresentation.coreRadiusMultiplier(p.style);
⋮----
shapes.setColor(core.r, core.g, core.b, .16f);
shapes.circle(p.position.x, p.position.y, visualRadius * 1.65f, 16);
⋮----
shapes.circle(p.position.x, p.position.y, visualRadius, 14);
⋮----
shapes.setColor(1f, 1f, 1f, .82f);
shapes.circle(p.position.x, p.position.y, visualRadius * .42f, 9);
⋮----
shapes.setColor(.08f, .04f, .14f, .90f);
shapes.circle(p.position.x, p.position.y, visualRadius * .38f, 9);
⋮----
shapes.setColor(m.element == DamageElement.FROST ? VisualTheme.CYAN : VisualTheme.GOLD);
shapes.circle(m.position.x, m.position.y, m.radius * 1.35f, 10);
⋮----
drawAbilityObjects();
for (Enemy e : enemies) drawEnemy(e, !authored);
⋮----
shapes.setColor(player.invulnerable() ? Color.WHITE : VisualTheme.CYAN);
float playerPulse = 1f + MathUtils.sin(visualTime * 7f) * .035f;
shapes.circle(player.position.x, player.position.y, player.radius * playerPulse, 24);
shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .18f);
shapes.circle(player.position.x, player.position.y, player.radius * 1.45f * playerPulse, 24);
⋮----
polish.drawAuthoredDeaths(batch, pools);
spritePass.renderCombat(batch, player, enemies, pools);
drawCombatText();
drawHud();
⋮----
private void drawEnemy(Enemy e, boolean drawBody) {
⋮----
float speedRatio = MathUtils.clamp(e.velocity.len() / Math.max(.01f, e.speed), 0f, 1.5f);
float gait = MathUtils.sin(visualTime * (5f + speedRatio * 3f) + e.position.x * .7f) * .06f * speedRatio;
if (e.attack.state() == EnemyState.TELEGRAPHING) {
float pulse = .82f + MathUtils.sin(visualTime * 14f) * .18f;
⋮----
shapes.setColor(VisualTheme.RED.r, VisualTheme.RED.g, VisualTheme.RED.b, .44f);
drawThreatRing(e.position.x, e.position.y, radius, e.type == Enemy.Type.BOSS ? 10 : 6,
⋮----
if (e.type == Enemy.Type.BOSS && e.bossCombat != null && e.bossCombat.charging()) {
float radius = e.radius * (1.42f + MathUtils.sin(visualTime * 20f) * .08f);
shapes.setColor(1f, .15f, .05f, .54f);
drawThreatRing(e.position.x, e.position.y, radius, 12, .090f);
shapes.setColor(1f, .68f, .22f, .48f);
⋮----
float ox = MathUtils.cosDeg(angle);
float oy = MathUtils.sinDeg(angle);
⋮----
shapes.triangle(tipX, tipY,
⋮----
shapes.setColor(c);
⋮----
shapes.ellipse(e.position.x - sx, e.position.y - sy, sx * 2f, sy * 2f);
⋮----
if (EnemyHealthBarPresentation.visible(e)) {
float width = e.radius * 2f * EnemyHealthBarPresentation.widthMultiplier(e);
⋮----
shapes.setColor(.08f, .09f, .10f, .76f);
shapes.rect(x, y, width, .065f);
shapes.setColor(e.type == Enemy.Type.ELITE ? VisualTheme.GOLD : VisualTheme.RED);
shapes.rect(x, y,
width * MathUtils.clamp(e.hp / Math.max(1f, e.maxHp), 0f, 1f), .065f);
⋮----
private void drawThreatRing(float cx, float cy, float radius, int pips, float pipRadius) {
int count = Math.max(4, pips);
⋮----
shapes.circle(cx + MathUtils.cosDeg(angle) * radius,
cy + MathUtils.sinDeg(angle) * radius, pipRadius, 8);
⋮----
private void drawAbilityObjects() {
float angle = abilitySystem.runtime().orbitalAngle;
if (player.abilities.unlocked(AbilityType.DRONE)) {
float dx = player.position.x + MathUtils.cosDeg(angle + 180f) * 1.8f;
float dy = player.position.y + MathUtils.sinDeg(angle + 180f) * 1.8f;
shapes.setColor(VisualTheme.GREEN.r, VisualTheme.GREEN.g, VisualTheme.GREEN.b, .20f);
shapes.circle(dx, dy, .34f, 14);
shapes.setColor(VisualTheme.GREEN);
shapes.circle(dx, dy, .18f, 12);
⋮----
if (player.abilities.unlocked(AbilityType.ORBITAL_BLADE)) {
float orbit = 2f + player.abilities.level(AbilityType.ORBITAL_BLADE) * .12f;
float bx = player.position.x + MathUtils.cosDeg(angle) * orbit;
float by = player.position.y + MathUtils.sinDeg(angle) * orbit;
shapes.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, .20f);
shapes.circle(bx, by, .46f, 16);
shapes.setColor(VisualTheme.GOLD);
shapes.rect(bx - .12f, by - .34f, .24f, .68f);
⋮----
private void drawCombatText() {
⋮----
batch.begin();
font.getData().setScale(.034f);
⋮----
float alpha = MathUtils.clamp(n.life / n.maxLife, 0f, 1f);
font.setColor(n.color.r, n.color.g, n.color.b, alpha);
font.getData().setScale(n.critical ? .050f : .034f);
font.draw(batch, n.text, n.x - .45f, n.y, .9f, Align.center, false);
⋮----
batch.end();
⋮----
private void drawHud() {
float physicalW = Gdx.graphics.getWidth();
float physicalH = Gdx.graphics.getHeight();
combatHud.render(shapes, batch, font, player, director, enemies, physicalW, physicalH);
⋮----
CombatOverlayViewport.compute((int) physicalW, (int) physicalH);
float w = overlay.width();
float h = overlay.height();
⋮----
if (choosingUpgrade || choosingLegendary) drawChoiceBackdrop(w, h, choosingLegendary);
if (gameOver) drawGameOverBackdrop(w, h);
batch.getProjectionMatrix().setToOrtho2D(0, 0, w, h);
⋮----
if (choosingLegendary) drawLegendaryText(w, h);
else if (choosingUpgrade) drawUpgradeText(w, h);
if (gameOver) drawGameOverText(w, h);
⋮----
private void drawChoiceBackdrop(float w, float h, boolean legendary) {
shapes.getProjectionMatrix().setToOrtho2D(0, 0, w, h);
⋮----
shapes.setColor(.004f, .008f, .013f, .82f);
shapes.rect(0f, 0f, w, h);
⋮----
Color panelAccent = legendary ? VisualTheme.GOLD : VisualTheme.accent();
UiRenderer.premiumPanel(shapes, panelX, panelY, panelW, panelH, panelAccent, true);
⋮----
int count = legendary ? Math.max(1, legendaryChoiceCount) : 3;
float cardWidth = Math.min(w * .27f, panelW / Math.max(3f, count) - w * .018f);
⋮----
Color accent = legendary ? VisualTheme.GOLD : VisualTheme.upgradeRarity(choices[i].rarity);
⋮----
UiRenderer.premiumCard(shapes, left, cardY, cardWidth, cardHeight,
⋮----
float badge = Math.min(cardWidth, cardHeight) * .13f;
shapes.setColor(accent.r, accent.g, accent.b, .24f);
shapes.circle(centerX, cardY + cardHeight * .69f, badge, 24);
shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .92f);
shapes.circle(centerX, cardY + cardHeight * .69f, badge * .48f, 20);
⋮----
float iconSize = Math.min(cardWidth * .22f, cardHeight * .30f);
UpgradeIconRenderer.draw(shapes, choices[i], centerX,
⋮----
private void drawGameOverBackdrop(float w, float h) {
⋮----
shapes.setColor(.004f, .006f, .010f, .88f);
⋮----
float panelW = Math.min(w * .58f, 760f);
float panelH = Math.min(h * .42f, 410f);
⋮----
Color accent = revived ? VisualTheme.GOLD : VisualTheme.danger();
⋮----
shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .995f);
shapes.rect(panelX, panelY, panelW, panelH);
shapes.setColor(VisualTheme.BORDER.r, VisualTheme.BORDER.g, VisualTheme.BORDER.b, .95f);
shapes.rect(panelX, panelY, panelW, 2f);
shapes.rect(panelX, panelY + panelH - 2f, panelW, 2f);
shapes.rect(panelX, panelY, 2f, panelH);
shapes.rect(panelX + panelW - 2f, panelY, 2f, panelH);
⋮----
float pulse = .68f + .22f * (MathUtils.sin(visualTime * 3.2f) * .5f + .5f);
shapes.setColor(accent.r, accent.g, accent.b, revived ? .72f : pulse);
shapes.rect(panelX, panelY + panelH - 6f, panelW, 6f);
shapes.rect(panelX, panelY, 5f, panelH);
⋮----
float ctaH = Math.max(52f, panelH * .18f);
shapes.setColor(accent.r, accent.g, accent.b, revived ? .14f : .20f);
shapes.rect(ctaX, ctaY, ctaW, ctaH);
shapes.setColor(accent.r, accent.g, accent.b, .88f);
shapes.rect(ctaX, ctaY, ctaW, 2f);
shapes.rect(ctaX, ctaY + ctaH - 2f, ctaW, 2f);
⋮----
private void drawLegendaryText(float w, float h) {
font.getData().setScale(1.45f);
font.setColor(VisualTheme.GOLD);
font.draw(batch, t("combat.legendaryTitle"), 0, h * .79f, w, Align.center, false);
font.getData().setScale(.68f);
font.setColor(VisualTheme.MUTED);
font.draw(batch, t("combat.legendarySubtitle"), 0, h * .70f, w, Align.center, false);
⋮----
font.getData().setScale(.86f);
⋮----
font.draw(batch, f("combat.legendaryCard", i + 1, t(choice.titleKey())), x - 145f, h * .53f, 290f, Align.center, false);
font.getData().setScale(.66f);
font.setColor(VisualTheme.TEXT);
font.draw(batch, t(choice.descriptionKey()), x - 145f, h * .44f, 290f, Align.center, true);
⋮----
font.draw(batch, t("combat.legendaryFooter"), x - 145f, h * .35f, 290f, Align.center, false);
⋮----
private void drawUpgradeText(float w, float h) {
font.getData().setScale(1.50f);
⋮----
font.draw(batch, t("combat.upgradeTitle"), 0, h * .705f, w, Align.center, false);
⋮----
float cardWidth = Math.min(w * .27f, panelW / 3f - w * .018f);
⋮----
font.getData().setScale(1.08f);
font.setColor(VisualTheme.upgradeRarity(choices[i].rarity));
font.draw(batch, f("combat.upgradeCard", i + 1, t(choices[i].titleKey())),
⋮----
font.getData().setScale(.78f);
boolean buildPath = i == 0 && UpgradeSelector.isBuildFocusedChoice(player, choices[i]);
font.setColor(buildPath ? VisualTheme.CYAN : VisualTheme.TEXT_DIM);
font.draw(batch, buildPath
? choices[i].rarity.name() + "  |  " + t("combat.upgradeBuildPath")
: choices[i].rarity.name(),
⋮----
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t(choices[i].descriptionKey()),
⋮----
String guidanceKey = AbilityUpgradeGuidance.key(player, choices[i]);
if (guidanceKey == null) guidanceKey = ProtocolUpgradeGuidance.key(player, choices[i]);
⋮----
font.getData().setScale(.70f);
⋮----
font.draw(batch, t(guidanceKey),
⋮----
font.draw(batch, t("combat.upgradeFooter"), 0, h * .305f, w, Align.center, false);
⋮----
private void drawGameOverText(float w, float h) {
⋮----
font.getData().setScale(1.62f);
font.setColor(accent);
font.draw(batch, t("combat.gameOver"), 0, h * .625f, w, Align.center, false);
⋮----
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, f("hud.stage", RunStageContext.stage()), 0, h * .535f, w, Align.center, false);
⋮----
font.getData().setScale(.92f);
⋮----
font.draw(batch, revived ? t("combat.results") : t("combat.revive"), 0, h * .425f, w, Align.center, false);
⋮----
font.draw(batch, f("hud.kills", director.kills()), 0, h * .365f, w, Align.center, false);
⋮----
private void handleOverlayInput() {
⋮----
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) idx = 0;
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) idx = 1;
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) idx = 2;
if (Gdx.input.justTouched() && legendaryChoiceCount > 0) {
CombatOverlayViewport.Viewport overlay = CombatOverlayViewport.compute(
Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
float logicalX = overlay.toLogicalX(Gdx.input.getX());
idx = Math.min(legendaryChoiceCount - 1,
(int)(logicalX / overlay.width() * legendaryChoiceCount));
⋮----
if (legendaryChoices[idx].apply(player)) {
addCameraShake(.64f);
impact(player.position.x, player.position.y, 2.9f, .42f, VisualTheme.GOLD);
⋮----
if (Gdx.input.justTouched()) {
⋮----
idx = Math.min(2, (int)(logicalX / overlay.width() * 3f));
⋮----
applyUpgradeWithSynergyFeedback(choices[idx]);
⋮----
if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.R)) {
finishRun();
⋮----
if (!revived) game.services.ads.showRewarded(AdsService.Reward.REVIVE, () -> {
⋮----
game.services.ads.preload();
⋮----
else finishRun();
⋮----
if (!gameOver && !choosingUpgrade && !choosingLegendary && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) finishRun();
⋮----
private void applyUpgradeWithSynergyFeedback(Upgrade upgrade) {
⋮----
AbilitySynergyUnlockDetector.snapshot(player.abilities);
upgrade.apply(player);
⋮----
AbilitySynergyUnlockDetector.newlyActivated(before, player.abilities);
String key = AbilitySynergyUnlockDetector.hudKey(synergy);
⋮----
CombatVisualEvents.markSynergy(key);
AudioDirector.playGlobal(AudioDirector.Cue.LEVEL_UP);
addCameraShake(.22f);
impact(player.position.x, player.position.y, 1.8f, .26f, VisualTheme.GOLD);
⋮----
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
⋮----
@Override public void resize(int width, int height) {
⋮----
@Override public void dispose() {
spritePass.dispose();
shapes.dispose();
batch.dispose();
font.dispose();
```

## File: src/main/java/com/deadlinezero/game/screen/GearScreen.java
```java
/** Responsive gear inventory with explicit equip/upgrade/fuse actions. */
public final class GearScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final ShapeRenderer shapes = new ShapeRenderer();
private final BitmapFont font = new BitmapFont();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
⋮----
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
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
⋮----
cardBounds[i] = ResponsiveGrid.cardBounds(i, metrics.safeLeft(), metrics.contentTop(), cardHeight, grid);
⋮----
float actionW = metrics.contentWidth() / actions.length;
float actionH = Math.max(metrics.touchTarget(), metrics.footerTop() - metrics.safeBottom() - 12f);
⋮----
actions[i] = new Rectangle(metrics.safeLeft() + i * actionW + 4f, metrics.safeBottom() + 6f,
⋮----
@Override public void render(float delta) {
visualTime += Math.max(0f, delta);
handleInput();
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
int size = game.profile.inventory.size();
if (size > 0) index = Math.max(0, Math.min(index, size - 1));
⋮----
int pageEnd = Math.min(size, pageStart + PAGE_SIZE);
⋮----
drawShapes(size, pageStart, pageEnd);
drawText(size, pageStart, pageEnd);
⋮----
private void drawShapes(int size, int pageStart, int pageEnd) {
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, visualTime);
UiRenderer.topRail(shapes, metrics);
UiRenderer.bottomNav(shapes, metrics);
UiRenderer.premiumPanel(shapes, detail.x, detail.y, detail.width, detail.height,
size > 0 ? rarityColor(game.profile.inventory.items().get(index).rarity) : VisualTheme.CYAN_SOFT, true);
if (size > 0) drawDetailChrome(shapes, game.profile.inventory.items().get(index));
else drawEmptyGearState(shapes);
⋮----
EquipmentItem item = game.profile.inventory.items().get(i);
EquipmentItem equipped = game.profile.equipped(item.slot);
boolean isEquipped = equipped != null && equipped.id.equals(item.id);
UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, rarityColor(item.rarity), i == index, isEquipped, false);
drawGearCardChrome(shapes, r, item, i == index, isEquipped);
⋮----
UiRenderer.premiumButton(shapes, actions[0].x, actions[0].y, actions[0].width, actions[0].height, VisualTheme.CYAN_SOFT, UiRenderer.ButtonState.NORMAL);
UiRenderer.premiumButton(shapes, actions[1].x, actions[1].y, actions[1].width, actions[1].height, VisualTheme.GOLD,
⋮----
UiRenderer.premiumButton(shapes, actions[2].x, actions[2].y, actions[2].width, actions[2].height, VisualTheme.CYAN_SOFT,
⋮----
UiRenderer.premiumButton(shapes, actions[3].x, actions[3].y, actions[3].width, actions[3].height, VisualTheme.VIOLET,
⋮----
shapes.end();
⋮----
private void drawEmptyGearState(ShapeRenderer shapes) {
float size = Math.min(118f, detail.height * .42f);
⋮----
UiRenderer.iconBadge(shapes, x - 12f, y - 12f, size + 24f, VisualTheme.CYAN_SOFT, false);
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.GEAR, x, y, size, VisualTheme.CYAN_SOFT, .72f);
⋮----
float railW = Math.min(detail.width * .48f, 520f);
⋮----
UiRenderer.segmentedTrack(shapes, railX, railY, railW, 8f, .18f, 10, VisualTheme.CYAN_SOFT);
⋮----
private void drawGearCardChrome(ShapeRenderer shapes, Rectangle r, EquipmentItem item,
⋮----
Color rarity = rarityColor(item.rarity);
⋮----
shapes.setColor(rarity.r, rarity.g, rarity.b, railAlpha);
shapes.rect(r.x + 6f, r.y + 6f, 4f, Math.max(0f, r.height - 12f));
shapes.rect(r.x + 10f, r.y + r.height - 5f, Math.max(0f, r.width - 16f), 3f);
⋮----
shapes.setColor(rarity.r, rarity.g, rarity.b, equipped ? .10f : .065f);
shapes.rect(r.x + 10f, r.y + 8f, Math.max(0f, r.width - 18f), Math.max(0f, r.height - 16f));
⋮----
float chipW = Math.min(58f, r.width * .18f);
shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, .92f);
shapes.rect(r.x + r.width - chipW - 8f, r.y + 8f, chipW, 3f);
⋮----
private void drawDetailChrome(ShapeRenderer shapes, EquipmentItem item) {
⋮----
float itemScore = EquipmentService.score(item);
float equippedScore = EquipmentService.score(equipped);
⋮----
Color compare = equipped != null && equipped.id.equals(item.id)
? VisualTheme.positive()
: delta >= 0f ? VisualTheme.positive() : VisualTheme.danger();
⋮----
shapes.setColor(rarity.r, rarity.g, rarity.b, .78f);
shapes.rect(detail.x + 5f, detail.y + detail.height - 5f, Math.max(0f, detail.width - 10f), 3f);
⋮----
shapes.setColor(VisualTheme.BORDER.r, VisualTheme.BORDER.g, VisualTheme.BORDER.b, .46f);
shapes.rect(splitX, detail.y + 12f, 2f, Math.max(0f, detail.height - 24f));
⋮----
shapes.setColor(compare.r, compare.g, compare.b, .08f);
shapes.rect(detail.x + 8f, detail.y + 8f, Math.max(0f, detail.width * .50f - 12f),
Math.max(0f, detail.height - 16f));
shapes.setColor(compare.r, compare.g, compare.b, .72f);
shapes.rect(detail.x + 8f, detail.y + 8f, Math.max(0f, detail.width * .50f - 12f), 3f);
⋮----
private void drawText(int size, int pageStart, int pageEnd) {
batch.begin();
int ascensionPieces = ThreatSetBonusRules.equippedPieces(game.profile);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t("gear.title"), metrics.safeLeft() + 18f, metrics.headerBottom() + 56f,
metrics.contentWidth() * .38f, Align.left, false);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, f("gear.power", String.format(java.util.Locale.ROOT, "%.3f", game.profile.aggregatePowerMultiplier())),
metrics.safeLeft() + metrics.contentWidth() * .40f, metrics.headerBottom() + 48f,
metrics.contentWidth() * .25f, Align.center, false);
font.setColor(ascensionPieces >= 2 ? VisualTheme.GOLD : VisualTheme.MUTED);
font.draw(batch, ThreatSetBonusRules.summary(ascensionPieces), metrics.safeLeft() + metrics.contentWidth() * .64f,
metrics.headerBottom() + 48f, metrics.contentWidth() * .34f, Align.right, false);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION) * 1.08f);
⋮----
font.draw(batch, t("gear.empty"), detail.x + 20f, detail.y + detail.height * .23f,
⋮----
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, t("gear.emptyBay"), detail.x + 20f,
⋮----
for (int i = pageStart; i < pageEnd; i++) drawCard(game.profile.inventory.items().get(i), i, pageStart);
drawDetail(game.profile.inventory.items().get(index), size);
⋮----
drawActions(size);
batch.end();
⋮----
private void drawCard(EquipmentItem item, int absoluteIndex, int pageStart) {
⋮----
boolean exclusive = ThreatMilestoneRewardCatalog.isExclusiveId(item.id);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
font.setColor(rarityColor(item.rarity));
font.draw(batch, localizedName(item), r.x + 16f, r.y + r.height - 15f, r.width - 32f, Align.left, false);
⋮----
font.setColor(exclusive ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
font.draw(batch, t(item.slotKey()) + "  •  " + t(item.rarityKey()) + "  •  Lv " + item.level,
⋮----
font.setColor(isEquipped ? VisualTheme.positive() : absoluteIndex == index ? VisualTheme.accent() : VisualTheme.TEXT_DIM);
font.draw(batch, isEquipped ? t("gear.equipped") : t("gear.unequipped"),
⋮----
font.draw(batch, t(item.rarityKey()), r.x + 16f, r.y + 16f, r.width * .45f, Align.left, false);
⋮----
private void drawDetail(EquipmentItem item, int size) {
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
⋮----
font.draw(batch, localizedName(item), x, top, width * .48f, Align.left, false);
⋮----
font.draw(batch, f("gear.itemStats", t(item.slotKey()), item.level, Math.round(item.powerBonus * 1000f) / 10f),
⋮----
font.setColor(isEquipped ? VisualTheme.positive() : scoreDelta >= 0f ? VisualTheme.positive() : VisualTheme.danger());
String compare = isEquipped ? t("gear.equipped") : equipped == null ? t("gear.noEquipped") :
f("gear.compare", String.format(java.util.Locale.ROOT, "%+.1f", equippedScore <= 0f ? 100f : (scoreDelta / equippedScore) * 100f));
font.draw(batch, compare, x, top - 54f, width * .48f, Align.left, false);
⋮----
font.setColor(VisualTheme.GOLD);
font.draw(batch, f("gear.upgrade", EquipmentUpgradeService.cost(item)), rightX, top, detail.width * .42f, Align.left, false);
⋮----
font.draw(batch, f("gear.index", index + 1, size), rightX, top - 28f, detail.width * .42f, Align.left, false);
if (!status.isEmpty()) {
font.setColor(VisualTheme.accent());
font.draw(batch, status, rightX, top - 56f, detail.width * .42f, Align.left, true);
⋮----
private void drawActions(int size) {
String[] labels = {t("gear.back"), t("gear.equip"), t("gear.upgradeButton"), t("gear.fuse")};
⋮----
font.setColor(size == 0 && i > 0 ? VisualTheme.MUTED : i == 1 ? VisualTheme.TEXT_STRONG : VisualTheme.TEXT);
⋮----
font.draw(batch, labels[i], r.x + 8f, r.y + r.height * .60f, r.width - 16f, Align.center, false);
⋮----
private void handleInput() {
⋮----
if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
game.showMenu();
⋮----
if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) { move(-1, size); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) { move(1, size); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.E)) { toggleEquip(); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.U)) { upgradeSelected(); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.F)) { fuseSelected(game.profile.inventory.items().get(index)); return; }
⋮----
if (!Gdx.input.justTouched()) return;
⋮----
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (actions[0].contains(touch)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); return; }
⋮----
if (actions[1].contains(touch)) { toggleEquip(); return; }
if (actions[2].contains(touch)) { upgradeSelected(); return; }
if (actions[3].contains(touch)) { fuseSelected(game.profile.inventory.items().get(index)); return; }
⋮----
if (!cardBounds[i - pageStart].contains(touch)) continue;
⋮----
AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
⋮----
private void move(int delta, int size) {
⋮----
private void toggleEquip() {
if (game.profile.inventory.size() == 0) return;
EquipmentItem item = game.profile.inventory.items().get(index);
EquipmentItem current = game.profile.equipped(item.slot);
if (current != null && current.id.equals(item.id)) EquipmentService.unequip(game.profile, item.slot);
else EquipmentService.equip(game.profile, item.id);
status = t("gear.loadoutUpdated");
game.saveProfile();
⋮----
private void upgradeSelected() {
⋮----
if (EquipmentService.upgrade(game.profile, item.id)) {
status = t("gear.upgraded");
⋮----
status = t("gear.upgradeUnavailable");
⋮----
private void fuseSelected(EquipmentItem selected) {
if (selected.rarity == EquipmentItem.Rarity.MYTHIC) { status = t("gear.mythicNoFuse"); return; }
⋮----
for (EquipmentItem candidate : game.profile.inventory.items()) {
if (candidate.id.equals(selected.id) || candidate.slot != selected.slot || candidate.rarity != selected.rarity) continue;
⋮----
if (second == null || third == null) { status = t("gear.needThree"); AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); return; }
EquipmentItem merged = EquipmentService.mergeThree(game.profile, selected.id, second.id, third.id);
if (merged == null) { status = t("gear.fusionFailed"); AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); return; }
status = f("gear.created", t(merged.rarityKey()), localizedName(merged));
index = Math.max(0, game.profile.inventory.size() - 1);
⋮----
private Color rarityColor(EquipmentItem.Rarity rarity) {
⋮----
private String localizedName(EquipmentItem item) {
⋮----
String key = item.nameKey();
if (key != null) return t(key);
return f("equipment.generatedName", t(item.rarityKey()), t(item.slotKey()));
⋮----
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
⋮----
@Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/screen/MenuLayoutModel.java
```java
/** Pure layout model for the responsive home/deployment screen. */
public final class MenuLayoutModel {
⋮----
public static Layout layout(UiLayout.Metrics m) {
⋮----
float contentX = m.safeLeft();
float contentY = m.contentBottom();
float contentW = m.contentWidth();
float contentH = m.contentHeight();
⋮----
float leftW = m.wide() ? Math.min(contentW * .54f, 820f) : contentW * .52f;
⋮----
float rightW = Math.max(m.touchTarget(), contentX + contentW - rightX);
⋮----
Rectangle survivor = new Rectangle(contentX, contentY, leftW, contentH);
⋮----
Rectangle deploy = new Rectangle(rightX, contentY, rightW, deployH);
⋮----
Rectangle threat = new Rectangle(rightX, deploy.y + deploy.height + gap, rightW, threatH);
⋮----
Rectangle loadout = new Rectangle(rightX, loadoutY, rightW, Math.max(m.touchTarget(), m.contentTop() - loadoutY));
⋮----
Rectangle topRail = new Rectangle(
m.safeLeft(),
m.headerBottom(),
m.contentWidth(),
m.safeTop() - m.headerBottom()
⋮----
Rectangle bottomNav = new Rectangle(
⋮----
m.safeBottom(),
⋮----
m.footerTop() - m.safeBottom()
⋮----
// Rectangle.contains(Rectangle) is strict on shared edges. Keep touch regions one logical
// unit inside the visual rail so containment stays deterministic across aspect ratios.
⋮----
float innerWidth = Math.max(0f, bottomNav.width - inset * 2f);
⋮----
float tabH = Math.max(0f, bottomNav.height - inset * 2f);
⋮----
tabs[i] = new Rectangle(bottomNav.x + inset + i * tabW, bottomNav.y + inset, tabW, tabH);
⋮----
return new Layout(topRail, survivor, loadout, threat, deploy, bottomNav, tabs);
```

## File: src/main/java/com/deadlinezero/game/screen/MenuScreen.java
```java
/** Responsive premium Base/Home shell with explicit mobile interaction regions. */
public final class MenuScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final BitmapFont font = new BitmapFont();
private final ShapeRenderer shapes = new ShapeRenderer();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
⋮----
private BalanceTelemetrySummary.Summary balanceSummary = BalanceTelemetrySummary.summarize(null);
private BalanceTelemetryReport.Report balanceReport = BalanceTelemetryReport.analyze(null);
⋮----
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
@Override public void resize(int width, int height) {
viewport.resize(width, height);
metrics = UiLayout.compute(width, height);
layout = MenuLayoutModel.layout(metrics);
⋮----
@Override public void render(float delta) {
t += Math.max(0f, delta);
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
drawShapes(p);
drawContent(p);
handleInput();
⋮----
private void drawShapes(PlayerProfile p) {
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, t);
UiRenderer.topRail(shapes, metrics);
UiRenderer.bottomNav(shapes, metrics);
UiRenderer.premiumPanel(shapes, layout.survivorCard().x, layout.survivorCard().y,
layout.survivorCard().width, layout.survivorCard().height, VisualTheme.accent(), true);
UiRenderer.premiumPanel(shapes, layout.loadoutCard().x, layout.loadoutCard().y,
layout.loadoutCard().width, layout.loadoutCard().height, VisualTheme.CYAN_SOFT, false);
UiRenderer.premiumPanel(shapes, layout.threatCard().x, layout.threatCard().y,
layout.threatCard().width, layout.threatCard().height,
⋮----
float deployPulse = .5f + .5f * (float)Math.sin(t * 2.4f);
UiRenderer.premiumCta(shapes, layout.deploy().x, layout.deploy().y,
layout.deploy().width, layout.deploy().height, VisualTheme.accent(), deployPulse);
drawHomeChrome(shapes, p);
drawHomeIcons(shapes, p);
⋮----
Rectangle[] tabs = layout.bottomTabs();
⋮----
shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .14f);
shapes.rect(tab.x + 4f, tab.y + 5f, tab.width - 8f, tab.height - 10f);
shapes.setColor(VisualTheme.accent());
shapes.rect(tab.x + 16f, tab.y + 4f, Math.max(0f, tab.width - 32f), 3f);
⋮----
shapes.setColor(VisualTheme.DIVIDER);
shapes.rect(tab.x, tab.y + 18f, 1f, Math.max(0f, tab.height - 36f));
⋮----
shapes.end();
⋮----
private void drawHomeChrome(ShapeRenderer shapes, PlayerProfile p) {
Rectangle survivor = layout.survivorCard();
// The survivor is the hero, not a full-screen cyan slab: frame the portrait instead.
shapes.setColor(VisualTheme.SURFACE_2);
shapes.rect(survivor.x + 10f, survivor.y + 10f, Math.max(0f, survivor.width - 20f), Math.max(0f, survivor.height - 20f));
⋮----
shapes.rect(survivor.x + 10f, survivor.y + 10f, 5f, Math.max(0f, survivor.height - 20f));
shapes.rect(survivor.x + 10f, survivor.y + survivor.height - 5f, Math.max(0f, survivor.width * .34f), 3f);
shapes.setColor(VisualTheme.BORDER);
shapes.rect(survivor.x + survivor.width * .46f, survivor.y + 24f, 1f, Math.max(0f, survivor.height - 48f));
⋮----
// Hero staging: a grounded platform and soft spotlight make the survivor feel authored,
// rather than a loose sprite floating inside a card.
⋮----
shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .10f);
shapes.ellipse(heroCx - survivor.width * .16f, heroBaseY - 18f, survivor.width * .32f, 36f);
shapes.setColor(VisualTheme.CYAN_SOFT.r, VisualTheme.CYAN_SOFT.g, VisualTheme.CYAN_SOFT.b, .22f);
shapes.rect(heroCx - survivor.width * .10f, heroBaseY - 2f, survivor.width * .20f, 2f);
shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .035f);
shapes.triangle(heroCx - survivor.width * .20f, survivor.y + survivor.height - 18f,
⋮----
Rectangle loadout = layout.loadoutCard();
⋮----
shapes.setColor(weaponAccent.r, weaponAccent.g, weaponAccent.b, .46f);
shapes.rect(loadout.x + 6f, loadout.y + loadout.height - 4f, Math.max(0f, loadout.width - 12f), 3f);
⋮----
Rectangle threat = layout.threatCard();
⋮----
shapes.setColor(threatAccent.r, threatAccent.g, threatAccent.b, p.selectedThreatTier > 0 ? .82f : .38f);
shapes.rect(threat.x + 6f, threat.y + threat.height - 4f, Math.max(0f, threat.width - 12f), 3f);
⋮----
shapes.setColor(threatAccent.r, threatAccent.g, threatAccent.b, .08f);
shapes.rect(threat.x + 7f, threat.y + 7f, Math.max(0f, threat.width - 14f), Math.max(0f, threat.height - 14f));
⋮----
private void drawHomeIcons(ShapeRenderer shapes, PlayerProfile p) {
Rectangle top = layout.topRail();
⋮----
VisualTheme.accent(),
⋮----
UiRenderer.iconBadge(shapes, ix - 4f, iy - 4f, icon + 8f, topColors[i], i != 3 || p.highestStage > 1);
UiIconRenderer.draw(shapes, topIcons[i], ix, iy, icon, topColors[i], .95f);
⋮----
Color color = i == 0 ? VisualTheme.accent() : VisualTheme.TEXT_DIM;
UiIconRenderer.draw(shapes, navIcons[i], x, y, size, color, i == 0 ? 1f : .72f);
⋮----
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.ARSENAL,
⋮----
UiIconRenderer.draw(shapes,
ThreatTierRules.unlocked(p) ? UiIconRenderer.Icon.STAGE : UiIconRenderer.Icon.LOCK,
⋮----
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
⋮----
private void drawTopRail(PlayerProfile p) {
Rectangle r = layout.topRail();
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, f("menu.level", p.accountLevel), r.x + 18f, baseline, col - 24f, Align.left, false);
font.setColor(VisualTheme.GOLD);
font.draw(batch, f("menu.credits", p.currency(PlayerProfile.Currency.CREDITS)), r.x + col, baseline, col, Align.center, false);
font.setColor(VisualTheme.accent());
font.draw(batch, f("menu.gems", p.currency(PlayerProfile.Currency.GEMS)), r.x + col * 2f, baseline, col, Align.center, false);
⋮----
font.draw(batch, f("menu.stage", p.selectedStage, p.highestStage), r.x + col * 3f, baseline, col - 18f, Align.right, false);
⋮----
private void drawSurvivorCard(PlayerProfile p) {
Rectangle r = layout.survivorCard();
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, GameConfig.TITLE, r.x + pad, r.y + r.height - 30f);
⋮----
font.draw(batch, t("menu.tagline"), r.x + pad, r.y + r.height - 56f);
⋮----
if (game.art.authoredAvailable()) {
TextureRegion portrait = game.art.survivor(p.selectedSurvivor, GameArt.Motion.IDLE, t);
float maxH = Math.min(380f, r.height * .82f);
⋮----
float aspect = portrait.getRegionWidth() / (float) Math.max(1, portrait.getRegionHeight());
⋮----
drawH = drawW / Math.max(.01f, aspect);
⋮----
float py = r.y + Math.max(28f, (r.height - drawH) * .24f);
batch.setColor(Color.WHITE);
batch.draw(portrait, px, py, drawW, drawH);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE) * 1.12f);
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, p.selectedSurvivor.displayName.toUpperCase(), tx, r.y + r.height * .64f, tw, Align.left, false);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY));
⋮----
font.draw(batch, p.selectedSurvivor.role.toUpperCase(), tx, r.y + r.height * .54f, tw, Align.left, false);
⋮----
font.draw(batch, t("menu.changeSurvivor"), tx, r.y + r.height * .43f, tw, Align.left, true);
⋮----
font.draw(batch, t("survivor.title"), tx, r.y + 34f, tw, Align.left, false);
⋮----
private void drawLoadout(PlayerProfile p) {
Rectangle r = layout.loadoutCard();
⋮----
font.draw(batch, t("arsenal.title"), r.x + pad, r.y + r.height - 26f);
⋮----
var weapon = WeaponCatalog.byId(p.selectedWeaponId);
if (game.art != null && game.art.authoredAvailable()) {
TextureRegion region = game.art.regionOrNull("weapon/" + weapon.id);
⋮----
float aspect = region.getRegionWidth() / (float)Math.max(1, region.getRegionHeight());
⋮----
float drawH = drawW / Math.max(.01f, aspect);
⋮----
batch.draw(region, r.x + r.width - drawW - 28f, r.y + 34f, drawW, drawH);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
⋮----
font.draw(batch, weapon.displayName.toUpperCase(),
⋮----
font.draw(batch, f("menu.deployStage", p.selectedStage), r.x + pad, r.y + r.height - 88f, r.width - pad * 2f, Align.left, false);
⋮----
font.draw(batch, t("menu.arsenal"), r.x + pad, r.y + 28f);
⋮----
private void drawThreat(PlayerProfile p) {
Rectangle r = layout.threatCard();
⋮----
if (ThreatTierRules.unlocked(p)) {
font.setColor(p.selectedThreatTier > 0 ? VisualTheme.GOLD : VisualTheme.CYAN_SOFT);
font.draw(batch, f("menu.threat", p.selectedThreatTier, p.highestThreatTier,
ThreatTierRules.rewardBonusPercent(p.selectedThreatTier)),
⋮----
font.setColor(VisualTheme.MUTED);
font.draw(batch, f("menu.threatLocked", ThreatTierRules.UNLOCK_STAGE),
⋮----
private void drawDeploy(PlayerProfile p) {
Rectangle r = layout.deploy();
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION) * 1.12f);
⋮----
font.draw(batch, t("menu.deploy"), r.x, r.y + r.height * .64f, r.width, Align.center, false);
⋮----
font.setColor(VisualTheme.TEXT);
font.draw(batch, f("menu.deployStage", p.selectedStage), r.x, r.y + r.height * .32f, r.width, Align.center, false);
⋮----
private void drawBottomNav() {
⋮----
t("menu.base"), t("menu.arsenal"), t("menu.gear"),
t("menu.missions"), t("menu.shop"), t("menu.settings")
⋮----
font.setColor(i == 0 ? VisualTheme.accent() : VisualTheme.TEXT_DIM);
font.draw(batch, labels[i], tab.x + 6f, tab.y + tab.height * .30f, tab.width - 12f, Align.center, false);
⋮----
private void drawBalanceDebug() {
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .78f);
⋮----
String line = String.format(java.util.Locale.ROOT,
⋮----
balanceSummary.runs(), balanceSummary.winRate() * 100f, balanceSummary.averageSeconds(),
balanceSummary.averageDps(), balanceSummary.averageDamageTakenPerMinute(), balanceSummary.averageKillsPerMinute());
font.draw(batch, line, r.x + 20f, r.y + 54f, r.width - 40f, Align.left, true);
BalanceTelemetryReport.Outlier outlier = balanceReport.worstOutlier();
⋮----
font.setColor(VisualTheme.danger());
String diagnostic = String.format(java.util.Locale.ROOT, "%s %s", outlier.dimension(), outlier.key());
font.draw(batch, diagnostic, r.x + 20f, r.y + 76f, r.width - 40f, Align.left, false);
⋮----
private void handleInput() {
if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
⋮----
var samples = BalanceTelemetryStore.loadRecent();
balanceSummary = BalanceTelemetrySummary.summarize(samples);
balanceReport = BalanceTelemetryReport.analyze(samples);
⋮----
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
⋮----
if (!Gdx.input.justTouched()) return;
⋮----
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (layout.survivorCard().contains(touch)) { selectCue(); game.showSurvivors(); return; }
if (layout.loadoutCard().contains(touch)) { selectCue(); game.showArsenal(); return; }
if (layout.threatCard().contains(touch)) {
changeThreat(touch.x < layout.threatCard().x + layout.threatCard().width * .5f ? -1 : 1);
⋮----
if (layout.deploy().contains(touch)) { selectCue(); game.startRun(); return; }
⋮----
if (!tabs[i].contains(touch)) continue;
⋮----
case 1 -> game.showArsenal();
case 2 -> game.showGear();
case 3 -> game.showMissions();
case 4 -> game.showShop();
case 5 -> game.showSettings();
⋮----
private void changeThreat(int delta) {
if (!ThreatTierRules.unlocked(game.profile)) return;
int next = Math.max(0, Math.min(game.profile.highestThreatTier, game.profile.selectedThreatTier + delta));
if (game.profile.selectThreatTier(next)) {
⋮----
game.saveProfile();
⋮----
private void selectCue() { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); }
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
⋮----
@Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/screen/MissionsScreen.java
```java
/** Responsive daily/weekly missions plus permanent non-FOMO mastery progression. */
public final class MissionsScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final BitmapFont font = new BitmapFont();
private final ShapeRenderer shapes = new ShapeRenderer();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
⋮----
private final Rectangle[] achievementRows = new Rectangle[AchievementService.Achievement.values().length];
⋮----
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
@Override public void resize(int width, int height) {
viewport.resize(width, height);
metrics = UiLayout.compute(width, height);
layout = MetaLayout.compute(metrics);
Rectangle[] columns = MetaLayout.columns(layout.content(), 3, 18f);
⋮----
Rectangle dailyInner = inset(dailyPanel, 16f, 48f, 16f, 14f);
Rectangle[] d = MetaLayout.rows(dailyInner, 4, 10f);
System.arraycopy(d, 0, dailyRows, 0, dailyRows.length);
Rectangle weeklyInner = inset(weeklyPanel, 16f, 48f, 16f, 14f);
Rectangle[] w = MetaLayout.rows(weeklyInner, 3, 12f);
System.arraycopy(w, 0, weeklyRows, 0, weeklyRows.length);
⋮----
float masteryH = Math.min(142f, progressPanel.height * .32f);
masteryPanel = new Rectangle(progressPanel.x + 14f, progressPanel.y + progressPanel.height - masteryH - 46f,
⋮----
achievementsPanel = new Rectangle(progressPanel.x + 14f, progressPanel.y + 14f,
⋮----
float cellH = (achievementsPanel.height - gap * Math.max(0, rows - 1)) / rows;
⋮----
achievementRows[i] = new Rectangle(achievementsPanel.x + c * (cellW + gap),
⋮----
@Override public void render(float delta) {
visualTime += Math.max(0f, delta);
handleInput();
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, visualTime);
UiRenderer.topRail(shapes, metrics);
UiRenderer.premiumPanel(shapes, dailyPanel.x, dailyPanel.y, dailyPanel.width, dailyPanel.height,
⋮----
UiRenderer.premiumPanel(shapes, weeklyPanel.x, weeklyPanel.y, weeklyPanel.width, weeklyPanel.height,
⋮----
UiRenderer.premiumPanel(shapes, progressPanel.x, progressPanel.y, progressPanel.width, progressPanel.height,
VisualTheme.accent(), false);
UiRenderer.sectionBand(shapes, dailyPanel.x + 6f, dailyPanel.y + dailyPanel.height - 42f,
⋮----
UiRenderer.sectionBand(shapes, weeklyPanel.x + 6f, weeklyPanel.y + weeklyPanel.height - 42f,
⋮----
UiRenderer.sectionBand(shapes, progressPanel.x + 6f, progressPanel.y + progressPanel.height - 42f,
progressPanel.width - 12f, 34f, VisualTheme.accent());
drawMissionStateCard(shapes, dailyRows[0], p.daily.loginClaimed, !p.daily.loginClaimed, VisualTheme.GOLD);
drawMissionStateCard(shapes, dailyRows[1], p.daily.killMissionClaimed, p.daily.killsToday >= 100, VisualTheme.GOLD);
drawMissionStateCard(shapes, dailyRows[2], p.daily.runMissionClaimed, p.daily.runsToday >= 3, VisualTheme.GOLD);
drawMissionStateCard(shapes, dailyRows[3], p.daily.bossMissionClaimed, p.daily.bossesToday >= 1, VisualTheme.GOLD);
⋮----
drawMissionStateCard(shapes, weeklyRows[0], p.weekly.killMissionClaimed,
⋮----
drawMissionStateCard(shapes, weeklyRows[1], p.weekly.runMissionClaimed,
⋮----
drawMissionStateCard(shapes, weeklyRows[2], p.weekly.bossMissionClaimed,
⋮----
drawMissionProgressBars(shapes, p);
drawMissionRowIcons(shapes, p);
⋮----
UiRenderer.premiumPanel(shapes, masteryPanel.x, masteryPanel.y, masteryPanel.width, masteryPanel.height,
VisualTheme.accent(), true);
drawMissionIcons(shapes, p);
⋮----
AchievementService.Achievement a = AchievementService.Achievement.values()[i];
boolean unlocked = AchievementService.unlocked(p, a);
boolean claimed = p.achievements.claimed(a);
⋮----
UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, accent,
⋮----
shapes.setColor(accent.r, accent.g, accent.b, claimed ? .18f : unlocked ? .78f : .28f);
shapes.rect(r.x + 5f, r.y + r.height - 4f, Math.max(0f, r.width - 10f), 3f);
⋮----
shapes.end();
⋮----
batch.begin();
drawHeader();
drawDaily(p);
drawWeekly(p);
drawProgress(p);
batch.end();
⋮----
private void drawMissionIcons(ShapeRenderer shapes, PlayerProfile p) {
⋮----
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.MISSIONS,
⋮----
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.TROPHY,
⋮----
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.STAGE,
⋮----
size, VisualTheme.accent(), .92f);
⋮----
AchievementService.Achievement[] all = AchievementService.Achievement.values();
⋮----
boolean unlocked = AchievementService.unlocked(p, all[i]);
boolean claimed = p.achievements.claimed(all[i]);
⋮----
UiIconRenderer.draw(shapes,
⋮----
private void drawMissionRowIcons(ShapeRenderer shapes, PlayerProfile p) {
⋮----
Color accent = claimed ? VisualTheme.MUTED : ready ? VisualTheme.positive() : VisualTheme.GOLD;
UiRenderer.iconBadge(shapes, r.x + 10f, r.y + r.height * .50f - 16f, 32f, accent, ready && !claimed);
UiIconRenderer.draw(shapes, daily[i], r.x + 16f, r.y + r.height * .50f - 10f,
⋮----
Color accent = claimed ? VisualTheme.MUTED : ready ? VisualTheme.positive() : VisualTheme.VIOLET;
⋮----
UiIconRenderer.draw(shapes, weekly[i], r.x + 16f, r.y + r.height * .50f - 10f,
⋮----
UiRenderer.iconBadge(shapes, masteryPanel.x + 10f, masteryPanel.y + masteryPanel.height - 43f,
⋮----
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.ARSENAL,
⋮----
UiRenderer.iconBadge(shapes, masteryPanel.x + 10f, masteryPanel.y + masteryPanel.height * .45f - 15f,
⋮----
private void drawMissionProgressBars(ShapeRenderer shapes, PlayerProfile p) {
drawRowProgress(shapes, dailyRows[0], p.daily.loginClaimed ? 1f : 0f, VisualTheme.GOLD);
drawRowProgress(shapes, dailyRows[1], p.daily.killsToday / 100f, VisualTheme.GOLD);
drawRowProgress(shapes, dailyRows[2], p.daily.runsToday / 3f, VisualTheme.GOLD);
drawRowProgress(shapes, dailyRows[3], p.daily.bossesToday, VisualTheme.GOLD);
drawRowProgress(shapes, weeklyRows[0], p.weekly.kills / (float) WeeklyService.KILL_TARGET, VisualTheme.VIOLET);
drawRowProgress(shapes, weeklyRows[1], p.weekly.runs / (float) WeeklyService.RUN_TARGET, VisualTheme.VIOLET);
drawRowProgress(shapes, weeklyRows[2], p.weekly.bosses / (float) WeeklyService.BOSS_TARGET, VisualTheme.VIOLET);
⋮----
private void drawRowProgress(ShapeRenderer shapes, Rectangle r, float progress, Color accent) {
float barW = Math.max(32f, r.width - 24f);
UiRenderer.progress(shapes, r.x + 12f, r.y + 9f, barW, 5f, progress, accent);
⋮----
private void drawMissionStateCard(ShapeRenderer shapes, Rectangle r, boolean claimed,
⋮----
Color stateAccent = claimed ? VisualTheme.MUTED : ready ? VisualTheme.positive() : categoryAccent;
UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, stateAccent,
⋮----
shapes.setColor(stateAccent.r * alpha, stateAccent.g * alpha, stateAccent.b * alpha, 1f);
shapes.rect(r.x + 5f, r.y + r.height - 5f, Math.max(0f, r.width - 10f), 3f);
shapes.rect(r.x + 5f, r.y + 5f, 3f, Math.max(0f, r.height - 10f));
⋮----
shapes.setColor(stateAccent.r * .10f, stateAccent.g * .10f, stateAccent.b * .10f, 1f);
shapes.rect(r.x + 8f, r.y + 16f, Math.max(0f, r.width - 16f), Math.max(0f, r.height - 24f));
float notch = Math.min(22f, r.width * .08f);
shapes.setColor(stateAccent.r, stateAccent.g, stateAccent.b, .92f);
shapes.rect(r.x + r.width - notch - 8f, r.y + 7f, notch, 3f);
⋮----
shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .30f);
shapes.rect(r.x + 7f, r.y + 7f, Math.max(0f, r.width - 14f), Math.max(0f, r.height - 14f));
⋮----
private void drawHeader() {
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, t("shop.back"), layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
layout.back().width - 16f, Align.left, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t("missions.title"), metrics.safeLeft() + 138f, metrics.headerBottom() + 55f,
metrics.contentWidth() - 276f, Align.center, false);
⋮----
private void drawDaily(PlayerProfile p) {
heading(t("missions.daily"), dailyPanel, VisualTheme.GOLD);
drawClaimRow(dailyRows[0], f("missions.login", p.daily.loginStreak,
p.daily.loginClaimed ? t("missions.loginClaimed") : t("missions.loginClaim")),
⋮----
drawClaimRow(dailyRows[1], progressText(t("missions.dailyKills"), p.daily.killsToday, 100),
⋮----
drawClaimRow(dailyRows[2], progressText(t("missions.dailyRuns"), p.daily.runsToday, 3),
⋮----
drawClaimRow(dailyRows[3], progressText(t("missions.dailyBoss"), p.daily.bossesToday, 1),
⋮----
private void drawWeekly(PlayerProfile p) {
heading(t("missions.weekly"), weeklyPanel, VisualTheme.VIOLET);
drawClaimRow(weeklyRows[0], progressText(f("missions.weeklyKills", WeeklyService.KILL_TARGET), p.weekly.kills, WeeklyService.KILL_TARGET),
⋮----
drawClaimRow(weeklyRows[1], progressText(f("missions.weeklyRuns", WeeklyService.RUN_TARGET), p.weekly.runs, WeeklyService.RUN_TARGET),
⋮----
drawClaimRow(weeklyRows[2], progressText(f("missions.weeklyBoss", WeeklyService.BOSS_TARGET), p.weekly.bosses, WeeklyService.BOSS_TARGET),
⋮----
private void drawProgress(PlayerProfile p) {
heading(t("missions.mastery"), progressPanel, VisualTheme.accent());
WeaponDefinition weapon = p.selectedWeapon();
EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(p.selectedStage);
int weaponRank = p.mastery.weaponRank(weapon.id);
int biomeRank = p.mastery.biomeRank(biome);
int weaponNext = p.mastery.winsForNextWeaponRank(weapon.id);
int biomeNext = p.mastery.winsForNextBiomeRank(biome);
⋮----
font.draw(batch, f("missions.masteryLine", t(weapon.displayNameKey()), weaponRank, MasteryProgress.MAX_RANK,
t("mastery.rank." + weaponRank), nextLabel(weaponNext)), masteryPanel.x + 50f,
⋮----
font.setColor(VisualTheme.VIOLET);
font.draw(batch, f("missions.masteryLine", t(biome.labelKey()), biomeRank, MasteryProgress.MAX_RANK,
t("mastery.rank." + biomeRank), nextLabel(biomeNext)), masteryPanel.x + 50f,
⋮----
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, t("missions.achievements"), achievementsPanel.x, achievementsPanel.y + achievementsPanel.height + 18f,
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .90f);
font.setColor(claimed ? VisualTheme.MUTED : unlocked ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
font.draw(batch, t(all[i].titleKey()), r.x + 34f, r.y + r.height * .67f, r.width - 42f, Align.left, true);
font.setColor(claimed ? VisualTheme.MUTED : unlocked ? VisualTheme.accent() : VisualTheme.MUTED);
font.draw(batch, claimed ? t("missions.claimed") : unlocked ? t("common.open") : t("missions.locked"),
⋮----
private void heading(String text, Rectangle panel, com.badlogic.gdx.graphics.Color color) {
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION) * 1.05f);
font.setColor(color);
font.draw(batch, text, panel.x + 16f, panel.y + panel.height - 16f, panel.width - 58f, Align.left, false);
⋮----
private void drawClaimRow(Rectangle r, String text, boolean claimed, boolean ready) {
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * 1.04f);
font.setColor(claimed ? VisualTheme.MUTED : VisualTheme.TEXT_STRONG);
font.draw(batch, text, r.x + 52f, r.y + r.height * .64f, r.width - 64f, Align.left, true);
font.setColor(claimed ? VisualTheme.MUTED : ready ? VisualTheme.positive() : VisualTheme.TEXT_DIM);
font.draw(batch, claimed ? t("missions.claimed") : ready ? t("common.open") : "…",
⋮----
private String progressText(String title, int progress, int target) {
return f("missions.progress", title, Math.min(progress, target), target, "");
⋮----
private void handleInput() {
if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { game.showMenu(); return; }
⋮----
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
⋮----
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
⋮----
for (int i = 0; i < achievementRows.length; i++) if (achievementRows[i].contains(touch)) { changed |= claimAchievement(i); break; }
⋮----
if (changed) game.saveProfile();
⋮----
private boolean claimAchievement(int index) {
⋮----
return AchievementService.claim(game.profile, all[index]);
⋮----
private String nextLabel(int winsNeeded) {
if (winsNeeded <= 0) return t("missions.nextMax");
return winsNeeded == 1 ? f("missions.nextOne", winsNeeded) : f("missions.nextMany", winsNeeded);
⋮----
private static Rectangle inset(Rectangle r, float left, float top, float right, float bottom) {
return new Rectangle(r.x + left, r.y + bottom, Math.max(1f, r.width - left - right), Math.max(1f, r.height - top - bottom));
⋮----
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
⋮----
@Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/screen/RunContractScreen.java
```java
/** Responsive three-card pre-run risk/reward selection. */
public final class RunContractScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final BitmapFont font = new BitmapFont();
private final ShapeRenderer shapes = new ShapeRenderer();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
private final RunModifierContext.Modifier[] offers = RunModifierContext.offers();
⋮----
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
@Override public void resize(int width, int height) {
viewport.resize(width, height);
metrics = UiLayout.compute(width, height);
layout = MetaLayout.compute(metrics);
cards = MetaLayout.columns(layout.content(), offers.length, 20f);
⋮----
@Override public void render(float delta) {
time += Math.min(.05f, Math.max(0f, delta));
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, time);
UiRenderer.topRail(shapes, metrics);
⋮----
Color accent = accent(offer);
UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, accent, offer.legendary(), offer.legendary(), false);
float pulse = .80f + .20f * (float) Math.sin(time * (offer.legendary() ? 4.2f : 2.4f) + i * .7f);
shapes.setColor(accent.r, accent.g, accent.b, .70f + pulse * .18f);
shapes.rect(r.x + 8f, r.y + 12f, 4f, r.height - 24f);
shapes.setColor(accent.r, accent.g, accent.b, .26f + pulse * .06f);
shapes.rect(r.x + 18f, r.y + r.height - 8f, r.width - 36f, 2f);
Rectangle cta = cta(r);
UiRenderer.premiumButton(shapes, cta.x, cta.y, cta.width, cta.height, accent,
offer.legendary() ? UiRenderer.ButtonState.SELECTED : UiRenderer.ButtonState.NORMAL);
⋮----
shapes.end();
⋮----
batch.begin();
drawHeader();
for (int i = 0; i < offers.length; i++) drawCard(i, offers[i], cards[i]);
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, t("contract.footer"), layout.footer().x + 16f,
layout.footer().y + layout.footer().height * .56f, layout.footer().width - 32f, Align.center, false);
batch.end();
⋮----
handleInput();
⋮----
private void drawHeader() {
⋮----
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, t("shop.back"), layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
layout.back().width - 16f, Align.left, false);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t("contract.title"), metrics.safeLeft() + 138f, metrics.headerBottom() + 59f,
metrics.contentWidth() - 276f, Align.center, false);
⋮----
BossAffixRules.Affix bossAffix = BossAffixRules.forRun(RunStageContext.stage(), RunStageContext.threatTier());
String mutator = EndgameMutatorRules.active() ? f("contract.mutator", EndgameMutatorRules.label()) : "";
String bossAffixText = bossAffix == BossAffixRules.Affix.NONE ? "" : f("contract.bossAffix", bossAffix.title);
⋮----
font.setColor(RunStageContext.threatTier() > 0 ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
font.draw(batch, f("contract.header", RunStageContext.stage(), RunStageContext.threatTier(),
ThreatTierRules.rewardBonusPercent(RunStageContext.threatTier()), mutator, bossAffixText),
metrics.safeLeft() + 138f, metrics.headerBottom() + 28f, metrics.contentWidth() - 276f, Align.center, false);
⋮----
private void drawCard(int index, RunModifierContext.Modifier m, Rectangle r) {
Color accent = accent(m);
⋮----
font.setColor(m.legendary() ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
font.draw(batch, m.legendary() ? t("contract.legendary") : t("contract.standard"),
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
font.setColor(accent);
font.draw(batch, f("contract.cardTitle", index + 1, t(m.titleKey())),
⋮----
font.draw(batch, t(m.descriptionKey()), r.x + 28f, r.y + r.height - 112f,
⋮----
font.draw(batch, f("contract.stats",
oneDecimal(m.enemyHp * EndgameMutatorRules.enemyHpMultiplier()),
oneDecimal(m.enemySpeed * EndgameMutatorRules.enemySpeedMultiplier()),
oneDecimal(m.enemyDamage * EndgameMutatorRules.enemyDamageMultiplier()),
oneDecimal(m.spawnInterval * EndgameMutatorRules.spawnIntervalMultiplier())),
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
⋮----
font.draw(batch, f("contract.threat", m.threatPercent()), r.x + pad, r.y + r.height * .31f,
⋮----
int totalRewardBonus = Math.round((m.reward * EndgameMutatorRules.rewardMultiplier() - 1f) * 100f);
⋮----
font.draw(batch, f("contract.rewards", totalRewardBonus), cta.x + 8f, cta.y + cta.height * .61f,
⋮----
String hazard = hazardText();
if (!hazard.isEmpty()) {
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .90f);
font.setColor(RunStageContext.threatTier() >= 8 ? VisualTheme.danger() : VisualTheme.GOLD);
font.draw(batch, hazard, r.x + 16f, r.y + 18f, r.width - 32f, Align.center, true);
⋮----
private String hazardText() {
int stage = RunStageContext.stage();
int tier = RunStageContext.threatTier();
String biome = EnvironmentBiomeRules.isNullSector(stage) ? t("contract.nullSector")
: EnvironmentBiomeRules.isFoundry(stage) ? t("contract.foundry") : "";
String endgame = tier >= 8 ? t("contract.endgameHeavy") : tier >= 5 ? t("contract.endgame") : "";
if (biome.isEmpty()) return endgame;
if (endgame.isEmpty()) return biome;
⋮----
private Rectangle cta(Rectangle card) {
return new Rectangle(card.x + 24f, card.y + 46f, card.width - 48f, 66f);
⋮----
private void handleInput() {
if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
game.showMenu();
⋮----
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) { choose(0); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) { choose(1); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) { choose(2); return; }
if (!Gdx.input.justTouched()) return;
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (layout.back().contains(touch)) { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); return; }
for (int i = 0; i < cards.length; i++) if (cards[i].contains(touch)) { choose(i); return; }
⋮----
private void choose(int index) {
⋮----
AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
game.startRunWithContract(offers[index]);
⋮----
private String oneDecimal(float value) { return String.format(java.util.Locale.ROOT, "%.2f", value); }
private Color accent(RunModifierContext.Modifier modifier) {
if (modifier.legendary()) return VisualTheme.GOLD;
⋮----
case BLOOD_MOON -> VisualTheme.danger();
⋮----
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
⋮----
@Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/screen/RunResultScreen.java
```java
public final class RunResultScreen extends ScreenAdapter {
private static final Color COACHING_ACCENT = new Color(.78f, .64f, 1f, 1f);
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final BitmapFont font = new BitmapFont();
private final ShapeRenderer shapes = new ShapeRenderer();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
⋮----
this.advice = RunRecoveryAdvice.forResult(result);
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
@Override public void resize(int width, int height) {
viewport.resize(width, height);
metrics = UiLayout.compute(width, height);
layout = MetaLayout.compute(metrics);
Rectangle c = layout.content();
hero = new Rectangle(c.x, c.y + c.height * .61f, c.width, c.height * .39f);
Rectangle metricArea = new Rectangle(c.x, c.y + c.height * .32f, c.width, c.height * .23f);
metricsCards = MetaLayout.columns(metricArea, 3, 16f);
coaching = new Rectangle(c.x, c.y, c.width, c.height * .26f);
actions = MetaLayout.actions(layout.footer(), 3, 14f);
⋮----
@Override public void render(float delta) {
visualTime += Math.max(0f, delta);
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, visualTime);
UiRenderer.premiumPanel(shapes, hero.x, hero.y, hero.width, hero.height, VisualTheme.accent(), true);
shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .22f);
shapes.rect(hero.x + 6f, hero.y + hero.height - 6f, hero.width - 12f, 4f);
drawResultHeroMark(shapes);
⋮----
Color[] rewardAccents = {VisualTheme.GOLD, VisualTheme.accent(), VisualTheme.VIOLET};
⋮----
UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, rewardAccents[i], false, i == 0, false);
⋮----
shapes.setColor(accent.r, accent.g, accent.b, .90f);
shapes.rect(r.x, r.y + r.height - 4f, r.width, 4f);
drawRewardIcon(shapes, r, i, accent);
⋮----
UiRenderer.premiumPanel(shapes, coaching.x, coaching.y, coaching.width, coaching.height, COACHING_ACCENT, false);
shapes.setColor(COACHING_ACCENT.r, COACHING_ACCENT.g, COACHING_ACCENT.b, .72f);
shapes.rect(coaching.x, coaching.y, 4f, coaching.height);
if (result.drop() != null) {
Color dropAccent = VisualTheme.equipmentRarity(result.drop().rarity);
⋮----
shapes.setColor(dropAccent.r, dropAccent.g, dropAccent.b, .18f);
shapes.rect(splitX, coaching.y + 8f, coaching.width * .37f - 8f, coaching.height - 16f);
shapes.setColor(dropAccent.r, dropAccent.g, dropAccent.b, .90f);
shapes.rect(splitX, coaching.y + coaching.height - 4f, coaching.width * .37f - 8f, 4f);
float gearSize = Math.min(52f, coaching.height * .38f);
UiRenderer.iconBadge(shapes, splitX + 14f, coaching.y + coaching.height * .5f - gearSize * .5f,
⋮----
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.GEAR,
⋮----
UiRenderer.premiumButton(shapes, actions[0].x, actions[0].y, actions[0].width, actions[0].height, VisualTheme.GOLD, UiRenderer.ButtonState.SELECTED);
UiRenderer.premiumButton(shapes, actions[1].x, actions[1].y, actions[1].width, actions[1].height, VisualTheme.CYAN_SOFT, UiRenderer.ButtonState.NORMAL);
UiRenderer.premiumButton(shapes, actions[2].x, actions[2].y, actions[2].width, actions[2].height, VisualTheme.VIOLET,
⋮----
shapes.end();
⋮----
batch.begin();
drawHero();
drawMetrics();
drawCoaching();
drawActions();
batch.end();
⋮----
handleInput();
⋮----
private void drawResultHeroMark(ShapeRenderer shapes) {
float size = Math.min(52f, hero.height * .30f);
⋮----
UiRenderer.iconBadge(shapes, x - 7f, y - 7f, size + 14f, VisualTheme.accent(), true);
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.STAGE, x, y, size, VisualTheme.accent(), .94f);
⋮----
private void drawRewardIcon(ShapeRenderer shapes, Rectangle r, int index, Color accent) {
⋮----
float size = Math.min(28f, r.height * .24f);
UiIconRenderer.draw(shapes, icon, r.x + 16f, r.y + r.height - size - 14f, size, accent, .88f);
⋮----
private void drawHero() {
font.getData().setScale(UiTypography.scale(UiTypography.Role.DISPLAY));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t("result.complete"), hero.x + 24f, hero.y + hero.height * .73f,
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY));
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, f("result.summary", result.stage(), result.kills(), formatTime(result.secondsSurvived())),
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
font.setColor(VisualTheme.GOLD);
font.draw(batch, f("result.contract", result.contractTitle(), result.contractBonusPercent()),
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(result.threatTier() > 0 ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
font.draw(batch, f("result.threat", result.threatTier(), result.threatBonusPercent()),
⋮----
private void drawMetrics() {
drawMetric(metricsCards[0], t("result.credits"), String.valueOf(result.rewards().credits()), VisualTheme.GOLD);
drawMetric(metricsCards[1], "GEMS", String.valueOf(result.rewards().gems()), VisualTheme.accent());
drawMetric(metricsCards[2], "ACCOUNT XP", String.valueOf(result.rewards().accountXp()), VisualTheme.VIOLET);
⋮----
private void drawMetric(Rectangle r, String label, String value, Color accent) {
⋮----
font.draw(batch, label, r.x + 12f, r.y + r.height - 18f, r.width - 24f, Align.center, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
font.setColor(accent);
font.draw(batch, value, r.x + 12f, r.y + r.height * .43f, r.width - 24f, Align.center, false);
⋮----
private void drawCoaching() {
⋮----
font.setColor(COACHING_ACCENT);
font.draw(batch, t(advice.headlineKey()), coaching.x + 24f, y, coaching.width * .58f, Align.left, false);
⋮----
font.draw(batch, t(advice.detailKey()), coaching.x + 24f, y - 30f, coaching.width * .58f, Align.left, true);
⋮----
Color rarity = VisualTheme.equipmentRarity(result.drop().rarity);
⋮----
font.setColor(rarity);
font.draw(batch, t(result.drop().rarityKey()),
⋮----
font.draw(batch, f("result.drop", t(result.drop().rarityKey()), dropDisplayName(result.drop()), result.drop().level),
⋮----
private void drawActions() {
String[] labels = {"BASE", "RETRY", bonusClaimed ? t("result.doubleClaimed") : t("result.doubleOffer")};
⋮----
font.setColor(i == 2 && bonusClaimed ? VisualTheme.MUTED : i == 0 ? VisualTheme.TEXT_STRONG : VisualTheme.TEXT);
font.draw(batch, labels[i], r.x + 8f, r.y + r.height * .60f, r.width - 16f, Align.center, true);
⋮----
private void handleInput() {
if (!bonusClaimed && Gdx.input.isKeyJustPressed(Input.Keys.D)) { claimDoubleCredits(); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.R)) { game.startRun(); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { game.showMenu(); return; }
if (!Gdx.input.justTouched()) return;
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (actions[0].contains(touch)) game.showMenu();
else if (actions[1].contains(touch)) game.startRun();
else if (actions[2].contains(touch) && !bonusClaimed) claimDoubleCredits();
⋮----
private void claimDoubleCredits() {
game.services.ads.showRewarded(AdsService.Reward.DOUBLE_LOOT, () -> {
⋮----
game.profile.addCurrency(PlayerProfile.Currency.CREDITS, result.rewards().credits());
game.saveProfile();
game.services.ads.preload();
⋮----
private static String formatTime(float seconds) {
int total = Math.max(0, (int) seconds);
return String.format(java.util.Locale.ROOT, "%02d:%02d", total / 60, total % 60);
⋮----
private String localizedName(com.deadlinezero.game.meta.EquipmentItem item) {
⋮----
String key = item.nameKey();
if (key != null) return t(key);
return f("equipment.generatedName", t(item.rarityKey()), t(item.slotKey()));
⋮----
private String dropDisplayName(com.deadlinezero.game.meta.EquipmentItem item) {
⋮----
return key != null ? t(key) : t(item.slotKey());
⋮----
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
⋮----
@Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/screen/SettingsScreen.java
```java
/** Responsive production settings with large touch targets and persistent accessibility controls. */
public final class SettingsScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final BitmapFont font = new BitmapFont();
private final ShapeRenderer shapes = new ShapeRenderer();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
⋮----
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
@Override public void resize(int width, int height) {
viewport.resize(width, height);
metrics = UiLayout.compute(width, height);
layout = MetaLayout.compute(metrics);
Rectangle[] columns = MetaLayout.columns(layout.content(), 3, 18f);
⋮----
Rectangle[] columnRows = MetaLayout.rows(columns[c], ROWS_PER_COLUMN, 8f);
⋮----
@Override public void render(float delta) {
visualTime += Math.max(0f, delta);
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
boolean privacyRequired = game.services.privacy.optionsRequired();
boolean policyAvailable = game.services.privacy.policyAvailable();
String[] labels = labels();
String[] values = values(s, privacyRequired, policyAvailable);
⋮----
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, visualTime);
UiRenderer.topRail(shapes, metrics);
drawSettingsGroupFrames(shapes);
⋮----
boolean disabled = isDisabled(i, privacyRequired, policyAvailable);
Color accent = settingsAccent(i);
UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, accent, i == row, false, disabled);
⋮----
shapes.setColor(0f, 0f, 0f, .22f);
shapes.rect(r.x + 3f, r.y + 3f, r.width - 6f, r.height - 6f);
} else if (isSliderRow(i)) {
float value = sliderValue(s, i);
UiRenderer.segmentedTrack(shapes, r.x + r.width * .56f, r.y + 10f, r.width * .38f, 7f, value, 8, accent);
⋮----
shapes.end();
⋮----
batch.begin();
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * 1.05f);
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, t("shop.back"), layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
layout.back().width - 16f, Align.left, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t("settings.title"), metrics.safeLeft() + 136f, metrics.headerBottom() + 56f,
metrics.contentWidth() - 272f, Align.center, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, t("settings.subtitle"), metrics.safeLeft() + 136f, metrics.headerBottom() + 28f,
⋮----
for (int i = 0; i < rows.length; i++) drawRow(i, rows[i], labels[i], values[i], isDisabled(i, privacyRequired, policyAvailable));
batch.end();
⋮----
handleInput(s, privacyRequired, policyAvailable);
⋮----
private void drawSettingsGroupFrames(ShapeRenderer shapes) {
⋮----
UiRenderer.premiumPanel(shapes, x, y, w, h, accents[group], false);
UiRenderer.sectionPlate(shapes, x + 6f, y + h - 12f, w - 12f, 8f, accents[group], true);
⋮----
private Color settingsAccent(int index) {
⋮----
return index <= 14 ? VisualTheme.GOLD : VisualTheme.accent();
⋮----
private void drawRow(int index, Rectangle r, String label, String value, boolean disabled) {
⋮----
font.setColor(disabled ? VisualTheme.MUTED : index == row ? VisualTheme.accent() : VisualTheme.TEXT_DIM);
font.draw(batch, label, r.x + 14f, r.y + r.height - 17f, r.width - 28f, Align.left, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL) * 1.04f);
font.setColor(disabled ? VisualTheme.MUTED : index == row ? VisualTheme.TEXT_STRONG : VisualTheme.TEXT);
font.draw(batch, value, r.x + 14f, r.y + 23f, r.width - 28f, Align.right, false);
⋮----
private void handleInput(AccessibilitySettings s, boolean privacyRequired, boolean policyAvailable) {
if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { saveAndBack(); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) row = Math.max(0, row - 1);
if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) row = Math.min(LAST_ROW, row + 1);
if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && !isSliderRow(row)) { adjustOrOpen(s, privacyRequired, policyAvailable, -1f); return; }
if ((Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) && !isSliderRow(row)) {
adjustOrOpen(s, privacyRequired, policyAvailable, 1f); return;
⋮----
if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && isSliderRow(row)) { applyAdjustment(s, -1f); persistSettings(s); return; }
if ((Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) && isSliderRow(row)) {
applyAdjustment(s, 1f); persistSettings(s); return;
⋮----
if (!Gdx.input.justTouched()) return;
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (layout.back().contains(touch)) { saveAndBack(); return; }
⋮----
if (!r.contains(touch)) continue;
⋮----
if (row == PRIVACY_ROW) { if (privacyRequired) openPrivacy(); return; }
if (row == POLICY_ROW) { if (policyAvailable) openPolicy(); return; }
if (row == CLOUD_ROW) { openCloud(); return; }
if (row == GRAPHICS_ROW) { GraphicsSettings.set(GraphicsSettings.active().next(1)); GraphicsSettings.save(); selectCue(); return; }
if (row == FRAME_RATE_ROW) { GraphicsSettings.setFrameRate(GraphicsSettings.frameRate().next(1)); GraphicsSettings.save(); selectCue(); return; }
if (isSliderRow(row)) setSliderFromTouch(s, row, touch.x, r);
else applyAdjustment(s, 1f);
persistSettings(s);
⋮----
private void adjustOrOpen(AccessibilitySettings s, boolean privacyRequired, boolean policyAvailable, float dir) {
if (row == PRIVACY_ROW) { if (dir > 0f && privacyRequired) openPrivacy(); return; }
if (row == POLICY_ROW) { if (dir > 0f && policyAvailable) openPolicy(); return; }
if (row == CLOUD_ROW) { if (dir > 0f) openCloud(); return; }
if (row == GRAPHICS_ROW) { GraphicsSettings.set(GraphicsSettings.active().next(dir > 0f ? 1 : -1)); GraphicsSettings.save(); selectCue(); return; }
if (row == FRAME_RATE_ROW) { GraphicsSettings.setFrameRate(GraphicsSettings.frameRate().next(dir > 0f ? 1 : -1)); GraphicsSettings.save(); selectCue(); return; }
applyAdjustment(s, dir);
⋮----
private String[] labels() {
⋮----
t("settings.screenShake"), t("settings.shakeStrength"), t("settings.hitStop"), t("settings.damageFlash"),
t("settings.highContrastTelegraphs"), t("settings.reduceFlashes"), t("settings.haptics"), t("settings.colorVision"),
t("settings.reducedMotion"), t("settings.uiScale"), t("settings.masterVolume"), t("settings.sfxVolume"),
t("settings.musicVolume"), t("settings.graphicsQuality"), t("settings.frameRate"),
t("settings.privacyChoices"), t("settings.privacyPolicy"), t("settings.cloudSave")
⋮----
private String[] values(AccessibilitySettings s, boolean privacyRequired, boolean policyAvailable) {
⋮----
onOff(s.screenShake), pct(s.screenShakeStrength), onOff(s.hitStop), onOff(s.damageFlash),
onOff(s.highContrastTelegraphs), onOff(s.reduceFlashes), onOff(s.haptics), s.colorVisionMode.label,
onOff(s.reducedMotion), pct(s.uiScale), pct(s.masterVolume), pct(s.sfxVolume), pct(s.musicVolume),
GraphicsSettings.active().name(), GraphicsSettings.frameRate().label,
privacyRequired ? t("common.open") : t("common.notRequired"),
policyAvailable ? t("common.open") : t("common.unavailable"),
game.services.cloudSave.available() ? t("common.open") : t("common.notConfigured")
⋮----
private boolean isDisabled(int i, boolean privacyRequired, boolean policyAvailable) {
⋮----
private void openPrivacy() {
selectCue();
game.services.privacy.showOptions(() -> Gdx.app.postRunnable(() -> AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK)));
⋮----
private void openCloud() { selectCue(); game.showCloudSave(); }
private void openPolicy() { selectCue(); game.services.privacy.openPolicy(); }
⋮----
private void setSliderFromTouch(AccessibilitySettings s, int targetRow, float x, Rectangle r) {
⋮----
float value = clamp((x - left) / Math.max(1f, right - left), 0f, 1f);
⋮----
private float sliderValue(AccessibilitySettings s, int targetRow) {
⋮----
private void applyAdjustment(AccessibilitySettings s, float dir) {
⋮----
case 1 -> s.screenShakeStrength = clamp(s.screenShakeStrength + dir * .1f, 0f, 1f);
⋮----
case COLOR_VISION_ROW -> s.colorVisionMode = s.colorVisionMode.next(dir > 0f ? 1 : -1);
case REDUCED_MOTION_ROW -> s.setReducedMotion(!s.reducedMotion);
case UI_SCALE_ROW -> s.uiScale = clamp(s.uiScale + dir * .05f, .85f, 1.35f);
case MASTER_VOLUME_ROW -> s.masterVolume = clamp(s.masterVolume + dir * .05f, 0f, 1f);
case SFX_VOLUME_ROW -> s.sfxVolume = clamp(s.sfxVolume + dir * .05f, 0f, 1f);
case MUSIC_VOLUME_ROW -> s.musicVolume = clamp(s.musicVolume + dir * .05f, 0f, 1f);
⋮----
private void persistSettings(AccessibilitySettings s) {
s.save();
game.audio.setVolumes(s.masterVolume, s.sfxVolume, s.musicVolume);
⋮----
private static boolean isSliderRow(int value) { return value == 1 || value == UI_SCALE_ROW || value == MASTER_VOLUME_ROW || value == SFX_VOLUME_ROW || value == MUSIC_VOLUME_ROW; }
private void saveAndBack() { game.accessibility.save(); AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); game.showMenu(); }
private void selectCue() { AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT); }
private String onOff(boolean value) { return value ? t("common.on") : t("common.off"); }
private String t(String key) { return game.i18n.text(key); }
private static String pct(float value) { return Math.round(value * 100f) + "%"; }
private static float clamp(float v, float min, float max) { return Math.max(min, Math.min(max, v)); }
⋮----
@Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/screen/ShopScreen.java
```java
/** Responsive economy shell with soft, premium, rewarded and Play Billing offers. */
public final class ShopScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final BitmapFont font = new BitmapFont();
private final ShapeRenderer shapes = new ShapeRenderer();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
⋮----
this.status = t("shop.choose");
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
@Override public void resize(int width, int height) {
viewport.resize(width, height);
metrics = UiLayout.compute(width, height);
layout = MetaLayout.compute(metrics);
Rectangle c = layout.content();
Rectangle chestArea = new Rectangle(c.x, c.y + c.height * .38f, c.width, c.height * .62f);
chestCards = MetaLayout.columns(chestArea, 3, 18f);
Rectangle purchaseArea = new Rectangle(c.x, c.y, c.width, c.height * .31f);
purchaseButtons = MetaLayout.columns(purchaseArea, 4, 14f);
⋮----
@Override public void render(float delta) {
visualTime += Math.max(0f, delta);
if (PurchaseGrantService.syncPermanent(game.profile, game.services.billing)) game.saveProfile();
⋮----
game.services.billing.restoreConsumables(this::deliverConsumable);
⋮----
syncBillingStatus();
⋮----
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, visualTime);
UiRenderer.topRail(shapes, metrics);
⋮----
Color chestAccent = i == 0 ? VisualTheme.CYAN_SOFT : i == 1 ? VisualTheme.VIOLET : VisualTheme.positive();
UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, chestAccent, i == 2 && !disabled, false, disabled);
drawChestChrome(shapes, r, i, disabled);
float iconSize = Math.min(52f, r.width * .13f);
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.CHEST,
⋮----
Rectangle button = chestButton(r);
UiRenderer.premiumButton(shapes, button.x, button.y, button.width, button.height, chestAccent,
⋮----
String productId = purchaseProductId(i);
⋮----
boolean enabled = game.services.offers.current().enabled(productId);
boolean featured = game.services.offers.current().featured(productId);
Color offerAccent = i == 0 ? VisualTheme.GOLD : i == 3 ? VisualTheme.CYAN_SOFT : VisualTheme.accent();
UiRenderer.premiumButton(shapes, r.x, r.y, r.width, r.height, offerAccent,
⋮----
drawOfferChrome(shapes, r, i, owned || !enabled, featured);
⋮----
UiIconRenderer.draw(shapes, offerIcon, r.x + 14f, r.y + r.height - 32f, 18f,
⋮----
shapes.end();
⋮----
batch.begin();
drawHeader(p);
drawChestCards(p);
drawPurchaseRow(p);
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, status, layout.footer().x + 20f, layout.footer().y + layout.footer().height * .56f,
layout.footer().width - 40f, Align.center, true);
batch.end();
⋮----
handleInput();
⋮----
private void drawChestChrome(ShapeRenderer shapes, Rectangle r, int index, boolean disabled) {
⋮----
case 2 -> VisualTheme.positive();
⋮----
shapes.setColor(accent.r, accent.g, accent.b, alpha);
shapes.rect(r.x + 5f, r.y + r.height - 7f, Math.max(0f, r.width - 10f), 4f);
shapes.setColor(accent.r, accent.g, accent.b, disabled ? .035f : .065f);
shapes.rect(r.x + 8f, r.y + 8f, Math.max(0f, r.width - 16f), Math.max(0f, r.height - 16f));
⋮----
float emblemRadius = Math.min(r.width, r.height) * .07f;
⋮----
shapes.setColor(accent.r, accent.g, accent.b, disabled ? .10f : .18f);
shapes.circle(cx, cy, emblemRadius * 1.7f, 28);
shapes.setColor(accent.r, accent.g, accent.b, disabled ? .26f : .76f);
shapes.circle(cx, cy, emblemRadius, 24);
shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .92f);
shapes.circle(cx, cy, emblemRadius * .48f, 20);
⋮----
private void drawOfferChrome(ShapeRenderer shapes, Rectangle r, int index,
⋮----
default -> VisualTheme.accent();
⋮----
shapes.setColor(VisualTheme.BORDER.r, VisualTheme.BORDER.g, VisualTheme.BORDER.b, .24f);
shapes.rect(r.x + 6f, r.y + r.height - 5f, Math.max(0f, r.width - 12f), 2f);
⋮----
shapes.setColor(accent.r, accent.g, accent.b, featured ? .88f : .42f);
shapes.rect(r.x + 6f, r.y + r.height - (featured ? 6f : 4f),
Math.max(0f, r.width - 12f), featured ? 4f : 2f);
⋮----
shapes.setColor(accent.r, accent.g, accent.b, .08f);
shapes.rect(r.x + 7f, r.y + 7f, Math.max(0f, r.width - 14f), Math.max(0f, r.height - 14f));
⋮----
private void drawHeader(PlayerProfile p) {
⋮----
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, t("shop.back"), layout.back().x + 10f, layout.back().y + layout.back().height * .56f,
layout.back().width - 16f, Align.left, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t("shop.title"), metrics.safeLeft() + 140f, metrics.headerBottom() + 55f,
metrics.contentWidth() - 520f, Align.left, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
font.setColor(VisualTheme.GOLD);
font.draw(batch, f("shop.credits", p.currency(PlayerProfile.Currency.CREDITS)), metrics.safeRight() - 360f,
metrics.headerBottom() + 49f, 170f, Align.right, false);
font.setColor(VisualTheme.accent());
font.draw(batch, f("shop.gems", p.currency(PlayerProfile.Currency.GEMS)), metrics.safeRight() - 174f,
metrics.headerBottom() + 49f, 160f, Align.right, false);
⋮----
private void drawChestCards(PlayerProfile p) {
String[] titles = {t("shop.field"), t("shop.elite"), t("shop.daily")};
⋮----
f("shop.standardRoll", ChestService.CREDIT_CHEST_COST),
f("shop.bestOf3", ChestService.GEM_CHEST_COST),
p.daily.rewardedChestClaimed ? t("shop.claimedToday") : t("shop.freeRoll")
⋮----
String[] buttons = {t("shop.open1"), t("shop.open2"), t("shop.free3")};
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
font.setColor(i == 0 ? VisualTheme.CYAN_SOFT : i == 1 ? VisualTheme.VIOLET : VisualTheme.positive());
font.draw(batch, titles[i], r.x + 18f, r.y + r.height - 28f, r.width - 36f, Align.left, false);
⋮----
font.draw(batch, descriptions[i], r.x + 26f, r.y + r.height * .50f, r.width - 52f, Align.center, true);
Rectangle b = chestButton(r);
⋮----
font.setColor(i == 2 && p.daily.rewardedChestClaimed ? VisualTheme.MUTED : VisualTheme.TEXT_STRONG);
font.draw(batch, buttons[i], b.x + 8f, b.y + b.height * .61f, b.width - 16f, Align.center, false);
⋮----
private void drawPurchaseRow(PlayerProfile p) {
⋮----
t("shop.starterPack"),
t("shop.gemPacks") + " • S",
t("shop.gemPacks") + " • L",
f("shop.removeAds", p.removeAdsPurchased ? t("shop.owned") : "")
⋮----
font.setColor(owned || !enabled ? VisualTheme.MUTED : i == 0 ? VisualTheme.GOLD : i == 3 ? VisualTheme.CYAN_SOFT : VisualTheme.TEXT_STRONG);
font.draw(batch, labels[i], r.x + 28f, r.y + r.height * .66f, r.width - 56f, Align.left, true);
⋮----
font.setColor(owned || !enabled ? VisualTheme.MUTED : VisualTheme.TEXT_DIM);
String sublabel = owned ? t("shop.owned") : !enabled ? t("shop.billingUnavailable") : t("shop.playBilling");
font.draw(batch, sublabel, r.x + 28f, r.y + 22f, r.width - 56f, Align.left, false);
⋮----
private Rectangle chestButton(Rectangle card) {
return new Rectangle(card.x + 24f, card.y + 22f, card.width - 48f, Math.max(60f, card.height * .22f));
⋮----
private void handleInput() {
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) open(false);
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) open(true);
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) openRewarded();
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) purchase(BillingService.STARTER_PACK);
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) purchase(BillingService.GEMS_SMALL);
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) purchase(BillingService.GEMS_LARGE);
if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) purchase(BillingService.REMOVE_ADS);
if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) { game.showMenu(); return; }
if (!Gdx.input.justTouched()) return;
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (layout.back().contains(touch)) { game.showMenu(); return; }
⋮----
if (!chestCards[i].contains(touch)) continue;
if (i == 0) open(false);
else if (i == 1) open(true);
else openRewarded();
⋮----
if (!purchaseButtons[i].contains(touch)) continue;
⋮----
case 0 -> purchase(BillingService.STARTER_PACK);
case 1 -> purchase(BillingService.GEMS_SMALL);
case 2 -> purchase(BillingService.GEMS_LARGE);
case 3 -> purchase(BillingService.REMOVE_ADS);
⋮----
private void syncBillingStatus() {
BillingService.State state = game.services.billing.state();
⋮----
String product = game.services.billing.activeProductId();
status = product.isBlank() ? t("shop.paymentPending") : f("shop.paymentPendingProduct", product);
} else if (state == BillingService.State.CONNECTING) status = t("shop.connecting");
else if (state == BillingService.State.UNAVAILABLE) status = t("shop.billingUnavailable");
else if (state == BillingService.State.PURCHASE_IN_PROGRESS) status = t("shop.purchaseConfirm");
⋮----
private void open(boolean premium) {
EquipmentItem item = premium ? ChestService.openGemChest(game.profile) : ChestService.openCreditChest(game.profile);
if (item == null) { status = game.profile.inventory.full() ? t("shop.inventoryFull") : t("shop.notEnough"); return; }
status = f("shop.obtained", item.name, item.level);
game.saveProfile();
⋮----
private void openRewarded() {
if (game.profile.daily.rewardedChestClaimed) { status = t("shop.dailyClaimed"); return; }
if (game.profile.inventory.full()) { status = t("shop.inventoryFull"); return; }
status = t("shop.loadingReward");
game.services.ads.showRewarded(AdsService.Reward.BONUS_CHEST, () -> {
if (game.profile.daily.rewardedChestClaimed || game.profile.inventory.full()) return;
EquipmentItem item = EquipmentDropTable.roll(game.profile.selectedStage, false);
game.profile.inventory.add(item);
⋮----
status = f("shop.freeCrate", item.name, item.level);
⋮----
game.services.ads.preload();
}, () -> status = t("shop.rewardUnavailable"));
⋮----
private void purchase(String productId) {
if (!game.services.offers.current().enabled(productId)) { status = t("shop.billingUnavailable"); return; }
if (BillingService.REMOVE_ADS.equals(productId) && game.profile.removeAdsPurchased) { status = t("shop.adFreeOwned"); return; }
if (BillingService.STARTER_PACK.equals(productId) && game.profile.starterPackGranted) { status = t("shop.starterClaimed"); return; }
if (game.services.billing.state() == BillingService.State.PURCHASE_PENDING) { status = t("shop.paymentAlreadyPending"); return; }
status = t("shop.openingPurchase");
if (BillingService.isConsumable(productId)) {
game.services.billing.purchaseWithReceipt(productId, this::deliverConsumable, () -> status = t("shop.purchaseCancelled"));
⋮----
game.services.billing.purchase(productId, () -> {
boolean granted = PurchaseGrantService.grant(game.profile, productId);
if (granted) { game.saveProfile(); status = t("shop.purchaseDelivered"); }
else status = t("shop.purchaseAlreadyDelivered");
}, () -> status = t("shop.purchaseCancelled"));
⋮----
private String purchaseProductId(int index) {
⋮----
private void deliverConsumable(BillingService.PurchaseReceipt receipt) {
ConsumablePurchaseDelivery.deliver(game.profile, game.services.billing, receipt, game::saveProfile,
granted -> status = granted ? t("shop.purchaseDelivered") : t("shop.recoveredFinalized"),
() -> status = t("shop.purchaseFinalizationPending"));
⋮----
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
⋮----
@Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/screen/SurvivorLayoutModel.java
```java
/** Pure responsive layout for survivor browsing and explicit selection actions. */
public final class SurvivorLayoutModel {
⋮----
public static Layout layout(UiLayout.Metrics m) {
Rectangle back = new Rectangle(m.safeLeft(), m.headerBottom(), 132f, m.safeTop() - m.headerBottom());
Rectangle card = new Rectangle(m.safeLeft(), m.contentBottom(), m.contentWidth(), m.contentHeight());
float navSize = Math.max(m.touchTarget(), 64f);
Rectangle previous = new Rectangle(card.x + 12f, card.y + (card.height - navSize) * .5f, navSize, navSize);
Rectangle next = new Rectangle(card.x + card.width - navSize - 12f, previous.y, navSize, navSize);
⋮----
float leftW = card.width * (m.wide() ? .48f : .46f);
Rectangle portrait = new Rectangle(
⋮----
Math.max(260f, leftW - navSize - 36f),
Math.max(240f, card.height - 108f)
⋮----
float rightW = Math.max(320f, rightEdge - rightX);
Rectangle cta = new Rectangle(rightX, card.y + 24f, rightW, 68f);
Rectangle xpBar = new Rectangle(rightX, cta.y + cta.height + 22f, rightW, 16f);
⋮----
Rectangle stats = new Rectangle(rightX, statsY, rightW, Math.max(150f, card.y + card.height - 42f - statsY));
⋮----
return new Layout(back, card, portrait, stats, xpBar, previous, next, cta);
```

## File: src/main/java/com/deadlinezero/game/screen/SurvivorScreen.java
```java
/** Responsive survivor roster with authored-art focus and explicit mobile actions. */
public final class SurvivorScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final ShapeRenderer shapes = new ShapeRenderer();
private final BitmapFont font = new BitmapFont();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
⋮----
SurvivorCatalog.Survivor[] values = SurvivorCatalog.Survivor.values();
⋮----
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
@Override public void resize(int width, int height) {
viewport.resize(width, height);
metrics = UiLayout.compute(width, height);
layout = SurvivorLayoutModel.layout(metrics);
⋮----
@Override public void render(float delta) {
artTime += Math.max(0f, delta);
handleInput();
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
⋮----
SurvivorCatalog.Survivor survivor = SurvivorCatalog.Survivor.values()[index];
boolean unlocked = game.profile.survivors.unlocked(survivor);
int level = game.profile.survivors.level(survivor);
long xp = game.profile.survivors.xp(survivor);
long next = game.profile.survivors.xpForNext(survivor);
float progress = next <= 0 ? 1f : Math.min(1f, xp / (float) next);
⋮----
drawShapes(unlocked, progress);
drawContent(survivor, unlocked, level, xp, next);
⋮----
private void drawShapes(boolean unlocked, float progress) {
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, artTime);
UiRenderer.topRail(shapes, metrics);
UiRenderer.premiumCard(shapes, layout.card().x, layout.card().y, layout.card().width, layout.card().height,
unlocked ? VisualTheme.accent() : VisualTheme.MUTED, true, false, !unlocked);
UiRenderer.premiumPanel(shapes, layout.portrait().x, layout.portrait().y, layout.portrait().width, layout.portrait().height,
unlocked ? VisualTheme.accent() : VisualTheme.MUTED, true);
UiRenderer.premiumPanel(shapes, layout.stats().x, layout.stats().y, layout.stats().width, layout.stats().height,
⋮----
drawSurvivorChrome(shapes, unlocked);
UiRenderer.progress(shapes, layout.xpBar().x, layout.xpBar().y, layout.xpBar().width, layout.xpBar().height,
⋮----
UiRenderer.premiumButton(shapes, layout.previous().x, layout.previous().y, layout.previous().width, layout.previous().height,
⋮----
UiRenderer.premiumButton(shapes, layout.next().x, layout.next().y, layout.next().width, layout.next().height,
⋮----
drawChevron(layout.previous(), false);
drawChevron(layout.next(), true);
⋮----
else if (game.profile.selectedSurvivor == SurvivorCatalog.Survivor.values()[index]) ctaState = UiRenderer.ButtonState.SELECTED;
⋮----
UiRenderer.premiumButton(shapes, layout.cta().x, layout.cta().y, layout.cta().width, layout.cta().height,
game.profile.selectedSurvivor == SurvivorCatalog.Survivor.values()[index] ? VisualTheme.positive() : VisualTheme.GOLD, ctaState);
⋮----
if (!game.art.authoredAvailable()) {
Rectangle p = layout.portrait();
shapes.setColor(unlocked ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
shapes.circle(p.x + p.width * .5f, p.y + p.height * .52f, Math.min(p.width, p.height) * .17f, 36);
⋮----
shapes.end();
⋮----
private void drawSurvivorChrome(ShapeRenderer shapes, boolean unlocked) {
⋮----
Color accent = unlocked ? VisualTheme.accent() : VisualTheme.MUTED;
⋮----
Rectangle card = layout.card();
shapes.setColor(accent.r, accent.g, accent.b, unlocked ? .56f : .18f);
shapes.rect(card.x + 6f, card.y + card.height - 5f, Math.max(0f, card.width - 12f), 3f);
⋮----
Rectangle portrait = layout.portrait();
shapes.setColor(accent.r, accent.g, accent.b, unlocked ? .085f : .035f);
shapes.rect(portrait.x + 8f, portrait.y + 8f, Math.max(0f, portrait.width - 16f), Math.max(0f, portrait.height - 16f));
shapes.setColor(accent.r, accent.g, accent.b, unlocked ? .72f : .24f);
shapes.rect(portrait.x + 8f, portrait.y + portrait.height - 5f, Math.max(0f, portrait.width - 16f), 3f);
⋮----
shapes.setColor(accent.r, accent.g, accent.b, unlocked ? .10f : .035f);
shapes.ellipse(cx - portrait.width * .22f, baseY - 20f, portrait.width * .44f, 40f);
shapes.setColor(accent.r, accent.g, accent.b, unlocked ? .24f : .08f);
shapes.rect(cx - portrait.width * .13f, baseY, portrait.width * .26f, 2f);
shapes.setColor(accent.r, accent.g, accent.b, unlocked ? .035f : .015f);
shapes.triangle(cx - portrait.width * .28f, portrait.y + portrait.height - 20f,
⋮----
Rectangle stats = layout.stats();
shapes.setColor(selected ? VisualTheme.positive().r : accent.r,
selected ? VisualTheme.positive().g : accent.g,
selected ? VisualTheme.positive().b : accent.b,
⋮----
shapes.rect(stats.x + 7f, stats.y + stats.height - 4f, Math.max(0f, stats.width - 14f), 3f);
⋮----
Rectangle cta = layout.cta();
⋮----
shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, .10f);
shapes.rect(cta.x + 6f, cta.y + 6f, Math.max(0f, cta.width - 12f), Math.max(0f, cta.height - 12f));
shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, .92f);
shapes.rect(cta.x + 10f, cta.y + cta.height - 4f, Math.max(0f, cta.width - 20f), 3f);
⋮----
private void drawChevron(Rectangle bounds, boolean right) {
⋮----
float half = Math.min(bounds.width, bounds.height) * .15f;
⋮----
float stroke = Math.max(3f, half * .30f);
shapes.setColor(VisualTheme.TEXT_STRONG);
shapes.rectLine(tail, cy + half, tip, cy, stroke);
shapes.rectLine(tip, cy, tail, cy - half, stroke);
⋮----
private void drawContent(SurvivorCatalog.Survivor survivor, boolean unlocked, int level, long xp, long next) {
batch.begin();
drawHeader();
drawPortrait(survivor, unlocked);
drawStats(survivor, unlocked, level, xp, next);
drawCta(survivor, unlocked);
batch.end();
⋮----
private void drawHeader() {
Rectangle back = layout.back();
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, t("shop.back"), back.x + 12f, back.y + back.height * .56f, back.width - 20f, Align.left, false);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.TITLE));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, t("survivor.title"), metrics.safeLeft(), metrics.headerBottom() + 53f,
metrics.contentWidth(), Align.center, false);
⋮----
if (!status.isEmpty()) {
⋮----
font.setColor(VisualTheme.accent());
font.draw(batch, status, metrics.safeRight() - 360f, metrics.headerBottom() + 46f,
⋮----
private void drawPortrait(SurvivorCatalog.Survivor survivor, boolean unlocked) {
⋮----
if (game.art.authoredAvailable()) {
TextureRegion portrait = game.art.survivor(survivor, GameArt.Motion.IDLE, artTime);
float aspect = portrait.getRegionWidth() / (float) Math.max(1, portrait.getRegionHeight());
⋮----
drawH = drawW / Math.max(.01f, aspect);
⋮----
float py = p.y + Math.max(18f, (p.height - drawH) * .18f);
if (unlocked) batch.setColor(Color.WHITE);
else batch.setColor(.38f, .42f, .46f, 1f);
batch.draw(portrait, px, py, drawW, drawH);
batch.setColor(Color.WHITE);
⋮----
font.setColor(unlocked ? VisualTheme.CYAN_SOFT : VisualTheme.MUTED);
font.draw(batch, unlocked ? "ACTIVE OPERATIVE" : "LOCKED OPERATIVE",
⋮----
private void drawStats(SurvivorCatalog.Survivor survivor, boolean unlocked, int level, long xp, long next) {
Rectangle s = layout.stats();
⋮----
font.setColor(unlocked ? VisualTheme.TEXT_STRONG : VisualTheme.MUTED);
font.draw(batch, t(survivor.displayNameKey()).toUpperCase(), x, s.y + s.height - 30f, w, Align.left, false);
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY));
⋮----
font.draw(batch, f("survivor.roleLevel", t(survivor.roleKey()).toUpperCase(java.util.Locale.ROOT), level),
⋮----
drawMetric("HP", "x" + fmt(survivor.hpMultiplier), x, metricTop, colW,
survivor.hpMultiplier >= 1f ? VisualTheme.positive() : VisualTheme.TEXT_STRONG);
drawMetric("DAMAGE", "x" + fmt(survivor.weaponMultiplier), x + colW, metricTop, colW,
⋮----
drawMetric("SPEED", "x" + fmt(survivor.speedMultiplier), x, metricTop - 58f, colW,
⋮----
drawMetric("CRIT", "+" + Math.round(survivor.critBonus * 100f) + "%", x + colW, metricTop - 58f, colW,
⋮----
drawMetric("ABILITY", "+" + Math.round(survivor.abilityBonus * 100f) + "%", x, metricTop - 116f, colW,
survivor.abilityBonus > 0f ? VisualTheme.accent() : VisualTheme.TEXT_STRONG);
⋮----
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, f("survivor.xp", xp, next), layout.xpBar().x, layout.xpBar().y + 36f,
layout.xpBar().width, Align.left, false);
⋮----
private void drawMetric(String label, String value, float x, float y, float width, Color valueColor) {
⋮----
font.draw(batch, label, x, y, width, Align.left, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.METRIC));
font.setColor(valueColor);
font.draw(batch, value, x, y - 25f, width, Align.left, false);
⋮----
private void drawCta(SurvivorCatalog.Survivor survivor, boolean unlocked) {
Rectangle c = layout.cta();
⋮----
if (!unlocked) label = unlockText(survivor);
else if (game.profile.selectedSurvivor == survivor) label = t("survivor.selected");
else label = t("survivor.select");
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
⋮----
font.draw(batch, label, c.x + 12f, c.y + c.height * .62f, c.width - 24f, Align.center, false);
⋮----
private void handleInput() {
⋮----
if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
backCue();
game.showMenu();
⋮----
if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) { move(-1, values); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) { move(1, values); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
select(values[index]);
⋮----
if (!Gdx.input.justTouched()) return;
⋮----
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (layout.back().contains(touch)) { backCue(); game.showMenu(); return; }
if (layout.previous().contains(touch)) { move(-1, values); return; }
if (layout.next().contains(touch)) { move(1, values); return; }
if (layout.cta().contains(touch)) select(values[index]);
⋮----
private void move(int delta, SurvivorCatalog.Survivor[] values) {
⋮----
AudioDirector.playGlobal(AudioDirector.Cue.UI_SELECT);
⋮----
private void select(SurvivorCatalog.Survivor survivor) {
if (!game.profile.selectSurvivor(survivor)) {
status = t("survivor.locked");
AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK);
⋮----
status = f("survivor.selectedStatus", t(survivor.displayNameKey()));
game.saveProfile();
⋮----
private void backCue() { AudioDirector.playGlobal(AudioDirector.Cue.UI_BACK); }
⋮----
private String unlockText(SurvivorCatalog.Survivor survivor) {
⋮----
case REX -> t("survivor.default");
case NYX -> t("survivor.unlockNyx");
case BASTION -> t("survivor.unlockBastion");
case VOLT -> t("survivor.unlockVolt");
case WRAITH -> t("survivor.unlockWraith");
⋮----
private String fmt(float value) { return String.format(java.util.Locale.ROOT, "%.2f", value); }
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
⋮----
@Override public void dispose() { batch.dispose(); shapes.dispose(); font.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/screen/VictoryScreen.java
```java
public final class VictoryScreen extends ScreenAdapter {
⋮----
private final SpriteBatch batch = new SpriteBatch();
private final BitmapFont font = new BitmapFont();
private final ShapeRenderer shapes = new ShapeRenderer();
private final UiViewport viewport = new UiViewport();
private final Vector2 touch = new Vector2();
⋮----
resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
⋮----
if (ReviewPromptPolicy.eligible(firstClear, result.stage(), reviewAttempted)) {
⋮----
game.saveProfile();
game.services.review.requestReview();
⋮----
@Override public void resize(int width, int height) {
viewport.resize(width, height);
metrics = UiLayout.compute(width, height);
layout = MetaLayout.compute(metrics);
Rectangle c = layout.content();
hero = new Rectangle(c.x, c.y + c.height * .60f, c.width, c.height * .40f);
Rectangle rewards = new Rectangle(c.x, c.y + c.height * .34f, c.width, c.height * .20f);
rewardCards = MetaLayout.columns(rewards, 3, 16f);
noticePanel = new Rectangle(c.x, c.y, c.width, c.height * .28f);
actions = MetaLayout.actions(layout.footer(), 3, 14f);
⋮----
@Override public void render(float delta) {
visualTime += Math.max(0f, delta);
Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
viewport.apply(batch, shapes);
boolean canShare = game.services.share.available();
⋮----
shapes.begin(ShapeRenderer.ShapeType.Filled);
UiRenderer.background(shapes, metrics, visualTime);
UiRenderer.premiumPanel(shapes, hero.x, hero.y, hero.width, hero.height, VisualTheme.GOLD, true);
drawCelebrationBackdrop(shapes);
⋮----
Color rewardAccent = i == 0 ? VisualTheme.GOLD : i == 1 ? VisualTheme.accent() : VisualTheme.VIOLET;
UiRenderer.premiumCard(shapes, r.x, r.y, r.width, r.height, rewardAccent, false, i == 0, false);
drawRewardIcon(shapes, r, i, rewardAccent);
⋮----
Color noticeAccent = result.unlockedThreatTier() > 0 ? VisualTheme.GOLD : firstClear ? VisualTheme.positive() : VisualTheme.VIOLET;
UiRenderer.premiumPanel(shapes, noticePanel.x, noticePanel.y, noticePanel.width, noticePanel.height, noticeAccent, false);
drawNoticeShowcase(shapes);
drawVictoryChrome(shapes);
UiRenderer.premiumButton(shapes, actions[0].x, actions[0].y, actions[0].width, actions[0].height, VisualTheme.CYAN_SOFT, UiRenderer.ButtonState.NORMAL);
UiRenderer.premiumButton(shapes, actions[1].x, actions[1].y, actions[1].width, actions[1].height, VisualTheme.VIOLET,
⋮----
UiRenderer.premiumButton(shapes, actions[2].x, actions[2].y, actions[2].width, actions[2].height, VisualTheme.GOLD, UiRenderer.ButtonState.SELECTED);
shapes.end();
⋮----
batch.begin();
drawHero();
drawRewards();
drawNotice();
drawActions(canShare);
batch.end();
⋮----
handleInput(canShare);
⋮----
private void drawCelebrationBackdrop(ShapeRenderer shapes) {
⋮----
float pulse = .5f + .5f * (float)Math.sin(visualTime * 1.8f);
float outer = Math.min(hero.width * .18f, hero.height * .72f);
⋮----
float a0 = (float)Math.toRadians(i * 36f - 5f);
float a1 = (float)Math.toRadians(i * 36f + 5f);
⋮----
shapes.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, .035f + pulse * .018f);
shapes.triangle(
cx + (float)Math.cos(a0) * inner, cy + (float)Math.sin(a0) * inner,
cx + (float)Math.cos(a1) * inner, cy + (float)Math.sin(a1) * inner,
cx + (float)Math.cos((a0 + a1) * .5f) * outer, cy + (float)Math.sin((a0 + a1) * .5f) * outer);
⋮----
float trophySize = Math.min(54f, hero.height * .30f);
⋮----
UiRenderer.iconBadge(shapes, tx - 8f, ty - 8f, trophySize + 16f, VisualTheme.GOLD, true);
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.TROPHY, tx, ty, trophySize, VisualTheme.GOLD, .96f);
⋮----
private void drawRewardIcon(ShapeRenderer shapes, Rectangle r, int index, Color accent) {
⋮----
float size = Math.min(28f, r.height * .26f);
UiIconRenderer.draw(shapes, icon, r.x + 16f, r.y + r.height - size - 14f, size, accent, .92f);
⋮----
private void drawNoticeShowcase(ShapeRenderer shapes) {
if (result.drop() == null) return;
Color rarity = VisualTheme.equipmentRarity(result.drop().rarity);
⋮----
UiRenderer.premiumCard(shapes, x, y, cardW, cardH, rarity, true, false, false);
float badge = Math.min(58f, cardH * .54f);
UiRenderer.iconBadge(shapes, x + 14f, y + cardH * .5f - badge * .5f, badge, rarity, true);
UiIconRenderer.draw(shapes, UiIconRenderer.Icon.GEAR,
⋮----
shapes.setColor(rarity.r, rarity.g, rarity.b, .88f);
shapes.rect(x + 7f, y + cardH - 4f, Math.max(0f, cardW - 14f), 3f);
⋮----
private void drawVictoryChrome(ShapeRenderer shapes) {
float pulse = .72f + .18f * (float)Math.sin(visualTime * 2.2f);
shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, .14f);
shapes.rect(hero.x + 8f, hero.y + 8f, Math.max(0f, hero.width - 16f), Math.max(0f, hero.height - 16f));
shapes.setColor(VisualTheme.positive().r, VisualTheme.positive().g, VisualTheme.positive().b, pulse);
shapes.rect(hero.x + 10f, hero.y + hero.height - 6f, Math.max(0f, hero.width - 20f), 4f);
⋮----
Color[] rewardAccents = {VisualTheme.GOLD, VisualTheme.accent(), VisualTheme.VIOLET};
⋮----
shapes.setColor(accent.r, accent.g, accent.b, .68f);
shapes.rect(r.x + 6f, r.y + r.height - 4f, Math.max(0f, r.width - 12f), 3f);
shapes.setColor(accent.r, accent.g, accent.b, .055f);
shapes.rect(r.x + 7f, r.y + 7f, Math.max(0f, r.width - 14f), Math.max(0f, r.height - 14f));
⋮----
Color noticeAccent = result.unlockedThreatTier() > 0 ? VisualTheme.GOLD
: firstClear ? VisualTheme.positive() : VisualTheme.VIOLET;
shapes.setColor(noticeAccent.r, noticeAccent.g, noticeAccent.b, .56f);
shapes.rect(noticePanel.x + 7f, noticePanel.y + noticePanel.height - 4f,
Math.max(0f, noticePanel.width - 14f), 3f);
⋮----
shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .10f);
shapes.rect(next.x + 6f, next.y + 6f, Math.max(0f, next.width - 12f), Math.max(0f, next.height - 12f));
shapes.setColor(VisualTheme.accent().r, VisualTheme.accent().g, VisualTheme.accent().b, .88f);
shapes.rect(next.x + 10f, next.y + next.height - 4f, Math.max(0f, next.width - 20f), 3f);
⋮----
private void drawHero() {
font.getData().setScale(UiTypography.scale(UiTypography.Role.DISPLAY));
font.setColor(VisualTheme.positive());
font.draw(batch, t("victory.title"), hero.x + 20f, hero.y + hero.height * .84f,
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY));
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, f("result.summary", result.stage(), result.kills(), formatTime(result.secondsSurvived())),
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION));
font.setColor(VisualTheme.GOLD);
font.draw(batch, f("result.contract", result.contractTitle(), result.contractBonusPercent()),
⋮----
font.setColor(result.threatTier() > 0 ? VisualTheme.GOLD : VisualTheme.TEXT_DIM);
font.draw(batch, f("result.threat", result.threatTier(), result.threatBonusPercent()),
⋮----
private void drawRewards() {
drawMetric(rewardCards[0], "CREDITS", String.valueOf(result.rewards().credits()), VisualTheme.GOLD);
drawMetric(rewardCards[1], "GEMS", String.valueOf(result.rewards().gems()), VisualTheme.accent());
drawMetric(rewardCards[2], "ACCOUNT XP", String.valueOf(result.rewards().accountXp()), VisualTheme.VIOLET);
⋮----
private void drawMetric(Rectangle r, String label, String value, Color accent) {
⋮----
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, label, r.x + 10f, r.y + r.height - 18f, r.width - 20f, Align.center, false);
font.getData().setScale(UiTypography.scale(UiTypography.Role.SECTION));
font.setColor(accent);
font.draw(batch, value, r.x + 10f, r.y + r.height * .43f, r.width - 20f, Align.center, false);
⋮----
private void drawNotice() {
⋮----
if (result.unlockedThreatTier() > 0) {
⋮----
String milestone = result.threatMilestoneGems() > 0 ? f("victory.milestone", result.threatMilestoneGems()) : "";
font.draw(batch, f("victory.threatUnlocked", result.unlockedThreatTier(), milestone), left, top, width, Align.center, true);
EquipmentItem exclusive = ThreatMilestoneRewardCatalog.forTier(result.unlockedThreatTier());
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.LABEL));
font.setColor(Color.MAGENTA);
font.draw(batch, f("victory.mythic", localizedName(exclusive).toUpperCase(java.util.Locale.ROOT)),
⋮----
font.draw(batch, f("victory.firstClear", bonusCredits, bonusGems), left, top, width, Align.center, false);
⋮----
float masteryWidth = result.drop() == null ? width : noticePanel.width * .60f;
drawMasteryNotice(left, noticePanel.y + noticePanel.height * .43f, masteryWidth);
if (result.drop() != null) {
⋮----
font.setColor(rarity);
font.draw(batch, t(result.drop().rarityKey()),
⋮----
font.draw(batch, f("result.drop", t(result.drop().rarityKey()), dropDisplayName(result.drop()), result.drop().level),
⋮----
private void drawActions(boolean canShare) {
String[] labels = {t("victory.base"), canShare ? t("victory.share") : t("victory.shareDisabled"), t("victory.nextStage")};
⋮----
font.setColor(i == 1 && !canShare ? VisualTheme.MUTED : i == 2 ? VisualTheme.TEXT_STRONG : VisualTheme.TEXT);
font.draw(batch, labels[i], r.x + 8f, r.y + r.height * .60f, r.width - 16f, Align.center, false);
⋮----
private void handleInput(boolean canShare) {
if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) { game.showMenu(); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.R) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { game.startRun(); return; }
if (Gdx.input.isKeyJustPressed(Input.Keys.H)) { share(); return; }
if (!Gdx.input.justTouched()) return;
viewport.unproject(Gdx.input.getX(), Gdx.input.getY(), touch);
if (actions[0].contains(touch)) game.showMenu();
else if (actions[1].contains(touch) && canShare) share();
else if (actions[2].contains(touch)) game.startRun();
⋮----
private void share() {
if (!game.services.share.available()) return;
game.services.share.shareText(RunShareText.format(result, game.i18n));
⋮----
private void drawMasteryNotice(float x, float y, float width) {
MasteryRunNotice.Notice notice = MasteryRunNotice.current();
if (notice == null || !notice.visible()) return;
StringBuilder detail = new StringBuilder();
if (notice.weaponRankedUp()) detail.append(f("victory.masteryWeapon",
localizedNoticeName(notice.weaponNameKey(), notice.weaponName()).toUpperCase(java.util.Locale.ROOT), notice.weaponRank()));
if (notice.biomeRankedUp()) {
if (notice.weaponRankedUp()) detail.append("  •  ");
detail.append(f("victory.masteryBiome", localizedNoticeName(notice.biomeNameKey(), notice.biomeName()), notice.biomeRank()));
⋮----
String text = f("victory.mastery", detail.toString(), notice.creditsReward(), notice.gemsReward());
⋮----
font.setColor(new Color(.72f, .58f, 1f, 1f));
font.draw(batch, text, x, y, width, Align.center, true);
⋮----
private static String formatTime(float seconds) {
int total = Math.max(0, (int) seconds);
return String.format(java.util.Locale.ROOT, "%02d:%02d", total / 60, total % 60);
⋮----
private String localizedNoticeName(String key, String fallback) { return key == null || key.isBlank() ? fallback : t(key); }
private String localizedName(com.deadlinezero.game.meta.EquipmentItem item) {
⋮----
String key = item.nameKey();
if (key != null) return t(key);
return f("equipment.generatedName", t(item.rarityKey()), t(item.slotKey()));
⋮----
private String dropDisplayName(com.deadlinezero.game.meta.EquipmentItem item) {
⋮----
return key != null ? t(key) : t(item.slotKey());
⋮----
private String t(String key) { return game.i18n.text(key); }
private String f(String key, Object... args) { return game.i18n.format(key, args); }
⋮----
@Override public void dispose() { batch.dispose(); font.dispose(); shapes.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/services/AdsService.java
```java
public interface AdsService {
⋮----
interface FullscreenListener {
void onOpening();
void onClosed();
⋮----
boolean isRewardedReady();
void showRewarded(Reward reward, Runnable onEarned, Runnable onUnavailable);
default void preload() {}
default void setFullscreenListener(FullscreenListener listener) {}
```

## File: src/main/java/com/deadlinezero/game/services/BillingService.java
```java
public interface BillingService {
⋮----
Set<String> PRODUCTS = Set.of(REMOVE_ADS, STARTER_PACK, GEMS_SMALL, GEMS_LARGE);
⋮----
interface PurchaseReceiptListener {
void onPurchased(PurchaseReceipt receipt);
⋮----
void initialize();
boolean owns(String productId);
void purchase(String productId, Runnable onSuccess, Runnable onFailure);
void restore();
⋮----
/** Current store lifecycle state. Implementations without a platform store remain READY/no-op. */
default State state() { return State.READY; }
⋮----
/** True only when owns() reflects a completed authoritative store query. */
default boolean authoritativeEntitlements() { return false; }
⋮----
/** Product associated with an active or pending purchase, or an empty string when none exists. */
default String activeProductId() { return ""; }
⋮----
/**
     * Receipt-aware purchase path used by consumables so the profile can persist an idempotency key
     * before Google Play consumption. Implementations that do not support receipts fall back to the
     * legacy purchase contract, which keeps desktop/no-op implementations source compatible.
     */
default void purchaseWithReceipt(String productId, PurchaseReceiptListener onSuccess, Runnable onFailure) {
purchase(productId, () -> onSuccess.onPurchased(new PurchaseReceipt(productId, "")), onFailure);
⋮----
/** Replays purchased but not yet consumed items. */
default void restoreConsumables(PurchaseReceiptListener listener) {}
⋮----
/** Consumes a previously delivered Play purchase only after the profile grant has been persisted. */
default void finishConsumable(String receiptId, Runnable onSuccess, Runnable onFailure) { onSuccess.run(); }
⋮----
static boolean isKnownProduct(String productId) {
return productId != null && PRODUCTS.contains(productId);
⋮----
static boolean isConsumable(String productId) {
return GEMS_SMALL.equals(productId) || GEMS_LARGE.equals(productId);
⋮----
static boolean isDurable(String productId) {
return REMOVE_ADS.equals(productId) || STARTER_PACK.equals(productId);
```

## File: src/main/java/com/deadlinezero/game/services/CloudAuthenticationRequiredException.java
```java
/** Signals that a configured cloud provider requires an interactive account sign-in. */
public final class CloudAuthenticationRequiredException extends Exception {
```

## File: src/main/java/com/deadlinezero/game/services/CloudProviderConflictException.java
```java
/** Signals that the cloud provider itself has two unresolved snapshot versions. */
public final class CloudProviderConflictException extends Exception {
⋮----
if (conflict == null) throw new IllegalArgumentException("conflict");
⋮----
public CloudSaveAdapter.ProviderConflict conflict() { return conflict; }
```

## File: src/main/java/com/deadlinezero/game/services/CloudRemoteChangedException.java
```java
/** Signals that the remote cloud snapshot changed after the user inspected it. */
public final class CloudRemoteChangedException extends Exception {
```

## File: src/main/java/com/deadlinezero/game/services/CloudSaveAdapter.java
```java
/** Provider-neutral cloud persistence boundary. Platform modules may bind Google Play Games or another backend. */
public interface CloudSaveAdapter {
⋮----
default boolean available() { return true; }
default boolean supportsAuthentication() { return false; }
default void authenticate() throws Exception { }
RemoteBackup read() throws Exception;
void write(String payload) throws Exception;
default void writeIfUnchanged(String payload, RemoteBackup expectedRemote) throws Exception {
throw new UnsupportedOperationException("Cloud provider does not support conditional writes");
⋮----
default ProviderConflict pendingConflict() { return null; }
default void resolvePendingConflict(ConflictChoice choice) throws Exception {
throw new IllegalStateException("Cloud provider does not expose a resolvable conflict");
⋮----
static CloudSaveAdapter unavailable() {
return new CloudSaveAdapter() {
@Override public boolean available() { return false; }
@Override public RemoteBackup read() { return null; }
@Override public void write(String payload) {
throw new IllegalStateException("Cloud save provider is unavailable");
```

## File: src/main/java/com/deadlinezero/game/services/CloudSaveService.java
```java
/** Explicit cloud backup operations. Conflict policy stays user/UX controlled instead of silently overwriting progress. */
public final class CloudSaveService {
⋮----
this.adapter = adapter == null ? CloudSaveAdapter.unavailable() : adapter;
⋮----
public boolean available() { return adapter.available(); }
public boolean supportsAuthentication() { return adapter.supportsAuthentication(); }
public void authenticate() throws Exception { adapter.authenticate(); }
public CloudSaveAdapter.ProviderConflict pendingProviderConflict() { return adapter.pendingConflict(); }
⋮----
public void resolveProviderConflict(CloudSaveAdapter.ConflictChoice choice) throws Exception {
if (choice == null) throw new IllegalArgumentException("choice");
adapter.resolvePendingConflict(choice);
⋮----
public void uploadLocal() throws Exception {
upload(ProfileStore.exportBackup());
⋮----
public void upload(String localBackup) throws Exception {
ProfileBackupCodec.decode(localBackup);
adapter.write(localBackup);
⋮----
public void uploadIfUnchanged(Comparison comparison) throws Exception {
if (comparison == null) throw new IllegalArgumentException("comparison");
String currentLocal = ProfileStore.exportBackup();
if (!currentLocal.equals(comparison.localBackup())) {
throw new IllegalStateException("Local profile changed; refresh before uploading");
⋮----
ProfileBackupCodec.decode(currentLocal);
adapter.writeIfUnchanged(currentLocal, comparison.remote());
⋮----
public CloudSaveAdapter.RemoteBackup inspectRemote() throws Exception {
CloudSaveAdapter.RemoteBackup remote = adapter.read();
if (remote == null || remote.payload() == null || remote.payload().isBlank()) return null;
ProfileBackupCodec.decode(remote.payload());
⋮----
public Comparison inspectAgainstLocal() throws Exception {
String localBackup = ProfileStore.exportBackup();
CloudSaveAdapter.RemoteBackup remote = inspectRemote();
return new Comparison(localBackup, remote, classify(localBackup, remote));
⋮----
public ConflictState compareRemoteToLocal() throws Exception {
return inspectAgainstLocal().state();
⋮----
public ConflictState compareRemoteToLocal(String localBackup) throws Exception {
return classify(localBackup, inspectRemote());
⋮----
public ConflictState classify(String localBackup, CloudSaveAdapter.RemoteBackup remote) {
Map<String, Object> localValues = ProfileBackupCodec.decode(localBackup);
⋮----
Map<String, Object> remoteValues = ProfileBackupCodec.decode(remote.payload());
⋮----
if (localValues.equals(remoteValues)) return ConflictState.EQUAL;
⋮----
Map<String, Object> localNonMonotone = withoutMonotone(localValues);
Map<String, Object> remoteNonMonotone = withoutMonotone(remoteValues);
if (!localNonMonotone.equals(remoteNonMonotone)) return ConflictState.DIVERGED;
⋮----
ProfileBackupSummary local = ProfileBackupSummary.from(localValues);
ProfileBackupSummary cloud = ProfileBackupSummary.from(remoteValues);
if (local.dominates(cloud)) return ConflictState.LOCAL_AHEAD;
if (cloud.dominates(local)) return ConflictState.REMOTE_AHEAD;
⋮----
public CloudSaveAdapter.RemoteBackup revalidateRemote(Comparison comparison) throws Exception {
⋮----
if (!ProfileStore.exportBackup().equals(comparison.localBackup())) {
throw new IllegalStateException("Local profile changed; refresh before continuing");
⋮----
CloudSaveAdapter.RemoteBackup current = inspectRemote();
if (!sameRemote(current, comparison.remote())) throw new CloudRemoteChangedException();
⋮----
public RestoreResult downloadRemote() throws Exception {
⋮----
if (remote == null) return new RestoreResult(DownloadResult.EMPTY_REMOTE, null);
return applyRemote(remote.payload());
⋮----
public RestoreResult applyRemote(String remoteBackup) {
PlayerProfile restored = ProfileStore.importBackup(remoteBackup);
if (restored == null) return new RestoreResult(DownloadResult.REJECTED_NEWER_SCHEMA, null);
// The store only returns after a complete typed reload succeeds. Callers must replace their
// active in-memory profile before any subsequent save.
return new RestoreResult(DownloadResult.APPLIED, restored);
⋮----
private static Map<String, Object> withoutMonotone(Map<String, Object> source) {
⋮----
for (String key : ProfileBackupSummary.MONOTONE_KEYS) copy.remove(key);
⋮----
public static boolean sameRemote(CloudSaveAdapter.RemoteBackup a, CloudSaveAdapter.RemoteBackup b) {
⋮----
return a.modifiedAtEpochMillis() == b.modifiedAtEpochMillis()
&& java.util.Objects.equals(a.payload(), b.payload());
```

## File: src/main/java/com/deadlinezero/game/services/GameServices.java
```java
public final class GameServices {
⋮----
this(ads, billing, PrivacyService.noOp(), ShareService.noOp(), HapticsService.noOp(),
CloudSaveAdapter.unavailable(), ThermalService.noOp(), OfferConfigService.safeLocal());
⋮----
this(ads, billing, privacy, ShareService.noOp(), HapticsService.noOp(),
⋮----
this(ads, billing, privacy, share, HapticsService.noOp(),
⋮----
this(ads, billing, privacy, share, haptics, cloudSave, ThermalService.noOp(), OfferConfigService.safeLocal());
⋮----
this(ads, billing, privacy, share, haptics, cloudSave, thermal, OfferConfigService.safeLocal());
⋮----
this(ads, billing, privacy, share, haptics, cloudSave, thermal, offers, ReviewService.noOp());
⋮----
this.privacy = privacy == null ? PrivacyService.noOp() : privacy;
this.share = share == null ? ShareService.noOp() : share;
this.haptics = haptics == null ? HapticsService.noOp() : haptics;
this.cloudSave = cloudSave == null ? CloudSaveAdapter.unavailable() : cloudSave;
this.thermal = thermal == null ? ThermalService.noOp() : thermal;
this.offers = offers == null ? OfferConfigService.safeLocal() : offers;
this.review = review == null ? ReviewService.noOp() : review;
⋮----
public static GameServices noOp() {
return new GameServices(new AdsService() {
public boolean isRewardedReady() { return false; }
public void showRewarded(Reward reward, Runnable earned, Runnable unavailable) { unavailable.run(); }
}, new BillingService() {
public void initialize() {}
public boolean owns(String id) { return false; }
public void purchase(String id, Runnable success, Runnable failure) { failure.run(); }
public void restore() {}
}, PrivacyService.noOp(), ShareService.noOp(), HapticsService.noOp());
```

## File: src/main/java/com/deadlinezero/game/services/HapticsService.java
```java
/** Platform haptic feedback bridge. Implementations should keep pulses short and non-blocking. */
public interface HapticsService {
void dash();
void damage();
void bossKill();
⋮----
static HapticsService noOp() {
return new HapticsService() {
@Override public void dash() {}
@Override public void damage() {}
@Override public void bossKill() {}
```

## File: src/main/java/com/deadlinezero/game/services/OfferConfigService.java
```java
/**
 * Store-offer presentation boundary for optional remote configuration.
 *
 * Remote data may only control visibility and a featured known Play product. Product IDs, prices and
 * grant quantities remain owned by BillingService / Play Billing. Invalid or missing remote data
 * falls back to a complete safe catalog instead of silently hiding monetization.
 */
public interface OfferConfigService {
⋮----
enabledProducts = Set.copyOf(enabledProducts == null ? Set.of() : enabledProducts);
⋮----
public boolean enabled(String productId) {
return BillingService.isKnownProduct(productId) && enabledProducts.contains(productId);
⋮----
public boolean featured(String productId) {
return enabled(productId) && productId.equals(featuredProductId);
⋮----
Snapshot current();
⋮----
default void refresh() {}
⋮----
static Snapshot safeDefaults() {
return new Snapshot(BillingService.PRODUCTS, BillingService.STARTER_PACK);
⋮----
/**
     * Sanitizes an optional remote snapshot. Unknown product IDs are dropped. A missing/empty/fully
     * invalid remote catalog falls back to safe defaults. Featured products must also be enabled.
     */
static Snapshot sanitize(Set<String> remoteEnabledProducts, String remoteFeaturedProductId) {
if (remoteEnabledProducts == null || remoteEnabledProducts.isEmpty()) return safeDefaults();
⋮----
if (BillingService.isKnownProduct(productId)) known.add(productId);
⋮----
if (known.isEmpty()) return safeDefaults();
⋮----
if (!BillingService.isKnownProduct(featured) || !known.contains(featured)) featured = "";
return new Snapshot(known, featured);
⋮----
static OfferConfigService safeLocal() {
Snapshot defaults = safeDefaults();
⋮----
static OfferConfigService fixed(Snapshot snapshot) {
Snapshot safe = snapshot == null ? safeDefaults() : sanitize(snapshot.enabledProducts(), snapshot.featuredProductId());
```

## File: src/main/java/com/deadlinezero/game/services/PrivacyService.java
```java
/** Platform privacy controls exposed to the shared game UI. */
public interface PrivacyService {
boolean optionsRequired();
void showOptions(Runnable onDismissed);
boolean policyAvailable();
void openPolicy();
⋮----
static PrivacyService noOp() {
return new PrivacyService() {
@Override public boolean optionsRequired() { return false; }
@Override public void showOptions(Runnable onDismissed) {
if (onDismissed != null) onDismissed.run();
⋮----
@Override public boolean policyAvailable() { return false; }
@Override public void openPolicy() { }
```

## File: src/main/java/com/deadlinezero/game/services/ReviewService.java
```java
/** Platform boundary for optional store-managed in-app review prompts. */
public interface ReviewService {
void requestReview();
⋮----
static ReviewService noOp() {
```

## File: src/main/java/com/deadlinezero/game/services/ShareService.java
```java
/** Platform-native, opt-in text sharing. No analytics, account or social SDK required. */
public interface ShareService {
boolean available();
void shareText(String text);
⋮----
static ShareService noOp() {
return new ShareService() {
public boolean available() { return false; }
public void shareText(String text) { }
```

## File: src/main/java/com/deadlinezero/game/services/SingleFlightGate.java
```java
/**
 * Small thread-safe guard for asynchronous operations that must never overlap.
 * A caller that successfully begins an operation owns the gate until end() is called.
 */
public final class SingleFlightGate {
private final AtomicBoolean active = new AtomicBoolean(false);
⋮----
public boolean tryBegin() {
return active.compareAndSet(false, true);
⋮----
public void end() {
active.set(false);
⋮----
public boolean active() {
return active.get();
```

## File: src/main/java/com/deadlinezero/game/services/ThermalService.java
```java
/** Platform-neutral thermal pressure bridge used to protect sustained mobile performance. */
public interface ThermalService {
⋮----
Level level();
⋮----
static ThermalService noOp() {
```

## File: src/main/java/com/deadlinezero/game/ui/MetaLayout.java
```java
/** Shared pure layout contract for production meta screens. */
public final class MetaLayout {
⋮----
public static Layout compute(UiLayout.Metrics m) {
Rectangle header = new Rectangle(m.safeLeft(), m.headerBottom(), m.contentWidth(), m.safeTop() - m.headerBottom());
Rectangle content = new Rectangle(m.safeLeft(), m.contentBottom(), m.contentWidth(), m.contentHeight());
Rectangle footer = new Rectangle(m.safeLeft(), m.safeBottom(), m.contentWidth(), m.footerTop() - m.safeBottom());
Rectangle back = new Rectangle(m.safeLeft(), m.headerBottom(), Math.max(112f, m.touchTarget() * 2f), header.height);
return new Layout(header, content, footer, back);
⋮----
public static Rectangle[] columns(Rectangle area, int count, float gap) {
int safeCount = Math.max(1, count);
float safeGap = Math.max(0f, gap);
float width = (area.width - safeGap * Math.max(0, safeCount - 1)) / safeCount;
⋮----
result[i] = new Rectangle(area.x + i * (width + safeGap), area.y, width, area.height);
⋮----
public static Rectangle[] rows(Rectangle area, int count, float gap) {
⋮----
float height = (area.height - safeGap * Math.max(0, safeCount - 1)) / safeCount;
⋮----
result[i] = new Rectangle(area.x, y, area.width, height);
⋮----
public static Rectangle[] actions(Rectangle footer, int count, float gap) {
⋮----
float height = Math.max(UiLayout.MIN_TOUCH_TARGET, footer.height - 12f);
float width = (footer.width - safeGap * Math.max(0, safeCount - 1)) / safeCount;
⋮----
result[i] = new Rectangle(footer.x + i * (width + safeGap), y, width, height);
```

## File: src/main/java/com/deadlinezero/game/ui/ResponsiveGrid.java
```java
/** Pure responsive card-grid calculations for inventory/loadout screens. */
public final class ResponsiveGrid {
⋮----
public static Spec compute(float contentWidth, float minCardWidth, int maxColumns, float gap) {
float safeGap = Math.max(0f, gap);
float safeMin = Math.max(1f, minCardWidth);
int safeMax = Math.max(1, maxColumns);
int columns = (int) Math.floor((Math.max(1f, contentWidth) + safeGap) / (safeMin + safeGap));
columns = Math.max(1, Math.min(safeMax, columns));
float cardWidth = (Math.max(1f, contentWidth) - safeGap * Math.max(0, columns - 1)) / columns;
return new Spec(columns, cardWidth, safeGap);
⋮----
public static Rectangle cardBounds(int index, float originX, float topY, float cardHeight, Spec spec) {
if (spec == null) throw new IllegalArgumentException("spec");
int safeIndex = Math.max(0, index);
int col = safeIndex % spec.columns();
int row = safeIndex / spec.columns();
float x = originX + col * (spec.cardWidth() + spec.gap());
float y = topY - (row + 1) * cardHeight - row * spec.gap();
return new Rectangle(x, y, spec.cardWidth(), cardHeight);
```

## File: src/main/java/com/deadlinezero/game/ui/UiIconRenderer.java
```java
/**
 * Lightweight authored icon language for production UI.
 *
 * Drawn from simple primitives so icons remain crisp at every supported phone
 * resolution and do not depend on text glyphs or platform emoji rendering.
 * Call while ShapeRenderer is in Filled mode.
 */
public final class UiIconRenderer {
⋮----
public static void draw(ShapeRenderer shapes, Icon icon, float x, float y, float size, Color color) {
draw(shapes, icon, x, y, size, color, 1f);
⋮----
public static void draw(ShapeRenderer shapes, Icon icon, float x, float y, float size, Color color, float alpha) {
⋮----
shapes.setColor(color.r, color.g, color.b, clamp(alpha));
⋮----
float t = Math.max(2f, s * .10f);
⋮----
shapes.triangle(cx, y + s, x + s * .14f, y + s * .28f, x + s * .86f, y + s * .28f);
set(shapes, color, alpha * .36f);
shapes.triangle(cx, y + s * .78f, x + s * .30f, y + s * .38f, x + s * .70f, y + s * .38f);
⋮----
shapes.circle(cx, cy, s * .38f, 20);
set(shapes, color, alpha * .18f);
shapes.circle(cx, cy, s * .24f, 16);
set(shapes, color, alpha);
shapes.rect(cx - t * .45f, y + s * .30f, t * .9f, s * .40f);
⋮----
shapes.triangle(cx, y + s, x + s * .08f, cy, cx, y);
shapes.triangle(cx, y + s, x + s * .92f, cy, cx, y);
set(shapes, color, alpha * .30f);
shapes.triangle(cx, y + s * .80f, x + s * .28f, cy, cx, y + s * .18f);
shapes.triangle(cx, y + s * .80f, x + s * .72f, cy, cx, y + s * .18f);
⋮----
shapes.rect(x + s * .18f, y + s * .24f, s * .64f, s * .52f);
shapes.triangle(cx, y + s, x + s * .18f, y + s * .76f, x + s * .82f, y + s * .76f);
set(shapes, color, alpha * .24f);
shapes.rect(x + s * .34f, y + s * .36f, s * .32f, s * .24f);
⋮----
shapes.triangle(cx, y + s, x + s * .06f, y + s * .48f, x + s * .94f, y + s * .48f);
shapes.rect(x + s * .18f, y + s * .10f, s * .64f, s * .42f);
⋮----
shapes.rect(x + s * .42f, y + s * .10f, s * .16f, s * .28f);
⋮----
shapes.circle(cx, cy, s * .34f, 20);
⋮----
shapes.circle(cx, cy, s * .18f, 16);
⋮----
shapes.rect(cx - t * .5f, y, t, s);
shapes.rect(x, cy - t * .5f, s, t);
⋮----
shapes.rect(x + s * .12f, y + s * .18f, s * .30f, s * .64f);
shapes.rect(x + s * .58f, y + s * .18f, s * .30f, s * .64f);
set(shapes, color, alpha * .22f);
shapes.rect(x + s * .24f, y + s * .32f, s * .52f, s * .36f);
⋮----
shapes.rect(x + s * .10f, yy, s * .16f, s * .14f);
shapes.rect(x + s * .36f, yy + s * .045f, s * .54f, t * .55f);
⋮----
shapes.rect(x + s * .16f, y + s * .18f, s * .68f, s * .56f);
shapes.rect(x + s * .30f, y + s * .70f, s * .40f, t);
shapes.rect(x + s * .30f, y + s * .70f, t, s * .18f);
shapes.rect(x + s * .70f - t, y + s * .70f, t, s * .18f);
⋮----
shapes.rect(x + s * .28f, y + s * .30f, s * .44f, s * .10f);
⋮----
shapes.circle(cx, cy, s * .28f, 20);
shapes.rect(cx - t * .5f, y, t, s * .22f);
shapes.rect(cx - t * .5f, y + s * .78f, t, s * .22f);
shapes.rect(x, cy - t * .5f, s * .22f, t);
shapes.rect(x + s * .78f, cy - t * .5f, s * .22f, t);
⋮----
shapes.circle(cx, cy, s * .11f, 14);
⋮----
shapes.rect(x + s * .18f, y + s * .08f, s * .64f, s * .50f);
shapes.rect(x + s * .28f, y + s * .56f, t, s * .22f);
shapes.rect(x + s * .72f - t, y + s * .56f, t, s * .22f);
shapes.rect(x + s * .28f, y + s * .74f, s * .44f, t);
⋮----
shapes.rect(x + s * .28f, y + s * .42f, s * .44f, s * .42f);
shapes.rect(cx - t * .5f, y + s * .18f, t, s * .28f);
shapes.rect(x + s * .30f, y + s * .10f, s * .40f, t);
shapes.rect(x + s * .12f, y + s * .58f, s * .16f, t);
shapes.rect(x + s * .72f, y + s * .58f, s * .16f, t);
⋮----
shapes.circle(x + s * .36f, y + s * .46f, s * .22f, 18);
shapes.circle(x + s * .56f, y + s * .58f, s * .28f, 20);
shapes.circle(x + s * .76f, y + s * .44f, s * .18f, 16);
shapes.rect(x + s * .20f, y + s * .28f, s * .64f, s * .24f);
set(shapes, color, alpha * .20f);
shapes.rect(cx - t * .5f, y + s * .08f, t, s * .28f);
⋮----
shapes.rect(x + s * .16f, y + s * .20f, s * .68f, s * .46f);
shapes.rect(x + s * .12f, y + s * .62f, s * .76f, s * .18f);
⋮----
shapes.rect(cx - t * .5f, y + s * .34f, t, s * .24f);
⋮----
shapes.rect(x + s * .20f, y + s * .68f, s * .60f, t * .65f);
⋮----
private static void set(ShapeRenderer shapes, Color c, float alpha) {
shapes.setColor(c.r, c.g, c.b, clamp(alpha));
⋮----
private static float clamp(float v) {
return Math.max(0f, Math.min(1f, v));
```

## File: src/main/java/com/deadlinezero/game/ui/UiLayout.java
```java
/** Pure responsive layout math shared by all production-facing UI. */
public final class UiLayout {
⋮----
public float contentWidth() { return safeRight - safeLeft; }
public float contentHeight() { return contentTop - contentBottom; }
public float centerX() { return width * .5f; }
public float centerY() { return height * .5f; }
⋮----
public static Metrics compute(int screenWidth, int screenHeight) {
⋮----
float extraX = Math.max(0f, width - BASE_WIDTH);
float extraY = Math.max(0f, height - BASE_HEIGHT);
float horizontalMargin = Math.max(MIN_SAFE_MARGIN, MIN_SAFE_MARGIN + extraX * .04f);
horizontalMargin = Math.min(horizontalMargin, Math.max(MIN_SAFE_MARGIN, (width - MIN_CONTENT_WIDTH) * .5f));
float verticalMargin = Math.max(MIN_SAFE_MARGIN, MIN_SAFE_MARGIN + extraY * .04f);
⋮----
return new Metrics(
```

## File: src/main/java/com/deadlinezero/game/ui/UiMotion.java
```java
/** Deterministic short UI motion curves with an immediate reduced-motion path. */
public final class UiMotion {
⋮----
public static float progress(float elapsed, float duration, boolean reduceMotion) {
⋮----
float t = MathUtils.clamp(elapsed / duration, 0f, 1f);
return easeOutCubic(t);
⋮----
public static float easeOutCubic(float t) {
float c = MathUtils.clamp(t, 0f, 1f);
⋮----
public static float easeInOut(float t) {
⋮----
return c < .5f ? 4f * c * c * c : 1f - (float) Math.pow(-2f * c + 2f, 3f) / 2f;
⋮----
public static float pressScale(float elapsed, boolean reduceMotion) {
⋮----
float p = progress(elapsed, PRESS_SECONDS, false);
return 1f - .025f * (1f - Math.abs(p * 2f - 1f));
```

## File: src/main/java/com/deadlinezero/game/ui/UiRenderer.java
```java
/** Allocation-free shape primitives for the shared production UI language. */
public final class UiRenderer {
⋮----
private static final ButtonStyle NORMAL = new ButtonStyle(Tone.NEUTRAL, .88f, .78f, 1f);
private static final ButtonStyle PRESSED = new ButtonStyle(Tone.ACCENT, .98f, 1f, 1f);
private static final ButtonStyle SELECTED = new ButtonStyle(Tone.SELECTED, .94f, 1f, 1f);
private static final ButtonStyle DISABLED = new ButtonStyle(Tone.DISABLED, .58f, .55f, .62f);
private static final ButtonStyle DANGER = new ButtonStyle(Tone.DANGER, .90f, 1f, 1f);
⋮----
public static ButtonStyle buttonStyle(ButtonState state) {
⋮----
/** Draw while ShapeRenderer is already in Filled mode. */
public static void background(ShapeRenderer shapes, UiLayout.Metrics m, float time) {
// ShapeRenderer does not enable blending automatically. Nearly every premium surface below
// intentionally uses translucent overlays, so without this the alpha channel is ignored and
// subtle 5-15% accents become opaque cyan/violet slabs on Android.
Gdx.gl.glEnable(GL20.GL_BLEND);
Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
⋮----
set(shapes, VisualTheme.SURFACE_0, 1f);
shapes.rect(0f, 0f, m.width(), m.height());
⋮----
set(shapes, VisualTheme.SURFACE_1, .52f);
shapes.rect(0f, m.height() * .73f, m.width(), m.height() * .27f);
set(shapes, VisualTheme.SURFACE_2, .34f);
shapes.rect(0f, 0f, m.width(), m.height() * .13f);
⋮----
// Keep the sci-fi scan language, but let content dominate the frame.
boolean reduceMotion = AccessibilitySettings.active().reducedMotion;
float pulse = reduceMotion ? .022f : .022f + .010f * (float) Math.sin(time * .75f);
set(shapes, VisualTheme.accent(), pulse);
⋮----
for (float y = 42f; y < m.height(); y += step) shapes.rect(0f, y, m.width(), 1f);
⋮----
// Shader-inspired light shafts and horizon bloom, implemented with cheap geometry.
// They create depth on every screen without shipping a static background bitmap.
float drift = reduceMotion ? 0f : (float)Math.sin(time * .22f) * m.width() * .025f;
float horizon = m.height() * .58f;
set(shapes, VisualTheme.accent(), .018f);
shapes.triangle(m.width() * .08f + drift, m.height(), m.width() * .22f + drift, m.height(),
m.width() * .42f + drift, 0f);
shapes.triangle(m.width() * .74f - drift, m.height(), m.width() * .86f - drift, m.height(),
m.width() * .58f - drift, 0f);
set(shapes, VisualTheme.CYAN_SOFT, .028f);
shapes.rect(0f, horizon - 22f, m.width(), 44f);
set(shapes, VisualTheme.SURFACE_0, .74f);
shapes.rect(0f, horizon + 4f, m.width(), 2f);
⋮----
// Quiet edge rails create depth without flooding the screen with cyan.
set(shapes, VisualTheme.BORDER, .28f);
shapes.rect(m.safeLeft(), m.safeBottom(), 2f, m.safeTop() - m.safeBottom());
shapes.rect(m.safeLeft() + m.contentWidth() - 2f, m.safeBottom(), 2f, m.safeTop() - m.safeBottom());
⋮----
public static void panel(ShapeRenderer shapes, float x, float y, float w, float h) {
set(shapes, VisualTheme.SURFACE_1, .985f);
shapes.rect(x, y, w, h);
set(shapes, VisualTheme.BORDER, .64f);
border(shapes, x, y, w, h, 2f);
set(shapes, VisualTheme.SURFACE_2, .72f);
shapes.rect(x + 3f, y + h - 4f, Math.max(0f, w - 6f), 1f);
⋮----
public static void card(ShapeRenderer shapes, float x, float y, float w, float h, boolean focused, boolean selected) {
set(shapes, selected ? VisualTheme.SURFACE_2 : VisualTheme.SURFACE_1, .97f);
⋮----
set(shapes, VisualTheme.SURFACE_0, .36f);
shapes.rect(x + 5f, y + 5f, Math.max(0f, w - 10f), Math.min(10f, Math.max(0f, h - 10f)));
⋮----
set(shapes, border, focused || selected ? 1f : .68f);
border(shapes, x, y, w, h, focused || selected ? 3f : 2f);
⋮----
set(shapes, VisualTheme.accent(), .95f);
shapes.rect(x, y, 5f, h);
⋮----
cornerMarks(shapes, x, y, w, h, focused || selected ? VisualTheme.accent() : VisualTheme.DIVIDER);
⋮----
public static void button(ShapeRenderer shapes, float x, float y, float w, float h, ButtonState state) {
ButtonStyle style = buttonStyle(state);
Color fill = switch (style.tone()) {
case ACCENT, SELECTED -> VisualTheme.accent();
case DANGER -> VisualTheme.danger();
⋮----
Color borderColor = switch (style.tone()) {
⋮----
set(shapes, fill, style.fillAlpha());
⋮----
set(shapes, borderColor, style.borderAlpha());
border(shapes, x, y, w, h, state == ButtonState.PRESSED ? 4f : 2f);
⋮----
set(shapes, VisualTheme.TEXT_STRONG, .82f);
shapes.rect(x + 7f, y + 7f, 4f, Math.max(0f, h - 14f));
⋮----
public static void iconBadge(ShapeRenderer shapes, float x, float y, float size, Color accent, boolean active) {
Color a = accent == null ? VisualTheme.accent() : accent;
set(shapes, VisualTheme.SURFACE_0, .96f);
shapes.circle(x + size * .5f, y + size * .5f, size * .50f, 24);
set(shapes, active ? a : VisualTheme.BORDER, active ? .24f : .18f);
shapes.circle(x + size * .5f, y + size * .5f, size * .40f, 24);
set(shapes, active ? a : VisualTheme.BORDER, active ? .90f : .50f);
float t = Math.max(1.5f, size * .055f);
border(shapes, x, y, size, size, t);
cornerMarks(shapes, x, y, size, size, active ? a : VisualTheme.DIVIDER);
⋮----
public static void sectionBand(ShapeRenderer shapes, float x, float y, float w, float h, Color accent) {
⋮----
set(shapes, VisualTheme.SURFACE_0, .72f);
⋮----
set(shapes, a, .18f);
shapes.rect(x, y, Math.min(w, Math.max(44f, w * .34f)), h);
set(shapes, a, .88f);
shapes.rect(x, y, 4f, h);
shapes.rect(x + 8f, y + h - 3f, Math.max(0f, Math.min(w - 16f, w * .42f)), 2f);
⋮----
public static void premiumCta(ShapeRenderer shapes, float x, float y, float w, float h, Color accent, float pulse) {
⋮----
float p = Math.max(0f, Math.min(1f, pulse));
set(shapes, VisualTheme.SURFACE_2, .98f);
⋮----
set(shapes, a, .10f + .10f * p);
shapes.rect(x + 5f, y + 5f, Math.max(0f, w - 10f), Math.max(0f, h - 10f));
set(shapes, a, .72f + .24f * p);
⋮----
shapes.rect(x + 10f, y + h - 6f, Math.max(0f, w - 20f), 4f);
shapes.rect(x + 10f, y + 10f, 4f, Math.max(0f, h - 20f));
cornerMarks(shapes, x, y, w, h, a);
⋮----
/**
     * Production panel with layered depth, bevel notches and restrained emissive trim.
     * Draw while ShapeRenderer is already in Filled mode.
     */
public static void premiumPanel(ShapeRenderer shapes, float x, float y, float w, float h,
⋮----
float notch = Math.min(18f, Math.min(w, h) * .10f);
⋮----
// Shadow / separation from background.
set(shapes, VisualTheme.BG, .92f);
shapes.rect(x + 7f, y - 7f, Math.max(0f, w), Math.max(0f, h));
⋮----
// Main body + subtle inset.
set(shapes, VisualTheme.SURFACE_1, .995f);
⋮----
set(shapes, VisualTheme.SURFACE_2, emphasized ? .72f : .46f);
⋮----
// Angular cut-corner overlays.
set(shapes, VisualTheme.BG, 1f);
shapes.triangle(x, y + h, x + notch, y + h, x, y + h - notch);
shapes.triangle(x + w, y, x + w - notch, y, x + w, y + notch);
⋮----
// Double-frame and accent hierarchy.
set(shapes, VisualTheme.BORDER, emphasized ? .94f : .72f);
border(shapes, x, y, w, h, emphasized ? 2.5f : 2f);
set(shapes, a, emphasized ? .88f : .52f);
shapes.rect(x + notch + 5f, y + h - 4f, Math.max(0f, w - notch * 2f - 10f), 3f);
shapes.rect(x + 4f, y + notch + 5f, 3f, Math.max(0f, h - notch * 2f - 10f));
⋮----
// Inner highlight gives the card material depth without a texture dependency.
set(shapes, a, emphasized ? .10f : .045f);
shapes.rect(x + 9f, y + 9f, Math.max(0f, w - 18f), Math.max(0f, h - 18f));
set(shapes, VisualTheme.SURFACE_1, .96f);
shapes.rect(x + 13f, y + 13f, Math.max(0f, w - 26f), Math.max(0f, h - 26f));
⋮----
cornerMarks(shapes, x + 3f, y + 3f, w - 6f, h - 6f, a);
⋮----
public static void premiumCard(ShapeRenderer shapes, float x, float y, float w, float h,
⋮----
set(shapes, VisualTheme.SURFACE_1, disabled ? .72f : .98f);
⋮----
set(shapes, VisualTheme.SURFACE_2, disabled ? .18f : focused || selected ? .66f : .38f);
shapes.rect(x + 4f, y + 4f, Math.max(0f, w - 8f), Math.max(0f, h - 8f));
⋮----
set(shapes, frame, disabled ? .34f : focused || selected ? .95f : .64f);
border(shapes, x, y, w, h, focused || selected ? 2.5f : 2f);
⋮----
set(shapes, a, disabled ? .14f : selected ? .92f : focused ? .72f : .34f);
shapes.rect(x + 6f, y + h - 5f, Math.max(0f, w - 12f), 3f);
shapes.rect(x + 6f, y + 7f, focused || selected ? 4f : 2f, Math.max(0f, h - 14f));
⋮----
set(shapes, a, .10f);
shapes.rect(x + 10f, y + 10f, Math.max(0f, w - 20f), Math.max(0f, h - 20f));
⋮----
cornerMarks(shapes, x, y, w, h, frame);
⋮----
public static void premiumButton(ShapeRenderer shapes, float x, float y, float w, float h,
⋮----
set(shapes, disabled ? VisualTheme.SURFACE_1 : VisualTheme.SURFACE_2, disabled ? .60f : .99f);
⋮----
set(shapes, a, disabled ? .10f : active ? .18f : .07f);
⋮----
set(shapes, disabled ? VisualTheme.BORDER : a, disabled ? .38f : active ? 1f : .70f);
border(shapes, x, y, w, h, active ? 3f : 2f);
shapes.rect(x + 9f, y + h - 5f, Math.max(0f, w - 18f), active ? 4f : 2f);
⋮----
shapes.rect(x + 9f, y + 9f, 4f, Math.max(0f, h - 18f));
⋮----
cornerMarks(shapes, x, y, w, h, disabled ? VisualTheme.BORDER : a);
⋮----
public static void sectionPlate(ShapeRenderer shapes, float x, float y, float w, float h,
⋮----
set(shapes, VisualTheme.SURFACE_0, .94f);
⋮----
set(shapes, a, strong ? .18f : .09f);
shapes.rect(x + 3f, y + 3f, Math.max(0f, w - 6f), Math.max(0f, h - 6f));
set(shapes, a, strong ? .92f : .58f);
⋮----
shapes.rect(x + 8f, y + h - 3f, Math.max(0f, Math.min(w - 16f, w * .48f)), 2f);
set(shapes, VisualTheme.BORDER, .62f);
border(shapes, x, y, w, h, 1.5f);
⋮----
public static void segmentedTrack(ShapeRenderer shapes, float x, float y, float w, float h,
⋮----
float p = Math.max(0f, Math.min(1f, progress));
int count = Math.max(1, segments);
float gap = Math.min(3f, w / Math.max(12f, count * 8f));
float segW = Math.max(1f, (w - gap * (count - 1)) / count);
int filled = Math.round(p * count);
⋮----
set(shapes, i < filled ? a : VisualTheme.SURFACE_0, i < filled ? .95f : .98f);
shapes.rect(sx, y, segW, h);
set(shapes, i < filled ? a : VisualTheme.BORDER, i < filled ? .82f : .55f);
border(shapes, sx, y, segW, h, 1f);
⋮----
public static void progress(ShapeRenderer shapes, float x, float y, float w, float h, float progress, Color color) {
⋮----
set(shapes, VisualTheme.SURFACE_0, .98f);
⋮----
set(shapes, VisualTheme.BORDER, .75f);
⋮----
set(shapes, color == null ? VisualTheme.accent() : color, .95f);
shapes.rect(x + 2f, y + 2f, Math.max(0f, (w - 4f) * p), Math.max(0f, h - 4f));
⋮----
public static void chip(ShapeRenderer shapes, float x, float y, float w, float h, Color accent) {
set(shapes, VisualTheme.SURFACE_2, .94f);
⋮----
set(shapes, accent == null ? VisualTheme.BORDER : accent, .88f);
shapes.rect(x, y, 3f, h);
border(shapes, x, y, w, h, 1f);
⋮----
public static void topRail(ShapeRenderer shapes, UiLayout.Metrics m) {
float h = m.safeTop() - m.headerBottom();
⋮----
shapes.rect(m.safeLeft(), m.headerBottom(), m.contentWidth(), h);
set(shapes, VisualTheme.BORDER, .78f);
shapes.rect(m.safeLeft(), m.headerBottom(), m.contentWidth(), 2f);
⋮----
public static void bottomNav(ShapeRenderer shapes, UiLayout.Metrics m) {
float h = m.footerTop() - m.safeBottom();
⋮----
shapes.rect(m.safeLeft(), m.safeBottom(), m.contentWidth(), h);
⋮----
shapes.rect(m.safeLeft(), m.footerTop() - 2f, m.contentWidth(), 2f);
⋮----
private static void cornerMarks(ShapeRenderer shapes, float x, float y, float w, float h, Color color) {
float l = Math.min(14f, Math.min(w, h) * .15f);
set(shapes, color, .9f);
shapes.rect(x, y + h - 2f, l, 2f);
shapes.rect(x, y + h - l, 2f, l);
shapes.rect(x + w - l, y + h - 2f, l, 2f);
shapes.rect(x + w - 2f, y + h - l, 2f, l);
⋮----
private static void border(ShapeRenderer shapes, float x, float y, float w, float h, float t) {
shapes.rect(x, y, w, t);
shapes.rect(x, y + h - t, w, t);
shapes.rect(x, y + t, t, Math.max(0f, h - t * 2f));
shapes.rect(x + w - t, y + t, t, Math.max(0f, h - t * 2f));
⋮----
private static void set(ShapeRenderer shapes, Color c, float alpha) {
shapes.setColor(c.r, c.g, c.b, Math.max(0f, Math.min(1f, alpha)));
```

## File: src/main/java/com/deadlinezero/game/ui/UiTypography.java
```java
/** Named bitmap-font roles so screens do not invent arbitrary scales. */
public final class UiTypography {
⋮----
public static float scale(Role role) {
```

## File: src/main/java/com/deadlinezero/game/ui/UiViewport.java
```java
/** Shared logical viewport for rendering and touch conversion. */
public final class UiViewport {
private final OrthographicCamera camera = new OrthographicCamera();
private final ExtendViewport viewport = new ExtendViewport(UiLayout.BASE_WIDTH, UiLayout.BASE_HEIGHT, camera);
private UiLayout.Metrics metrics = UiLayout.compute((int) UiLayout.BASE_WIDTH, (int) UiLayout.BASE_HEIGHT);
⋮----
public void resize(int screenWidth, int screenHeight) {
int safeWidth = Math.max(1, screenWidth);
int safeHeight = Math.max(1, screenHeight);
viewport.update(safeWidth, safeHeight, true);
metrics = UiLayout.compute(screenWidth, screenHeight);
⋮----
public void apply(SpriteBatch batch, ShapeRenderer shapes) {
viewport.apply(true);
if (batch != null) batch.setProjectionMatrix(camera.combined);
if (shapes != null) shapes.setProjectionMatrix(camera.combined);
⋮----
public void apply(SpriteBatch batch) {
apply(batch, null);
⋮----
public void apply(ShapeRenderer shapes) {
apply(null, shapes);
⋮----
public Vector2 unproject(float screenX, float screenY, Vector2 out) {
if (out == null) throw new IllegalArgumentException("out");
out.set(screenX, screenY);
viewport.unproject(out);
⋮----
public float width() { return viewport.getWorldWidth(); }
public float height() { return viewport.getWorldHeight(); }
public UiLayout.Metrics metrics() { return metrics; }
public OrthographicCamera camera() { return camera; }
```

## File: src/main/java/com/deadlinezero/game/util/Pools.java
```java
public final class Pools {
⋮----
for (int i = 0; i < GameConfig.MAX_PROJECTILES; i++) projectiles.add(new Projectile());
for (int i = 0; i < MAX_HOSTILE_PROJECTILES; i++) hostileProjectiles.add(new EnemyProjectile());
for (int i = 0; i < MAX_HOMING_MISSILES; i++) homingMissiles.add(new HomingMissile());
for (int i = 0; i < MAX_IMPACTS; i++) impacts.add(new ImpactFx());
for (int i = 0; i < MAX_DAMAGE_NUMBERS; i++) damageNumbers.add(new DamageNumber());
for (int i = 0; i < MAX_ARCS; i++) arcs.add(new ArcFx());
for (int i = 0; i < MAX_DEATH_FX; i++) deathFx.add(new DeathFx());
⋮----
public Projectile projectile() {
⋮----
Projectile p = projectiles.get(projectileCursor);
⋮----
public EnemyProjectile hostileProjectile() {
⋮----
EnemyProjectile p = hostileProjectiles.get(hostileProjectileCursor);
⋮----
public HomingMissile homingMissile() {
⋮----
HomingMissile missile = homingMissiles.get(homingMissileCursor);
⋮----
public ImpactFx impact() {
⋮----
ImpactFx f = impacts.get(impactCursor);
⋮----
public DamageNumber damageNumber() {
⋮----
DamageNumber n = damageNumbers.get(damageNumberCursor);
⋮----
public ArcFx arc() {
⋮----
ArcFx arc = arcs.get(arcCursor);
⋮----
public DeathFx deathFx() {
⋮----
DeathFx fx = deathFx.get(deathFxCursor);
⋮----
DeathFx oldest = deathFx.get(deathFxCursor);
```

## File: src/main/java/com/deadlinezero/game/visual/ActiveBuildStatus.java
```java
/** Builds a compact, allocation-bounded HUD summary of the run's active build identity. */
public final class ActiveBuildStatus {
⋮----
public static void fill(Player player, String[] out) {
if (out == null || out.length < 2) throw new IllegalArgumentException("out");
⋮----
DroneDoctrine doctrine = a.droneDoctrine();
⋮----
String synergy = primarySynergy(a);
⋮----
static String primarySynergy(AbilityLoadout a) {
if (a.hasStormBladeSynergy()) return "hud.build.stormBlade";
if (a.hasTeslaEvolution()) return "hud.build.arcReactor";
if (a.hasCryoMissileEvolution()) return "hud.build.cryoBarrage";
if (a.hasSuperconductorSynergy()) return "hud.build.superconductor";
if (a.hasTargetNetworkSynergy()) return "hud.build.targetNetwork";
if (a.hasPermafrostBladeSynergy()) return "hud.build.permafrostBlades";
```

## File: src/main/java/com/deadlinezero/game/visual/ActorMaterialProfile.java
```java
/**
 * Pure presentation budget for actor silhouette reinforcement.
 *
 * Standard crowd enemies remain single-draw. Only player/high-priority enemies receive one
 * enlarged dark underlay so busy authored floors cannot swallow important silhouettes.
 */
public final class ActorMaterialProfile {
⋮----
private static final Profile NONE = new Profile(false, 1f, 0f);
private static final Profile PLAYER = new Profile(true, 1.078f, .80f);
private static final Profile CHAMPION = new Profile(true, 1.068f, .74f);
private static final Profile SPECIALIST = new Profile(true, 1.072f, .78f);
private static final Profile ELITE = new Profile(true, 1.076f, .80f);
private static final Profile BOSS = new Profile(true, 1.080f, .80f);
⋮----
public static Profile player() { return PLAYER; }
⋮----
public static Profile enemy(Enemy.Type type, Enemy.Variant variant) {
```

## File: src/main/java/com/deadlinezero/game/visual/AdaptiveFxBudget.java
```java
/** Smoothly adapts optional visual density to sustained frame rate without changing gameplay. */
public final class AdaptiveFxBudget {
⋮----
public void update(float dt) {
dt = Math.max(0f, Math.min(.1f, dt));
advanceExternalCeiling(dt);
⋮----
warmup = Math.max(0f, warmup - dt);
⋮----
float fps = Math.max(1f, Gdx.graphics.getFramesPerSecond());
float fpsBlend = 1f - (float)Math.exp(-dt * 2.0f);
smoothedFps = MathUtils.lerp(smoothedFps, fps, fpsBlend);
⋮----
// Degrade quickly under load, recover slowly to avoid visual quality oscillation.
⋮----
float qualityBlend = 1f - (float)Math.exp(-dt * response);
quality = MathUtils.lerp(quality, target, qualityBlend);
⋮----
public void setExternalCeiling(float ceiling) {
externalCeilingTarget = MathUtils.clamp(ceiling, .40f, 1f);
⋮----
void advanceExternalCeiling(float dt) {
⋮----
float safeDt = MathUtils.clamp(dt, 0f, .1f);
float blend = 1f - (float)Math.exp(-safeDt * .45f);
externalCeiling = MathUtils.lerp(externalCeiling, externalCeilingTarget, blend);
if (Math.abs(externalCeilingTarget - externalCeiling) < .002f) externalCeiling = externalCeilingTarget;
⋮----
public float quality() {
return Math.min(Math.min(MathUtils.clamp(quality, .40f, 1f), GraphicsSettings.fxCeiling()), externalCeiling);
⋮----
public boolean allowHeavyFx() { return quality() >= .72f; }
public boolean allowExtraFx() { return quality() >= .90f; }
public int geometrySegments(int high, int low) {
return Math.max(low, Math.round(MathUtils.lerp(low, high, quality())));
⋮----
public float smoothedFps() { return smoothedFps; }
```

## File: src/main/java/com/deadlinezero/game/visual/AnimationProfileCatalog.java
```java
/** Production animation timing contract kept independent from gameplay simulation. */
public final class AnimationProfileCatalog {
⋮----
public float duration(GameArt.Motion motion) {
⋮----
private static final Profile REX = new Profile(.13f, .082f, .060f, .055f, .105f);
private static final Profile NYX = new Profile(.13f, .074f, .052f, .050f, .098f);
private static final Profile BASTION = new Profile(.15f, .105f, .078f, .065f, .120f);
private static final Profile VOLT = new Profile(.12f, .080f, .060f, .052f, .102f);
private static final Profile WRAITH = new Profile(.11f, .066f, .050f, .046f, .092f);
⋮----
private static final Profile SHAMBLER = new Profile(.15f, .110f, .095f, .060f, .125f);
private static final Profile RUNNER = new Profile(.11f, .070f, .070f, .050f, .095f);
private static final Profile BRUTE = new Profile(.17f, .125f, .115f, .072f, .145f);
private static final Profile RANGED = new Profile(.14f, .095f, .085f, .058f, .115f);
private static final Profile ELITE = new Profile(.13f, .088f, .080f, .055f, .110f);
private static final Profile BOSS = new Profile(.18f, .120f, .105f, .070f, .155f);
⋮----
public static Profile survivor(SurvivorCatalog.Survivor survivor) {
⋮----
public static Profile enemy(Enemy.Type type) {
⋮----
public static boolean loops(GameArt.Motion motion) {
```

## File: src/main/java/com/deadlinezero/game/visual/ArtManifest.java
```java
/** Central production-art contract and lightweight runtime atlas validator. */
public final class ArtManifest {
⋮----
/** Atlas-driven effects already consumed by AuthoredVfxRenderer. */
⋮----
public static int validate(GameArt art) {
if (!art.authoredAvailable()) return 0;
⋮----
for (String key : REQUIRED_STATIC) missing += requireRegion(art, key);
for (String key : REQUIRED_FX) missing += requireMotion(art, key);
for (WeaponDefinition weapon : WeaponCatalog.all()) missing += requireRegion(art, "weapon/" + weapon.id);
⋮----
for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
String root = "survivor/" + survivor.name().toLowerCase();
missing += requireMotion(art, root, "idle");
missing += requireMotion(art, root, "run");
missing += requireMotion(art, root, "attack");
missing += requireMotion(art, root, "hit");
missing += requireMotion(art, root, "death");
⋮----
for (Enemy.Type type : Enemy.Type.values()) {
String root = "enemy/" + type.name().toLowerCase();
⋮----
missing += requireRegion(art, root + "/corpse");
⋮----
for (BiomeEnemyRoster.Identity identity : BiomeEnemyRoster.Identity.values()) {
⋮----
String root = "enemy/biome/" + identity.name().toLowerCase();
for (Direction8 direction : Direction8.values()) {
String directionalRoot = root + "/" + direction.atlasToken();
missing += requireMotion(art, directionalRoot, "idle");
missing += requireMotion(art, directionalRoot, "run");
missing += requireMotion(art, directionalRoot, "attack");
missing += requireMotion(art, directionalRoot, "hit");
missing += requireMotion(art, directionalRoot, "death");
⋮----
for (BossIdentity identity : BossIdentity.values()) {
String root = "boss/" + identity.name().toLowerCase();
⋮----
if (missing == 0) Gdx.app.log("ArtManifest", "Production atlas validation passed.");
else Gdx.app.log("ArtManifest", "Production atlas incomplete: " + missing + " required entries missing.");
⋮----
private static int requireMotion(GameArt art, String root, String motion) {
return requireMotion(art, root + "/" + motion);
⋮----
private static int requireMotion(GameArt art, String key) {
if (art.hasAnimation(key)) return 0;
Gdx.app.log("ArtManifest", "Missing authored animation/region: " + key);
⋮----
private static int requireRegion(GameArt art, String key) {
if (art.hasRegion(key)) return 0;
Gdx.app.log("ArtManifest", "Missing authored region: " + key);
```

## File: src/main/java/com/deadlinezero/game/visual/ArtProfileCatalog.java
```java
/**
 * Production-art proportions and anchors. Values are world-space presentation data only;
 * gameplay collision radii remain authoritative and independent from sprite dimensions.
 */
public final class ArtProfileCatalog {
⋮----
private static final CharacterProfile REX = new CharacterProfile(1.72f, .60f, .29f, .23f);
private static final CharacterProfile NYX = new CharacterProfile(1.70f, .59f, .31f, .25f);
private static final CharacterProfile BASTION = new CharacterProfile(1.86f, .65f, .34f, .27f);
private static final CharacterProfile VOLT = new CharacterProfile(1.73f, .60f, .30f, .24f);
private static final CharacterProfile WRAITH = new CharacterProfile(1.68f, .58f, .30f, .23f);
⋮----
private static final CharacterProfile SHAMBLER = new CharacterProfile(1.70f, .48f, 0f, 0f);
private static final CharacterProfile RUNNER = new CharacterProfile(1.46f, .41f, 0f, 0f);
private static final CharacterProfile BRUTE = new CharacterProfile(2.20f, .66f, 0f, 0f);
private static final CharacterProfile RANGED = new CharacterProfile(1.64f, .48f, 0f, 0f);
private static final CharacterProfile ELITE = new CharacterProfile(2.55f, .76f, 0f, 0f);
private static final CharacterProfile SHIELDED = new CharacterProfile(2.28f, .68f, 0f, 0f);
private static final CharacterProfile REGENERATOR = new CharacterProfile(1.74f, .50f, 0f, 0f);
private static final CharacterProfile PHANTOM = new CharacterProfile(1.55f, .45f, 0f, 0f);
private static final CharacterProfile BOSS = new CharacterProfile(5.15f, 1.35f, 0f, 0f);
⋮----
public static CharacterProfile survivor(SurvivorCatalog.Survivor survivor) {
⋮----
public static CharacterProfile enemy(Enemy.Type type) {
```

## File: src/main/java/com/deadlinezero/game/visual/AudioManifest.java
```java
/** Central contract for authored combat audio expected in a production build. */
public final class AudioManifest {
⋮----
public static int validate() {
⋮----
if (Gdx.files.internal(path).exists()) continue;
⋮----
Gdx.app.log("AudioManifest", "Missing production audio: " + path);
⋮----
if (missing == 0) Gdx.app.log("AudioManifest", "Production combat audio validation passed.");
else Gdx.app.log("AudioManifest", "Production combat audio incomplete: " + missing + " required files missing.");
```

## File: src/main/java/com/deadlinezero/game/visual/AuthoredCoreDirectionalArt.java
```java
/**
 * Shipped/file-backed core directional art gateway. A complete seven-actor raster can replace
 * every high-resolution core bootstrap at once. It also owns lazy 64px boss art.
 */
public final class AuthoredCoreDirectionalArt implements Disposable {
⋮----
regions = texture == null ? null : split(texture, TOTAL_TILES);
⋮----
public static AuthoredCoreDirectionalArt create() {
Texture coreTexture = loadValidated(PATH, width(), height(), "authored core");
// Keep the gateway alive even when no raster is present so boss art remains lazy.
return new AuthoredCoreDirectionalArt(coreTexture);
⋮----
private static Texture loadValidated(String path, int expectedWidth, int expectedHeight, String label) {
if (!Gdx.files.internal(path).exists()) return null;
Texture loaded = new Texture(Gdx.files.internal(path));
if (loaded.getWidth() != expectedWidth || loaded.getHeight() != expectedHeight) {
loaded.dispose();
throw new IllegalStateException("Invalid " + label + " sheet dimensions: expected "
⋮----
loaded.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
⋮----
private static TextureRegion[] split(Texture source, int tileCount) {
⋮----
result[tile] = new TextureRegion(source, x, y, TILE, TILE);
⋮----
static int rows() { return (TOTAL_TILES + COLUMNS - 1) / COLUMNS; }
static int width() { return COLUMNS * TILE; }
static int height() { return rows() * TILE; }
⋮----
public boolean supports(String key) {
return firstTile(key) >= 0 || HighResBossDirectionalArt.firstTile(key) >= 0;
⋮----
public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
int first = firstTile(key);
⋮----
int count = frameCount(key);
int raw = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
int frame = count <= 1 ? 0 : (loop ? raw % count : Math.min(count - 1, raw));
⋮----
if (HighResBossDirectionalArt.firstTile(key) < 0) return null;
HighResBossDirectionalArt boss = ensureBossArt();
return boss == null ? null : boss.region(key, stateTime, frameDuration, loop);
⋮----
private HighResBossDirectionalArt ensureBossArt() {
⋮----
bossArt = HighResBossDirectionalArt.create();
⋮----
static int firstTile(String key) {
if (key == null || key.isBlank()) return -1;
int actor = actorIndex(key);
⋮----
String rest = key.substring(ROOTS[actor].length());
int slash = rest.indexOf('/');
if (slash <= 0 || slash >= rest.length() - 1) return -1;
int direction = directionIndex(rest.substring(0, slash));
int motion = motionOffset(rest.substring(slash + 1));
⋮----
static int frameCount(String key) {
if (firstTile(key) < 0) return 0;
String motion = key.substring(key.lastIndexOf('/') + 1);
⋮----
private static int actorIndex(String key) {
⋮----
for (int i = 0; i < ROOTS.length; i++) if (key.startsWith(ROOTS[i])) return i;
⋮----
private static int directionIndex(String token) {
⋮----
private static int motionOffset(String motion) {
⋮----
@Override public void dispose() {
if (bossArt != null) bossArt.dispose();
if (texture != null) texture.dispose();
```

## File: src/main/java/com/deadlinezero/game/visual/AuthoredVfxRenderer.java
```java
/** Optional atlas-driven VFX overlay. Missing authored FX simply leave procedural effects visible. */
public final class AuthoredVfxRenderer {
⋮----
public void draw(SpriteBatch batch, Player player, Iterable<Enemy> enemies, Pools pools) {
if (!art.authoredAvailable()) return;
batch.begin();
drawMuzzle(batch, player, enemies);
drawDash(batch, player);
drawLevelUp(batch, player);
drawLegendary(batch, player);
drawNullArchon(batch, enemies);
drawImpacts(batch, pools);
drawBossDeath(batch, pools);
batch.end();
⋮----
private void drawMuzzle(SpriteBatch batch, Player player, Iterable<Enemy> enemies) {
float age = CombatVisualEvents.playerShotAgeSeconds();
⋮----
TextureRegion region = art.effectOrNull("muzzle_fire", age, .025f);
⋮----
Enemy target = nearest(player, enemies);
float angle = target == null ? player.velocity.angleDeg() : MathUtils.atan2(
⋮----
// Rex now carries the rifle inside the authored character frames. Keep the transient flash
// tight to that baked weapon silhouette instead of using the older external-weapon reach.
⋮----
float h = w * region.getRegionHeight() / (float)Math.max(1, region.getRegionWidth());
⋮----
float x = player.position.x + MathUtils.cos(r) * .48f;
float y = player.position.y + MathUtils.sin(r) * .48f;
batch.draw(region, x - w * .15f, y - h * .5f, w * .15f, h * .5f, w, h, 1f, 1f, angle);
⋮----
private void drawDash(SpriteBatch batch, Player player) {
float age = CombatVisualEvents.dashAgeSeconds();
⋮----
TextureRegion region = art.effectOrNull("dash", age, .04f);
⋮----
batch.setColor(1f, 1f, 1f, MathUtils.clamp(1f - age / .24f, 0f, 1f));
batch.draw(region, player.position.x - size * .5f, player.position.y - size * .5f, size, size);
batch.setColor(Color.WHITE);
⋮----
private void drawLevelUp(SpriteBatch batch, Player player) {
float age = CombatVisualEvents.levelUpAgeSeconds();
⋮----
TextureRegion region = art.effectOrNull("level_up", age, .06f);
⋮----
private void drawLegendary(SpriteBatch batch, Player player) {
if (!player.alive || !player.legendary.hasAny()) return;
float stateTime = (TimeUtils.millis() % 120_000L) / 1000f;
if (player.legendary.hasOverdrive()) drawLegendaryLayer(batch, player, "legendary_overdrive", stateTime, 3.35f, .72f);
if (player.legendary.hasSingularity()) drawLegendaryLayer(batch, player, "legendary_singularity", stateTime, 3.75f, .64f);
if (player.legendary.hasApex()) drawLegendaryLayer(batch, player, "legendary_apex", stateTime, 4.15f, .58f);
⋮----
private void drawLegendaryLayer(SpriteBatch batch, Player player, String effect, float stateTime, float size, float alpha) {
TextureRegion region = art.loopingEffectOrNull(effect, stateTime, .075f);
⋮----
float pulse = 1f + MathUtils.sin(stateTime * 4.6f) * .045f;
⋮----
batch.setColor(1f, 1f, 1f, alpha);
batch.draw(region, player.position.x - drawSize * .5f, player.position.y - drawSize * .5f, drawSize, drawSize);
⋮----
private void drawNullArchon(SpriteBatch batch, Iterable<Enemy> enemies) {
⋮----
|| enemy.bossCombat.identity() != BossIdentity.NULL_ARCHON) continue;
⋮----
int phase = enemy.bossPhases == null ? 1 : enemy.bossPhases.phase();
TextureRegion aura = art.loopingEffectOrNull("null_archon_aura", stateTime, .075f);
⋮----
float pulse = 1f + MathUtils.sin(stateTime * 5.2f) * .055f;
⋮----
batch.setColor(.82f, .86f, 1f, phase >= 3 ? .74f : .56f);
batch.draw(aura, enemy.position.x - size * .5f, enemy.position.y - size * .5f, size, size);
⋮----
if (enemy.bossCombat.summonTelegraphing(phase)) {
float progress = enemy.bossCombat.summonTelegraphProgress(phase);
TextureRegion portal = art.loopingEffectOrNull("null_archon_portal", stateTime, .055f);
⋮----
batch.setColor(.82f, .72f, 1f, .52f + progress * .42f);
batch.draw(portal, enemy.position.x - size * .5f, y - size * .5f, size, size);
⋮----
TextureRegion fracture = art.loopingEffectOrNull("null_archon_fracture", stateTime, .065f);
⋮----
float size = 6.4f + MathUtils.sin(stateTime * 3.8f) * .35f;
batch.setColor(.74f, .64f, 1f, .44f);
batch.draw(fracture, enemy.position.x - size * .5f, enemy.position.y - size * .5f, size, size);
⋮----
private void drawImpacts(SpriteBatch batch, Pools pools) {
⋮----
float age = Math.max(0f, fx.maxLife - fx.life);
String name = classify(fx.color);
TextureRegion region = art.effectOrNull(name, age, .035f);
⋮----
float size = Math.max(.5f, fx.size * 2.15f);
batch.setColor(1f, 1f, 1f, MathUtils.clamp(fx.life / Math.max(.001f, fx.maxLife), 0f, 1f));
batch.draw(region, fx.position.x - size * .5f, fx.position.y - size * .5f, size, size);
⋮----
private void drawBossDeath(SpriteBatch batch, Pools pools) {
⋮----
TextureRegion region = art.effectOrNull("boss_explosion", fx.age, .055f);
⋮----
batch.draw(region, fx.x - size * .5f, fx.y - size * .5f, size, size);
⋮----
private String classify(Color c) {
⋮----
private Enemy nearest(Player player, Iterable<Enemy> enemies) {
⋮----
float d2 = player.position.dst2(enemy.position);
```

## File: src/main/java/com/deadlinezero/game/visual/BiomeDirectionalBootstrapArt.java
```java
/**
 * Dedicated generated directional art for biome-signature enemies and the Null Archon.
 * It also owns shipped/high-resolution core-actor layers so GameArt keeps one priority gateway.
 * Final atlas regions still override this layer through GameArt.
 */
public final class BiomeDirectionalBootstrapArt implements Disposable {
⋮----
public static BiomeDirectionalBootstrapArt create() {
⋮----
authored = AuthoredCoreDirectionalArt.create();
⋮----
// Generated high-resolution/core layers remain available if shipped art is invalid.
⋮----
return new BiomeDirectionalBootstrapArt(authored);
⋮----
static int rows() { return (TOTAL_TILES + COLUMNS - 1) / COLUMNS; }
static int width() { return COLUMNS * TILE; }
static int height() { return rows() * TILE; }
⋮----
public boolean supports(String key) {
return (authoredCore != null && authoredCore.supports(key))
|| HighResDirectionalBootstrapArt.firstTile(key) >= 0
|| firstTile(key) >= 0;
⋮----
public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
⋮----
TextureRegion authored = authoredCore.region(key, stateTime, frameDuration, loop);
⋮----
if (HighResDirectionalBootstrapArt.firstTile(key) >= 0) {
HighResDirectionalBootstrapArt highRes = ensureHighResCore();
⋮----
TextureRegion region = highRes.region(key, stateTime, frameDuration, loop);
⋮----
int first = firstTile(key);
⋮----
TextureRegion[] biomeRegions = ensureBiomeRegions();
⋮----
int count = frameCount(key);
int raw = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
int frame = count <= 1 ? 0 : (loop ? raw % count : Math.min(count - 1, raw));
⋮----
private TextureRegion[] ensureBiomeRegions() {
⋮----
Pixmap p = new Pixmap(width(), height(), Pixmap.Format.RGBA8888);
p.setBlending(Pixmap.Blending.SourceOver);
⋮----
for (int actor = 0; actor < ROOTS.length; actor++) drawActorSet(p, actor, actor * ACTOR_BLOCK);
texture = new Texture(p);
texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
⋮----
regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
⋮----
if (texture != null) texture.dispose();
⋮----
p.dispose();
⋮----
private HighResDirectionalBootstrapArt ensureHighResCore() {
⋮----
highResCore = HighResDirectionalBootstrapArt.create();
⋮----
static int firstTile(String key) {
int actor = actorIndex(key);
⋮----
String rest = key.substring(ROOTS[actor].length());
int slash = rest.indexOf('/');
if (slash <= 0 || slash >= rest.length() - 1) return -1;
int direction = directionIndex(rest.substring(0, slash));
int motion = motionOffset(rest.substring(slash + 1));
⋮----
static int frameCount(String key) {
if (firstTile(key) < 0) return 0;
String motion = key.substring(key.lastIndexOf('/') + 1);
⋮----
static String root(BiomeEnemyRoster.Identity identity) {
⋮----
return "enemy/biome/" + identity.name().toLowerCase();
⋮----
private static int actorIndex(String key) {
⋮----
for (int i = 0; i < ROOTS.length; i++) if (key.startsWith(ROOTS[i])) return i;
⋮----
private static int directionIndex(String token) {
⋮----
private static int motionOffset(String motion) {
⋮----
private static void drawActorSet(Pixmap p, int actor, int base) {
⋮----
drawFrame(p, b, actor, dirs[d][0], dirs[d][1], 0, 0);
drawFrame(p, b + 1, actor, dirs[d][0], dirs[d][1], 1, 0);
drawFrame(p, b + 2, actor, dirs[d][0], dirs[d][1], 1, 1);
drawFrame(p, b + 3, actor, dirs[d][0], dirs[d][1], 1, 2);
drawFrame(p, b + 4, actor, dirs[d][0], dirs[d][1], 2, 0);
drawFrame(p, b + 5, actor, dirs[d][0], dirs[d][1], 2, 1);
drawFrame(p, b + 6, actor, dirs[d][0], dirs[d][1], 3, 0);
drawFrame(p, b + 7, actor, dirs[d][0], dirs[d][1], 4, 0);
drawFrame(p, b + 8, actor, dirs[d][0], dirs[d][1], 4, 1);
drawFrame(p, b + 9, actor, dirs[d][0], dirs[d][1], 4, 2);
⋮----
private static void drawFrame(Pixmap p, int tile, int actor, int dx, int dy, int motion, int frame) {
⋮----
primary(p, actor);
p.drawLine(ox + 7 + frame * 2, oy + 17 + frame * 3, ox + 24 + frame, oy + 22 + frame * 3);
secondary(p, actor);
p.fillCircle(ox + 16 + frame * 2, oy + 18 + frame * 3, Math.max(3, radius - frame));
⋮----
p.fillCircle(cx, cy, radius);
⋮----
p.fillCircle(cx + sx * 2, cy - 7 + sy * 2, Math.max(4, radius - 2));
p.drawLine(cx - px * 3, cy + 5 - py * 2, cx - px * 4, oy + 29 - py * 2);
p.drawLine(cx + px * 3, cy + 5 + py * 2, cx + px * 4, oy + 29 + py * 2);
⋮----
accent(p, actor);
drawSignature(p, actor, cx, cy, sx, sy, px, py, motion, frame);
⋮----
p.drawLine(cx, cy + 1, cx + sx * (7 + reach), cy + 1 + sy * (7 + reach));
⋮----
p.setColor(1f, .95f, .88f, 1f);
p.drawLine(ox + 5, oy + 7, ox + 12, oy + 14);
p.drawLine(ox + 5, oy + 14, ox + 12, oy + 7);
⋮----
private static void drawSignature(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py, int motion, int frame) {
⋮----
p.drawLine(cx - px * 8, cy + 4 - py * 8, cx + px * 8, cy + 4 + py * 8);
p.drawLine(cx - sx * 7, cy - sy * 7, cx - sx * 12, cy - sy * 12);
p.fillCircle(cx + sx * 7, cy - 5 + sy * 7, 2);
⋮----
p.fillCircle(cx - px * 8, cy - py * 8, 3);
p.fillCircle(cx + px * 8, cy + py * 8, 3);
p.drawLine(cx + sx * 5, cy + sy * 5, cx + sx * (13 + (motion == 2 ? frame * 3 : 0)), cy + sy * (13 + (motion == 2 ? frame * 3 : 0)));
⋮----
p.drawRectangle(cx - 8 + sx * 3, cy - 7 + sy * 3, 16, 13);
p.drawLine(cx - px * 7, cy - py * 7, cx + px * 7, cy + py * 7);
⋮----
p.drawLine(cx - px * 4, cy - 7 - py * 4, cx - px * 8 - sx * 3, cy - 13 - py * 8 - sy * 3);
p.drawLine(cx + px * 4, cy - 7 + py * 4, cx + px * 8 - sx * 3, cy - 13 + py * 8 - sy * 3);
p.drawLine(cx - sx * 6, cy - sy * 6, cx - sx * 13 + px * 4, cy - sy * 13 + py * 4);
⋮----
p.drawCircle(cx + sx * 3, cy - 7 + sy * 3, 7);
p.fillCircle(cx + sx * 5, cy - 7 + sy * 5, 2);
p.drawLine(cx - px * 8, cy - py * 8, cx + px * 8, cy + py * 8);
⋮----
p.drawCircle(cx, cy, 10);
p.drawCircle(cx, cy, 6);
p.drawLine(cx - px * 10, cy - py * 10, cx + px * 10, cy + py * 10);
⋮----
p.drawCircle(cx + sx * 2, cy + sy * 2, 11);
p.drawLine(cx - px * 7, cy - 9 - py * 7, cx - px * 10 - sx * 3, cy - 15 - py * 10 - sy * 3);
p.drawLine(cx + px * 7, cy - 9 + py * 7, cx + px * 10 - sx * 3, cy - 15 + py * 10 - sy * 3);
p.drawLine(cx - px * 9, cy + 7 - py * 9, cx - px * 13, cy + 14 - py * 13);
p.drawLine(cx + px * 9, cy + 7 + py * 9, cx + px * 13, cy + 14 + py * 13);
⋮----
private static void primary(Pixmap p, int actor) { setPalette(p, PRIMARY[actor]); }
private static void secondary(Pixmap p, int actor) { setPalette(p, SECONDARY[actor]); }
private static void accent(Pixmap p, int actor) { setPalette(p, ACCENT[actor]); }
private static void setPalette(Pixmap p, float[] c) { p.setColor(c[0], c[1], c[2], 1f); }
⋮----
@Override public void dispose() {
if (authoredCore != null) authoredCore.dispose();
if (highResCore != null) highResCore.dispose();
```

## File: src/main/java/com/deadlinezero/game/visual/BootstrapArtCatalog.java
```java
/**
 * Maps the compact legacy bootstrap art sheet to the production naming contract.
 * Final atlas regions and the generated bootstrap textures take priority.
 */
public final class BootstrapArtCatalog {
⋮----
public static boolean supports(String key) { return tileIndex(key) >= 0; }
⋮----
public static TextureRegion region(Texture texture, String key) {
int tile = tileIndex(key);
⋮----
if (x + TILE > texture.getWidth() || y + TILE > texture.getHeight()) return null;
return new TextureRegion(texture, x, y, TILE, TILE);
⋮----
/** Returns a 4x4 sheet tile index, or -1 when the key is outside the bootstrap contract. */
static int tileIndex(String key) {
if (key == null || key.isBlank()) return -1;
⋮----
if (key.startsWith("survivor/rex/")) return 0;
if (key.startsWith("survivor/nyx/")) return 1;
if (key.startsWith("survivor/bastion/")) return 2;
if (key.startsWith("survivor/volt/")) return 3;
if (key.startsWith("survivor/wraith/")) return 10;
⋮----
if (key.startsWith("enemy/shambler/")) return 4;
if (key.startsWith("enemy/runner/")) return 5;
if (key.startsWith("enemy/brute/")) return 6;
if (key.startsWith("enemy/ranged/")) return 7;
if (key.startsWith("enemy/elite/")) return 8;
if (key.startsWith("enemy/shielded/")) return 6;
if (key.startsWith("enemy/regenerator/")) return 4;
if (key.startsWith("enemy/phantom/")) return 10;
if (key.startsWith("enemy/boss/")) return 9;
if (key.startsWith("boss/alpha/")) return 9;
if (key.startsWith("boss/revenant/")) return 10;
if (key.startsWith("boss/warden/")) return 6;
if (key.startsWith("boss/harvester/")) return 12;
if (key.startsWith("boss/null_archon/")) return 14;
⋮----
if (key.equals("weapon/ar9")) return 11;
if (key.equals("weapon/scattergun")) return 12;
if (key.equals("weapon/rail_rifle")) return 13;
if (key.equals("weapon/inferno_smg")) return 12;
if (key.equals("weapon/cryo_lance")) return 13;
if (key.equals("weapon/arc_carbine")) return 14;
if (key.equals("weapon/breacher")) return 11;
if (key.equals("weapon/ion_needle")) return 14;
if (key.equals("weapon/cinder_cannon")) return 12;
if (key.equals("weapon/tempest_burst")) return 14;
if (key.equals("weapon/whiteout_shard")) return 13;
if (key.equals("weapon/phoenix_repeater")) return 12;
⋮----
if (key.equals("fx/muzzle_fire") || key.equals("fx/impact_fire") || key.equals("fx/boss_explosion")) return 12;
if (key.equals("fx/impact_frost") || key.equals("fx/dash") || key.equals("fx/impact_energy")) return 13;
if (key.equals("fx/impact_shock") || key.equals("fx/level_up")) return 14;
if (key.equals("fx/impact_kill") || key.equals("fx/legendary_overdrive")
|| key.equals("fx/legendary_singularity") || key.equals("fx/legendary_apex")) return 10;
⋮----
if (key.equals("environment/decal/crack_a")) return 7;
if (key.equals("environment/decal/blood_a")) return 9;
if (key.equals("environment/prop/barrier_a")) return 15;
```

## File: src/main/java/com/deadlinezero/game/visual/BootstrapEnvironmentArt.java
```java
/** Compact deterministic environment art generated once at startup. */
public final class BootstrapEnvironmentArt implements Disposable {
⋮----
regions[i] = new TextureRegion(texture, x, y, TILE, TILE);
⋮----
public static BootstrapEnvironmentArt create() {
⋮----
Pixmap p = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
⋮----
for (int i = 0; i < KEYS.length; i++) drawTile(p, i);
Texture texture = new Texture(p);
texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
return new BootstrapEnvironmentArt(texture);
⋮----
p.dispose();
⋮----
public boolean supports(String key) { return indexOf(key) >= 0; }
⋮----
public TextureRegion region(String key) {
int index = indexOf(key);
⋮----
static int indexOf(String key) {
⋮----
for (int i = 0; i < KEYS.length; i++) if (KEYS[i].equals(key)) return i;
⋮----
private static void drawTile(Pixmap p, int tile) {
⋮----
case 0, 1, 2 -> drawConcrete(p, ox, oy, tile);
case 3 -> drawHazard(p, ox, oy);
case 4 -> drawCrack(p, ox, oy);
case 5 -> drawBlood(p, ox, oy);
case 6 -> drawScorch(p, ox, oy);
case 7 -> drawBarrier(p, ox, oy);
case 8, 9 -> drawDebris(p, ox, oy, tile - 8);
case 10, 11 -> drawWall(p, ox, oy, tile - 10);
case 12 -> drawCrate(p, ox, oy);
case 13 -> drawBeacon(p, ox, oy);
⋮----
private static void drawConcrete(Pixmap p, int ox, int oy, int variant) {
⋮----
p.setColor(base, base + .018f, base + .028f, 1f);
p.fillRectangle(ox, oy, TILE, TILE);
p.setColor(.15f, .18f, .20f, .70f);
p.drawRectangle(ox, oy, TILE - 1, TILE - 1);
p.setColor(.07f, .09f, .11f, .55f);
p.drawLine(ox + 8 + variant * 5, oy + 18, ox + 25 + variant * 3, oy + 16);
p.drawLine(ox + 25 + variant * 3, oy + 16, ox + 32, oy + 28 + variant * 2);
p.setColor(.22f, .25f, .27f, .28f);
p.fillCircle(ox + 49 - variant * 7, oy + 12 + variant * 9, 2);
p.fillCircle(ox + 16 + variant * 11, oy + 48 - variant * 5, 1);
⋮----
private static void drawHazard(Pixmap p, int ox, int oy) {
p.setColor(.095f, .105f, .11f, 1f);
⋮----
p.setColor(.72f, .48f, .08f, .82f);
p.fillTriangle(ox + x, oy + TILE, ox + x + 8, oy + TILE, ox + x + 40, oy);
p.setColor(.15f, .13f, .08f, .55f);
p.drawLine(ox + x + 8, oy + TILE, ox + x + 40, oy);
⋮----
private static void drawCrack(Pixmap p, int ox, int oy) {
p.setColor(0f, 0f, 0f, 0f); p.fillRectangle(ox, oy, TILE, TILE);
p.setColor(.03f, .035f, .04f, .88f);
p.drawLine(ox + 10, oy + 12, ox + 27, oy + 28);
p.drawLine(ox + 27, oy + 28, ox + 48, oy + 19);
p.drawLine(ox + 27, oy + 28, ox + 35, oy + 50);
p.drawLine(ox + 20, oy + 22, ox + 15, oy + 37);
⋮----
private static void drawBlood(Pixmap p, int ox, int oy) {
⋮----
p.setColor(.34f, .025f, .035f, .72f);
p.fillCircle(ox + 30, oy + 34, 13);
p.fillCircle(ox + 44, oy + 28, 7);
p.fillCircle(ox + 18, oy + 44, 5);
p.setColor(.62f, .045f, .055f, .36f);
p.fillCircle(ox + 26, oy + 30, 6);
⋮----
private static void drawScorch(Pixmap p, int ox, int oy) {
⋮----
p.setColor(.015f, .012f, .010f, .68f);
p.fillCircle(ox + 32, oy + 33, 18);
p.setColor(.20f, .075f, .025f, .42f);
p.drawCircle(ox + 32, oy + 33, 14);
p.drawCircle(ox + 32, oy + 33, 17);
⋮----
private static void drawBarrier(Pixmap p, int ox, int oy) {
⋮----
p.setColor(.055f, .065f, .075f, .72f); p.fillRectangle(ox + 6, oy + 46, 52, 9);
p.setColor(.20f, .23f, .25f, 1f); p.fillRectangle(ox + 7, oy + 18, 50, 29);
p.setColor(.08f, .10f, .12f, 1f); p.fillRectangle(ox + 11, oy + 22, 42, 21);
p.setColor(.88f, .56f, .06f, 1f);
for (int x = 12; x < 53; x += 12) p.fillRectangle(ox + x, oy + 24, 6, 17);
p.setColor(.34f, .38f, .40f, 1f); p.drawRectangle(ox + 7, oy + 18, 50, 29);
⋮----
private static void drawDebris(Pixmap p, int ox, int oy, int variant) {
⋮----
p.setColor(.12f, .14f, .15f, .92f);
p.fillRectangle(ox + 12, oy + 35, 19, 9);
p.fillRectangle(ox + 34, oy + 24 + variant * 5, 16, 8);
p.setColor(.30f, .33f, .34f, .85f);
p.drawLine(ox + 8, oy + 49, ox + 27, oy + 21);
p.drawLine(ox + 30, oy + 52, ox + 52, oy + 37);
p.setColor(.74f, .46f, .07f, .78f);
p.fillRectangle(ox + 20 + variant * 10, oy + 19, 4, 15);
⋮----
private static void drawWall(Pixmap p, int ox, int oy, int variant) {
⋮----
p.setColor(.055f, .065f, .078f, .98f); p.fillRectangle(ox + 2, oy + 12, 60, 42);
p.setColor(.16f, .19f, .22f, 1f); p.fillRectangle(ox + 5, oy + 15, 54, 34);
p.setColor(.08f, .095f, .11f, 1f); p.fillRectangle(ox + 8, oy + 19, 48, 24);
p.setColor(.31f, .34f, .36f, .86f);
p.drawRectangle(ox + 5, oy + 15, 54, 34);
p.drawLine(ox + 32, oy + 16, ox + 32, oy + 48);
p.setColor(variant == 0 ? .12f : .72f, variant == 0 ? .62f : .22f, variant == 0 ? .74f : .08f, .88f);
p.fillRectangle(ox + 10, oy + 22, 4, 17);
p.fillRectangle(ox + 50, oy + 22, 4, 17);
⋮----
private static void drawCrate(Pixmap p, int ox, int oy) {
⋮----
p.setColor(.16f, .18f, .19f, .92f); p.fillRectangle(ox + 10, oy + 16, 44, 38);
p.setColor(.30f, .33f, .34f, 1f); p.drawRectangle(ox + 10, oy + 16, 44, 38);
p.drawLine(ox + 14, oy + 20, ox + 50, oy + 50);
p.drawLine(ox + 50, oy + 20, ox + 14, oy + 50);
p.setColor(.82f, .50f, .06f, .92f); p.fillRectangle(ox + 27, oy + 31, 10, 5);
⋮----
private static void drawBeacon(Pixmap p, int ox, int oy) {
⋮----
p.setColor(.07f, .08f, .09f, .95f); p.fillRectangle(ox + 27, oy + 25, 10, 28);
p.setColor(.24f, .28f, .31f, 1f); p.fillRectangle(ox + 22, oy + 49, 20, 6);
p.setColor(.10f, .68f, .82f, .90f); p.fillCircle(ox + 32, oy + 20, 8);
p.setColor(.68f, .95f, 1f, .72f); p.fillCircle(ox + 32, oy + 20, 3);
⋮----
@Override public void dispose() { texture.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/visual/BootstrapVfxArt.java
```java
/**
 * Compact deterministic multi-frame VFX sheet used when the production atlas has not supplied
 * an effect yet. Geometry/key lookup is always available; GPU allocation happens lazily on use.
 */
public final class BootstrapVfxArt implements Disposable {
⋮----
public static BootstrapVfxArt create() {
return new BootstrapVfxArt();
⋮----
public boolean supports(String key) { return firstTile(key) >= 0; }
⋮----
public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
int first = firstTile(key);
⋮----
TextureRegion[] loadedRegions = ensureRegions();
⋮----
int count = frameCount(key);
int rawFrame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
int frame = loop ? rawFrame % count : Math.min(count - 1, rawFrame);
⋮----
private TextureRegion[] ensureRegions() {
⋮----
Pixmap pixmap = new Pixmap(width(), height(), Pixmap.Format.RGBA8888);
pixmap.setBlending(Pixmap.Blending.SourceOver);
⋮----
drawFrame(pixmap, effect, frame, effect * FRAMES_PER_EFFECT + frame);
⋮----
texture = new Texture(pixmap);
texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
⋮----
regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
⋮----
if (texture != null) texture.dispose();
⋮----
pixmap.dispose();
⋮----
static int firstTile(String key) {
int effect = effectIndex(key);
⋮----
static int frameCount(String key) {
⋮----
case 0 -> 4;          // muzzle
case 3, 4, 5, 6, 7 -> 5; // impacts
case 8 -> 8;          // boss explosion
⋮----
static int effectIndex(String key) {
⋮----
for (int i = 0; i < ROOTS.length; i++) if (ROOTS[i].equals(key)) return i;
⋮----
static int rows() { return (TOTAL_TILES + COLUMNS - 1) / COLUMNS; }
static int width() { return COLUMNS * TILE; }
static int height() { return rows() * TILE; }
⋮----
private static void drawFrame(Pixmap p, int effect, int frame, int tile) {
⋮----
float t = frame / (float)Math.max(1, frameCount(ROOTS[effect]) - 1);
⋮----
case 0 -> drawMuzzle(p, cx, cy, frame);
case 1 -> drawDash(p, cx, cy, t);
case 2 -> drawLevelUp(p, cx, cy, t);
case 3 -> drawImpact(p, cx, cy, t, .30f, .74f, 1f);
case 4 -> drawImpact(p, cx, cy, t, 1f, .24f, .08f);
case 5 -> drawImpact(p, cx, cy, t, .35f, .88f, 1f);
case 6 -> drawShock(p, cx, cy, t);
case 7 -> drawImpact(p, cx, cy, t, .42f, 1f, .42f);
case 8 -> drawBossExplosion(p, cx, cy, t);
case 9 -> drawLegendary(p, cx, cy, frame, 1f, .34f, .16f, 0);
case 10 -> drawLegendary(p, cx, cy, frame, .50f, .38f, 1f, 1);
case 11 -> drawLegendary(p, cx, cy, frame, 1f, .82f, .24f, 2);
⋮----
private static void drawMuzzle(Pixmap p, int cx, int cy, int frame) {
⋮----
set(p, 1f, .96f, .72f, 1f);
p.fillCircle(cx - 5, cy, Math.max(2, 6 - frame));
set(p, 1f, .48f, .10f, .92f);
p.fillTriangle(cx - 3, cy - 6, cx + reach, cy, cx - 3, cy + 6);
set(p, 1f, .88f, .32f, 1f);
p.drawLine(cx, cy, cx + reach + 5, cy);
⋮----
private static void drawDash(Pixmap p, int cx, int cy, float t) {
int outer = 8 + Math.round(t * 13f);
set(p, .25f, .86f, 1f, 1f - t * .55f);
p.drawCircle(cx, cy, outer);
p.drawCircle(cx, cy, Math.max(3, outer - 4));
int streak = 13 + Math.round(t * 10f);
p.drawLine(cx - streak, cy - 7, cx + 5, cy - 2);
p.drawLine(cx - streak, cy + 7, cx + 5, cy + 2);
⋮----
private static void drawLevelUp(Pixmap p, int cx, int cy, float t) {
int radius = 8 + Math.round(t * 12f);
set(p, .45f, 1f, .72f, 1f - t * .40f);
p.drawCircle(cx, cy, radius);
p.drawCircle(cx, cy, Math.max(2, radius - 6));
set(p, .84f, 1f, .92f, 1f - t * .25f);
⋮----
int x1 = cx + (int)(Math.cos(a) * 6);
int y1 = cy + (int)(Math.sin(a) * 6);
int x2 = cx + (int)(Math.cos(a) * (radius + 5));
int y2 = cy + (int)(Math.sin(a) * (radius + 5));
p.drawLine(x1, y1, x2, y2);
⋮----
private static void drawImpact(Pixmap p, int cx, int cy, float t, float r, float g, float b) {
int core = Math.max(2, Math.round(6f * (1f - t)));
int radius = 5 + Math.round(t * 14f);
set(p, r, g, b, 1f - t * .45f);
p.fillCircle(cx, cy, core);
⋮----
int x1 = cx + (int)(Math.cos(a) * 4);
int y1 = cy + (int)(Math.sin(a) * 4);
⋮----
private static void drawShock(Pixmap p, int cx, int cy, float t) {
set(p, .78f, .36f, 1f, 1f - t * .40f);
int spread = 5 + Math.round(t * 13f);
⋮----
p.drawLine(x, cy - spread, x + (i % 2 == 0 ? 5 : -5), cy - 2);
p.drawLine(x + (i % 2 == 0 ? 5 : -5), cy - 2, x, cy + spread);
⋮----
p.drawCircle(cx, cy, 5 + Math.round(t * 10f));
⋮----
private static void drawBossExplosion(Pixmap p, int cx, int cy, float t) {
int outer = 7 + Math.round(t * 17f);
set(p, 1f, .20f + t * .30f, .06f, 1f - t * .45f);
p.fillCircle(cx, cy, Math.max(2, Math.round(8f * (1f - t))));
⋮----
set(p, 1f, .82f, .26f, 1f - t * .30f);
⋮----
int inner = 6 + Math.round(t * 5f);
int x1 = cx + (int)(Math.cos(a) * inner);
int y1 = cy + (int)(Math.sin(a) * inner);
int x2 = cx + (int)(Math.cos(a) * (outer + 8));
int y2 = cy + (int)(Math.sin(a) * (outer + 8));
⋮----
private static void drawLegendary(Pixmap p, int cx, int cy, int frame, float r, float g, float b, int style) {
⋮----
set(p, r, g, b, .88f);
⋮----
p.drawCircle(cx, cy, radius - 5);
⋮----
int x1 = cx + (int)(Math.cos(a) * (radius - 4));
int y1 = cy + (int)(Math.sin(a) * (radius - 4));
int x2 = cx + (int)(Math.cos(a) * (radius + 6));
int y2 = cy + (int)(Math.sin(a) * (radius + 6));
⋮----
set(p, .10f, .08f, .18f, .92f);
p.fillCircle(cx, cy, 7);
⋮----
set(p, 1f, .96f, .72f, .95f);
p.fillCircle(cx, cy, 3 + frame % 2);
⋮----
private static void set(Pixmap p, float r, float g, float b, float a) { p.setColor(r, g, b, a); }
⋮----
@Override public void dispose() {
```

## File: src/main/java/com/deadlinezero/game/visual/BossPhaseTransitionProfile.java
```java
/** Data-only presentation contract for boss phase transitions. */
public final class BossPhaseTransitionProfile {
⋮----
public static Spec forPhase(BossIdentity identity, int phase) {
⋮----
int safePhase = Math.max(2, Math.min(3, phase));
⋮----
return new Spec(duration, radius, pitch, vibration);
```

## File: src/main/java/com/deadlinezero/game/visual/BossRevealCameraProfile.java
```java
/** Pure timing/comfort profile for the short non-blocking camera reveal when a boss enters. */
public final class BossRevealCameraProfile {
⋮----
public static float envelope(float remainingSeconds) {
float remaining = MathUtils.clamp(remainingSeconds, 0f, DURATION);
⋮----
return MathUtils.sin(progress * MathUtils.PI);
⋮----
public static float focusWeight(float envelope, boolean reducedMotion) {
⋮----
return MAX_FOCUS_WEIGHT * MathUtils.clamp(envelope, 0f, 1f);
⋮----
public static float zoom(float baseZoom, float envelope, boolean reducedMotion) {
⋮----
return baseZoom + MAX_ZOOM_OUT * MathUtils.clamp(envelope, 0f, 1f);
```

## File: src/main/java/com/deadlinezero/game/visual/ChampionBadgeRenderer.java
```java
/**
 * Draws compact, shape-based champion markers under authored enemy sprites.
 *
 * Champion identity must not depend on color alone, but the previous two-letter plates were almost
 * as large as small enemies and read like debug labels. These markers keep the accessibility signal
 * while staying subordinate to the actual actor silhouette.
 */
public final class ChampionBadgeRenderer {
private final ShapeRenderer shapes = new ShapeRenderer();
⋮----
public void draw(SpriteBatch batch, Array<Enemy> enemies) {
⋮----
shapes.setProjectionMatrix(batch.getProjectionMatrix());
shapes.begin(ShapeRenderer.ShapeType.Filled);
⋮----
ChampionVariantPresentation.Marker marker = ChampionVariantPresentation.marker(enemy.variant);
⋮----
float size = MathUtils.clamp(enemy.radius * .72f, .22f, .46f);
⋮----
drawMarker(marker, cx, cy, size);
⋮----
shapes.end();
⋮----
private void drawMarker(ChampionVariantPresentation.Marker marker, float cx, float cy, float size) {
float t = Math.max(.026f, size * .12f);
⋮----
shapes.setColor(.015f, .022f, .028f, .44f);
shapes.circle(cx, cy, r * 1.28f, 20);
shapes.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, .72f);
⋮----
shapes.triangle(cx + r * .82f, cy,
⋮----
shapes.rect(cx - r * .62f, cy - r * .44f, t, r * .88f);
shapes.rect(cx + r * .62f - t, cy - r * .44f, t, r * .88f);
shapes.rect(cx - r * .42f, cy + r * .52f - t, r * .84f, t);
shapes.rect(cx - r * .42f, cy - r * .52f, r * .84f, t);
⋮----
shapes.rectLine(cx - r * .48f + off, cy - r * .48f,
⋮----
shapes.circle(cx, cy, r * .24f, 12);
⋮----
float dx = MathUtils.cosDeg(a) * r * .62f;
float dy = MathUtils.sinDeg(a) * r * .62f;
shapes.circle(cx + dx, cy + dy, r * .13f, 10);
⋮----
shapes.rect(cx - t * .6f, cy - r * .60f, t * 1.2f, r * 1.20f);
shapes.rect(cx - r * .60f, cy - t * .6f, r * 1.20f, t * 1.2f);
⋮----
shapes.rectLine(cx - r * .52f, cy - r * .52f, cx + r * .52f, cy + r * .52f, t);
shapes.rectLine(cx - r * .52f, cy + r * .52f, cx + r * .52f, cy - r * .52f, t);
⋮----
shapes.triangle(cx, cy + r * .70f,
⋮----
shapes.setColor(.015f, .022f, .028f, .88f);
shapes.circle(cx, cy, r * .22f, 12);
⋮----
shapes.circle(cx, cy, r * .40f, 18);
⋮----
shapes.circle(cx, cy, r * .22f, 14);
⋮----
shapes.rect(cx - t * .45f, cy + r * .38f, t * .9f, r * .28f);
shapes.rect(cx - t * .45f, cy - r * .66f, t * .9f, r * .28f);
shapes.rect(cx + r * .38f, cy - t * .45f, r * .28f, t * .9f);
shapes.rect(cx - r * .66f, cy - t * .45f, r * .28f, t * .9f);
⋮----
public void dispose() {
shapes.dispose();
```

## File: src/main/java/com/deadlinezero/game/visual/ChampionVariantPresentation.java
```java
/** Stable non-color semantic cue for champion variants. */
public final class ChampionVariantPresentation {
⋮----
public static Marker marker(Enemy.Variant variant) {
```

## File: src/main/java/com/deadlinezero/game/visual/CharacterSpriteRenderer.java
```java
/** Draws authored character art with event-driven attacks and independent per-entity state clocks. */
public final class CharacterSpriteRenderer {
private static final class Clock {
⋮----
public void update(float dt) { frameDelta = Math.max(0f, dt); }
⋮----
public boolean authoredAvailable() { return art.authoredAvailable(); }
⋮----
public void draw(SpriteBatch batch, Player player, Array<Enemy> enemies) {
if (!art.authoredAvailable()) return;
batch.begin();
drawPlayer(batch, player, enemies);
for (Enemy enemy : enemies) if (enemy.alive) drawEnemy(batch, enemy);
batch.end();
⋮----
private void drawPlayer(SpriteBatch batch, Player player, Array<Enemy> enemies) {
var survivor = RunLoadoutContext.survivor();
⋮----
else if (playerAttackWindow(survivor)) motion = GameArt.Motion.ATTACK;
else motion = player.velocity.len2() > .04f ? GameArt.Motion.RUN : GameArt.Motion.IDLE;
⋮----
Clock clock = clock(player, motion);
Enemy target = motion == GameArt.Motion.ATTACK ? nearestEnemy(player, enemies) : null;
⋮----
clock.direction = resolvePlayerFacing(
⋮----
ArtProfileCatalog.CharacterProfile profile = ArtProfileCatalog.survivor(survivor);
TextureRegion region = art.survivor(survivor, motion, clock.direction, clock.time);
float h = profile.height();
float aspect = region.getRegionWidth() / (float)Math.max(1, region.getRegionHeight());
⋮----
int pieces = RunLoadoutContext.ascensionSetPieces();
float pulse = .5f + .5f * MathUtils.sin(clock.time * (pieces >= 4 ? 7.5f : 4.5f));
⋮----
float alpha = player.invulnerable() ? .78f : 1f;
drawMaterialized(batch, region,
player.position.x, player.position.y - profile.footOffset(),
w * scale, h * scale, r, g, b, alpha, ActorMaterialProfile.player());
batch.setColor(1f, 1f, 1f, 1f);
⋮----
static Direction8 resolvePlayerFacing(float moveX, float moveY, float aimX, float aimY,
⋮----
return Direction8.fromVector(aimX, aimY, fallback);
⋮----
return Direction8.fromVector(moveX, moveY, fallback);
⋮----
private Enemy nearestEnemy(Player player, Array<Enemy> enemies) {
⋮----
float d2 = player.position.dst2(enemy.position);
⋮----
private boolean playerAttackWindow(com.deadlinezero.game.meta.SurvivorCatalog.Survivor survivor) {
float frame = AnimationProfileCatalog.survivor(survivor).attack();
float window = Math.max(.085f, Math.min(.18f, frame * 2.5f));
return CombatVisualEvents.playerShotAgeSeconds() <= window;
⋮----
private void drawEnemy(SpriteBatch batch, Enemy enemy) {
⋮----
if (enemy.hitFlash > .22f || enemy.attack.state() == EnemyState.STUNNED) motion = GameArt.Motion.HIT;
else if (enemy.attack.state() == EnemyState.ATTACKING || enemy.attack.state() == EnemyState.TELEGRAPHING || enemy.tacticalTelegraph()) motion = GameArt.Motion.ATTACK;
else motion = enemy.velocity.len2() > .025f ? GameArt.Motion.RUN : GameArt.Motion.IDLE;
⋮----
Clock clock = clock(enemy, motion);
clock.direction = Direction8.fromVector(enemy.velocity.x, enemy.velocity.y, clock.direction);
ArtProfileCatalog.CharacterProfile profile = ArtProfileCatalog.enemy(enemy.type);
⋮----
? enemy.bossCombat.identity() : BossIdentity.ALPHA;
⋮----
? BiomeEnemyRoster.Identity.NONE : BiomeEnemyRoster.identityFor(RunStageContext.stage(), enemy.type);
⋮----
region = art.boss(bossIdentity, motion, clock.direction, clock.time);
⋮----
region = art.biomeEnemy(biomeIdentity, enemy.type, motion, clock.direction, clock.time);
⋮----
region = art.enemy(enemy.type, motion, clock.direction, clock.time);
⋮----
float flash = Math.min(1f, Math.max(0f, enemy.hitFlash));
⋮----
float shield = enemy.shieldFraction();
⋮----
float pulse = .5f + .5f * MathUtils.sin(enemy.variantTime * 4.6f);
⋮----
float pulse = .5f + .5f * MathUtils.sin(enemy.variantTime * 10f);
⋮----
alpha = enemy.phased() ? .48f : .86f + pulse * .12f;
scale *= enemy.phased() ? .94f : 1f;
⋮----
// Dedicated sprites carry the palette; keep only subtle material modulation and sizing here.
⋮----
float reaction = MathUtils.clamp(enemy.reactionFlash / .24f, 0f, 1f);
float wave = MathUtils.sin(reaction * MathUtils.PI);
⋮----
r = MathUtils.lerp(r, 1f, .58f * wave);
g = MathUtils.lerp(g, .58f, .45f * wave);
b = MathUtils.lerp(b, .20f, .50f * wave);
⋮----
r = MathUtils.lerp(r, .82f, .46f * wave);
g = MathUtils.lerp(g, .96f, .56f * wave);
b = MathUtils.lerp(b, 1f, .66f * wave);
⋮----
r = MathUtils.lerp(r, .72f, .50f * wave);
g = MathUtils.lerp(g, .58f, .48f * wave);
b = MathUtils.lerp(b, 1f, .68f * wave);
⋮----
if (enemy.supportBuffed()) {
float pulse = .5f + .5f * MathUtils.sin(enemy.variantTime * 11f);
r = MathUtils.lerp(r, .58f, .18f + pulse * .08f);
g = MathUtils.lerp(g, .88f, .22f + pulse * .08f);
b = MathUtils.lerp(b, 1f, .30f + pulse * .10f);
⋮----
if (biomeIdentity == BiomeEnemyRoster.Identity.NULL_WARD && enemy.supportPulseFlash() > 0f) {
float pulse = MathUtils.clamp(enemy.supportPulseFlash() / .48f, 0f, 1f);
float wave = MathUtils.sin(pulse * MathUtils.PI);
r = MathUtils.lerp(r, .62f, wave * .42f);
g = MathUtils.lerp(g, .86f, wave * .48f);
b = MathUtils.lerp(b, 1f, wave * .62f);
⋮----
if (biomeIdentity == BiomeEnemyRoster.Identity.PHASE_STALKER && enemy.phased()) {
float fracture = .5f + .5f * MathUtils.sin(enemy.variantTime * 18f);
b = MathUtils.clamp(b * (1.08f + fracture * .08f), 0f, 1f);
⋮----
if (enemy.tacticalTelegraph()) {
float pulse = .5f + .5f * MathUtils.sin(enemy.variantTime * 32f);
if (enemy.pendingTactic() == Enemy.Tactic.STRAFE) {
r = MathUtils.lerp(r, .50f, .34f + pulse * .18f);
g = MathUtils.lerp(g, .92f, .34f + pulse * .18f);
b = MathUtils.lerp(b, 1f, .42f + pulse * .22f);
⋮----
} else if (enemy.pendingTactic() == Enemy.Tactic.CHARGE) {
⋮----
r = MathUtils.lerp(r, 1f, .40f + pulse * .22f);
g = MathUtils.lerp(g, .48f, .28f + pulse * .16f);
b = MathUtils.lerp(b, .22f, .24f + pulse * .14f);
⋮----
int phase = enemy.bossPhases.phase();
⋮----
float pulse = MathUtils.clamp(clock.phasePulse / .42f, 0f, 1f);
⋮----
g = MathUtils.lerp(g, .92f, wave * .35f);
b = MathUtils.lerp(b, 1f, wave * .45f);
⋮----
float finalR = MathUtils.clamp(r, 0f, 1f);
float finalG = MathUtils.clamp(g, 0f, 1f);
float finalB = MathUtils.clamp(b, 0f, 1f);
⋮----
enemy.position.x, enemy.position.y - profile.footOffset(),
⋮----
ActorMaterialProfile.enemy(enemy.type, enemy.variant));
⋮----
private Clock clock(Object actor, GameArt.Motion motion) {
Clock clock = clocks.get(actor);
⋮----
clock = new Clock();
⋮----
clocks.put(actor, clock);
⋮----
clock.phasePulse = Math.max(0f, clock.phasePulse - frameDelta);
⋮----
private void drawMaterialized(SpriteBatch batch, TextureRegion region,
⋮----
if (material != null && material.outline()) {
float outlineWidth = width * material.scale();
float outlineHeight = height * material.scale();
batch.setColor(.012f, .022f, .030f, MathUtils.clamp(alpha * material.alpha(), 0f, 1f));
drawCentered(batch, region, centerX, y, outlineWidth, outlineHeight);
⋮----
batch.setColor(r, g, b, alpha);
drawCentered(batch, region, centerX, y, width, height);
⋮----
private void drawCentered(SpriteBatch batch, TextureRegion region, float centerX, float y, float width, float height) {
batch.draw(region, centerX - width * .5f, y, width, height);
```

## File: src/main/java/com/deadlinezero/game/visual/CombatAudioLayer.java
```java
/** Optional authored combat audio. Missing files are treated as silent fallbacks, never build blockers. */
public final class CombatAudioLayer implements Disposable {
⋮----
private final Sound shot = load("audio/combat/shot.ogg");
private final Sound dash = load("audio/combat/dash.ogg");
private final Sound levelUp = load("audio/combat/level_up.ogg");
private final Sound legendaryOverdrive = load("audio/combat/legendary_overdrive.ogg");
private final Sound legendarySingularity = load("audio/combat/legendary_singularity.ogg");
private final Sound legendaryApex = load("audio/combat/legendary_apex.ogg");
private final Sound bossAlpha = load("audio/combat/boss_alpha_intro.ogg");
private final Sound bossRevenant = load("audio/combat/boss_revenant_intro.ogg");
private final Sound bossPhase = load("audio/combat/boss_phase.ogg");
private final Sound forgeHound = load("audio/combat/forge_hound_attack.ogg");
private final Sound cinderGunner = load("audio/combat/cinder_gunner_attack.ogg");
private final Sound slagGuard = load("audio/combat/slag_guard_attack.ogg");
private final Sound phaseStalker = load("audio/combat/phase_stalker_attack.ogg");
private final Sound staticSeer = load("audio/combat/static_seer_attack.ogg");
private final Sound nullWard = load("audio/combat/null_ward_attack.ogg");
⋮----
AudioManifest.validate();
⋮----
public void update(Player player, Array<Enemy> enemies) {
long shotSerial = CombatVisualEvents.playerShotSerial();
⋮----
play(shot, .32f);
⋮----
long dashSerial = CombatVisualEvents.dashSerial();
⋮----
play(dash, .50f);
⋮----
long levelSerial = CombatVisualEvents.levelUpSerial();
⋮----
play(levelUp, .62f);
⋮----
if (player.legendary.hasOverdrive() && !seenOverdrive) {
⋮----
play(legendaryOverdrive, .82f);
⋮----
if (player.legendary.hasSingularity() && !seenSingularity) {
⋮----
play(legendarySingularity, .82f);
⋮----
if (player.legendary.hasApex() && !seenApex) {
⋮----
play(legendaryApex, .88f);
⋮----
Integer previous = bossPhases.get(enemy);
int phase = enemy.bossPhases.phase();
⋮----
bossPhases.put(enemy, phase);
play(enemy.bossCombat.revenant() ? bossRevenant : bossAlpha, .78f);
⋮----
play(bossPhase, .72f);
⋮----
EnemyState state = enemy.attack.state();
EnemyState previousState = enemyStates.put(enemy, state);
⋮----
BiomeEnemyRoster.Identity identity = BiomeEnemyRoster.identityFor(RunStageContext.stage(), enemy.type);
⋮----
float distance2 = player.position.dst2(enemy.position);
⋮----
if (biomeCueEnemy != null) playBiomeCue(biomeCueIdentity, biomeCueDistance2);
⋮----
private void playBiomeCue(BiomeEnemyRoster.Identity identity, float distance2) {
⋮----
float distance = (float)Math.sqrt(Math.max(0f, distance2));
float proximity = Math.max(0f, Math.min(1f, 1f - distance / 12f));
play(sound, .16f + proximity * .28f);
⋮----
private Sound load(String path) {
⋮----
if (!Gdx.files.internal(path).exists()) return null;
return Gdx.audio.newSound(Gdx.files.internal(path));
⋮----
private void play(Sound sound, float volume) {
if (sound != null) sound.play(volume);
⋮----
@Override public void dispose() {
dispose(shot);
dispose(dash);
dispose(levelUp);
dispose(legendaryOverdrive);
dispose(legendarySingularity);
dispose(legendaryApex);
dispose(bossAlpha);
dispose(bossRevenant);
dispose(bossPhase);
dispose(forgeHound);
dispose(cinderGunner);
dispose(slagGuard);
dispose(phaseStalker);
dispose(staticSeer);
dispose(nullWard);
⋮----
private void dispose(Sound sound) {
if (sound != null) sound.dispose();
```

## File: src/main/java/com/deadlinezero/game/visual/CombatFeel.java
```java
/** Centralized micro-feedback controller: hit-stop, recoil impulse, and transient camera kick. */
public final class CombatFeel {
⋮----
private final Vector2 recoilDir = new Vector2();
⋮----
public void triggerHitStop(float seconds) {
hitStop = Math.max(hitStop, MathUtils.clamp(seconds, 0f, .08f));
⋮----
public void triggerRecoil(float angleDeg, float amount) {
recoil = Math.max(recoil, MathUtils.clamp(amount, 0f, .45f));
recoilDir.set(1f, 0f).setAngleDeg(angleDeg + 180f);
⋮----
public float consumeSimulationScale(float dt) {
⋮----
hitStop = Math.max(0f, hitStop - Math.max(0f, dt));
⋮----
public void update(float dt) {
recoil = Math.max(0f, recoil - Math.max(0f, dt) * 3.8f);
⋮----
public float recoilX() { return recoilDir.x * recoil; }
public float recoilY() { return recoilDir.y * recoil; }
public float recoilAmount() { return recoil; }
```

## File: src/main/java/com/deadlinezero/game/visual/CombatHudLayout.java
```java
/** Pure combat-HUD geometry, independent from simulation and GL state. */
public final class CombatHudLayout {
⋮----
public float toLogicalX(float physicalX) { return physicalX * scaleX; }
public float toLogicalY(float physicalY) { return physicalY * scaleY; }
⋮----
public static Layout compute(int screenWidth, int screenHeight, float uiScale, boolean bossActive) {
int physicalW = Math.max(1, screenWidth);
int physicalH = Math.max(1, screenHeight);
UiLayout.Metrics m = UiLayout.compute(screenWidth, screenHeight);
float sx = m.width() / physicalW;
float sy = m.height() / physicalH;
float s = MathUtils.clamp(uiScale, .85f, 1.35f);
⋮----
float railW = Math.min(420f * s, m.contentWidth() * .30f);
⋮----
float top = m.safeTop() - 12f;
Rectangle hp = new Rectangle(m.safeLeft() + 12f, top - railH, railW, railH);
Rectangle xp = new Rectangle(hp.x + hp.width + gap, hp.y, railW, railH);
⋮----
float timelineW = Math.min(760f, m.contentWidth() * .54f);
Rectangle timeline = new Rectangle(m.centerX() - timelineW * .5f,
⋮----
float bossW = Math.min(860f, m.contentWidth() * .62f);
⋮----
boss = new Rectangle(m.centerX() - bossW * .5f,
⋮----
float hintW = Math.min(360f, m.contentWidth() * .30f);
⋮----
Rectangle onboarding = new Rectangle(m.centerX() - hintW * .5f,
m.safeBottom() + 48f * s, hintW, hintH);
⋮----
float dashRadius = Math.max(32f * s, 28f);
⋮----
return new Layout(m.width(), m.height(), sx, sy, hp, xp, timeline, boss, onboarding,
```

## File: src/main/java/com/deadlinezero/game/visual/CombatHudRenderer.java
```java
/** Dedicated responsive mobile HUD renderer. Keeps combat presentation separate from simulation/input. */
public final class CombatHudRenderer {
private static final Color HARVESTER_COLOR = new Color(.96f, .42f, .10f, 1f);
private static final Color NULL_ARCHON_COLOR = new Color(.52f, .42f, 1f, 1f);
private final Matrix4 projection = new Matrix4();
⋮----
if (i18n == null) throw new IllegalArgumentException("i18n");
⋮----
public void triggerDamageFlash() {
if (AccessibilitySettings.active().damageFlash) damageFlash = 1f;
⋮----
public void update(float dt) {
float safeDt = Math.max(0f, dt);
damageFlash = Math.max(0f, damageFlash - safeDt * 2.8f);
⋮----
public void render(ShapeRenderer shapes, SpriteBatch batch, BitmapFont font,
⋮----
Enemy boss = findBoss(enemies);
CombatHudLayout.Layout layout = CombatHudLayout.compute((int) width, (int) height, ui(), boss != null);
projection.setToOrtho2D(0, 0, layout.logicalWidth(), layout.logicalHeight());
updateOnboarding(player, director);
drawBars(shapes, player, director, boss, layout, width, height);
drawText(batch, font, player, director, boss, layout);
drawDamageVignette(shapes, layout.logicalWidth(), layout.logicalHeight());
⋮----
private float ui() { return AccessibilitySettings.active().uiScale; }
⋮----
private void updateOnboarding(Player player, WaveDirector director) {
OnboardingState onboarding = OnboardingState.active();
if (!onboarding.completed()) {
if (player.velocity.len2() > .12f) onboarding.markMovementSeen();
if (player.dashTimer > .05f) onboarding.markDashSeen();
if (player.level > 1) onboarding.markUpgradeSeen();
if (director.bossWarning() || director.bossSpawned()) onboarding.markBossSeen();
onboarding.refreshCompletion();
⋮----
int nextStep = OnboardingHintPolicy.step(
onboarding.movementSeen(), onboarding.dashSeen(), onboarding.upgradeSeen(), onboarding.bossSeen());
if (onboarding.completed()) nextStep = OnboardingHintPolicy.NONE;
⋮----
private boolean showOnboardingHint() {
return OnboardingHintPolicy.visible(
OnboardingState.active().completed(), onboardingHintStep, onboardingHintAge);
⋮----
private void drawBars(ShapeRenderer shapes, Player player, WaveDirector director, Enemy boss,
⋮----
shapes.setProjectionMatrix(projection);
shapes.begin(ShapeRenderer.ShapeType.Filled);
Rectangle hpRect = layout.hp();
Rectangle xpRect = layout.xp();
⋮----
UiRenderer.card(shapes, hpRect.x, hpRect.y, hpRect.width, hpRect.height, false, false);
UiRenderer.card(shapes, xpRect.x, xpRect.y, xpRect.width, xpRect.height, false, false);
float hp = MathUtils.clamp(player.hp / Math.max(1f, player.maxHp), 0f, 1f);
float xp = MathUtils.clamp(player.xp / (float) Math.max(1, player.xpNext), 0f, 1f);
UiRenderer.progress(shapes, hpRect.x + 5f, hpRect.y + 5f, hpRect.width - 10f, hpRect.height - 10f,
hp, hp < .28f ? VisualTheme.danger() : VisualTheme.accent());
UiRenderer.progress(shapes, xpRect.x + 5f, xpRect.y + 5f, xpRect.width - 10f, xpRect.height - 10f,
⋮----
drawRailChrome(shapes, hpRect, xpRect, hp, xp);
⋮----
Rectangle timeline = layout.timeline();
UiRenderer.progress(shapes, timeline.x, timeline.y, timeline.width, timeline.height,
director.bossProgress(), director.bossWarning()
? (AccessibilitySettings.active().highContrastTelegraphs ? Color.WHITE : VisualTheme.danger())
: (AccessibilitySettings.active().highContrastTelegraphs ? VisualTheme.CYAN : VisualTheme.CYAN_SOFT));
drawBossWarningChrome(shapes, director, timeline);
⋮----
if (boss != null && layout.boss() != null) {
Rectangle b = layout.boss();
float ratio = MathUtils.clamp(boss.hp / Math.max(1f, boss.maxHp), 0f, 1f);
UiRenderer.card(shapes, b.x, b.y, b.width, b.height, true, false);
Color identity = AccessibilitySettings.active().highContrastTelegraphs ? Color.WHITE : bossColor(boss);
UiRenderer.progress(shapes, b.x + 4f, b.y + 4f, b.width - 8f, b.height - 8f, ratio, identity);
shapes.setColor(VisualTheme.SURFACE_0);
shapes.rect(b.x + b.width * .33f, b.y + 3f, 2f, b.height - 6f);
shapes.rect(b.x + b.width * .66f, b.y + 3f, 2f, b.height - 6f);
drawBossPhaseChrome(shapes, b, boss, identity);
⋮----
if (showOnboardingHint()) {
Rectangle hint = layout.onboarding();
UiRenderer.card(shapes, hint.x, hint.y, hint.width, hint.height, false, false);
shapes.setColor(VisualTheme.CYAN_SOFT.r, VisualTheme.CYAN_SOFT.g, VisualTheme.CYAN_SOFT.b, .78f);
shapes.rect(hint.x + 7f, hint.y + hint.height - 3f, hint.width - 14f, 2f);
⋮----
drawEventCueChrome(shapes, layout);
drawMobileControls(shapes, player, layout, physicalW, physicalH);
shapes.end();
⋮----
private void drawRailChrome(ShapeRenderer shapes, Rectangle hpRect, Rectangle xpRect,
⋮----
float s = MathUtils.clamp(ui(), .85f, 1.35f);
float accentH = Math.max(2f, 2.5f * s);
⋮----
shapes.setColor(VisualTheme.accent());
shapes.rect(hpRect.x + 5f, hpRect.y + hpRect.height - 5f - accentH,
Math.max(18f, (hpRect.width - 10f) * MathUtils.clamp(hpRatio, 0f, 1f)), accentH);
shapes.setColor(VisualTheme.VIOLET);
shapes.rect(xpRect.x + 5f, xpRect.y + xpRect.height - 5f - accentH,
Math.max(18f, (xpRect.width - 10f) * MathUtils.clamp(xpRatio, 0f, 1f)), accentH);
⋮----
drawProgressTicks(shapes, hpRect.x + 5f, hpRect.y + 5f, hpRect.width - 10f, hpRect.height - 10f, 4);
drawProgressTicks(shapes, xpRect.x + 5f, xpRect.y + 5f, xpRect.width - 10f, xpRect.height - 10f, 4);
⋮----
float urgency = 1f - MathUtils.clamp(hpRatio / .28f, 0f, 1f);
Color danger = VisualTheme.danger();
shapes.setColor(danger.r, danger.g, danger.b, .18f + urgency * .22f);
float t = Math.max(2f, 2.5f * s);
shapes.rect(hpRect.x - t, hpRect.y - t, hpRect.width + t * 2f, t);
shapes.rect(hpRect.x - t, hpRect.y + hpRect.height, hpRect.width + t * 2f, t);
shapes.rect(hpRect.x - t, hpRect.y, t, hpRect.height);
shapes.rect(hpRect.x + hpRect.width, hpRect.y, t, hpRect.height);
⋮----
private void drawProgressTicks(ShapeRenderer shapes, float x, float y, float w, float h, int segments) {
shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .72f);
float tickW = Math.max(1f, 1.5f * ui());
⋮----
shapes.rect(px - tickW * .5f, y + 2f, tickW, Math.max(0f, h - 4f));
⋮----
private void drawBossWarningChrome(ShapeRenderer shapes, WaveDirector director, Rectangle timeline) {
if (!director.bossWarning() || director.bossSpawned()) return;
float seconds = Math.max(0f, director.secondsUntilBoss());
float urgency = 1f - MathUtils.clamp(seconds / 30f, 0f, 1f);
float pulse = AccessibilitySettings.active().minimizesFlashes()
⋮----
: .58f + .22f * (MathUtils.sin(seconds * 4.8f) * .5f + .5f);
Color danger = AccessibilitySettings.active().highContrastTelegraphs ? Color.WHITE : VisualTheme.danger();
⋮----
shapes.setColor(danger.r, danger.g, danger.b, .10f + urgency * .14f);
shapes.rect(timeline.x - pad, timeline.y - pad, timeline.width + pad * 2f, timeline.height + pad * 2f);
shapes.setColor(danger.r, danger.g, danger.b, pulse);
shapes.rect(timeline.x - pad, timeline.y + timeline.height + pad - 2f, timeline.width + pad * 2f, 2f);
shapes.rect(timeline.x - pad, timeline.y - pad, timeline.width + pad * 2f, 2f);
⋮----
float notchW = Math.max(18f, timeline.width * .025f);
⋮----
shapes.setColor(danger.r, danger.g, danger.b, .88f);
shapes.rect(cx - notchW * .5f, timeline.y + timeline.height + pad - 5f, notchW, 5f);
⋮----
private void drawBossPhaseChrome(ShapeRenderer shapes, Rectangle bossRect, Enemy boss, Color identity) {
int phase = boss.bossPhases == null ? 1 : boss.bossPhases.phase();
int clampedPhase = MathUtils.clamp(phase, 1, 3);
⋮----
float capH = Math.max(2f, 2.5f * ui());
⋮----
shapes.setColor(identity.r, identity.g, identity.b, alpha);
shapes.rect(bossRect.x + i * segmentW + 2f, bossRect.y + bossRect.height - capH - 2f,
Math.max(0f, segmentW - 4f), capH);
⋮----
: .16f + .08f * (MathUtils.sin(clampedPhase * 2.1f + boss.hp * .01f) * .5f + .5f);
shapes.setColor(identity.r, identity.g, identity.b, pulse);
shapes.rect(bossRect.x - 5f, bossRect.y - 5f, bossRect.width + 10f, 3f);
shapes.rect(bossRect.x - 5f, bossRect.y + bossRect.height + 2f, bossRect.width + 10f, 3f);
⋮----
private void drawEventCueChrome(ShapeRenderer shapes, CombatHudLayout.Layout layout) {
⋮----
drawCuePlate(shapes, timeline, timeline.y + 88f * s,
CombatVisualEvents.synergyAgeSeconds(), 1.65f, VisualTheme.GOLD, 1.0f, s);
drawCuePlate(shapes, timeline, timeline.y + 62f * s,
CombatVisualEvents.protocolAgeSeconds(), .90f, VisualTheme.CYAN, .84f, s);
drawCuePlate(shapes, timeline, timeline.y + 44f * s,
CombatVisualEvents.sentinelInterceptAgeSeconds(), .72f, VisualTheme.CYAN_SOFT, .68f, s);
⋮----
private void drawCuePlate(ShapeRenderer shapes, Rectangle timeline, float centerY,
⋮----
float fade = MathUtils.clamp(1f - age / duration, 0f, 1f);
float width = Math.min(timeline.width * widthScale, 620f * s);
float height = Math.max(22f * s, 28f * s);
⋮----
shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .72f * fade);
shapes.rect(x, y, width, height);
shapes.setColor(accent.r, accent.g, accent.b, .10f * fade);
shapes.rect(x + 3f, y + 3f, Math.max(0f, width - 6f), Math.max(0f, height - 6f));
shapes.setColor(accent.r, accent.g, accent.b, .78f * fade);
shapes.rect(x, y + height - 2f, width, 2f);
shapes.rect(x + width * .5f - Math.max(10f, width * .04f), y,
Math.max(20f, width * .08f), 2f);
⋮----
private void drawMobileControls(ShapeRenderer shapes, Player player, CombatHudLayout.Layout layout,
⋮----
if (VirtualStick.hudActive()) {
float physicalMax = Math.max(64f, physicalH * .12f);
float max = physicalMax * layout.scaleY();
float ox = layout.toLogicalX(VirtualStick.hudOriginX());
float oy = layout.toLogicalY(VirtualStick.hudOriginY());
float vx = VirtualStick.hudValueX();
float vy = VirtualStick.hudValueY();
shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .09f);
shapes.circle(ox, oy, max, 40);
shapes.setColor(VisualTheme.CYAN_SOFT.r, VisualTheme.CYAN_SOFT.g, VisualTheme.CYAN_SOFT.b, .20f);
shapes.circle(ox, oy, max * .62f, 32);
shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .50f);
shapes.circle(ox + vx * max * .68f, oy + vy * max * .68f, max * .24f, 28);
⋮----
float radius = layout.dashRadius() * (MobileCombatInput.dashDown() ? 1.12f : 1f);
float alpha = MobileCombatInput.dashDown() ? .46f : .26f;
Color dashColor = player.canDash() ? VisualTheme.accent() : VisualTheme.MUTED;
shapes.setColor(dashColor.r, dashColor.g, dashColor.b, alpha);
shapes.circle(layout.dashX(), layout.dashY(), radius, 40);
shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .82f);
shapes.circle(layout.dashX(), layout.dashY(), radius * .78f, 40);
shapes.setColor(dashColor.r, dashColor.g, dashColor.b, player.canDash() ? .32f : .16f);
shapes.circle(layout.dashX(), layout.dashY(), radius * .60f, 36);
⋮----
shapes.circle(layout.dashX(), layout.dashY(), radius * .43f, 32);
shapes.setColor(dashColor);
shapes.circle(layout.dashX(), layout.dashY(),
MobileCombatInput.dashDown() ? radius * .22f : radius * .12f, 18);
⋮----
private void drawText(SpriteBatch batch, BitmapFont font, Player player, WaveDirector director,
⋮----
float w = layout.logicalWidth();
batch.setProjectionMatrix(projection);
batch.begin();
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * s);
font.setColor(VisualTheme.TEXT_STRONG);
font.draw(batch, f("hud.hp", (int) player.hp, (int) player.maxHp), layout.hp().x + 12f,
layout.hp().y + layout.hp().height * .70f, layout.hp().width - 24f, Align.left, false);
font.draw(batch, f("hud.level", player.level), layout.xp().x + 12f,
layout.xp().y + layout.xp().height * .70f, layout.xp().width - 24f, Align.left, false);
⋮----
font.setColor(VisualTheme.TEXT_DIM);
font.draw(batch, f("hud.stage", RunStageContext.stage()), layout.hp().x,
layout.timeline().y + 29f * s, 160f * s, Align.left, false);
font.draw(batch, f("hud.kills", director.kills()), w - layout.hp().x - 180f * s,
layout.timeline().y + 29f * s, 180f * s, Align.right, false);
⋮----
boolean contrast = AccessibilitySettings.active().highContrastTelegraphs;
if (!director.bossSpawned()) {
int remaining = Math.max(0, Math.round(director.secondsUntilBoss()));
font.setColor(director.bossWarning() ? (contrast ? Color.WHITE : VisualTheme.danger()) : VisualTheme.TEXT_DIM);
font.draw(batch, director.bossWarning() ? f("hud.bossSignal", remaining) : f("hud.bossEta", remaining),
layout.timeline().x, layout.timeline().y + 29f * s, layout.timeline().width, Align.center, false);
} else if (boss != null && layout.boss() != null) {
⋮----
font.setColor(contrast ? Color.WHITE : bossColor(boss));
font.draw(batch, f("hud.bossPhase", bossName(boss), phase), layout.boss().x,
layout.boss().y + layout.boss().height + 21f * s, layout.boss().width, Align.center, false);
⋮----
font.setColor(contrast ? Color.WHITE : VisualTheme.danger());
font.draw(batch, t("hud.bossLost"), layout.timeline().x, layout.timeline().y + 29f * s,
layout.timeline().width, Align.center, false);
⋮----
if (RunModifierContext.active()) {
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .86f * s);
font.setColor(VisualTheme.GOLD);
font.draw(batch, f("hud.contract", RunModifierContext.title(), RunModifierContext.rewardBonusPercent()),
layout.hp().x, layout.timeline().y - 14f * s, Math.min(430f, w * .34f), Align.left, false);
⋮----
WeaponLegendaryPresentation.Style legendaryStyle = WeaponLegendaryPresentation.style(player);
⋮----
font.setColor(legendaryStyle.r, legendaryStyle.g, legendaryStyle.b, 1f);
font.draw(batch, f("hud.weaponLegendary", legendaryStyle.label), w - layout.hp().x - 430f,
layout.timeline().y - 14f * s, 430f, Align.right, false);
⋮----
RunEncounterDirector.Type encounter = director.activeEncounter();
if (encounter != RunEncounterDirector.Type.NONE && !director.bossSpawned()) {
⋮----
case SWARM_SURGE -> t("encounter.swarm_surge");
case HUNTER_PACK -> t("encounter.hunter_pack");
case JUGGERNAUT_PUSH -> t("encounter.juggernaut_push");
case PHANTOM_BREACH -> t("encounter.phantom_breach");
case REGEN_BLOOM -> t("encounter.regen_bloom");
case BULWARK_LINE -> t("encounter.bulwark_line");
⋮----
int seconds = Math.max(1, Math.round(director.encounterSecondsRemaining()));
font.setColor(contrast ? Color.WHITE : VisualTheme.GOLD);
font.draw(batch, f("hud.encounter", name, seconds), layout.timeline().x,
layout.timeline().y - 14f * s, layout.timeline().width, Align.center, false);
⋮----
if (encounter == RunEncounterDirector.Type.NONE && !director.bossSpawned()) {
drawBuildStatus(batch, font, player, layout, s);
⋮----
font.setColor(player.canDash() ? VisualTheme.CYAN : VisualTheme.MUTED);
font.draw(batch, player.canDash() ? t("hud.dash") : String.format(java.util.Locale.ROOT, "%.1f", player.dashTimer),
layout.dashX() - layout.dashRadius(), layout.dashY() + 4f * s, layout.dashRadius() * 2f, Align.center, false);
⋮----
drawSynergyUnlock(batch, font, layout, s);
drawSentinelIntercept(batch, font, layout, s);
drawProtocolCue(batch, font, layout, s);
drawOnboardingHint(batch, font, layout, s);
batch.end();
⋮----
private void drawBuildStatus(SpriteBatch batch, BitmapFont font, Player player,
⋮----
ActiveBuildStatus.fill(player, activeBuildKeys);
⋮----
? f("hud.build.summaryOne", t(activeBuildKeys[0]))
: f("hud.build.summaryTwo", t(activeBuildKeys[0]), t(activeBuildKeys[1]));
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .82f * s);
font.setColor(VisualTheme.CYAN_SOFT);
font.draw(batch, text, layout.timeline().x, layout.timeline().y - 14f * s,
⋮----
private void drawSynergyUnlock(SpriteBatch batch, BitmapFont font, CombatHudLayout.Layout layout, float s) {
float age = CombatVisualEvents.synergyAgeSeconds();
⋮----
String key = CombatVisualEvents.synergyKey();
⋮----
float alpha = MathUtils.clamp(1f - age / 1.65f, 0f, 1f);
font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY) * 1.12f * s);
font.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, alpha);
font.draw(batch, t(key), layout.timeline().x, layout.timeline().y + 88f * s,
⋮----
private void drawSentinelIntercept(SpriteBatch batch, BitmapFont font, CombatHudLayout.Layout layout, float s) {
float age = CombatVisualEvents.sentinelInterceptAgeSeconds();
⋮----
float alpha = MathUtils.clamp(1f - age / .72f, 0f, 1f);
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .94f * s);
font.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, alpha);
font.draw(batch, t("hud.sentinelBlock"), layout.timeline().x, layout.timeline().y + 44f * s,
⋮----
private void drawProtocolCue(SpriteBatch batch, BitmapFont font, CombatHudLayout.Layout layout, float s) {
float age = CombatVisualEvents.protocolAgeSeconds();
⋮----
String key = switch (CombatVisualEvents.protocolCue()) {
⋮----
float alpha = MathUtils.clamp(1f - age / .90f, 0f, 1f);
font.getData().setScale(UiTypography.scale(UiTypography.Role.BODY) * 1.08f * s);
⋮----
font.draw(batch, t(key), layout.timeline().x, layout.timeline().y + 62f * s,
⋮----
private void drawOnboardingHint(SpriteBatch batch, BitmapFont font, CombatHudLayout.Layout layout, float s) {
if (!showOnboardingHint()) return;
OnboardingState o = OnboardingState.active();
⋮----
if (!o.movementSeen()) hint = t("hud.onboardingMove");
else if (!o.dashSeen()) hint = t("hud.onboardingDash");
else if (!o.upgradeSeen()) hint = t("hud.onboardingUpgrade");
else if (!o.bossSeen()) hint = t("hud.onboardingBoss");
⋮----
Rectangle r = layout.onboarding();
float fade = MathUtils.clamp(
⋮----
font.getData().setScale(UiTypography.scale(UiTypography.Role.CAPTION) * .92f * s);
font.setColor(VisualTheme.CYAN_SOFT.r, VisualTheme.CYAN_SOFT.g, VisualTheme.CYAN_SOFT.b, fade);
font.draw(batch, hint, r.x + 12f, r.y + r.height * .62f, r.width - 24f, Align.center, true);
⋮----
private void drawDamageVignette(ShapeRenderer shapes, float w, float h) {
AccessibilitySettings settings = AccessibilitySettings.active();
⋮----
float alpha = (settings.minimizesFlashes() ? .07f : .16f) * damageFlash;
shapes.setColor(1f, .03f, .02f, alpha);
float edge = Math.min(46f * ui(), Math.min(w, h) * .06f);
shapes.rect(0f, 0f, w, edge);
shapes.rect(0f, h - edge, w, edge);
shapes.rect(0f, edge, edge, h - edge * 2f);
shapes.rect(w - edge, edge, edge, h - edge * 2f);
⋮----
private Enemy findBoss(Array<Enemy> enemies) {
⋮----
private BossIdentity bossIdentity(Enemy boss) { return boss != null && boss.bossCombat != null ? boss.bossCombat.identity() : BossIdentity.ALPHA; }
private String bossName(Enemy boss) {
return switch (bossIdentity(boss)) {
case REVENANT -> t("boss.revenant");
case WARDEN -> t("boss.warden");
case HARVESTER -> t("boss.harvester");
case NULL_ARCHON -> t("boss.null_archon");
default -> t("boss.alpha");
⋮----
private String t(String key) { return i18n.text(key); }
private String f(String key, Object... args) { return i18n.format(key, args); }
private Color bossColor(Enemy boss) {
⋮----
default -> VisualTheme.danger();
```

## File: src/main/java/com/deadlinezero/game/visual/CombatOverlayViewport.java
```java
/**
 * Normalizes combat modal overlays to the same logical UI space as the HUD.
 *
 * Android may report a render-surface size that is larger than the captured/display backbuffer.
 * Drawing modal cards directly in raw Gdx pixel coordinates can therefore push choices off-screen.
 */
public final class CombatOverlayViewport {
⋮----
public float toLogicalX(float physicalX) { return physicalX * scaleX; }
public float toLogicalY(float physicalY) { return physicalY * scaleY; }
⋮----
public static Viewport compute(int physicalWidth, int physicalHeight) {
int w = Math.max(1, physicalWidth);
int h = Math.max(1, physicalHeight);
UiLayout.Metrics m = UiLayout.compute(w, h);
return new Viewport(m.width(), m.height(), m.width() / w, m.height() / h);
```

## File: src/main/java/com/deadlinezero/game/visual/CombatPolishController.java
```java
/**
 * Centralized presentation-only combat feedback: micro hit-stop, recoil, local lighting,
 * persistent death marks, corpses, resilient event-driven audio and accessible haptics.
 * Arena hazards are rendered here, while damage still flows through pooled hostile projectiles.
 */
public final class CombatPolishController {
⋮----
private final CombatFeel feel = new CombatFeel();
private final LocalLightRenderer lights = new LocalLightRenderer();
private final LegendaryFxRenderer legendaryFx = new LegendaryFxRenderer();
private final LeaperRuntime leapers = LeaperSharedRuntime.get();
private final ArenaHazardRuntime hazards = new ArenaHazardRuntime();
private final SingularityImpactTracker singularityImpacts = new SingularityImpactTracker();
⋮----
private final AdaptiveFxBudget fxBudget = new AdaptiveFxBudget();
⋮----
this(art, AccessibilitySettings.load(), ThermalService.noOp());
⋮----
this(art, settings, ThermalService.noOp());
⋮----
deaths = new DeathFxRenderer(art);
this.settings = settings == null ? new AccessibilitySettings() : settings;
this.thermal = thermal == null ? ThermalService.noOp() : thermal;
CombatVisualEvents.reset();
⋮----
static Pools currentPools() { return currentPools; }
⋮----
public float simulationScale(float visualDelta) {
return settings.hitStop ? feel.consumeSimulationScale(visualDelta) : 1f;
⋮----
public void updateVisual(float dt) {
feel.update(dt);
fxBudget.setExternalCeiling(ThermalBudgetPolicy.fxCeiling(thermal.level()));
fxBudget.update(dt);
⋮----
public void updateSimulation(float dt, Pools pools) {
for (DeathFx fx : pools.deathFx) fx.update(dt);
⋮----
public void onShot(float angleDeg) {
CombatVisualEvents.markPlayerShot();
AudioDirector.playGlobal(AudioDirector.Cue.SHOT, .96f + MathUtils.random(.08f), 0f);
⋮----
feel.triggerRecoil(angleDeg, .075f * settings.screenShakeStrength);
⋮----
public void onProjectileHit(boolean critical) {
AudioDirector.playGlobal(critical ? AudioDirector.Cue.CRIT : AudioDirector.Cue.HIT,
critical ? 1.04f : .98f + MathUtils.random(.04f), 0f);
if (critical) vibrate(14);
if (settings.hitStop && critical) feel.triggerHitStop(.014f);
⋮----
public void onEnemyKilled(Enemy enemy, Pools pools) {
DeathFx fx = pools.deathFx();
⋮----
float duration = enemy.type == Enemy.Type.BOSS ? 26f : (fxBudget.allowHeavyFx() ? 13f : 8f);
float rotation = enemy.velocity.len2() > .001f ? enemy.velocity.angleDeg() - 90f : MathUtils.random(0f, 360f);
fx.spawn(enemy.type, enemy.position.x, enemy.position.y, rotation, enemy.radius, duration);
⋮----
int threatTier = RunStageContext.threatTier();
if (DeathBurstRules.enabled(enemy.type, threatTier)) {
hazards.scheduleDeathBurst(enemy.position.x, enemy.position.y,
DeathBurstRules.radius(enemy.type, threatTier), DeathBurstRules.damage(enemy.type, threatTier));
⋮----
AudioDirector.playGlobal(enemy.type == Enemy.Type.BOSS ? AudioDirector.Cue.BOSS_KILL : AudioDirector.Cue.KILL,
enemy.type == Enemy.Type.BOSS ? .88f : .96f + MathUtils.random(.08f), 0f);
⋮----
if (enemy.type == Enemy.Type.BOSS) vibrate(48);
else if (enemy.type == Enemy.Type.ELITE || enemy.type == Enemy.Type.BRUTE) vibrate(24);
⋮----
feel.triggerHitStop(.065f);
⋮----
feel.triggerHitStop(.030f);
⋮----
feel.triggerHitStop(.018f);
⋮----
private void vibrate(int millis) {
⋮----
try { Gdx.input.vibrate(millis); } catch (RuntimeException ignored) { }
⋮----
public void applyCameraRecoil(OrthographicCamera camera) {
⋮----
camera.position.x += feel.recoilX();
camera.position.y += feel.recoilY();
⋮----
public void drawWorldUnderlay(ShapeRenderer shapes, Player player, Array<Enemy> enemies, Pools pools, float time) {
⋮----
updateAndDrawHazards(shapes, player, pools, time);
updateAndDrawSingularityImpacts(shapes, pools, time);
deaths.drawFallback(shapes, pools.deathFx);
legendaryFx.render(shapes, player, time, fxBudget.quality());
drawPlayerEventFx(shapes, player);
drawElementReactionFx(shapes, enemies);
collectRenderEnemySubsets(enemies);
drawLeaperTelegraphs(shapes, leaperRenderEnemies, time);
drawBossPhaseTransitions(shapes, bossRenderEnemies, time);
drawRevenantIdentity(shapes, bossRenderEnemies, time);
drawWardenIdentity(shapes, bossRenderEnemies, time);
if (!settings.reduceFlashes && fxBudget.allowHeavyFx()) lights.draw(shapes, player, enemies, pools, time);
⋮----
private void drawPlayerEventFx(ShapeRenderer shapes, Player player) {
float dashAge = CombatVisualEvents.dashAgeSeconds();
⋮----
float progress = MathUtils.clamp(dashAge / .36f, 0f, 1f);
⋮----
float radius = player.radius * MathUtils.lerp(1.25f, 3.15f, progress);
⋮----
shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b,
⋮----
shapes.circle(player.position.x, player.position.y, radius, fxBudget.geometrySegments(32, 18));
shapes.setColor(VisualTheme.CYAN_SOFT.r, VisualTheme.CYAN_SOFT.g, VisualTheme.CYAN_SOFT.b,
⋮----
shapes.circle(player.position.x, player.position.y, Math.max(.08f, radius * .54f),
fxBudget.geometrySegments(28, 16));
⋮----
float levelAge = CombatVisualEvents.levelUpAgeSeconds();
⋮----
float progress = MathUtils.clamp(levelAge / .82f, 0f, 1f);
⋮----
float radius = player.radius * MathUtils.lerp(1.45f, 4.25f, progress);
⋮----
shapes.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b,
⋮----
shapes.circle(player.position.x, player.position.y, radius, fxBudget.geometrySegments(36, 20));
shapes.setColor(VisualTheme.VIOLET.r, VisualTheme.VIOLET.g, VisualTheme.VIOLET.b,
⋮----
shapes.circle(player.position.x, player.position.y, Math.max(.10f, radius * .62f),
fxBudget.geometrySegments(30, 18));
⋮----
private void drawElementReactionFx(ShapeRenderer shapes, Array<Enemy> enemies) {
int segments = fxBudget.geometrySegments(30, 16);
⋮----
float progress = MathUtils.clamp(1f - enemy.reactionFlash / .24f, 0f, 1f);
⋮----
float radius = enemy.radius * MathUtils.lerp(1.25f, 2.85f, progress);
⋮----
shapes.setColor(primary.r, primary.g, primary.b, (.18f + .22f * fade) * fade * flashScale);
shapes.circle(enemy.position.x, enemy.position.y, radius, segments);
shapes.setColor(secondary.r, secondary.g, secondary.b, .22f * fade * flashScale);
shapes.circle(enemy.position.x, enemy.position.y, Math.max(.08f, radius * .56f), Math.max(12, segments - 6));
⋮----
if (fxBudget.allowHeavyFx()) {
⋮----
shapes.setColor(primary.r, primary.g, primary.b, .20f * fade * flashScale);
shapes.rectLine(
enemy.position.x + MathUtils.cosDeg(angle) * inner,
enemy.position.y + MathUtils.sinDeg(angle) * inner,
enemy.position.x + MathUtils.cosDeg(angle) * outer,
enemy.position.y + MathUtils.sinDeg(angle) * outer,
⋮----
private void updateAndDrawSingularityImpacts(ShapeRenderer shapes, Pools pools, float time) {
float dt = Float.isNaN(lastSingularityVisualTime) ? 0f : MathUtils.clamp(time - lastSingularityVisualTime, 0f, .05f);
⋮----
singularityImpacts.update(pools.projectiles, dt);
int triggered = singularityImpacts.consumeTriggeredCount();
⋮----
AudioDirector.playGlobal(AudioDirector.Cue.SINGULARITY, .82f + Math.min(3, triggered) * .04f, 0f);
vibrate(12);
if (settings.hitStop) feel.triggerHitStop(.012f);
⋮----
int segments = fxBudget.geometrySegments(42, 22);
for (SingularityImpactTracker.Impact impact : singularityImpacts.impacts()) {
float progress = impact.progress();
⋮----
float wave = MathUtils.sin(progress * MathUtils.PI);
float outer = MathUtils.lerp(.34f, 2.75f, progress);
float inner = MathUtils.lerp(.72f, .08f, progress);
⋮----
shapes.setColor(.46f, .20f, 1f, (.18f + wave * .24f) * fade * flashScale);
shapes.circle(impact.x, impact.y, outer, segments);
shapes.setColor(.08f, .02f, .16f, (.52f + wave * .28f) * fade * flashScale);
shapes.circle(impact.x, impact.y, Math.max(.05f, inner), segments);
shapes.setColor(.86f, .72f, 1f, .46f * wave * flashScale);
shapes.circle(impact.x, impact.y, Math.max(.04f, outer * .16f), segments);
⋮----
int rays = fxBudget.allowExtraFx() ? 10 : 6;
⋮----
float from = outer * (1.16f + .12f * MathUtils.sinDeg(angle * 2f));
⋮----
float x1 = impact.x + MathUtils.cosDeg(angle) * from;
float y1 = impact.y + MathUtils.sinDeg(angle) * from;
float x2 = impact.x + MathUtils.cosDeg(angle) * to;
float y2 = impact.y + MathUtils.sinDeg(angle) * to;
shapes.setColor(.68f, .46f, 1f, .22f * fade * flashScale);
shapes.rectLine(x1, y1, x2, y2, .035f + wave * .018f);
⋮----
private void updateAndDrawHazards(ShapeRenderer shapes, Player player, Pools pools, float time) {
float dt = Float.isNaN(lastHazardVisualTime) ? 0f : MathUtils.clamp(time - lastHazardVisualTime, 0f, .05f);
⋮----
hazards.update(dt, player.position.x, player.position.y);
⋮----
float damage = hazards.consumePlayerDamage(player.position.x, player.position.y, player.radius);
⋮----
EnemyProjectile hit = pools.hostileProjectile();
⋮----
hit.spawn(player.position.x, player.position.y, 0f, 0f, damage,
Math.max(.18f, player.radius * .72f), .14f, false, 0f);
⋮----
for (ArenaHazardRuntime.Hazard hazard : hazards.hazards()) {
boolean warning = hazard.phase() == ArenaHazardRuntime.Phase.WARNING;
⋮----
if (FoundryHazardPresentation.isFoundry(hazard.type())) {
FoundryHazardPresentation.Profile profile = FoundryHazardPresentation.forType(hazard.type());
if (hazard.consumeActivationCue()) {
float pitch = hazard.type() == ArenaHazardRuntime.Type.STEAM_JET ? 1.10f
: hazard.type() == ArenaHazardRuntime.Type.HEAT_LINE ? 1.02f : .88f;
AudioDirector.playGlobal(profile.cue, pitch, 0f);
⋮----
drawFoundryHazard(shapes, hazard, profile, warning, time, flashScale);
⋮----
if (NullHazardPresentation.isNull(hazard.type())) {
NullHazardPresentation.Profile profile = NullHazardPresentation.forType(hazard.type());
⋮----
float pitch = hazard.type() == ArenaHazardRuntime.Type.STATIC_BURST ? 1.16f
: hazard.type() == ArenaHazardRuntime.Type.NULL_BEAM ? .94f : .82f;
⋮----
drawNullHazard(shapes, hazard, profile, warning, time, flashScale);
⋮----
float pulse = .5f + .5f * MathUtils.sin(time * (hazard.type() == ArenaHazardRuntime.Type.DEATH_BURST ? 18f : 12f));
⋮----
float urgency = 1f - hazard.warningFraction();
⋮----
if (hazard.type() == ArenaHazardRuntime.Type.DEATH_BURST) {
shapes.setColor(1f, .36f, .08f, alpha);
⋮----
shapes.setColor(1f, .08f, .05f, alpha);
⋮----
shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (1f + pulse * .035f), 36);
shapes.setColor(1f, .76f, .18f, (.10f + urgency * .18f) * flashScale);
shapes.circle(hazard.x(), hazard.y(), Math.max(.12f, hazard.radius() * (.12f + urgency * .08f)), 20);
⋮----
float alpha = (hazard.type() == ArenaHazardRuntime.Type.DEATH_BURST ? .34f : .42f) * flashScale;
⋮----
shapes.setColor(1f, .30f, .04f, alpha);
⋮----
shapes.setColor(1f, .04f, .02f, alpha);
⋮----
shapes.circle(hazard.x(), hazard.y(), hazard.radius(), 40);
shapes.setColor(1f, .82f, .26f, .30f * flashScale);
shapes.circle(hazard.x(), hazard.y(), hazard.radius() * .34f, 24);
⋮----
private void drawFoundryHazard(ShapeRenderer shapes, ArenaHazardRuntime.Hazard hazard,
⋮----
float pulse = .5f + .5f * MathUtils.sin(time * profile.pulseSpeed);
float urgency = warning ? 1f - hazard.warningFraction() : 1f;
int segments = fxBudget.geometrySegments(40, 22);
⋮----
float radius = hazard.radius() * (warning ? 1f + pulse * .035f : 1f);
⋮----
shapes.setColor(r, g, b, alpha);
shapes.circle(hazard.x(), hazard.y(), radius, segments);
⋮----
switch (hazard.type()) {
⋮----
shapes.setColor(1f, .70f, .10f, (warning ? .16f + urgency * .24f : .48f) * flashScale);
shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (.24f + pulse * .06f), segments / 2);
int spokes = fxBudget.allowHeavyFx() ? profile.spokes : 4;
⋮----
float inner = hazard.radius() * .26f;
float outer = hazard.radius() * (.68f + .12f * MathUtils.sinDeg(angle * 3f + time * 90f));
shapes.setColor(1f, .34f, .03f, (warning ? .20f : .52f) * flashScale);
⋮----
hazard.x() + MathUtils.cosDeg(angle) * inner,
hazard.y() + MathUtils.sinDeg(angle) * inner,
hazard.x() + MathUtils.cosDeg(angle + 7f) * outer,
hazard.y() + MathUtils.sinDeg(angle + 7f) * outer,
⋮----
shapes.setColor(.92f, .97f, 1f, (warning ? .22f + urgency * .22f : .58f) * flashScale);
shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (.18f + pulse * .05f), segments / 2);
int jets = fxBudget.allowHeavyFx() ? profile.spokes : 3;
⋮----
float offset = (i - (jets - 1) * .5f) * hazard.radius() * .18f;
float length = hazard.radius() * (warning ? .58f + urgency * .20f : .95f + pulse * .16f);
shapes.setColor(.82f, .93f, 1f, (warning ? .16f : .38f) * flashScale);
shapes.rectLine(hazard.x() + offset, hazard.y() - hazard.radius() * .18f,
hazard.x() + offset * .72f, hazard.y() + length, warning ? .035f : .075f);
⋮----
float band = hazard.radius() * (warning ? .12f + urgency * .05f : .22f);
shapes.setColor(1f, .80f, .18f, (warning ? .18f + urgency * .24f : .52f) * flashScale);
shapes.rectLine(hazard.x() - hazard.radius() * .78f, hazard.y(),
hazard.x() + hazard.radius() * .78f, hazard.y(), band);
shapes.setColor(1f, .32f, .04f, (warning ? .12f : .34f) * flashScale);
shapes.rectLine(hazard.x(), hazard.y() - hazard.radius() * .78f,
hazard.x(), hazard.y() + hazard.radius() * .78f, band * .58f);
⋮----
private void drawNullHazard(ShapeRenderer shapes, ArenaHazardRuntime.Hazard hazard,
⋮----
float radius = hazard.radius() * (warning ? 1f + pulse * .045f : 1f);
⋮----
float core = hazard.radius() * (warning ? .20f + urgency * .08f : .34f - pulse * .08f);
shapes.setColor(.04f, .01f, .12f, (warning ? .28f : .70f) * flashScale);
shapes.circle(hazard.x(), hazard.y(), Math.max(.08f, core), segments / 2);
int spokes = fxBudget.allowHeavyFx() ? profile.spokes : 5;
⋮----
float outer = hazard.radius() * (.86f + pulse * .08f);
float inner = hazard.radius() * .30f;
shapes.setColor(.58f, .30f, 1f, (warning ? .16f : .38f) * flashScale);
⋮----
hazard.x() + MathUtils.cosDeg(angle) * outer,
hazard.y() + MathUtils.sinDeg(angle) * outer,
hazard.x() + MathUtils.cosDeg(angle + 18f) * inner,
hazard.y() + MathUtils.sinDeg(angle + 18f) * inner,
⋮----
shapes.setColor(.86f, .98f, 1f, (warning ? .24f + urgency * .20f : .62f) * flashScale);
shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (.16f + pulse * .07f), segments / 2);
⋮----
float inner = hazard.radius() * .18f;
float outer = hazard.radius() * (.72f + pulse * .18f);
shapes.setColor(.20f, .78f, 1f, (warning ? .20f : .54f) * flashScale);
⋮----
hazard.x() + MathUtils.cosDeg(angle + (i % 2 == 0 ? 8f : -8f)) * outer,
hazard.y() + MathUtils.sinDeg(angle + (i % 2 == 0 ? 8f : -8f)) * outer,
⋮----
float band = hazard.radius() * (warning ? .10f + urgency * .05f : .20f);
shapes.setColor(.80f, .62f, 1f, (warning ? .20f + urgency * .22f : .52f) * flashScale);
shapes.rectLine(hazard.x() - hazard.radius() * .84f, hazard.y(),
hazard.x() + hazard.radius() * .84f, hazard.y(), band);
shapes.setColor(.34f, .84f, 1f, (warning ? .12f : .38f) * flashScale);
shapes.rectLine(hazard.x(), hazard.y() - hazard.radius() * .84f,
hazard.x(), hazard.y() + hazard.radius() * .84f, band * .52f);
⋮----
shapes.setColor(.94f, .86f, 1f, (warning ? .14f : .34f) * flashScale);
shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (.28f + pulse * .05f), segments / 2);
⋮----
private void collectRenderEnemySubsets(Array<Enemy> enemies) {
leaperRenderEnemies.clear();
bossRenderEnemies.clear();
⋮----
if (leapers.contains(enemy)) leaperRenderEnemies.add(enemy);
⋮----
bossRenderEnemies.add(enemy);
⋮----
private void drawBossPhaseTransitions(ShapeRenderer shapes, Array<Enemy> enemies, float time) {
⋮----
if (!enemy.bossPhases.consumePhaseChanged()) continue;
⋮----
phaseFxPhase = enemy.bossPhases.phase();
BossPhaseTransitionProfile.Spec spec = BossPhaseTransitionProfile.forPhase(enemy.bossCombat.identity(), phaseFxPhase);
⋮----
phaseFxUntil = time + spec.duration();
AudioDirector.playGlobal(AudioDirector.Cue.BOSS_PHASE, spec.audioPitch(), 0f);
vibrate(spec.vibrationMs());
if (settings.hitStop) feel.triggerHitStop(phaseFxPhase >= 3 ? .045f : .030f);
⋮----
BossIdentity identity = phaseBoss.bossCombat.identity();
BossPhaseTransitionProfile.Spec spec = BossPhaseTransitionProfile.forPhase(identity, phaseFxPhase);
float progress = MathUtils.clamp((time - phaseFxStarted) / Math.max(.001f, spec.duration()), 0f, 1f);
⋮----
float radius = phaseBoss.radius * MathUtils.lerp(1.08f, spec.radiusMultiplier(), progress);
⋮----
shapes.setColor(r, g, b, (.34f + .18f * fade) * fade * alphaScale);
drawBossPeripheralRing(shapes, phaseBoss.position.x, phaseBoss.position.y, radius, 12,
Math.max(.055f, phaseBoss.radius * .055f));
shapes.setColor(r, g, b, .24f * fade * alphaScale);
drawBossPeripheralRing(shapes, phaseBoss.position.x, phaseBoss.position.y, radius * 1.28f, 8,
Math.max(.045f, phaseBoss.radius * .045f));
if (!settings.reduceFlashes && fxBudget.allowHeavyFx()) {
shapes.setColor(1f, 1f, 1f, .34f * fade);
drawBossPeripheralRing(shapes, phaseBoss.position.x, phaseBoss.position.y,
⋮----
Math.max(.035f, phaseBoss.radius * .04f));
⋮----
private void drawBossPeripheralRing(ShapeRenderer shapes, float cx, float cy,
⋮----
int count = Math.max(4, pips);
⋮----
shapes.circle(cx + MathUtils.cosDeg(angle) * radius,
cy + MathUtils.sinDeg(angle) * radius, pipRadius, 8);
⋮----
private void drawLeaperTelegraphs(ShapeRenderer shapes, Array<Enemy> enemies, float time) {
⋮----
if (!enemy.alive || !leapers.contains(enemy)) continue;
if (leapers.telegraphing(enemy)) {
float pulse = .84f + MathUtils.sin(time * 22f) * .16f;
⋮----
shapes.setColor(1f, .28f, .08f, .18f + .10f * pulse);
shapes.circle(enemy.position.x, enemy.position.y, radius, 22);
shapes.setColor(1f, .78f, .18f, .72f);
shapes.circle(enemy.position.x, enemy.position.y, Math.max(.08f, enemy.radius * .28f), 12);
} else if (fxBudget.allowHeavyFx()) {
shapes.setColor(1f, .45f, .12f, .10f);
shapes.circle(enemy.position.x, enemy.position.y, enemy.radius * 1.32f, 16);
⋮----
private void drawRevenantIdentity(ShapeRenderer shapes, Array<Enemy> enemies, float time) {
⋮----
if (!enemy.alive || enemy.type != Enemy.Type.BOSS || enemy.bossCombat == null || !enemy.bossCombat.revenant()) continue;
float pulse = .86f + MathUtils.sin(time * (enemy.bossCombat.charging() ? 18f : 8f)) * .14f;
⋮----
shapes.setColor(.68f, .10f, .95f, .32f + .08f * pulse);
drawBossPeripheralRing(shapes, enemy.position.x, enemy.position.y, radius, 10,
Math.max(.045f, enemy.radius * .05f));
⋮----
shapes.setColor(1f, .10f, .22f, .36f + .08f * pulse);
drawBossPeripheralRing(shapes, enemy.position.x, enemy.position.y,
⋮----
Math.max(.040f, enemy.radius * .045f));
⋮----
private void drawWardenIdentity(ShapeRenderer shapes, Array<Enemy> enemies, float time) {
⋮----
if (!enemy.alive || enemy.type != Enemy.Type.BOSS || enemy.bossCombat == null || !enemy.bossCombat.warden()) continue;
float pulse = .90f + MathUtils.sin(time * (enemy.bossCombat.charging() ? 9f : 4.5f)) * .10f;
⋮----
shapes.setColor(.10f, .55f, .82f, .30f + .07f * pulse);
drawBossPeripheralRing(shapes, enemy.position.x, enemy.position.y, outer, 12,
Math.max(.050f, enemy.radius * .052f));
shapes.setColor(.96f, .62f, .12f, .38f + .07f * pulse);
⋮----
Math.max(.042f, enemy.radius * .047f));
⋮----
float ring = enemy.radius * (2.32f + MathUtils.sin(time * 3.2f) * .08f);
shapes.setColor(.75f, .88f, 1f, .18f);
drawBossPeripheralRing(shapes, enemy.position.x, enemy.position.y, ring, 6,
Math.max(.034f, enemy.radius * .038f));
⋮----
public void drawAuthoredDeaths(SpriteBatch batch, Pools pools) {
⋮----
deaths.drawAuthored(batch, pools.deathFx);
⋮----
public float fxQuality() { return fxBudget.quality(); }
```

## File: src/main/java/com/deadlinezero/game/visual/CombatSpritePass.java
```java
/** Single entry point for authored combat presentation and optional lightweight grading/audio. */
public final class CombatSpritePass {
⋮----
private final ChampionBadgeRenderer championBadges = new ChampionBadgeRenderer();
private final CombatAudioLayer audio = new CombatAudioLayer();
private final PostFxShader postFx = new PostFxShader();
⋮----
characters = new CharacterSpriteRenderer(art);
environment = new EnvironmentRenderer(art);
weapon = new WeaponRenderer(art);
companions = new CompanionRenderer(art);
vfx = new AuthoredVfxRenderer(art);
quality = GraphicsQuality.autoDetect();
⋮----
public void update(float dt) {
float safeDt = Math.max(0f, dt);
⋮----
environment.update(safeDt);
characters.update(safeDt);
companions.update(safeDt);
⋮----
public boolean authoredAvailable() { return characters.authoredAvailable(); }
public GraphicsQuality quality() { return quality; }
public void setQuality(GraphicsQuality quality) { if (quality != null) this.quality = quality; }
⋮----
/** First world pass: opaque authored/bootstrap floor and hazard tiles. */
public void renderEnvironmentFloor(SpriteBatch batch) {
if (!characters.authoredAvailable()) return;
environment.drawFloor(batch, 1f);
⋮----
/** Second world pass: decals and props, intended above ground FX but below combatants. */
public void renderEnvironmentDressing(SpriteBatch batch) {
⋮----
environment.drawSetDressing(batch);
⋮----
/** Transitional environment-only wrapper retaining the historical blended floor. */
public void renderEnvironment(SpriteBatch batch) {
⋮----
environment.drawAuthored(batch);
⋮----
/** Draws characters, weapons and authored combat VFX, but never draws the environment. */
public void renderCombat(SpriteBatch batch, Player player, Array<Enemy> enemies) {
renderCombat(batch, player, enemies, CombatPolishController.currentPools());
⋮----
public void renderCombat(SpriteBatch batch, Player player, Array<Enemy> enemies, Pools pools) {
audio.update(player, enemies);
⋮----
// Champion identity markers are a ground-layer accessibility cue. Draw them before
// characters so the actor silhouette remains the visual priority.
championBadges.draw(batch, enemies);
if (postFx.available() && quality.postFxIntensity > 0f) batch.setShader(postFx.shader(quality.postFxIntensity));
characters.draw(batch, player, enemies);
batch.setShader(null);
companions.draw(batch, player);
⋮----
Enemy target = nearestEnemy(player, enemies);
float aimAngle = target == null ? fallbackAim(player) :
MathUtils.atan2(target.position.y - player.position.y, target.position.x - player.position.x) * MathUtils.radiansToDegrees;
float shotFlash = target == null || !player.alive ? 0f : MathUtils.clamp(1f - CombatVisualEvents.playerShotAgeSeconds() / .075f, 0f, 1f);
if (!playerSpriteHasIntegratedWeapon()) weapon.draw(batch, player, aimAngle, shotFlash);
if (pools != null) vfx.draw(batch, player, enemies, pools);
⋮----
/** Rex's published production atlas already contains the rifle in every directional motion. */
boolean playerSpriteHasIntegratedWeapon() {
if (RunLoadoutContext.survivor() != SurvivorCatalog.Survivor.REX) return false;
return art.hasAnimation("survivor/rex/e/idle")
&& art.hasAnimation("survivor/rex/e/run")
&& art.hasAnimation("survivor/rex/e/attack");
⋮----
/** Compatibility wrapper preserving the historical environment + combat ordering. */
public void render(SpriteBatch batch, Player player, Array<Enemy> enemies) {
render(batch, player, enemies, CombatPolishController.currentPools());
⋮----
public void render(SpriteBatch batch, Player player, Array<Enemy> enemies, Pools pools) {
renderEnvironment(batch);
renderCombat(batch, player, enemies, pools);
⋮----
private Enemy nearestEnemy(Player player, Array<Enemy> enemies) {
⋮----
float d2 = player.position.dst2(enemy.position);
⋮----
private float fallbackAim(Player player) {
return player.velocity.len2() > .01f ? player.velocity.angleDeg() : 0f;
⋮----
public void dispose() {
championBadges.dispose();
environment.dispose();
audio.dispose();
postFx.dispose();
```

## File: src/main/java/com/deadlinezero/game/visual/CombatVisualEvents.java
```java
/** Presentation-only event bridge for authored animation, VFX and audio timing without gameplay coupling. */
public final class CombatVisualEvents {
⋮----
public static void markPlayerShot() {
lastPlayerShotNanos = TimeUtils.nanoTime();
⋮----
public static void markDash() {
lastDashNanos = TimeUtils.nanoTime();
⋮----
public static void markLevelUp() {
lastLevelUpNanos = TimeUtils.nanoTime();
⋮----
public static void markProtocol(ProtocolCue cue) {
⋮----
lastProtocolNanos = TimeUtils.nanoTime();
⋮----
public static void markSynergy(String key) {
if (key == null || key.isBlank()) return;
⋮----
lastSynergyNanos = TimeUtils.nanoTime();
⋮----
public static void markSentinelIntercept() {
lastSentinelInterceptNanos = TimeUtils.nanoTime();
⋮----
public static float playerShotAgeSeconds() { return age(lastPlayerShotNanos); }
public static float dashAgeSeconds() { return age(lastDashNanos); }
public static float levelUpAgeSeconds() { return age(lastLevelUpNanos); }
public static long playerShotSerial() { return playerShotSerial; }
public static long dashSerial() { return dashSerial; }
public static long levelUpSerial() { return levelUpSerial; }
public static float protocolAgeSeconds() { return age(lastProtocolNanos); }
public static long protocolSerial() { return protocolSerial; }
public static ProtocolCue protocolCue() { return protocolCue; }
public static float synergyAgeSeconds() { return age(lastSynergyNanos); }
public static long synergySerial() { return synergySerial; }
public static String synergyKey() { return synergyKey; }
public static float sentinelInterceptAgeSeconds() { return age(lastSentinelInterceptNanos); }
public static long sentinelInterceptSerial() { return sentinelInterceptSerial; }
⋮----
private static float age(long nanos) {
⋮----
long elapsed = Math.max(0L, TimeUtils.nanoTime() - nanos);
⋮----
public static void reset() {
```

## File: src/main/java/com/deadlinezero/game/visual/CompanionRenderer.java
```java
/** Lightweight authored/fallback companion pass for the Drone ability and its doctrine identity. */
public final class CompanionRenderer {
⋮----
public void update(float dt) { stateTime += Math.max(0f, dt); }
⋮----
public void draw(SpriteBatch batch, Player player) {
if (player == null || !player.alive || player.abilities.level(AbilityType.DRONE) <= 0) return;
⋮----
DroneDoctrine doctrine = player.abilities.droneDoctrine();
float angle = stateTime * orbitSpeedDegrees(doctrine) + 180f;
float orbit = orbitRadius(doctrine);
float x = player.position.x + MathUtils.cosDeg(angle) * orbit;
float y = player.position.y + MathUtils.sinDeg(angle) * orbit;
float pulse = .5f + .5f * MathUtils.sin(stateTime * pulseSpeed(doctrine));
float size = baseSize(doctrine) * (1f + pulse * .08f);
⋮----
TextureRegion region = art.region("companion/drone");
batch.begin();
⋮----
case HUNTER -> batch.setColor(1f, .58f + pulse * .10f, .20f, 1f);
case SENTINEL -> batch.setColor(.36f, .88f + pulse * .08f, 1f, 1f);
default -> batch.setColor(.62f, 1f, .68f, 1f);
⋮----
batch.draw(region, x - size * .5f, y - size * .5f, size, size);
batch.setColor(1f, 1f, 1f, 1f);
batch.end();
⋮----
static float orbitRadius(DroneDoctrine doctrine) {
⋮----
static float orbitSpeedDegrees(DroneDoctrine doctrine) {
⋮----
static float pulseSpeed(DroneDoctrine doctrine) {
⋮----
static float baseSize(DroneDoctrine doctrine) {
```

## File: src/main/java/com/deadlinezero/game/visual/DeathFxRenderer.java
```java
/** Renders death animation first, then long-lived corpses/blood stains. */
public final class DeathFxRenderer {
⋮----
public void drawFallback(ShapeRenderer shapes, Array<DeathFx> effects) {
⋮----
float alpha = MathUtils.clamp(fx.life / Math.max(.001f, fx.maxLife), 0f, 1f);
float fade = Math.min(1f, alpha * 3f);
shapes.setColor(.22f, .015f, .018f, .18f * fade);
shapes.ellipse(fx.x - fx.radius * 1.1f, fx.y - fx.radius * .38f,
⋮----
shapes.setColor(.06f, .055f, .05f, .48f * fade);
shapes.ellipse(fx.x - fx.radius * .82f, fx.y - fx.radius * .22f,
⋮----
// Make the first few frames of a kill read as an event, not just a disappearing sprite.
⋮----
float burst = 1f - MathUtils.clamp(fx.age / burstWindow, 0f, 1f);
⋮----
float x1 = fx.x + MathUtils.cosDeg(angle) * inner;
float y1 = fx.y + MathUtils.sinDeg(angle) * inner;
float x2 = fx.x + MathUtils.cosDeg(angle) * outer;
float y2 = fx.y + MathUtils.sinDeg(angle) * outer;
if (fx.type == Enemy.Type.BOSS) shapes.setColor(1f, .64f, .16f, rayAlpha);
else shapes.setColor(.92f, .14f, .08f, rayAlpha);
shapes.rectLine(x1, y1, x2, y2, Math.max(.028f, fx.radius * .075f * burst));
float shard = Math.max(.035f, fx.radius * (.11f + .08f * burst));
shapes.circle(x2, y2, shard, 8);
⋮----
shapes.setColor(1f, fx.type == Enemy.Type.BOSS ? .72f : .24f,
⋮----
shapes.circle(fx.x, fx.y, outer * .64f, 28);
shapes.setColor(1f, 1f, 1f, .32f * burst);
shapes.circle(fx.x, fx.y, Math.max(.06f, fx.radius * .30f * burst), 12);
⋮----
// Persistent secondary splatter breaks up the otherwise clean arena floor.
float splatter = Math.min(1f, fx.age / .16f) * fade;
shapes.setColor(.20f, .012f, .016f, .16f * splatter);
shapes.circle(fx.x + fx.radius * .58f, fx.y - fx.radius * .20f, fx.radius * .28f, 10);
shapes.circle(fx.x - fx.radius * .52f, fx.y + fx.radius * .08f, fx.radius * .20f, 9);
⋮----
public void drawAuthored(SpriteBatch batch, Array<DeathFx> effects) {
if (!art.authoredAvailable()) return;
batch.begin();
⋮----
AnimationProfileCatalog.Profile anim = AnimationProfileCatalog.enemy(fx.type);
float deathWindow = MathUtils.clamp(anim.death() * (fx.type == Enemy.Type.BOSS ? 7f : 6f), .55f, 1.15f);
⋮----
? art.enemy(fx.type, GameArt.Motion.DEATH, fx.age)
: art.regionOrNull("enemy/" + fx.type.name().toLowerCase() + "/corpse");
⋮----
float w = Math.max(.8f, fx.radius * (fx.type == Enemy.Type.BOSS ? 4.1f : 2.8f));
⋮----
float h = w * region.getRegionHeight() / (float)Math.max(1, region.getRegionWidth());
batch.setColor(1f, 1f, 1f, animatingDeath ? 1f : fade);
batch.draw(region,
⋮----
batch.setColor(1f, 1f, 1f, 1f);
batch.end();
```

## File: src/main/java/com/deadlinezero/game/visual/Direction8.java
```java
/** Stable eight-way facing used by authored top-down character animation keys. */
⋮----
public String atlasToken() { return atlasToken; }
⋮----
/** Keeps the previous facing while nearly stationary to avoid idle-direction flicker. */
public static Direction8 fromVector(float x, float y, Direction8 fallback) {
⋮----
if (!Float.isFinite(x) || !Float.isFinite(y) || x * x + y * y < MIN_DIRECTION_LEN2) return safeFallback;
⋮----
double degrees = Math.toDegrees(Math.atan2(y, x));
int octant = Math.floorMod((int)Math.floor((degrees + 22.5d) / 45d), 8);
```

## File: src/main/java/com/deadlinezero/game/visual/DirectionalBootstrapArt.java
```java
/**
 * Deterministic eight-way bootstrap sheet for the core combat roster.
 * Geometry/key lookup is always available, while the GPU texture is generated lazily on first use.
 * Final atlas frames always override this bootstrap layer.
 */
public final class DirectionalBootstrapArt implements Disposable {
⋮----
public static DirectionalBootstrapArt create() {
return new DirectionalBootstrapArt();
⋮----
public boolean supports(String key) { return firstTile(key) >= 0; }
⋮----
public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
int first = firstTile(key);
⋮----
TextureRegion[] loadedRegions = ensureRegions();
⋮----
int count = frameCount(key);
⋮----
int rawFrame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
int frame = loop ? rawFrame % count : Math.min(count - 1, rawFrame);
⋮----
private TextureRegion[] ensureRegions() {
⋮----
Pixmap pixmap = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
pixmap.setBlending(Pixmap.Blending.SourceOver);
⋮----
for (int actor = 0; actor < ACTOR_COUNT; actor++) drawActorSet(pixmap, actor, actor * ACTOR_BLOCK);
texture = new Texture(pixmap);
texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
⋮----
regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
⋮----
if (texture != null) texture.dispose();
⋮----
pixmap.dispose();
⋮----
static int firstTile(String key) {
if (key == null || key.isBlank()) return -1;
int actor = actorIndex(key);
⋮----
String rest = key.substring(ROOTS[actor].length());
int slash = rest.indexOf('/');
if (slash <= 0 || slash >= rest.length() - 1) return -1;
int direction = directionIndex(rest.substring(0, slash));
int motion = motionOffset(rest.substring(slash + 1));
⋮----
static int frameCount(String key) {
if (firstTile(key) < 0) return 0;
int slash = key.lastIndexOf('/');
String motion = key.substring(slash + 1);
⋮----
static int actorIndex(String key) {
⋮----
for (int i = 0; i < ROOTS.length; i++) if (key.startsWith(ROOTS[i])) return i;
⋮----
private static int directionIndex(String token) {
⋮----
private static int motionOffset(String motion) {
⋮----
private static void drawActorSet(Pixmap p, int actor, int actorBase) {
⋮----
drawFrame(p, base, actor, dx, dy, 0, 0);
drawFrame(p, base + 1, actor, dx, dy, 0, 1);
drawFrame(p, base + 2, actor, dx, dy, 1, 0);
drawFrame(p, base + 3, actor, dx, dy, 1, 1);
drawFrame(p, base + 4, actor, dx, dy, 1, 2);
drawFrame(p, base + 5, actor, dx, dy, 2, 0);
drawFrame(p, base + 6, actor, dx, dy, 2, 1);
drawFrame(p, base + 7, actor, dx, dy, 3, 0);
drawFrame(p, base + 8, actor, dx, dy, 3, 1);
drawFrame(p, base + 9, actor, dx, dy, 4, 0);
drawFrame(p, base + 10, actor, dx, dy, 4, 1);
drawFrame(p, base + 11, actor, dx, dy, 4, 2);
⋮----
/** motion: 0 idle, 1 run, 2 attack, 3 hit, 4 death. */
private static void drawFrame(Pixmap p, int tile, int actor, int dx, int dy, int motion, int frame) {
⋮----
int attackReach = motion == 2 && frame == 1 ? (isBoss(actor) ? 5 : 4) : 0;
int bodyRadius = bodyRadius(actor);
⋮----
drawShadow(p, ox, oy, actor, motion, frame, bodyRadius);
⋮----
drawDeath(p, ox, oy, actor, dx, bodyRadius, frame);
⋮----
drawLegs(p, actor, cx, cy, sx, sy, perpX, perpY, stride);
drawTorso(p, actor, cx, cy, sx, sy, bodyRadius);
drawIdentitySilhouette(p, actor, cx, cy, sx, sy, perpX, perpY);
drawFacingDetail(p, actor, cx, cy, sx, sy, perpX, perpY);
drawWeaponOrClaw(p, actor, cx, cy, sx, sy, perpX, perpY, motion, frame, attackReach);
⋮----
if (motion == 3) drawHitSpark(p, ox, oy, sx, sy, frame);
if (motion == 0 && frame == 1 && (isSurvivor(actor) || isBoss(actor))) drawIdlePulse(p, cx, cy, actor);
⋮----
private static void drawShadow(Pixmap p, int ox, int oy, int actor, int motion, int frame, int bodyRadius) {
int alpha = isBoss(actor) ? 105 : 78;
set(p, 0, 0, 0, alpha);
int radius = motion == 4 ? Math.max(3, bodyRadius - 2) : bodyRadius + 1;
⋮----
p.fillCircle(x, oy + 26, radius);
set(p, 22, 28, 35, alpha / 2);
p.drawLine(x - radius, oy + 27, x + radius, oy + 27);
⋮----
private static void drawDeath(Pixmap p, int ox, int oy, int actor, int dx, int bodyRadius, int frame) {
⋮----
setSecondary(p, actor);
p.fillCircle(ox + 16 + shift, oy + 18 + frame * 3, Math.max(4, bodyRadius - frame));
setPrimary(p, actor);
p.fillCircle(ox + 13 + shift, oy + 12 + frame * 4, Math.max(3, bodyRadius - 2 - frame));
setAccent(p, actor);
p.drawLine(ox + 8 + shift, oy + 14 + frame * 3, ox + 23 + shift, oy + 20 + frame * 3);
⋮----
set(p, 0.96f, .18f, .18f, .72f);
p.drawLine(ox + 9 + shift, oy + 24, ox + 21 + shift, oy + 25);
⋮----
private static void drawLegs(Pixmap p, int actor, int cx, int cy, int sx, int sy, int perpX, int perpY, int stride) {
⋮----
p.drawLine(leftX, leftY, leftX + sx * stride - perpX, cy + 13 - perpY * 2);
p.drawLine(rightX, rightY, rightX - sx * stride + perpX, cy + 13 + perpY * 2);
⋮----
p.fillCircle(leftX + sx * stride - perpX, cy + 13 - perpY * 2, isBoss(actor) ? 2 : 1);
p.fillCircle(rightX - sx * stride + perpX, cy + 13 + perpY * 2, isBoss(actor) ? 2 : 1);
⋮----
private static void drawTorso(Pixmap p, int actor, int cx, int cy, int sx, int sy, int bodyRadius) {
⋮----
p.fillCircle(cx, cy + 2, bodyRadius);
⋮----
p.fillCircle(cx + sx * 2, cy - 7 + sy * 2, Math.max(5, bodyRadius - 2));
⋮----
p.drawLine(cx - 4, cy - 1, cx + 4, cy - 1);
p.drawLine(cx - 3, cy, cx + 3, cy);
if (isSurvivor(actor) || isBoss(actor)) p.drawLine(cx - 4 + sx, cy + 4 + sy, cx + 4 + sx, cy + 4 + sy);
⋮----
private static void drawFacingDetail(Pixmap p, int actor, int cx, int cy, int sx, int sy, int perpX, int perpY) {
⋮----
if (isSurvivor(actor) || actor == 4 || actor == 8) {
p.drawLine(faceX - perpX * 3, faceY - perpY * 3, faceX + perpX * 3, faceY + perpY * 3);
if (isSurvivor(actor)) p.drawLine(faceX - perpX * 2 + sx, faceY - perpY * 2 + sy,
⋮----
p.fillCircle(faceX - perpX * 2, faceY - perpY * 2, 1);
p.fillCircle(faceX + perpX * 2, faceY + perpY * 2, 1);
⋮----
private static void drawWeaponOrClaw(Pixmap p, int actor, int cx, int cy, int sx, int sy,
⋮----
p.drawLine(cx, cy + 2, handX, handY);
⋮----
if (isSurvivor(actor) || actor == 4) {
⋮----
p.drawLine(handX - perpX, handY - perpY, muzzleX - perpX, muzzleY - perpY);
⋮----
p.drawLine(handX, handY, muzzleX, muzzleY);
drawWeaponIdentity(p, actor, handX, handY, muzzleX, muzzleY, perpX, perpY);
⋮----
set(p, 1f, .83f, .35f, 1f);
p.fillCircle(muzzleX + sx * 2, muzzleY + sy * 2, 2);
set(p, 1f, .96f, .72f, .85f);
p.drawLine(muzzleX + sx, muzzleY + sy, muzzleX + sx * 4, muzzleY + sy * 4);
⋮----
p.drawLine(cx - perpX * 2, cy + 3 - perpY * 2, clawX, clawY);
⋮----
p.drawLine(clawX, clawY, clawX + sx * 2 - perpX, clawY + sy * 2 - perpY);
⋮----
private static void drawWeaponIdentity(Pixmap p, int actor, int handX, int handY, int muzzleX, int muzzleY,
⋮----
case 0 -> p.drawLine(handX - perpX * 2, handY - perpY * 2, handX + perpX * 2, handY + perpY * 2);
case 10 -> p.drawLine(handX + perpX * 2, handY + perpY * 2, muzzleX + perpX * 3, muzzleY + perpY * 3);
⋮----
p.fillCircle(handX, handY, 2);
⋮----
p.drawLine(handX + perpX * 2, handY + perpY * 2, muzzleX + perpX * 2, muzzleY + perpY * 2);
⋮----
case 12 -> p.drawLine(muzzleX - perpX * 2, muzzleY - perpY * 2, muzzleX + perpX * 2, muzzleY + perpY * 2);
⋮----
p.drawLine(muzzleX, muzzleY, muzzleX + perpX * 3, muzzleY + perpY * 3);
p.drawLine(muzzleX, muzzleY, muzzleX - perpX * 3, muzzleY - perpY * 3);
⋮----
private static void drawHitSpark(Pixmap p, int ox, int oy, int sx, int sy, int frame) {
⋮----
set(p, 1f, .88f, .82f, frame == 0 ? 1f : .72f);
⋮----
p.drawLine(hx - reach, hy - reach, hx + reach, hy + reach);
p.drawLine(hx - reach, hy + reach, hx + reach, hy - reach);
set(p, 1f, .42f, .28f, frame == 0 ? .90f : .48f);
p.fillCircle(hx, hy, frame == 0 ? 2 : 1);
⋮----
private static void drawIdlePulse(Pixmap p, int cx, int cy, int actor) {
⋮----
p.drawLine(cx - 2, cy + 6, cx + 2, cy + 6);
⋮----
private static void drawIdentitySilhouette(Pixmap p, int actor, int cx, int cy, int sx, int sy, int perpX, int perpY) {
⋮----
case 1 -> p.drawLine(cx - perpX * 5, cy + 1 - perpY * 5, cx - perpX * 8 - sx, cy + 4 - perpY * 8 - sy);
⋮----
p.drawLine(cx - sx * 2, cy - 5 - sy * 2, cx - sx * 6, cy - 10 - sy * 6);
p.drawLine(cx + perpX * 3, cy - 5 + perpY * 3, cx - sx * 4 + perpX * 4, cy - 9 - sy * 4 + perpY * 4);
⋮----
p.drawLine(cx - perpX * 8, cy, cx + perpX * 8, cy);
p.drawLine(cx - perpX * 7, cy + 1, cx + perpX * 7, cy + 1);
⋮----
case 4 -> p.drawLine(cx + sx * 4, cy + sy * 4, cx + sx * 13, cy + sy * 13);
⋮----
p.drawLine(cx - perpX * 5, cy - 7 - perpY * 5, cx - perpX * 7 + sx * 2, cy - 12 - perpY * 7 + sy * 2);
p.drawLine(cx + perpX * 5, cy - 7 + perpY * 5, cx + perpX * 7 + sx * 2, cy - 12 + perpY * 7 + sy * 2);
⋮----
p.drawLine(cx - perpX * 5, cy - 7, cx - perpX * 8 - sx * 2, cy - 11 - sy * 2);
p.drawLine(cx + perpX * 5, cy - 7, cx + perpX * 8 - sx * 2, cy - 11 - sy * 2);
p.drawLine(cx, cy - 11, cx - sx * 3, cy - 15 - sy * 3);
⋮----
p.drawCircle(cx, cy - 7, 8);
p.drawLine(cx - perpX * 7, cy - 7 - perpY * 7, cx + perpX * 7, cy - 7 + perpY * 7);
⋮----
p.drawRectangle(cx - 7, cy - 3, 14, 10);
p.drawLine(cx - 6, cy + 1, cx + 6, cy + 1);
⋮----
p.drawCircle(cx + sx * 5, cy + sy * 5, 9);
⋮----
p.fillCircle(cx + sx * 2, cy + sy * 2, 6);
⋮----
p.drawLine(cx + sx * 5 - perpX * 8, cy + sy * 5 - perpY * 8,
⋮----
p.drawLine(cx - perpX * 4, cy - 6 - perpY * 4, cx - perpX * 6 - sx * 2, cy - 12 - perpY * 6 - sy * 2);
p.drawLine(cx + perpX * 4, cy - 6 + perpY * 4, cx + perpX * 6 - sx * 2, cy - 12 + perpY * 6 - sy * 2);
⋮----
p.drawRectangle(cx - 8, cy - 2, 16, 7);
p.drawLine(cx - perpX * 8, cy + 5, cx + perpX * 8, cy + 5);
p.drawLine(cx - 6, cy - 4, cx + 6, cy - 4);
⋮----
p.drawCircle(cx, cy + 1, 9);
p.drawLine(cx - 6, cy - 8, cx - 2, cy - 12);
p.drawLine(cx + 2, cy - 12, cx + 6, cy - 8);
p.drawLine(cx - 8, cy + 3, cx - 5, cy + 7);
p.drawLine(cx + 5, cy + 7, cx + 8, cy + 3);
⋮----
p.drawCircle(cx + sx, cy - 6 + sy, 7);
p.drawLine(cx - perpX * 6, cy + 5 - perpY * 6, cx + sx * 2, cy + 9 + sy * 2);
p.drawLine(cx + perpX * 6, cy + 5 + perpY * 6, cx + sx * 2, cy + 9 + sy * 2);
p.drawLine(cx - perpX * 5, cy + 7 - perpY * 5, cx - perpX * 3 - sx * 3, cy + 12 - perpY * 3 - sy * 3);
⋮----
private static int bodyRadius(int actor) {
⋮----
if (isBoss(actor)) return 10;
return isSurvivor(actor) ? 7 : 8;
⋮----
private static boolean isBoss(int actor) { return actor >= 6 && actor <= 9; }
private static boolean isSurvivor(int actor) { return actor == 0 || actor >= 10; }
⋮----
private static void setPrimary(Pixmap p, int actor) { setPalette(p, PRIMARY[actor]); }
private static void setSecondary(Pixmap p, int actor) { setPalette(p, SECONDARY[actor]); }
private static void setAccent(Pixmap p, int actor) { setPalette(p, ACCENT[actor]); }
private static void setPalette(Pixmap p, float[] color) { p.setColor(color[0], color[1], color[2], 1f); }
⋮----
private static void set(Pixmap p, int r, int g, int b, int a) {
p.setColor(r / 255f, g / 255f, b / 255f, a / 255f);
⋮----
private static void set(Pixmap p, float r, float g, float b, float a) { p.setColor(r, g, b, a); }
⋮----
@Override public void dispose() {
```

## File: src/main/java/com/deadlinezero/game/visual/EnemyHealthBarPresentation.java
```java
/** Pure presentation policy for world-space enemy health bars. */
public final class EnemyHealthBarPresentation {
⋮----
public static boolean visible(Enemy enemy) {
⋮----
public static float widthMultiplier(Enemy enemy) {
```

## File: src/main/java/com/deadlinezero/game/visual/EnvironmentArtCatalog.java
```java
/**
 * Canonical production naming for biome-specific environment art.
 *
 * <p>The runtime always prefers these atlas regions and then falls back to the existing generic
 * environment keys/bootstrap art. This lets production art land biome-by-biome without making
 * incomplete packs crash or visually disappear.</p>
 */
public final class EnvironmentArtCatalog {
⋮----
public static String biomeToken(EnvironmentBiomeRules.Biome biome) {
⋮----
return safe.name().toLowerCase(java.util.Locale.ROOT);
⋮----
public static String productionKey(EnvironmentBiomeRules.Biome biome, String genericKey) {
if (genericKey == null || !genericKey.startsWith(ROOT)) return genericKey;
return ROOT + biomeToken(biome) + "/" + genericKey.substring(ROOT.length());
⋮----
public static List<String> productionKeys(EnvironmentBiomeRules.Biome biome) {
⋮----
for (String generic : BootstrapEnvironmentArt.KEYS) keys.add(productionKey(biome, generic));
return Collections.unmodifiableList(keys);
⋮----
public static List<String> allProductionKeys() {
⋮----
EnvironmentBiomeRules.Biome.values().length * BootstrapEnvironmentArt.KEYS.length);
for (EnvironmentBiomeRules.Biome biome : EnvironmentBiomeRules.Biome.values()) {
keys.addAll(productionKeys(biome));
```

## File: src/main/java/com/deadlinezero/game/visual/EnvironmentBiomeRules.java
```java
/** Pure stage-to-biome routing used by environment presentation and tests. */
public final class EnvironmentBiomeRules {
⋮----
public String labelKey() { return "biome." + name().toLowerCase(java.util.Locale.ROOT); }
⋮----
public static Biome forStage(int stage) {
int safe = Math.max(1, stage);
⋮----
public static boolean isFoundry(int stage) {
return forStage(stage) == Biome.CINDER_FOUNDRY;
⋮----
public static boolean isNullSector(int stage) {
return forStage(stage) == Biome.NULL_SECTOR;
⋮----
public static boolean isCryoVault(int stage) {
return forStage(stage) == Biome.CRYO_VAULT;
⋮----
public static boolean isCryogenicDepths(int stage) {
return forStage(stage) == Biome.CRYOGENIC_DEPTHS;
```

## File: src/main/java/com/deadlinezero/game/visual/EnvironmentRenderer.java
```java
/**
 * Deterministic combat environment presentation.
 * Procedural geometry is always available; final atlas art takes priority over generated bootstrap art.
 */
public final class EnvironmentRenderer implements Disposable {
⋮----
private static final Color FOUNDRY_WALL_TINT = new Color(1f, .58f, .32f, 1f);
private static final Color NULL_WALL_TINT = new Color(.64f, .56f, 1f, 1f);
private static final Color CRYO_WALL_TINT = new Color(.58f, .88f, 1f, 1f);
private static final Color DEPTHS_WALL_TINT = new Color(.40f, .72f, .86f, 1f);
⋮----
bootstrap = BootstrapEnvironmentArt.create();
⋮----
public void update(float dt) {
visualTime += MathUtils.clamp(dt, 0f, .1f);
⋮----
private boolean foundry() {
return EnvironmentBiomeRules.isFoundry(RunStageContext.stage());
⋮----
private boolean nullSector() {
return EnvironmentBiomeRules.isNullSector(RunStageContext.stage());
⋮----
private boolean cryoVault() {
return EnvironmentBiomeRules.isCryoVault(RunStageContext.stage());
⋮----
private boolean cryogenicDepths() {
return EnvironmentBiomeRules.isCryogenicDepths(RunStageContext.stage());
⋮----
public void drawGround(ShapeRenderer shapes, float time) {
if (cryogenicDepths()) drawCryogenicDepthsGround(shapes, time);
else if (cryoVault()) drawCryoVaultGround(shapes, time);
else if (nullSector()) drawNullSectorGround(shapes, time);
else if (foundry()) drawFoundryGround(shapes, time);
else drawQuarantineGround(shapes, time);
⋮----
private void drawQuarantineGround(ShapeRenderer shapes, float time) {
shapes.setColor(.016f, .025f, .033f, 1f);
shapes.rect(-HALF_W, -HALF_H, HALF_W * 2f, HALF_H * 2f);
shapes.setColor(.030f, .055f, .064f, .84f);
for (int x = -40; x <= 40; x += 4) shapes.rect(x, -24f, .035f, 48f);
for (int y = -24; y <= 24; y += 4) shapes.rect(-40f, y, 80f, .035f);
shapes.setColor(.055f, .10f, .105f, .46f);
⋮----
float ox = MathUtils.sin(x * 1.7f + y * .4f) * .35f;
float oy = MathUtils.cos(y * 1.3f + x * .2f) * .28f;
shapes.rect(x + ox, y + oy, 1.1f, .07f);
shapes.rect(x + ox + .25f, y + oy - .34f, .62f, .045f);
⋮----
float pulse = .035f + .018f * (MathUtils.sin(time * 1.4f) * .5f + .5f);
shapes.setColor(VisualTheme.RED.r, VisualTheme.RED.g, VisualTheme.RED.b, pulse);
shapes.circle(-17f, 8f, 5.2f, 36);
shapes.circle(19f, -10f, 4.1f, 32);
⋮----
private void drawFoundryGround(ShapeRenderer shapes, float time) {
shapes.setColor(.032f, .019f, .017f, 1f);
⋮----
shapes.setColor(.12f, .050f, .025f, .78f);
for (int x = -40; x <= 40; x += 4) shapes.rect(x, -24f, .045f, 48f);
for (int y = -24; y <= 24; y += 4) shapes.rect(-40f, y, 80f, .045f);
shapes.setColor(.20f, .075f, .025f, .35f);
⋮----
float skew = MathUtils.sin(x * .37f + y * .61f) * .44f;
shapes.rect(x + skew, y, 1.7f, .08f);
shapes.rect(x + skew + .55f, y - .40f, .85f, .05f);
⋮----
float furnace = .5f + .5f * MathUtils.sin(time * 2.15f);
shapes.setColor(1f, .19f, .025f, .035f + furnace * .028f);
shapes.circle(-20f, -9f, 6.4f + furnace * .7f, 40);
shapes.circle(18f, 9f, 5.2f + furnace * .55f, 36);
shapes.setColor(1f, .58f, .08f, .055f + furnace * .03f);
shapes.rect(-4.5f, -24f, 9f, 48f);
⋮----
private void drawNullSectorGround(ShapeRenderer shapes, float time) {
shapes.setColor(.012f, .012f, .032f, 1f);
⋮----
float pulse = .5f + .5f * MathUtils.sin(time * 1.65f);
shapes.setColor(.12f, .10f, .30f, .72f);
for (int x = -40; x <= 40; x += 4) shapes.rect(x, -24f, .030f + pulse * .012f, 48f);
for (int y = -24; y <= 24; y += 4) shapes.rect(-40f, y, 80f, .030f + pulse * .012f);
shapes.setColor(.35f, .18f, .82f, .12f + pulse * .07f);
⋮----
float x = i * 8.2f + MathUtils.sin(time * .75f + i) * .55f;
shapes.rectLine(x - 5.5f, -20f, x + 5.5f, 20f, .045f);
⋮----
shapes.setColor(.12f, .72f, 1f, .055f + pulse * .045f);
shapes.circle(-18f, 10f, 4.4f + pulse * .55f, 36);
shapes.circle(20f, -8f, 5.6f + pulse * .70f, 40);
shapes.setColor(.72f, .28f, 1f, .04f + pulse * .035f);
shapes.rect(-3.2f, -24f, 6.4f, 48f);
⋮----
private void drawCryogenicDepthsGround(ShapeRenderer shapes, float time) {
shapes.setColor(.004f, .014f, .024f, 1f);
⋮----
float pulse = .5f + .5f * MathUtils.sin(time * .82f);
shapes.setColor(.045f, .16f, .22f, .82f);
for (int x = -40; x <= 40; x += 5) shapes.rect(x, -24f, .040f, 48f);
for (int y = -24; y <= 24; y += 5) shapes.rect(-40f, y, 80f, .040f);
shapes.setColor(.16f, .58f, .72f, .08f + pulse * .05f);
⋮----
shapes.rectLine(x - 5f, -22f, x + 2f, 22f, .055f);
shapes.rectLine(x + 2f, -22f, x - 5f, 22f, .028f);
⋮----
shapes.setColor(.48f, .88f, 1f, .045f + pulse * .035f);
shapes.circle(-20f, 11f, 6.2f + pulse * .7f, 40);
shapes.circle(21f, -10f, 6.8f + pulse * .8f, 40);
⋮----
private void drawCryoVaultGround(ShapeRenderer shapes, float time) {
shapes.setColor(.010f, .024f, .038f, 1f);
⋮----
float pulse = .5f + .5f * MathUtils.sin(time * 1.15f);
shapes.setColor(.10f, .28f, .38f, .68f);
for (int x = -40; x <= 40; x += 4) shapes.rect(x, -24f, .028f, 48f);
for (int y = -24; y <= 24; y += 4) shapes.rect(-40f, y, 80f, .028f);
shapes.setColor(.44f, .86f, 1f, .08f + pulse * .05f);
⋮----
shapes.rectLine(x - 3.2f, -21f, x + 3.8f, 21f, .038f);
⋮----
shapes.setColor(.64f, .94f, 1f, .055f + pulse * .045f);
shapes.circle(-21f, -10f, 5.4f + pulse * .5f, 40);
shapes.circle(18f, 9f, 4.7f + pulse * .4f, 36);
shapes.setColor(.22f, .70f, 1f, .035f + pulse * .03f);
shapes.rect(-6.0f, -24f, 12f, 48f);
⋮----
public void drawAuthored(SpriteBatch batch) {
if (!hasAnyEnvironmentArt()) return;
batch.begin();
drawFloorInternal(batch, TRANSITION_FLOOR_ALPHA);
drawSetDressingInternal(batch);
batch.setColor(Color.WHITE);
batch.end();
⋮----
public void drawFloor(SpriteBatch batch, float alpha) {
if (!hasFloorArt()) return;
⋮----
drawFloorInternal(batch, MathUtils.clamp(alpha, 0f, 1f));
⋮----
public void drawSetDressing(SpriteBatch batch) {
if (!hasSetDressingArt()) return;
⋮----
private void drawFloorInternal(SpriteBatch batch, float alpha) {
// Floor art carries structure, not the focal hierarchy. Keep it below actors/projectiles
// so phone-scale combat reads cleanly even with authored high-frequency texture detail.
if (cryogenicDepths()) batch.setColor(.48f, .76f, .88f, alpha * .72f);
else if (cryoVault()) batch.setColor(.72f, .92f, 1f, alpha * .74f);
else if (nullSector()) batch.setColor(.66f, .62f, 1f, alpha * .70f);
else if (foundry()) batch.setColor(1f, .63f, .43f, alpha * .76f);
else batch.setColor(.84f, .92f, .96f, alpha * .78f);
⋮----
int variant = floorVariant(gx, gy);
TextureRegion region = region("environment/floor/concrete_" + (char)('a' + variant));
if (region != null) batch.draw(region, gx * FLOOR_TILE_WORLD, gy * FLOOR_TILE_WORLD, FLOOR_TILE_WORLD, FLOOR_TILE_WORLD);
⋮----
TextureRegion hazard = region("environment/floor/hazard_a");
⋮----
if (cryogenicDepths()) batch.setColor(.10f, .62f, .78f, Math.min(1f, alpha * .82f));
else if (cryoVault()) batch.setColor(.28f, .82f, 1f, Math.min(1f, alpha * .80f));
else if (nullSector()) batch.setColor(.48f, .28f, 1f, Math.min(1f, alpha * .78f));
else if (foundry()) batch.setColor(1f, .38f, .08f, Math.min(1f, alpha * .84f));
else batch.setColor(.82f, .94f, 1f, Math.min(1f, alpha * .76f));
if (cryogenicDepths()) {
⋮----
batch.draw(hazard, x, -13f, HAZARD_TILE_WORLD, HAZARD_TILE_WORLD);
batch.draw(hazard, x + 6f, 13f, HAZARD_TILE_WORLD, HAZARD_TILE_WORLD);
⋮----
} else if (cryoVault()) {
⋮----
batch.draw(hazard, x, -10f, HAZARD_TILE_WORLD, HAZARD_TILE_WORLD);
batch.draw(hazard, -x, 10f, HAZARD_TILE_WORLD, HAZARD_TILE_WORLD);
⋮----
} else if (nullSector()) {
⋮----
batch.draw(hazard, x, y, HAZARD_TILE_WORLD, HAZARD_TILE_WORLD);
⋮----
} else if (foundry()) {
⋮----
// Quarantine Yard uses sparse perimeter warning pads instead of a continuous
// center stripe, keeping the player and combat readable on phone screens.
⋮----
static int floorVariant(int gridX, int gridY) {
return Math.floorMod(gridX * 31 + gridY * 17, 3);
⋮----
static int detailVariant(int gridX, int gridY) {
⋮----
return Math.floorMod(h, 8);
⋮----
static float beaconPulse(float time) {
return .5f + .5f * MathUtils.sin(time * 2.8f);
⋮----
private void drawSetDressingInternal(SpriteBatch batch) {
TextureRegion crack = region("environment/decal/crack_a");
TextureRegion blood = region("environment/decal/blood_a");
TextureRegion scorch = region("environment/decal/scorch_a");
TextureRegion barrier = region("environment/prop/barrier_a");
TextureRegion debrisA = region("environment/prop/debris_a");
TextureRegion debrisB = region("environment/prop/debris_b");
TextureRegion wallA = region("environment/prop/wall_a");
TextureRegion wallB = region("environment/prop/wall_b");
TextureRegion crate = region("environment/prop/crate_a");
TextureRegion beacon = region("environment/prop/beacon_a");
⋮----
drawAmbientDetails(batch, crack, blood, scorch, debrisA, debrisB);
⋮----
drawCryogenicDepthsDressing(batch, crack, scorch, barrier, debrisA, debrisB, wallA, wallB, crate, beacon);
⋮----
if (cryoVault()) {
drawCryoVaultDressing(batch, crack, scorch, barrier, debrisA, debrisB, wallA, wallB, crate, beacon);
⋮----
if (nullSector()) {
drawNullSectorDressing(batch, crack, scorch, barrier, debrisA, debrisB, wallA, wallB, crate, beacon);
⋮----
if (foundry()) {
drawFoundryDressing(batch, crack, scorch, barrier, debrisA, debrisB, wallA, wallB, crate, beacon);
⋮----
batch.setColor(1f, 1f, 1f, .76f);
draw(batch, crack, -10.5f, -7.2f, 3.4f);
draw(batch, crack, 13.4f, 6.1f, 2.8f);
draw(batch, crack, -18f, 9f, 2.35f);
batch.setColor(1f, 1f, 1f, .60f);
draw(batch, blood, 5.8f, -3.2f, 2.2f);
draw(batch, blood, -4.2f, 10.1f, 1.7f);
draw(batch, scorch, 9.3f, 8.4f, 3.6f);
⋮----
drawPropShadow(batch, barrier, -21f, 12f, 2.9f);
drawPropShadow(batch, barrier, 22f, -13f, 2.9f);
drawPropShadow(batch, crate, -16f, 14.2f, 2.7f);
drawPropShadow(batch, crate, 17.5f, -12.8f, 2.5f);
drawArenaEdgeShadows(batch, wallA, wallB);
⋮----
batch.setColor(1f, 1f, 1f, 1f);
draw(batch, barrier, -21f, 12f, 2.9f);
draw(batch, barrier, 22f, -13f, 2.9f);
draw(batch, debrisA, -24f, -11.5f, 3.4f);
draw(batch, debrisB, 20f, 10.4f, 3.1f);
draw(batch, crate, -16f, 14.2f, 2.7f);
draw(batch, crate, 17.5f, -12.8f, 2.5f);
drawArenaEdge(batch, wallA, wallB, beacon);
⋮----
private void drawAmbientDetails(SpriteBatch batch, TextureRegion crack, TextureRegion blood,
⋮----
boolean depthsBiome = cryogenicDepths();
boolean cryoBiome = cryoVault();
boolean nullBiome = nullSector();
boolean hotBiome = foundry();
⋮----
int variant = detailVariant(gx, gy);
⋮----
if (Math.abs(x) < 4.5f && Math.abs(y) < 3.6f) continue;
float scaleJitter = 1f + ((detailVariant(gx + 13, gy - 7) - 3.5f) * .045f);
⋮----
if (depthsBiome) batch.setColor(.48f, .82f, .92f, variant == 0 ? .40f : .24f);
else if (cryoBiome) batch.setColor(.72f, .94f, 1f, variant == 0 ? .42f : .25f);
else if (nullBiome) batch.setColor(.72f, .62f, 1f, variant == 0 ? .38f : .23f);
else batch.setColor(1f, 1f, 1f, variant == 0 ? (hotBiome ? .34f : .40f) : .22f);
draw(batch, crack, x, y, (variant == 0 ? 1.45f : 1.05f) * scaleJitter);
⋮----
if (depthsBiome) batch.setColor(.24f, .66f, .78f, variant == 1 ? .30f : .18f);
else if (cryoBiome) batch.setColor(.48f, .86f, 1f, variant == 1 ? .28f : .17f);
else if (nullBiome) batch.setColor(.52f, .32f, 1f, variant == 1 ? .30f : .18f);
else batch.setColor(1f, hotBiome ? .45f : 1f, hotBiome ? .20f : 1f, variant == 1 ? .28f : .17f);
draw(batch, stain, x, y, (variant == 1 ? 1.35f : .98f) * scaleJitter);
⋮----
drawPropShadow(batch, debris, x, y, 1.25f * scaleJitter);
if (depthsBiome) batch.setColor(.52f, .82f, .90f, .76f);
else if (cryoBiome) batch.setColor(.74f, .92f, 1f, .78f);
else if (nullBiome) batch.setColor(.72f, .68f, 1f, .74f);
else batch.setColor(hotBiome ? 1f : .78f, hotBiome ? .64f : .82f, hotBiome ? .38f : .86f, .72f);
draw(batch, debris, x, y, 1.20f * scaleJitter);
⋮----
private void drawFoundryDressing(SpriteBatch batch, TextureRegion crack, TextureRegion scorch,
⋮----
batch.setColor(1f, .48f, .24f, .70f);
draw(batch, scorch, -12f, -7.5f, 4.8f);
draw(batch, scorch, 13f, 7f, 4.2f);
draw(batch, crack, -3f, 11f, 3.1f);
draw(batch, crack, 5f, -12f, 2.7f);
⋮----
drawPropShadow(batch, barrier, -18f, -12f, 3.2f);
drawPropShadow(batch, barrier, 19f, 12f, 3.2f);
drawPropShadow(batch, crate, -22f, 10f, 3.0f);
drawPropShadow(batch, crate, 23f, -9f, 3.0f);
drawPropShadow(batch, debrisA, -8f, 14f, 3.2f);
drawPropShadow(batch, debrisB, 9f, -14f, 3.2f);
drawArenaEdgeShadows(batch, wallB, wallA);
⋮----
batch.setColor(1f, .66f, .42f, 1f);
draw(batch, barrier, -18f, -12f, 3.2f);
draw(batch, barrier, 19f, 12f, 3.2f);
draw(batch, crate, -22f, 10f, 3.0f);
draw(batch, crate, 23f, -9f, 3.0f);
draw(batch, debrisA, -8f, 14f, 3.2f);
draw(batch, debrisB, 9f, -14f, 3.2f);
drawArenaEdge(batch, wallB, wallA, beacon);
⋮----
private void drawCryogenicDepthsDressing(SpriteBatch batch, TextureRegion crack, TextureRegion scorch,
⋮----
batch.setColor(.38f, .72f, .84f, .58f);
draw(batch, crack, -17f, 7f, 4.8f);
draw(batch, crack, 18f, -8f, 4.6f);
draw(batch, scorch, -4f, -14f, 3.6f);
draw(batch, scorch, 5f, 14f, 3.4f);
drawPropShadow(batch, barrier, -22f, -12f, 3.3f);
drawPropShadow(batch, barrier, 22f, 12f, 3.3f);
drawPropShadow(batch, crate, -12f, 13f, 3.0f);
drawPropShadow(batch, crate, 12f, -13f, 3.0f);
drawPropShadow(batch, debrisA, -26f, 3f, 3.1f);
drawPropShadow(batch, debrisB, 26f, -3f, 3.1f);
⋮----
batch.setColor(.54f, .80f, .90f, .96f);
draw(batch, barrier, -22f, -12f, 3.3f);
draw(batch, barrier, 22f, 12f, 3.3f);
draw(batch, crate, -12f, 13f, 3.0f);
draw(batch, crate, 12f, -13f, 3.0f);
draw(batch, debrisA, -26f, 3f, 3.1f);
draw(batch, debrisB, 26f, -3f, 3.1f);
⋮----
private void drawCryoVaultDressing(SpriteBatch batch, TextureRegion crack, TextureRegion scorch,
⋮----
batch.setColor(.62f, .90f, 1f, .66f);
draw(batch, crack, -13f, -8f, 4.4f);
draw(batch, crack, 14f, 9f, 3.8f);
draw(batch, scorch, -2f, 12f, 3.2f);
draw(batch, scorch, 6f, -13f, 3.5f);
⋮----
drawPropShadow(batch, barrier, -19f, 11f, 3.1f);
drawPropShadow(batch, barrier, 19f, -11f, 3.1f);
drawPropShadow(batch, crate, -15f, -14f, 2.8f);
drawPropShadow(batch, crate, 15f, 14f, 2.8f);
drawPropShadow(batch, debrisA, -24f, 5f, 3.0f);
drawPropShadow(batch, debrisB, 24f, -5f, 3.0f);
⋮----
batch.setColor(.76f, .92f, 1f, 1f);
draw(batch, barrier, -19f, 11f, 3.1f);
draw(batch, barrier, 19f, -11f, 3.1f);
draw(batch, crate, -15f, -14f, 2.8f);
draw(batch, crate, 15f, 14f, 2.8f);
draw(batch, debrisA, -24f, 5f, 3.0f);
draw(batch, debrisB, 24f, -5f, 3.0f);
⋮----
private void drawNullSectorDressing(SpriteBatch batch, TextureRegion crack, TextureRegion scorch,
⋮----
batch.setColor(.64f, .48f, 1f, .66f);
draw(batch, crack, -14f, 8f, 4.0f);
draw(batch, crack, 12f, -9f, 3.6f);
draw(batch, scorch, -4f, -12f, 3.8f);
draw(batch, scorch, 6f, 12f, 3.4f);
⋮----
drawPropShadow(batch, barrier, -20f, 11f, 3.0f);
drawPropShadow(batch, barrier, 20f, -11f, 3.0f);
drawPropShadow(batch, crate, -13f, -14f, 2.8f);
drawPropShadow(batch, crate, 14f, 14f, 2.8f);
drawPropShadow(batch, debrisA, -24f, -7f, 3.1f);
drawPropShadow(batch, debrisB, 24f, 7f, 3.1f);
⋮----
batch.setColor(.72f, .68f, 1f, 1f);
draw(batch, barrier, -20f, 11f, 3.0f);
draw(batch, barrier, 20f, -11f, 3.0f);
draw(batch, crate, -13f, -14f, 2.8f);
draw(batch, crate, 14f, 14f, 2.8f);
draw(batch, debrisA, -24f, -7f, 3.1f);
draw(batch, debrisB, 24f, 7f, 3.1f);
⋮----
private void drawArenaEdgeShadows(SpriteBatch batch, TextureRegion wallA, TextureRegion wallB) {
⋮----
drawPropShadow(batch, ((x / 10) & 1) == 0 ? wallA : wallB, x, 17.9f, 4.2f);
drawPropShadow(batch, ((x / 10) & 1) == 0 ? wallB : wallA, x, -17.9f, 4.2f);
⋮----
drawPropShadow(batch, wallA, -31.4f, y, 3.7f);
drawPropShadow(batch, wallB, 31.4f, y, 3.7f);
⋮----
private void drawArenaEdge(SpriteBatch batch, TextureRegion wallA, TextureRegion wallB, TextureRegion beacon) {
batch.setColor(cryogenicDepths() ? DEPTHS_WALL_TINT : cryoVault() ? CRYO_WALL_TINT : nullSector() ? NULL_WALL_TINT : foundry() ? FOUNDRY_WALL_TINT : Color.WHITE);
⋮----
draw(batch, ((x / 10) & 1) == 0 ? wallA : wallB, x, 17.9f, 4.2f);
draw(batch, ((x / 10) & 1) == 0 ? wallB : wallA, x, -17.9f, 4.2f);
⋮----
draw(batch, wallA, -31.4f, y, 3.7f);
draw(batch, wallB, 31.4f, y, 3.7f);
⋮----
drawBeacon(batch, beacon, -29.6f, -15.6f);
drawBeacon(batch, beacon, 29.6f, -15.6f);
drawBeacon(batch, beacon, -29.6f, 15.6f);
drawBeacon(batch, beacon, 29.6f, 15.6f);
⋮----
private void drawBeacon(SpriteBatch batch, TextureRegion beacon, float x, float y) {
⋮----
float pulse = beaconPulse(visualTime);
⋮----
batch.setColor(.08f, .52f, .70f, .14f + pulse * .12f);
draw(batch, beacon, x, y, glowSize);
batch.setColor(.48f, .88f, 1f, .88f + pulse * .10f);
⋮----
batch.setColor(.20f, .72f, 1f, .12f + pulse * .12f);
⋮----
batch.setColor(.70f, .96f, 1f, .90f + pulse * .10f);
⋮----
batch.setColor(.36f, .16f, 1f, .11f + pulse * .12f);
⋮----
batch.setColor(.42f, .86f, 1f, .88f + pulse * .12f);
⋮----
batch.setColor(1f, .18f, .03f, .10f + pulse * .12f);
⋮----
batch.setColor(1f, .58f, .12f, .88f + pulse * .12f);
⋮----
batch.setColor(.18f, .88f, 1f, .10f + pulse * .10f);
⋮----
batch.setColor(.62f, .96f, 1f, .88f + pulse * .12f);
⋮----
draw(batch, beacon, x, y, 2.0f);
⋮----
private void drawPropShadow(SpriteBatch batch, TextureRegion region, float x, float y, float width) {
⋮----
batch.setColor(.01f, .015f, .02f, .36f);
draw(batch, region, x + SHADOW_OFFSET_X, y + SHADOW_OFFSET_Y, width * 1.03f);
⋮----
private boolean hasAnyEnvironmentArt() { return hasFloorArt() || hasSetDressingArt(); }
⋮----
private boolean hasFloorArt() {
return region("environment/floor/concrete_a") != null
|| region("environment/floor/concrete_b") != null
|| region("environment/floor/concrete_c") != null
|| region("environment/floor/hazard_a") != null;
⋮----
private boolean hasSetDressingArt() {
return region("environment/decal/crack_a") != null
|| region("environment/decal/blood_a") != null
|| region("environment/decal/scorch_a") != null
|| region("environment/prop/barrier_a") != null
|| region("environment/prop/debris_a") != null
|| region("environment/prop/debris_b") != null
|| region("environment/prop/wall_a") != null
|| region("environment/prop/wall_b") != null
|| region("environment/prop/crate_a") != null
|| region("environment/prop/beacon_a") != null;
⋮----
private TextureRegion region(String key) {
EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(RunStageContext.stage());
String productionKey = EnvironmentArtCatalog.productionKey(biome, key);
TextureRegion production = art.regionOrNull(productionKey);
⋮----
TextureRegion finalOrLegacy = art.regionOrNull(key);
⋮----
return bootstrap == null ? null : bootstrap.region(key);
⋮----
private void draw(SpriteBatch batch, TextureRegion region, float x, float y, float width) {
⋮----
float aspect = region.getRegionHeight() <= 0 ? 1f : region.getRegionWidth() / (float)region.getRegionHeight();
float h = width / Math.max(.2f, aspect);
batch.draw(region, x - width * .5f, y - h * .5f, width, h);
⋮----
@Override public void dispose() {
if (bootstrap != null) bootstrap.dispose();
```

## File: src/main/java/com/deadlinezero/game/visual/FinalArtContract.java
```java
/**
 * Release-quality animation requirements. Bootstrap/generated art is intentionally excluded:
 * these values describe the minimum frame counts expected from the final production atlas.
 */
public final class FinalArtContract {
⋮----
public static int minimumFrames(GameArt.Motion motion, boolean boss) {
⋮----
public static int preferredFastRunFrames() { return 10; }
public static int directions() { return Direction8.values().length; }
⋮----
public static int minimumDirectionalActorFrames(boolean boss) {
⋮----
for (GameArt.Motion motion : GameArt.Motion.values()) {
perDirection += minimumFrames(motion, boss);
⋮----
return directions() * perDirection;
```

## File: src/main/java/com/deadlinezero/game/visual/FoundryHazardPresentation.java
```java
/** Presentation contract for Cinder Foundry hazards. Keeps color, animation and audio routing deterministic. */
public final class FoundryHazardPresentation {
public static final class Profile {
⋮----
private static final Profile LAVA = new Profile(
⋮----
private static final Profile STEAM = new Profile(
⋮----
private static final Profile HEAT = new Profile(
⋮----
public static boolean isFoundry(ArenaHazardRuntime.Type type) {
⋮----
public static Profile forType(ArenaHazardRuntime.Type type) {
⋮----
default -> throw new IllegalArgumentException("Not a Foundry hazard: " + type);
```

## File: src/main/java/com/deadlinezero/game/visual/GameArt.java
```java
/**
 * Authored-art gateway. Final atlas art takes priority, then generated directional/VFX bootstrap
 * layers, followed by compact legacy bootstrap art and finally the procedural emergency fallback.
 */
public final class GameArt implements Disposable {
⋮----
loadAtlas();
loadDirectionalBootstrap();
loadBiomeDirectionalBootstrap();
loadNullVfxBootstrap();
loadVfxBootstrap();
loadBootstrap();
⋮----
createFallback();
⋮----
public boolean authoredAvailable() { return authoredAvailable; }
⋮----
public TextureRegion survivor(SurvivorCatalog.Survivor survivor, Motion motion, float stateTime) {
String prefix = "survivor/" + survivor.name().toLowerCase() + "/" + motion.name().toLowerCase();
float frameDuration = AnimationProfileCatalog.survivor(survivor).duration(motion);
return animated(prefix, frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
⋮----
public TextureRegion survivor(SurvivorCatalog.Survivor survivor, Motion motion, Direction8 direction, float stateTime) {
String root = "survivor/" + survivor.name().toLowerCase();
⋮----
TextureRegion directional = animatedOrNull(
directionalPrefix(root, direction, motion), frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
return directional == null ? survivor(survivor, motion, stateTime) : directional;
⋮----
public TextureRegion enemy(Enemy.Type type, Motion motion, float stateTime) {
String prefix = "enemy/" + type.name().toLowerCase() + "/" + motion.name().toLowerCase();
float frameDuration = AnimationProfileCatalog.enemy(type).duration(motion);
TextureRegion region = animatedOrNull(prefix, frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
⋮----
Enemy.Type readableFallback = readableFallback(type);
⋮----
String fallbackPrefix = "enemy/" + readableFallback.name().toLowerCase() + "/" + motion.name().toLowerCase();
float fallbackDuration = AnimationProfileCatalog.enemy(readableFallback).duration(motion);
region = animatedOrNull(fallbackPrefix, fallbackDuration, stateTime, AnimationProfileCatalog.loops(motion));
⋮----
public TextureRegion enemy(Enemy.Type type, Motion motion, Direction8 direction, float stateTime) {
String root = "enemy/" + type.name().toLowerCase();
⋮----
TextureRegion region = animatedOrNull(
⋮----
String fallbackRoot = "enemy/" + readableFallback.name().toLowerCase();
⋮----
region = animatedOrNull(
directionalPrefix(fallbackRoot, direction, motion), fallbackDuration, stateTime, AnimationProfileCatalog.loops(motion));
⋮----
return enemy(type, motion, stateTime);
⋮----
public TextureRegion biomeEnemy(BiomeEnemyRoster.Identity identity, Enemy.Type fallbackType,
⋮----
String root = BiomeDirectionalBootstrapArt.root(identity);
⋮----
float frameDuration = AnimationProfileCatalog.enemy(fallbackType).duration(motion);
⋮----
return enemy(fallbackType, motion, direction, stateTime);
⋮----
public TextureRegion boss(BossIdentity identity, Motion motion, float stateTime) {
String prefix = bossRoot(identity) + "/" + motion.name().toLowerCase();
float frameDuration = AnimationProfileCatalog.enemy(Enemy.Type.BOSS).duration(motion);
⋮----
return region == null ? enemy(Enemy.Type.BOSS, motion, stateTime) : region;
⋮----
public TextureRegion boss(BossIdentity identity, Motion motion, Direction8 direction, float stateTime) {
String root = bossRoot(identity);
⋮----
return region == null ? boss(identity, motion, stateTime) : region;
⋮----
public TextureRegion boss(boolean revenant, Motion motion, float stateTime) {
return boss(revenant ? BossIdentity.REVENANT : BossIdentity.ALPHA, motion, stateTime);
⋮----
public TextureRegion boss(boolean revenant, Motion motion, Direction8 direction, float stateTime) {
return boss(revenant ? BossIdentity.REVENANT : BossIdentity.ALPHA, motion, direction, stateTime);
⋮----
static String bossRoot(BossIdentity identity) {
⋮----
return "boss/" + safeIdentity.name().toLowerCase();
⋮----
public TextureRegion effect(String name, float stateTime, float frameDuration) {
TextureRegion region = effectOrNull(name, stateTime, frameDuration);
⋮----
public TextureRegion effectOrNull(String name, float stateTime, float frameDuration) {
return animatedOrNull("fx/" + name, frameDuration, stateTime, false);
⋮----
public TextureRegion loopingEffectOrNull(String name, float stateTime, float frameDuration) {
return animatedOrNull("fx/" + name, frameDuration, stateTime, true);
⋮----
public TextureRegion region(String name) {
TextureRegion region = regionOrNull(name);
⋮----
public TextureRegion regionOrNull(String name) {
⋮----
TextureRegion region = atlas.findRegion(name);
⋮----
TextureRegion region = biomeDirectionalBootstrap.region(name, 0f, 1f, false);
⋮----
TextureRegion region = nullVfxBootstrap.region(name, 0f, 1f, false);
⋮----
TextureRegion region = vfxBootstrap.region(name, 0f, 1f, false);
⋮----
return bootstrapRegion(name);
⋮----
public boolean hasRegion(String name) { return regionOrNull(name) != null; }
⋮----
public boolean hasAnimation(String prefix) {
⋮----
Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(prefix);
if ((frames != null && frames.size > 0) || atlas.findRegion(prefix) != null) return true;
⋮----
if (directionalBootstrap != null && directionalBootstrap.supports(prefix)) return true;
if (biomeDirectionalBootstrap != null && biomeDirectionalBootstrap.supports(prefix)) return true;
if (nullVfxBootstrap != null && nullVfxBootstrap.supports(prefix)) return true;
if (vfxBootstrap != null && vfxBootstrap.supports(prefix)) return true;
return bootstrapRegion(prefix) != null;
⋮----
private TextureRegion animated(String prefix, float frameDuration, float stateTime, boolean loop) {
TextureRegion region = animatedOrNull(prefix, frameDuration, stateTime, loop);
⋮----
private TextureRegion animatedOrNull(String prefix, float frameDuration, float stateTime, boolean loop) {
⋮----
int rawFrame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
int frame = loop ? rawFrame % frames.size : Math.min(frames.size - 1, rawFrame);
return frames.get(frame);
⋮----
TextureRegion single = atlas.findRegion(prefix);
⋮----
TextureRegion region = biomeDirectionalBootstrap.region(prefix, stateTime, frameDuration, loop);
⋮----
TextureRegion region = directionalBootstrap.region(prefix, stateTime, frameDuration, loop);
⋮----
TextureRegion region = nullVfxBootstrap.region(prefix, stateTime, frameDuration, loop);
⋮----
TextureRegion region = vfxBootstrap.region(prefix, stateTime, frameDuration, loop);
⋮----
return bootstrapRegion(prefix);
⋮----
private TextureRegion bootstrapRegion(String key) {
⋮----
int tile = BootstrapArtCatalog.tileIndex(key);
⋮----
static String directionalPrefix(String root, Direction8 direction, Motion motion) {
⋮----
return root + "/" + safeDirection.atlasToken() + "/" + motion.name().toLowerCase();
⋮----
private static Enemy.Type readableFallback(Enemy.Type type) {
⋮----
private void loadAtlas() {
if (!Gdx.files.internal(ATLAS_PATH).exists()) return;
⋮----
atlas = new TextureAtlas(Gdx.files.internal(ATLAS_PATH));
⋮----
Gdx.app.error("GameArt", "Unable to load production atlas; bootstrap art will be used.", exception);
⋮----
private void loadDirectionalBootstrap() {
⋮----
directionalBootstrap = DirectionalBootstrapArt.create();
⋮----
Gdx.app.error("GameArt", "Unable to create directional bootstrap art; legacy bootstrap remains active.", exception);
if (directionalBootstrap != null) directionalBootstrap.dispose();
⋮----
private void loadBiomeDirectionalBootstrap() {
⋮----
biomeDirectionalBootstrap = BiomeDirectionalBootstrapArt.create();
⋮----
Gdx.app.error("GameArt", "Unable to create biome directional art; base enemy art remains active.", exception);
if (biomeDirectionalBootstrap != null) biomeDirectionalBootstrap.dispose();
⋮----
private void loadNullVfxBootstrap() {
⋮----
nullVfxBootstrap = NullBootstrapVfxArt.create();
⋮----
Gdx.app.error("GameArt", "Unable to create Null boss VFX; generic boss VFX remain active.", exception);
if (nullVfxBootstrap != null) nullVfxBootstrap.dispose();
⋮----
private void loadVfxBootstrap() {
⋮----
vfxBootstrap = BootstrapVfxArt.create();
⋮----
Gdx.app.error("GameArt", "Unable to create bootstrap VFX; procedural VFX remain active.", exception);
if (vfxBootstrap != null) vfxBootstrap.dispose();
⋮----
private void loadBootstrap() {
if (!Gdx.files.internal(BOOTSTRAP_PATH).exists()) return;
⋮----
byte[] png = decodeBase64(Gdx.files.internal(BOOTSTRAP_PATH).readString("UTF-8"));
⋮----
pixmap = new Pixmap(png, 0, png.length);
bootstrapTexture = new Texture(pixmap);
bootstrapTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
⋮----
if (x + BootstrapArtCatalog.TILE <= bootstrapTexture.getWidth()
&& y + BootstrapArtCatalog.TILE <= bootstrapTexture.getHeight()) {
bootstrapRegions[tile] = new TextureRegion(
⋮----
Gdx.app.error("GameArt", "Unable to load bootstrap art source; procedural fallback remains active.", exception);
if (bootstrapTexture != null) bootstrapTexture.dispose();
⋮----
if (pixmap != null) pixmap.dispose();
⋮----
static byte[] decodeBase64(String input) {
if (input == null || input.isBlank()) return new byte[0];
ByteArrayOutputStream out = new ByteArrayOutputStream(input.length() * 3 / 4);
⋮----
for (int i = 0; i < input.length(); i++) {
char c = input.charAt(i);
if (Character.isWhitespace(c)) continue;
⋮----
int digit = base64Digit(c);
if (digit < 0) throw new IllegalArgumentException("Invalid Base64 character at index " + i);
⋮----
out.write((value >> bits) & 0xff);
⋮----
return out.toByteArray();
⋮----
private static int base64Digit(char c) {
⋮----
private void createFallback() {
Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
pixmap.setColor(new Color(.08f, .80f, 1f, 1f));
pixmap.fillCircle(8, 8, 7);
pixmap.setColor(new Color(.92f, .98f, 1f, 1f));
pixmap.fillCircle(6, 6, 2);
fallbackTexture = new Texture(pixmap);
fallbackTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
fallbackRegion = new TextureRegion(fallbackTexture);
pixmap.dispose();
⋮----
@Override public void dispose() {
if (atlas != null) atlas.dispose();
⋮----
if (fallbackTexture != null) fallbackTexture.dispose();
```

## File: src/main/java/com/deadlinezero/game/visual/GraphicsQuality.java
```java
/** Conservative mobile quality tiers. AUTO avoids expensive effects on weaker devices. */
⋮----
public static GraphicsQuality autoDetect() {
int width = Math.max(1, Gdx.graphics.getBackBufferWidth());
int height = Math.max(1, Gdx.graphics.getBackBufferHeight());
⋮----
int fps = Gdx.graphics.getFramesPerSecond();
⋮----
// Resolution is a useful zero-cost proxy before a real benchmark/device database is added.
```

## File: src/main/java/com/deadlinezero/game/visual/HighResBossDirectionalArt.java
```java
/**
 * Dedicated 64px eight-way boss art. Kept on its own texture so core actors and bosses
 * can scale independently while remaining below conservative GLES texture dimensions.
 */
public final class HighResBossDirectionalArt implements Disposable {
⋮----
regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
⋮----
public static HighResBossDirectionalArt create() {
Pixmap pixmap = new Pixmap(width(), height(), Pixmap.Format.RGBA8888);
pixmap.setBlending(Pixmap.Blending.SourceOver);
⋮----
for (int actor = 0; actor < ACTOR_COUNT; actor++) drawActorSet(pixmap, actor, actor * ACTOR_BLOCK);
Texture texture = new Texture(pixmap);
texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
return new HighResBossDirectionalArt(texture);
⋮----
pixmap.dispose();
⋮----
static int rows() { return (TOTAL_TILES + COLUMNS - 1) / COLUMNS; }
static int width() { return COLUMNS * TILE; }
static int height() { return rows() * TILE; }
⋮----
public boolean supports(String key) { return firstTile(key) >= 0; }
⋮----
public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
int first = firstTile(key);
⋮----
int count = frameCount(key);
int raw = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
int frame = count <= 1 ? 0 : (loop ? raw % count : Math.min(count - 1, raw));
⋮----
static int firstTile(String key) {
if (key == null || key.isBlank()) return -1;
int actor = actorIndex(key);
⋮----
String rest = key.substring(ROOTS[actor].length());
int slash = rest.indexOf('/');
if (slash <= 0 || slash >= rest.length() - 1) return -1;
int direction = directionIndex(rest.substring(0, slash));
int motion = motionOffset(rest.substring(slash + 1));
⋮----
static int frameCount(String key) {
if (firstTile(key) < 0) return 0;
String motion = key.substring(key.lastIndexOf('/') + 1);
⋮----
private static int actorIndex(String key) {
for (int i = 0; i < ROOTS.length; i++) if (key.startsWith(ROOTS[i])) return i;
⋮----
private static int directionIndex(String token) {
⋮----
private static int motionOffset(String motion) {
⋮----
private static void drawActorSet(Pixmap p, int actor, int base) {
⋮----
drawFrame(p, b, actor, dx, dy, 0, 0);
drawFrame(p, b + 1, actor, dx, dy, 0, 1);
drawFrame(p, b + 2, actor, dx, dy, 1, 0);
drawFrame(p, b + 3, actor, dx, dy, 1, 1);
drawFrame(p, b + 4, actor, dx, dy, 1, 2);
drawFrame(p, b + 5, actor, dx, dy, 2, 0);
drawFrame(p, b + 6, actor, dx, dy, 2, 1);
drawFrame(p, b + 7, actor, dx, dy, 3, 0);
drawFrame(p, b + 8, actor, dx, dy, 3, 1);
drawFrame(p, b + 9, actor, dx, dy, 4, 0);
drawFrame(p, b + 10, actor, dx, dy, 4, 1);
drawFrame(p, b + 11, actor, dx, dy, 4, 2);
⋮----
/** motion: 0 idle, 1 run, 2 attack, 3 hit, 4 death. */
private static void drawFrame(Pixmap p, int tile, int actor, int dx, int dy, int motion, int frame) {
⋮----
set(p, 0f, 0f, 0f, .34f);
p.fillCircle(ox + 32, oy + 53, 19);
⋮----
setSecondary(p, actor);
p.fillCircle(ox + 32 + shift, oy + 38 + frame * 4, Math.max(10, 18 - frame * 2));
setPrimary(p, actor);
p.fillCircle(ox + 27 + shift, oy + 25 + frame * 6, Math.max(7, 13 - frame * 2));
setAccent(p, actor);
p.drawLine(ox + 16 + shift, oy + 31 + frame * 3, ox + 50 + shift, oy + 42 + frame * 3);
⋮----
p.fillCircle(cx, cy + 2, 18);
⋮----
p.fillCircle(cx + sx * 3, cy - 16 + sy * 3, 12);
⋮----
p.fillCircle(shoulderX, shoulderY, 7);
⋮----
drawIdentity(p, actor, cx, cy, sx, sy, px, py, motion, frame);
⋮----
p.fillCircle(faceX - px * 4, faceY - py * 4, 2);
p.fillCircle(faceX + px * 4, faceY + py * 4, 2);
⋮----
p.drawLine(cx, cy, handX, handY);
⋮----
p.drawLine(handX, handY, tipX, tipY);
⋮----
p.fillCircle(tipX, tipY, 6);
p.drawLine(tipX, tipY, tipX + sx * 8 + px * 6, tipY + sy * 8 + py * 6);
p.drawLine(tipX, tipY, tipX + sx * 8 - px * 6, tipY + sy * 8 - py * 6);
⋮----
set(p, 1f, .96f, .86f, 1f);
p.drawLine(ox + 10, oy + 11, ox + 23, oy + 24);
p.drawLine(ox + 10, oy + 24, ox + 23, oy + 11);
⋮----
private static void drawIdentity(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py,
⋮----
case 0 -> { // Alpha: crown/horns and rage arc
p.drawLine(cx - px * 9, cy - 19 - py * 9, cx - px * 16 - sx * 7, cy - 29 - py * 16 - sy * 7);
p.drawLine(cx + px * 9, cy - 19 + py * 9, cx + px * 16 - sx * 7, cy - 29 + py * 16 - sy * 7);
p.drawCircle(cx, cy, 22);
⋮----
case 1 -> { // Revenant: orbital cage and spectral tail
p.drawCircle(cx, cy, 23);
p.drawCircle(cx, cy, 15);
p.drawLine(cx - sx * 11, cy - sy * 11, cx - sx * 26 + px * 7, cy - sy * 26 + py * 7);
⋮----
case 2 -> { // Warden: slab shield and reinforced crossbar
p.drawRectangle(cx - 17 + sx * 5, cy - 13 + sy * 5, 34, 27);
p.drawLine(cx - px * 15, cy - py * 15, cx + px * 15, cy + py * 15);
⋮----
case 3 -> { // Harvester: twin scythe wings
p.drawLine(cx - px * 15, cy - py * 15, cx - px * 27 - sx * 9, cy - py * 27 - sy * 9);
p.drawLine(cx + px * 15, cy + py * 15, cx + px * 27 - sx * 9, cy + py * 27 - sy * 9);
p.drawCircle(cx - px * 27 - sx * 9, cy - py * 27 - sy * 9, 6);
p.drawCircle(cx + px * 27 - sx * 9, cy + py * 27 - sy * 9, 6);
⋮----
if (motion == 0 && frame == 1) p.drawCircle(cx + sx * 2, cy + sy * 2, 25);
⋮----
private static void setPrimary(Pixmap p, int actor) { setPalette(p, PRIMARY[actor]); }
private static void setSecondary(Pixmap p, int actor) { setPalette(p, SECONDARY[actor]); }
private static void setAccent(Pixmap p, int actor) { setPalette(p, ACCENT[actor]); }
private static void setPalette(Pixmap p, float[] c) { p.setColor(c[0], c[1], c[2], 1f); }
private static void set(Pixmap p, float r, float g, float b, float a) { p.setColor(r, g, b, a); }
⋮----
@Override public void dispose() { texture.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/visual/HighResDirectionalBootstrapArt.java
```java
/**
 * Higher-resolution directional bootstrap for the most frequently visible combat actors.
 * It intentionally covers only all five survivors plus Shambler and Runner so the Android
 * memory cost stays bounded. Final atlas art still overrides this layer.
 */
public final class HighResDirectionalBootstrapArt implements Disposable {
⋮----
regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
⋮----
public static HighResDirectionalBootstrapArt create() {
int rows = rows();
Pixmap pixmap = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
pixmap.setBlending(Pixmap.Blending.SourceOver);
⋮----
for (int actor = 0; actor < ACTOR_COUNT; actor++) drawActorSet(pixmap, actor, actor * ACTOR_BLOCK);
Texture texture = new Texture(pixmap);
texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
return new HighResDirectionalBootstrapArt(texture);
⋮----
pixmap.dispose();
⋮----
static int rows() { return (TOTAL_TILES + COLUMNS - 1) / COLUMNS; }
static int width() { return COLUMNS * TILE; }
static int height() { return rows() * TILE; }
⋮----
public boolean supports(String key) { return firstTile(key) >= 0; }
⋮----
public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
int first = firstTile(key);
⋮----
int count = frameCount(key);
⋮----
int rawFrame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
int frame = loop ? rawFrame % count : Math.min(count - 1, rawFrame);
⋮----
static int firstTile(String key) {
if (key == null || key.isBlank()) return -1;
int actor = actorIndex(key);
⋮----
String rest = key.substring(ROOTS[actor].length());
int slash = rest.indexOf('/');
if (slash <= 0 || slash >= rest.length() - 1) return -1;
int direction = directionIndex(rest.substring(0, slash));
int motion = motionOffset(rest.substring(slash + 1));
⋮----
static int frameCount(String key) {
if (firstTile(key) < 0) return 0;
String motion = key.substring(key.lastIndexOf('/') + 1);
⋮----
static int actorIndex(String key) {
⋮----
for (int i = 0; i < ROOTS.length; i++) if (key.startsWith(ROOTS[i])) return i;
⋮----
private static int directionIndex(String token) {
⋮----
private static int motionOffset(String motion) {
⋮----
private static void drawActorSet(Pixmap p, int actor, int actorBase) {
⋮----
drawFrame(p, base, actor, dx, dy, 0, 0);
drawFrame(p, base + 1, actor, dx, dy, 0, 1);
drawFrame(p, base + 2, actor, dx, dy, 1, 0);
drawFrame(p, base + 3, actor, dx, dy, 1, 1);
drawFrame(p, base + 4, actor, dx, dy, 1, 2);
drawFrame(p, base + 5, actor, dx, dy, 2, 0);
drawFrame(p, base + 6, actor, dx, dy, 2, 1);
drawFrame(p, base + 7, actor, dx, dy, 3, 0);
drawFrame(p, base + 8, actor, dx, dy, 3, 1);
drawFrame(p, base + 9, actor, dx, dy, 4, 0);
drawFrame(p, base + 10, actor, dx, dy, 4, 1);
drawFrame(p, base + 11, actor, dx, dy, 4, 2);
⋮----
/** motion: 0 idle, 1 run, 2 attack, 3 hit, 4 death. */
private static void drawFrame(Pixmap p, int tile, int actor, int dx, int dy, int motion, int frame) {
⋮----
drawShadow(p, ox, oy, actor, motion, frame);
⋮----
drawDeath(p, ox, oy, actor, dx, frame);
⋮----
drawLegs(p, actor, cx, cy + bob, sx, sy, perpX, perpY, stride);
drawBody(p, actor, cx, cy + bob, sx, sy, perpX, perpY);
drawIdentity(p, actor, cx, cy + bob, sx, sy, perpX, perpY);
drawFace(p, actor, cx, cy + bob, sx, sy, perpX, perpY);
drawArmsAndWeapon(p, actor, cx, cy + bob, sx, sy, perpX, perpY, motion, frame, reach);
if (motion == 3) drawHit(p, ox, oy, sx, sy, frame);
⋮----
private static void drawShadow(Pixmap p, int ox, int oy, int actor, int motion, int frame) {
set(p, 0f, 0f, 0f, actor < 5 ? .28f : .34f);
⋮----
p.fillCircle(ox + 24 + (motion == 4 ? frame * 2 : 0), oy + 39, width);
set(p, .11f, .14f, .17f, .30f);
p.drawLine(ox + 12, oy + 40, ox + 36, oy + 40);
⋮----
private static void drawLegs(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py, int stride) {
⋮----
setSecondary(p, actor);
⋮----
p.drawLine(lx, ly, lx + sx * stride - px * 2, cy + 18 - py * 3);
p.drawLine(rx, ry, rx - sx * stride + px * 2, cy + 18 + py * 3);
setPrimary(p, actor);
p.fillCircle(lx + sx * stride - px * 2, cy + 18 - py * 3, actor == 2 ? 3 : 2);
p.fillCircle(rx - sx * stride + px * 2, cy + 18 + py * 3, actor == 2 ? 3 : 2);
⋮----
private static void drawBody(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py) {
⋮----
p.fillCircle(cx, cy + 3, radius);
⋮----
p.fillCircle(cx + sx * 3, cy - 10 + sy * 3, Math.max(7, radius - 3));
⋮----
setAccent(p, actor);
p.drawLine(cx - px * 6, cy - py * 6, cx + px * 6, cy + py * 6);
p.drawLine(cx - px * 5 + sx * 2, cy + 6 - py * 5 + sy * 2,
⋮----
set(p, 1f, 1f, 1f, .35f);
p.drawLine(cx - 5, cy - 5, cx + 1, cy - 8);
⋮----
private static void drawFace(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py) {
⋮----
p.drawLine(fx - px * 5, fy - py * 5, fx + px * 5, fy + py * 5);
p.drawLine(fx - px * 4 + sx, fy - py * 4 + sy, fx + px * 4 + sx, fy + py * 4 + sy);
⋮----
p.fillCircle(fx - px * 3, fy - py * 3, 2);
p.fillCircle(fx + px * 3, fy + py * 3, 2);
⋮----
private static void drawArmsAndWeapon(Pixmap p, int actor, int cx, int cy, int sx, int sy,
⋮----
p.drawLine(cx, cy + 2, handX, handY);
⋮----
p.drawLine(handX - px * 2, handY - py * 2, muzzleX - px * 2, muzzleY - py * 2);
p.drawLine(handX + px * 2, handY + py * 2, muzzleX + px, muzzleY + py);
⋮----
p.drawLine(handX, handY, muzzleX, muzzleY);
drawWeaponIdentity(p, actor, handX, handY, muzzleX, muzzleY, px, py);
⋮----
set(p, 1f, .84f, .34f, 1f);
p.fillCircle(muzzleX + sx * 3, muzzleY + sy * 3, 4);
set(p, 1f, .97f, .72f, .9f);
p.drawLine(muzzleX + sx * 2, muzzleY + sy * 2, muzzleX + sx * 8, muzzleY + sy * 8);
⋮----
p.drawLine(cx - px * 3, cy + 3 - py * 3, clawX, clawY);
⋮----
p.drawLine(clawX, clawY, clawX + sx * 4 - px * 2, clawY + sy * 4 - py * 2);
p.drawLine(clawX, clawY, clawX + sx * 3 + px * 2, clawY + sy * 3 + py * 2);
⋮----
private static void drawWeaponIdentity(Pixmap p, int actor, int hx, int hy, int mx, int my, int px, int py) {
⋮----
case 0 -> p.drawLine(hx - px * 4, hy - py * 4, hx + px * 3, hy + py * 3);
⋮----
p.drawLine(hx + px * 3, hy + py * 3, mx + px * 5, my + py * 5);
p.drawLine(hx - px * 3, hy - py * 3, mx - px * 5, my - py * 5);
⋮----
setSecondary(p, actor); p.fillCircle(hx, hy, 4); setAccent(p, actor);
p.drawLine(hx + px * 3, hy + py * 3, mx + px * 3, my + py * 3);
⋮----
p.drawLine(mx - px * 4, my - py * 4, mx + px * 4, my + py * 4);
p.fillCircle(mx, my, 2);
⋮----
p.drawLine(mx, my, mx + px * 5, my + py * 5);
p.drawLine(mx, my, mx - px * 5, my - py * 5);
⋮----
private static void drawIdentity(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py) {
⋮----
case 0 -> { // Rex shoulder pads
p.fillCircle(cx - px * 8, cy - py * 8, 3);
p.fillCircle(cx + px * 8, cy + py * 8, 3);
⋮----
case 1 -> { // Nyx fins
p.drawLine(cx - px * 7, cy - 8 - py * 7, cx - px * 10 - sx * 3, cy - 17 - py * 10 - sy * 3);
p.drawLine(cx + px * 7, cy - 8 + py * 7, cx + px * 10 - sx * 3, cy - 17 + py * 10 - sy * 3);
⋮----
case 2 -> { // Bastion armor slab
p.drawRectangle(cx - 11, cy - 4, 22, 10);
p.drawRectangle(cx - 8, cy - 8, 16, 4);
⋮----
case 3 -> { // Volt capacitor halo
p.drawCircle(cx, cy + 2, 14);
p.drawLine(cx - 9, cy - 11, cx - 4, cy - 17);
p.drawLine(cx + 4, cy - 17, cx + 9, cy - 11);
⋮----
case 4 -> { // Wraith cloak
p.drawLine(cx - px * 9, cy + 8 - py * 9, cx - px * 6 - sx * 5, cy + 17 - py * 6 - sy * 5);
p.drawLine(cx + px * 9, cy + 8 + py * 9, cx + px * 6 - sx * 5, cy + 17 + py * 6 - sy * 5);
⋮----
case 5 -> { // Shambler asymmetry
p.drawLine(cx - px * 7, cy + 1 - py * 7, cx - px * 12 - sx * 2, cy + 7 - py * 12 - sy * 2);
p.drawLine(cx + px * 4, cy - 5 + py * 4, cx + px * 8, cy - 10 + py * 8);
⋮----
case 6 -> { // Runner swept spikes
p.drawLine(cx - sx * 4, cy - 8 - sy * 4, cx - sx * 10, cy - 17 - sy * 10);
p.drawLine(cx + px * 5, cy - 7 + py * 5, cx - sx * 7 + px * 7, cy - 14 - sy * 7 + py * 7);
⋮----
private static void drawHit(Pixmap p, int ox, int oy, int sx, int sy, int frame) {
⋮----
set(p, 1f, .92f, .82f, 1f);
p.drawLine(hx - 6, hy - 6, hx + 6, hy + 6);
p.drawLine(hx - 6, hy + 6, hx + 6, hy - 6);
set(p, 1f, .30f, .20f, .9f);
p.fillCircle(hx, hy, frame == 0 ? 4 : 2);
⋮----
private static void drawDeath(Pixmap p, int ox, int oy, int actor, int dx, int frame) {
⋮----
p.fillCircle(ox + 24 + shift, oy + 27 + frame * 4, Math.max(6, 11 - frame));
⋮----
p.fillCircle(ox + 19 + shift, oy + 18 + frame * 6, Math.max(5, 8 - frame));
⋮----
p.drawLine(ox + 11 + shift, oy + 22 + frame * 4, ox + 34 + shift, oy + 29 + frame * 4);
⋮----
set(p, .95f, .16f, .16f, .68f);
p.drawLine(ox + 13 + shift, oy + 38, ox + 34 + shift, oy + 39);
⋮----
private static void setPrimary(Pixmap p, int actor) { setPalette(p, PRIMARY[actor]); }
private static void setSecondary(Pixmap p, int actor) { setPalette(p, SECONDARY[actor]); }
private static void setAccent(Pixmap p, int actor) { setPalette(p, ACCENT[actor]); }
private static void setPalette(Pixmap p, float[] c) { p.setColor(c[0], c[1], c[2], 1f); }
private static void set(Pixmap p, float r, float g, float b, float a) { p.setColor(r, g, b, a); }
⋮----
@Override public void dispose() { texture.dispose(); }
```

## File: src/main/java/com/deadlinezero/game/visual/HostileProjectilePresentation.java
```java
/** Pure mapping from combat source identity to hostile projectile presentation. */
public final class HostileProjectilePresentation {
⋮----
public static EnemyProjectile.Style styleFor(Enemy source) {
⋮----
BossIdentity identity = source.bossCombat == null ? BossIdentity.ALPHA : source.bossCombat.identity();
⋮----
return switch (source.biomeIdentity()) {
⋮----
public static float coreRadiusMultiplier(EnemyProjectile.Style style) {
```

## File: src/main/java/com/deadlinezero/game/visual/LeaperPresentationProfile.java
```java
/** Presentation-only contract for the upcoming LEAPER enemy. */
public final class LeaperPresentationProfile {
```

## File: src/main/java/com/deadlinezero/game/visual/LegendaryFxRenderer.java
```java
/** Lightweight procedural signatures for active legendary transformations. */
public final class LegendaryFxRenderer {
public void render(ShapeRenderer shapes, Player player, float time, float quality) {
if (player == null || !player.legendary.hasAny()) return;
float q = MathUtils.clamp(quality, .35f, 1f);
⋮----
if (player.legendary.hasOverdrive()) {
float pulse = 1f + MathUtils.sin(time * 9f) * .08f;
shapes.setColor(1f, .32f, .08f, .13f * q);
shapes.circle(player.position.x, player.position.y, 1.28f * pulse, 24);
shapes.setColor(1f, .78f, .18f, .26f * q);
shapes.circle(player.position.x, player.position.y, .78f * pulse, 20);
⋮----
if (player.legendary.hasSingularity()) {
float pulse = 1f + MathUtils.sin(time * 6.5f + 1.7f) * .10f;
shapes.setColor(.55f, .16f, 1f, .14f * q);
shapes.circle(player.position.x, player.position.y, 1.55f * pulse, 28);
shapes.setColor(.18f, .72f, 1f, .22f * q);
shapes.circle(player.position.x, player.position.y, 1.02f / pulse, 24);
⋮----
if (player.legendary.hasApex()) {
float pulse = 1f + MathUtils.sin(time * 4.2f + .8f) * .06f;
shapes.setColor(1f, .86f, .28f, .13f * q);
shapes.circle(player.position.x, player.position.y, 1.88f * pulse, 30);
⋮----
float x = player.position.x + MathUtils.cosDeg(angle) * 1.48f;
float y = player.position.y + MathUtils.sinDeg(angle) * 1.48f;
shapes.setColor(.90f, .96f, 1f, .42f * q);
shapes.circle(x, y, .10f, 10);
⋮----
drawWeaponFamily(shapes, player, time, q);
⋮----
private void drawWeaponFamily(ShapeRenderer shapes, Player player, float time, float q) {
WeaponLegendaryPresentation.Style style = WeaponLegendaryPresentation.style(player);
⋮----
float pulse = 1f + MathUtils.sin(time * 7.2f) * .075f;
⋮----
shapes.setColor(style.r, style.g, style.b, .16f * q);
shapes.circle(x, y, 1.46f * pulse, segments);
shapes.setColor(style.r, style.g, style.b, .34f * q);
shapes.circle(x, y, .92f / pulse, segments);
⋮----
shapes.setColor(style.r, style.g, style.b, .30f * q);
shapes.rectLine(x + MathUtils.cosDeg(a) * .95f, y + MathUtils.sinDeg(a) * .95f,
x + MathUtils.cosDeg(a) * 1.55f, y + MathUtils.sinDeg(a) * 1.55f, .035f);
⋮----
shapes.setColor(1f, .82f, .30f, .34f * q);
shapes.circle(x + MathUtils.cosDeg(a) * 1.22f, y + MathUtils.sinDeg(a) * 1.22f, .07f, 8);
⋮----
shapes.setColor(.96f, .98f, 1f, .42f * q);
shapes.rectLine(x - 1.55f, y, x + 1.55f, y, .026f);
shapes.rectLine(x, y - 1.55f, x, y + 1.55f, .026f);
⋮----
shapes.setColor(1f, .18f + i * .12f, .03f, .30f * q);
shapes.circle(x + MathUtils.cosDeg(a) * r, y + MathUtils.sinDeg(a) * r, .10f, 10);
⋮----
float a = 90f + i * 120f + MathUtils.sin(time * 2.5f) * 8f;
shapes.setColor(.74f, .96f, 1f, .38f * q);
shapes.triangle(x + MathUtils.cosDeg(a) * .85f, y + MathUtils.sinDeg(a) * .85f,
x + MathUtils.cosDeg(a + 8f) * 1.45f, y + MathUtils.sinDeg(a + 8f) * 1.45f,
x + MathUtils.cosDeg(a - 8f) * 1.45f, y + MathUtils.sinDeg(a - 8f) * 1.45f);
⋮----
float r2 = 1.42f + .12f * MathUtils.sin(time * 10f + i);
shapes.setColor(.72f, .54f, 1f, .36f * q);
shapes.rectLine(x + MathUtils.cosDeg(a) * r1, y + MathUtils.sinDeg(a) * r1,
x + MathUtils.cosDeg(a + 12f) * r2, y + MathUtils.sinDeg(a + 12f) * r2, .030f);
⋮----
float r = 1.08f + .18f * MathUtils.sin(time * 8f + i);
shapes.setColor(1f, .48f, .14f, .28f * q);
shapes.rectLine(x + MathUtils.cosDeg(a) * .78f, y + MathUtils.sinDeg(a) * .78f,
x + MathUtils.cosDeg(a) * r, y + MathUtils.sinDeg(a) * r, .045f);
⋮----
shapes.setColor(.40f, .94f, 1f, .42f * q);
shapes.circle(x + MathUtils.cosDeg(a) * 1.18f, y + MathUtils.sinDeg(a) * 1.18f, .075f, 8);
⋮----
shapes.setColor(1f, .58f, .08f, .30f * q);
shapes.circle(x, y, 1.76f * pulse, segments);
shapes.setColor(1f, .92f, .46f, .24f * q);
shapes.circle(x, y, .64f / pulse, segments);
```

## File: src/main/java/com/deadlinezero/game/visual/LocalLightRenderer.java
```java
/** Cheap additive-looking local light halos implemented with translucent geometry for mobile scalability. */
public final class LocalLightRenderer {
public void draw(ShapeRenderer shapes, Player player, Iterable<Enemy> enemies, Pools pools, float time) {
// ShapeRenderer does not enable alpha blending by itself. Without this, the tiny alpha values
// below are ignored and the player's light becomes a fully opaque cyan disc on Android.
Gdx.gl.glEnable(GL20.GL_BLEND);
Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
⋮----
halo(shapes, player.position.x, player.position.y, 1.85f,
player.invulnerable() ? Color.WHITE : VisualTheme.CYAN, .055f);
⋮----
halo(shapes, mx, my, 1.25f, VisualTheme.CYAN, .075f);
⋮----
Color c = missile.element.name().equals("FROST") ? VisualTheme.CYAN : VisualTheme.GOLD;
halo(shapes, missile.position.x, missile.position.y, 1.05f, c, .065f);
⋮----
halo(shapes, projectile.position.x, projectile.position.y, 1.15f, VisualTheme.RED, .052f);
⋮----
float pulse = .75f + .25f * MathUtils.sin(time * 4f);
⋮----
halo(shapes, enemy.position.x, enemy.position.y,
⋮----
private void halo(ShapeRenderer shapes, float x, float y, float radius, Color color, float alpha) {
shapes.setColor(color.r, color.g, color.b, alpha * .30f);
shapes.circle(x, y, radius * 1.55f, 28);
shapes.setColor(color.r, color.g, color.b, alpha * .60f);
shapes.circle(x, y, radius, 24);
shapes.setColor(color.r, color.g, color.b, alpha);
shapes.circle(x, y, radius * .52f, 20);
```

## File: src/main/java/com/deadlinezero/game/visual/NullBootstrapVfxArt.java
```java
/** Dedicated animated VFX fallback for Null-sector boss pressure and telegraphs. */
public final class NullBootstrapVfxArt implements Disposable {
⋮----
public static NullBootstrapVfxArt create() {
return new NullBootstrapVfxArt();
⋮----
public boolean supports(String key) { return firstTile(key) >= 0; }
⋮----
public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
int first = firstTile(key);
if (first < 0 || !ensureTexture()) return null;
int raw = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
int frame = loop ? raw % FRAMES_PER_EFFECT : Math.min(FRAMES_PER_EFFECT - 1, raw);
⋮----
private boolean ensureTexture() {
⋮----
Pixmap p = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
p.setBlending(Pixmap.Blending.SourceOver);
⋮----
drawFrame(p, effect, frame, effect * FRAMES_PER_EFFECT + frame);
⋮----
texture = new Texture(p);
texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
⋮----
regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
⋮----
texture.dispose();
⋮----
p.dispose();
⋮----
static int firstTile(String key) {
⋮----
for (int i = 0; i < ROOTS.length; i++) if (ROOTS[i].equals(key)) return i * FRAMES_PER_EFFECT;
⋮----
static int width() { return COLUMNS * TILE; }
static int height() { return ((TOTAL_TILES + COLUMNS - 1) / COLUMNS) * TILE; }
⋮----
private static void drawFrame(Pixmap p, int effect, int frame, int tile) {
⋮----
case 0 -> drawAura(p, cx, cy, frame, t);
case 1 -> drawPortal(p, cx, cy, frame, t);
case 2 -> drawFracture(p, cx, cy, frame, t);
⋮----
private static void drawAura(Pixmap p, int cx, int cy, int frame, float t) {
⋮----
p.setColor(.48f, .34f, 1f, .72f);
p.drawCircle(cx, cy, outer);
p.drawCircle(cx, cy, outer - 6);
p.setColor(.28f, .72f, 1f, .70f);
⋮----
int x1 = cx + (int)(Math.cos(a) * 10);
int y1 = cy + (int)(Math.sin(a) * 10);
int x2 = cx + (int)(Math.cos(a) * (outer + 6));
int y2 = cy + (int)(Math.sin(a) * (outer + 6));
p.drawLine(x1, y1, x2, y2);
⋮----
p.setColor(.82f, .72f, 1f, .82f);
p.fillCircle(cx, cy, 2 + frame % 2);
⋮----
private static void drawPortal(Pixmap p, int cx, int cy, int frame, float t) {
int radius = 8 + Math.round(t * 20f);
p.setColor(.34f, .22f, .88f, .88f);
p.drawCircle(cx, cy, radius);
p.drawCircle(cx, cy, Math.max(3, radius - 7));
p.setColor(.52f, .86f, 1f, .82f);
⋮----
int r1 = Math.max(4, radius - 5);
⋮----
p.drawLine(cx + (int)(Math.cos(a) * r1), cy + (int)(Math.sin(a) * r1),
cx + (int)(Math.cos(a + .18) * r2), cy + (int)(Math.sin(a + .18) * r2));
⋮----
p.setColor(.08f, .06f, .18f, .86f);
p.fillCircle(cx, cy, Math.max(2, radius - 9));
⋮----
private static void drawFracture(Pixmap p, int cx, int cy, int frame, float t) {
int reach = 9 + Math.round(t * 20f);
p.setColor(.72f, .58f, 1f, 1f - t * .25f);
⋮----
int mx = cx + (int)(Math.cos(a) * reach * .45f);
int my = cy + (int)(Math.sin(a) * reach * .45f);
int ex = cx + (int)(Math.cos(a) * reach);
int ey = cy + (int)(Math.sin(a) * reach);
p.drawLine(cx, cy, mx, my);
p.drawLine(mx, my, ex, ey);
⋮----
p.drawLine(mx, my, mx + (int)(Math.cos(branch) * reach * .35f), my + (int)(Math.sin(branch) * reach * .35f));
⋮----
p.setColor(.28f, .78f, 1f, .85f);
p.drawCircle(cx, cy, 5 + frame);
⋮----
@Override public void dispose() {
if (texture != null) texture.dispose();
```

## File: src/main/java/com/deadlinezero/game/visual/NullHazardPresentation.java
```java
/** Presentation contract for Null Sector hazards. */
public final class NullHazardPresentation {
public static final class Profile {
⋮----
private static final Profile RIFT = new Profile(
⋮----
private static final Profile STATIC = new Profile(
⋮----
private static final Profile BEAM = new Profile(
⋮----
public static boolean isNull(ArenaHazardRuntime.Type type) {
⋮----
public static Profile forType(ArenaHazardRuntime.Type type) {
⋮----
default -> throw new IllegalArgumentException("Not a Null Sector hazard: " + type);
```

## File: src/main/java/com/deadlinezero/game/visual/OnboardingHintPolicy.java
```java
/** Pure display policy for non-blocking combat onboarding hints. */
public final class OnboardingHintPolicy {
⋮----
public static int step(boolean movementSeen, boolean dashSeen, boolean upgradeSeen, boolean bossSeen) {
⋮----
public static boolean visible(boolean completed, int step, float ageSeconds) {
```

## File: src/main/java/com/deadlinezero/game/visual/PlayerProjectilePresentation.java
```java
/**
 * Pure presentation routing for player projectiles.
 *
 * The combat simulation owns damage/cadence. This class only gives each weapon family a recognizable
 * phone-scale silhouette: trail length/width, core scale, impact scale and an optional accent style.
 */
public final class PlayerProjectilePresentation {
⋮----
private static final Color PALE_CYAN = new Color(.78f, .96f, 1f, 1f);
private static final Color ICE = new Color(.52f, .90f, 1f, 1f);
private static final Color HOT = new Color(1f, .36f, .06f, 1f);
private static final Color EMBER = new Color(1f, .74f, .20f, 1f);
private static final Color ARC = new Color(.55f, .42f, 1f, 1f);
private static final Color BREACH = new Color(1f, .30f, .12f, 1f);
⋮----
public static Profile profile(Projectile projectile) {
if (projectile == null) return forWeapon("ar9", DamageElement.KINETIC, false, WeaponSignatureRuntime.Kind.NONE);
return forWeapon(WeaponSignatureRuntime.weaponId(), projectile.element, projectile.critical,
⋮----
static Profile forWeapon(String weaponId, DamageElement element, boolean critical,
⋮----
String id = weaponId == null ? "ar9" : weaponId.toLowerCase(java.util.Locale.ROOT);
⋮----
alpha = Math.min(1f, alpha + .08f);
⋮----
return new Profile(style, color, accent, length, width, alpha, core, impact, signature);
```

## File: src/main/java/com/deadlinezero/game/visual/PostFxShader.java
```java
/**
 * Optional low-cost fullscreen shader used later for subtle glow/color grading.
 * Failure is non-fatal: callers can fall back to the default SpriteBatch shader.
 */
public final class PostFxShader implements Disposable {
⋮----
shader = new ShaderProgram(VERTEX, FRAGMENT);
available = shader.isCompiled();
if (!available) Gdx.app.log("PostFxShader", shader.getLog());
⋮----
public boolean available() { return available; }
⋮----
public ShaderProgram shader(float intensity) {
⋮----
shader.bind();
shader.setUniformf("u_intensity", Math.max(0f, Math.min(1f, intensity)));
⋮----
@Override public void dispose() {
if (shader != null) shader.dispose();
```

## File: src/main/java/com/deadlinezero/game/visual/ProductionAtlasAudit.java
```java
/**
 * Strict release-art audit. Unlike GameArt.hasAnimation(), this class inspects only art/game.atlas
 * and deliberately ignores every generated/bootstrap fallback.
 */
public final class ProductionAtlasAudit {
⋮----
public static int validate() {
if (!Gdx.files.internal(ATLAS_PATH).exists()) {
Gdx.app.log("ProductionAtlasAudit", "Final production atlas is not installed: " + ATLAS_PATH);
⋮----
atlas = new TextureAtlas(Gdx.files.internal(ATLAS_PATH));
⋮----
for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
issues += auditDirectionalActor(atlas, "survivor/" + survivor.name().toLowerCase(), false);
⋮----
for (BiomeEnemyRoster.Identity identity : BiomeEnemyRoster.Identity.values()) {
⋮----
issues += auditDirectionalActor(atlas, "enemy/biome/" + identity.name().toLowerCase(), false);
⋮----
for (BossIdentity identity : BossIdentity.values()) {
issues += auditDirectionalActor(atlas, "boss/" + identity.name().toLowerCase(), true);
⋮----
if (issues == 0) Gdx.app.log("ProductionAtlasAudit", "Final directional animation audit passed.");
else Gdx.app.log("ProductionAtlasAudit", "Final atlas has " + issues + " missing/under-framed animation groups.");
⋮----
Gdx.app.error("ProductionAtlasAudit", "Unable to audit final atlas.", exception);
⋮----
if (atlas != null) atlas.dispose();
⋮----
static int auditDirectionalActor(TextureAtlas atlas, String root, boolean boss) {
⋮----
for (Direction8 direction : Direction8.values()) {
for (GameArt.Motion motion : GameArt.Motion.values()) {
String key = root + "/" + direction.atlasToken() + "/" + motion.name().toLowerCase();
int actual = frameCount(atlas, key);
int required = FinalArtContract.minimumFrames(motion, boss);
⋮----
Gdx.app.log("ProductionAtlasAudit", "Under-framed " + key + ": " + actual + "/" + required);
⋮----
static int frameCount(TextureAtlas atlas, String key) {
if (atlas == null || key == null || key.isBlank()) return 0;
Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(key);
⋮----
return atlas.findRegion(key) == null ? 0 : 1;
```

## File: src/main/java/com/deadlinezero/game/visual/SingularityImpactTracker.java
```java
/** Tracks pooled Singularity Shot lifecycles and emits short-lived visual collapse impacts. */
public final class SingularityImpactTracker {
⋮----
public static final class Impact {
⋮----
public float progress() {
return 1f - Math.max(0f, Math.min(1f, life / Math.max(.001f, maxLife)));
⋮----
private static final class State {
⋮----
public void update(Array<Projectile> projectiles, float dt) {
float safeDt = Math.max(0f, dt);
⋮----
Impact impact = impacts.get(i);
⋮----
if (impact.life <= 0f) impacts.removeIndex(i);
⋮----
State state = states.get(projectile);
⋮----
state = new State();
states.put(projectile, state);
⋮----
if (state.active && state.singularity) trigger(state.x, state.y);
⋮----
trigger(projectile.position.x, projectile.position.y);
⋮----
trigger(state.x, state.y);
⋮----
private void trigger(float x, float y) {
if (impacts.size >= MAX_IMPACTS) impacts.removeIndex(0);
Impact impact = new Impact();
⋮----
impacts.add(impact);
⋮----
public Array<Impact> impacts() { return impacts; }
⋮----
public int consumeTriggeredCount() {
```

## File: src/main/java/com/deadlinezero/game/visual/UpgradeIconRenderer.java
```java
/**
 * Scalable combat-upgrade glyphs. The rarity color owns the outer frame while the inner symbol
 * communicates the mechanical family before the player reads the card copy.
 */
public final class UpgradeIconRenderer {
private static final Color FIRE = new Color(1f, .34f, .06f, 1f);
private static final Color ICE = new Color(.48f, .90f, 1f, 1f);
private static final Color SHOCK = new Color(.62f, .42f, 1f, 1f);
private static final Color ORANGE = new Color(1f, .62f, .12f, 1f);
⋮----
public static void draw(ShapeRenderer shapes, Upgrade upgrade, float cx, float cy, float size, Color rarity) {
⋮----
UpgradePresentation.Archetype type = UpgradePresentation.archetype(upgrade);
Color family = familyColor(type);
⋮----
shapes.setColor(rarity.r, rarity.g, rarity.b, .18f);
shapes.circle(cx, cy, outer * 1.18f, 28);
shapes.setColor(rarity.r, rarity.g, rarity.b, .92f);
shapes.circle(cx, cy, outer, 28);
shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, 1f);
shapes.circle(cx, cy, inner, 26);
shapes.setColor(family.r, family.g, family.b, .18f);
shapes.circle(cx, cy, inner * .82f, 24);
⋮----
drawGlyph(shapes, type, cx, cy, size * .62f, family);
⋮----
static Color familyColor(UpgradePresentation.Archetype type) {
⋮----
private static void drawGlyph(ShapeRenderer shapes, UpgradePresentation.Archetype type,
⋮----
float t = Math.max(2f, s * .085f);
shapes.setColor(color);
⋮----
shapes.triangle(x - s * .10f, cy - s * .22f,
⋮----
shapes.triangle(cx, cy + s * .42f, cx - s * .34f, cy - s * .30f, cx, cy - s * .08f);
shapes.triangle(cx, cy + s * .42f, cx + s * .34f, cy - s * .30f, cx, cy - s * .08f);
shapes.setColor(VisualTheme.SURFACE_0);
shapes.circle(cx, cy - s * .02f, s * .10f, 12);
⋮----
shapes.triangle(cx + s * .40f, cy, cx - s * .10f, cy + s * .28f, cx - s * .10f, cy - s * .28f);
shapes.rect(cx - s * .38f, cy - t * .5f, s * .34f, t);
shapes.rect(cx - s * .30f, cy + s * .16f, s * .20f, t * .65f);
shapes.rect(cx - s * .30f, cy - s * .18f, s * .20f, t * .65f);
⋮----
shapes.rect(cx - t * .55f, cy - s * .32f, t * 1.10f, s * .64f);
shapes.rect(cx - s * .32f, cy - t * .55f, s * .64f, t * 1.10f);
shapes.setColor(color.r, color.g, color.b, .28f);
shapes.circle(cx, cy, s * .36f, 20);
⋮----
shapes.rect(cx - s * .30f, y - t * .45f, s * .45f, t * .9f);
shapes.triangle(cx + s * .30f, y, cx + s * .10f, y + s * .09f, cx + s * .10f, y - s * .09f);
⋮----
shapes.circle(cx, cy, s * .34f, 20);
⋮----
shapes.circle(cx, cy, s * .21f, 18);
⋮----
shapes.circle(cx, cy, s * .08f, 12);
shapes.rect(cx - t * .5f, cy + s * .27f, t, s * .18f);
shapes.rect(cx - t * .5f, cy - s * .45f, t, s * .18f);
shapes.rect(cx + s * .27f, cy - t * .5f, s * .18f, t);
shapes.rect(cx - s * .45f, cy - t * .5f, s * .18f, t);
⋮----
shapes.rect(cx - s * .35f, cy - t * .5f, s * .56f, t);
shapes.triangle(cx + s * .40f, cy, cx + s * .12f, cy + s * .16f, cx + s * .12f, cy - s * .16f);
shapes.setColor(color.r, color.g, color.b, .45f);
shapes.rect(cx - s * .34f, cy + s * .16f, s * .24f, t * .55f);
shapes.rect(cx - s * .34f, cy - s * .18f, s * .24f, t * .55f);
⋮----
shapes.rect(cx - t * .5f, cy - s * .34f, t, s * .68f);
shapes.rect(cx - s * .34f, cy - t * .5f, s * .68f, t);
⋮----
float x = cx + MathUtils.cosDeg(a) * s * .30f;
float y = cy + MathUtils.sinDeg(a) * s * .30f;
shapes.circle(x, y, s * .065f, 10);
⋮----
shapes.triangle(cx, cy + s * .44f, cx - s * .30f, cy - s * .34f, cx + s * .30f, cy - s * .34f);
shapes.setColor(1f, .78f, .22f, 1f);
shapes.triangle(cx + s * .05f, cy + s * .18f, cx - s * .13f, cy - s * .25f, cx + s * .17f, cy - s * .25f);
⋮----
float dx = MathUtils.cosDeg(a) * s * .40f;
float dy = MathUtils.sinDeg(a) * s * .40f;
shapes.rectLine(cx - dx, cy - dy, cx + dx, cy + dy, t * .60f);
⋮----
shapes.circle(cx, cy, s * .10f, 12);
⋮----
shapes.triangle(cx + s * .10f, cy + s * .42f, cx - s * .25f, cy + s * .02f, cx + s * .02f, cy + s * .02f);
shapes.triangle(cx - s * .08f, cy - s * .42f, cx + s * .25f, cy - s * .02f, cx - s * .02f, cy - s * .02f);
⋮----
shapes.setColor(colors[i]);
shapes.circle(cx + MathUtils.cosDeg(a) * s * .24f,
cy + MathUtils.sinDeg(a) * s * .24f, s * .11f, 12);
⋮----
shapes.circle(cx, cy, s * .07f, 10);
⋮----
shapes.rect(cx - t * .45f, cy - s * .22f, t * .90f, s * .44f);
shapes.triangle(cx, cy + s * .43f, cx - s * .16f, cy + s * .16f, cx + s * .16f, cy + s * .16f);
shapes.triangle(cx - t * .45f, cy - s * .12f, cx - s * .23f, cy - s * .34f, cx - t * .45f, cy - s * .28f);
shapes.triangle(cx + t * .45f, cy - s * .12f, cx + s * .23f, cy - s * .34f, cx + t * .45f, cy - s * .28f);
⋮----
shapes.rect(cx - s * .18f, cy - s * .16f, s * .36f, s * .32f);
shapes.rect(cx - s * .40f, cy - t * .45f, s * .22f, t * .9f);
shapes.rect(cx + s * .18f, cy - t * .45f, s * .22f, t * .9f);
⋮----
shapes.circle(cx, cy, s * .11f, 12);
⋮----
float bx = cx + MathUtils.cosDeg(a) * s * .32f;
float by = cy + MathUtils.sinDeg(a) * s * .32f;
shapes.triangle(bx, by,
cx + MathUtils.cosDeg(a + 18f) * s * .16f,
cy + MathUtils.sinDeg(a + 18f) * s * .16f,
cx + MathUtils.cosDeg(a - 18f) * s * .16f,
cy + MathUtils.sinDeg(a - 18f) * s * .16f);
⋮----
float ex = cx + MathUtils.cosDeg(a) * s * .36f;
float ey = cy + MathUtils.sinDeg(a) * s * .36f;
shapes.rectLine(cx, cy, ex, ey, t * .55f);
shapes.circle(ex, ey, s * .075f, 10);
```

## File: src/main/java/com/deadlinezero/game/visual/UpgradePresentation.java
```java
/** Pure routing from run upgrades to a stable visual archetype. */
public final class UpgradePresentation {
⋮----
public static Archetype archetype(Upgrade upgrade) {
```

## File: src/main/java/com/deadlinezero/game/visual/VisualTheme.java
```java
/** Centralized visual language for UI and combat. Keeps colors consistent and art-swappable. */
public final class VisualTheme {
public static final Color BG = new Color(.008f, .013f, .021f, 1f);
public static final Color PANEL = new Color(.025f, .041f, .060f, .96f);
public static final Color PANEL_ALT = new Color(.035f, .061f, .082f, .96f);
⋮----
/** Premium UI surface tokens. */
public static final Color SURFACE_0 = new Color(.010f, .017f, .026f, 1f);
public static final Color SURFACE_1 = new Color(.020f, .033f, .048f, .985f);
public static final Color SURFACE_2 = new Color(.032f, .051f, .070f, .985f);
public static final Color BORDER = new Color(.105f, .205f, .275f, .92f);
public static final Color BORDER_FOCUS = new Color(.19f, .72f, .90f, 1f);
public static final Color TEXT_STRONG = new Color(.975f, .990f, 1f, 1f);
public static final Color TEXT_DIM = new Color(.65f, .73f, .80f, 1f);
⋮----
public static final Color CYAN = new Color(.08f, .80f, 1f, 1f);
public static final Color CYAN_SOFT = new Color(.18f, .63f, .78f, 1f);
public static final Color GOLD = new Color(1f, .72f, .16f, 1f);
public static final Color RED = new Color(1f, .16f, .12f, 1f);
public static final Color GREEN = new Color(.20f, 1f, .58f, 1f);
public static final Color VIOLET = new Color(.70f, .27f, 1f, 1f);
public static final Color TEXT = new Color(.94f, .97f, 1f, 1f);
public static final Color MUTED = new Color(.53f, .62f, .70f, 1f);
public static final Color DIVIDER = new Color(.12f, .22f, .29f, .8f);
⋮----
private static final Color DANGER_DEUTERANOPIA = new Color(1f, .55f, .08f, 1f);
private static final Color DANGER_PROTANOPIA = new Color(1f, .62f, .10f, 1f);
private static final Color DANGER_TRITANOPIA = new Color(.92f, .18f, .58f, 1f);
private static final Color POSITIVE_DEUTERANOPIA = new Color(.10f, .72f, 1f, 1f);
private static final Color POSITIVE_PROTANOPIA = new Color(.08f, .68f, 1f, 1f);
private static final Color POSITIVE_TRITANOPIA = new Color(.20f, .88f, .55f, 1f);
private static final Color ACCENT_DEUTERANOPIA = new Color(.12f, .78f, 1f, 1f);
private static final Color ACCENT_PROTANOPIA = new Color(.14f, .76f, 1f, 1f);
private static final Color ACCENT_TRITANOPIA = new Color(.18f, .92f, .62f, 1f);
⋮----
public static Color semantic(Color standard, Color deuteranopia, Color protanopia, Color tritanopia) {
AccessibilitySettings.ColorVisionMode mode = AccessibilitySettings.active().colorVisionMode;
⋮----
public static Color danger() {
return semantic(RED, DANGER_DEUTERANOPIA, DANGER_PROTANOPIA, DANGER_TRITANOPIA);
⋮----
public static Color positive() {
return semantic(GREEN, POSITIVE_DEUTERANOPIA, POSITIVE_PROTANOPIA, POSITIVE_TRITANOPIA);
⋮----
public static Color accent() {
return semantic(CYAN, ACCENT_DEUTERANOPIA, ACCENT_PROTANOPIA, ACCENT_TRITANOPIA);
⋮----
public static Color equipmentRarity(EquipmentItem.Rarity rarity) {
⋮----
case COMMON -> new Color(.72f, .76f, .80f, 1f);
case RARE -> semantic(new Color(.14f, .65f, 1f, 1f), new Color(.12f, .72f, 1f, 1f), new Color(.12f, .72f, 1f, 1f), new Color(.12f, .82f, .58f, 1f));
case EPIC -> semantic(new Color(.66f, .30f, 1f, 1f), new Color(.86f, .38f, .88f, 1f), new Color(.82f, .38f, .92f, 1f), new Color(.88f, .34f, .72f, 1f));
case LEGENDARY -> semantic(new Color(1f, .63f, .08f, 1f), new Color(1f, .68f, .08f, 1f), new Color(1f, .70f, .10f, 1f), new Color(.95f, .55f, .18f, 1f));
case MYTHIC -> semantic(new Color(1f, .18f, .48f, 1f), new Color(.98f, .32f, .68f, 1f), new Color(.96f, .34f, .72f, 1f), new Color(.72f, .34f, 1f, 1f));
⋮----
public static Color upgradeRarity(UpgradeRarity rarity) {
⋮----
case COMMON -> new Color(.74f, .79f, .84f, 1f);
case RARE -> new Color(.12f, .68f, 1f, 1f);
case EPIC -> new Color(.67f, .30f, 1f, 1f);
case LEGENDARY -> new Color(1f, .68f, .08f, 1f);
```

## File: src/main/java/com/deadlinezero/game/visual/WeaponLegendaryPresentation.java
```java
/** Pure presentation routing for the one weapon-family legendary owned by the current run. */
public final class WeaponLegendaryPresentation {
⋮----
public static Style style(Player p) {
⋮----
if (p.legendary.hasVanguardProtocol()) return Style.VANGUARD;
if (p.legendary.hasScatterMaelstrom()) return Style.SCATTER;
if (p.legendary.hasRailPhaseLance()) return Style.RAIL;
if (p.legendary.hasInfernoPyroclasm()) return Style.INFERNO;
if (p.legendary.hasCryoPrism()) return Style.CRYO;
if (p.legendary.hasArcOverload()) return Style.ARC;
if (p.legendary.hasBreacherRupture()) return Style.BREACHER;
if (p.legendary.hasIonCascade()) return Style.ION;
if (p.legendary.hasCinderFurnace()) return Style.CINDER;
```

## File: src/main/java/com/deadlinezero/game/visual/WeaponRenderer.java
```java
/** Draws the currently equipped weapon as an authored overlay, with recoil and aim orientation. */
public final class WeaponRenderer {
⋮----
public void draw(SpriteBatch batch, Player player, float aimAngleDeg, float shotFlash) {
if (!art.authoredAvailable()) return;
String id = player.weapon.definition.id.toLowerCase();
TextureRegion region = art.regionOrNull("weapon/" + id);
⋮----
ArtProfileCatalog.CharacterProfile profile = ArtProfileCatalog.survivor(RunLoadoutContext.survivor());
⋮----
float recoil = MathUtils.clamp(shotFlash, 0f, 1f) * .16f;
⋮----
float forwardX = MathUtils.cos(radians);
float forwardY = MathUtils.sin(radians);
float anchorX = profile.weaponAnchorX();
float anchorY = profile.weaponAnchorY();
⋮----
float h = w * region.getRegionHeight() / (float)Math.max(1, region.getRegionWidth());
⋮----
batch.begin();
batch.setColor(1f, 1f, 1f, 1f);
batch.draw(region,
⋮----
batch.end();
```

## File: src/main/java/com/deadlinezero/game/visual/WorldFxRenderer.java
```java
/** Procedural combat presentation layer used until authored sprite/VFX assets replace primitives. */
public final class WorldFxRenderer {
private final AdaptiveFxBudget budget = new AdaptiveFxBudget();
⋮----
public float fxQuality() { return budget.quality(); }
public float smoothedFps() { return budget.smoothedFps(); }
⋮----
public void drawGroundShadows(ShapeRenderer shapes, Player player, Array<Enemy> enemies) {
budget.update(Gdx.graphics.getDeltaTime());
shapes.setColor(0f, 0f, 0f, .24f * MathUtils.lerp(.65f, 1f, budget.quality()));
shapes.ellipse(player.position.x - player.radius * 1.05f, player.position.y - player.radius * .82f,
⋮----
drawAbilityEvolutionAura(shapes, player);
int stride = budget.quality() < .55f ? 2 : 1;
⋮----
Enemy e = enemies.get(i);
⋮----
shapes.ellipse(e.position.x - width * .5f, e.position.y - e.radius * .72f,
⋮----
drawSpecialistTelegraph(shapes, e);
drawHarvesterSummonTelegraph(shapes, e);
⋮----
if (e.tacticalTelegraph()) {
float phase = e.tacticalWindup <= 0f ? 0f : MathUtils.clamp(e.tacticalWindup / .34f, 0f, 1f);
float pulse = .55f + .45f * MathUtils.sin(e.variantTime * 34f);
int segments = budget.geometrySegments(24, 12);
if (e.pendingTactic() == Enemy.Tactic.STRAFE) {
⋮----
shapes.setColor(.30f, .88f, 1f, .34f + pulse * .10f);
drawPeripheralRing(shapes, e.position.x, e.position.y, radius, Math.max(5, segments / 4), .055f);
if (budget.allowHeavyFx() && e.velocity.len2() > .01f) {
float len = e.velocity.len();
⋮----
shapes.setColor(.68f, .96f, 1f, .48f);
shapes.rectLine(e.position.x - nx * e.radius * 1.8f, e.position.y - ny * e.radius * 1.8f,
⋮----
} else if (e.pendingTactic() == Enemy.Tactic.CHARGE) {
⋮----
shapes.setColor(1f, .38f, .10f, .38f + pulse * .10f);
drawPeripheralRing(shapes, e.position.x, e.position.y, radius, Math.max(6, segments / 4), .064f);
if (budget.allowHeavyFx()) {
shapes.setColor(1f, .72f, .28f, .54f);
drawChargeArrows(shapes, e.position.x, e.position.y, radius * .82f, e.radius * .28f);
⋮----
private void drawPeripheralRing(ShapeRenderer shapes, float cx, float cy,
⋮----
int safePips = Math.max(4, pips);
⋮----
float x = cx + MathUtils.cosDeg(angle) * radius;
float y = cy + MathUtils.sinDeg(angle) * radius;
shapes.circle(x, y, pipRadius, 8);
⋮----
private void drawBracketCorners(ShapeRenderer shapes, float cx, float cy,
⋮----
float t = Math.max(.020f, length * .12f);
⋮----
shapes.rect(cx - d, cy + d - t, length, t);
shapes.rect(cx - d, cy + d - length, t, length);
shapes.rect(cx + d - length, cy + d - t, length, t);
shapes.rect(cx + d - t, cy + d - length, t, length);
shapes.rect(cx - d, cy - d, length, t);
shapes.rect(cx - d, cy - d, t, length);
shapes.rect(cx + d - length, cy - d, length, t);
shapes.rect(cx + d - t, cy - d, t, length);
⋮----
private void drawChargeArrows(ShapeRenderer shapes, float cx, float cy,
⋮----
float ox = MathUtils.cosDeg(angle);
float oy = MathUtils.sinDeg(angle);
⋮----
shapes.triangle(tipX, tipY,
⋮----
private float timeDeg(float degrees) {
⋮----
private void drawSpecialistTelegraph(ShapeRenderer shapes, Enemy e) {
if (budget.quality() < .34f) return;
float pulse = .5f + .5f * MathUtils.sin(e.variantTime * 6.5f + e.position.y * .31f);
int segments = budget.geometrySegments(28, 14);
⋮----
float shield = e.shieldFraction();
⋮----
shapes.setColor(.24f, .76f, 1f, .26f + shield * .14f);
drawPeripheralRing(shapes, e.position.x, e.position.y, radius, Math.max(6, segments / 4), .050f);
⋮----
shapes.setColor(.72f, .94f, 1f, .42f);
drawBracketCorners(shapes, e.position.x, e.position.y, radius * .86f, e.radius * .34f);
⋮----
float wounded = 1f - MathUtils.clamp(e.hp / Math.max(1f, e.maxHp), 0f, 1f);
⋮----
shapes.setColor(.30f, 1f, .42f, .18f + wounded * .18f);
drawPeripheralRing(shapes, e.position.x, e.position.y, radius, Math.max(5, segments / 4), .045f);
⋮----
shapes.setColor(.72f, 1f, .58f, .42f);
float pipRadius = Math.max(.035f, e.radius * .075f);
⋮----
float a = timeDeg(e.variantTime * 32f + i * 120f);
shapes.circle(e.position.x + MathUtils.cos(a) * radius * .72f,
e.position.y + MathUtils.sin(a) * radius * .72f, pipRadius, 10);
⋮----
float radius = e.radius * (e.phased() ? 2.05f : 1.48f + pulse * .18f);
float alpha = e.phased() ? .28f : .10f + pulse * .06f;
shapes.setColor(.60f, .36f, 1f, Math.max(.24f, alpha));
drawPeripheralRing(shapes, e.position.x, e.position.y, radius, Math.max(6, segments / 4), .052f);
if (e.phased() && budget.allowHeavyFx() && e.velocity.len2() > .01f) {
⋮----
shapes.setColor(.72f, .60f, 1f, .30f);
drawPeripheralRing(shapes,
⋮----
shapes.setColor(.82f, .72f, 1f, .18f);
⋮----
/** Three collapsing portals warn the player before HARVESTER creates its next minion wave. */
private void drawHarvesterSummonTelegraph(ShapeRenderer shapes, Enemy e) {
if (budget.quality() < .34f || e.type != Enemy.Type.BOSS || e.bossCombat == null || e.bossPhases == null
|| !e.bossCombat.harvester()) return;
int phase = e.bossPhases.phase();
if (!e.bossCombat.summonTelegraphing(phase)) return;
float progress = e.bossCombat.summonTelegraphProgress(phase);
float pulse = .5f + .5f * MathUtils.sin(e.variantTime * 22f);
⋮----
int segments = budget.geometrySegments(30, 16);
⋮----
float x = e.position.x + MathUtils.cosDeg(angle) * orbit;
float y = e.position.y + MathUtils.sinDeg(angle) * orbit;
float radius = e.radius * MathUtils.lerp(.72f, .28f, progress);
shapes.setColor(1f, .34f, .06f, .12f + progress * .20f);
shapes.circle(x, y, radius * 1.55f, segments);
shapes.setColor(.72f, 1f, .24f, .18f + progress * .28f);
shapes.circle(x, y, radius, segments);
⋮----
shapes.setColor(1f, .82f, .24f, .24f + progress * .20f);
shapes.circle(x, y, Math.max(.05f, radius * .34f), budget.geometrySegments(14, 8));
⋮----
private void drawAbilityEvolutionAura(ShapeRenderer shapes, Player player) {
if (budget.quality() < .42f) return;
float t = (float)(System.nanoTime() * 0.000000001);
float pulse = .82f + MathUtils.sin(t * 4.8f) * .18f;
⋮----
if (player.abilities.hasSuperconductorSynergy()) {
shapes.setColor(.35f, .92f, 1f, .16f);
shapes.circle(player.position.x, player.position.y, base * 1.22f, segments);
⋮----
shapes.setColor(.72f, .52f, 1f, .20f);
shapes.circle(player.position.x, player.position.y, base * .98f, segments);
⋮----
if (player.abilities.hasTeslaEvolution()) {
shapes.setColor(.32f, .86f, 1f, .20f + pulse * .06f);
shapes.circle(player.position.x, player.position.y, base * 1.48f, segments);
⋮----
if (player.abilities.hasCryoMissileEvolution()) {
shapes.setColor(.58f, .92f, 1f, .13f + pulse * .05f);
shapes.circle(player.position.x, player.position.y, base * 1.72f, segments);
⋮----
if (player.abilities.hasTargetNetworkSynergy() && budget.allowHeavyFx()) {
⋮----
float x = player.position.x + MathUtils.cosDeg(angle) * r;
float y = player.position.y + MathUtils.sinDeg(angle) * r;
shapes.setColor(.42f, 1f, .54f, .35f);
shapes.circle(x, y, .08f + pulse * .025f, budget.geometrySegments(10, 6));
⋮----
if (player.abilities.hasStormBladeSynergy()) {
shapes.setColor(.70f, .40f, 1f, .20f + pulse * .08f);
shapes.circle(player.position.x, player.position.y, base * 2.15f, segments);
} else if (player.abilities.hasPermafrostBladeSynergy()) {
shapes.setColor(.62f, .94f, 1f, .17f + pulse * .06f);
shapes.circle(player.position.x, player.position.y, base * 2.05f, segments);
⋮----
for (AbilityType type : AbilityType.values()) if (player.abilities.evolved(type)) evolved++;
if (evolved >= 3 && budget.allowExtraFx()) {
shapes.setColor(1f, .82f, .28f, .12f + pulse * .05f);
shapes.circle(player.position.x, player.position.y, base * 2.42f, segments);
⋮----
public void drawChampionAuras(ShapeRenderer shapes, Array<Enemy> enemies, float time) {
⋮----
float pulse = .82f + MathUtils.sin(time * 7.5f + e.position.x * .37f) * .18f;
⋮----
float pip = Math.max(.045f, e.radius * .12f);
⋮----
shapes.setColor(.48f, .88f, 1f, .30f);
int count = budget.allowHeavyFx() ? 4 : 3;
⋮----
float x = e.position.x + MathUtils.cosDeg(angle) * radius;
float y = e.position.y + MathUtils.sinDeg(angle) * radius;
float tangentX = -MathUtils.sinDeg(angle) * pip * 1.8f;
float tangentY = MathUtils.cosDeg(angle) * pip * 1.8f;
shapes.rectLine(x - tangentX, y - tangentY, x + tangentX, y + tangentY, pip * .48f);
⋮----
shapes.setColor(.82f, .90f, 1f, .28f);
⋮----
shapes.rect(e.position.x - d - bar, e.position.y - d, bar, d * .60f);
shapes.rect(e.position.x + d, e.position.y - d, bar, d * .60f);
shapes.rect(e.position.x - d - bar, e.position.y + d * .40f, bar, d * .60f);
shapes.rect(e.position.x + d, e.position.y + d * .40f, bar, d * .60f);
shapes.rect(e.position.x - d, e.position.y + d, d * .60f, bar);
shapes.rect(e.position.x + d * .40f, e.position.y + d, d * .60f, bar);
shapes.rect(e.position.x - d, e.position.y - d - bar, d * .60f, bar);
shapes.rect(e.position.x + d * .40f, e.position.y - d - bar, d * .60f, bar);
⋮----
shapes.setColor(1f, .42f, .18f, .32f);
⋮----
shapes.triangle(baseX + tx * half, baseY + ty * half,
⋮----
public void drawProjectileTrails(ShapeRenderer shapes, Array<Projectile> projectiles,
⋮----
float q = budget.quality();
⋮----
float speed = p.velocity.len();
⋮----
PlayerProjectilePresentation.Profile visual = PlayerProjectilePresentation.profile(p);
Color c = visual.color();
⋮----
shapes.setColor(c.r, c.g, c.b, visual.alpha() * MathUtils.lerp(.65f, 1f, q));
shapes.rectLine(p.position.x, p.position.y,
p.position.x - nx * visual.trailLength(),
p.position.y - ny * visual.trailLength(),
visual.trailWidth());
⋮----
drawPlayerProjectileAccent(shapes, p, visual, nx, ny);
⋮----
if (p.life > 1.41f && budget.allowHeavyFx()) {
⋮----
Color accent = visual.accent();
shapes.setColor(accent.r, accent.g, accent.b, .62f);
shapes.triangle(bx - sideX * .12f, by - sideY * .12f,
⋮----
shapes.setColor(1f, 1f, .88f, .84f);
shapes.circle(bx, by, .105f * visual.coreScale(), budget.geometrySegments(10, 6));
⋮----
shapes.setColor(c.r, c.g, c.b, .44f * MathUtils.lerp(.7f, 1f, q));
⋮----
shapes.setColor(1f, .82f, .26f, .36f);
shapes.circle(p.position.x - nx * .24f, p.position.y - ny * .24f,
.10f, budget.geometrySegments(10, 6));
⋮----
shapes.setColor(.82f, 1f, 1f, .48f);
shapes.rectLine(p.position.x + sideX, p.position.y + sideY,
⋮----
shapes.setColor(.82f, .62f, 1f, .32f);
shapes.circle(p.position.x - nx * .18f, p.position.y - ny * .18f,
.12f, budget.geometrySegments(12, 7));
⋮----
float speed = m.velocity.len();
⋮----
int trailNodes = budget.allowExtraFx() ? 3 : (budget.allowHeavyFx() ? 2 : 1);
⋮----
shapes.setColor(c.r, c.g, c.b, .42f * (1f - t * .55f));
float jitter = MathUtils.sin((m.life + i) * 19f) * .035f;
shapes.circle(m.position.x - nx * (.22f + i * .22f) + ny * jitter,
⋮----
.10f * (1f - t * .45f), budget.geometrySegments(10, 6));
⋮----
private void drawPlayerProjectileAccent(ShapeRenderer shapes, Projectile p,
⋮----
float core = Math.max(.055f, p.radius * visual.coreScale());
⋮----
switch (visual.style()) {
⋮----
shapes.setColor(1f, 1f, 1f, .78f);
⋮----
p.position.x - nx * visual.trailLength() * .82f,
p.position.y - ny * visual.trailLength() * .82f,
Math.max(.014f, visual.trailWidth() * .38f));
shapes.setColor(accent.r, accent.g, accent.b, .34f);
shapes.circle(p.position.x, p.position.y, core * .72f, budget.geometrySegments(10, 6));
⋮----
shapes.setColor(accent.r, accent.g, accent.b, .38f);
shapes.rectLine(p.position.x - sideX * core, p.position.y - sideY * core,
⋮----
shapes.rectLine(p.position.x + sideX * core, p.position.y + sideY * core,
⋮----
shapes.setColor(accent.r, accent.g, accent.b, visual.signature() ? .56f : .34f);
shapes.circle(p.position.x - nx * .22f, p.position.y - ny * .22f,
core * (visual.signature() ? 1.32f : .88f), budget.geometrySegments(14, 8));
shapes.setColor(1f, .90f, .45f, visual.signature() ? .78f : .48f);
shapes.circle(p.position.x, p.position.y, core * .48f, budget.geometrySegments(10, 6));
⋮----
float shard = core * (visual.signature() ? 2.0f : 1.35f);
shapes.setColor(accent.r, accent.g, accent.b, visual.signature() ? .70f : .42f);
shapes.triangle(
⋮----
if (visual.signature()) {
shapes.setColor(1f, 1f, 1f, .58f);
shapes.rectLine(p.position.x + sideX * shard * .72f, p.position.y + sideY * shard * .72f,
⋮----
shapes.rectLine(p.position.x - sideX * shard * .72f, p.position.y - sideY * shard * .72f,
⋮----
float separation = core * (visual.signature() ? 1.25f : .82f);
⋮----
shapes.rectLine(p.position.x + sideX * separation, p.position.y + sideY * separation,
p.position.x - nx * visual.trailLength() * .68f - sideX * separation,
p.position.y - ny * visual.trailLength() * .68f - sideY * separation,
visual.signature() ? .030f : .020f);
⋮----
core * .64f, budget.geometrySegments(10, 6));
⋮----
shapes.setColor(accent.r, accent.g, accent.b, .42f);
shapes.circle(p.position.x - nx * .14f, p.position.y - ny * .14f,
core * 1.15f, budget.geometrySegments(12, 7));
shapes.setColor(1f, .78f, .34f, .42f);
⋮----
shapes.setColor(accent.r, accent.g, accent.b, .48f);
shapes.circle(p.position.x, p.position.y, core, budget.geometrySegments(10, 6));
⋮----
public void drawElectricArcs(ShapeRenderer shapes, Array<ArcFx> arcs, float time) {
int segments = budget.geometrySegments(7, 4);
int stride = budget.quality() < .50f ? 2 : 1;
⋮----
ArcFx arc = arcs.get(ai);
if (!arc.refreshFromClock()) continue;
⋮----
float length = (float)Math.sqrt(dx * dx + dy * dy);
⋮----
float alpha = MathUtils.clamp(arc.life / Math.max(.001f, arc.maxLife), 0f, 1f);
⋮----
float envelope = MathUtils.sin(t * MathUtils.PI);
float jitter = MathUtils.sin(time * 43f + i * 5.71f + arc.x1 * 2.3f) * .15f * envelope;
⋮----
shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, 1f, .72f * alpha);
shapes.rectLine(prevX, prevY, x, y, .055f);
⋮----
shapes.setColor(1f, 1f, 1f, .52f * alpha);
shapes.rectLine(prevX, prevY, x, y, .018f);
```

## File: src/main/java/com/deadlinezero/game/world/ArenaHazardRuntime.java
```java
/**
 * Deterministic telegraphed arena hazards used by endgame pressure, biome pressure and delayed enemy death bursts.
 * Gameplay never deals damage during WARNING; each ACTIVE hazard can hit the player at most once.
 */
public final class ArenaHazardRuntime {
⋮----
public static final class Hazard {
⋮----
this.radius = Math.max(.1f, radius);
this.damage = Math.max(0f, damage);
this.warningDuration = Math.max(.01f, warningDuration);
⋮----
this.activeRemaining = Math.max(.01f, activeDuration);
⋮----
public Type type() { return type; }
public float x() { return x; }
public float y() { return y; }
public float radius() { return radius; }
public float damage() { return damage; }
public Phase phase() { return warningRemaining > 0f ? Phase.WARNING : Phase.ACTIVE; }
public float warningFraction() {
return warningRemaining <= 0f ? 0f : Math.min(1f, warningRemaining / warningDuration);
⋮----
public float activeFraction() {
return warningRemaining > 0f ? 0f : Math.max(0f, activeRemaining);
⋮----
public boolean playerDamageConsumed() { return playerDamageConsumed; }
/** Presentation-only edge trigger. Returns true exactly once after this hazard becomes ACTIVE. */
public boolean consumeActivationCue() {
if (phase() != Phase.ACTIVE || activationCueConsumed) return false;
⋮----
this(RunStageContext.stage(), RunStageContext.runOrdinal(), RunStageContext.threatTier());
⋮----
this.stage = Math.max(1, stage);
this.threatTier = ThreatTierRules.sanitizeTier(threatTier);
int x = this.stage * 0x45d9f3b + Math.max(0, runOrdinal) * 0x119de1f3 + this.threatTier * 0x27d4eb2d;
⋮----
this.periodicTimer = periodicInterval();
this.foundryTimer = foundryHazardInterval() * .72f;
this.nullTimer = nullSectorHazardInterval() * .68f;
⋮----
public boolean periodicHazardsEnabled() { return threatTier >= 5; }
⋮----
public float periodicInterval() {
if (!periodicHazardsEnabled()) return Float.POSITIVE_INFINITY;
return Math.max(6.2f, 12.5f - threatTier * .30f);
⋮----
/** Cinder Foundry owns stages 10-19. */
public boolean foundryHazardsEnabled() { return stage >= 10 && stage < 20; }
⋮----
public float foundryHazardInterval() {
if (!foundryHazardsEnabled()) return Float.POSITIVE_INFINITY;
float stagePressure = Math.min(9, Math.max(0, stage - 10)) * .25f;
⋮----
return Math.max(10.2f, 16.5f - stagePressure - threatPressure);
⋮----
/** Null Sector owns stage 20+ and uses a separate deterministic pressure clock. */
public boolean nullSectorHazardsEnabled() { return stage >= 20; }
⋮----
public float nullSectorHazardInterval() {
if (!nullSectorHazardsEnabled()) return Float.POSITIVE_INFINITY;
float stagePressure = Math.min(20, Math.max(0, stage - 20)) * .18f;
⋮----
return Math.max(8.8f, 14.2f - stagePressure - threatPressure);
⋮----
/** Advances timers and deterministically schedules endgame and biome hazards near the player's current region. */
public void update(float dt, float playerX, float playerY) {
float safeDt = Math.max(0f, dt);
if (periodicHazardsEnabled()) {
⋮----
schedulePeriodicStrike(playerX, playerY);
periodicTimer += periodicInterval();
⋮----
if (foundryHazardsEnabled()) {
⋮----
scheduleFoundryHazard(playerX, playerY);
foundryTimer += foundryHazardInterval();
⋮----
if (nullSectorHazardsEnabled()) {
⋮----
scheduleNullSectorHazard(playerX, playerY);
nullTimer += nullSectorHazardInterval();
⋮----
Iterator<Hazard> it = hazards.iterator();
while (it.hasNext()) {
Hazard h = it.next();
⋮----
if (h.warningRemaining <= 0f && h.activeRemaining <= 0f) it.remove();
⋮----
/** Delayed hostile explosion. No damage occurs until the warning has completed. */
public void scheduleDeathBurst(float x, float y, float radius, float damage) {
hazards.add(new Hazard(Type.DEATH_BURST, x, y, radius, damage, .48f, .24f));
⋮----
/** Returns accumulated damage from newly-hit active hazards, consuming each hazard at most once. */
public float consumePlayerDamage(float playerX, float playerY, float playerRadius) {
⋮----
float safeRadius = Math.max(0f, playerRadius);
⋮----
if (h.phase() != Phase.ACTIVE || h.playerDamageConsumed) continue;
⋮----
public List<Hazard> hazards() { return Collections.unmodifiableList(hazards); }
public int activeCount() { return hazards.size(); }
⋮----
private void schedulePeriodicStrike(float playerX, float playerY) {
int n = mix(seed + periodicIndex++ * 0x9e3779b9);
⋮----
float x = clamp(playerX + (float)Math.cos(angle) * distance, -29f, 29f);
float y = clamp(playerY + (float)Math.sin(angle) * distance, -15f, 15f);
⋮----
float warning = Math.max(.62f, 1.12f - threatTier * .018f);
hazards.add(new Hazard(Type.ORBITAL_STRIKE, x, y, radius, damage, warning, .32f));
⋮----
private void scheduleFoundryHazard(float playerX, float playerY) {
int n = mix(seed ^ 0x51ed270b ^ foundryIndex * 0x6d2b79f5);
int type = Math.floorMod(n, 3);
⋮----
if (type == 0) scheduleLavaVent(n, playerX, playerY);
else if (type == 1) scheduleSteamJet(n, playerX, playerY);
else scheduleHeatLine(n, playerX, playerY);
⋮----
private void scheduleLavaVent(int n, float playerX, float playerY) {
⋮----
float x = clamp(playerX + (float)Math.cos(angle) * distance, -28.5f, 28.5f);
float y = clamp(playerY + (float)Math.sin(angle) * distance, -14.5f, 14.5f);
float radius = 2.05f + Math.min(9, stage - 10) * .018f;
⋮----
hazards.add(new Hazard(Type.LAVA_VENT, x, y, radius, damage, 1.18f, .46f));
⋮----
private void scheduleSteamJet(int n, float playerX, float playerY) {
⋮----
float x = clamp(playerX + side * (2.2f + ((n >>> 16) & 0x3f) / 63f * 2.4f), -29f, 29f);
float y = clamp(playerY + (((n >>> 23) & 0x7f) / 127f - .5f) * 5f, -15f, 15f);
⋮----
hazards.add(new Hazard(Type.STEAM_JET, x, y, 1.45f, damage, .82f, .34f));
⋮----
/** Heat lines are represented by five overlapping telegraphed nodes, so rendered circles exactly match collision. */
private void scheduleHeatLine(int n, float playerX, float playerY) {
⋮----
float centerX = clamp(playerX + (((n >>> 18) & 0x1f) / 31f - .5f) * 3.2f, -24f, 24f);
float centerY = clamp(playerY + (((n >>> 23) & 0x1f) / 31f - .5f) * 3.2f, -11f, 11f);
⋮----
hazards.add(new Hazard(Type.HEAT_LINE, x, y, radius, damage, 1.04f, .28f));
⋮----
private void scheduleNullSectorHazard(float playerX, float playerY) {
int n = mix(seed ^ 0x7f4a7c15 ^ nullIndex * 0x5bd1e995);
⋮----
if (type == 0) scheduleVoidRift(n, playerX, playerY);
else if (type == 1) scheduleStaticBurst(n, playerX, playerY);
else scheduleNullBeam(n, playerX, playerY);
⋮----
private void scheduleVoidRift(int n, float playerX, float playerY) {
⋮----
float radius = 2.20f + Math.min(20, stage - 20) * .015f;
⋮----
hazards.add(new Hazard(Type.VOID_RIFT, x, y, radius, damage, 1.15f, .42f));
⋮----
private void scheduleStaticBurst(int n, float playerX, float playerY) {
⋮----
float x = clamp(playerX + (float)Math.cos(Math.toRadians(angle)) * distance, -29f, 29f);
float y = clamp(playerY + (float)Math.sin(Math.toRadians(angle)) * distance, -15f, 15f);
hazards.add(new Hazard(Type.STATIC_BURST, x, y, 1.22f, damage, .76f, .30f));
⋮----
private void scheduleNullBeam(int n, float playerX, float playerY) {
⋮----
float centerX = clamp(playerX + (((n >>> 18) & 0x1f) / 31f - .5f) * 2.8f, -23f, 23f);
float centerY = clamp(playerY + (((n >>> 23) & 0x1f) / 31f - .5f) * 2.8f, -10f, 10f);
⋮----
hazards.add(new Hazard(Type.NULL_BEAM, x, y, radius, damage, .98f, .25f));
⋮----
private static int mix(int x) {
⋮----
private static float clamp(float value, float min, float max) {
return Math.max(min, Math.min(max, value));
```

## File: src/main/java/com/deadlinezero/game/world/BiomeEnemyBehaviorRules.java
```java
/** Pure tactical tuning for biome-signature enemies. */
public final class BiomeEnemyBehaviorRules {
⋮----
private static final Profile DEFAULT = new Profile(1f, 1f, 1f, 1f, 1f, false, false, 1f);
⋮----
public static Profile forIdentity(BiomeEnemyRoster.Identity identity) {
⋮----
case FORGE_HOUND -> new Profile(1.12f, 1.34f, .72f, .80f, 1.28f, true, false, 1f);
case CINDER_GUNNER -> new Profile(.96f, 1.05f, .72f, 1.32f, .85f, false, true, 1f);
case SLAG_GUARD -> new Profile(.88f, 1.14f, .78f, .72f, 1.38f, true, false, 1f);
case PHASE_STALKER -> new Profile(1.08f, 1.48f, .66f, 1.42f, 1.05f, false, true, 1f);
case STATIC_SEER -> new Profile(.92f, 1.08f, .62f, 1.48f, .80f, false, true, 1f);
case NULL_WARD -> new Profile(.82f, 1.02f, .90f, .88f, .92f, false, false, 1.55f);
```

## File: src/main/java/com/deadlinezero/game/world/BiomeEnemyRoster.java
```java
/**
 * Deterministic biome enemy identities layered on top of stable low-level enemy archetypes.
 * This keeps collision/AI contracts intact while giving each biome a distinct combat population.
 */
public final class BiomeEnemyRoster {
⋮----
public boolean resists(DamageElement element) {
⋮----
public static Identity identityFor(int stage, Enemy.Type type) {
⋮----
EnvironmentBiomeRules.Biome biome = EnvironmentBiomeRules.forStage(stage);
⋮----
/**
     * Converts part of the generic population into biome-signature archetypes while preserving
     * boss spawns and already-specialized encounter choices.
     */
public static Enemy.Type remap(int stage, float rawRoll, Enemy.Type fallback) {
⋮----
float r = MathUtils.clamp(rawRoll, 0f, 1f);
⋮----
public static float elementalDamageMultiplier(int stage, Enemy.Type type, DamageElement element) {
⋮----
Identity identity = identityFor(stage, type);
return identity.resists(element) ? identity.resistanceMultiplier : 1f;
```

## File: src/main/java/com/deadlinezero/game/world/DeathBurstRules.java
```java
/** Deterministic endgame rule for enemies that leave a delayed hostile death burst. */
public final class DeathBurstRules {
⋮----
public static boolean enabled(Enemy.Type type, int threatTier) {
⋮----
int tier = ThreatTierRules.sanitizeTier(threatTier);
⋮----
public static float radius(Enemy.Type type, int threatTier) {
⋮----
return base + Math.max(0, tier - 8) * .025f;
⋮----
public static float damage(Enemy.Type type, int threatTier) {
```

## File: src/main/java/com/deadlinezero/game/world/EndgameWaveCompositionRules.java
```java
/** Pure bounded composition overlay for high-Threat runs. */
public final class EndgameWaveCompositionRules {
⋮----
public static Enemy.Type override(int threatTier, EndgameMutatorRules.Mutator mutator,
⋮----
float r = MathUtils.clamp(rawRoll, 0f, 1f);
⋮----
float gate = .24f + band.ordinal() * .035f;
⋮----
float pick = gate <= 0f ? 0f : r / Math.max(.0001f, gate * intensity);
⋮----
public static float maximumOverrideShare(int threatTier, WaveDirector.PressureBand band) {
⋮----
return (.24f + band.ordinal() * .035f) * intensity;
⋮----
/** Alternate within the same mutator identity when one enemy type has repeated too long. */
public static Enemy.Type streakBreaker(EndgameMutatorRules.Mutator mutator, Enemy.Type repeated) {
```

## File: src/main/java/com/deadlinezero/game/world/LeaperSpawnRules.java
```java
/** Stage and pressure-band spawn contract for the LEAPER archetype. */
public final class LeaperSpawnRules {
⋮----
public static boolean unlocked(int stage) {
⋮----
public static float share(int stage, WaveDirector.PressureBand band) {
if (!unlocked(stage) || band == null) return 0f;
⋮----
float stageBonus = Math.max(0, stage - MIN_STAGE) * .0075f;
return MathUtils.clamp(base + stageBonus, 0f, MAX_SHARE);
```

## File: src/main/java/com/deadlinezero/game/world/RunEncounterDirector.java
```java
/** Deterministic, stage-scaled pressure encounters embedded in a run. */
public final class RunEncounterDirector {
⋮----
this.stage = Math.max(1, stage);
buildPlan(RunStageContext.encounterSeed(), RunStageContext.runOrdinal());
⋮----
private void buildPlan(int seed, int runOrdinal) {
int safeOrdinal = Math.max(0, runOrdinal);
int start = Math.floorMod(seed + safeOrdinal, CATALOG.length);
⋮----
plan[i] = CATALOG[Math.floorMod(start + i * direction, CATALOG.length)];
⋮----
anchorMutatorEncounter();
⋮----
private void anchorMutatorEncounter() {
if (RunStageContext.threatTier() < 5 || !EndgameMutatorRules.active()) return;
Type signature = switch (EndgameMutatorRules.current()) {
⋮----
public void update(float dt, float bossProgress) {
⋮----
remaining = Math.max(0f, remaining - Math.max(0f, dt));
if (remaining <= 0f) completeActive();
⋮----
if (bossProgress >= threshold) start(nextIndex++);
⋮----
private void start(int index) {
active = plan[MathUtils.clamp(index, 0, plan.length - 1)];
⋮----
private void completeActive() {
⋮----
long stageBonus = Math.min(50L, Math.max(0, stage - 1) * 2L);
RunEncounterRuntime.award(base + stageBonus);
⋮----
public float spawnIntervalMultiplier() {
⋮----
public Enemy.Type overrideType(float roll, Enemy.Type fallback) {
roll = MathUtils.clamp(roll, 0f, 1f);
⋮----
return BiomeEnemyRoster.remap(stage, roll, encounterType);
⋮----
public Type active() { return active; }
public boolean activeEncounter() { return active != Type.NONE; }
public float remaining() { return remaining; }
public int triggeredCount() { return nextIndex; }
public Type planned(int index) {
```

## File: src/main/java/com/deadlinezero/game/world/SpatialHash.java
```java
/** Broad-phase collision index. Buckets are retained and cleared, avoiding frame-by-frame allocation. */
public final class SpatialHash {
⋮----
public void add(Enemy enemy) {
⋮----
int cx = floor(enemy.position.x / cellSize);
int cy = floor(enemy.position.y / cellSize);
maxQueryRing = Math.max(maxQueryRing, Math.max(Math.abs(cx), Math.abs(cy)));
int key = key(cx, cy);
Array<Enemy> bucket = cells.get(key);
⋮----
cells.put(key, bucket);
⋮----
activeBuckets.add(bucket);
⋮----
bucket.add(enemy);
⋮----
public void rebuild(Array<Enemy> enemies) {
for (Array<Enemy> bucket : activeBuckets) bucket.clear();
activeBuckets.clear();
⋮----
for (Enemy enemy : enemies) add(enemy);
⋮----
public void query(float x, float y, float radius, Array<Enemy> out) {
out.clear();
int minX = floor((x - radius) / cellSize);
int maxX = floor((x + radius) / cellSize);
int minY = floor((y - radius) / cellSize);
int maxY = floor((y + radius) / cellSize);
⋮----
Array<Enemy> bucket = cells.get(key(cx, cy));
if (bucket != null) out.addAll(bucket);
⋮----
/** Finds the nearest alive enemy without allocating a candidate collection. */
public Enemy nearest(float x, float y) {
⋮----
int originX = floor(x / cellSize);
int originY = floor(y / cellSize);
⋮----
int limit = maxQueryRing + Math.max(Math.abs(originX), Math.abs(originY)) + 1;
⋮----
float outside = Math.max(0f, ring * cellSize - cellSize);
⋮----
/** Finds the nearest alive enemy inside radius, excluding up to two identities. */
public Enemy nearestWithin(float x, float y, float radius, Enemy excludeA, Enemy excludeB) {
⋮----
public int activeBucketCount() {
⋮----
public int retainedBucketCount() {
⋮----
private static int floor(float value) {
⋮----
private static int key(int x, int y) {
```

## File: src/main/java/com/deadlinezero/game/world/StageCombatPressureAudit.java
```java
/**
 * Pure diagnostic model for CI balance guardrails. It never feeds runtime difficulty.
 * The score intentionally combines core enemy scaling, assault-band spawn tempo and
 * nominal biome hazard pressure so biome boundaries cannot hide abrupt spikes.
 */
public final class StageCombatPressureAudit {
⋮----
public static Snapshot snapshot(int stage) {
int s = Math.max(1, stage);
⋮----
float speed = Math.min(1.42f, 1f + i * .018f);
float stageAcceleration = Math.min(.11f, i * .008f);
float spawnInterval = Math.max(.055f, .34f - stageAcceleration);
⋮----
hazardInterval = Math.max(8.8f, 14.2f - Math.min(20, s - 20) * .18f);
⋮----
hazardInterval = Math.max(10.2f, 16.5f - Math.min(9, s - 10) * .25f);
⋮----
float hazardPerSecond = Float.isFinite(hazardInterval) ? hazardDamage / hazardInterval : 0f;
⋮----
return new Snapshot(s, hp, damage, speed, spawnInterval, hazardInterval, hazardDamage, composite);
⋮----
public static float relativeJump(int fromStage, int toStage) {
float from = snapshot(fromStage).compositePressure();
float to = snapshot(toStage).compositePressure();
```

## File: src/main/java/com/deadlinezero/game/world/WaveDirector.java
```java
/** Stage-aware wave pacing with readable pressure bands, squad bursts and named special encounters. */
public final class WaveDirector {
⋮----
private final int stage = Math.max(1, RunStageContext.stage());
private final float bossArrival = StageMissionRules.bossArrivalSeconds(stage);
private final RunEncounterDirector encounters = new RunEncounterDirector(stage);
⋮----
public void update(float dt) {
⋮----
encounters.update(dt, bossProgress());
⋮----
RunMissionRuntime.update(elapsed, kills);
⋮----
public boolean shouldSpawn() { return !bossSpawned && spawnTimer <= 0f; }
⋮----
public void onSpawn() {
float runPressure = RunModifierContext.spawnIntervalMultiplier();
if (bossPending && RunModifierContext.twinApex() && bossSpawnCount == 1) {
⋮----
spawnTimer = (.085f + MathUtils.random(0f, .035f)) * encounters.spawnIntervalMultiplier() * runPressure;
⋮----
float base = switch (pressureBand()) {
⋮----
float stageAcceleration = Math.min(.11f, (stage - 1) * .008f);
float lateAcceleration = Math.min(.10f, elapsed * .00055f);
spawnTimer = Math.max(.055f, (base - stageAcceleration - lateAcceleration)
* encounters.spawnIntervalMultiplier() * runPressure);
⋮----
float squadChance = switch (pressureBand()) {
⋮----
squadChance = Math.min(.22f, squadChance + (stage - 1) * .008f);
if (encounters.activeEncounter()) squadChance = Math.min(.32f, squadChance + .08f);
if (RunModifierContext.eliteHunt() || RunModifierContext.specialistSiege()) squadChance = Math.min(.38f, squadChance + .07f);
if (!bossPending && MathUtils.random() < squadChance) {
int min = pressureBand().ordinal() >= PressureBand.ASSAULT.ordinal() ? 2 : 1;
int max = Math.min(5, min + 1 + stage / 5);
squadRemaining = MathUtils.random(min, max);
⋮----
public void onBossSpawned() {
⋮----
int required = RunModifierContext.twinApex() ? 2 : 1;
⋮----
public void onKill() {
⋮----
public int kills() { return kills; }
public float elapsed() { return elapsed; }
public boolean bossPending() { return bossPending; }
public boolean bossSpawned() { return bossSpawned; }
public int bossSpawnCount() { return bossSpawnCount; }
public int squadRemaining() { return squadRemaining; }
public float bossArrivalSeconds() { return bossArrival; }
public float secondsUntilBoss() { return Math.max(0f, bossArrival - elapsed); }
public float bossProgress() { return MathUtils.clamp(elapsed / Math.max(1f, bossArrival), 0f, 1f); }
public boolean bossWarning() { return !bossSpawned && secondsUntilBoss() <= 30f; }
public RunEncounterDirector.Type activeEncounter() { return encounters.active(); }
public float encounterSecondsRemaining() { return encounters.remaining(); }
⋮----
public PressureBand pressureBand() {
float p = bossProgress();
⋮----
Enemy.Type legendaryOverride(float rawRoll) {
float r = MathUtils.clamp(rawRoll, 0f, 1f);
if (RunModifierContext.phantomEclipse()) {
⋮----
if (RunModifierContext.specialistSiege()) {
⋮----
public Enemy.Type chooseType() {
⋮----
Enemy.Type legendary = legendaryOverride(MathUtils.random());
⋮----
if (RunModifierContext.eliteHunt()) {
float eliteRoll = MathUtils.random();
float unlockBias = Math.min(.16f, Math.max(0, stage - 4) * .018f);
⋮----
float r = MathUtils.random();
float stageBias = Math.min(.14f, (stage - 1) * .012f);
Enemy.Type fallback = switch (pressureBand()) {
⋮----
Enemy.Type encounter = encounters.overrideType(MathUtils.random(), fallback);
Enemy.Type selected = EndgameWaveCompositionRules.override(
RunStageContext.threatTier(), EndgameMutatorRules.current(), pressureBand(), MathUtils.random(), encounter);
return enforceEndgameVariety(selected);
⋮----
private Enemy.Type enforceEndgameVariety(Enemy.Type selected) {
if (selected == null || RunStageContext.threatTier() < 5 || !EndgameMutatorRules.active()) return selected;
⋮----
Enemy.Type replacement = EndgameWaveCompositionRules.streakBreaker(EndgameMutatorRules.current(), selected);
```

## File: src/main/java/com/deadlinezero/game/DeadlineZeroGame.java
```java
public final class DeadlineZeroGame extends Game {
⋮----
public DeadlineZeroGame(GameServices services) { this.services = services == null ? GameServices.noOp() : services; }
⋮----
@Override public void create() {
art = new GameArt();
accessibility = AccessibilitySettings.load();
i18n = Localization.loadEnglish();
GraphicsSettings.load();
audio = new AudioDirector();
audio.setVolumes(accessibility.masterVolume, accessibility.sfxVolume, accessibility.musicVolume);
services.ads.setFullscreenListener(new AdsService.FullscreenListener() {
@Override public void onOpening() {
⋮----
if (audio != null) audio.suspend();
⋮----
@Override public void onClosed() {
⋮----
if (audio != null) audio.resume();
⋮----
profile = ProfileStore.load();
EntitlementStore.loadInto(profile);
long epochDay = System.currentTimeMillis() / DAY_MS;
DailyService.refresh(profile, epochDay);
WeeklyService.refresh(profile, epochDay);
profile.survivors.refreshUnlocks(profile);
services.billing.initialize();
services.ads.preload();
saveProfile();
showMenu();
⋮----
@Override public void render() {
if (profile != null && PurchaseGrantService.syncPermanent(profile, services.billing)) saveProfile();
⋮----
super.render();
⋮----
public void showMenu() { RunMissionRuntime.end(); RunEncounterRuntime.end(); RunModifierContext.end(); RunLoadoutContext.end(); if (audio != null) audio.stopCombatMusic(); setScreen(new MenuScreen(this)); }
public void showGear() { setScreen(new GearScreen(this)); }
public void showArsenal() { setScreen(new ArsenalScreen(this)); }
public void showMissions() { setScreen(new MissionsScreen(this)); }
public void showShop() { setScreen(new ShopScreen(this)); }
public void showSurvivors() { setScreen(new SurvivorScreen(this)); }
public void showSettings() { setScreen(new SettingsScreen(this)); }
public void showCloudSave() { setScreen(new CloudSaveScreen(this)); }
⋮----
/** Prepares a stable run identity, then asks the player to choose one of three risk/reward contracts. */
public void startRun() {
⋮----
int runOrdinal = profile == null ? 0 : Math.max(0, profile.totalRuns);
⋮----
RunStageContext.begin(selectedStage, runOrdinal, threatTier);
RunModifierContext.end();
setScreen(new RunContractScreen(this));
⋮----
/** Starts combat only after validating that the chosen contract belongs to the current offer set. */
public void startRunWithContract(RunModifierContext.Modifier contract) {
if (!RunModifierContext.choose(contract)) return;
RunLoadoutContext.begin(profile);
RunEncounterRuntime.begin();
RunMissionRuntime.begin(() -> Gdx.app.postRunnable(() -> finishVictory()), RunModifierContext.requiredBossKills());
if (audio != null) audio.startCombatMusic(RunStageContext.stage());
setScreen(new GameScreen(this));
⋮----
private void finishVictory() {
if (!(getScreen() instanceof GameScreen)) return;
finishRunInternal(RunMissionRuntime.kills(), RunMissionRuntime.elapsed(), true, true);
⋮----
public void finishRun(int kills, float secondsSurvived, boolean bossKilled, int ignoredStage) {
boolean objectiveComplete = bossKilled && RunMissionRuntime.bossKills() >= RunMissionRuntime.requiredBossKills();
finishRunInternal(kills, secondsSurvived, objectiveComplete, false);
⋮----
private void finishRunInternal(int kills, float secondsSurvived, boolean bossKilled, boolean victorySignal) {
int safeStage = RunStageContext.stage();
int runThreatTier = RunStageContext.threatTier();
⋮----
long firstClearCredits = firstClear ? StageMissionRules.firstClearCredits(safeStage) : 0L;
int firstClearGems = firstClear ? StageMissionRules.firstClearGems(safeStage) : 0;
String contractTitle = RunModifierContext.title();
int contractBonus = RunModifierContext.rewardBonusPercent();
⋮----
RunRewardCalculator.Rewards rewards = RunSettlement.apply(profile, kills, secondsSurvived, bossKilled, safeStage);
long encounterCredits = RunEncounterRuntime.consumeBonusCredits();
if (encounterCredits > 0L) profile.addCurrency(PlayerProfile.Currency.CREDITS, encounterCredits);
⋮----
DailyService.recordRun(profile, kills, bossKilled);
WeeklyService.recordRun(profile, kills, bossKilled);
long survivorXp = 35L + Math.max(0, kills) / 4L + safeStage * 12L + (bossKilled ? 80L : 0L);
profile.survivors.addXp(profile.selectedSurvivor, survivorXp);
⋮----
profile.addCurrency(PlayerProfile.Currency.CREDITS, firstClearCredits);
profile.addCurrency(PlayerProfile.Currency.GEMS, firstClearGems);
profile.highestStage = StageRules.nextStage(safeStage);
⋮----
? ThreatProgressionService.applyBossClear(profile, safeStage, runThreatTier)
: ThreatProgressionService.UnlockResult.none();
⋮----
if (!profile.inventory.full() && (bossKilled || MathUtils.randomBoolean(.55f))) {
drop = EquipmentDropTable.roll(safeStage, bossKilled);
profile.inventory.add(drop);
⋮----
RunMissionRuntime.end();
RunEncounterRuntime.end();
⋮----
RunLoadoutContext.end();
if (audio != null) audio.stopCombatMusic();
⋮----
RunResult result = new RunResult(kills, secondsSurvived, bossKilled, safeStage, rewards, drop,
⋮----
runThreatTier, ThreatTierRules.rewardBonusPercent(runThreatTier),
threatUnlock.unlocked() ? threatUnlock.tier() : 0,
threatUnlock.milestoneGems());
if (bossKilled || victorySignal) setScreen(new VictoryScreen(this, result, firstClear, firstClearCredits, firstClearGems));
else setScreen(new RunResultScreen(this, result));
⋮----
/**
     * Applies a cloud restore atomically to the running game. The restore service returns a freshly
     * reloaded profile; replacing the active reference here prevents a later save from resurrecting
     * the stale pre-restore object. Store-owned entitlements remain device/store authoritative.
     */
public boolean applyCloudRestore(CloudSaveService.RestoreResult restore) {
if (restore == null || restore.result() != CloudSaveService.DownloadResult.APPLIED || restore.profile() == null) {
⋮----
profile = restore.profile();
⋮----
public void saveProfile() {
ProfileStore.save(profile);
EntitlementStore.save(profile);
if (accessibility != null) accessibility.save();
⋮----
@Override public void pause() {
⋮----
super.pause();
⋮----
@Override public void resume() {
super.resume();
⋮----
@Override public void dispose() {
⋮----
super.dispose();
if (audio != null) audio.dispose();
if (art != null) art.dispose();
⋮----
@Override public void setScreen(com.badlogic.gdx.Screen screen) { if (getScreen() != null) getScreen().dispose(); super.setScreen(screen); }
```

## File: src/test/java/com/deadlinezero/game/abilities/AbilityLoadoutTierTest.java
```java
public final class AbilityLoadoutTierTest {
@Test public void tiersAdvanceAtLevelsThreeAndFive() {
AbilityLoadout loadout = new AbilityLoadout();
assertEquals(0, loadout.tier(AbilityType.TESLA_ORB));
loadout.upgrade(AbilityType.TESLA_ORB);
assertEquals(1, loadout.tier(AbilityType.TESLA_ORB));
⋮----
assertEquals(2, loadout.tier(AbilityType.TESLA_ORB));
⋮----
assertEquals(3, loadout.tier(AbilityType.TESLA_ORB));
assertTrue(loadout.evolved(AbilityType.TESLA_ORB));
⋮----
@Test public void levelsRemainCappedAtFive() {
⋮----
for (int i = 0; i < 20; i++) loadout.upgrade(AbilityType.DRONE);
assertEquals(AbilityLoadout.MAX_LEVEL, loadout.level(AbilityType.DRONE));
⋮----
@Test public void superconductorRequiresMatureTeslaAndCryo() {
⋮----
for (int i = 0; i < 3; i++) loadout.upgrade(AbilityType.TESLA_ORB);
assertFalse(loadout.hasSuperconductorSynergy());
for (int i = 0; i < 3; i++) loadout.upgrade(AbilityType.CRYO_NOVA);
assertTrue(loadout.hasSuperconductorSynergy());
⋮----
@Test public void droneDoctrineRequiresTierTwoAndIsExclusive() {
⋮----
assertFalse(loadout.chooseDroneDoctrine(DroneDoctrine.HUNTER));
for (int i = 0; i < 3; i++) loadout.upgrade(AbilityType.DRONE);
assertTrue(loadout.chooseDroneDoctrine(DroneDoctrine.HUNTER));
assertEquals(DroneDoctrine.HUNTER, loadout.droneDoctrine());
assertTrue(loadout.hasDroneDoctrine());
assertFalse(loadout.chooseDroneDoctrine(DroneDoctrine.SENTINEL));
⋮----
@Test public void stormBladeRequiresBothEvolutions() {
⋮----
for (int i = 0; i < 5; i++) loadout.upgrade(AbilityType.ORBITAL_BLADE);
for (int i = 0; i < 4; i++) loadout.upgrade(AbilityType.TESLA_ORB);
assertFalse(loadout.hasStormBladeSynergy());
⋮----
assertTrue(loadout.hasStormBladeSynergy());
```

## File: src/test/java/com/deadlinezero/game/abilities/AbilitySynergyUnlockDetectorTest.java
```java
final class AbilitySynergyUnlockDetectorTest {
@Test void detectsNewSuperconductorAfterMaturingTesla() {
AbilityLoadout a = new AbilityLoadout();
for (int i = 0; i < 3; i++) a.upgrade(AbilityType.CRYO_NOVA);
for (int i = 0; i < 2; i++) a.upgrade(AbilityType.TESLA_ORB);
var before = AbilitySynergyUnlockDetector.snapshot(a);
⋮----
a.upgrade(AbilityType.TESLA_ORB);
⋮----
assertEquals(AbilitySynergyUnlockDetector.Synergy.SUPERCONDUCTOR,
AbilitySynergyUnlockDetector.newlyActivated(before, a));
⋮----
@Test void noEventWhenUpgradeDoesNotCreateSynergy() {
⋮----
assertEquals(AbilitySynergyUnlockDetector.Synergy.NONE,
⋮----
@Test void stormBladeHasPriorityWhenSeveralSynergiesAppearTogether() {
⋮----
for (int i = 0; i < 5; i++) a.upgrade(AbilityType.ORBITAL_BLADE);
for (int i = 0; i < 4; i++) a.upgrade(AbilityType.TESLA_ORB);
⋮----
assertEquals(AbilitySynergyUnlockDetector.Synergy.STORM_BLADE,
```

## File: src/test/java/com/deadlinezero/game/abilities/AbilityUpgradeGuidanceTest.java
```java
final class AbilityUpgradeGuidanceTest {
private Player fresh() {
RunLoadoutContext.end();
return new Player(0f, 0f);
⋮----
@Test void freshAbilityShowsUnlockThenTierAndEvolutionMilestones() {
Player p = fresh();
assertEquals("combat.abilityGuidance.unlock", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
p.abilities.upgrade(AbilityType.TESLA_ORB);
assertEquals("combat.abilityGuidance.level", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
⋮----
assertEquals("combat.abilityGuidance.tier2", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
⋮----
assertEquals("combat.abilityGuidance.evolution", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
⋮----
@Test void choiceThatCompletesSynergyOverridesGenericMilestone() {
⋮----
for (int i = 0; i < 3; i++) p.abilities.upgrade(AbilityType.CRYO_NOVA);
for (int i = 0; i < 2; i++) p.abilities.upgrade(AbilityType.TESLA_ORB);
assertEquals("combat.synergy.superconductor", AbilityUpgradeGuidance.key(p, Upgrade.TESLA_ORB));
⋮----
@Test void evolvedTeslaMakesDroneTierTwoChoiceExposeArcReactor() {
⋮----
for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.TESLA_ORB);
for (int i = 0; i < 2; i++) p.abilities.upgrade(AbilityType.DRONE);
assertEquals("combat.synergy.arcReactor", AbilityUpgradeGuidance.key(p, Upgrade.DRONE));
⋮----
@Test void nonAbilityAndMaxedAbilityHaveNoGuidance() {
⋮----
assertNull(AbilityUpgradeGuidance.key(p, Upgrade.DAMAGE));
for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.ORBITAL_BLADE);
assertNull(AbilityUpgradeGuidance.key(p, Upgrade.ORBITAL));
```

## File: src/test/java/com/deadlinezero/game/abilities/DroneDoctrineRulesTest.java
```java
final class DroneDoctrineRulesTest {
@Test void hunterIsTheOffensiveDoctrine() {
assertEquals(1.30f, DroneDoctrineRules.damageMultiplier(DroneDoctrine.HUNTER), .0001f);
assertEquals(.72f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.HUNTER, false), .0001f);
assertEquals(.82f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.HUNTER, true), .0001f);
assertEquals(0f, DroneDoctrineRules.interceptionRange(DroneDoctrine.HUNTER), .0001f);
⋮----
@Test void sentinelTradesDamageForProjectileInterception() {
assertEquals(.88f, DroneDoctrineRules.damageMultiplier(DroneDoctrine.SENTINEL), .0001f);
assertEquals(0f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.SENTINEL, false), .0001f);
assertEquals(3.4f, DroneDoctrineRules.interceptionRange(DroneDoctrine.SENTINEL), .0001f);
⋮----
@Test void noDoctrinePreservesExistingDroneBehavior() {
assertEquals(1f, DroneDoctrineRules.damageMultiplier(DroneDoctrine.NONE), .0001f);
assertEquals(0f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.NONE, false), .0001f);
assertEquals(.62f, DroneDoctrineRules.secondaryTargetMultiplier(DroneDoctrine.NONE, true), .0001f);
assertEquals(0f, DroneDoctrineRules.interceptionRange(DroneDoctrine.NONE), .0001f);
```

## File: src/test/java/com/deadlinezero/game/ai/AttackControllerCadenceTest.java
```java
public final class AttackControllerCadenceTest {
@Test public void cadenceModifiersAreClamped() {
AttackController c = new AttackController(EnemyArchetype.MELEE);
c.setCadence(.1f, 4f, .2f);
assertEquals(.45f, c.cooldownMultiplier(), .0001f);
assertEquals(1.8f, c.telegraphMultiplier(), .0001f);
⋮----
@Test public void fasterCadenceUsesShorterConfiguredCycle() {
AttackController fast = new AttackController(EnemyArchetype.MELEE);
AttackController slow = new AttackController(EnemyArchetype.MELEE);
fast.setCadence(.50f, .70f, .70f);
slow.setCadence(1.40f, 1.30f, 1.30f);
⋮----
assertTrue(fast.cooldownMultiplier() < slow.cooldownMultiplier());
assertTrue(fast.telegraphMultiplier() < slow.telegraphMultiplier());
⋮----
float fastCycle = EnemyArchetype.MELEE.attackCooldown * fast.cooldownMultiplier()
+ EnemyArchetype.MELEE.telegraphDuration * fast.telegraphMultiplier();
float slowCycle = EnemyArchetype.MELEE.attackCooldown * slow.cooldownMultiplier()
+ EnemyArchetype.MELEE.telegraphDuration * slow.telegraphMultiplier();
assertTrue(fastCycle < slowCycle);
```

## File: src/test/java/com/deadlinezero/game/ai/BiomeEnemyAttackPatternTest.java
```java
public final class BiomeEnemyAttackPatternTest {
@AfterEach void resetStage() { RunStageContext.begin(1); }
⋮----
@Test public void cinderGunnerUsesExplosiveBurstInsteadOfSingleShot() {
RunStageContext.begin(10);
var p = EnemyPatternCatalog.ranged(Enemy.Variant.NORMAL);
assertEquals(3, p.shots());
assertEquals(6.5f, p.spreadDegrees(), .001f);
assertTrue(p.explosive());
assertEquals(1.15f, p.explosionRadius(), .001f);
assertTrue(p.shots() * p.damageMultiplier() <= 1.05f);
⋮----
@Test public void staticSeerUsesFastWideZoningFan() {
RunStageContext.begin(20);
⋮----
assertEquals(5, p.shots());
assertEquals(15f, p.spreadDegrees(), .001f);
assertTrue(p.speedMultiplier() > 1.1f);
assertFalse(p.explosive());
assertEquals(1f, p.shots() * p.damageMultiplier(), .001f);
⋮----
@Test public void forgeHoundPounceIsFastRecoveryAndCompact() {
⋮----
var p = EnemyPatternCatalog.charge(Enemy.Type.RUNNER, Enemy.Variant.NORMAL);
assertTrue(p.recoveryMultiplier() < .7f);
assertTrue(p.impactRadius() < 1f);
assertTrue(p.impactDamageMultiplier() > 1.2f);
⋮----
@Test public void slagGuardRamIsHeavyAndWide() {
⋮----
var p = EnemyPatternCatalog.charge(Enemy.Type.SHIELDED, Enemy.Variant.NORMAL);
assertTrue(p.impactDamageMultiplier() > 1.7f);
assertTrue(p.impactRadius() > 1.3f);
assertTrue(p.knockbackStrength() > 1.4f);
assertTrue(p.recoveryMultiplier() > 1.2f);
⋮----
@Test public void quarantinePatternsRemainHistorical() {
RunStageContext.begin(1);
var ranged = EnemyPatternCatalog.ranged(Enemy.Variant.NORMAL);
assertEquals(1, ranged.shots());
assertEquals(1f, ranged.damageMultiplier(), .001f);
assertFalse(ranged.explosive());
var charge = EnemyPatternCatalog.charge(Enemy.Type.BRUTE, Enemy.Variant.NORMAL);
assertEquals(1.38f, charge.impactDamageMultiplier(), .001f);
assertEquals(1.12f, charge.impactRadius(), .001f);
```

## File: src/test/java/com/deadlinezero/game/ai/BossAffixRulesTest.java
```java
final class BossAffixRulesTest {
@AfterEach void cleanup() { RunStageContext.begin(1, 0, 0); }
⋮----
@Test void standardRunsHaveNoBossAffix() {
assertEquals(BossAffixRules.Affix.NONE, BossAffixRules.forRun(10, 0));
⋮----
@Test void ascendedRunsRotateDeterministically() {
BossAffixRules.Affix first = BossAffixRules.forRun(12, 7);
BossAffixRules.Affix second = BossAffixRules.forRun(12, 7);
assertEquals(first, second);
assertTrue(first != BossAffixRules.Affix.NONE);
⋮----
@Test void maximumThreatAlwaysUsesApocalypse() {
assertEquals(BossAffixRules.Affix.APOCALYPSE,
BossAffixRules.forRun(20, ThreatTierRules.MAX_TIER));
⋮----
@Test void affixRaisesBossStatsOnAscendedRun() {
RunStageContext.begin(12, 4, 0);
BossVariantStats.Stats normal = BossVariantStats.forStage(12, 1000f, 1f, 20f);
RunStageContext.begin(12, 4, 20);
BossVariantStats.Stats ascended = BossVariantStats.forStage(12, 1000f, 1f, 20f);
assertTrue(ascended.hp() > normal.hp());
assertTrue(ascended.speed() > normal.speed());
assertTrue(ascended.damage() > normal.damage());
⋮----
@Test void apocalypseIncreasesBossActionPressure() {
RunStageContext.begin(20, 0, 20);
BossCombatRuntime runtime = new BossCombatRuntime(BossIdentity.ALPHA);
assertEquals(BossAffixRules.Affix.APOCALYPSE, runtime.affix());
assertEquals(9, runtime.summonCount(3));
assertEquals(28, runtime.enrageShots());
assertEquals(3, runtime.enrageExplosiveEvery());
assertTrue(runtime.enrageExplosionRadius() > 2f);
assertTrue(runtime.enrageProjectileSpeed() > 8.2f);
```

## File: src/test/java/com/deadlinezero/game/ai/BossAttackPatternCatalogTest.java
```java
final class BossAttackPatternCatalogTest {
@Test void revenantIsDenserAndFasterInEachPhase() {
⋮----
var alpha = BossAttackPatternCatalog.forPhase(false, phase);
var revenant = BossAttackPatternCatalog.forPhase(true, phase);
assertTrue(revenant.shots() >= alpha.shots());
assertTrue(revenant.speedMultiplier() > alpha.speedMultiplier());
⋮----
@Test void phaseTwoRevenantIntroducesExplosives() {
var alpha = BossAttackPatternCatalog.forPhase(false, 2);
var revenant = BossAttackPatternCatalog.forPhase(true, 2);
assertEquals(0, alpha.explosiveEvery());
assertTrue(revenant.explosiveEvery() > 0);
assertTrue(revenant.explosionRadius() > 0f);
⋮----
@Test void phaseThreeRevenantEscalatesDensity() {
var phase2 = BossAttackPatternCatalog.forPhase(true, 2);
var phase3 = BossAttackPatternCatalog.forPhase(true, 3);
assertTrue(phase3.shots() > phase2.shots());
assertTrue(phase3.explosionRadius() >= phase2.explosionRadius());
⋮----
@Test void wardenTradesDensityAndSpeedForHeavyAreaDenial() {
⋮----
var alpha = BossAttackPatternCatalog.forPhase(BossIdentity.ALPHA, phase);
var warden = BossAttackPatternCatalog.forPhase(BossIdentity.WARDEN, phase);
assertTrue(warden.shots() <= alpha.shots());
assertTrue(warden.speedMultiplier() < alpha.speedMultiplier());
assertTrue(warden.damageMultiplier() > alpha.damageMultiplier());
⋮----
var phase2 = BossAttackPatternCatalog.forPhase(BossIdentity.WARDEN, 2);
var phase3 = BossAttackPatternCatalog.forPhase(BossIdentity.WARDEN, 3);
assertEquals(2, phase2.explosiveEvery());
⋮----
@Test void legacyBooleanRoutingStillUsesWardenOnWardenStages() {
RunStageContext.begin(7);
⋮----
var routed = BossAttackPatternCatalog.forPhase(false, 2);
var expected = BossAttackPatternCatalog.forPhase(BossIdentity.WARDEN, 2);
assertEquals(expected, routed);
⋮----
RunStageContext.begin(1);
⋮----
@Test void frostColossusEscalatesFromHeavyVolleyToAreaDenial() {
var p1 = BossAttackPatternCatalog.forPhase(BossIdentity.FROST_COLOSSUS, 1);
var p2 = BossAttackPatternCatalog.forPhase(BossIdentity.FROST_COLOSSUS, 2);
var p3 = BossAttackPatternCatalog.forPhase(BossIdentity.FROST_COLOSSUS, 3);
assertTrue(p1.damageMultiplier() > p2.damageMultiplier());
assertTrue(p2.radial());
assertTrue(p3.radial());
assertTrue(p3.shots() > p2.shots());
assertTrue(p3.explosionRadius() > p2.explosionRadius());
assertEquals(2, p3.explosiveEvery());
```

## File: src/test/java/com/deadlinezero/game/ai/BossCombatSecondaryTuningTest.java
```java
final class BossCombatSecondaryTuningTest {
@Test void revenantSummonsMoreMinions() {
BossCombatRuntime alpha = new BossCombatRuntime(false);
BossCombatRuntime revenant = new BossCombatRuntime(true);
assertTrue(revenant.summonCount(2) > alpha.summonCount(2));
assertTrue(revenant.summonCount(3) > alpha.summonCount(3));
⋮----
@Test void revenantEnrageIsDenserAndFaster() {
⋮----
assertTrue(revenant.enrageShots() > alpha.enrageShots());
assertTrue(revenant.enrageProjectileSpeed() > alpha.enrageProjectileSpeed());
assertTrue(revenant.enrageExplosiveEvery() < alpha.enrageExplosiveEvery());
assertTrue(revenant.enrageExplosionRadius() > alpha.enrageExplosionRadius());
⋮----
@Test void alphaKeepsExistingSecondaryValues() {
⋮----
assertEquals(3, alpha.summonCount(2));
assertEquals(6, alpha.summonCount(3));
assertEquals(20, alpha.enrageShots());
assertEquals(8.2f, alpha.enrageProjectileSpeed(), .0001f);
assertEquals(4, alpha.enrageExplosiveEvery());
assertEquals(2.0f, alpha.enrageExplosionRadius(), .0001f);
```

## File: src/test/java/com/deadlinezero/game/ai/BossCombatVariantTest.java
```java
final class BossCombatVariantTest {
@Test void revenantSummonsMoreMinions() {
var alpha = new BossCombatRuntime(false);
var revenant = new BossCombatRuntime(true);
assertTrue(revenant.summonCount(2) > alpha.summonCount(2));
assertTrue(revenant.summonCount(3) > alpha.summonCount(3));
⋮----
@Test void revenantEnrageIsDenserAndFaster() {
⋮----
assertTrue(revenant.enrageShots() > alpha.enrageShots());
assertTrue(revenant.enrageProjectileSpeed() > alpha.enrageProjectileSpeed());
assertTrue(revenant.enrageExplosiveEvery() < alpha.enrageExplosiveEvery());
assertTrue(revenant.enrageExplosionRadius() > alpha.enrageExplosionRadius());
⋮----
@Test void alphaKeepsLegacySecondaryPattern() {
⋮----
assertEquals(3, alpha.summonCount(2));
assertEquals(6, alpha.summonCount(3));
assertEquals(20, alpha.enrageShots());
assertEquals(8.2f, alpha.enrageProjectileSpeed(), .0001f);
⋮----
@Test void frostColossusUsesHeavyControlledSecondaryPattern() {
var frost = new BossCombatRuntime(BossIdentity.FROST_COLOSSUS);
assertEquals(FrostColossusBossProfile.PHASE2_SUMMON_COUNT, frost.summonCount(2));
assertEquals(FrostColossusBossProfile.PHASE3_SUMMON_COUNT, frost.summonCount(3));
assertEquals(FrostColossusBossProfile.ENRAGE_SHOTS, frost.enrageShots());
assertEquals(FrostColossusBossProfile.ENRAGE_PROJECTILE_SPEED, frost.enrageProjectileSpeed(), .0001f);
assertEquals(FrostColossusBossProfile.ENRAGE_EXPLOSIVE_EVERY, frost.enrageExplosiveEvery());
assertEquals(FrostColossusBossProfile.ENRAGE_EXPLOSION_RADIUS, frost.enrageExplosionRadius(), .0001f);
assertTrue(frost.frostColossus());
```

## File: src/test/java/com/deadlinezero/game/ai/BossIdentityTest.java
```java
final class BossIdentityTest {
@Test void preservesEarlyAlphaAndExistingRevenantStages() {
assertEquals(BossIdentity.ALPHA, BossIdentity.forStage(1));
assertEquals(BossIdentity.REVENANT, BossIdentity.forStage(4));
assertEquals(BossIdentity.ALPHA, BossIdentity.forStage(5));
assertEquals(BossIdentity.REVENANT, BossIdentity.forStage(6));
⋮----
@Test void introducesWardenOnLateOddCycleWithoutCollidingWithRevenant() {
assertEquals(BossIdentity.WARDEN, BossIdentity.forStage(7));
assertEquals(BossIdentity.REVENANT, BossIdentity.forStage(8));
assertEquals(BossIdentity.ALPHA, BossIdentity.forStage(9));
assertEquals(BossIdentity.REVENANT, BossIdentity.forStage(10));
assertEquals(BossIdentity.WARDEN, BossIdentity.forStage(11));
⋮----
@Test void introducesFrostColossusAtCryogenicMilestones() {
assertEquals(6, BossIdentity.values().length);
assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(35));
assertEquals(BossIdentity.FROST_COLOSSUS, BossIdentity.forStage(40));
assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(45));
assertEquals(BossIdentity.FROST_COLOSSUS, BossIdentity.forStage(50));
assertNotEquals(BossIdentity.FROST_COLOSSUS, BossIdentity.forStage(39));
```

## File: src/test/java/com/deadlinezero/game/ai/BossVariantStatsContractTest.java
```java
final class BossVariantStatsContractTest {
@Test void alphaKeepsBaseStats() {
var s = BossVariantStats.forStage(3, 2200f, 1.35f, 24f);
assertEquals(2200f, s.hp(), .001f);
assertEquals(1.35f, s.speed(), .001f);
assertEquals(24f, s.damage(), .001f);
⋮----
@Test void revenantTradesHpForPressure() {
var s = BossVariantStats.forStage(4, 2200f, 1.35f, 24f);
assertTrue(s.hp() < 2200f);
assertTrue(s.speed() > 1.35f);
assertTrue(s.damage() > 24f);
⋮----
@Test void frostColossusIsSlowDurableAndHeavy() {
var s = BossVariantStats.forIdentity(BossIdentity.FROST_COLOSSUS, 2200f, 1.35f, 24f);
assertTrue(s.hp() > 3000f);
assertTrue(s.speed() < 1.10f);
assertTrue(s.damage() > 27f);
```

## File: src/test/java/com/deadlinezero/game/ai/EnemyPatternCatalogTest.java
```java
public final class EnemyPatternCatalogTest {
@Test public void rangedVariantsHaveDistinctThreatProfiles() {
var normal = EnemyPatternCatalog.ranged(Enemy.Variant.NORMAL);
var swift = EnemyPatternCatalog.ranged(Enemy.Variant.SWIFT);
var armored = EnemyPatternCatalog.ranged(Enemy.Variant.ARMORED);
var feral = EnemyPatternCatalog.ranged(Enemy.Variant.FERAL);
⋮----
assertTrue(swift.shots() > normal.shots());
assertTrue(feral.shots() > swift.shots());
assertTrue(armored.explosive());
assertFalse(normal.explosive());
assertTrue(armored.damageMultiplier() > normal.damageMultiplier());
⋮----
@Test public void eliteChargeIsMoreThreateningThanBruteCharge() {
var brute = EnemyPatternCatalog.charge(Enemy.Type.BRUTE, Enemy.Variant.NORMAL);
var elite = EnemyPatternCatalog.charge(Enemy.Type.ELITE, Enemy.Variant.NORMAL);
assertTrue(elite.impactDamageMultiplier() > brute.impactDamageMultiplier());
assertTrue(elite.impactRadius() > brute.impactRadius());
assertTrue(elite.knockbackStrength() > brute.knockbackStrength());
⋮----
@Test public void feralChargeTradesRecoveryForAggression() {
var normal = EnemyPatternCatalog.charge(Enemy.Type.BRUTE, Enemy.Variant.NORMAL);
var feral = EnemyPatternCatalog.charge(Enemy.Type.BRUTE, Enemy.Variant.FERAL);
assertTrue(feral.impactDamageMultiplier() > normal.impactDamageMultiplier());
assertTrue(feral.recoveryMultiplier() < normal.recoveryMultiplier());
```

## File: src/test/java/com/deadlinezero/game/ai/EnemyVariantBalanceTest.java
```java
final class EnemyVariantBalanceTest {
@Test void allChampionRangedPatternsStayInsideProductionBounds() {
for (Enemy.Variant variant : Enemy.Variant.values()) {
⋮----
var p = EnemyPatternCatalog.ranged(variant);
assertTrue(p.shots() >= 1 && p.shots() <= 8, variant + " shots");
assertTrue(p.spreadDegrees() >= 0f && p.spreadDegrees() <= 20f, variant + " spread");
assertTrue(p.speedMultiplier() >= .80f && p.speedMultiplier() <= 1.35f, variant + " speed");
assertTrue(p.damageMultiplier() >= .10f && p.damageMultiplier() <= 1.55f, variant + " damage");
if (p.explosive()) assertTrue(p.explosionRadius() >= 1f && p.explosionRadius() <= 2f, variant + " radius");
⋮----
@Test void allChampionChargePatternsStayInsideProductionBounds() {
⋮----
var p = EnemyPatternCatalog.charge(Enemy.Type.ELITE, variant);
assertTrue(p.impactDamageMultiplier() >= .75f && p.impactDamageMultiplier() <= 3f, variant + " damage");
assertTrue(p.impactRadius() >= .8f && p.impactRadius() <= 2f, variant + " radius");
assertTrue(p.knockbackStrength() >= .7f && p.knockbackStrength() <= 2.5f, variant + " knockback");
assertTrue(p.recoveryMultiplier() >= .45f && p.recoveryMultiplier() <= 1.6f, variant + " recovery");
```

## File: src/test/java/com/deadlinezero/game/ai/FrostColossusBossProfileTest.java
```java
final class FrostColossusBossProfileTest {
@Test void tuningRemainsHeavyAndBounded() {
assertTrue(FrostColossusBossProfile.HP_MULTIPLIER >= 1.30f && FrostColossusBossProfile.HP_MULTIPLIER <= 1.55f);
assertTrue(FrostColossusBossProfile.SPEED_MULTIPLIER >= .70f && FrostColossusBossProfile.SPEED_MULTIPLIER < 1f);
assertTrue(FrostColossusBossProfile.DAMAGE_MULTIPLIER >= 1.10f && FrostColossusBossProfile.DAMAGE_MULTIPLIER <= 1.30f);
assertTrue(FrostColossusBossProfile.PHASE3_CHARGE_COOLDOWN < FrostColossusBossProfile.PHASE2_CHARGE_COOLDOWN);
assertTrue(FrostColossusBossProfile.PHASE3_SUMMON_COOLDOWN < FrostColossusBossProfile.PHASE2_SUMMON_COOLDOWN);
assertTrue(FrostColossusBossProfile.PHASE3_SUMMON_COUNT > FrostColossusBossProfile.PHASE2_SUMMON_COUNT);
assertTrue(FrostColossusBossProfile.ENRAGE_EXPLOSION_RADIUS >= 2.5f);
```

## File: src/test/java/com/deadlinezero/game/ai/HarvesterBossProfileTest.java
```java
final class HarvesterBossProfileTest {
@Test void entersLateGameRotationDeterministically() {
assertEquals(BossIdentity.HARVESTER, BossIdentity.forStage(12));
assertEquals(BossIdentity.HARVESTER, BossIdentity.forStage(17));
assertEquals(BossIdentity.HARVESTER, BossIdentity.forStage(22));
⋮----
@Test void identityHasPressureOrientedStats() {
BossVariantStats.Stats stats = BossVariantStats.forIdentity(BossIdentity.HARVESTER, 100f, 2f, 10f);
assertEquals(116f, stats.hp(), .001f);
assertEquals(2.16f, stats.speed(), .001f);
assertEquals(11.4f, stats.damage(), .001f);
⋮----
@Test void phaseThreeUsesDenseFastProjectilePattern() {
BossAttackPatternCatalog.Pattern pattern = BossAttackPatternCatalog.forPhase(BossIdentity.HARVESTER, 3);
assertEquals(24, pattern.shots());
assertTrue(pattern.radial());
assertTrue(pattern.speedMultiplier() >= 1.2f);
assertEquals(4, pattern.explosiveEvery());
⋮----
@Test void runtimeSummonsMoreMinionsThanWarden() {
BossCombatRuntime harvester = new BossCombatRuntime(BossIdentity.HARVESTER);
BossCombatRuntime warden = new BossCombatRuntime(BossIdentity.WARDEN);
assertTrue(harvester.summonCount(2) > warden.summonCount(2));
assertTrue(harvester.summonCount(3) > warden.summonCount(3));
assertTrue(harvester.enrageShots() > warden.enrageShots());
```

## File: src/test/java/com/deadlinezero/game/ai/HarvesterSummonTelegraphTest.java
```java
final class HarvesterSummonTelegraphTest {
@Test void exposesDeterministicWarningBeforeSummon() {
BossCombatRuntime runtime = new BossCombatRuntime(BossIdentity.HARVESTER);
assertFalse(runtime.summonTelegraphing(2));
runtime.update(7.40f, 2);
assertTrue(runtime.summonTelegraphing(2));
float progress = runtime.summonTelegraphProgress(2);
assertTrue(progress > 0f && progress < 1f);
runtime.update(.61f, 2);
⋮----
assertTrue(runtime.consumeSummon(2));
⋮----
@Test void phaseOneNeverTelegraphsSummons() {
⋮----
runtime.update(8f, 1);
assertFalse(runtime.summonTelegraphing(1));
assertTrue(runtime.summonTelegraphProgress(1) == 0f);
⋮----
@Test void warningWindowIsLongEnoughToRead() {
assertTrue(BossCombatRuntime.summonTelegraphSeconds() >= .65f);
assertTrue(BossCombatRuntime.summonTelegraphSeconds() <= .90f);
```

## File: src/test/java/com/deadlinezero/game/ai/LeaperProfileTest.java
```java
final class LeaperProfileTest {
@Test void leapRangeIsReadableAndBounded() {
assertFalse(LeaperProfile.inLeapRange(1.5f));
assertTrue(LeaperProfile.inLeapRange(2.2f));
assertTrue(LeaperProfile.inLeapRange(6f));
assertTrue(LeaperProfile.inLeapRange(8.6f));
assertFalse(LeaperProfile.inLeapRange(9f));
⋮----
@Test void leapTimingRemainsTelegraphable() {
assertTrue(LeaperProfile.LEAP_WINDUP >= .16f);
assertTrue(LeaperProfile.LEAP_IMPACT_WINDOW <= .25f);
assertTrue(LeaperProfile.LEAP_COOLDOWN_MIN >= 1.25f);
assertTrue(LeaperProfile.LEAP_COOLDOWN_MAX > LeaperProfile.LEAP_COOLDOWN_MIN);
⋮----
@Test void baseProfileOccupiesFastSkirmisherBand() {
assertTrue(LeaperProfile.BASE_HP >= 35f && LeaperProfile.BASE_HP <= 70f);
assertTrue(LeaperProfile.BASE_SPEED >= 2.8f && LeaperProfile.BASE_SPEED <= 3.8f);
assertTrue(LeaperProfile.LEAP_IMPULSE > LeaperProfile.BASE_SPEED);
```

## File: src/test/java/com/deadlinezero/game/ai/LeaperRegistryTest.java
```java
final class LeaperRegistryTest {
⋮----
void registryMarksOnlyRegisteredEnemyInstances() {
LeaperRegistry registry = new LeaperRegistry();
Enemy a = new Enemy(Enemy.Type.RUNNER, 0f, 0f, 10f, 2f, .3f, 1f, 1);
Enemy b = new Enemy(Enemy.Type.RUNNER, 0f, 0f, 10f, 2f, .3f, 1f, 1);
⋮----
assertFalse(registry.contains(a));
assertFalse(registry.contains(b));
registry.register(a);
assertTrue(registry.contains(a));
⋮----
void nullRegistrationIsIgnoredSafely() {
⋮----
registry.register(null);
assertFalse(registry.contains(null));
```

## File: src/test/java/com/deadlinezero/game/ai/LeaperRuntimeTest.java
```java
final class LeaperRuntimeTest {
@Test void registeredEnemyCanTelegraphAndImpactOnce() {
LeaperRuntime runtime = new LeaperRuntime();
Enemy enemy = new Enemy(Enemy.Type.RUNNER, 0f, 0f, 46f, 3.15f, .38f, 12f, 10);
runtime.register(enemy);
assertTrue(runtime.contains(enemy));
⋮----
for (int i = 0; i < 240 && !runtime.telegraphing(enemy); i++) {
runtime.update(enemy, .02f, 4f, 1f, 0f);
⋮----
assertTrue(runtime.telegraphing(enemy));
⋮----
for (int i = 0; i < 20; i++) runtime.update(enemy, .02f, 4f, 1f, 0f);
assertTrue(enemy.impulse.x > 0f);
assertTrue(runtime.consumeImpact(enemy));
assertFalse(runtime.consumeImpact(enemy));
⋮----
@Test void unregisteredEnemyNeverActivates() {
⋮----
for (int i = 0; i < 200; i++) runtime.update(enemy, .02f, 4f, 1f, 0f);
assertFalse(runtime.contains(enemy));
assertFalse(runtime.telegraphing(enemy));
```

## File: src/test/java/com/deadlinezero/game/ai/NullArchonBossProfileTest.java
```java
final class NullArchonBossProfileTest {
@Test void nullArchonOwnsFiveStageNullMilestones() {
assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(20));
assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(25));
assertEquals(BossIdentity.NULL_ARCHON, BossIdentity.forStage(30));
assertTrue(BossIdentity.forStage(19) != BossIdentity.NULL_ARCHON);
assertTrue(BossIdentity.forStage(21) != BossIdentity.NULL_ARCHON);
⋮----
@Test void nullArchonHasFastDensePhaseThreePattern() {
BossAttackPatternCatalog.Pattern p = BossAttackPatternCatalog.forPhase(BossIdentity.NULL_ARCHON, 3);
assertEquals(30, p.shots());
assertEquals(12f, p.spreadDegrees(), .0001f);
assertTrue(p.speedMultiplier() > 1.20f);
assertEquals(5, p.explosiveEvery());
assertTrue(p.explosionRadius() >= 1.70f);
assertTrue(p.radial());
⋮----
@Test void nullArchonCombatCadenceIsDistinctAndAggressive() {
BossCombatRuntime runtime = new BossCombatRuntime(BossIdentity.NULL_ARCHON);
assertTrue(runtime.nullArchon());
assertEquals(NullArchonBossProfile.PHASE3_SUMMON_COUNT, runtime.summonCount(3));
assertEquals(NullArchonBossProfile.ENRAGE_SHOTS, runtime.enrageShots());
assertEquals(NullArchonBossProfile.ENRAGE_PROJECTILE_SPEED, runtime.enrageProjectileSpeed(), .0001f);
assertEquals(NullArchonBossProfile.ENRAGE_EXPLOSIVE_EVERY, runtime.enrageExplosiveEvery());
assertEquals(NullArchonBossProfile.ENRAGE_EXPLOSION_RADIUS, runtime.enrageExplosionRadius(), .0001f);
⋮----
@Test void nullArchonStatsRemainInsideLateGameBossEnvelope() {
assertTrue(NullArchonBossProfile.HP_MULTIPLIER >= 1.15f && NullArchonBossProfile.HP_MULTIPLIER <= 1.30f);
assertTrue(NullArchonBossProfile.SPEED_MULTIPLIER >= 1.05f && NullArchonBossProfile.SPEED_MULTIPLIER <= 1.18f);
assertTrue(NullArchonBossProfile.DAMAGE_MULTIPLIER >= 1.10f && NullArchonBossProfile.DAMAGE_MULTIPLIER <= 1.22f);
```

## File: src/test/java/com/deadlinezero/game/ai/RevenantBossProfileTest.java
```java
final class RevenantBossProfileTest {
@Test void alternatesAfterStageFour() {
assertFalse(RevenantBossProfile.useForStage(3));
assertTrue(RevenantBossProfile.useForStage(4));
assertFalse(RevenantBossProfile.useForStage(5));
assertTrue(RevenantBossProfile.useForStage(6));
⋮----
@Test void tradesDurabilityForPressure() {
assertTrue(RevenantBossProfile.HP_MULTIPLIER < 1f);
assertTrue(RevenantBossProfile.SPEED_MULTIPLIER > 1f);
assertTrue(RevenantBossProfile.DAMAGE_MULTIPLIER > 1f);
assertTrue(RevenantBossProfile.PHASE3_CHARGE_COOLDOWN < RevenantBossProfile.PHASE2_CHARGE_COOLDOWN);
assertTrue(RevenantBossProfile.PHASE3_SUMMON_COOLDOWN < RevenantBossProfile.PHASE2_SUMMON_COOLDOWN);
⋮----
@Test void revenantRecoversChargeEarlier() {
BossCombatRuntime standard = new BossCombatRuntime(false);
BossCombatRuntime revenant = new BossCombatRuntime(true);
standard.update(5f, 2);
revenant.update(5f, 2);
assertTrue(standard.consumeCharge(2));
assertTrue(revenant.consumeCharge(2));
standard.update(3.5f, 2);
revenant.update(3.5f, 2);
assertFalse(standard.consumeCharge(2));
⋮----
@Test void revenantSummonsMoreMinionsInLaterPhases() {
⋮----
assertEquals(3, standard.summonCount(2));
assertEquals(6, standard.summonCount(3));
assertEquals(RevenantBossProfile.PHASE2_SUMMON_COUNT, revenant.summonCount(2));
assertEquals(RevenantBossProfile.PHASE3_SUMMON_COUNT, revenant.summonCount(3));
assertTrue(revenant.summonCount(2) > standard.summonCount(2));
assertTrue(revenant.summonCount(3) > standard.summonCount(3));
⋮----
@Test void revenantEnrageIsDenserAndFaster() {
⋮----
assertTrue(revenant.enrageShots() > standard.enrageShots());
assertTrue(revenant.enrageProjectileSpeed() > standard.enrageProjectileSpeed());
assertTrue(revenant.enrageExplosiveEvery() < standard.enrageExplosiveEvery());
assertTrue(revenant.enrageExplosionRadius() > standard.enrageExplosionRadius());
assertEquals(RevenantBossProfile.ENRAGE_SHOTS, revenant.enrageShots());
assertEquals(RevenantBossProfile.ENRAGE_PROJECTILE_SPEED, revenant.enrageProjectileSpeed());
```

## File: src/test/java/com/deadlinezero/game/ai/WardenBossProfileTest.java
```java
final class WardenBossProfileTest {
@Test void wardenIsHeavySlowAndDangerous() {
assertTrue(WardenBossProfile.HP_MULTIPLIER > 1f);
assertTrue(WardenBossProfile.SPEED_MULTIPLIER < 1f);
assertTrue(WardenBossProfile.DAMAGE_MULTIPLIER > 1f);
⋮----
@Test void stageSevenReceivesWardenStats() {
var base = BossVariantStats.forStage(1, 100f, 10f, 20f);
var warden = BossVariantStats.forStage(7, 100f, 10f, 20f);
assertTrue(warden.hp() > base.hp());
assertTrue(warden.speed() < base.speed());
assertTrue(warden.damage() > base.damage());
⋮----
@Test void runtimeUsesLowDensityHeavyPressure() {
BossCombatRuntime runtime = new BossCombatRuntime(BossIdentity.WARDEN);
assertEquals(2, runtime.summonCount(2));
assertEquals(4, runtime.summonCount(3));
assertEquals(12, runtime.enrageShots());
assertEquals(2, runtime.enrageExplosiveEvery());
assertTrue(runtime.enrageExplosionRadius() > 2f);
assertTrue(runtime.warden());
```

## File: src/test/java/com/deadlinezero/game/audio/AudioCueLimiterTest.java
```java
final class AudioCueLimiterTest {
@Test void firstCueAlwaysPasses() {
AudioCueLimiter limiter = new AudioCueLimiter();
assertTrue(limiter.allow(AudioDirector.Cue.SHOT, 1_000_000L));
⋮----
@Test void repeatedCueInsideWindowIsRejected() {
⋮----
assertTrue(limiter.allow(AudioDirector.Cue.HIT, now));
assertFalse(limiter.allow(AudioDirector.Cue.HIT, now + 20_000_000L));
⋮----
@Test void cuePassesOnceItsWindowExpires() {
⋮----
assertTrue(limiter.allow(AudioDirector.Cue.CRIT, now));
assertTrue(limiter.allow(AudioDirector.Cue.CRIT,
now + AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.CRIT)));
⋮----
@Test void cuesAreRateLimitedIndependently() {
⋮----
assertTrue(limiter.allow(AudioDirector.Cue.SHOT, now));
assertTrue(limiter.allow(AudioDirector.Cue.KILL, now));
assertFalse(limiter.allow(AudioDirector.Cue.SHOT, now + 1_000_000L));
⋮----
@Test void bossPhaseCueCannotStackDuringTransitionBurst() {
⋮----
assertTrue(limiter.allow(AudioDirector.Cue.BOSS_PHASE, now));
assertFalse(limiter.allow(AudioDirector.Cue.BOSS_PHASE, now + 300_000_000L));
assertTrue(limiter.allow(AudioDirector.Cue.BOSS_PHASE,
now + AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.BOSS_PHASE)));
⋮----
@Test void sentinelBlocksAreRateLimitedDuringProjectileBursts() {
⋮----
assertTrue(limiter.allow(AudioDirector.Cue.SENTINEL_BLOCK, now));
assertFalse(limiter.allow(AudioDirector.Cue.SENTINEL_BLOCK, now + 100_000_000L));
assertTrue(limiter.allow(AudioDirector.Cue.SENTINEL_BLOCK,
now + AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.SENTINEL_BLOCK)));
⋮----
@Test void resetRestoresImmediatePlayback() {
⋮----
assertTrue(limiter.allow(AudioDirector.Cue.UI_SELECT, now));
assertFalse(limiter.allow(AudioDirector.Cue.UI_SELECT, now + 1_000_000L));
limiter.reset();
assertTrue(limiter.allow(AudioDirector.Cue.UI_SELECT, now + 1_000_000L));
```

## File: src/test/java/com/deadlinezero/game/audio/AudioDirectorFallbackTest.java
```java
final class AudioDirectorFallbackTest {
@Test void bossPhaseFallsBackToBossHitWhenDedicatedAssetIsMissing() {
assertEquals(AudioDirector.Cue.BOSS_HIT, AudioDirector.fallbackCue(AudioDirector.Cue.BOSS_PHASE));
⋮----
@Test void protocolProcFallsBackToCritWithoutDedicatedAsset() {
assertEquals(AudioDirector.Cue.CRIT, AudioDirector.fallbackCue(AudioDirector.Cue.PROTOCOL_PROC));
⋮----
@Test void sentinelBlockFallsBackToDashWithoutDedicatedAsset() {
assertEquals(AudioDirector.Cue.DASH, AudioDirector.fallbackCue(AudioDirector.Cue.SENTINEL_BLOCK));
⋮----
@Test void ordinaryCuesDoNotUnexpectedlyAlias() {
assertNull(AudioDirector.fallbackCue(AudioDirector.Cue.SHOT));
assertNull(AudioDirector.fallbackCue(AudioDirector.Cue.BOSS_KILL));
```

## File: src/test/java/com/deadlinezero/game/audio/AudioDirectorVolumeTest.java
```java
final class AudioDirectorVolumeTest {
@Test void clampsFiniteVolumesToUnitRange() {
assertEquals(0f, AudioDirector.normalizeVolume(-0.5f));
assertEquals(0.35f, AudioDirector.normalizeVolume(0.35f));
assertEquals(1f, AudioDirector.normalizeVolume(1.8f));
⋮----
@Test void rejectsNonFiniteVolumesToSafeSilence() {
assertEquals(0f, AudioDirector.normalizeVolume(Float.NaN));
assertEquals(0f, AudioDirector.normalizeVolume(Float.POSITIVE_INFINITY));
assertEquals(0f, AudioDirector.normalizeVolume(Float.NEGATIVE_INFINITY));
```

## File: src/test/java/com/deadlinezero/game/audio/FoundryHazardAudioContractTest.java
```java
final class FoundryHazardAudioContractTest {
@Test void dedicatedFoundryCuesHaveSafeFallbacks() {
assertEquals(AudioDirector.Cue.BOSS_HIT, AudioDirector.fallbackCue(AudioDirector.Cue.FOUNDRY_LAVA));
assertEquals(AudioDirector.Cue.DASH, AudioDirector.fallbackCue(AudioDirector.Cue.FOUNDRY_STEAM));
assertEquals(AudioDirector.Cue.CRIT, AudioDirector.fallbackCue(AudioDirector.Cue.FOUNDRY_HEAT));
⋮----
@Test void foundryCuesAreRateLimitedAsEnvironmentalEvents() {
assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.FOUNDRY_LAVA) >= 400_000_000L);
assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.FOUNDRY_STEAM) >= 250_000_000L);
assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.FOUNDRY_HEAT) >= 350_000_000L);
```

## File: src/test/java/com/deadlinezero/game/audio/MusicProfileSelectorTest.java
```java
final class MusicProfileSelectorTest {
@Test void stagesMapToStableIntensityBands() {
assertEquals(MusicProfileSelector.Profile.SURVIVAL, MusicProfileSelector.forStage(-3));
assertEquals(MusicProfileSelector.Profile.SURVIVAL, MusicProfileSelector.forStage(1));
assertEquals(MusicProfileSelector.Profile.SURVIVAL, MusicProfileSelector.forStage(3));
assertEquals(MusicProfileSelector.Profile.PRESSURE, MusicProfileSelector.forStage(4));
assertEquals(MusicProfileSelector.Profile.PRESSURE, MusicProfileSelector.forStage(6));
assertEquals(MusicProfileSelector.Profile.APEX, MusicProfileSelector.forStage(7));
assertEquals(MusicProfileSelector.Profile.APEX, MusicProfileSelector.forStage(99));
⋮----
@Test void profilesExposeDeterministicAssetPaths() {
assertEquals("audio/music/combat.ogg", MusicProfileSelector.assetPath(MusicProfileSelector.Profile.SURVIVAL));
assertEquals("audio/music/combat_pressure.ogg", MusicProfileSelector.assetPath(MusicProfileSelector.Profile.PRESSURE));
assertEquals("audio/music/combat_apex.ogg", MusicProfileSelector.assetPath(MusicProfileSelector.Profile.APEX));
assertEquals("audio/music/combat.ogg", MusicProfileSelector.assetPath(null));
```

## File: src/test/java/com/deadlinezero/game/audio/NullHazardAudioContractTest.java
```java
final class NullHazardAudioContractTest {
@Test void nullHazardCuesHaveResilientFallbackChains() {
assertEquals(AudioDirector.Cue.SINGULARITY, AudioDirector.fallbackCue(AudioDirector.Cue.NULL_RIFT));
assertEquals(AudioDirector.Cue.CRIT, AudioDirector.fallbackCue(AudioDirector.Cue.NULL_STATIC));
assertEquals(AudioDirector.Cue.BOSS_PHASE, AudioDirector.fallbackCue(AudioDirector.Cue.NULL_BEAM));
⋮----
@Test void nullHazardCuesAreRateLimitedAsEnvironmentalEvents() {
assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.NULL_RIFT) >= 400_000_000L);
assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.NULL_STATIC) >= 250_000_000L);
assertTrue(AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.NULL_BEAM) >= 350_000_000L);
```

## File: src/test/java/com/deadlinezero/game/audio/WeaponSignatureAudioContractTest.java
```java
final class WeaponSignatureAudioContractTest {
@Test void authoredSignatureCuesHaveSafeFallbacks() {
assertEquals(AudioDirector.Cue.CRIT, AudioDirector.fallbackCue(AudioDirector.Cue.ION_OVERCHARGE));
assertEquals(AudioDirector.Cue.BOSS_HIT, AudioDirector.fallbackCue(AudioDirector.Cue.CINDER_OVERHEAT));
⋮----
@Test void signatureCuesAreRateLimitedIndependently() {
long ion = AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.ION_OVERCHARGE);
long cinder = AudioCueLimiter.minIntervalNanos(AudioDirector.Cue.CINDER_OVERHEAT);
assertTrue(ion >= 100_000_000L);
assertTrue(cinder >= 220_000_000L);
assertTrue(cinder > ion);
```

## File: src/test/java/com/deadlinezero/game/combat/EndgameWeaponArchetypeTest.java
```java
final class EndgameWeaponArchetypeTest {
@Test void ionNeedleIsPrecisionShockPiercer() {
⋮----
assertEquals(DamageElement.SHOCK, weapon.element);
assertTrue(weapon.fireInterval < .10f);
assertTrue(weapon.critChance >= .20f);
assertTrue(weapon.penetration >= 2);
assertTrue(weapon.spreadDegrees < 1f);
⋮----
@Test void cinderCannonIsSlowHeavyFireWeapon() {
⋮----
assertEquals(DamageElement.FIRE, weapon.element);
assertTrue(weapon.damage >= 80f);
assertTrue(weapon.fireInterval >= 1f);
assertTrue(weapon.knockback >= 4f);
assertTrue(weapon.penetration >= 1);
⋮----
@Test void newWeaponsStayInsideGlobalPaperDpsBand() {
assertTrue(WeaponCatalog.paperDps(WeaponCatalog.ION_NEEDLE) <= 230f);
assertTrue(WeaponCatalog.paperDps(WeaponCatalog.ION_NEEDLE) >= 45f);
assertTrue(WeaponCatalog.paperDps(WeaponCatalog.CINDER_CANNON) <= 230f);
assertTrue(WeaponCatalog.paperDps(WeaponCatalog.CINDER_CANNON) >= 45f);
```

## File: src/test/java/com/deadlinezero/game/combat/WeaponCatalogTest.java
```java
final class WeaponCatalogTest {
⋮----
void rosterHasDistinctStableIds() {
WeaponDefinition[] all = WeaponCatalog.all();
assertTrue(all.length >= 12);
⋮----
assertNotNull(weapon);
assertTrue(ids.add(weapon.id), "duplicate weapon id: " + weapon.id);
assertEquals(weapon, WeaponCatalog.byId(weapon.id));
⋮----
void everyWeaponHasSafeRuntimeParameters() {
for (WeaponDefinition weapon : WeaponCatalog.all()) {
assertTrue(weapon.damage > 0f);
assertTrue(weapon.fireInterval >= .04f);
assertTrue(weapon.projectileSpeed >= 10f);
assertTrue(weapon.projectileCount >= 1 && weapon.projectileCount <= 12);
assertTrue(weapon.spreadDegrees >= 0f && weapon.spreadDegrees <= 16f);
assertTrue(weapon.critChance >= 0f && weapon.critChance <= .35f);
assertTrue(weapon.critMultiplier >= 1f && weapon.critMultiplier <= 3f);
assertTrue(weapon.penetration >= 0 && weapon.penetration <= 6);
assertTrue(weapon.knockback >= 0f && weapon.knockback <= 5f);
assertNotNull(weapon.element);
⋮----
void rawPaperDpsStaysWithinIntentionalBand() {
⋮----
float dps = WeaponCatalog.paperDps(weapon);
assertTrue(dps >= 45f, weapon.id + " paper DPS too low: " + dps);
assertTrue(dps <= 230f, weapon.id + " paper DPS too high: " + dps);
⋮----
void elementalRosterCoversFireFrostAndShock() {
⋮----
assertTrue(fire && frost && shock);
⋮----
void unknownIdsFallBackSafelyToStarterWeapon() {
assertEquals(WeaponCatalog.AR9, WeaponCatalog.byId(null));
assertEquals(WeaponCatalog.AR9, WeaponCatalog.byId("missing_weapon"));
```

## File: src/test/java/com/deadlinezero/game/combat/WeaponSignatureBalanceTest.java
```java
final class WeaponSignatureBalanceTest {
@Test void ionNeedleSignatureStaysInsideControlledAverageDamageBudget() {
assertAverageDamageMultiplier(WeaponCatalog.ION_NEEDLE, 1.18f, 1.26f);
⋮----
@Test void cinderThermalCycleStaysInsideControlledAverageDamageBudget() {
assertAverageDamageMultiplier(WeaponCatalog.CINDER_CANNON, 1.12f, 1.16f);
⋮----
@Test void newEndgameSignaturesStayInsideControlledAverageDamageBudget() {
assertAverageDamageMultiplier(WeaponCatalog.TEMPEST_BURST, 1.02f, 1.05f);
assertAverageDamageMultiplier(WeaponCatalog.WHITEOUT_SHARD, 1.01f, 1.04f);
assertAverageDamageMultiplier(WeaponCatalog.PHOENIX_REPEATER, 1.05f, 1.08f);
⋮----
@Test void nonSignatureWeaponsNeverReceiveSignaturePower() {
Set<String> signatureIds = Set.of(
⋮----
for (WeaponDefinition weapon : WeaponCatalog.all()) {
if (signatureIds.contains(weapon.id)) continue;
WeaponSignatureRuntime.begin(weapon);
⋮----
var modifier = WeaponSignatureRuntime.consumeShot(false);
assertTrue(!modifier.active() && modifier.damageMultiplier() == 1f,
⋮----
private static void assertAverageDamageMultiplier(WeaponDefinition weapon, float min, float max) {
⋮----
for (int i = 0; i < 120; i++) totalMultiplier += WeaponSignatureRuntime.consumeShot(false).damageMultiplier();
⋮----
assertTrue(average >= min && average <= max,
```

## File: src/test/java/com/deadlinezero/game/combat/WeaponSignatureRuntimeTest.java
```java
final class WeaponSignatureRuntimeTest {
@BeforeEach void resetCore() { SingularityCoreRuntime.begin(false); }
⋮----
@Test void ionNeedleOverchargesExactlyEveryFifthProjectile() {
WeaponSignatureRuntime.begin(WeaponCatalog.ION_NEEDLE);
⋮----
var mark = WeaponSignatureRuntime.consumeShot(false);
assertEquals(i % 5 == 0, mark.active());
⋮----
assertEquals(WeaponSignatureRuntime.Kind.ION_OVERCHARGE, mark.kind());
assertTrue(mark.forceCritical());
assertEquals(1, mark.penetrationBonus());
assertTrue(mark.damageMultiplier() >= 2f);
} else assertEquals(WeaponSignatureRuntime.Kind.NONE, mark.kind());
⋮----
@Test void cinderCannonThermalCycleTriggersEveryFourthShell() {
WeaponSignatureRuntime.begin(WeaponCatalog.CINDER_CANNON);
⋮----
assertEquals(i % 4 == 0, mark.active());
⋮----
assertEquals(WeaponSignatureRuntime.Kind.CINDER_OVERHEAT, mark.kind());
assertFalse(mark.forceCritical());
assertEquals(1.55f, mark.damageMultiplier(), .0001f);
assertEquals(.17f, mark.radius(), .0001f);
⋮----
@Test void tempestBurstSurgesOnEverySecondThreeShotBurst() {
WeaponSignatureRuntime.begin(WeaponCatalog.TEMPEST_BURST);
⋮----
assertEquals(i % 6 == 0, mark.active());
⋮----
assertEquals(WeaponSignatureRuntime.Kind.TEMPEST_SURGE, mark.kind());
assertEquals(2, mark.penetrationBonus());
assertEquals(1.20f, mark.damageMultiplier(), .0001f);
⋮----
@Test void whiteoutShardAddsControlShardEverySecondVolley() {
WeaponSignatureRuntime.begin(WeaponCatalog.WHITEOUT_SHARD);
⋮----
assertEquals(i % 8 == 0, mark.active());
⋮----
assertEquals(WeaponSignatureRuntime.Kind.WHITEOUT_SHATTER, mark.kind());
⋮----
assertEquals(1.38f, mark.knockbackMultiplier(), .0001f);
⋮----
@Test void phoenixRepeaterIgnitesEveryFifthRound() {
WeaponSignatureRuntime.begin(WeaponCatalog.PHOENIX_REPEATER);
⋮----
assertEquals(WeaponSignatureRuntime.Kind.PHOENIX_IGNITION, mark.kind());
assertEquals(1.30f, mark.damageMultiplier(), .0001f);
⋮----
@Test void beginningANewRunResetsSignatureCadence() {
⋮----
for (int i = 0; i < 4; i++) assertFalse(WeaponSignatureRuntime.consumeShot(false).active());
⋮----
assertFalse(WeaponSignatureRuntime.consumeShot(false).active());
assertEquals(1, WeaponSignatureRuntime.shotIndex());
⋮----
@Test void ionOverchargeTransformsActualProjectile() {
⋮----
for (int i = 0; i < 4; i++) new Projectile().spawn(0, 0, 1, 0, 10f, false, 2, 1f, DamageElement.SHOCK);
Projectile p = new Projectile().spawn(0, 0, 1, 0, 10f, false, 2, 1f, DamageElement.SHOCK);
assertTrue(p.weaponSignature);
assertEquals(WeaponSignatureRuntime.Kind.ION_OVERCHARGE, p.weaponSignatureKind);
assertTrue(p.critical);
assertEquals(3, p.penetrationRemaining);
assertTrue(p.damage >= 20f);
assertTrue(p.radius > .11f);
⋮----
@Test void cinderThermalShellStacksWithNormalFireIdentity() {
⋮----
for (int i = 0; i < 3; i++) new Projectile().spawn(0, 0, 1, 0, 20f, false, 1, 4f, DamageElement.FIRE);
Projectile p = new Projectile().spawn(0, 0, 1, 0, 20f, false, 1, 4f, DamageElement.FIRE);
⋮----
assertEquals(WeaponSignatureRuntime.Kind.CINDER_OVERHEAT, p.weaponSignatureKind);
assertEquals(DamageElement.FIRE, p.element);
assertEquals(31f, p.damage, .0001f);
assertEquals(2, p.penetrationRemaining);
assertEquals(.17f, p.radius, .0001f);
assertTrue(p.knockback > 4f);
⋮----
@Test void newEndgameSignaturesTransformActualProjectiles() {
⋮----
for (int i = 0; i < 5; i++) new Projectile().spawn(0, 0, 1, 0, 10f, false, 1, 1f, DamageElement.SHOCK);
Projectile tempest = new Projectile().spawn(0, 0, 1, 0, 10f, false, 1, 1f, DamageElement.SHOCK);
assertEquals(WeaponSignatureRuntime.Kind.TEMPEST_SURGE, tempest.weaponSignatureKind);
assertEquals(3, tempest.penetrationRemaining);
assertEquals(12f, tempest.damage, .0001f);
⋮----
for (int i = 0; i < 7; i++) new Projectile().spawn(0, 0, 1, 0, 20f, false, 0, 2f, DamageElement.FROST);
Projectile whiteout = new Projectile().spawn(0, 0, 1, 0, 20f, false, 0, 2f, DamageElement.FROST);
assertEquals(WeaponSignatureRuntime.Kind.WHITEOUT_SHATTER, whiteout.weaponSignatureKind);
assertEquals(1, whiteout.penetrationRemaining);
assertTrue(whiteout.knockback > 2.7f);
⋮----
for (int i = 0; i < 4; i++) new Projectile().spawn(0, 0, 1, 0, 20f, false, 1, 1f, DamageElement.FIRE);
Projectile phoenix = new Projectile().spawn(0, 0, 1, 0, 20f, false, 1, 1f, DamageElement.FIRE);
assertEquals(WeaponSignatureRuntime.Kind.PHOENIX_IGNITION, phoenix.weaponSignatureKind);
assertEquals(26f, phoenix.damage, .0001f);
assertEquals(2, phoenix.penetrationRemaining);
```

## File: src/test/java/com/deadlinezero/game/config/AccessibilityColorVisionTest.java
```java
final class AccessibilityColorVisionTest {
@Test void colorVisionModesCycleBothDirections() {
⋮----
assertEquals(AccessibilitySettings.ColorVisionMode.DEUTERANOPIA, mode.next(1));
assertEquals(AccessibilitySettings.ColorVisionMode.TRITANOPIA, mode.next(-1));
assertEquals(AccessibilitySettings.ColorVisionMode.STANDARD,
AccessibilitySettings.ColorVisionMode.TRITANOPIA.next(1));
⋮----
@Test void invalidStoredColorVisionModeFallsBackToStandard() {
⋮----
AccessibilitySettings.ColorVisionMode.fromStored("UNKNOWN"));
⋮----
AccessibilitySettings.ColorVisionMode.fromStored(null));
⋮----
@Test void normalizeClampsComfortScalarsAndReducedMotion() {
AccessibilitySettings settings = new AccessibilitySettings();
⋮----
settings.normalize();
assertEquals(1f, settings.screenShakeStrength);
assertEquals(.85f, settings.uiScale);
assertEquals(1f, settings.masterVolume);
assertEquals(0f, settings.sfxVolume);
assertEquals(1f, settings.musicVolume);
assertFalse(settings.screenShake);
assertFalse(settings.hitStop);
assertTrue(settings.reduceFlashes);
```

## File: src/test/java/com/deadlinezero/game/config/AccessibilitySettingsTest.java
```java
final class AccessibilitySettingsTest {
@Test void reducedMotionDisablesMotionHeavyFeedback() {
AccessibilitySettings settings = new AccessibilitySettings();
settings.setReducedMotion(true);
⋮----
assertTrue(settings.reducedMotion);
assertTrue(settings.motionControlLocked());
assertFalse(settings.screenShake);
assertFalse(settings.hitStop);
assertTrue(settings.reduceFlashes);
assertFalse(settings.allowsScreenShake());
assertFalse(settings.allowsHitStop());
assertTrue(settings.minimizesFlashes());
⋮----
@Test void normalizationRepairsInconsistentReducedMotionPreferences() {
⋮----
settings.normalize();
⋮----
@Test void disablingPresetRestoresMotionFeedbackWithoutTouchingFlashPreference() {
⋮----
settings.setReducedMotion(false);
⋮----
assertFalse(settings.reducedMotion);
assertFalse(settings.motionControlLocked());
assertTrue(settings.screenShake);
assertTrue(settings.hitStop);
⋮----
assertTrue(settings.allowsScreenShake());
assertTrue(settings.allowsHitStop());
⋮----
@Test void helperMethodsRespectManualControls() {
⋮----
assertFalse(settings.minimizesFlashes());
```

## File: src/test/java/com/deadlinezero/game/config/GraphicsSettingsTest.java
```java
final class GraphicsSettingsTest {
@Test void profilesAreStrictlyOrderedAndBounded() {
var values = GraphicsSettings.Quality.values();
assertEquals(4, values.length);
⋮----
assertTrue(values[i].fxCeiling >= .40f && values[i].fxCeiling <= 1f);
if (i > 0) assertTrue(values[i].fxCeiling > values[i - 1].fxCeiling);
⋮----
@Test void qualityNavigationClampsAtEnds() {
assertEquals(GraphicsSettings.Quality.LOW, GraphicsSettings.Quality.LOW.next(-1));
assertEquals(GraphicsSettings.Quality.MEDIUM, GraphicsSettings.Quality.LOW.next(1));
assertEquals(GraphicsSettings.Quality.ULTRA, GraphicsSettings.Quality.ULTRA.next(1));
assertEquals(GraphicsSettings.Quality.HIGH, GraphicsSettings.Quality.ULTRA.next(-1));
⋮----
@Test void frameRateTargetsAreExplicitAndClampAtEnds() {
var values = GraphicsSettings.FrameRate.values();
assertEquals(3, values.length);
assertEquals(60, values[0].target);
assertEquals(90, values[1].target);
assertEquals(120, values[2].target);
assertEquals(GraphicsSettings.FrameRate.FPS_60, GraphicsSettings.FrameRate.FPS_60.next(-1));
assertEquals(GraphicsSettings.FrameRate.FPS_90, GraphicsSettings.FrameRate.FPS_60.next(1));
assertEquals(GraphicsSettings.FrameRate.FPS_120, GraphicsSettings.FrameRate.FPS_120.next(1));
assertEquals(GraphicsSettings.FrameRate.FPS_90, GraphicsSettings.FrameRate.FPS_120.next(-1));
```

## File: src/test/java/com/deadlinezero/game/config/LocalizationCatalogGuardTest.java
```java
/**
 * Repository-level localization guardrail.
 *
 * Fails CI when a static UI localization lookup references a missing key, the English catalog
 * contains duplicate keys, a MessageFormat placeholder sequence is malformed, or a screen
 * reintroduces a direct user-facing literal in BitmapFont.draw(). Dynamic content families are
 * resolved through stable enum/id-derived keys and are therefore exempt from static orphan checks.
 */
final class LocalizationCatalogGuardTest {
private static final Pattern STATIC_LOOKUP = Pattern.compile(
⋮----
private static final Pattern DIRECT_FONT_LITERAL = Pattern.compile(
⋮----
private static final Set<String> ALLOWED_PRESENTATION_LITERALS = Set.of("‹", "›", "", " • ");
private static final List<String> DYNAMIC_PREFIXES = List.of(
⋮----
@Test void catalogHasUniqueKeysAndValidPlaceholderSequences() throws Exception {
Catalog catalog = loadCatalog();
assertTrue(catalog.duplicates.isEmpty(), "Duplicate i18n keys: " + catalog.duplicates);
⋮----
for (Map.Entry<String, String> entry : catalog.values.entrySet()) {
⋮----
new MessageFormat(entry.getValue());
⋮----
malformed.add(entry.getKey() + " -> " + exception.getMessage());
⋮----
assertTrue(malformed.isEmpty(), "Malformed i18n MessageFormat patterns: " + malformed);
⋮----
@Test void everyStaticLocalizationLookupExistsInCatalog() throws Exception {
⋮----
Set<String> referenced = staticReferencedKeys();
⋮----
missing.removeAll(catalog.values.keySet());
if (!missing.isEmpty()) {
System.err.println("I18N_MISSING_KEYS " + missing);
⋮----
assertTrue(missing.isEmpty(), "Missing i18n keys referenced by UI code: " + missing);
⋮----
@Test void screensDoNotReintroduceDirectUserFacingFontLiterals() throws Exception {
Path root = repositoryRoot();
⋮----
for (Path source : presentationSources(root)) {
String content = Files.readString(source, StandardCharsets.UTF_8);
Matcher matcher = DIRECT_FONT_LITERAL.matcher(content);
while (matcher.find()) {
String literal = matcher.group(1);
if (ALLOWED_PRESENTATION_LITERALS.contains(literal)) continue;
if (literal.isBlank()) continue;
violations.add(root.relativize(source) + " -> \"" + literal + "\"");
⋮----
assertTrue(violations.isEmpty(),
⋮----
@Test void catalogOrphanAuditStaysVisibleWithoutBlockingDynamicContent() throws Exception {
⋮----
List<String> clearOrphans = catalog.values.keySet().stream()
.filter(key -> !referenced.contains(key))
.filter(key -> DYNAMIC_PREFIXES.stream().noneMatch(key::startsWith))
.sorted()
.toList();
⋮----
// Diagnostic only: static orphan detection is intentionally non-blocking because some
// strings are reached indirectly by runtime state. It remains visible in test reports.
if (!clearOrphans.isEmpty()) {
System.out.println("I18N_ORPHAN_AUDIT " + clearOrphans.size() + " candidate(s): " + clearOrphans);
⋮----
assertFalse(catalog.values.isEmpty(), "Localization catalog must not be empty");
⋮----
private static Set<String> staticReferencedKeys() throws IOException {
⋮----
Matcher matcher = STATIC_LOOKUP.matcher(content);
⋮----
String key = matcher.group(1);
if (key.endsWith(".") && DYNAMIC_PREFIXES.stream().anyMatch(key::startsWith)) continue;
keys.add(key);
⋮----
private static List<Path> presentationSources(Path root) throws IOException {
⋮----
Path screens = root.resolve("core/src/main/java/com/deadlinezero/game/screen");
try (Stream<Path> stream = Files.walk(screens)) {
stream.filter(path -> path.toString().endsWith(".java")).forEach(result::add);
⋮----
result.add(root.resolve("core/src/main/java/com/deadlinezero/game/visual/CombatHudRenderer.java"));
result.add(root.resolve("core/src/main/java/com/deadlinezero/game/meta/RunShareText.java"));
⋮----
private static Catalog loadCatalog() throws IOException {
Path file = repositoryRoot().resolve("assets/i18n/messages.properties");
⋮----
for (String raw : Files.readAllLines(file, StandardCharsets.UTF_8)) {
String line = raw.strip();
if (line.isEmpty() || line.startsWith("#") || line.startsWith("!")) continue;
int split = firstUnescapedSeparator(line);
⋮----
String key = line.substring(0, split).strip();
String value = line.substring(split + 1).strip();
if (values.putIfAbsent(key, value) != null) duplicates.add(key);
⋮----
return new Catalog(values, duplicates);
⋮----
private static int firstUnescapedSeparator(String line) {
⋮----
for (int i = 0; i < line.length(); i++) {
char ch = line.charAt(i);
⋮----
private static Path repositoryRoot() {
Path current = Path.of("").toAbsolutePath().normalize();
for (Path candidate = current; candidate != null; candidate = candidate.getParent()) {
if (Files.isRegularFile(candidate.resolve("assets/i18n/messages.properties"))
&& Files.isDirectory(candidate.resolve("core/src/main/java"))) return candidate;
⋮----
throw new IllegalStateException("Unable to locate repository root from " + current);
```

## File: src/test/java/com/deadlinezero/game/config/LocalizationGlyphSanitizerTest.java
```java
final class LocalizationGlyphSanitizerTest {
⋮----
void unsupportedUiGlyphsAreNormalizedForDefaultBitmapFont() {
⋮----
String sanitized = Localization.sanitizeForBitmapFont(source);
⋮----
assertEquals("A | B < C > D <- E -> F ^ G v H - I - J ... K", sanitized);
⋮----
assertFalse(sanitized.indexOf(unsupported) >= 0, "unsupported glyph survived: " + unsupported);
⋮----
void unresolvedFormatTokensDoNotLeakIntoVisibleUi() {
assertEquals("TAP / R TO CHANGE",
Localization.sanitizeForBitmapFont("{0} • TAP / R TO CHANGE"));
⋮----
void leadingDesktopEscapeHintDoesNotCrowdMobileBackRail() {
assertEquals("BACK TO BASE", Localization.sanitizeForBitmapFont("ESC • BACK TO BASE"));
assertEquals("TAP A CARD | ESC TO CANCEL",
Localization.sanitizeForBitmapFont("TAP A CARD • ESC TO CANCEL"));
⋮----
void nullAndAsciiStringsRemainSafe() {
assertEquals("", Localization.sanitizeForBitmapFont(null));
assertEquals("DPS 120 | FIRE 0.25s", Localization.sanitizeForBitmapFont("DPS 120 | FIRE 0.25s"));
```

## File: src/test/java/com/deadlinezero/game/config/LocalizationReleaseContractTest.java
```java
final class LocalizationReleaseContractTest {
@Test void releaseContractMatchesRepositoryLocalizationArchitecture() throws Exception {
Path root = repositoryRoot();
String contract = Files.readString(root.resolve("play/store/LOCALIZATION.md"), StandardCharsets.UTF_8);
⋮----
assertTrue(Files.isRegularFile(root.resolve("assets/i18n/messages.properties")));
assertTrue(Files.isRegularFile(root.resolve(
⋮----
assertTrue(contract.contains("repository-level localization architecture"));
assertTrue(contract.contains("centralized English catalog"));
assertTrue(contract.contains("English-only"));
assertTrue(contract.contains("[x] Centralized translatable string catalog exists for core UI."));
assertTrue(contract.contains("[ ] Runtime locale selection is implemented."));
assertFalse(contract.contains("no repository-level i18n/localization resource system"));
⋮----
private static Path repositoryRoot() {
Path current = Path.of("").toAbsolutePath().normalize();
for (Path candidate = current; candidate != null; candidate = candidate.getParent()) {
if (Files.isRegularFile(candidate.resolve("play/store/LOCALIZATION.md"))
&& Files.isRegularFile(candidate.resolve("assets/i18n/messages.properties"))) {
⋮----
throw new IllegalStateException("Unable to locate repository root from " + current);
```

## File: src/test/java/com/deadlinezero/game/config/MobileRuntimeBudgetTest.java
```java
final class MobileRuntimeBudgetTest {
@Test void hardCapsStayWithinMobileSafetyBudget() {
assertTrue(GameConfig.MAX_ENEMIES <= 420);
assertTrue(GameConfig.MAX_PROJECTILES <= 768);
assertTrue(Pools.MAX_HOSTILE_PROJECTILES <= 384);
assertTrue(Pools.MAX_HOMING_MISSILES <= 96);
assertTrue(Pools.MAX_IMPACTS <= 192);
assertTrue(Pools.MAX_DAMAGE_NUMBERS <= 192);
assertTrue(Pools.MAX_ARCS <= 96);
assertTrue(Pools.MAX_DEATH_FX <= 72);
⋮----
@Test void budgetsKeepEnoughHeadroomForEndgameBuilds() {
assertTrue(GameConfig.MAX_ENEMIES >= 300);
assertTrue(GameConfig.MAX_PROJECTILES >= 600);
assertTrue(Pools.MAX_HOSTILE_PROJECTILES >= 256);
assertTrue(Pools.MAX_DAMAGE_NUMBERS >= 128);
```

## File: src/test/java/com/deadlinezero/game/entities/EnemyBiomeElementResistanceTest.java
```java
public final class EnemyBiomeElementResistanceTest {
@AfterEach void resetStage() {
RunStageContext.begin(1);
⋮----
@Test public void forgeHoundAttenuatesFireStatusPowerAndDuration() {
RunStageContext.begin(10);
Enemy e = enemy(Enemy.Type.RUNNER);
e.applyElement(DamageElement.FIRE, 100f);
⋮----
assertEquals(13.64f, e.burnDps, .001f);
assertEquals(1.488f, e.burnTimer, .001f);
⋮----
@Test public void cinderGunnerUsesItsOwnFireResistance() {
⋮----
Enemy e = enemy(Enemy.Type.RANGED);
⋮----
assertEquals(15.84f, e.burnDps, .001f);
assertEquals(1.728f, e.burnTimer, .001f);
⋮----
@Test public void nonResistedElementKeepsFullStatusPower() {
⋮----
Enemy baseline = enemy(Enemy.Type.RUNNER);
baseline.applyElement(DamageElement.FIRE, 100f);
⋮----
assertEquals(22f, baseline.burnDps, .001f);
assertEquals(2.4f, baseline.burnTimer, .001f);
assertTrue(resisted < baseline.burnDps);
⋮----
@Test public void phaseStalkerAttenuatesShockReactionAndStun() {
RunStageContext.begin(20);
Enemy e = enemy(Enemy.Type.PHANTOM);
⋮----
e.applyElement(DamageElement.SHOCK, 100f);
⋮----
assertEquals(Enemy.ElementReaction.OVERLOAD, e.lastReaction);
assertEquals(13.64f, before - e.hp, .01f);
assertEquals(.341f, e.shockTimer, .001f);
⋮----
@Test public void nullWardAttenuatesFrostReactionAndSlow() {
⋮----
Enemy e = enemy(Enemy.Type.REGENERATOR);
⋮----
e.applyElement(DamageElement.FROST, 100f);
⋮----
assertEquals(Enemy.ElementReaction.STEAM_BURST, e.lastReaction);
assertEquals(18.48f, before - e.hp, .01f);
assertEquals(1.056f, e.slowTimer, .001f);
assertEquals(.7492f, e.slowMultiplier, .001f);
⋮----
private Enemy enemy(Enemy.Type type) {
return new Enemy(type, 0f, 0f, 1000f, 1f, .5f, 10f, 1, false);
```

## File: src/test/java/com/deadlinezero/game/entities/EnemyBiomeTacticsTest.java
```java
public final class EnemyBiomeTacticsTest {
@AfterEach void resetStage() { RunStageContext.begin(1); }
⋮----
@Test public void forgeHoundSchedulesChargeAtMidRange() {
RunStageContext.begin(10);
Enemy e = new Enemy(Enemy.Type.RUNNER, 0, 0, 100, 4f, .3f, 8f, 1);
e.updateAi(.01f, 5f);
assertEquals(Enemy.Tactic.CHARGE, e.pendingTactic());
assertTrue(e.tacticalTelegraph());
⋮----
@Test public void cinderGunnerSchedulesStrafe() {
⋮----
Enemy e = new Enemy(Enemy.Type.RANGED, 0, 0, 100, 2f, .4f, 8f, 1);
e.updateAi(.01f, 6f);
assertEquals(Enemy.Tactic.STRAFE, e.pendingTactic());
⋮----
@Test public void slagGuardSchedulesHeavyCharge() {
⋮----
Enemy e = new Enemy(Enemy.Type.SHIELDED, 0, 0, 100, 2f, .5f, 8f, 1);
⋮----
@Test public void phaseStalkerSchedulesFlankStrafe() {
RunStageContext.begin(20);
Enemy e = new Enemy(Enemy.Type.PHANTOM, 0, 0, 100, 3f, .4f, 8f, 1);
⋮----
@Test public void staticSeerSchedulesZoningStrafe() {
⋮----
e.updateAi(.01f, 8f);
⋮----
@Test public void nullWardGetsSupportRecoveryMultiplier() {
⋮----
Enemy e = new Enemy(Enemy.Type.REGENERATOR, 0, 0, 1000, 2f, .4f, 8f, 1);
assertTrue(e.biomeBehavior().recoveryMultiplier() > 1.5f);
assertTrue(e.biomeBehavior().speedMultiplier() < 1f);
```

## File: src/test/java/com/deadlinezero/game/entities/EnemyChargeImpactTest.java
```java
public final class EnemyChargeImpactTest {
@Test public void chargeImpactCanOnlyBeConsumedOncePerCharge() {
RunStageContext.begin(1);
Enemy e = new Enemy(Enemy.Type.BRUTE, 0f, 0f, 200f, 1.6f, .72f, 18f, 10);
e.velocity.set(1f, 0f);
⋮----
e.updateAi(.01f, 4f);
e.updateStatus(.40f);
⋮----
assertTrue(e.chargeImpactActive());
assertTrue(e.consumeChargeImpact());
assertFalse(e.consumeChargeImpact());
assertFalse(e.chargeImpactActive());
```

## File: src/test/java/com/deadlinezero/game/entities/EnemyContentScaleTest.java
```java
final class EnemyContentScaleTest {
@Test void p5EnemyAndEliteProfileTargetsAreExplicitlyMet() {
int baseEnemyArchetypes = Enemy.Type.values().length - 1; // BOSS counted separately.
int biomeSignatureProfiles = BiomeEnemyRoster.Identity.values().length - 1; // NONE is fallback.
int championProfiles = Enemy.Variant.values().length - 1; // NORMAL is baseline.
⋮----
assertEquals(8, baseEnemyArchetypes);
assertEquals(6, biomeSignatureProfiles);
assertEquals(8, championProfiles);
assertTrue(baseEnemyArchetypes + biomeSignatureProfiles + championProfiles >= 20,
⋮----
assertTrue(championProfiles >= 8, "champion/elite profile count regressed below P5 8+ target");
⋮----
@Test void championRollCoversAllEightProfilesWithStableBoundaries() {
assertEquals(Enemy.Variant.SWIFT, Enemy.variantForRoll(0f));
assertEquals(Enemy.Variant.ARMORED, Enemy.variantForRoll(.125f));
assertEquals(Enemy.Variant.FERAL, Enemy.variantForRoll(.25f));
assertEquals(Enemy.Variant.VOLATILE, Enemy.variantForRoll(.375f));
assertEquals(Enemy.Variant.JUGGERNAUT, Enemy.variantForRoll(.5f));
assertEquals(Enemy.Variant.RAVAGER, Enemy.variantForRoll(.625f));
assertEquals(Enemy.Variant.AEGIS, Enemy.variantForRoll(.75f));
assertEquals(Enemy.Variant.HUNTER, Enemy.variantForRoll(.875f));
assertEquals(Enemy.Variant.HUNTER, Enemy.variantForRoll(1f));
assertEquals(Enemy.Variant.SWIFT, Enemy.variantForRoll(-1f));
```

## File: src/test/java/com/deadlinezero/game/entities/EnemyElementReactionTest.java
```java
public final class EnemyElementReactionTest {
private Enemy enemy() {
RunStageContext.begin(1);
return new Enemy(Enemy.Type.BOSS, 0f, 0f, 500f, 1f, .6f, 10f, 1);
⋮----
@Test public void fireOnFrozenTargetTriggersThermalShock() {
Enemy e = enemy();
e.applyElement(DamageElement.FROST, 40f);
⋮----
e.applyElement(DamageElement.FIRE, 40f);
⋮----
assertEquals(Enemy.ElementReaction.THERMAL_SHOCK, e.lastReaction);
assertTrue(e.hp < hpBefore);
assertEquals(0f, e.slowTimer, .0001f);
assertTrue(e.burnTimer > 0f);
⋮----
@Test public void frostOnBurningTargetTriggersSteamBurst() {
⋮----
assertEquals(Enemy.ElementReaction.STEAM_BURST, e.lastReaction);
⋮----
assertEquals(0f, e.burnTimer, .0001f);
assertTrue(e.slowTimer > 0f);
⋮----
@Test public void shockOnPrimedTargetTriggersLongerOverloadStun() {
⋮----
e.applyElement(DamageElement.FIRE, 30f);
⋮----
e.applyElement(DamageElement.SHOCK, 30f);
⋮----
assertEquals(Enemy.ElementReaction.OVERLOAD, e.lastReaction);
⋮----
assertTrue(e.shockTimer >= .55f);
⋮----
@Test public void reactionMarkerExpiresWithRuntimeUpdate() {
⋮----
e.updateStatus(.30f);
⋮----
assertEquals(Enemy.ElementReaction.NONE, e.lastReaction);
assertEquals(0f, e.reactionFlash, .0001f);
```

## File: src/test/java/com/deadlinezero/game/entities/EnemyProjectileStyleTest.java
```java
final class EnemyProjectileStyleTest {
@Test void defaultStyleTracksActiveBiome() {
RunStageContext.begin(1, 0, 0);
assertEquals(EnemyProjectile.Style.DEFAULT, EnemyProjectile.defaultStyleForActiveBiome());
⋮----
RunStageContext.begin(10, 0, 0);
assertEquals(EnemyProjectile.Style.CINDER, EnemyProjectile.defaultStyleForActiveBiome());
⋮----
RunStageContext.begin(20, 0, 0);
assertEquals(EnemyProjectile.Style.NULL, EnemyProjectile.defaultStyleForActiveBiome());
⋮----
@Test void nullSectorThinNonExplosiveVolleyUsesStaticIdentity() {
⋮----
assertEquals(EnemyProjectile.Style.STATIC,
EnemyProjectile.defaultStyleForActiveContext(.18f, false));
assertEquals(EnemyProjectile.Style.NULL,
EnemyProjectile.defaultStyleForActiveContext(.22f, false));
⋮----
EnemyProjectile.defaultStyleForActiveContext(.18f, true));
⋮----
@Test void explicitSourceStyleOverridesBiomeFallback() {
⋮----
EnemyProjectile projectile = new EnemyProjectile().spawn(
⋮----
assertEquals(EnemyProjectile.Style.CINDER, projectile.style);
```

## File: src/test/java/com/deadlinezero/game/entities/EnemySpecialistTest.java
```java
final class EnemySpecialistTest {
@BeforeEach void resetStage() { RunStageContext.begin(1, 0, 0); }
@AfterEach void cleanupStage() { RunStageContext.begin(1, 0, 0); }
⋮----
@Test void shieldedEnemyAbsorbsDamageBeforeHealth() {
Enemy enemy = new Enemy(Enemy.Type.SHIELDED, 0f, 0f, 100f, 2f, .5f, 10f, 10);
⋮----
enemy.damage(Math.min(20f, shieldBefore));
⋮----
assertEquals(hpBefore, enemy.hp, .001f);
assertTrue(enemy.shieldHp < shieldBefore);
assertTrue(enemy.shieldFraction() >= 0f && enemy.shieldFraction() <= 1f);
⋮----
@Test void shieldedEnemyRechargesAfterRecoveryDelay() {
⋮----
enemy.damage(enemy.shieldHp * .5f);
⋮----
for (int i = 0; i < 260; i++) enemy.updateStatus(1f / 60f);
⋮----
assertTrue(enemy.shieldHp > depleted);
assertTrue(enemy.shieldHp <= enemy.shieldMaxHp);
⋮----
@Test void regeneratorRecoversHealthOnlyAfterTakingPressureBreak() {
Enemy enemy = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 100f, 2f, .5f, 10f, 10);
enemy.damage(35f);
⋮----
for (int i = 0; i < 120; i++) enemy.updateStatus(1f / 60f);
assertEquals(damaged, enemy.hp, .01f);
⋮----
assertTrue(enemy.hp > damaged);
assertTrue(enemy.hp <= enemy.maxHp);
⋮----
@Test void phantomCyclesIntoMitigationAndSpeedWindow() {
Enemy enemy = new Enemy(Enemy.Type.PHANTOM, 0f, 0f, 100f, 2f, .5f, 10f, 10);
assertFalse(enemy.phased());
⋮----
for (int i = 0; i < 216; i++) enemy.updateStatus(1f / 60f);
assertTrue(enemy.phased());
assertTrue(enemy.effectiveSpeed() > baseConfiguredSpeed * 1.30f,
⋮----
enemy.damage(50f);
assertTrue(before - enemy.hp < 20f);
```

## File: src/test/java/com/deadlinezero/game/entities/EnemyTacticsTest.java
```java
public final class EnemyTacticsTest {
@Test public void rangedPreparesStrafeAtOuterPreferredRange() {
RunStageContext.begin(1);
Enemy ranged = new Enemy(Enemy.Type.RANGED, 0f, 0f, 72f, 2.15f, .42f, 13f, 12);
ranged.velocity.set(1f, 0f);
ranged.updateAi(.01f, 8.5f);
assertEquals(Enemy.Tactic.STRAFE, ranged.pendingTactic());
assertTrue(ranged.tacticalTelegraph());
⋮----
@Test public void brutePreparesChargeAtMidRange() {
⋮----
Enemy brute = new Enemy(Enemy.Type.BRUTE, 0f, 0f, 145f, 1.6f, .72f, 18f, 15);
brute.velocity.set(1f, 0f);
brute.updateAi(.01f, 5f);
assertEquals(Enemy.Tactic.CHARGE, brute.pendingTactic());
assertTrue(brute.tacticalTelegraph());
⋮----
@Test public void bossDoesNotUseGenericTactics() {
⋮----
Enemy boss = new Enemy(Enemy.Type.BOSS, 0f, 0f, 2200f, 1.35f, 1.65f, 24f, 280);
boss.velocity.set(1f, 0f);
boss.updateAi(.01f, 5f);
assertEquals(Enemy.Tactic.NONE, boss.pendingTactic());
```

## File: src/test/java/com/deadlinezero/game/entities/EnemyVariantTest.java
```java
final class EnemyVariantTest {
@Test void armoredResistsKnockback() {
Enemy normal = new Enemy(Enemy.Type.SHAMBLER, 0f, 0f, 100f, 2f, .4f, 10f, 5);
Enemy armored = new Enemy(Enemy.Type.SHAMBLER, 0f, 0f, 100f, 2f, .4f, 10f, 5);
⋮----
armored.applyVariant(Enemy.Variant.ARMORED);
normal.addImpulse(10f, 0f);
armored.addImpulse(10f, 0f);
assertTrue(armored.impulse.x < normal.impulse.x * .5f);
⋮----
@Test void feralEnragesAtLowHealth() {
Enemy feral = new Enemy(Enemy.Type.SHAMBLER, 0f, 0f, 100f, 2f, .4f, 10f, 5);
⋮----
feral.applyVariant(Enemy.Variant.FERAL);
float healthySpeed = feral.effectiveSpeed();
⋮----
float enragedSpeed = feral.effectiveSpeed();
assertTrue(enragedSpeed > healthySpeed * 1.15f);
⋮----
@Test void swiftHasBurstWindow() {
Enemy swift = new Enemy(Enemy.Type.SHAMBLER, 0f, 0f, 100f, 2f, .4f, 10f, 5);
⋮----
swift.applyVariant(Enemy.Variant.SWIFT);
⋮----
float burst = swift.effectiveSpeed();
⋮----
float cruise = swift.effectiveSpeed();
assertTrue(burst > cruise * 1.20f);
⋮----
@Test void bossNeverAcceptsChampionVariant() {
Enemy boss = new Enemy(Enemy.Type.BOSS, 0f, 0f, 1000f, 1f, 1f, 20f, 100);
boss.applyVariant(Enemy.Variant.FERAL);
assertEquals(Enemy.Variant.NORMAL, boss.variant);
```

## File: src/test/java/com/deadlinezero/game/entities/NullWardSupportTest.java
```java
final class NullWardSupportTest {
@Test void pulseHealsAndBuffsNearbyNonBossAlly() {
RunStageContext.begin(20, 77, 0);
Enemy ward = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 100f, 2f, .45f, 10f, 8, false);
Enemy ally = new Enemy(Enemy.Type.RUNNER, 1f, 0f, 100f, 3f, .35f, 10f, 8, false);
ally.damage(50f);
⋮----
float speedBefore = ally.effectiveSpeed();
⋮----
ward.updateStatus(Enemy.nullWardPulseInterval());
⋮----
assertTrue(ally.hp > hpBefore, "Null Ward should heal a nearby ally");
assertTrue(ally.supportBuffed(), "Null Ward should grant its temporary support buff");
assertTrue(ally.effectiveSpeed() > speedBefore, "support buff should increase ally mobility");
assertTrue(ward.supportPulseFlash() > 0f, "pulse should expose a short visual event window");
⋮----
@Test void simultaneousWardsCannotStackBurstHealingOnOneTarget() {
RunStageContext.begin(20, 79, 0);
Enemy wardA = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 100f, 2f, .45f, 10f, 8, false);
Enemy wardB = new Enemy(Enemy.Type.REGENERATOR, .5f, 0f, 100f, 2f, .45f, 10f, 8, false);
⋮----
ally.damage(60f);
⋮----
wardA.updateStatus(Enemy.nullWardPulseInterval());
wardB.updateStatus(Enemy.nullWardPulseInterval());
⋮----
assertEquals(before + expectedSingleHeal, ally.hp, .01f,
⋮----
assertTrue(ally.supportBuffed(), "the shared target should still receive the support buff");
⋮----
@Test void pulseIgnoresBossesAndDistantEnemies() {
RunStageContext.begin(20, 78, 0);
Enemy ward = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 100f, 2f, .45f, 10f, 8);
Enemy boss = new Enemy(Enemy.Type.BOSS, 1f, 0f, 500f, 1f, 1.2f, 20f, 100, false);
Enemy distant = new Enemy(Enemy.Type.RUNNER, Enemy.nullWardPulseRadius() + 2f, 0f, 100f, 3f, .35f, 10f, 8, false);
boss.damage(100f);
distant.damage(40f);
⋮----
assertEquals(bossHp, boss.hp, .001f);
assertEquals(distantHp, distant.hp, .001f);
assertTrue(!boss.supportBuffed());
assertTrue(!distant.supportBuffed());
```

## File: src/test/java/com/deadlinezero/game/fx/DamageNumberTest.java
```java
final class DamageNumberTest {
@Test void displayTextIsRoundedAndCachedAtSpawn() {
DamageNumber number = new DamageNumber().spawn(1f, 2f, 12.6f, false, Color.WHITE);
assertEquals("13", number.text);
⋮----
number.update(.1f);
⋮----
@Test void displayTextClampsToAtLeastOne() {
DamageNumber number = new DamageNumber().spawn(0f, 0f, .2f, false, Color.WHITE);
assertEquals("1", number.text);
```

## File: src/test/java/com/deadlinezero/game/meta/AchievementServiceTest.java
```java
public final class AchievementServiceTest {
@Test void lifetimeThresholdsUnlockDeterministically() {
PlayerProfile p = new PlayerProfile();
assertFalse(AchievementService.unlocked(p, AchievementService.Achievement.FIRST_DEPLOYMENT));
⋮----
for (AchievementService.Achievement achievement : AchievementService.Achievement.values()) {
assertTrue(AchievementService.unlocked(p, achievement), achievement.name());
⋮----
@Test void claimPaysExactlyOnce() {
⋮----
long before = p.currency(PlayerProfile.Currency.CREDITS);
assertTrue(AchievementService.claim(p, AchievementService.Achievement.FIRST_DEPLOYMENT));
assertEquals(before + 500L, p.currency(PlayerProfile.Currency.CREDITS));
assertTrue(p.achievements.claimed(AchievementService.Achievement.FIRST_DEPLOYMENT));
assertFalse(AchievementService.claim(p, AchievementService.Achievement.FIRST_DEPLOYMENT));
⋮----
@Test void lockedAchievementCannotBeClaimed() {
⋮----
assertFalse(AchievementService.claim(p, AchievementService.Achievement.DEEP_STRIKE));
assertFalse(p.achievements.claimed(AchievementService.Achievement.DEEP_STRIKE));
```

## File: src/test/java/com/deadlinezero/game/meta/BalanceCoefficientAuditTest.java
```java
final class BalanceCoefficientAuditTest {
@Test void stageCurveStaysMonotonicWithoutSpikes() {
assertTrue(BalanceCoefficientAudit.stageCurvesHealthy());
⋮----
@Test void threatCurveStaysMonotonicAndRewarded() {
assertTrue(BalanceCoefficientAudit.threatCurvesHealthy());
```

## File: src/test/java/com/deadlinezero/game/meta/BalanceCurveRegressionTest.java
```java
/** Release gates for campaign/endgame pacing and reward curves. */
public final class BalanceCurveRegressionTest {
public static void bossArrivalPacingIsBoundedAndMonotonic() {
⋮----
float seconds = StageMissionRules.bossArrivalSeconds(stage);
check(seconds >= previous, "boss arrival regressed at stage " + stage);
check(seconds <= 600f, "boss arrival exceeds ten-minute first-playable ceiling");
⋮----
near(StageMissionRules.bossArrivalSeconds(1), 360f, "StageMissionRules.bossArrivalSeconds(1)");
near(StageMissionRules.bossArrivalSeconds(17), 600f, "StageMissionRules.bossArrivalSeconds(17)");
⋮----
public static void threatCurveRaisesRiskAndRewardsWithoutSpeedRunaway() {
⋮----
float hp = ThreatTierRules.enemyHpMultiplier(tier);
float damage = ThreatTierRules.enemyDamageMultiplier(tier);
float speed = ThreatTierRules.enemySpeedMultiplier(tier);
float spawn = ThreatTierRules.spawnIntervalMultiplier(tier);
float reward = ThreatTierRules.rewardMultiplier(tier);
check(hp >= previousHp, "balance invariant failed");
check(damage >= previousDamage, "balance invariant failed");
check(reward >= previousReward, "balance invariant failed");
check(speed <= 1.28f, "threat speed exceeds readability ceiling");
check(spawn >= .72f, "spawn interval falls below density floor");
⋮----
near(ThreatTierRules.enemyHpMultiplier(20), 4.0f, "ThreatTierRules.enemyHpMultiplier(20)");
near(ThreatTierRules.enemyDamageMultiplier(20), 2.10f, "ThreatTierRules.enemyDamageMultiplier(20)");
near(ThreatTierRules.rewardMultiplier(20), 2.50f, "ThreatTierRules.rewardMultiplier(20)");
⋮----
public static void campaignBaseCurveIsStrictlyProgressive() {
RunStageContext.begin(1, 0, 0);
RunModifierContext.end();
⋮----
float nextHp = StageRules.enemyHpMultiplier(stage);
float nextDamage = StageRules.enemyDamageMultiplier(stage);
float nextReward = StageRules.rewardMultiplier(stage);
check(nextHp > hp, "balance invariant failed");
check(nextDamage > damage, "balance invariant failed");
check(nextReward > reward, "balance invariant failed");
check(StageRules.enemySpeedMultiplier(stage) <= 1.78f, "campaign speed exceeds global ceiling");
⋮----
public static void firstClearRewardsRemainProgressive() {
⋮----
long nextCredits = StageMissionRules.firstClearCredits(stage);
int nextGems = StageMissionRules.firstClearGems(stage);
check(nextCredits > credits, "balance invariant failed");
check(nextGems >= gems, "balance invariant failed");
⋮----
check(StageMissionRules.firstClearGems(20) <= 30, "first-clear gems must stay bounded");
⋮----
private static void near(float actual, float expected, String label) {
check(Math.abs(actual - expected) <= .001f, label + ": expected " + expected + ", got " + actual);
⋮----
private static void check(boolean condition, String message) {
if (!condition) throw new AssertionError(message);
⋮----
public static void main(String[] args) {
bossArrivalPacingIsBoundedAndMonotonic();
threatCurveRaisesRiskAndRewardsWithoutSpeedRunaway();
campaignBaseCurveIsStrictlyProgressive();
firstClearRewardsRemainProgressive();
```

## File: src/test/java/com/deadlinezero/game/meta/BalanceHealthRulesTest.java
```java
final class BalanceHealthRulesTest {
@Test void requiresEnoughRunsBeforeJudging() {
⋮----
assertEquals(BalanceHealthRules.Status.LOW_SAMPLE, BalanceHealthRules.assess(summary).status());
⋮----
@Test void detectsDifficultyAndDurationOutliers() {
assertEquals(BalanceHealthRules.Status.TOO_HARD,
BalanceHealthRules.assess(new BalanceTelemetrySummary.Summary(10, 2, .20f, 100f, 40f, 80f, 20f)).status());
assertEquals(BalanceHealthRules.Status.TOO_EASY,
BalanceHealthRules.assess(new BalanceTelemetrySummary.Summary(10, 9, .90f, 100f, 40f, 80f, 20f)).status());
assertEquals(BalanceHealthRules.Status.TOO_SHORT,
BalanceHealthRules.assess(new BalanceTelemetrySummary.Summary(10, 5, .50f, 30f, 40f, 80f, 20f)).status());
assertEquals(BalanceHealthRules.Status.TOO_LONG,
BalanceHealthRules.assess(new BalanceTelemetrySummary.Summary(10, 5, .50f, 240f, 40f, 80f, 20f)).status());
⋮----
@Test void acceptsTargetEnvelope() {
⋮----
assertEquals(BalanceHealthRules.Status.HEALTHY, BalanceHealthRules.assess(summary).status());
```

## File: src/test/java/com/deadlinezero/game/meta/BalanceTelemetryReportTest.java
```java
final class BalanceTelemetryReportTest {
@Test void identifiesStrongestSupportedOutlier() {
⋮----
for (int i = 0; i < 6; i++) samples.add(sample(4, 0, false, "REDLINE", "STANDARD PRESSURE", "REX", "ar9"));
for (int i = 0; i < 6; i++) samples.add(sample(5, 0, i < 4, "BLOOD MOON", "STANDARD PRESSURE", "NYX", "shotgun"));
⋮----
BalanceTelemetryReport.Report report = BalanceTelemetryReport.analyze(samples);
assertNotNull(report.worstOutlier());
assertEquals(BalanceHealthRules.Status.TOO_HARD, report.worstOutlier().assessment().status());
assertEquals(BalanceTelemetrySegments.Dimension.STAGE, report.worstOutlier().dimension());
assertEquals("4", report.worstOutlier().key());
⋮----
@Test void healthySegmentsProduceNoOutlier() {
⋮----
for (int i = 0; i < 10; i++) samples.add(sample(4, 1, i < 5, "REDLINE", "STANDARD PRESSURE", "REX", "ar9"));
⋮----
assertEquals(BalanceHealthRules.Status.HEALTHY, report.overallHealth().status());
assertNull(report.worstOutlier());
⋮----
@Test void mutatorCanSurfaceAsTheWorstOutlier() {
⋮----
for (int i = 0; i < 6; i++) samples.add(sample(12, 6, false, "REDLINE", "SWARM", "REX", "ar9"));
for (int i = 0; i < 6; i++) samples.add(sample(12, 6, i < 4, "REDLINE", "BULWARK", "REX", "ar9"));
⋮----
private static BalanceRunSample sample(int stage, int threat, boolean victory, String contract,
⋮----
return new BalanceRunSample(1, stage, threat, 1, victory, 100f, 20, 5000f, 800f, 300f, 80f,
```

## File: src/test/java/com/deadlinezero/game/meta/BalanceTelemetryRuntimeTest.java
```java
final class BalanceTelemetryRuntimeTest {
@Test void runAccumulatorCapturesCombatAndResetsBetweenRuns() {
RunStageContext.begin(12, 7, 5);
BalanceTelemetryRuntime.setContract("BLOOD MOON");
BalanceTelemetryRuntime.recordDamageDealt(120f);
BalanceTelemetryRuntime.recordDamageDealt(30f);
BalanceTelemetryRuntime.recordDamageReceived(40f);
⋮----
BalanceRunSample first = BalanceTelemetryRuntime.settle(true, 60f, 24);
assertEquals(12, first.stage());
assertEquals(5, first.threatTier());
assertEquals(7, first.runOrdinal());
assertEquals("BLOOD MOON", first.contract());
assertEquals(EndgameMutatorRules.label(), first.mutator());
assertEquals(150f, first.damageDealt(), .001f);
assertEquals(40f, first.damageReceived(), .001f);
assertEquals(120f, first.maxHitDealt(), .001f);
assertEquals(40f, first.maxHitReceived(), .001f);
assertEquals(2.5f, first.dps(), .001f);
assertTrue(first.victory());
assertFalse(BalanceTelemetryRuntime.active());
⋮----
RunStageContext.begin(2, 8, 0);
BalanceRunSample second = BalanceTelemetryRuntime.settle(false, 30f, 2);
assertEquals(0f, second.damageDealt(), .001f);
assertEquals(0f, second.damageReceived(), .001f);
assertEquals("STANDARD", second.contract());
assertEquals("STANDARD PRESSURE", second.mutator());
assertFalse(second.victory());
⋮----
@Test void invalidDamageNeverPoisonsTelemetry() {
RunStageContext.begin(1, 0, 0);
BalanceTelemetryRuntime.recordDamageDealt(Float.NaN);
BalanceTelemetryRuntime.recordDamageDealt(Float.POSITIVE_INFINITY);
BalanceTelemetryRuntime.recordDamageReceived(-50f);
BalanceRunSample sample = BalanceTelemetryRuntime.settle(false, Float.NaN, -9);
assertEquals(0f, sample.damageDealt(), .001f);
assertEquals(0f, sample.damageReceived(), .001f);
assertEquals(0f, sample.seconds(), .001f);
assertEquals(0, sample.kills());
```

## File: src/test/java/com/deadlinezero/game/meta/BalanceTelemetrySegmentsTest.java
```java
final class BalanceTelemetrySegmentsTest {
@Test void groupsAndSortsNumericDimensions() {
⋮----
samples.add(sample(3, 2, true, "REDLINE", "STANDARD PRESSURE", "REX", "ar9"));
samples.add(sample(1, 0, false, "REDLINE", "STANDARD PRESSURE", "NYX", "shotgun"));
samples.add(sample(3, 2, true, "BLOOD MOON", "STANDARD PRESSURE", "REX", "ar9"));
⋮----
List<BalanceTelemetrySegments.Segment> stages = BalanceTelemetrySegments.group(samples, BalanceTelemetrySegments.Dimension.STAGE);
assertEquals(List.of("1", "3"), stages.stream().map(BalanceTelemetrySegments.Segment::key).toList());
assertEquals(2, stages.get(1).summary().runs());
assertEquals(1f, stages.get(1).summary().winRate(), .0001f);
⋮----
List<BalanceTelemetrySegments.Segment> threats = BalanceTelemetrySegments.group(samples, BalanceTelemetrySegments.Dimension.THREAT);
assertEquals(List.of("0", "2"), threats.stream().map(BalanceTelemetrySegments.Segment::key).toList());
⋮----
@Test void groupsCategoricalDimensionsDeterministically() {
⋮----
samples.add(sample(10, 5, true, "REDLINE", "SWARM", "REX", "ar9"));
samples.add(sample(10, 5, false, "BLOOD MOON", "BULWARK", "NYX", "shotgun"));
⋮----
var contracts = BalanceTelemetrySegments.group(samples, BalanceTelemetrySegments.Dimension.CONTRACT);
assertEquals(List.of("BLOOD MOON", "REDLINE"), contracts.stream().map(BalanceTelemetrySegments.Segment::key).toList());
assertEquals(2, contracts.get(1).summary().runs());
assertTrue(contracts.get(1).summary().averageDps() > 0f);
⋮----
var mutators = BalanceTelemetrySegments.group(samples, BalanceTelemetrySegments.Dimension.MUTATOR);
assertEquals(List.of("BULWARK", "SWARM"), mutators.stream().map(BalanceTelemetrySegments.Segment::key).toList());
assertEquals(2, mutators.get(1).summary().runs());
⋮----
private static BalanceRunSample sample(int stage, int threat, boolean victory, String contract,
⋮----
return new BalanceRunSample(1, stage, threat, 1, victory, 100f, 20, 5000f, 800f, 300f, 80f,
```

## File: src/test/java/com/deadlinezero/game/meta/BalanceTelemetrySummaryTest.java
```java
final class BalanceTelemetrySummaryTest {
@Test void summaryComputesStableBalancingMetrics() {
⋮----
samples.add(new BalanceRunSample(1, 10, 2, 4, true, 60f, 30,
⋮----
samples.add(new BalanceRunSample(2, 10, 2, 5, false, 120f, 20,
⋮----
BalanceTelemetrySummary.Summary summary = BalanceTelemetrySummary.summarize(samples);
assertEquals(2, summary.runs());
assertEquals(1, summary.wins());
assertEquals(.5f, summary.winRate(), .0001f);
assertEquals(90f, summary.averageSeconds(), .0001f);
assertEquals(7.5f, summary.averageDps(), .0001f);
assertEquals(120f, summary.averageDamageTakenPerMinute(), .0001f);
assertEquals(20f, summary.averageKillsPerMinute(), .0001f);
⋮----
@Test void emptySummaryIsAllZero() {
BalanceTelemetrySummary.Summary summary = BalanceTelemetrySummary.summarize(new Array<>());
assertEquals(0, summary.runs());
assertEquals(0f, summary.winRate(), .0001f);
assertEquals(0f, summary.averageDps(), .0001f);
```

## File: src/test/java/com/deadlinezero/game/meta/ConsumablePurchaseDeliveryTest.java
```java
final class ConsumablePurchaseDeliveryTest {
@Test void grantIsPersistedBeforePlayConsumption() {
PlayerProfile profile = new PlayerProfile();
RecordingBilling billing = new RecordingBilling();
⋮----
boolean granted = ConsumablePurchaseDelivery.deliver(profile, billing, receipt,
⋮----
assertEquals(250L, profile.currency(PlayerProfile.Currency.GEMS));
assertTrue(profile.hasDeliveredPurchaseReceipt("token-1"));
events.add("persist");
⋮----
assertTrue(firstDelivery);
events.add("finalized");
⋮----
() -> events.add("failure"));
⋮----
assertTrue(granted);
assertEquals(List.of("persist", "consume", "finalized"), events);
⋮----
@Test void replayedReceiptDoesNotDuplicateCurrencyButIsStillFinalized() {
⋮----
assertTrue(ConsumablePurchaseDelivery.deliver(profile, billing, receipt, () -> {}, ignored -> {}, () -> {}));
assertEquals(1_200L, profile.currency(PlayerProfile.Currency.GEMS));
⋮----
boolean grantedAgain = ConsumablePurchaseDelivery.deliver(profile, billing, receipt,
() -> events.add("persist"),
⋮----
assertFalse(firstDelivery);
⋮----
assertFalse(grantedAgain);
⋮----
@Test void invalidReceiptNeverMutatesPersistsOrConsumes() {
⋮----
assertFalse(ConsumablePurchaseDelivery.deliver(profile, billing,
⋮----
() -> events.add("persist"), ignored -> events.add("finalized"), () -> events.add("failure")));
⋮----
assertEquals(0L, profile.currency(PlayerProfile.Currency.GEMS));
assertTrue(events.isEmpty());
⋮----
private static final class RecordingBilling implements BillingService {
⋮----
@Override public void initialize() {}
@Override public boolean owns(String productId) { return false; }
@Override public void purchase(String productId, Runnable onSuccess, Runnable onFailure) { onFailure.run(); }
@Override public void restore() {}
@Override public void finishConsumable(String receiptId, Runnable onSuccess, Runnable onFailure) {
events.add("consume");
onSuccess.run();
```

## File: src/test/java/com/deadlinezero/game/meta/CounterSafetyTest.java
```java
final class CounterSafetyTest {
@Test void longAdditionSaturatesInsteadOfWrapping() {
assertEquals(Long.MAX_VALUE, SaturatingMath.addPositive(Long.MAX_VALUE - 3L, 10L));
assertEquals(12L, SaturatingMath.addPositive(5L, 7L));
assertEquals(5L, SaturatingMath.addPositive(5L, -7L));
⋮----
@Test void dailyCountersSaturateAndIgnoreNegativeKills() {
assertEquals(Integer.MAX_VALUE, DailyCounterMath.increment(Integer.MAX_VALUE));
assertEquals(Integer.MAX_VALUE, DailyCounterMath.addKills(Integer.MAX_VALUE - 2, 10));
assertEquals(25, DailyCounterMath.addKills(25, -100));
assertEquals(31, DailyCounterMath.addKills(25, 6));
```

## File: src/test/java/com/deadlinezero/game/meta/DailyServiceTest.java
```java
public final class DailyServiceTest {
@Test public void loginRewardCannotBeClaimedTwice() {
PlayerProfile profile = new PlayerProfile();
DailyService.refresh(profile, 100L);
⋮----
assertTrue(DailyService.claimLogin(profile));
long credits = profile.currency(PlayerProfile.Currency.CREDITS);
long gems = profile.currency(PlayerProfile.Currency.GEMS);
⋮----
assertFalse(DailyService.claimLogin(profile));
assertEquals(credits, profile.currency(PlayerProfile.Currency.CREDITS));
assertEquals(gems, profile.currency(PlayerProfile.Currency.GEMS));
⋮----
@Test public void consecutiveDaysIncreaseStreakAndThirdDayGrantsGems() {
⋮----
DailyService.refresh(profile, 200L);
⋮----
DailyService.refresh(profile, 201L);
⋮----
DailyService.refresh(profile, 202L);
⋮----
long gemsBefore = profile.currency(PlayerProfile.Currency.GEMS);
assertEquals(3, profile.daily.loginStreak);
⋮----
assertEquals(gemsBefore + 2L, profile.currency(PlayerProfile.Currency.GEMS));
⋮----
@Test public void missionsRequireThresholdAndCannotDoublePay() {
⋮----
DailyService.refresh(profile, 300L);
⋮----
DailyService.recordRun(profile, 99, false);
assertFalse(DailyService.claimKillMission(profile));
assertFalse(DailyService.claimRunMission(profile));
assertFalse(DailyService.claimBossMission(profile));
⋮----
DailyService.recordRun(profile, 1, true);
DailyService.recordRun(profile, 0, false);
long creditsBefore = profile.currency(PlayerProfile.Currency.CREDITS);
⋮----
assertTrue(DailyService.claimKillMission(profile));
assertTrue(DailyService.claimRunMission(profile));
assertTrue(DailyService.claimBossMission(profile));
assertEquals(creditsBefore + 800L, profile.currency(PlayerProfile.Currency.CREDITS));
assertEquals(gemsBefore + 3L, profile.currency(PlayerProfile.Currency.GEMS));
⋮----
@Test public void missedDayResetsStreak() {
⋮----
DailyService.refresh(profile, 400L);
DailyService.refresh(profile, 401L);
DailyService.refresh(profile, 405L);
assertEquals(1, profile.daily.loginStreak);
```

## File: src/test/java/com/deadlinezero/game/meta/EndgameMutatorRulesTest.java
```java
final class EndgameMutatorRulesTest {
@AfterEach void reset() {
RunModifierContext.end();
RunStageContext.begin(1, 0, 0);
⋮----
@Test void mutatorsStayDisabledBelowThreatThree() {
⋮----
RunStageContext.begin(20, 7, threat);
assertEquals(EndgameMutatorRules.Mutator.NONE, EndgameMutatorRules.current());
assertFalse(EndgameMutatorRules.active());
assertEquals(1f, EndgameMutatorRules.rewardMultiplier());
⋮----
@Test void sameRunIdentityAlwaysProducesSameMutator() {
RunStageContext.begin(24, 13, 5);
EndgameMutatorRules.Mutator first = EndgameMutatorRules.current();
⋮----
assertEquals(first, EndgameMutatorRules.current());
assertNotEquals(EndgameMutatorRules.Mutator.NONE, first);
⋮----
@Test void highThreatRotationExposesMultiplePressureProfiles() {
boolean[] seen = new boolean[EndgameMutatorRules.Mutator.values().length];
⋮----
RunStageContext.begin(22, ordinal, 5);
int index = EndgameMutatorRules.current().ordinal();
⋮----
assertTrue(count >= 4);
⋮----
@Test void everyMutatorStaysInsideCombatAndEconomyBudget() {
for (EndgameMutatorRules.Mutator mutator : EndgameMutatorRules.Mutator.values()) {
assertTrue(mutator.enemyHp >= .90f && mutator.enemyHp <= 1.10f);
assertTrue(mutator.enemySpeed >= .98f && mutator.enemySpeed <= 1.05f);
assertTrue(mutator.enemyDamage >= 1f && mutator.enemyDamage <= 1.10f);
assertTrue(mutator.spawnInterval >= .88f && mutator.spawnInterval <= 1f);
assertTrue(mutator.reward >= 1f && mutator.reward <= 1.08f);
⋮----
@Test void compositeMutatorPressureNeverExceedsTwentyPercentOverlay() {
⋮----
/ Math.max(.01f, mutator.spawnInterval);
assertTrue(pressure >= .95f, mutator + " should remain a meaningful endgame profile");
assertTrue(pressure <= 1.20f, mutator + " exceeds the allowed composite pressure overlay: " + pressure);
⋮----
@Test void runModifierScalingIncludesCurrentMutatorWithoutChangingLowThreatRuns() {
RunStageContext.begin(10, 2, 0);
RunModifierContext.begin();
float lowHp = RunModifierContext.modifier().enemyHp;
float lowReward = RunModifierContext.modifier().reward;
assertEquals(lowHp, RunModifierContext.enemyHpMultiplier(), .0001f);
assertEquals(lowReward, RunModifierContext.rewardMultiplier(), .0001f);
⋮----
RunStageContext.begin(10, 2, 4);
⋮----
assertEquals(RunModifierContext.modifier().enemyHp * EndgameMutatorRules.enemyHpMultiplier(),
RunModifierContext.enemyHpMultiplier(), .0001f);
assertEquals(RunModifierContext.modifier().reward * EndgameMutatorRules.rewardMultiplier(),
RunModifierContext.rewardMultiplier(), .0001f);
⋮----
@Test void activeRunTitleSurfacesMutatorWithoutChangingContractIdentity() {
RunStageContext.begin(20, 9, 6);
⋮----
String contract = RunModifierContext.modifier().title;
assertTrue(RunModifierContext.title().startsWith(contract + " • "));
assertTrue(RunModifierContext.title().endsWith(EndgameMutatorRules.label()));
⋮----
BalanceTelemetryRuntime.setContract(contract);
BalanceRunSample sample = BalanceTelemetryRuntime.settle(false, 30f, 4);
assertEquals(contract, sample.contract());
assertEquals(EndgameMutatorRules.label(), sample.mutator());
```

## File: src/test/java/com/deadlinezero/game/meta/EquipmentUpgradeSafetyTest.java
```java
final class EquipmentUpgradeSafetyTest {
@Test void extremeLevelCostSaturatesInsteadOfOverflowingCheap() {
EquipmentItem item = item(Integer.MAX_VALUE, .10f);
assertEquals(Long.MAX_VALUE, EquipmentUpgradeService.cost(item));
⋮----
@Test void maxLevelUpgradeDoesNotChargeOrWrapLevel() {
PlayerProfile profile = fundedProfile();
⋮----
EquipmentItem result = EquipmentUpgradeService.upgrade(profile, item);
⋮----
assertSame(item, result);
assertEquals(Long.MAX_VALUE, profile.currency(PlayerProfile.Currency.CREDITS));
⋮----
@Test void nonRepresentablePowerUpgradeDoesNotChargeOrResetPower() {
⋮----
EquipmentItem item = item(10, Float.MAX_VALUE);
⋮----
assertTrue(result.powerBonus > 0f);
⋮----
private static PlayerProfile fundedProfile() {
PlayerProfile profile = new PlayerProfile();
profile.addCurrency(PlayerProfile.Currency.CREDITS, Long.MAX_VALUE);
⋮----
private static EquipmentItem item(int level, float power) {
return new EquipmentItem("safety", "Safety", PlayerProfile.EquipmentSlot.WEAPON,
```

## File: src/test/java/com/deadlinezero/game/meta/InventoryRestoreTest.java
```java
final class InventoryRestoreTest {
@Test void persistedExclusiveGearSurvivesAFullNormalInventory() {
Inventory inventory = new Inventory();
⋮----
assertTrue(inventory.restore(normal("normal_" + i)));
⋮----
EquipmentItem exclusive = ThreatMilestoneRewardCatalog.forTier(5);
assertTrue(inventory.restore(exclusive));
assertNotNull(inventory.find(exclusive.id));
assertEquals(Inventory.NORMAL_CAPACITY + 1, inventory.size());
⋮----
@Test void restoreStillRejectsOverflowAndDuplicateExclusiveGear() {
⋮----
for (int i = 0; i < Inventory.NORMAL_CAPACITY; i++) assertTrue(inventory.restore(normal("normal_" + i)));
⋮----
for (int tier : tiers) assertTrue(inventory.restore(ThreatMilestoneRewardCatalog.forTier(tier)));
⋮----
assertFalse(inventory.restore(ThreatMilestoneRewardCatalog.forTier(5)));
assertFalse(inventory.restore(normal("overflow")));
assertEquals(Inventory.MAX_ITEMS, inventory.size());
⋮----
private static EquipmentItem normal(String id) {
return new EquipmentItem(id, id, PlayerProfile.EquipmentSlot.ARMOR,
```

## File: src/test/java/com/deadlinezero/game/meta/MasteryEconomyGuardrailTest.java
```java
final class MasteryEconomyGuardrailTest {
@Test void fullMasteryEconomyRemainsFiniteAndNonPayToWin() {
⋮----
for (WeaponDefinition weapon : WeaponCatalog.all()) {
MasteryProgress mastery = new MasteryProgress();
⋮----
MasteryProgress.Gain gain = mastery.recordVictory(weapon.id, 1);
totalCredits += gain.creditsReward() - (gain.biomeRankAfter() > gain.biomeRankBefore() ? 260 : 0);
totalGems += gain.gemsReward() - (gain.biomeRankAfter() > gain.biomeRankBefore() ? 3 : 0);
⋮----
assertEquals(WeaponCatalog.all().length * MasteryProgress.MAX_RANK * 180, totalCredits);
⋮----
expectedGems += MasteryProgress.MAX_RANK * MasteryProgress.weaponGemsPerRank(weapon);
⋮----
assertEquals(expectedGems, totalGems);
assertTrue(totalGems <= 100, "weapon mastery gem budget drifted too high");
⋮----
@Test void biomeMasteryBudgetIsSmallAndOneTime() {
int credits = EnvironmentBiomeRules.Biome.values().length * MasteryProgress.MAX_RANK * 260;
int gems = EnvironmentBiomeRules.Biome.values().length * MasteryProgress.MAX_RANK * 3;
assertEquals(6500, credits);
assertEquals(75, gems);
⋮----
@Test void persistenceIdentifiersStaySafeAndUnique() {
⋮----
assertTrue(weapon.id.matches("[a-z0-9_]+"), weapon.id);
assertTrue(ids.add(weapon.id), "duplicate weapon mastery id: " + weapon.id);
⋮----
assertEquals(12, ids.size());
assertEquals(5, EnvironmentBiomeRules.Biome.values().length);
```

## File: src/test/java/com/deadlinezero/game/meta/MasteryProgressTest.java
```java
final class MasteryProgressTest {
@Test void weaponAndBiomeRanksFollowPermanentVictoryThresholds() {
MasteryProgress mastery = new MasteryProgress();
assertEquals(0, mastery.weaponRank(WeaponCatalog.AR9.id));
MasteryProgress.Gain first = mastery.recordVictory(WeaponCatalog.AR9.id, 1);
assertEquals(1, first.weaponRankAfter());
assertEquals(1, first.biomeRankAfter());
assertTrue(first.rankedUp());
assertEquals(440, first.creditsReward());
assertEquals(5, first.gemsReward());
⋮----
mastery.recordVictory(WeaponCatalog.AR9.id, 1);
MasteryProgress.Gain third = mastery.recordVictory(WeaponCatalog.AR9.id, 1);
assertEquals(2, third.weaponRankAfter());
assertEquals(2, third.biomeRankAfter());
assertEquals(440, third.creditsReward());
assertEquals(5, third.gemsReward());
assertEquals(4, mastery.winsForNextWeaponRank(WeaponCatalog.AR9.id));
⋮----
@Test void prestigeTitlesAreStableAndClamped() {
assertEquals("UNTRAINED", MasteryProgress.rankTitle(-5));
assertEquals("INITIATE", MasteryProgress.rankTitle(1));
assertEquals("SPECIALIST", MasteryProgress.rankTitle(2));
assertEquals("VETERAN", MasteryProgress.rankTitle(3));
assertEquals("ELITE", MasteryProgress.rankTitle(4));
assertEquals("ASCENDANT", MasteryProgress.rankTitle(5));
assertEquals("ASCENDANT", MasteryProgress.rankTitle(99));
⋮----
@Test void masterySeparatesWeaponsAndBiomes() {
⋮----
mastery.recordVictory(WeaponCatalog.CINDER_CANNON.id, 12);
assertEquals(1, mastery.weaponWins(WeaponCatalog.CINDER_CANNON.id));
assertEquals(0, mastery.weaponWins(WeaponCatalog.AR9.id));
assertEquals(1, mastery.biomeWins(EnvironmentBiomeRules.Biome.CINDER_FOUNDRY));
assertEquals(0, mastery.biomeWins(EnvironmentBiomeRules.Biome.NULL_SECTOR));
⋮----
@Test void noRepeatedRankRewardBetweenThresholds() {
⋮----
mastery.recordVictory(WeaponCatalog.AR9.id, 20);
MasteryProgress.Gain second = mastery.recordVictory(WeaponCatalog.AR9.id, 20);
assertFalse(second.rankedUp());
assertEquals(0, second.creditsReward());
assertEquals(0, second.gemsReward());
⋮----
@Test void maxRankIsBoundedAndSanitized() {
⋮----
mastery.setWeaponWins(WeaponCatalog.AR9.id, Integer.MAX_VALUE);
mastery.setBiomeWins(EnvironmentBiomeRules.Biome.NULL_SECTOR, -10);
assertEquals(MasteryProgress.MAX_RANK, mastery.weaponRank(WeaponCatalog.AR9.id));
assertEquals(0, mastery.winsForNextWeaponRank(WeaponCatalog.AR9.id));
```

## File: src/test/java/com/deadlinezero/game/meta/MasteryRunNoticeTest.java
```java
final class MasteryRunNoticeTest {
@AfterEach void clear() { MasteryRunNotice.clear(); }
⋮----
@Test void capturesOnlyRanksThatActuallyAdvanced() {
⋮----
MasteryRunNotice.capture(gain, "VX Rail Rifle", EnvironmentBiomeRules.Biome.NULL_SECTOR);
MasteryRunNotice.Notice notice = MasteryRunNotice.current();
assertTrue(notice.visible());
assertTrue(notice.weaponRankedUp());
assertEquals(2, notice.weaponRank());
assertEquals(0, notice.biomeRank());
assertEquals(180, notice.creditsReward());
assertEquals(2, notice.gemsReward());
⋮----
@Test void nonRankVictoryClearsStaleNotice() {
MasteryRunNotice.capture(new MasteryProgress.Gain(0, 1, 0, 1, 440, 5),
⋮----
MasteryRunNotice.capture(new MasteryProgress.Gain(1, 1, 1, 1, 0, 0),
⋮----
assertNull(MasteryRunNotice.current());
```

## File: src/test/java/com/deadlinezero/game/meta/OnboardingCompletionPolicyTest.java
```java
final class OnboardingCompletionPolicyTest {
@Test void requiresEveryTutorialMilestone() {
assertFalse(OnboardingCompletionPolicy.completed(false, false, false, false));
assertFalse(OnboardingCompletionPolicy.completed(true, false, true, true));
assertFalse(OnboardingCompletionPolicy.completed(true, true, false, true));
assertFalse(OnboardingCompletionPolicy.completed(true, true, true, false));
assertTrue(OnboardingCompletionPolicy.completed(true, true, true, true));
```

## File: src/test/java/com/deadlinezero/game/meta/P0RunRegressionTest.java
```java
/** Cross-system P0 regressions for run settlement, persistence and interrupted-run safety. */
final class P0RunRegressionTest {
⋮----
static void startHeadlessGdx() {
app = new HeadlessApplication(new ApplicationListener() {
@Override public void create() { }
@Override public void resize(int width, int height) { }
@Override public void render() { }
@Override public void pause() { }
@Override public void resume() { }
@Override public void dispose() { }
⋮----
static void stopHeadlessGdx() {
if (app != null) app.exit();
⋮----
void resetState() {
Preferences preferences = Gdx.app.getPreferences("deadline-zero-profile-v1");
preferences.clear();
preferences.flush();
RunMissionRuntime.end();
RunModifierContext.end();
RunEncounterRuntime.end();
RunLoadoutContext.end();
RunStageContext.begin(1, 0, 0);
⋮----
void repeatedVictorySignalSettlesOnlyOnce() {
PlayerProfile profile = ProfileStore.load();
long creditsBefore = profile.currency(PlayerProfile.Currency.CREDITS);
⋮----
RunMissionRuntime.begin(() -> RunSettlement.apply(profile, 20, 180f, true, 1));
RunMissionRuntime.update(180f, 20);
⋮----
RunMissionRuntime.signalBossDefeated();
long creditsAfterFirstSignal = profile.currency(PlayerProfile.Currency.CREDITS);
⋮----
assertEquals(runsBefore + 1, runsAfterFirstSignal);
assertEquals(runsAfterFirstSignal, profile.totalRuns);
assertTrue(creditsAfterFirstSignal > creditsBefore);
assertEquals(creditsAfterFirstSignal, profile.currency(PlayerProfile.Currency.CREDITS));
⋮----
void completedSettlementSurvivesSaveReloadWithoutDuplication() {
⋮----
profile.addCurrency(PlayerProfile.Currency.CREDITS, 100L);
⋮----
RunSettlement.apply(profile, 17, 125f, false, 1);
long expectedCredits = profile.currency(PlayerProfile.Currency.CREDITS);
long expectedGems = profile.currency(PlayerProfile.Currency.GEMS);
⋮----
ProfileStore.save(profile);
PlayerProfile restored = ProfileStore.load();
⋮----
assertEquals(expectedCredits, restored.currency(PlayerProfile.Currency.CREDITS));
assertEquals(expectedGems, restored.currency(PlayerProfile.Currency.GEMS));
assertEquals(expectedXp, restored.accountXp);
assertEquals(expectedRuns, restored.totalRuns);
assertEquals(expectedKills, restored.totalKills);
⋮----
ProfileStore.save(restored);
PlayerProfile restoredAgain = ProfileStore.load();
assertEquals(expectedCredits, restoredAgain.currency(PlayerProfile.Currency.CREDITS));
assertEquals(expectedGems, restoredAgain.currency(PlayerProfile.Currency.GEMS));
assertEquals(expectedXp, restoredAgain.accountXp);
assertEquals(expectedRuns, restoredAgain.totalRuns);
assertEquals(expectedKills, restoredAgain.totalKills);
⋮----
void interruptedRunEndsWithoutGrantingSettlementRewards() {
⋮----
long gemsBefore = profile.currency(PlayerProfile.Currency.GEMS);
⋮----
RunMissionRuntime.begin(() -> RunSettlement.apply(profile, 50, 300f, true, 1));
RunMissionRuntime.update(240f, 39);
⋮----
assertEquals(creditsBefore, profile.currency(PlayerProfile.Currency.CREDITS));
assertEquals(gemsBefore, profile.currency(PlayerProfile.Currency.GEMS));
assertEquals(xpBefore, profile.accountXp);
assertEquals(runsBefore, profile.totalRuns);
assertEquals(killsBefore, profile.totalKills);
```

## File: src/test/java/com/deadlinezero/game/meta/PlayerProfileSafetyTest.java
```java
final class PlayerProfileSafetyTest {
@Test void currenciesSaturateInsteadOfWrapping() {
PlayerProfile profile = new PlayerProfile();
profile.addCurrency(PlayerProfile.Currency.CREDITS, Long.MAX_VALUE - 2L);
profile.addCurrency(PlayerProfile.Currency.CREDITS, 10L);
assertEquals(Long.MAX_VALUE, profile.currency(PlayerProfile.Currency.CREDITS));
⋮----
@Test void runCountersSaturateAndIgnoreNegativeKills() {
⋮----
profile.recordRun(10, 4);
assertEquals(Integer.MAX_VALUE, profile.totalRuns);
assertEquals(Long.MAX_VALUE, profile.totalKills);
⋮----
profile.recordRun(-100, 4);
assertEquals(25L, profile.totalKills);
⋮----
@Test void accountXpPreservesNormalThresholds() {
⋮----
profile.addAccountXp(249L);
assertEquals(1, profile.accountLevel);
assertEquals(249L, profile.accountXp);
⋮----
profile.addAccountXp(1L);
assertEquals(2, profile.accountLevel);
assertEquals(0L, profile.accountXp);
⋮----
profile.addAccountXp(360L);
assertEquals(3, profile.accountLevel);
⋮----
@Test void extremeAccountXpAdvancesWithoutLinearLevelLoop() {
ProfileCounterMath.LevelProgress progress = ProfileCounterMath.advanceAccountXp(1, 0L, Long.MAX_VALUE);
assertTrue(progress.level() > 1_000_000);
assertTrue(progress.level() < Integer.MAX_VALUE);
assertTrue(progress.xp() >= 0L);
assertTrue(progress.xp() < ProfileCounterMath.xpForLevel(progress.level()));
⋮----
@Test void maxLevelNeverCarriesUnboundedXp() {
ProfileCounterMath.LevelProgress progress = ProfileCounterMath.advanceAccountXp(
⋮----
assertEquals(Integer.MAX_VALUE, progress.level());
assertEquals(ProfileCounterMath.xpForLevel(Integer.MAX_VALUE) - 1L, progress.xp());
⋮----
@Test void restoredProfileIsCanonicalizedBeforeUse() {
⋮----
profile.normalizeLoadedState();
⋮----
assertEquals(1, profile.highestStage);
assertEquals(1, profile.selectedStage);
assertEquals(0, profile.totalRuns);
assertEquals(0L, profile.totalKills);
⋮----
@Test void nonFiniteEquipmentPowerCannotPoisonAggregateStats() {
⋮----
EquipmentItem item = new EquipmentItem("bad", "Bad Save", PlayerProfile.EquipmentSlot.ARMOR,
⋮----
profile.equip(item);
assertEquals(0f, item.powerBonus);
assertEquals(1f, profile.aggregatePowerMultiplier());
⋮----
EquipmentItem infinite = new EquipmentItem("inf", "Infinite Save", PlayerProfile.EquipmentSlot.CORE,
⋮----
assertEquals(0f, infinite.powerBonus);
```

## File: src/test/java/com/deadlinezero/game/meta/ProfileBackupCodecTest.java
```java
final class ProfileBackupCodecTest {
@Test void roundTripsEverySupportedPreferenceTypeDeterministically() {
⋮----
values.put("z-string", "héllo\nworld");
values.put("a-int", 42);
values.put("long", 9_000_000_000L);
values.put("float", 1.25f);
values.put("bool", true);
values.put(ProfileSchema.VERSION_KEY, ProfileSchema.CURRENT_VERSION);
⋮----
String a = ProfileBackupCodec.encode(values);
String b = ProfileBackupCodec.encode(new LinkedHashMap<>(values));
⋮----
assertEquals(a, b);
assertEquals(values, ProfileBackupCodec.decode(a));
assertEquals(ProfileSchema.CURRENT_VERSION, ProfileBackupCodec.schemaVersion(ProfileBackupCodec.decode(a)));
⋮----
@Test void tamperingIsRejectedBeforeImport() {
String backup = ProfileBackupCodec.encode(Map.of("credits", 123L));
assertThrows(IllegalArgumentException.class, () -> ProfileBackupCodec.decode(backup + "x"));
⋮----
@Test void duplicateKeysAreRejected() throws Exception {
String valid = ProfileBackupCodec.encode(Map.of("credits", 123L));
int first = valid.indexOf('\n');
int second = valid.indexOf('\n', first + 1);
String body = valid.substring(second + 1);
⋮----
String duplicate = valid.substring(0, first + 1) + sha256(duplicateBody) + "\n" + duplicateBody;
⋮----
assertThrows(IllegalArgumentException.class, () -> ProfileBackupCodec.decode(duplicate));
⋮----
@Test void unsupportedValueTypesAreRejected() {
assertThrows(IllegalArgumentException.class, () -> ProfileBackupCodec.encode(Map.of("bad", 1.0d)));
⋮----
private static String sha256(String value) throws Exception {
byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
StringBuilder out = new StringBuilder(digest.length * 2);
for (byte b : digest) out.append(String.format("%02x", b));
return out.toString();
```

## File: src/test/java/com/deadlinezero/game/meta/ProfileBackupSummaryTest.java
```java
final class ProfileBackupSummaryTest {
@Test void dominanceRequiresNoRegressionAcrossMonotoneProgress() {
ProfileBackupSummary local = ProfileBackupSummary.from(Map.of(
⋮----
ProfileBackupSummary remote = ProfileBackupSummary.from(Map.of(
⋮----
assertTrue(local.dominates(remote));
assertFalse(remote.dominates(local));
⋮----
@Test void conflictingProgressIsNotDominance() {
ProfileBackupSummary moreStage = ProfileBackupSummary.from(Map.of(
⋮----
ProfileBackupSummary moreHistory = ProfileBackupSummary.from(Map.of(
⋮----
assertFalse(moreStage.dominates(moreHistory));
assertFalse(moreHistory.dominates(moreStage));
```

## File: src/test/java/com/deadlinezero/game/meta/ProfileMigrationDocumentationTest.java
```java
final class ProfileMigrationDocumentationTest {
@Test void documentedSchemaVersionMatchesRuntimeSchema() throws Exception {
Path root = repositoryRoot();
String contract = Files.readString(root.resolve("docs/PROFILE_MIGRATIONS.md"), StandardCharsets.UTF_8);
⋮----
assertTrue(contract.contains("- Current version: `" + ProfileSchema.CURRENT_VERSION + "`"),
⋮----
assertTrue(contract.contains("Version `1 -> 2`"));
assertTrue(contract.contains("Version `2 -> 3`"));
assertTrue(contract.contains("newer unsupported schema remains untouched"));
⋮----
private static Path repositoryRoot() {
Path current = Path.of("").toAbsolutePath().normalize();
for (Path candidate = current; candidate != null; candidate = candidate.getParent()) {
if (Files.isRegularFile(candidate.resolve("docs/PROFILE_MIGRATIONS.md"))
&& Files.isRegularFile(candidate.resolve("settings.gradle"))) {
⋮----
throw new IllegalStateException("Unable to locate repository root from " + current);
```

## File: src/test/java/com/deadlinezero/game/meta/ProfileSchemaTest.java
```java
final class ProfileSchemaTest {
@Test void legacyProfileIsStampedWithoutLosingExistingValues() {
MemoryStore store = new MemoryStore();
store.values.put("credits", 12500);
store.values.put("inventory.count", 120);
store.values.put("purchase.receipt.count", 7);
store.values.put("survivor.REX.level", 12);
⋮----
assertTrue(ProfileSchema.migrate(store));
⋮----
assertEquals(ProfileSchema.CURRENT_VERSION, store.getInteger(ProfileSchema.VERSION_KEY, -1));
assertEquals(12500, store.getInteger("credits", -1));
assertEquals(120, store.getInteger("inventory.count", -1));
assertEquals(7, store.getInteger("purchase.receipt.count", -1));
assertEquals(12, store.getInteger("survivor.REX.level", -1));
assertEquals(1, store.flushes);
⋮----
@Test void v1ProfileMigratesThroughV3WithoutLosingProgress() {
⋮----
store.putInteger(ProfileSchema.VERSION_KEY, 1);
store.putInteger("credits", 4321);
store.putInteger("daily.runs", 2);
⋮----
assertEquals(3, store.getInteger(ProfileSchema.VERSION_KEY, -1));
assertEquals(4321, store.getInteger("credits", -1));
assertEquals(2, store.getInteger("daily.runs", -1));
⋮----
@Test void v2ProfileMigratesToV3WithoutLosingWeeklyProgress() {
⋮----
store.putInteger(ProfileSchema.VERSION_KEY, 2);
store.putInteger("weekly.kills", 760);
store.putInteger("weekly.runs", 11);
⋮----
assertEquals(760, store.getInteger("weekly.kills", -1));
assertEquals(11, store.getInteger("weekly.runs", -1));
⋮----
@Test void currentSchemaMigrationIsIdempotent() {
⋮----
store.putInteger(ProfileSchema.VERSION_KEY, ProfileSchema.CURRENT_VERSION);
store.putInteger("gems", 999);
⋮----
assertEquals(999, store.getInteger("gems", -1));
assertEquals(0, store.flushes);
⋮----
@Test void newerSchemaIsPreservedAndRejectedForWrites() {
⋮----
store.putInteger(ProfileSchema.VERSION_KEY, ProfileSchema.CURRENT_VERSION + 3);
store.putInteger("credits", 777);
⋮----
assertFalse(ProfileSchema.migrate(store));
⋮----
assertEquals(ProfileSchema.CURRENT_VERSION + 3, store.getInteger(ProfileSchema.VERSION_KEY, -1));
assertEquals(777, store.getInteger("credits", -1));
⋮----
@Test void corruptNegativeVersionIsTreatedAsLegacy() {
⋮----
store.putInteger(ProfileSchema.VERSION_KEY, -42);
store.putInteger("accountLevel", 5);
⋮----
assertEquals(5, store.getInteger("accountLevel", -1));
⋮----
private static final class MemoryStore implements ProfileSchema.Store {
⋮----
@Override public int getInteger(String key, int defaultValue) {
return values.getOrDefault(key, defaultValue);
⋮----
@Override public void putInteger(String key, int value) {
values.put(key, value);
⋮----
@Override public void flush() {
```

## File: src/test/java/com/deadlinezero/game/meta/ProfileStoreBackupTest.java
```java
final class ProfileStoreBackupTest {
⋮----
@BeforeAll static void startGdx() {
app = new HeadlessApplication(new ApplicationAdapter() {}, new HeadlessApplicationConfiguration());
⋮----
@AfterAll static void stopGdx() {
if (app != null) app.exit();
⋮----
@Test void typePoisonedBackupRollsBackToOriginalReadableProfile() {
Preferences prefs = Gdx.app.getPreferences(PREFS);
prefs.clear();
prefs.putInteger(ProfileSchema.VERSION_KEY, ProfileSchema.CURRENT_VERSION);
prefs.putInteger("accountLevel", 7);
prefs.putLong("credits", 321L);
prefs.flush();
⋮----
Map<String, Object> poisoned = new HashMap<>(prefs.get());
poisoned.put("accountLevel", "not-an-integer");
String backup = ProfileBackupCodec.encode(poisoned);
⋮----
assertThrows(RuntimeException.class, () -> ProfileStore.importBackup(backup));
⋮----
assertEquals(7, prefs.getInteger("accountLevel", -1));
assertEquals(321L, prefs.getLong("credits", -1L));
assertEquals(7, ProfileStore.load().accountLevel);
```

## File: src/test/java/com/deadlinezero/game/meta/PurchaseGrantServiceTest.java
```java
public final class PurchaseGrantServiceTest {
@Test public void removeAdsIsPermanentAndIdempotent() {
PlayerProfile profile = new PlayerProfile();
assertTrue(PurchaseGrantService.grant(profile, BillingService.REMOVE_ADS));
assertTrue(profile.removeAdsPurchased);
assertFalse(PurchaseGrantService.grant(profile, BillingService.REMOVE_ADS));
⋮----
@Test public void starterPackPaysExactlyOnce() {
⋮----
assertTrue(PurchaseGrantService.grant(profile, BillingService.STARTER_PACK));
assertEquals(5_000L, profile.currency(PlayerProfile.Currency.CREDITS));
assertEquals(250L, profile.currency(PlayerProfile.Currency.GEMS));
⋮----
assertFalse(PurchaseGrantService.grant(profile, BillingService.STARTER_PACK));
⋮----
@Test public void gemProductsRemainConsumableWithDistinctReceipts() {
⋮----
assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, "receipt-small-1"));
assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, "receipt-small-2"));
assertEquals(500L, profile.currency(PlayerProfile.Currency.GEMS));
⋮----
assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_LARGE, "receipt-large-1"));
assertEquals(1_700L, profile.currency(PlayerProfile.Currency.GEMS));
⋮----
@Test public void consumableWithoutReceiptIsRejected() {
⋮----
assertFalse(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL));
assertFalse(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, null));
assertFalse(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, ""));
assertFalse(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, "   "));
⋮----
assertEquals(0L, profile.currency(PlayerProfile.Currency.GEMS));
assertTrue(profile.deliveredPurchaseReceipts().isEmpty());
⋮----
@Test public void sameConsumableReceiptCanOnlyBeGrantedOnce() {
⋮----
assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, receipt));
⋮----
assertTrue(profile.hasDeliveredPurchaseReceipt(receipt));
⋮----
assertFalse(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, receipt));
⋮----
@Test public void differentConsumableReceiptsStillStack() {
⋮----
assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, "receipt-a"));
assertTrue(PurchaseGrantService.grant(profile, BillingService.GEMS_SMALL, "receipt-b"));
⋮----
@Test public void restoreOnlyRehydratesPermanentEntitlements() {
⋮----
FakeBilling billing = new FakeBilling(true, BillingService.REMOVE_ADS, BillingService.STARTER_PACK, BillingService.GEMS_LARGE);
⋮----
assertTrue(PurchaseGrantService.syncPermanent(profile, billing));
⋮----
assertTrue(profile.starterPackGranted);
⋮----
assertFalse(PurchaseGrantService.syncPermanent(profile, billing));
⋮----
@Test public void cachedRemoveAdsSurvivesUntilStoreSnapshotIsAuthoritative() {
⋮----
assertFalse(PurchaseGrantService.syncPermanent(profile, new FakeBilling(false)));
⋮----
@Test public void authoritativeStoreSnapshotRevokesStaleRemoveAds() {
⋮----
assertTrue(PurchaseGrantService.syncPermanent(profile, new FakeBilling(true)));
assertFalse(profile.removeAdsPurchased);
⋮----
@Test public void unknownProductCannotMutateProfile() {
⋮----
assertFalse(PurchaseGrantService.grant(profile, "unknown_product"));
assertEquals(0L, profile.currency(PlayerProfile.Currency.CREDITS));
⋮----
private static final class FakeBilling implements BillingService {
⋮----
for (String id : ids) owned.add(id);
⋮----
@Override public void initialize() { }
@Override public boolean owns(String productId) { return owned.contains(productId); }
@Override public boolean authoritativeEntitlements() { return authoritative; }
@Override public void purchase(String productId, Runnable onSuccess, Runnable onFailure) { onFailure.run(); }
@Override public void restore() { }
```

## File: src/test/java/com/deadlinezero/game/meta/ReviewPromptPolicyTest.java
```java
final class ReviewPromptPolicyTest {
@Test void onlyMeaningfulFirstClearIsEligible() {
assertFalse(ReviewPromptPolicy.eligible(false, 5, false));
assertFalse(ReviewPromptPolicy.eligible(true, 1, false));
assertFalse(ReviewPromptPolicy.eligible(true, 2, false));
assertTrue(ReviewPromptPolicy.eligible(true, 3, false));
assertTrue(ReviewPromptPolicy.eligible(true, 20, false));
assertFalse(ReviewPromptPolicy.eligible(true, 20, true));
```

## File: src/test/java/com/deadlinezero/game/meta/RunLoadoutContextResetTest.java
```java
final class RunLoadoutContextResetTest {
@Test void endRestoresSafeDefaultLoadoutState() {
PlayerProfile profile = new PlayerProfile();
⋮----
RunLoadoutContext.begin(profile);
RunLoadoutContext.end();
⋮----
assertEquals(1f, RunLoadoutContext.maxHpMultiplier(), 0.0001f);
assertEquals(1f, RunLoadoutContext.moveSpeedMultiplier(), 0.0001f);
assertEquals(1f, RunLoadoutContext.dashCooldownMultiplier(), 0.0001f);
assertEquals(.30f, RunLoadoutContext.dashInvulnerabilitySeconds(), 0.0001f);
assertEquals(1f, RunLoadoutContext.weaponDamageMultiplier(), 0.0001f);
assertEquals(0f, RunLoadoutContext.critChanceBonus(), 0.0001f);
assertEquals(0f, RunLoadoutContext.critDamageBonus(), 0.0001f);
assertEquals(1f, RunLoadoutContext.abilityPowerMultiplier(), 0.0001f);
assertEquals(1f, RunLoadoutContext.damageTakenMultiplier(), 0.0001f);
assertEquals(0, RunLoadoutContext.startingTeslaLevel());
assertEquals(0, RunLoadoutContext.ascensionSetPieces());
assertFalse(RunLoadoutContext.zeroDayCoreEquipped());
assertEquals(SurvivorCatalog.Survivor.REX, RunLoadoutContext.survivor());
assertEquals(WeaponCatalog.AR9.id, RunLoadoutContext.weaponDefinition().id);
assertEquals(WeaponSynergyRules.Synergy.NONE, RunLoadoutContext.weaponSynergy());
```

## File: src/test/java/com/deadlinezero/game/meta/RunMissionRuntimeTest.java
```java
final class RunMissionRuntimeTest {
@AfterEach void cleanup() { RunMissionRuntime.end(); }
⋮----
@Test void standardMissionSignalsVictoryAfterOneBoss() {
AtomicInteger callbacks = new AtomicInteger();
RunMissionRuntime.begin(callbacks::incrementAndGet, 1);
RunMissionRuntime.signalBossDefeated();
assertEquals(1, RunMissionRuntime.bossKills());
assertEquals(1, callbacks.get());
⋮----
@Test void twinMissionWaitsForSecondBossAndSignalsOnce() {
⋮----
RunMissionRuntime.begin(callbacks::incrementAndGet, 2);
⋮----
assertEquals(0, callbacks.get());
⋮----
assertEquals(2, RunMissionRuntime.bossKills());
⋮----
@Test void requiredBossCountIsSanitized() {
RunMissionRuntime.begin(() -> {}, 0);
assertEquals(1, RunMissionRuntime.requiredBossKills());
```

## File: src/test/java/com/deadlinezero/game/meta/RunModifierContextTest.java
```java
final class RunModifierContextTest {
private static final EnumSet<RunModifierContext.Modifier> STANDARD = EnumSet.of(
⋮----
@AfterEach void cleanup() { RunModifierContext.end(); }
⋮----
@Test void selectionIsDeterministicForSameStageAndRunOrdinal() {
RunStageContext.begin(7, 12);
RunModifierContext.begin();
RunModifierContext.Modifier first = RunModifierContext.modifier();
RunModifierContext.end();
⋮----
assertEquals(first, RunModifierContext.modifier());
⋮----
@Test void consecutiveFallbackRunsNeverRepeatTheSameStandardContract() {
⋮----
RunStageContext.begin(9, ordinal);
⋮----
RunModifierContext.Modifier current = RunModifierContext.modifier();
assertTrue(STANDARD.contains(current));
if (previous != null) assertNotEquals(previous, current);
⋮----
@Test void fallbackRotationExposesEveryStandardContractWithinFiveRuns() {
EnumSet<RunModifierContext.Modifier> seen = EnumSet.noneOf(RunModifierContext.Modifier.class);
⋮----
seen.add(RunModifierContext.modifier());
⋮----
assertEquals(STANDARD, seen);
⋮----
@Test void offerSetContainsThreeUniqueDeterministicContracts() {
RunStageContext.begin(11, 27);
RunModifierContext.Modifier[] first = RunModifierContext.offers();
RunModifierContext.Modifier[] second = RunModifierContext.offers();
assertEquals(3, first.length);
assertEquals(first[0], second[0]);
assertEquals(first[1], second[1]);
assertEquals(first[2], second[2]);
assertEquals(3, EnumSet.of(first[0], first[1], first[2]).size());
⋮----
@Test void legendaryOfferAppearsOnDeterministicCadenceAfterStageThree() {
RunStageContext.begin(3, 1);
assertTrue(RunModifierContext.legendaryOfferAvailable());
RunModifierContext.Modifier[] offers = RunModifierContext.offers();
assertTrue(offers[2].legendary());
assertTrue(offers[2].rewardBonusPercent() >= 48);
⋮----
RunStageContext.begin(2, 2);
assertFalse(RunModifierContext.legendaryOfferAvailable());
for (RunModifierContext.Modifier offer : RunModifierContext.offers()) assertFalse(offer.legendary());
⋮----
@Test void legendaryRotationExposesAllThreeLegendaryMutators() {
⋮----
RunStageContext.begin(stage, ordinal);
if (RunModifierContext.legendaryOfferAvailable()) {
RunModifierContext.Modifier legendary = RunModifierContext.offers()[2];
assertTrue(legendary.legendary());
seen.add(legendary);
⋮----
assertEquals(EnumSet.of(RunModifierContext.Modifier.PHANTOM_ECLIPSE,
⋮----
@Test void onlyOfferedContractsCanBeActivated() {
RunStageContext.begin(4, 2);
⋮----
assertTrue(RunModifierContext.choose(offers[1]));
assertEquals(offers[1], RunModifierContext.modifier());
⋮----
for (RunModifierContext.Modifier candidate : RunModifierContext.Modifier.values()) {
⋮----
assertNotNull(outsider);
assertFalse(RunModifierContext.choose(outsider));
assertFalse(RunModifierContext.active());
⋮----
@Test void activeContractAlwaysPaysARewardPremium() {
RunStageContext.begin(6, 3);
⋮----
assertNotNull(RunModifierContext.modifier());
assertTrue(RunModifierContext.rewardMultiplier() > 1f);
assertTrue(RunModifierContext.rewardBonusPercent() >= 18);
⋮----
@Test void rewardCalculatorIncludesActiveContractPremium() {
⋮----
RunRewardCalculator.Rewards baseline = RunRewardCalculator.calculate(120, 240f, true, 6);
RunStageContext.begin(6, 18);
⋮----
RunRewardCalculator.Rewards contracted = RunRewardCalculator.calculate(120, 240f, true, 6);
assertTrue(contracted.credits() > baseline.credits());
assertTrue(contracted.accountXp() > baseline.accountXp());
assertEquals(baseline.gems(), contracted.gems());
⋮----
@Test void twinApexRequiresTwoBossDefeatsWhenOffered() {
⋮----
for (RunModifierContext.Modifier offer : RunModifierContext.offers()) {
⋮----
assertTrue(RunModifierContext.choose(offer));
assertEquals(2, RunModifierContext.requiredBossKills());
⋮----
assertTrue(found);
⋮----
@Test void stageRulesApplyCombatContractMultipliers() {
⋮----
float hp = StageRules.enemyHpMultiplier(8);
float speed = StageRules.enemySpeedMultiplier(8);
float damage = StageRules.enemyDamageMultiplier(8);
RunStageContext.begin(8, 5);
⋮----
RunModifierContext.Modifier modifier = RunModifierContext.modifier();
assertEquals(hp * modifier.enemyHp, StageRules.enemyHpMultiplier(8), .0001f);
assertEquals(Math.min(1.60f, speed * modifier.enemySpeed), StageRules.enemySpeedMultiplier(8), .0001f);
assertEquals(damage * modifier.enemyDamage, StageRules.enemyDamageMultiplier(8), .0001f);
```

## File: src/test/java/com/deadlinezero/game/meta/RunRecoveryAdviceTest.java
```java
final class RunRecoveryAdviceTest {
@Test void earlyHighThreatFailureRecommendsSurvivability() {
RunResult result = result(20, 6, 20f, 8);
RunRecoveryAdvice.Advice advice = RunRecoveryAdvice.forResult(result);
assertEquals(RunRecoveryAdvice.Focus.SURVIVABILITY, advice.focus());
assertTrue(advice.detail().contains("Threat"));
⋮----
@Test void slowMidRunClearRecommendsOffense() {
// Stage 10 targets 495s; 300s is past the opening gate while 8 kills is clearly below target clear speed.
RunResult result = result(10, 0, 300f, 8);
⋮----
assertEquals(RunRecoveryAdvice.Focus.OFFENSE, advice.focus());
⋮----
@Test void lateFailureWithGoodClearSpeedRecommendsFinalDefense() {
⋮----
float nearBoss = StageMissionRules.bossArrivalSeconds(stage) * .90f;
// Keep clear speed above the 14 KPM offense threshold so late-run defense is the deciding branch.
RunResult result = result(stage, 2, nearBoss, 150);
⋮----
assertEquals(RunRecoveryAdvice.Focus.ENDGAME_DEFENSE, advice.focus());
⋮----
@Test void adviceIsPureAndNullSafe() {
assertEquals(RunRecoveryAdvice.Focus.BALANCED, RunRecoveryAdvice.forResult(null).focus());
⋮----
private static RunResult result(int stage, int threat, float seconds, int kills) {
return new RunResult(kills, seconds, false, stage,
```

## File: src/test/java/com/deadlinezero/game/meta/RunRewardCalculatorTest.java
```java
final class RunRewardCalculatorTest {
@Test void ordinaryRewardsRemainStable() {
var rewards = RunRewardCalculator.calculate(60, 120f, true, 1);
assertEquals(345L, rewards.credits());
assertEquals(195L, rewards.accountXp());
assertEquals(1, rewards.gems());
⋮----
@Test void largeRunsKeepLongPrecisionInsteadOfNarrowingToInt() {
var rewards = RunRewardCalculator.calculate(Integer.MAX_VALUE, 0f, false, 100);
assertTrue(rewards.credits() > Integer.MAX_VALUE);
assertTrue(rewards.accountXp() > Integer.MAX_VALUE);
⋮----
@Test void scalingSaturatesSafelyAtLongLimit() {
assertEquals(Long.MAX_VALUE, ProfileCounterMath.scaleNonNegative(Long.MAX_VALUE, 2f));
assertEquals(0L, ProfileCounterMath.scaleNonNegative(100L, Float.NaN));
assertEquals(0L, ProfileCounterMath.scaleNonNegative(-100L, 2f));
```

## File: src/test/java/com/deadlinezero/game/meta/RunShareTextTest.java
```java
final class RunShareTextTest {
@Test void shareTextContainsOnlySettledRunFactsAndPlayLink() {
RunResult result = new RunResult(87, 154f, true, 20,
⋮----
String text = RunShareText.format(result);
assertTrue(text.contains("Stage 20"));
assertTrue(text.contains("Threat 6"));
assertTrue(text.contains("87 kills"));
assertTrue(text.contains("02:34"));
assertTrue(text.contains("REDLINE • SWARM"));
assertTrue(text.endsWith(RunShareText.PLAY_URL));
assertFalse(text.toLowerCase().contains("best"));
assertFalse(text.toLowerCase().contains("million"));
⋮----
@Test void threatZeroDoesNotPretendAscensionWasActive() {
RunResult result = new RunResult(10, 65f, true, 2,
⋮----
assertFalse(text.contains("Threat 0"));
assertTrue(text.contains("01:05"));
⋮----
@Test void contractTextCannotInjectExtraLines() {
RunResult result = new RunResult(1, 1f, true, 1,
⋮----
assertTrue(text.contains("Contract: REDLINE FAKE CLAIM"));
```

## File: src/test/java/com/deadlinezero/game/meta/SingularityCoreRuntimeTest.java
```java
final class SingularityCoreRuntimeTest {
@AfterEach void cleanup() { SingularityCoreRuntime.begin(false); }
⋮----
@Test void disabledRuntimeNeverMarksShots() {
SingularityCoreRuntime.begin(false);
for (int i = 0; i < 12; i++) assertFalse(SingularityCoreRuntime.consumeShotMark());
assertEquals(0L, SingularityCoreRuntime.shotSequence());
⋮----
@Test void activeRuntimeMarksExactlyEverySixthShot() {
SingularityCoreRuntime.begin(true);
⋮----
assertEquals(i % SingularityCoreRules.SHOT_INTERVAL == 0, SingularityCoreRuntime.consumeShotMark());
⋮----
assertEquals(18L, SingularityCoreRuntime.shotSequence());
⋮----
@Test void beginResetsCadenceForEveryRun() {
⋮----
for (int i = 0; i < 5; i++) assertFalse(SingularityCoreRuntime.consumeShotMark());
⋮----
assertTrue(SingularityCoreRuntime.consumeShotMark());
⋮----
@Test void sixthProjectileBecomesSingularityShockRound() {
⋮----
projectile = new Projectile().spawn(0f, 0f, 10f, 0f, 100f, false, 1, 2f, DamageElement.KINETIC);
⋮----
assertTrue(projectile.singularity);
assertEquals(135f, projectile.damage, .0001f);
assertEquals(3, projectile.penetrationRemaining);
assertEquals(3.6f, projectile.knockback, .0001f);
assertEquals(.16f, projectile.radius, .0001f);
assertEquals(DamageElement.SHOCK, projectile.element);
⋮----
@Test void ordinaryProjectilesKeepOriginalCombatProfile() {
⋮----
Projectile projectile = new Projectile().spawn(0f, 0f, 10f, 0f, 100f, true, 1, 2f, DamageElement.FIRE);
assertFalse(projectile.singularity);
assertEquals(100f, projectile.damage, .0001f);
assertEquals(1, projectile.penetrationRemaining);
assertEquals(2f, projectile.knockback, .0001f);
assertEquals(.11f, projectile.radius, .0001f);
assertEquals(DamageElement.FIRE, projectile.element);
```

## File: src/test/java/com/deadlinezero/game/meta/StageRulesTest.java
```java
final class StageRulesTest {
⋮----
void stageOneUsesNeutralMultipliers() {
assertEquals(1f, StageRules.enemyHpMultiplier(1), 0.0001f);
assertEquals(1f, StageRules.enemyDamageMultiplier(1), 0.0001f);
assertEquals(1f, StageRules.enemySpeedMultiplier(1), 0.0001f);
assertEquals(1f, StageRules.rewardMultiplier(1), 0.0001f);
⋮----
void invalidStagesClampToStageOne() {
assertEquals(StageRules.enemyHpMultiplier(1), StageRules.enemyHpMultiplier(-12), 0.0001f);
assertEquals(StageRules.enemyDamageMultiplier(1), StageRules.enemyDamageMultiplier(0), 0.0001f);
assertEquals(2, StageRules.nextStage(0));
⋮----
void scalingRemainsMonotonicAcrossCampaignRange() {
float previousHp = StageRules.enemyHpMultiplier(1);
float previousDamage = StageRules.enemyDamageMultiplier(1);
float previousReward = StageRules.rewardMultiplier(1);
⋮----
float hp = StageRules.enemyHpMultiplier(stage);
float damage = StageRules.enemyDamageMultiplier(stage);
float reward = StageRules.rewardMultiplier(stage);
assertTrue(hp > previousHp);
assertTrue(damage > previousDamage);
assertTrue(reward > previousReward);
⋮----
void enemySpeedHasHardSafetyCap() {
assertTrue(StageRules.enemySpeedMultiplier(1000) <= 1.42f);
assertEquals(1.42f, StageRules.enemySpeedMultiplier(1000), 0.0001f);
⋮----
void nextStageAlwaysAdvancesExactlyOneFromClampedInput() {
assertEquals(2, StageRules.nextStage(1));
assertEquals(26, StageRules.nextStage(25));
```

## File: src/test/java/com/deadlinezero/game/meta/SurvivorProgressionSafetyTest.java
```java
final class SurvivorProgressionSafetyTest {
@Test void exactThresholdLevelsNormally() {
SurvivorProgression progression = new SurvivorProgression();
progression.addXp(SurvivorCatalog.Survivor.REX, 180L);
assertEquals(2, progression.level(SurvivorCatalog.Survivor.REX));
assertEquals(0L, progression.xp(SurvivorCatalog.Survivor.REX));
⋮----
@Test void extremeXpRemainsCanonicalWithoutOverflow() {
⋮----
progression.addXp(SurvivorCatalog.Survivor.REX, Long.MAX_VALUE);
⋮----
int level = progression.level(SurvivorCatalog.Survivor.REX);
long xp = progression.xp(SurvivorCatalog.Survivor.REX);
assertTrue(level > 1_000_000);
assertTrue(xp >= 0L);
assertTrue(xp < progression.xpForNext(SurvivorCatalog.Survivor.REX));
⋮----
@Test void corruptedPersistedXpIsNormalizedOnRestore() {
⋮----
progression.setState(SurvivorCatalog.Survivor.NYX, 1, Long.MAX_VALUE, true);
⋮----
assertTrue(progression.level(SurvivorCatalog.Survivor.NYX) > 1);
assertTrue(progression.xp(SurvivorCatalog.Survivor.NYX) >= 0L);
assertTrue(progression.xp(SurvivorCatalog.Survivor.NYX) < progression.xpForNext(SurvivorCatalog.Survivor.NYX));
assertTrue(progression.unlocked(SurvivorCatalog.Survivor.NYX));
⋮----
@Test void addingToExistingProgressCannotWrapNegative() {
⋮----
progression.setState(SurvivorCatalog.Survivor.REX, 50, 100L, true);
⋮----
assertTrue(progression.xp(SurvivorCatalog.Survivor.REX) >= 0L);
assertTrue(progression.xp(SurvivorCatalog.Survivor.REX) < progression.xpForNext(SurvivorCatalog.Survivor.REX));
```

## File: src/test/java/com/deadlinezero/game/meta/ThreatProgressionServiceTest.java
```java
final class ThreatProgressionServiceTest {
@Test void clearBeforeEndgameDoesNotUnlockThreat() {
PlayerProfile profile = new PlayerProfile();
⋮----
ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 9, 0);
assertFalse(result.unlocked());
assertEquals(0, profile.highestThreatTier);
⋮----
@Test void clearingHighestThreatUnlocksExactlyOneTier() {
⋮----
ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 10, 3);
assertTrue(result.unlocked());
assertEquals(4, result.tier());
assertEquals(4, profile.highestThreatTier);
assertEquals(4, profile.selectedThreatTier);
⋮----
@Test void lowerThreatClearCannotAdvanceHighestThreat() {
⋮----
ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 12, 3);
⋮----
assertEquals(6, profile.highestThreatTier);
⋮----
@Test void milestoneGemsAndMythicGearAreGrantedOnlyOnFirstUnlock() {
⋮----
ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 10, 4);
⋮----
assertEquals(5, result.tier());
assertEquals(6, result.milestoneGems());
assertEquals(6L, profile.currency(PlayerProfile.Currency.GEMS));
⋮----
EquipmentItem reward = profile.inventory.find("threat_05_helmet");
assertNotNull(reward);
assertEquals(EquipmentItem.Rarity.MYTHIC, reward.rarity);
assertEquals(PlayerProfile.EquipmentSlot.HELMET, reward.slot);
⋮----
int itemCount = profile.inventory.size();
ThreatProgressionService.UnlockResult duplicate = ThreatProgressionService.applyBossClear(profile, 10, 4);
assertFalse(duplicate.unlocked());
⋮----
assertEquals(itemCount, profile.inventory.size());
⋮----
@Test void milestoneGearUsesReservedCapacityWhenNormalInventoryIsFull() {
⋮----
assertTrue(profile.inventory.add(new EquipmentItem("normal_" + i, "Normal " + i,
⋮----
assertTrue(profile.inventory.full());
⋮----
assertNotNull(profile.inventory.find("threat_05_helmet"));
assertEquals(Inventory.NORMAL_CAPACITY + 1, profile.inventory.size());
⋮----
@Test void allMilestoneRewardsAreUniqueAndMappedToExpectedTiers() {
⋮----
EquipmentItem item = ThreatMilestoneRewardCatalog.forTier(tiers[i]);
assertNotNull(item);
assertEquals(EquipmentItem.Rarity.MYTHIC, item.rarity);
⋮----
for (int j = i + 1; j < ids.length; j++) assertFalse(ids[i].equals(ids[j]));
⋮----
@Test void maxThreatCannotOverflow() {
⋮----
ThreatProgressionService.UnlockResult result = ThreatProgressionService.applyBossClear(profile, 30, ThreatTierRules.MAX_TIER);
⋮----
assertEquals(ThreatTierRules.MAX_TIER, profile.highestThreatTier);
```

## File: src/test/java/com/deadlinezero/game/meta/ThreatSetBonusRulesTest.java
```java
final class ThreatSetBonusRulesTest {
@Test void setCountsOnlyEquippedExclusiveMilestoneItems() {
PlayerProfile profile = new PlayerProfile();
EquipmentItem helm = ThreatMilestoneRewardCatalog.forTier(5);
EquipmentItem gloves = ThreatMilestoneRewardCatalog.forTier(10);
EquipmentItem ordinary = new EquipmentItem("ordinary", "Ordinary Armor",
⋮----
profile.inventory.add(helm);
profile.inventory.add(gloves);
profile.inventory.add(ordinary);
EquipmentService.equip(profile, helm.id);
EquipmentService.equip(profile, gloves.id);
EquipmentService.equip(profile, ordinary.id);
assertEquals(2, ThreatSetBonusRules.equippedPieces(profile));
⋮----
@Test void twoPieceBonusImprovesWeaponAndAbilityOnly() {
assertEquals(1.08f, ThreatSetBonusRules.weaponMultiplier(2), .0001f);
assertEquals(1.08f, ThreatSetBonusRules.abilityMultiplier(2), .0001f);
assertEquals(1f, ThreatSetBonusRules.hpMultiplier(2), .0001f);
assertEquals(1f, ThreatSetBonusRules.moveSpeedMultiplier(2), .0001f);
⋮----
@Test void threePieceBonusAddsDurabilityAndMobility() {
assertEquals(1.05f, ThreatSetBonusRules.hpMultiplier(3), .0001f);
assertEquals(1.06f, ThreatSetBonusRules.moveSpeedMultiplier(3), .0001f);
assertEquals(1f, ThreatSetBonusRules.damageTakenMultiplier(3), .0001f);
⋮----
@Test void fourPieceBonusAddsDefenseAndDashWindow() {
assertEquals(.90f, ThreatSetBonusRules.damageTakenMultiplier(4), .0001f);
assertEquals(.06f, ThreatSetBonusRules.dashInvulnerabilityBonus(4), .0001f);
⋮----
@Test void runLoadoutSnapshotsEquippedAscensionSet() {
⋮----
EquipmentItem item = ThreatMilestoneRewardCatalog.forTier(tier);
profile.inventory.add(item);
EquipmentService.equip(profile, item.id);
⋮----
RunLoadoutContext.begin(profile);
assertEquals(4, RunLoadoutContext.ascensionSetPieces());
assertTrue(RunLoadoutContext.weaponDamageMultiplier() > 1f);
assertTrue(RunLoadoutContext.abilityPowerMultiplier() > 1f);
assertTrue(RunLoadoutContext.damageTakenMultiplier() < 1f);
assertTrue(RunLoadoutContext.dashInvulnerabilitySeconds() >= .36f);
```

## File: src/test/java/com/deadlinezero/game/meta/ThreatTierRulesTest.java
```java
final class ThreatTierRulesTest {
@AfterEach void cleanup() {
RunModifierContext.end();
RunStageContext.begin(1, 0, 0);
⋮----
@Test void threatRemainsLockedBeforeStageTen() {
PlayerProfile profile = new PlayerProfile();
⋮----
profile.normalizeLoadedState();
assertFalse(ThreatTierRules.unlocked(profile));
assertEquals(0, profile.highestThreatTier);
assertEquals(0, profile.selectedThreatTier);
assertFalse(profile.selectThreatTier(1));
⋮----
@Test void unlockedProfileCanSelectOnlyEarnedThreat() {
⋮----
assertTrue(ThreatTierRules.unlocked(profile));
assertTrue(profile.selectThreatTier(4));
assertEquals(4, profile.selectedThreatTier);
assertFalse(profile.selectThreatTier(5));
⋮----
@Test void nextThreatUnlocksSequentiallyAndCapsAtTwenty() {
⋮----
assertTrue(profile.unlockNextThreatTier());
assertEquals(1, profile.highestThreatTier);
assertEquals(1, profile.selectedThreatTier);
⋮----
assertFalse(profile.unlockNextThreatTier());
assertEquals(ThreatTierRules.MAX_TIER, profile.highestThreatTier);
⋮----
@Test void threatTierRaisesCombatAndRewardScaling() {
RunStageContext.begin(10, 0, 0);
float hp0 = StageRules.enemyHpMultiplier(10);
float damage0 = StageRules.enemyDamageMultiplier(10);
float speed0 = StageRules.enemySpeedMultiplier(10);
float reward0 = StageRules.rewardMultiplier(10);
⋮----
RunStageContext.begin(10, 0, 8);
assertTrue(StageRules.enemyHpMultiplier(10) > hp0);
assertTrue(StageRules.enemyDamageMultiplier(10) > damage0);
assertTrue(StageRules.enemySpeedMultiplier(10) > speed0);
assertTrue(StageRules.rewardMultiplier(10) > reward0);
⋮----
@Test void threatTierAlsoAcceleratesSpawnPressure() {
⋮----
RunModifierContext.begin();
RunModifierContext.Modifier baselineContract = RunModifierContext.modifier();
float standardPressure = RunModifierContext.spawnIntervalMultiplier();
⋮----
RunStageContext.begin(10, 0, 10);
⋮----
assertEquals(baselineContract, RunModifierContext.modifier(),
⋮----
float ascendedPressure = RunModifierContext.spawnIntervalMultiplier();
⋮----
* EndgameMutatorRules.spawnIntervalMultiplier()
* ThreatTierRules.spawnIntervalMultiplier(10);
⋮----
assertTrue(ascendedPressure < standardPressure);
assertEquals(expected, ascendedPressure, .0001f);
⋮----
@Test void milestoneGemsAreOnlyPaidEveryFiveTiers() {
assertEquals(0, ThreatTierRules.milestoneGemReward(4));
assertEquals(6, ThreatTierRules.milestoneGemReward(5));
assertEquals(8, ThreatTierRules.milestoneGemReward(10));
assertEquals(12, ThreatTierRules.milestoneGemReward(20));
⋮----
@Test void runSeedIncludesThreatTier() {
RunStageContext.begin(12, 33, 0);
int base = RunStageContext.encounterSeed();
RunStageContext.begin(12, 33, 1);
int ascended = RunStageContext.encounterSeed();
assertTrue(base != ascended);
```

## File: src/test/java/com/deadlinezero/game/meta/WeaponProgressionTest.java
```java
final class WeaponProgressionTest {
⋮----
void starterWeaponIsAlwaysAvailable() {
assertTrue(WeaponProgression.unlocked(null, WeaponCatalog.AR9));
PlayerProfile profile = new PlayerProfile();
assertTrue(WeaponProgression.unlocked(profile, WeaponCatalog.AR9));
assertEquals(WeaponCatalog.AR9, profile.selectedWeapon());
⋮----
void lockedWeaponCannotBeSelectedEarly() {
⋮----
assertFalse(profile.selectWeapon(WeaponCatalog.RAIL_RIFLE));
⋮----
void accountLevelUnlocksWeaponSelection() {
⋮----
assertTrue(profile.selectWeapon(WeaponCatalog.ARC_CARBINE));
assertEquals(WeaponCatalog.ARC_CARBINE, profile.selectedWeapon());
⋮----
void stageProgressCanUnlockControlWeaponsEarlier() {
⋮----
assertTrue(WeaponProgression.unlocked(profile, WeaponCatalog.ARC_CARBINE));
⋮----
void endgameWeaponsUnlockInDeterministicOrder() {
⋮----
assertFalse(WeaponProgression.unlocked(profile, WeaponCatalog.TEMPEST_BURST));
⋮----
assertTrue(WeaponProgression.unlocked(profile, WeaponCatalog.TEMPEST_BURST));
assertFalse(WeaponProgression.unlocked(profile, WeaponCatalog.WHITEOUT_SHARD));
⋮----
assertTrue(WeaponProgression.unlocked(profile, WeaponCatalog.WHITEOUT_SHARD));
assertFalse(WeaponProgression.unlocked(profile, WeaponCatalog.PHOENIX_REPEATER));
⋮----
assertTrue(profile.selectWeapon(WeaponCatalog.PHOENIX_REPEATER));
assertEquals(WeaponCatalog.PHOENIX_REPEATER, profile.selectedWeapon());
⋮----
void stageProgressAlsoUnlocksEndgameWeapons() {
⋮----
assertTrue(WeaponProgression.unlocked(profile, WeaponCatalog.PHOENIX_REPEATER));
⋮----
void invalidStoredWeaponFallsBackSafely() {
⋮----
profile.validateSelectedWeapon();
```

## File: src/test/java/com/deadlinezero/game/meta/WeaponSynergyRulesTest.java
```java
final class WeaponSynergyRulesTest {
@Test void resolvesOnlyIntentionalSignaturePairs() {
assertEquals(WeaponSynergyRules.Synergy.ARC_CONDUCTOR,
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.VOLT, WeaponCatalog.ION_NEEDLE));
assertEquals(WeaponSynergyRules.Synergy.EXECUTION_PROTOCOL,
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.NYX, WeaponCatalog.ION_NEEDLE));
assertEquals(WeaponSynergyRules.Synergy.SIEGE_FURNACE,
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.BASTION, WeaponCatalog.CINDER_CANNON));
assertEquals(WeaponSynergyRules.Synergy.CRYO_GHOST,
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.WRAITH, WeaponCatalog.CRYO_LANCE));
assertEquals(WeaponSynergyRules.Synergy.TEMPEST_CIRCUIT,
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.VOLT, WeaponCatalog.TEMPEST_BURST));
assertEquals(WeaponSynergyRules.Synergy.WHITEOUT_GHOST,
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.WRAITH, WeaponCatalog.WHITEOUT_SHARD));
assertEquals(WeaponSynergyRules.Synergy.PHOENIX_BULWARK,
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.BASTION, WeaponCatalog.PHOENIX_REPEATER));
assertEquals(WeaponSynergyRules.Synergy.NONE,
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.REX, WeaponCatalog.ION_NEEDLE));
⋮----
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.VOLT, WeaponCatalog.AR9));
⋮----
WeaponSynergyRules.resolve(SurvivorCatalog.Survivor.NYX, WeaponCatalog.PHOENIX_REPEATER));
⋮----
@Test void synergyBonusesStayInsideSafePowerBudget() {
for (WeaponSynergyRules.Synergy synergy : WeaponSynergyRules.Synergy.values()) {
assertTrue(synergy.weaponDamageMultiplier >= 1f && synergy.weaponDamageMultiplier <= 1.12f);
assertTrue(synergy.abilityPowerMultiplier >= 1f && synergy.abilityPowerMultiplier <= 1.10f);
assertTrue(synergy.critChanceBonus >= 0f && synergy.critChanceBonus <= .06f);
assertTrue(synergy.damageTakenMultiplier >= .95f && synergy.damageTakenMultiplier <= 1f);
⋮----
@Test void nullInputsFallBackWithoutGrantingFreeSynergy() {
assertEquals(WeaponSynergyRules.Synergy.NONE, WeaponSynergyRules.resolve(null, null));
```

## File: src/test/java/com/deadlinezero/game/meta/WeeklyServiceTest.java
```java
public final class WeeklyServiceTest {
@Test public void utcWeeksRollOverOnMonday() {
assertEquals(0L, WeeklyService.weekIndexForEpochDay(0L));
assertEquals(0L, WeeklyService.weekIndexForEpochDay(3L));
assertEquals(1L, WeeklyService.weekIndexForEpochDay(4L));
assertEquals(-1L, WeeklyService.weekIndexForEpochDay(-4L));
⋮----
@Test public void sameWeekKeepsProgressAndNextWeekResets() {
PlayerProfile profile = new PlayerProfile();
WeeklyService.refresh(profile, 100L);
WeeklyService.recordRun(profile, 250, true);
⋮----
WeeklyService.refresh(profile, 101L);
assertEquals(week, profile.weekly.weekIndex);
assertEquals(250, profile.weekly.kills);
assertEquals(1, profile.weekly.runs);
assertEquals(1, profile.weekly.bosses);
⋮----
WeeklyService.refresh(profile, 107L);
assertTrue(profile.weekly.weekIndex > week);
assertEquals(0, profile.weekly.kills);
assertEquals(0, profile.weekly.runs);
assertEquals(0, profile.weekly.bosses);
⋮----
@Test public void weeklyClaimsRequireTargetsAndCannotDoublePay() {
⋮----
WeeklyService.refresh(profile, 200L);
⋮----
WeeklyService.recordRun(profile, i == 0 ? WeeklyService.KILL_TARGET : 0, i < WeeklyService.BOSS_TARGET);
⋮----
long creditsBefore = profile.currency(PlayerProfile.Currency.CREDITS);
long gemsBefore = profile.currency(PlayerProfile.Currency.GEMS);
⋮----
assertTrue(WeeklyService.claimKillMission(profile));
assertTrue(WeeklyService.claimRunMission(profile));
assertTrue(WeeklyService.claimBossMission(profile));
assertEquals(creditsBefore + 6000L, profile.currency(PlayerProfile.Currency.CREDITS));
assertEquals(gemsBefore + 12L, profile.currency(PlayerProfile.Currency.GEMS));
⋮----
assertFalse(WeeklyService.claimKillMission(profile));
assertFalse(WeeklyService.claimRunMission(profile));
assertFalse(WeeklyService.claimBossMission(profile));
⋮----
@Test public void countersClampInsteadOfOverflowing() {
⋮----
WeeklyService.recordRun(profile, 50, true);
assertEquals(Integer.MAX_VALUE, profile.weekly.kills);
assertEquals(Integer.MAX_VALUE, profile.weekly.runs);
assertEquals(Integer.MAX_VALUE, profile.weekly.bosses);
```

## File: src/test/java/com/deadlinezero/game/perf/AdaptiveFrameRateGovernorTest.java
```java
final class AdaptiveFrameRateGovernorTest {
@Test void startsAtSelectedTarget() {
AdaptiveFrameRateGovernor governor = new AdaptiveFrameRateGovernor();
governor.reset(120);
assertEquals(120, governor.effectiveTarget());
⋮----
@Test void sustainedInstabilityStepsDownOneTier() {
⋮----
governor.update(120, bad);
⋮----
assertEquals(90, governor.effectiveTarget());
⋮----
@Test void recoveryIsSlowerThanDegradation() {
⋮----
for (int i = 0; i < 3; i++) governor.update(120, bad120);
⋮----
for (int i = 0; i < 7; i++) governor.update(120, stable90);
⋮----
governor.update(120, stable90);
⋮----
@Test void userCeilingAlwaysWinsImmediately() {
⋮----
assertEquals(60, governor.update(60, stable120));
⋮----
@Test void normalizesUnsupportedTargets() {
⋮----
governor.reset(75);
assertEquals(60, governor.effectiveTarget());
governor.reset(100);
⋮----
governor.reset(144);
```

## File: src/test/java/com/deadlinezero/game/perf/PerformanceTelemetryTest.java
```java
final class PerformanceTelemetryTest {
@Test void stableSixtyFpsWindowPasses() {
PerformanceTelemetry telemetry = new PerformanceTelemetry();
for (int i = 0; i < 120; i++) telemetry.record(1f / 60f, 60);
PerformanceTelemetry.Snapshot s = telemetry.snapshot(60);
assertEquals(60, s.targetFps());
assertTrue(s.averageFps() >= 59.9f);
assertTrue(s.p95FrameMs() < 17f);
assertEquals(0f, s.jankRatio(), .0001f);
assertTrue(s.stable());
⋮----
@Test void sustainedSlowFramesFailTarget() {
⋮----
for (int i = 0; i < 120; i++) telemetry.record(1f / 45f, 60);
⋮----
assertTrue(s.averageFps() < 50f);
assertFalse(s.stable());
⋮----
@Test void jankBurstsAreDetected() {
⋮----
for (int i = 0; i < 110; i++) telemetry.record(1f / 60f, 60);
for (int i = 0; i < 10; i++) telemetry.record(.050f, 60);
⋮----
assertTrue(s.jankRatio() > .05f);
assertTrue(s.p95FrameMs() >= 50f);
⋮----
@Test void invalidSamplesAreIgnored() {
⋮----
telemetry.record(0f, 60);
telemetry.record(Float.NaN, 60);
telemetry.record(-1f, 60);
assertEquals(0, telemetry.sampleCount());
assertFalse(telemetry.snapshot(60).stable());
⋮----
@Test void rollingWindowIsBounded() {
⋮----
for (int i = 0; i < 1000; i++) telemetry.record(1f / 120f, 120);
assertEquals(240, telemetry.sampleCount());
assertTrue(telemetry.snapshot(120).stable());
```

## File: src/test/java/com/deadlinezero/game/perf/ThermalBudgetPolicyTest.java
```java
final class ThermalBudgetPolicyTest {
@Test void userTargetWinsWhenBelowThermalCeiling() {
assertEquals(60, ThermalBudgetPolicy.allowedFps(60, ThermalService.Level.NORMAL));
assertEquals(90, ThermalBudgetPolicy.allowedFps(90, ThermalService.Level.LIGHT));
⋮----
@Test void thermalPressureCapsHighFrameRateTargets() {
assertEquals(90, ThermalBudgetPolicy.allowedFps(120, ThermalService.Level.MODERATE));
assertEquals(60, ThermalBudgetPolicy.allowedFps(120, ThermalService.Level.SEVERE));
assertEquals(60, ThermalBudgetPolicy.allowedFps(120, ThermalService.Level.CRITICAL));
⋮----
@Test void unsupportedUserTargetsNormalizeToSupportedTiers() {
assertEquals(60, ThermalBudgetPolicy.allowedFps(75, ThermalService.Level.NORMAL));
assertEquals(90, ThermalBudgetPolicy.allowedFps(100, ThermalService.Level.NORMAL));
assertEquals(120, ThermalBudgetPolicy.allowedFps(144, ThermalService.Level.NORMAL));
⋮----
@Test void fxCeilingTracksThermalLevel() {
assertEquals(1.00f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.NORMAL), .0001f);
assertEquals(.92f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.LIGHT), .0001f);
assertEquals(.76f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.MODERATE), .0001f);
assertEquals(.58f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.SEVERE), .0001f);
assertEquals(.46f, ThermalBudgetPolicy.fxCeiling(ThermalService.Level.CRITICAL), .0001f);
⋮----
@Test void nullThermalLevelFailsOpenToUnknownPolicy() {
assertEquals(120, ThermalBudgetPolicy.allowedFps(120, null));
assertEquals(1.00f, ThermalBudgetPolicy.fxCeiling(null), .0001f);
```

## File: src/test/java/com/deadlinezero/game/progression/CombatProtocolStateTest.java
```java
final class CombatProtocolStateTest {
@Test void rhythmEmpowersEverySixthVolley() {
CombatProtocolState state = new CombatProtocolState();
state.enableRhythm();
for (int i = 0; i < 5; i++) assertEquals(1f, state.onVolley().damageMultiplier(), .0001f);
var proc = state.onVolley();
assertTrue(proc.forcedCrit());
assertEquals(1.30f, proc.damageMultiplier(), .0001f);
assertEquals(1f, state.onVolley().damageMultiplier(), .0001f);
⋮----
@Test void killchainArmsAndConsumesNextVolley() {
⋮----
state.enableKillchain();
for (int i = 0; i < 7; i++) state.onKill();
assertFalse(state.killchainArmed());
state.onKill();
assertTrue(state.killchainArmed());
⋮----
assertEquals(1.45f, proc.damageMultiplier(), .0001f);
assertEquals(1, proc.bonusPenetration());
⋮----
@Test void simultaneousProcsCombineWithoutUnboundedStacking() {
⋮----
for (int i = 0; i < 5; i++) state.onVolley();
for (int i = 0; i < 8; i++) state.onKill();
⋮----
assertEquals(1.75f, proc.damageMultiplier(), .0001f);
⋮----
@Test void evolvedRhythmProcsEveryFourthVolleyAtHigherDamage() {
⋮----
state.evolveRhythm();
for (int i = 0; i < 3; i++) assertEquals(1f, state.onVolley().damageMultiplier(), .0001f);
⋮----
@Test void evolvedKillchainArmsAfterFiveKillsWithTwoPenetration() {
⋮----
state.evolveKillchain();
for (int i = 0; i < 4; i++) assertFalse(state.onKill());
assertTrue(state.onKill());
⋮----
assertEquals(1.60f, proc.damageMultiplier(), .0001f);
assertEquals(2, proc.bonusPenetration());
⋮----
@Test void evolvedCombinedProcRemainsCapped() {
⋮----
for (int i = 0; i < 3; i++) state.onVolley();
for (int i = 0; i < 5; i++) state.onKill();
⋮----
assertEquals(2.05f, proc.damageMultiplier(), .0001f);
⋮----
@Test void reactionCoreOnlyAmplifiesRealElementReactions() {
⋮----
state.enableReactionCore();
assertEquals(0f, state.reactionBonus(100f, Enemy.ElementReaction.NONE), .0001f);
assertEquals(35f, state.reactionBonus(100f, Enemy.ElementReaction.OVERLOAD), .0001f);
assertEquals(0f, state.reactionBonus(-5f, Enemy.ElementReaction.THERMAL_SHOCK), .0001f);
⋮----
@Test void evolvedReactionCoreRaisesBonusToFiftyFivePercent() {
⋮----
state.evolveReactionCore();
assertEquals(55f, state.reactionBonus(100f, Enemy.ElementReaction.OVERLOAD), .0001f);
⋮----
@Test void evolutionsCannotActivateBeforeTheirBaseProtocol() {
⋮----
assertFalse(state.rhythmEvolved());
assertFalse(state.killchainEvolved());
assertFalse(state.reactionEvolved());
```

## File: src/test/java/com/deadlinezero/game/progression/LegendarySelectorTest.java
```java
public final class LegendarySelectorTest {
@Test public void offersBeginAtLevelEight() {
Player p = new Player(0f, 0f);
⋮----
assertFalse(LegendarySelector.shouldOffer(p));
⋮----
assertTrue(LegendarySelector.shouldOffer(p));
⋮----
@Test public void selectorNeverReturnsOwnedChoice() {
⋮----
assertTrue(LegendaryEffects.applyOverdrive(p));
⋮----
int count = LegendarySelector.fillChoices(p, choices);
assertEquals(3, count);
⋮----
assertNotNull(choices[i]);
assertTrue(choices[i] != LegendaryChoice.OVERDRIVE);
⋮----
@Test public void weaponFamilyChoiceKeepsOffersAliveAfterGenericChoicesAreOwned() {
⋮----
assertTrue(LegendaryEffects.applySingularity(p));
assertTrue(LegendaryEffects.applyApex(p));
⋮----
assertTrue(LegendaryChoice.VANGUARD_PROTOCOL.eligible(p));
assertTrue(LegendaryEffects.applyVanguardProtocol(p));
```

## File: src/test/java/com/deadlinezero/game/progression/LegendaryStateTest.java
```java
public final class LegendaryStateTest {
@Test public void grantsAreOneShot() {
LegendaryState state = new LegendaryState();
assertFalse(state.hasAny());
assertTrue(state.grantOverdrive());
assertFalse(state.grantOverdrive());
assertTrue(state.hasOverdrive());
assertTrue(state.hasAny());
⋮----
@Test public void flagsRemainIndependent() {
⋮----
assertTrue(state.grantSingularity());
assertTrue(state.grantApex());
assertFalse(state.hasOverdrive());
assertTrue(state.hasSingularity());
assertTrue(state.hasApex());
⋮----
@Test public void overdriveTransformsWeaponAndMobilityOnce() {
Player player = new Player(0f, 0f);
⋮----
assertTrue(LegendaryEffects.applyOverdrive(player));
assertFalse(LegendaryEffects.applyOverdrive(player));
assertTrue(player.weapon.damage > damage);
assertTrue(player.weapon.fireInterval < interval);
assertTrue(player.moveSpeed > speed);
⋮----
@Test public void singularityTransformsBallisticsOnce() {
⋮----
assertTrue(LegendaryEffects.applySingularity(player));
assertFalse(LegendaryEffects.applySingularity(player));
assertEquals(projectiles + 2, player.weapon.projectileCount);
assertEquals(penetration + 3, player.weapon.penetration);
⋮----
@Test public void apexEstablishesEveryAbilityAtTierTwo() {
⋮----
assertTrue(LegendaryEffects.applyApex(player));
assertFalse(LegendaryEffects.applyApex(player));
for (AbilityType type : AbilityType.values()) assertTrue(player.abilities.tier(type) >= 2);
```

## File: src/test/java/com/deadlinezero/game/progression/ProtocolUpgradeGuidanceTest.java
```java
final class ProtocolUpgradeGuidanceTest {
private Player fresh() {
RunLoadoutContext.end();
return new Player(0f, 0f);
⋮----
@Test void baseProtocolChoicesShowUnlockGuidance() {
Player p = fresh();
assertEquals("combat.protocolGuidance.unlock",
ProtocolUpgradeGuidance.key(p, Upgrade.RHYTHM_DRIVER));
⋮----
ProtocolUpgradeGuidance.key(p, Upgrade.KILLCHAIN_CAPACITOR));
⋮----
ProtocolUpgradeGuidance.key(p, Upgrade.REACTION_CORE));
⋮----
@Test void evolutionChoiceShowsEvolutionGuidanceAfterBase() {
⋮----
Upgrade.RHYTHM_DRIVER.apply(p);
assertEquals("combat.protocolGuidance.evolution",
ProtocolUpgradeGuidance.key(p, Upgrade.RHYTHM_ACCELERATOR));
⋮----
@Test void evolutionGuidanceDisappearsAfterEvolution() {
⋮----
Upgrade.KILLCHAIN_CAPACITOR.apply(p);
Upgrade.KILLCHAIN_OVERCHARGE.apply(p);
assertNull(ProtocolUpgradeGuidance.key(p, Upgrade.KILLCHAIN_OVERCHARGE));
⋮----
@Test void nonProtocolUpgradeHasNoProtocolGuidance() {
assertNull(ProtocolUpgradeGuidance.key(fresh(), Upgrade.DAMAGE));
```

## File: src/test/java/com/deadlinezero/game/progression/RemainingWeaponFamilyLegendaryBalanceTest.java
```java
final class RemainingWeaponFamilyLegendaryBalanceTest {
@Test void ar9VanguardStaysInsideAssaultPowerBudget() {
⋮----
assertTrue(dpsMultiplier >= 1.26f && dpsMultiplier <= 1.31f);
⋮----
@Test void scatterMaelstromControlsVolleyGrowth() {
⋮----
assertTrue(volleyMultiplier >= 1.08f && volleyMultiplier <= 1.11f);
⋮----
@Test void infernoPyroclasmControlsSustainedGrowth() {
⋮----
assertTrue(dpsMultiplier >= 1.30f && dpsMultiplier <= 1.33f);
⋮----
@Test void breacherRuptureControlsPelletGrowth() {
```

## File: src/test/java/com/deadlinezero/game/progression/UpgradeDraftPolicyTest.java
```java
final class UpgradeDraftPolicyTest {
private Player freshPlayer() {
RunLoadoutContext.end();
return new Player(0f, 0f);
⋮----
@Test void kineticFreshRunKeepsBroadDraftPool() {
Player player = freshPlayer();
assertFalse(UpgradeDraftPolicy.hasEstablishedBuild(player));
assertEquals(1f, UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.FIRE_CONTROL), .0001f);
assertEquals(1f, UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.TESLA_ORB), .0001f);
⋮----
@Test void elementalBuildStronglyPrefersMatchingFamilyAndDeemphasizesConflicts() {
⋮----
assertTrue(UpgradeDraftPolicy.hasEstablishedBuild(player));
assertTrue(UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.FIRE_CONTROL) >= 2f);
assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.THERMAL_LANCE));
assertTrue(UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.CRYO_HAMMER) < 1f);
assertFalse(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.CRYO_HAMMER));
⋮----
@Test void investedAbilityPrefersItsOwnTreeAndKnownSynergyPartners() {
⋮----
player.abilities.upgrade(AbilityType.TESLA_ORB);
⋮----
assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.TESLA_ORB));
assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.CRYO_NOVA));
assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.DRONE));
assertFalse(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.MISSILE_SWARM));
⋮----
@Test void maturePartnerRaisesSynergyOfferWeight() {
⋮----
player.abilities.upgrade(AbilityType.CRYO_NOVA);
⋮----
assertTrue(UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.MISSILE_SWARM) >= 2f);
assertTrue(UpgradeDraftPolicy.affinityMultiplier(player, Upgrade.ORBITAL) >= 2f);
⋮----
@Test void protocolEvolutionBecomesFocusedBuildPathChoice() {
⋮----
player.protocols.enableRhythm();
⋮----
assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, Upgrade.RHYTHM_ACCELERATOR));
assertTrue(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_ACCELERATOR));
⋮----
@Test void establishedElementAlwaysGetsOneRelevantDraftSlot() {
⋮----
UpgradeSelector.fillChoices(player, choices);
assertTrue(UpgradeDraftPolicy.isFocusedCandidate(player, choices[0]), choices[0].name());
assertTrue(UpgradeSelector.isAvailable(player, choices[0]), choices[0].name());
assertTrue(choices[0] != choices[1] && choices[0] != choices[2] && choices[1] != choices[2]);
⋮----
@Test void establishedAbilityAlwaysGetsOwnOrSynergyRelevantDraftSlot() {
```

## File: src/test/java/com/deadlinezero/game/progression/UpgradePoolTest.java
```java
final class UpgradePoolTest {
@Test void productionPoolMeetsFiftyUpgradeTargetWithUniquePresentation() {
Upgrade[] upgrades = Upgrade.values();
assertTrue(upgrades.length >= 50, "P5 requires 50+ standard upgrades");
assertEquals(60, upgrades.length);
⋮----
assertTrue(titles.add(upgrade.title), "duplicate upgrade title: " + upgrade.title);
assertFalse(upgrade.description.isBlank(), upgrade.name());
⋮----
assertTrue(common >= 10, "common pool too small");
assertTrue(rare >= 20, "rare pool too small");
assertTrue(epic >= 8, "epic pool too small");
⋮----
@Test void everyUpgradeKeepsRuntimeStatsFiniteAndInsideSafetyCaps() {
RunLoadoutContext.end();
for (Upgrade upgrade : Upgrade.values()) {
Player player = new Player(0f, 0f);
upgrade.apply(player);
assertRuntimeSafe(player, upgrade.name());
⋮----
@Test void repeatedStackingSaturatesInsteadOfEscapingMobileSafetyCaps() {
⋮----
for (int i = 0; i < 120; i++) for (Upgrade upgrade : stress) upgrade.apply(player);
⋮----
assertEquals(Upgrade.MIN_FIRE_INTERVAL, player.weapon.fireInterval, .0001f);
assertEquals(Upgrade.MAX_DAMAGE, player.weapon.damage, .0001f);
assertEquals(Upgrade.MAX_MOVE_SPEED, player.moveSpeed, .0001f);
assertEquals(Upgrade.MAX_HP, player.maxHp, .0001f);
assertEquals(Upgrade.MAX_PROJECTILES, player.weapon.projectileCount);
assertEquals(Upgrade.MAX_CRIT_CHANCE, player.weapon.critChance, .0001f);
assertEquals(Upgrade.MAX_CRIT_MULTIPLIER, player.weapon.critMultiplier, .0001f);
assertEquals(Upgrade.MAX_PROJECTILE_SPEED, player.weapon.projectileSpeed, .0001f);
assertEquals(Upgrade.MAX_PENETRATION, player.weapon.penetration);
assertEquals(Upgrade.MAX_KNOCKBACK, player.weapon.knockback, .0001f);
assertEquals(Upgrade.MIN_DASH_COOLDOWN, player.dashCooldown, .0001f);
⋮----
@Test void selectorAlwaysReturnsThreeDistinctEligibleChoices() {
⋮----
UpgradeSelector.fillChoices(player, choices);
⋮----
assertNotNull(choice);
assertTrue(unique.add(choice), "selector returned duplicate choice");
assertTrue(UpgradeSelector.isAvailable(player, choice), choice.name());
⋮----
@Test void everyUpgradeEventuallyStopsBeingUsefulWhenRepeatedAlone() {
⋮----
while (UpgradeSelector.isAvailable(player, upgrade) && applications < 240) {
⋮----
assertTrue(applications < 240, "upgrade never saturated: " + upgrade.name());
assertFalse(UpgradeSelector.isAvailable(player, upgrade), "upgrade still offered after saturation: " + upgrade.name());
⋮----
@Test void droneDoctrinesUnlockAtTierTwoAndBecomeMutuallyExclusive() {
⋮----
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DRONE_HUNTER_DOCTRINE));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DRONE_SENTINEL_DOCTRINE));
⋮----
for (int i = 0; i < 3; i++) Upgrade.DRONE.apply(player);
assertTrue(UpgradeSelector.isAvailable(player, Upgrade.DRONE_HUNTER_DOCTRINE));
assertTrue(UpgradeSelector.isAvailable(player, Upgrade.DRONE_SENTINEL_DOCTRINE));
⋮----
Upgrade.DRONE_HUNTER_DOCTRINE.apply(player);
⋮----
@Test void protocolEvolutionsRequireTheirBaseAndThenSaturate() {
⋮----
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_ACCELERATOR));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.KILLCHAIN_OVERCHARGE));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.REACTION_CASCADE));
⋮----
Upgrade.RHYTHM_DRIVER.apply(player);
Upgrade.KILLCHAIN_CAPACITOR.apply(player);
Upgrade.REACTION_CORE.apply(player);
⋮----
assertTrue(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_ACCELERATOR));
assertTrue(UpgradeSelector.isAvailable(player, Upgrade.KILLCHAIN_OVERCHARGE));
assertTrue(UpgradeSelector.isAvailable(player, Upgrade.REACTION_CASCADE));
⋮----
Upgrade.RHYTHM_ACCELERATOR.apply(player);
Upgrade.KILLCHAIN_OVERCHARGE.apply(player);
Upgrade.REACTION_CASCADE.apply(player);
⋮----
@Test void eventProtocolsAreOneTimeRunChoices() {
⋮----
assertTrue(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_DRIVER));
assertTrue(UpgradeSelector.isAvailable(player, Upgrade.KILLCHAIN_CAPACITOR));
assertTrue(UpgradeSelector.isAvailable(player, Upgrade.REACTION_CORE));
⋮----
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.RHYTHM_DRIVER));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.KILLCHAIN_CAPACITOR));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.REACTION_CORE));
⋮----
@Test void hardCappedChoicesDisappearFromEligibility() {
⋮----
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.RAPID_FIRE));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DAMAGE));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.SPEED));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.MULTISHOT));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.CRIT));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.CRIT_POWER));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.BALLISTICS));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.PENETRATION));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.KNOCKBACK));
assertFalse(UpgradeSelector.isAvailable(player, Upgrade.DASH_CORE));
⋮----
private static void assertRuntimeSafe(Player player, String context) {
assertTrue(Float.isFinite(player.hp), context);
assertTrue(Float.isFinite(player.maxHp), context);
assertTrue(Float.isFinite(player.moveSpeed), context);
assertTrue(Float.isFinite(player.dashCooldown), context);
assertTrue(Float.isFinite(player.weapon.damage), context);
assertTrue(Float.isFinite(player.weapon.fireInterval), context);
assertTrue(Float.isFinite(player.weapon.projectileSpeed), context);
assertTrue(Float.isFinite(player.weapon.spreadDegrees), context);
assertTrue(Float.isFinite(player.weapon.critChance), context);
assertTrue(Float.isFinite(player.weapon.critMultiplier), context);
assertTrue(Float.isFinite(player.weapon.knockback), context);
⋮----
assertTrue(player.maxHp >= Upgrade.MIN_HP && player.maxHp <= Upgrade.MAX_HP, context);
assertTrue(player.hp >= 0f && player.hp <= player.maxHp, context);
assertTrue(player.moveSpeed >= 1f && player.moveSpeed <= Upgrade.MAX_MOVE_SPEED, context);
assertTrue(player.dashCooldown >= Upgrade.MIN_DASH_COOLDOWN, context);
assertTrue(player.weapon.damage > 0f && player.weapon.damage <= Upgrade.MAX_DAMAGE, context);
assertTrue(player.weapon.fireInterval >= Upgrade.MIN_FIRE_INTERVAL, context);
assertTrue(player.weapon.projectileSpeed >= 1f && player.weapon.projectileSpeed <= Upgrade.MAX_PROJECTILE_SPEED, context);
assertTrue(player.weapon.projectileCount >= 1 && player.weapon.projectileCount <= Upgrade.MAX_PROJECTILES, context);
assertTrue(player.weapon.spreadDegrees >= 0f && player.weapon.spreadDegrees <= Upgrade.MAX_SPREAD_DEGREES, context);
assertTrue(player.weapon.critChance >= 0f && player.weapon.critChance <= Upgrade.MAX_CRIT_CHANCE, context);
assertTrue(player.weapon.critMultiplier >= 1f && player.weapon.critMultiplier <= Upgrade.MAX_CRIT_MULTIPLIER, context);
assertTrue(player.weapon.penetration >= 0 && player.weapon.penetration <= Upgrade.MAX_PENETRATION, context);
assertTrue(player.weapon.knockback >= 0f && player.weapon.knockback <= Upgrade.MAX_KNOCKBACK, context);
```

## File: src/test/java/com/deadlinezero/game/progression/WeaponFamilyLegendaryBalanceTest.java
```java
final class WeaponFamilyLegendaryBalanceTest {
@AfterEach void resetLoadout() { RunLoadoutContext.begin(null); }
⋮----
@Test void directWeaponTransformationsStayInsideIntentionalPowerBands() {
assertVolleyBand("rail_rifle", LegendaryChoice.RAIL_PHASE_LANCE, 1.18f, 1.28f);
assertVolleyBand("cryo_lance", LegendaryChoice.CRYO_PRISM, 1.40f, 1.48f);
assertVolleyBand("arc_carbine", LegendaryChoice.ARC_OVERLOAD, 1.32f, 1.40f);
⋮----
@Test void cinderFurnaceOnlyAddsModestBaselineCadenceBeforeSignaturePayload() {
Player p = playerWith("cinder_cannon");
⋮----
assertTrue(LegendaryChoice.CINDER_FURNACE.apply(p));
⋮----
assertTrue(cadenceGain >= 1.05f && cadenceGain <= 1.08f);
⋮----
@Test void ionCascadeKeepsBaselineWeaponDamageStable() {
Player p = playerWith("ion_needle");
⋮----
assertTrue(LegendaryChoice.ION_CASCADE.apply(p));
assertTrue(Math.abs(p.weapon.damage / before - 1f) < .0001f);
⋮----
private static void assertVolleyBand(String weaponId, LegendaryChoice choice, float min, float max) {
Player p = playerWith(weaponId);
⋮----
assertTrue(choice.apply(p));
⋮----
assertTrue(ratio >= min && ratio <= max, weaponId + " volley ratio out of band: " + ratio);
⋮----
private static Player playerWith(String weaponId) {
PlayerProfile profile = new PlayerProfile();
⋮----
RunLoadoutContext.begin(profile);
Player player = new Player(0f, 0f);
```

## File: src/test/java/com/deadlinezero/game/progression/WeaponFamilyLegendaryTest.java
```java
final class WeaponFamilyLegendaryTest {
@AfterEach void resetLoadout() { RunLoadoutContext.begin(null); }
⋮----
@Test void ionCascadeOnlyAppearsForIonNeedleAndAcceleratesSignatureCadence() {
Player p = playerWith("ion_needle");
⋮----
assertTrue(LegendaryChoice.ION_CASCADE.eligible(p));
assertFalse(LegendaryChoice.CINDER_FURNACE.eligible(p));
assertTrue(LegendaryChoice.ION_CASCADE.apply(p));
assertTrue(p.legendary.hasIonCascade());
assertTrue(WeaponSignatureRuntime.ionCascadeEnabled());
⋮----
var shot = WeaponSignatureRuntime.consumeShot(false);
assertEquals(i == 4, shot.active());
⋮----
assertEquals(WeaponSignatureRuntime.Kind.ION_OVERCHARGE, shot.kind());
assertEquals(2, shot.penetrationBonus());
assertTrue(shot.radius() >= .15f);
⋮----
assertFalse(LegendaryChoice.ION_CASCADE.eligible(p));
⋮----
@Test void cinderFurnaceCyclesEveryThirdShellAndStrengthensPayload() {
Player p = playerWith("cinder_cannon");
⋮----
assertTrue(LegendaryChoice.CINDER_FURNACE.apply(p));
assertTrue(WeaponSignatureRuntime.cinderFurnaceEnabled());
⋮----
assertEquals(i == 3, shot.active());
⋮----
assertEquals(WeaponSignatureRuntime.Kind.CINDER_OVERHEAT, shot.kind());
assertEquals(1.72f, shot.damageMultiplier(), .0001f);
⋮----
assertEquals(.21f, shot.radius(), .0001f);
⋮----
@Test void railPhaseLanceTurnsPrecisionWeaponIntoExtremePiercer() {
Player p = playerWith("rail_rifle");
⋮----
assertTrue(LegendaryChoice.RAIL_PHASE_LANCE.apply(p));
assertEquals(damage * 1.22f, p.weapon.damage, .0001f);
assertEquals(speed * 1.12f, p.weapon.projectileSpeed, .0001f);
assertEquals(penetration + 2, p.weapon.penetration);
assertEquals(crit + .08f, p.weapon.critChance, .0001f);
⋮----
@Test void cryoPrismCreatesThreeControlledFrostLances() {
Player p = playerWith("cryo_lance");
⋮----
assertTrue(LegendaryChoice.CRYO_PRISM.apply(p));
assertEquals(3, p.weapon.projectileCount);
assertEquals(damage * .48f, p.weapon.damage, .0001f);
assertEquals(penetration + 1, p.weapon.penetration);
assertTrue(p.weapon.spreadDegrees >= 5f);
⋮----
@Test void arcOverloadDoublesShockVectorsWithoutRunawayPaperDamage() {
Player p = playerWith("arc_carbine");
⋮----
assertTrue(LegendaryChoice.ARC_OVERLOAD.apply(p));
assertEquals(2, p.weapon.projectileCount);
assertEquals(damage * .68f, p.weapon.damage, .0001f);
assertEquals(2, p.weapon.penetration);
⋮----
assertTrue(totalVolley >= damage * 1.30f && totalVolley <= damage * 1.40f);
⋮----
@Test void incompatibleWeaponSpecificChoicesNeverEnterOffers() {
Player p = playerWith("ar9");
⋮----
int count = LegendarySelector.fillChoices(p, out);
assertEquals(4, count);
⋮----
assertTrue(choice == LegendaryChoice.OVERDRIVE
⋮----
assertTrue(sawVanguard);
⋮----
@Test void ownedGeneralLegendariesStillAllowCompatibleFamilyOffer() {
⋮----
assertTrue(LegendaryEffects.applyOverdrive(p));
assertTrue(LegendaryEffects.applySingularity(p));
assertTrue(LegendaryEffects.applyApex(p));
assertTrue(LegendarySelector.shouldOffer(p));
⋮----
assertEquals(1, LegendarySelector.fillChoices(p, out));
assertEquals(LegendaryChoice.RAIL_PHASE_LANCE, out[0]);
⋮----
private static Player playerWith(String weaponId) {
PlayerProfile profile = new PlayerProfile();
⋮----
RunLoadoutContext.begin(profile);
return new Player(0f, 0f);
```

## File: src/test/java/com/deadlinezero/game/screen/GameScreenBossSummonRosterTest.java
```java
public final class GameScreenBossSummonRosterTest {
@Test public void nullArchonPhaseTwoUsesStalkersAndSeersOnly() {
assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 2, 0));
assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(true, false, 2, 1));
assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 2, 2));
assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 2, 3));
⋮----
@Test public void nullArchonPhaseThreeAddsNullWards() {
assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 3, 0));
assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(true, false, 3, 1));
assertEquals(Enemy.Type.REGENERATOR, GameScreen.bossSummonType(true, false, 3, 2));
assertEquals(Enemy.Type.PHANTOM, GameScreen.bossSummonType(true, false, 3, 3));
assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(true, false, 3, 4));
assertEquals(Enemy.Type.REGENERATOR, GameScreen.bossSummonType(true, false, 3, 5));
⋮----
@Test public void revenantHistoricalPhaseThreeCadenceIsPreserved() {
assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(false, true, 3, 0));
assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, true, 3, 1));
assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(false, true, 3, 2));
assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, true, 3, 3));
⋮----
@Test public void standardBossHistoricalRosterIsPreserved() {
assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, false, 2, 0));
assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(false, false, 3, 0));
assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, false, 3, 1));
assertEquals(Enemy.Type.RUNNER, GameScreen.bossSummonType(false, false, 3, 2));
assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(false, false, 3, 3));
⋮----
@Test public void frostColossusUsesHeavyCryogenicSummons() {
assertEquals(Enemy.Type.SHIELDED, GameScreen.bossSummonType(BossIdentity.FROST_COLOSSUS, 2, 0));
assertEquals(Enemy.Type.BRUTE, GameScreen.bossSummonType(BossIdentity.FROST_COLOSSUS, 2, 1));
assertEquals(Enemy.Type.SHIELDED, GameScreen.bossSummonType(BossIdentity.FROST_COLOSSUS, 3, 0));
assertEquals(Enemy.Type.BRUTE, GameScreen.bossSummonType(BossIdentity.FROST_COLOSSUS, 3, 1));
assertEquals(Enemy.Type.RANGED, GameScreen.bossSummonType(BossIdentity.FROST_COLOSSUS, 3, 2));
```

## File: src/test/java/com/deadlinezero/game/screen/MenuLayoutModelTest.java
```java
final class MenuLayoutModelTest {
⋮----
void homeLayoutIsContainedAndNonOverlappingAtBaselineAndWidePhone() {
⋮----
UiLayout.Metrics metrics = UiLayout.compute(size[0], size[1]);
MenuLayoutModel.Layout layout = MenuLayoutModel.layout(metrics);
⋮----
assertContained(layout.survivorCard(), metrics);
assertContained(layout.loadoutCard(), metrics);
assertContained(layout.threatCard(), metrics);
assertContained(layout.deploy(), metrics);
assertTrue(layout.deploy().height >= metrics.touchTarget());
assertFalse(layout.survivorCard().overlaps(layout.deploy()));
assertFalse(layout.loadoutCard().overlaps(layout.deploy()));
assertFalse(layout.threatCard().overlaps(layout.deploy()));
⋮----
void bottomTabsAreEvenAndTouchSafe() {
UiLayout.Metrics metrics = UiLayout.compute(1536, 691);
⋮----
Rectangle[] tabs = layout.bottomTabs();
⋮----
assertEquals(6, tabs.length);
⋮----
assertEquals(width, tab.width, .01f);
assertTrue(tab.width >= metrics.touchTarget());
assertTrue(tab.height >= metrics.touchTarget());
assertTrue(layout.bottomNav().contains(tab));
⋮----
private static void assertContained(Rectangle r, UiLayout.Metrics m) {
assertTrue(r.x >= m.safeLeft() - .01f);
assertTrue(r.y >= m.safeBottom() - .01f);
assertTrue(r.x + r.width <= m.safeRight() + .01f);
assertTrue(r.y + r.height <= m.safeTop() + .01f);
```

## File: src/test/java/com/deadlinezero/game/screen/MetaScreenLayoutContractTest.java
```java
final class MetaScreenLayoutContractTest {
⋮----
void sharedMetaLayoutKeepsPanelsAndActionsInsideSafeFrame() {
⋮----
UiLayout.Metrics metrics = UiLayout.compute(size[0], size[1]);
MetaLayout.Layout layout = MetaLayout.compute(metrics);
⋮----
assertContained(layout.header(), metrics);
assertContained(layout.content(), metrics);
assertContained(layout.footer(), metrics);
assertContained(layout.back(), metrics);
⋮----
Rectangle[] columns = MetaLayout.columns(layout.content(), 3, 18f);
for (Rectangle column : columns) assertContained(column, metrics);
assertFalse(columns[0].overlaps(columns[1]));
assertFalse(columns[1].overlaps(columns[2]));
⋮----
Rectangle[] actions = MetaLayout.actions(layout.footer(), 4, 12f);
⋮----
assertTrue(action.height >= metrics.touchTarget());
assertContained(action, metrics);
⋮----
void cardRowsStayTouchSafe() {
UiLayout.Metrics metrics = UiLayout.compute(1536, 691);
⋮----
Rectangle[] rows = MetaLayout.rows(layout.content(), 5, 12f);
for (Rectangle row : rows) assertTrue(row.height >= metrics.touchTarget());
⋮----
private static void assertContained(Rectangle r, UiLayout.Metrics m) {
assertTrue(r.x >= m.safeLeft() - .01f);
assertTrue(r.y >= m.safeBottom() - .01f);
assertTrue(r.x + r.width <= m.safeRight() + .01f);
assertTrue(r.y + r.height <= m.safeTop() + .01f);
```

## File: src/test/java/com/deadlinezero/game/screen/SurvivorLayoutModelTest.java
```java
final class SurvivorLayoutModelTest {
⋮----
void rosterLayoutStaysReadableAtBaselineAndWidePhone() {
⋮----
UiLayout.Metrics metrics = UiLayout.compute(size[0], size[1]);
SurvivorLayoutModel.Layout layout = SurvivorLayoutModel.layout(metrics);
⋮----
assertContained(layout.card(), metrics);
assertContained(layout.portrait(), metrics);
assertContained(layout.stats(), metrics);
assertContained(layout.xpBar(), metrics);
assertContained(layout.cta(), metrics);
assertTrue(layout.cta().height >= metrics.touchTarget());
assertTrue(layout.previous().width >= metrics.touchTarget());
assertTrue(layout.next().width >= metrics.touchTarget());
assertFalse(layout.portrait().overlaps(layout.stats()));
assertFalse(layout.stats().overlaps(layout.cta()));
assertFalse(layout.xpBar().overlaps(layout.cta()));
⋮----
void navigationAndSelectionAreExplicitTargets() {
UiLayout.Metrics metrics = UiLayout.compute(1536, 691);
⋮----
Rectangle empty = new Rectangle(layout.card().x + layout.card().width * .45f,
layout.card().y + 12f, 30f, 30f);
⋮----
assertFalse(layout.previous().overlaps(layout.next()));
assertFalse(layout.cta().overlaps(layout.previous()));
assertFalse(layout.cta().overlaps(layout.next()));
assertFalse(layout.cta().overlaps(empty));
⋮----
private static void assertContained(Rectangle r, UiLayout.Metrics m) {
assertTrue(r.x >= m.safeLeft() - .01f);
assertTrue(r.y >= m.safeBottom() - .01f);
assertTrue(r.x + r.width <= m.safeRight() + .01f);
assertTrue(r.y + r.height <= m.safeTop() + .01f);
```

## File: src/test/java/com/deadlinezero/game/services/BillingProductCatalogTest.java
```java
final class BillingProductCatalogTest {
@Test void catalogContainsExactlyTheExpectedProducts() {
assertEquals(Set.of(
⋮----
@Test void productIdsArePlaySafeAndUnique() {
assertEquals(4, BillingService.PRODUCTS.size());
⋮----
assertTrue(id.matches("[a-z0-9_]{3,64}"), () -> "Invalid Play product id: " + id);
assertFalse(id.startsWith("test_"), () -> "Test product leaked into production catalog: " + id);
assertFalse(id.contains("placeholder"), () -> "Placeholder product leaked into production catalog: " + id);
⋮----
@Test void durableAndConsumableClassificationIsCompleteAndExclusive() {
⋮----
assertTrue(BillingService.isDurable(id) ^ BillingService.isConsumable(id),
⋮----
assertTrue(BillingService.isKnownProduct(id));
⋮----
assertFalse(BillingService.isKnownProduct("unknown_product"));
assertFalse(BillingService.isDurable("unknown_product"));
assertFalse(BillingService.isConsumable("unknown_product"));
```

## File: src/test/java/com/deadlinezero/game/services/BillingServiceStateTest.java
```java
final class BillingServiceStateTest {
@Test void legacyImplementationsDefaultToReadyAndNoActiveProduct() {
BillingService service = new BillingService() {
@Override public void initialize() {}
@Override public boolean owns(String productId) { return false; }
@Override public void purchase(String productId, Runnable onSuccess, Runnable onFailure) {}
@Override public void restore() {}
⋮----
assertEquals(BillingService.State.READY, service.state());
assertEquals("", service.activeProductId());
⋮----
@Test void pendingStateRemainsDistinctFromInProgress() {
assertEquals("PURCHASE_IN_PROGRESS", BillingService.State.PURCHASE_IN_PROGRESS.name());
assertEquals("PURCHASE_PENDING", BillingService.State.PURCHASE_PENDING.name());
```

## File: src/test/java/com/deadlinezero/game/services/CloudSaveServiceTest.java
```java
final class CloudSaveServiceTest {
@Test void unavailableAdapterBehavesAsEmptyRemote() throws Exception {
CloudSaveService service = new CloudSaveService(null);
assertFalse(service.available());
assertNull(service.inspectRemote());
assertEquals(CloudSaveService.DownloadResult.EMPTY_REMOTE, service.downloadRemote().result());
⋮----
@Test void unavailableProviderRejectsUploadExplicitly() {
⋮----
String backup = ProfileBackupCodec.encode(Map.of("accountLevel", 1));
assertThrows(IllegalStateException.class, () -> service.upload(backup));
⋮----
@Test void corruptRemotePayloadIsRejectedBeforeImport() {
CloudSaveService service = serviceReturning("not-a-valid-backup");
assertThrows(IllegalArgumentException.class, service::inspectRemote);
⋮----
@Test void monotoneAdvanceIsSafeOnlyWhenEveryOtherPersistedFieldMatches() throws Exception {
⋮----
localValues.put("highestStage", 4);
localValues.put("accountLevel", 8);
localValues.put("totalRuns", 20);
localValues.put("totalKills", 3000L);
localValues.put("threat.highest", 1);
localValues.put("credits", 100L);
⋮----
remoteValues.put("highestStage", 6);
remoteValues.put("accountLevel", 9);
remoteValues.put("totalRuns", 30);
remoteValues.put("totalKills", 5000L);
remoteValues.put("threat.highest", 2);
⋮----
CloudSaveService service = serviceReturning(ProfileBackupCodec.encode(remoteValues));
assertEquals(CloudSaveService.ConflictState.REMOTE_AHEAD,
service.compareRemoteToLocal(ProfileBackupCodec.encode(localValues)));
⋮----
@Test void uniqueNonMonotoneProgressForcesDivergedEvenWhenRemoteCountersAreAhead() throws Exception {
⋮----
localValues.put("achievement.FIRST_CLEAR.claimed", true);
⋮----
remoteValues.put("achievement.FIRST_CLEAR.claimed", false);
⋮----
assertEquals(CloudSaveService.ConflictState.DIVERGED,
⋮----
@Test void exactPayloadEqualityIsEqual() throws Exception {
String backup = ProfileBackupCodec.encode(Map.of(
⋮----
CloudSaveService service = serviceReturning(backup);
assertEquals(CloudSaveService.ConflictState.EQUAL, service.compareRemoteToLocal(backup));
⋮----
@Test void observedRemoteIdentityRequiresPayloadAndVersionToMatch() {
⋮----
assertTrue(CloudSaveService.sameRemote(observed, new CloudSaveAdapter.RemoteBackup("payload-a", 100L)));
assertFalse(CloudSaveService.sameRemote(observed, new CloudSaveAdapter.RemoteBackup("payload-b", 100L)));
assertFalse(CloudSaveService.sameRemote(observed, new CloudSaveAdapter.RemoteBackup("payload-a", 101L)));
assertFalse(CloudSaveService.sameRemote(observed, null));
assertTrue(CloudSaveService.sameRemote(null, null));
⋮----
@Test void classificationCanBeBoundToAnExactAlreadyInspectedRemote() {
⋮----
local.put("highestStage", 4);
local.put("accountLevel", 8);
local.put("totalRuns", 20);
local.put("totalKills", 3000L);
local.put("threat.highest", 1);
local.put("credits", 100L);
⋮----
remote.put("highestStage", 5);
remote.put("totalRuns", 25);
String localBackup = ProfileBackupCodec.encode(local);
String remoteBackup = ProfileBackupCodec.encode(remote);
⋮----
CloudSaveService service = serviceReturning("unused");
⋮----
service.classify(localBackup, new CloudSaveAdapter.RemoteBackup(remoteBackup, 123L)));
⋮----
@Test void authenticationRequestIsDelegatedExplicitly() throws Exception {
⋮----
CloudSaveService service = new CloudSaveService(new CloudSaveAdapter() {
@Override public boolean supportsAuthentication() { return true; }
@Override public void authenticate() { authenticated[0] = true; }
@Override public RemoteBackup read() { return null; }
@Override public void write(String payload) { }
⋮----
assertTrue(service.supportsAuthentication());
service.authenticate();
assertTrue(authenticated[0]);
⋮----
@Test void providerConflictChoiceIsDelegatedExplicitly() throws Exception {
⋮----
@Override public ProviderConflict pendingConflict() { return conflict; }
@Override public void resolvePendingConflict(ConflictChoice choice) { chosen[0] = choice; }
⋮----
assertEquals(conflict, service.pendingProviderConflict());
service.resolveProviderConflict(CloudSaveAdapter.ConflictChoice.CONFLICTING);
assertEquals(CloudSaveAdapter.ConflictChoice.CONFLICTING, chosen[0]);
⋮----
@Test void uploadRejectsCorruptLocalPayloadBeforeProviderWrite() {
⋮----
@Override public void write(String payload) { throw new AssertionError("provider must not receive corrupt backup"); }
⋮----
assertThrows(IllegalArgumentException.class, () -> service.upload("corrupt"));
⋮----
@Test void restoreResultRequiresFreshProfileOnAppliedRestore() {
assertTrue(CloudSaveService.RestoreResult.class.isRecord());
⋮----
private static CloudSaveService serviceReturning(String payload) {
return new CloudSaveService(new CloudSaveAdapter() {
@Override public RemoteBackup read() { return new RemoteBackup(payload, 789L); }
@Override public void write(String ignored) { }
```

## File: src/test/java/com/deadlinezero/game/services/OfferConfigServiceTest.java
```java
final class OfferConfigServiceTest {
@Test void defaultsKeepEveryKnownProductAvailable() {
OfferConfigService.Snapshot snapshot = OfferConfigService.safeDefaults();
for (String productId : BillingService.PRODUCTS) assertTrue(snapshot.enabled(productId));
assertTrue(snapshot.featured(BillingService.STARTER_PACK));
⋮----
@Test void missingOrFullyInvalidRemoteConfigFallsBackSafely() {
assertEquals(BillingService.PRODUCTS, OfferConfigService.sanitize(null, null).enabledProducts());
assertEquals(BillingService.PRODUCTS, OfferConfigService.sanitize(Set.of(), "").enabledProducts());
assertEquals(BillingService.PRODUCTS,
OfferConfigService.sanitize(Set.of("unknown_offer"), "unknown_offer").enabledProducts());
⋮----
@Test void remoteConfigCanOnlyExposeKnownProducts() {
OfferConfigService.Snapshot snapshot = OfferConfigService.sanitize(
Set.of(BillingService.GEMS_SMALL, "not_a_play_product"),
⋮----
assertEquals(Set.of(BillingService.GEMS_SMALL), snapshot.enabledProducts());
assertTrue(snapshot.enabled(BillingService.GEMS_SMALL));
assertTrue(snapshot.featured(BillingService.GEMS_SMALL));
assertFalse(snapshot.enabled("not_a_play_product"));
⋮----
@Test void featuredOfferMustAlsoBeEnabled() {
⋮----
Set.of(BillingService.GEMS_SMALL),
⋮----
assertEquals("", snapshot.featuredProductId());
assertFalse(snapshot.featured(BillingService.STARTER_PACK));
```

## File: src/test/java/com/deadlinezero/game/services/SingleFlightGateTest.java
```java
final class SingleFlightGateTest {
@Test void rejectsOverlappingOperationsUntilReleased() {
SingleFlightGate gate = new SingleFlightGate();
⋮----
assertTrue(gate.tryBegin());
assertTrue(gate.active());
assertFalse(gate.tryBegin());
⋮----
gate.end();
assertFalse(gate.active());
⋮----
@Test void endIsIdempotent() {
```

## File: src/test/java/com/deadlinezero/game/services/ThermalServiceTest.java
```java
final class ThermalServiceTest {
@Test void thermalLevelsApplyConservativeFpsCeilings() {
assertEquals(120, ThermalService.Level.UNKNOWN.fpsCeiling);
assertEquals(120, ThermalService.Level.NORMAL.fpsCeiling);
assertEquals(120, ThermalService.Level.LIGHT.fpsCeiling);
assertEquals(90, ThermalService.Level.MODERATE.fpsCeiling);
assertEquals(60, ThermalService.Level.SEVERE.fpsCeiling);
assertEquals(60, ThermalService.Level.CRITICAL.fpsCeiling);
assertEquals(1.00f, ThermalService.Level.NORMAL.fxCeiling, .0001f);
assertEquals(.92f, ThermalService.Level.LIGHT.fxCeiling, .0001f);
assertEquals(.76f, ThermalService.Level.MODERATE.fxCeiling, .0001f);
assertEquals(.58f, ThermalService.Level.SEVERE.fxCeiling, .0001f);
assertEquals(.46f, ThermalService.Level.CRITICAL.fxCeiling, .0001f);
⋮----
@Test void noOpIsNonRestrictive() {
assertEquals(ThermalService.Level.UNKNOWN, ThermalService.noOp().level());
```

## File: src/test/java/com/deadlinezero/game/ui/ResponsiveGridTest.java
```java
final class ResponsiveGridTest {
⋮----
void baselineUsesTwoColumnsAndWidePhoneUsesThree() {
UiLayout.Metrics baseline = UiLayout.compute(1280, 720);
UiLayout.Metrics wide = UiLayout.compute(1536, 691);
⋮----
ResponsiveGrid.Spec baseGrid = ResponsiveGrid.compute(baseline.contentWidth(), 420f, 3, 16f);
ResponsiveGrid.Spec wideGrid = ResponsiveGrid.compute(wide.contentWidth(), 420f, 3, 16f);
⋮----
assertEquals(2, baseGrid.columns());
assertEquals(3, wideGrid.columns());
assertTrue(baseGrid.cardWidth() >= 300f);
assertTrue(wideGrid.cardWidth() >= 300f);
⋮----
void targetFormatsProduceTouchSafeNonOverlappingCards() {
⋮----
UiLayout.Metrics m = UiLayout.compute(size[0], size[1]);
ResponsiveGrid.Spec spec = ResponsiveGrid.compute(m.contentWidth(), 420f, 3, 16f);
Rectangle a = ResponsiveGrid.cardBounds(0, m.safeLeft(), m.contentTop(), 108f, spec);
Rectangle b = ResponsiveGrid.cardBounds(1, m.safeLeft(), m.contentTop(), 108f, spec);
assertTrue(a.width >= 300f);
assertTrue(a.height >= m.touchTarget());
assertTrue(b.x >= a.x + a.width + 15.9f);
assertTrue(b.x + b.width <= m.safeRight() + .01f);
```

## File: src/test/java/com/deadlinezero/game/ui/UiLayoutTest.java
```java
final class UiLayoutTest {
⋮----
void widePhoneExtendsHorizontallyWithoutShrinkingLogicalHeight() {
UiLayout.Metrics m = UiLayout.compute(1536, 691);
⋮----
assertEquals(720f, m.height(), 0.01f);
assertTrue(m.width() > 1280f);
assertTrue(m.safeLeft() >= 24f);
assertTrue(m.safeRight() <= m.width() - 24f);
assertTrue(m.contentWidth() >= 1180f);
⋮----
void requiredFormatsKeepSafeFrameAndTouchTargets() {
⋮----
UiLayout.Metrics m = UiLayout.compute(size[0], size[1]);
assertTrue(m.width() >= 1280f);
assertTrue(m.height() >= 720f);
assertTrue(m.contentWidth() > 0f);
assertTrue(m.touchTarget() >= 56f);
assertTrue(m.safeBottom() < m.safeTop());
assertTrue(m.headerBottom() <= m.safeTop());
assertTrue(m.footerTop() >= m.safeBottom());
assertTrue(m.contentBottom() < m.contentTop());
⋮----
void sixteenByTenExtendsVerticallyWithoutStretching() {
UiLayout.Metrics m = UiLayout.compute(2560, 1600);
assertEquals(1280f, m.width(), 0.01f);
assertEquals(800f, m.height(), 0.01f);
⋮----
void invalidPhysicalDimensionsFallBackToBaseline() {
UiLayout.Metrics m = UiLayout.compute(0, 0);
```

## File: src/test/java/com/deadlinezero/game/ui/UiMotionTest.java
```java
final class UiMotionTest {
⋮----
void timingConstantsStayWithinInteractionBudget() {
assertTrue(UiMotion.PRESS_SECONDS >= .07f && UiMotion.PRESS_SECONDS <= .10f);
assertTrue(UiMotion.FOCUS_SECONDS >= .10f && UiMotion.FOCUS_SECONDS <= .14f);
assertTrue(UiMotion.REVEAL_SECONDS >= .16f && UiMotion.REVEAL_SECONDS <= .22f);
⋮----
void reducedMotionResolvesImmediatelyToStableEndState() {
assertEquals(1f, UiMotion.progress(0f, UiMotion.REVEAL_SECONDS, true), .0001f);
assertEquals(1f, UiMotion.progress(.01f, UiMotion.PRESS_SECONDS, true), .0001f);
⋮----
void progressIsClampedAndMonotonic() {
float a = UiMotion.progress(0f, UiMotion.REVEAL_SECONDS, false);
float b = UiMotion.progress(UiMotion.REVEAL_SECONDS * .5f, UiMotion.REVEAL_SECONDS, false);
float c = UiMotion.progress(UiMotion.REVEAL_SECONDS, UiMotion.REVEAL_SECONDS, false);
assertEquals(0f, a, .0001f);
assertTrue(b > a && b < c);
assertEquals(1f, c, .0001f);
assertEquals(1f, UiMotion.progress(99f, UiMotion.REVEAL_SECONDS, false), .0001f);
```

## File: src/test/java/com/deadlinezero/game/ui/UiRendererStateTest.java
```java
final class UiRendererStateTest {
⋮----
void interactiveStatesResolveToDistinctSemanticStyles() {
UiRenderer.ButtonStyle normal = UiRenderer.buttonStyle(UiRenderer.ButtonState.NORMAL);
UiRenderer.ButtonStyle pressed = UiRenderer.buttonStyle(UiRenderer.ButtonState.PRESSED);
UiRenderer.ButtonStyle selected = UiRenderer.buttonStyle(UiRenderer.ButtonState.SELECTED);
UiRenderer.ButtonStyle disabled = UiRenderer.buttonStyle(UiRenderer.ButtonState.DISABLED);
UiRenderer.ButtonStyle danger = UiRenderer.buttonStyle(UiRenderer.ButtonState.DANGER);
⋮----
assertNotEquals(normal.tone(), selected.tone());
assertNotEquals(normal.tone(), disabled.tone());
assertNotEquals(selected.tone(), danger.tone());
assertTrue(pressed.fillAlpha() > normal.fillAlpha());
assertTrue(disabled.labelAlpha() > 0f);
assertTrue(disabled.borderAlpha() > 0f);
assertTrue(danger.borderAlpha() >= normal.borderAlpha());
⋮----
void nullStateFallsBackToNormalPresentation() {
UiRenderer.ButtonStyle fallback = UiRenderer.buttonStyle(null);
⋮----
assertTrue(fallback == normal);
```

## File: src/test/java/com/deadlinezero/game/visual/ActiveBuildStatusTest.java
```java
final class ActiveBuildStatusTest {
private Player fresh() {
RunLoadoutContext.end();
return new Player(0f, 0f);
⋮----
@Test void doctrineTakesFirstHudSlot() {
Player p = fresh();
for (int i = 0; i < 3; i++) p.abilities.upgrade(AbilityType.DRONE);
p.abilities.chooseDroneDoctrine(DroneDoctrine.HUNTER);
⋮----
ActiveBuildStatus.fill(p, keys);
assertEquals("hud.build.hunter", keys[0]);
assertNull(keys[1]);
⋮----
@Test void secondSlotShowsPrimaryActiveSynergy() {
⋮----
for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.TESLA_ORB);
⋮----
p.abilities.chooseDroneDoctrine(DroneDoctrine.SENTINEL);
⋮----
assertEquals("hud.build.sentinel", keys[0]);
assertEquals("hud.build.arcReactor", keys[1]);
⋮----
@Test void strongestLateSynergyWinsSingleSynergySlot() {
⋮----
for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.ORBITAL_BLADE);
⋮----
assertEquals("hud.build.stormBlade", keys[0]);
⋮----
@Test void emptyBuildProducesNoTags() {
⋮----
ActiveBuildStatus.fill(fresh(), keys);
assertNull(keys[0]);
```

## File: src/test/java/com/deadlinezero/game/visual/ActorMaterialProfileTest.java
```java
final class ActorMaterialProfileTest {
@Test void standardCrowdEnemiesStaySingleDraw() {
assertFalse(ActorMaterialProfile.enemy(Enemy.Type.SHAMBLER, Enemy.Variant.NORMAL).outline());
assertFalse(ActorMaterialProfile.enemy(Enemy.Type.RUNNER, Enemy.Variant.NORMAL).outline());
assertFalse(ActorMaterialProfile.enemy(Enemy.Type.BRUTE, Enemy.Variant.NORMAL).outline());
assertFalse(ActorMaterialProfile.enemy(Enemy.Type.RANGED, Enemy.Variant.NORMAL).outline());
⋮----
@Test void priorityActorsReceiveSilhouetteReinforcement() {
assertTrue(ActorMaterialProfile.player().outline());
assertTrue(ActorMaterialProfile.enemy(Enemy.Type.BOSS, Enemy.Variant.NORMAL).outline());
assertTrue(ActorMaterialProfile.enemy(Enemy.Type.ELITE, Enemy.Variant.NORMAL).outline());
assertTrue(ActorMaterialProfile.enemy(Enemy.Type.SHIELDED, Enemy.Variant.NORMAL).outline());
assertTrue(ActorMaterialProfile.enemy(Enemy.Type.REGENERATOR, Enemy.Variant.NORMAL).outline());
assertTrue(ActorMaterialProfile.enemy(Enemy.Type.PHANTOM, Enemy.Variant.NORMAL).outline());
assertTrue(ActorMaterialProfile.enemy(Enemy.Type.SHAMBLER, Enemy.Variant.FERAL).outline());
⋮----
@Test void reinforcementStaysSubtle() {
⋮----
ActorMaterialProfile.player(),
ActorMaterialProfile.enemy(Enemy.Type.BOSS, Enemy.Variant.NORMAL),
ActorMaterialProfile.enemy(Enemy.Type.ELITE, Enemy.Variant.NORMAL),
ActorMaterialProfile.enemy(Enemy.Type.SHAMBLER, Enemy.Variant.SWIFT)
⋮----
assertTrue(p.scale() >= 1f && p.scale() <= 1.08f);
assertTrue(p.alpha() >= 0f && p.alpha() <= .80f);
```

## File: src/test/java/com/deadlinezero/game/visual/AdaptiveFxBudgetTest.java
```java
final class AdaptiveFxBudgetTest {
@Test void externalCeilingCanReduceQualityImmediately() {
AdaptiveFxBudget budget = new AdaptiveFxBudget();
budget.setExternalCeiling(.58f);
assertEquals(.58f, budget.quality(), .0001f);
⋮----
@Test void recoveryIsGradualToAvoidThermalOscillation() {
⋮----
budget.setExternalCeiling(1f);
⋮----
budget.advanceExternalCeiling(.1f);
assertTrue(budget.quality() > .58f);
assertTrue(budget.quality() < 1f);
⋮----
@Test void externalCeilingIsClampedToSupportedRange() {
⋮----
budget.setExternalCeiling(.10f);
assertEquals(.40f, budget.quality(), .0001f);
budget.setExternalCeiling(2f);
for (int i = 0; i < 400; i++) budget.advanceExternalCeiling(.1f);
assertEquals(1f, budget.quality(), .003f);
```

## File: src/test/java/com/deadlinezero/game/visual/AnimationProfileCatalogTest.java
```java
final class AnimationProfileCatalogTest {
⋮----
void fastArchetypesAnimateFasterThanHeavyArchetypes() {
var wraith = AnimationProfileCatalog.survivor(SurvivorCatalog.Survivor.WRAITH);
var bastion = AnimationProfileCatalog.survivor(SurvivorCatalog.Survivor.BASTION);
assertTrue(wraith.run() < bastion.run());
assertTrue(wraith.attack() < bastion.attack());
⋮----
var runner = AnimationProfileCatalog.enemy(Enemy.Type.RUNNER);
var brute = AnimationProfileCatalog.enemy(Enemy.Type.BRUTE);
assertTrue(runner.run() < brute.run());
assertTrue(runner.attack() < brute.attack());
⋮----
void everyProductionTimingIsSafeForRealtimeAnimation() {
for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
assertSafe(AnimationProfileCatalog.survivor(survivor));
⋮----
for (Enemy.Type type : Enemy.Type.values()) {
assertSafe(AnimationProfileCatalog.enemy(type));
⋮----
void transientHitAndDeathAnimationsNeverLoop() {
assertTrue(AnimationProfileCatalog.loops(GameArt.Motion.IDLE));
assertTrue(AnimationProfileCatalog.loops(GameArt.Motion.RUN));
assertTrue(AnimationProfileCatalog.loops(GameArt.Motion.ATTACK));
assertFalse(AnimationProfileCatalog.loops(GameArt.Motion.HIT));
assertFalse(AnimationProfileCatalog.loops(GameArt.Motion.DEATH));
⋮----
private static void assertSafe(AnimationProfileCatalog.Profile profile) {
assertRange(profile.idle());
assertRange(profile.run());
assertRange(profile.attack());
assertRange(profile.hit());
assertRange(profile.death());
⋮----
private static void assertRange(float value) {
assertTrue(value >= .04f && value <= .20f, "unsafe animation frame duration: " + value);
```

## File: src/test/java/com/deadlinezero/game/visual/ArtProfileCatalogPhoneReadabilityTest.java
```java
final class ArtProfileCatalogPhoneReadabilityTest {
@Test void nonBossEnemiesStayPhoneReadableWithoutBossScaleCreep() {
for (Enemy.Type type : Enemy.Type.values()) {
ArtProfileCatalog.CharacterProfile p = ArtProfileCatalog.enemy(type);
⋮----
assertTrue(p.height() >= 4.5f);
⋮----
assertTrue(p.height() >= 1.45f, type + " is too small for phone-scale authored rendering");
assertTrue(p.height() <= 2.60f, type + " is too large relative to gameplay collision scale");
assertTrue(p.footOffset() > 0f && p.footOffset() < p.height() * .40f);
⋮----
@Test void highPriorityEnemiesRemainVisuallyLargerThanBasicCrowd() {
float runner = ArtProfileCatalog.enemy(Enemy.Type.RUNNER).height();
float shambler = ArtProfileCatalog.enemy(Enemy.Type.SHAMBLER).height();
assertTrue(ArtProfileCatalog.enemy(Enemy.Type.BRUTE).height() > shambler);
assertTrue(ArtProfileCatalog.enemy(Enemy.Type.ELITE).height() > shambler);
assertTrue(ArtProfileCatalog.enemy(Enemy.Type.SHIELDED).height() > shambler);
assertTrue(shambler > runner);
```

## File: src/test/java/com/deadlinezero/game/visual/ArtProfileCatalogTest.java
```java
final class ArtProfileCatalogTest {
@Test void shamblerKeepsReadableStandardEnemyScale() {
var shambler = ArtProfileCatalog.enemy(Enemy.Type.SHAMBLER);
var runner = ArtProfileCatalog.enemy(Enemy.Type.RUNNER);
var rex = ArtProfileCatalog.survivor(SurvivorCatalog.Survivor.REX);
⋮----
assertTrue(shambler.height() >= 1.50f, "Shambler must remain readable at phone gameplay scale");
assertTrue(shambler.height() > runner.height(), "Runner should remain the smaller/faster silhouette");
assertTrue(shambler.height() < rex.height(), "Baseline Shambler must remain smaller than Rex");
assertTrue(shambler.footOffset() >= .40f && shambler.footOffset() <= .48f,
```

## File: src/test/java/com/deadlinezero/game/visual/AuthoredCoreDirectionalArtTest.java
```java
final class AuthoredCoreDirectionalArtTest {
⋮----
@Test void optionalShippedPngMustMatchRuntimeGridAndContainVisibleTiles() throws Exception {
Path asset = locateAsset();
⋮----
// Absence is supported deliberately: GameArt falls back to deterministic generated art.
assertEquals(768, AuthoredCoreDirectionalArt.width());
assertEquals(2016, AuthoredCoreDirectionalArt.height());
⋮----
BufferedImage image = ImageIO.read(asset.toFile());
assertNotNull(image, "assets/art/core_authored.png must be a real PNG when shipped");
assertEquals(AuthoredCoreDirectionalArt.width(), image.getWidth());
assertEquals(AuthoredCoreDirectionalArt.height(), image.getHeight());
assertEquals(768, image.getWidth());
assertEquals(2016, image.getHeight());
⋮----
if (((image.getRGB(x, y) >>> 24) & 0xff) > 16) visible++;
⋮----
assertTrue(visible >= 40, "authored tile " + tile + " unexpectedly empty: " + visible);
⋮----
@Test void allSevenActorsCoverEightDirectionsAndFiveMotions() {
⋮----
assertMotion(root, direction, "idle", 2);
assertMotion(root, direction, "run", 3);
assertMotion(root, direction, "attack", 2);
assertMotion(root, direction, "hit", 2);
assertMotion(root, direction, "death", 3);
⋮----
assertEquals(7, AuthoredCoreDirectionalArt.ACTOR_COUNT);
assertEquals(672, AuthoredCoreDirectionalArt.TOTAL_TILES);
⋮----
@Test void unrelatedActorsRemainOnTheirDedicatedFallbackLayers() {
assertEquals(-1, AuthoredCoreDirectionalArt.firstTile("enemy/brute/e/run"));
assertEquals(-1, AuthoredCoreDirectionalArt.firstTile("enemy/ranged/e/run"));
assertEquals(-1, AuthoredCoreDirectionalArt.firstTile("boss/alpha/e/run"));
⋮----
private static void assertMotion(String root, String direction, String motion, int frames) {
⋮----
assertTrue(AuthoredCoreDirectionalArt.firstTile(key) >= 0, key);
assertEquals(frames, AuthoredCoreDirectionalArt.frameCount(key), key);
⋮----
private static Path locateAsset() {
Path direct = Path.of("assets", "art", "core_authored.png");
if (Files.isRegularFile(direct)) return direct;
Path parent = Path.of("..", "assets", "art", "core_authored.png");
if (Files.isRegularFile(parent)) return parent;
```

## File: src/test/java/com/deadlinezero/game/visual/BiomeDirectionalBootstrapArtTest.java
```java
final class BiomeDirectionalBootstrapArtTest {
⋮----
@Test void everyBiomeIdentityHasFullEightWayMotionCoverage() {
for (BiomeEnemyRoster.Identity identity : BiomeEnemyRoster.Identity.values()) {
⋮----
String root = BiomeDirectionalBootstrapArt.root(identity);
⋮----
assertTrue(BiomeDirectionalBootstrapArt.firstTile(key) >= 0, () -> "Missing biome art: " + key);
⋮----
@Test void nullArchonHasDedicatedCoverageSeparateFromLegacyBosses() {
⋮----
assertTrue(BiomeDirectionalBootstrapArt.firstTile(key) >= 0, () -> "Missing Null Archon art: " + key);
⋮----
assertNotEquals(
BiomeDirectionalBootstrapArt.firstTile("enemy/biome/phase_stalker/e/idle"),
BiomeDirectionalBootstrapArt.firstTile("boss/null_archon/e/idle"));
⋮----
@Test void motionFrameCountsMatchRuntimeAnimationContract() {
⋮----
assertTrue(BiomeDirectionalBootstrapArt.frameCount(root + "idle") == 1);
assertTrue(BiomeDirectionalBootstrapArt.frameCount(root + "attack") == 2);
assertTrue(BiomeDirectionalBootstrapArt.frameCount(root + "run") == 3);
assertTrue(BiomeDirectionalBootstrapArt.frameCount(root + "death") == 3);
```

## File: src/test/java/com/deadlinezero/game/visual/BootstrapArtAssetTest.java
```java
final class BootstrapArtAssetTest {
@Test void shippedBootstrapPngIsValidAndEveryTileHasVisiblePixels() throws Exception {
Path asset = locateAsset();
String encoded = Files.readString(asset, StandardCharsets.UTF_8).trim();
byte[] png = Base64.getDecoder().decode(encoded);
⋮----
assertTrue(png.length > 1024, "bootstrap art unexpectedly tiny");
assertEquals((byte)0x89, png[0]);
assertEquals((byte)'P', png[1]);
assertEquals((byte)'N', png[2]);
assertEquals((byte)'G', png[3]);
⋮----
BufferedImage image = ImageIO.read(new ByteArrayInputStream(png));
assertNotNull(image, "bootstrap art must decode as PNG");
assertEquals(256, image.getWidth());
assertEquals(256, image.getHeight());
⋮----
if (((image.getRGB(x, y) >>> 24) & 0xff) > 16) visible++;
⋮----
assertTrue(visible >= 80, "bootstrap tile " + tile + " lacks visible authored pixels: " + visible);
⋮----
private static Path locateAsset() {
Path direct = Path.of("assets", "art", "game.png.b64");
if (Files.isRegularFile(direct)) return direct;
Path parent = Path.of("..", "assets", "art", "game.png.b64");
if (Files.isRegularFile(parent)) return parent;
throw new AssertionError("Cannot locate assets/art/game.png.b64 from " + Path.of("").toAbsolutePath());
```

## File: src/test/java/com/deadlinezero/game/visual/BootstrapArtCatalogTest.java
```java
final class BootstrapArtCatalogTest {
⋮----
@Test void bootstrapCoversEntireProductionArtContract() {
for (String key : ArtManifest.REQUIRED_STATIC) assertSupported(key);
for (String key : ArtManifest.REQUIRED_FX) assertSupported(key);
⋮----
for (WeaponDefinition weapon : WeaponCatalog.all()) assertSupported("weapon/" + weapon.id);
⋮----
for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
String root = "survivor/" + survivor.name().toLowerCase();
for (String motion : MOTIONS) assertSupported(root + "/" + motion);
⋮----
for (Enemy.Type type : Enemy.Type.values()) {
String root = "enemy/" + type.name().toLowerCase();
⋮----
assertSupported(root + "/corpse");
⋮----
for (BossIdentity identity : BossIdentity.values()) {
String root = GameArt.bossRoot(identity);
⋮----
@Test void portableBase64DecoderHandlesPaddingAndWhitespace() {
byte[] decoded = GameArt.decodeBase64("U HJvZHVjdGlvbi1hcnQ=\n");
assertArrayEquals("Production-art".getBytes(StandardCharsets.UTF_8), decoded);
⋮----
private static void assertSupported(String key) {
assertTrue(BootstrapArtCatalog.supports(key), () -> "Missing bootstrap art mapping: " + key);
```

## File: src/test/java/com/deadlinezero/game/visual/BootstrapEnvironmentArtTest.java
```java
final class BootstrapEnvironmentArtTest {
@Test void environmentKeysAreUniqueAndAddressable() {
⋮----
assertEquals(i, BootstrapEnvironmentArt.indexOf(key), key);
⋮----
assertTrue(!key.equals(BootstrapEnvironmentArt.KEYS[j]), "Duplicate environment key: " + key);
⋮----
@Test void sheetFitsConservativeMobileTextureLimits() {
⋮----
assertTrue(BootstrapEnvironmentArt.COLUMNS * BootstrapEnvironmentArt.TILE <= 2048);
assertTrue(rows * BootstrapEnvironmentArt.TILE <= 2048);
⋮----
@Test void requiredSetDressingIsPresent() {
⋮----
for (String key : required) assertTrue(BootstrapEnvironmentArt.indexOf(key) >= 0, key);
⋮----
@Test void floorVariationIsStableAndCoversAllConcreteTiles() {
⋮----
int first = EnvironmentRenderer.floorVariant(x, y);
int second = EnvironmentRenderer.floorVariant(x, y);
assertEquals(first, second);
assertTrue(first >= 0 && first < 3);
⋮----
assertTrue(seen[0] && seen[1] && seen[2]);
⋮----
@Test void microDetailDistributionIsStableSparseAndVaried() {
⋮----
int first = EnvironmentRenderer.detailVariant(x, y);
int second = EnvironmentRenderer.detailVariant(x, y);
⋮----
assertTrue(first >= 0 && first < 8);
⋮----
assertTrue(variants >= 6, "variants=" + variants);
assertTrue(visible >= 8 && visible <= total / 2, "visible=" + visible + "/" + total);
⋮----
@Test void beaconPulseIsDeterministicAndAlwaysNormalized() {
⋮----
float first = EnvironmentRenderer.beaconPulse(time);
float second = EnvironmentRenderer.beaconPulse(time);
assertEquals(first, second, 0f);
assertTrue(first >= 0f && first <= 1f, "pulse=" + first);
⋮----
assertTrue(sawLow && sawHigh);
```

## File: src/test/java/com/deadlinezero/game/visual/BootstrapVfxArtTest.java
```java
final class BootstrapVfxArtTest {
⋮----
@Test void everyRequiredEffectHasMultipleFrames() {
assertEquals(ArtManifest.REQUIRED_FX.length, EFFECTS.length);
⋮----
assertEquals(i, BootstrapVfxArt.effectIndex(key), key);
assertTrue(BootstrapVfxArt.firstTile(key) >= 0, key);
assertTrue(BootstrapVfxArt.frameCount(key) >= 4, key);
assertEquals(ArtManifest.REQUIRED_FX[i], key);
⋮----
@Test void effectBlocksAreContiguousAndNonOverlapping() {
⋮----
int first = BootstrapVfxArt.firstTile(effect);
assertEquals(previousLast + 1, first);
⋮----
assertEquals(BootstrapVfxArt.TOTAL_TILES - 1, previousLast);
assertEquals(EFFECTS.length * BootstrapVfxArt.FRAMES_PER_EFFECT, BootstrapVfxArt.TOTAL_TILES);
⋮----
@Test void sheetStaysSmallAndMobileSafe() {
assertTrue(BootstrapVfxArt.width() <= 1024);
assertTrue(BootstrapVfxArt.height() <= 1024);
assertEquals(384, BootstrapVfxArt.width());
assertEquals(576, BootstrapVfxArt.height());
⋮----
@Test void malformedOrUnknownEffectsAreRejected() {
assertEquals(-1, BootstrapVfxArt.effectIndex(null));
assertEquals(-1, BootstrapVfxArt.firstTile("fx/unknown"));
assertEquals(0, BootstrapVfxArt.frameCount("fx/unknown"));
assertEquals(-1, BootstrapVfxArt.firstTile("muzzle_fire"));
```

## File: src/test/java/com/deadlinezero/game/visual/BossIdentityArtRoutingTest.java
```java
final class BossIdentityArtRoutingTest {
@Test void everyBossIdentityMapsToItsOwnStableArtRoot() {
assertEquals("boss/alpha", GameArt.bossRoot(BossIdentity.ALPHA));
assertEquals("boss/revenant", GameArt.bossRoot(BossIdentity.REVENANT));
assertEquals("boss/warden", GameArt.bossRoot(BossIdentity.WARDEN));
assertEquals("boss/harvester", GameArt.bossRoot(BossIdentity.HARVESTER));
assertEquals("boss/null_archon", GameArt.bossRoot(BossIdentity.NULL_ARCHON));
assertEquals("boss/warden", GameArt.bossRoot(BossIdentity.FROST_COLOSSUS));
assertEquals("boss/alpha", GameArt.bossRoot(null));
⋮----
@Test void everyBossIdentityHasAProductionSafeBootstrapAcrossDirectionsAndMotions() {
⋮----
for (BossIdentity identity : BossIdentity.values()) {
String root = GameArt.bossRoot(identity);
⋮----
boolean directional = DirectionalBootstrapArt.firstTile(key) >= 0;
boolean compactFallback = BootstrapArtCatalog.supports(key);
assertTrue(directional || compactFallback, () -> "Missing boss art fallback: " + key);
```

## File: src/test/java/com/deadlinezero/game/visual/BossPhaseTransitionProfileTest.java
```java
final class BossPhaseTransitionProfileTest {
@Test void phaseThreeEscalatesPresentation() {
var phase2 = BossPhaseTransitionProfile.forPhase(BossIdentity.ALPHA, 2);
var phase3 = BossPhaseTransitionProfile.forPhase(BossIdentity.ALPHA, 3);
assertTrue(phase3.duration() > phase2.duration());
assertTrue(phase3.radiusMultiplier() > phase2.radiusMultiplier());
assertTrue(phase3.vibrationMs() > phase2.vibrationMs());
⋮----
@Test void identitiesHaveDistinctAudioWeight() {
var revenant = BossPhaseTransitionProfile.forPhase(BossIdentity.REVENANT, 3);
var alpha = BossPhaseTransitionProfile.forPhase(BossIdentity.ALPHA, 3);
var warden = BossPhaseTransitionProfile.forPhase(BossIdentity.WARDEN, 3);
assertTrue(revenant.audioPitch() > alpha.audioPitch());
assertTrue(alpha.audioPitch() > warden.audioPitch());
⋮----
@Test void frostColossusHasDistinctHeavyPhasePitch() {
var frost = BossPhaseTransitionProfile.forPhase(BossIdentity.FROST_COLOSSUS, 3);
⋮----
assertTrue(frost.audioPitch() < alpha.audioPitch());
```

## File: src/test/java/com/deadlinezero/game/visual/BossRevealCameraProfileTest.java
```java
final class BossRevealCameraProfileTest {
@Test void revealEnvelopeStartsAndEndsAtRest() {
assertEquals(0f, BossRevealCameraProfile.envelope(BossRevealCameraProfile.DURATION), .0001f);
assertEquals(0f, BossRevealCameraProfile.envelope(0f), .0001f);
float midpoint = BossRevealCameraProfile.envelope(BossRevealCameraProfile.DURATION * .5f);
assertTrue(midpoint > .98f);
⋮----
@Test void revealNeverExceedsComfortBounds() {
⋮----
float e = BossRevealCameraProfile.envelope(remaining);
assertTrue(e >= 0f && e <= 1.001f);
assertTrue(BossRevealCameraProfile.focusWeight(e, false)
⋮----
assertTrue(BossRevealCameraProfile.zoom(.88f, e, false)
⋮----
@Test void reducedMotionDisablesSpecialCameraMovement() {
assertEquals(0f, BossRevealCameraProfile.focusWeight(1f, true), .0001f);
assertEquals(.88f, BossRevealCameraProfile.zoom(.88f, 1f, true), .0001f);
⋮----
@Test void hudSafeOffsetStaysWithinComfortBudget() {
assertTrue(BossRevealCameraProfile.HUD_SAFE_Y_OFFSET >= .35f);
assertTrue(BossRevealCameraProfile.HUD_SAFE_Y_OFFSET <= 1.10f);
assertTrue(BossRevealCameraProfile.MAX_ZOOM_OUT <= .20f);
```

## File: src/test/java/com/deadlinezero/game/visual/ChampionVariantPresentationTest.java
```java
final class ChampionVariantPresentationTest {
@Test void everyChampionVariantHasDistinctShapeSemantics() {
⋮----
EnumSet.noneOf(ChampionVariantPresentation.Marker.class);
⋮----
for (Enemy.Variant variant : Enemy.Variant.values()) {
ChampionVariantPresentation.Marker marker = ChampionVariantPresentation.marker(variant);
⋮----
assertEquals(ChampionVariantPresentation.Marker.NONE, marker);
⋮----
seen.add(marker);
⋮----
assertEquals(8, seen.size());
⋮----
@Test void highImpactVariantsKeepExpectedNonColorMarkers() {
assertEquals(ChampionVariantPresentation.Marker.CHEVRON,
ChampionVariantPresentation.marker(Enemy.Variant.SWIFT));
assertEquals(ChampionVariantPresentation.Marker.ARMOR,
ChampionVariantPresentation.marker(Enemy.Variant.ARMORED));
assertEquals(ChampionVariantPresentation.Marker.CLAW,
ChampionVariantPresentation.marker(Enemy.Variant.FERAL));
assertEquals(ChampionVariantPresentation.Marker.VOLATILE_CORE,
ChampionVariantPresentation.marker(Enemy.Variant.VOLATILE));
assertEquals(ChampionVariantPresentation.Marker.JUGGERNAUT,
ChampionVariantPresentation.marker(Enemy.Variant.JUGGERNAUT));
assertEquals(ChampionVariantPresentation.Marker.RAVAGER,
ChampionVariantPresentation.marker(Enemy.Variant.RAVAGER));
assertEquals(ChampionVariantPresentation.Marker.AEGIS,
ChampionVariantPresentation.marker(Enemy.Variant.AEGIS));
assertEquals(ChampionVariantPresentation.Marker.HUNTER,
ChampionVariantPresentation.marker(Enemy.Variant.HUNTER));
```

## File: src/test/java/com/deadlinezero/game/visual/CharacterSpriteFacingTest.java
```java
final class CharacterSpriteFacingTest {
⋮----
void attackFacingUsesAimVectorOverMovement() {
assertEquals(Direction8.N,
CharacterSpriteRenderer.resolvePlayerFacing(1f, 0f, 0f, 4f, true, Direction8.E));
assertEquals(Direction8.SW,
CharacterSpriteRenderer.resolvePlayerFacing(0f, 1f, -3f, -3f, true, Direction8.N));
⋮----
void nonAttackFacingStillUsesMovement() {
assertEquals(Direction8.W,
CharacterSpriteRenderer.resolvePlayerFacing(-2f, 0f, 0f, 4f, false, Direction8.E));
⋮----
void attackWithoutTargetKeepsMovementOrPreviousFacing() {
assertEquals(Direction8.SE,
CharacterSpriteRenderer.resolvePlayerFacing(2f, -2f, 0f, 0f, true, Direction8.N));
assertEquals(Direction8.NW,
CharacterSpriteRenderer.resolvePlayerFacing(0f, 0f, 0f, 0f, true, Direction8.NW));
```

## File: src/test/java/com/deadlinezero/game/visual/CombatHudLayoutTest.java
```java
final class CombatHudLayoutTest {
⋮----
void hudStaysNonOverlappingAcrossTargetFormatsAndUiScales() {
⋮----
CombatHudLayout.Layout layout = CombatHudLayout.compute(size[0], size[1], scale, boss);
assertTrue(layout.hp().width > 0f);
assertTrue(layout.xp().width > 0f);
assertFalse(layout.hp().overlaps(layout.xp()));
assertFalse(layout.timeline().overlaps(layout.hp()));
assertFalse(layout.timeline().overlaps(layout.xp()));
⋮----
assertTrue(layout.boss() != null);
assertFalse(layout.boss().overlaps(layout.timeline()));
⋮----
assertTrue(layout.dashRadius() * 2f >= 56f);
assertTrue(layout.logicalWidth() >= 1280f);
assertTrue(layout.logicalHeight() >= 720f);
⋮----
void physicalControlCoordinatesMapIntoLogicalHudSpace() {
CombatHudLayout.Layout wide = CombatHudLayout.compute(1536, 691, 1f, false);
⋮----
assertTrue(Math.abs(wide.toLogicalX(dashPhysicalX) - wide.dashX()) < 1f);
assertTrue(Math.abs(wide.toLogicalY(dashPhysicalY) - wide.dashY()) < 1f);
```

## File: src/test/java/com/deadlinezero/game/visual/CombatOverlayViewportTest.java
```java
final class CombatOverlayViewportTest {
@Test void normalizesWidePhoneHiDpiSurfaceToLogicalHudSpace() {
CombatOverlayViewport.Viewport v = CombatOverlayViewport.compute(2880, 1620);
assertEquals(1280f, v.width(), .001f);
assertEquals(720f, v.height(), .001f);
assertEquals(1280f / 2880f, v.scaleX(), .0001f);
assertEquals(720f / 1620f, v.scaleY(), .0001f);
⋮----
@Test void threeChoiceCentersStayInsideLogicalViewport() {
⋮----
float center = v.width() * ((i + 1f) / 4f);
assertTrue(center > 0f && center < v.width());
⋮----
assertEquals(960f, v.width() * .75f, .001f);
⋮----
@Test void physicalTouchMappingPreservesChoiceColumns() {
⋮----
float physicalX = 2160f; // 75% of the reported Android surface.
float logicalX = v.toLogicalX(physicalX);
assertEquals(960f, logicalX, .001f);
assertEquals(2, Math.min(2, (int)(logicalX / v.width() * 3f)));
```

## File: src/test/java/com/deadlinezero/game/visual/CombatVisualEventsProtocolTest.java
```java
final class CombatVisualEventsProtocolTest {
@Test void protocolCuePublishesAndResets() {
CombatVisualEvents.reset();
assertEquals(CombatVisualEvents.ProtocolCue.NONE, CombatVisualEvents.protocolCue());
long before = CombatVisualEvents.protocolSerial();
⋮----
CombatVisualEvents.markProtocol(CombatVisualEvents.ProtocolCue.RHYTHM);
assertEquals(CombatVisualEvents.ProtocolCue.RHYTHM, CombatVisualEvents.protocolCue());
assertEquals(before + 1, CombatVisualEvents.protocolSerial());
assertTrue(CombatVisualEvents.protocolAgeSeconds() < 1f);
⋮----
assertEquals(0L, CombatVisualEvents.protocolSerial());
⋮----
@Test void noneCueDoesNotPublish() {
⋮----
CombatVisualEvents.markProtocol(CombatVisualEvents.ProtocolCue.NONE);
⋮----
@Test void sentinelInterceptPublishesAndResets() {
⋮----
CombatVisualEvents.markSentinelIntercept();
assertEquals(1L, CombatVisualEvents.sentinelInterceptSerial());
assertTrue(CombatVisualEvents.sentinelInterceptAgeSeconds() < 1f);
⋮----
assertEquals(0L, CombatVisualEvents.sentinelInterceptSerial());
⋮----
@Test void synergyUnlockPublishesAndResets() {
⋮----
CombatVisualEvents.markSynergy("hud.synergyUnlocked.arcReactor");
assertEquals("hud.synergyUnlocked.arcReactor", CombatVisualEvents.synergyKey());
assertEquals(1L, CombatVisualEvents.synergySerial());
assertTrue(CombatVisualEvents.synergyAgeSeconds() < 1f);
⋮----
assertEquals(0L, CombatVisualEvents.synergySerial());
assertEquals(null, CombatVisualEvents.synergyKey());
```

## File: src/test/java/com/deadlinezero/game/visual/CompanionRendererTest.java
```java
final class CompanionRendererTest {
@Test void hunterUsesAggressiveWideOrbit() {
assertEquals(2.25f, CompanionRenderer.orbitRadius(DroneDoctrine.HUNTER), .0001f);
assertEquals(145f, CompanionRenderer.orbitSpeedDegrees(DroneDoctrine.HUNTER), .0001f);
assertEquals(.76f, CompanionRenderer.baseSize(DroneDoctrine.HUNTER), .0001f);
⋮----
@Test void sentinelUsesDefensiveCompactOrbit() {
assertEquals(1.55f, CompanionRenderer.orbitRadius(DroneDoctrine.SENTINEL), .0001f);
assertEquals(95f, CompanionRenderer.orbitSpeedDegrees(DroneDoctrine.SENTINEL), .0001f);
assertEquals(.86f, CompanionRenderer.baseSize(DroneDoctrine.SENTINEL), .0001f);
⋮----
@Test void baseDroneKeepsNeutralPresentation() {
assertEquals(1.80f, CompanionRenderer.orbitRadius(DroneDoctrine.NONE), .0001f);
assertEquals(110f, CompanionRenderer.orbitSpeedDegrees(DroneDoctrine.NONE), .0001f);
assertEquals(.80f, CompanionRenderer.baseSize(DroneDoctrine.NONE), .0001f);
```

## File: src/test/java/com/deadlinezero/game/visual/Direction8Test.java
```java
final class Direction8Test {
@Test void cardinalAndDiagonalVectorsMapToStableDirections() {
assertEquals(Direction8.E, Direction8.fromVector(1f, 0f, Direction8.N));
assertEquals(Direction8.NE, Direction8.fromVector(1f, 1f, Direction8.E));
assertEquals(Direction8.N, Direction8.fromVector(0f, 1f, Direction8.E));
assertEquals(Direction8.NW, Direction8.fromVector(-1f, 1f, Direction8.E));
assertEquals(Direction8.W, Direction8.fromVector(-1f, 0f, Direction8.E));
assertEquals(Direction8.SW, Direction8.fromVector(-1f, -1f, Direction8.E));
assertEquals(Direction8.S, Direction8.fromVector(0f, -1f, Direction8.E));
assertEquals(Direction8.SE, Direction8.fromVector(1f, -1f, Direction8.E));
⋮----
@Test void stationaryOrInvalidVectorsPreservePreviousFacing() {
assertEquals(Direction8.NW, Direction8.fromVector(0f, 0f, Direction8.NW));
assertEquals(Direction8.S, Direction8.fromVector(Float.NaN, 1f, Direction8.S));
assertEquals(Direction8.E, Direction8.fromVector(0f, 0f, null));
⋮----
@Test void directionalAtlasPrefixIsCanonical() {
assertEquals("survivor/rex/nw/run",
GameArt.directionalPrefix("survivor/rex", Direction8.NW, GameArt.Motion.RUN));
assertEquals("enemy/brute/e/attack",
GameArt.directionalPrefix("enemy/brute", null, GameArt.Motion.ATTACK));
```

## File: src/test/java/com/deadlinezero/game/visual/DirectionalBootstrapArtTest.java
```java
final class DirectionalBootstrapArtTest {
⋮----
@Test void coreCombatRosterCoversEightDirectionsAndAllMotions() {
⋮----
assertMotion(root, direction, "idle", 2);
assertMotion(root, direction, "run", 3);
assertMotion(root, direction, "attack", 2);
assertMotion(root, direction, "hit", 2);
assertMotion(root, direction, "death", 3);
⋮----
@Test void everyPlayableSurvivorHasFullDirectionalCoverage() {
for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
String root = "survivor/" + survivor.name().toLowerCase();
⋮----
@Test void everyActorDirectionOccupiesExactlyOneTwelveTileMotionBlock() {
⋮----
int idle = DirectionalBootstrapArt.firstTile(root + "/" + direction + "/idle");
int run = DirectionalBootstrapArt.firstTile(root + "/" + direction + "/run");
int attack = DirectionalBootstrapArt.firstTile(root + "/" + direction + "/attack");
int hit = DirectionalBootstrapArt.firstTile(root + "/" + direction + "/hit");
int death = DirectionalBootstrapArt.firstTile(root + "/" + direction + "/death");
assertEquals(idle + 2, run, root + "/" + direction);
assertEquals(idle + 5, attack, root + "/" + direction);
assertEquals(idle + 7, hit, root + "/" + direction);
assertEquals(idle + 9, death, root + "/" + direction);
assertTrue(death + 2 < DirectionalBootstrapArt.TOTAL_TILES, root + "/" + direction);
⋮----
@Test void actorBlocksAreContiguousNonOverlappingAndInsideSheet() {
⋮----
int first = DirectionalBootstrapArt.firstTile(root + "/n/idle");
int last = DirectionalBootstrapArt.firstTile(root + "/nw/death") + 2;
assertTrue(first > previousLast, root);
assertEquals(DirectionalBootstrapArt.ACTOR_BLOCK - 1, last - first, root);
⋮----
assertTrue(previousLast < DirectionalBootstrapArt.TOTAL_TILES);
assertEquals(DirectionalBootstrapArt.ACTOR_BLOCK * ROOTS.length, DirectionalBootstrapArt.TOTAL_TILES);
assertEquals(1344, DirectionalBootstrapArt.TOTAL_TILES);
⋮----
@Test void generatedSheetStaysWithinBaselineGlesTextureDimension() {
⋮----
assertTrue(width <= 2048, "bootstrap sheet width exceeds baseline GLES texture size");
assertTrue(height <= 2048, "bootstrap sheet height exceeds baseline GLES texture size");
assertEquals(768, width);
assertEquals(1792, height);
⋮----
@Test void actorIdentityLookupIsStable() {
assertEquals(ROOTS.length, DirectionalBootstrapArt.ACTOR_COUNT);
assertEquals(8, DIRECTIONS.length);
assertEquals(12, DirectionalBootstrapArt.FRAMES_PER_DIRECTION);
⋮----
assertEquals(i, DirectionalBootstrapArt.actorIndex(ROOTS[i] + "/e/run"));
⋮----
@Test void malformedOrUnsupportedKeysAreRejected() {
assertEquals(-1, DirectionalBootstrapArt.firstTile(null));
assertEquals(-1, DirectionalBootstrapArt.firstTile("survivor/rex/run"));
assertEquals(-1, DirectionalBootstrapArt.firstTile("survivor/unknown/e/run"));
assertEquals(-1, DirectionalBootstrapArt.firstTile("enemy/shielded/e/run"));
assertEquals(-1, DirectionalBootstrapArt.firstTile("enemy/runner/center/run"));
assertEquals(-1, DirectionalBootstrapArt.firstTile("boss/harvester/e/dance"));
⋮----
private static void assertMotion(String root, String direction, String motion, int frames) {
⋮----
int first = DirectionalBootstrapArt.firstTile(key);
assertTrue(first >= 0, () -> "Missing directional bootstrap key: " + key);
assertEquals(frames, DirectionalBootstrapArt.frameCount(key), key);
```

## File: src/test/java/com/deadlinezero/game/visual/DirectionalBootstrapLazyLoadTest.java
```java
final class DirectionalBootstrapLazyLoadTest {
@Test void creationAndKeyLookupDoNotRequireGraphicsAllocation() {
DirectionalBootstrapArt art = assertDoesNotThrow(DirectionalBootstrapArt::create);
⋮----
assertTrue(art.supports("survivor/rex/e/run"));
assertTrue(art.supports("enemy/brute/nw/death"));
assertTrue(art.supports("boss/harvester/s/attack"));
⋮----
assertDoesNotThrow(art::dispose);
```

## File: src/test/java/com/deadlinezero/game/visual/DirectionalGpuMemoryBudgetTest.java
```java
final class DirectionalGpuMemoryBudgetTest {
⋮----
@Test void directionalLayersStayInsideTwentyMiBGpuBudget() {
long baseDirectional = rgbaBytes(768, 1792);
long highResCore = rgbaBytes(HighResDirectionalBootstrapArt.width(), HighResDirectionalBootstrapArt.height());
long highResBoss = rgbaBytes(HighResBossDirectionalArt.width(), HighResBossDirectionalArt.height());
long biomeDirectional = rgbaBytes(BiomeDirectionalBootstrapArt.width(), BiomeDirectionalBootstrapArt.height());
⋮----
assertTrue(baseDirectional <= 6L * MIB, "base directional sheet exceeded 6 MiB");
assertTrue(highResCore <= 6L * MIB, "48px core sheet exceeded 6 MiB");
assertTrue(highResBoss <= 6L * MIB, "64px boss sheet exceeded 6 MiB");
assertTrue(biomeDirectional <= 3L * MIB, "biome directional sheet exceeded 3 MiB");
⋮----
assertTrue(peakDirectionalBytes <= 20L * MIB,
⋮----
private static long rgbaBytes(int width, int height) {
```

## File: src/test/java/com/deadlinezero/game/visual/EnemyHealthBarPresentationTest.java
```java
final class EnemyHealthBarPresentationTest {
@Test void fullHealthStandardEnemyDoesNotRenderWorldBar() {
Enemy enemy = enemy(Enemy.Type.SHAMBLER);
assertFalse(EnemyHealthBarPresentation.visible(enemy));
⋮----
@Test void damagedStandardEnemyRendersWorldBar() {
⋮----
assertTrue(EnemyHealthBarPresentation.visible(enemy));
⋮----
@Test void eliteKeepsPriorityBarAtFullHealth() {
Enemy enemy = enemy(Enemy.Type.ELITE);
⋮----
assertTrue(EnemyHealthBarPresentation.widthMultiplier(enemy) > 1f);
⋮----
@Test void bossNeverUsesWorldBarBecauseHudOwnsBossHealth() {
Enemy boss = enemy(Enemy.Type.BOSS);
assertFalse(EnemyHealthBarPresentation.visible(boss));
⋮----
private static Enemy enemy(Enemy.Type type) {
return new Enemy(type, 0f, 0f, 100f, 1f, .5f, 5f, 1);
```

## File: src/test/java/com/deadlinezero/game/visual/EnvironmentArtCatalogTest.java
```java
final class EnvironmentArtCatalogTest {
@Test void productionKeysAreUniqueAcrossAllFiveBiomes() {
var all = EnvironmentArtCatalog.allProductionKeys();
assertEquals(70, all.size());
assertEquals(70, new HashSet<>(all).size());
⋮----
@Test void preservesTheGenericSlotShapeUnderBiomePrefix() {
String key = EnvironmentArtCatalog.productionKey(
⋮----
assertEquals("environment/cinder_foundry/prop/crate_a", key);
⋮----
@Test void everyBiomeDefinesTheFullFourteenSlotPack() {
for (EnvironmentBiomeRules.Biome biome : EnvironmentBiomeRules.Biome.values()) {
var keys = EnvironmentArtCatalog.productionKeys(biome);
assertEquals(BootstrapEnvironmentArt.KEYS.length, keys.size());
String prefix = "environment/" + EnvironmentArtCatalog.biomeToken(biome) + "/";
assertTrue(keys.stream().allMatch(key -> key.startsWith(prefix)));
```

## File: src/test/java/com/deadlinezero/game/visual/EnvironmentBiomeRulesTest.java
```java
public final class EnvironmentBiomeRulesTest {
@Test public void quarantineOwnsEarlyStages() {
assertEquals(EnvironmentBiomeRules.Biome.QUARANTINE_YARD, EnvironmentBiomeRules.forStage(1));
assertEquals(EnvironmentBiomeRules.Biome.QUARANTINE_YARD, EnvironmentBiomeRules.forStage(9));
assertFalse(EnvironmentBiomeRules.isFoundry(9));
assertFalse(EnvironmentBiomeRules.isNullSector(9));
⋮----
@Test public void foundryOwnsMiddleStagesOnly() {
assertEquals(EnvironmentBiomeRules.Biome.CINDER_FOUNDRY, EnvironmentBiomeRules.forStage(10));
assertEquals(EnvironmentBiomeRules.Biome.CINDER_FOUNDRY, EnvironmentBiomeRules.forStage(19));
assertTrue(EnvironmentBiomeRules.isFoundry(10));
assertFalse(EnvironmentBiomeRules.isFoundry(20));
⋮----
@Test public void nullSectorOwnsStagesTwentyThroughTwentyNine() {
assertEquals(EnvironmentBiomeRules.Biome.NULL_SECTOR, EnvironmentBiomeRules.forStage(20));
assertEquals(EnvironmentBiomeRules.Biome.NULL_SECTOR, EnvironmentBiomeRules.forStage(29));
assertTrue(EnvironmentBiomeRules.isNullSector(20));
assertFalse(EnvironmentBiomeRules.isNullSector(30));
⋮----
@Test public void cryoVaultOwnsStagesThirtyThroughThirtyNine() {
assertEquals(EnvironmentBiomeRules.Biome.CRYO_VAULT, EnvironmentBiomeRules.forStage(30));
assertEquals(EnvironmentBiomeRules.Biome.CRYO_VAULT, EnvironmentBiomeRules.forStage(39));
assertTrue(EnvironmentBiomeRules.isCryoVault(30));
assertFalse(EnvironmentBiomeRules.isCryoVault(40));
⋮----
@Test public void cryogenicDepthsStartsAtStageForty() {
assertEquals(EnvironmentBiomeRules.Biome.CRYOGENIC_DEPTHS, EnvironmentBiomeRules.forStage(40));
assertEquals(EnvironmentBiomeRules.Biome.CRYOGENIC_DEPTHS, EnvironmentBiomeRules.forStage(55));
assertTrue(EnvironmentBiomeRules.isCryogenicDepths(40));
assertFalse(EnvironmentBiomeRules.isCryogenicDepths(39));
⋮----
@Test public void invalidStagesSanitizeToFirstBiome() {
assertEquals(EnvironmentBiomeRules.Biome.QUARANTINE_YARD, EnvironmentBiomeRules.forStage(0));
assertEquals(EnvironmentBiomeRules.Biome.QUARANTINE_YARD, EnvironmentBiomeRules.forStage(-50));
```

## File: src/test/java/com/deadlinezero/game/visual/FinalArtContractTest.java
```java
final class FinalArtContractTest {
@Test void standardActorFrameFloorMatchesProductionSpec() {
assertEquals(4, FinalArtContract.minimumFrames(GameArt.Motion.IDLE, false));
assertEquals(8, FinalArtContract.minimumFrames(GameArt.Motion.RUN, false));
assertEquals(6, FinalArtContract.minimumFrames(GameArt.Motion.ATTACK, false));
assertEquals(3, FinalArtContract.minimumFrames(GameArt.Motion.HIT, false));
assertEquals(8, FinalArtContract.minimumFrames(GameArt.Motion.DEATH, false));
assertEquals(232, FinalArtContract.minimumDirectionalActorFrames(false));
⋮----
@Test void bossFrameFloorIsHigherForReadability() {
assertEquals(6, FinalArtContract.minimumFrames(GameArt.Motion.IDLE, true));
assertEquals(8, FinalArtContract.minimumFrames(GameArt.Motion.ATTACK, true));
assertEquals(4, FinalArtContract.minimumFrames(GameArt.Motion.HIT, true));
assertEquals(10, FinalArtContract.minimumFrames(GameArt.Motion.DEATH, true));
assertEquals(288, FinalArtContract.minimumDirectionalActorFrames(true));
⋮----
@Test void productionContractKeepsEightDirectionsAndFastRunHeadroom() {
assertEquals(8, FinalArtContract.directions());
assertTrue(FinalArtContract.preferredFastRunFrames() >= 10);
```

## File: src/test/java/com/deadlinezero/game/visual/FinalArtLayoutContractTest.java
```java
final class FinalArtLayoutContractTest {
@Test void productionLayoutMatchesRuntimeFrameContract() throws IOException {
JsonValue layout = new JsonReader().parse(Files.readString(layoutPath()));
⋮----
JsonValue directions = layout.get("directions");
assertEquals(FinalArtContract.directions(), directions.size);
⋮----
for (Direction8 direction : Direction8.values()) {
assertEquals(direction.atlasToken(), directions.getString(directionIndex++));
⋮----
JsonValue motionOrder = layout.get("motionOrder");
assertEquals(GameArt.Motion.values().length, motionOrder.size);
⋮----
for (GameArt.Motion motion : GameArt.Motion.values()) {
assertEquals(motion.name().toLowerCase(), motionOrder.getString(motionIndex++));
⋮----
JsonValue standard = layout.get("standardFrames");
JsonValue boss = layout.get("bossFrames");
⋮----
String key = motion.name().toLowerCase();
assertEquals(FinalArtContract.minimumFrames(motion, false), standard.getInt(key), key + " standard frames");
assertEquals(FinalArtContract.minimumFrames(motion, true), boss.getInt(key), key + " boss frames");
⋮----
int expectedStandardFrames = FinalArtContract.minimumDirectionalActorFrames(false);
int expectedBossFrames = FinalArtContract.minimumDirectionalActorFrames(true);
assertEquals(232, expectedStandardFrames);
assertEquals(288, expectedBossFrames);
⋮----
@Test void everyFastRunOverrideUsesRuntimePreferredBudget() throws IOException {
JsonValue actors = new JsonReader().parse(Files.readString(layoutPath())).get("actors");
⋮----
if (!actor.has("runFrames")) continue;
assertEquals(FinalArtContract.preferredFastRunFrames(), actor.getInt("runFrames"), actor.getString("id"));
fastActors.add(actor.getString("id"));
⋮----
assertTrue(fastActors.contains("wraith"));
assertTrue(fastActors.contains("forge_hound"));
assertTrue(fastActors.contains("phase_stalker"));
⋮----
private static Path layoutPath() {
Path direct = Path.of("art_sources", "final-sprite-layout.json");
if (Files.isRegularFile(direct)) return direct;
Path fromCore = Path.of("..", "art_sources", "final-sprite-layout.json");
if (Files.isRegularFile(fromCore)) return fromCore;
throw new IllegalStateException("Unable to locate art_sources/final-sprite-layout.json from "
+ Path.of("").toAbsolutePath());
```

## File: src/test/java/com/deadlinezero/game/visual/FoundryHazardPresentationTest.java
```java
final class FoundryHazardPresentationTest {
@Test void foundryTypesRouteToDistinctProfilesAndCues() {
FoundryHazardPresentation.Profile lava = FoundryHazardPresentation.forType(ArenaHazardRuntime.Type.LAVA_VENT);
FoundryHazardPresentation.Profile steam = FoundryHazardPresentation.forType(ArenaHazardRuntime.Type.STEAM_JET);
FoundryHazardPresentation.Profile heat = FoundryHazardPresentation.forType(ArenaHazardRuntime.Type.HEAT_LINE);
⋮----
assertEquals(AudioDirector.Cue.FOUNDRY_LAVA, lava.cue);
assertEquals(AudioDirector.Cue.FOUNDRY_STEAM, steam.cue);
assertEquals(AudioDirector.Cue.FOUNDRY_HEAT, heat.cue);
assertNotEquals(lava.pulseSpeed, steam.pulseSpeed);
assertNotEquals(steam.pulseSpeed, heat.pulseSpeed);
assertTrue(lava.spokes > 0);
assertTrue(steam.spokes > 0);
assertTrue(heat.spokes > 0);
⋮----
@Test void onlyBiomeSpecificTypesAreClassifiedAsFoundry() {
assertTrue(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.LAVA_VENT));
assertTrue(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.STEAM_JET));
assertTrue(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.HEAT_LINE));
assertFalse(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.ORBITAL_STRIKE));
assertFalse(FoundryHazardPresentation.isFoundry(ArenaHazardRuntime.Type.DEATH_BURST));
```

## File: src/test/java/com/deadlinezero/game/visual/HighResBossDirectionalArtTest.java
```java
final class HighResBossDirectionalArtTest {
⋮----
@Test void sheetStaysWithinConservativeGlesDimensions() {
assertEquals(64, HighResBossDirectionalArt.TILE);
assertEquals(4, HighResBossDirectionalArt.ACTOR_COUNT);
assertEquals(384, HighResBossDirectionalArt.TOTAL_TILES);
assertEquals(1024, HighResBossDirectionalArt.width());
assertEquals(1536, HighResBossDirectionalArt.height());
assertTrue(HighResBossDirectionalArt.width() <= 2048);
assertTrue(HighResBossDirectionalArt.height() <= 2048);
⋮----
@Test void everyBossDirectionAndMotionHasExpectedAnimationFrames() {
⋮----
assertMotion(root, direction, "idle", 2);
assertMotion(root, direction, "run", 3);
assertMotion(root, direction, "attack", 2);
assertMotion(root, direction, "hit", 2);
assertMotion(root, direction, "death", 3);
⋮----
@Test void actorAndDirectionBlocksNeverOverlap() {
⋮----
assertEquals(base, HighResBossDirectionalArt.firstTile(root + "idle"));
assertEquals(base + 2, HighResBossDirectionalArt.firstTile(root + "run"));
assertEquals(base + 5, HighResBossDirectionalArt.firstTile(root + "attack"));
assertEquals(base + 7, HighResBossDirectionalArt.firstTile(root + "hit"));
assertEquals(base + 9, HighResBossDirectionalArt.firstTile(root + "death"));
⋮----
private static void assertMotion(String root, String direction, String motion, int frames) {
⋮----
assertTrue(HighResBossDirectionalArt.firstTile(key) >= 0, key);
assertEquals(frames, HighResBossDirectionalArt.frameCount(key), key);
```

## File: src/test/java/com/deadlinezero/game/visual/HighResDirectionalBootstrapArtTest.java
```java
final class HighResDirectionalBootstrapArtTest {
⋮----
@Test void highVisibilityRosterHasFullEightWayMotionCoverage() {
⋮----
assertMotion(root, direction, "idle", 2);
assertMotion(root, direction, "run", 3);
assertMotion(root, direction, "attack", 2);
assertMotion(root, direction, "hit", 2);
assertMotion(root, direction, "death", 3);
⋮----
@Test void eachDirectionUsesOneContiguousTwelveTileBlock() {
⋮----
int idle = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/idle");
int run = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/run");
int attack = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/attack");
int hit = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/hit");
int death = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/death");
assertEquals(idle + 2, run);
assertEquals(idle + 5, attack);
assertEquals(idle + 7, hit);
assertEquals(idle + 9, death);
assertTrue(death + 2 < HighResDirectionalBootstrapArt.TOTAL_TILES);
⋮----
@Test void memoryFootprintStaysInsideConservativeGlesDimension() {
assertEquals(7, HighResDirectionalBootstrapArt.ACTOR_COUNT);
assertEquals(12, HighResDirectionalBootstrapArt.FRAMES_PER_DIRECTION);
assertEquals(672, HighResDirectionalBootstrapArt.TOTAL_TILES);
assertEquals(768, HighResDirectionalBootstrapArt.width());
assertEquals(2016, HighResDirectionalBootstrapArt.height());
assertTrue(HighResDirectionalBootstrapArt.width() <= 2048);
assertTrue(HighResDirectionalBootstrapArt.height() <= 2048);
⋮----
@Test void intentionallyFallsBackForLessVisibleRoster() {
assertEquals(-1, HighResDirectionalBootstrapArt.firstTile("enemy/brute/e/run"));
assertEquals(-1, HighResDirectionalBootstrapArt.firstTile("enemy/ranged/e/run"));
assertEquals(-1, HighResDirectionalBootstrapArt.firstTile("boss/alpha/e/run"));
assertEquals(-1, HighResDirectionalBootstrapArt.firstTile("survivor/unknown/e/run"));
⋮----
@Test void actorLookupOrderIsStable() {
⋮----
assertEquals(i, HighResDirectionalBootstrapArt.actorIndex(ROOTS[i] + "/e/run"));
⋮----
private static void assertMotion(String root, String direction, String motion, int frames) {
⋮----
assertTrue(HighResDirectionalBootstrapArt.firstTile(key) >= 0, key);
assertEquals(frames, HighResDirectionalBootstrapArt.frameCount(key), key);
```

## File: src/test/java/com/deadlinezero/game/visual/HostileProjectilePresentationTest.java
```java
final class HostileProjectilePresentationTest {
@AfterEach void reset() { RunStageContext.begin(1, 0, 0); }
⋮----
@Test void cinderGunnerAndStaticSeerUseDistinctSourceStyles() {
RunStageContext.begin(12, 4, 0);
Enemy cinder = new Enemy(Enemy.Type.RANGED, 0f, 0f, 50f, 2f, .4f, 10f, 5);
assertEquals(EnemyProjectile.Style.CINDER, HostileProjectilePresentation.styleFor(cinder));
⋮----
RunStageContext.begin(22, 4, 0);
Enemy seer = new Enemy(Enemy.Type.RANGED, 0f, 0f, 50f, 2f, .4f, 10f, 5);
assertEquals(EnemyProjectile.Style.STATIC, HostileProjectilePresentation.styleFor(seer));
⋮----
@Test void nullSupportAndPhantomUseVoidIdentity() {
RunStageContext.begin(22, 5, 0);
Enemy ward = new Enemy(Enemy.Type.REGENERATOR, 0f, 0f, 80f, 2f, .45f, 10f, 5);
Enemy stalker = new Enemy(Enemy.Type.PHANTOM, 0f, 0f, 60f, 3f, .4f, 10f, 5);
assertEquals(EnemyProjectile.Style.NULL, HostileProjectilePresentation.styleFor(ward));
assertEquals(EnemyProjectile.Style.NULL, HostileProjectilePresentation.styleFor(stalker));
⋮----
@Test void frostColossusUsesColdEnergyProjectileStyle() {
RunStageContext.begin(40, 6, 0);
Enemy frost = new Enemy(Enemy.Type.BOSS, 0f, 0f, 5000f, 1f, 1f, 20f, 100);
assertEquals(EnemyProjectile.Style.STATIC, HostileProjectilePresentation.styleFor(frost));
⋮----
@Test void ordinaryEarlyGameEnemyRemainsDefault() {
RunStageContext.begin(4, 2, 0);
Enemy ranged = new Enemy(Enemy.Type.RANGED, 0f, 0f, 50f, 2f, .4f, 10f, 5);
assertEquals(EnemyProjectile.Style.DEFAULT, HostileProjectilePresentation.styleFor(ranged));
⋮----
@Test void presentationMultipliersNeverChangeCollisionRadiusContract() {
for (EnemyProjectile.Style style : EnemyProjectile.Style.values()) {
float multiplier = HostileProjectilePresentation.coreRadiusMultiplier(style);
assertTrue(multiplier >= .80f && multiplier <= 1.25f);
```

## File: src/test/java/com/deadlinezero/game/visual/NullBootstrapVfxArtTest.java
```java
final class NullBootstrapVfxArtTest {
@Test void exposesEveryNullArchonEffectAsEightFrameAnimation() {
⋮----
assertEquals(i * NullBootstrapVfxArt.FRAMES_PER_EFFECT, NullBootstrapVfxArt.firstTile(keys[i]));
⋮----
assertEquals(24, NullBootstrapVfxArt.TOTAL_TILES);
assertEquals(512, NullBootstrapVfxArt.width());
assertEquals(192, NullBootstrapVfxArt.height());
⋮----
@Test void createAndLookupDoNotRequireGraphicsContext() {
NullBootstrapVfxArt art = NullBootstrapVfxArt.create();
⋮----
assertTrue(art.supports("fx/null_archon_aura"));
assertTrue(art.supports("fx/null_archon_portal"));
assertTrue(art.supports("fx/null_archon_fracture"));
⋮----
art.dispose();
⋮----
@Test void rejectsUnknownEffects() {
assertEquals(-1, NullBootstrapVfxArt.firstTile("fx/null_archon_unknown"));
assertTrue(NullBootstrapVfxArt.firstTile(null) < 0);
```

## File: src/test/java/com/deadlinezero/game/visual/NullHazardPresentationTest.java
```java
final class NullHazardPresentationTest {
@Test void nullTypesRouteToDistinctProfilesAndCues() {
NullHazardPresentation.Profile rift = NullHazardPresentation.forType(ArenaHazardRuntime.Type.VOID_RIFT);
NullHazardPresentation.Profile statik = NullHazardPresentation.forType(ArenaHazardRuntime.Type.STATIC_BURST);
NullHazardPresentation.Profile beam = NullHazardPresentation.forType(ArenaHazardRuntime.Type.NULL_BEAM);
⋮----
assertEquals(AudioDirector.Cue.NULL_RIFT, rift.cue);
assertEquals(AudioDirector.Cue.NULL_STATIC, statik.cue);
assertEquals(AudioDirector.Cue.NULL_BEAM, beam.cue);
assertNotEquals(rift.pulseSpeed, statik.pulseSpeed);
assertNotEquals(statik.pulseSpeed, beam.pulseSpeed);
assertTrue(rift.spokes > 0);
assertTrue(statik.spokes > 0);
assertTrue(beam.spokes > 0);
⋮----
@Test void nullClassificationDoesNotCaptureOtherBiomeHazards() {
assertTrue(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.VOID_RIFT));
assertTrue(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.STATIC_BURST));
assertTrue(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.NULL_BEAM));
assertFalse(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.LAVA_VENT));
assertFalse(NullHazardPresentation.isNull(ArenaHazardRuntime.Type.ORBITAL_STRIKE));
```

## File: src/test/java/com/deadlinezero/game/visual/OnboardingHintPolicyTest.java
```java
final class OnboardingHintPolicyTest {
@Test void stepsAdvanceInGameplayOrder() {
assertEquals(0, OnboardingHintPolicy.step(false, false, false, false));
assertEquals(1, OnboardingHintPolicy.step(true, false, false, false));
assertEquals(2, OnboardingHintPolicy.step(true, true, false, false));
assertEquals(3, OnboardingHintPolicy.step(true, true, true, false));
assertEquals(OnboardingHintPolicy.NONE,
OnboardingHintPolicy.step(true, true, true, true));
⋮----
@Test void eachHintAutoHidesWithoutCompletingOnboarding() {
assertTrue(OnboardingHintPolicy.visible(false, 1, 0f));
assertTrue(OnboardingHintPolicy.visible(false, 1, OnboardingHintPolicy.MAX_VISIBLE_SECONDS - .01f));
assertFalse(OnboardingHintPolicy.visible(false, 1, OnboardingHintPolicy.MAX_VISIBLE_SECONDS));
assertFalse(OnboardingHintPolicy.visible(false, OnboardingHintPolicy.NONE, 0f));
assertFalse(OnboardingHintPolicy.visible(true, 1, 0f));
```

## File: src/test/java/com/deadlinezero/game/visual/PlayerProjectilePresentationTest.java
```java
final class PlayerProjectilePresentationTest {
@Test void allTwelveWeaponFamiliesHaveDistinctPresentationStyles() {
⋮----
EnumSet.noneOf(PlayerProjectilePresentation.Style.class);
⋮----
for (WeaponDefinition weapon : WeaponCatalog.all()) {
PlayerProjectilePresentation.Profile profile = PlayerProjectilePresentation.forWeapon(
⋮----
styles.add(profile.style());
assertSane(profile);
⋮----
assertEquals(12, styles.size());
⋮----
@Test void allFiveSignatureShotsEscalateTheirExpectedFamily() {
assertSignature("ion_needle", DamageElement.SHOCK, WeaponSignatureRuntime.Kind.ION_OVERCHARGE,
⋮----
assertSignature("cinder_cannon", DamageElement.FIRE, WeaponSignatureRuntime.Kind.CINDER_OVERHEAT,
⋮----
assertSignature("tempest_burst", DamageElement.SHOCK, WeaponSignatureRuntime.Kind.TEMPEST_SURGE,
⋮----
assertSignature("whiteout_shard", DamageElement.FROST, WeaponSignatureRuntime.Kind.WHITEOUT_SHATTER,
⋮----
assertSignature("phoenix_repeater", DamageElement.FIRE, WeaponSignatureRuntime.Kind.PHOENIX_IGNITION,
⋮----
@Test void criticalPresentationNeverShrinksItsBaseProfile() {
⋮----
PlayerProjectilePresentation.Profile base = PlayerProjectilePresentation.forWeapon(
⋮----
PlayerProjectilePresentation.Profile crit = PlayerProjectilePresentation.forWeapon(
⋮----
assertTrue(crit.coreScale() >= base.coreScale());
assertTrue(crit.impactScale() >= base.impactScale());
assertTrue(crit.alpha() >= base.alpha());
⋮----
private static void assertSignature(String weaponId, DamageElement element,
⋮----
PlayerProjectilePresentation.Profile signature = PlayerProjectilePresentation.forWeapon(
⋮----
assertEquals(expectedStyle, signature.style());
assertTrue(signature.signature());
assertTrue(signature.trailLength() >= base.trailLength());
assertTrue(signature.coreScale() >= base.coreScale());
assertTrue(signature.impactScale() >= base.impactScale());
assertSane(signature);
⋮----
private static void assertSane(PlayerProjectilePresentation.Profile profile) {
assertTrue(profile.trailLength() > 0f);
assertTrue(profile.trailWidth() > 0f);
assertTrue(profile.alpha() > 0f && profile.alpha() <= 1f);
assertTrue(profile.coreScale() > 0f);
assertTrue(profile.impactScale() > 0f);
```

## File: src/test/java/com/deadlinezero/game/visual/SingularityImpactTrackerTest.java
```java
final class SingularityImpactTrackerTest {
@Test void activeSingularityEmitsExactlyOneImpactWhenItEnds() {
SingularityImpactTracker tracker = new SingularityImpactTracker();
⋮----
Projectile projectile = singularity(1L, 3f, -2f, true);
projectiles.add(projectile);
⋮----
tracker.update(projectiles, .016f);
assertEquals(0, tracker.impacts().size);
assertEquals(0, tracker.consumeTriggeredCount());
⋮----
assertEquals(1, tracker.impacts().size);
assertEquals(1, tracker.consumeTriggeredCount());
assertEquals(3f, tracker.impacts().first().x, .0001f);
assertEquals(-2f, tracker.impacts().first().y, .0001f);
⋮----
@Test void pooledReuseStillEmitsPreviousSingularityAtLastKnownPosition() {
⋮----
Projectile projectile = singularity(4L, -5f, 6f, true);
⋮----
projectile.position.set(11f, 12f);
⋮----
assertEquals(-5f, tracker.impacts().first().x, .0001f);
assertEquals(6f, tracker.impacts().first().y, .0001f);
⋮----
@Test void singularityThatEndsBeforeFirstRenderStillProducesImpact() {
⋮----
projectiles.add(singularity(9L, 1.5f, 2.5f, false));
⋮----
assertEquals(1.5f, tracker.impacts().first().x, .0001f);
assertEquals(2.5f, tracker.impacts().first().y, .0001f);
⋮----
@Test void impactsExpireAfterTheirVisualLifetime() {
⋮----
projectiles.add(singularity(2L, 0f, 0f, false));
tracker.update(projectiles, 0f);
⋮----
tracker.update(projectiles, SingularityImpactTracker.IMPACT_LIFETIME + .01f);
⋮----
@Test void impactProgressMovesFromZeroTowardOne() {
⋮----
projectiles.add(singularity(3L, 0f, 0f, false));
⋮----
float start = tracker.impacts().first().progress();
tracker.update(projectiles, SingularityImpactTracker.IMPACT_LIFETIME * .5f);
float middle = tracker.impacts().first().progress();
assertEquals(0f, start, .0001f);
assertTrue(middle > .45f && middle < .55f);
⋮----
private static Projectile singularity(long generation, float x, float y, boolean active) {
Projectile projectile = new Projectile();
⋮----
projectile.position.set(x, y);
```

## File: src/test/java/com/deadlinezero/game/visual/SpecialistPresentationTest.java
```java
final class SpecialistPresentationTest {
@Test void specialistProfilesArePurposefullyDistinct() {
var shambler = ArtProfileCatalog.enemy(Enemy.Type.SHAMBLER);
var shielded = ArtProfileCatalog.enemy(Enemy.Type.SHIELDED);
var regenerator = ArtProfileCatalog.enemy(Enemy.Type.REGENERATOR);
var phantom = ArtProfileCatalog.enemy(Enemy.Type.PHANTOM);
⋮----
assertTrue(shielded.height() > shambler.height());
assertTrue(regenerator.height() > shambler.height());
assertTrue(phantom.height() > 0f);
assertNotEquals(shambler, shielded);
assertNotEquals(shambler, regenerator);
assertNotEquals(shambler, phantom);
```

## File: src/test/java/com/deadlinezero/game/visual/UpgradePresentationTest.java
```java
final class UpgradePresentationTest {
@Test void everyUpgradeHasAVisualArchetype() {
for (Upgrade upgrade : Upgrade.values()) {
assertNotNull(UpgradePresentation.archetype(upgrade), upgrade.name());
⋮----
assertEquals(60, Upgrade.values().length);
⋮----
@Test void allSixteenVisualArchetypesAreActuallyUsed() {
⋮----
EnumSet.noneOf(UpgradePresentation.Archetype.class);
for (Upgrade upgrade : Upgrade.values()) seen.add(UpgradePresentation.archetype(upgrade));
assertEquals(EnumSet.allOf(UpgradePresentation.Archetype.class), seen);
⋮----
@Test void signatureAbilityFamiliesRemainSemanticallyDistinct() {
assertEquals(UpgradePresentation.Archetype.SHOCK,
UpgradePresentation.archetype(Upgrade.TESLA_ORB));
assertEquals(UpgradePresentation.Archetype.MISSILE,
UpgradePresentation.archetype(Upgrade.MISSILE_SWARM));
assertEquals(UpgradePresentation.Archetype.FROST,
UpgradePresentation.archetype(Upgrade.CRYO_NOVA));
assertEquals(UpgradePresentation.Archetype.DRONE,
UpgradePresentation.archetype(Upgrade.DRONE));
assertEquals(UpgradePresentation.Archetype.ORBITAL,
UpgradePresentation.archetype(Upgrade.ORBITAL));
assertEquals(UpgradePresentation.Archetype.PROTOCOL,
UpgradePresentation.archetype(Upgrade.REACTION_CASCADE));
⋮----
@Test void nullUpgradeFallsBackToDamageInsteadOfCrashing() {
assertEquals(UpgradePresentation.Archetype.DAMAGE,
UpgradePresentation.archetype(null));
assertTrue(UpgradePresentation.Archetype.values().length >= 16);
```

## File: src/test/java/com/deadlinezero/game/visual/WeaponLegendaryPresentationTest.java
```java
final class WeaponLegendaryPresentationTest {
@Test void everyWeaponFamilyLegendaryHasDistinctPresentation() {
⋮----
for (WeaponLegendaryPresentation.Style style : WeaponLegendaryPresentation.Style.values()) {
⋮----
assertTrue(labels.add(style.label), "duplicate legendary presentation label: " + style.label);
assertTrue(style.r >= 0f && style.r <= 1f);
assertTrue(style.g >= 0f && style.g <= 1f);
assertTrue(style.b >= 0f && style.b <= 1f);
⋮----
assertEquals(9, labels.size());
⋮----
@Test void stateRoutesAllNineWeaponFamilies() {
assertStyle(WeaponLegendaryPresentation.Style.VANGUARD, p -> p.legendary.grantVanguardProtocol());
assertStyle(WeaponLegendaryPresentation.Style.SCATTER, p -> p.legendary.grantScatterMaelstrom());
assertStyle(WeaponLegendaryPresentation.Style.RAIL, p -> p.legendary.grantRailPhaseLance());
assertStyle(WeaponLegendaryPresentation.Style.INFERNO, p -> p.legendary.grantInfernoPyroclasm());
assertStyle(WeaponLegendaryPresentation.Style.CRYO, p -> p.legendary.grantCryoPrism());
assertStyle(WeaponLegendaryPresentation.Style.ARC, p -> p.legendary.grantArcOverload());
assertStyle(WeaponLegendaryPresentation.Style.BREACHER, p -> p.legendary.grantBreacherRupture());
assertStyle(WeaponLegendaryPresentation.Style.ION, p -> p.legendary.grantIonCascade());
assertStyle(WeaponLegendaryPresentation.Style.CINDER, p -> p.legendary.grantCinderFurnace());
⋮----
@Test void genericLegendaryDoesNotPretendToBeWeaponFamilyPerk() {
Player p = new Player(0f, 0f);
p.legendary.grantOverdrive();
assertEquals(WeaponLegendaryPresentation.Style.NONE, WeaponLegendaryPresentation.style(p));
assertNotEquals("OVERDRIVE", WeaponLegendaryPresentation.Style.VANGUARD.label);
⋮----
private static void assertStyle(WeaponLegendaryPresentation.Style expected, Grant grant) {
⋮----
grant.apply(p);
assertEquals(expected, WeaponLegendaryPresentation.style(p));
⋮----
private interface Grant { void apply(Player player); }
```

## File: src/test/java/com/deadlinezero/game/world/ArenaHazardRuntimeTest.java
```java
final class ArenaHazardRuntimeTest {
@Test void deathBurstCannotDamageDuringWarningAndHitsOnlyOnce() {
ArenaHazardRuntime runtime = new ArenaHazardRuntime(12, 4, 10);
runtime.scheduleDeathBurst(2f, -1f, 2.2f, 31f);
⋮----
assertEquals(0f, runtime.consumePlayerDamage(2f, -1f, .4f), .0001f);
runtime.update(.30f, 0f, 0f);
assertEquals(ArenaHazardRuntime.Phase.WARNING, runtime.hazards().get(0).phase());
⋮----
runtime.update(.19f, 0f, 0f);
assertEquals(ArenaHazardRuntime.Phase.ACTIVE, runtime.hazards().get(0).phase());
assertEquals(31f, runtime.consumePlayerDamage(2f, -1f, .4f), .0001f);
⋮----
@Test void playerOutsideActiveHazardIsNotHit() {
⋮----
runtime.scheduleDeathBurst(0f, 0f, 2f, 20f);
runtime.update(.49f, 0f, 0f);
assertEquals(0f, runtime.consumePlayerDamage(4f, 0f, .4f), .0001f);
assertFalse(runtime.hazards().get(0).playerDamageConsumed());
⋮----
@Test void periodicHazardsUnlockAtThreatFive() {
ArenaHazardRuntime standard = new ArenaHazardRuntime(10, 1, 4);
assertFalse(standard.periodicHazardsEnabled());
assertTrue(Float.isInfinite(standard.periodicInterval()));
⋮----
ArenaHazardRuntime ascended = new ArenaHazardRuntime(10, 1, 5);
assertTrue(ascended.periodicHazardsEnabled());
assertTrue(Float.isFinite(ascended.periodicInterval()));
⋮----
@Test void periodicStrikePlacementIsDeterministicForSameRun() {
ArenaHazardRuntime first = new ArenaHazardRuntime(14, 22, 9);
ArenaHazardRuntime second = new ArenaHazardRuntime(14, 22, 9);
float interval = first.periodicInterval();
⋮----
first.update(.05f, 3f, -2f);
second.update(.05f, 3f, -2f);
⋮----
assertEquals(1, first.activeCount());
assertEquals(1, second.activeCount());
ArenaHazardRuntime.Hazard a = first.hazards().get(0);
ArenaHazardRuntime.Hazard b = second.hazards().get(0);
assertEquals(a.type(), b.type());
assertEquals(a.x(), b.x(), .0001f);
assertEquals(a.y(), b.y(), .0001f);
assertEquals(a.radius(), b.radius(), .0001f);
assertEquals(a.damage(), b.damage(), .0001f);
⋮----
@Test void higherThreatRaisesPeriodicDamageAndFrequency() {
ArenaHazardRuntime tier5 = new ArenaHazardRuntime(12, 0, 5);
ArenaHazardRuntime tier20 = new ArenaHazardRuntime(12, 0, 20);
assertTrue(tier20.periodicInterval() < tier5.periodicInterval());
⋮----
float interval = tier20.periodicInterval();
for (float elapsed = 0f; elapsed < interval + .1f; elapsed += .05f) tier20.update(.05f, 0f, 0f);
assertTrue(tier20.hazards().get(0).damage() > 14f);
⋮----
@Test void foundryHazardsOwnStagesTenThroughNineteen() {
ArenaHazardRuntime yard = new ArenaHazardRuntime(9, 2, 0);
assertFalse(yard.foundryHazardsEnabled());
assertTrue(Float.isInfinite(yard.foundryHazardInterval()));
⋮----
ArenaHazardRuntime foundry = new ArenaHazardRuntime(10, 2, 0);
assertTrue(foundry.foundryHazardsEnabled());
assertTrue(Float.isFinite(foundry.foundryHazardInterval()));
⋮----
ArenaHazardRuntime lastFoundry = new ArenaHazardRuntime(19, 2, 0);
assertTrue(lastFoundry.foundryHazardsEnabled());
ArenaHazardRuntime nullSector = new ArenaHazardRuntime(20, 2, 0);
assertFalse(nullSector.foundryHazardsEnabled());
assertTrue(Float.isInfinite(nullSector.foundryHazardInterval()));
⋮----
@Test void foundryHazardsAreDeterministicForSameRun() {
ArenaHazardRuntime first = new ArenaHazardRuntime(18, 37, 6);
ArenaHazardRuntime second = new ArenaHazardRuntime(18, 37, 6);
float untilFirst = first.foundryHazardInterval() * .72f + .05f;
⋮----
first.update(.05f, 4f, -1f);
second.update(.05f, 4f, -1f);
⋮----
assertEquals(first.activeCount(), second.activeCount());
assertTrue(first.activeCount() > 0);
for (int i = 0; i < first.activeCount(); i++) {
ArenaHazardRuntime.Hazard a = first.hazards().get(i);
ArenaHazardRuntime.Hazard b = second.hazards().get(i);
⋮----
@Test void laterFoundryStagesIncreasePressureButStayBounded() {
ArenaHazardRuntime stage10 = new ArenaHazardRuntime(10, 0, 0);
ArenaHazardRuntime stage19 = new ArenaHazardRuntime(19, 0, 0);
assertTrue(stage19.foundryHazardInterval() < stage10.foundryHazardInterval());
assertTrue(stage19.foundryHazardInterval() >= 10.2f);
⋮----
@Test void foundryWarningEventuallyBecomesDamageableAndStillHitsOnce() {
ArenaHazardRuntime runtime = new ArenaHazardRuntime(10, 3, 0);
float untilFirst = runtime.foundryHazardInterval() * .72f + .05f;
for (float elapsed = 0f; elapsed < untilFirst; elapsed += .05f) runtime.update(.05f, 0f, 0f);
ArenaHazardRuntime.Hazard target = runtime.hazards().get(0);
assertEquals(0f, runtime.consumePlayerDamage(target.x(), target.y(), .1f), .0001f);
⋮----
runtime.update(.05f, 0f, 0f);
damage = runtime.consumePlayerDamage(target.x(), target.y(), .1f);
⋮----
assertTrue(damage > 0f);
⋮----
@Test void nullSectorHazardsStartAtStageTwentyAndReplaceFoundryPressure() {
ArenaHazardRuntime stage19 = new ArenaHazardRuntime(19, 4, 0);
assertFalse(stage19.nullSectorHazardsEnabled());
assertTrue(Float.isInfinite(stage19.nullSectorHazardInterval()));
⋮----
ArenaHazardRuntime stage20 = new ArenaHazardRuntime(20, 4, 0);
assertTrue(stage20.nullSectorHazardsEnabled());
assertFalse(stage20.foundryHazardsEnabled());
assertTrue(Float.isFinite(stage20.nullSectorHazardInterval()));
⋮----
float untilFirst = stage20.nullSectorHazardInterval() * .68f + .05f;
for (float elapsed = 0f; elapsed < untilFirst; elapsed += .05f) stage20.update(.05f, 1f, 2f);
assertTrue(stage20.activeCount() > 0);
for (ArenaHazardRuntime.Hazard hazard : stage20.hazards()) {
assertTrue(hazard.type() == ArenaHazardRuntime.Type.VOID_RIFT
|| hazard.type() == ArenaHazardRuntime.Type.STATIC_BURST
|| hazard.type() == ArenaHazardRuntime.Type.NULL_BEAM);
assertEquals(ArenaHazardRuntime.Phase.WARNING, hazard.phase());
assertEquals(0f, stage20.consumePlayerDamage(hazard.x(), hazard.y(), .1f), .0001f);
⋮----
@Test void nullSectorHazardsAreDeterministicForSameRun() {
ArenaHazardRuntime first = new ArenaHazardRuntime(24, 19, 0);
ArenaHazardRuntime second = new ArenaHazardRuntime(24, 19, 0);
float untilFirst = first.nullSectorHazardInterval() * .68f + .05f;
⋮----
first.update(.05f, -3f, 2f);
second.update(.05f, -3f, 2f);
⋮----
@Test void laterNullSectorStagesIncreasePressureButStayBounded() {
ArenaHazardRuntime stage20 = new ArenaHazardRuntime(20, 0, 0);
ArenaHazardRuntime stage30 = new ArenaHazardRuntime(30, 0, 0);
assertTrue(stage30.nullSectorHazardInterval() < stage20.nullSectorHazardInterval());
assertTrue(stage30.nullSectorHazardInterval() >= 8.8f);
⋮----
@Test void deathBurstRulesEscalateWithThreatAndNeverApplyToBoss() {
assertFalse(DeathBurstRules.enabled(Enemy.Type.BRUTE, 7));
assertTrue(DeathBurstRules.enabled(Enemy.Type.BRUTE, 8));
assertTrue(DeathBurstRules.enabled(Enemy.Type.SHIELDED, 8));
assertFalse(DeathBurstRules.enabled(Enemy.Type.PHANTOM, 14));
assertTrue(DeathBurstRules.enabled(Enemy.Type.PHANTOM, 15));
assertFalse(DeathBurstRules.enabled(Enemy.Type.BOSS, 20));
assertTrue(DeathBurstRules.damage(Enemy.Type.ELITE, 20) > DeathBurstRules.damage(Enemy.Type.ELITE, 8));
```

## File: src/test/java/com/deadlinezero/game/world/BiomeEnemyBehaviorRulesTest.java
```java
public final class BiomeEnemyBehaviorRulesTest {
@Test public void forgeHoundIsAggressiveBurstCharger() {
var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.FORGE_HOUND);
assertTrue(p.aggressiveCharge());
assertFalse(p.evasiveStrafe());
assertTrue(p.speedMultiplier() > 1f);
assertTrue(p.burstMultiplier() > 1.3f);
assertTrue(p.chargeStrengthMultiplier() > 1.2f);
⋮----
@Test public void cinderGunnerPrioritizesStrongFrequentStrafes() {
var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.CINDER_GUNNER);
assertTrue(p.evasiveStrafe());
assertFalse(p.aggressiveCharge());
assertTrue(p.tacticCooldownMultiplier() < .8f);
assertTrue(p.strafeStrengthMultiplier() > 1.3f);
⋮----
@Test public void slagGuardTradesSpeedForChargeWeight() {
var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.SLAG_GUARD);
assertTrue(p.speedMultiplier() < 1f);
⋮----
assertTrue(p.chargeStrengthMultiplier() > 1.35f);
⋮----
@Test public void phaseStalkerHasHighestMobilityBurst() {
var phase = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.PHASE_STALKER);
var forge = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.FORGE_HOUND);
assertTrue(phase.evasiveStrafe());
assertTrue(phase.burstMultiplier() > forge.burstMultiplier());
assertTrue(phase.strafeStrengthMultiplier() > 1.4f);
⋮----
@Test public void staticSeerHasFastestTacticalCadence() {
var seer = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.STATIC_SEER);
var cinder = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.CINDER_GUNNER);
assertTrue(seer.tacticCooldownMultiplier() < cinder.tacticCooldownMultiplier());
assertTrue(seer.strafeStrengthMultiplier() > cinder.strafeStrengthMultiplier());
⋮----
@Test public void nullWardIsSlowSupportRegenerator() {
var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.NULL_WARD);
assertTrue(p.speedMultiplier() < .9f);
assertTrue(p.recoveryMultiplier() > 1.5f);
⋮----
@Test public void standardEnemiesKeepNeutralProfile() {
var p = BiomeEnemyBehaviorRules.forIdentity(BiomeEnemyRoster.Identity.NONE);
assertEquals(1f, p.speedMultiplier(), .0001f);
assertEquals(1f, p.burstMultiplier(), .0001f);
assertEquals(1f, p.tacticCooldownMultiplier(), .0001f);
assertEquals(1f, p.recoveryMultiplier(), .0001f);
```

## File: src/test/java/com/deadlinezero/game/world/BiomeEnemyRosterTest.java
```java
final class BiomeEnemyRosterTest {
@Test void quarantineKeepsStandardPopulation() {
assertEquals(BiomeEnemyRoster.Identity.NONE, BiomeEnemyRoster.identityFor(9, Enemy.Type.RUNNER));
assertEquals(Enemy.Type.SHAMBLER, BiomeEnemyRoster.remap(9, .1f, Enemy.Type.SHAMBLER));
⋮----
@Test void foundryMapsThreeSignatureEnemies() {
assertEquals(BiomeEnemyRoster.Identity.FORGE_HOUND, BiomeEnemyRoster.identityFor(10, Enemy.Type.RUNNER));
assertEquals(BiomeEnemyRoster.Identity.CINDER_GUNNER, BiomeEnemyRoster.identityFor(15, Enemy.Type.RANGED));
assertEquals(BiomeEnemyRoster.Identity.SLAG_GUARD, BiomeEnemyRoster.identityFor(19, Enemy.Type.SHIELDED));
assertEquals(Enemy.Type.RUNNER, BiomeEnemyRoster.remap(10, .10f, Enemy.Type.SHAMBLER));
assertEquals(Enemy.Type.RANGED, BiomeEnemyRoster.remap(10, .25f, Enemy.Type.SHAMBLER));
assertEquals(Enemy.Type.SHIELDED, BiomeEnemyRoster.remap(10, .40f, Enemy.Type.SHAMBLER));
⋮----
@Test void nullSectorMapsThreeSignatureEnemies() {
assertEquals(BiomeEnemyRoster.Identity.PHASE_STALKER, BiomeEnemyRoster.identityFor(20, Enemy.Type.PHANTOM));
assertEquals(BiomeEnemyRoster.Identity.STATIC_SEER, BiomeEnemyRoster.identityFor(25, Enemy.Type.RANGED));
assertEquals(BiomeEnemyRoster.Identity.NULL_WARD, BiomeEnemyRoster.identityFor(29, Enemy.Type.REGENERATOR));
assertEquals(Enemy.Type.PHANTOM, BiomeEnemyRoster.remap(20, .10f, Enemy.Type.SHAMBLER));
assertEquals(Enemy.Type.RANGED, BiomeEnemyRoster.remap(20, .30f, Enemy.Type.SHAMBLER));
assertEquals(Enemy.Type.REGENERATOR, BiomeEnemyRoster.remap(20, .48f, Enemy.Type.SHAMBLER));
⋮----
@Test void cryoVaultUsesHeavyControlPopulationWithoutReusingBiomeIdentityArt() {
assertEquals(BiomeEnemyRoster.Identity.NONE, BiomeEnemyRoster.identityFor(30, Enemy.Type.SHIELDED));
assertEquals(Enemy.Type.SHIELDED, BiomeEnemyRoster.remap(30, .10f, Enemy.Type.SHAMBLER));
assertEquals(Enemy.Type.PHANTOM, BiomeEnemyRoster.remap(30, .30f, Enemy.Type.SHAMBLER));
assertEquals(Enemy.Type.BRUTE, BiomeEnemyRoster.remap(30, .48f, Enemy.Type.SHAMBLER));
assertEquals(.62f, BiomeEnemyRoster.elementalDamageMultiplier(30, Enemy.Type.SHIELDED, DamageElement.FROST), .0001f);
assertEquals(.72f, BiomeEnemyRoster.elementalDamageMultiplier(30, Enemy.Type.BRUTE, DamageElement.FROST), .0001f);
assertEquals(.78f, BiomeEnemyRoster.elementalDamageMultiplier(30, Enemy.Type.PHANTOM, DamageElement.FROST), .0001f);
assertEquals(1f, BiomeEnemyRoster.elementalDamageMultiplier(30, Enemy.Type.SHIELDED, DamageElement.FIRE), .0001f);
⋮----
@Test void cryogenicDepthsUsesSustainRangedElitePressure() {
assertEquals(BiomeEnemyRoster.Identity.NONE, BiomeEnemyRoster.identityFor(40, Enemy.Type.REGENERATOR));
assertEquals(Enemy.Type.REGENERATOR, BiomeEnemyRoster.remap(40, .10f, Enemy.Type.SHAMBLER));
assertEquals(Enemy.Type.RANGED, BiomeEnemyRoster.remap(40, .30f, Enemy.Type.SHAMBLER));
assertEquals(Enemy.Type.ELITE, BiomeEnemyRoster.remap(40, .52f, Enemy.Type.SHAMBLER));
assertEquals(.58f, BiomeEnemyRoster.elementalDamageMultiplier(40, Enemy.Type.REGENERATOR, DamageElement.FROST), .0001f);
assertEquals(.70f, BiomeEnemyRoster.elementalDamageMultiplier(40, Enemy.Type.RANGED, DamageElement.SHOCK), .0001f);
assertEquals(1.12f, BiomeEnemyRoster.elementalDamageMultiplier(40, Enemy.Type.ELITE, DamageElement.FIRE), .0001f);
⋮----
@Test void elementalResistanceProfilesAreBoundedAndSpecific() {
assertEquals(.62f, BiomeEnemyRoster.elementalDamageMultiplier(10, Enemy.Type.RUNNER, DamageElement.FIRE), .0001f);
assertEquals(1f, BiomeEnemyRoster.elementalDamageMultiplier(10, Enemy.Type.RUNNER, DamageElement.SHOCK), .0001f);
assertEquals(.58f, BiomeEnemyRoster.elementalDamageMultiplier(20, Enemy.Type.RANGED, DamageElement.SHOCK), .0001f);
assertEquals(.66f, BiomeEnemyRoster.elementalDamageMultiplier(20, Enemy.Type.REGENERATOR, DamageElement.FROST), .0001f);
for (BiomeEnemyRoster.Identity identity : BiomeEnemyRoster.Identity.values()) {
assertTrue(identity.resistanceMultiplier > 0f && identity.resistanceMultiplier <= 1f);
⋮----
@Test void bossesAreNeverRemappedOrAssignedARegularIdentity() {
assertEquals(Enemy.Type.BOSS, BiomeEnemyRoster.remap(25, .1f, Enemy.Type.BOSS));
assertEquals(BiomeEnemyRoster.Identity.NONE, BiomeEnemyRoster.identityFor(25, Enemy.Type.BOSS));
```

## File: src/test/java/com/deadlinezero/game/world/EndgameWaveCompositionRulesTest.java
```java
final class EndgameWaveCompositionRulesTest {
@Test void noOverrideBeforeThreatFive() {
for (EndgameMutatorRules.Mutator mutator : EndgameMutatorRules.Mutator.values()) {
assertEquals(Enemy.Type.BRUTE,
EndgameWaveCompositionRules.override(4, mutator, WaveDirector.PressureBand.CRISIS, 0f, Enemy.Type.BRUTE));
⋮----
@Test void eachMutatorHasARecognizableCompositionIdentity() {
assertEquals(Enemy.Type.RUNNER, EndgameWaveCompositionRules.override(10,
⋮----
assertEquals(Enemy.Type.SHIELDED, EndgameWaveCompositionRules.override(10,
⋮----
assertEquals(Enemy.Type.BRUTE, EndgameWaveCompositionRules.override(10,
⋮----
@Test void overrideBudgetRemainsBoundedAndRisesTowardCrisis() {
float opening5 = EndgameWaveCompositionRules.maximumOverrideShare(5, WaveDirector.PressureBand.OPENING);
float crisis5 = EndgameWaveCompositionRules.maximumOverrideShare(5, WaveDirector.PressureBand.CRISIS);
float crisis10 = EndgameWaveCompositionRules.maximumOverrideShare(10, WaveDirector.PressureBand.CRISIS);
⋮----
assertTrue(opening5 > 0f);
assertTrue(crisis5 > opening5);
assertTrue(crisis10 > crisis5);
assertTrue(crisis10 <= .35f, "mutator composition must never replace more than 35% of normal picks");
⋮----
@Test void rollsOutsideBudgetPreserveEncounterChoice() {
⋮----
assertEquals(fallback, EndgameWaveCompositionRules.override(10,
⋮----
@Test void everyActiveMutatorCanBreakARepeatedEnemyStreak() {
⋮----
Enemy.Type replacement = EndgameWaveCompositionRules.streakBreaker(mutator, repeated);
assertNotEquals(repeated, replacement, mutator + " must have a same-theme streak breaker");
assertNotEquals(Enemy.Type.BOSS, replacement);
```

## File: src/test/java/com/deadlinezero/game/world/FoundryHazardActivationCueTest.java
```java
final class FoundryHazardActivationCueTest {
@Test void activationCueFiresExactlyOnceAfterWarningCompletes() {
ArenaHazardRuntime runtime = new ArenaHazardRuntime(10, 3, 0);
float spawnDelay = runtime.foundryHazardInterval() * .72f;
⋮----
runtime.update(.05f, 0f, 0f);
⋮----
ArenaHazardRuntime.Hazard hazard = runtime.hazards().get(0);
⋮----
assertFalse(hazard.consumeActivationCue());
for (int i = 0; i < 30 && hazard.phase() == ArenaHazardRuntime.Phase.WARNING; i++) {
⋮----
assertTrue(hazard.consumeActivationCue());
```

## File: src/test/java/com/deadlinezero/game/world/RunEncounterDirectorTest.java
```java
final class RunEncounterDirectorTest {
@Test void sameStageAndOrdinalProduceSameEncounterPlan() {
RunStageContext.begin(4, 12);
RunEncounterDirector first = new RunEncounterDirector(4);
⋮----
RunEncounterDirector second = new RunEncounterDirector(4);
⋮----
for (int i = 0; i < 3; i++) assertEquals(first.planned(i), second.planned(i));
⋮----
@Test void consecutiveRunsChangeEncounterPlan() {
⋮----
RunStageContext.begin(4, 13);
⋮----
for (int i = 0; i < 3; i++) differs |= first.planned(i) != second.planned(i);
assertTrue(differs);
⋮----
@Test void eachRunUsesThreeDistinctEncounters() {
RunStageContext.begin(8, 27);
RunEncounterDirector director = new RunEncounterDirector(8);
⋮----
for (int i = 0; i < 3; i++) unique.add(director.planned(i));
assertEquals(3, unique.size());
assertNotEquals(RunEncounterDirector.Type.NONE, director.planned(0));
⋮----
@Test void runRotationExposesSpecialistEncounters() {
⋮----
RunStageContext.begin(6, ordinal);
RunEncounterDirector director = new RunEncounterDirector(6);
for (int i = 0; i < 3; i++) seen.add(director.planned(i));
⋮----
assertTrue(seen.contains(RunEncounterDirector.Type.PHANTOM_BREACH));
assertTrue(seen.contains(RunEncounterDirector.Type.REGEN_BLOOM));
assertTrue(seen.contains(RunEncounterDirector.Type.BULWARK_LINE));
⋮----
@Test void highThreatMutatorGuaranteesItsSignatureEncounterWithoutDuplicates() {
⋮----
RunStageContext.begin(20, ordinal, 6);
EndgameMutatorRules.Mutator mutator = EndgameMutatorRules.current();
RunEncounterDirector director = new RunEncounterDirector(20);
⋮----
assertEquals(3, unique.size(), "anchoring must preserve three distinct events");
⋮----
assertTrue(unique.contains(expected), mutator + " should surface its signature encounter");
```

## File: src/test/java/com/deadlinezero/game/world/SpatialHashTest.java
```java
final class SpatialHashTest {
private static Enemy enemy(float x, float y) {
return new Enemy(Enemy.Type.SHAMBLER, x, y, 100f, 1f, .4f, 1f, 1);
⋮----
@Test void nearestReturnsClosestAliveEnemyAcrossCellBoundaries() {
SpatialHash hash = new SpatialHash(2.2f);
⋮----
Enemy fartherSameCell = enemy(1.8f, 0f);
Enemy closestNextCell = enemy(2.21f, 0f);
Enemy far = enemy(8f, 0f);
enemies.add(fartherSameCell);
enemies.add(closestNextCell);
enemies.add(far);
⋮----
hash.rebuild(enemies);
⋮----
assertSame(fartherSameCell, hash.nearest(0f, 0f));
assertSame(closestNextCell, hash.nearest(2.15f, 0f));
⋮----
@Test void nearestSkipsDeadEnemies() {
⋮----
Enemy dead = enemy(.2f, 0f);
Enemy alive = enemy(1.1f, 0f);
⋮----
enemies.add(dead);
enemies.add(alive);
⋮----
assertSame(alive, hash.nearest(0f, 0f));
⋮----
@Test void nearestReturnsNullForEmptyOrAllDeadIndex() {
⋮----
assertNull(hash.nearest(0f, 0f));
⋮----
Enemy dead = enemy(0f, 0f);
⋮----
assertEquals(0, hash.activeBucketCount());
⋮----
@Test void nearestWithinHonorsRadiusAndExclusions() {
⋮----
Enemy source = enemy(0f, 0f);
Enemy excluded = enemy(1f, 0f);
Enemy valid = enemy(2.8f, 0f);
Enemy outside = enemy(3.41f, 0f);
enemies.add(source);
enemies.add(excluded);
enemies.add(valid);
enemies.add(outside);
⋮----
assertSame(valid, hash.nearestWithin(0f, 0f, 3.4f, source, excluded));
assertNull(hash.nearestWithin(0f, 0f, .9f, source, excluded));
⋮----
@Test void nearestWithinUsesTrueEuclideanRadiusNotOnlyCoveredCells() {
⋮----
Enemy diagonalOutside = enemy(2.5f, 2.5f);
Enemy inside = enemy(2.0f, 2.0f);
enemies.add(diagonalOutside);
enemies.add(inside);
⋮----
assertSame(inside, hash.nearestWithin(0f, 0f, 3.4f, null, null));
⋮----
@Test void incrementalAddMakesSpawnImmediatelyQueryable() {
⋮----
Enemy spawned = enemy(3f, 0f);
⋮----
hash.add(spawned);
⋮----
assertSame(spawned, hash.nearest(0f, 0f));
assertEquals(1, hash.activeBucketCount());
⋮----
@Test void rebuildAfterIncrementalAddDoesNotDuplicateEnemy() {
⋮----
Enemy spawned = enemy(1f, 0f);
enemies.add(spawned);
⋮----
hash.query(1f, 0f, .5f, out);
assertEquals(1, out.size);
assertSame(spawned, out.first());
⋮----
@Test void historicalBucketsDoNotStayActiveAcrossRebuilds() {
⋮----
enemies.clear();
enemies.add(enemy(i * 2.3f, 0f));
⋮----
int retained = hash.retainedBucketCount();
assertEquals(120, retained);
⋮----
enemies.add(enemy(0f, 0f));
⋮----
assertEquals(retained, hash.retainedBucketCount());
⋮----
@Test void retainedBucketsAreReusedWhileActiveCountTracksCurrentPopulation() {
⋮----
enemies.add(enemy(-5f, 0f));
enemies.add(enemy(5f, 0f));
⋮----
assertEquals(2, hash.activeBucketCount());
assertEquals(2, retained);
```

## File: src/test/java/com/deadlinezero/game/world/StageCombatPressureAuditTest.java
```java
final class StageCombatPressureAuditTest {
@Test void lateGamePressureIsStrictlyMonotonic() {
float previous = StageCombatPressureAudit.snapshot(9).compositePressure();
⋮----
float current = StageCombatPressureAudit.snapshot(stage).compositePressure();
assertTrue(current > previous, "stage " + stage + " pressure must exceed previous stage");
⋮----
@Test void foundryBoundaryAddsContentWithoutAnUnfairSpike() {
float jump = StageCombatPressureAudit.relativeJump(9, 10);
assertTrue(jump > .20f, "Foundry should feel materially harder than stage 9");
assertTrue(jump < .27f, "Foundry boundary pressure spike too large: " + jump);
⋮----
@Test void nullSectorBoundaryRemainsReadable() {
float jump = StageCombatPressureAudit.relativeJump(19, 20);
assertTrue(jump > .10f, "Null Sector should introduce a meaningful pressure step");
assertTrue(jump < .15f, "Null Sector boundary pressure spike too large: " + jump);
⋮----
@Test void postBoundaryStageStepsStayControlled() {
⋮----
float jump = StageCombatPressureAudit.relativeJump(stage - 1, stage);
assertTrue(jump < .21f, "stage " + stage + " pressure jump too large: " + jump);
⋮----
@Test void biomeHazardPressureIsAbsentThenExplicit() {
StageCombatPressureAudit.Snapshot quarantine = StageCombatPressureAudit.snapshot(9);
StageCombatPressureAudit.Snapshot foundry = StageCombatPressureAudit.snapshot(10);
StageCombatPressureAudit.Snapshot nullSector = StageCombatPressureAudit.snapshot(20);
assertTrue(!Float.isFinite(quarantine.hazardInterval()));
assertTrue(Float.isFinite(foundry.hazardInterval()));
assertTrue(Float.isFinite(nullSector.hazardInterval()));
float foundryPressurePerSecond = foundry.nominalHazardDamage() / foundry.hazardInterval();
float nullPressurePerSecond = nullSector.nominalHazardDamage() / nullSector.hazardInterval();
assertTrue(nullPressurePerSecond > foundryPressurePerSecond,
```

## File: src/test/java/com/deadlinezero/game/world/WaveDirectorTest.java
```java
public final class WaveDirectorTest {
@AfterEach public void cleanup() { RunModifierContext.end(); }
⋮----
@Test public void pressureBandsProgressTowardBoss() {
RunStageContext.begin(1);
WaveDirector d = new WaveDirector();
float boss = d.bossArrivalSeconds();
assertEquals(WaveDirector.PressureBand.OPENING, d.pressureBand());
d.update(boss * .30f);
assertEquals(WaveDirector.PressureBand.BUILD, d.pressureBand());
⋮----
assertEquals(WaveDirector.PressureBand.ASSAULT, d.pressureBand());
d.update(boss * .25f);
assertEquals(WaveDirector.PressureBand.CRISIS, d.pressureBand());
⋮----
@Test public void spawnCadenceAcceleratesAcrossPressureBands() {
⋮----
WaveDirector opening = new WaveDirector();
opening.onSpawn();
opening.update(.30f);
assertTrue(!opening.shouldSpawn());
⋮----
WaveDirector crisis = new WaveDirector();
crisis.update(crisis.bossArrivalSeconds() * .85f);
crisis.onSpawn();
crisis.update(.30f);
assertTrue(crisis.shouldSpawn());
⋮----
@Test public void bossBecomesPendingAtArrival() {
RunStageContext.begin(3);
⋮----
d.update(d.bossArrivalSeconds() + .01f);
assertTrue(d.bossPending());
⋮----
@Test public void twinApexSpawnsTwoBossSignalsBeforeClosingGate() {
activateLegendary(RunModifierContext.Modifier.TWIN_APEX);
⋮----
assertEquals(Enemy.Type.BOSS, d.chooseType());
d.onBossSpawned();
assertEquals(1, d.bossSpawnCount());
⋮----
assertTrue(!d.bossSpawned());
⋮----
d.onSpawn();
d.update(.20f);
assertTrue(d.shouldSpawn());
⋮----
assertEquals(2, d.bossSpawnCount());
assertTrue(d.bossSpawned());
⋮----
@Test public void phantomEclipseHasDeterministicPhantomDominatedMapping() {
activateLegendary(RunModifierContext.Modifier.PHANTOM_ECLIPSE);
⋮----
assertEquals(Enemy.Type.PHANTOM, d.legendaryOverride(.00f));
assertEquals(Enemy.Type.PHANTOM, d.legendaryOverride(.55f));
assertEquals(Enemy.Type.RUNNER, d.legendaryOverride(.60f));
assertEquals(Enemy.Type.RANGED, d.legendaryOverride(.80f));
assertEquals(Enemy.Type.REGENERATOR, d.legendaryOverride(.90f));
assertEquals(Enemy.Type.ELITE, d.legendaryOverride(.99f));
⋮----
@Test public void specialistSiegeMapsEveryRollToHeavySpecialists() {
activateLegendary(RunModifierContext.Modifier.SPECIALIST_SIEGE);
⋮----
assertEquals(Enemy.Type.SHIELDED, d.legendaryOverride(.00f));
assertEquals(Enemy.Type.REGENERATOR, d.legendaryOverride(.40f));
assertEquals(Enemy.Type.ELITE, d.legendaryOverride(.70f));
assertEquals(Enemy.Type.BRUTE, d.legendaryOverride(.85f));
assertEquals(Enemy.Type.RANGED, d.legendaryOverride(.99f));
⋮----
private static void activateLegendary(RunModifierContext.Modifier target) {
⋮----
RunStageContext.begin(stage, ordinal);
for (RunModifierContext.Modifier offer : RunModifierContext.offers()) {
⋮----
if (!RunModifierContext.choose(target)) throw new AssertionError("Legendary contract activation failed");
⋮----
throw new AssertionError("Legendary contract was never offered: " + target);
```

## File: build.gradle
```
plugins { id 'java-library' }
java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

dependencies {
    api "com.badlogicgames.gdx:gdx:${gdxVersion}"
    api "com.badlogicgames.gdx:gdx-ai:1.8.2"
    testImplementation platform('org.junit:junit-bom:5.11.4')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    testImplementation "com.badlogicgames.gdx:gdx-backend-headless:${gdxVersion}"
    testRuntimeOnly "com.badlogicgames.gdx:gdx-platform:${gdxVersion}:natives-desktop"
}

tasks.withType(Test).configureEach {
    useJUnitPlatform()
    testLogging {
        events "failed"
        exceptionFormat "full"
        showStandardStreams = true
    }
}
```
