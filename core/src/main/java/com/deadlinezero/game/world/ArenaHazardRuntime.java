package com.deadlinezero.game.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.deadlinezero.game.meta.RunStageContext;
import com.deadlinezero.game.meta.ThreatTierRules;

/**
 * Deterministic telegraphed arena hazards used by endgame pressure, biome pressure and delayed enemy death bursts.
 * Gameplay never deals damage during WARNING; each ACTIVE hazard can hit the player at most once.
 */
public final class ArenaHazardRuntime {
    public enum Type { ORBITAL_STRIKE, DEATH_BURST, LAVA_VENT, STEAM_JET, HEAT_LINE }
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
    private final int stage;
    private final int threatTier;
    private final int seed;
    private int periodicIndex;
    private int foundryIndex;
    private float periodicTimer;
    private float foundryTimer;

    public ArenaHazardRuntime() {
        this(RunStageContext.stage(), RunStageContext.runOrdinal(), RunStageContext.threatTier());
    }

    ArenaHazardRuntime(int stage, int runOrdinal, int threatTier) {
        this.stage = Math.max(1, stage);
        this.threatTier = ThreatTierRules.sanitizeTier(threatTier);
        int x = this.stage * 0x45d9f3b + Math.max(0, runOrdinal) * 0x119de1f3 + this.threatTier * 0x27d4eb2d;
        x ^= x >>> 16;
        this.seed = x;
        this.periodicTimer = periodicInterval();
        this.foundryTimer = foundryHazardInterval() * .72f;
    }

    public boolean periodicHazardsEnabled() { return threatTier >= 5; }

    public float periodicInterval() {
        if (!periodicHazardsEnabled()) return Float.POSITIVE_INFINITY;
        return Math.max(6.2f, 12.5f - threatTier * .30f);
    }

    /** Cinder Foundry starts at stage 10 and always has its own telegraphed environmental pressure. */
    public boolean foundryHazardsEnabled() { return stage >= 10; }

    public float foundryHazardInterval() {
        if (!foundryHazardsEnabled()) return Float.POSITIVE_INFINITY;
        float stagePressure = Math.min(20, Math.max(0, stage - 10)) * .25f;
        float threatPressure = threatTier * .06f;
        return Math.max(9.5f, 16.5f - stagePressure - threatPressure);
    }

    /** Advances timers and deterministically schedules endgame and biome hazards near the player's current region. */
    public void update(float dt, float playerX, float playerY) {
        float safeDt = Math.max(0f, dt);
        if (periodicHazardsEnabled()) {
            periodicTimer -= safeDt;
            if (periodicTimer <= 0f) {
                schedulePeriodicStrike(playerX, playerY);
                periodicTimer += periodicInterval();
            }
        }
        if (foundryHazardsEnabled()) {
            foundryTimer -= safeDt;
            if (foundryTimer <= 0f) {
                scheduleFoundryHazard(playerX, playerY);
                foundryTimer += foundryHazardInterval();
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

    private void scheduleFoundryHazard(float playerX, float playerY) {
        int n = mix(seed ^ 0x51ed270b ^ foundryIndex * 0x6d2b79f5);
        int type = Math.floorMod(n, 3);
        foundryIndex++;
        if (type == 0) scheduleLavaVent(n, playerX, playerY);
        else if (type == 1) scheduleSteamJet(n, playerX, playerY);
        else scheduleHeatLine(n, playerX, playerY);
    }

    private void scheduleLavaVent(int n, float playerX, float playerY) {
        float angle = ((n >>> 4) & 0xffff) / 65535f * (float)(Math.PI * 2.0);
        float distance = 1.8f + (((n >>> 20) & 0x7f) / 127f) * 3.8f;
        float x = clamp(playerX + (float)Math.cos(angle) * distance, -28.5f, 28.5f);
        float y = clamp(playerY + (float)Math.sin(angle) * distance, -14.5f, 14.5f);
        float radius = 2.05f + Math.min(20, stage - 10) * .018f;
        float damage = 15f + (stage - 10) * .55f + threatTier * .45f;
        hazards.add(new Hazard(Type.LAVA_VENT, x, y, radius, damage, 1.18f, .46f));
    }

    private void scheduleSteamJet(int n, float playerX, float playerY) {
        float side = ((n >>> 9) & 1) == 0 ? -1f : 1f;
        float x = clamp(playerX + side * (2.2f + ((n >>> 16) & 0x3f) / 63f * 2.4f), -29f, 29f);
        float y = clamp(playerY + (((n >>> 23) & 0x7f) / 127f - .5f) * 5f, -15f, 15f);
        float damage = 11f + (stage - 10) * .42f + threatTier * .38f;
        hazards.add(new Hazard(Type.STEAM_JET, x, y, 1.45f, damage, .82f, .34f));
    }

    /** Heat lines are represented by five overlapping telegraphed nodes, so rendered circles exactly match collision. */
    private void scheduleHeatLine(int n, float playerX, float playerY) {
        boolean horizontal = ((n >>> 13) & 1) == 0;
        float damage = 8f + (stage - 10) * .34f + threatTier * .30f;
        float radius = 1.30f;
        float spacing = 2.35f;
        float centerX = clamp(playerX + (((n >>> 18) & 0x1f) / 31f - .5f) * 3.2f, -24f, 24f);
        float centerY = clamp(playerY + (((n >>> 23) & 0x1f) / 31f - .5f) * 3.2f, -11f, 11f);
        for (int i = -2; i <= 2; i++) {
            float x = horizontal ? centerX + i * spacing : centerX;
            float y = horizontal ? centerY : centerY + i * spacing;
            hazards.add(new Hazard(Type.HEAT_LINE, x, y, radius, damage, 1.04f, .28f));
        }
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
