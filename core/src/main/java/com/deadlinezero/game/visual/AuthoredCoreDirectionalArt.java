package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Shipped directional sprite sheet for the three most visible core combat identities.
 * This is file-backed production art, not a runtime Pixmap generator.
 */
public final class AuthoredCoreDirectionalArt implements Disposable {
    static final String PATH = "art/core_authored.png";
    static final int TILE = 48;
    static final int COLUMNS = 16;
    static final int FRAMES_PER_DIRECTION = 12;
    static final int ACTOR_BLOCK = FRAMES_PER_DIRECTION * 8;
    static final int ACTOR_COUNT = 3;
    static final int TOTAL_TILES = ACTOR_BLOCK * ACTOR_COUNT;

    private static final String[] ROOTS = {
        "survivor/rex/", "enemy/shambler/", "enemy/runner/"
    };

    private final Texture texture;
    private final TextureRegion[] regions = new TextureRegion[TOTAL_TILES];

    private AuthoredCoreDirectionalArt(Texture texture) {
        this.texture = texture;
        for (int tile = 0; tile < TOTAL_TILES; tile++) {
            int x = (tile % COLUMNS) * TILE;
            int y = (tile / COLUMNS) * TILE;
            regions[tile] = new TextureRegion(texture, x, y, TILE, TILE);
        }
    }

    public static AuthoredCoreDirectionalArt create() {
        if (!Gdx.files.internal(PATH).exists()) return null;
        Texture texture = new Texture(Gdx.files.internal(PATH));
        int expectedWidth = width();
        int expectedHeight = height();
        if (texture.getWidth() != expectedWidth || texture.getHeight() != expectedHeight) {
            texture.dispose();
            throw new IllegalStateException(
                "Invalid authored core sheet dimensions: expected " + expectedWidth + "x" + expectedHeight);
        }
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return new AuthoredCoreDirectionalArt(texture);
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

    @Override public void dispose() { texture.dispose(); }
}
