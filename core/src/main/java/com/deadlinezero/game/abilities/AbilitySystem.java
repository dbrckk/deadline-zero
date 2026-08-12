package com.deadlinezero.game.abilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.HomingMissile;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.fx.DamageNumber;
import com.deadlinezero.game.fx.ImpactFx;
import com.deadlinezero.game.util.Pools;

/** Executes passive player abilities without allocating during the frame loop. */
public final class AbilitySystem {
    public interface Listener { void onKilled(Enemy enemy); }

    private final Player player;
    private final Array<Enemy> enemies;
    private final Pools pools;
    private final Listener listener;
    private final AbilityRuntime runtime = new AbilityRuntime();

    public AbilitySystem(Player player, Array<Enemy> enemies, Pools pools, Listener listener) {
        this.player = player;
        this.enemies = enemies;
        this.pools = pools;
        this.listener = listener;
    }

    public AbilityRuntime runtime() { return runtime; }

    public void update(float dt) {
        runtime.update(dt);
        player.updateRuntime(dt);
        updateHomingMissiles(dt);
        updateTesla();
        updateMissiles();
        updateCryo();
        updateDrone();
        updateOrbital();
    }

    private void updateTesla() {
        int level = player.abilities.level(AbilityType.TESLA_ORB);
        if (level <= 0 || !runtime.readyTesla()) return;
        Enemy target = nearest(player.position.x, player.position.y, 10f, null);
        if (target == null) return;

        float damage = 18f + level * 9f;
        int chains = 1 + level;
        if (player.abilities.hasTeslaEvolution()) {
            damage *= 1.7f;
            chains += 3;
        }
        Enemy current = target;
        Enemy previous = null;
        for (int i = 0; i < chains && current != null; i++) {
            damageEnemy(current, damage, DamageElement.SHOCK, Color.CYAN, .65f);
            previous = current;
            current = nearest(previous.position.x, previous.position.y, 4.2f, previous);
            damage *= .82f;
        }
        runtime.resetTesla(level);
    }

    private void updateMissiles() {
        int level = player.abilities.level(AbilityType.MISSILE_SWARM);
        if (level <= 0 || !runtime.readyMissile()) return;

        int count = 1 + level;
        float radius = 1.6f + level * .18f;
        float damage = 24f + level * 13f;
        boolean cryoEvolution = player.abilities.hasCryoMissileEvolution();
        if (cryoEvolution) {
            radius *= 1.45f;
            damage *= 1.35f;
        }

        for (int i = 0; i < count; i++) {
            Enemy target = nearest(player.position.x, player.position.y, 18f, null);
            if (target == null) break;
            HomingMissile missile = pools.homingMissile();
            if (missile == null) break;
            missile.spawn(player.position.x, player.position.y, target,
                8.5f + level * .45f, 230f + level * 22f, damage, radius, 4.2f,
                cryoEvolution ? DamageElement.FROST : DamageElement.KINETIC);
        }
        runtime.resetMissile(level);
    }

    private void updateHomingMissiles(float dt) {
        for (HomingMissile missile : pools.homingMissiles) {
            if (!missile.active) continue;
            if (missile.target == null || !missile.target.alive) {
                missile.target = nearest(missile.position.x, missile.position.y, 12f, null);
                if (missile.target == null) {
                    missile.update(dt);
                    continue;
                }
            }

            missile.update(dt);
            if (!missile.active) continue;
            Enemy target = missile.target;
            if (target == null || !target.alive) continue;
            float rr = missile.radius + target.radius;
            float dx = missile.position.x - target.position.x;
            float dy = missile.position.y - target.position.y;
            if (dx * dx + dy * dy > rr * rr) continue;

            missile.active = false;
            explode(missile.position.x, missile.position.y, missile.explosionRadius,
                missile.damage, missile.element);
        }
    }

    private void updateCryo() {
        int level = player.abilities.level(AbilityType.CRYO_NOVA);
        if (level <= 0 || !runtime.readyCryo()) return;
        float radius = 3.5f + level * .55f;
        float damage = 8f + level * 5f;
        impact(player.position.x, player.position.y, radius, .36f, new Color(.25f, .8f, 1f, 1f));
        float r2 = radius * radius;
        for (Enemy e : enemies) {
            if (!e.alive || e.position.dst2(player.position) > r2) continue;
            damageEnemy(e, damage, DamageElement.FROST, new Color(.55f, .9f, 1f, 1f), .35f);
        }
        runtime.resetCryo(level);
    }

    private void updateDrone() {
        int level = player.abilities.level(AbilityType.DRONE);
        if (level <= 0 || !runtime.readyDrone()) return;
        float angle = runtime.orbitalAngle + 180f;
        float x = player.position.x + MathUtils.cosDeg(angle) * 1.8f;
        float y = player.position.y + MathUtils.sinDeg(angle) * 1.8f;
        Enemy target = nearest(x, y, 11f, null);
        if (target != null) {
            float damage = 12f + level * 7f;
            DamageElement element = player.abilities.hasTeslaEvolution() ? DamageElement.SHOCK : DamageElement.KINETIC;
            damageEnemy(target, damage, element, element == DamageElement.SHOCK ? Color.CYAN : Color.LIME, .42f);
            impact(x, y, .25f, .08f, Color.LIME);
        }
        runtime.resetDrone(level);
    }

    private void updateOrbital() {
        int level = player.abilities.level(AbilityType.ORBITAL_BLADE);
        if (level <= 0 || !runtime.readyOrbital()) return;
        float orbit = 2.0f + level * .12f;
        float x = player.position.x + MathUtils.cosDeg(runtime.orbitalAngle) * orbit;
        float y = player.position.y + MathUtils.sinDeg(runtime.orbitalAngle) * orbit;
        float radius = .55f + level * .05f;
        float r2 = radius * radius;
        float damage = 10f + level * 5f;
        for (Enemy e : enemies) {
            if (!e.alive) continue;
            float dx = e.position.x - x;
            float dy = e.position.y - y;
            if (dx * dx + dy * dy > r2) continue;
            damageEnemy(e, damage, DamageElement.KINETIC, Color.GOLD, .28f);
        }
        runtime.resetOrbital(level);
    }

    private void explode(float x, float y, float radius, float damage, DamageElement element) {
        impact(x, y, radius, .28f, element == DamageElement.FROST ? new Color(.35f, .8f, 1f, 1f) : Color.ORANGE);
        float r2 = radius * radius;
        for (Enemy e : enemies) {
            if (!e.alive) continue;
            float dx = e.position.x - x;
            float dy = e.position.y - y;
            if (dx * dx + dy * dy > r2) continue;
            damageEnemy(e, damage, element, element == DamageElement.FROST ? Color.CYAN : Color.ORANGE, .45f);
        }
    }

    private Enemy nearest(float x, float y, float range, Enemy exclude) {
        Enemy best = null;
        float bestD2 = range * range;
        for (Enemy e : enemies) {
            if (!e.alive || e == exclude) continue;
            float dx = e.position.x - x;
            float dy = e.position.y - y;
            float d2 = dx * dx + dy * dy;
            if (d2 < bestD2) { bestD2 = d2; best = e; }
        }
        return best;
    }

    private void damageEnemy(Enemy e, float damage, DamageElement element, Color color, float fxSize) {
        if (!e.alive) return;
        boolean wasAlive = e.alive;
        e.damage(damage);
        e.hitFlash = 1f;
        e.applyElement(element, damage);
        DamageNumber n = pools.damageNumber();
        if (n != null) n.spawn(e.position.x, e.position.y + e.radius, damage, false, color);
        impact(e.position.x, e.position.y, fxSize, .14f, color);
        if (wasAlive && !e.alive) listener.onKilled(e);
    }

    private void impact(float x, float y, float size, float duration, Color color) {
        ImpactFx fx = pools.impact();
        if (fx != null) fx.spawn(x, y, size, duration, color);
    }
}
