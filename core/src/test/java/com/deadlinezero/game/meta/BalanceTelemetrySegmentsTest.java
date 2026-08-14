package com.deadlinezero.game.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.utils.Array;

final class BalanceTelemetrySegmentsTest {
    @Test void groupsAndSortsNumericDimensions() {
        Array<BalanceRunSample> samples = new Array<>();
        samples.add(sample(3, 2, true, "REDLINE", "REX", "ar9"));
        samples.add(sample(1, 0, false, "REDLINE", "NYX", "shotgun"));
        samples.add(sample(3, 2, true, "BLOOD MOON", "REX", "ar9"));

        List<BalanceTelemetrySegments.Segment> stages = BalanceTelemetrySegments.group(samples, BalanceTelemetrySegments.Dimension.STAGE);
        assertEquals(List.of("1", "3"), stages.stream().map(BalanceTelemetrySegments.Segment::key).toList());
        assertEquals(2, stages.get(1).summary().runs());
        assertEquals(1f, stages.get(1).summary().winRate(), .0001f);

        List<BalanceTelemetrySegments.Segment> threats = BalanceTelemetrySegments.group(samples, BalanceTelemetrySegments.Dimension.THREAT);
        assertEquals(List.of("0", "2"), threats.stream().map(BalanceTelemetrySegments.Segment::key).toList());
    }

    @Test void groupsCategoricalDimensionsDeterministically() {
        Array<BalanceRunSample> samples = new Array<>();
        samples.add(sample(1, 0, true, "REDLINE", "REX", "ar9"));
        samples.add(sample(1, 0, false, "BLOOD MOON", "NYX", "shotgun"));
        samples.add(sample(1, 0, true, "REDLINE", "REX", "ar9"));

        var contracts = BalanceTelemetrySegments.group(samples, BalanceTelemetrySegments.Dimension.CONTRACT);
        assertEquals(List.of("BLOOD MOON", "REDLINE"), contracts.stream().map(BalanceTelemetrySegments.Segment::key).toList());
        assertEquals(2, contracts.get(1).summary().runs());
        assertTrue(contracts.get(1).summary().averageDps() > 0f);
    }

    private static BalanceRunSample sample(int stage, int threat, boolean victory, String contract, String survivor, String weapon) {
        return new BalanceRunSample(1, stage, threat, 1, victory, 100f, 20, 5000f, 800f, 300f, 80f,
            contract, survivor, weapon, 0, false);
    }
}
