package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Enemy;
import com.deadlinezero.game.meta.SurvivorCatalog;
import org.junit.jupiter.api.Test;

final class ArtProfileCatalogTest {
    @Test void shamblerKeepsReadableStandardEnemyScale() {
        var shambler = ArtProfileCatalog.enemy(Enemy.Type.SHAMBLER);
        var runner = ArtProfileCatalog.enemy(Enemy.Type.RUNNER);
        var rex = ArtProfileCatalog.survivor(SurvivorCatalog.Survivor.REX);

        assertTrue(shambler.height() >= 1.50f, "Shambler must remain readable at phone gameplay scale");
        assertTrue(shambler.height() > runner.height(), "Runner should remain the smaller/faster silhouette");
        assertTrue(shambler.height() < rex.height(), "Baseline Shambler must remain smaller than Rex");
        assertTrue(shambler.footOffset() >= .40f && shambler.footOffset() <= .48f,
            "Shambler foot anchor must track its enlarged presentation scale");
    }
}
