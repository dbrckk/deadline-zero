class_name DZHud
extends CanvasLayer

signal upgrade_chosen(index: int)

var hp_bar: ProgressBar
var xp_bar: ProgressBar
var status_label: Label
var wave_label: Label
var upgrade_panel: PanelContainer
var upgrade_buttons: Array[Button] = []
var upgrade_cards: Array[VBoxContainer] = []
var upgrade_family_labels: Array[Label] = []
var upgrade_title_labels: Array[Label] = []
var upgrade_detail_labels: Array[Label] = []
var boss_panel: PanelContainer
var boss_name_label: Label
var boss_hp_bar: ProgressBar
var boss_phase_label: Label
var boss_hp_max := 1.0
var threat_panel: PanelContainer
var threat_label: Label

func _ready() -> void:
    process_mode = Node.PROCESS_MODE_ALWAYS
    _build()

func set_health(value: float, maximum: float) -> void:
    hp_bar.max_value = max(1.0, maximum)
    hp_bar.value = value

func set_progress(xp: int, next_xp: int, level: int, kills: int, elapsed: float) -> void:
    xp_bar.max_value = max(1, next_xp)
    xp_bar.value = xp
    status_label.text = "LV %d   KILLS %d   %02d:%02d" % [level, kills, int(elapsed) / 60, int(elapsed) % 60]

func set_wave(text: String) -> void:
    wave_label.text = text

func show_boss(name: String, maximum: float) -> void:
    boss_hp_max = max(1.0, maximum)
    boss_name_label.text = name
    boss_hp_bar.max_value = boss_hp_max
    boss_hp_bar.value = boss_hp_max
    boss_phase_label.text = "THREAT LOCK"
    boss_panel.visible = true

func set_boss_health(value: float, maximum: float) -> void:
    boss_hp_max = max(1.0, maximum)
    boss_hp_bar.max_value = boss_hp_max
    boss_hp_bar.value = clamp(value, 0.0, boss_hp_max)
    var ratio := boss_hp_bar.value / boss_hp_max
    boss_phase_label.text = "PHASE III // EXECUTE" if ratio <= 0.30 else ("PHASE II // ENRAGED" if ratio <= 0.65 else "PHASE I // HUNT")
    if boss_hp_bar.value <= 0.0:
        boss_panel.visible = false

func hide_boss() -> void:
    boss_panel.visible = false

func set_offscreen_threat(direction: Vector2, threat_kind: String, distance: float) -> void:
    if direction.length_squared() < 0.001:
        hide_offscreen_threat()
        return
    var arrow := _direction_arrow(direction.normalized())
    threat_label.text = "%s  %s  %dm" % [arrow, threat_kind.to_upper(), int(round(distance))]
    threat_panel.visible = true

func hide_offscreen_threat() -> void:
    threat_panel.visible = false

func _direction_arrow(direction: Vector2) -> String:
    var angle := atan2(direction.y, direction.x)
    var octant := int(round(angle / (PI / 4.0)))
    match octant:
        0: return "→"
        1: return "↘"
        2: return "↓"
        3: return "↙"
        4, -4: return "←"
        -3: return "↖"
        -2: return "↑"
        -1: return "↗"
        _: return "→"

func show_upgrade(items: Array) -> void:
    for i in range(upgrade_buttons.size()):
        var item: Dictionary = items[i] if i < items.size() else {}
        var id := str(item.get("id", "damage"))
        upgrade_family_labels[i].text = str(item.get("family", "UPGRADE"))
        upgrade_title_labels[i].text = str(item.get("title", "UPGRADE"))
        upgrade_detail_labels[i].text = str(item.get("detail", ""))
        upgrade_buttons[i].text = _upgrade_glyph(id)
        _style_upgrade_card(i, id)
    upgrade_panel.visible = true

func hide_upgrade() -> void:
    upgrade_panel.visible = false

func show_game_over() -> void:
    wave_label.text = "RUN TERMINATED"

func _build() -> void:
    var root := Control.new()
    root.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    add_child(root)

    var top := VBoxContainer.new()
    top.position = Vector2(28, 24)
    top.size = Vector2(500, 100)
    root.add_child(top)

    hp_bar = ProgressBar.new()
    hp_bar.custom_minimum_size = Vector2(420, 22)
    hp_bar.show_percentage = false
    top.add_child(hp_bar)

    xp_bar = ProgressBar.new()
    xp_bar.custom_minimum_size = Vector2(420, 12)
    xp_bar.show_percentage = false
    top.add_child(xp_bar)

    status_label = Label.new()
    status_label.text = "LV 1   KILLS 0"
    status_label.add_theme_font_size_override("font_size", 20)
    top.add_child(status_label)

    wave_label = Label.new()
    wave_label.text = "QUARANTINE YARD"
    wave_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    wave_label.add_theme_font_size_override("font_size", 28)
    wave_label.set_anchors_preset(Control.PRESET_CENTER_TOP)
    wave_label.position = Vector2(-220, 24)
    wave_label.size = Vector2(440, 42)
    root.add_child(wave_label)

    threat_panel = PanelContainer.new()
    threat_panel.name = "ThreatPanel"
    threat_panel.set_anchors_preset(Control.PRESET_CENTER_RIGHT)
    threat_panel.position = Vector2(-210, -34)
    threat_panel.size = Vector2(180, 68)
    threat_panel.visible = false
    threat_panel.mouse_filter = Control.MOUSE_FILTER_IGNORE
    root.add_child(threat_panel)

    threat_label = Label.new()
    threat_label.name = "ThreatLabel"
    threat_label.text = "→ ELITE  18m"
    threat_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    threat_label.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
    threat_label.add_theme_font_size_override("font_size", 18)
    threat_label.modulate = Color(1.0, 0.56, 0.22)
    threat_panel.add_child(threat_label)

    var threat_style := StyleBoxFlat.new()
    threat_style.bg_color = Color(0.06, 0.025, 0.01, 0.88)
    threat_style.border_color = Color(1.0, 0.42, 0.08, 0.86)
    threat_style.set_border_width_all(2)
    threat_style.corner_radius_top_left = 8
    threat_style.corner_radius_top_right = 8
    threat_style.corner_radius_bottom_left = 8
    threat_style.corner_radius_bottom_right = 8
    threat_panel.add_theme_stylebox_override("panel", threat_style)

    boss_panel = PanelContainer.new()
    boss_panel.set_anchors_preset(Control.PRESET_CENTER_TOP)
    boss_panel.position = Vector2(-330, 76)
    boss_panel.size = Vector2(660, 78)
    boss_panel.visible = false
    root.add_child(boss_panel)

    var boss_box := VBoxContainer.new()
    boss_box.add_theme_constant_override("separation", 3)
    boss_panel.add_child(boss_box)

    var boss_header := HBoxContainer.new()
    boss_header.alignment = BoxContainer.ALIGNMENT_CENTER
    boss_box.add_child(boss_header)

    boss_name_label = Label.new()
    boss_name_label.text = "REVENANT PRIME"
    boss_name_label.add_theme_font_size_override("font_size", 18)
    boss_name_label.modulate = Color(1.0, 0.82, 0.42)
    boss_header.add_child(boss_name_label)

    var spacer := Control.new()
    spacer.custom_minimum_size = Vector2(32, 1)
    boss_header.add_child(spacer)

    boss_phase_label = Label.new()
    boss_phase_label.text = "PHASE I // HUNT"
    boss_phase_label.add_theme_font_size_override("font_size", 13)
    boss_phase_label.modulate = Color(1.0, 0.42, 0.26)
    boss_header.add_child(boss_phase_label)

    boss_hp_bar = ProgressBar.new()
    boss_hp_bar.custom_minimum_size = Vector2(620, 18)
    boss_hp_bar.show_percentage = false
    boss_box.add_child(boss_hp_bar)

    var boss_style := StyleBoxFlat.new()
    boss_style.bg_color = Color(0.025, 0.035, 0.045, 0.96)
    boss_style.border_color = Color(0.92, 0.28, 0.12, 0.72)
    boss_style.set_border_width_all(2)
    boss_style.corner_radius_top_left = 6
    boss_style.corner_radius_top_right = 6
    boss_style.corner_radius_bottom_left = 6
    boss_style.corner_radius_bottom_right = 6
    boss_panel.add_theme_stylebox_override("panel", boss_style)

    upgrade_panel = PanelContainer.new()
    upgrade_panel.set_anchors_preset(Control.PRESET_CENTER)
    upgrade_panel.position = Vector2(-480, -155)
    upgrade_panel.size = Vector2(960, 310)
    upgrade_panel.visible = false
    root.add_child(upgrade_panel)

    var box := VBoxContainer.new()
    box.add_theme_constant_override("separation", 18)
    upgrade_panel.add_child(box)

    var title := Label.new()
    title.text = "SELECT COMBAT UPGRADE"
    title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    title.add_theme_font_size_override("font_size", 30)
    box.add_child(title)

    var row := HBoxContainer.new()
    row.alignment = BoxContainer.ALIGNMENT_CENTER
    row.add_theme_constant_override("separation", 18)
    box.add_child(row)

    for i in range(3):
        var card := VBoxContainer.new()
        card.custom_minimum_size = Vector2(280, 190)
        card.add_theme_constant_override("separation", 5)
        row.add_child(card)
        upgrade_cards.append(card)

        var family := Label.new()
        family.text = "UPGRADE"
        family.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        family.add_theme_font_size_override("font_size", 13)
        card.add_child(family)
        upgrade_family_labels.append(family)

        var button := Button.new()
        button.custom_minimum_size = Vector2(280, 82)
        button.text = "◆"
        button.add_theme_font_size_override("font_size", 38)
        button.pressed.connect(_on_upgrade_pressed.bind(i))
        card.add_child(button)
        upgrade_buttons.append(button)

        var upgrade_title := Label.new()
        upgrade_title.text = "UPGRADE"
        upgrade_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        upgrade_title.add_theme_font_size_override("font_size", 21)
        card.add_child(upgrade_title)
        upgrade_title_labels.append(upgrade_title)

        var detail := Label.new()
        detail.text = ""
        detail.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        detail.add_theme_font_size_override("font_size", 16)
        detail.modulate = Color(0.76, 0.84, 0.90)
        card.add_child(detail)
        upgrade_detail_labels.append(detail)

func _upgrade_glyph(id: String) -> String:
    match id:
        "damage": return "▲"
        "rate": return "»»"
        "speed": return "➤"
        "health": return "+"
        "projectile": return "◆"
        "multishot": return "⋙"
        _: return "◆"

func _upgrade_color(id: String) -> Color:
    match id:
        "damage", "multishot": return Color(1.0, 0.66, 0.18)
        "rate", "speed": return Color(0.18, 0.86, 1.0)
        "health": return Color(0.32, 0.94, 0.52)
        "projectile": return Color(0.76, 0.82, 1.0)
        _: return Color(0.58, 0.42, 1.0)

func _style_upgrade_card(index: int, id: String) -> void:
    var accent := _upgrade_color(id)
    upgrade_family_labels[index].modulate = accent
    upgrade_title_labels[index].modulate = Color.WHITE
    var normal := StyleBoxFlat.new()
    normal.bg_color = Color(0.035, 0.055, 0.070, 0.98)
    normal.border_color = Color(accent.r, accent.g, accent.b, 0.72)
    normal.set_border_width_all(2)
    normal.corner_radius_top_left = 8
    normal.corner_radius_top_right = 8
    normal.corner_radius_bottom_left = 8
    normal.corner_radius_bottom_right = 8
    var hover := normal.duplicate()
    hover.bg_color = Color(accent.r * 0.16, accent.g * 0.16, accent.b * 0.16, 1.0)
    hover.border_color = accent
    upgrade_buttons[index].add_theme_stylebox_override("normal", normal)
    upgrade_buttons[index].add_theme_stylebox_override("hover", hover)
    upgrade_buttons[index].add_theme_stylebox_override("pressed", hover)
    upgrade_buttons[index].add_theme_color_override("font_color", accent)

func _on_upgrade_pressed(index: int) -> void:
    upgrade_chosen.emit(index)
