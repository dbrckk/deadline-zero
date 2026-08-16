package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.world.BiomeEnemyRoster;
import org.junit.jupiter.api.Test;

final class BiomeDirectionalBootstrapArtTest {
    private static final String[] DIRECTIONS = { "n", "ne", "e", "se", "s", "sw", "w", "nw" };
    private static final String[] MOTIONS = { "idle", "run", "attack", "hit", "death" };

    @Test void everyBiomeIdentityHasFullEightWayMotionCoverage() {
        for (BiomeEnemyRoster.Identity identity : BiomeEnemyRoster.Identity.values()) {
            if (identity == BiomeEnemyRoster.Identity.NONE) continue;
            String root = BiomeDirectionalBootstrapArt.root(identity);
            for (String direction : DIRECTIONS) {
                for (String motion : MOTIONS) {
                    String key = root + "/" + direction + "/" + motion;
                    assertTrue(BiomeDirectionalBootstrapArt.firstTile(key) >= 0, () -> "Missing biome art: " + key);
                }
            }
        }
    }

    @Test void nullArchonHasDedicatedCoverageSeparateFromLegacyBosses() {
        for (String direction : DIRECTIONS) {
            for (String motion : MOTIONS) {
                String key = "boss/null_archon/" + direction + "/" + motion;
                assertTrue(BiomeDirectionalBootstrapArt.firstTile(key) >= 0, () -> "Missing Null Archon art: " + key);
            }
        }
        assertNotEquals(
            BiomeDirectionalBootstrapArt.firstTile("enemy/biome/phase_stalker/e/idle"),
            BiomeDirectionalBootstrapArt.firstTile("boss/null_archon/e/idle"));
    }

    @Test void motionFrameCountsMatchRuntimeAnimationContract() {
        String root = "enemy/biome/forge_hound/e/";
        assertTrue(BiomeDirectionalBootstrapArt.frameCount(root + "idle") == 1);
        assertTrue(BiomeDirectionalBootstrapArt.frameCount(root + "attack") == 2);
        assertTrue(BiomeDirectionalBootstrapArt.frameCount(root + "run") == 3);
        assertTrue(BiomeDirectionalBootstrapArt.frameCount(root + "death") == 3);
    }
}
