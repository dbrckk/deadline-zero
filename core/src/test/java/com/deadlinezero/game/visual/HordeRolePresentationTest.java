package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deadlinezero.game.entities.Enemy;
import org.junit.jupiter.api.Test;

final class HordeRolePresentationTest {
    @Test
    void coreZombieRolesRemainSemanticallyDistinctWithoutColor() {
        HordeRolePresentation.RoleStyle runner = HordeRolePresentation.style(Enemy.Type.RUNNER);
        HordeRolePresentation.RoleStyle brute = HordeRolePresentation.style(Enemy.Type.BRUTE);
        HordeRolePresentation.RoleStyle ranged = HordeRolePresentation.style(Enemy.Type.RANGED);
        HordeRolePresentation.RoleStyle boss = HordeRolePresentation.style(Enemy.Type.BOSS);

        assertEquals(HordeRolePresentation.Motion.FAST, runner.motion());
        assertTrue(brute.shadowScale() > runner.shadowScale());
        assertEquals(HordeRolePresentation.Telegraph.PREFIRE, ranged.telegraph());
        assertEquals(HordeRolePresentation.Telegraph.PHASED, boss.telegraph());
        assertNotEquals(runner.silhouetteClass(), brute.silhouetteClass());
    }

    @Test
    void specialistRolesHaveNonColorGeometry() {
        assertEquals(HordeRolePresentation.Silhouette.SHIELDED,
            HordeRolePresentation.style(Enemy.Type.SHIELDED).silhouetteClass());
        assertEquals(HordeRolePresentation.Silhouette.PHANTOM,
            HordeRolePresentation.style(Enemy.Type.PHANTOM).silhouetteClass());
        assertEquals(HordeRolePresentation.Telegraph.SUPPORT,
            HordeRolePresentation.style(Enemy.Type.REGENERATOR).telegraph());
    }
}
