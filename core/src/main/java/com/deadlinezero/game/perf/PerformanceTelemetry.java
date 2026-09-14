package com.deadlinezero.game.perf;

import java.util.Arrays;

/**
 * Lightweight rolling frame-time telemetry for launch-quality validation.
 * Keeps gameplay behavior untouched while exposing objective stability metrics.
 */
public final class PerformanceTelemetry {
    public record Snapshot(
        int targetFps,
        float averageFps,
        float p95FrameMs,
        float p99FrameMs,
        float jankRatio,
        boolean stable
    ) {}

    private static final int CAPACITY = 240;
    private final float[] frameMs = new float[CAPACITY];
    private int count;
    private int cursor;

    public void record(float deltaSeconds, int targetFps) {
        if (!Float.isFinite(deltaSeconds) || deltaSeconds <= 0f) return;
        float ms = Math.min(250f, deltaSeconds * 1000f);
        frameMs[cursor] = ms;
        cursor = (cursor + 1) % CAPACITY;
        if (count < CAPACITY) count++;
    }

    public int sampleCount() { return count; }

    public Snapshot snapshot(int targetFps) {
        int safeTarget = Math.max(1, targetFps);
        if (count == 0) return new Snapshot(safeTarget, 0f, 0f, 0f, 0f, false);

        float[] sorted = new float[count];
        float total = 0f;
        int jank = 0;
        float frameBudgetMs = 1000f / safeTarget;
        float jankThresholdMs = frameBudgetMs * 1.50f;

        for (int i = 0; i < count; i++) {
            float value = frameMs[i];
            sorted[i] = value;
            total += value;
            if (value > jankThresholdMs) jank++;
        }
        Arrays.sort(sorted);

        float averageFrameMs = total / count;
        float averageFps = averageFrameMs <= 0f ? 0f : 1000f / averageFrameMs;
        float p95 = percentile(sorted, .95f);
        float p99 = percentile(sorted, .99f);
        float jankRatio = jank / (float) count;

        boolean stable = count >= 60
            && averageFps >= safeTarget * .95f
            && p95 <= frameBudgetMs * 1.25f
            && jankRatio <= .05f;

        return new Snapshot(safeTarget, averageFps, p95, p99, jankRatio, stable);
    }

    private static float percentile(float[] sorted, float percentile) {
        if (sorted.length == 0) return 0f;
        int index = Math.min(sorted.length - 1,
            Math.max(0, (int)Math.ceil(percentile * sorted.length) - 1));
        return sorted[index];
    }
}
