package com.deadlinezero.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** Floating left-side movement stick with keyboard fallback and stable multi-touch ownership. */
public final class VirtualStick {
    private static final int MAX_POINTERS = 10;
    private static VirtualStick activeInstance;

    private final Vector2 value = new Vector2();
    private final Vector2 origin = new Vector2();
    private int pointer = -1;

    public VirtualStick() { activeInstance = this; }

    public Vector2 update(float worldW, float worldH) {
        float x = 0f, y = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) x--;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x++;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) y++;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) y--;
        if (x != 0f || y != 0f) {
            pointer = -1;
            return value.set(x, y).nor();
        }

        final float screenW = Gdx.graphics.getWidth();
        final float screenH = Gdx.graphics.getHeight();
        final float leftZone = screenW * .58f;

        if (pointer >= 0 && !Gdx.input.isTouched(pointer)) pointer = -1;
        if (pointer < 0) {
            for (int p = 0; p < MAX_POINTERS; p++) {
                if (!Gdx.input.isTouched(p)) continue;
                float sx = Gdx.input.getX(p);
                if (sx >= leftZone) continue;
                pointer = p;
                origin.set(sx, screenH - Gdx.input.getY(p));
                break;
            }
        }

        if (pointer < 0) return value.setZero();

        float sx = Gdx.input.getX(pointer);
        float sy = screenH - Gdx.input.getY(pointer);
        value.set(sx - origin.x, sy - origin.y);

        float max = Math.max(64f, screenH * .12f);
        float deadZone = max * .12f;
        float length = value.len();
        if (length <= deadZone) return value.setZero();
        if (length > max) value.setLength(max);

        float normalized = MathUtils.clamp((Math.min(length, max) - deadZone) / (max - deadZone), 0f, 1f);
        return value.nor().scl(normalized);
    }

    public boolean active() { return pointer >= 0; }
    public float originX() { return origin.x; }
    public float originY() { return origin.y; }
    public float valueX() { return value.x; }
    public float valueY() { return value.y; }

    public static boolean hudActive() { return activeInstance != null && activeInstance.active(); }
    public static float hudOriginX() { return activeInstance == null ? 0f : activeInstance.originX(); }
    public static float hudOriginY() { return activeInstance == null ? 0f : activeInstance.originY(); }
    public static float hudValueX() { return activeInstance == null ? 0f : activeInstance.valueX(); }
    public static float hudValueY() { return activeInstance == null ? 0f : activeInstance.valueY(); }
}
