package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Shipped/file-backed core directional art gateway. A complete seven-actor raster can replace
 * every high-resolution core bootstrap at once, while smaller authored actor sheets can migrate
 * incrementally without breaking the generated fallbacks. It also owns lazy 64px boss art.
 */
public final class AuthoredCoreDirectionalArt implements Disposable {
    static final String PATH = "art/core_authored.png";
    static final String REX_PATH = "art/rex_authored.png";
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

    private final Texture texture;
    private final TextureRegion[] regions;
    private final Texture rexTexture;
    private final TextureRegion[] rexRegions;
    private HighResBossDirectionalArt bossArt;
    private boolean bossArtAttempted;

    private AuthoredCoreDirectionalArt(Texture texture, Texture rexTexture) {
        this.texture = texture;
        this.rexTexture = rexTexture;
        regions = texture == null ? null : split(texture, TOTAL_TILES);
        rexRegions = rexTexture == null ? null : split(rexTexture, ACTOR_BLOCK);
    }

    public static AuthoredCoreDirectionalArt create() {
        Texture coreTexture = loadValidated(PATH, width(), height(), "authored core");
        // The complete sheet always wins. Avoid loading the partial REX texture at the same time.
        Texture rexTexture = coreTexture == null
            ? loadValidated(REX_PATH, COLUMNS * TILE, rexRows() * TILE, "authored REX")
            : null;
        // Keep the gateway alive even when no raster is present so boss art remains lazy.
        return new AuthoredCoreDirectionalArt(coreTexture, rexTexture);
    }

    private static Texture loadValidated(String path, int expectedWidth, int expectedHeight, String label) {
        if (!Gdx.files.internal(path).exists()) return null;
        Texture loaded = new Texture(Gdx.files.internal(path));
        if (loaded.getWidth() != expectedWidth || loaded.getHeight() != expectedHeight) {
            loaded.dispose();
            throw new IllegalStateException("Invalid " + label + " sheet dimensions: expected "
                + expectedWidth + "x" + expectedHeight);
        }
        loaded.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return loaded;
    }

    private static TextureRegion[] split(Texture source, int tileCount) {
        TextureRegion[] result = new TextureRegion[tileCount];
        for (int tile = 0; tile < tileCount; tile++) {
            int x = (tile % COLUMNS) * TILE;
            int y = (tile / COLUMNS) * TILE;
            result[tile] = new TextureRegion(source, x, y, TILE, TILE);
        }
        return result;
    }

    static int rows() { return (TOTAL_TILES + COLUMNS - 1) / COLUMNS; }
    static int rexRows() { return (ACTOR_BLOCK + COLUMNS - 1) / COLUMNS; }
    static int width() { return COLUMNS * TILE; }
    static int height() { return rows() * TILE; }

    public boolean supports(String key) {
        return firstTile(key) >= 0 || HighResBossDirectionalArt.firstTile(key) >= 0;
    }

    public TextureRegion region(String key, float stateTime, float frameDuration, boolean loop) {
        int first = firstTile(key);
        if (first >= 0) {
            TextureRegion[] source = regions;
            int sourceFirst = first;
            if (source == null && first < ACTOR_BLOCK && rexRegions != null) {
                source = rexRegions;
                sourceFirst = first;
            }
            if (source != null) {
                int count = frameCount(key);
                int raw = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
                int frame = count <= 1 ? 0 : (loop ? raw % count : Math.min(count - 1, raw));
                return source[sourceFirst + frame];
            }
        }

        if (HighResBossDirectionalArt.firstTile(key) < 0) return null;
        HighResBossDirectionalArt boss = ensureBossArt();
        return boss == null ? null : boss.region(key, stateTime, frameDuration, loop);
    }

    private HighResBossDirectionalArt ensureBossArt() {
        if (bossArtAttempted) return bossArt;
        bossArtAttempted = true;
        try {
            bossArt = HighResBossDirectionalArt.create();
        } catch (RuntimeException ignored) {
            bossArt = null;
        }
        return bossArt;
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

    @Override public void dispose() {
        if (bossArt != null) bossArt.dispose();
        if (texture != null) texture.dispose();
        if (rexTexture != null) rexTexture.dispose();
    }
}
