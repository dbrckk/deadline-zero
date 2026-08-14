package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class DirectionalBootstrapArtTest {
    private static final String[] ROOTS = {
        "survivor/rex", "enemy/shambler", "enemy/runner"
    };
    private static final String[] DIRECTIONS = {
        "n", "ne", "e", "se", "s", "sw", "w", "nw"
    };

    @Test void verticalSliceCoversEightDirectionsAndAllMotions() {
        for (String root : ROOTS) {
            for (String direction : DIRECTIONS) {
                assertMotion(root, direction, "idle", 1);
                assertMotion(root, direction, "run", 3);
                assertMotion(root, direction, "attack", 2);
                assertMotion(root, direction, "hit", 1);
                assertMotion(root, direction, "death", 3);
            }
        }
    }

    @Test void actorBlocksDoNotOverlapAndStayInsideGeneratedSheet() {
        int rexLast = DirectionalBootstrapArt.firstTile("survivor/rex/nw/death") + 2;
        int shamblerFirst = DirectionalBootstrapArt.firstTile("enemy/shambler/n/idle");
        int shamblerLast = DirectionalBootstrapArt.firstTile("enemy/shambler/nw/death") + 2;
        int runnerFirst = DirectionalBootstrapArt.firstTile("enemy/runner/n/idle");
        int runnerLast = DirectionalBootstrapArt.firstTile("enemy/runner/nw/death") + 2;

        assertTrue(rexLast < shamblerFirst);
        assertTrue(shamblerLast < runnerFirst);
        assertTrue(runnerLast < DirectionalBootstrapArt.TOTAL_TILES);
        assertEquals(240, DirectionalBootstrapArt.TOTAL_TILES);
    }

    @Test void malformedOrUnsupportedKeysAreRejected() {
        assertEquals(-1, DirectionalBootstrapArt.firstTile(null));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("survivor/rex/run"));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("survivor/nyx/e/run"));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("enemy/brute/e/run"));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("enemy/runner/center/run"));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("enemy/runner/e/dance"));
    }

    private static void assertMotion(String root, String direction, String motion, int frames) {
        String key = root + "/" + direction + "/" + motion;
        int first = DirectionalBootstrapArt.firstTile(key);
        assertTrue(first >= 0, () -> "Missing directional bootstrap key: " + key);
        assertEquals(frames, DirectionalBootstrapArt.frameCount(key), key);
    }
}
