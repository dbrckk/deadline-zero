package com.deadlinezero.game.util;

import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.entities.EnemyProjectile;
import com.deadlinezero.game.entities.HomingMissile;
import com.deadlinezero.game.entities.Projectile;
import com.deadlinezero.game.fx.ArcFx;
import com.deadlinezero.game.fx.DamageNumber;
import com.deadlinezero.game.fx.DeathFx;
import com.deadlinezero.game.fx.ImpactFx;

public final class Pools {
    public static final int MAX_HOSTILE_PROJECTILES = 512;
    public static final int MAX_HOMING_MISSILES = 96;
    public static final int MAX_DAMAGE_NUMBERS = 256;
    public static final int MAX_ARCS = 96;
    public static final int MAX_DEATH_FX = 72;

    public final Array<Projectile> projectiles = new Array<>(false, GameConfig.MAX_PROJECTILES);
    public final Array<EnemyProjectile> hostileProjectiles = new Array<>(false, MAX_HOSTILE_PROJECTILES);
    public final Array<HomingMissile> homingMissiles = new Array<>(false, MAX_HOMING_MISSILES);
    public final Array<ImpactFx> impacts = new Array<>(false, 256);
    public final Array<DamageNumber> damageNumbers = new Array<>(false, MAX_DAMAGE_NUMBERS);
    public final Array<ArcFx> arcs = new Array<>(false, MAX_ARCS);
    public final Array<DeathFx> deathFx = new Array<>(false, MAX_DEATH_FX);

    private int projectileCursor;
    private int hostileProjectileCursor;
    private int homingMissileCursor;
    private int impactCursor;
    private int damageNumberCursor;
    private int arcCursor;
    private int deathFxCursor;

    public Pools() {
        for (int i = 0; i < GameConfig.MAX_PROJECTILES; i++) projectiles.add(new Projectile());
        for (int i = 0; i < MAX_HOSTILE_PROJECTILES; i++) hostileProjectiles.add(new EnemyProjectile());
        for (int i = 0; i < MAX_HOMING_MISSILES; i++) homingMissiles.add(new HomingMissile());
        for (int i = 0; i < 256; i++) impacts.add(new ImpactFx());
        for (int i = 0; i < MAX_DAMAGE_NUMBERS; i++) damageNumbers.add(new DamageNumber());
        for (int i = 0; i < MAX_ARCS; i++) arcs.add(new ArcFx());
        for (int i = 0; i < MAX_DEATH_FX; i++) deathFx.add(new DeathFx());
    }

    public Projectile projectile() {
        int size = projectiles.size;
        for (int i = 0; i < size; i++) {
            projectileCursor = (projectileCursor + 1) % size;
            Projectile p = projectiles.get(projectileCursor);
            if (!p.active) return p;
        }
        return null;
    }

    public EnemyProjectile hostileProjectile() {
        int size = hostileProjectiles.size;
        for (int i = 0; i < size; i++) {
            hostileProjectileCursor = (hostileProjectileCursor + 1) % size;
            EnemyProjectile p = hostileProjectiles.get(hostileProjectileCursor);
            if (!p.active) return p;
        }
        return null;
    }

    public HomingMissile homingMissile() {
        int size = homingMissiles.size;
        for (int i = 0; i < size; i++) {
            homingMissileCursor = (homingMissileCursor + 1) % size;
            HomingMissile missile = homingMissiles.get(homingMissileCursor);
            if (!missile.active) return missile;
        }
        return null;
    }

    public ImpactFx impact() {
        int size = impacts.size;
        for (int i = 0; i < size; i++) {
            impactCursor = (impactCursor + 1) % size;
            ImpactFx f = impacts.get(impactCursor);
            if (!f.active) return f;
        }
        return null;
    }

    public DamageNumber damageNumber() {
        int size = damageNumbers.size;
        for (int i = 0; i < size; i++) {
            damageNumberCursor = (damageNumberCursor + 1) % size;
            DamageNumber n = damageNumbers.get(damageNumberCursor);
            if (!n.active) return n;
        }
        return null;
    }

    public ArcFx arc() {
        int size = arcs.size;
        for (int i = 0; i < size; i++) {
            arcCursor = (arcCursor + 1) % size;
            ArcFx arc = arcs.get(arcCursor);
            if (!arc.active) return arc;
        }
        return null;
    }

    public DeathFx deathFx() {
        int size = deathFx.size;
        for (int i = 0; i < size; i++) {
            deathFxCursor = (deathFxCursor + 1) % size;
            DeathFx fx = deathFx.get(deathFxCursor);
            if (!fx.active) return fx;
        }
        DeathFx oldest = deathFx.get(deathFxCursor);
        oldest.active = false;
        return oldest;
    }
}
