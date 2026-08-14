package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.fx.DeathFx;
import com.deadlinezero.game.fx.ImpactFx;
import com.deadlinezero.game.util.Pools;

/** Optional atlas-driven VFX overlay. Missing authored FX simply leave procedural effects visible. */
public final class AuthoredVfxRenderer {
    private final GameArt art;

    public AuthoredVfxRenderer(GameArt art) { this.art = art; }

    public void draw(SpriteBatch batch, Player player, Iterable<Enemy> enemies, Pools pools) {
        if (!art.authoredAvailable()) return;
        batch.begin();
        drawMuzzle(batch, player, enemies);
        drawDash(batch, player);
        drawLevelUp(batch, player);
        drawLegendary(batch, player);
        drawImpacts(batch, pools);
        drawBossDeath(batch, pools);
        batch.end();
    }

    private void drawMuzzle(SpriteBatch batch, Player player, Iterable<Enemy> enemies) {
        float age = CombatVisualEvents.playerShotAgeSeconds();
        if (age > .10f || !player.alive) return;
        TextureRegion region = art.effectOrNull("muzzle_fire", age, .025f);
        if (region == null) return;
        Enemy target = nearest(player, enemies);
        float angle = target == null ? player.velocity.angleDeg() : MathUtils.atan2(
            target.position.y - player.position.y, target.position.x - player.position.x) * MathUtils.radiansToDegrees;
        float w = .95f;
        float h = w * region.getRegionHeight() / (float)Math.max(1, region.getRegionWidth());
        float r = angle * MathUtils.degreesToRadians;
        float x = player.position.x + MathUtils.cos(r) * .72f;
        float y = player.position.y + MathUtils.sin(r) * .72f;
        batch.draw(region, x - w * .15f, y - h * .5f, w * .15f, h * .5f, w, h, 1f, 1f, angle);
    }

    private void drawDash(SpriteBatch batch, Player player) {
        float age = CombatVisualEvents.dashAgeSeconds();
        if (age > .24f) return;
        TextureRegion region = art.effectOrNull("dash", age, .04f);
        if (region == null) return;
        float size = 2.1f;
        batch.setColor(1f, 1f, 1f, MathUtils.clamp(1f - age / .24f, 0f, 1f));
        batch.draw(region, player.position.x - size * .5f, player.position.y - size * .5f, size, size);
        batch.setColor(Color.WHITE);
    }

    private void drawLevelUp(SpriteBatch batch, Player player) {
        float age = CombatVisualEvents.levelUpAgeSeconds();
        if (age > .75f) return;
        TextureRegion region = art.effectOrNull("level_up", age, .06f);
        if (region == null) return;
        float size = 2.8f + age * 1.1f;
        batch.draw(region, player.position.x - size * .5f, player.position.y - size * .5f, size, size);
    }

    private void drawLegendary(SpriteBatch batch, Player player) {
        if (!player.alive || !player.legendary.hasAny()) return;
        float stateTime = CombatVisualEvents.levelUpAgeSeconds();
        if (!Float.isFinite(stateTime)) stateTime = 0f;
        if (player.legendary.hasOverdrive()) drawLegendaryLayer(batch, player, "legendary_overdrive", stateTime, 3.35f, .72f);
        if (player.legendary.hasSingularity()) drawLegendaryLayer(batch, player, "legendary_singularity", stateTime, 3.75f, .64f);
        if (player.legendary.hasApex()) drawLegendaryLayer(batch, player, "legendary_apex", stateTime, 4.15f, .58f);
        batch.setColor(Color.WHITE);
    }

    private void drawLegendaryLayer(SpriteBatch batch, Player player, String effect, float stateTime, float size, float alpha) {
        TextureRegion region = art.effectOrNull(effect, stateTime, .075f);
        if (region == null) return;
        float pulse = 1f + MathUtils.sin(stateTime * 4.6f) * .045f;
        float drawSize = size * pulse;
        batch.setColor(1f, 1f, 1f, alpha);
        batch.draw(region, player.position.x - drawSize * .5f, player.position.y - drawSize * .5f, drawSize, drawSize);
    }

    private void drawImpacts(SpriteBatch batch, Pools pools) {
        for (ImpactFx fx : pools.impacts) {
            if (!fx.active) continue;
            float age = Math.max(0f, fx.maxLife - fx.life);
            String name = classify(fx.color);
            TextureRegion region = art.effectOrNull(name, age, .035f);
            if (region == null) continue;
            float size = Math.max(.5f, fx.size * 2.15f);
            batch.setColor(1f, 1f, 1f, MathUtils.clamp(fx.life / Math.max(.001f, fx.maxLife), 0f, 1f));
            batch.draw(region, fx.position.x - size * .5f, fx.position.y - size * .5f, size, size);
        }
        batch.setColor(Color.WHITE);
    }

    private void drawBossDeath(SpriteBatch batch, Pools pools) {
        for (DeathFx fx : pools.deathFx) {
            if (!fx.active || fx.type != Enemy.Type.BOSS || fx.age > 1.2f) continue;
            TextureRegion region = art.effectOrNull("boss_explosion", fx.age, .055f);
            if (region == null) continue;
            float size = 6.2f + fx.age * 1.6f;
            batch.draw(region, fx.x - size * .5f, fx.y - size * .5f, size, size);
        }
    }

    private String classify(Color c) {
        if (c.b > .70f && c.r < .55f) return "impact_frost";
        if (c.r > .70f && c.b > .55f) return "impact_shock";
        if (c.r > .75f && c.g < .35f) return "impact_fire";
        if (c.g > .55f && c.r < .55f) return "impact_kill";
        return "impact_energy";
    }

    private Enemy nearest(Player player, Iterable<Enemy> enemies) {
        Enemy best = null;
        float bestD2 = Float.MAX_VALUE;
        for (Enemy enemy : enemies) {
            if (!enemy.alive) continue;
            float d2 = player.position.dst2(enemy.position);
            if (d2 < bestD2) { bestD2 = d2; best = enemy; }
        }
        return best;
    }
}
