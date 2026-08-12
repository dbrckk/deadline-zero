package com.deadlinezero.game.util;

import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.config.GameConfig;
import com.deadlinezero.game.entities.Projectile;
import com.deadlinezero.game.fx.ImpactFx;

public final class Pools {
    public final Array<Projectile> projectiles = new Array<>(false, GameConfig.MAX_PROJECTILES);
    public final Array<ImpactFx> impacts = new Array<>(false, 256);
    private int projectileCursor;
    private int impactCursor;

    public Pools() {
        for (int i = 0; i < GameConfig.MAX_PROJECTILES; i++) projectiles.add(new Projectile());
        for (int i = 0; i < 256; i++) impacts.add(new ImpactFx());
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

    public ImpactFx impact() {
        int size = impacts.size;
        for (int i = 0; i < size; i++) {
            impactCursor = (impactCursor + 1) % size;
            ImpactFx f = impacts.get(impactCursor);
            if (!f.active) return f;
        }
        return null;
    }
}
