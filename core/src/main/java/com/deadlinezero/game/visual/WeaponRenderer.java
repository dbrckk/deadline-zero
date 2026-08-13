package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.entities.Player;

/** Draws the currently equipped weapon as an authored overlay, with recoil and aim orientation. */
public final class WeaponRenderer {
    private final GameArt art;

    public WeaponRenderer(GameArt art) { this.art = art; }

    public void draw(SpriteBatch batch, Player player, float aimAngleDeg, float shotFlash) {
        if (!art.authoredAvailable()) return;
        String id = player.weapon.definition.id.toLowerCase();
        TextureRegion region = art.regionOrNull("weapon/" + id);
        if (region == null) return;

        boolean flipY = aimAngleDeg > 90f || aimAngleDeg < -90f;
        float recoil = MathUtils.clamp(shotFlash, 0f, 1f) * .16f;
        float radians = aimAngleDeg * MathUtils.degreesToRadians;
        float forwardX = MathUtils.cos(radians);
        float forwardY = MathUtils.sin(radians);
        float x = player.position.x + forwardX * (.34f - recoil);
        float y = player.position.y + .18f + forwardY * (.34f - recoil);
        float w = 1.10f;
        float h = w * region.getRegionHeight() / (float)Math.max(1, region.getRegionWidth());

        batch.begin();
        batch.setColor(1f, 1f, 1f, 1f);
        batch.draw(region,
            x - w * .12f, y - h * .5f,
            w * .12f, h * .5f,
            w, h,
            1f, flipY ? -1f : 1f,
            aimAngleDeg);
        batch.end();
    }
}
