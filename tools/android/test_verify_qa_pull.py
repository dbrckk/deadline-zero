import subprocess
import tempfile
import unittest
from pathlib import Path


class VerifyQaPullTest(unittest.TestCase):
    def test_retries_transient_adb_pull_and_requires_nonempty_destination(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            fake_adb = root / "adb"
            attempts = root / "attempts"
            destination = root / "capture.png"
            fake_adb.write_text(
                "#!/usr/bin/env bash\n"
                "set -eu\n"
                f"attempts='{attempts}'\n"
                "count=0\n"
                "[ ! -f \"$attempts\" ] || count=$(cat \"$attempts\")\n"
                "count=$((count+1))\n"
                "printf '%s' \"$count\" > \"$attempts\"\n"
                "if [ \"$count\" -lt 2 ]; then exit 1; fi\n"
                "printf 'png' > \"$3\"\n"
            )
            fake_adb.chmod(0o755)
            script = Path(__file__).with_name("pull_qa_file.sh")
            result = subprocess.run(
                [str(script), "/sdcard/fake.png", str(destination)],
                env={"PATH": f"{root}:/usr/bin:/bin", "QA_PULL_RETRIES": "3", "QA_PULL_DELAY_SECONDS": "0"},
                text=True,
                capture_output=True,
            )
            self.assertEqual(0, result.returncode, result.stderr)
            self.assertEqual("2", attempts.read_text())
            self.assertEqual(b"png", destination.read_bytes())

    def test_fails_after_retry_budget_is_exhausted(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            fake_adb = root / "adb"
            fake_adb.write_text("#!/usr/bin/env bash\nexit 1\n")
            fake_adb.chmod(0o755)
            script = Path(__file__).with_name("pull_qa_file.sh")
            result = subprocess.run(
                [str(script), "/sdcard/fake.png", str(root / "capture.png")],
                env={"PATH": f"{root}:/usr/bin:/bin", "QA_PULL_RETRIES": "2", "QA_PULL_DELAY_SECONDS": "0"},
                text=True,
                capture_output=True,
            )
            self.assertNotEqual(0, result.returncode)


if __name__ == "__main__":
    unittest.main()
