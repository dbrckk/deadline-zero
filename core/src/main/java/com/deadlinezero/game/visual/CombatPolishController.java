package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.config.AccessibilitySettings;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.fx.DeathFx;
import com.deadlinezero.game.util.Pools;

/**
 * Centralized presentation-only combat feedback: micro hit-stop, recoil, local lighting,
 * persistent death marks and corpse rendering. Keeps tuning out of GameScreen.
 */
public final class CombatPolishController {
    private final CombatFeel feel = new CombatFeel();
    private final LocalLightRenderer lights = new LocalLightRenderer();
    private final DeathFxRenderer deaths;
    private final AccessibilitySettings settings;

    public CombatPolishController(GameArt art, AccessibilitySettings settings) {
        deaths = new DeathFxRenderer(art);
        this.settings = settings == null ? new AccessibilitySettings() : settings;
    }

    public float simulationScale(float visualDelta) {
        return settings.hitStop ? feel.consumeSimulationScale(visualDelta) : 1f;
    }

    public void updateVisual(float dt) {
        feel.update(dt);
    }

    public void updateSimulation(float dt, Pools pools) {
        for (DeathFx fx : pools.deathFx) fx.update(dt);
    }

    public void onShot(float angleDeg) {
        if (settings.screenShake && settings.screenShakeStrength > 0f) {
            feel.triggerRecoil(angleDeg, .075f * settings.screenShakeStrength);
        }
    }

    public void onProjectileHit(boolean critical) {
        if (settings.hitStop && critical) feel.triggerHitStop(.014f);
    }

    public void onEnemyKilled(Enemy enemy, Pools pools) {
        DeathFx fx = pools.deathFx();
        if (fx != null) {
            float duration = enemy.type == Enemy.Type.BOSS ? 26f : 13f;
            float rotation = enemy.velocity.len2() > .001f ? enemy.velocity.angleDeg() - 90f : MathUtils.random(0f, 360f);
            fx.spawn(enemy.type, enemy.position.x, enemy.position.y, rotation, enemy.radius, duration);
        }
        if (!settings.hitStop) return;
        if (enemy.type == Enemy.Type.BOSS) {
            feel.triggerHitStop(.065f);
        } else if (enemy.type == Enemy.Type.ELITE || enemy.type == Enemy.Type.BRUTE) {
            feel.triggerHitStop(.030f);
        } else {
            feel.triggerHitStop(.018f);
        }
    }

    public void applyCameraRecoil(OrthographicCamera camera) {
        if (!settings.screenShake || settings.screenShakeStrength <= 0f) return;
        camera.position.x += feel.recoilX();
        camera.position.y += feel.recoilY();
    }

    public void drawWorldUnderlay(ShapeRenderer shapes, Player player, Array<Enemy> enemies, Pools pools, float time) {
        deaths.drawFallback(shapes, pools.deathFx);
        if (!settings.reduceFlashes) lights.draw(shapes, player, enemies, pools, time);
    }

    public void drawAuthoredDeaths(SpriteBatch batch, Pools pools) {
        deaths.drawAuthored(batch, pools.deathFx);
    }
}
