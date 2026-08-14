package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.ai.BossIdentity;
import com.deadlinezero.game.combat.WeaponCatalog;
import com.deadlinezero.game.combat.WeaponDefinition;
import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.SurvivorCatalog;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

final class BootstrapArtCatalogTest {
    private static final String[] MOTIONS = { "idle", "run", "attack", "hit", "death" };

    @Test void bootstrapCoversEntireProductionArtContract() {
        for (String key : ArtManifest.REQUIRED_STATIC) assertSupported(key);
        for (String key : ArtManifest.REQUIRED_FX) assertSupported(key);

        for (WeaponDefinition weapon : WeaponCatalog.all()) assertSupported("weapon/" + weapon.id);

        for (SurvivorCatalog.Survivor survivor : SurvivorCatalog.Survivor.values()) {
            String root = "survivor/" + survivor.name().toLowerCase();
            for (String motion : MOTIONS) assertSupported(root + "/" + motion);
        }

        for (Enemy.Type type : Enemy.Type.values()) {
            String root = "enemy/" + type.name().toLowerCase();
            for (String motion : MOTIONS) assertSupported(root + "/" + motion);
            assertSupported(root + "/corpse");
        }

        for (BossIdentity identity : BossIdentity.values()) {
            String root = "boss/" + identity.name().toLowerCase();
            for (String motion : MOTIONS) assertSupported(root + "/" + motion);
        }
    }

    @Test void portableBase64DecoderHandlesPaddingAndWhitespace() {
        byte[] decoded = GameArt.decodeBase64("U HJvZHVjdGlvbi1hcnQ=\n");
        assertArrayEquals("Production-art".getBytes(StandardCharsets.UTF_8), decoded);
    }

    private static void assertSupported(String key) {
        assertTrue(BootstrapArtCatalog.supports(key), () -> "Missing bootstrap art mapping: " + key);
    }
}
