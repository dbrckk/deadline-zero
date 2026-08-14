package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.util.Pools;

/** Single entry point for authored combat presentation and optional lightweight grading/audio. */
public final class CombatSpritePass {
    private final CharacterSpriteRenderer characters;
    private final EnvironmentRenderer environment;
    private final WeaponRenderer weapon;
    private final AuthoredVfxRenderer vfx;
    private final CombatAudioLayer audio = new CombatAudioLayer();
    private final PostFxShader postFx = new PostFxShader();
    private GraphicsQuality quality;
    private float stateTime;

    public CombatSpritePass(GameArt art) {
        characters = new CharacterSpriteRenderer(art);
        environment = new EnvironmentRenderer(art);
        weapon = new WeaponRenderer(art);
        vfx = new AuthoredVfxRenderer(art);
        quality = GraphicsQuality.autoDetect();
    }

    public void update(float dt) {
        float safeDt = Math.max(0f, dt);
        stateTime += safeDt;
        characters.update(safeDt);
    }

    public boolean authoredAvailable() { return characters.authoredAvailable(); }
    public GraphicsQuality quality() { return quality; }
    public void setQuality(GraphicsQuality quality) { if (quality != null) this.quality = quality; }

    public void render(SpriteBatch batch, Player player, Array<Enemy> enemies) {
        render(batch, player, enemies, CombatPolishController.currentPools());
    }

    public void render(SpriteBatch batch, Player player, Array<Enemy> enemies, Pools pools) {
        audio.update(player, enemies);
        if (!characters.authoredAvailable()) return;
        environment.drawAuthored(batch);
        if (postFx.available() && quality.postFxIntensity > 0f) batch.setShader(postFx.shader(quality.postFxIntensity));
        characters.draw(batch, player, enemies);
        batch.setShader(null);

        Enemy target = nearestEnemy(player, enemies);
        float aimAngle = target == null ? fallbackAim(player) :
            MathUtils.atan2(target.position.y - player.position.y, target.position.x - player.position.x) * MathUtils.radiansToDegrees;
        float shotFlash = target == null || !player.alive ? 0f : MathUtils.clamp(1f - CombatVisualEvents.playerShotAgeSeconds() / .075f, 0f, 1f);
        weapon.draw(batch, player, aimAngle, shotFlash);
        if (pools != null) vfx.draw(batch, player, enemies, pools);
    }

    private Enemy nearestEnemy(Player player, Array<Enemy> enemies) {
        Enemy best = null;
        float bestD2 = Float.MAX_VALUE;
        for (Enemy enemy : enemies) {
            if (!enemy.alive) continue;
            float d2 = player.position.dst2(enemy.position);
            if (d2 < bestD2) { bestD2 = d2; best = enemy; }
        }
        return best;
    }

    private float fallbackAim(Player player) {
        return player.velocity.len2() > .01f ? player.velocity.angleDeg() : 0f;
    }

    public void dispose() {
        audio.dispose();
        postFx.dispose();
    }
}
