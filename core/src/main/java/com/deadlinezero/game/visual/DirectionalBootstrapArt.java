package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Deterministic eight-way bootstrap sheet for the core combat roster.
 * Generated once during art initialization and served from prebuilt TextureRegions.
 * Final atlas frames always override this bootstrap layer.
 */
public final class DirectionalBootstrapArt implements Disposable {
    static final int TILE = 32;
    static final int COLUMNS = 20;
    static final int FRAMES_PER_DIRECTION = 10;
    static final int ACTOR_BLOCK = FRAMES_PER_DIRECTION * 8;
    static final int ACTOR_COUNT = 14;
    static final int TOTAL_TILES = ACTOR_BLOCK * ACTOR_COUNT;

    private static final String[] ROOTS = {
        "survivor/rex/",
        "enemy/shambler/",
        "enemy/runner/",
        "enemy/brute/",
        "enemy/ranged/",
        "enemy/elite/",
        "boss/alpha/",
        "boss/revenant/",
        "boss/warden/",
        "boss/harvester/",
        "survivor/nyx/",
        "survivor/bastion/",
        "survivor/volt/",
        "survivor/wraith/"
    };

    private final Texture texture;
    private final TextureRegion[] regions = new TextureRegion[TOTAL_TILES];

    private DirectionalBootstrapArt(Texture texture) {
        this.texture = texture;
        for (int tile = 0; tile < regions.length; tile++) {
            int x = (tile % COLUMNS) * TILE;
            int y = (tile / COLUMNS) * TILE;
            regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
        }
    }

    public static DirectionalBootstrapArt create() {
        int rows = (TOTAL_TILES + COLUMNS - 1) / COLUMNS;
        Pixmap pixmap = new Pixmap(COLUMNS * TILE, rows * TILE, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.SourceOver);
        try {
            for (int actor = 0; actor < ACTOR_COUNT; actor++) drawActorSet(pixmap, actor, actor * ACTOR_BLOCK);
            Texture texture = new Texture(pixmap);
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            return new DirectionalBootstrapArt(texture);
        } finally {
            pixmap.dispose();
        }
    }

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
        int slash = key.lastIndexOf('/');
        String motion = key.substring(slash + 1);
        return switch (motion) {
            case "idle", "hit" -> 1;
            case "attack" -> 2;
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
            case "n" -> 0;
            case "ne" -> 1;
            case "e" -> 2;
            case "se" -> 3;
            case "s" -> 4;
            case "sw" -> 5;
            case "w" -> 6;
            case "nw" -> 7;
            default -> -1;
        };
    }

    private static int motionOffset(String motion) {
        return switch (motion) {
            case "idle" -> 0;
            case "run" -> 1;
            case "attack" -> 4;
            case "hit" -> 6;
            case "death" -> 7;
            default -> -1;
        };
    }

    private static void drawActorSet(Pixmap p, int actor, int actorBase) {
        int[][] directions = {
            {0, 1}, {1, 1}, {1, 0}, {1, -1},
            {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}
        };
        for (int direction = 0; direction < directions.length; direction++) {
            int dx = directions[direction][0];
            int dy = directions[direction][1];
            int base = actorBase + direction * FRAMES_PER_DIRECTION;
            drawFrame(p, base, actor, dx, dy, 0, 0);
            drawFrame(p, base + 1, actor, dx, dy, 1, 0);
            drawFrame(p, base + 2, actor, dx, dy, 1, 1);
            drawFrame(p, base + 3, actor, dx, dy, 1, 2);
            drawFrame(p, base + 4, actor, dx, dy, 2, 0);
            drawFrame(p, base + 5, actor, dx, dy, 2, 1);
            drawFrame(p, base + 6, actor, dx, dy, 3, 0);
            drawFrame(p, base + 7, actor, dx, dy, 4, 0);
            drawFrame(p, base + 8, actor, dx, dy, 4, 1);
            drawFrame(p, base + 9, actor, dx, dy, 4, 2);
        }
    }

    /** motion: 0 idle, 1 run, 2 attack, 3 hit, 4 death. */
    private static void drawFrame(Pixmap p, int tile, int actor, int dx, int dy, int motion, int frame) {
        int ox = (tile % COLUMNS) * TILE;
        int oy = (tile / COLUMNS) * TILE;
        int sx = dx;
        int sy = -dy;
        int bob = motion == 1 && frame == 1 ? -1 : 0;
        int stride = motion == 1 ? (frame == 0 ? -2 : frame == 1 ? 2 : 0) : 0;
        int attackReach = motion == 2 && frame == 1 ? (isBoss(actor) ? 5 : 4) : 0;
        int bodyRadius = bodyRadius(actor);

        set(p, 0, 0, 0, isBoss(actor) ? 105 : 80);
        p.fillCircle(ox + 16, oy + 26, motion == 4 ? bodyRadius - 2 : bodyRadius + 1);

        if (motion == 4) {
            int side = dx < 0 ? -1 : 1;
            int shift = frame * 3 * side;
            setSecondary(p, actor);
            p.fillCircle(ox + 16 + shift, oy + 18 + frame * 3, Math.max(4, bodyRadius - frame));
            setPrimary(p, actor);
            p.fillCircle(ox + 13 + shift, oy + 12 + frame * 4, Math.max(3, bodyRadius - 2 - frame));
            setAccent(p, actor);
            p.drawLine(ox + 8 + shift, oy + 14 + frame * 3, ox + 23 + shift, oy + 20 + frame * 3);
            return;
        }

        int cx = ox + 16;
        int cy = oy + 16 + bob;
        int perpX = -sy;
        int perpY = sx;

        setPrimary(p, actor);
        p.drawLine(cx - perpX * 3, cy + 6 - perpY * 2, cx - perpX * 3 + sx * stride, oy + 29 - perpY * 2);
        p.drawLine(cx + perpX * 3, cy + 6 + perpY * 2, cx + perpX * 3 - sx * stride, oy + 29 + perpY * 2);

        setSecondary(p, actor);
        p.fillCircle(cx, cy + 2, bodyRadius);
        setPrimary(p, actor);
        p.fillCircle(cx + sx * 2, cy - 7 + sy * 2, Math.max(5, bodyRadius - 2));

        drawIdentitySilhouette(p, actor, cx, cy, sx, sy, perpX, perpY);

        int faceX = cx + sx * 5;
        int faceY = cy - 7 + sy * 5;
        setAccent(p, actor);
        if (isSurvivor(actor) || actor == 4) {
            p.drawLine(faceX - perpX * 3, faceY - perpY * 3, faceX + perpX * 3, faceY + perpY * 3);
        } else {
            p.fillCircle(faceX - perpX * 2, faceY - perpY * 2, 1);
            p.fillCircle(faceX + perpX * 2, faceY + perpY * 2, 1);
        }

        setPrimary(p, actor);
        int handX = cx + sx * (7 + attackReach);
        int handY = cy + 2 + sy * (7 + attackReach);
        p.drawLine(cx, cy + 2, handX, handY);

        if (isSurvivor(actor) || actor == 4) {
            setAccent(p, actor);
            int muzzleX = cx + sx * (12 + attackReach);
            int muzzleY = cy + 2 + sy * (12 + attackReach);
            p.drawLine(handX, handY, muzzleX, muzzleY);
            if (motion == 2 && frame == 1) {
                set(p, 1f, .83f, .35f, 1f);
                p.fillCircle(muzzleX + sx * 2, muzzleY + sy * 2, 2);
            }
        } else {
            int clawX = cx + sx * (5 + attackReach) - perpX * 7;
            int clawY = cy + 3 + sy * (5 + attackReach) - perpY * 7;
            p.drawLine(cx - perpX * 2, cy + 3 - perpY * 2, clawX, clawY);
        }

        if (motion == 3) {
            set(p, 1f, .88f, .82f, 1f);
            p.drawLine(ox + 5, oy + 6, ox + 12, oy + 13);
            p.drawLine(ox + 5, oy + 12, ox + 13, oy + 6);
        }
    }

    private static void drawIdentitySilhouette(Pixmap p, int actor, int cx, int cy, int sx, int sy, int perpX, int perpY) {
        setAccent(p, actor);
        switch (actor) {
            case 3 -> {
                p.drawLine(cx - perpX * 8, cy, cx + perpX * 8, cy);
                p.drawLine(cx - perpX * 7, cy + 1, cx + perpX * 7, cy + 1);
            }
            case 4 -> p.drawLine(cx + sx * 4, cy + sy * 4, cx + sx * 13, cy + sy * 13);
            case 5 -> {
                p.drawLine(cx - perpX * 5, cy - 7 - perpY * 5, cx - perpX * 7 + sx * 2, cy - 12 - perpY * 7 + sy * 2);
                p.drawLine(cx + perpX * 5, cy - 7 + perpY * 5, cx + perpX * 7 + sx * 2, cy - 12 + perpY * 7 + sy * 2);
            }
            case 6 -> {
                p.drawLine(cx - perpX * 5, cy - 7, cx - perpX * 8 - sx * 2, cy - 11 - sy * 2);
                p.drawLine(cx + perpX * 5, cy - 7, cx + perpX * 8 - sx * 2, cy - 11 - sy * 2);
            }
            case 7 -> {
                p.drawCircle(cx, cy - 7, 8);
                p.drawLine(cx - perpX * 7, cy - 7 - perpY * 7, cx + perpX * 7, cy - 7 + perpY * 7);
            }
            case 8 -> p.drawRectangle(cx - 7, cy - 3, 14, 10);
            case 9 -> {
                p.drawCircle(cx + sx * 5, cy + sy * 5, 9);
                setSecondary(p, actor);
                p.fillCircle(cx + sx * 2, cy + sy * 2, 6);
            }
            case 10 -> {
                p.drawLine(cx - perpX * 4, cy - 6 - perpY * 4, cx - perpX * 6 - sx * 2, cy - 12 - perpY * 6 - sy * 2);
                p.drawLine(cx + perpX * 4, cy - 6 + perpY * 4, cx + perpX * 6 - sx * 2, cy - 12 + perpY * 6 - sy * 2);
            }
            case 11 -> {
                p.drawRectangle(cx - 8, cy - 2, 16, 7);
                p.drawLine(cx - perpX * 8, cy + 5, cx + perpX * 8, cy + 5);
            }
            case 12 -> {
                p.drawCircle(cx, cy + 1, 9);
                p.drawLine(cx - 6, cy - 8, cx - 2, cy - 12);
                p.drawLine(cx + 2, cy - 12, cx + 6, cy - 8);
            }
            case 13 -> {
                p.drawCircle(cx + sx, cy - 6 + sy, 7);
                p.drawLine(cx - perpX * 6, cy + 5 - perpY * 6, cx + sx * 2, cy + 9 + sy * 2);
                p.drawLine(cx + perpX * 6, cy + 5 + perpY * 6, cx + sx * 2, cy + 9 + sy * 2);
            }
            default -> { }
        }
    }

    private static int bodyRadius(int actor) {
        if (actor == 3 || actor == 11) return 9;
        if (isBoss(actor)) return 10;
        return isSurvivor(actor) ? 7 : 8;
    }

    private static boolean isBoss(int actor) { return actor >= 6 && actor <= 9; }

    private static boolean isSurvivor(int actor) { return actor == 0 || actor >= 10; }

    private static void setPrimary(Pixmap p, int actor) {
        float[][] colors = {
            {.18f,.72f,.94f}, {.42f,.64f,.36f}, {.72f,.47f,.28f}, {.55f,.34f,.32f}, {.30f,.52f,.72f},
            {.64f,.28f,.70f}, {.72f,.18f,.23f}, {.34f,.31f,.68f}, {.30f,.46f,.64f}, {.56f,.24f,.16f},
            {.68f,.30f,.88f}, {.82f,.62f,.22f}, {.24f,.82f,.66f}, {.72f,.78f,.90f}
        };
        set(p, colors[actor][0], colors[actor][1], colors[actor][2], 1f);
    }

    private static void setSecondary(Pixmap p, int actor) {
        float[][] colors = {
            {.08f,.28f,.44f}, {.19f,.31f,.17f}, {.31f,.19f,.13f}, {.25f,.14f,.14f}, {.12f,.23f,.35f},
            {.26f,.10f,.30f}, {.29f,.07f,.10f}, {.13f,.11f,.31f}, {.12f,.20f,.30f}, {.25f,.09f,.06f},
            {.25f,.10f,.38f}, {.38f,.28f,.10f}, {.08f,.34f,.28f}, {.20f,.23f,.34f}
        };
        set(p, colors[actor][0], colors[actor][1], colors[actor][2], 1f);
    }

    private static void setAccent(Pixmap p, int actor) {
        float[][] colors = {
            {.76f,.96f,1f}, {.94f,.30f,.25f}, {1f,.61f,.30f}, {1f,.32f,.26f}, {.40f,.90f,1f},
            {1f,.38f,.92f}, {1f,.48f,.28f}, {.48f,.92f,1f}, {.50f,.82f,1f}, {1f,.74f,.28f},
            {1f,.42f,1f}, {1f,.90f,.52f}, {.62f,1f,.86f}, {.52f,.90f,1f}
        };
        set(p, colors[actor][0], colors[actor][1], colors[actor][2], 1f);
    }

    private static void set(Pixmap p, int r, int g, int b, int a) {
        p.setColor(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    private static void set(Pixmap p, float r, float g, float b, float a) { p.setColor(r, g, b, a); }

    @Override public void dispose() { texture.dispose(); }
}
