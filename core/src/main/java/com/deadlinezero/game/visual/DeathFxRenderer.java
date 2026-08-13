package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.fx.DeathFx;

/** Renders long-lived corpses/blood stains using authored art when available and cheap procedural fallback otherwise. */
public final class DeathFxRenderer {
    private final GameArt art;

    public DeathFxRenderer(GameArt art) { this.art = art; }

    public void drawFallback(ShapeRenderer shapes, Array<DeathFx> effects) {
        for (DeathFx fx : effects) {
            if (!fx.active) continue;
            float alpha = MathUtils.clamp(fx.life / Math.max(.001f, fx.maxLife), 0f, 1f);
            float fade = Math.min(1f, alpha * 3f);
            shapes.setColor(.22f, .015f, .018f, .18f * fade);
            shapes.ellipse(fx.x - fx.radius * 1.1f, fx.y - fx.radius * .38f,
                fx.radius * 2.2f, fx.radius * .76f);
            shapes.setColor(.06f, .055f, .05f, .48f * fade);
            shapes.ellipse(fx.x - fx.radius * .82f, fx.y - fx.radius * .22f,
                fx.radius * 1.64f, fx.radius * .44f);
        }
    }

    public void drawAuthored(SpriteBatch batch, Array<DeathFx> effects) {
        if (!art.authoredAvailable()) return;
        batch.begin();
        for (DeathFx fx : effects) {
            if (!fx.active) continue;
            String name = "enemy/" + fx.type.name().toLowerCase() + "/corpse";
            TextureRegion region = art.regionOrNull(name);
            if (region == null) continue;
            float alpha = MathUtils.clamp(fx.life / Math.max(.001f, fx.maxLife), 0f, 1f);
            float fade = Math.min(1f, alpha * 3f);
            float w = Math.max(.8f, fx.radius * (fx.type == Enemy.Type.BOSS ? 4.1f : 2.8f));
            float h = w * region.getRegionHeight() / (float)Math.max(1, region.getRegionWidth());
            batch.setColor(1f, 1f, 1f, fade);
            batch.draw(region,
                fx.x - w * .5f, fx.y - h * .35f,
                w * .5f, h * .35f,
                w, h,
                1f, 1f,
                fx.angleDeg);
        }
        batch.setColor(1f, 1f, 1f, 1f);
        batch.end();
    }
}
