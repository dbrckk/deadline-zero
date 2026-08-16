package com.deadlinezero.game.world;

/**
 * Pure diagnostic model for CI balance guardrails. It never feeds runtime difficulty.
 * The score intentionally combines core enemy scaling, assault-band spawn tempo and
 * nominal biome hazard pressure so biome boundaries cannot hide abrupt spikes.
 */
public final class StageCombatPressureAudit {
    public record Snapshot(int stage, float hpMultiplier, float damageMultiplier, float speedMultiplier,
                           float assaultSpawnInterval, float hazardInterval, float nominalHazardDamage,
                           float compositePressure) { }

    private StageCombatPressureAudit() { }

    public static Snapshot snapshot(int stage) {
        int s = Math.max(1, stage);
        int i = s - 1;
        float hp = 1f + i * .16f + i * i * .006f;
        float damage = 1f + i * .095f;
        float speed = Math.min(1.42f, 1f + i * .018f);
        float stageAcceleration = Math.min(.11f, i * .008f);
        float spawnInterval = Math.max(.055f, .34f - stageAcceleration);

        float hazardInterval = Float.POSITIVE_INFINITY;
        float hazardDamage = 0f;
        if (s >= 20) {
            hazardInterval = Math.max(8.8f, 14.2f - Math.min(20, s - 20) * .18f);
            float rift = 17f + (s - 20) * .46f;
            float burst = 7.5f + (s - 20) * .24f;
            float beam = 8.5f + (s - 20) * .28f;
            hazardDamage = (rift + burst + beam) / 3f;
        } else if (s >= 10) {
            hazardInterval = Math.max(10.2f, 16.5f - Math.min(9, s - 10) * .25f);
            float lava = 15f + (s - 10) * .55f;
            float steam = 11f + (s - 10) * .42f;
            float heat = 8f + (s - 10) * .34f;
            hazardDamage = (lava + steam + heat) / 3f;
        }

        float core = hp * damage * speed / spawnInterval;
        float hazardPerSecond = Float.isFinite(hazardInterval) ? hazardDamage / hazardInterval : 0f;
        float biomeRolePressure = s >= 20 ? 1.055f : (s >= 10 ? 1.025f : 1f);
        float composite = core * biomeRolePressure * (1f + hazardPerSecond * .018f);
        return new Snapshot(s, hp, damage, speed, spawnInterval, hazardInterval, hazardDamage, composite);
    }

    public static float relativeJump(int fromStage, int toStage) {
        float from = snapshot(fromStage).compositePressure();
        float to = snapshot(toStage).compositePressure();
        return from <= 0f ? 0f : to / from - 1f;
    }
}
