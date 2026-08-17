package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Dedicated 64px eight-way boss art. Kept on its own texture so core actors and bosses
 * can scale independently while remaining below conservative GLES texture dimensions.
 */
public final class HighResBossDirectionalArt implements Disposable {
    static final int TILE = 64;
    static final int COLUMNS = 16;
    static final int FRAMES_PER_DIRECTION = 12;
    static final int ACTOR_BLOCK = FRAMES_PER_DIRECTION * 8;
    static final int ACTOR_COUNT = 4;
    static final int TOTAL_TILES = ACTOR_BLOCK * ACTOR_COUNT;

    private static final String[] ROOTS = {
        "boss/alpha/", "boss/revenant/", "boss/warden/", "boss/harvester/"
    };
    private static final int[][] DIRECTIONS = {
        {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}
    };
    private static final float[][] PRIMARY = {
        {.72f,.18f,.23f}, {.34f,.31f,.68f}, {.30f,.46f,.64f}, {.56f,.24f,.16f}
    };
    private static final float[][] SECONDARY = {
        {.29f,.07f,.10f}, {.13f,.11f,.31f}, {.12f,.20f,.30f}, {.25f,.09f,.06f}
    };
    private static final float[][] ACCENT = {
        {1f,.48f,.28f}, {.48f,.92f,1f}, {.50f,.82f,1f}, {1f,.74f,.28f}
    };

    private final Texture texture;
    private final TextureRegion[] regions = new TextureRegion[TOTAL_TILES];

    private HighResBossDirectionalArt(Texture texture) {
        this.texture = texture;
        for (int tile = 0; tile < TOTAL_TILES; tile++) {
            int x = (tile % COLUMNS) * TILE;
            int y = (tile / COLUMNS) * TILE;
            regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
        }
    }

    public static HighResBossDirectionalArt create() {
        Pixmap pixmap = new Pixmap(width(), height(), Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.SourceOver);
        try {
            for (int actor = 0; actor < ACTOR_COUNT; actor++) drawActorSet(pixmap, actor, actor * ACTOR_BLOCK);
            Texture texture = new Texture(pixmap);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return new HighResBossDirectionalArt(texture);
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
        int raw = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
        int frame = count <= 1 ? 0 : (loop ? raw % count : Math.min(count - 1, raw));
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

    private static int actorIndex(String key) {
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

    private static void drawActorSet(Pixmap p, int actor, int base) {
        for (int d = 0; d < DIRECTIONS.length; d++) {
            int dx = DIRECTIONS[d][0], dy = DIRECTIONS[d][1];
            int b = base + d * FRAMES_PER_DIRECTION;
            drawFrame(p, b, actor, dx, dy, 0, 0);
            drawFrame(p, b + 1, actor, dx, dy, 0, 1);
            drawFrame(p, b + 2, actor, dx, dy, 1, 0);
            drawFrame(p, b + 3, actor, dx, dy, 1, 1);
            drawFrame(p, b + 4, actor, dx, dy, 1, 2);
            drawFrame(p, b + 5, actor, dx, dy, 2, 0);
            drawFrame(p, b + 6, actor, dx, dy, 2, 1);
            drawFrame(p, b + 7, actor, dx, dy, 3, 0);
            drawFrame(p, b + 8, actor, dx, dy, 3, 1);
            drawFrame(p, b + 9, actor, dx, dy, 4, 0);
            drawFrame(p, b + 10, actor, dx, dy, 4, 1);
            drawFrame(p, b + 11, actor, dx, dy, 4, 2);
        }
    }

    /** motion: 0 idle, 1 run, 2 attack, 3 hit, 4 death. */
    private static void drawFrame(Pixmap p, int tile, int actor, int dx, int dy, int motion, int frame) {
        int ox = (tile % COLUMNS) * TILE;
        int oy = (tile / COLUMNS) * TILE;
        int sx = dx, sy = -dy, px = -sy, py = sx;
        int bob = motion == 0 && frame == 1 ? -1 : motion == 1 ? (frame == 1 ? -2 : frame == 2 ? 1 : 0) : 0;
        int recoil = motion == 3 && frame == 1 ? 3 : 0;
        int reach = motion == 2 && frame == 1 ? 8 : 0;
        int cx = ox + 32 - sx * recoil;
        int cy = oy + 34 + bob - sy * recoil;

        set(p, 0f, 0f, 0f, .34f);
        p.fillCircle(ox + 32, oy + 53, 19);

        if (motion == 4) {
            int side = dx < 0 ? -1 : 1;
            int shift = frame * 5 * side;
            setSecondary(p, actor);
            p.fillCircle(ox + 32 + shift, oy + 38 + frame * 4, Math.max(10, 18 - frame * 2));
            setPrimary(p, actor);
            p.fillCircle(ox + 27 + shift, oy + 25 + frame * 6, Math.max(7, 13 - frame * 2));
            setAccent(p, actor);
            p.drawLine(ox + 16 + shift, oy + 31 + frame * 3, ox + 50 + shift, oy + 42 + frame * 3);
            return;
        }

        setSecondary(p, actor);
        p.fillCircle(cx, cy + 2, 18);
        setPrimary(p, actor);
        p.fillCircle(cx + sx * 3, cy - 16 + sy * 3, 12);
        for (int sign : new int[] {-1, 1}) {
            int shoulderX = cx + px * 17 * sign;
            int shoulderY = cy - 5 + py * 17 * sign;
            p.fillCircle(shoulderX, shoulderY, 7);
        }

        setAccent(p, actor);
        drawIdentity(p, actor, cx, cy, sx, sy, px, py, motion, frame);
        int faceX = cx + sx * 10, faceY = cy - 16 + sy * 10;
        p.fillCircle(faceX - px * 4, faceY - py * 4, 2);
        p.fillCircle(faceX + px * 4, faceY + py * 4, 2);

        setPrimary(p, actor);
        int handX = cx + sx * (14 + reach), handY = cy + sy * (14 + reach);
        p.drawLine(cx, cy, handX, handY);
        setAccent(p, actor);
        int tipX = cx + sx * (26 + reach), tipY = cy + sy * (26 + reach);
        p.drawLine(handX, handY, tipX, tipY);
        if (motion == 2 && frame == 1) {
            p.fillCircle(tipX, tipY, 6);
            p.drawLine(tipX, tipY, tipX + sx * 8 + px * 6, tipY + sy * 8 + py * 6);
            p.drawLine(tipX, tipY, tipX + sx * 8 - px * 6, tipY + sy * 8 - py * 6);
        }
        if (motion == 3) {
            set(p, 1f, .96f, .86f, 1f);
            p.drawLine(ox + 10, oy + 11, ox + 23, oy + 24);
            p.drawLine(ox + 10, oy + 24, ox + 23, oy + 11);
        }
    }

    private static void drawIdentity(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py,
                                     int motion, int frame) {
        switch (actor) {
            case 0 -> { // Alpha: crown/horns and rage arc
                p.drawLine(cx - px * 9, cy - 19 - py * 9, cx - px * 16 - sx * 7, cy - 29 - py * 16 - sy * 7);
                p.drawLine(cx + px * 9, cy - 19 + py * 9, cx + px * 16 - sx * 7, cy - 29 + py * 16 - sy * 7);
                p.drawCircle(cx, cy, 22);
            }
            case 1 -> { // Revenant: orbital cage and spectral tail
                p.drawCircle(cx, cy, 23);
                p.drawCircle(cx, cy, 15);
                p.drawLine(cx - sx * 11, cy - sy * 11, cx - sx * 26 + px * 7, cy - sy * 26 + py * 7);
            }
            case 2 -> { // Warden: slab shield and reinforced crossbar
                p.drawRectangle(cx - 17 + sx * 5, cy - 13 + sy * 5, 34, 27);
                p.drawLine(cx - px * 15, cy - py * 15, cx + px * 15, cy + py * 15);
            }
            case 3 -> { // Harvester: twin scythe wings
                p.drawLine(cx - px * 15, cy - py * 15, cx - px * 27 - sx * 9, cy - py * 27 - sy * 9);
                p.drawLine(cx + px * 15, cy + py * 15, cx + px * 27 - sx * 9, cy + py * 27 - sy * 9);
                p.drawCircle(cx - px * 27 - sx * 9, cy - py * 27 - sy * 9, 6);
                p.drawCircle(cx + px * 27 - sx * 9, cy + py * 27 - sy * 9, 6);
            }
            default -> { }
        }
        if (motion == 0 && frame == 1) p.drawCircle(cx + sx * 2, cy + sy * 2, 25);
    }

    private static void setPrimary(Pixmap p, int actor) { setPalette(p, PRIMARY[actor]); }
    private static void setSecondary(Pixmap p, int actor) { setPalette(p, SECONDARY[actor]); }
    private static void setAccent(Pixmap p, int actor) { setPalette(p, ACCENT[actor]); }
    private static void setPalette(Pixmap p, float[] c) { p.setColor(c[0], c[1], c[2], 1f); }
    private static void set(Pixmap p, float r, float g, float b, float a) { p.setColor(r, g, b, a); }

    @Override public void dispose() { texture.dispose(); }
}
