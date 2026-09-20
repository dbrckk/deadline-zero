#!/usr/bin/env python3
from __future__ import annotations

import json
import tempfile
import unittest
from pathlib import Path

import validate_environment_art_contract as target


class EnvironmentArtContractTest(unittest.TestCase):
    def contract(self):
        return {
            "biomes": [{"id": f"b{i}"} for i in range(5)],
            "slots": [
                {
                    "path": f"floor/s{i}",
                    "kind": "tile",
                    "tileable": True,
                    "alpha": False,
                }
                for i in range(14)
            ],
        }

    def test_expected_matrix_is_seventy_unique_regions(self):
        keys = target.expected_keys(self.contract())
        self.assertEqual(70, len(keys))
        self.assertEqual(70, len(set(keys)))

    def test_atlas_coverage_ignores_page_declarations(self):
        contract = self.contract()
        key = target.expected_keys(contract)[0]
        text = f"""environment-b0.png
size: 256, 256
format: RGBA8888
filter: Linear,Linear
repeat: none
{key}
  rotate: false
  xy: 0, 0
  size: 256, 256
  orig: 256, 256
  offset: 0, 0
  index: -1
"""
        with tempfile.TemporaryDirectory() as directory:
            atlas = Path(directory) / "game.atlas"
            atlas.write_text(text, encoding="utf-8")
            present, missing = target.coverage(contract, atlas)
        self.assertEqual([key], present)
        self.assertEqual(69, len(missing))

    def test_load_contract_rejects_duplicate_slots(self):
        contract = self.contract()
        contract["slots"][1]["path"] = contract["slots"][0]["path"]
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "contract.json"
            path.write_text(json.dumps(contract), encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "slot paths must be unique"):
                target.load_contract(path)


if __name__ == "__main__":
    unittest.main()
