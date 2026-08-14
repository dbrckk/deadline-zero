package com.deadlinezero.game.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.ThreatTierRules;

/**
 * Deterministic telegraphed arena hazards used by endgame pressure and delayed enemy death bursts.
 * Gameplay never deals damage during WARNING; each ACTIVE hazard can hit the player at most once.
 */
public final class ArenaHazardRuntime {
    public enum Type { ORBITAL_STRIKE, DEATH_BURST }
    public enum Phase { WARNING, ACTIVE }

    public static final class Hazard {
        private final Type type;
        private final float x;
        private final float y;
        private final float radius;
        private final float damage;
        private final float warningDuration;
        private float warningRemaining;
        private float activeRemaining;
        private boolean playerDamageConsumed;

        private Hazard(Type type, float x, float y, float radius, float damage,
                       float warningDuration, float activeDuration) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.radius = Math.max(.1f, radius);
            this.damage = Math.max(0f, damage);
            this.warningDuration = Math.max(.01f, warningDuration);
            this.warningRemaining = this.warningDuration;
            this.activeRemaining = Math.max(.01f, activeDuration);
        }

        public Type type() { return type; }
        public float x() { return x; }
        public float y() { return y; }
        public float radius() { return radius; }
        public float damage() { return damage; }
        public Phase phase() { return warningRemaining > 0f ? Phase.WARNING : Phase.ACTIVE; }
        public float warningFraction() {
            return warningRemaining <= 0f ? 0f : Math.min(1f, warningRemaining / warningDuration);
        }
        public float activeFraction() {
            return warningRemaining > 0f ? 0f : Math.max(0f, activeRemaining);
        }
        public boolean playerDamageConsumed() { return playerDamageConsumed; }
    }

    private final List<Hazard> hazards = new ArrayList<>();
    private final int threatTier;
    private final int seed;
    private int periodicIndex;
    private float periodicTimer;

    public ArenaHazardRuntime() {
        this(RunStageContext.stage(), RunStageContext.runOrdinal(), RunStageContext.threatTier());
    }

    ArenaHazardRuntime(int stage, int runOrdinal, int threatTier) {
        this.threatTier = ThreatTierRules.sanitizeTier(threatTier);
        int x = Math.max(1, stage) * 0x45d9f3b + Math.max(0, runOrdinal) * 0x119de1f3 + this.threatTier * 0x27d4eb2d;
        x ^= x >>> 16;
        this.seed = x;
        this.periodicTimer = periodicInterval();
    }

    public boolean periodicHazardsEnabled() { return threatTier >= 5; }

    public float periodicInterval() {
        if (!periodicHazardsEnabled()) return Float.POSITIVE_INFINITY;
        return Math.max(6.2f, 12.5f - threatTier * .30f);
    }

    /** Advances timers and deterministically schedules endgame strikes near the player's current region. */
    public void update(float dt, float playerX, float playerY) {
        float safeDt = Math.max(0f, dt);
        if (periodicHazardsEnabled()) {
            periodicTimer -= safeDt;
            if (periodicTimer <= 0f) {
                schedulePeriodicStrike(playerX, playerY);
                periodicTimer += periodicInterval();
            }
        }

        Iterator<Hazard> it = hazards.iterator();
        while (it.hasNext()) {
            Hazard h = it.next();
            if (h.warningRemaining > 0f) {
                h.warningRemaining -= safeDt;
                if (h.warningRemaining > 0f) continue;
            } else {
                h.activeRemaining -= safeDt;
            }
            if (h.warningRemaining <= 0f && h.activeRemaining <= 0f) it.remove();
        }
    }

    /** Delayed hostile explosion. No damage occurs until the warning has completed. */
    public void scheduleDeathBurst(float x, float y, float radius, float damage) {
        hazards.add(new Hazard(Type.DEATH_BURST, x, y, radius, damage, .48f, .24f));
    }

    /** Returns accumulated damage from newly-hit active hazards, consuming each hazard at most once. */
    public float consumePlayerDamage(float playerX, float playerY, float playerRadius) {
        float total = 0f;
        float safeRadius = Math.max(0f, playerRadius);
        for (Hazard h : hazards) {
            if (h.phase() != Phase.ACTIVE || h.playerDamageConsumed) continue;
            float rr = h.radius + safeRadius;
            float dx = playerX - h.x;
            float dy = playerY - h.y;
            if (dx * dx + dy * dy > rr * rr) continue;
            h.playerDamageConsumed = true;
            total += h.damage;
        }
        return total;
    }

    public List<Hazard> hazards() { return Collections.unmodifiableList(hazards); }
    public int activeCount() { return hazards.size(); }

    private void schedulePeriodicStrike(float playerX, float playerY) {
        int n = mix(seed + periodicIndex++ * 0x9e3779b9);
        float angle = ((n & 0xffff) / 65535f) * (float)(Math.PI * 2.0);
        float distance = 1.4f + (((n >>> 16) & 0xff) / 255f) * 4.1f;
        float x = clamp(playerX + (float)Math.cos(angle) * distance, -29f, 29f);
        float y = clamp(playerY + (float)Math.sin(angle) * distance, -15f, 15f);
        float radius = 2.25f + threatTier * .035f;
        float damage = 14f + threatTier * 1.25f;
        float warning = Math.max(.62f, 1.12f - threatTier * .018f);
        hazards.add(new Hazard(Type.ORBITAL_STRIKE, x, y, radius, damage, warning, .32f));
    }

    private static int mix(int x) {
        x ^= x >>> 16;
        x *= 0x7feb352d;
        x ^= x >>> 15;
        x *= 0x846ca68b;
        x ^= x >>> 16;
        return x;
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
