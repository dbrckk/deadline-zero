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
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.fx.DeathFx;
import com.deadlinezero.game.util.Pools;

/**
 * Centralized presentation-only combat feedback: micro hit-stop, recoil, local lighting,
 * persistent death marks, corpses, resilient event-driven audio and accessible haptics.
 */
public final class CombatPolishController {
    private static Pools currentPools;
    private final CombatFeel feel = new CombatFeel();
    private final LocalLightRenderer lights = new LocalLightRenderer();
    private final LegendaryFxRenderer legendaryFx = new LegendaryFxRenderer();
    private final LeaperRuntime leapers = LeaperSharedRuntime.get();
    private final DeathFxRenderer deaths;
    private final AccessibilitySettings settings;
    private final AdaptiveFxBudget fxBudget = new AdaptiveFxBudget();
    private Enemy phaseBoss;
    private float phaseFxStarted;
    private float phaseFxUntil;
    private int phaseFxPhase;

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
        deaths.drawFallback(shapes, pools.deathFx);
        legendaryFx.render(shapes, player, time, fxBudget.quality());
        drawLeaperTelegraphs(shapes, enemies, time);
        drawBossPhaseTransitions(shapes, enemies, time);
        drawRevenantIdentity(shapes, enemies, time);
        drawWardenIdentity(shapes, enemies, time);
        if (!settings.reduceFlashes && fxBudget.allowHeavyFx()) lights.draw(shapes, player, enemies, pools, time);
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
            AudioDirector.playGlobal(AudioDirector.Cue.BOSS_HIT, spec.audioPitch(), 0f);
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
