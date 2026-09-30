import importlib.util
import unittest
from pathlib import Path

MODULE_PATH = Path(__file__).with_name("build_runtime_release_evidence.py")
SPEC = importlib.util.spec_from_file_location("build_runtime_release_evidence", MODULE_PATH)
MODULE = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(MODULE)


def performance(scenario):
    return {
        "scenario": scenario,
        "targetFps": 60,
        "averageFps": 58.0,
        "p95FrameMs": 18.0,
        "p99FrameMs": 22.0,
        "jankRatio": 0.03,
        "thermalLevel": "NORMAL",
        "effectiveFxQuality": 0.9,
    }


def profiles():
    rows = []
    ceilings = {"LOW": 0.50, "MEDIUM": 0.68, "HIGH": 0.86, "ULTRA": 1.0}
    for quality, ceiling in ceilings.items():
        for fps in (60, 90, 120):
            rows.append({
                "quality": quality,
                "qualityCeiling": ceiling,
                "requestedFps": fps,
                "effectiveFps": fps,
                "effectiveFxQuality": ceiling,
            })
    return {"schemaVersion": 1, "combinations": rows}


class RuntimeReleaseEvidenceTest(unittest.TestCase):
    def test_complete_clean_evidence_passes(self):
        result = MODULE.build_evidence(
            {"ok": True, "findings": []},
            performance("loaded-40-enemy-ring"),
            performance("horde-160-projectile-180"),
            profiles(),
            "abc123",
        )
        self.assertTrue(result["automatedRuntimeGatePassed"])
        self.assertEqual([], result["errors"])
        self.assertEqual(12, result["graphicsProfiles"]["combinationCount"])
        self.assertEqual("abc123", result["commit"])

    def test_missing_profile_combination_fails(self):
        profile_data = profiles()
        profile_data["combinations"].pop()
        result = MODULE.build_evidence(
            {"ok": True, "findings": []},
            performance("loaded"),
            performance("stress"),
            profile_data,
        )
        self.assertFalse(result["automatedRuntimeGatePassed"])
        self.assertTrue(any("12 supported combinations" in error for error in result["errors"]))

    def test_crash_finding_fails(self):
        result = MODULE.build_evidence(
            {"ok": False, "findings": [{"type": "java_crash"}]},
            performance("loaded"),
            performance("stress"),
            profiles(),
        )
        self.assertFalse(result["automatedRuntimeGatePassed"])
        self.assertGreaterEqual(len(result["errors"]), 1)

    def test_fx_ceiling_violation_fails(self):
        profile_data = profiles()
        profile_data["combinations"][0]["effectiveFxQuality"] = 0.9
        result = MODULE.build_evidence(
            {"ok": True, "findings": []},
            performance("loaded"),
            performance("stress"),
            profile_data,
        )
        self.assertFalse(result["automatedRuntimeGatePassed"])
        self.assertTrue(any("FX ceiling" in error for error in result["errors"]))


if __name__ == "__main__":
    unittest.main()
