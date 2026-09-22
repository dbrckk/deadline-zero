package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Enemy;
import org.junit.jupiter.api.Test;

final class ArtProfileCatalogPhoneReadabilityTest {
    @Test void nonBossEnemiesStayPhoneReadableWithoutBossScaleCreep() {
        for (Enemy.Type type : Enemy.Type.values()) {
            ArtProfileCatalog.CharacterProfile p = ArtProfileCatalog.enemy(type);
            if (type == Enemy.Type.BOSS) {
                assertTrue(p.height() >= 4.5f);
                continue;
            }
            assertTrue(p.height() >= 1.45f, type + " is too small for phone-scale authored rendering");
            assertTrue(p.height() <= 2.60f, type + " is too large relative to gameplay collision scale");
            assertTrue(p.footOffset() > 0f && p.footOffset() < p.height() * .40f);
        }
    }

    @Test void highPriorityEnemiesRemainVisuallyLargerThanBasicCrowd() {
        float runner = ArtProfileCatalog.enemy(Enemy.Type.RUNNER).height();
        float shambler = ArtProfileCatalog.enemy(Enemy.Type.SHAMBLER).height();
        assertTrue(ArtProfileCatalog.enemy(Enemy.Type.BRUTE).height() > shambler);
        assertTrue(ArtProfileCatalog.enemy(Enemy.Type.ELITE).height() > shambler);
        assertTrue(ArtProfileCatalog.enemy(Enemy.Type.SHIELDED).height() > shambler);
        assertTrue(shambler > runner);
    }
}
