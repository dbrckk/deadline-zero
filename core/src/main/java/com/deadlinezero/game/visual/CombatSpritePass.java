package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;

/**
 * Single entry point for authored combat sprites and optional lightweight grading.
 * GameScreen only needs update() + render(), keeping the simulation independent from art.
 */
public final class CombatSpritePass {
    private final CharacterSpriteRenderer characters;
    private final PostFxShader postFx = new PostFxShader();
    private GraphicsQuality quality;

    public CombatSpritePass(GameArt art) {
        characters = new CharacterSpriteRenderer(art);
        quality = GraphicsQuality.autoDetect();
    }

    public void update(float dt) { characters.update(dt); }

    public boolean authoredAvailable() { return characters.authoredAvailable(); }

    public GraphicsQuality quality() { return quality; }

    public void setQuality(GraphicsQuality quality) {
        if (quality != null) this.quality = quality;
    }

    public void render(SpriteBatch batch, Player player, Array<Enemy> enemies) {
        if (!characters.authoredAvailable()) return;
        if (postFx.available() && quality.postFxIntensity > 0f) {
            batch.setShader(postFx.shader(quality.postFxIntensity));
        }
        characters.draw(batch, player, enemies);
        batch.setShader(null);
    }

    public void dispose() { postFx.dispose(); }
}
