#!/usr/bin/env python3
from __future__ import annotations

import unittest

import upsert_environment_atlas as target


class EnvironmentAtlasUpsertTest(unittest.TestCase):
    def test_appends_new_page_without_touching_existing_page(self):
        existing = """actor.png
size: 96, 96
format: RGBA8888
filter: Linear,Linear
repeat: none
survivor/rex/n/idle
  rotate: false
  xy: 0, 0
  size: 96, 96
  index: 0
"""
        fragment = """environment-quarantine_yard.png
size: 1024, 1024
format: RGBA8888
filter: Linear,Linear
repeat: none
environment/quarantine_yard/floor/concrete_a
  rotate: false
  xy: 0, 0
  size: 256, 256
  index: -1
"""
        result = target.upsert_atlas_text(existing, fragment)
        self.assertIn("actor.png", result)
        self.assertIn("survivor/rex/n/idle", result)
        self.assertEqual(1, result.count("environment-quarantine_yard.png"))

    def test_replaces_existing_environment_page_instead_of_duplicating_it(self):
        existing = """actor.png
size: 96, 96
format: RGBA8888
filter: Linear,Linear
repeat: none
survivor/rex/n/idle
  rotate: false
  xy: 0, 0
  size: 96, 96
  index: 0

environment-null_sector.png
size: 1024, 1024
format: RGBA8888
filter: Linear,Linear
repeat: none
environment/null_sector/floor/old
  rotate: false
  xy: 0, 0
  size: 256, 256
  index: -1
"""
        fragment = """environment-null_sector.png
size: 1024, 1024
format: RGBA8888
filter: Linear,Linear
repeat: none
environment/null_sector/floor/concrete_a
  rotate: false
  xy: 0, 0
  size: 256, 256
  index: -1
"""
        result = target.upsert_atlas_text(existing, fragment)
        self.assertEqual(1, result.count("environment-null_sector.png"))
        self.assertNotIn("floor/old", result)
        self.assertIn("floor/concrete_a", result)


if __name__ == "__main__":
    unittest.main()
