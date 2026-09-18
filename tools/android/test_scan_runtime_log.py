import importlib.util
import pathlib
import unittest

ROOT = pathlib.Path(__file__).resolve().parents[2]
MODULE_PATH = ROOT / "tools" / "android" / "scan_runtime_log.py"
spec = importlib.util.spec_from_file_location("scan_runtime_log", MODULE_PATH)
mod = importlib.util.module_from_spec(spec)
spec.loader.exec_module(mod)

class RuntimeLogScannerTest(unittest.TestCase):
    def test_clean_log_passes(self):
        result = mod.scan("I ActivityManager: Start proc com.deadlinezero.game\nI DeadlineZero: running")
        self.assertTrue(result["ok"])
        self.assertEqual([], result["findings"])

    def test_detects_package_anr(self):
        result = mod.scan("E ActivityManager: ANR in com.deadlinezero.game (com.deadlinezero.game/.android.AndroidLauncher)")
        self.assertFalse(result["ok"])
        self.assertEqual("anr", result["findings"][0]["type"])

    def test_detects_java_crash_only_for_game_process(self):
        log = """E AndroidRuntime: FATAL EXCEPTION: main
E AndroidRuntime: Process: com.deadlinezero.game, PID: 1234
E AndroidRuntime: java.lang.IllegalStateException: boom
"""
        result = mod.scan(log)
        self.assertFalse(result["ok"])
        self.assertEqual("java_crash", result["findings"][0]["type"])

    def test_ignores_other_process_java_crash(self):
        log = """E AndroidRuntime: FATAL EXCEPTION: main
E AndroidRuntime: Process: com.example.other, PID: 99
"""
        self.assertTrue(mod.scan(log)["ok"])

    def test_ignores_instrumentation_process_with_package_prefix(self):
        log = """E AndroidRuntime: FATAL EXCEPTION: Instr: androidx.test.runner.AndroidJUnitRunner
E AndroidRuntime: Process: com.deadlinezero.game.test, PID: 2222
"""
        self.assertTrue(mod.scan(log)["ok"])

    def test_ignores_instrumentation_process_anr_with_package_prefix(self):
        self.assertTrue(mod.scan("E ActivityManager: ANR in com.deadlinezero.game.test (com.deadlinezero.game.test)").get("ok"))

    def test_exact_game_package_still_matches_with_activity_suffix(self):
        result = mod.scan("E ActivityManager: ANR in com.deadlinezero.game (com.deadlinezero.game/.android.AndroidLauncher)")
        self.assertFalse(result["ok"])

    def test_detects_native_crash_when_package_is_in_context(self):
        log = """I DEBUG: Cmdline: com.deadlinezero.game
F libc: Fatal signal 11 (SIGSEGV), code 1, fault addr 0x0 in tid 1234
"""
        result = mod.scan(log)
        self.assertFalse(result["ok"])
        self.assertEqual("native_crash", result["findings"][0]["type"])

    def test_ignores_intentional_force_stop(self):
        self.assertTrue(mod.scan("I ActivityManager: Force stopping com.deadlinezero.game appid=12345 user=0")["ok"])

if __name__ == "__main__":
    unittest.main()
