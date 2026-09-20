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
android/
  scan_runtime_log.py
  test_scan_runtime_log.py
blender/
  add_rex_rifle.py
  build_rex_actions.py
  catalog_blend_actions.py
  inspect_actor_source.py
  prepare_meshy_actor.py
  refine_rex_rifle_attack.py
  render_actor_8dir.py
  repair_rex_weights.py
  style_rex_materials.py
  validate_rex_weapon_visibility.py
  validate_rig.py
environment/
  generate_quarantine_yard_candidate.py
  pack_environment_art.py
  test_upsert_environment_atlas.py
  test_validate_environment_art_contract.py
  upsert_environment_atlas.py
  validate_environment_art_contract.py
perf/
  compare_android_benchmark.py
  test_compare_android_benchmark.py
sprites/
  assemble_actor_sheet.py
  audit_final_art_status.py
  normalize_actor_frames.py
  normalize_rex_frames.py
  resolve_actor_actions.py
  resolve_actor_candidate.py
  test_resolve_actor_actions.py
  test_validate_actor_production_contracts.py
  test_validate_actor_role_metrics.py
  upsert_directional_actor_atlas.py
  validate_actor_production_contracts.py
  validate_actor_role_metrics.py
  validate_final_art_promotion_consistency.py
  validate_ranged_attack_readability.py
build_final_sprite_frames.py
import_pixellab_idle.py
slice_sprite_sheet.py
test_verify_final_atlas.py
texturepacker-final.json
validate_final_sprite_layout.py
validate_rex_reference.py
verify_final_atlas.py
```

# Files

## File: android/scan_runtime_log.py
```python
#!/usr/bin/env python3
⋮----
DEFAULT_PACKAGE = "com.deadlinezero.game"
⋮----
def _exact_package(pattern_prefix: str, package: str) -> re.Pattern
⋮----
# Require a delimiter that cannot continue an Android package name. This prevents
# com.deadlinezero.game.test from being mistaken for com.deadlinezero.game.
⋮----
def scan(text: str, package: str = DEFAULT_PACKAGE) -> dict
⋮----
lines = text.splitlines()
findings = []
⋮----
anr = _exact_package(r"\bANR in\s+", package)
process = _exact_package(r"\bProcess:\s*", package)
cmdline = _exact_package(r"\bCmdline:\s*", package)
fatal_exception = re.compile(r"\bFATAL EXCEPTION\b")
native_fatal = re.compile(r"\bFatal signal\s+(?:6|11)\b", re.IGNORECASE)
⋮----
window = "\n".join(lines[i:min(len(lines), i + 8)])
⋮----
start = max(0, i - 3)
end = min(len(lines), i + 8)
window = "\n".join(lines[start:end])
⋮----
def main() -> int
⋮----
parser = argparse.ArgumentParser()
⋮----
args = parser.parse_args()
⋮----
result = scan(Path(args.logcat).read_text(errors="replace"), args.package)
rendered = json.dumps(result, indent=2, sort_keys=True)
```

## File: android/test_scan_runtime_log.py
```python
ROOT = pathlib.Path(__file__).resolve().parents[2]
MODULE_PATH = ROOT / "tools" / "android" / "scan_runtime_log.py"
spec = importlib.util.spec_from_file_location("scan_runtime_log", MODULE_PATH)
mod = importlib.util.module_from_spec(spec)
⋮----
class RuntimeLogScannerTest(unittest.TestCase)
⋮----
def test_clean_log_passes(self)
⋮----
result = mod.scan("I ActivityManager: Start proc com.deadlinezero.game\nI DeadlineZero: running")
⋮----
def test_detects_package_anr(self)
⋮----
result = mod.scan("E ActivityManager: ANR in com.deadlinezero.game (com.deadlinezero.game/.android.AndroidLauncher)")
⋮----
def test_detects_java_crash_only_for_game_process(self)
⋮----
log = """E AndroidRuntime: FATAL EXCEPTION: main
result = mod.scan(log)
⋮----
def test_ignores_other_process_java_crash(self)
⋮----
def test_ignores_instrumentation_process_with_package_prefix(self)
⋮----
log = """E AndroidRuntime: FATAL EXCEPTION: Instr: androidx.test.runner.AndroidJUnitRunner
⋮----
def test_ignores_instrumentation_process_anr_with_package_prefix(self)
⋮----
def test_exact_game_package_still_matches_with_activity_suffix(self)
⋮----
def test_detects_native_crash_when_package_is_in_context(self)
⋮----
log = """I DEBUG: Cmdline: com.deadlinezero.game
⋮----
def test_ignores_intentional_force_stop(self)
```

## File: blender/add_rex_rifle.py
```python
#!/usr/bin/env python3
"""Add a deterministic separate sci-fi rifle to Rex.

The rifle is authored around a grip origin, placed explicitly at the evaluated
right-wrist joint in the rest pose, then bone-parented while preserving that
world transform. This avoids Blender bone-parent tail offsets that previously
left the weapon visibly detached from the hand after GLB export/import.
"""
⋮----
def argv()
⋮----
a = sys.argv
a = a[a.index("--") + 1:] if "--" in a else []
p = argparse.ArgumentParser()
⋮----
def mat(name, color, metal=.5, rough=.32, emission=None)
⋮----
m = bpy.data.materials.new(name)
⋮----
bs = m.node_tree.nodes.get("Principled BSDF")
⋮----
e = bs.inputs.get("Emission Color") or bs.inputs.get("Emission")
⋮----
s = bs.inputs.get("Emission Strength")
⋮----
def cube(name, loc, scale, material)
⋮----
o = bpy.context.object
⋮----
def main()
⋮----
a = argv()
⋮----
arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
⋮----
arm = arms[0]
bone = "R_Wrist" if arm.data.bones.get("R_Wrist") else "RightHand"
⋮----
dark = mat("Rifle_Gunmetal", (0.012, 0.020, 0.030), .88, .22)
navy = mat("Rifle_Navy", (0.020, 0.060, 0.090), .72, .27)
cyan = mat("Rifle_Cyan", (0.01, .36, .54), .35, .18, (0.01, .34, .52))
orange = mat("Rifle_Orange", (.56, .11, .02), .5, .30)
⋮----
# Weapon-local coordinates: +X muzzle, origin = firing-hand grip.
# Slightly exaggerated silhouette is deliberate for 96px readability.
parts = [
⋮----
rifle = bpy.context.object
⋮----
# Move the joined object's origin to the authored grip at world (0,0,0)
# without moving geometry.
⋮----
# Explicit rest-pose world placement. Rex faces -Y in the Blender sprite
# scene; +X points inward from his right hand toward the torso. A diagonal
# forward/inward low-ready vector remains legible from all 8 cameras.
pb = arm.pose.bones[bone]
⋮----
wrist_world = arm.matrix_world @ pb.matrix.translation
direction = Vector((0.42, -0.88, -0.22)).normalized()
rot = direction.to_track_quat("X", "Z").to_matrix().to_4x4()
desired = rot
⋮----
grip_error = (rifle.matrix_world.translation - desired.translation).length
```

## File: blender/build_rex_actions.py
```python
#!/usr/bin/env python3
"""Build deterministic Rex animation actions on a rigged SMPL22 GLB.

These actions are deformation/animation QA motions. They remain intentionally
conservative so a valid skin is stressed without forcing anatomically absurd
poses that would make a good rig look broken.
"""
⋮----
REQUIRED_BONES = {
⋮----
def parse_args() -> argparse.Namespace
⋮----
argv = sys.argv
argv = argv[argv.index("--") + 1 :] if "--" in argv else []
p = argparse.ArgumentParser()
⋮----
def reset_scene() -> None
⋮----
def import_asset(path: Path) -> None
⋮----
suffix = path.suffix.lower()
⋮----
def armature_and_meshes()
⋮----
arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
⋮----
arm = arms[0]
meshes = [
⋮----
missing = sorted(REQUIRED_BONES - set(arm.pose.bones.keys()))
⋮----
def bounds(meshes)
⋮----
points = [obj.matrix_world @ Vector(corner) for obj in meshes for corner in obj.bound_box]
mins = Vector((min(p.x for p in points), min(p.y for p in points), min(p.z for p in points)))
maxs = Vector((max(p.x for p in points), max(p.y for p in points), max(p.z for p in points)))
⋮----
def ground_actor(arm, meshes) -> None
⋮----
shift = Vector((-(mins.x + maxs.x) * 0.5, -(mins.y + maxs.y) * 0.5, -mins.z))
actor_objects = set(meshes) | {arm}
roots = [o for o in actor_objects if o.parent not in actor_objects]
⋮----
world = obj.matrix_world.copy()
⋮----
def clear_actions(arm) -> None
⋮----
def zero_pose(arm) -> None
⋮----
def apply_pose(arm, rotations=None, locations=None) -> None
⋮----
rotations = rotations or {}
locations = locations or {}
⋮----
bone = arm.pose.bones[name]
⋮----
def key_pose(arm, frame: int) -> None
⋮----
def make_action(arm, name: str, frames: int, poses: list[tuple[int, dict, dict]]) -> None
⋮----
action = bpy.data.actions.new(name=name)
⋮----
def build_actions(arm) -> None
⋮----
# Idle: subtle breathing/weight shift.
idle_a = {
idle_b = {
⋮----
# Run: keep the rifle-ready upper body stable while the legs drive the gait.
# Large opposing shoulder swings move the rifle/cape silhouette enough to
# violate the final 6px horizontal-pivot budget in side/diagonal views.
run_a = {
run_b = {
⋮----
# Rifle attack: compact low-ready -> shoulder aim -> recoil -> recovery.
# Negative shoulder X raises the firing arm on this SMPL22 rest pose. The
# support arm follows without extreme cape-adjacent shoulder deformation.
ready = {
aim = {
recoil = {
⋮----
# Hit: compact recoil.
hit = {
⋮----
# Death QA: controlled knee-collapse rather than stacking huge spine bends.
fall_mid = {
fall_end = {
⋮----
def main() -> None
⋮----
args = parse_args()
⋮----
expected = {"idle", "run", "attack", "hit", "death"}
missing = expected - set(bpy.data.actions.keys())
```

## File: blender/catalog_blend_actions.py
```python
#!/usr/bin/env python3
"""Emit deterministic structural/action metadata for the currently opened .blend file."""
⋮----
def argv_after_separator() -> list[str]
⋮----
def main() -> None
⋮----
parser = argparse.ArgumentParser()
⋮----
args = parser.parse_args(argv_after_separator())
⋮----
actions = []
⋮----
armatures = []
⋮----
meshes = []
⋮----
payload = {
output = Path(args.output)
```

## File: blender/inspect_actor_source.py
```python
#!/usr/bin/env python3
"""Inspect a Blender actor source without modifying it.

Usage:
  blender -b actor.blend -P tools/blender/inspect_actor_source.py -- \
    --json-out build/actor-source-inspection.json
"""
⋮----
def parse_args() -> argparse.Namespace
⋮----
argv = sys.argv
argv = argv[argv.index("--") + 1 :] if "--" in argv else []
parser = argparse.ArgumentParser()
⋮----
def rounded(values)
⋮----
def main() -> None
⋮----
args = parse_args()
scene = bpy.context.scene
meshes = [obj for obj in scene.objects if obj.type == "MESH"]
armatures = [obj for obj in scene.objects if obj.type == "ARMATURE"]
actions = []
⋮----
mesh_rows = []
total_vertices = 0
total_polygons = 0
total_triangles = 0
⋮----
mesh = obj.data
⋮----
armature_rows = []
⋮----
images = []
⋮----
report = {
```

## File: blender/prepare_meshy_actor.py
```python
#!/usr/bin/env python3
"""Prepare a raw Meshy GLB as a Deadline Zero actor master.

Blender usage:
  blender -b --factory-startup -P tools/blender/prepare_meshy_actor.py -- \
    --input path/to/rex.glb --output build/rex_master.blend --actor Rex

This deliberately does NOT invent an armature. Automatic rigging without a
validated humanoid skeleton can silently produce bad deformation. Instead it
performs every safe deterministic preprocessing step and leaves a clean,
normalized master ready for rigging.
"""
⋮----
def args()
⋮----
a = sys.argv[sys.argv.index('--') + 1:] if '--' in sys.argv else []
p = argparse.ArgumentParser()
⋮----
def mesh_objects()
⋮----
def world_bounds(objs)
⋮----
pts = [o.matrix_world @ Vector(corner) for o in objs for corner in o.bound_box]
lo = Vector((min(p.x for p in pts), min(p.y for p in pts), min(p.z for p in pts)))
hi = Vector((max(p.x for p in pts), max(p.y for p in pts), max(p.z for p in pts)))
⋮----
def triangles(obj)
⋮----
deps = bpy.context.evaluated_depsgraph_get()
m = obj.evaluated_get(deps).to_mesh()
m.calc_loop_triangles(); n = len(m.loop_triangles)
⋮----
def main()
⋮----
a = args()
⋮----
objs = mesh_objects()
⋮----
# Keep the HD source collection untouched by modifiers where possible.
⋮----
h = hi.z - lo.z
⋮----
scale = a.target_height / h
center_xy = Vector(((lo.x + hi.x) * .5, (lo.y + hi.y) * .5, lo.z))
⋮----
# Apply transforms so downstream armatures/rendering get stable coordinates.
⋮----
before = sum(triangles(o) for o in objs)
⋮----
ratio = max(0.01, min(1.0, a.decimate / before))
⋮----
mod = o.modifiers.new('DZ_PreviewDecimate', 'DECIMATE'); mod.ratio = ratio
⋮----
after = sum(triangles(o) for o in objs)
⋮----
# Neutral material makes an untextured Meshy export readable while rigging.
⋮----
mat = bpy.data.materials.new('DZ_RiggingNeutral')
```

## File: blender/refine_rex_rifle_attack.py
```python
#!/usr/bin/env python3
"""Refine Rex's generic attack QA action into a clearer rifle-firing motion.

This runs after build_rex_actions.py on the generated .blend. It only replaces
the armature's `attack` action, leaving idle/run/hit/death untouched.
"""
⋮----
def parse_args() -> argparse.Namespace
⋮----
argv = sys.argv
argv = argv[argv.index("--") + 1 :] if "--" in argv else []
p = argparse.ArgumentParser()
⋮----
def find_armature()
⋮----
arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
⋮----
def zero_pose(arm) -> None
⋮----
def apply_pose(arm, rotations=None, locations=None) -> None
⋮----
rotations = rotations or {}
locations = locations or {}
⋮----
bone = arm.pose.bones[name]
⋮----
def key_pose(arm, frame: int) -> None
⋮----
def rebuild_attack(arm) -> None
⋮----
old = bpy.data.actions.get("attack")
⋮----
action = bpy.data.actions.new("attack")
⋮----
# Stronger phone-scale silhouette: low-ready -> shoulder aim -> recoil -> settle.
# Angles stay below extreme cape-stressing ranges used by early QA versions.
poses = [
⋮----
def main() -> None
⋮----
args = parse_args()
arm = find_armature()
```

## File: blender/render_actor_8dir.py
```python
#!/usr/bin/env python3
"""Blender-side renderer for Deadline Zero actor sprites."""
⋮----
DIRECTIONS = [
DEFAULT_COUNTS = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}
⋮----
def parse_args() -> argparse.Namespace
⋮----
argv = sys.argv
argv = argv[argv.index("--") + 1 :] if "--" in argv else []
p = argparse.ArgumentParser()
⋮----
def find_armature()
⋮----
arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
⋮----
def ensure_camera(args: argparse.Namespace)
⋮----
cam_obj = bpy.data.objects.get("DZ_Camera")
⋮----
data = bpy.data.cameras.new("DZ_Camera")
cam_obj = bpy.data.objects.new("DZ_Camera", data)
⋮----
def look_at(obj, point: Vector)
⋮----
direction = point - obj.location
⋮----
def select_eevee(scene) -> str
⋮----
def configure_scene(args: argparse.Namespace)
⋮----
scene = bpy.context.scene
engine = select_eevee(scene)
⋮----
def ensure_preview_material_and_lights(armature)
⋮----
meshes = [o for o in bpy.context.scene.objects if o.type == "MESH" and any(m.type == "ARMATURE" and m.object == armature for m in o.modifiers)]
fallback = bpy.data.materials.get("DZ_ActorPreview") or bpy.data.materials.new("DZ_ActorPreview")
⋮----
data = bpy.data.lights.new(name, "AREA")
⋮----
obj = bpy.data.objects.new(name, data)
⋮----
def _parse_vec(name: str, default: tuple[float, float, float]) -> Vector
⋮----
raw = os.environ.get(name, "").strip()
⋮----
parts = [float(v) for v in raw.split(",")]
⋮----
def _material(name, color, metallic, roughness, emission=None)
⋮----
mat = bpy.data.materials.get(name) or bpy.data.materials.new(name)
⋮----
bsdf = mat.node_tree.nodes.get("Principled BSDF")
⋮----
slot = bsdf.inputs.get("Emission Color") or bsdf.inputs.get("Emission")
⋮----
strength = bsdf.inputs.get("Emission Strength")
⋮----
def _weapon_cube(name, loc, scale, material)
⋮----
obj = bpy.context.object
⋮----
def attach_configured_weapon(armature)
⋮----
style = os.environ.get("DZ_WEAPON_STYLE", "").strip()
⋮----
bone_name = os.environ.get("DZ_WEAPON_BONE", "").strip()
⋮----
available = ", ".join(b.name for b in armature.data.bones)
⋮----
dark = _material("DZ_Weapon_Gunmetal", (0.018, 0.024, 0.032), .85, .24)
body = _material("DZ_Weapon_Body", (0.035, 0.08, 0.12), .68, .28)
accent = _material("DZ_Weapon_Accent", (0.02, .42, .58), .32, .20, (0.02, .38, .58))
parts = [
⋮----
weapon = bpy.context.object
⋮----
pose_bone = armature.pose.bones[bone_name]
⋮----
grip_world = armature.matrix_world @ pose_bone.matrix.translation
forward = _parse_vec("DZ_WEAPON_FORWARD", (0.0, -1.0, 0.0)).normalized()
offset = _parse_vec("DZ_WEAPON_GRIP_OFFSET", (0.0, 0.0, 0.0))
desired = forward.to_track_quat("X", "Z").to_matrix().to_4x4()
⋮----
def _git_blob_sha(data: bytes) -> str
⋮----
def _download_defensive_prop(work_root: Path) -> Path | None
⋮----
url = os.environ.get("DZ_DEFENSIVE_PROP_URL", "").strip()
⋮----
filename = os.environ.get("DZ_DEFENSIVE_PROP_FILENAME", "").strip() or "defensive-prop.fbx"
out_dir = work_root / "defensive-prop"
⋮----
path = out_dir / filename
⋮----
data = response.read()
⋮----
expected_size = int(os.environ.get("DZ_DEFENSIVE_PROP_SIZE", "0") or 0)
⋮----
expected_blob = os.environ.get("DZ_DEFENSIVE_PROP_BLOB", "").strip()
actual_blob = _git_blob_sha(data)
⋮----
actual_sha256 = hashlib.sha256(data).hexdigest()
expected_sha256 = os.environ.get("DZ_DEFENSIVE_PROP_SHA256", "").strip()
⋮----
report = {
⋮----
def _import_prop(path: Path)
⋮----
before = set(bpy.context.scene.objects)
suffix = path.suffix.lower()
⋮----
imported = [o for o in bpy.context.scene.objects if o not in before]
meshes = [o for o in imported if o.type == "MESH"]
⋮----
prop = bpy.context.object
⋮----
def attach_configured_defensive_prop(armature, work_root: Path)
⋮----
path = _download_defensive_prop(work_root)
⋮----
bone_name = os.environ.get("DZ_DEFENSIVE_PROP_BONE", "").strip()
⋮----
prop = _import_prop(path)
⋮----
# Deterministic readable material. Source geometry remains authoritative while
# avoiding broken external texture references in FBX-only CI renders.
steel = _material("DZ_Shield_Steel", (0.08, 0.13, 0.20), .72, .24)
accent = _material("DZ_Shield_Accent", (0.03, 0.30, 0.48), .45, .22)
⋮----
scale = float(os.environ.get("DZ_DEFENSIVE_PROP_SCALE", "1") or 1.0)
⋮----
forward = _parse_vec("DZ_DEFENSIVE_PROP_FORWARD", (0.0, -1.0, 0.0)).normalized()
offset = _parse_vec("DZ_DEFENSIVE_PROP_GRIP_OFFSET", (0.0, 0.0, 0.0))
rotation = _parse_vec("DZ_DEFENSIVE_PROP_ROTATION", (0.0, 0.0, 0.0))
desired = forward.to_track_quat("Y", "Z").to_matrix().to_4x4()
desired = desired @ Euler(tuple(math.radians(v) for v in rotation), "XYZ").to_matrix().to_4x4()
⋮----
def set_action(armature, action_name: str)
⋮----
action = bpy.data.actions.get(action_name)
⋮----
matches = [a for a in bpy.data.actions if a.name.lower() == action_name.lower()]
⋮----
available = ", ".join(sorted(a.name for a in bpy.data.actions))
⋮----
action = matches[0]
⋮----
def sample_frames(action, wanted: int) -> list[int]
⋮----
span = max(1, end_i - start_i + 1)
⋮----
def main()
⋮----
args = parse_args()
action_map = {"idle": args.idle_action, "run": args.run_action, "attack": args.attack_action, "hit": args.hit_action, "death": args.death_action}
engine = configure_scene(args)
armature = find_armature()
weapon = attach_configured_weapon(armature)
defensive_prop = attach_configured_defensive_prop(armature, args.output.parent)
⋮----
cam = ensure_camera(args)
root = args.output.resolve(); root.mkdir(parents=True, exist_ok=True)
target = Vector((0.0, 0.0, args.target_height))
⋮----
rendered = 0
⋮----
radians = math.radians(degrees)
⋮----
action = set_action(armature, action_map[animation])
frames = sample_frames(action, wanted)
out_dir = root / animation / direction; out_dir.mkdir(parents=True, exist_ok=True)
```

## File: blender/repair_rex_weights.py
```python
#!/usr/bin/env python3
"""Repair Rex auto-rig weight leaks before animation rendering.

Rex is exported as a triangle-soup style mesh, so topology-based cape isolation
is unreliable. The repair therefore combines two deterministic spatial gates:
1. remove arm weights from central vertices far from the arm chain;
2. lock the back-central cape slab to torso bones so shoulder motion cannot
   stretch it into the arms.
"""
⋮----
ARM_CHAINS = {
TORSO_BONES = ["Pelvis", "Spine1", "Spine2", "Spine3", "Neck"]
⋮----
def parse_args() -> argparse.Namespace
⋮----
argv = sys.argv
argv = argv[argv.index("--") + 1 :] if "--" in argv else []
p = argparse.ArgumentParser()
⋮----
def reset_scene() -> None
⋮----
def import_asset(path: Path) -> None
⋮----
suffix = path.suffix.lower()
⋮----
def get_armature_and_meshes()
⋮----
arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
⋮----
arm = arms[0]
meshes = [
⋮----
def world_bounds(meshes)
⋮----
pts = [obj.matrix_world @ Vector(c) for obj in meshes for c in obj.bound_box]
lo = Vector((min(p.x for p in pts), min(p.y for p in pts), min(p.z for p in pts)))
hi = Vector((max(p.x for p in pts), max(p.y for p in pts), max(p.z for p in pts)))
⋮----
def segment_distance(p: Vector, a: Vector, b: Vector) -> float
⋮----
ab = b - a
denom = ab.length_squared
⋮----
t = max(0.0, min(1.0, (p - a).dot(ab) / denom))
⋮----
def bone_segment_world(arm, name: str)
⋮----
bone = arm.data.bones.get(name)
⋮----
def chain_distance(p: Vector, arm, names: list[str]) -> float
⋮----
vals = []
⋮----
seg = bone_segment_world(arm, name)
⋮----
def nearest_torso_bone(p: Vector, arm) -> str
⋮----
best_name = None
best_dist = math.inf
⋮----
d = segment_distance(p, seg[0], seg[1])
⋮----
def group_weight(vertex, group_index: int) -> float
⋮----
def ensure_group(obj, name: str)
⋮----
def replace_all_weights(obj, vertex, target_group) -> None
⋮----
def repair_mesh(obj, arm, global_lo, global_hi, args)
⋮----
height = global_hi.z - global_lo.z
width = global_hi.x - global_lo.x
depth = global_hi.y - global_lo.y
center_x = (global_lo.x + global_hi.x) * 0.5
center_y = (global_lo.y + global_hi.y) * 0.5
arm_limit = height * args.arm_distance
central_limit = width * args.central_x
cape_x_limit = width * args.cape_half_width
cape_y_cut = center_y + depth * args.cape_back
⋮----
arm_groups = {
torso_groups = {
⋮----
changed_vertices = set()
removed_weight = 0.0
suspicious_before = 0
cross_side_removed = 0.0
⋮----
# Pass 1: remove obvious arm influence leaks.
⋮----
p = obj.matrix_world @ v.co
x_rel = p.x - center_x
⋮----
entries = [(n, g) for n, g in arm_groups[side] if g is not None]
weights = [(n, g, group_weight(v, g.index)) for n, g in entries]
total_arm = sum(w for _, _, w in weights)
⋮----
d_arm = chain_distance(p, arm, names)
wrong_side = (side == "L" and x_rel < -central_limit) or (side == "R" and x_rel > central_limit)
leak = d_arm > arm_limit and abs(x_rel) <= central_limit
⋮----
target = torso_groups[nearest_torso_bone(p, arm)]
transfer = 0.0
⋮----
existing = group_weight(v, target.index)
⋮----
# Pass 2: Rex's cape is a back-central slab. Lock it to the torso so arm
# bones cannot pull cape triangles outward. Limit the vertical range to
# below the helmet and above the boots.
cape_vertices = 0
⋮----
z_norm = (p.z - global_lo.z) / height
⋮----
target_name = nearest_torso_bone(p, arm)
⋮----
# Normalize edited non-cape vertices. Cape vertices are already exactly 1.
⋮----
v = obj.data.vertices[vidx]
weighted = [(g.group, g.weight) for g in v.groups if g.weight > 0]
total = sum(w for _, w in weighted)
⋮----
suspicious_after = 0
⋮----
total = sum(
⋮----
def export_glb(path: Path) -> None
⋮----
def main() -> None
⋮----
args = parse_args()
⋮----
reports = [repair_mesh(m, arm, lo, hi, args) for m in meshes]
residual = sum(r["suspicious_after"] for r in reports)
payload = {
```

## File: blender/style_rex_materials.py
```python
#!/usr/bin/env python3
"""Apply a deterministic phone-scale Rex palette to an imported rigged actor.

The Meshy Lite source has no UV/material data. This pass therefore uses narrow,
stable spatial regions rather than broad body bands. The goal is readability at
96px: dark navy/black armor as the dominant mass, a small cyan visor/energy cue,
very restrained orange accents, and a dark cape. No geometry or weights change.
"""
⋮----
def parse()
⋮----
a=sys.argv; a=a[a.index('--')+1:] if '--' in a else []
p=argparse.ArgumentParser()
⋮----
def mat(name,color,metallic=.0,rough=.5,emit=None,emit_strength=.0)
⋮----
m=bpy.data.materials.new(name)
⋮----
bs=m.node_tree.nodes.get('Principled BSDF')
⋮----
def main()
⋮----
a=parse()
⋮----
meshes=[o for o in bpy.context.scene.objects if o.type=='MESH' and any(x.type=='ARMATURE' for x in o.modifiers)]
⋮----
mats=[
⋮----
pts=[o.matrix_world@Vector(c) for c in o.bound_box]
lo=Vector(tuple(min(p[i] for p in pts) for i in range(3)))
hi=Vector(tuple(max(p[i] for p in pts) for i in range(3)))
h=hi.z-lo.z; w=hi.x-lo.x; d=hi.y-lo.y
cx=(lo.x+hi.x)/2; cy=(lo.y+hi.y)/2
⋮----
p=o.matrix_world@poly.center
zn=(p.z-lo.z)/h
xn=abs(p.x-cx)/w
yn=(p.y-cy)/d
idx=0
⋮----
# Cape: keep it darker than the armor but not pitch black.
⋮----
idx=2
# Cyan visor: deliberately narrow, front-most and high on the helmet.
⋮----
idx=3
# Small chest energy marker only; avoid turning the breastplate white/cyan.
⋮----
# Restrained orange: tiny outer-shoulder tabs and shin tabs on the front half.
⋮----
idx=4
# Dark undersuit/joints: elbows, inner legs, waist gaps.
⋮----
idx=1
```

## File: blender/validate_rex_weapon_visibility.py
```python
#!/usr/bin/env python3
"""Validate that Rex's separate rifle is actually visible in sprite camera views.

Run inside Blender on the animated .blend after the rifle has been attached.
The check projects the rifle's evaluated mesh into the same orthographic camera
used by render_actor_8dir.py and verifies that representative poses keep a
meaningful weapon footprint on canvas. This catches successful-but-invisible
bone attachments before spending time judging 232 flattened PNGs manually.
"""
⋮----
DIRECTIONS = [
ANIMATIONS = ("idle", "run", "attack", "hit", "death")
⋮----
def parse_args() -> argparse.Namespace
⋮----
argv = sys.argv
argv = argv[argv.index("--") + 1 :] if "--" in argv else []
p = argparse.ArgumentParser()
⋮----
def find_armature()
⋮----
arms = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
⋮----
def find_rifle()
⋮----
exact = bpy.data.objects.get("Rex_Rifle")
⋮----
matches = [o for o in bpy.context.scene.objects if o.type == "MESH" and "rifle" in o.name.lower()]
⋮----
def ensure_camera(args: argparse.Namespace)
⋮----
cam = bpy.data.objects.get("DZ_Camera")
⋮----
data = bpy.data.cameras.new("DZ_Camera")
cam = bpy.data.objects.new("DZ_Camera", data)
⋮----
def look_at(obj, point: Vector) -> None
⋮----
direction = point - obj.location
⋮----
def set_action(armature, name: str)
⋮----
action = bpy.data.actions.get(name)
⋮----
matches = [a for a in bpy.data.actions if a.name.lower() == name.lower()]
⋮----
action = matches[0]
⋮----
def representative_frame(action) -> int
⋮----
def projected_metrics(scene, cam, obj, size: int)
⋮----
depsgraph = bpy.context.evaluated_depsgraph_get()
evaluated = obj.evaluated_get(depsgraph)
mesh = evaluated.to_mesh()
⋮----
coords = []
⋮----
world = evaluated.matrix_world @ vertex.co
ndc = world_to_camera_view(scene, cam, world)
⋮----
xs = [p[0] for p in coords]
ys = [p[1] for p in coords]
visible = [p for p in coords if 0.0 <= p[0] <= 1.0 and 0.0 <= p[1] <= 1.0 and p[2] >= 0.0]
width = max(xs) - min(xs)
height = max(ys) - min(ys)
⋮----
def main() -> None
⋮----
args = parse_args()
scene = bpy.context.scene
⋮----
arm = find_armature()
rifle = find_rifle()
cam = ensure_camera(args)
target = Vector((0.0, 0.0, args.target_height))
⋮----
samples = []
failures = []
⋮----
radians = math.radians(degrees)
⋮----
action = set_action(arm, animation)
frame = representative_frame(action)
⋮----
metrics = projected_metrics(scene, cam, rifle, args.size)
ok = (
sample = {
⋮----
report = {
```

## File: blender/validate_rig.py
```python
#!/usr/bin/env python3
"""Validate a rigged Rex asset in headless Blender and render neutral previews."""
⋮----
def parse_args() -> argparse.Namespace
⋮----
argv = sys.argv
argv = argv[argv.index("--") + 1 :] if "--" in argv else []
p = argparse.ArgumentParser()
⋮----
def reset_scene() -> None
⋮----
def import_asset(path: Path) -> None
⋮----
suffix = path.suffix.lower()
⋮----
def world_bounds(meshes)
⋮----
points = [obj.matrix_world @ Vector(corner) for obj in meshes for corner in obj.bound_box]
mins = Vector((min(p.x for p in points), min(p.y for p in points), min(p.z for p in points)))
maxs = Vector((max(p.x for p in points), max(p.y for p in points), max(p.z for p in points)))
⋮----
def look_at(obj, target: Vector) -> None
⋮----
def ensure_material(meshes) -> None
⋮----
fallback = bpy.data.materials.get("DZ_RigPreview") or bpy.data.materials.new("DZ_RigPreview")
⋮----
def add_area(name: str, location: Vector, energy: float, size: float, target: Vector)
⋮----
data = bpy.data.lights.new(name, "AREA")
⋮----
obj = bpy.data.objects.new(name, data)
⋮----
def select_eevee_engine(scene) -> str
⋮----
def render_previews(meshes, output: Path, size: int) -> tuple[list[str], str]
⋮----
scene = bpy.context.scene
engine = select_eevee_engine(scene)
⋮----
center = (mins + maxs) * 0.5
height = max(0.01, maxs.z - mins.z)
radius = max(maxs.x - mins.x, maxs.y - mins.y, height) * 1.8
⋮----
cam_data = bpy.data.cameras.new("DZ_RigValidationCamera")
⋮----
cam = bpy.data.objects.new("DZ_RigValidationCamera", cam_data)
⋮----
preview_dir = output / "preview"
⋮----
results = []
⋮----
angle = math.radians(degrees)
⋮----
path = preview_dir / f"rex_{name}.png"
⋮----
def main() -> None
⋮----
args = parse_args()
⋮----
all_meshes = [o for o in bpy.context.scene.objects if o.type == "MESH"]
armatures = [o for o in bpy.context.scene.objects if o.type == "ARMATURE"]
⋮----
armature = armatures[0]
# GLTF import may create editor-only custom-shape meshes for bones. They are
# not Rex geometry. Validate and render only meshes actually deformed by the
# single imported armature.
meshes = [
⋮----
bones = list(armature.data.bones)
triangles = 0
total_vertices = 0
weighted_vertices = 0
vertex_groups = 0
⋮----
ratio = weighted_vertices / total_vertices if total_vertices else 0.0
⋮----
report = {
report_path = args.output / "rig-report.json"
```

## File: environment/generate_quarantine_yard_candidate.py
```python
#!/usr/bin/env python3
"""Generate a deterministic Quarantine Yard environment candidate pack.

This is an authored procedural candidate, not final release art. It exists to move
M3 from zero environment sources to a reviewable, reproducible first-biome pack
without mislabeling generated references as final.
"""
⋮----
SIZE = 512
BG = (18, 29, 34, 255)
STEEL = (55, 72, 78, 255)
STEEL_DARK = (29, 41, 46, 255)
STEEL_LIGHT = (90, 108, 112, 255)
RED = (165, 42, 38, 255)
RED_BRIGHT = (224, 62, 48, 255)
CYAN = (76, 202, 218, 255)
DIRTY = (70, 66, 54, 255)
⋮----
SLOTS = (
⋮----
def clamp(v: int) -> int
⋮----
def rgba_noise(base, strength: int, seed: int, *, periodic: bool = False) -> Image.Image
⋮----
img = Image.new("RGBA", (SIZE, SIZE))
px = img.load()
rnd = random.Random(seed)
phases = [rnd.random() * math.tau for _ in range(6)]
⋮----
n = (
⋮----
n = rnd.uniform(-strength, strength)
⋮----
def vignette(img: Image.Image, amount: float = .18) -> None
⋮----
cx = cy = SIZE / 2
maxd = math.hypot(cx, cy)
⋮----
d = math.hypot(x - cx, y - cy) / maxd
f = 1.0 - amount * d * d
⋮----
def add_panel_seams(img: Image.Image, spacing: int, alpha: int = 80) -> None
⋮----
d = ImageDraw.Draw(img, "RGBA")
⋮----
def add_scuffs(img: Image.Image, seed: int, count: int = 45) -> None
⋮----
x = rnd.randrange(SIZE)
y = rnd.randrange(SIZE)
length = rnd.randrange(8, 52)
angle = rnd.random() * math.tau
x2 = int(x + math.cos(angle) * length)
y2 = int(y + math.sin(angle) * length)
shade = rnd.choice(((8, 13, 15, 32), (120, 118, 96, 20), (145, 55, 43, 14)))
⋮----
def floor_variant(seed: int, base=(43, 56, 61, 255), seam=128) -> Image.Image
⋮----
img = rgba_noise(base, 15, seed, periodic=True)
⋮----
def floor_concrete_a() -> Image.Image
⋮----
img = floor_variant(11, (41, 53, 58, 255), 128)
⋮----
def floor_concrete_b() -> Image.Image
⋮----
img = floor_variant(17, (36, 48, 54, 255), 96)
⋮----
def floor_concrete_c() -> Image.Image
⋮----
img = floor_variant(23, (47, 57, 59, 255), 160)
⋮----
rnd = random.Random(23)
⋮----
rr = rnd.randrange(10, 34)
⋮----
def floor_hazard() -> Image.Image
⋮----
img = floor_variant(29, (32, 43, 47, 255), 128)
overlay = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
d = ImageDraw.Draw(overlay, "RGBA")
band = 32
⋮----
overlay = overlay.filter(ImageFilter.GaussianBlur(.35))
img = Image.alpha_composite(img, overlay)
⋮----
def transparent() -> Image.Image
⋮----
def glow_layer(color, center, radii) -> Image.Image
⋮----
layer = transparent()
d = ImageDraw.Draw(layer, "RGBA")
⋮----
def crack() -> Image.Image
⋮----
img = transparent()
⋮----
rnd = random.Random(41)
origin = (256, 260)
⋮----
pts = [origin]
angle = branch * (math.tau / 11) + rnd.uniform(-.16, .16)
length = rnd.randrange(85, 210)
steps = rnd.randrange(5, 9)
⋮----
r = length * s / steps
⋮----
def blood() -> Image.Image
⋮----
rnd = random.Random(47)
⋮----
x = int(rnd.gauss(256, 72))
y = int(rnd.gauss(256, 56))
rx = rnd.randrange(10, 52)
ry = rnd.randrange(6, 36)
⋮----
x = rnd.randrange(100, 412)
y = rnd.randrange(110, 402)
r = rnd.randrange(3, 10)
⋮----
def scorch() -> Image.Image
⋮----
center = (256, 256)
⋮----
rnd = random.Random(53)
⋮----
ang = rnd.random() * math.tau
r = rnd.randrange(35, 165)
x = int(center[0] + math.cos(ang) * r)
y = int(center[1] + math.sin(ang) * r * .72)
rr = rnd.randrange(6, 22)
⋮----
def premium_finish(img: Image.Image, slot: str, seed: int) -> Image.Image
⋮----
"""Apply deterministic phone-scale material finishing without changing silhouette intent."""
img = img.convert("RGBA")
⋮----
graded = ImageEnhance.Contrast(img).enhance(1.10)
graded = ImageEnhance.Color(graded).enhance(1.04)
graded = ImageEnhance.Sharpness(graded).enhance(1.20)
# Fine periodic speckle so floors retain material read after 2x downsample.
px = graded.load()
⋮----
x = rnd.randrange(SIZE); y = rnd.randrange(SIZE)
⋮----
delta = rnd.choice((-5, -3, -2, 2, 3, 5))
⋮----
alpha = img.getchannel("A")
⋮----
# Contact/ambient occlusion under transparent objects and decals.
ao_mask = alpha.filter(ImageFilter.GaussianBlur(13))
ao = Image.new("RGBA", img.size, (0, 0, 0, 0))
shifted = Image.new("L", img.size, 0)
⋮----
out = Image.alpha_composite(ao, img)
⋮----
# Crisp bevel read: dark outer edge + cool upper edge highlight.
expanded = alpha.filter(ImageFilter.MaxFilter(9))
contracted = alpha.filter(ImageFilter.MinFilter(7))
outer = ImageChops.subtract(expanded, alpha)
inner = ImageChops.subtract(alpha, contracted)
⋮----
rim_dark = Image.new("RGBA", img.size, (7, 12, 14, 0))
⋮----
out = Image.alpha_composite(rim_dark, out)
⋮----
highlight = Image.new("RGBA", img.size, (150, 178, 181, 0))
# Bias highlight toward the upper-left by masking with a simple directional ramp.
ramp = Image.new("L", img.size)
rp = ramp.load()
⋮----
hi_mask = ImageChops.multiply(inner, ramp)
⋮----
out = Image.alpha_composite(out, highlight)
⋮----
# Subtle material grain clipped to the original silhouette.
grain = Image.new("RGBA", img.size, (0, 0, 0, 0))
gp = grain.load()
⋮----
v = rnd.choice((-1, 1))
⋮----
out = Image.alpha_composite(out, grain)
⋮----
out = ImageEnhance.Contrast(out).enhance(1.08)
out = ImageEnhance.Sharpness(out).enhance(1.28)
⋮----
def metallic_shadow(img: Image.Image, box, alpha=110)
⋮----
shadow = transparent()
d = ImageDraw.Draw(shadow, "RGBA")
⋮----
def barrier() -> Image.Image
⋮----
img = metallic_shadow(img, (70, 170, 442, 340))
⋮----
def debris(seed: int, warm=False) -> Image.Image
⋮----
pieces=[]
⋮----
cx=rnd.randrange(120,392); cy=rnd.randrange(125,390)
rr=rnd.randrange(18,52)
pts=[]
⋮----
a=k*math.tau/rnd.randrange(4,7)+rnd.uniform(-.25,.25)
rad=rr*rnd.uniform(.55,1.2)
⋮----
shadow=transparent(); sd=ImageDraw.Draw(shadow,"RGBA")
⋮----
img=Image.alpha_composite(img,shadow.filter(ImageFilter.GaussianBlur(7)))
d=ImageDraw.Draw(img,"RGBA")
⋮----
base=(83,74,55,255) if warm else ((55,66,69,255) if i%2==0 else (74,78,72,255))
⋮----
def wall(seed: int, red_panel=False) -> Image.Image
⋮----
img=transparent()
img=metallic_shadow(img,(60,120,452,385),125)
⋮----
def crate() -> Image.Image
⋮----
img=metallic_shadow(img,(120,110,392,390),125)
⋮----
def beacon() -> Image.Image
⋮----
img=Image.alpha_composite(img,glow_layer(CYAN,(256,245),((120,18),(80,28),(48,48))))
⋮----
rad=math.radians(a)
x=256+math.cos(rad)*94; y=246+math.sin(rad)*94
⋮----
GENERATORS = {
⋮----
def main() -> int
⋮----
p=argparse.ArgumentParser(description=__doc__)
⋮----
args=p.parse_args()
⋮----
produced=[]
⋮----
path=args.output/(slot+".png")
⋮----
img=GENERATORS[slot]().convert("RGBA")
img=premium_finish(img, slot, 1000 + len(produced) * 97)
⋮----
# Contract requires fully opaque floors.
alpha=Image.new("L",img.size,255)
⋮----
manifest={
```

## File: environment/pack_environment_art.py
```python
#!/usr/bin/env python3
"""Pack one authored biome environment set into a deterministic libGDX atlas page.

Source layout:
  art_sources/environment/<biome>/<slot path>.png

Example:
  art_sources/environment/cinder_foundry/floor/concrete_a.png
  art_sources/environment/cinder_foundry/prop/crate_a.png

The source master is expected to be 512x512. This tool downsamples each slot to
256x256, writes one 1024x1024 RGBA page (14 occupied cells in a 4x4 grid), an
atlas fragment and a QA manifest. It does not mutate game.atlas automatically.
"""
⋮----
ROOT = Path(__file__).resolve().parents[2]
CONTRACT = ROOT / "config" / "environment-art-contract.json"
DEFAULT_SOURCE = ROOT / "art_sources" / "environment"
DEFAULT_BUILD = ROOT / "build" / "environment_art"
MASTER = 512
CELL = 256
COLUMNS = 4
⋮----
def sha256(path: Path) -> str
⋮----
h = hashlib.sha256()
⋮----
def load_contract() -> dict
⋮----
def alpha_bbox(image: Image.Image)
⋮----
def normalize(source: Path, slot: dict) -> tuple[Image.Image, dict]
⋮----
image = raw.convert("RGBA")
⋮----
alpha = image.getchannel("A")
extrema = alpha.getextrema()
bbox = alpha_bbox(image)
⋮----
coverage = (bbox[2] - bbox[0]) * (bbox[3] - bbox[1]) / float(MASTER * MASTER)
⋮----
runtime = image.resize((CELL, CELL), Image.Resampling.LANCZOS)
⋮----
def atlas_fragment(page_name: str, biome: str, slots: list[dict]) -> str
⋮----
lines = [
⋮----
key = f"environment/{biome}/{slot['path']}"
⋮----
def main() -> int
⋮----
parser = argparse.ArgumentParser(description=__doc__)
⋮----
args = parser.parse_args()
⋮----
contract = load_contract()
biome_ids = {item["id"] for item in contract["biomes"]}
⋮----
slots = contract["slots"]
page = Image.new("RGBA", (COLUMNS * CELL, COLUMNS * CELL), (0, 0, 0, 0))
records = []
⋮----
source = args.source_root / args.biome / (slot["path"] + ".png")
⋮----
page_name = f"environment-{args.biome}.png"
page_path = args.output / page_name
fragment_path = args.output / f"environment-{args.biome}.atlas.txt"
manifest_path = args.output / f"environment-{args.biome}.manifest.json"
⋮----
manifest = {
```

## File: environment/test_upsert_environment_atlas.py
```python
#!/usr/bin/env python3
⋮----
class EnvironmentAtlasUpsertTest(unittest.TestCase)
⋮----
def test_appends_new_page_without_touching_existing_page(self)
⋮----
existing = """actor.png
fragment = """environment-quarantine_yard.png
result = target.upsert_atlas_text(existing, fragment)
⋮----
def test_replaces_existing_environment_page_instead_of_duplicating_it(self)
⋮----
fragment = """environment-null_sector.png
```

## File: environment/test_validate_environment_art_contract.py
```python
#!/usr/bin/env python3
⋮----
class EnvironmentArtContractTest(unittest.TestCase)
⋮----
def contract(self)
⋮----
def test_expected_matrix_is_seventy_unique_regions(self)
⋮----
keys = target.expected_keys(self.contract())
⋮----
def test_atlas_coverage_ignores_page_declarations(self)
⋮----
contract = self.contract()
key = target.expected_keys(contract)[0]
text = f"""environment-b0.png
⋮----
atlas = Path(directory) / "game.atlas"
⋮----
def test_load_contract_rejects_duplicate_slots(self)
⋮----
path = Path(directory) / "contract.json"
```

## File: environment/upsert_environment_atlas.py
```python
#!/usr/bin/env python3
"""Install or replace one generated environment atlas page in assets/art/game.atlas."""
⋮----
ROOT = Path(__file__).resolve().parents[2]
DEFAULT_ATLAS = ROOT / "assets" / "art" / "game.atlas"
⋮----
def is_page_line(line: str) -> bool
⋮----
stripped = line.strip()
⋮----
def page_blocks(text: str) -> tuple[list[str], list[list[str]]]
⋮----
lines = text.splitlines()
starts = [i for i, line in enumerate(lines) if is_page_line(line)]
⋮----
prefix = lines[: starts[0]]
blocks: list[list[str]] = []
⋮----
end = starts[index + 1] if index + 1 < len(starts) else len(lines)
block = lines[start:end]
⋮----
def upsert_atlas_text(existing: str, fragment: str) -> str
⋮----
fragment_lines = fragment.strip().splitlines()
⋮----
page_name = fragment_lines[0].strip()
⋮----
kept = [block for block in blocks if not block or block[0].strip() != page_name]
⋮----
out: list[str] = []
⋮----
def main() -> int
⋮----
parser = argparse.ArgumentParser(description=__doc__)
⋮----
args = parser.parse_args()
⋮----
fragment = args.fragment.read_text(encoding="utf-8")
first = fragment.strip().splitlines()[0].strip()
⋮----
updated = upsert_atlas_text(args.atlas.read_text(encoding="utf-8"), fragment)
destination = args.atlas.parent / args.page.name
```

## File: environment/validate_environment_art_contract.py
```python
#!/usr/bin/env python3
"""Validate the Deadline Zero biome environment-art contract and report atlas coverage."""
⋮----
ROOT = Path(__file__).resolve().parents[2]
DEFAULT_CONTRACT = ROOT / "config" / "environment-art-contract.json"
DEFAULT_ATLAS = ROOT / "assets" / "art" / "game.atlas"
EXPECTED_BIOMES = 5
EXPECTED_SLOTS = 14
⋮----
def load_contract(path: Path) -> dict
⋮----
data = json.loads(path.read_text(encoding="utf-8"))
biomes = data.get("biomes")
slots = data.get("slots")
⋮----
biome_ids = [item.get("id") for item in biomes]
⋮----
slot_paths = [item.get("path") for item in slots]
⋮----
kind = slot.get("kind")
⋮----
def expected_keys(contract: dict) -> list[str]
⋮----
def atlas_region_names(path: Path) -> set[str]
⋮----
names: set[str] = set()
⋮----
stripped = raw.strip()
⋮----
lower = stripped.lower()
⋮----
def coverage(contract: dict, atlas: Path) -> tuple[list[str], list[str]]
⋮----
expected = expected_keys(contract)
regions = atlas_region_names(atlas) if atlas.is_file() else set()
present = [key for key in expected if key in regions]
missing = [key for key in expected if key not in regions]
⋮----
def main() -> int
⋮----
parser = argparse.ArgumentParser(description=__doc__)
⋮----
args = parser.parse_args()
⋮----
contract = load_contract(args.contract)
⋮----
payload = {
```

## File: perf/compare_android_benchmark.py
```python
#!/usr/bin/env python3
⋮----
MAX_AVG_FPS_DROP = 0.15
MAX_P95_INCREASE = 0.15
MAX_P99_INCREASE = 0.20
MAX_JANK_ABSOLUTE_INCREASE = 0.05
⋮----
COMPARABLE_THERMAL = {"UNKNOWN", "NORMAL", "LIGHT"}
⋮----
def load(path: str) -> dict
⋮----
def finite_positive(value) -> bool
⋮----
def validate(data: dict) -> None
⋮----
required = {
missing = required - data.keys()
⋮----
def comparable(base: dict, current: dict) -> tuple[bool, str]
⋮----
def compare(base: dict, current: dict) -> dict
⋮----
result = {
⋮----
avg_drop = (base["averageFps"] - current["averageFps"]) / base["averageFps"]
p95_inc = (current["p95FrameMs"] - base["p95FrameMs"]) / base["p95FrameMs"]
p99_inc = (current["p99FrameMs"] - base["p99FrameMs"]) / base["p99FrameMs"]
jank_inc = current["jankRatio"] - base["jankRatio"]
⋮----
latency_regression = p95_inc > MAX_P95_INCREASE or p99_inc > MAX_P99_INCREASE
jank_regression = jank_inc > MAX_JANK_ABSOLUTE_INCREASE
⋮----
# SwiftShader/host scheduling can depress average FPS while percentile frame times
# remain unchanged. Treat average FPS as a corroborating signal, not a standalone gate.
⋮----
def main() -> int
⋮----
parser = argparse.ArgumentParser()
⋮----
args = parser.parse_args()
⋮----
result = compare(load(args.baseline), load(args.current))
rendered = json.dumps(result, indent=2, sort_keys=True)
```

## File: perf/test_compare_android_benchmark.py
```python
ROOT = pathlib.Path(__file__).resolve().parents[2]
MODULE_PATH = ROOT / "tools" / "perf" / "compare_android_benchmark.py"
spec = importlib.util.spec_from_file_location("compare_android_benchmark", MODULE_PATH)
mod = importlib.util.module_from_spec(spec)
⋮----
def sample(**overrides)
⋮----
data = {
⋮----
class CompareAndroidBenchmarkTest(unittest.TestCase)
⋮----
def test_equal_runs_pass(self)
⋮----
result = mod.compare(sample(), sample())
⋮----
def test_detects_relative_regressions(self)
⋮----
current = sample(averageFps=46.0, p95FrameMs=24.0, p99FrameMs=32.0, jankRatio=0.16)
result = mod.compare(sample(), current)
⋮----
def test_average_fps_drop_alone_is_advisory(self)
⋮----
current = sample(averageFps=42.0, p95FrameMs=19.0, p99FrameMs=24.0, jankRatio=0.08)
⋮----
def test_thermal_pressure_skips_comparison(self)
⋮----
result = mod.compare(sample(), sample(thermalLevel="SEVERE"))
⋮----
def test_different_target_skips_comparison(self)
⋮----
result = mod.compare(sample(), sample(targetFps=90))
⋮----
def test_materially_lighter_enemy_workload_skips_comparison(self)
⋮----
result = mod.compare(sample(), sample(activeEnemies=30))
⋮----
def test_materially_lighter_projectile_workload_skips_comparison(self)
⋮----
baseline = sample(scenario="horde-160-projectile-180", activeEnemies=160, activeProjectiles=180)
current = sample(scenario="horde-160-projectile-180", activeEnemies=160, activeProjectiles=120)
result = mod.compare(baseline, current)
⋮----
def test_zero_projectile_baseline_remains_comparable(self)
⋮----
baseline = sample(activeProjectiles=0)
current = sample(activeProjectiles=0)
⋮----
def test_legacy_baseline_schema_skips_instead_of_failing(self)
⋮----
baseline = {
result = mod.compare(baseline, sample())
```

## File: sprites/assemble_actor_sheet.py
```python
#!/usr/bin/env python3
"""Assemble normalized directional actor frames into the canonical source-sheet layout.

Input layout:
  <input>/<motion>/<direction>/<motion>_<index>.png

Output layout:
  rows: n, ne, e, se, s, sw, w, nw
  columns: idle, run, attack, hit, death

The resulting sheet is deterministic and is intended to round-trip through
tools/slice_sprite_sheet.py without changing any pixel.
"""
⋮----
DIRECTIONS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
MOTIONS = ("idle", "run", "attack", "hit", "death")
STANDARD_COUNTS = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}
⋮----
def parse_args() -> argparse.Namespace
⋮----
p = argparse.ArgumentParser(description=__doc__)
⋮----
def sha256(path: Path) -> str
⋮----
h = hashlib.sha256()
⋮----
def main() -> int
⋮----
args = parse_args()
⋮----
counts = dict(STANDARD_COUNTS)
⋮----
columns = sum(counts[m] for m in MOTIONS)
width = columns * args.cell
height = len(DIRECTIONS) * args.cell
sheet = Image.new("RGBA", (width, height), (0, 0, 0, 0))
⋮----
inputs: list[dict] = []
⋮----
column = 0
⋮----
source = args.input / motion / direction / f"{motion}_{index:02d}.png"
⋮----
rgba = image.convert("RGBA")
⋮----
manifest = {
```

## File: sprites/audit_final_art_status.py
```python
#!/usr/bin/env python3
"""Report final-art maturity from published manifests plus production-contract evidence."""
⋮----
ROOT = Path(__file__).resolve().parents[2]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
CONTRACTS = ROOT / "config" / "actor-production-contracts.json"
ART = ROOT / "assets" / "art"
GATES = (
⋮----
def parse_args()
⋮----
p = argparse.ArgumentParser(description=__doc__)
⋮----
def manifest_path(actor_id: str) -> Path
⋮----
direct = ART / f"{actor_id}-manifest.json"
⋮----
fallback = ART / "boss-manifest.json"
⋮----
def contract_key(actor_id: str) -> str
⋮----
def main() -> int
⋮----
args = parse_args()
layout = json.loads(LAYOUT.read_text(encoding="utf-8"))
contracts = json.loads(CONTRACTS.read_text(encoding="utf-8")).get("actors", {})
rows = []
⋮----
actor_id = actor["id"]
path = manifest_path(actor_id)
data = json.loads(path.read_text(encoding="utf-8")) if path.is_file() else {}
contract = contracts.get(contract_key(actor_id), {})
validation = contract.get("validation", {})
⋮----
manifest_phone = data.get("phone_qa_pass") is True
contract_phone = validation.get("phone_qa_pass") is True
manifest_android = data.get("android_visual_qa_pass") is True
contract_android = validation.get("android_visual_qa_pass") is True
manifest_accepted = data.get("android_accepted") is True
contract_accepted = contract.get("status") == "accepted"
⋮----
gate_state = {
missing_gates = [label for key, label in GATES if not gate_state[key]]
score = len(GATES) - len(missing_gates)
drift = []
⋮----
minimum_score = min((r["maturity_score"] for r in rows), default=0)
summary = {
```

## File: sprites/normalize_actor_frames.py
```python
#!/usr/bin/env python3
"""Normalize 512px actor renders into Deadline Zero's 96x96 sprite contract.

Uses one fixed transform per direction and one global scale across directions so
animation motion is preserved instead of independently recentering every frame.
"""
⋮----
EXPECTED = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}
DIRS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
⋮----
def parse_args()
⋮----
p = argparse.ArgumentParser()
⋮----
def bbox_alpha(im)
⋮----
def union_boxes(boxes)
⋮----
def main()
⋮----
a = parse_args()
⋮----
expected_paths=[]
⋮----
missing=[str(p) for *_,p in expected_paths if not p.exists()]
⋮----
source=[]; boxes_by_direction=defaultdict(list); source_size=None
⋮----
im=Image.open(p).convert("RGBA")
if source_size is None: source_size=im.size
⋮----
b=bbox_alpha(im)
⋮----
unions={d:union_boxes(boxes_by_direction[d]) for d in DIRS}
maxw=max(b[2]-b[0] for b in unions.values()); maxh=max(b[3]-b[1] for b in unions.values())
scale=min(a.max_width/maxw, a.max_height/maxh)
source_center_x=source_size[0]/2.0
transforms={}
⋮----
nw=max(1,round(uw*scale)); nh=max(1,round(uh*scale))
⋮----
# Some native actions (notably death animations) contain asymmetric
# root motion. Centering the *whole* directional motion envelope keeps
# every frame inside the 96px cell without per-frame recentering, so
# the actual animation motion remains intact.
x=round((96-nw)/2)
⋮----
x=round(48-(source_center_x-b[0])*scale)
y=92-nh
⋮----
cells={}
⋮----
u=unions[d]; t=transforms[d]
crop=im.crop(u).resize(tuple(t["scaled_size"]),Image.Resampling.LANCZOS)
cell=Image.new("RGBA",(96,96),(0,0,0,0)); cell.alpha_composite(crop,tuple(t["dest"]))
⋮----
attack_adjustments=[]
⋮----
attack_keys=[("attack",d,i) for i in range(EXPECTED["attack"])]
centers=[]
⋮----
bb=bbox_alpha(cells[key])
⋮----
median=statistics.median(centers)
⋮----
delta=cx-median
⋮----
target=median+(a.attack_center_limit if delta>0 else -a.attack_center_limit)
shift=round(target-cx)
cell=cells[key]
bb=bbox_alpha(cell)
min_shift=2-bb[0]
max_shift=(96-2)-bb[2]
shift=max(min_shift,min(max_shift,shift))
shifted=Image.new("RGBA",(96,96),(0,0,0,0))
⋮----
new_bb=bbox_alpha(shifted)
new_cx=(new_bb[0]+new_bb[2])/2.0 if new_bb else None
⋮----
rows=[]; motion=defaultdict(list)
⋮----
cell=cells[(anim,d,i)]
out=a.output/anim/d/f"{anim}_{i:02d}.png"; out.parent.mkdir(parents=True,exist_ok=True); cell.save(out)
⋮----
row={"path":str(out.relative_to(a.output)),"bbox":None,"margin":-1,"width":0,"height":0}
⋮----
margin=min(bb[0],bb[1],96-bb[2],96-bb[3])
row={"path":str(out.relative_to(a.output)),"bbox":list(bb),"margin":margin,"width":bb[2]-bb[0],"height":bb[3]-bb[1]}
⋮----
bad=[r for r in rows if r["margin"]<2 or r["width"]<12 or r["height"]<20]
motion_ranges={}
⋮----
report={
⋮----
sheet=Image.new("RGBA",(8*96,5*96),(28,28,32,255)); draw=ImageDraw.Draw(sheet)
⋮----
idx=count//2
⋮----
im=Image.open(a.output/anim/d/f"{anim}_{idx:02d}.png").convert("RGBA")
```

## File: sprites/normalize_rex_frames.py
```python
#!/usr/bin/env python3
"""Normalize Rex 512px renders into the 96x96 production cell contract.

A single fixed transform is used for every frame of a direction. This preserves
intra-animation motion and cross-action pose offsets instead of independently
recentering/grounding every frame, which can erase run bob, recoil and collapse
motion. The transform is still deterministic and uses one global scale across
all eight directions.
"""
⋮----
EXPECTED = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}
DIRS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
⋮----
def args()
⋮----
p = argparse.ArgumentParser()
⋮----
def bbox_alpha(im)
⋮----
def union_boxes(boxes)
⋮----
def main()
⋮----
a = args()
⋮----
expected_paths = []
⋮----
missing = [str(p) for *_, p in expected_paths if not p.exists()]
⋮----
source = []
boxes_by_direction = defaultdict(list)
source_size = None
⋮----
im = Image.open(p).convert("RGBA")
⋮----
source_size = im.size
⋮----
b = bbox_alpha(im)
⋮----
unions = {d: union_boxes(boxes_by_direction[d]) for d in DIRS}
maxw = max(b[2] - b[0] for b in unions.values())
maxh = max(b[3] - b[1] for b in unions.values())
scale = min(82 / maxw, 88 / maxh)
⋮----
source_center_x = source_size[0] / 2.0
transforms = {}
⋮----
nw = max(1, round(uw * scale))
nh = max(1, round(uh * scale))
# Preserve the renderer's world-centre projection horizontally instead
# of centering an asymmetric cape/rifle silhouette.
pivot_x_in_crop = source_center_x - b[0]
x = round(48 - pivot_x_in_crop * scale)
# Ground the whole direction union once. Individual frames are never
# re-grounded, so their relative vertical movement survives.
y = 92 - nh
⋮----
rows = []
motion = defaultdict(list)
⋮----
u = unions[d]
t = transforms[d]
crop = im.crop(u).resize(tuple(t["scaled_size"]), Image.Resampling.LANCZOS)
cell = Image.new("RGBA", (96, 96), (0, 0, 0, 0))
⋮----
out = a.output / anim / d / f"{anim}_{i:02d}.png"
⋮----
bb = bbox_alpha(cell)
⋮----
row = {"path": str(out.relative_to(a.output)), "bbox": None, "margin": -1, "width": 0, "height": 0}
⋮----
margin = min(bb[0], bb[1], 96 - bb[2], 96 - bb[3])
row = {
⋮----
bad = [r for r in rows if r["margin"] < 2 or r["width"] < 12 or r["height"] < 20]
⋮----
motion_ranges = {}
⋮----
report = {
⋮----
# Phone-scale sheet: one representative sample for each animation/direction.
thumb = 96
sheet = Image.new("RGBA", (8 * thumb, 5 * thumb), (28, 28, 32, 255))
draw = ImageDraw.Draw(sheet)
⋮----
idx = count // 2
⋮----
im = Image.open(a.output / anim / d / f"{anim}_{idx:02d}.png").convert("RGBA")
```

## File: sprites/resolve_actor_actions.py
```python
#!/usr/bin/env python3
"""Resolve required actor motions against imported Blender action names.

Candidate configs use semantic aliases while Blender importers may append object,
armature, rig, skeleton, or numeric suffixes. Resolution is deterministic:
exact names win; canonical matches are accepted only when unique; ambiguous
canonical matches are reported and never guessed.
"""
⋮----
REQUIRED = ("idle", "run", "attack", "hit", "death")
KNOWN_SUFFIXES = ("characterarmature", "armature", "rig", "skeleton")
⋮----
def normalize(name: str) -> str
⋮----
value = re.sub(r"\.\d{3}$", "", name.strip().lower())
compact = re.sub(r"[^a-z0-9]+", "", value)
changed = True
⋮----
changed = False
⋮----
compact = compact[: -len(suffix)]
⋮----
def resolve(actions: dict[str, list[str]], available: list[str]) -> dict
⋮----
lowered = {name.lower(): name for name in available}
canonical_to_names: dict[str, list[str]] = {}
⋮----
mapped: dict[str, str] = {}
methods: dict[str, str] = {}
ambiguities: dict[str, dict[str, list[str]]] = {}
⋮----
exact = lowered.get(alias.lower())
⋮----
matches = canonical_to_names.get(normalize(alias), [])
⋮----
missing = [motion for motion in REQUIRED if motion not in mapped]
⋮----
def main() -> None
⋮----
parser = argparse.ArgumentParser()
⋮----
args = parser.parse_args()
⋮----
candidate = json.loads(args.candidate.read_text())
inspection = json.loads(args.inspection.read_text())
available = [row["name"] for row in inspection["actions"]]
payload = resolve(candidate["actions"], available)
```

## File: sprites/resolve_actor_candidate.py
```python
#!/usr/bin/env python3
"""Resolve a production actor candidate into deterministic CI metadata."""
⋮----
def load_candidates(config: Path, fragments_dir: Path) -> dict
⋮----
data = json.loads(config.read_text())
candidates = dict(data.get("candidates", {}))
⋮----
payload = json.loads(fragment.read_text())
fragment_candidates = payload.get("candidates", payload)
overlap = candidates.keys() & fragment_candidates.keys()
⋮----
def immutable_url(source: dict) -> str
⋮----
def main() -> None
⋮----
parser = argparse.ArgumentParser()
⋮----
args = parser.parse_args()
⋮----
candidates = load_candidates(args.config, args.fragments_dir)
⋮----
c = candidates[args.candidate]
source = c["source"]
render = c["render"]
actions = c["actions"]
weapon = c.get("weapon", {})
defensive_prop = dict(c.get("defensive_prop", {}))
path = source["path"]
source_url = immutable_url(source)
⋮----
filename = Path(path).name
payload = {
⋮----
forward = weapon.get("forward", [])
grip_offset = weapon.get("grip_offset", [])
prop_forward = defensive_prop.get("forward", [])
prop_offset = defensive_prop.get("grip_offset", [])
prop_rotation = defensive_prop.get("rotation", [])
values = {
⋮----
value = str(value)
```

## File: sprites/test_resolve_actor_actions.py
```python
class ResolveActorActionsTest(unittest.TestCase)
⋮----
def test_exact_match_wins(self)
⋮----
result = resolve(
⋮----
def test_import_suffixes_are_ignored(self)
⋮----
def test_ambiguous_canonical_match_does_not_guess(self)
⋮----
def test_blender_numeric_suffix_is_ignored(self)
```

## File: sprites/test_validate_actor_production_contracts.py
```python
#!/usr/bin/env python3
⋮----
MODULE_PATH = Path(__file__).with_name("validate_actor_production_contracts.py")
spec = importlib.util.spec_from_file_location("production_contracts", MODULE_PATH)
module = importlib.util.module_from_spec(spec)
⋮----
class CompleteRosterContractTest(unittest.TestCase)
⋮----
def actors(self)
⋮----
def test_accepts_exact_complete_roster(self)
⋮----
def test_rejects_missing_actor(self)
⋮----
actors = self.actors()
⋮----
def test_rejects_unexpected_accepted_actor(self)
⋮----
def test_ignores_nonaccepted_candidate(self)
```

## File: sprites/test_validate_actor_role_metrics.py
```python
class ValidateActorRoleMetricsTest(unittest.TestCase)
⋮----
def setUp(self)
⋮----
def test_no_constraints_passes(self)
⋮----
report = validate({"role_gate": {}}, self.metrics)
⋮----
def test_minimum_constraints_pass(self)
⋮----
candidate = {"role_gate": {"metrics": {
report = validate(candidate, self.metrics)
⋮----
def test_failed_constraint_is_reported(self)
⋮----
candidate = {"role_gate": {"metrics": {"min_median_bbox_area_px2": 6000}}}
⋮----
def test_maximum_constraint_passes(self)
⋮----
candidate = {"role_gate": {"metrics": {"max_median_bbox_width_px": 80}}}
⋮----
def test_unknown_gate_fails_closed(self)
⋮----
candidate = {"role_gate": {"metrics": {"minimum_bossiness": 1}}}
⋮----
def test_boolean_threshold_is_rejected(self)
⋮----
candidate = {"role_gate": {"metrics": {"min_median_bbox_width_px": True}}}
```

## File: sprites/upsert_directional_actor_atlas.py
```python
#!/usr/bin/env python3
"""Build or replace one directional actor page in the production LibGDX atlas.

The key property is *upsert*, not overwrite: existing atlas pages for other actors
are preserved. This makes validated actor publication composable as the roster
moves from Rex to Shambler and beyond.
"""
⋮----
DIRECTIONS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
ANIMATIONS = (("idle", 4), ("run", 8), ("attack", 6), ("hit", 3), ("death", 8))
CELL = 96
EXPECTED_FRAMES = 232
⋮----
def parse_args() -> argparse.Namespace
⋮----
p = argparse.ArgumentParser()
⋮----
def atlas_page_starts(lines: list[str]) -> list[int]
⋮----
starts = []
⋮----
line = lines[i]
⋮----
def split_pages(text: str) -> list[list[str]]
⋮----
lines = text.splitlines()
starts = atlas_page_starts(lines)
⋮----
prefix = [line for line in lines[: starts[0]] if line.strip()]
⋮----
pages = []
⋮----
end = starts[pos + 1] if pos + 1 < len(starts) else len(lines)
page = lines[start:end]
⋮----
def build_sheet(sprite_root: Path, atlas_root: str) -> tuple[Image.Image, list[tuple[str, int, int, int]]]
⋮----
columns = sum(count for _, count in ANIMATIONS)
sheet = Image.new("RGBA", (columns * CELL, len(DIRECTIONS) * CELL), (0, 0, 0, 0))
entries: list[tuple[str, int, int, int]] = []
⋮----
column = 0
⋮----
path = sprite_root / animation / direction / f"{animation}_{frame:02d}.png"
⋮----
image = source.convert("RGBA")
⋮----
x = column * CELL
y = row * CELL
⋮----
def page_lines(png_name: str, sheet: Image.Image, entries: list[tuple[str, int, int, int]]) -> list[str]
⋮----
lines = [
⋮----
def main() -> None
⋮----
args = parse_args()
⋮----
existing = split_pages(args.atlas.read_text()) if args.atlas.is_file() else []
png_name = args.png.name
kept = [page for page in existing if page and page[0].strip() != png_name]
replaced = len(kept) != len(existing)
⋮----
source = {}
⋮----
source = json.loads(args.source_manifest.read_text())
⋮----
metadata = {
```

## File: sprites/validate_actor_production_contracts.py
```python
#!/usr/bin/env python3
⋮----
ROOT = Path(__file__).resolve().parents[2]
CONFIG = ROOT / "config" / "actor-production-contracts.json"
ATLAS = ROOT / "assets" / "art" / "game.atlas"
EXPECTED_COMPLETE_ROSTER = {
⋮----
def fail(message: str) -> None
⋮----
def validate_complete_roster(actors) -> None
⋮----
accepted_names = {actor for actor, spec in actors.items() if spec.get("status") == "accepted"}
⋮----
missing = sorted(EXPECTED_COMPLETE_ROSTER - accepted_names)
unexpected = sorted(accepted_names - EXPECTED_COMPLETE_ROSTER)
⋮----
def main() -> None
⋮----
data = json.loads(CONFIG.read_text(encoding="utf-8"))
⋮----
contract = data.get("frame_contract") or {}
directions = contract.get("directions") or []
animations = contract.get("animations") or {}
total = contract.get("total_frames")
frames_per_direction = contract.get("frames_per_direction")
⋮----
atlas_text = ATLAS.read_text(encoding="utf-8") if ATLAS.exists() else ""
actors = data.get("actors") or {}
⋮----
accepted = []
⋮----
status = spec.get("status")
root = spec.get("atlas_root")
⋮----
png = spec.get("published_png")
⋮----
manifest_path = spec.get("published_manifest")
⋮----
path = ROOT / manifest_path
⋮----
manifest = json.loads(path.read_text(encoding="utf-8"))
⋮----
expected_png_sha256 = manifest.get("png_sha256")
⋮----
actual_png_sha256 = hashlib.sha256((ROOT / png).read_bytes()).hexdigest()
⋮----
source = spec.get("source") or {}
provenance_pairs = (
⋮----
evidence_value = manifest.get(evidence_key)
⋮----
key = f"{root}/{direction}/{animation}\n"
got = atlas_text.count(key)
```

## File: sprites/validate_actor_role_metrics.py
```python
#!/usr/bin/env python3
⋮----
ALLOWED = {
⋮----
def _load(path)
⋮----
def validate(candidate, metrics)
⋮----
constraints = candidate.get("role_gate", {}).get("metrics", {})
⋮----
unknown = sorted(set(constraints) - set(ALLOWED))
⋮----
checks = []
passed = True
⋮----
actual = metrics[metric_name]
⋮----
ok = actual >= threshold if operator == ">=" else actual <= threshold
⋮----
passed = passed and ok
⋮----
def main()
⋮----
parser = argparse.ArgumentParser(description="Validate config-driven actor role silhouette metrics")
⋮----
args = parser.parse_args()
⋮----
candidate = _load(args.candidate)
metrics = _load(args.metrics)
report = validate(candidate, metrics)
rendered = json.dumps(report, indent=2) + "\n"
```

## File: sprites/validate_final_art_promotion_consistency.py
```python
#!/usr/bin/env python3
"""Fail when accepted actor QA evidence and published manifest state drift apart."""
⋮----
ROOT = Path(__file__).resolve().parents[2]
CONTRACTS = ROOT / "config" / "actor-production-contracts.json"
ART = ROOT / "assets" / "art"
⋮----
def manifest_path(actor: str) -> Path
⋮----
def is_explicit_candidate(manifest: dict) -> bool
⋮----
stage = str(manifest.get("source_stage") or "").lower()
⋮----
def main() -> int
⋮----
contracts = json.loads(CONTRACTS.read_text(encoding="utf-8")).get("actors", {})
failures: list[str] = []
checked = 0
skipped_candidates: list[str] = []
⋮----
validation = contract.get("validation", {})
⋮----
path = manifest_path(actor)
⋮----
manifest = json.loads(path.read_text(encoding="utf-8"))
⋮----
expected_run = validation.get("android_runtime_run_id")
⋮----
expected_artifact = validation.get("android_visual_artifact_id")
```

## File: sprites/validate_ranged_attack_readability.py
```python
#!/usr/bin/env python3
"""Validate that a RANGED actor has a phone-readable forward attack protrusion."""
⋮----
def bbox(path: Path)
⋮----
def directional_extension(root: Path, direction: str) -> dict
⋮----
idle = sorted((root / "idle" / direction).glob("*.png"))
attack = sorted((root / "attack" / direction).glob("*.png"))
⋮----
ib = [bbox(p) for p in idle]
ab = [bbox(p) for p in attack]
⋮----
idle_front = median(b[2] for b in ib)
attack_front = max(b[2] for b in ab)
extension = attack_front - idle_front
⋮----
idle_front = median(b[0] for b in ib)
attack_front = min(b[0] for b in ab)
extension = idle_front - attack_front
⋮----
def main() -> None
⋮----
ap = argparse.ArgumentParser()
⋮----
args = ap.parse_args()
rows = [directional_extension(args.input, "e"), directional_extension(args.input, "w")]
minimum = min(r["forward_extension_px"] for r in rows)
payload = {
```

## File: build_final_sprite_frames.py
```python
#!/usr/bin/env python3
"""Batch-cut all production actor sheets defined by final-sprite-layout.json.

Requires Pillow because it delegates frame extraction to slice_sprite_sheet.py.
Typical final-art command:

  python tools/build_final_sprite_frames.py --clean --require-all --strict

Without --require-all, missing source PNGs are skipped so artists can deliver actors incrementally.
"""
⋮----
ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
CUTTER = ROOT / "tools" / "slice_sprite_sheet.py"
DEFAULT_OUTPUT = ROOT / "build" / "art_frames"
⋮----
cell_arg = str(cell_w) if cell_w == cell_h else f"{cell_w}x{cell_h}"
command = [
⋮----
def manifest_path(actor: dict, output: Path) -> Path
⋮----
def main() -> int
⋮----
parser = argparse.ArgumentParser(description=__doc__)
⋮----
args = parser.parse_args()
⋮----
layout = json.loads(LAYOUT.read_text(encoding="utf-8"))
actors = sorted(layout["actors"], key=lambda actor: (actor["priority"], actor["id"]))
output = args.output.resolve()
⋮----
missing: list[str] = []
warnings: list[str] = []
built: list[dict] = []
⋮----
sheet = ROOT / "art_sources" / f"{actor['id']}.png"
⋮----
completed = subprocess.run(
⋮----
manifest = manifest_path(actor, output)
⋮----
metadata = json.loads(manifest.read_text(encoding="utf-8"))
actor_warnings = metadata.get("warnings", [])
⋮----
aggregate = {
aggregate_path = output / "final-sprite-build.json"
```

## File: import_pixellab_idle.py
```python
#!/usr/bin/env python3
"""Import a manually generated PixelLab idle animation into Deadline Zero.

The tool accepts an animated GIF or a directory of PNG frames, normalizes it to
the actor cell from art_sources/final-sprite-layout.json, selects the contracted
idle frame count, preserves pixel scale by default, anchors frames to a stable
foot pivot, and writes production-named frames plus a preview/QA manifest.

Example:
  python -m pip install pillow
  python tools/import_pixellab_idle.py rex s art_sources/pixellab/rex_idle_s.gif

Output:
  build/pixellab_idle/survivor/rex/idle/s/idle_00.png ... idle_03.png
  build/pixellab_idle/rex_idle_s.gif
  build/pixellab_idle/rex_idle_s.json
"""
⋮----
ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
DEFAULT_OUT = ROOT / "build" / "pixellab_idle"
DIRECTIONS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
⋮----
def load_actor(actor_id: str) -> tuple[dict, dict]
⋮----
layout = json.loads(LAYOUT.read_text(encoding="utf-8"))
⋮----
def load_frames(source: Path) -> tuple[list[Image.Image], list[int]]
⋮----
paths = sorted(source.glob("*.png"))
⋮----
im = Image.open(source)
⋮----
def alpha_bbox(im: Image.Image)
⋮----
def evenly_spaced_indices(count: int, wanted: int) -> list[int]
⋮----
def normalize(frame: Image.Image, cell: tuple[int, int], foot_y: int | None) -> tuple[Image.Image, dict]
⋮----
bbox = alpha_bbox(frame)
⋮----
art = frame.crop(bbox)
⋮----
scale = min((cw - 4) / aw, (ch - 4) / ah)
⋮----
art = art.resize((nw, nh), Image.Resampling.NEAREST)
⋮----
target_foot = foot_y if foot_y is not None else ch - 3
x = (cw - aw) // 2
y = target_foot - ah
⋮----
y = 2
canvas = Image.new("RGBA", (cw, ch), (0, 0, 0, 0))
⋮----
out_bbox = alpha_bbox(canvas)
⋮----
def main() -> int
⋮----
ap = argparse.ArgumentParser(description=__doc__)
⋮----
args = ap.parse_args()
⋮----
wanted = (layout["bossFrames"] if actor["profile"] == "boss" else layout["standardFrames"])["idle"]
cell = tuple(actor["cell"])
⋮----
chosen = evenly_spaced_indices(len(frames), wanted)
⋮----
foot_values = [m["footY"] for m in metrics]
center_values = [m["centerX"] for m in metrics]
warnings = []
⋮----
out = args.output.resolve()
frame_dir = out / actor["root"] / "idle" / args.direction
⋮----
preview = out / f"{args.actor}_idle_{args.direction}.gif"
⋮----
manifest = {
manifest_path = out / f"{args.actor}_idle_{args.direction}.json"
```

## File: slice_sprite_sheet.py
```python
#!/usr/bin/env python3
"""Cut a deterministic Deadline Zero actor sprite sheet into atlas-ready PNG frames.

Requires Pillow (`python -m pip install pillow`). The sheet layout is intentionally simple:
- rows: 8 directions in N, NE, E, SE, S, SW, W, NW order
- columns: motion groups in idle, run, attack, hit, death order
- every source cell has the same dimensions

The cutter also records alpha bounds and catches unstable foot/center pivots before packing.
Death is excluded from pivot-stability checks because the authored silhouette is expected to fall.
"""
⋮----
DIRECTIONS = ("n", "ne", "e", "se", "s", "sw", "w", "nw")
STANDARD = {"idle": 4, "run": 8, "attack": 6, "hit": 3, "death": 8}
BOSS = {"idle": 6, "run": 8, "attack": 8, "hit": 4, "death": 10}
PIVOT_CHECKED_MOTIONS = frozenset(("idle", "run", "attack", "hit"))
⋮----
def parse_cell(value: str) -> Tuple[int, int]
⋮----
token = value.lower().replace("x", ",")
parts = [part.strip() for part in token.split(",") if part.strip()]
⋮----
size = int(parts[0])
⋮----
def frame_counts(args: argparse.Namespace) -> Dict[str, int]
⋮----
base = dict(BOSS if args.boss else STANDARD)
⋮----
override = getattr(args, motion)
⋮----
def occupied_bbox(frame: Image.Image)
⋮----
def validate_padding(frame: Image.Image, padding: int) -> bool
⋮----
bbox = occupied_bbox(frame)
⋮----
def alpha_metrics(frame: Image.Image)
⋮----
valid = [item for item in sequence if item["metrics"] is not None]
⋮----
median_foot = statistics.median(item["metrics"]["footY"] for item in valid)
median_center = statistics.median(item["metrics"]["centerX"] for item in valid)
motion_multiplier = 2.0 if motion == "attack" else 1.0
allowed_foot = foot_tolerance * motion_multiplier
allowed_center = center_tolerance * motion_multiplier
⋮----
frame_index = item["index"]
metrics = item["metrics"]
foot_delta = abs(metrics["footY"] - median_foot)
center_delta = abs(metrics["centerX"] - median_center)
⋮----
total_columns = sum(counts.values())
required_w = total_columns * cell_w
required_h = len(DIRECTIONS) * cell_h
⋮----
root_path = Path(*root.split("/"))
metadata = {
⋮----
column = 0
⋮----
sequence: list[dict] = []
⋮----
x = column * cell_w
y = row * cell_h
frame = sheet.crop((x, y, x + cell_w, y + cell_h))
metrics = alpha_metrics(frame)
⋮----
target_dir = output / root_path / direction
⋮----
target = target_dir / f"{motion}_{frame_index:02d}.png"
⋮----
frame_record = {
⋮----
def main() -> int
⋮----
parser = argparse.ArgumentParser(description=__doc__)
⋮----
args = parser.parse_args()
⋮----
counts = frame_counts(args)
⋮----
sheet = source.convert("RGBA")
metadata = export(
⋮----
manifest_name = args.root.strip("/").replace("/", "__") + ".frames.json"
manifest_path = args.output / manifest_name
⋮----
warnings = metadata["warnings"]
```

## File: test_verify_final_atlas.py
```python
#!/usr/bin/env python3
⋮----
class FinalAtlasVerifierTest(unittest.TestCase)
⋮----
def test_parse_atlas_preserves_paths_pages_and_frame_indices(self) -> None
⋮----
text = """game.png
⋮----
atlas = Path(directory) / "game.atlas"
⋮----
parsed = verify_final_atlas.parse_atlas(atlas)
pages = verify_final_atlas.atlas_pages(atlas)
⋮----
def test_expected_contract_applies_fast_run_override_only_to_run(self) -> None
⋮----
layout = {
expected = verify_final_atlas.expected_contract(layout)
```

## File: texturepacker-final.json
```json
{
  "pot": false,
  "paddingX": 4,
  "paddingY": 4,
  "edgePadding": true,
  "duplicatePadding": true,
  "rotation": false,
  "minWidth": 256,
  "minHeight": 256,
  "maxWidth": 4096,
  "maxHeight": 4096,
  "stripWhitespaceX": false,
  "stripWhitespaceY": false,
  "alias": true,
  "ignoreBlankImages": false,
  "fast": false,
  "debug": false,
  "filterMin": "Nearest",
  "filterMag": "Nearest",
  "wrapX": "ClampToEdge",
  "wrapY": "ClampToEdge",
  "format": "RGBA8888",
  "useIndexes": true,
  "bleed": true,
  "bleedIterations": 2,
  "limitMemory": true,
  "grid": false,
  "scale": [1.0],
  "scaleSuffix": [""]
}
```

## File: validate_final_sprite_layout.py
```python
#!/usr/bin/env python3
"""Validate the final production sprite-sheet contract without external dependencies.

The validator always checks layout integrity and TexturePacker parity. If actor PNG
files are present in art_sources/, it also validates their PNG dimensions directly
from the IHDR chunk.
"""
⋮----
ROOT = Path(__file__).resolve().parents[1]
LAYOUT_PATH = ROOT / "art_sources" / "final-sprite-layout.json"
PACKER_PATH = ROOT / "tools" / "texturepacker-final.json"
PNG_SIGNATURE = b"\x89PNG\r\n\x1a\n"
EXPECTED_DIRECTIONS = ["n", "ne", "e", "se", "s", "sw", "w", "nw"]
EXPECTED_MOTIONS = ["idle", "run", "attack", "hit", "death"]
⋮----
def fail(message: str) -> None
⋮----
def positive_int(value, label: str) -> int
⋮----
def validate_frame_map(value, label: str) -> dict[str, int]
⋮----
def png_dimensions(path: Path) -> tuple[int, int]
⋮----
header = handle.read(24)
⋮----
def validate_actor(actor: dict, standard: dict[str, int], boss: dict[str, int]) -> tuple[str, str, int, int, int]
⋮----
actor_id = actor.get("id")
root = actor.get("root")
profile = actor.get("profile")
cell = actor.get("cell")
priority = actor.get("priority")
⋮----
cell_w = positive_int(cell[0], f"{actor_id}.cell[0]")
cell_h = positive_int(cell[1], f"{actor_id}.cell[1]")
⋮----
expected_cell = 128 if profile == "boss" else 96
⋮----
priority = positive_int(priority, f"{actor_id}.priority")
⋮----
counts = dict(boss if profile == "boss" else standard)
⋮----
columns = sum(counts.values())
⋮----
def validate_packing(layout_packing: dict) -> None
⋮----
padding = positive_int(layout_packing.get("padding"), "packing.padding")
⋮----
max_page = layout_packing.get("maxPage")
⋮----
packer = json.loads(PACKER_PATH.read_text(encoding="utf-8"))
parity = {
⋮----
def main() -> int
⋮----
layout = json.loads(LAYOUT_PATH.read_text(encoding="utf-8"))
⋮----
standard = validate_frame_map(layout.get("standardFrames"), "standardFrames")
boss = validate_frame_map(layout.get("bossFrames"), "bossFrames")
actors = layout.get("actors")
⋮----
ids: set[str] = set()
roots: set[str] = set()
present = 0
⋮----
png = ROOT / "art_sources" / f"{actor_id}.png"
```

## File: validate_rex_reference.py
```python
#!/usr/bin/env python3
"""Keep Rex's final-art reference aligned with the machine-readable sprite contract."""
⋮----
ROOT = Path(__file__).resolve().parents[1]
LAYOUT = ROOT / "art_sources" / "final-sprite-layout.json"
REFERENCE = ROOT / "art_sources" / "rex-reference.md"
EXPECTED_DIRECTIONS = ["n", "ne", "e", "se", "s", "sw", "w", "nw"]
EXPECTED_MOTIONS = ["idle", "run", "attack", "hit", "death"]
⋮----
def fail(message: str) -> None
⋮----
def main() -> int
⋮----
layout = json.loads(LAYOUT.read_text(encoding="utf-8"))
reference = REFERENCE.read_text(encoding="utf-8")
rex = next((actor for actor in layout["actors"] if actor["id"] == "rex"), None)
⋮----
counts = dict(layout["standardFrames"])
⋮----
columns = sum(counts[motion] for motion in EXPECTED_MOTIONS)
width = columns * rex["cell"][0]
height = len(EXPECTED_DIRECTIONS) * rex["cell"][1]
total = columns * len(EXPECTED_DIRECTIONS)
⋮----
required_tokens = [
```

## File: verify_final_atlas.py
```python
#!/usr/bin/env python3
"""Verify that a packed libGDX TextureAtlas covers the final actor animation contract."""
⋮----
ROOT = Path(__file__).resolve().parents[1]
LAYOUT_PATH = ROOT / "art_sources" / "final-sprite-layout.json"
PNG_SIGNATURE = b"\x89PNG\r\n\x1a\n"
⋮----
def atlas_pages(path: Path) -> list[str]
⋮----
pages: list[str] = []
⋮----
stripped = raw.strip()
⋮----
lower = stripped.lower()
⋮----
def png_dimensions(path: Path) -> tuple[int, int]
⋮----
header = handle.read(24)
⋮----
def parse_atlas(path: Path) -> dict[str, set[int]]
⋮----
regions: dict[str, set[int]] = {}
current: str | None = None
current_index = -1
⋮----
def commit() -> None
⋮----
current = None
⋮----
current = stripped.replace("\\", "/").strip("/")
⋮----
current_index = int(value.strip())
⋮----
def expected_contract(layout: dict) -> dict[str, set[int]]
⋮----
directions = layout["directions"]
motions = layout["motionOrder"]
expected: dict[str, set[int]] = {}
⋮----
counts = dict(layout["bossFrames"] if actor["profile"] == "boss" else layout["standardFrames"])
⋮----
key = f"{actor['root']}/{direction}/{motion}"
⋮----
def validate_pages(atlas: Path, layout: dict) -> list[tuple[str, int, int]]
⋮----
names = atlas_pages(atlas)
⋮----
root = atlas.parent.resolve()
seen: set[str] = set()
result: list[tuple[str, int, int]] = []
⋮----
page = (atlas.parent / name).resolve()
⋮----
def main() -> int
⋮----
parser = argparse.ArgumentParser(description=__doc__)
⋮----
args = parser.parse_args()
atlas = args.atlas.resolve()
⋮----
layout = json.loads(LAYOUT_PATH.read_text(encoding="utf-8"))
pages = validate_pages(atlas, layout)
actual = parse_atlas(atlas)
expected = expected_contract(layout)
missing_keys: list[str] = []
bad_indices: list[str] = []
⋮----
actual_indices = actual.get(key)
⋮----
missing = sorted(required_indices - actual_indices)
extra = sorted(actual_indices - required_indices)
⋮----
expected_frames = sum(len(indices) for indices in expected.values())
```
