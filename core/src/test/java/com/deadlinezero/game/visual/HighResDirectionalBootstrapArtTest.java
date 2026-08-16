package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class HighResDirectionalBootstrapArtTest {
    private static final String[] ROOTS = {
        "survivor/rex", "survivor/nyx", "survivor/bastion", "survivor/volt", "survivor/wraith",
        "enemy/shambler", "enemy/runner"
    };
    private static final String[] DIRECTIONS = {"n", "ne", "e", "se", "s", "sw", "w", "nw"};

    @Test void highVisibilityRosterHasFullEightWayMotionCoverage() {
        for (String root : ROOTS) {
            for (String direction : DIRECTIONS) {
                assertMotion(root, direction, "idle", 2);
                assertMotion(root, direction, "run", 3);
                assertMotion(root, direction, "attack", 2);
                assertMotion(root, direction, "hit", 2);
                assertMotion(root, direction, "death", 3);
            }
        }
    }

    @Test void eachDirectionUsesOneContiguousTwelveTileBlock() {
        for (String root : ROOTS) {
            for (String direction : DIRECTIONS) {
                int idle = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/idle");
                int run = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/run");
                int attack = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/attack");
                int hit = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/hit");
                int death = HighResDirectionalBootstrapArt.firstTile(root + "/" + direction + "/death");
                assertEquals(idle + 2, run);
                assertEquals(idle + 5, attack);
                assertEquals(idle + 7, hit);
                assertEquals(idle + 9, death);
                assertTrue(death + 2 < HighResDirectionalBootstrapArt.TOTAL_TILES);
            }
        }
    }

    @Test void memoryFootprintStaysInsideConservativeGlesDimension() {
        assertEquals(7, HighResDirectionalBootstrapArt.ACTOR_COUNT);
        assertEquals(12, HighResDirectionalBootstrapArt.FRAMES_PER_DIRECTION);
        assertEquals(672, HighResDirectionalBootstrapArt.TOTAL_TILES);
        assertEquals(768, HighResDirectionalBootstrapArt.width());
        assertEquals(2016, HighResDirectionalBootstrapArt.height());
        assertTrue(HighResDirectionalBootstrapArt.width() <= 2048);
        assertTrue(HighResDirectionalBootstrapArt.height() <= 2048);
    }

    @Test void intentionallyFallsBackForLessVisibleRoster() {
        assertEquals(-1, HighResDirectionalBootstrapArt.firstTile("enemy/brute/e/run"));
        assertEquals(-1, HighResDirectionalBootstrapArt.firstTile("enemy/ranged/e/run"));
        assertEquals(-1, HighResDirectionalBootstrapArt.firstTile("boss/alpha/e/run"));
        assertEquals(-1, HighResDirectionalBootstrapArt.firstTile("survivor/unknown/e/run"));
    }

    @Test void actorLookupOrderIsStable() {
        for (int i = 0; i < ROOTS.length; i++) {
            assertEquals(i, HighResDirectionalBootstrapArt.actorIndex(ROOTS[i] + "/e/run"));
        }
    }

    private static void assertMotion(String root, String direction, String motion, int frames) {
        String key = root + "/" + direction + "/" + motion;
        assertTrue(HighResDirectionalBootstrapArt.firstTile(key) >= 0, key);
        assertEquals(frames, HighResDirectionalBootstrapArt.frameCount(key), key);
    }
}
