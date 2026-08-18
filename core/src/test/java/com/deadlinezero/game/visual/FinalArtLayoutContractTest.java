package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

final class FinalArtLayoutContractTest {
    @Test void productionLayoutMatchesRuntimeFrameContract() throws IOException {
        JsonValue layout = new JsonReader().parse(Files.readString(layoutPath()));

        JsonValue directions = layout.get("directions");
        assertEquals(FinalArtContract.directions(), directions.size);
        int directionIndex = 0;
        for (Direction8 direction : Direction8.values()) {
            assertEquals(direction.atlasToken(), directions.getString(directionIndex++));
        }

        JsonValue motionOrder = layout.get("motionOrder");
        assertEquals(GameArt.Motion.values().length, motionOrder.size);
        int motionIndex = 0;
        for (GameArt.Motion motion : GameArt.Motion.values()) {
            assertEquals(motion.name().toLowerCase(), motionOrder.getString(motionIndex++));
        }

        JsonValue standard = layout.get("standardFrames");
        JsonValue boss = layout.get("bossFrames");
        for (GameArt.Motion motion : GameArt.Motion.values()) {
            String key = motion.name().toLowerCase();
            assertEquals(FinalArtContract.minimumFrames(motion, false), standard.getInt(key), key + " standard frames");
            assertEquals(FinalArtContract.minimumFrames(motion, true), boss.getInt(key), key + " boss frames");
        }

        int expectedStandardFrames = FinalArtContract.minimumDirectionalActorFrames(false);
        int expectedBossFrames = FinalArtContract.minimumDirectionalActorFrames(true);
        assertEquals(232, expectedStandardFrames);
        assertEquals(288, expectedBossFrames);
    }

    @Test void everyFastRunOverrideUsesRuntimePreferredBudget() throws IOException {
        JsonValue actors = new JsonReader().parse(Files.readString(layoutPath())).get("actors");
        List<String> fastActors = new ArrayList<>();
        for (JsonValue actor = actors.child; actor != null; actor = actor.next) {
            if (!actor.has("runFrames")) continue;
            assertEquals(FinalArtContract.preferredFastRunFrames(), actor.getInt("runFrames"), actor.getString("id"));
            fastActors.add(actor.getString("id"));
        }
        assertTrue(fastActors.contains("wraith"));
        assertTrue(fastActors.contains("forge_hound"));
        assertTrue(fastActors.contains("phase_stalker"));
    }

    private static Path layoutPath() {
        Path direct = Path.of("art_sources", "final-sprite-layout.json");
        if (Files.isRegularFile(direct)) return direct;
        Path fromCore = Path.of("..", "art_sources", "final-sprite-layout.json");
        if (Files.isRegularFile(fromCore)) return fromCore;
        throw new IllegalStateException("Unable to locate art_sources/final-sprite-layout.json from "
            + Path.of("").toAbsolutePath());
    }
}
