package com.deadlinezero.game.visual;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.utils.Array;
import com.deadlinezero.game.entities.Projectile;

final class SingularityImpactTrackerTest {
    @Test void activeSingularityEmitsExactlyOneImpactWhenItEnds() {
        SingularityImpactTracker tracker = new SingularityImpactTracker();
        Array<Projectile> projectiles = new Array<>();
        Projectile projectile = singularity(1L, 3f, -2f, true);
        projectiles.add(projectile);

        tracker.update(projectiles, .016f);
        assertEquals(0, tracker.impacts().size);
        assertEquals(0, tracker.consumeTriggeredCount());

        projectile.active = false;
        tracker.update(projectiles, .016f);
        assertEquals(1, tracker.impacts().size);
        assertEquals(1, tracker.consumeTriggeredCount());
        assertEquals(3f, tracker.impacts().first().x, .0001f);
        assertEquals(-2f, tracker.impacts().first().y, .0001f);

        tracker.update(projectiles, .016f);
        assertEquals(1, tracker.impacts().size);
        assertEquals(0, tracker.consumeTriggeredCount());
    }

    @Test void pooledReuseStillEmitsPreviousSingularityAtLastKnownPosition() {
        SingularityImpactTracker tracker = new SingularityImpactTracker();
        Array<Projectile> projectiles = new Array<>();
        Projectile projectile = singularity(4L, -5f, 6f, true);
        projectiles.add(projectile);
        tracker.update(projectiles, .016f);

        projectile.generation = 5L;
        projectile.singularity = false;
        projectile.active = true;
        projectile.position.set(11f, 12f);
        tracker.update(projectiles, .016f);

        assertEquals(1, tracker.impacts().size);
        assertEquals(-5f, tracker.impacts().first().x, .0001f);
        assertEquals(6f, tracker.impacts().first().y, .0001f);
        assertEquals(1, tracker.consumeTriggeredCount());
    }

    @Test void singularityThatEndsBeforeFirstRenderStillProducesImpact() {
        SingularityImpactTracker tracker = new SingularityImpactTracker();
        Array<Projectile> projectiles = new Array<>();
        projectiles.add(singularity(9L, 1.5f, 2.5f, false));

        tracker.update(projectiles, .016f);

        assertEquals(1, tracker.impacts().size);
        assertEquals(1, tracker.consumeTriggeredCount());
        assertEquals(1.5f, tracker.impacts().first().x, .0001f);
        assertEquals(2.5f, tracker.impacts().first().y, .0001f);
    }

    @Test void impactsExpireAfterTheirVisualLifetime() {
        SingularityImpactTracker tracker = new SingularityImpactTracker();
        Array<Projectile> projectiles = new Array<>();
        projectiles.add(singularity(2L, 0f, 0f, false));
        tracker.update(projectiles, 0f);
        assertEquals(1, tracker.impacts().size);

        tracker.update(projectiles, SingularityImpactTracker.IMPACT_LIFETIME + .01f);
        assertEquals(0, tracker.impacts().size);
    }

    @Test void impactProgressMovesFromZeroTowardOne() {
        SingularityImpactTracker tracker = new SingularityImpactTracker();
        Array<Projectile> projectiles = new Array<>();
        projectiles.add(singularity(3L, 0f, 0f, false));
        tracker.update(projectiles, 0f);
        float start = tracker.impacts().first().progress();
        tracker.update(projectiles, SingularityImpactTracker.IMPACT_LIFETIME * .5f);
        float middle = tracker.impacts().first().progress();
        assertEquals(0f, start, .0001f);
        assertTrue(middle > .45f && middle < .55f);
    }

    private static Projectile singularity(long generation, float x, float y, boolean active) {
        Projectile projectile = new Projectile();
        projectile.generation = generation;
        projectile.position.set(x, y);
        projectile.singularity = true;
        projectile.active = active;
        return projectile;
    }
}
