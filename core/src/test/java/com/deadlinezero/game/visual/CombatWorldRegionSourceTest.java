package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.junit.jupiter.api.Test;

final class CombatWorldRegionSourceTest {
    @Test
    void floorAvailabilityCanComeFromEnvironmentBootstrapRegionSource() {
        TextureRegion concrete = new TextureRegion();
        CombatWorldRenderer.RegionSource source = key ->
            "environment/floor/concrete_a".equals(key) ? concrete : null;

        CombatWorldRenderer renderer = new CombatWorldRenderer(source);

        assertTrue(renderer.hasUsableFloorSource());
    }
}
