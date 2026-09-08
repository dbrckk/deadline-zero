import unittest

from resolve_actor_actions import canonical, resolve


class ResolveActorActionsTest(unittest.TestCase):
    def test_exact_match_wins(self):
        result = resolve(["Idle", "Run", "Punch", "HitReact", "Death"], {
            "idle": ["Idle"],
            "run": ["Run"],
            "attack": ["Punch"],
            "hit": ["HitReact"],
            "death": ["Death"],
        })
        self.assertEqual(result["missing"], [])
        self.assertEqual(result["resolution_method"]["run"], "exact")

    def test_import_suffixes_are_ignored(self):
        result = resolve([
            "Idle_CharacterArmature",
            "Walk_CharacterArmature",
            "Punch_CharacterArmature",
            "HitReact_CharacterArmature",
            "Death_CharacterArmature",
        ], {
            "idle": ["Idle"],
            "run": ["Walk", "Run"],
            "attack": ["Punch"],
            "hit": ["HitReact"],
            "death": ["Death"],
        })
        self.assertEqual(result["missing"], [])
        self.assertTrue(all(method == "canonical" for method in result["resolution_method"].values()))

    def test_ambiguous_canonical_match_does_not_guess(self):
        result = resolve(["Run_Armature", "Run_Rig"], {
            "idle": [], "run": ["Run"], "attack": [], "hit": [], "death": []
        })
        self.assertIn("run", result["missing"])
        self.assertIn("Run", result["ambiguities"]["run"])

    def test_blender_numeric_suffix_is_ignored(self):
        self.assertEqual(canonical("Death_CharacterArmature.001"), "death")


if __name__ == "__main__":
    unittest.main()
