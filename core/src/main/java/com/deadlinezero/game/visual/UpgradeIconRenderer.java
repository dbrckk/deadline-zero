package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.progression.Upgrade;

/**
 * Scalable combat-upgrade glyphs. The rarity color owns the outer frame while the inner symbol
 * communicates the mechanical family before the player reads the card copy.
 */
public final class UpgradeIconRenderer {
    private static final Color FIRE = new Color(1f, .34f, .06f, 1f);
    private static final Color ICE = new Color(.48f, .90f, 1f, 1f);
    private static final Color SHOCK = new Color(.62f, .42f, 1f, 1f);
    private static final Color ORANGE = new Color(1f, .62f, .12f, 1f);

    private UpgradeIconRenderer() { }

    public static void draw(ShapeRenderer shapes, Upgrade upgrade, float cx, float cy, float size, Color rarity) {
        if (shapes == null || size <= 0f || rarity == null) return;
        UpgradePresentation.Archetype type = UpgradePresentation.archetype(upgrade);
        Color family = familyColor(type);
        float outer = size * .50f;
        float inner = size * .39f;

        shapes.setColor(rarity.r, rarity.g, rarity.b, .18f);
        shapes.circle(cx, cy, outer * 1.18f, 28);
        shapes.setColor(rarity.r, rarity.g, rarity.b, .92f);
        shapes.circle(cx, cy, outer, 28);
        shapes.setColor(VisualTheme.SURFACE_0.r, VisualTheme.SURFACE_0.g, VisualTheme.SURFACE_0.b, 1f);
        shapes.circle(cx, cy, inner, 26);
        shapes.setColor(family.r, family.g, family.b, .18f);
        shapes.circle(cx, cy, inner * .82f, 24);

        drawGlyph(shapes, type, cx, cy, size * .62f, family);
    }

    static Color familyColor(UpgradePresentation.Archetype type) {
        return switch (type) {
            case RATE, MOBILITY -> VisualTheme.CYAN_SOFT;
            case DAMAGE, MULTISHOT, BREACH, MISSILE -> VisualTheme.GOLD;
            case VITALITY -> VisualTheme.GREEN;
            case CRITICAL, PROTOCOL -> VisualTheme.VIOLET;
            case BALLISTIC -> VisualTheme.TEXT_STRONG;
            case FIRE -> FIRE;
            case FROST -> ICE;
            case SHOCK -> SHOCK;
            case ELEMENTAL -> VisualTheme.CYAN;
            case DRONE -> VisualTheme.CYAN_SOFT;
            case ORBITAL -> ORANGE;
        };
    }

    private static void drawGlyph(ShapeRenderer shapes, UpgradePresentation.Archetype type,
                                  float cx, float cy, float s, Color color) {
        float t = Math.max(2f, s * .085f);
        shapes.setColor(color);
        switch (type) {
            case RATE -> {
                for (int i = -1; i <= 1; i++) {
                    float x = cx + i * s * .20f;
                    shapes.triangle(x - s * .10f, cy - s * .22f,
                        x + s * .12f, cy, x - s * .10f, cy + s * .22f);
                }
            }
            case DAMAGE -> {
                shapes.triangle(cx, cy + s * .42f, cx - s * .34f, cy - s * .30f, cx, cy - s * .08f);
                shapes.triangle(cx, cy + s * .42f, cx + s * .34f, cy - s * .30f, cx, cy - s * .08f);
                shapes.setColor(VisualTheme.SURFACE_0);
                shapes.circle(cx, cy - s * .02f, s * .10f, 12);
            }
            case MOBILITY -> {
                shapes.triangle(cx + s * .40f, cy, cx - s * .10f, cy + s * .28f, cx - s * .10f, cy - s * .28f);
                shapes.rect(cx - s * .38f, cy - t * .5f, s * .34f, t);
                shapes.rect(cx - s * .30f, cy + s * .16f, s * .20f, t * .65f);
                shapes.rect(cx - s * .30f, cy - s * .18f, s * .20f, t * .65f);
            }
            case VITALITY -> {
                shapes.rect(cx - t * .55f, cy - s * .32f, t * 1.10f, s * .64f);
                shapes.rect(cx - s * .32f, cy - t * .55f, s * .64f, t * 1.10f);
                shapes.setColor(color.r, color.g, color.b, .28f);
                shapes.circle(cx, cy, s * .36f, 20);
            }
            case MULTISHOT -> {
                for (int i = -1; i <= 1; i++) {
                    float y = cy + i * s * .22f;
                    shapes.rect(cx - s * .30f, y - t * .45f, s * .45f, t * .9f);
                    shapes.triangle(cx + s * .30f, y, cx + s * .10f, y + s * .09f, cx + s * .10f, y - s * .09f);
                }
            }
            case CRITICAL -> {
                shapes.circle(cx, cy, s * .34f, 20);
                shapes.setColor(VisualTheme.SURFACE_0);
                shapes.circle(cx, cy, s * .21f, 18);
                shapes.setColor(color);
                shapes.circle(cx, cy, s * .08f, 12);
                shapes.rect(cx - t * .5f, cy + s * .27f, t, s * .18f);
                shapes.rect(cx - t * .5f, cy - s * .45f, t, s * .18f);
                shapes.rect(cx + s * .27f, cy - t * .5f, s * .18f, t);
                shapes.rect(cx - s * .45f, cy - t * .5f, s * .18f, t);
            }
            case BALLISTIC -> {
                shapes.rect(cx - s * .35f, cy - t * .5f, s * .56f, t);
                shapes.triangle(cx + s * .40f, cy, cx + s * .12f, cy + s * .16f, cx + s * .12f, cy - s * .16f);
                shapes.setColor(color.r, color.g, color.b, .45f);
                shapes.rect(cx - s * .34f, cy + s * .16f, s * .24f, t * .55f);
                shapes.rect(cx - s * .34f, cy - s * .18f, s * .24f, t * .55f);
            }
            case BREACH -> {
                shapes.rect(cx - t * .5f, cy - s * .34f, t, s * .68f);
                shapes.rect(cx - s * .34f, cy - t * .5f, s * .68f, t);
                for (int i = 0; i < 4; i++) {
                    float a = 45f + i * 90f;
                    float x = cx + MathUtils.cosDeg(a) * s * .30f;
                    float y = cy + MathUtils.sinDeg(a) * s * .30f;
                    shapes.circle(x, y, s * .065f, 10);
                }
            }
            case FIRE -> {
                shapes.triangle(cx, cy + s * .44f, cx - s * .30f, cy - s * .34f, cx + s * .30f, cy - s * .34f);
                shapes.setColor(1f, .78f, .22f, 1f);
                shapes.triangle(cx + s * .05f, cy + s * .18f, cx - s * .13f, cy - s * .25f, cx + s * .17f, cy - s * .25f);
            }
            case FROST -> {
                for (int i = 0; i < 3; i++) {
                    float a = i * 60f;
                    float dx = MathUtils.cosDeg(a) * s * .40f;
                    float dy = MathUtils.sinDeg(a) * s * .40f;
                    shapes.rectLine(cx - dx, cy - dy, cx + dx, cy + dy, t * .60f);
                }
                shapes.circle(cx, cy, s * .10f, 12);
            }
            case SHOCK -> {
                shapes.triangle(cx + s * .10f, cy + s * .42f, cx - s * .25f, cy + s * .02f, cx + s * .02f, cy + s * .02f);
                shapes.triangle(cx - s * .08f, cy - s * .42f, cx + s * .25f, cy - s * .02f, cx - s * .02f, cy - s * .02f);
            }
            case ELEMENTAL -> {
                Color[] colors = {FIRE, ICE, SHOCK};
                for (int i = 0; i < 3; i++) {
                    float a = 90f + i * 120f;
                    shapes.setColor(colors[i]);
                    shapes.circle(cx + MathUtils.cosDeg(a) * s * .24f,
                        cy + MathUtils.sinDeg(a) * s * .24f, s * .11f, 12);
                }
                shapes.setColor(color);
                shapes.circle(cx, cy, s * .07f, 10);
            }
            case MISSILE -> {
                shapes.rect(cx - t * .45f, cy - s * .22f, t * .90f, s * .44f);
                shapes.triangle(cx, cy + s * .43f, cx - s * .16f, cy + s * .16f, cx + s * .16f, cy + s * .16f);
                shapes.triangle(cx - t * .45f, cy - s * .12f, cx - s * .23f, cy - s * .34f, cx - t * .45f, cy - s * .28f);
                shapes.triangle(cx + t * .45f, cy - s * .12f, cx + s * .23f, cy - s * .34f, cx + t * .45f, cy - s * .28f);
            }
            case DRONE -> {
                shapes.rect(cx - s * .18f, cy - s * .16f, s * .36f, s * .32f);
                shapes.rect(cx - s * .40f, cy - t * .45f, s * .22f, t * .9f);
                shapes.rect(cx + s * .18f, cy - t * .45f, s * .22f, t * .9f);
                shapes.circle(cx, cy, s * .07f, 10);
            }
            case ORBITAL -> {
                shapes.circle(cx, cy, s * .11f, 12);
                for (int i = 0; i < 3; i++) {
                    float a = i * 120f;
                    float bx = cx + MathUtils.cosDeg(a) * s * .32f;
                    float by = cy + MathUtils.sinDeg(a) * s * .32f;
                    shapes.triangle(bx, by,
                        cx + MathUtils.cosDeg(a + 18f) * s * .16f,
                        cy + MathUtils.sinDeg(a + 18f) * s * .16f,
                        cx + MathUtils.cosDeg(a - 18f) * s * .16f,
                        cy + MathUtils.sinDeg(a - 18f) * s * .16f);
                }
            }
            case PROTOCOL -> {
                shapes.circle(cx, cy, s * .10f, 12);
                for (int i = 0; i < 4; i++) {
                    float a = 45f + i * 90f;
                    float ex = cx + MathUtils.cosDeg(a) * s * .36f;
                    float ey = cy + MathUtils.sinDeg(a) * s * .36f;
                    shapes.rectLine(cx, cy, ex, ey, t * .55f);
                    shapes.circle(ex, ey, s * .075f, 10);
                }
            }
        }
    }
}
