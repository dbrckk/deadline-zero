package com.deadlinezero.game.fx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public final class ImpactFx {
    public final Vector2 position = new Vector2();
    public float life;
    public float maxLife;
    public float size;
    public final Color color = new Color();
    public boolean active;
    public ImpactFx spawn(float x, float y, float size, float duration, Color c) {
        position.set(x,y); this.size=size; this.life=this.maxLife=duration; this.color.set(c); active=true; return this;
    }
}
