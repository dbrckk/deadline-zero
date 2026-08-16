package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.SurvivorCatalog;
import com.deadlinezero.game.world.BiomeEnemyRoster;
import java.io.ByteArrayOutputStream;

/**
 * Authored-art gateway. Final atlas art takes priority, then generated directional/VFX bootstrap
 * layers, followed by compact legacy bootstrap art and finally the procedural emergency fallback.
 */
public final class GameArt implements Disposable {
    public enum Motion { IDLE, RUN, ATTACK, HIT, DEATH }

    private static final String ATLAS_PATH = "art/game.atlas";
    private static final String BOOTSTRAP_PATH = "art/game.png.b64";
    private static final int LEGACY_BOOTSTRAP_TILES = 16;

    private TextureAtlas atlas;
    private DirectionalBootstrapArt directionalBootstrap;
    private BiomeDirectionalBootstrapArt biomeDirectionalBootstrap;
    private BootstrapVfxArt vfxBootstrap;
    private Texture bootstrapTexture;
    private TextureRegion[] bootstrapRegions;
    private Texture fallbackTexture;
    private TextureRegion fallbackRegion;
    private boolean authoredAvailable;

    public GameArt() {
        loadAtlas();
        loadDirectionalBootstrap();
        loadBiomeDirectionalBootstrap();
        loadVfxBootstrap();
        loadBootstrap();
        authoredAvailable = atlas != null || directionalBootstrap != null || biomeDirectionalBootstrap != null
            || vfxBootstrap != null || bootstrapTexture != null;
        createFallback();
    }

    public boolean authoredAvailable() { return authoredAvailable; }

    public TextureRegion survivor(SurvivorCatalog.Survivor survivor, Motion motion, float stateTime) {
        String prefix = "survivor/" + survivor.name().toLowerCase() + "/" + motion.name().toLowerCase();
        float frameDuration = AnimationProfileCatalog.survivor(survivor).duration(motion);
        return animated(prefix, frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
    }

    /** Directional atlas contract: survivor/{id}/{n|ne|e|se|s|sw|w|nw}/{motion}. */
    public TextureRegion survivor(SurvivorCatalog.Survivor survivor, Motion motion, Direction8 direction, float stateTime) {
        String root = "survivor/" + survivor.name().toLowerCase();
        float frameDuration = AnimationProfileCatalog.survivor(survivor).duration(motion);
        TextureRegion directional = animatedOrNull(
            directionalPrefix(root, direction, motion), frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
        return directional == null ? survivor(survivor, motion, stateTime) : directional;
    }

    public TextureRegion enemy(Enemy.Type type, Motion motion, float stateTime) {
        String prefix = "enemy/" + type.name().toLowerCase() + "/" + motion.name().toLowerCase();
        float frameDuration = AnimationProfileCatalog.enemy(type).duration(motion);
        TextureRegion region = animatedOrNull(prefix, frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
        if (region != null) return region;

        Enemy.Type readableFallback = readableFallback(type);
        if (readableFallback != null) {
            String fallbackPrefix = "enemy/" + readableFallback.name().toLowerCase() + "/" + motion.name().toLowerCase();
            float fallbackDuration = AnimationProfileCatalog.enemy(readableFallback).duration(motion);
            region = animatedOrNull(fallbackPrefix, fallbackDuration, stateTime, AnimationProfileCatalog.loops(motion));
            if (region != null) return region;
        }
        return fallbackRegion;
    }

    /** Directional atlas contract: enemy/{type}/{n|ne|e|se|s|sw|w|nw}/{motion}. */
    public TextureRegion enemy(Enemy.Type type, Motion motion, Direction8 direction, float stateTime) {
        String root = "enemy/" + type.name().toLowerCase();
        float frameDuration = AnimationProfileCatalog.enemy(type).duration(motion);
        TextureRegion region = animatedOrNull(
            directionalPrefix(root, direction, motion), frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
        if (region != null) return region;

        Enemy.Type readableFallback = readableFallback(type);
        if (readableFallback != null) {
            String fallbackRoot = "enemy/" + readableFallback.name().toLowerCase();
            float fallbackDuration = AnimationProfileCatalog.enemy(readableFallback).duration(motion);
            region = animatedOrNull(
                directionalPrefix(fallbackRoot, direction, motion), fallbackDuration, stateTime, AnimationProfileCatalog.loops(motion));
            if (region != null) return region;
        }
        return enemy(type, motion, stateTime);
    }

    /**
     * Dedicated biome identity contract: enemy/biome/{identity}/{direction}/{motion}.
     * Falls back to the stable low-level archetype when final/generated identity art is unavailable.
     */
    public TextureRegion biomeEnemy(BiomeEnemyRoster.Identity identity, Enemy.Type fallbackType,
                                    Motion motion, Direction8 direction, float stateTime) {
        String root = BiomeDirectionalBootstrapArt.root(identity);
        if (root != null) {
            float frameDuration = AnimationProfileCatalog.enemy(fallbackType).duration(motion);
            TextureRegion region = animatedOrNull(
                directionalPrefix(root, direction, motion), frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
            if (region != null) return region;
        }
        return enemy(fallbackType, motion, direction, stateTime);
    }

    /** Uses dedicated boss identity art when present, falling back to the generic BOSS authored set. */
    public TextureRegion boss(BossIdentity identity, Motion motion, float stateTime) {
        String prefix = bossRoot(identity) + "/" + motion.name().toLowerCase();
        float frameDuration = AnimationProfileCatalog.enemy(Enemy.Type.BOSS).duration(motion);
        TextureRegion region = animatedOrNull(prefix, frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
        return region == null ? enemy(Enemy.Type.BOSS, motion, stateTime) : region;
    }

    /** Directional atlas contract: boss/{identity}/{n|ne|e|se|s|sw|w|nw}/{motion}. */
    public TextureRegion boss(BossIdentity identity, Motion motion, Direction8 direction, float stateTime) {
        String root = bossRoot(identity);
        float frameDuration = AnimationProfileCatalog.enemy(Enemy.Type.BOSS).duration(motion);
        TextureRegion region = animatedOrNull(
            directionalPrefix(root, direction, motion), frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
        return region == null ? boss(identity, motion, stateTime) : region;
    }

    /** Compatibility overload retained for older callers and tests. */
    public TextureRegion boss(boolean revenant, Motion motion, float stateTime) {
        return boss(revenant ? BossIdentity.REVENANT : BossIdentity.ALPHA, motion, stateTime);
    }

    /** Compatibility overload retained for older callers and tests. */
    public TextureRegion boss(boolean revenant, Motion motion, Direction8 direction, float stateTime) {
        return boss(revenant ? BossIdentity.REVENANT : BossIdentity.ALPHA, motion, direction, stateTime);
    }

    static String bossRoot(BossIdentity identity) {
        BossIdentity safeIdentity = identity == null ? BossIdentity.ALPHA : identity;
        return "boss/" + safeIdentity.name().toLowerCase();
    }

    public TextureRegion effect(String name, float stateTime, float frameDuration) {
        TextureRegion region = effectOrNull(name, stateTime, frameDuration);
        return region == null ? fallbackRegion : region;
    }

    /** Returns null when a one-shot authored FX sequence is absent, preserving procedural fallback. */
    public TextureRegion effectOrNull(String name, float stateTime, float frameDuration) {
        return animatedOrNull("fx/" + name, frameDuration, stateTime, false);
    }

    /** Returns null when a looping authored FX sequence is absent. */
    public TextureRegion loopingEffectOrNull(String name, float stateTime, float frameDuration) {
        return animatedOrNull("fx/" + name, frameDuration, stateTime, true);
    }

    public TextureRegion region(String name) {
        TextureRegion region = regionOrNull(name);
        return region == null ? fallbackRegion : region;
    }

    /** Returns null instead of fallback when neither final nor bootstrap authored art exists. */
    public TextureRegion regionOrNull(String name) {
        if (atlas != null) {
            TextureRegion region = atlas.findRegion(name);
            if (region != null) return region;
        }
        if (biomeDirectionalBootstrap != null) {
            TextureRegion region = biomeDirectionalBootstrap.region(name, 0f, 1f, false);
            if (region != null) return region;
        }
        if (vfxBootstrap != null) {
            TextureRegion region = vfxBootstrap.region(name, 0f, 1f, false);
            if (region != null) return region;
        }
        return bootstrapRegion(name);
    }

    public boolean hasRegion(String name) { return regionOrNull(name) != null; }

    public boolean hasAnimation(String prefix) {
        if (atlas != null) {
            Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(prefix);
            if ((frames != null && frames.size > 0) || atlas.findRegion(prefix) != null) return true;
        }
        if (directionalBootstrap != null && directionalBootstrap.supports(prefix)) return true;
        if (biomeDirectionalBootstrap != null && biomeDirectionalBootstrap.supports(prefix)) return true;
        if (vfxBootstrap != null && vfxBootstrap.supports(prefix)) return true;
        return bootstrapRegion(prefix) != null;
    }

    private TextureRegion animated(String prefix, float frameDuration, float stateTime, boolean loop) {
        TextureRegion region = animatedOrNull(prefix, frameDuration, stateTime, loop);
        return region == null ? fallbackRegion : region;
    }

    private TextureRegion animatedOrNull(String prefix, float frameDuration, float stateTime, boolean loop) {
        if (atlas != null) {
            Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(prefix);
            if (frames != null && frames.size > 0) {
                int rawFrame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
                int frame = loop ? rawFrame % frames.size : Math.min(frames.size - 1, rawFrame);
                return frames.get(frame);
            }
            TextureRegion single = atlas.findRegion(prefix);
            if (single != null) return single;
        }
        if (biomeDirectionalBootstrap != null) {
            TextureRegion region = biomeDirectionalBootstrap.region(prefix, stateTime, frameDuration, loop);
            if (region != null) return region;
        }
        if (directionalBootstrap != null) {
            TextureRegion region = directionalBootstrap.region(prefix, stateTime, frameDuration, loop);
            if (region != null) return region;
        }
        if (vfxBootstrap != null) {
            TextureRegion region = vfxBootstrap.region(prefix, stateTime, frameDuration, loop);
            if (region != null) return region;
        }
        return bootstrapRegion(prefix);
    }

    private TextureRegion bootstrapRegion(String key) {
        if (bootstrapRegions == null) return null;
        int tile = BootstrapArtCatalog.tileIndex(key);
        return tile < 0 || tile >= bootstrapRegions.length ? null : bootstrapRegions[tile];
    }

    static String directionalPrefix(String root, Direction8 direction, Motion motion) {
        Direction8 safeDirection = direction == null ? Direction8.E : direction;
        return root + "/" + safeDirection.atlasToken() + "/" + motion.name().toLowerCase();
    }

    private static Enemy.Type readableFallback(Enemy.Type type) {
        return switch (type) {
            case SHIELDED -> Enemy.Type.BRUTE;
            case REGENERATOR -> Enemy.Type.SHAMBLER;
            case PHANTOM -> Enemy.Type.RUNNER;
            default -> null;
        };
    }

    private void loadAtlas() {
        if (!Gdx.files.internal(ATLAS_PATH).exists()) return;
        try {
            atlas = new TextureAtlas(Gdx.files.internal(ATLAS_PATH));
        } catch (RuntimeException exception) {
            Gdx.app.error("GameArt", "Unable to load production atlas; bootstrap art will be used.", exception);
            atlas = null;
        }
    }

    private void loadDirectionalBootstrap() {
        try {
            directionalBootstrap = DirectionalBootstrapArt.create();
        } catch (RuntimeException exception) {
            Gdx.app.error("GameArt", "Unable to create directional bootstrap art; legacy bootstrap remains active.", exception);
            if (directionalBootstrap != null) directionalBootstrap.dispose();
            directionalBootstrap = null;
        }
    }

    private void loadBiomeDirectionalBootstrap() {
        try {
            biomeDirectionalBootstrap = BiomeDirectionalBootstrapArt.create();
        } catch (RuntimeException exception) {
            Gdx.app.error("GameArt", "Unable to create biome directional art; base enemy art remains active.", exception);
            if (biomeDirectionalBootstrap != null) biomeDirectionalBootstrap.dispose();
            biomeDirectionalBootstrap = null;
        }
    }

    private void loadVfxBootstrap() {
        try {
            vfxBootstrap = BootstrapVfxArt.create();
        } catch (RuntimeException exception) {
            Gdx.app.error("GameArt", "Unable to create bootstrap VFX; procedural VFX remain active.", exception);
            if (vfxBootstrap != null) vfxBootstrap.dispose();
            vfxBootstrap = null;
        }
    }

    private void loadBootstrap() {
        if (!Gdx.files.internal(BOOTSTRAP_PATH).exists()) return;
        Pixmap pixmap = null;
        try {
            byte[] png = decodeBase64(Gdx.files.internal(BOOTSTRAP_PATH).readString("UTF-8"));
            if (png.length == 0) return;
            pixmap = new Pixmap(png, 0, png.length);
            bootstrapTexture = new Texture(pixmap);
            bootstrapTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            bootstrapRegions = new TextureRegion[LEGACY_BOOTSTRAP_TILES];
            for (int tile = 0; tile < bootstrapRegions.length; tile++) {
                int x = (tile % BootstrapArtCatalog.SHEET_COLUMNS) * BootstrapArtCatalog.TILE;
                int y = (tile / BootstrapArtCatalog.SHEET_COLUMNS) * BootstrapArtCatalog.TILE;
                if (x + BootstrapArtCatalog.TILE <= bootstrapTexture.getWidth()
                    && y + BootstrapArtCatalog.TILE <= bootstrapTexture.getHeight()) {
                    bootstrapRegions[tile] = new TextureRegion(
                        bootstrapTexture, x, y, BootstrapArtCatalog.TILE, BootstrapArtCatalog.TILE);
                }
            }
        } catch (RuntimeException exception) {
            Gdx.app.error("GameArt", "Unable to load bootstrap art source; procedural fallback remains active.", exception);
            if (bootstrapTexture != null) bootstrapTexture.dispose();
            bootstrapTexture = null;
            bootstrapRegions = null;
        } finally {
            if (pixmap != null) pixmap.dispose();
        }
    }

    /** Small Android-safe Base64 decoder; avoids relying on API-level-specific java.util.Base64. */
    static byte[] decodeBase64(String input) {
        if (input == null || input.isBlank()) return new byte[0];
        ByteArrayOutputStream out = new ByteArrayOutputStream(input.length() * 3 / 4);
        int value = 0;
        int bits = -8;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) continue;
            if (c == '=') break;
            int digit = base64Digit(c);
            if (digit < 0) throw new IllegalArgumentException("Invalid Base64 character at index " + i);
            value = (value << 6) | digit;
            bits += 6;
            if (bits >= 0) {
                out.write((value >> bits) & 0xff);
                bits -= 8;
            }
        }
        return out.toByteArray();
    }

    private static int base64Digit(char c) {
        if (c >= 'A' && c <= 'Z') return c - 'A';
        if (c >= 'a' && c <= 'z') return c - 'a' + 26;
        if (c >= '0' && c <= '9') return c - '0' + 52;
        if (c == '+') return 62;
        if (c == '/') return 63;
        return -1;
    }

    private void createFallback() {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(.08f, .80f, 1f, 1f));
        pixmap.fillCircle(8, 8, 7);
        pixmap.setColor(new Color(.92f, .98f, 1f, 1f));
        pixmap.fillCircle(6, 6, 2);
        fallbackTexture = new Texture(pixmap);
        fallbackTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        fallbackRegion = new TextureRegion(fallbackTexture);
        pixmap.dispose();
    }

    @Override public void dispose() {
        if (atlas != null) atlas.dispose();
        if (directionalBootstrap != null) directionalBootstrap.dispose();
        if (biomeDirectionalBootstrap != null) biomeDirectionalBootstrap.dispose();
        if (vfxBootstrap != null) vfxBootstrap.dispose();
        if (bootstrapTexture != null) bootstrapTexture.dispose();
        if (fallbackTexture != null) fallbackTexture.dispose();
    }
}
