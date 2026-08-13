package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.fx.DeathFx;

/** Renders death animation first, then long-lived corpses/blood stains. */
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
            AnimationProfileCatalog.Profile anim = AnimationProfileCatalog.enemy(fx.type);
            float deathWindow = MathUtils.clamp(anim.death() * (fx.type == Enemy.Type.BOSS ? 7f : 6f), .55f, 1.15f);
            boolean animatingDeath = fx.age < deathWindow;
            TextureRegion region = animatingDeath
                ? art.enemy(fx.type, GameArt.Motion.DEATH, fx.age)
                : art.regionOrNull("enemy/" + fx.type.name().toLowerCase() + "/corpse");
            if (region == null) continue;

            float alpha = MathUtils.clamp(fx.life / Math.max(.001f, fx.maxLife), 0f, 1f);
            float fade = Math.min(1f, alpha * 3f);
            float w = Math.max(.8f, fx.radius * (fx.type == Enemy.Type.BOSS ? 4.1f : 2.8f));
            if (animatingDeath) w *= fx.type == Enemy.Type.BOSS ? 1.18f : 1.08f;
            float h = w * region.getRegionHeight() / (float)Math.max(1, region.getRegionWidth());
            batch.setColor(1f, 1f, 1f, animatingDeath ? 1f : fade);
            batch.draw(region,
                fx.x - w * .5f, fx.y - h * .35f,
                w * .5f, h * .35f,
                w, h,
                1f, 1f,
                animatingDeath ? 0f : fx.angleDeg);
        }
        batch.setColor(1f, 1f, 1f, 1f);
        batch.end();
    }
}
