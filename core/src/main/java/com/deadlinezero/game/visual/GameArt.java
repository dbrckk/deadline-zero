package com.deadlinezero.game.visual;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
        return animated(prefix, motion == Motion.RUN ? .085f : .12f, stateTime);
    }

    public TextureRegion enemy(Enemy.Type type, Motion motion, float stateTime) {
        String prefix = "enemy/" + type.name().toLowerCase() + "/" + motion.name().toLowerCase();
        return animated(prefix, motion == Motion.RUN ? .095f : .13f, stateTime);
    }

    public TextureRegion effect(String name, float stateTime, float frameDuration) {
        return animated("fx/" + name, frameDuration, stateTime);
    }

    public TextureRegion region(String name) {
        if (atlas != null) {
            TextureAtlas.AtlasRegion region = atlas.findRegion(name);
            if (region != null) return region;
        }
        return fallbackRegion;
    }

    private TextureRegion animated(String prefix, float frameDuration, float stateTime) {
        if (atlas == null) return fallbackRegion;
        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(prefix);
        if (frames == null || frames.size == 0) {
            TextureAtlas.AtlasRegion single = atlas.findRegion(prefix);
            return single == null ? fallbackRegion : single;
        }
        int frame = (int)(Math.max(0f, stateTime) / Math.max(.016f, frameDuration)) % frames.size;
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
