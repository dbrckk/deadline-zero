package com.deadlinezero.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.abilities.AbilitySystem;
import com.deadlinezero.game.abilities.AbilitySynergyUnlockDetector;
import com.deadlinezero.game.abilities.AbilityUpgradeGuidance;
import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.ai.BossAttackPatternCatalog;
import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.ai.BossVariantStats;
import com.deadlinezero.game.ai.EnemyPatternCatalog;
import com.deadlinezero.game.ai.EnemyState;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.EnemyProjectile;
import com.deadlinezero.game.entities.HomingMissile;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.entities.Projectile;
import com.deadlinezero.game.fx.ArcFx;
import com.deadlinezero.game.fx.DamageNumber;
import com.deadlinezero.game.fx.ImpactFx;
import com.deadlinezero.game.input.VirtualStick;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.config.GraphicsSettings;
import com.deadlinezero.game.perf.PerformanceTelemetry;
import com.deadlinezero.game.perf.AdaptiveFrameRateGovernor;
import com.deadlinezero.game.perf.ThermalBudgetPolicy;
import com.deadlinezero.game.progression.LegendaryChoice;
import com.deadlinezero.game.progression.LegendarySelector;
import com.deadlinezero.game.progression.ProtocolUpgradeGuidance;
import com.deadlinezero.game.progression.Upgrade;
import com.deadlinezero.game.progression.UpgradeSelector;
import com.deadlinezero.game.services.AdsService;
import com.deadlinezero.game.ui.UiRenderer;
import com.deadlinezero.game.util.Pools;
import com.deadlinezero.game.visual.CombatHudRenderer;
import com.deadlinezero.game.visual.CombatPolishController;
import com.deadlinezero.game.visual.CombatSpritePass;
import com.deadlinezero.game.visual.CombatVisualEvents;
import com.deadlinezero.game.visual.BossRevealCameraProfile;
import com.deadlinezero.game.visual.HostileProjectilePresentation;
import com.deadlinezero.game.visual.PlayerProjectilePresentation;
import com.deadlinezero.game.visual.UpgradeIconRenderer;
import com.deadlinezero.game.visual.VisualTheme;
import com.deadlinezero.game.visual.WorldFxRenderer;
import com.deadlinezero.game.world.SpatialHash;
import com.deadlinezero.game.world.WaveDirector;

public final class GameScreen extends ScreenAdapter {
    private static final Color ENEMY_RUNNER = new Color(.95f, .35f, .25f, 1f);
    private static final Color ENEMY_BRUTE = new Color(.58f, .10f, .15f, 1f);
    private static final Color ENEMY_RANGED = new Color(.95f, .62f, .16f, 1f);
    private static final Color ENEMY_ELITE = new Color(.76f, .18f, .86f, 1f);
    private static final Color ENEMY_DEFAULT = new Color(.30f, .70f, .39f, 1f);
    private static final float COMBAT_CAMERA_ZOOM = .88f;

    private final DeadlineZeroGame game;
    private final OrthographicCamera cam = new OrthographicCamera(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
    private final ShapeRenderer shapes = new ShapeRenderer();
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private final Player player = new Player(0, 0);
    private final Array<Enemy> enemies = new Array<>(false, GameConfig.MAX_ENEMIES);
    private final Array<Enemy> collisionCandidates = new Array<>(false, 32);
    private final Pools pools = new Pools();
    private final WaveDirector director = new WaveDirector();
    private final SpatialHash spatial = new SpatialHash(2.2f);
    private final VirtualStick stick = new VirtualStick();
    private final Vector2 aim = new Vector2();
    private final Vector2 shotVelocity = new Vector2();
    private final AbilitySystem abilitySystem;
    private final CombatHudRenderer combatHud;
    private final WorldFxRenderer worldFx = new WorldFxRenderer();
    private final PerformanceTelemetry performanceTelemetry = new PerformanceTelemetry();
    private final AdaptiveFrameRateGovernor frameRateGovernor = new AdaptiveFrameRateGovernor();
    private final CombatSpritePass spritePass;
    private final CombatPolishController polish;
    private float accumulator, fireTimer, contactTimer, cameraShake, visualTime, performanceEvaluationTimer;
    private float bossRevealTimer;
    private Enemy bossRevealTarget;
    private boolean choosingUpgrade, choosingLegendary, gameOver, revived, bossKilledThisRun, settling;
    private final Upgrade[] choices = new Upgrade[3];
    private final LegendaryChoice[] legendaryChoices = new LegendaryChoice[3];
    private int legendaryChoiceCount;

    public GameScreen(DeadlineZeroGame game) {
        this.combatHud = new CombatHudRenderer(game.i18n);
        this.game = game;
        this.abilitySystem = new AbilitySystem(player, enemies, pools, spatial, this::onEnemyKilled);
        this.spritePass = new CombatSpritePass(game.art);
        this.polish = new CombatPolishController(game.art, game.accessibility, game.services.thermal);
        frameRateGovernor.reset(GraphicsSettings.frameRate().target);
        float gearPower = game.profile == null ? 1f : game.profile.aggregatePowerMultiplier();
        player.weapon.damage *= gearPower;
        cam.position.set(0, 0, 0);
        cam.update();
        font.getData().setScale(.75f);
    }

    @Override public void render(float delta) {
        performanceTelemetry.record(delta, frameRateGovernor.effectiveTarget());
        performanceEvaluationTimer += Math.min(delta, .25f);
        if (performanceEvaluationTimer >= 2f && performanceTelemetry.sampleCount() >= 60) {
            performanceEvaluationTimer = 0f;
            int before = frameRateGovernor.effectiveTarget();
            int userTarget = GraphicsSettings.frameRate().target;
            int allowedTarget = ThermalBudgetPolicy.allowedFps(userTarget, game.services.thermal.level());
            int after = frameRateGovernor.update(
                allowedTarget,
                performanceTelemetry.snapshot(before)
            );
            if (after != before) Gdx.graphics.setForegroundFPS(after);
        }
        delta = Math.min(delta, .05f);
        visualTime += delta;
        combatHud.update(delta);
        polish.updateVisual(delta);
        float simulationScale = polish.simulationScale(delta);
        spritePass.update(delta * simulationScale);
        accumulator += delta * simulationScale;
        while (accumulator >= GameConfig.FIXED_STEP) {
            if (!choosingUpgrade && !choosingLegendary && !gameOver) update(GameConfig.FIXED_STEP);
            accumulator -= GameConfig.FIXED_STEP;
        }
        draw();
        handleOverlayInput();
    }

    public PerformanceTelemetry.Snapshot performanceSnapshot() {
        return performanceTelemetry.snapshot(frameRateGovernor.effectiveTarget());
    }

    public int effectiveFrameRateTarget() {
        return frameRateGovernor.effectiveTarget();
    }

    public com.deadlinezero.game.services.ThermalService.Level thermalLevel() {
        return game.services.thermal.level();
    }

    public float effectiveFxQuality() {
        return polish.fxQuality();
    }

    public int activeEnemyCount() {
        int count = 0;
        for (Enemy enemy : enemies) if (enemy.alive) count++;
        return count;
    }

    public int activeProjectileCount() {
        int count = 0;
        for (Projectile projectile : pools.projectiles) if (projectile.active) count++;
        for (EnemyProjectile projectile : pools.hostileProjectiles) if (projectile.active) count++;
        for (HomingMissile missile : pools.homingMissiles) if (missile.active) count++;
        return count;
    }

    public int activeSpatialBucketCount() {
        return spatial.activeBucketCount();
    }

    public int retainedSpatialBucketCount() {
        return spatial.retainedBucketCount();
    }

    private void update(float dt) {
        director.update(dt);
        player.updateRuntime(dt);
        fireTimer -= dt;
        contactTimer -= dt;
        Vector2 move = stick.update(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        player.velocity.set(move).scl(player.moveSpeed);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.canDash() && move.len2() > .08f) {
            player.position.mulAdd(move, 4.8f);
            player.triggerDash();
            CombatVisualEvents.markDash();
            if (game.accessibility != null && game.accessibility.haptics) game.services.haptics.dash();
            addCameraShake(.12f);
            impact(player.position.x, player.position.y, .9f, .16f, VisualTheme.CYAN);
        }
        player.position.mulAdd(player.velocity, dt);
        player.position.x = MathUtils.clamp(player.position.x, -31, 31);
        player.position.y = MathUtils.clamp(player.position.y, -17, 17);

        if (director.shouldSpawn() && enemies.size < GameConfig.MAX_ENEMIES) {
            spawnEnemy();
            director.onSpawn();
        }

        // Reuse the index produced at the end of the previous simulation tick.
        Enemy target = spatial.nearest(player.position.x, player.position.y);
        if (target != null && fireTimer <= 0f) {
            fire(target);
            fireTimer = player.weapon.fireInterval;
        }

        updateEnemies(dt);
        // Enemy movement invalidates the pre-update index; rebuild for collision/ability queries.
        spatial.rebuild(enemies);
        abilitySystem.update(dt);
        updatePlayerProjectiles(dt);
        updateHostileProjectiles(dt);

        for (ImpactFx f : pools.impacts) if (f.active) { f.life -= dt; if (f.life <= 0f) f.active = false; }
        for (DamageNumber n : pools.damageNumbers) n.update(dt);
        for (ArcFx arc : pools.arcs) arc.update(dt);
        polish.updateSimulation(dt, pools);
        for (int i = enemies.size - 1; i >= 0; i--) if (!enemies.get(i).alive) enemies.removeIndex(i);

        if (game.accessibility != null && game.accessibility.allowsScreenShake() && cameraShake > .0001f) {
            cam.position.x += MathUtils.random(-1f, 1f) * cameraShake;
            cam.position.y += MathUtils.random(-1f, 1f) * cameraShake;
            cameraShake = Math.max(0f, cameraShake - dt * 2.7f);
        } else {
            cameraShake = 0f;
        }
        // Mobile survivor-shooter framing: keep the operative readable and let the arena move around
        // them. A small velocity look-ahead preserves anticipation without making the camera floaty.
        float cameraTargetX = player.position.x * .82f + player.velocity.x * .060f;
        float cameraTargetY = player.position.y * .82f + player.velocity.y * .060f;
        float cameraZoomTarget = COMBAT_CAMERA_ZOOM;

        if (bossRevealTimer > 0f && bossRevealTarget != null && bossRevealTarget.alive) {
            bossRevealTimer = Math.max(0f, bossRevealTimer - dt);
            boolean reducedMotion = game.accessibility != null && game.accessibility.reducedMotion;
            float reveal = BossRevealCameraProfile.envelope(bossRevealTimer);
            float focus = BossRevealCameraProfile.focusWeight(reveal, reducedMotion);
            float midpointX = (player.position.x + bossRevealTarget.position.x) * .5f;
            float midpointY = (player.position.y + bossRevealTarget.position.y) * .5f;
            cameraTargetX = MathUtils.lerp(cameraTargetX, midpointX, focus);
            cameraTargetY = MathUtils.lerp(cameraTargetY, midpointY, focus);
            cameraZoomTarget = BossRevealCameraProfile.zoom(COMBAT_CAMERA_ZOOM, reveal, reducedMotion);
        } else {
            bossRevealTimer = 0f;
            bossRevealTarget = null;
        }

        cam.position.x = MathUtils.lerp(cam.position.x, cameraTargetX, bossRevealTimer > 0f ? .15f : .11f);
        cam.position.y = MathUtils.lerp(cam.position.y, cameraTargetY, bossRevealTimer > 0f ? .15f : .11f);
        cam.zoom = MathUtils.lerp(cam.zoom, cameraZoomTarget, .12f);
        polish.applyCameraRecoil(cam);
        cam.update();
    }

    private void updateEnemies(float dt) {
        for (Enemy e : enemies) {
            if (!e.alive) continue;
            float dx = player.position.x - e.position.x;
            float dy = player.position.y - e.position.y;
            float len2 = dx * dx + dy * dy;
            float distance = (float)Math.sqrt(Math.max(len2, .0001f));
            e.updateAi(dt, distance);

            if (e.type == Enemy.Type.BOSS && e.bossCombat != null && e.bossPhases != null) {
                int phase = e.bossPhases.phase();
                if (e.bossCombat.consumeCharge(phase)) {
                    addCameraShake(.16f);
                    impact(e.position.x, e.position.y, 2.8f, .24f, VisualTheme.RED);
                }
                if (e.bossCombat.consumeSummon(phase)) spawnBossMinions(e, phase);
                if (e.bossCombat.consumeEnragePulse(phase)) bossEnragePulse(e);
            }

            if (len2 > .0001f) {
                float inv = 1f / distance;
                float speed = e.effectiveSpeed();
                float direction = 1f;
                if (e.type == Enemy.Type.RANGED && distance < e.attack.archetype().preferredRange) direction = -0.65f;
                if (e.attack.state() == EnemyState.TELEGRAPHING || e.attack.state() == EnemyState.RECOVERING) speed *= 0.22f;
                e.velocity.set(dx * inv * speed * direction, dy * inv * speed * direction).add(e.impulse);
            }
            e.position.mulAdd(e.velocity, dt);

            if (e.attack.consumeAttack()) resolveEnemyAttack(e);

            boolean aliveBeforeStatus = e.alive;
            e.updateStatus(dt);
            if (aliveBeforeStatus && !e.alive) onEnemyKilled(e);
            float rr = player.radius + e.radius;
            if (e.alive && e.type != Enemy.Type.RANGED && len2 < rr * rr && contactTimer <= 0f) {
                if (e.consumeChargeImpact()) {
                    EnemyPatternCatalog.ChargePattern charge = EnemyPatternCatalog.charge(e.type, e.variant);
                    damagePlayer(e.contactDamage * charge.impactDamageMultiplier(), .58f);
                    float inv = 1f / distance;
                    player.position.x += dx * inv * charge.knockbackStrength();
                    player.position.y += dy * inv * charge.knockbackStrength();
                    player.position.x = MathUtils.clamp(player.position.x, -31, 31);
                    player.position.y = MathUtils.clamp(player.position.y, -17, 17);
                    impact(e.position.x, e.position.y, charge.impactRadius(), .26f, VisualTheme.GOLD);
                    addCameraShake(e.type == Enemy.Type.ELITE ? .52f : .38f);
                    contactTimer = Math.max(.30f, .42f * charge.recoveryMultiplier());
                } else {
                    damagePlayer(e.contactDamage, .35f);
                    contactTimer = .28f;
                }
            }
        }
    }

    static Enemy.Type bossSummonType(boolean nullArchon, boolean revenant, int phase, int index) {
        return bossSummonType(nullArchon ? BossIdentity.NULL_ARCHON
            : revenant ? BossIdentity.REVENANT : BossIdentity.ALPHA, phase, index);
    }

    static Enemy.Type bossSummonType(BossIdentity identity, int phase, int index) {
        BossIdentity safeIdentity = identity == null ? BossIdentity.ALPHA : identity;
        int safeIndex = Math.max(0, index);
        if (safeIdentity == BossIdentity.NULL_ARCHON) {
            if (phase >= 3) {
                return switch (safeIndex % 3) {
                    case 0 -> Enemy.Type.PHANTOM;
                    case 1 -> Enemy.Type.RANGED;
                    default -> Enemy.Type.REGENERATOR;
                };
            }
            return safeIndex % 3 == 1 ? Enemy.Type.RANGED : Enemy.Type.PHANTOM;
        }
        if (safeIdentity == BossIdentity.FROST_COLOSSUS) {
            if (phase >= 3) {
                return switch (safeIndex % 3) {
                    case 0 -> Enemy.Type.SHIELDED;
                    case 1 -> Enemy.Type.BRUTE;
                    default -> Enemy.Type.RANGED;
                };
            }
            return safeIndex % 2 == 0 ? Enemy.Type.SHIELDED : Enemy.Type.BRUTE;
        }
        boolean revenant = safeIdentity == BossIdentity.REVENANT;
        boolean rangedSlot = phase >= 3 && (revenant ? safeIndex % 2 == 0 : safeIndex % 3 == 0);
        return rangedSlot ? Enemy.Type.RANGED : Enemy.Type.RUNNER;
    }

    private void spawnBossMinions(Enemy boss, int phase) {
        int count = boss.bossCombat == null ? (phase >= 3 ? 6 : 3) : boss.bossCombat.summonCount(phase);
        BossIdentity identity = boss.bossCombat == null ? BossIdentity.ALPHA : boss.bossCombat.identity();
        boolean revenant = identity == BossIdentity.REVENANT;
        boolean nullArchon = identity == BossIdentity.NULL_ARCHON;
        boolean frostColossus = identity == BossIdentity.FROST_COLOSSUS;
        for (int i = 0; i < count && enemies.size < GameConfig.MAX_ENEMIES; i++) {
            float angle = i * (MathUtils.PI2 / count) + MathUtils.random(-.18f, .18f);
            float dist = 2.6f + MathUtils.random(0f, 1.2f);
            float x = boss.position.x + MathUtils.cos(angle) * dist;
            float y = boss.position.y + MathUtils.sin(angle) * dist;
            float scale = 1f + director.elapsed() / 210f;
            Enemy.Type type = bossSummonType(identity, phase, i);
            Enemy minion = switch (type) {
                case RANGED -> new Enemy(type, x, y, 68f * scale, 2.2f, .42f, 12f, 10);
                case PHANTOM -> new Enemy(type, x, y, 54f * scale, 3.25f, .40f, 11f, 10);
                case REGENERATOR -> new Enemy(type, x, y, 92f * scale, 2.05f, .48f, 11f, 12);
                case SHIELDED -> new Enemy(type, x, y, 125f * scale, 1.72f, .64f, 14f, 16);
                case BRUTE -> new Enemy(type, x, y, 118f * scale, 1.82f, .68f, 16f, 16);
                default -> new Enemy(type, x, y, 30f * scale, 4.35f, .34f, 8f, 5);
            };
            enemies.add(minion);
            spatial.add(minion);
            abilitySystem.onEnemySpawned(minion);
            impact(x, y, nullArchon ? .82f : frostColossus ? .78f : .65f,
                nullArchon ? .22f : frostColossus ? .20f : .18f,
                frostColossus ? VisualTheme.CYAN : revenant ? VisualTheme.RED : VisualTheme.VIOLET);
        }
        addCameraShake(nullArchon ? .22f : frostColossus ? .24f : (revenant ? .18f : .12f));
    }

    private void bossEnragePulse(Enemy boss) {
        int shots = boss.bossCombat == null ? 20 : boss.bossCombat.enrageShots();
        float speed = boss.bossCombat == null ? 8.2f : boss.bossCombat.enrageProjectileSpeed();
        int explosiveEvery = boss.bossCombat == null ? 4 : boss.bossCombat.enrageExplosiveEvery();
        float explosionRadius = boss.bossCombat == null ? 2.0f : boss.bossCombat.enrageExplosionRadius();
        for (int i = 0; i < shots; i++) {
            boolean explosive = explosiveEvery > 0 && i % explosiveEvery == 0;
            spawnHostileShot(boss, boss.position.x, boss.position.y, i * (360f / shots), speed,
                boss.contactDamage * .65f, explosive ? .26f : .24f, explosive, explosionRadius);
        }
        BossIdentity identity = boss.bossCombat == null ? BossIdentity.ALPHA : boss.bossCombat.identity();
        boolean revenant = identity == BossIdentity.REVENANT;
        boolean frostColossus = identity == BossIdentity.FROST_COLOSSUS;
        impact(boss.position.x, boss.position.y,
            frostColossus ? 6.2f : revenant ? 5.8f : 5.1f, .34f,
            frostColossus ? VisualTheme.CYAN : revenant ? VisualTheme.RED : VisualTheme.VIOLET);
        addCameraShake(frostColossus ? .50f : revenant ? .46f : .38f);
    }

    private void updatePlayerProjectiles(float dt) {
        for (Projectile p : pools.projectiles) {
            if (!p.active) continue;
            p.position.mulAdd(p.velocity, dt);
            p.life -= dt;
            if (p.life <= 0f) { p.active = false; continue; }
            spatial.query(p.position.x, p.position.y, 1.35f, collisionCandidates);
            for (Enemy e : collisionCandidates) {
                if (!e.alive || e == p.lastHit) continue;
                float rr = p.radius + e.radius;
                if (p.position.dst2(e.position) > rr * rr) continue;
                boolean wasAlive = e.alive;
                e.damage(p.damage);
                e.hitFlash = 1f;
                e.applyElement(p.element, p.damage);
                float reactionBonus = player.protocols.reactionBonus(p.damage, e.lastReaction);
                if (reactionBonus > 0f && e.alive) {
                    e.damage(reactionBonus);
                    CombatVisualEvents.markProtocol(CombatVisualEvents.ProtocolCue.REACTION);
                }
                damageNumber(e.position.x, e.position.y + e.radius, p.damage + reactionBonus, p.critical,
                    p.critical ? VisualTheme.GOLD : VisualTheme.TEXT);
                float vlen = p.velocity.len();
                if (vlen > .001f) e.addImpulse(p.velocity.x / vlen * p.knockback, p.velocity.y / vlen * p.knockback);
                PlayerProjectilePresentation.Profile projectileVisual = PlayerProjectilePresentation.profile(p);
                Color impactColor = p.weaponSignature ? projectileVisual.accent() : projectileVisual.color();
                float impactScale = projectileVisual.impactScale();
                impact(p.position.x, p.position.y,
                    (p.critical ? .78f : .44f) * impactScale,
                    (p.critical ? .17f : .12f) * (p.weaponSignature ? 1.18f : 1f),
                    impactColor);
                if (p.weaponSignature) addCameraShake(.095f);
                else if (p.critical) addCameraShake(.075f);
                polish.onProjectileHit(p.critical);
                if (p.element == DamageElement.SHOCK && e.alive) chainShock(e, p.damage * .42f, 3);
                if (wasAlive && !e.alive) onEnemyKilled(e);
                p.lastHit = e;
                if (p.penetrationRemaining > 0) p.penetrationRemaining--; else p.active = false;
                break;
            }
        }
    }

    private void updateHostileProjectiles(float dt) {
        for (EnemyProjectile p : pools.hostileProjectiles) {
            if (!p.active) continue;
            p.position.mulAdd(p.velocity, dt);
            p.life -= dt;
            if (p.life <= 0f) { p.active = false; continue; }
            float rr = p.radius + player.radius;
            if (p.position.dst2(player.position) <= rr * rr) {
                p.active = false;
                if (p.explosive) {
                    impact(p.position.x, p.position.y, p.explosionRadius, .32f, VisualTheme.GOLD);
                    if (p.position.dst2(player.position) <= p.explosionRadius * p.explosionRadius) damagePlayer(p.damage, .48f);
                } else damagePlayer(p.damage, .22f);
            }
        }
    }

    private void resolveEnemyAttack(Enemy e) {
        aim.set(player.position).sub(e.position);
        if (aim.len2() < .0001f) aim.set(1f, 0f); else aim.nor();
        if (e.type == Enemy.Type.RANGED) {
            EnemyPatternCatalog.RangedPattern pattern = EnemyPatternCatalog.ranged(e.variant);
            float base = aim.angleDeg();
            for (int i = 0; i < pattern.shots(); i++) {
                float spread = (i - (pattern.shots() - 1) / 2f) * pattern.spreadDegrees();
                spawnHostileShot(e, e.position.x, e.position.y, base + spread,
                    8.5f * pattern.speedMultiplier(),
                    e.contactDamage * pattern.damageMultiplier(),
                    pattern.explosive() ? .24f : .18f,
                    pattern.explosive(), pattern.explosionRadius());
            }
            impact(e.position.x, e.position.y, pattern.explosive() ? .85f : .46f, .12f,
                pattern.explosive() ? VisualTheme.GOLD : VisualTheme.RED);
            return;
        }
        if (e.type == Enemy.Type.BOSS) {
            int phase = e.bossPhases == null ? 1 : e.bossPhases.phase();
            BossIdentity identity = e.bossCombat == null ? BossIdentity.ALPHA : e.bossCombat.identity();
            boolean revenant = identity == BossIdentity.REVENANT;
            boolean frostColossus = identity == BossIdentity.FROST_COLOSSUS;
            BossAttackPatternCatalog.Pattern pattern = BossAttackPatternCatalog.forPhase(identity, phase);
            float base = aim.angleDeg();
            for (int i = 0; i < pattern.shots(); i++) {
                float angle = pattern.radial()
                    ? i * pattern.spreadDegrees()
                    : base + (i - (pattern.shots() - 1) / 2f) * pattern.spreadDegrees();
                boolean explosive = pattern.explosiveEvery() > 0 && i % pattern.explosiveEvery() == 0;
                spawnHostileShot(e, e.position.x, e.position.y, angle,
                    7.2f * pattern.speedMultiplier(),
                    e.contactDamage * pattern.damageMultiplier(),
                    explosive ? .24f : .22f,
                    explosive, explosive ? pattern.explosionRadius() : 0f);
            }
            impact(e.position.x, e.position.y,
                frostColossus ? .92f : revenant ? .82f : .64f, .15f,
                frostColossus ? VisualTheme.CYAN : revenant ? VisualTheme.VIOLET : VisualTheme.RED);
            addCameraShake(.18f + phase * .05f + (revenant ? .04f : 0f) + (frostColossus ? .06f : 0f));
        }
    }

    private void spawnHostileShot(Enemy source, float x, float y, float angle, float speed, float damage,
                                  float radius, boolean explosive, float explosionRadius) {
        EnemyProjectile p = pools.hostileProjectile();
        if (p == null) return;
        shotVelocity.set(speed, 0f).setAngleDeg(angle);
        p.spawn(x, y, shotVelocity.x, shotVelocity.y, damage, radius, 4.5f, explosive, explosionRadius,
            HostileProjectilePresentation.styleFor(source));
    }

    private void chainShock(Enemy source, float damage, int maxChains) {
        Enemy current = source;
        for (int chain = 0; chain < maxChains; chain++) {
            Enemy nearest = spatial.nearestWithin(
                current.position.x, current.position.y, 3.4f, current, source);
            if (nearest == null) break;
            float fromX = current.position.x;
            float fromY = current.position.y;
            boolean wasAlive = nearest.alive;
            nearest.damage(damage);
            nearest.applyElement(DamageElement.SHOCK, damage);
            ArcFx arc = pools.arc();
            if (arc != null) arc.spawn(fromX, fromY, nearest.position.x, nearest.position.y, .11f);
            damageNumber(nearest.position.x, nearest.position.y + nearest.radius, damage, false, VisualTheme.CYAN);
            impact(nearest.position.x, nearest.position.y, .62f, .16f, VisualTheme.CYAN);
            if (wasAlive && !nearest.alive) onEnemyKilled(nearest);
            current = nearest;
            damage *= .78f;
        }
    }

    private void damagePlayer(float damage, float shake) {
        float hpBefore = player.hp;
        player.damage(damage);
        if (player.hp == hpBefore) return;
        if (game.accessibility != null && game.accessibility.haptics) game.services.haptics.damage();
        combatHud.triggerDamageFlash();
        addCameraShake(shake);
        impact(player.position.x, player.position.y, 1.1f, .18f, VisualTheme.RED);
        damageNumber(player.position.x, player.position.y + player.radius, damage, false, VisualTheme.RED);
        if (!player.alive) gameOver = true;
    }

    private void damageNumber(float x, float y, float value, boolean critical, Color color) {
        DamageNumber n = pools.damageNumber();
        if (n != null) n.spawn(x, y, value, critical, color);
    }

    private void onEnemyKilled(Enemy e) {
        if (e.type == Enemy.Type.BOSS) {
            bossKilledThisRun = true;
            if (game.accessibility != null && game.accessibility.haptics) game.services.haptics.bossKill();
        }
        polish.onEnemyKilled(e, pools);
        if (player.protocols.onKill()) {
            CombatVisualEvents.markProtocol(CombatVisualEvents.ProtocolCue.KILLCHAIN_ARMED);
        }
        director.onKill();
        if (player.addXp(e.xpValue)) prepareUpgrade();

        float killScale = switch (e.type) {
            case BOSS -> 4.1f;
            case ELITE, BRUTE -> 3.15f;
            case PHANTOM, REGENERATOR, SHIELDED -> 2.75f;
            default -> 2.45f;
        };
        Color killColor = switch (e.type) {
            case BOSS -> VisualTheme.GOLD;
            case PHANTOM -> VisualTheme.VIOLET;
            case REGENERATOR -> VisualTheme.GREEN;
            case RANGED -> VisualTheme.CYAN;
            default -> VisualTheme.RED;
        };
        float killDuration = e.type == Enemy.Type.BOSS ? .52f
            : (e.type == Enemy.Type.ELITE || e.type == Enemy.Type.BRUTE ? .36f : .28f);
        impact(e.position.x, e.position.y, e.radius * killScale, killDuration, killColor);
        if (e.type == Enemy.Type.BOSS) {
            impact(e.position.x, e.position.y, e.radius * 2.2f, .68f, Color.WHITE);
        }
        float killShake = e.type == Enemy.Type.BOSS ? .78f
            : (e.type == Enemy.Type.ELITE || e.type == Enemy.Type.BRUTE ? .22f : .115f);
        addCameraShake(killShake);
    }

    private void spawnEnemy() {
        Enemy.Type t = director.chooseType();
        float angle = MathUtils.random(MathUtils.PI2);
        float dist = t == Enemy.Type.BOSS ? 15f : MathUtils.random(13f, 19f);
        float x = player.position.x + MathUtils.cos(angle) * dist;
        float y = player.position.y + MathUtils.sin(angle) * dist;
        float scale = 1f + director.elapsed() / 180f;
        Enemy e = switch (t) {
            case RUNNER -> new Enemy(t, x, y, 28 * scale, 4.2f, .34f, 8, 6);
            case BRUTE -> new Enemy(t, x, y, 145 * scale, 1.6f, .72f, 18, 15);
            case RANGED -> new Enemy(t, x, y, 72 * scale, 2.15f, .42f, 13, 12);
            case ELITE -> new Enemy(t, x, y, 420 * scale, 2.1f, 1.05f, 28, 42);
            case BOSS -> {
                BossVariantStats.Stats stats = BossVariantStats.forStage(RunStageContext.stage(),
                    2200f * scale, 1.35f, 24f);
                yield new Enemy(t, x, y, stats.hp(), stats.speed(), 1.65f, stats.damage(), 280);
            }
            default -> new Enemy(t, x, y, 52 * scale, 2.55f, .46f, 10, 8);
        };
        enemies.add(e);
        spatial.add(e);
        abilitySystem.onEnemySpawned(e);
        if (t == Enemy.Type.BOSS) {
            director.onBossSpawned();
            bossRevealTarget = e;
            bossRevealTimer = BossRevealCameraProfile.DURATION;
        }
    }

    private void fire(Enemy target) {
        aim.set(target.position).sub(player.position).nor();
        float base = aim.angleDeg();
        int count = player.weapon.projectileCount;
        com.deadlinezero.game.progression.CombatProtocolState.VolleyModifier protocol = player.protocols.onVolley();
        if (protocol.damageMultiplier() > 1f) {
            CombatVisualEvents.ProtocolCue cue = protocol.forcedCrit() && protocol.bonusPenetration() > 0
                ? CombatVisualEvents.ProtocolCue.COMBINED
                : protocol.forcedCrit() ? CombatVisualEvents.ProtocolCue.RHYTHM
                : CombatVisualEvents.ProtocolCue.KILLCHAIN;
            CombatVisualEvents.markProtocol(cue);
            AudioDirector.playGlobal(AudioDirector.Cue.PROTOCOL_PROC);
        }
        for (int i = 0; i < count; i++) {
            float spread = (i - (count - 1) / 2f) * player.weapon.spreadDegrees;
            shotVelocity.set(player.weapon.projectileSpeed, 0f).setAngleDeg(base + spread);
            boolean crit = protocol.forcedCrit() || MathUtils.random() < player.weapon.critChance;
            Projectile p = pools.projectile();
            if (p != null) p.spawn(player.position.x, player.position.y, shotVelocity.x, shotVelocity.y,
                player.weapon.damage * protocol.damageMultiplier() * (crit ? player.weapon.critMultiplier : 1f), crit,
                Math.min(Upgrade.MAX_PENETRATION, player.weapon.penetration + protocol.bonusPenetration()),
                player.weapon.knockback, player.weapon.element);
        }
        polish.onShot(base);
        addCameraShake(count > 1 ? .052f : .043f);
    }

    private void addCameraShake(float amount) {
        if (amount <= 0f || game.accessibility == null || !game.accessibility.allowsScreenShake()) return;
        cameraShake = Math.max(cameraShake, amount * game.accessibility.screenShakeStrength);
    }

    private void impact(float x, float y, float s, float d, Color c) {
        ImpactFx f = pools.impact();
        if (f != null) f.spawn(x, y, s, d, c);
    }

    private void prepareUpgrade() {
        if (LegendarySelector.shouldOffer(player)) {
            legendaryChoiceCount = LegendarySelector.fillChoices(player, legendaryChoices);
            if (legendaryChoiceCount > 0) {
                choosingLegendary = true;
                return;
            }
        }
        prepareStandardUpgrade();
    }

    private void prepareStandardUpgrade() {
        choosingUpgrade = true;
        UpgradeSelector.fillChoices(player, choices);
    }

    private void finishRun() {
        if (settling) return;
        settling = true;
        game.finishRun(director.kills(), director.elapsed(), bossKilledThisRun, 0);
    }

    private void draw() {
        Gdx.gl.glClearColor(VisualTheme.BG.r, VisualTheme.BG.g, VisualTheme.BG.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Combat uses translucent shadows, telegraphs, impacts and modal overlays extensively.
        // ShapeRenderer does not enable alpha blending itself, so make the world pipeline explicit.
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        boolean authored = spritePass.authoredAvailable();

        batch.setProjectionMatrix(cam.combined);
        if (authored) spritePass.renderEnvironmentFloor(batch);

        shapes.setProjectionMatrix(cam.combined);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        if (!authored) {
            shapes.setColor(.018f, .030f, .040f, 1f);
            shapes.rect(-40, -24, 80, 48);
            shapes.setColor(.06f, .14f, .17f, .38f);
            for (int x = -40; x < 40; x += 2) shapes.rect(x, -24, .02f, 48);
            for (int y = -24; y < 24; y += 2) shapes.rect(-40, y, 80, .02f);
        }
        polish.drawWorldUnderlay(shapes, player, enemies, pools, visualTime);
        worldFx.drawGroundShadows(shapes, player, enemies);
        shapes.end();

        if (authored) spritePass.renderEnvironmentDressing(batch);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        worldFx.drawProjectileTrails(shapes, pools.projectiles, pools.hostileProjectiles, pools.homingMissiles);
        worldFx.drawElectricArcs(shapes, pools.arcs, visualTime);

        for (ImpactFx f : pools.impacts) if (f.active) {
            float a = MathUtils.clamp(f.life / f.maxLife, 0f, 1f);
            shapes.setColor(f.color.r, f.color.g, f.color.b, a * .12f);
            shapes.circle(f.position.x, f.position.y, f.size * (1.45f - a * .32f), 24);
            shapes.setColor(f.color.r, f.color.g, f.color.b, a * .50f);
            shapes.circle(f.position.x, f.position.y, f.size * (1f - a * .42f), 20);
            shapes.setColor(1f, 1f, 1f, a * .42f);
            shapes.circle(f.position.x, f.position.y, Math.max(.06f, f.size * .18f * a), 12);
        }
        for (Projectile p : pools.projectiles) if (p.active) {
            PlayerProjectilePresentation.Profile visual = PlayerProjectilePresentation.profile(p);
            Color core = visual.color();
            float radius = p.radius * visual.coreScale();
            if (visual.signature()) {
                Color accent = visual.accent();
                shapes.setColor(accent.r, accent.g, accent.b, .20f);
                shapes.circle(p.position.x, p.position.y, radius * 1.75f, 16);
            }
            shapes.setColor(core);
            shapes.circle(p.position.x, p.position.y, radius, visual.signature() ? 16 : 12);
            if (visual.style() == PlayerProjectilePresentation.Style.RAIL
                || (visual.signature() && visual.style() == PlayerProjectilePresentation.Style.TEMPEST)) {
                shapes.setColor(1f, 1f, 1f, .84f);
                shapes.circle(p.position.x, p.position.y, Math.max(.035f, radius * .38f), 9);
            }
        }
        for (EnemyProjectile p : pools.hostileProjectiles) if (p.active) {
            Color core = switch (p.style) {
                case CINDER -> Color.ORANGE;
                case STATIC -> VisualTheme.CYAN;
                case NULL -> VisualTheme.VIOLET;
                default -> p.explosive ? VisualTheme.GOLD : VisualTheme.RED;
            };
            float visualRadius = p.radius * HostileProjectilePresentation.coreRadiusMultiplier(p.style);
            if (p.style != EnemyProjectile.Style.DEFAULT) {
                shapes.setColor(core.r, core.g, core.b, .16f);
                shapes.circle(p.position.x, p.position.y, visualRadius * 1.65f, 16);
            }
            shapes.setColor(core);
            shapes.circle(p.position.x, p.position.y, visualRadius, 14);
            if (p.style == EnemyProjectile.Style.STATIC) {
                shapes.setColor(1f, 1f, 1f, .82f);
                shapes.circle(p.position.x, p.position.y, visualRadius * .42f, 9);
            } else if (p.style == EnemyProjectile.Style.NULL) {
                shapes.setColor(.08f, .04f, .14f, .90f);
                shapes.circle(p.position.x, p.position.y, visualRadius * .38f, 9);
            }
        }
        for (HomingMissile m : pools.homingMissiles) if (m.active) {
            shapes.setColor(m.element == DamageElement.FROST ? VisualTheme.CYAN : VisualTheme.GOLD);
            shapes.circle(m.position.x, m.position.y, m.radius * 1.35f, 10);
        }
        drawAbilityObjects();
        for (Enemy e : enemies) drawEnemy(e, !authored);

        if (!authored) {
            shapes.setColor(player.invulnerable() ? Color.WHITE : VisualTheme.CYAN);
            float playerPulse = 1f + MathUtils.sin(visualTime * 7f) * .035f;
            shapes.circle(player.position.x, player.position.y, player.radius * playerPulse, 24);
            shapes.setColor(VisualTheme.CYAN.r, VisualTheme.CYAN.g, VisualTheme.CYAN.b, .18f);
            shapes.circle(player.position.x, player.position.y, player.radius * 1.45f * playerPulse, 24);
        }
        shapes.end();

        batch.setProjectionMatrix(cam.combined);
        polish.drawAuthoredDeaths(batch, pools);
        spritePass.renderCombat(batch, player, enemies, pools);
        drawCombatText();
        drawHud();
    }

    private void drawEnemy(Enemy e, boolean drawBody) {
        if (!e.alive) return;
        Color c = switch (e.type) {
            case RUNNER -> ENEMY_RUNNER;
            case BRUTE -> ENEMY_BRUTE;
            case RANGED -> ENEMY_RANGED;
            case ELITE -> ENEMY_ELITE;
            case BOSS -> VisualTheme.RED;
            default -> ENEMY_DEFAULT;
        };
        float speedRatio = MathUtils.clamp(e.velocity.len() / Math.max(.01f, e.speed), 0f, 1.5f);
        float gait = MathUtils.sin(visualTime * (5f + speedRatio * 3f) + e.position.x * .7f) * .06f * speedRatio;
        if (e.attack.state() == EnemyState.TELEGRAPHING) {
            float pulse = .82f + MathUtils.sin(visualTime * 14f) * .18f;
            float radius = (e.type == Enemy.Type.BOSS ? e.radius * 2.45f : e.radius * 1.72f) * pulse;
            shapes.setColor(VisualTheme.RED.r, VisualTheme.RED.g, VisualTheme.RED.b, .44f);
            drawThreatRing(e.position.x, e.position.y, radius, e.type == Enemy.Type.BOSS ? 10 : 6,
                e.type == Enemy.Type.BOSS ? .085f : .052f);
        }
        if (e.type == Enemy.Type.BOSS && e.bossCombat != null && e.bossCombat.charging()) {
            float radius = e.radius * (1.42f + MathUtils.sin(visualTime * 20f) * .08f);
            shapes.setColor(1f, .15f, .05f, .54f);
            drawThreatRing(e.position.x, e.position.y, radius, 12, .090f);
            shapes.setColor(1f, .68f, .22f, .48f);
            for (int i = 0; i < 4; i++) {
                float angle = i * 90f + visualTime * 70f;
                float ox = MathUtils.cosDeg(angle);
                float oy = MathUtils.sinDeg(angle);
                float tx = -oy;
                float ty = ox;
                float tipX = e.position.x + ox * radius * .78f;
                float tipY = e.position.y + oy * radius * .78f;
                float baseX = e.position.x + ox * radius * 1.05f;
                float baseY = e.position.y + oy * radius * 1.05f;
                float half = e.radius * .18f;
                shapes.triangle(tipX, tipY,
                    baseX + tx * half, baseY + ty * half,
                    baseX - tx * half, baseY - ty * half);
            }
        }
        if (drawBody) {
            if (e.hitFlash > 0f) c = Color.WHITE;
            shapes.setColor(c);
            float sx = e.radius * (1f - gait);
            float sy = e.radius * (1f + gait);
            shapes.ellipse(e.position.x - sx, e.position.y - sy, sx * 2f, sy * 2f);
        }
        if (e.type != Enemy.Type.BOSS) {
            shapes.setColor(.08f, .09f, .10f, .82f);
            shapes.rect(e.position.x - e.radius, e.position.y + e.radius + .12f, e.radius * 2f, .07f);
            shapes.setColor(VisualTheme.RED);
            shapes.rect(e.position.x - e.radius, e.position.y + e.radius + .12f,
                e.radius * 2f * MathUtils.clamp(e.hp / Math.max(1f, e.maxHp), 0f, 1f), .07f);
        }
    }

    private void drawThreatRing(float cx, float cy, float radius, int pips, float pipRadius) {
        int count = Math.max(4, pips);
        for (int i = 0; i < count; i++) {
            float angle = i * (360f / count);
            shapes.circle(cx + MathUtils.cosDeg(angle) * radius,
                cy + MathUtils.sinDeg(angle) * radius, pipRadius, 8);
        }
    }

    private void drawAbilityObjects() {
        float angle = abilitySystem.runtime().orbitalAngle;
        if (player.abilities.unlocked(AbilityType.DRONE)) {
            float dx = player.position.x + MathUtils.cosDeg(angle + 180f) * 1.8f;
            float dy = player.position.y + MathUtils.sinDeg(angle + 180f) * 1.8f;
            shapes.setColor(VisualTheme.GREEN.r, VisualTheme.GREEN.g, VisualTheme.GREEN.b, .20f);
            shapes.circle(dx, dy, .34f, 14);
            shapes.setColor(VisualTheme.GREEN);
            shapes.circle(dx, dy, .18f, 12);
        }
        if (player.abilities.unlocked(AbilityType.ORBITAL_BLADE)) {
            float orbit = 2f + player.abilities.level(AbilityType.ORBITAL_BLADE) * .12f;
            float bx = player.position.x + MathUtils.cosDeg(angle) * orbit;
            float by = player.position.y + MathUtils.sinDeg(angle) * orbit;
            shapes.setColor(VisualTheme.GOLD.r, VisualTheme.GOLD.g, VisualTheme.GOLD.b, .20f);
            shapes.circle(bx, by, .46f, 16);
            shapes.setColor(VisualTheme.GOLD);
            shapes.rect(bx - .12f, by - .34f, .24f, .68f);
        }
    }

    private void drawCombatText() {
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        font.getData().setScale(.034f);
        for (DamageNumber n : pools.damageNumbers) {
            if (!n.active) continue;
            float alpha = MathUtils.clamp(n.life / n.maxLife, 0f, 1f);
            font.setColor(n.color.r, n.color.g, n.color.b, alpha);
            font.getData().setScale(n.critical ? .050f : .034f);
            font.draw(batch, n.text, n.x - .45f, n.y, .9f, Align.center, false);
        }
        batch.end();
        font.getData().setScale(.75f);
    }

    private void drawHud() {
        float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
        combatHud.render(shapes, batch, font, player, director, enemies, w, h);
        if (!choosingUpgrade && !choosingLegendary && !gameOver) return;
        if (choosingUpgrade || choosingLegendary) drawChoiceBackdrop(w, h, choosingLegendary);
        if (gameOver) drawGameOverBackdrop(w, h);
        batch.getProjectionMatrix().setToOrtho2D(0, 0, w, h);
        batch.begin();
        if (choosingLegendary) drawLegendaryText(w, h);
        else if (choosingUpgrade) drawUpgradeText(w, h);
        if (gameOver) drawGameOverText(w, h);
        batch.end();
    }

    private void drawChoiceBackdrop(float w, float h, boolean legendary) {
        shapes.getProjectionMatrix().setToOrtho2D(0, 0, w, h);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(.004f, .008f, .013f, .82f);
        shapes.rect(0f, 0f, w, h);

        float panelX = w * .07f;
        float panelY = h * .265f;
        float panelW = w * .86f;
        float panelH = h * .49f;
        Color panelAccent = legendary ? VisualTheme.GOLD : VisualTheme.accent();
        UiRenderer.premiumPanel(shapes, panelX, panelY, panelW, panelH, panelAccent, true);

        int count = legendary ? Math.max(1, legendaryChoiceCount) : 3;
        float cardWidth = Math.min(w * .27f, panelW / Math.max(3f, count) - w * .018f);
        float cardHeight = h * .285f;
        float cardY = h * .34f;
        for (int i = 0; i < count; i++) {
            float centerX = w * ((i + 1f) / (count + 1f));
            float left = centerX - cardWidth * .5f;
            Color accent = legendary ? VisualTheme.GOLD : VisualTheme.upgradeRarity(choices[i].rarity);

            UiRenderer.premiumCard(shapes, left, cardY, cardWidth, cardHeight,
                accent, legendary, false, false);
            if (legendary) {
                float badge = Math.min(cardWidth, cardHeight) * .13f;
                shapes.setColor(accent.r, accent.g, accent.b, .24f);
                shapes.circle(centerX, cardY + cardHeight * .69f, badge, 24);
                shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .92f);
                shapes.circle(centerX, cardY + cardHeight * .69f, badge * .48f, 20);
            } else {
                float iconSize = Math.min(cardWidth * .22f, cardHeight * .30f);
                UpgradeIconRenderer.draw(shapes, choices[i], centerX,
                    cardY + cardHeight * .73f, iconSize, accent);
            }
        }
        shapes.end();
    }

    private void drawGameOverBackdrop(float w, float h) {
        shapes.getProjectionMatrix().setToOrtho2D(0, 0, w, h);
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(.004f, .006f, .010f, .88f);
        shapes.rect(0f, 0f, w, h);

        float panelW = Math.min(w * .58f, 760f);
        float panelH = Math.min(h * .42f, 410f);
        float panelX = (w - panelW) * .5f;
        float panelY = h * .30f;
        Color accent = revived ? VisualTheme.GOLD : VisualTheme.danger();

        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, .995f);
        shapes.rect(panelX, panelY, panelW, panelH);
        shapes.setColor(VisualTheme.BORDER.r, VisualTheme.BORDER.g, VisualTheme.BORDER.b, .95f);
        shapes.rect(panelX, panelY, panelW, 2f);
        shapes.rect(panelX, panelY + panelH - 2f, panelW, 2f);
        shapes.rect(panelX, panelY, 2f, panelH);
        shapes.rect(panelX + panelW - 2f, panelY, 2f, panelH);

        float pulse = .68f + .22f * (MathUtils.sin(visualTime * 3.2f) * .5f + .5f);
        shapes.setColor(accent.r, accent.g, accent.b, revived ? .72f : pulse);
        shapes.rect(panelX, panelY + panelH - 6f, panelW, 6f);
        shapes.rect(panelX, panelY, 5f, panelH);

        float ctaX = panelX + panelW * .12f;
        float ctaY = panelY + panelH * .12f;
        float ctaW = panelW * .76f;
        float ctaH = Math.max(52f, panelH * .18f);
        shapes.setColor(accent.r, accent.g, accent.b, revived ? .14f : .20f);
        shapes.rect(ctaX, ctaY, ctaW, ctaH);
        shapes.setColor(accent.r, accent.g, accent.b, .88f);
        shapes.rect(ctaX, ctaY, ctaW, 2f);
        shapes.rect(ctaX, ctaY + ctaH - 2f, ctaW, 2f);

        shapes.end();
    }

    private void drawLegendaryText(float w, float h) {
        font.getData().setScale(1.45f);
        font.setColor(VisualTheme.GOLD);
        font.draw(batch, t("combat.legendaryTitle"), 0, h * .79f, w, Align.center, false);
        font.getData().setScale(.68f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, t("combat.legendarySubtitle"), 0, h * .70f, w, Align.center, false);
        for (int i = 0; i < legendaryChoiceCount; i++) {
            LegendaryChoice choice = legendaryChoices[i];
            float x = w * ((i + 1f) / (legendaryChoiceCount + 1f));
            font.getData().setScale(.86f);
            font.setColor(VisualTheme.GOLD);
            font.draw(batch, f("combat.legendaryCard", i + 1, t(choice.titleKey())), x - 145f, h * .53f, 290f, Align.center, false);
            font.getData().setScale(.66f);
            font.setColor(VisualTheme.TEXT);
            font.draw(batch, t(choice.descriptionKey()), x - 145f, h * .44f, 290f, Align.center, true);
            font.setColor(VisualTheme.MUTED);
            font.draw(batch, t("combat.legendaryFooter"), x - 145f, h * .35f, 290f, Align.center, false);
        }
        font.getData().setScale(.75f);
    }

    private void drawUpgradeText(float w, float h) {
        font.getData().setScale(1.50f);
        font.setColor(VisualTheme.TEXT);
        font.draw(batch, t("combat.upgradeTitle"), 0, h * .705f, w, Align.center, false);

        float panelW = w * .86f;
        float cardWidth = Math.min(w * .27f, panelW / 3f - w * .018f);
        float cardHeight = h * .285f;
        float cardY = h * .34f;
        for (int i = 0; i < 3; i++) {
            float centerX = w * ((i + 1f) / 4f);
            float left = centerX - cardWidth * .5f;

            font.getData().setScale(1.08f);
            font.setColor(VisualTheme.upgradeRarity(choices[i].rarity));
            font.draw(batch, f("combat.upgradeCard", i + 1, t(choices[i].titleKey())),
                left + 14f, cardY + cardHeight * .535f, cardWidth - 28f, Align.center, true);

            font.getData().setScale(.78f);
            boolean buildPath = i == 0 && UpgradeSelector.isBuildFocusedChoice(player, choices[i]);
            font.setColor(buildPath ? VisualTheme.CYAN : VisualTheme.TEXT_DIM);
            font.draw(batch, buildPath
                    ? choices[i].rarity.name() + "  |  " + t("combat.upgradeBuildPath")
                    : choices[i].rarity.name(),
                left + 14f, cardY + cardHeight * .405f, cardWidth - 28f, Align.center, false);

            font.getData().setScale(.86f);
            font.setColor(VisualTheme.TEXT_STRONG);
            font.draw(batch, t(choices[i].descriptionKey()),
                left + 20f, cardY + cardHeight * .295f, cardWidth - 40f, Align.center, true);

            String guidanceKey = AbilityUpgradeGuidance.key(player, choices[i]);
            if (guidanceKey == null) guidanceKey = ProtocolUpgradeGuidance.key(player, choices[i]);
            if (guidanceKey != null) {
                font.getData().setScale(.70f);
                font.setColor(VisualTheme.GOLD);
                font.draw(batch, t(guidanceKey),
                    left + 18f, cardY + cardHeight * .145f, cardWidth - 36f, Align.center, true);
            }
        }

        font.getData().setScale(.78f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, t("combat.upgradeFooter"), 0, h * .305f, w, Align.center, false);
        font.getData().setScale(.75f);
    }

    private void drawGameOverText(float w, float h) {
        Color accent = revived ? VisualTheme.GOLD : VisualTheme.danger();
        font.getData().setScale(1.62f);
        font.setColor(accent);
        font.draw(batch, t("combat.gameOver"), 0, h * .625f, w, Align.center, false);

        font.getData().setScale(.78f);
        font.setColor(VisualTheme.TEXT_DIM);
        font.draw(batch, f("hud.stage", RunStageContext.stage()), 0, h * .535f, w, Align.center, false);

        font.getData().setScale(.92f);
        font.setColor(VisualTheme.TEXT_STRONG);
        font.draw(batch, revived ? t("combat.results") : t("combat.revive"), 0, h * .425f, w, Align.center, false);

        font.getData().setScale(.68f);
        font.setColor(VisualTheme.MUTED);
        font.draw(batch, f("hud.kills", director.kills()), 0, h * .365f, w, Align.center, false);
        font.getData().setScale(.75f);
    }

    private void handleOverlayInput() {
        if (choosingLegendary) {
            int idx = -1;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) idx = 0;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) idx = 1;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) idx = 2;
            if (Gdx.input.justTouched() && legendaryChoiceCount > 0) {
                idx = Math.min(legendaryChoiceCount - 1,
                    (int)(Gdx.input.getX() / (float)Gdx.graphics.getWidth() * legendaryChoiceCount));
            }
            if (idx >= 0 && idx < legendaryChoiceCount && legendaryChoices[idx] != null) {
                if (legendaryChoices[idx].apply(player)) {
                    addCameraShake(.64f);
                    impact(player.position.x, player.position.y, 2.9f, .42f, VisualTheme.GOLD);
                }
                choosingLegendary = false;
                legendaryChoiceCount = 0;
                prepareStandardUpgrade();
            }
            return;
        }
        if (choosingUpgrade) {
            int idx = -1;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) idx = 0;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) idx = 1;
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) idx = 2;
            if (Gdx.input.justTouched()) idx = Math.min(2, (int)(Gdx.input.getX() / (float)Gdx.graphics.getWidth() * 3));
            if (idx >= 0) {
                applyUpgradeWithSynergyFeedback(choices[idx]);
                choosingUpgrade = false;
            }
        }
        if (gameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                finishRun();
                return;
            }
            if (Gdx.input.justTouched()) {
                if (!revived) game.services.ads.showRewarded(AdsService.Reward.REVIVE, () -> {
                    player.alive = true;
                    player.hp = player.maxHp * .45f;
                    gameOver = false;
                    revived = true;
                    game.services.ads.preload();
                }, this::finishRun);
                else finishRun();
            }
        }
        if (!gameOver && !choosingUpgrade && !choosingLegendary && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) finishRun();
    }

    private void applyUpgradeWithSynergyFeedback(Upgrade upgrade) {
        AbilitySynergyUnlockDetector.Snapshot before =
            AbilitySynergyUnlockDetector.snapshot(player.abilities);
        upgrade.apply(player);
        AbilitySynergyUnlockDetector.Synergy synergy =
            AbilitySynergyUnlockDetector.newlyActivated(before, player.abilities);
        String key = AbilitySynergyUnlockDetector.hudKey(synergy);
        if (key == null) return;
        CombatVisualEvents.markSynergy(key);
        AudioDirector.playGlobal(AudioDirector.Cue.LEVEL_UP);
        addCameraShake(.22f);
        impact(player.position.x, player.position.y, 1.8f, .26f, VisualTheme.GOLD);
    }

    private String t(String key) { return game.i18n.text(key); }
    private String f(String key, Object... args) { return game.i18n.format(key, args); }

    @Override public void resize(int width, int height) {
        cam.viewportWidth = GameConfig.WORLD_WIDTH;
        cam.viewportHeight = GameConfig.WORLD_WIDTH * ((float)height / width);
        cam.zoom = COMBAT_CAMERA_ZOOM;
        cam.update();
    }

    @Override public void dispose() {
        spritePass.dispose();
        shapes.dispose();
        batch.dispose();
        font.dispose();
    }
}
