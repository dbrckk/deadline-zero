package com.deadlinezero.game.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.deadlinezero.game.meta.RunStageContext;

final class RunEncounterDirectorTest {
    @Test void sameStageAndOrdinalProduceSameEncounterPlan() {
        RunStageContext.begin(4, 12);
        RunEncounterDirector first = new RunEncounterDirector(4);
        RunStageContext.begin(4, 12);
        RunEncounterDirector second = new RunEncounterDirector(4);

        for (int i = 0; i < 3; i++) assertEquals(first.planned(i), second.planned(i));
    }

    @Test void consecutiveRunsChangeEncounterPlan() {
        RunStageContext.begin(4, 12);
        RunEncounterDirector first = new RunEncounterDirector(4);
        RunStageContext.begin(4, 13);
        RunEncounterDirector second = new RunEncounterDirector(4);

        boolean differs = false;
        for (int i = 0; i < 3; i++) differs |= first.planned(i) != second.planned(i);
        assertTrue(differs);
    }

    @Test void eachRunUsesThreeDistinctEncounters() {
        RunStageContext.begin(8, 27);
        RunEncounterDirector director = new RunEncounterDirector(8);
        Set<RunEncounterDirector.Type> unique = new HashSet<>();
        for (int i = 0; i < 3; i++) unique.add(director.planned(i));
        assertEquals(3, unique.size());
        assertNotEquals(RunEncounterDirector.Type.NONE, director.planned(0));
    }

    @Test void runRotationExposesSpecialistEncounters() {
        Set<RunEncounterDirector.Type> seen = new HashSet<>();
        for (int ordinal = 0; ordinal < 12; ordinal++) {
            RunStageContext.begin(6, ordinal);
            RunEncounterDirector director = new RunEncounterDirector(6);
            for (int i = 0; i < 3; i++) seen.add(director.planned(i));
        }
        assertTrue(seen.contains(RunEncounterDirector.Type.PHANTOM_BREACH));
        assertTrue(seen.contains(RunEncounterDirector.Type.REGEN_BLOOM));
        assertTrue(seen.contains(RunEncounterDirector.Type.BULWARK_LINE));
    }
}
