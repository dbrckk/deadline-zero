package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.deadlinezero.game.abilities.AbilityType;
import com.deadlinezero.game.abilities.DroneDoctrine;
import com.deadlinezero.game.entities.Player;
import com.deadlinezero.game.meta.RunLoadoutContext;
import org.junit.jupiter.api.Test;

final class ActiveBuildStatusTest {
    private Player fresh() {
        RunLoadoutContext.end();
        return new Player(0f, 0f);
    }

    @Test void doctrineTakesFirstHudSlot() {
        Player p = fresh();
        for (int i = 0; i < 3; i++) p.abilities.upgrade(AbilityType.DRONE);
        p.abilities.chooseDroneDoctrine(DroneDoctrine.HUNTER);

        String[] keys = new String[2];
        ActiveBuildStatus.fill(p, keys);
        assertEquals("hud.build.hunter", keys[0]);
        assertNull(keys[1]);
    }

    @Test void secondSlotShowsPrimaryActiveSynergy() {
        Player p = fresh();
        for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.TESLA_ORB);
        for (int i = 0; i < 3; i++) p.abilities.upgrade(AbilityType.DRONE);
        p.abilities.chooseDroneDoctrine(DroneDoctrine.SENTINEL);

        String[] keys = new String[2];
        ActiveBuildStatus.fill(p, keys);
        assertEquals("hud.build.sentinel", keys[0]);
        assertEquals("hud.build.arcReactor", keys[1]);
    }

    @Test void strongestLateSynergyWinsSingleSynergySlot() {
        Player p = fresh();
        for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.TESLA_ORB);
        for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.ORBITAL_BLADE);
        for (int i = 0; i < 3; i++) p.abilities.upgrade(AbilityType.DRONE);

        String[] keys = new String[2];
        ActiveBuildStatus.fill(p, keys);
        assertEquals("hud.build.stormBlade", keys[0]);
    }

    @Test void evolvedProtocolFillsOpenBuildSlot() {
        Player p = fresh();
        p.protocols.enableRhythm();
        p.protocols.evolveRhythm();

        String[] keys = new String[2];
        ActiveBuildStatus.fill(p, keys);
        assertEquals("hud.build.rhythmAccelerator", keys[0]);
        assertNull(keys[1]);
    }

    @Test void doctrineAndSynergyKeepPriorityOverEvolvedProtocol() {
        Player p = fresh();
        for (int i = 0; i < 5; i++) p.abilities.upgrade(AbilityType.TESLA_ORB);
        for (int i = 0; i < 3; i++) p.abilities.upgrade(AbilityType.DRONE);
        p.abilities.chooseDroneDoctrine(DroneDoctrine.SENTINEL);
        p.protocols.enableKillchain();
        p.protocols.evolveKillchain();

        String[] keys = new String[2];
        ActiveBuildStatus.fill(p, keys);
        assertEquals("hud.build.sentinel", keys[0]);
        assertEquals("hud.build.arcReactor", keys[1]);
    }

    @Test void protocolPriorityIsStableWhenSeveralAreEvolved() {
        Player p = fresh();
        p.protocols.enableRhythm();
        p.protocols.enableKillchain();
        p.protocols.enableReactionCore();
        p.protocols.evolveRhythm();
        p.protocols.evolveKillchain();
        p.protocols.evolveReactionCore();

        String[] keys = new String[2];
        ActiveBuildStatus.fill(p, keys);
        assertEquals("hud.build.rhythmAccelerator", keys[0]);
    }

    @Test void emptyBuildProducesNoTags() {
        String[] keys = new String[2];
        ActiveBuildStatus.fill(fresh(), keys);
        assertNull(keys[0]);
        assertNull(keys[1]);
    }
}
