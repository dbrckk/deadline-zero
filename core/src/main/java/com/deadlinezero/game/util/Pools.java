package com.deadlinezero.game.util;

import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Projectile;
import com.deadlinezero.game.fx.ImpactFx;

public final class Pools {
    public final Array<Projectile> projectiles = new Array<>(false, 900);
    public final Array<ImpactFx> impacts = new Array<>(false, 256);
    public Pools() { for(int i=0;i<900;i++) projectiles.add(new Projectile()); for(int i=0;i<256;i++) impacts.add(new ImpactFx()); }
    public Projectile projectile() { for (Projectile p: projectiles) if(!p.active) return p; return null; }
    public ImpactFx impact() { for (ImpactFx f: impacts) if(!f.active) return f; return null; }
}
