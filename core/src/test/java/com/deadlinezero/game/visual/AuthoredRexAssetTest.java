package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

final class AuthoredRexAssetTest {
    @Test void shippedRexRasterMatchesRuntimeGridAndContainsEveryFrame() throws Exception {
        Path asset = locateAsset();
        assertNotNull(asset, "assets/art/rex_authored.png must ship with the project");

        BufferedImage image = ImageIO.read(asset.toFile());
        assertNotNull(image, "rex_authored.png must be a valid PNG");
        assertEquals(768, image.getWidth());
        assertEquals(288, image.getHeight());
        assertEquals(6, AuthoredCoreDirectionalArt.rexRows());
        assertEquals(96, AuthoredCoreDirectionalArt.ACTOR_BLOCK);

        for (int tile = 0; tile < AuthoredCoreDirectionalArt.ACTOR_BLOCK; tile++) {
            int ox = (tile % AuthoredCoreDirectionalArt.COLUMNS) * AuthoredCoreDirectionalArt.TILE;
            int oy = (tile / AuthoredCoreDirectionalArt.COLUMNS) * AuthoredCoreDirectionalArt.TILE;
            int visible = 0;
            for (int y = oy; y < oy + AuthoredCoreDirectionalArt.TILE; y++) {
                for (int x = ox; x < ox + AuthoredCoreDirectionalArt.TILE; x++) {
                    if (((image.getRGB(x, y) >>> 24) & 0xff) > 16) visible++;
                }
            }
            assertTrue(visible >= 40, "REX authored tile " + tile + " unexpectedly empty: " + visible);
        }
    }

    private static Path locateAsset() {
        Path direct = Path.of("assets", "art", "rex_authored.png");
        if (Files.isRegularFile(direct)) return direct;
        Path parent = Path.of("..", "assets", "art", "rex_authored.png");
        return Files.isRegularFile(parent) ? parent : null;
    }
}
