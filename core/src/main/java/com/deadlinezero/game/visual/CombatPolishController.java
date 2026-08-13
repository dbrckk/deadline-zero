package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
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

    public CombatPolishController(GameArt art) {
        deaths = new DeathFxRenderer(art);
    }

    public float simulationScale(float visualDelta) {
        return feel.consumeSimulationScale(visualDelta);
    }

    public void updateVisual(float dt) {
        feel.update(dt);
    }

    public void updateSimulation(float dt, Pools pools) {
        for (DeathFx fx : pools.deaths) fx.update(dt);
    }

    public void onShot(float angleDeg) {
        feel.triggerRecoil(angleDeg, .075f);
    }

    public void onProjectileHit(boolean critical) {
        if (critical) feel.triggerHitStop(.014f);
    }

    public void onEnemyKilled(Enemy enemy, Pools pools) {
        DeathFx fx = pools.deathFx();
        if (fx != null) {
            float duration = enemy.type == Enemy.Type.BOSS ? 26f : 13f;
            float rotation = enemy.velocity.len2() > .001f ? enemy.velocity.angleDeg() - 90f : MathUtils.random(0f, 360f);
            fx.spawn(enemy.type, enemy.position.x, enemy.position.y, rotation, enemy.radius, duration);
        }
        if (enemy.type == Enemy.Type.BOSS) {
            feel.triggerHitStop(.065f);
        } else if (enemy.type == Enemy.Type.ELITE || enemy.type == Enemy.Type.BRUTE) {
            feel.triggerHitStop(.030f);
        } else {
            feel.triggerHitStop(.018f);
        }
    }

    public void applyCameraRecoil(OrthographicCamera camera) {
        camera.position.x += feel.recoilX();
        camera.position.y += feel.recoilY();
    }

    public void drawWorldUnderlay(ShapeRenderer shapes, Player player, Array<Enemy> enemies, Pools pools, float time) {
        deaths.drawFallback(shapes, pools.deaths);
        lights.draw(shapes, player, enemies, pools, time);
    }

    public void drawAuthoredDeaths(SpriteBatch batch, Pools pools) {
        deaths.drawAuthored(batch, pools.deaths);
    }
}
