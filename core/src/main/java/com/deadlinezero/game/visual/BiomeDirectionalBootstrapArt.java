package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.deadlinezero.game.world.BiomeEnemyRoster;

/**
 * Dedicated generated directional art for biome-signature enemies and the Null Archon.
 * Final atlas regions still override this layer through GameArt.
 */
public final class BiomeDirectionalBootstrapArt implements Disposable {
    static final int TILE = 32;
    static final int COLUMNS = 20;
    static final int FRAMES_PER_DIRECTION = 10;
    static final int ACTOR_BLOCK = FRAMES_PER_DIRECTION * 8;

    private static final String[] ROOTS = {
        "enemy/biome/forge_hound/",
        "enemy/biome/cinder_gunner/",
        "enemy/biome/slag_guard/",
        "enemy/biome/phase_stalker/",
        "enemy/biome/static_seer/",
        "enemy/biome/null_ward/",
        "boss/null_archon/"
    };
    static final int TOTAL_TILES = ACTOR_BLOCK * ROOTS.length;

    private final Texture texture;
    private final TextureRegion[] regions = new TextureRegion[TOTAL_TILES];

    private BiomeDirectionalBootstrapArt(Texture texture) {
        this.texture = texture;
        for (int tile = 0; tile < regions.length; tile++) {
            int x = (tile % COLUMNS) * TILE;
            int y = (tile / COLUMNS) * TILE;
            regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
        }
    }

    public static BiomeDirectionalBootstrapArt create() {
        int rows = (TOTAL_TILES + COLUMNS - 1) / COLUMNS;
        Pixmap p = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
        p.setBlending(Pixmap.Blending.SourceOver);
        try {
            for (int actor = 0; actor < ROOTS.length; actor++) drawActorSet(p, actor, actor * ACTOR_BLOCK);
            Texture texture = new Texture(p);
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            return new BiomeDirectionalBootstrapArt(texture);
        } finally {
            p.dispose();
        }
    }

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
            case "idle", "hit" -> 1;
            case "attack" -> 2;
            case "run", "death" -> 3;
            default -> 0;
        };
    }

    static String root(BiomeEnemyRoster.Identity identity) {
        if (identity == null || identity == BiomeEnemyRoster.Identity.NONE) return null;
        return "enemy/biome/" + identity.name().toLowerCase();
    }

    private static int actorIndex(String key) {
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
            case "idle" -> 0; case "run" -> 1; case "attack" -> 4; case "hit" -> 6; case "death" -> 7;
            default -> -1;
        };
    }

    private static void drawActorSet(Pixmap p, int actor, int base) {
        int[][] dirs = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
        for (int d = 0; d < dirs.length; d++) {
            int b = base + d * FRAMES_PER_DIRECTION;
            drawFrame(p, b, actor, dirs[d][0], dirs[d][1], 0, 0);
            drawFrame(p, b + 1, actor, dirs[d][0], dirs[d][1], 1, 0);
            drawFrame(p, b + 2, actor, dirs[d][0], dirs[d][1], 1, 1);
            drawFrame(p, b + 3, actor, dirs[d][0], dirs[d][1], 1, 2);
            drawFrame(p, b + 4, actor, dirs[d][0], dirs[d][1], 2, 0);
            drawFrame(p, b + 5, actor, dirs[d][0], dirs[d][1], 2, 1);
            drawFrame(p, b + 6, actor, dirs[d][0], dirs[d][1], 3, 0);
            drawFrame(p, b + 7, actor, dirs[d][0], dirs[d][1], 4, 0);
            drawFrame(p, b + 8, actor, dirs[d][0], dirs[d][1], 4, 1);
            drawFrame(p, b + 9, actor, dirs[d][0], dirs[d][1], 4, 2);
        }
    }

    private static void drawFrame(Pixmap p, int tile, int actor, int dx, int dy, int motion, int frame) {
        int ox = (tile % COLUMNS) * TILE;
        int oy = (tile / COLUMNS) * TILE;
        int sx = dx, sy = -dy;
        int px = -sy, py = sx;
        int bob = motion == 1 && frame == 1 ? -1 : 0;
        int cx = ox + 16, cy = oy + 17 + bob;
        int radius = actor == 6 ? 10 : (actor == 2 || actor == 5 ? 9 : 7);

        if (motion == 4) {
            primary(p, actor);
            p.drawLine(ox + 7 + frame * 2, oy + 17 + frame * 3, ox + 24 + frame, oy + 22 + frame * 3);
            secondary(p, actor);
            p.fillCircle(ox + 16 + frame * 2, oy + 18 + frame * 3, Math.max(3, radius - frame));
            return;
        }

        secondary(p, actor);
        p.fillCircle(cx, cy, radius);
        primary(p, actor);
        p.fillCircle(cx + sx * 2, cy - 7 + sy * 2, Math.max(4, radius - 2));
        p.drawLine(cx - px * 3, cy + 5 - py * 2, cx - px * 4, oy + 29 - py * 2);
        p.drawLine(cx + px * 3, cy + 5 + py * 2, cx + px * 4, oy + 29 + py * 2);

        accent(p, actor);
        drawSignature(p, actor, cx, cy, sx, sy, px, py, motion, frame);

        int reach = motion == 2 && frame == 1 ? (actor == 6 ? 7 : 4) : 0;
        primary(p, actor);
        p.drawLine(cx, cy + 1, cx + sx * (7 + reach), cy + 1 + sy * (7 + reach));
        if (motion == 3) {
            p.setColor(1f, .95f, .88f, 1f);
            p.drawLine(ox + 5, oy + 7, ox + 12, oy + 14);
            p.drawLine(ox + 5, oy + 14, ox + 12, oy + 7);
        }
    }

    private static void drawSignature(Pixmap p, int actor, int cx, int cy, int sx, int sy, int px, int py, int motion, int frame) {
        switch (actor) {
            case 0 -> { // forge hound: low quadruped jaw + furnace spine
                p.drawLine(cx - px * 8, cy + 4 - py * 8, cx + px * 8, cy + 4 + py * 8);
                p.drawLine(cx - sx * 7, cy - sy * 7, cx - sx * 12, cy - sy * 12);
                p.fillCircle(cx + sx * 7, cy - 5 + sy * 7, 2);
            }
            case 1 -> { // cinder gunner: shoulder tanks + long barrel
                p.fillCircle(cx - px * 8, cy - py * 8, 3);
                p.fillCircle(cx + px * 8, cy + py * 8, 3);
                p.drawLine(cx + sx * 5, cy + sy * 5, cx + sx * (13 + (motion == 2 ? frame * 3 : 0)), cy + sy * (13 + (motion == 2 ? frame * 3 : 0)));
            }
            case 2 -> { // slag guard: slab shield
                p.drawRectangle(cx - 8 + sx * 3, cy - 7 + sy * 3, 16, 13);
                p.drawLine(cx - px * 7, cy - py * 7, cx + px * 7, cy + py * 7);
            }
            case 3 -> { // phase stalker: split horns and phase tail
                p.drawLine(cx - px * 4, cy - 7 - py * 4, cx - px * 8 - sx * 3, cy - 13 - py * 8 - sy * 3);
                p.drawLine(cx + px * 4, cy - 7 + py * 4, cx + px * 8 - sx * 3, cy - 13 + py * 8 - sy * 3);
                p.drawLine(cx - sx * 6, cy - sy * 6, cx - sx * 13 + px * 4, cy - sy * 13 + py * 4);
            }
            case 4 -> { // static seer: cyclops halo + antenna
                p.drawCircle(cx + sx * 3, cy - 7 + sy * 3, 7);
                p.fillCircle(cx + sx * 5, cy - 7 + sy * 5, 2);
                p.drawLine(cx - px * 8, cy - py * 8, cx + px * 8, cy + py * 8);
            }
            case 5 -> { // null ward: floating ring cage
                p.drawCircle(cx, cy, 10);
                p.drawCircle(cx, cy, 6);
                p.drawLine(cx - px * 10, cy - py * 10, cx + px * 10, cy + py * 10);
            }
            case 6 -> { // null archon: crown, orbit ring, bifurcated mantle
                p.drawCircle(cx + sx * 2, cy + sy * 2, 11);
                p.drawLine(cx - px * 7, cy - 9 - py * 7, cx - px * 10 - sx * 3, cy - 15 - py * 10 - sy * 3);
                p.drawLine(cx + px * 7, cy - 9 + py * 7, cx + px * 10 - sx * 3, cy - 15 + py * 10 - sy * 3);
                p.drawLine(cx - px * 9, cy + 7 - py * 9, cx - px * 13, cy + 14 - py * 13);
                p.drawLine(cx + px * 9, cy + 7 + py * 9, cx + px * 13, cy + 14 + py * 13);
            }
            default -> { }
        }
    }

    private static void primary(Pixmap p, int actor) {
        float[][] c = {
            {.95f,.30f,.10f},{.72f,.18f,.08f},{.42f,.22f,.16f},
            {.34f,.26f,.78f},{.22f,.56f,.92f},{.30f,.20f,.58f},{.38f,.24f,.78f}
        };
        p.setColor(c[actor][0], c[actor][1], c[actor][2], 1f);
    }

    private static void secondary(Pixmap p, int actor) {
        float[][] c = {
            {.20f,.16f,.14f},{.18f,.15f,.14f},{.25f,.24f,.24f},
            {.10f,.12f,.24f},{.10f,.18f,.28f},{.13f,.12f,.22f},{.10f,.09f,.20f}
        };
        p.setColor(c[actor][0], c[actor][1], c[actor][2], 1f);
    }

    private static void accent(Pixmap p, int actor) {
        float[][] c = {
            {1f,.72f,.18f},{1f,.48f,.12f},{1f,.50f,.18f},
            {.74f,.58f,1f},{.42f,.92f,1f},{.66f,.48f,1f},{.76f,.62f,1f}
        };
        p.setColor(c[actor][0], c[actor][1], c[actor][2], 1f);
    }

    @Override public void dispose() { texture.dispose(); }
}
