package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.deadlinezero.game.entities.Enemy;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

final class ChampionVariantPresentationTest {
    @Test void everyChampionVariantHasDistinctShapeSemantics() {
        EnumSet<ChampionVariantPresentation.Marker> seen =
            EnumSet.noneOf(ChampionVariantPresentation.Marker.class);

        for (Enemy.Variant variant : Enemy.Variant.values()) {
            ChampionVariantPresentation.Marker marker = ChampionVariantPresentation.marker(variant);
            if (variant == Enemy.Variant.NORMAL) {
                assertEquals(ChampionVariantPresentation.Marker.NONE, marker);
            } else {
                seen.add(marker);
            }
        }

        assertEquals(8, seen.size());
    }

    @Test void highImpactVariantsKeepExpectedNonColorMarkers() {
        assertEquals(ChampionVariantPresentation.Marker.CHEVRON,
            ChampionVariantPresentation.marker(Enemy.Variant.SWIFT));
        assertEquals(ChampionVariantPresentation.Marker.ARMOR,
            ChampionVariantPresentation.marker(Enemy.Variant.ARMORED));
        assertEquals(ChampionVariantPresentation.Marker.CLAW,
            ChampionVariantPresentation.marker(Enemy.Variant.FERAL));
        assertEquals(ChampionVariantPresentation.Marker.VOLATILE_CORE,
            ChampionVariantPresentation.marker(Enemy.Variant.VOLATILE));
        assertEquals(ChampionVariantPresentation.Marker.JUGGERNAUT,
            ChampionVariantPresentation.marker(Enemy.Variant.JUGGERNAUT));
        assertEquals(ChampionVariantPresentation.Marker.RAVAGER,
            ChampionVariantPresentation.marker(Enemy.Variant.RAVAGER));
        assertEquals(ChampionVariantPresentation.Marker.AEGIS,
            ChampionVariantPresentation.marker(Enemy.Variant.AEGIS));
        assertEquals(ChampionVariantPresentation.Marker.HUNTER,
            ChampionVariantPresentation.marker(Enemy.Variant.HUNTER));
    }
}
