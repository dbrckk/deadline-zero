package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class HighResBossDirectionalArtTest {
    private static final String[] ROOTS = {
        "boss/alpha/", "boss/revenant/", "boss/warden/", "boss/harvester/"
    };
    private static final String[] DIRECTIONS = {"n", "ne", "e", "se", "s", "sw", "w", "nw"};

    @Test void sheetStaysWithinConservativeGlesDimensions() {
        assertEquals(64, HighResBossDirectionalArt.TILE);
        assertEquals(4, HighResBossDirectionalArt.ACTOR_COUNT);
        assertEquals(384, HighResBossDirectionalArt.TOTAL_TILES);
        assertEquals(1024, HighResBossDirectionalArt.width());
        assertEquals(1536, HighResBossDirectionalArt.height());
        assertTrue(HighResBossDirectionalArt.width() <= 2048);
        assertTrue(HighResBossDirectionalArt.height() <= 2048);
    }

    @Test void everyBossDirectionAndMotionHasExpectedAnimationFrames() {
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

    @Test void actorAndDirectionBlocksNeverOverlap() {
        for (int actor = 0; actor < ROOTS.length; actor++) {
            for (int direction = 0; direction < DIRECTIONS.length; direction++) {
                String root = ROOTS[actor] + DIRECTIONS[direction] + "/";
                int base = actor * HighResBossDirectionalArt.ACTOR_BLOCK
                    + direction * HighResBossDirectionalArt.FRAMES_PER_DIRECTION;
                assertEquals(base, HighResBossDirectionalArt.firstTile(root + "idle"));
                assertEquals(base + 2, HighResBossDirectionalArt.firstTile(root + "run"));
                assertEquals(base + 5, HighResBossDirectionalArt.firstTile(root + "attack"));
                assertEquals(base + 7, HighResBossDirectionalArt.firstTile(root + "hit"));
                assertEquals(base + 9, HighResBossDirectionalArt.firstTile(root + "death"));
            }
        }
    }

    private static void assertMotion(String root, String direction, String motion, int frames) {
        String key = root + direction + "/" + motion;
        assertTrue(HighResBossDirectionalArt.firstTile(key) >= 0, key);
        assertEquals(frames, HighResBossDirectionalArt.frameCount(key), key);
    }
}
