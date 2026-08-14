package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.meta.SurvivorCatalog;
import org.junit.jupiter.api.Test;

final class DirectionalBootstrapArtTest {
    private static final String[] ROOTS = {
        "survivor/rex",
        "enemy/shambler", "enemy/runner", "enemy/brute", "enemy/ranged", "enemy/elite",
        "boss/alpha", "boss/revenant", "boss/warden", "boss/harvester",
        "survivor/nyx", "survivor/bastion", "survivor/volt", "survivor/wraith"
    };
    private static final String[] DIRECTIONS = {
        "n", "ne", "e", "se", "s", "sw", "w", "nw"
    };

    @Test void coreCombatRosterCoversEightDirectionsAndAllMotions() {
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

    @Test void everyPlayableSurvivorHasFullDirectionalCoverage() {
        for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
            String root = "survivor/" + survivor.name().toLowerCase();
            for (String direction : DIRECTIONS) {
                assertMotion(root, direction, "idle", 1);
                assertMotion(root, direction, "run", 3);
                assertMotion(root, direction, "attack", 2);
                assertMotion(root, direction, "hit", 1);
                assertMotion(root, direction, "death", 3);
            }
        }
    }

    @Test void actorBlocksAreContiguousNonOverlappingAndInsideSheet() {
        int previousLast = -1;
        for (String root : ROOTS) {
            int first = DirectionalBootstrapArt.firstTile(root + "/n/idle");
            int last = DirectionalBootstrapArt.firstTile(root + "/nw/death") + 2;
            assertTrue(first > previousLast, root);
            assertEquals(DirectionalBootstrapArt.ACTOR_BLOCK - 1, last - first, root);
            previousLast = last;
        }
        assertTrue(previousLast < DirectionalBootstrapArt.TOTAL_TILES);
        assertEquals(DirectionalBootstrapArt.ACTOR_BLOCK * ROOTS.length, DirectionalBootstrapArt.TOTAL_TILES);
        assertEquals(1120, DirectionalBootstrapArt.TOTAL_TILES);
    }

    @Test void generatedSheetStaysWithinBaselineGlesTextureDimension() {
        int rows = (DirectionalBootstrapArt.TOTAL_TILES + DirectionalBootstrapArt.COLUMNS - 1)
            / DirectionalBootstrapArt.COLUMNS;
        int width = DirectionalBootstrapArt.COLUMNS * DirectionalBootstrapArt.TILE;
        int height = rows * DirectionalBootstrapArt.TILE;
        assertTrue(width <= 2048, "bootstrap sheet width exceeds baseline GLES texture size");
        assertTrue(height <= 2048, "bootstrap sheet height exceeds baseline GLES texture size");
        assertEquals(640, width);
        assertEquals(1792, height);
    }

    @Test void actorIdentityLookupIsStable() {
        for (int i = 0; i < ROOTS.length; i++) {
            assertEquals(i, DirectionalBootstrapArt.actorIndex(ROOTS[i] + "/e/run"));
        }
    }

    @Test void malformedOrUnsupportedKeysAreRejected() {
        assertEquals(-1, DirectionalBootstrapArt.firstTile(null));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("survivor/rex/run"));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("survivor/unknown/e/run"));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("enemy/shielded/e/run"));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("enemy/runner/center/run"));
        assertEquals(-1, DirectionalBootstrapArt.firstTile("boss/harvester/e/dance"));
    }

    private static void assertMotion(String root, String direction, String motion, int frames) {
        String key = root + "/" + direction + "/" + motion;
        int first = DirectionalBootstrapArt.firstTile(key);
        assertTrue(first >= 0, () -> "Missing directional bootstrap key: " + key);
        assertEquals(frames, DirectionalBootstrapArt.frameCount(key), key);
    }
}
