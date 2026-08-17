package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class FinalArtContractTest {
    @Test void standardActorFrameFloorMatchesProductionSpec() {
        assertEquals(4, FinalArtContract.minimumFrames(GameArt.Motion.IDLE, false));
        assertEquals(8, FinalArtContract.minimumFrames(GameArt.Motion.RUN, false));
        assertEquals(6, FinalArtContract.minimumFrames(GameArt.Motion.ATTACK, false));
        assertEquals(3, FinalArtContract.minimumFrames(GameArt.Motion.HIT, false));
        assertEquals(8, FinalArtContract.minimumFrames(GameArt.Motion.DEATH, false));
        assertEquals(232, FinalArtContract.minimumDirectionalActorFrames(false));
    }

    @Test void bossFrameFloorIsHigherForReadability() {
        assertEquals(6, FinalArtContract.minimumFrames(GameArt.Motion.IDLE, true));
        assertEquals(8, FinalArtContract.minimumFrames(GameArt.Motion.ATTACK, true));
        assertEquals(4, FinalArtContract.minimumFrames(GameArt.Motion.HIT, true));
        assertEquals(10, FinalArtContract.minimumFrames(GameArt.Motion.DEATH, true));
        assertEquals(288, FinalArtContract.minimumDirectionalActorFrames(true));
    }

    @Test void productionContractKeepsEightDirectionsAndFastRunHeadroom() {
        assertEquals(8, FinalArtContract.directions());
        assertTrue(FinalArtContract.preferredFastRunFrames() >= 10);
    }
}
