package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.ai.LeaperRuntime;
import com.deadlinezero.game.ai.LeaperSharedRuntime;
import com.deadlinezero.game.audio.AudioDirector;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.EnemyProjectile;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.fx.DeathFx;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.util.Pools;
import com.deadlinezero.game.world.ArenaHazardRuntime;
import com.deadlinezero.game.world.DeathBurstRules;

/**
 * Centralized presentation-only combat feedback: micro hit-stop, recoil, local lighting,
 * persistent death marks, corpses, resilient event-driven audio and accessible haptics.
 * Arena hazards are rendered here, while damage still flows through pooled hostile projectiles.
 */
public final class CombatPolishController {
    private static Pools currentPools;
    private final CombatFeel feel = new CombatFeel();
    private final LocalLightRenderer lights = new LocalLightRenderer();
    private final LegendaryFxRenderer legendaryFx = new LegendaryFxRenderer();
    private final LeaperRuntime leapers = LeaperSharedRuntime.get();
    private final ArenaHazardRuntime hazards = new ArenaHazardRuntime();
    private final SingularityImpactTracker singularityImpacts = new SingularityImpactTracker();
    private final DeathFxRenderer deaths;
    private final AccessibilitySettings settings;
    private final AdaptiveFxBudget fxBudget = new AdaptiveFxBudget();
    private Enemy phaseBoss;
    private float phaseFxStarted;
    private float phaseFxUntil;
    private int phaseFxPhase;
    private float lastHazardVisualTime = Float.NaN;
    private float lastSingularityVisualTime = Float.NaN;

    public CombatPolishController(GameArt art) {
        this(art, AccessibilitySettings.load());
    }

    public CombatPolishController(GameArt art, AccessibilitySettings settings) {
        deaths = new DeathFxRenderer(art);
        this.settings = settings == null ? new AccessibilitySettings() : settings;
        CombatVisualEvents.reset();
    }

    static Pools currentPools() { return currentPools; }

    public float simulationScale(float visualDelta) {
        return settings.hitStop ? feel.consumeSimulationScale(visualDelta) : 1f;
    }

    public void updateVisual(float dt) {
        feel.update(dt);
        fxBudget.update(dt);
    }

    public void updateSimulation(float dt, Pools pools) {
        for (DeathFx fx : pools.deathFx) fx.update(dt);
    }

    public void onShot(float angleDeg) {
        CombatVisualEvents.markPlayerShot();
        AudioDirector.playGlobal(AudioDirector.Cue.SHOT, .96f + MathUtils.random(.08f), 0f);
        if (settings.screenShake && settings.screenShakeStrength > 0f) {
            feel.triggerRecoil(angleDeg, .075f * settings.screenShakeStrength);
        }
    }

    public void onProjectileHit(boolean critical) {
        AudioDirector.playGlobal(critical ? AudioDirector.Cue.CRIT : AudioDirector.Cue.HIT,
            critical ? 1.04f : .98f + MathUtils.random(.04f), 0f);
        if (critical) vibrate(14);
        if (settings.hitStop && critical) feel.triggerHitStop(.014f);
    }

    public void onEnemyKilled(Enemy enemy, Pools pools) {
        DeathFx fx = pools.deathFx();
        if (fx != null) {
            float duration = enemy.type == Enemy.Type.BOSS ? 26f : (fxBudget.allowHeavyFx() ? 13f : 8f);
            float rotation = enemy.velocity.len2() > .001f ? enemy.velocity.angleDeg() - 90f : MathUtils.random(0f, 360f);
            fx.spawn(enemy.type, enemy.position.x, enemy.position.y, rotation, enemy.radius, duration);
        }

        int threatTier = RunStageContext.threatTier();
        if (DeathBurstRules.enabled(enemy.type, threatTier)) {
            hazards.scheduleDeathBurst(enemy.position.x, enemy.position.y,
                DeathBurstRules.radius(enemy.type, threatTier), DeathBurstRules.damage(enemy.type, threatTier));
        }

        AudioDirector.playGlobal(enemy.type == Enemy.Type.BOSS ? AudioDirector.Cue.BOSS_KILL : AudioDirector.Cue.KILL,
            enemy.type == Enemy.Type.BOSS ? .88f : .96f + MathUtils.random(.08f), 0f);

        if (enemy.type == Enemy.Type.BOSS) vibrate(48);
        else if (enemy.type == Enemy.Type.ELITE || enemy.type == Enemy.Type.BRUTE) vibrate(24);

        if (!settings.hitStop) return;
        if (enemy.type == Enemy.Type.BOSS) {
            feel.triggerHitStop(.065f);
        } else if (enemy.type == Enemy.Type.ELITE || enemy.type == Enemy.Type.BRUTE) {
            feel.triggerHitStop(.030f);
        } else {
            feel.triggerHitStop(.018f);
        }
    }

    private void vibrate(int millis) {
        if (!settings.haptics || millis <= 0) return;
        try { Gdx.input.vibrate(millis); } catch (RuntimeException ignored) { }
    }

    public void applyCameraRecoil(OrthographicCamera camera) {
        if (!settings.screenShake || settings.screenShakeStrength <= 0f) return;
        camera.position.x += feel.recoilX();
        camera.position.y += feel.recoilY();
    }

    public void drawWorldUnderlay(ShapeRenderer shapes, Player player, Array<Enemy> enemies, Pools pools, float time) {
        currentPools = pools;
        updateAndDrawHazards(shapes, player, pools, time);
        updateAndDrawSingularityImpacts(shapes, pools, time);
        deaths.drawFallback(shapes, pools.deathFx);
        legendaryFx.render(shapes, player, time, fxBudget.quality());
        drawLeaperTelegraphs(shapes, enemies, time);
        drawBossPhaseTransitions(shapes, enemies, time);
        drawRevenantIdentity(shapes, enemies, time);
        drawWardenIdentity(shapes, enemies, time);
        if (!settings.reduceFlashes && fxBudget.allowHeavyFx()) lights.draw(shapes, player, enemies, pools, time);
    }

    private void updateAndDrawSingularityImpacts(ShapeRenderer shapes, Pools pools, float time) {
        float dt = Float.isNaN(lastSingularityVisualTime) ? 0f : MathUtils.clamp(time - lastSingularityVisualTime, 0f, .05f);
        lastSingularityVisualTime = time;
        singularityImpacts.update(pools.projectiles, dt);
        int triggered = singularityImpacts.consumeTriggeredCount();
        if (triggered > 0) {
            AudioDirector.playGlobal(AudioDirector.Cue.SINGULARITY, .82f + Math.min(3, triggered) * .04f, 0f);
            vibrate(12);
            if (settings.hitStop) feel.triggerHitStop(.012f);
        }

        float flashScale = settings.reduceFlashes ? .48f : 1f;
        int segments = fxBudget.geometrySegments(42, 22);
        for (SingularityImpactTracker.Impact impact : singularityImpacts.impacts()) {
            float progress = impact.progress();
            float fade = 1f - progress;
            float wave = MathUtils.sin(progress * MathUtils.PI);
            float outer = MathUtils.lerp(.34f, 2.75f, progress);
            float inner = MathUtils.lerp(.72f, .08f, progress);

            shapes.setColor(.46f, .20f, 1f, (.18f + wave * .24f) * fade * flashScale);
            shapes.circle(impact.x, impact.y, outer, segments);
            shapes.setColor(.08f, .02f, .16f, (.52f + wave * .28f) * fade * flashScale);
            shapes.circle(impact.x, impact.y, Math.max(.05f, inner), segments);
            shapes.setColor(.86f, .72f, 1f, .46f * wave * flashScale);
            shapes.circle(impact.x, impact.y, Math.max(.04f, outer * .16f), segments);

            if (fxBudget.allowHeavyFx()) {
                int rays = fxBudget.allowExtraFx() ? 10 : 6;
                for (int i = 0; i < rays; i++) {
                    float angle = i * (360f / rays) + progress * 115f;
                    float from = outer * (1.16f + .12f * MathUtils.sinDeg(angle * 2f));
                    float to = outer * .34f;
                    float x1 = impact.x + MathUtils.cosDeg(angle) * from;
                    float y1 = impact.y + MathUtils.sinDeg(angle) * from;
                    float x2 = impact.x + MathUtils.cosDeg(angle) * to;
                    float y2 = impact.y + MathUtils.sinDeg(angle) * to;
                    shapes.setColor(.68f, .46f, 1f, .22f * fade * flashScale);
                    shapes.rectLine(x1, y1, x2, y2, .035f + wave * .018f);
                }
            }
        }
    }

    private void updateAndDrawHazards(ShapeRenderer shapes, Player player, Pools pools, float time) {
        float dt = Float.isNaN(lastHazardVisualTime) ? 0f : MathUtils.clamp(time - lastHazardVisualTime, 0f, .05f);
        lastHazardVisualTime = time;
        hazards.update(dt, player.position.x, player.position.y);

        float damage = hazards.consumePlayerDamage(player.position.x, player.position.y, player.radius);
        if (damage > 0f) {
            EnemyProjectile hit = pools.hostileProjectile();
            if (hit != null) {
                hit.spawn(player.position.x, player.position.y, 0f, 0f, damage,
                    Math.max(.18f, player.radius * .72f), .14f, false, 0f);
            }
        }

        for (ArenaHazardRuntime.Hazard hazard : hazards.hazards()) {
            boolean warning = hazard.phase() == ArenaHazardRuntime.Phase.WARNING;
            float flashScale = settings.reduceFlashes ? .55f : 1f;
            if (FoundryHazardPresentation.isFoundry(hazard.type())) {
                FoundryHazardPresentation.Profile profile = FoundryHazardPresentation.forType(hazard.type());
                if (hazard.consumeActivationCue()) {
                    float pitch = hazard.type() == ArenaHazardRuntime.Type.STEAM_JET ? 1.10f
                        : hazard.type() == ArenaHazardRuntime.Type.HEAT_LINE ? 1.02f : .88f;
                    AudioDirector.playGlobal(profile.cue, pitch, 0f);
                }
                drawFoundryHazard(shapes, hazard, profile, warning, time, flashScale);
                continue;
            }
            if (NullHazardPresentation.isNull(hazard.type())) {
                NullHazardPresentation.Profile profile = NullHazardPresentation.forType(hazard.type());
                if (hazard.consumeActivationCue()) {
                    float pitch = hazard.type() == ArenaHazardRuntime.Type.STATIC_BURST ? 1.16f
                        : hazard.type() == ArenaHazardRuntime.Type.NULL_BEAM ? .94f : .82f;
                    AudioDirector.playGlobal(profile.cue, pitch, 0f);
                }
                drawNullHazard(shapes, hazard, profile, warning, time, flashScale);
                continue;
            }

            float pulse = .5f + .5f * MathUtils.sin(time * (hazard.type() == ArenaHazardRuntime.Type.DEATH_BURST ? 18f : 12f));
            if (warning) {
                float urgency = 1f - hazard.warningFraction();
                float alpha = (.08f + urgency * .12f + pulse * .04f) * flashScale;
                if (hazard.type() == ArenaHazardRuntime.Type.DEATH_BURST) {
                    shapes.setColor(1f, .36f, .08f, alpha);
                } else {
                    shapes.setColor(1f, .08f, .05f, alpha);
                }
                shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (1f + pulse * .035f), 36);
                shapes.setColor(1f, .76f, .18f, (.10f + urgency * .18f) * flashScale);
                shapes.circle(hazard.x(), hazard.y(), Math.max(.12f, hazard.radius() * (.12f + urgency * .08f)), 20);
            } else {
                float alpha = (hazard.type() == ArenaHazardRuntime.Type.DEATH_BURST ? .34f : .42f) * flashScale;
                if (hazard.type() == ArenaHazardRuntime.Type.DEATH_BURST) {
                    shapes.setColor(1f, .30f, .04f, alpha);
                } else {
                    shapes.setColor(1f, .04f, .02f, alpha);
                }
                shapes.circle(hazard.x(), hazard.y(), hazard.radius(), 40);
                shapes.setColor(1f, .82f, .26f, .30f * flashScale);
                shapes.circle(hazard.x(), hazard.y(), hazard.radius() * .34f, 24);
            }
        }
    }

    private void drawFoundryHazard(ShapeRenderer shapes, ArenaHazardRuntime.Hazard hazard,
                                   FoundryHazardPresentation.Profile profile, boolean warning,
                                   float time, float flashScale) {
        float pulse = .5f + .5f * MathUtils.sin(time * profile.pulseSpeed);
        float urgency = warning ? 1f - hazard.warningFraction() : 1f;
        int segments = fxBudget.geometrySegments(40, 22);
        float r = warning ? profile.warningR : profile.activeR;
        float g = warning ? profile.warningG : profile.activeG;
        float b = warning ? profile.warningB : profile.activeB;
        float alpha = (warning ? .10f + urgency * .16f + pulse * .04f : .34f + pulse * .12f) * flashScale;
        float radius = hazard.radius() * (warning ? 1f + pulse * .035f : 1f);

        shapes.setColor(r, g, b, alpha);
        shapes.circle(hazard.x(), hazard.y(), radius, segments);

        switch (hazard.type()) {
            case LAVA_VENT -> {
                shapes.setColor(1f, .70f, .10f, (warning ? .16f + urgency * .24f : .48f) * flashScale);
                shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (.24f + pulse * .06f), segments / 2);
                int spokes = fxBudget.allowHeavyFx() ? profile.spokes : 4;
                for (int i = 0; i < spokes; i++) {
                    float angle = i * (360f / spokes) + pulse * 18f;
                    float inner = hazard.radius() * .26f;
                    float outer = hazard.radius() * (.68f + .12f * MathUtils.sinDeg(angle * 3f + time * 90f));
                    shapes.setColor(1f, .34f, .03f, (warning ? .20f : .52f) * flashScale);
                    shapes.rectLine(
                        hazard.x() + MathUtils.cosDeg(angle) * inner,
                        hazard.y() + MathUtils.sinDeg(angle) * inner,
                        hazard.x() + MathUtils.cosDeg(angle + 7f) * outer,
                        hazard.y() + MathUtils.sinDeg(angle + 7f) * outer,
                        warning ? .035f : .07f);
                }
            }
            case STEAM_JET -> {
                shapes.setColor(.92f, .97f, 1f, (warning ? .22f + urgency * .22f : .58f) * flashScale);
                shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (.18f + pulse * .05f), segments / 2);
                int jets = fxBudget.allowHeavyFx() ? profile.spokes : 3;
                for (int i = 0; i < jets; i++) {
                    float offset = (i - (jets - 1) * .5f) * hazard.radius() * .18f;
                    float length = hazard.radius() * (warning ? .58f + urgency * .20f : .95f + pulse * .16f);
                    shapes.setColor(.82f, .93f, 1f, (warning ? .16f : .38f) * flashScale);
                    shapes.rectLine(hazard.x() + offset, hazard.y() - hazard.radius() * .18f,
                        hazard.x() + offset * .72f, hazard.y() + length, warning ? .035f : .075f);
                }
            }
            case HEAT_LINE -> {
                float band = hazard.radius() * (warning ? .12f + urgency * .05f : .22f);
                shapes.setColor(1f, .80f, .18f, (warning ? .18f + urgency * .24f : .52f) * flashScale);
                shapes.rectLine(hazard.x() - hazard.radius() * .78f, hazard.y(),
                    hazard.x() + hazard.radius() * .78f, hazard.y(), band);
                shapes.setColor(1f, .32f, .04f, (warning ? .12f : .34f) * flashScale);
                shapes.rectLine(hazard.x(), hazard.y() - hazard.radius() * .78f,
                    hazard.x(), hazard.y() + hazard.radius() * .78f, band * .58f);
            }
            default -> { }
        }
    }

    private void drawNullHazard(ShapeRenderer shapes, ArenaHazardRuntime.Hazard hazard,
                                NullHazardPresentation.Profile profile, boolean warning,
                                float time, float flashScale) {
        float pulse = .5f + .5f * MathUtils.sin(time * profile.pulseSpeed);
        float urgency = warning ? 1f - hazard.warningFraction() : 1f;
        int segments = fxBudget.geometrySegments(42, 22);
        float r = warning ? profile.warningR : profile.activeR;
        float g = warning ? profile.warningG : profile.activeG;
        float b = warning ? profile.warningB : profile.activeB;
        float alpha = (warning ? .09f + urgency * .17f + pulse * .035f : .30f + pulse * .13f) * flashScale;
        float radius = hazard.radius() * (warning ? 1f + pulse * .045f : 1f);

        shapes.setColor(r, g, b, alpha);
        shapes.circle(hazard.x(), hazard.y(), radius, segments);

        switch (hazard.type()) {
            case VOID_RIFT -> {
                float core = hazard.radius() * (warning ? .20f + urgency * .08f : .34f - pulse * .08f);
                shapes.setColor(.04f, .01f, .12f, (warning ? .28f : .70f) * flashScale);
                shapes.circle(hazard.x(), hazard.y(), Math.max(.08f, core), segments / 2);
                int spokes = fxBudget.allowHeavyFx() ? profile.spokes : 5;
                for (int i = 0; i < spokes; i++) {
                    float angle = i * (360f / spokes) - time * 70f;
                    float outer = hazard.radius() * (.86f + pulse * .08f);
                    float inner = hazard.radius() * .30f;
                    shapes.setColor(.58f, .30f, 1f, (warning ? .16f : .38f) * flashScale);
                    shapes.rectLine(
                        hazard.x() + MathUtils.cosDeg(angle) * outer,
                        hazard.y() + MathUtils.sinDeg(angle) * outer,
                        hazard.x() + MathUtils.cosDeg(angle + 18f) * inner,
                        hazard.y() + MathUtils.sinDeg(angle + 18f) * inner,
                        warning ? .028f : .055f);
                }
            }
            case STATIC_BURST -> {
                int spokes = fxBudget.allowHeavyFx() ? profile.spokes : 4;
                shapes.setColor(.86f, .98f, 1f, (warning ? .24f + urgency * .20f : .62f) * flashScale);
                shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (.16f + pulse * .07f), segments / 2);
                for (int i = 0; i < spokes; i++) {
                    float angle = i * (360f / spokes) + time * 95f;
                    float inner = hazard.radius() * .18f;
                    float outer = hazard.radius() * (.72f + pulse * .18f);
                    shapes.setColor(.20f, .78f, 1f, (warning ? .20f : .54f) * flashScale);
                    shapes.rectLine(
                        hazard.x() + MathUtils.cosDeg(angle) * inner,
                        hazard.y() + MathUtils.sinDeg(angle) * inner,
                        hazard.x() + MathUtils.cosDeg(angle + (i % 2 == 0 ? 8f : -8f)) * outer,
                        hazard.y() + MathUtils.sinDeg(angle + (i % 2 == 0 ? 8f : -8f)) * outer,
                        warning ? .03f : .065f);
                }
            }
            case NULL_BEAM -> {
                float band = hazard.radius() * (warning ? .10f + urgency * .05f : .20f);
                shapes.setColor(.80f, .62f, 1f, (warning ? .20f + urgency * .22f : .52f) * flashScale);
                shapes.rectLine(hazard.x() - hazard.radius() * .84f, hazard.y(),
                    hazard.x() + hazard.radius() * .84f, hazard.y(), band);
                shapes.setColor(.34f, .84f, 1f, (warning ? .12f : .38f) * flashScale);
                shapes.rectLine(hazard.x(), hazard.y() - hazard.radius() * .84f,
                    hazard.x(), hazard.y() + hazard.radius() * .84f, band * .52f);
                if (fxBudget.allowHeavyFx()) {
                    shapes.setColor(.94f, .86f, 1f, (warning ? .14f : .34f) * flashScale);
                    shapes.circle(hazard.x(), hazard.y(), hazard.radius() * (.28f + pulse * .05f), segments / 2);
                }
            }
            default -> { }
        }
    }

    private void drawBossPhaseTransitions(ShapeRenderer shapes, Array<Enemy> enemies, float time) {
        for (Enemy enemy : enemies) {
            if (!enemy.alive || enemy.type != Enemy.Type.BOSS || enemy.bossPhases == null || enemy.bossCombat == null) continue;
            if (!enemy.bossPhases.consumePhaseChanged()) continue;
            phaseBoss = enemy;
            phaseFxPhase = enemy.bossPhases.phase();
            BossPhaseTransitionProfile.Spec spec = BossPhaseTransitionProfile.forPhase(enemy.bossCombat.identity(), phaseFxPhase);
            phaseFxStarted = time;
            phaseFxUntil = time + spec.duration();
            AudioDirector.playGlobal(AudioDirector.Cue.BOSS_PHASE, spec.audioPitch(), 0f);
            vibrate(spec.vibrationMs());
            if (settings.hitStop) feel.triggerHitStop(phaseFxPhase >= 3 ? .045f : .030f);
        }

        if (phaseBoss == null || !phaseBoss.alive || time >= phaseFxUntil || phaseBoss.bossCombat == null) {
            phaseBoss = null;
            return;
        }

        BossIdentity identity = phaseBoss.bossCombat.identity();
        BossPhaseTransitionProfile.Spec spec = BossPhaseTransitionProfile.forPhase(identity, phaseFxPhase);
        float progress = MathUtils.clamp((time - phaseFxStarted) / Math.max(.001f, spec.duration()), 0f, 1f);
        float fade = 1f - progress;
        float radius = phaseBoss.radius * MathUtils.lerp(1.08f, spec.radiusMultiplier(), progress);
        float r = identity == BossIdentity.REVENANT ? .82f : identity == BossIdentity.WARDEN ? .22f : 1f;
        float g = identity == BossIdentity.REVENANT ? .18f : identity == BossIdentity.WARDEN ? .68f : .22f;
        float b = identity == BossIdentity.REVENANT ? 1f : identity == BossIdentity.WARDEN ? 1f : .12f;
        float alphaScale = settings.reduceFlashes ? .42f : 1f;

        shapes.setColor(r, g, b, (.28f + .20f * fade) * fade * alphaScale);
        shapes.circle(phaseBoss.position.x, phaseBoss.position.y, radius, 36);
        shapes.setColor(r, g, b, .14f * fade * alphaScale);
        shapes.circle(phaseBoss.position.x, phaseBoss.position.y, radius * 1.34f, 38);
        if (!settings.reduceFlashes && fxBudget.allowHeavyFx()) {
            shapes.setColor(1f, 1f, 1f, .20f * fade);
            shapes.circle(phaseBoss.position.x, phaseBoss.position.y, phaseBoss.radius * (.42f + .34f * fade), 20);
        }
    }

    private void drawLeaperTelegraphs(ShapeRenderer shapes, Array<Enemy> enemies, float time) {
        for (Enemy enemy : enemies) {
            if (!enemy.alive || !leapers.contains(enemy)) continue;
            if (leapers.telegraphing(enemy)) {
                float pulse = .84f + MathUtils.sin(time * 22f) * .16f;
                float radius = enemy.radius * (2.15f + .32f * pulse);
                shapes.setColor(1f, .28f, .08f, .18f + .10f * pulse);
                shapes.circle(enemy.position.x, enemy.position.y, radius, 22);
                shapes.setColor(1f, .78f, .18f, .72f);
                shapes.circle(enemy.position.x, enemy.position.y, Math.max(.08f, enemy.radius * .28f), 12);
            } else if (fxBudget.allowHeavyFx()) {
                shapes.setColor(1f, .45f, .12f, .10f);
                shapes.circle(enemy.position.x, enemy.position.y, enemy.radius * 1.32f, 16);
            }
        }
    }

    private void drawRevenantIdentity(ShapeRenderer shapes, Array<Enemy> enemies, float time) {
        for (Enemy enemy : enemies) {
            if (!enemy.alive || enemy.type != Enemy.Type.BOSS || enemy.bossCombat == null || !enemy.bossCombat.revenant()) continue;
            float pulse = .86f + MathUtils.sin(time * (enemy.bossCombat.charging() ? 18f : 8f)) * .14f;
            float radius = enemy.radius * (1.72f + pulse * .24f);
            shapes.setColor(.68f, .10f, .95f, .13f + .08f * pulse);
            shapes.circle(enemy.position.x, enemy.position.y, radius, 30);
            if (fxBudget.allowHeavyFx()) {
                shapes.setColor(1f, .10f, .22f, .16f + .08f * pulse);
                shapes.circle(enemy.position.x, enemy.position.y, enemy.radius * (1.28f + pulse * .12f), 26);
            }
        }
    }

    private void drawWardenIdentity(ShapeRenderer shapes, Array<Enemy> enemies, float time) {
        for (Enemy enemy : enemies) {
            if (!enemy.alive || enemy.type != Enemy.Type.BOSS || enemy.bossCombat == null || !enemy.bossCombat.warden()) continue;
            float pulse = .90f + MathUtils.sin(time * (enemy.bossCombat.charging() ? 9f : 4.5f)) * .10f;
            float outer = enemy.radius * (2.02f + pulse * .18f);
            shapes.setColor(.10f, .55f, .82f, .12f + .07f * pulse);
            shapes.circle(enemy.position.x, enemy.position.y, outer, 32);
            shapes.setColor(.96f, .62f, .12f, .18f + .07f * pulse);
            shapes.circle(enemy.position.x, enemy.position.y, enemy.radius * (1.46f + pulse * .08f), 28);
            if (fxBudget.allowHeavyFx()) {
                float ring = enemy.radius * (2.32f + MathUtils.sin(time * 3.2f) * .08f);
                shapes.setColor(.75f, .88f, 1f, .07f);
                shapes.circle(enemy.position.x, enemy.position.y, ring, 34);
            }
        }
    }

    public void drawAuthoredDeaths(SpriteBatch batch, Pools pools) {
        currentPools = pools;
        deaths.drawAuthored(batch, pools.deathFx);
    }

    public float fxQuality() { return fxBudget.quality(); }
}
