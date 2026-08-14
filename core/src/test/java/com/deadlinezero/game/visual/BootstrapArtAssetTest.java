package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

final class BootstrapArtAssetTest {
    @Test void shippedBootstrapPngIsValidAndEveryTileHasVisiblePixels() throws Exception {
        Path asset = locateAsset();
        String encoded = Files.readString(asset, StandardCharsets.UTF_8).trim();
        byte[] png = Base64.getDecoder().decode(encoded);

        assertTrue(png.length > 1024, "bootstrap art unexpectedly tiny");
        assertEquals((byte)0x89, png[0]);
        assertEquals((byte)'P', png[1]);
        assertEquals((byte)'N', png[2]);
        assertEquals((byte)'G', png[3]);

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(png));
        assertNotNull(image, "bootstrap art must decode as PNG");
        assertEquals(256, image.getWidth());
        assertEquals(256, image.getHeight());

        for (int tile = 0; tile < 16; tile++) {
            int ox = (tile % BootstrapArtCatalog.SHEET_COLUMNS) * BootstrapArtCatalog.TILE;
            int oy = (tile / BootstrapArtCatalog.SHEET_COLUMNS) * BootstrapArtCatalog.TILE;
            int visible = 0;
            for (int y = oy; y < oy + BootstrapArtCatalog.TILE; y++) {
                for (int x = ox; x < ox + BootstrapArtCatalog.TILE; x++) {
                    if (((image.getRGB(x, y) >>> 24) & 0xff) > 16) visible++;
                }
            }
            assertTrue(visible >= 80, "bootstrap tile " + tile + " lacks visible authored pixels: " + visible);
        }
    }

    private static Path locateAsset() {
        Path direct = Path.of("assets", "art", "game.png.b64");
        if (Files.isRegularFile(direct)) return direct;
        Path parent = Path.of("..", "assets", "art", "game.png.b64");
        if (Files.isRegularFile(parent)) return parent;
        throw new AssertionError("Cannot locate assets/art/game.png.b64 from " + Path.of("").toAbsolutePath());
    }
}
