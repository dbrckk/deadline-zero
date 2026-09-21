package com.deadlinezero.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Lightweight authored icon language for production UI.
 *
 * Drawn from simple primitives so icons remain crisp at every supported phone
 * resolution and do not depend on text glyphs or platform emoji rendering.
 * Call while ShapeRenderer is in Filled mode.
 */
public final class UiIconRenderer {
    public enum Icon {
        LEVEL,
        CREDITS,
        GEMS,
        STAGE,
        BASE,
        ARSENAL,
        GEAR,
        MISSIONS,
        SHOP,
        SETTINGS,
        LOCK,
        TROPHY
    }

    private UiIconRenderer() {}

    public static void draw(ShapeRenderer shapes, Icon icon, float x, float y, float size, Color color) {
        draw(shapes, icon, x, y, size, color, 1f);
    }

    public static void draw(ShapeRenderer shapes, Icon icon, float x, float y, float size, Color color, float alpha) {
        if (shapes == null || icon == null || color == null || size <= 0f) return;
        shapes.setColor(color.r, color.g, color.b, clamp(alpha));
        float s = size;
        float cx = x + s * .5f;
        float cy = y + s * .5f;
        float t = Math.max(2f, s * .10f);

        switch (icon) {
            case LEVEL -> {
                shapes.triangle(cx, y + s, x + s * .14f, y + s * .28f, x + s * .86f, y + s * .28f);
                set(shapes, color, alpha * .36f);
                shapes.triangle(cx, y + s * .78f, x + s * .30f, y + s * .38f, x + s * .70f, y + s * .38f);
            }
            case CREDITS -> {
                shapes.circle(cx, cy, s * .38f, 20);
                set(shapes, color, alpha * .18f);
                shapes.circle(cx, cy, s * .24f, 16);
                set(shapes, color, alpha);
                shapes.rect(cx - t * .45f, y + s * .30f, t * .9f, s * .40f);
            }
            case GEMS -> {
                shapes.triangle(cx, y + s, x + s * .08f, cy, cx, y);
                shapes.triangle(cx, y + s, x + s * .92f, cy, cx, y);
                set(shapes, color, alpha * .30f);
                shapes.triangle(cx, y + s * .80f, x + s * .28f, cy, cx, y + s * .18f);
                shapes.triangle(cx, y + s * .80f, x + s * .72f, cy, cx, y + s * .18f);
            }
            case STAGE -> {
                shapes.rect(x + s * .18f, y + s * .24f, s * .64f, s * .52f);
                shapes.triangle(cx, y + s, x + s * .18f, y + s * .76f, x + s * .82f, y + s * .76f);
                set(shapes, color, alpha * .24f);
                shapes.rect(x + s * .34f, y + s * .36f, s * .32f, s * .24f);
            }
            case BASE -> {
                shapes.triangle(cx, y + s, x + s * .06f, y + s * .48f, x + s * .94f, y + s * .48f);
                shapes.rect(x + s * .18f, y + s * .10f, s * .64f, s * .42f);
                set(shapes, color, alpha * .24f);
                shapes.rect(x + s * .42f, y + s * .10f, s * .16f, s * .28f);
            }
            case ARSENAL -> {
                shapes.circle(cx, cy, s * .34f, 20);
                set(shapes, color, alpha * .18f);
                shapes.circle(cx, cy, s * .18f, 16);
                set(shapes, color, alpha);
                shapes.rect(cx - t * .5f, y, t, s);
                shapes.rect(x, cy - t * .5f, s, t);
            }
            case GEAR -> {
                shapes.rect(x + s * .12f, y + s * .18f, s * .30f, s * .64f);
                shapes.rect(x + s * .58f, y + s * .18f, s * .30f, s * .64f);
                set(shapes, color, alpha * .22f);
                shapes.rect(x + s * .24f, y + s * .32f, s * .52f, s * .36f);
            }
            case MISSIONS -> {
                for (int i = 0; i < 3; i++) {
                    float yy = y + s * (.18f + i * .27f);
                    shapes.rect(x + s * .10f, yy, s * .16f, s * .14f);
                    shapes.rect(x + s * .36f, yy + s * .045f, s * .54f, t * .55f);
                }
            }
            case SHOP -> {
                shapes.rect(x + s * .16f, y + s * .18f, s * .68f, s * .56f);
                shapes.rect(x + s * .30f, y + s * .70f, s * .40f, t);
                shapes.rect(x + s * .30f, y + s * .70f, t, s * .18f);
                shapes.rect(x + s * .70f - t, y + s * .70f, t, s * .18f);
                set(shapes, color, alpha * .22f);
                shapes.rect(x + s * .28f, y + s * .30f, s * .44f, s * .10f);
            }
            case SETTINGS -> {
                shapes.circle(cx, cy, s * .28f, 20);
                shapes.rect(cx - t * .5f, y, t, s * .22f);
                shapes.rect(cx - t * .5f, y + s * .78f, t, s * .22f);
                shapes.rect(x, cy - t * .5f, s * .22f, t);
                shapes.rect(x + s * .78f, cy - t * .5f, s * .22f, t);
                set(shapes, color, alpha * .22f);
                shapes.circle(cx, cy, s * .11f, 14);
            }
            case LOCK -> {
                shapes.rect(x + s * .18f, y + s * .08f, s * .64f, s * .50f);
                shapes.rect(x + s * .28f, y + s * .56f, t, s * .22f);
                shapes.rect(x + s * .72f - t, y + s * .56f, t, s * .22f);
                shapes.rect(x + s * .28f, y + s * .74f, s * .44f, t);
            }
            case TROPHY -> {
                shapes.rect(x + s * .28f, y + s * .42f, s * .44f, s * .42f);
                shapes.rect(cx - t * .5f, y + s * .18f, t, s * .28f);
                shapes.rect(x + s * .30f, y + s * .10f, s * .40f, t);
                shapes.rect(x + s * .12f, y + s * .58f, s * .16f, t);
                shapes.rect(x + s * .72f, y + s * .58f, s * .16f, t);
            }
        }
    }

    private static void set(ShapeRenderer shapes, Color c, float alpha) {
        shapes.setColor(c.r, c.g, c.b, clamp(alpha));
    }

    private static float clamp(float v) {
        return Math.max(0f, Math.min(1f, v));
    }
}
