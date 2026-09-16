# Deadline Zero — Zombie Waves Reference Architecture

Date: 2026-09-16
Branch: `zombie-waves-reference-rebuild`
Reference target: Zombie Waves gameplay structure and pacing
Implementation stack: Java + libGDX

## 1. Objective

Rework Deadline Zero so its combat loop, horde pressure, run pacing, upgrade cadence, targeting flow, and moment-to-moment survival feel follow the strongest proven patterns visible in Zombie Waves, while keeping Deadline Zero's own codebase, engine, characters, enemies, weapons, UI, art, audio, lore, progression data, and production infrastructure.

The goal is explicitly **not** to rebuild systems that already exist in Deadline Zero. The migration must preserve and reuse existing modules whenever they already provide the required responsibility. New code is introduced only where the current architecture lacks a clean equivalent or where a responsibility is currently embedded in an unsuitable place.

The design is therefore a **reference-driven refactor** rather than a rewrite.

## 2. Reference-analysis boundary

Zombie Waves may be used as a technical and behavioral reference for:

- combat-loop structure;
- control philosophy;
- auto-target / auto-fire behavior;
- wave escalation and density;
- spawn pressure;
- enemy-role composition;
- XP pickup and level-up cadence;
- upgrade-family organization;
- weapon/trait synergy patterns;
- elite and boss presentation cadence;
- run duration and reward pacing;
- meta-loop organization;
- performance-oriented patterns that can be independently reproduced.

Zombie Waves proprietary source code, assets, audio, textures, character designs, names, maps, UI artwork, or data tables are not imported into Deadline Zero.

If a valid Zombie Waves APK/XAPK is later available, reverse-engineering is limited to identifying architecture, state machines, configuration structure, dependency layout, timings, constants, and algorithmic patterns that can be independently reimplemented in Java/libGDX.

The current uploaded file `uptodown-com.ddup.zombiewaves.zw.apk` was inspected and is **not the Zombie Waves game package**. It contains Uptodown application classes/libraries, has no `com.ddup.zombiewaves.zw` strings, and contains no game runtime or Unity/IL2CPP libraries. It is therefore excluded as a source of game architecture.

## 3. Current Deadline Zero modules to preserve

The current project already contains most of the required foundations.

### Player/runtime

Keep:

- `Player`
- `ActorState`
- existing movement state
- dash/invulnerability behavior
- accessibility-aware input/haptics
- current HP and level model

`Player` already owns level, XP, XP threshold growth, weapon runtime, abilities, legendary state, movement, dash, and damage handling. This should remain the player-state authority.

### Weapons

Keep:

- `WeaponCatalog`
- `WeaponDefinition`
- `WeaponRuntime`
- `WeaponSignatureRuntime`
- `DamageElement`

These already separate static weapon definitions from mutable run-time stats. The migration should extend this architecture rather than replace it.

### Horde pacing

Keep:

- `WaveDirector`
- `RunEncounterDirector`
- `StageMissionRules`
- `EndgameWaveCompositionRules`
- `BiomeEnemyRoster`
- `BiomeEnemyBehaviorRules`
- `SpatialHash`

`WaveDirector` already provides pressure bands, accelerating spawn intervals, squad bursts, boss timing, encounter overrides, stage scaling, and enemy-role selection. It is the correct foundation for Zombie-Waves-like horde pacing.

### Enemy runtime

Keep:

- `Enemy`
- `EnemyProjectile`
- `Projectile`
- `HomingMissile`
- existing boss identity / phase controllers
- existing champion/variant system

### Run upgrades

Keep:

- `Upgrade`
- `UpgradeSelector`
- `UpgradeRarity`
- `LegendaryChoice`
- `LegendarySelector`
- `LegendaryEffects`
- `LegendaryState`

The current upgrade pool is already extensive enough to support a richer synergy graph without rebuilding the progression engine.

### Rendering / UX

Keep the combat-visual rebuild already underway:

- `CombatWorldRenderer`
- `CombatReadabilityPass`
- `CombatHudRenderer`
- `CombatSpritePass`
- existing FX quality / accessibility systems
- Android visual QA and performance probes

## 4. Architectural changes

Only the following responsibilities should be added or extracted.

### 4.1 `AutoTargetingSystem`

Purpose: make movement the primary player input while target acquisition is handled automatically.

Responsibilities:

- select the highest-priority valid hostile target;
- prefer nearest meaningful threat by default;
- allow weapon-specific target rules;
- reject dead/out-of-range/invalid targets;
- expose target position and target entity without owning firing;
- use spatial-hash queries rather than scanning the complete horde on every frame;
- update at a bounded cadence instead of every render frame where possible.

Target ranking should support a small weighted score:

- distance;
- boss/elite priority;
- imminent ranged threat;
- current target stickiness;
- weapon constraints.

No manual aiming input is required on mobile in the default mode.

### 4.2 `AutoFireController`

Purpose: decouple weapon cadence from `GameScreen` orchestration.

Responsibilities:

- consume `WeaponRuntime`;
- consume the current target from `AutoTargetingSystem`;
- track shot cooldown;
- emit projectiles through the existing projectile path;
- support weapon-specific burst, spread, multi-shot, piercing, elemental, missile, and signature behavior;
- stop cleanly when no valid target exists;
- expose deterministic test hooks.

The controller must not duplicate weapon stats already held in `WeaponRuntime`.

### 4.3 `XpPickup` + `PickupSystem`

Purpose: replace or supplement direct XP-on-kill behavior with a visible spatial reward loop closer to the reference game.

Responsibilities:

- spawn pooled XP pickups from enemy deaths;
- store value, position, pickup radius, and magnet state;
- attract toward the player inside a configurable magnet radius;
- accelerate attraction near the player;
- merge/simplify distant pickups when density is high;
- grant XP through `Player.addXp()` only when collected;
- support vacuum/pickup-range upgrade effects;
- cap active pickup count and avoid per-frame allocations.

This is one of the few genuinely new gameplay subsystems required.

### 4.4 `RunTraitGraph`

Purpose: organize existing upgrades into readable families and synergies instead of a flat pool.

It does not replace `Upgrade` or `UpgradeSelector`. It adds metadata describing:

- trait family;
- prerequisites;
- exclusion groups;
- synergy tags;
- evolution/legendary eligibility;
- weapon compatibility;
- ability compatibility.

Initial families:

- Ballistics
- Fire
- Frost
- Shock
- Mobility
- Survival
- Crowd Control
- Multishot/Barrage
- Crit/Execution
- Companion/Ability

The selector uses this metadata to bias choices toward coherent builds without making runs deterministic.

### 4.5 `HordePressureProfile`

Purpose: make the current `WaveDirector` easier to tune against a reference pacing curve without hardcoding more numbers into the class.

Data should define, per pressure phase:

- spawn interval range;
- burst size;
- active-enemy target;
- special-enemy budget;
- runner/brute/ranged/elite weighting;
- pickup economy multiplier;
- boss transition timing.

`WaveDirector` remains the runtime authority and consumes this profile.

## 5. Control model

Default mobile combat becomes movement-first.

- left-side virtual stick controls movement;
- player automatically acquires targets;
- weapon fires automatically while a valid target exists;
- dash remains an explicit secondary action in Deadline Zero unless later testing shows it harms readability;
- weapon skills/active abilities may remain explicit when they provide meaningful tactical choice;
- no permanent right-side aiming stick is introduced.

Desktop keeps keyboard movement and may optionally expose manual aim as a debug/accessibility mode, but the production combat loop is designed around auto-targeting.

## 6. Horde model

The desired feel is continuous pressure rather than sparse individual encounters.

### Early run

- enough enemies to establish immediate movement pressure;
- mostly basic infected with a small runner presence;
- frequent XP collection and fast first upgrades.

### Mid run

- denser surrounding pressure;
- composition changes matter more than raw HP scaling;
- ranged and brute roles force path changes;
- elite encounters create short spikes rather than long empty gaps.

### Late run

- sustained high density;
- dangerous role combinations;
- reduced safe space;
- build power must visibly clear crowds;
- boss arrival is readable and dramatic without stopping the horde identity.

Spawn logic should prefer perimeter/ring spawn regions outside the immediate player-safe radius and avoid obvious single-point streams.

## 7. Enemy movement and crowd behavior

Deadline Zero should preserve existing enemy state machines but refine the crowd layer.

Required behavior:

- enemies converge on the player without perfectly overlapping;
- local separation keeps silhouettes readable;
- brutes displace lighter infected visually where appropriate;
- runners use more direct interception;
- ranged enemies seek useful standoff bands;
- elites/bosses may reserve more collision space;
- crowd calculations use `SpatialHash` or equivalent local queries.

This is a refactor around current enemy logic, not a new ECS or physics engine.

## 8. Weapon feel

Existing weapon definitions remain authoritative.

The migration focuses on making each weapon create a distinct crowd-clearing pattern:

- assault-style automatic stream;
- shotgun cone;
- penetrating rail/bullet line;
- rapid SMG swarm control;
- explosive/area damage;
- elemental status spread;
- homing missile behavior;
- signature/legendary transformations.

Weapon identities should be produced through firing-pattern components and existing stat modifiers instead of bespoke duplicated combat code.

## 9. Upgrade cadence

Target run cadence:

- first upgrade arrives quickly;
- upgrades remain frequent enough to reinforce power growth;
- choice frequency slows slightly as the build matures;
- meaningful synergy choices appear before the boss;
- legendary/evolution moments are rarer and visually stronger.

`Player.xpNext` remains the level-threshold authority unless tests show the current curve cannot hit the target cadence.

The first migration step changes **how XP is collected**, not the whole level formula.

## 10. Boss integration

Keep current boss identities, phases, telegraphs, visuals, and stage routing.

Refactor only the surrounding run flow:

- boss ETA tied to pressure curve;
- pre-boss warning window;
- horde pressure transitions cleanly into boss state;
- optional adds remain readable;
- boss rewards feed the same pickup/upgrade/reward pipeline;
- no duplicate boss framework is created.

## 11. Meta progression

Deadline Zero already has survivor, weapon, gear, mission, contract, currency, and progression infrastructure. That layer remains.

Zombie Waves may guide information architecture and progression cadence, but the existing Deadline Zero systems are preserved unless a later isolated spec identifies a concrete weakness.

The first implementation plan is combat-only.

## 12. Visual identity

Gameplay structure may use Zombie Waves as a reference, but presentation remains Deadline Zero.

The current approved visual target remains:

- dark sci-fi biohazard;
- charcoal/gunmetal environment;
- restrained cyan tech accents;
- amber warnings;
- red danger/biohazard;
- infected green used selectively;
- readable zombie silhouettes;
- premium compact HUD;
- strong blood/impact/death feedback;
- no large prototype-like cyan panels;
- no hazard-stripe wallpaper.

## 13. Performance architecture

The reference-driven refactor must not trade horde scale for expensive architecture.

Rules:

- no per-frame full-list enemy targeting scan if local spatial queries can be used;
- target acquisition updates at a bounded frequency;
- projectiles remain pooled where currently supported;
- XP pickups are pooled;
- distant XP can merge or simplify;
- local separation queries use spatial buckets;
- FX remain governed by graphics quality and thermal scaling;
- no new heavyweight dependency or engine migration;
- Java + libGDX remain the production stack.

Existing CI gates remain mandatory:

- loaded 40-enemy performance probe;
- 160-enemy / 180-projectile stress probe;
- Android runtime journey;
- desktop smoke;
- Android production build;
- visual QA.

## 14. Migration strategy

### Phase 0 — reference and baseline

1. preserve current branch baseline;
2. document reference behavior;
3. retain existing gameplay tests;
4. capture current combat visual/performance baselines.

### Phase 1 — targeting and firing extraction

1. add `AutoTargetingSystem` in TDD;
2. add `AutoFireController` in TDD;
3. route existing weapon/projectile logic through the controller;
4. preserve weapon stats and damage outcomes;
5. verify mobile movement-only combat.

### Phase 2 — XP pickup loop

1. add pooled `XpPickup`;
2. add `PickupSystem`;
3. spawn XP from enemy deaths;
4. magnetize and collect into `Player.addXp()`;
5. add pickup-range/magnet tuning hooks;
6. verify no XP is lost under dense horde conditions.

### Phase 3 — horde pressure refactor

1. introduce `HordePressureProfile`;
2. migrate current timing constants from `WaveDirector` into data;
3. preserve boss/encounter semantics;
4. tune active-enemy density and burst size;
5. add perimeter spawn distribution;
6. tune crowd separation and role spacing.

### Phase 4 — trait synergy graph

1. classify existing upgrades by family/tags;
2. add prerequisite/exclusion metadata;
3. bias `UpgradeSelector` toward build coherence;
4. preserve current upgrade effects;
5. add only missing effects needed for reference-like build evolution.

### Phase 5 — game feel and presentation

1. finish hazard-wallpaper removal;
2. polish zombie crowd readability;
3. tune hit/death/pickup FX;
4. tune auto-fire feedback;
5. tune boss transition and low-HP feedback;
6. validate Android 1536×691 screenshots.

### Phase 6 — meta alignment

Only after combat is approved:

- Home/Base hierarchy;
- survivor selection;
- weapons/gear presentation;
- contracts/missions/shop/settings.

## 15. Testing strategy

### Unit tests

Required new deterministic tests:

- target selection priority;
- target stickiness;
- no-target behavior;
- fire cadence;
- weapon pattern routing;
- XP pickup spawn/value;
- pickup magnet attraction;
- pickup merge/cap rules;
- exact XP conservation;
- trait prerequisite/exclusion rules;
- pressure-profile progression;
- perimeter spawn constraints;
- local-separation rules.

### Integration tests

Required:

- movement-only mobile run can kill enemies;
- auto-fire does not shoot dead targets;
- level-up remains reachable through pickup XP;
- upgrades still modify existing `WeaponRuntime` correctly;
- boss arrival still works;
- contracts/mutators still affect wave behavior;
- victory/defeat settlement remains unchanged.

### Android visual QA

Required captures:

- first 30 seconds;
- early dense horde;
- runner pressure;
- ranged pressure;
- brute + crowd;
- elite crowd;
- XP field before collection;
- XP magnet collection;
- level-up choice;
- boss warning;
- boss combat;
- low HP;
- 1536×691 combat.

## 16. Acceptance criteria

The migration succeeds when:

- a player can control the run primarily with one movement stick;
- target acquisition and firing are automatic and stable;
- horde pressure is continuous and clearly denser than the current prototype feel;
- enemies visibly surround and compress available safe space;
- kills create visible collectible XP;
- level-ups produce frequent, coherent build decisions;
- existing weapons retain their identities and data model;
- existing bosses, contracts, survivors, gear, saves, settlement, accessibility, CI, and performance infrastructure remain functional;
- the game reads immediately as a zombie-wave survival shooter;
- visual presentation is Deadline Zero, not an imitation of Zombie Waves artwork;
- no proprietary Zombie Waves code or assets are committed to the repository.

## 17. Decision summary

Do **not** replace Deadline Zero with a new engine or a clean-room rewrite of the whole project.

Reuse the existing architecture aggressively. The likely high-value work is concentrated in four areas:

1. isolate auto-targeting and auto-fire;
2. add a spatial XP-pickup loop;
3. retune `WaveDirector` through a dedicated pressure profile;
4. organize the existing upgrade pool into a synergy graph.

Everything else should preferentially remain existing Deadline Zero code and be refactored only where required by those changes.
