#!/usr/bin/env python3
from __future__ import annotations

import tempfile
import unittest
from pathlib import Path

import verify_final_atlas


class FinalAtlasVerifierTest(unittest.TestCase):
    def test_parse_atlas_preserves_paths_pages_and_frame_indices(self) -> None:
        text = """game.png
size: 512, 512
format: RGBA8888
filter: Nearest,Nearest
repeat: none
survivor/rex/n/idle
  rotate: false
  xy: 0, 0
  size: 96, 96
  index: 0
survivor/rex/n/idle
  rotate: false
  xy: 96, 0
  size: 96, 96
  index: 1

game2.png
size: 512, 512
format: RGBA8888
filter: Nearest,Nearest
repeat: none
boss/alpha/e/attack
  rotate: false
  xy: 0, 0
  size: 128, 128
  index: 7
"""
        with tempfile.TemporaryDirectory() as directory:
            atlas = Path(directory) / "game.atlas"
            atlas.write_text(text, encoding="utf-8")
            parsed = verify_final_atlas.parse_atlas(atlas)
            pages = verify_final_atlas.atlas_pages(atlas)

        self.assertEqual({0, 1}, parsed["survivor/rex/n/idle"])
        self.assertEqual({7}, parsed["boss/alpha/e/attack"])
        self.assertEqual(["game.png", "game2.png"], pages)
        self.assertNotIn("game.png", parsed)
        self.assertNotIn("game2.png", parsed)

    def test_expected_contract_applies_fast_run_override_only_to_run(self) -> None:
        layout = {
            "directions": ["n", "e"],
            "motionOrder": ["idle", "run"],
            "standardFrames": {"idle": 4, "run": 8},
            "bossFrames": {"idle": 6, "run": 8},
            "actors": [
                {"id": "wraith", "root": "survivor/wraith", "profile": "standard", "runFrames": 10},
                {"id": "alpha", "root": "boss/alpha", "profile": "boss"},
            ],
        }
        expected = verify_final_atlas.expected_contract(layout)

        self.assertEqual(set(range(4)), expected["survivor/wraith/n/idle"])
        self.assertEqual(set(range(10)), expected["survivor/wraith/e/run"])
        self.assertEqual(set(range(6)), expected["boss/alpha/n/idle"])
        self.assertEqual(set(range(8)), expected["boss/alpha/e/run"])
        self.assertEqual(8, len(expected))


if __name__ == "__main__":
    unittest.main()
