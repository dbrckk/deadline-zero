package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.ai.BossIdentity;
import org.junit.jupiter.api.Test;

final class BossIdentityArtRoutingTest {
    @Test void everyBossIdentityMapsToItsOwnStableArtRoot() {
        assertEquals("boss/alpha", GameArt.bossRoot(BossIdentity.ALPHA));
        assertEquals("boss/revenant", GameArt.bossRoot(BossIdentity.REVENANT));
        assertEquals("boss/warden", GameArt.bossRoot(BossIdentity.WARDEN));
        assertEquals("boss/harvester", GameArt.bossRoot(BossIdentity.HARVESTER));
        assertEquals("boss/alpha", GameArt.bossRoot(null));
    }

    @Test void directionalBootstrapCoversEveryBossIdentityAcrossAllDirectionsAndMotions() {
        String[] directions = { "n", "ne", "e", "se", "s", "sw", "w", "nw" };
        String[] motions = { "idle", "run", "attack", "hit", "death" };
        for (BossIdentity identity : BossIdentity.values()) {
            String root = GameArt.bossRoot(identity);
            for (String direction : directions) {
                for (String motion : motions) {
                    String key = root + "/" + direction + "/" + motion;
                    assertTrue(DirectionalBootstrapArt.firstTile(key) >= 0, () -> "Missing boss art: " + key);
                }
            }
        }
    }
}
