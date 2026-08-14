package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Player;

/** Lightweight procedural signatures for active legendary transformations. */
public final class LegendaryFxRenderer {
    public void render(ShapeRenderer shapes, Player player, float time, float quality) {
        if (player == null || !player.legendary.hasAny()) return;
        float q = MathUtils.clamp(quality, .35f, 1f);

        if (player.legendary.hasOverdrive()) {
            float pulse = 1f + MathUtils.sin(time * 9f) * .08f;
            shapes.setColor(1f, .32f, .08f, .13f * q);
            shapes.circle(player.position.x, player.position.y, 1.28f * pulse, 24);
            shapes.setColor(1f, .78f, .18f, .26f * q);
            shapes.circle(player.position.x, player.position.y, .78f * pulse, 20);
        }

        if (player.legendary.hasSingularity()) {
            float pulse = 1f + MathUtils.sin(time * 6.5f + 1.7f) * .10f;
            shapes.setColor(.55f, .16f, 1f, .14f * q);
            shapes.circle(player.position.x, player.position.y, 1.55f * pulse, 28);
            shapes.setColor(.18f, .72f, 1f, .22f * q);
            shapes.circle(player.position.x, player.position.y, 1.02f / pulse, 24);
        }

        if (player.legendary.hasApex()) {
            float pulse = 1f + MathUtils.sin(time * 4.2f + .8f) * .06f;
            shapes.setColor(1f, .86f, .28f, .13f * q);
            shapes.circle(player.position.x, player.position.y, 1.88f * pulse, 30);
            for (int i = 0; i < 5; i++) {
                float angle = time * 52f + i * 72f;
                float x = player.position.x + MathUtils.cosDeg(angle) * 1.48f;
                float y = player.position.y + MathUtils.sinDeg(angle) * 1.48f;
                shapes.setColor(.90f, .96f, 1f, .42f * q);
                shapes.circle(x, y, .10f, 10);
            }
        }

        drawWeaponFamily(shapes, player, time, q);
    }

    private void drawWeaponFamily(ShapeRenderer shapes, Player player, float time, float q) {
        WeaponLegendaryPresentation.Style style = WeaponLegendaryPresentation.style(player);
        if (style == WeaponLegendaryPresentation.Style.NONE) return;

        float pulse = 1f + MathUtils.sin(time * 7.2f) * .075f;
        int segments = q > .70f ? 28 : 18;
        float x = player.position.x;
        float y = player.position.y;
        shapes.setColor(style.r, style.g, style.b, .16f * q);
        shapes.circle(x, y, 1.46f * pulse, segments);
        shapes.setColor(style.r, style.g, style.b, .34f * q);
        shapes.circle(x, y, .92f / pulse, segments);

        switch (style) {
            case VANGUARD -> {
                for (int i = 0; i < 4; i++) {
                    float a = 45f + i * 90f;
                    shapes.setColor(style.r, style.g, style.b, .30f * q);
                    shapes.rectLine(x + MathUtils.cosDeg(a) * .95f, y + MathUtils.sinDeg(a) * .95f,
                        x + MathUtils.cosDeg(a) * 1.55f, y + MathUtils.sinDeg(a) * 1.55f, .035f);
                }
            }
            case SCATTER -> {
                for (int i = 0; i < 6; i++) {
                    float a = time * 24f + i * 60f;
                    shapes.setColor(1f, .82f, .30f, .34f * q);
                    shapes.circle(x + MathUtils.cosDeg(a) * 1.22f, y + MathUtils.sinDeg(a) * 1.22f, .07f, 8);
                }
            }
            case RAIL -> {
                shapes.setColor(.96f, .98f, 1f, .42f * q);
                shapes.rectLine(x - 1.55f, y, x + 1.55f, y, .026f);
                shapes.rectLine(x, y - 1.55f, x, y + 1.55f, .026f);
            }
            case INFERNO -> {
                for (int i = 0; i < 3; i++) {
                    float a = time * (48f + i * 9f) + i * 120f;
                    float r = 1.05f + i * .20f;
                    shapes.setColor(1f, .18f + i * .12f, .03f, .30f * q);
                    shapes.circle(x + MathUtils.cosDeg(a) * r, y + MathUtils.sinDeg(a) * r, .10f, 10);
                }
            }
            case CRYO -> {
                for (int i = 0; i < 3; i++) {
                    float a = 90f + i * 120f + MathUtils.sin(time * 2.5f) * 8f;
                    shapes.setColor(.74f, .96f, 1f, .38f * q);
                    shapes.triangle(x + MathUtils.cosDeg(a) * .85f, y + MathUtils.sinDeg(a) * .85f,
                        x + MathUtils.cosDeg(a + 8f) * 1.45f, y + MathUtils.sinDeg(a + 8f) * 1.45f,
                        x + MathUtils.cosDeg(a - 8f) * 1.45f, y + MathUtils.sinDeg(a - 8f) * 1.45f);
                }
            }
            case ARC -> {
                for (int i = 0; i < 5; i++) {
                    float a = time * 72f + i * 72f;
                    float r1 = .82f;
                    float r2 = 1.42f + .12f * MathUtils.sin(time * 10f + i);
                    shapes.setColor(.72f, .54f, 1f, .36f * q);
                    shapes.rectLine(x + MathUtils.cosDeg(a) * r1, y + MathUtils.sinDeg(a) * r1,
                        x + MathUtils.cosDeg(a + 12f) * r2, y + MathUtils.sinDeg(a + 12f) * r2, .030f);
                }
            }
            case BREACHER -> {
                for (int i = 0; i < 8; i++) {
                    float a = i * 45f;
                    float r = 1.08f + .18f * MathUtils.sin(time * 8f + i);
                    shapes.setColor(1f, .48f, .14f, .28f * q);
                    shapes.rectLine(x + MathUtils.cosDeg(a) * .78f, y + MathUtils.sinDeg(a) * .78f,
                        x + MathUtils.cosDeg(a) * r, y + MathUtils.sinDeg(a) * r, .045f);
                }
            }
            case ION -> {
                for (int i = 0; i < 4; i++) {
                    float a = time * 95f + i * 90f;
                    shapes.setColor(.40f, .94f, 1f, .42f * q);
                    shapes.circle(x + MathUtils.cosDeg(a) * 1.18f, y + MathUtils.sinDeg(a) * 1.18f, .075f, 8);
                }
            }
            case CINDER -> {
                shapes.setColor(1f, .58f, .08f, .30f * q);
                shapes.circle(x, y, 1.76f * pulse, segments);
                shapes.setColor(1f, .92f, .46f, .24f * q);
                shapes.circle(x, y, .64f / pulse, segments);
            }
            default -> { }
        }
    }
}
