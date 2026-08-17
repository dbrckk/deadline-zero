package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

final class AuthoredCoreDirectionalArtTest {
    private static final String[] ROOTS = {
        "survivor/rex", "survivor/nyx", "survivor/bastion", "survivor/volt", "survivor/wraith",
        "enemy/shambler", "enemy/runner"
    };
    private static final String[] DIRECTIONS = {"n", "ne", "e", "se", "s", "sw", "w", "nw"};

    @Test void optionalShippedPngMustMatchRuntimeGridAndContainVisibleTiles() throws Exception {
        Path asset = locateAsset();
        if (asset == null) {
            // Absence is supported deliberately: GameArt falls back to deterministic generated art.
            assertEquals(768, AuthoredCoreDirectionalArt.width());
            assertEquals(2016, AuthoredCoreDirectionalArt.height());
            return;
        }

        BufferedImage image = ImageIO.read(asset.toFile());
        assertNotNull(image, "assets/art/core_authored.png must be a real PNG when shipped");
        assertEquals(AuthoredCoreDirectionalArt.width(), image.getWidth());
        assertEquals(AuthoredCoreDirectionalArt.height(), image.getHeight());
        assertEquals(768, image.getWidth());
        assertEquals(2016, image.getHeight());

        for (int tile = 0; tile < AuthoredCoreDirectionalArt.TOTAL_TILES; tile++) {
            int ox = (tile % AuthoredCoreDirectionalArt.COLUMNS) * AuthoredCoreDirectionalArt.TILE;
            int oy = (tile / AuthoredCoreDirectionalArt.COLUMNS) * AuthoredCoreDirectionalArt.TILE;
            int visible = 0;
            for (int y = oy; y < oy + AuthoredCoreDirectionalArt.TILE; y++) {
                for (int x = ox; x < ox + AuthoredCoreDirectionalArt.TILE; x++) {
                    if (((image.getRGB(x, y) >>> 24) & 0xff) > 16) visible++;
                }
            }
            assertTrue(visible >= 40, "authored tile " + tile + " unexpectedly empty: " + visible);
        }
    }

    @Test void allSevenActorsCoverEightDirectionsAndFiveMotions() {
        for (String root : ROOTS) {
            for (String direction : DIRECTIONS) {
                assertMotion(root, direction, "idle", 2);
                assertMotion(root, direction, "run", 3);
                assertMotion(root, direction, "attack", 2);
                assertMotion(root, direction, "hit", 2);
                assertMotion(root, direction, "death", 3);
            }
        }
        assertEquals(7, AuthoredCoreDirectionalArt.ACTOR_COUNT);
        assertEquals(672, AuthoredCoreDirectionalArt.TOTAL_TILES);
    }

    @Test void unrelatedActorsRemainOnTheirDedicatedFallbackLayers() {
        assertEquals(-1, AuthoredCoreDirectionalArt.firstTile("enemy/brute/e/run"));
        assertEquals(-1, AuthoredCoreDirectionalArt.firstTile("enemy/ranged/e/run"));
        assertEquals(-1, AuthoredCoreDirectionalArt.firstTile("boss/alpha/e/run"));
    }

    private static void assertMotion(String root, String direction, String motion, int frames) {
        String key = root + "/" + direction + "/" + motion;
        assertTrue(AuthoredCoreDirectionalArt.firstTile(key) >= 0, key);
        assertEquals(frames, AuthoredCoreDirectionalArt.frameCount(key), key);
    }

    private static Path locateAsset() {
        Path direct = Path.of("assets", "art", "core_authored.png");
        if (Files.isRegularFile(direct)) return direct;
        Path parent = Path.of("..", "assets", "art", "core_authored.png");
        if (Files.isRegularFile(parent)) return parent;
        return null;
    }
}
