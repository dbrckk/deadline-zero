extends Node3D

const UPGRADE_POOL := [
    {"id":"damage", "label":"HEAVY PAYLOAD\nDamage +25%"},
    {"id":"rate", "label":"RAPID FIRE\nFire rate +22%"},
    {"id":"speed", "label":"SCOUT FRAME\nMove speed +14%"},
    {"id":"health", "label":"REACTIVE PLATING\nMax HP +30"},
    {"id":"projectile", "label":"HYPER VELOCITY\nProjectile speed +20%"},
    {"id":"multishot", "label":"MULTISHOT\n+1 projectile"}
]

var player: DZPlayer
var camera: Camera3D
var hud: DZHud
var spawn_clock := 0.0
var elapsed := 0.0
var kills := 0
var level := 1
var xp := 0
var xp_next := 10
var game_over := false
var pending_upgrades: Array = []
var touch_id := -1
var touch_origin := Vector2.ZERO

func _ready() -> void:
    randomize()
    _build_world()

    player = DZPlayer.new()
    add_child(player)
    player.global_position = Vector3.ZERO
    player.health_changed.connect(_on_health_changed)
    player.died.connect(_on_player_died)

    camera = Camera3D.new()
    camera.current = true
    camera.fov = 48.0
    add_child(camera)
    camera.global_position = Vector3(0.0, 14.0, 10.0)
    camera.look_at(Vector3(0.0, 0.6, 0.0), Vector3.UP)

    hud = DZHud.new()
    add_child(hud)
    hud.upgrade_chosen.connect(_on_upgrade_chosen)
    hud.set_health(player.health, player.max_health)
    hud.set_progress(xp, xp_next, level, kills, elapsed)

    for i in range(8):
        _spawn_enemy()

func _process(delta: float) -> void:
    if player and is_instance_valid(player):
        var desired := player.global_position + Vector3(0.0, 14.0, 10.0)
        camera.global_position = camera.global_position.lerp(desired, 1.0 - exp(-delta * 4.5))
        camera.look_at(player.global_position + Vector3(0.0, 0.65, 0.0), Vector3.UP)

func _physics_process(delta: float) -> void:
    if game_over:
        return
    elapsed += delta
    spawn_clock -= delta
    if spawn_clock <= 0.0:
        var batch := 1 + int(elapsed / 45.0)
        for i in range(min(batch, 4)):
            _spawn_enemy()
        spawn_clock = max(0.20, 0.82 - elapsed * 0.0035)
    hud.set_progress(xp, xp_next, level, kills, elapsed)
    hud.set_wave(_wave_name())

func _unhandled_input(event: InputEvent) -> void:
    if player == null:
        return
    if event is InputEventScreenTouch:
        var touch := event as InputEventScreenTouch
        if touch.pressed and touch.position.x < get_viewport().get_visible_rect().size.x * 0.55 and touch_id < 0:
            touch_id = touch.index
            touch_origin = touch.position
        elif not touch.pressed and touch.index == touch_id:
            touch_id = -1
            player.set_touch_move(Vector2.ZERO)
    elif event is InputEventScreenDrag:
        var drag := event as InputEventScreenDrag
        if drag.index == touch_id:
            var vector := (drag.position - touch_origin) / 90.0
            player.set_touch_move(Vector2(vector.x, vector.y).limit_length(1.0))

func _spawn_enemy() -> void:
    if player == null or game_over:
        return
    var angle := randf() * TAU
    var radius := randf_range(12.0, 18.0)
    var pos := player.global_position + Vector3(cos(angle) * radius, 0.0, sin(angle) * radius)
    var roll := randf()
    var kind := "shambler"
    if elapsed > 25.0 and roll > 0.72:
        kind = "runner"
    if elapsed > 55.0 and roll > 0.88:
        kind = "brute"
    if elapsed > 100.0 and roll > 0.96:
        kind = "elite"
    var difficulty := 1.0 + elapsed / 210.0 + float(level - 1) * 0.035
    var enemy := DZEnemy.new()
    enemy.configure(kind, difficulty, player)
    enemy.died.connect(_on_enemy_died)
    add_child(enemy)
    enemy.global_position = pos

func _on_enemy_died(xp_value: int, at: Vector3) -> void:
    kills += 1
    var orb := DZXpOrb.new()
    orb.amount = xp_value
    orb.target = player
    orb.collected.connect(_on_xp_collected)
    add_child(orb)
    orb.global_position = at + Vector3(0.0, 0.18, 0.0)

func _on_xp_collected(amount: int) -> void:
    xp += amount
    while xp >= xp_next:
        xp -= xp_next
        level += 1
        xp_next = int(round(float(xp_next) * 1.24 + 4.0))
        _offer_upgrade()
        break

func _offer_upgrade() -> void:
    pending_upgrades.clear()
    var available := UPGRADE_POOL.duplicate(true)
    available.shuffle()
    for i in range(3):
        pending_upgrades.append(available[i])
    var labels: Array[String] = []
    for item in pending_upgrades:
        labels.append(item["label"])
    hud.show_upgrade(labels)
    get_tree().paused = true

func _on_upgrade_chosen(index: int) -> void:
    if index < 0 or index >= pending_upgrades.size():
        return
    player.apply_upgrade(pending_upgrades[index]["id"])
    pending_upgrades.clear()
    hud.hide_upgrade()
    get_tree().paused = false

func _on_health_changed(current: float, maximum: float) -> void:
    if hud:
        hud.set_health(current, maximum)

func _on_player_died() -> void:
    game_over = true
    if hud:
        hud.show_game_over()

func _wave_name() -> String:
    if elapsed < 45.0:
        return "QUARANTINE YARD"
    if elapsed < 90.0:
        return "CINDER SURGE"
    if elapsed < 150.0:
        return "NULL SECTOR"
    return "OVERRUN // THREAT ESCALATING"

func _build_world() -> void:
    var environment := WorldEnvironment.new()
    var env := Environment.new()
    env.background_mode = Environment.BG_COLOR
    env.background_color = Color(0.012, 0.020, 0.027)
    env.ambient_light_source = Environment.AMBIENT_SOURCE_COLOR
    env.ambient_light_color = Color(0.22, 0.34, 0.42)
    env.ambient_light_energy = 0.85
    env.tonemap_mode = Environment.TONE_MAPPER_FILMIC
    environment.environment = env
    add_child(environment)

    var sun := DirectionalLight3D.new()
    sun.rotation_degrees = Vector3(-58.0, -28.0, 0.0)
    sun.light_color = Color(0.76, 0.88, 1.0)
    sun.light_energy = 1.4
    sun.shadow_enabled = true
    add_child(sun)

    var fill := OmniLight3D.new()
    fill.position = Vector3(0.0, 8.0, 0.0)
    fill.light_color = Color(0.08, 0.65, 1.0)
    fill.light_energy = 2.2
    fill.omni_range = 28.0
    add_child(fill)

    var floor := MeshInstance3D.new()
    var plane := PlaneMesh.new()
    plane.size = Vector2(72.0, 72.0)
    floor.mesh = plane
    var floor_mat := StandardMaterial3D.new()
    floor_mat.albedo_color = Color(0.075, 0.09, 0.095)
    floor_mat.roughness = 0.86
    floor_mat.metallic = 0.08
    floor.material_override = floor_mat
    add_child(floor)

    for i in range(34):
        var prop := MeshInstance3D.new()
        var box := BoxMesh.new()
        box.size = Vector3(randf_range(0.5, 1.8), randf_range(0.25, 1.1), randf_range(0.5, 1.8))
        prop.mesh = box
        prop.position = Vector3(randf_range(-28.0, 28.0), box.size.y * 0.5, randf_range(-28.0, 28.0))
        var mat := StandardMaterial3D.new()
        mat.albedo_color = Color(0.11, 0.13, 0.14).lerp(Color(0.22, 0.12, 0.06), randf() * 0.35)
        mat.roughness = 0.74
        mat.metallic = 0.35
        prop.material_override = mat
        add_child(prop)

    for i in range(18):
        var stripe := MeshInstance3D.new()
        var stripe_mesh := BoxMesh.new()
        stripe_mesh.size = Vector3(randf_range(1.5, 4.0), 0.015, 0.08)
        stripe.mesh = stripe_mesh
        stripe.position = Vector3(randf_range(-26.0, 26.0), 0.012, randf_range(-26.0, 26.0))
        stripe.rotation.y = randf_range(0.0, TAU)
        var stripe_mat := StandardMaterial3D.new()
        stripe_mat.albedo_color = Color(0.82, 0.42, 0.06)
        stripe_mat.emission_enabled = true
        stripe_mat.emission = Color(0.45, 0.10, 0.01)
        stripe_mat.emission_energy_multiplier = 0.45
        stripe.material_override = stripe_mat
        add_child(stripe)
