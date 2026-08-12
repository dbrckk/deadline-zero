package com.deadlinezero.game.util;

import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.entities.EnemyProjectile;
import com.deadlinezero.game.entities.Projectile;
import com.deadlinezero.game.fx.DamageNumber;
import com.deadlinezero.game.fx.ImpactFx;

public final class Pools {
    public static final int MAX_HOSTILE_PROJECTILES = 512;
    public static final int MAX_DAMAGE_NUMBERS = 256;

    public final Array<Projectile> projectiles = new Array<>(false, GameConfig.MAX_PROJECTILES);
    public final Array<EnemyProjectile> hostileProjectiles = new Array<>(false, MAX_HOSTILE_PROJECTILES);
    public final Array<ImpactFx> impacts = new Array<>(false, 256);
    public final Array<DamageNumber> damageNumbers = new Array<>(false, MAX_DAMAGE_NUMBERS);

    private int projectileCursor;
    private int hostileProjectileCursor;
    private int impactCursor;
    private int damageNumberCursor;

    public Pools() {
        for (int i = 0; i < GameConfig.MAX_PROJECTILES; i++) projectiles.add(new Projectile());
        for (int i = 0; i < MAX_HOSTILE_PROJECTILES; i++) hostileProjectiles.add(new EnemyProjectile());
        for (int i = 0; i < 256; i++) impacts.add(new ImpactFx());
        for (int i = 0; i < MAX_DAMAGE_NUMBERS; i++) damageNumbers.add(new DamageNumber());
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
}
