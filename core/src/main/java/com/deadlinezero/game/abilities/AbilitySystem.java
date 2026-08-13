package com.deadlinezero.game.abilities;

import java.util.WeakHashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.ai.LeaperRuntime;
import com.deadlinezero.game.ai.LeaperSharedRuntime;
import com.deadlinezero.game.combat.DamageElement;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.HomingMissile;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.fx.ArcFx;
import com.deadlinezero.game.fx.DamageNumber;
import com.deadlinezero.game.fx.ImpactFx;
import com.deadlinezero.game.meta.RunLoadoutContext;
import com.deadlinezero.game.meta.RunMissionRuntime;
import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.StageMissionRules;
import com.deadlinezero.game.util.Pools;
import com.deadlinezero.game.world.LeaperSpawnRules;
import com.deadlinezero.game.world.WaveDirector;

/** Executes passive player abilities without allocating during the frame loop. */
public final class AbilitySystem {
    public interface Listener { void onKilled(Enemy enemy); }

    private final Player player;
    private final Array<Enemy> enemies;
    private final Pools pools;
    private final Listener listener;
    private final AbilityRuntime runtime = new AbilityRuntime();
    private final LeaperRuntime leapers = LeaperSharedRuntime.get();
    private final WeakHashMap<Enemy, Boolean> leaperDecisions = new WeakHashMap<>();
    private final float abilityPower;

    public AbilitySystem(Player player, Array<Enemy> enemies, Pools pools, Listener listener) {
        this.player = player;
        this.enemies = enemies;
        this.pools = pools;
        this.listener = listener;
        this.abilityPower = RunLoadoutContext.abilityPowerMultiplier();
    }

    public AbilityRuntime runtime() { return runtime; }

    public void update(float dt) {
        runtime.update(dt);
        updateLeapers(dt);
        updateHomingMissiles(dt);
        updateTesla();
        updateMissiles();
        updateCryo();
        updateDrone();
        updateOrbital();
    }

    private void updateLeapers(float dt) {
        int stage = RunStageContext.stage();
        float arrival = Math.max(1f, StageMissionRules.bossArrivalSeconds(stage));
        float progress = MathUtils.clamp(RunMissionRuntime.elapsed() / arrival, 0f, 1f);
        WaveDirector.PressureBand band = progress < .24f ? WaveDirector.PressureBand.OPENING
            : progress < .52f ? WaveDirector.PressureBand.BUILD
            : progress < .80f ? WaveDirector.PressureBand.ASSAULT
            : WaveDirector.PressureBand.CRISIS;
        float chance = LeaperSpawnRules.share(stage, band);

        for (Enemy e : enemies) {
            if (!e.alive || e.type != Enemy.Type.RUNNER) continue;
            Boolean selected = leaperDecisions.get(e);
            if (selected == null) {
                selected = MathUtils.random() < chance;
                leaperDecisions.put(e, selected);
                if (selected) leapers.register(e);
            }
            if (!selected) continue;

            float dx = player.position.x - e.position.x;
            float dy = player.position.y - e.position.y;
            float distance2 = dx * dx + dy * dy;
            float distance = (float)Math.sqrt(Math.max(.0001f, distance2));
            leapers.update(e, dt, distance, dx / distance, dy / distance);
        }
    }

    private void updateTesla() {
        int level = player.abilities.level(AbilityType.TESLA_ORB);
        if (level <= 0 || !runtime.readyTesla()) return;
        Enemy target = nearest(player.position.x, player.position.y, 10f, null);
        if (target == null) return;

        int tier = player.abilities.tier(AbilityType.TESLA_ORB);
        float damage = (18f + level * 9f) * abilityPower;
        int chains = 1 + level;
        if (tier >= 2) {
            damage *= 1.12f;
            chains += 1;
        }
        if (player.abilities.evolved(AbilityType.TESLA_ORB)) {
            damage *= 1.22f;
            chains += 2;
        }
        if (player.abilities.hasTeslaEvolution()) {
            damage *= 1.45f;
            chains += 2;
        }

        boolean superconductor = player.abilities.hasSuperconductorSynergy();
        float originX = player.position.x;
        float originY = player.position.y;
        Enemy current = target;
        Enemy previous = null;
        for (int i = 0; i < chains && current != null; i++) {
            arc(originX, originY, current.position.x, current.position.y, .13f);
            if (superconductor && current.alive) current.applyElement(DamageElement.FROST, damage * .18f);
            damageEnemy(current, damage, DamageElement.SHOCK, Color.CYAN, .65f);
            previous = current;
            originX = previous.position.x;
            originY = previous.position.y;
            current = nearest(previous.position.x, previous.position.y, tier >= 3 ? 5.0f : 4.2f, previous);
            damage *= tier >= 3 ? .86f : .82f;
        }
        runtime.resetTesla(level);
    }

    private void updateMissiles() {
        int level = player.abilities.level(AbilityType.MISSILE_SWARM);
        if (level <= 0 || !runtime.readyMissile()) return;

        int tier = player.abilities.tier(AbilityType.MISSILE_SWARM);
        int count = 1 + level;
        float radius = 1.6f + level * .18f;
        float damage = (24f + level * 13f) * abilityPower;
        if (tier >= 2) count += 1;
        if (tier >= 3) {
            radius *= 1.20f;
            damage *= 1.18f;
        }

        boolean cryoEvolution = player.abilities.hasCryoMissileEvolution();
        if (cryoEvolution) {
            radius *= 1.45f;
            damage *= 1.35f;
        }
        if (player.abilities.hasTargetNetworkSynergy()) {
            count += 2;
            damage *= 1.12f;
        }

        DamageElement payload = cryoEvolution
            ? DamageElement.FROST
            : (tier >= 3 && player.weapon.element == DamageElement.FIRE ? DamageElement.FIRE : DamageElement.KINETIC);

        for (int i = 0; i < count; i++) {
            Enemy target = nearest(player.position.x, player.position.y, 18f, null);
            if (target == null) break;
            HomingMissile missile = pools.homingMissile();
            if (missile == null) break;
            missile.spawn(player.position.x, player.position.y, target,
                8.5f + level * .45f, 230f + level * 22f, damage, radius, 4.2f, payload);
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
        int tier = player.abilities.tier(AbilityType.CRYO_NOVA);
        float radius = 3.5f + level * .55f;
        float damage = (8f + level * 5f) * abilityPower;
        if (tier >= 2) radius *= 1.12f;
        if (tier >= 3) {
            radius *= 1.08f;
            damage *= 1.35f;
        }
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
        int tier = player.abilities.tier(AbilityType.DRONE);
        float angle = runtime.orbitalAngle + 180f;
        float x = player.position.x + MathUtils.cosDeg(angle) * 1.8f;
        float y = player.position.y + MathUtils.sinDeg(angle) * 1.8f;
        Enemy target = nearest(x, y, tier >= 2 ? 12.5f : 11f, null);
        if (target != null) {
            float damage = (12f + level * 7f) * abilityPower;
            if (tier >= 2) damage *= 1.10f;
            if (tier >= 3) damage *= 1.25f;
            DamageElement element = player.abilities.hasTeslaEvolution() ? DamageElement.SHOCK : DamageElement.KINETIC;
            if (element == DamageElement.SHOCK) arc(x, y, target.position.x, target.position.y, .10f);
            damageEnemy(target, damage, element, element == DamageElement.SHOCK ? Color.CYAN : Color.LIME, .42f);

            if (player.abilities.hasTargetNetworkSynergy()) {
                Enemy second = nearest(x, y, 10f, target);
                if (second != null) {
                    arc(x, y, second.position.x, second.position.y, .08f);
                    damageEnemy(second, damage * .62f, DamageElement.KINETIC, Color.LIME, .30f);
                }
            }
            impact(x, y, .25f, .08f, Color.LIME);
        }
        runtime.resetDrone(level);
    }

    private void updateOrbital() {
        int level = player.abilities.level(AbilityType.ORBITAL_BLADE);
        if (level <= 0 || !runtime.readyOrbital()) return;
        int tier = player.abilities.tier(AbilityType.ORBITAL_BLADE);
        float orbit = 2.0f + level * .12f;
        float x = player.position.x + MathUtils.cosDeg(runtime.orbitalAngle) * orbit;
        float y = player.position.y + MathUtils.sinDeg(runtime.orbitalAngle) * orbit;
        float radius = .55f + level * .05f + (tier >= 2 ? .10f : 0f);
        float r2 = radius * radius;
        float damage = (10f + level * 5f) * abilityPower * (tier >= 3 ? 1.35f : 1f);
        DamageElement element = player.abilities.hasStormBladeSynergy()
            ? DamageElement.SHOCK
            : (player.abilities.hasPermafrostBladeSynergy() ? DamageElement.FROST : DamageElement.KINETIC);
        Color color = element == DamageElement.SHOCK ? Color.CYAN : (element == DamageElement.FROST ? new Color(.55f, .9f, 1f, 1f) : Color.GOLD);
        for (Enemy e : enemies) {
            if (!e.alive) continue;
            float dx = e.position.x - x;
            float dy = e.position.y - y;
            if (dx * dx + dy * dy > r2) continue;
            damageEnemy(e, damage, element, color, .28f);
        }
        runtime.resetOrbital(level);
    }

    private void explode(float x, float y, float radius, float damage, DamageElement element) {
        Color blast = switch (element) {
            case FROST -> new Color(.35f, .8f, 1f, 1f);
            case FIRE -> Color.ORANGE;
            case SHOCK -> Color.CYAN;
            default -> Color.ORANGE;
        };
        impact(x, y, radius, .28f, blast);
        float r2 = radius * radius;
        for (Enemy e : enemies) {
            if (!e.alive) continue;
            float dx = e.position.x - x;
            float dy = e.position.y - y;
            if (dx * dx + dy * dy > r2) continue;
            damageEnemy(e, damage, element, blast, .45f);
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

    private void arc(float x1, float y1, float x2, float y2, float duration) {
        ArcFx fx = pools.arc();
        if (fx != null) fx.spawn(x1, y1, x2, y2, duration);
    }
}
