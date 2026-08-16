package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Higher-resolution directional bootstrap for the most frequently visible combat actors.
 * It intentionally covers only all five survivors plus Shambler and Runner so the Android
 * memory cost stays bounded. Final atlas art still overrides this layer.
 */
public final class HighResDirectionalBootstrapArt implements Disposable {
    static final int TILE = 48;
    static final int COLUMNS = 16;
    static final int FRAMES_PER_DIRECTION = 12;
    static final int ACTOR_BLOCK = FRAMES_PER_DIRECTION * 8;
    static final int ACTOR_COUNT = 7;
    static final int TOTAL_TILES = ACTOR_BLOCK * ACTOR_COUNT;

    private static final String[] ROOTS = {
        "survivor/rex/", "survivor/nyx/", "survivor/bastion/", "survivor/volt/", "survivor/wraith/",
        "enemy/shambler/", "enemy/runner/"
    };
    private static final int[][] DIRECTIONS = {
        {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}
    };
    private static final float[][] PRIMARY = {
        {.18f,.72f,.94f}, {.68f,.30f,.88f}, {.82f,.62f,.22f}, {.24f,.82f,.66f}, {.72f,.78f,.90f},
        {.42f,.64f,.36f}, {.72f,.47f,.28f}
    };
    private static final float[][] SECONDARY = {
        {.08f,.28f,.44f}, {.25f,.10f,.38f}, {.38f,.28f,.10f}, {.08f,.34f,.28f}, {.20f,.23f,.34f},
        {.19f,.31f,.17f}, {.31f,.19f,.13f}
    };
    private static final float[][] ACCENT = {
        {.76f,.96f,1f}, {1f,.42f,1f}, {1f,.90f,.52f}, {.62f,1f,.86f}, {.52f,.90f,1f},
        {.94f,.30f,.25f}, {1f,.61f,.30f}
    };

    private final Texture texture;
    private final TextureRegion[] regions = new TextureRegion[TOTAL_TILES];

    private HighResDirectionalBootstrapArt(Texture texture) {
        this.texture = texture;
        for (int tile = 0; tile < TOTAL_TILES; tile++) {
            int x = (tile % COLUMNS) * TILE;
            int y = (tile / COLUMNS) * TILE;
            regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
        }
    }

    public static HighResDirectionalBootstrapArt create() {
        int rows = rows();
        Pixmap pixmap = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.SourceOver);
        try {
            for (int actor = 0; actor < ACTOR_COUNT; actor++) drawActorSet(pixmap, actor, actor * ACTOR_BLOCK);
            Texture texture = new Texture(pixmap);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return new HighResDirectionalBootstrapArt(texture);
        } finally {
            pixmap.dispose();
        }
    }

    static int rows() { return (TOTAL_TILES + COLUMNS - 1) / COLUMNS; }
    static int width() { return COLUMNS * TILE; }
    static int height() { return rows() * TILE; }

    public boolean supports(String key) { return firstTile(key) >= 0; }

    public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
        int first = firstTile(key);
        if (first < 0) return null;
        int count = frameCount(key);
        if (count <= 1) return regions[first];
        int rawFrame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
        int frame = loop ? rawFrame % count : Math.min(count - 1, rawFrame);
        return regions[first + frame];
    }

    static int firstTile(String key) {
        if (key == null || key.isBlank()) return -1;
        int actor = actorIndex(key);
        if (actor < 0) return -1;
        String rest = key.substring(ROOTS[actor].length());
        int slash = rest.indexOf('/');
        if (slash <= 0 || slash >= rest.length() - 1) return -1;
        int direction = directionIndex(rest.substring(0, slash));
        int motion = motionOffset(rest.substring(slash + 1));
        if (direction < 0 || motion < 0) return -1;
        return actor * ACTOR_BLOCK + direction * FRAMES_PER_DIRECTION + motion;
    }

    static int frameCount(String key) {
        if (firstTile(key) < 0) return 0;
        String motion = key.substring(key.lastIndexOf('/') + 1);
        return switch (motion) {
            case "idle", "attack", "hit" -> 2;
            case "run", "death" -> 3;
            default -> 0;
        };
    }

    static int actorIndex(String key) {
        if (key == null) return -1;
        for (int i = 0; i < ROOTS.length; i++) if (key.startsWith(ROOTS[i])) return i;
        return -1;
    }

    private static int directionIndex(String token) {
        return switch (token) {
            case "n" -> 0; case "ne" -> 1; case "e" -> 2; case "se" -> 3;
            case "s" -> 4; case "sw" -> 5; case "w" -> 6; case "nw" -> 7;
            default -> -1;
        };
    }

    private static int motionOffset(String motion) {
        return switch (motion) {
            case "idle" -> 0; case "run" -> 2; case "attack" -> 5; case "hit" -> 7; case "death" -> 9;
            default -> -1;
        };
    }

    private static void drawActorSet(Pixmap p, int actor, int actorBase) {
        for (int direction = 0; direction < DIRECTIONS.length; direction++) {
            int dx = DIRECTIONS[direction][0];
            int dy = DIRECTIONS[direction][1];
            int base = actorBase + direction * FRAMES_PER_DIRECTION;
            drawFrame(p, base, actor, dx, dy, 0, 0);
            drawFrame(p, base + 1, actor, dx, dy, 0, 1);
            drawFrame(p, base + 2, actor, dx, dy, 1, 0);
            drawFrame(p, base + 3, actor, dx, dy, 1, 1);
            drawFrame(p, base + 4, actor, dx, dy, 1, 2);
            drawFrame(p, base + 5, actor, dx, dy, 2, 0);
            drawFrame(p, base + 6, actor, dx, dy, 2, 1);
            drawFrame(p, base + 7, actor, dx, dy, 3, 0);
            drawFrame(p, base + 8, actor, dx, dy, 3, 1);
            drawFrame(p, base + 9, actor, dx, dy, 4, 0);
            drawFrame(p, base + 10, actor, dx, dy, 4, 1);
            drawFrame(p, base + 11, actor, dx, dy, 4, 2);
        }
    }

    /** motion: 0 idle, 1 run, 2 attack, 3 hit, 4 death. */
    private static void drawFrame(Pixmap p, int tile, int actor, int dx, int dy, int motion, int frame) {
        int ox = (tile % COLUMNS) * TILE;
        int oy = (tile / COLUMNS) * TILE;
        int sx = dx;
        int sy = -dy;
        int cx = ox + 24;
        int cy = oy + 25;
        int perpX = -sy;
        int perpY = sx;
        int bob = motion == 0 ? frame : motion == 1 ? (frame == 1 ? -2 : frame == 2 ? 1 : 0) : 0;
        int stride = motion == 1 ? (frame == 0 ? -4 : frame == 1 ? 4 : 0) : 0;
        int recoil = motion == 3 && frame == 1 ? 3 : 0;
        int reach = motion == 2 && frame == 1 ? 6 : 0;

        drawShadow(p, ox, oy, actor, motion, frame);
        if (motion == 4) {
            drawDeath(p, ox, oy, actor, dx, frame);
            return;
        }

        cx -= sx * recoil;
        cy -= sy * recoil;
        drawLegs(p, actor, cx, cy + bob, sx, sy, perpX, perpY, stride);
        drawBody(p, actor, cx, cy + bob, sx, sy, perpX, perpY);
        drawIdentity(p, actor, cx, cy + bob, sx, sy, perpX, perpY);
        drawFace(p, actor, cx, cy + bob, sx, sy, perpX, perpY);
        drawArmsAndWeapon(p, actor, cx, cy + bob, sx, sy, perpX, perpY, motion, frame, reach);
        if (motion == 3) drawHit(p, ox, oy, sx, sy, frame);
    }

    private static void drawShadow(Pixmap p, int ox, int oy, int actor, int motion, int frame) {
        set(p, 0f, 0f, 0f, actor < 5 ? .28f : .34f);
        int width = motion == 4 ? 10 + frame * 3 : actor == 2 ? 13 : 11;
        p.fillCircle(ox + 24 + (motion == 4 ? frame * 2 : 0), oy + 39, width);
        set(p, .11f, .14f, .17f, .30f);
        p.drawLine(ox + 12, oy + 40, ox + 36, oy + 40);
    }

    private static void drawLegs(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py, int stride) {
        int spread = actor == 2 ? 6 : 5;
        setSecondary(p, actor);
        int lx = cx - px * spread;
        int ly = cy + 8 - py * 3;
        int rx = cx + px * spread;
        int ry = cy + 8 + py * 3;
        p.drawLine(lx, ly, lx + sx * stride - px * 2, cy + 18 - py * 3);
        p.drawLine(rx, ry, rx - sx * stride + px * 2, cy + 18 + py * 3);
        setPrimary(p, actor);
        p.fillCircle(lx + sx * stride - px * 2, cy + 18 - py * 3, actor == 2 ? 3 : 2);
        p.fillCircle(rx - sx * stride + px * 2, cy + 18 + py * 3, actor == 2 ? 3 : 2);
    }

    private static void drawBody(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py) {
        int radius = actor == 2 ? 12 : actor == 5 ? 10 : actor == 6 ? 9 : 11;
        setSecondary(p, actor);
        p.fillCircle(cx, cy + 3, radius);
        setPrimary(p, actor);
        p.fillCircle(cx + sx * 3, cy - 10 + sy * 3, Math.max(7, radius - 3));

        setAccent(p, actor);
        p.drawLine(cx - px * 6, cy - py * 6, cx + px * 6, cy + py * 6);
        p.drawLine(cx - px * 5 + sx * 2, cy + 6 - py * 5 + sy * 2,
            cx + px * 5 + sx * 2, cy + 6 + py * 5 + sy * 2);
        set(p, 1f, 1f, 1f, .35f);
        p.drawLine(cx - 5, cy - 5, cx + 1, cy - 8);
    }

    private static void drawFace(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py) {
        int fx = cx + sx * 8;
        int fy = cy - 10 + sy * 8;
        setAccent(p, actor);
        if (actor < 5) {
            p.drawLine(fx - px * 5, fy - py * 5, fx + px * 5, fy + py * 5);
            p.drawLine(fx - px * 4 + sx, fy - py * 4 + sy, fx + px * 4 + sx, fy + py * 4 + sy);
        } else {
            p.fillCircle(fx - px * 3, fy - py * 3, 2);
            p.fillCircle(fx + px * 3, fy + py * 3, 2);
        }
    }

    private static void drawArmsAndWeapon(Pixmap p, int actor, int cx, int cy, int sx, int sy,
                                          int px, int py, int motion, int frame, int reach) {
        int handX = cx + sx * (10 + reach);
        int handY = cy + 2 + sy * (10 + reach);
        setPrimary(p, actor);
        p.drawLine(cx, cy + 2, handX, handY);

        if (actor < 5) {
            int muzzleX = cx + sx * (19 + reach);
            int muzzleY = cy + 2 + sy * (19 + reach);
            setSecondary(p, actor);
            p.drawLine(handX - px * 2, handY - py * 2, muzzleX - px * 2, muzzleY - py * 2);
            p.drawLine(handX + px * 2, handY + py * 2, muzzleX + px, muzzleY + py);
            setAccent(p, actor);
            p.drawLine(handX, handY, muzzleX, muzzleY);
            drawWeaponIdentity(p, actor, handX, handY, muzzleX, muzzleY, px, py);
            if (motion == 2 && frame == 1) {
                set(p, 1f, .84f, .34f, 1f);
                p.fillCircle(muzzleX + sx * 3, muzzleY + sy * 3, 4);
                set(p, 1f, .97f, .72f, .9f);
                p.drawLine(muzzleX + sx * 2, muzzleY + sy * 2, muzzleX + sx * 8, muzzleY + sy * 8);
            }
        } else {
            int clawX = cx + sx * (8 + reach) - px * 10;
            int clawY = cy + 3 + sy * (8 + reach) - py * 10;
            p.drawLine(cx - px * 3, cy + 3 - py * 3, clawX, clawY);
            setAccent(p, actor);
            p.drawLine(clawX, clawY, clawX + sx * 4 - px * 2, clawY + sy * 4 - py * 2);
            p.drawLine(clawX, clawY, clawX + sx * 3 + px * 2, clawY + sy * 3 + py * 2);
        }
    }

    private static void drawWeaponIdentity(Pixmap p, int actor, int hx, int hy, int mx, int my, int px, int py) {
        switch (actor) {
            case 0 -> p.drawLine(hx - px * 4, hy - py * 4, hx + px * 3, hy + py * 3);
            case 1 -> {
                p.drawLine(hx + px * 3, hy + py * 3, mx + px * 5, my + py * 5);
                p.drawLine(hx - px * 3, hy - py * 3, mx - px * 5, my - py * 5);
            }
            case 2 -> {
                setSecondary(p, actor); p.fillCircle(hx, hy, 4); setAccent(p, actor);
                p.drawLine(hx + px * 3, hy + py * 3, mx + px * 3, my + py * 3);
            }
            case 3 -> {
                p.drawLine(mx - px * 4, my - py * 4, mx + px * 4, my + py * 4);
                p.fillCircle(mx, my, 2);
            }
            case 4 -> {
                p.drawLine(mx, my, mx + px * 5, my + py * 5);
                p.drawLine(mx, my, mx - px * 5, my - py * 5);
            }
            default -> { }
        }
    }

    private static void drawIdentity(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py) {
        setAccent(p, actor);
        switch (actor) {
            case 0 -> { // Rex shoulder pads
                p.fillCircle(cx - px * 8, cy - py * 8, 3);
                p.fillCircle(cx + px * 8, cy + py * 8, 3);
            }
            case 1 -> { // Nyx fins
                p.drawLine(cx - px * 7, cy - 8 - py * 7, cx - px * 10 - sx * 3, cy - 17 - py * 10 - sy * 3);
                p.drawLine(cx + px * 7, cy - 8 + py * 7, cx + px * 10 - sx * 3, cy - 17 + py * 10 - sy * 3);
            }
            case 2 -> { // Bastion armor slab
                p.drawRectangle(cx - 11, cy - 4, 22, 10);
                p.drawRectangle(cx - 8, cy - 8, 16, 4);
            }
            case 3 -> { // Volt capacitor halo
                p.drawCircle(cx, cy + 2, 14);
                p.drawLine(cx - 9, cy - 11, cx - 4, cy - 17);
                p.drawLine(cx + 4, cy - 17, cx + 9, cy - 11);
            }
            case 4 -> { // Wraith cloak
                p.drawLine(cx - px * 9, cy + 8 - py * 9, cx - px * 6 - sx * 5, cy + 17 - py * 6 - sy * 5);
                p.drawLine(cx + px * 9, cy + 8 + py * 9, cx + px * 6 - sx * 5, cy + 17 + py * 6 - sy * 5);
            }
            case 5 -> { // Shambler asymmetry
                p.drawLine(cx - px * 7, cy + 1 - py * 7, cx - px * 12 - sx * 2, cy + 7 - py * 12 - sy * 2);
                p.drawLine(cx + px * 4, cy - 5 + py * 4, cx + px * 8, cy - 10 + py * 8);
            }
            case 6 -> { // Runner swept spikes
                p.drawLine(cx - sx * 4, cy - 8 - sy * 4, cx - sx * 10, cy - 17 - sy * 10);
                p.drawLine(cx + px * 5, cy - 7 + py * 5, cx - sx * 7 + px * 7, cy - 14 - sy * 7 + py * 7);
            }
            default -> { }
        }
    }

    private static void drawHit(Pixmap p, int ox, int oy, int sx, int sy, int frame) {
        int hx = ox + 10 - sx * (frame == 0 ? 2 : 4);
        int hy = oy + 11 - sy * (frame == 0 ? 2 : 4);
        set(p, 1f, .92f, .82f, 1f);
        p.drawLine(hx - 6, hy - 6, hx + 6, hy + 6);
        p.drawLine(hx - 6, hy + 6, hx + 6, hy - 6);
        set(p, 1f, .30f, .20f, .9f);
        p.fillCircle(hx, hy, frame == 0 ? 4 : 2);
    }

    private static void drawDeath(Pixmap p, int ox, int oy, int actor, int dx, int frame) {
        int side = dx < 0 ? -1 : 1;
        int shift = frame * 5 * side;
        setSecondary(p, actor);
        p.fillCircle(ox + 24 + shift, oy + 27 + frame * 4, Math.max(6, 11 - frame));
        setPrimary(p, actor);
        p.fillCircle(ox + 19 + shift, oy + 18 + frame * 6, Math.max(5, 8 - frame));
        setAccent(p, actor);
        p.drawLine(ox + 11 + shift, oy + 22 + frame * 4, ox + 34 + shift, oy + 29 + frame * 4);
        if (frame >= 1) {
            set(p, .95f, .16f, .16f, .68f);
            p.drawLine(ox + 13 + shift, oy + 38, ox + 34 + shift, oy + 39);
        }
    }

    private static void setPrimary(Pixmap p, int actor) { setPalette(p, PRIMARY[actor]); }
    private static void setSecondary(Pixmap p, int actor) { setPalette(p, SECONDARY[actor]); }
    private static void setAccent(Pixmap p, int actor) { setPalette(p, ACCENT[actor]); }
    private static void setPalette(Pixmap p, float[] c) { p.setColor(c[0], c[1], c[2], 1f); }
    private static void set(Pixmap p, float r, float g, float b, float a) { p.setColor(r, g, b, a); }

    @Override public void dispose() { texture.dispose(); }
}
