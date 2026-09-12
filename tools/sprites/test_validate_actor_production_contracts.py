#!/usr/bin/env python3
import importlib.util
from pathlib import Path
import unittest

MODULE_PATH = Path(__file__).with_name("validate_actor_production_contracts.py")
spec = importlib.util.spec_from_file_location("production_contracts", MODULE_PATH)
module = importlib.util.module_from_spec(spec)
spec.loader.exec_module(module)


class CompleteRosterContractTest(unittest.TestCase):
    def actors(self):
        return {name: {"status": "accepted"} for name in module.EXPECTED_COMPLETE_ROSTER}

    def test_accepts_exact_complete_roster(self):
        module.validate_complete_roster(self.actors())

    def test_rejects_missing_actor(self):
        actors = self.actors()
        actors.pop("null_ward")
        with self.assertRaises(SystemExit):
            module.validate_complete_roster(actors)

    def test_rejects_unexpected_accepted_actor(self):
        actors = self.actors()
        actors["unreviewed_enemy"] = {"status": "accepted"}
        with self.assertRaises(SystemExit):
            module.validate_complete_roster(actors)

    def test_ignores_nonaccepted_candidate(self):
        actors = self.actors()
        actors["future_candidate"] = {"status": "candidate"}
        module.validate_complete_roster(actors)


if __name__ == "__main__":
    unittest.main()
