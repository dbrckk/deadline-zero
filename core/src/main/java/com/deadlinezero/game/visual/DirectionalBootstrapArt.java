package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Small deterministic eight-way vertical-slice sheet for REX, Shambler and Runner.
 * It is generated once during art initialization, then served from prebuilt regions with no
 * per-frame TextureRegion allocation. Final atlas frames still override this bootstrap layer.
 */
public final class DirectionalBootstrapArt implements Disposable {
    static final int TILE = 32;
    static final int COLUMNS = 16;
    static final int FRAMES_PER_DIRECTION = 10;
    static final int ACTOR_BLOCK = FRAMES_PER_DIRECTION * 8;
    static final int TOTAL_TILES = ACTOR_BLOCK * 3;

    private static final int REX_BASE = 0;
    private static final int SHAMBLER_BASE = ACTOR_BLOCK;
    private static final int RUNNER_BASE = ACTOR_BLOCK * 2;

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
            drawActorSet(pixmap, 0, REX_BASE);
            drawActorSet(pixmap, 1, SHAMBLER_BASE);
            drawActorSet(pixmap, 2, RUNNER_BASE);
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
        int actorBase;
        String rest;
        if (key.startsWith("survivor/rex/")) {
            actorBase = REX_BASE;
            rest = key.substring("survivor/rex/".length());
        } else if (key.startsWith("enemy/shambler/")) {
            actorBase = SHAMBLER_BASE;
            rest = key.substring("enemy/shambler/".length());
        } else if (key.startsWith("enemy/runner/")) {
            actorBase = RUNNER_BASE;
            rest = key.substring("enemy/runner/".length());
        } else {
            return -1;
        }

        int slash = rest.indexOf('/');
        if (slash <= 0 || slash >= rest.length() - 1) return -1;
        int direction = directionIndex(rest.substring(0, slash));
        int motion = motionOffset(rest.substring(slash + 1));
        if (direction < 0 || motion < 0) return -1;
        return actorBase + direction * FRAMES_PER_DIRECTION + motion;
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
            drawFrame(p, base, actor, dx, dy, 0, 0);       // idle
            drawFrame(p, base + 1, actor, dx, dy, 1, 0);   // run 0
            drawFrame(p, base + 2, actor, dx, dy, 1, 1);   // run 1
            drawFrame(p, base + 3, actor, dx, dy, 1, 2);   // run 2
            drawFrame(p, base + 4, actor, dx, dy, 2, 0);   // attack 0
            drawFrame(p, base + 5, actor, dx, dy, 2, 1);   // attack 1
            drawFrame(p, base + 6, actor, dx, dy, 3, 0);   // hit
            drawFrame(p, base + 7, actor, dx, dy, 4, 0);   // death 0
            drawFrame(p, base + 8, actor, dx, dy, 4, 1);   // death 1
            drawFrame(p, base + 9, actor, dx, dy, 4, 2);   // death 2
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
        int attackReach = motion == 2 && frame == 1 ? 4 : 0;

        set(p, 0, 0, 0, 80);
        p.fillCircle(ox + 16, oy + 25, motion == 4 ? 6 : 8);

        if (motion == 4) {
            int side = dx < 0 ? -1 : 1;
            int shift = frame * 3 * side;
            setSecondary(p, actor);
            p.fillCircle(ox + 16 + shift, oy + 18 + frame * 3, Math.max(4, 7 - frame));
            setPrimary(p, actor);
            p.fillCircle(ox + 13 + shift, oy + 13 + frame * 4, Math.max(3, 5 - frame));
            setAccent(p, actor);
            p.drawLine(ox + 9 + shift, oy + 14 + frame * 3, ox + 22 + shift, oy + 20 + frame * 3);
            return;
        }

        int cx = ox + 16;
        int cy = oy + 16 + bob;

        setPrimary(p, actor);
        int perpX = -sy;
        int perpY = sx;
        p.drawLine(cx - perpX * 3, cy + 6 - perpY * 2, cx - perpX * 3 + sx * stride, oy + 29 - perpY * 2);
        p.drawLine(cx + perpX * 3, cy + 6 + perpY * 2, cx + perpX * 3 - sx * stride, oy + 29 + perpY * 2);

        setSecondary(p, actor);
        p.fillCircle(cx, cy + 2, actor == 0 ? 7 : 8);
        setPrimary(p, actor);
        p.fillCircle(cx + sx * 2, cy - 7 + sy * 2, actor == 0 ? 5 : 6);

        int faceX = cx + sx * 5;
        int faceY = cy - 7 + sy * 5;
        setAccent(p, actor);
        if (actor == 0) {
            p.drawLine(faceX - perpX * 3, faceY - perpY * 3, faceX + perpX * 3, faceY + perpY * 3);
        } else {
            p.fillCircle(faceX - perpX * 2, faceY - perpY * 2, 1);
            p.fillCircle(faceX + perpX * 2, faceY + perpY * 2, 1);
        }

        setPrimary(p, actor);
        int handX = cx + sx * (7 + attackReach);
        int handY = cy + 2 + sy * (7 + attackReach);
        p.drawLine(cx, cy + 2, handX, handY);

        if (actor == 0) {
            setAccent(p, actor);
            int muzzleX = cx + sx * (12 + attackReach);
            int muzzleY = cy + 2 + sy * (12 + attackReach);
            p.drawLine(handX, handY, muzzleX, muzzleY);
            if (motion == 2 && frame == 1) {
                set(p, 1f, .83f, .35f, 1f);
                p.fillCircle(muzzleX + sx * 2, muzzleY + sy * 2, 2);
            }
        } else {
            int clawX = cx + sx * 5 - perpX * 7;
            int clawY = cy + 3 + sy * 5 - perpY * 7;
            p.drawLine(cx - perpX * 2, cy + 3 - perpY * 2, clawX, clawY);
        }

        if (motion == 3) {
            set(p, 1f, .88f, .82f, 1f);
            p.drawLine(ox + 5, oy + 6, ox + 12, oy + 13);
            p.drawLine(ox + 5, oy + 12, ox + 13, oy + 6);
        }
    }

    private static void setPrimary(Pixmap p, int actor) {
        if (actor == 0) set(p, .18f, .72f, .94f, 1f);
        else if (actor == 1) set(p, .42f, .64f, .36f, 1f);
        else set(p, .72f, .47f, .28f, 1f);
    }

    private static void setSecondary(Pixmap p, int actor) {
        if (actor == 0) set(p, .08f, .28f, .44f, 1f);
        else if (actor == 1) set(p, .19f, .31f, .17f, 1f);
        else set(p, .31f, .19f, .13f, 1f);
    }

    private static void setAccent(Pixmap p, int actor) {
        if (actor == 0) set(p, .76f, .96f, 1f, 1f);
        else if (actor == 1) set(p, .94f, .30f, .25f, 1f);
        else set(p, 1f, .61f, .30f, 1f);
    }

    private static void set(Pixmap p, int r, int g, int b, int a) {
        p.setColor(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    private static void set(Pixmap p, float r, float g, float b, float a) {
        p.setColor(r, g, b, a);
    }

    @Override public void dispose() { texture.dispose(); }
}
