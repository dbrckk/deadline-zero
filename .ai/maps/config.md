This file is a merged representation of a subset of the codebase, containing specifically included files and files not matching ignore patterns, combined into a single document by Repomix.
The content has been processed where content has been compressed (code blocks are separated by ⋮---- delimiter).

# File Summary

## Purpose
This file contains a packed representation of a subset of the repository's contents that is considered the most important context.
It is designed to be easily consumable by AI systems for analysis, code review,
or other automated processes.

## File Format
The content is organized as follows:
1. This summary section
2. Repository information
3. Directory structure
4. Repository files (if enabled)
5. Multiple file entries, each consisting of:
  a. A header with the file path (## File: path/to/file)
  b. The full contents of the file in a code block

## Usage Guidelines
- This file should be treated as read-only. Any changes should be made to the
  original repository files, not this packed version.
- When processing this file, use the file path to distinguish
  between different files in the repository.
- Be aware that this file may contain sensitive information. Handle it with
  the same level of security as you would the original repository.

## Notes
- Some files may have been excluded based on .gitignore rules and Repomix's configuration
- Binary files are not included in this packed representation. Please refer to the Repository Structure section for a complete list of file paths, including binary files
- Only files matching these patterns are included: **/*.{py,js,mjs,cjs,ts,tsx,jsx,java,kt,kts,gd,groovy,gradle,toml,json,yaml,yml,sql,sh}
- Files matching these patterns are excluded: .ai/**, **/node_modules/**, **/.gradle/**, **/build/**, **/dist/**, **/.venv/**, **/__pycache__/**, **/.pytest_cache/**, **/.git/**, **/coverage/**, **/*.lock, **/*.min.js, **/*.map, assets/**, art/**, art_sources/**, marketing/**, colab/**, kaggle/**, discovery-cache.json, health-snapshot.json, history.json
- Files matching patterns in .gitignore are excluded
- Files matching default ignore patterns are excluded
- Content has been compressed - code blocks are separated by ⋮---- delimiter
- Files are sorted by Git change count (files with more changes are at the bottom)

# Directory Structure
```
actor-candidates.d/
  bastion.json
  boss.json
  cinder_gunner.json
  elite.json
  forge_hound.json
  harvester.json
  null_archon.json
  null_ward.json
  nyx.json
  phantom.json
  phase_stalker.json
  ranged.json
  regenerator.json
  revenant.json
  shielded.json
  slag_guard.json
  static_seer.json
  volt.json
  warden.json
  wraith.json
actor-candidates.json
actor-production-contracts.json
environment-art-contract.json
```

# Files

## File: actor-candidates.d/bastion.json
```json
{
  "schema_version": 1,
  "candidates": {
    "bastion-golden-knight-rifle": {
      "actor": "bastion",
      "role": "survivor-juggernaut",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Knight_Golden_Male.blend",
        "git_blob_sha": "9cc053899acbfca453669435ead43a10f633f64b",
        "sha256": "f81a02ff576815cffc8366c89b57d30888f45f9df3e6865245c81837d52172c9",
        "size_bytes": 1888072
      },
      "actions": {
        "idle": ["Idle"],
        "run": ["Run", "Walk"],
        "attack": ["Shoot_OneHanded", "Shoot", "Weapon", "Attack"],
        "hit": ["RecieveHit", "ReceiveHit", "HitReact", "Hit"],
        "death": ["Death"]
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [0.0, -1.0, -0.08],
        "grip_offset": [0.0, -0.01, 0.01]
      },
      "render": {
        "target_height": 1.68,
        "ortho_scale": 8.0,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "survivor/bastion"
      },
      "selection_goal": "BASTION authored survivor: heavy armored juggernaut silhouette, explicit rifle geometry, native ranged attack and clear phone-scale distinction from Rex and Nyx.",
      "role_gate": {
        "requires_native_ranged_attack": true,
        "requires_visible_weapon_or_projectile_source": true,
        "requires_explicit_weapon_geometry": true,
        "requires_phone_scale_distinction": true,
        "metrics": {
          "min_median_bbox_width_px": 38,
          "min_median_bbox_height_px": 58,
          "min_median_bbox_area_px2": 2250
        }
      },
      "verdict": {
        "status": "qualified",
        "reason": "Exact 232-frame contract, immutable source fingerprint, heavy-silhouette gates and phone-scale visual QA passed; ready for production atlas staging and Android acceptance.",
        "validation_run_id": 34468282926,
        "validation_artifact_id": 10148561350
      }
    }
  }
}
```

## File: actor-candidates.d/boss.json
```json
{
  "schema_version": 1,
  "candidates": {
    "boss-ultimate-yeti": {
      "actor": "boss",
      "role": "boss",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "repository": "Hakhyun-Kim/constellation-defense",
        "commit": "4431655c1a29638495c6ab3d4543d37c8d6deed6",
        "path": "assets/models/quaternius-yeti.glb",
        "git_blob_sha": "a497b06d544af508e561decee951fc1f27179e02",
        "size_bytes": 121520,
        "sha256": "68b26d4793cd1567139a72a009845be1c1f3b09e871653fd254d891bcef69ad9"
      },
      "actions": {
        "idle": ["Idle", "Stand", "Rest"],
        "run": ["Walk", "Run", "Move"],
        "attack": ["Bite_Front", "Punch", "Attack", "Headbutt", "Bite", "Weapon"],
        "hit": ["HitReact", "HitRecieve", "HitReceive", "ReceiveHit", "RecieveHit", "Damage"],
        "death": ["Death", "Die", "Dying"]
      },
      "render": {
        "target_height": 2.2,
        "ortho_scale": 9.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "boss/alpha"
      },
      "selection_goal": "Third deterministic BOSS candidate after Dragon_Evolved and MushroomKing failed production gates. Big Yeti must prove dominant phone-scale silhouette, intact materials, exact shared animation compatibility and real Android readability before qualification.",
      "role_gate": {
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "requires_distinct_from_existing_actors": true,
        "metrics": {
          "min_median_bbox_width_px": 48,
          "min_median_bbox_height_px": 52,
          "min_median_bbox_area_px2": 2850
        }
      },
      "verdict": {
        "status": "accepted",
        "validation_run_id": 34390994184,
        "validation_artifact_id": 10119907335,
        "source_sha256": "68b26d4793cd1567139a72a009845be1c1f3b09e871653fd254d891bcef69ad9",
        "frame_count": 232,
        "minimum_master_margin_px": 100,
        "median_bbox_width_px": 62.0,
        "median_bbox_height_px": 60.0,
        "median_bbox_area_px2": 3658.0,
        "android_runtime_run_id": 34390994118,
        "android_visual_artifact_id": 10119773756,
        "android_visual_qa_pass": true,
        "reason": "Actor Candidate Validation #117 passed the exact 232-frame contract and BOSS silhouette gates. Verify #1469 passed core, Android build and real Android runtime capture. Crowded phone-scale evidence keeps the broad white/blue Yeti immediately readable and distinct from BRUTE, REGENERATOR and the other accepted actors. BOSS is accepted for production."
      }
    }
  }
}
```

## File: actor-candidates.d/cinder_gunner.json
```json
{
  "schema_version": 1,
  "candidates": {
    "cinder-gunner-blue-soldier-female-rifle": {
      "actor": "cinder_gunner",
      "role": "biome-cinder-gunner",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/BlueSoldier_Female.blend",
        "git_blob_sha": "ceaceb30aa5389cea266acf3becdae863c5820d4",
        "size_bytes": 2197748,
        "sha256": "9f79873e2e3452f4a3ac64ff7ba3388c01d6e62633f3477ab8a27d844c3adfb2"
      },
      "actions": {
        "idle": [
          "Idle"
        ],
        "run": [
          "Walk",
          "Run"
        ],
        "attack": [
          "Shoot_OneHanded",
          "Shoot",
          "Weapon",
          "Attack"
        ],
        "hit": [
          "RecieveHit",
          "ReceiveHit",
          "HitReact",
          "Hit"
        ],
        "death": [
          "Death"
        ]
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [
          0,
          -1,
          -0.08
        ],
        "grip_offset": [
          0,
          -0.01,
          0.01
        ]
      },
      "render": {
        "target_height": 1.45,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "enemy/biome/cinder_gunner"
      },
      "selection_goal": "CINDER GUNNER authored Cinder Foundry ranged enemy: clearly armed humanoid silhouette with native ranged attack, explicit rifle geometry, material integrity, and phone-scale distinction from generic RANGED, FORGE HOUND and SLAG GUARD.",
      "role_gate": {
        "requires_native_ranged_attack": true,
        "requires_visible_weapon_or_projectile_source": true,
        "requires_explicit_weapon_geometry": true,
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "metrics": {
          "min_median_bbox_area_px2": 1850,
          "min_median_bbox_width_px": 32
        }
      },
      "verdict": {
        "status": "accepted",
        "validation_run_id": 34676331151,
        "validation_artifact_id": 10292517429,
        "frame_count": 232,
        "master_min_margin_px": 40,
        "median_bbox_width_px": 34.5,
        "median_bbox_height_px": 62,
        "median_bbox_area_px2": 2079,
        "phone_qa_pass": true,
        "explicit_weapon_geometry_pass": true,
        "android_runtime_run_id": 34676647378,
        "android_visual_artifact_id": 10292557790,
        "android_visual_qa_pass": true,
        "reason": "Accepted after immutable source validation, exact 232-frame production rendering, enforced phone-scale silhouette metrics, explicit rifle visibility/native Shoot_OneHanded semantics, additive production staging, and semantic review of final Android gameplay/crowd/attack captures."
      }
    }
  }
}
```

## File: actor-candidates.d/elite.json
```json
{
  "schema_version": 1,
  "candidates": {
    "elite-ultimate-orc": {
      "actor": "elite",
      "role": "elite",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "repository": "yerdaulet-damir/liminal",
        "commit": "ff9ee5b57e2531b4f8841d6e7564283f943f6d34",
        "path": "client/public/models/monster_Orc.gltf",
        "git_blob_sha": "750b2401542551ef4fc01e7b5d85b96195119622",
        "size_bytes": 1280189,
        "sha256": "e61a37f8d9b2eee28928bdfad2c55e0798cc0a212e925cc5dcc66b243526c1a3"
      },
      "actions": {
        "idle": ["Idle"],
        "run": ["Walk", "Run"],
        "attack": ["Weapon", "Punch", "Attack"],
        "hit": ["HitReact", "RecieveHit", "ReceiveHit", "Hit"],
        "death": ["Death"]
      },
      "render": {
        "target_height": 1.45,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "selection_goal": "ELITE production candidate: visually premium, threatening and immediately distinct from Shambler, Runner, Brute and RANGED at 96px phone scale, with clean packed material, coherent native combat motions and no clipping.",
      "role_gate": {
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "requires_distinct_from_accepted_actors": true,
        "prefer_native_weapon_attack": true
      },
      "verdict": {
        "status": "accepted",
        "validation_run_id": 34238028804,
        "validation_artifact_id": 10062105502,
        "frame_count": 232,
        "master_min_margin_px": 28,
        "median_bbox_width_px": 43.0,
        "median_bbox_height_px": 49.0,
        "median_bbox_area_px2": 1982.0,
        "attack_action": "Weapon_CharacterArmature",
        "phone_contact_sheet_pass": true,
        "android_runtime_run_id": 34244152937,
        "android_visual_artifact_id": 10063433773,
        "android_visual_qa_pass": true,
        "reason": "Accepted after exact 232-frame validation, clean packed-material contact-sheet inspection, additive production staging, and real Android crowded visual QA confirming a distinct green/blue-crested armed Orc silhouette with no clipping or corruption."
      }
    }
  }
}
```

## File: actor-candidates.d/forge_hound.json
```json
{
  "schema_version": 1,
  "candidates": {
    "forge-hound-ultimate-dog": {
      "actor": "forge_hound",
      "role": "biome-forge-hound",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "repository": "mlflabs/brain",
        "commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e",
        "path": "assets/Ultimate Monsters/Blob/glTF/Dog.gltf",
        "git_blob_sha": "1ed34fa3b17358a766707bc59d3798d3f1304091",
        "size_bytes": 142124,
        "sha256": "21ed475bbf4d5fb7016085290f79663ad7107aedd2a5856318f2716c7ecc6402"
      },
      "actions": {
        "idle": [
          "Idle"
        ],
        "run": [
          "Run",
          "Walk"
        ],
        "attack": [
          "Bite_Front"
        ],
        "hit": [
          "HitRecieve",
          "HitReact"
        ],
        "death": [
          "Death"
        ]
      },
      "render": {
        "target_height": 1.35,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "enemy/biome/forge_hound"
      },
      "selection_goal": "FORGE HOUND authored biome enemy: aggressive non-humanoid hound silhouette with native bite combat animation, strong directional readability and clear distinction from humanoid enemies, REGNERATOR and PHANTOM at phone scale.",
      "role_gate": {
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "requires_non_humanoid_identity": true,
        "requires_native_bite_attack": true,
        "min_median_bbox_area_px2": 2200,
        "min_median_bbox_width_px": 38
      },
      "verdict": {
        "status": "qualified",
        "validation_run_id": 34656325603,
        "validation_artifact_id": 10285836366
      }
    }
  }
}
```

## File: actor-candidates.d/harvester.json
```json
{
  "schema_version": 1,
  "candidates": {
    "harvester-viking": {
      "actor": "harvester",
      "role": "boss-harvester",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Viking_Male.blend",
        "git_blob_sha": "79620718ba895acec12685980391d38c7d2a0e2c",
        "size_bytes": 2133356
      },
      "actions": {
        "idle": [
          "Idle"
        ],
        "run": [
          "Walk",
          "Run"
        ],
        "attack": [
          "Weapon",
          "Attack",
          "Punch"
        ],
        "hit": [
          "RecieveHit",
          "ReceiveHit",
          "HitReact",
          "Hit"
        ],
        "death": [
          "Defeat",
          "Death"
        ]
      },
      "render": {
        "target_height": 1.95,
        "ortho_scale": 6.75,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "boss/harvester"
      },
      "selection_goal": "HARVESTER authored boss: heavy brutal melee silhouette distinct from REVENANT and WARDEN, with readable phone-scale mass and native combat animation.",
      "role_gate": {
        "requires_native_weapon_attack": false,
        "requires_phone_scale_distinction": true,
        "min_median_bbox_area_px2": 2000,
        "min_median_bbox_width_px": 33
      },
      "verdict": {
        "status": "accepted",
        "reason": "Exact 232-frame contract, production phone-scale gates and real Android stage-6 gameplay/crowd/attack QA passed. Viking reads as a large horned heavy melee boss and remains distinct/readable in crowd.",
        "validation_run_id": 34622861724,
        "validation_artifact_id": 10273348562,
        "android_runtime_run_id": 34624187689,
        "android_visual_artifact_id": 10274036186,
        "android_visual_qa_pass": true
      },
      "retry_note": "At ortho 6.75, only late east/west Death frames clip horizontally; 226/232 frames are within margin. Prefer the alternative native Defeat animation before changing phone-scale framing or lowering role gates."
    }
  }
}
```

## File: actor-candidates.d/null_archon.json
```json
{
  "schema_version": 1,
  "candidates": {
    "null-archon-elf": {
      "actor": "null_archon",
      "role": "boss-null-archon",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Elf.blend",
        "git_blob_sha": "b8c40b71164829e7ffb60cc77dac1e730434f57e",
        "size_bytes": 1870736
      },
      "actions": {
        "idle": [
          "Idle"
        ],
        "run": [
          "Walk",
          "Run"
        ],
        "attack": [
          "Weapon",
          "Attack",
          "Punch"
        ],
        "hit": [
          "RecieveHit",
          "ReceiveHit",
          "HitReact",
          "Hit"
        ],
        "death": [
          "Defeat",
          "Death"
        ]
      },
      "render": {
        "target_height": 2,
        "ortho_scale": 8.2,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "boss/null_archon"
      },
      "selection_goal": "NULL ARCHON authored final boss: tall uncanny arcane humanoid silhouette, clearly distinct from WARDEN Witch, HARVESTER Viking and REVENANT Knight.",
      "role_gate": {
        "requires_native_weapon_attack": false,
        "requires_phone_scale_distinction": true,
        "min_median_bbox_area_px2": 2100,
        "min_median_bbox_width_px": 34
      },
      "verdict": {
        "status": "rejected",
        "reason": "Android semantic visual QA #1689 passed technically, but the rendered NULL ARCHON is a giant green wizard/elf with a pointed hat and reads too similarly to the accepted WARDEN arcane silhouette. Final-boss distinctness gate failed.",
        "validation_run_id": 34632184108,
        "validation_artifact_id": 10277155584,
        "android_runtime_run_id": 34639734882,
        "android_visual_artifact_id": 10279747820,
        "android_visual_qa_pass": false
      }
    },
    "null-archon-dragon-evolved": {
      "actor": "null_archon",
      "role": "boss-null-archon",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "repository": "mlflabs/brain",
        "commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e",
        "path": "assets/Ultimate Monsters/Flying/glTF/Dragon_Evolved.gltf",
        "git_blob_sha": "f70ceaed1f52d26e8b2cd24f8a9bd43cd380ed6a",
        "size_bytes": 991335,
        "sha256": "39ba6ea24b5f27acf68bbf4c19fe80ba070dbec167ff14bbe933453303426f5c"
      },
      "actions": {
        "idle": [
          "Flying_Idle",
          "Idle"
        ],
        "run": [
          "Fast_Flying",
          "Flying_Idle"
        ],
        "attack": [
          "Headbutt",
          "Punch",
          "Attack"
        ],
        "hit": [
          "HitReact",
          "HitRecieve",
          "HitReceive"
        ],
        "death": [
          "Death"
        ]
      },
      "render": {
        "target_height": 2.15,
        "ortho_scale": 10.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "boss/null_archon"
      },
      "selection_goal": "NULL ARCHON final-boss retry: evolved dragon selected after Elf failed semantic distinctness. Must read as an unmistakable apex non-humanoid final boss, remain readable at phone scale, and be clearly distinct from WARDEN, HARVESTER and REVENANT.",
      "role_gate": {
        "requires_phone_scale_distinction": true,
        "metrics": {
          "min_median_bbox_area_px2": 2800,
          "min_median_bbox_width_px": 48,
          "min_median_bbox_height_px": 48
        },
        "min_median_bbox_area_px2": 2800,
        "min_median_bbox_width_px": 48
      },
      "verdict": {
        "status": "qualified",
        "reason": "Validation #214 passed the exact 232-frame contract, immutable source fingerprint, 120px minimum master margin, and all 3 strict phone-scale role constraints. Contact-sheet inspection confirms a clean evolved-dragon silhouette clearly distinct from WARDEN, HARVESTER, REVENANT and ALPHA. Proceed to production staging and real Android semantic QA.",
        "validation_run_id": 34647430253,
        "validation_artifact_id": 10283435668,
        "source_sha256": "39ba6ea24b5f27acf68bbf4c19fe80ba070dbec167ff14bbe933453303426f5c",
        "frame_count": 232,
        "minimum_master_margin_px": 120,
        "median_bbox_width_px": 61,
        "median_bbox_height_px": 49,
        "median_bbox_area_px2": 2809
      }
    }
  }
}
```

## File: actor-candidates.d/null_ward.json
```json
{
  "schema_version": 1,
  "candidates": {
    "null-ward-ghost-skull": {
      "actor": "null_ward",
      "role": "biome-null-ward",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "repository": "mlflabs/brain",
        "commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e",
        "path": "assets/Ultimate Monsters/Flying/glTF/Ghost_Skull.gltf",
        "git_blob_sha": "8b6af9bca7bc05cc8056695b7ba75a1bf317148d"
      },
      "actions": {
        "idle": [
          "Flying_Idle"
        ],
        "run": [
          "Fast_Flying"
        ],
        "attack": [
          "Headbutt",
          "Punch"
        ],
        "hit": [
          "HitReact"
        ],
        "death": [
          "Death"
        ]
      },
      "render": {
        "target_height": 1.65,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "enemy/biome/null_ward"
      },
      "selection_goal": "NULL WARD Null Sector regenerator identity: spectral skull/ward silhouette with native flying locomotion and attack, strong defensive supernatural read, material integrity, and phone-scale distinction from PHASE STALKER and STATIC SEER.",
      "role_gate": {
        "requires_native_flying_locomotion": true,
        "requires_native_melee_attack": true,
        "requires_non_humanoid_identity": true,
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "metrics": {
          "min_median_bbox_area_px2": 2200,
          "min_median_bbox_width_px": 38
        }
      },
      "verdict": {
        "status": "qualified",
        "validation_run_id": 34696521969,
        "validation_artifact_id": 10299241585
      }
    }
  }
}
```

## File: actor-candidates.d/nyx.json
```json
{
  "schema_version": 1,
  "candidates": {
    "nyx-blue-soldier-female-rifle": {
      "actor": "nyx",
      "role": "survivor-sharpshooter",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/BlueSoldier_Female.blend",
        "git_blob_sha": "ceaceb30aa5389cea266acf3becdae863c5820d4",
        "size_bytes": 2197748,
        "sha256": "9f79873e2e3452f4a3ac64ff7ba3388c01d6e62633f3477ab8a27d844c3adfb2"
      },
      "actions": {
        "idle": ["Idle"],
        "run": ["Run", "Walk"],
        "attack": ["Shoot_OneHanded", "Shoot", "Weapon", "Attack"],
        "hit": ["RecieveHit", "ReceiveHit", "HitReact", "Hit"],
        "death": ["Death"]
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [0.0, -1.0, -0.08],
        "grip_offset": [0.0, -0.01, 0.01]
      },
      "render": {
        "target_height": 1.45,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "survivor/nyx"
      },
      "selection_goal": "NYX authored survivor: slim readable female sharpshooter silhouette, explicit rifle geometry, native ranged attack and clear phone-scale distinction from Rex.",
      "role_gate": {
        "requires_native_ranged_attack": true,
        "requires_visible_weapon_or_projectile_source": true,
        "requires_explicit_weapon_geometry": true,
        "requires_phone_scale_distinction": true
      },
      "verdict": {
        "status": "accepted",
        "validation_run_id": 34395791964,
        "validation_artifact_id": 10121633792,
        "frame_count": 232,
        "master_min_margin_px": 40,
        "median_bbox_width_px": 37.0,
        "median_bbox_height_px": 60.5,
        "median_bbox_area_px2": 2079.0,
        "phone_qa_pass": true,
        "explicit_weapon_geometry_pass": true,
        "android_runtime_run_id": 34395791923,
        "android_visual_artifact_id": 10121562557,
        "android_visual_qa_pass": true,
        "reason": "Exact 232/232 validation passed with native Shoot_OneHanded and explicit compact-rifle geometry. Verify #1487 exercised Survivor.NYX through the real selected-survivor runtime path and passed on Android. Human inspection of the crowded and attack captures confirms NYX is readable at phone scale, materially distinct from Rex, and her authored rifle/attack presentation remains visible under production gameplay composition."
      }
    }
  }
}
```

## File: actor-candidates.d/phantom.json
```json
{
  "schema_version": 1,
  "candidates": {
    "phantom-ultimate-ghost": {
      "actor": "phantom",
      "role": "phantom",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "repository": "ilrein/warptracker",
        "commit": "71bbfbdfacd118196994b26da68eec1876d55c6b",
        "path": "public/models/ghost.glb",
        "git_blob_sha": "64a689730f9520be17e2628fa861ed9b1930054c",
        "size_bytes": 288500,
        "sha256": "430fb42e7b1c0af455ff89cd5bfdf490d133658e844935d5ffe44e9d9912ffd4"
      },
      "actions": {
        "idle": ["Flying_Idle", "Idle", "Flying"],
        "run": ["Fast_Flying", "Flying", "Run", "Move"],
        "attack": ["Headbutt", "Attack", "Dash"],
        "hit": ["HitReact", "Hit", "Hurt", "Damage"],
        "death": ["Death", "Die"]
      },
      "render": {
        "target_height": 1.50,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "enemy/phantom"
      },
      "selection_goal": "PHANTOM production candidate: unmistakably ethereal floating silhouette with strong directional readability, visually distinct from all eight accepted actors at 96px and in crowded Android gameplay.",
      "role_gate": {
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "requires_ethereal_identity": true,
        "requires_distinct_from_regenerator": true,
        "requires_distinct_from_humanoids": true
      },
      "verdict": {
        "status": "accepted",
        "validation_run_id": 34289158328,
        "validation_artifact_id": 10080877183,
        "production_staging_run_id": 34293059458,
        "production_staging_artifact_id": 10082084606,
        "android_runtime_run_id": 34293226994,
        "android_visual_artifact_id": 10082228087,
        "reason": "Accepted after immutable source fingerprinting, exact 232/232 frame QA, 76px minimum master margin, 96px phone normalization, additive production staging and real Android crowded visual acceptance. Verify #1417 passed core, Android build and Android runtime; human inspection of artifact 10082228087 confirms both PHANTOM actors remain ethereal, distinct, materially intact and unclipped at phone scale."
      }
    }
  }
}
```

## File: actor-candidates.d/phase_stalker.json
```json
{
  "schema_version": 1,
  "candidates": {
    "phase-stalker-ghost": {
      "actor": "phase_stalker",
      "role": "biome-phase-stalker",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "repository": "mlflabs/brain",
        "commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e",
        "path": "assets/Ultimate Monsters/Flying/glTF/Ghost.gltf",
        "git_blob_sha": "c93cf0084ef108837fc94dd4d7fca5afbf9af3ee"
      },
      "actions": {
        "idle": [
          "Flying_Idle"
        ],
        "run": [
          "Fast_Flying"
        ],
        "attack": [
          "Headbutt",
          "Punch"
        ],
        "hit": [
          "HitReact"
        ],
        "death": [
          "Death"
        ]
      },
      "render": {
        "target_height": 1.55,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "enemy/biome/phase_stalker"
      },
      "selection_goal": "PHASE STALKER Null Sector phantom enemy: non-humanoid floating ghost silhouette, native flying locomotion and native melee attack, material integrity, and clear phone-scale distinction from Cinder Foundry actors.",
      "role_gate": {
        "requires_native_flying_locomotion": true,
        "requires_native_melee_attack": true,
        "requires_non_humanoid_identity": true,
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "metrics": {
          "min_median_bbox_area_px2": 2200,
          "min_median_bbox_width_px": 38
        }
      },
      "verdict": {
        "status": "qualified",
        "validation_run_id": 34686937622,
        "validation_artifact_id": 10295983021
      }
    }
  }
}
```

## File: actor-candidates.d/ranged.json
```json
{
  "schema_version": 1,
  "candidates": {
    "ranged-blue-soldier-male": {
      "actor": "ranged",
      "role": "ranged",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/BlueSoldier_Male.blend",
        "git_blob_sha": "5a9ff78571c9c2bfc5492912ca3dbc6e9ed573d8",
        "size_bytes": 1869476,
        "sha256": "ee9c0780a297c333aab1c59694afc4ad1d13fb0374327287c1ec8425be18337f"
      },
      "actions": {
        "idle": ["Idle"],
        "run": ["Walk", "Run"],
        "attack": ["Shoot_OneHanded", "Shoot", "Weapon", "Attack"],
        "hit": ["RecieveHit", "ReceiveHit", "HitReact", "Hit"],
        "death": ["Death"]
      },
      "render": {"target_height": 1.45, "ortho_scale": 7.5, "horizontal_anchor": "union-center"},
      "selection_goal": "RANGED production candidate: readable armed humanoid silhouette at phone scale with an explicit weapon or projectile source.",
      "role_gate": {"requires_native_ranged_attack": true, "requires_visible_weapon_or_projectile_source": true, "requires_phone_scale_distinction": true},
      "verdict": {
        "status": "rejected",
        "validation_run_id": 34218497104,
        "validation_artifact_id": 10052907674,
        "frame_count": 232,
        "master_min_margin_px": 37,
        "median_bbox_width_px": 31,
        "median_bbox_height_px": 62,
        "median_bbox_area_px2": 1850,
        "phone_qa_pass": true,
        "reason": "Technical 232/232 QA and native Shoot_OneHanded pass, but human inspection proves the source contains no weapon mesh; the forward extension is only the shooting arm."
      }
    },
    "ranged-blue-soldier-rifle": {
      "actor": "ranged",
      "role": "ranged",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/BlueSoldier_Male.blend",
        "git_blob_sha": "5a9ff78571c9c2bfc5492912ca3dbc6e9ed573d8",
        "size_bytes": 1869476,
        "sha256": "ee9c0780a297c333aab1c59694afc4ad1d13fb0374327287c1ec8425be18337f"
      },
      "actions": {
        "idle": ["Idle"],
        "run": ["Walk", "Run"],
        "attack": ["Shoot_OneHanded", "Shoot", "Weapon", "Attack"],
        "hit": ["RecieveHit", "ReceiveHit", "HitReact", "Hit"],
        "death": ["Death"]
      },
      "weapon": {"style": "compact-rifle", "bone": "Fist.R", "forward": [0.0, -1.0, -0.08], "grip_offset": [0.0, -0.01, 0.01]},
      "render": {"target_height": 1.45, "ortho_scale": 7.5, "horizontal_anchor": "union-center"},
      "selection_goal": "RANGED production candidate using the same immutable CC0 character plus a deterministic separate rifle prop attached by configuration, not actor-specific runtime code.",
      "role_gate": {"requires_native_ranged_attack": true, "requires_visible_weapon_or_projectile_source": true, "requires_phone_scale_distinction": true, "requires_explicit_weapon_geometry": true},
      "verdict": {
        "status": "accepted",
        "validation_run_id": 34231682966,
        "validation_artifact_id": 10058335566,
        "frame_count": 232,
        "master_min_margin_px": 37,
        "median_bbox_width_px": 32,
        "median_bbox_height_px": 62,
        "median_bbox_area_px2": 1888.5,
        "phone_qa_pass": true,
        "explicit_weapon_geometry_pass": true,
        "android_runtime_run_id": 34236059385,
        "android_visual_artifact_id": 10060021215,
        "android_visual_qa_pass": true,
        "reason": "Accepted after exact 232/232 validation, explicit rifle visibility across all directions/motions, additive production staging, and real crowded Android QA confirming the armed RANGED actor is distinct, grounded, textured and unclipped at phone scale."
      }
    }
  }
}
```

## File: actor-candidates.d/regenerator.json
```json
{
  "schema_version": 1,
  "candidates": {
    "regenerator-green-blob": {
      "actor": "regenerator",
      "role": "regenerator",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "repository": "Hakhyun-Kim/constellation-defense",
        "commit": "4431655c1a29638495c6ab3d4543d37c8d6deed6",
        "path": "assets/models/quaternius-green-blob.glb",
        "git_blob_sha": "9e21e57419cdacf349c142eba9e2ee106c8c36bc",
        "size_bytes": 87892,
        "sha256": "fc68353469e3fe36586b7051a39bfdd0a466e48187f57fb39abf3932ca72447d"
      },
      "actions": {
        "idle": ["Idle", "Stand", "Rest"],
        "run": ["Walk", "Run", "Move", "Jump", "Bounce"],
        "attack": ["Bite_Front", "Bite", "Attack", "Punch", "Hit", "JumpAttack"],
        "hit": ["HitRecieve", "HitReceive", "HitReact", "RecieveHit", "ReceiveHit", "Hurt", "Damage"],
        "death": ["Death", "Die"]
      },
      "render": {
        "target_height": 1.30,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "enemy/regenerator"
      },
      "selection_goal": "REGENERATOR production candidate: organic non-humanoid biomass silhouette with clean green material identity, clearly distinct from the humanoid Shambler at 96px and readable in all eight directions.",
      "role_gate": {
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "requires_organic_regeneration_identity": true,
        "requires_distinct_from_shambler": true
      },
      "verdict": {
        "status": "accepted",
        "reason": "Actor Candidate Validation #81 passed the exact 232-frame contract and phone-scale visual QA. Actor Production Staging #47 republished the candidate additively on top of the latest main atlas. Verify #1380 then passed core, Android build and real Android runtime instrumentation; artifact 10076741425 was inspected and confirms both REGENERATOR blobs are distinct from humanoid actors, grounded, materially intact and unclipped in the crowded production scene.",
        "validation_run_id": 34266381609,
        "validation_artifact_id": 10072289773,
        "validation": {
          "run_id": 34266381609,
          "artifact_id": 10072289773,
          "frame_count": 232,
          "minimum_master_margin_px": 127,
          "median_bbox_width_px": 65.0,
          "median_bbox_height_px": 60.0,
          "median_bbox_area_px2": 3960.0,
          "max_bbox_width_px": 68,
          "max_bbox_height_px": 66,
          "normalization_scale": 0.40594059405940597
        },
        "production_staging": {
          "run_id": 34278282930,
          "artifact_id": 10076587123
        },
        "android_acceptance": {
          "run_id": 34278412075,
          "run_number": 1380,
          "artifact_id": 10076741425,
          "visual_qa_pass": true
        }
      }
    }
  }
}
```

## File: actor-candidates.d/revenant.json
```json
{
  "schema_version": 1,
  "candidates": {
    "revenant-golden-knight": {
      "actor": "revenant",
      "role": "boss-revenant",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Knight_Golden_Male.blend",
        "git_blob_sha": "9cc053899acbfca453669435ead43a10f633f64b",
        "size_bytes": 1888072,
        "sha256": "f81a02ff576815cffc8366c89b57d30888f45f9df3e6865245c81837d52172c9"
      },
      "actions": {
        "idle": [
          "Idle"
        ],
        "run": [
          "Walk",
          "Run"
        ],
        "attack": [
          "Weapon",
          "Attack",
          "Punch"
        ],
        "hit": [
          "RecieveHit",
          "ReceiveHit",
          "HitReact",
          "Hit"
        ],
        "death": [
          "Death"
        ]
      },
      "render": {
        "target_height": 1.85,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "boss/revenant"
      },
      "selection_goal": "REVENANT authored boss: armored resurrected knight identity, imposing readable silhouette, native weapon attack and strong phone-scale distinction from Alpha and standard enemies.",
      "role_gate": {
        "requires_native_weapon_attack": true,
        "requires_phone_scale_distinction": true,
        "min_median_bbox_area_px2": 2000,
        "min_median_bbox_width_px": 34
      },
      "verdict": {
        "status": "accepted",
        "reason": "Exact 232-frame contract, immutable source fingerprint, native weapon attack, phone-scale boss silhouette and real Android gameplay/crowd/attack visual QA passed. REVENANT is production accepted.",
        "validation_run_id": 34570992444,
        "validation_artifact_id": 10187885488,
        "android_runtime_run_id": 34583221325,
        "android_visual_artifact_id": 10192621684,
        "android_visual_qa_pass": true
      },
      "staging_ack": "production-atlas-staged-71de3f7f",
      "post_staging_verification": "f8ac735-stable"
    }
  }
}
```

## File: actor-candidates.d/shielded.json
```json
{
  "schema_version": 1,
  "candidates": {
    "shielded-blue-soldier-kaykit-shield": {
      "actor": "shielded",
      "role": "shielded",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/BlueSoldier_Male.blend",
        "git_blob_sha": "5a9ff78571c9c2bfc5492912ca3dbc6e9ed573d8",
        "size_bytes": 1869476,
        "sha256": "ee9c0780a297c333aab1c59694afc4ad1d13fb0374327287c1ec8425be18337f"
      },
      "defensive_prop": {
        "provider": "KayKit Game Assets",
        "pack": "Character Pack: Adventurers",
        "license": "CC0 1.0",
        "repository": "KayKit-Game-Assets/KayKit-Character-Pack-Adventures-1.0",
        "commit": "672074b73ba276876a19e8816ecdc5241817ab47",
        "path": "addons/kaykit_character_pack_adventures/Assets/fbx/shield_badge.fbx",
        "git_blob_sha": "b334acbdead8d7ea6655263080dca3ab3e0be517",
        "size_bytes": 27596,
        "sha256": "8ae485d8451075d19ce504489328e7f3e9dea5cddbddee916508321853795604",
        "bone": "Fist.L",
        "role": "shield"
      },
      "actions": {
        "idle": ["Idle"],
        "run": ["Walk", "Run"],
        "attack": ["Punch", "SwordSlash", "Attack"],
        "hit": ["RecieveHit", "ReceiveHit", "HitReact", "Hit"],
        "death": ["Death"]
      },
      "render": {
        "target_height": 1.45,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "enemy/shielded",
        "role_gate": {
          "min_median_bbox_width_px": 34,
          "min_median_bbox_area_px2": 1950
        }
      },
      "selection_goal": "SHIELDED production candidate: heavy defensive silhouette with an explicit left-arm shield that remains readable in all eight directions at 96px and matches the runtime shieldHp archetype.",
      "role_gate": {
        "requires_explicit_shield_geometry": true,
        "requires_shield_visible_all_directions": true,
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true
      },
      "verdict": {
        "status": "accepted",
        "validation_run_id": 34251326917,
        "validation_artifact_id": 10066284057,
        "android_runtime_run_id": 34261934514,
        "android_visual_artifact_id": 10070394448,
        "reason": "Accepted after exact 232-frame validation, direct contact-sheet inspection, generic production staging, and real Android crowded visual acceptance. Shield geometry remains explicit and readable at phone scale; both SHIELDED actors are grounded, materially intact and unclipped in the production crowd screenshot."
      }
    }
  }
}
```

## File: actor-candidates.d/slag_guard.json
```json
{
  "schema_version": 1,
  "candidates": {
    "slag-guard-golden-knight-shield": {
      "actor": "slag_guard",
      "role": "biome-slag-guard",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Knight_Golden_Male.blend",
        "git_blob_sha": "9cc053899acbfca453669435ead43a10f633f64b",
        "size_bytes": 0
      },
      "actions": {
        "idle": [
          "Idle"
        ],
        "run": [
          "Walk",
          "Run"
        ],
        "attack": [
          "SwordSlash",
          "Punch"
        ],
        "hit": [
          "RecieveHit",
          "ReceiveHit",
          "HitReact",
          "Hit"
        ],
        "death": [
          "Death"
        ]
      },
      "defensive_prop": {
        "provider": "KayKit",
        "pack": "Skeletons 1.1 FREE",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/KayKit_Skeletons_1.1_FREE/assets/obj/Skeleton_Shield_Large_A.obj",
        "git_blob_sha": "d4a7c7db6e40b7373c106bfcaa44cdb2eb601304",
        "size_bytes": 59651,
        "role": "shield",
        "bone": "Fist.L",
        "forward": [
          0,
          -1,
          0
        ],
        "grip_offset": [
          0,
          -0.02,
          0
        ],
        "rotation": [
          0,
          0,
          0
        ],
        "scale": 1.2,
        "sha256": "391479a7ddea62901af6a67cdefe9205a68f621d82c7eda79c1765afd9bff371"
      },
      "render": {
        "target_height": 2.15,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "enemy/biome/slag_guard"
      },
      "selection_goal": "SLAG GUARD authored Cinder Foundry shield enemy: bulky armored humanoid silhouette with native melee attack, explicit large shield geometry, material integrity, and strong phone-scale distinction from generic SHIELDED, CINDER GUNNER and FORGE HOUND. Preserve heavy-role readability without relaxing silhouette gates.",
      "role_gate": {
        "requires_native_melee_attack": true,
        "requires_explicit_defensive_prop": true,
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "metrics": {
          "min_median_bbox_area_px2": 2200,
          "min_median_bbox_width_px": 38
        }
      },
      "verdict": {
        "status": "qualified",
        "validation_run_id": 34684950106,
        "validation_artifact_id": 10295445972
      }
    }
  }
}
```

## File: actor-candidates.d/static_seer.json
```json
{
  "schema_version": 1,
  "candidates": {
    "static-seer-hywirl": {
      "actor": "static_seer",
      "role": "biome-static-seer",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "repository": "mlflabs/brain",
        "commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e",
        "path": "assets/Ultimate Monsters/Flying/glTF/Hywirl.gltf",
        "git_blob_sha": "3de288e2b4b208f9fe019b38ef502f104f88877c"
      },
      "actions": {
        "idle": [
          "Flying_Idle"
        ],
        "run": [
          "Fast_Flying"
        ],
        "attack": [
          "Punch",
          "Headbutt"
        ],
        "hit": [
          "HitReact"
        ],
        "death": [
          "Death"
        ]
      },
      "render": {
        "target_height": 1.55,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "enemy/biome/static_seer"
      },
      "selection_goal": "STATIC SEER Null Sector ranged identity: alien floating caster silhouette, native flying locomotion and native attack motion, material integrity, and strong phone-scale distinction from PHASE STALKER and humanoid ranged enemies.",
      "role_gate": {
        "requires_native_flying_locomotion": true,
        "requires_native_melee_attack": true,
        "requires_non_humanoid_identity": true,
        "requires_phone_scale_distinction": true,
        "requires_material_integrity": true,
        "metrics": {
          "min_median_bbox_area_px2": 2200,
          "min_median_bbox_width_px": 38
        }
      },
      "verdict": {
        "status": "qualified",
        "validation_run_id": 34689516608,
        "validation_artifact_id": 10296907077
      }
    }
  }
}
```

## File: actor-candidates.d/volt.json
```json
{
  "schema_version": 1,
  "candidates": {
    "volt-casual3-male-rifle": {
      "actor": "volt",
      "role": "survivor-technician",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Casual3_Male.blend",
        "git_blob_sha": "b3fd1fad0ff11c7069825fb12467f2aaac7c8cda",
        "sha256": "4a8e3af92fc951c6b412b4ae6009fa68e53d131edae47e79faf9822f1a5468a4",
        "size_bytes": 2029180
      },
      "actions": {"idle":["Idle"],"run":["Run","Walk"],"attack":["Shoot_OneHanded","Shoot","Weapon","Attack"],"hit":["RecieveHit","ReceiveHit","HitReact","Hit"],"death":["Death"]},
      "weapon": {"style":"compact-rifle","bone":"Fist.R","forward":[0.0,-1.0,-0.08],"grip_offset":[0.0,-0.01,0.01]},
      "render": {"target_height":1.48,"ortho_scale":7.5,"horizontal_anchor":"union-center"},
      "production": {"atlas_root":"survivor/volt"},
      "selection_goal": "VOLT authored survivor: readable technician identity, explicit rifle geometry, native ranged attack, compact agile silhouette and clear phone-scale distinction from Rex, Nyx and Bastion.",
      "role_gate": {"requires_native_ranged_attack":true,"requires_visible_weapon_or_projectile_source":true,"requires_explicit_weapon_geometry":true,"requires_phone_scale_distinction":true},
      "verdict": {
        "status": "rejected",
        "validation_run_id": 34481550846,
        "validation_artifact_id": 10153975601,
        "reason": "Technically clean 232/232 with exact native actions and visible rifle, but the plain casual silhouette does not communicate VOLT's Technician identity strongly enough at phone scale."
      }
    },
    "volt-worker-male-rifle": {
      "actor": "volt",
      "role": "survivor-technician",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Worker_Male.blend",
        "git_blob_sha": "f7212f9f70cf7a9b99d766648757863435e4b409",
        "sha256": "b4b4f7a494d04b2a12a560536619ccb20c66876515fa487e6b95a10dc76d484e",
        "size_bytes": 1862096
      },
      "actions": {"idle":["Idle"],"run":["Run","Walk"],"attack":["Shoot_OneHanded","Shoot","Weapon","Attack"],"hit":["RecieveHit","ReceiveHit","HitReact","Hit"],"death":["Death"]},
      "weapon": {"style":"compact-rifle","bone":"Fist.R","forward":[0.0,-1.0,-0.08],"grip_offset":[0.0,-0.01,0.01]},
      "render": {"target_height":1.48,"ortho_scale":7.5,"horizontal_anchor":"union-center"},
      "production": {"atlas_root":"survivor/volt"},
      "selection_goal": "VOLT authored survivor: worker/technician visual identity, explicit rifle geometry, native ranged attack and clear phone-scale distinction from Rex, Nyx and Bastion.",
      "role_gate": {"requires_native_ranged_attack":true,"requires_visible_weapon_or_projectile_source":true,"requires_explicit_weapon_geometry":true,"requires_phone_scale_distinction":true},
      "verdict": {
        "status":"accepted",
        "reason":"Validated 232-frame authored survivor with immutable source fingerprint, readable worker/technician silhouette, visible rifle, and successful real Android phone-scale crowded visual QA.",
        "validation_run_id":34482346899,
        "validation_artifact_id":10154378635,
        "android_runtime_run_id":34487550020,
        "android_visual_artifact_id":10156440934,
        "android_visual_qa_pass":true
      }
    }
  }
}
```

## File: actor-candidates.d/warden.json
```json
{
  "schema_version": 1,
  "candidates": {
    "warden-wizard": {
      "actor": "warden",
      "role": "boss-warden",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Wizard.blend",
        "git_blob_sha": "e5f284892fbd25efd7f88e9c1671de03aef96d14",
        "size_bytes": 2152312
      },
      "actions": {
        "idle": [
          "Idle"
        ],
        "run": [
          "Walk",
          "Run"
        ],
        "attack": [
          "Weapon",
          "Attack",
          "Spell",
          "Punch"
        ],
        "hit": [
          "RecieveHit",
          "ReceiveHit",
          "HitReact",
          "Hit"
        ],
        "death": [
          "Death"
        ]
      },
      "render": {
        "target_height": 1.9,
        "ortho_scale": 8.2,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "boss/warden"
      },
      "selection_goal": "WARDEN authored boss: arcane controller silhouette clearly distinct from REVENANT/Alpha, readable at phone scale, with native attack animation.",
      "role_gate": {
        "requires_native_weapon_attack": false,
        "requires_phone_scale_distinction": true,
        "min_median_bbox_area_px2": 1900,
        "min_median_bbox_width_px": 32
      },
      "retry_note": "Validation #178 rendered all 232 frames but failed the 4px master margin gate due to framing. Increase ortho scale from 7.5 to 8.2; source/actions remain valid.",
      "verdict": {
        "status": "rejected",
        "reason": "Automated 232-frame and phone-scale metric gates passed, but Android visual QA #1628 fails semantic identity: the rendered boss is a large white cartoon creature rather than a readable arcane WARDEN/wizard. Rejecting candidate rather than accepting a misleading asset.",
        "validation_run_id": 34586539091,
        "validation_artifact_id": 10193963831,
        "android_runtime_run_id": 34587216402,
        "android_visual_artifact_id": 10194224991,
        "android_visual_qa_pass": false
      }
    },
    "warden-witch": {
      "actor": "warden",
      "role": "boss-warden",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Witch.blend",
        "git_blob_sha": "ccebbdc5f3b3cbd7b44468af9aa965075978a8ee",
        "size_bytes": 2188648,
        "sha256": "92042ee8da70dab6331218290bcfbc616165405757914596e82c6a9c36bce837"
      },
      "actions": {
        "idle": [
          "Idle"
        ],
        "run": [
          "Walk",
          "Run"
        ],
        "attack": [
          "Weapon",
          "Attack",
          "Spell",
          "Punch"
        ],
        "hit": [
          "RecieveHit",
          "ReceiveHit",
          "HitReact",
          "Hit"
        ],
        "death": [
          "Death"
        ]
      },
      "render": {
        "target_height": 1.9,
        "ortho_scale": 9.2,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "boss/warden"
      },
      "selection_goal": "WARDEN authored boss retry: Witch candidate selected for a stronger unmistakable arcane silhouette after Wizard failed semantic Android visual QA.",
      "role_gate": {
        "requires_native_weapon_attack": false,
        "requires_phone_scale_distinction": true,
        "min_median_bbox_area_px2": 1900,
        "min_median_bbox_width_px": 32
      },
      "retry_note": "Validation #185 rendered all 232 frames and resolved every action, but Witch geometry touched the master frame edge (0px minimum margin). Increase ortho scale 8.2 -> 9.2 before semantic visual QA.",
      "verdict": {
        "status": "accepted",
        "reason": "Exact 232-frame contract, 28px minimum margin, phone-scale metrics and real Android stage-7 gameplay/crowd/attack QA passed. Witch provides a clear arcane WARDEN identity distinct from Alpha and REVENANT.",
        "validation_run_id": 34592585776,
        "validation_artifact_id": 10196349863,
        "android_runtime_run_id": 34609391605,
        "android_visual_artifact_id": 10267628115,
        "android_visual_qa_pass": true
      }
    }
  }
}
```

## File: actor-candidates.d/wraith.json
```json
{
  "schema_version": 1,
  "candidates": {
    "wraith-ninja-male-rifle": {
      "actor": "wraith",
      "role": "survivor-runner",
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "repository": "ariesyous/projectbluebean",
        "commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "path": "assets/Ultimate Animated Character Pack - Nov 2019/Blends/Ninja_Male.blend",
        "git_blob_sha": "1ccda6b3d9e68981bdafad32cdf1457a8b517223",
        "size_bytes": 1872620,
        "sha256": "445a82fca15984090131910e45c6903b01c2e503c76c8dfb58db46927de8ea7e"
      },
      "actions": {
        "idle": [
          "Idle"
        ],
        "run": [
          "Run",
          "Walk"
        ],
        "attack": [
          "Shoot_OneHanded",
          "Shoot",
          "Weapon",
          "Attack"
        ],
        "hit": [
          "RecieveHit",
          "ReceiveHit",
          "HitReact",
          "Hit"
        ],
        "death": [
          "Death"
        ]
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [
          0,
          -1,
          -0.08
        ],
        "grip_offset": [
          0,
          -0.01,
          0.01
        ]
      },
      "render": {
        "target_height": 1.42,
        "ortho_scale": 7.5,
        "horizontal_anchor": "union-center"
      },
      "production": {
        "atlas_root": "survivor/wraith"
      },
      "selection_goal": "WRAITH authored survivor: fast runner/assassin identity, slim masked ninja silhouette, explicit rifle geometry, native ranged attack and strong phone-scale distinction from Rex, Nyx, Bastion and Volt.",
      "role_gate": {
        "requires_native_ranged_attack": true,
        "requires_visible_weapon_or_projectile_source": true,
        "requires_explicit_weapon_geometry": true,
        "requires_phone_scale_distinction": true
      },
      "verdict": {
        "status": "accepted",
        "reason": "Exact 232-frame contract, immutable source fingerprint, phone-scale contact-sheet inspection and real Android crowded gameplay/attack QA passed. WRAITH is production accepted.",
        "validation_run_id": 34500223976,
        "validation_artifact_id": 10161721488,
        "android_runtime_run_id": 34507795312,
        "android_visual_artifact_id": 10164659473,
        "android_visual_qa_pass": true
      }
    }
  }
}
```

## File: actor-candidates.json
```json
{
  "schema_version": 1,
  "candidates": {
    "brute-viking-male": {
      "actor": "brute",
      "source": {"provider":"Quaternius","pack":"Ultimate Animated Character Pack","license":"CC0 1.0","repository":"ariesyous/projectbluebean","commit":"4769997aaea204c66735813e2bf61dc3c94e7631","path":"assets/Ultimate Animated Character Pack - Nov 2019/Blends/Viking_Male.blend","git_blob_sha":"79620718ba895acec12685980391d38c7d2a0e2c","size_bytes":2133356},
      "actions":{"idle":["Idle"],"run":["Walk","Run"],"attack":["Punch","SwordSlash","Attack"],"hit":["RecieveHit","ReceiveHit","HitReact","Hit"],"death":["Death"]},
      "render":{"target_height":1.45,"ortho_scale":7.5,"horizontal_anchor":"union-center"},
      "selection_goal":"Substantially heavier silhouette than Shambler and Runner at 96px phone scale.",
      "verdict":{"status":"rejected","reason":"Median silhouette area 1708 px2 and width 29 px are materially smaller than Shambler."}
    },
    "brute-zombie-male-control": {
      "actor":"brute",
      "source":{"provider":"Quaternius","pack":"Ultimate Animated Character Pack","license":"CC0 1.0","repository":"ariesyous/projectbluebean","commit":"4769997aaea204c66735813e2bf61dc3c94e7631","path":"assets/Ultimate Animated Character Pack - Nov 2019/Blends/Zombie_Male.blend","git_blob_sha":"a53535441c7b83a3d29fed798ce26699529f0bb4","size_bytes":2007788},
      "actions":{"idle":["Idle"],"run":["Walk","Run"],"attack":["Punch","Attack"],"hit":["RecieveHit","ReceiveHit","HitReact","Hit"],"death":["Death"]},
      "render":{"target_height":1.45,"ortho_scale":7.5,"horizontal_anchor":"union-center"},
      "selection_goal":"Thematic zombie control candidate; reject if silhouette is not materially heavier than Shambler and Runner.",
      "verdict":{"status":"rejected","reason":"Median silhouette area 1952.5 px2 is smaller than Shambler 2202 px2."}
    },
    "brute-ultimate-orc": {
      "actor":"brute",
      "source":{"provider":"Quaternius","pack":"Ultimate Monsters","license":"CC0 1.0","official_drive_folder_id":"18m4KpzpEzhC9wl7jzr6dUc0N8Jozr79C","path":"Big/Blends/Orc.blend","url":"https://drive.google.com/uc?id=1c3MUyIuICUIihEoommpccm4e1BaPSxZ9","size_bytes":3046180,"sha256":"88a9ec1b17044b254dd715f7068549d1a1a9413d033e0ae0136f5267c00f12de"},
      "actions":{"idle":["Idle","idle"],"run":["Walk","Run","Walking","Running"],"attack":["Attack","Punch","MeleeAttack","Attack1","Attack_1"],"hit":["TakeDamage","Damage","Hit","HitReact","ReceiveHit","RecieveHit"],"death":["Death","Die","Dying"]},
      "render":{"target_height":1.45,"ortho_scale":7.5,"horizontal_anchor":"union-center"},
      "selection_goal":"Primary BRUTE candidate: visibly broader and heavier than Shambler and Runner while remaining a readable grounded biped.",
      "verdict":{"status":"rejected","validation_run_id":34154620490,"median_bbox_area_px2":2217,"median_bbox_width_px":46,"reason":"Only 0.7% larger median area than Shambler and rendered magenta because external material assets were unresolved."}
    },
    "brute-ultimate-yeti": {
      "actor":"brute",
      "source":{"provider":"Quaternius","pack":"Ultimate Monsters","license":"CC0 1.0","official_drive_folder_id":"18m4KpzpEzhC9wl7jzr6dUc0N8Jozr79C","path":"Big/Blends/Yeti.blend","url":"https://drive.google.com/uc?id=174GVv54Yo0q2dNRYVoJifKyepy8K3gO7","sha256":"c093bb3e81cfdb323465c9c550f1a5cf15d0af8b2bb8072e59cca1b585625f76"},
      "actions":{"idle":["Idle","idle"],"run":["Walk","Run","Walking","Running"],"attack":["Attack","Punch","MeleeAttack","Attack1","Attack_1"],"hit":["TakeDamage","Damage","Hit","HitReact","ReceiveHit","RecieveHit"],"death":["Death","Die","Dying"]},
      "render":{"target_height":1.45,"ortho_scale":7.5,"horizontal_anchor":"union-center"},
      "selection_goal":"Fallback BRUTE candidate: prioritize maximum body mass and grounded readability over thematic similarity.",
      "verdict":{"status":"rejected","validation_run_id":34155124577,"median_bbox_area_px2":2061,"median_bbox_width_px":45,"master_min_margin_px":48,"reason":"Technically clean 232/232 but median silhouette is smaller than Shambler; contact sheet also renders magenta due to unresolved external material assets."}
    },
    "brute-ultimate-blue-demon": {
      "actor":"brute",
      "source":{"provider":"Quaternius","pack":"Ultimate Monsters","license":"CC0 1.0","mirror_repository":"yerdaulet-damir/liminal","repository":"yerdaulet-damir/liminal","commit":"ff9ee5b57e2531b4f8841d6e7564283f943f6d34","path":"client/public/models/monster_BlueDemon.gltf","git_blob_sha":"52ec6f7bce0f753f2c6e539b39e52be6931c40cf","size_bytes":1190068,"sha256":"9eb899fc619dff9a973669e0962452990c8ec44d4501dd41fe4f4fe5afd631f4","upstream_path":"Big/glTF/BlueDemon.gltf","upstream_drive_url":"https://drive.google.com/uc?id=1mcxtHj9Aw1uu1FWYhl1rfPenuq3My-Z4"},
      "actions":{"idle":["Idle_CharacterArmature","Idle","idle"],"run":["Walk_CharacterArmature","Run_CharacterArmature","Walk","Run","Walking","Running"],"attack":["Punch_CharacterArmature","Weapon_CharacterArmature","Attack","Punch","MeleeAttack","Attack1","Attack_1"],"hit":["HitReact_CharacterArmature","TakeDamage","Damage","Hit","HitReact","ReceiveHit","RecieveHit"],"death":["Death_CharacterArmature","Death","Die","Dying"]},
      "render":{"target_height":1.45,"ortho_scale":7.5,"horizontal_anchor":"union-center"},
      "selection_goal":"BRUTE mass candidate: require a clearly larger phone-scale silhouette than Shambler and a grounded readable biped profile.",
      "verdict":{"status":"rejected","validation_run_id":34195721090,"median_bbox_area_px2":2066,"median_bbox_width_px":44,"master_min_margin_px":19,"reason":"Technically clean 232/232 with correct packed material, but median silhouette area 2066 px2 is smaller than accepted Shambler 2202 px2 and far below the BRUTE mass target."}
    },
    "brute-ultimate-demon": {
      "actor":"brute",
      "source":{"provider":"Quaternius","pack":"Ultimate Monsters","license":"CC0 1.0","mirror_repository":"yerdaulet-damir/liminal","repository":"yerdaulet-damir/liminal","commit":"ff9ee5b57e2531b4f8841d6e7564283f943f6d34","path":"client/public/models/monster_Demon.gltf","git_blob_sha":"77a74a32e368a28e0797a8803b689175dfa00228","size_bytes":1264925,"sha256":"9ce361b41a0e80d42a70f6325169b9c70a6e3304b8d856b8344a2b7f185067af","upstream_path":"Big/glTF/Demon.gltf"},
      "actions":{"idle":["Idle_CharacterArmature","Idle","idle"],"run":["Walk_CharacterArmature","Run_CharacterArmature","Walk","Run","Walking","Running"],"attack":["Punch_CharacterArmature","Weapon_CharacterArmature","Attack","Punch","MeleeAttack","Attack1","Attack_1"],"hit":["HitReact_CharacterArmature","TakeDamage","Damage","Hit","HitReact","ReceiveHit","RecieveHit"],"death":["Death_CharacterArmature","Death","Die","Dying"]},
      "render":{"target_height":1.45,"ortho_scale":7.5,"horizontal_anchor":"union-center"},
      "selection_goal":"BRUTE fallback: require a materially larger silhouette than Shambler, ideally at least 20 percent greater median bbox area, with readable grounded animations and intact packed material.",
      "verdict":{"status":"accepted","validation_run_id":34196722114,"validation_artifact_id":10044402041,"median_bbox_area_px2":2719.5,"median_bbox_width_px":55,"median_bbox_height_px":53,"master_min_margin_px":32,"shambler_area_ratio":1.235,"phone_qa_pass":true,"android_runtime_run_id":34205861027,"android_visual_artifact_id":10047825813,"android_visual_qa_pass":true,"reason":"232/232 production QA passes with intact packed material; median silhouette area is about 23.5% larger than accepted Shambler, and crowded real-Android QA confirms the authored red demon is materially larger, grounded, readable and unclipped."}
    }
  }
}
```

## File: actor-production-contracts.json
```json
{
  "schema_version": 1,
  "frame_contract": {
    "cell": [
      96,
      96
    ],
    "directions": [
      "n",
      "ne",
      "e",
      "se",
      "s",
      "sw",
      "w",
      "nw"
    ],
    "animations": {
      "idle": 4,
      "run": 8,
      "attack": 6,
      "hit": 3,
      "death": 8
    },
    "frames_per_direction": 29,
    "total_frames": 232
  },
  "actors": {
    "rex": {
      "kind": "survivor",
      "atlas_root": "survivor/rex",
      "published_png": "assets/art/rex.png",
      "normalization": "fixed-direction",
      "status": "accepted",
      "requires_weapon_visibility_gate": true,
      "validation": {
        "run_id": 35472131984,
        "artifact_id": 10594040502,
        "frame_count": 232,
        "phone_qa_pass": true,
        "weapon_visibility_pass": true,
        "android_runtime_run_id": 35497271551,
        "android_visual_artifact_id": 10601581509,
        "android_visual_qa_pass": true
      }
    },
    "shambler": {
      "kind": "enemy",
      "atlas_root": "enemy/shambler",
      "published_png": "assets/art/shambler.png",
      "published_manifest": "assets/art/shambler-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Zombie Apocalypse Kit",
        "license": "CC0 1.0",
        "master": "Zombie_Basic.blend",
        "sha256": "d13e073d52255d6a8a43160c6452755358c5a6cbbf900f2526359f113d8ed6ce",
        "texture": "Zombie_Atlas.png",
        "texture_sha256": "8803e5543d5e6b6f66aa41a5ef93e6c2ffb77b5476d0202296d2f4c8f4eb9e8d"
      },
      "actions": {
        "idle": "Idle",
        "run": "Walk",
        "attack": "Punch",
        "hit": "HitReact",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 3.25,
        "target_height": 0.58
      },
      "validation": {
        "run_id": 35496717473,
        "artifact_id": 10601186432,
        "frame_count": 232,
        "phone_qa_pass": true,
        "android_runtime_run_id": 35499754467,
        "android_visual_artifact_id": 10600929281,
        "android_visual_qa_pass": true
      }
    },
    "runner": {
      "kind": "enemy",
      "atlas_root": "enemy/runner",
      "published_png": "assets/art/runner.png",
      "published_manifest": "assets/art/runner-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "Zombie_Female.blend",
        "sha256": "663dfdf1aedc9e94f94e6a09586c71539fd9502f4f10dc84f03b7001715c3393",
        "git_blob_sha": "2177751d0026f788331001aed4ebbc0ca6bbb545",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 2415852
      },
      "actions": {
        "idle": "Idle",
        "run": "Run",
        "attack": "Punch",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.45
      },
      "validation": {
        "run_id": 34150615192,
        "frame_count": 232,
        "master_minimum_margin_px": 40,
        "phone_qa_pass": true,
        "android_runtime_run_id": 34152199690,
        "android_visual_artifact_id": 10029812483,
        "android_visual_qa_pass": true
      }
    },
    "brute": {
      "kind": "enemy",
      "atlas_root": "enemy/brute",
      "published_png": "assets/art/brute.png",
      "published_manifest": "assets/art/brute-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "Big/glTF/Demon.gltf",
        "sha256": "9ce361b41a0e80d42a70f6325169b9c70a6e3304b8d856b8344a2b7f185067af",
        "git_blob_sha": "77a74a32e368a28e0797a8803b689175dfa00228",
        "mirror_repository": "yerdaulet-damir/liminal",
        "mirror_commit": "ff9ee5b57e2531b4f8841d6e7564283f943f6d34",
        "size_bytes": 1264925
      },
      "actions": {
        "idle": "Idle_CharacterArmature",
        "run": "Walk_CharacterArmature",
        "attack": "Punch_CharacterArmature",
        "hit": "HitReact_CharacterArmature",
        "death": "Death_CharacterArmature"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.45
      },
      "validation": {
        "run_id": 34196722114,
        "frame_count": 232,
        "master_minimum_margin_px": 32,
        "median_bbox_area_px2": 2719.5,
        "median_bbox_width_px": 55,
        "shambler_area_ratio": 1.235,
        "phone_qa_pass": true,
        "android_runtime_run_id": 34205861027,
        "android_visual_artifact_id": 10047825813,
        "android_visual_qa_pass": true
      }
    },
    "ranged": {
      "kind": "enemy",
      "atlas_root": "enemy/ranged",
      "published_png": "assets/art/ranged.png",
      "published_manifest": "assets/art/ranged-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": true,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "BlueSoldier_Male.blend",
        "sha256": "ee9c0780a297c333aab1c59694afc4ad1d13fb0374327287c1ec8425be18337f",
        "git_blob_sha": "5a9ff78571c9c2bfc5492912ca3dbc6e9ed573d8",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 1869476
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [
          0,
          -1,
          -0.08
        ],
        "grip_offset": [
          0,
          -0.01,
          0.01
        ]
      },
      "actions": {
        "idle": "Idle",
        "run": "Walk",
        "attack": "Shoot_OneHanded",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.45
      },
      "validation": {
        "run_id": 34231682966,
        "frame_count": 232,
        "master_minimum_margin_px": 37,
        "median_bbox_area_px2": 1888.5,
        "median_bbox_width_px": 32,
        "phone_qa_pass": true,
        "explicit_weapon_geometry_pass": true,
        "android_runtime_run_id": 34236059385,
        "android_visual_artifact_id": 10060021215,
        "android_visual_qa_pass": true
      }
    },
    "elite": {
      "kind": "enemy",
      "atlas_root": "enemy/elite",
      "published_png": "assets/art/elite.png",
      "published_manifest": "assets/art/elite-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": true,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "monster_Orc.gltf",
        "sha256": "e61a37f8d9b2eee28928bdfad2c55e0798cc0a212e925cc5dcc66b243526c1a3",
        "git_blob_sha": "750b2401542551ef4fc01e7b5d85b96195119622",
        "mirror_repository": "yerdaulet-damir/liminal",
        "mirror_commit": "ff9ee5b57e2531b4f8841d6e7564283f943f6d34",
        "size_bytes": 1280189
      },
      "actions": {
        "idle": "Idle_CharacterArmature",
        "run": "Walk_CharacterArmature",
        "attack": "Weapon_CharacterArmature",
        "hit": "HitReact_CharacterArmature",
        "death": "Death_CharacterArmature"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.45
      },
      "validation": {
        "run_id": 34238028804,
        "frame_count": 232,
        "master_minimum_margin_px": 28,
        "median_bbox_area_px2": 1982,
        "median_bbox_width_px": 43,
        "phone_qa_pass": true,
        "native_weapon_attack_pass": true,
        "android_runtime_run_id": 34244152937,
        "android_visual_artifact_id": 10063433773,
        "android_visual_qa_pass": true
      }
    },
    "shielded": {
      "kind": "enemy",
      "atlas_root": "enemy/shielded",
      "published_png": "assets/art/shielded.png",
      "published_manifest": "assets/art/shielded-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "requires_defensive_prop_visibility_gate": true,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "BlueSoldier_Male.blend",
        "sha256": "ee9c0780a297c333aab1c59694afc4ad1d13fb0374327287c1ec8425be18337f",
        "git_blob_sha": "5a9ff78571c9c2bfc5492912ca3dbc6e9ed573d8",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 1869476
      },
      "defensive_prop": {
        "provider": "KayKit Game Assets",
        "pack": "Character Pack: Adventurers",
        "license": "CC0 1.0",
        "master": "shield_badge.fbx",
        "sha256": "8ae485d8451075d19ce504489328e7f3e9dea5cddbddee916508321853795604",
        "git_blob_sha": "b334acbdead8d7ea6655263080dca3ab3e0be517",
        "repository": "KayKit-Game-Assets/KayKit-Character-Pack-Adventures-1.0",
        "commit": "672074b73ba276876a19e8816ecdc5241817ab47",
        "bone": "Fist.L",
        "size_bytes": 27596
      },
      "actions": {
        "idle": "Idle",
        "run": "Walk",
        "attack": "Punch",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.45
      },
      "validation": {
        "run_id": 34251326917,
        "artifact_id": 10066284057,
        "frame_count": 232,
        "master_minimum_margin_px": 37,
        "median_bbox_area_px2": 2079.5,
        "median_bbox_width_px": 36,
        "phone_qa_pass": true,
        "explicit_shield_geometry_pass": true,
        "android_runtime_run_id": 34261934514,
        "android_visual_artifact_id": 10070394448,
        "android_visual_qa_pass": true
      }
    },
    "regenerator": {
      "kind": "enemy",
      "atlas_root": "enemy/regenerator",
      "published_png": "assets/art/regenerator.png",
      "published_manifest": "assets/art/regenerator-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "quaternius-green-blob.glb",
        "sha256": "fc68353469e3fe36586b7051a39bfdd0a466e48187f57fb39abf3932ca72447d",
        "git_blob_sha": "9e21e57419cdacf349c142eba9e2ee106c8c36bc",
        "mirror_repository": "Hakhyun-Kim/constellation-defense",
        "mirror_commit": "4431655c1a29638495c6ab3d4543d37c8d6deed6",
        "size_bytes": 87892
      },
      "actions": {
        "idle": "Idle_CharacterArmature",
        "run": "Walk_CharacterArmature",
        "attack": "Bite_Front_CharacterArmature",
        "hit": "HitRecieve_CharacterArmature",
        "death": "Death_CharacterArmature"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.3
      },
      "validation": {
        "run_id": 34266381609,
        "artifact_id": 10072289773,
        "frame_count": 232,
        "master_minimum_margin_px": 127,
        "median_bbox_area_px2": 3960,
        "median_bbox_width_px": 65,
        "median_bbox_height_px": 60,
        "phone_qa_pass": true,
        "organic_non_humanoid_identity_pass": true,
        "android_runtime_run_id": 34278412075,
        "android_visual_artifact_id": 10076741425,
        "android_visual_qa_pass": true
      }
    },
    "phantom": {
      "kind": "enemy",
      "atlas_root": "enemy/phantom",
      "published_png": "assets/art/phantom.png",
      "published_manifest": "assets/art/phantom-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "ghost.glb",
        "sha256": "430fb42e7b1c0af455ff89cd5bfdf490d133658e844935d5ffe44e9d9912ffd4",
        "git_blob_sha": "64a689730f9520be17e2628fa861ed9b1930054c",
        "mirror_repository": "ilrein/warptracker",
        "mirror_commit": "71bbfbdfacd118196994b26da68eec1876d55c6b",
        "size_bytes": 288500
      },
      "actions": {
        "idle": "Flying_Idle_CharacterArmature",
        "run": "Fast_Flying_CharacterArmature",
        "attack": "Headbutt_CharacterArmature",
        "hit": "HitReact_CharacterArmature",
        "death": "Death_CharacterArmature"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.5
      },
      "validation": {
        "run_id": 34289158328,
        "artifact_id": 10080877183,
        "frame_count": 232,
        "master_minimum_margin_px": 76,
        "median_bbox_area_px2": 3579.5,
        "median_bbox_width_px": 58,
        "median_bbox_height_px": 64,
        "phone_qa_pass": true,
        "ethereal_identity_pass": true,
        "android_runtime_run_id": 34293226994,
        "android_visual_artifact_id": 10082228087,
        "android_visual_qa_pass": true
      }
    },
    "boss": {
      "kind": "enemy",
      "atlas_root": "boss/alpha",
      "published_png": "assets/art/boss.png",
      "published_manifest": "assets/art/boss-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "quaternius-yeti.glb",
        "sha256": "68b26d4793cd1567139a72a009845be1c1f3b09e871653fd254d891bcef69ad9",
        "git_blob_sha": "a497b06d544af508e561decee951fc1f27179e02",
        "mirror_repository": "Hakhyun-Kim/constellation-defense",
        "mirror_commit": "4431655c1a29638495c6ab3d4543d37c8d6deed6",
        "size_bytes": 121520
      },
      "actions": {
        "idle": "Idle_CharacterArmature",
        "run": "Walk_CharacterArmature",
        "attack": "Bite_Front_CharacterArmature",
        "hit": "HitRecieve_CharacterArmature",
        "death": "Death_CharacterArmature"
      },
      "render": {
        "ortho_scale": 9.5,
        "target_height": 2.2
      },
      "validation": {
        "run_id": 34390994184,
        "artifact_id": 10119907335,
        "frame_count": 232,
        "master_minimum_margin_px": 100,
        "median_bbox_area_px2": 3658,
        "median_bbox_width_px": 62,
        "median_bbox_height_px": 60,
        "phone_qa_pass": true,
        "boss_identity_pass": true,
        "android_runtime_run_id": 34390994118,
        "android_visual_artifact_id": 10119773756,
        "android_visual_qa_pass": true
      }
    },
    "nyx": {
      "kind": "survivor",
      "atlas_root": "survivor/nyx",
      "published_png": "assets/art/nyx.png",
      "published_manifest": "assets/art/nyx-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": true,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "BlueSoldier_Female.blend",
        "sha256": "9f79873e2e3452f4a3ac64ff7ba3388c01d6e62633f3477ab8a27d844c3adfb2",
        "git_blob_sha": "ceaceb30aa5389cea266acf3becdae863c5820d4",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 2197748
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [
          0,
          -1,
          -0.08
        ],
        "grip_offset": [
          0,
          -0.01,
          0.01
        ]
      },
      "actions": {
        "idle": "Idle",
        "run": "Run",
        "attack": "Shoot_OneHanded",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.45
      },
      "validation": {
        "run_id": 34395791964,
        "artifact_id": 10121633792,
        "frame_count": 232,
        "master_minimum_margin_px": 40,
        "median_bbox_area_px2": 2079,
        "median_bbox_width_px": 37,
        "median_bbox_height_px": 60.5,
        "phone_qa_pass": true,
        "explicit_weapon_geometry_pass": true,
        "android_runtime_run_id": 34395791923,
        "android_visual_artifact_id": 10121562557,
        "android_visual_qa_pass": true
      }
    },
    "bastion": {
      "kind": "survivor",
      "atlas_root": "survivor/bastion",
      "published_png": "assets/art/bastion.png",
      "published_manifest": "assets/art/bastion-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": true,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "Knight_Golden_Male.blend",
        "sha256": "f81a02ff576815cffc8366c89b57d30888f45f9df3e6865245c81837d52172c9",
        "git_blob_sha": "9cc053899acbfca453669435ead43a10f633f64b",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 1888072
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [
          0,
          -1,
          -0.08
        ],
        "grip_offset": [
          0,
          -0.01,
          0.01
        ]
      },
      "actions": {
        "idle": "Idle",
        "run": "Run",
        "attack": "Shoot_OneHanded",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 8,
        "target_height": 1.68
      },
      "validation": {
        "run_id": 34477295133,
        "artifact_id": 10152231427,
        "frame_count": 232,
        "master_minimum_margin_px": 50,
        "median_bbox_area_px2": 2255,
        "median_bbox_width_px": 39,
        "phone_qa_pass": true,
        "explicit_weapon_geometry_pass": true,
        "android_runtime_run_id": 34477295057,
        "android_visual_artifact_id": 10152169355,
        "android_visual_qa_pass": true
      }
    },
    "volt": {
      "kind": "survivor",
      "atlas_root": "survivor/volt",
      "published_png": "assets/art/volt.png",
      "published_manifest": "assets/art/volt-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": true,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "Worker_Male.blend",
        "sha256": "b4b4f7a494d04b2a12a560536619ccb20c66876515fa487e6b95a10dc76d484e",
        "git_blob_sha": "f7212f9f70cf7a9b99d766648757863435e4b409",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 1862096
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [
          0,
          -1,
          -0.08
        ],
        "grip_offset": [
          0,
          -0.01,
          0.01
        ]
      },
      "actions": {
        "idle": "Idle",
        "run": "Run",
        "attack": "Shoot_OneHanded",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.48
      },
      "validation": {
        "run_id": 34482346899,
        "artifact_id": 10154378635,
        "frame_count": 232,
        "master_minimum_margin_px": 34,
        "median_bbox_area_px2": 1976,
        "median_bbox_width_px": 36,
        "phone_qa_pass": true,
        "explicit_weapon_geometry_pass": true,
        "technician_identity_pass": true,
        "android_runtime_run_id": 34487550020,
        "android_visual_artifact_id": 10156440934,
        "android_visual_qa_pass": true
      }
    },
    "wraith": {
      "kind": "survivor",
      "atlas_root": "survivor/wraith",
      "published_png": "assets/art/wraith.png",
      "published_manifest": "assets/art/wraith-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": true,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "Ninja_Male.blend",
        "sha256": "445a82fca15984090131910e45c6903b01c2e503c76c8dfb58db46927de8ea7e",
        "git_blob_sha": "1ccda6b3d9e68981bdafad32cdf1457a8b517223",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 1872620
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [
          0,
          -1,
          -0.08
        ],
        "grip_offset": [
          0,
          -0.01,
          0.01
        ]
      },
      "actions": {
        "idle": "Idle",
        "run": "Run",
        "attack": "Shoot_OneHanded",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.42
      },
      "validation": {
        "run_id": 34500223976,
        "artifact_id": 10161721488,
        "frame_count": 232,
        "master_minimum_margin_px": 46,
        "median_bbox_area_px2": 2108.5,
        "median_bbox_width_px": 36,
        "phone_qa_pass": true,
        "explicit_weapon_geometry_pass": true,
        "runner_identity_pass": true,
        "android_runtime_run_id": 34507795312,
        "android_visual_artifact_id": 10164659473,
        "android_visual_qa_pass": true
      }
    },
    "revenant": {
      "kind": "boss",
      "atlas_root": "boss/revenant",
      "published_png": "assets/art/revenant.png",
      "published_manifest": "assets/art/revenant-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "Knight_Golden_Male.blend",
        "sha256": "f81a02ff576815cffc8366c89b57d30888f45f9df3e6865245c81837d52172c9",
        "git_blob_sha": "9cc053899acbfca453669435ead43a10f633f64b",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 1888072
      },
      "actions": {
        "idle": "Idle",
        "run": "Walk",
        "attack": "Weapon",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.85
      },
      "validation": {
        "run_id": 34570992444,
        "artifact_id": 10187885488,
        "frame_count": 232,
        "master_minimum_margin_px": 36,
        "median_bbox_area_px2": 2140,
        "median_bbox_width_px": 36,
        "phone_qa_pass": true,
        "native_weapon_attack_pass": true,
        "android_runtime_run_id": 34583221325,
        "android_visual_artifact_id": 10192621684,
        "android_visual_qa_pass": true
      }
    },
    "warden": {
      "kind": "boss",
      "atlas_root": "boss/warden",
      "published_png": "assets/art/warden.png",
      "published_manifest": "assets/art/warden-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "Witch.blend",
        "sha256": "92042ee8da70dab6331218290bcfbc616165405757914596e82c6a9c36bce837",
        "git_blob_sha": "ccebbdc5f3b3cbd7b44468af9aa965075978a8ee",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 2188648
      },
      "actions": {
        "idle": "Idle",
        "run": "Walk",
        "attack": "Punch",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 9.2,
        "target_height": 1.9
      },
      "validation": {
        "run_id": 34592585776,
        "artifact_id": 10196349863,
        "frame_count": 232,
        "master_minimum_margin_px": 28,
        "median_bbox_area_px2": 1920,
        "median_bbox_width_px": 32,
        "phone_qa_pass": true,
        "android_runtime_run_id": 34609391605,
        "android_visual_artifact_id": 10267628115,
        "android_visual_qa_pass": true
      }
    },
    "harvester": {
      "kind": "boss",
      "atlas_root": "boss/harvester",
      "published_png": "assets/art/harvester.png",
      "published_manifest": "assets/art/harvester-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "Viking_Male.blend",
        "sha256": "3649d3342b6c157abf61314e8afda96d6c5bf724e996cd337c415eb2adf7021c",
        "git_blob_sha": "79620718ba895acec12685980391d38c7d2a0e2c",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 2133356
      },
      "actions": {
        "idle": "Idle",
        "run": "Walk",
        "attack": "Punch",
        "hit": "RecieveHit",
        "death": "Defeat"
      },
      "render": {
        "ortho_scale": 6.75,
        "target_height": 1.95
      },
      "validation": {
        "run_id": 34622861724,
        "artifact_id": 10273348562,
        "frame_count": 232,
        "master_minimum_margin_px": 100,
        "median_bbox_area_px2": 3357,
        "median_bbox_width_px": 40,
        "phone_qa_pass": true,
        "android_runtime_run_id": 34624187689,
        "android_visual_artifact_id": 10274036186,
        "android_visual_qa_pass": true
      }
    },
    "null_archon": {
      "kind": "boss",
      "atlas_root": "boss/null_archon",
      "published_png": "assets/art/null_archon.png",
      "published_manifest": "assets/art/null_archon-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "Dragon_Evolved.gltf",
        "sha256": "39ba6ea24b5f27acf68bbf4c19fe80ba070dbec167ff14bbe933453303426f5c",
        "git_blob_sha": "f70ceaed1f52d26e8b2cd24f8a9bd43cd380ed6a",
        "mirror_repository": "mlflabs/brain",
        "mirror_commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e",
        "size_bytes": 991335
      },
      "actions": {
        "idle": "Flying_Idle_CharacterArmature",
        "run": "Fast_Flying_CharacterArmature",
        "attack": "Headbutt_CharacterArmature",
        "hit": "HitReact_CharacterArmature",
        "death": "Death_CharacterArmature"
      },
      "render": {
        "ortho_scale": 10.5,
        "target_height": 2.15
      },
      "validation": {
        "run_id": 34647430253,
        "artifact_id": 10283435668,
        "frame_count": 232,
        "master_minimum_margin_px": 120,
        "median_bbox_area_px2": 2809,
        "median_bbox_width_px": 61,
        "median_bbox_height_px": 49,
        "phone_qa_pass": true,
        "apex_non_humanoid_identity_pass": true,
        "android_runtime_run_id": 34648535215,
        "android_visual_artifact_id": 10282293846,
        "android_visual_qa_pass": true
      }
    },
    "forge_hound": {
      "kind": "enemy",
      "atlas_root": "enemy/biome/forge_hound",
      "published_png": "assets/art/forge_hound.png",
      "published_manifest": "assets/art/forge_hound-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "Blob/glTF/Dog.gltf",
        "sha256": "21ed475bbf4d5fb7016085290f79663ad7107aedd2a5856318f2716c7ecc6402",
        "git_blob_sha": "1ed34fa3b17358a766707bc59d3798d3f1304091",
        "mirror_repository": "mlflabs/brain",
        "mirror_commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e",
        "size_bytes": 142124
      },
      "actions": {
        "idle": "Idle",
        "run": "Run",
        "attack": "Bite_Front",
        "hit": "HitRecieve",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.35
      },
      "validation": {
        "run_id": 34656325603,
        "artifact_id": 10285836366,
        "frame_count": 232,
        "master_minimum_margin_px": 145,
        "median_bbox_area_px2": 3654,
        "median_bbox_width_px": 63,
        "phone_qa_pass": true,
        "non_humanoid_identity_pass": true,
        "android_visual_qa_pass": true
      }
    },
    "cinder_gunner": {
      "kind": "enemy",
      "atlas_root": "enemy/biome/cinder_gunner",
      "published_png": "assets/art/cinder_gunner.png",
      "published_manifest": "assets/art/cinder_gunner-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": true,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "BlueSoldier_Female.blend",
        "sha256": "9f79873e2e3452f4a3ac64ff7ba3388c01d6e62633f3477ab8a27d844c3adfb2",
        "git_blob_sha": "ceaceb30aa5389cea266acf3becdae863c5820d4",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631",
        "size_bytes": 2197748
      },
      "weapon": {
        "style": "compact-rifle",
        "bone": "Fist.R",
        "forward": [
          0,
          -1,
          -0.08
        ],
        "grip_offset": [
          0,
          -0.01,
          0.01
        ]
      },
      "actions": {
        "idle": "Idle",
        "run": "Walk",
        "attack": "Shoot_OneHanded",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.45
      },
      "validation": {
        "run_id": 34676331151,
        "artifact_id": 10292517429,
        "frame_count": 232,
        "master_minimum_margin_px": 40,
        "median_bbox_area_px2": 2079,
        "median_bbox_width_px": 34.5,
        "phone_qa_pass": true,
        "explicit_weapon_geometry_pass": true,
        "android_runtime_run_id": 34676647378,
        "android_visual_artifact_id": 10292557790,
        "android_visual_qa_pass": true
      }
    },
    "slag_guard": {
      "kind": "enemy",
      "atlas_root": "enemy/biome/slag_guard",
      "published_png": "assets/art/slag_guard.png",
      "published_manifest": "assets/art/slag_guard-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Animated Character Pack",
        "license": "CC0 1.0",
        "master": "Knight_Golden_Male.blend",
        "sha256": "f81a02ff576815cffc8366c89b57d30888f45f9df3e6865245c81837d52172c9",
        "git_blob_sha": "9cc053899acbfca453669435ead43a10f633f64b",
        "mirror_repository": "ariesyous/projectbluebean",
        "mirror_commit": "4769997aaea204c66735813e2bf61dc3c94e7631"
      },
      "actions": {
        "idle": "Idle",
        "run": "Walk",
        "attack": "SwordSlash",
        "hit": "RecieveHit",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 2.15
      },
      "validation": {
        "run_id": 34684950106,
        "artifact_id": 10295445972,
        "frame_count": 232,
        "master_minimum_margin_px": 36,
        "median_bbox_area_px2": 2200.5,
        "median_bbox_width_px": 39,
        "phone_qa_pass": true,
        "android_runtime_run_id": 34684950117,
        "android_visual_artifact_id": 10295540725,
        "android_visual_qa_pass": true
      }
    },
    "phase_stalker": {
      "kind": "enemy",
      "atlas_root": "enemy/biome/phase_stalker",
      "published_png": "assets/art/phase_stalker.png",
      "published_manifest": "assets/art/phase_stalker-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "Flying/glTF/Ghost.gltf",
        "sha256": "40bea9d887874cc86b391b83e72cc5ac2cac340892e23f3d8068a568b6e61457",
        "git_blob_sha": "c93cf0084ef108837fc94dd4d7fca5afbf9af3ee",
        "mirror_repository": "mlflabs/brain",
        "mirror_commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e"
      },
      "actions": {
        "idle": "Flying_Idle",
        "run": "Fast_Flying",
        "attack": "Headbutt",
        "hit": "HitReact",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.55
      },
      "validation": {
        "run_id": 34686937622,
        "artifact_id": 10295983021,
        "frame_count": 232,
        "master_minimum_margin_px": 76,
        "median_bbox_area_px2": 3770,
        "median_bbox_width_px": 60,
        "phone_qa_pass": true,
        "android_runtime_run_id": 34686937606,
        "android_visual_artifact_id": 10295827983,
        "android_visual_qa_pass": true
      }
    },
    "static_seer": {
      "kind": "enemy",
      "atlas_root": "enemy/biome/static_seer",
      "published_png": "assets/art/static_seer.png",
      "published_manifest": "assets/art/static_seer-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "Flying/glTF/Hywirl.gltf",
        "sha256": "aed5759f91d18ce1922723d5698f5afad2c8d8b26f5c6f11b018ca967898fa50",
        "git_blob_sha": "3de288e2b4b208f9fe019b38ef502f104f88877c",
        "mirror_repository": "mlflabs/brain",
        "mirror_commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e"
      },
      "actions": {
        "idle": "Flying_Idle",
        "run": "Fast_Flying",
        "attack": "Punch",
        "hit": "HitReact",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.55
      },
      "validation": {
        "run_id": 34689516608,
        "artifact_id": 10296907077,
        "frame_count": 232,
        "master_minimum_margin_px": 75,
        "median_bbox_area_px2": 3136,
        "median_bbox_width_px": 57,
        "phone_qa_pass": true,
        "android_runtime_run_id": 34689516609,
        "android_visual_artifact_id": 10297011867,
        "android_visual_qa_pass": true
      }
    },
    "null_ward": {
      "kind": "enemy",
      "atlas_root": "enemy/biome/null_ward",
      "published_png": "assets/art/null_ward.png",
      "published_manifest": "assets/art/null_ward-manifest.json",
      "normalization": "union-center",
      "status": "accepted",
      "requires_weapon_visibility_gate": false,
      "source": {
        "provider": "Quaternius",
        "pack": "Ultimate Monsters",
        "license": "CC0 1.0",
        "master": "Flying/glTF/Ghost_Skull.gltf",
        "sha256": "5bf24cf0f22aa94b0b58315e7d879afc53a0964a5b326aa0c844ba3278cae1da",
        "git_blob_sha": "8b6af9bca7bc05cc8056695b7ba75a1bf317148d",
        "mirror_repository": "mlflabs/brain",
        "mirror_commit": "54b3258a7ab6558fee969100aafb24ff285e0f0e"
      },
      "actions": {
        "idle": "Flying_Idle",
        "run": "Fast_Flying",
        "attack": "Headbutt",
        "hit": "HitReact",
        "death": "Death"
      },
      "render": {
        "ortho_scale": 7.5,
        "target_height": 1.65
      },
      "validation": {
        "run_id": 34696521969,
        "artifact_id": 10299241585,
        "frame_count": 232,
        "master_minimum_margin_px": 76,
        "median_bbox_area_px2": 3563,
        "median_bbox_width_px": 58,
        "phone_qa_pass": true,
        "android_runtime_run_id": 34696521961,
        "android_visual_artifact_id": 10298309475,
        "android_visual_qa_pass": true
      }
    }
  }
}
```

## File: environment-art-contract.json
```json
{
  "version": 1,
  "purpose": "Production contract for Deadline Zero biome environment art.",
  "runtimePolicy": {
    "preferred": "environment/<biome>/<slot>",
    "fallback": "environment/<slot>",
    "bootstrapFallbackAllowedDuringDevelopment": true,
    "releaseGoal": "all 70 biome-specific slots authored and present in art/game.atlas"
  },
  "biomes": [
    {"id":"quarantine_yard","stageStart":1,"palette":"cold industrial steel, emergency red, dirty concrete"},
    {"id":"cinder_foundry","stageStart":10,"palette":"molten orange, furnace red, black steel"},
    {"id":"null_sector","stageStart":20,"palette":"violet void energy, electric cyan, black alloy"},
    {"id":"cryo_vault","stageStart":30,"palette":"clean ice blue, white frost, dark navy steel"},
    {"id":"cryogenic_depths","stageStart":40,"palette":"deep cyan, frozen teal, abyssal blue"}
  ],
  "slots": [
    {"path":"floor/concrete_a","kind":"tile","tileable":true,"alpha":false},
    {"path":"floor/concrete_b","kind":"tile","tileable":true,"alpha":false},
    {"path":"floor/concrete_c","kind":"tile","tileable":true,"alpha":false},
    {"path":"floor/hazard_a","kind":"tile","tileable":true,"alpha":false},
    {"path":"decal/crack_a","kind":"decal","tileable":false,"alpha":true},
    {"path":"decal/blood_a","kind":"decal","tileable":false,"alpha":true},
    {"path":"decal/scorch_a","kind":"decal","tileable":false,"alpha":true},
    {"path":"prop/barrier_a","kind":"prop","tileable":false,"alpha":true},
    {"path":"prop/debris_a","kind":"prop","tileable":false,"alpha":true},
    {"path":"prop/debris_b","kind":"prop","tileable":false,"alpha":true},
    {"path":"prop/wall_a","kind":"prop","tileable":false,"alpha":true},
    {"path":"prop/wall_b","kind":"prop","tileable":false,"alpha":true},
    {"path":"prop/crate_a","kind":"prop","tileable":false,"alpha":true},
    {"path":"prop/beacon_a","kind":"prop","tileable":false,"alpha":true}
  ],
  "sourceGuidance": {
    "masterResolution": 512,
    "runtimeTarget": 256,
    "style": "premium top-down sci-fi survival, physically readable materials, no text, no logos",
    "camera": "orthographic/top-down compatible",
    "lighting": "baked local material lighting only; preserve readability under runtime tint and local-light FX",
    "silhouetteRule": "props must remain readable at approximately 32-96 screen pixels",
    "floorRule": "floor tiles must be seamless and low-contrast enough not to compete with enemies/projectiles",
    "decalRule": "decals need clean transparent edges and must not contain baked rectangular backgrounds"
  }
}
```
