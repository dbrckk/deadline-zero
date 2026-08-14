package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Compact deterministic multi-frame VFX sheet used when the production atlas has not supplied
 * an effect yet. Generated once at startup and served from cached TextureRegions.
 */
public final class BootstrapVfxArt implements Disposable {
    static final int TILE = 48;
    static final int COLUMNS = 8;
    static final int FRAMES_PER_EFFECT = 8;

    private static final String[] ROOTS = {
        "fx/muzzle_fire",
        "fx/dash",
        "fx/level_up",
        "fx/impact_energy",
        "fx/impact_fire",
        "fx/impact_frost",
        "fx/impact_shock",
        "fx/impact_kill",
        "fx/boss_explosion",
        "fx/legendary_overdrive",
        "fx/legendary_singularity",
        "fx/legendary_apex"
    };

    static final int EFFECT_COUNT = ROOTS.length;
    static final int TOTAL_TILES = EFFECT_COUNT * FRAMES_PER_EFFECT;

    private final Texture texture;
    private final TextureRegion[] regions = new TextureRegion[TOTAL_TILES];

    private BootstrapVfxArt(Texture texture) {
        this.texture = texture;
        for (int tile = 0; tile < regions.length; tile++) {
            int x = (tile % COLUMNS) * TILE;
            int y = (tile / COLUMNS) * TILE;
            regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
        }
    }

    public static BootstrapVfxArt create() {
        int rows = rows();
        Pixmap pixmap = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.SourceOver);
        try {
            for (int effect = 0; effect < EFFECT_COUNT; effect++) {
                for (int frame = 0; frame < FRAMES_PER_EFFECT; frame++) {
                    drawFrame(pixmap, effect, frame, effect * FRAMES_PER_EFFECT + frame);
                }
            }
            Texture texture = new Texture(pixmap);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return new BootstrapVfxArt(texture);
        } finally {
            pixmap.dispose();
        }
    }

    public boolean supports(String key) { return firstTile(key) >= 0; }

    public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
        int first = firstTile(key);
        if (first < 0) return null;
        int count = frameCount(key);
        int rawFrame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
        int frame = loop ? rawFrame % count : Math.min(count - 1, rawFrame);
        return regions[first + frame];
    }

    static int firstTile(String key) {
        int effect = effectIndex(key);
        return effect < 0 ? -1 : effect * FRAMES_PER_EFFECT;
    }

    static int frameCount(String key) {
        int effect = effectIndex(key);
        if (effect < 0) return 0;
        return switch (effect) {
            case 0 -> 4;          // muzzle
            case 3, 4, 5, 6, 7 -> 5; // impacts
            case 8 -> 8;          // boss explosion
            default -> 6;
        };
    }

    static int effectIndex(String key) {
        if (key == null) return -1;
        for (int i = 0; i < ROOTS.length; i++) if (ROOTS[i].equals(key)) return i;
        return -1;
    }

    static int rows() { return (TOTAL_TILES + COLUMNS - 1) / COLUMNS; }
    static int width() { return COLUMNS * TILE; }
    static int height() { return rows() * TILE; }

    private static void drawFrame(Pixmap p, int effect, int frame, int tile) {
        int ox = (tile % COLUMNS) * TILE;
        int oy = (tile / COLUMNS) * TILE;
        int cx = ox + TILE / 2;
        int cy = oy + TILE / 2;
        float t = frame / (float)Math.max(1, frameCount(ROOTS[effect]) - 1);

        switch (effect) {
            case 0 -> drawMuzzle(p, cx, cy, frame);
            case 1 -> drawDash(p, cx, cy, t);
            case 2 -> drawLevelUp(p, cx, cy, t);
            case 3 -> drawImpact(p, cx, cy, t, .30f, .74f, 1f);
            case 4 -> drawImpact(p, cx, cy, t, 1f, .24f, .08f);
            case 5 -> drawImpact(p, cx, cy, t, .35f, .88f, 1f);
            case 6 -> drawShock(p, cx, cy, t);
            case 7 -> drawImpact(p, cx, cy, t, .42f, 1f, .42f);
            case 8 -> drawBossExplosion(p, cx, cy, t);
            case 9 -> drawLegendary(p, cx, cy, frame, 1f, .34f, .16f, 0);
            case 10 -> drawLegendary(p, cx, cy, frame, .50f, .38f, 1f, 1);
            case 11 -> drawLegendary(p, cx, cy, frame, 1f, .82f, .24f, 2);
            default -> { }
        }
    }

    private static void drawMuzzle(Pixmap p, int cx, int cy, int frame) {
        int reach = 9 + frame * 4;
        set(p, 1f, .96f, .72f, 1f);
        p.fillCircle(cx - 5, cy, Math.max(2, 6 - frame));
        set(p, 1f, .48f, .10f, .92f);
        p.fillTriangle(cx - 3, cy - 6, cx + reach, cy, cx - 3, cy + 6);
        set(p, 1f, .88f, .32f, 1f);
        p.drawLine(cx, cy, cx + reach + 5, cy);
    }

    private static void drawDash(Pixmap p, int cx, int cy, float t) {
        int outer = 8 + Math.round(t * 13f);
        set(p, .25f, .86f, 1f, 1f - t * .55f);
        p.drawCircle(cx, cy, outer);
        p.drawCircle(cx, cy, Math.max(3, outer - 4));
        int streak = 13 + Math.round(t * 10f);
        p.drawLine(cx - streak, cy - 7, cx + 5, cy - 2);
        p.drawLine(cx - streak, cy + 7, cx + 5, cy + 2);
    }

    private static void drawLevelUp(Pixmap p, int cx, int cy, float t) {
        int radius = 8 + Math.round(t * 12f);
        set(p, .45f, 1f, .72f, 1f - t * .40f);
        p.drawCircle(cx, cy, radius);
        p.drawCircle(cx, cy, Math.max(2, radius - 6));
        set(p, .84f, 1f, .92f, 1f - t * .25f);
        for (int i = 0; i < 8; i++) {
            double a = i * Math.PI / 4.0;
            int x1 = cx + (int)(Math.cos(a) * 6);
            int y1 = cy + (int)(Math.sin(a) * 6);
            int x2 = cx + (int)(Math.cos(a) * (radius + 5));
            int y2 = cy + (int)(Math.sin(a) * (radius + 5));
            p.drawLine(x1, y1, x2, y2);
        }
    }

    private static void drawImpact(Pixmap p, int cx, int cy, float t, float r, float g, float b) {
        int core = Math.max(2, Math.round(6f * (1f - t)));
        int radius = 5 + Math.round(t * 14f);
        set(p, r, g, b, 1f - t * .45f);
        p.fillCircle(cx, cy, core);
        p.drawCircle(cx, cy, radius);
        for (int i = 0; i < 6; i++) {
            double a = i * Math.PI / 3.0 + t;
            int x1 = cx + (int)(Math.cos(a) * 4);
            int y1 = cy + (int)(Math.sin(a) * 4);
            int x2 = cx + (int)(Math.cos(a) * (radius + 5));
            int y2 = cy + (int)(Math.sin(a) * (radius + 5));
            p.drawLine(x1, y1, x2, y2);
        }
    }

    private static void drawShock(Pixmap p, int cx, int cy, float t) {
        set(p, .78f, .36f, 1f, 1f - t * .40f);
        int spread = 5 + Math.round(t * 13f);
        for (int i = -2; i <= 2; i++) {
            int x = cx + i * 3;
            p.drawLine(x, cy - spread, x + (i % 2 == 0 ? 5 : -5), cy - 2);
            p.drawLine(x + (i % 2 == 0 ? 5 : -5), cy - 2, x, cy + spread);
        }
        p.drawCircle(cx, cy, 5 + Math.round(t * 10f));
    }

    private static void drawBossExplosion(Pixmap p, int cx, int cy, float t) {
        int outer = 7 + Math.round(t * 17f);
        set(p, 1f, .20f + t * .30f, .06f, 1f - t * .45f);
        p.fillCircle(cx, cy, Math.max(2, Math.round(8f * (1f - t))));
        p.drawCircle(cx, cy, outer);
        set(p, 1f, .82f, .26f, 1f - t * .30f);
        for (int i = 0; i < 12; i++) {
            double a = i * Math.PI / 6.0 + t * .45;
            int inner = 6 + Math.round(t * 5f);
            int x1 = cx + (int)(Math.cos(a) * inner);
            int y1 = cy + (int)(Math.sin(a) * inner);
            int x2 = cx + (int)(Math.cos(a) * (outer + 8));
            int y2 = cy + (int)(Math.sin(a) * (outer + 8));
            p.drawLine(x1, y1, x2, y2);
        }
    }

    private static void drawLegendary(Pixmap p, int cx, int cy, int frame, float r, float g, float b, int style) {
        float phase = frame / 6f;
        int radius = 13 + (frame % 3) * 2;
        set(p, r, g, b, .88f);
        p.drawCircle(cx, cy, radius);
        p.drawCircle(cx, cy, radius - 5);
        int spokes = style == 2 ? 10 : style == 1 ? 6 : 8;
        for (int i = 0; i < spokes; i++) {
            double a = i * Math.PI * 2.0 / spokes + phase;
            int x1 = cx + (int)(Math.cos(a) * (radius - 4));
            int y1 = cy + (int)(Math.sin(a) * (radius - 4));
            int x2 = cx + (int)(Math.cos(a) * (radius + 6));
            int y2 = cy + (int)(Math.sin(a) * (radius + 6));
            p.drawLine(x1, y1, x2, y2);
        }
        if (style == 1) {
            set(p, .10f, .08f, .18f, .92f);
            p.fillCircle(cx, cy, 7);
        } else if (style == 2) {
            set(p, 1f, .96f, .72f, .95f);
            p.fillCircle(cx, cy, 3 + frame % 2);
        }
    }

    private static void set(Pixmap p, float r, float g, float b, float a) { p.setColor(r, g, b, a); }

    @Override public void dispose() { texture.dispose(); }
}
