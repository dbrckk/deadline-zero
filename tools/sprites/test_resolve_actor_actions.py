import unittest

from resolve_actor_actions import normalize, resolve


class ResolveActorActionsTest(unittest.TestCase):
    def test_exact_match_wins(self):
        result = resolve(
            {
                "idle": ["Idle"],
                "run": ["Run"],
                "attack": ["Punch"],
                "hit": ["HitReact"],
                "death": ["Death"],
            },
            ["Idle", "Run", "Punch", "HitReact", "Death"],
        )
        self.assertEqual(result["missing"], [])
        self.assertEqual(result["resolution_method"]["run"], "exact")

    def test_import_suffixes_are_ignored(self):
        result = resolve(
            {
                "idle": ["Idle"],
                "run": ["Walk", "Run"],
                "attack": ["Punch"],
                "hit": ["HitReact"],
                "death": ["Death"],
            },
            [
                "Idle_CharacterArmature",
                "Walk_CharacterArmature",
                "Punch_CharacterArmature",
                "HitReact_CharacterArmature",
                "Death_CharacterArmature",
            ],
        )
        self.assertEqual(result["missing"], [])
        self.assertTrue(all(method == "canonical" for method in result["resolution_method"].values()))

    def test_ambiguous_canonical_match_does_not_guess(self):
        result = resolve(
            {"idle": [], "run": ["Run"], "attack": [], "hit": [], "death": []},
            ["Run_Armature", "Run_Rig"],
        )
        self.assertIn("run", result["missing"])
        self.assertIn("Run", result["ambiguities"]["run"])
        self.assertNotIn("run", result["resolved"])

    def test_blender_numeric_suffix_is_ignored(self):
        self.assertEqual(normalize("Death_CharacterArmature.001"), "death")


if __name__ == "__main__":
    unittest.main()
