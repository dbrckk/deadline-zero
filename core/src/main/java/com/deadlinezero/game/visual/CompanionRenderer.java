package com.deadlinezero.game.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.abilities.DroneDoctrine;
import com.deadlinezero.game.entities.Player;

/** Lightweight authored/fallback companion pass for the Drone ability and its doctrine identity. */
public final class CompanionRenderer {
    private final GameArt art;
    private float stateTime;

    public CompanionRenderer(GameArt art) { this.art = art; }

    public void update(float dt) { stateTime += Math.max(0f, dt); }

    public void draw(SpriteBatch batch, Player player) {
        if (player == null || !player.alive || player.abilities.level(AbilityType.DRONE) <= 0) return;

        DroneDoctrine doctrine = player.abilities.droneDoctrine();
        float angle = stateTime * orbitSpeedDegrees(doctrine) + 180f;
        float orbit = orbitRadius(doctrine);
        float x = player.position.x + MathUtils.cosDeg(angle) * orbit;
        float y = player.position.y + MathUtils.sinDeg(angle) * orbit;
        float pulse = .5f + .5f * MathUtils.sin(stateTime * pulseSpeed(doctrine));
        float size = baseSize(doctrine) * (1f + pulse * .08f);

        TextureRegion region = art.region("companion/drone");
        batch.begin();
        switch (doctrine) {
            case HUNTER -> batch.setColor(1f, .58f + pulse * .10f, .20f, 1f);
            case SENTINEL -> batch.setColor(.36f, .88f + pulse * .08f, 1f, 1f);
            default -> batch.setColor(.62f, 1f, .68f, 1f);
        }
        batch.draw(region, x - size * .5f, y - size * .5f, size, size);
        batch.setColor(1f, 1f, 1f, 1f);
        batch.end();
    }

    static float orbitRadius(DroneDoctrine doctrine) {
        return doctrine == DroneDoctrine.HUNTER ? 2.25f
            : doctrine == DroneDoctrine.SENTINEL ? 1.55f
            : 1.80f;
    }

    static float orbitSpeedDegrees(DroneDoctrine doctrine) {
        return doctrine == DroneDoctrine.HUNTER ? 145f
            : doctrine == DroneDoctrine.SENTINEL ? 95f
            : 110f;
    }

    static float pulseSpeed(DroneDoctrine doctrine) {
        return doctrine == DroneDoctrine.HUNTER ? 9.5f
            : doctrine == DroneDoctrine.SENTINEL ? 6.5f
            : 7.5f;
    }

    static float baseSize(DroneDoctrine doctrine) {
        return doctrine == DroneDoctrine.HUNTER ? .76f
            : doctrine == DroneDoctrine.SENTINEL ? .86f
            : .80f;
    }
}
