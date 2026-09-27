# Combat Depth V1 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Make Deadline Zero's mobile combat more distinctive, readable, and progressively intense without exceeding the existing Android performance envelope.

**Architecture:** Keep weapon identity in focused profile/feedback helpers instead of growing Main.gd. Enemy reactions remain owned by Enemy.gd, while run escalation is extracted into a deterministic director helper consumed by Main.gd. Every behavioral change starts with a headless regression test and is gated by the existing Godot/Android workflows.

**Tech Stack:** Godot 4.7.2, GDScript, GitHub Actions, Android export.

**Spec:** Approved in-chat design on 2026-09-26: weapon/protocol identity, enemy hierarchy/reactions, run escalation, strict mobile budget, progressive Main.gd responsibility extraction, full Godot + Android CI.

## Global Constraints

- Android/mobile performance remains a hard constraint; no per-hit or per-projectile dynamic lights.
- Preserve current playable behavior unless a task explicitly changes it.
- No placeholders, TODO-only behavior, or mock runtime systems in the final branch.
- Prefer deterministic pure helpers for balance/escalation logic so headless tests remain fast.
- Complete one independently testable behavior before starting the next.

## Review Focus

- Protocol combinations must remain mutually exclusive and preserve their existing gameplay modifiers.
- High enemy counts must not create unbounded FX, lights, or persistent nodes.
- Elite/boss reactions must remain readable without stun-locking or trivializing threats.
- Escalation boundaries around 45/75/90/150 seconds must be deterministic and not cause spawn spikes.
- Android touch movement, pause, upgrade selection, death, and restart flows must remain intact.

---

### Task 1: Weapon and protocol combat identity

**Files:**
- Modify: `godot/scripts/WeaponProfiles.gd`
- Modify: `godot/scripts/Projectile.gd`
- Modify: `godot/scripts/CombatFeel.gd`
- Test: `godot/tests/weapon_protocol_behavior_test.gd`

- [ ] Add failing assertions proving Vanguard, Scatter, Rail, Inferno, Cryo, and Arc expose distinct feedback/behavior signatures.
- [ ] Run the focused headless test and confirm RED.
- [ ] Add deterministic profile metadata for projectile scale/trail/impact feel without dynamic lights.
- [ ] Apply profile metadata in Projectile while preserving current damage/pierce/status behavior.
- [ ] Run focused tests and existing projectile/protocol tests; confirm GREEN.
- [ ] Commit the independently reviewable weapon-identity change.

### Task 2: Enemy hit reaction hierarchy

**Files:**
- Modify: `godot/scripts/Enemy.gd`
- Test: `godot/tests/native_enemy_behavior_test.gd`
- Test: `godot/tests/enemy_archetype_combat_test.gd`

- [ ] Add failing tests for regular, elite, and boss reaction envelopes.
- [ ] Confirm RED in headless Godot.
- [ ] Implement bounded hit-react motion/flash differences with bosses resistant to displacement and no persistent FX nodes.
- [ ] Verify death, regeneration, charge, dodge, and boss phase mechanics still pass.
- [ ] Commit the enemy-reaction change.

### Task 3: Deterministic run escalation director

**Files:**
- Create: `godot/scripts/RunDirector.gd`
- Modify: `godot/scripts/Main.gd`
- Create: `godot/tests/run_director_test.gd`
- Modify: `.github/workflows/godot-verify.yml`

- [ ] Write failing tests for spawn interval, batch size, archetype eligibility, and boss cadence at key time boundaries.
- [ ] Confirm RED because RunDirector does not exist.
- [ ] Implement pure deterministic RunDirector functions with explicit caps.
- [ ] Replace Main.gd's inline spawn escalation calculations with RunDirector calls.
- [ ] Add the test to CI and run the first-playable run-path regression.
- [ ] Commit the run-director extraction.

### Task 4: Combat readability under pressure

**Files:**
- Modify: `godot/scripts/Hud.gd`
- Modify: `godot/scripts/Main.gd`
- Test: `godot/tests/combat_danger_hud_test.gd`

- [ ] Add failing tests for simultaneous boss/elite danger prioritization and critical-health readability.
- [ ] Confirm RED.
- [ ] Make threat priority deterministic and avoid overlapping warnings while preserving the current damage vignette.
- [ ] Verify HUD at supported Android viewport sizes through existing responsive QA.
- [ ] Commit the readability change.

### Task 5: Full regression and mobile gate

**Files:**
- Modify only files required by discovered regressions.

- [ ] Run all headless Godot tests in `godot-verify.yml`.
- [ ] Run Android First Playable workflow.
- [ ] Run Responsive UI QA.
- [ ] Run Verify including Android runtime smoke.
- [ ] Fix root causes of any regression and rerun the affected plus full gates.
- [ ] Review the final diff for accidental dynamic lights, TODOs, placeholders, or duplicated responsibilities.
- [ ] Merge only after all required workflows are green.
