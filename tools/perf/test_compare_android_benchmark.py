import importlib.util
import pathlib
import unittest

ROOT = pathlib.Path(__file__).resolve().parents[2]
MODULE_PATH = ROOT / "tools" / "perf" / "compare_android_benchmark.py"
spec = importlib.util.spec_from_file_location("compare_android_benchmark", MODULE_PATH)
mod = importlib.util.module_from_spec(spec)
spec.loader.exec_module(mod)

def sample(**overrides):
    data = {
        "scenario": "loaded-40-enemy-ring",
        "targetFps": 60,
        "averageFps": 58.0,
        "p95FrameMs": 19.0,
        "p99FrameMs": 24.0,
        "jankRatio": 0.08,
        "stable": False,
        "thermalLevel": "NORMAL",
        "effectiveFxQuality": 0.76,
        "activeEnemies": 40,
        "activeProjectiles": 6,
    }
    data.update(overrides)
    return data

class CompareAndroidBenchmarkTest(unittest.TestCase):
    def test_equal_runs_pass(self):
        result = mod.compare(sample(), sample())
        self.assertTrue(result["comparable"])
        self.assertEqual([], result["regressions"])

    def test_detects_relative_regressions(self):
        current = sample(averageFps=46.0, p95FrameMs=24.0, p99FrameMs=32.0, jankRatio=0.16)
        result = mod.compare(sample(), current)
        self.assertTrue(result["comparable"])
        self.assertGreaterEqual(len(result["regressions"]), 4)

    def test_thermal_pressure_skips_comparison(self):
        result = mod.compare(sample(), sample(thermalLevel="SEVERE"))
        self.assertFalse(result["comparable"])
        self.assertIn("thermal", result["reason"])

    def test_different_target_skips_comparison(self):
        result = mod.compare(sample(), sample(targetFps=90))
        self.assertFalse(result["comparable"])

    def test_materially_lighter_workload_skips_comparison(self):
        result = mod.compare(sample(), sample(activeEnemies=30))
        self.assertFalse(result["comparable"])

    def test_legacy_baseline_schema_skips_instead_of_failing(self):
        baseline = {
            "targetFps": 60,
            "averageFps": 58.0,
            "p95FrameMs": 19.0,
            "p99FrameMs": 24.0,
            "jankRatio": 0.08,
            "stable": False,
        }
        result = mod.compare(baseline, sample())
        self.assertFalse(result["comparable"])
        self.assertIn("incompatible baseline", result["reason"])
        self.assertEqual([], result["regressions"])

if __name__ == "__main__":
    unittest.main()
