package com.deadlinezero.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public final class VirtualStick {
    private final Vector2 value = new Vector2();
    private final Vector2 origin = new Vector2();
    private boolean touching;
    public Vector2 update(float worldW, float worldH) {
        float x = 0, y = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) x--;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x++;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) y++;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) y--;
        if (x != 0 || y != 0) return value.set(x,y).nor();
        if (Gdx.input.isTouched()) {
            float sx = Gdx.input.getX(), sy = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (sx < Gdx.graphics.getWidth() * .55f) {
                if (!touching) { origin.set(sx, sy); touching = true; }
                value.set(sx - origin.x, sy - origin.y);
                float max = Math.max(64f, Gdx.graphics.getHeight() * .12f);
                if (value.len() > max) value.setLength(max);
                return value.scl(1f / max);
            }
        }
        touching = false;
        return value.setZero();
    }
}
