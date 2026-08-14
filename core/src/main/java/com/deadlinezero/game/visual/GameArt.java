package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.SurvivorCatalog;

/**
 * Authored-art gateway. The gameplay layer never needs to know whether art comes from an atlas
 * or the deterministic runtime fallback. Atlas convention: assets/art/game.atlas.
 */
public final class GameArt implements Disposable {
    public enum Motion { IDLE, RUN, ATTACK, HIT, DEATH }

    private static final String ATLAS_PATH = "art/game.atlas";
    private TextureAtlas atlas;
    private Texture fallbackTexture;
    private TextureRegion fallbackRegion;
    private boolean authoredAvailable;

    public GameArt() {
        if (Gdx.files.internal(ATLAS_PATH).exists()) {
            try {
                atlas = new TextureAtlas(Gdx.files.internal(ATLAS_PATH));
                authoredAvailable = true;
            } catch (RuntimeException ignored) {
                authoredAvailable = false;
            }
        }
        createFallback();
    }

    public boolean authoredAvailable() { return authoredAvailable; }

    public TextureRegion survivor(SurvivorCatalog.Survivor survivor, Motion motion, float stateTime) {
        String prefix = "survivor/" + survivor.name().toLowerCase() + "/" + motion.name().toLowerCase();
        float frameDuration = AnimationProfileCatalog.survivor(survivor).duration(motion);
        return animated(prefix, frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
    }

    public TextureRegion enemy(Enemy.Type type, Motion motion, float stateTime) {
        String prefix = "enemy/" + type.name().toLowerCase() + "/" + motion.name().toLowerCase();
        float frameDuration = AnimationProfileCatalog.enemy(type).duration(motion);
        return animated(prefix, frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
    }

    /** Uses dedicated boss identity art when present, falling back to the generic BOSS authored set. */
    public TextureRegion boss(boolean revenant, Motion motion, float stateTime) {
        String identity = revenant ? "revenant" : "alpha";
        String prefix = "boss/" + identity + "/" + motion.name().toLowerCase();
        float frameDuration = AnimationProfileCatalog.enemy(Enemy.Type.BOSS).duration(motion);
        TextureRegion region = animatedOrNull(prefix, frameDuration, stateTime, AnimationProfileCatalog.loops(motion));
        return region == null ? enemy(Enemy.Type.BOSS, motion, stateTime) : region;
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

    /** Returns null instead of fallback when an authored region is absent. */
    public TextureRegion regionOrNull(String name) {
        if (atlas == null) return null;
        return atlas.findRegion(name);
    }

    public boolean hasRegion(String name) { return regionOrNull(name) != null; }

    public boolean hasAnimation(String prefix) {
        if (atlas == null) return false;
        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(prefix);
        return (frames != null && frames.size > 0) || atlas.findRegion(prefix) != null;
    }

    private TextureRegion animated(String prefix, float frameDuration, float stateTime, boolean loop) {
        TextureRegion region = animatedOrNull(prefix, frameDuration, stateTime, loop);
        return region == null ? fallbackRegion : region;
    }

    private TextureRegion animatedOrNull(String prefix, float frameDuration, float stateTime, boolean loop) {
        if (atlas == null) return null;
        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(prefix);
        if (frames == null || frames.size == 0) return atlas.findRegion(prefix);
        int rawFrame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration));
        int frame = loop ? rawFrame % frames.size : Math.min(frames.size - 1, rawFrame);
        return frames.get(frame);
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
        if (fallbackTexture != null) fallbackTexture.dispose();
    }
}
