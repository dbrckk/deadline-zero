package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
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
    private final DeathFxRenderer deaths;
    private final AccessibilitySettings settings;
    private final AdaptiveFxBudget fxBudget = new AdaptiveFxBudget();

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
        if (!settings.reduceFlashes && fxBudget.allowHeavyFx()) lights.draw(shapes, player, enemies, pools, time);
    }

    public void drawAuthoredDeaths(SpriteBatch batch, Pools pools) {
        currentPools = pools;
        deaths.drawAuthored(batch, pools.deathFx);
    }

    public float fxQuality() { return fxBudget.quality(); }
}
