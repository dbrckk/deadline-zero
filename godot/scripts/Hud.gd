class_name DZHud
extends CanvasLayer

signal upgrade_chosen(index: int)
signal restart_requested
signal pause_requested
signal resume_requested
signal master_volume_changed(value: float)
signal sfx_volume_changed(value: float)

var hp_bar: ProgressBar
var health_bar: ProgressBar
var xp_bar: ProgressBar
var status_label: Label
var health_value_label: Label
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
var game_over_panel: PanelContainer
var game_over_summary: Label
var low_health_panel: PanelContainer
var low_health_label: Label
var threat_panel: PanelContainer
var threat_label: Label
var pause_panel: PanelContainer
var pause_button: Button
var master_volume: HSlider
var sfx_volume: HSlider
var impact_flash: ColorRect
var impact_flash_tween: Tween
var damage_vignette: ColorRect
var damage_vignette_tween: Tween
var touch_stick_root: Control
var touch_stick_knob: Control

func _ready() -> void:
    process_mode = Node.PROCESS_MODE_ALWAYS
    _build()

func pulse_damage_screen() -> void:
    if damage_vignette == null:
        return
    if damage_vignette_tween != null and damage_vignette_tween.is_valid():
        damage_vignette_tween.kill()
    damage_vignette.visible = true
    damage_vignette.modulate.a = 1.0
    damage_vignette_tween = damage_vignette.create_tween()
    damage_vignette_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    damage_vignette_tween.tween_property(damage_vignette, "modulate:a", 0.0, 0.26).set_trans(Tween.TRANS_QUAD).set_ease(Tween.EASE_OUT)
    damage_vignette_tween.tween_callback(func() -> void:
        if damage_vignette != null:
            damage_vignette.visible = false
            damage_vignette.modulate.a = 1.0
    )

func set_health(value: float, maximum: float) -> void:
    hp_bar.max_value = max(1.0, maximum)
    hp_bar.value = value
    if health_value_label != null:
        health_value_label.text = "HP %d / %d" % [int(round(value)), int(round(maximum))]
    var ratio: float = clampf(value / max(1.0, maximum), 0.0, 1.0)
    low_health_panel.visible = value > 0.0 and ratio <= 0.30
    if low_health_panel.visible:
        low_health_label.text = "CRITICAL INTEGRITY  •  %d%%" % int(round(ratio * 100.0))

func set_progress(xp: int, next_xp: int, level: int, kills: int, elapsed: float, threats := 0) -> void:
    xp_bar.max_value = max(1, next_xp)
    xp_bar.value = xp
    status_label.text = "LV %d   KILLS %d   THREATS %d   %02d:%02d" % [level, kills, threats, int(elapsed) / 60, int(elapsed) % 60]

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

func show_touch_stick(origin: Vector2) -> void:
    if touch_stick_root == null or touch_stick_knob == null:
        return
    touch_stick_root.position = origin - touch_stick_root.size * 0.5
    touch_stick_knob.position = (touch_stick_root.size - touch_stick_knob.size) * 0.5
    touch_stick_root.visible = true

func update_touch_stick(origin: Vector2, current: Vector2) -> void:
    if touch_stick_root == null or touch_stick_knob == null:
        return
    if not touch_stick_root.visible:
        show_touch_stick(origin)
    var input_vector := ((current - origin) / 90.0).limit_length(1.0)
    var travel_radius := (touch_stick_root.size.x - touch_stick_knob.size.x) * 0.5 - 4.0
    var displacement := input_vector * maxf(travel_radius, 0.0)
    touch_stick_knob.position = (touch_stick_root.size - touch_stick_knob.size) * 0.5 + displacement

func hide_touch_stick() -> void:
    if touch_stick_root != null:
        touch_stick_root.visible = false

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

func show_pause_settings() -> void:
    pause_panel.visible = true

func hide_pause_settings() -> void:
    pause_panel.visible = false

func show_impact_flash(critical: bool, killed: bool, boss: bool) -> void:
    if impact_flash == null:
        return
    if impact_flash_tween != null and impact_flash_tween.is_valid():
        impact_flash_tween.kill()
    var alpha := 0.055
    var tint := Color(0.68, 0.90, 1.0, alpha)
    if critical:
        alpha = 0.10
        tint = Color(1.0, 0.74, 0.20, alpha)
    if killed:
        alpha = maxf(alpha, 0.13)
        tint = Color(1.0, 0.38, 0.16, alpha)
    if boss:
        alpha = maxf(alpha, 0.18)
        tint = Color(1.0, 0.12, 0.055, alpha)
    impact_flash.color = tint
    impact_flash.visible = true
    impact_flash_tween = create_tween()
    impact_flash_tween.set_pause_mode(Tween.TWEEN_PAUSE_PROCESS)
    impact_flash_tween.tween_property(impact_flash, "color:a", 0.0, 0.16 if boss else 0.11)
    impact_flash_tween.tween_callback(func() -> void:
        if impact_flash != null:
            impact_flash.visible = false
    )

func show_game_over(kills: int, level: int, elapsed: float) -> void:
    wave_label.text = "RUN TERMINATED"
    upgrade_panel.visible = false
    boss_panel.visible = false
    var minutes := int(elapsed) / 60
    var seconds := int(elapsed) % 60
    game_over_summary.text = "LEVEL %d   •   KILLS %d   •   %02d:%02d" % [level, kills, minutes, seconds]
    low_health_panel.visible = false
    threat_panel.visible = false
    game_over_panel.visible = true

func _build() -> void:
    var root := Control.new()
    root.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    add_child(root)

    impact_flash = ColorRect.new()
    impact_flash.name = "ImpactFlash"
    impact_flash.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    impact_flash.color = Color(1.0, 1.0, 1.0, 0.0)
    impact_flash.mouse_filter = Control.MOUSE_FILTER_IGNORE
    impact_flash.visible = false
    root.add_child(impact_flash)

    damage_vignette = ColorRect.new()
    damage_vignette.name = "DamageVignette"
    damage_vignette.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    damage_vignette.color = Color(0.58, 0.015, 0.0, 0.30)
    damage_vignette.mouse_filter = Control.MOUSE_FILTER_IGNORE
    damage_vignette.visible = false
    root.add_child(damage_vignette)

    touch_stick_root = Control.new()
    touch_stick_root.name = "TouchStick"
    touch_stick_root.size = Vector2(112.0, 112.0)
    touch_stick_root.mouse_filter = Control.MOUSE_FILTER_IGNORE
    touch_stick_root.visible = false
    root.add_child(touch_stick_root)

    var stick_base := Panel.new()
    stick_base.name = "TouchStickBase"
    stick_base.position = Vector2.ZERO
    stick_base.size = touch_stick_root.size
    stick_base.mouse_filter = Control.MOUSE_FILTER_IGNORE
    var stick_base_style := StyleBoxFlat.new()
    stick_base_style.bg_color = Color(0.015, 0.035, 0.045, 0.34)
    stick_base_style.border_color = Color(0.18, 0.78, 1.0, 0.52)
    stick_base_style.set_border_width_all(2)
    stick_base_style.corner_radius_top_left = 56
    stick_base_style.corner_radius_top_right = 56
    stick_base_style.corner_radius_bottom_left = 56
    stick_base_style.corner_radius_bottom_right = 56
    stick_base.add_theme_stylebox_override("panel", stick_base_style)
    touch_stick_root.add_child(stick_base)

    touch_stick_knob = Panel.new()
    touch_stick_knob.name = "TouchStickKnob"
    touch_stick_knob.size = Vector2(42.0, 42.0)
    touch_stick_knob.position = (touch_stick_root.size - touch_stick_knob.size) * 0.5
    touch_stick_knob.mouse_filter = Control.MOUSE_FILTER_IGNORE
    var stick_knob_style := StyleBoxFlat.new()
    stick_knob_style.bg_color = Color(0.10, 0.62, 0.88, 0.74)
    stick_knob_style.border_color = Color(0.54, 0.92, 1.0, 0.88)
    stick_knob_style.set_border_width_all(2)
    stick_knob_style.corner_radius_top_left = 21
    stick_knob_style.corner_radius_top_right = 21
    stick_knob_style.corner_radius_bottom_left = 21
    stick_knob_style.corner_radius_bottom_right = 21
    touch_stick_knob.add_theme_stylebox_override("panel", stick_knob_style)
    touch_stick_root.add_child(touch_stick_knob)

    var vital_panel := PanelContainer.new()
    vital_panel.name = "VitalPanel"
    vital_panel.position = Vector2(22, 18)
    vital_panel.size = Vector2(360, 86)
    vital_panel.custom_minimum_size = Vector2(340, 82)
    vital_panel.mouse_filter = Control.MOUSE_FILTER_IGNORE
    root.add_child(vital_panel)

    var vital_style := StyleBoxFlat.new()
    vital_style.bg_color = Color(0.010, 0.020, 0.028, 0.94)
    vital_style.border_color = Color(0.12, 0.52, 0.72, 0.62)
    vital_style.set_border_width_all(1)
    vital_style.border_width_left = 3
    vital_style.corner_radius_top_left = 6
    vital_style.corner_radius_top_right = 6
    vital_style.corner_radius_bottom_left = 6
    vital_style.corner_radius_bottom_right = 6
    vital_style.content_margin_left = 12.0
    vital_style.content_margin_right = 11.0
    vital_style.content_margin_top = 6.0
    vital_style.content_margin_bottom = 6.0
    vital_panel.add_theme_stylebox_override("panel", vital_style)

    var vital_stack := VBoxContainer.new()
    vital_stack.name = "VitalStack"
    vital_stack.add_theme_constant_override("separation", 3)
    vital_panel.add_child(vital_stack)

    var header_row := HBoxContainer.new()
    header_row.name = "CombatHeader"
    vital_stack.add_child(header_row)

    var combat_link := Label.new()
    combat_link.name = "CombatLinkLabel"
    combat_link.text = "SURVIVOR // COMBAT LINK"
    combat_link.add_theme_font_size_override("font_size", 11)
    combat_link.modulate = Color(0.28, 0.78, 0.96)
    header_row.add_child(combat_link)

    var header_spacer := Control.new()
    header_spacer.size_flags_horizontal = Control.SIZE_EXPAND_FILL
    header_row.add_child(header_spacer)

    var signal_label := Label.new()
    signal_label.name = "SignalLabel"
    signal_label.text = "ONLINE"
    signal_label.add_theme_font_size_override("font_size", 10)
    signal_label.modulate = Color(0.48, 0.92, 0.68)
    header_row.add_child(signal_label)

    var vital_accent := ColorRect.new()
    vital_accent.name = "VitalAccent"
    vital_accent.color = Color(0.10, 0.58, 0.80, 0.90)
    vital_accent.custom_minimum_size = Vector2(74, 2)
    vital_accent.mouse_filter = Control.MOUSE_FILTER_IGNORE
    vital_stack.add_child(vital_accent)

    hp_bar = ProgressBar.new()
    hp_bar.name = "HealthBar"
    hp_bar.custom_minimum_size = Vector2(320, 18)
    hp_bar.show_percentage = false
    var hp_bg := StyleBoxFlat.new()
    hp_bg.bg_color = Color(0.06, 0.075, 0.085, 0.94)
    hp_bg.corner_radius_top_left = 3
    hp_bg.corner_radius_top_right = 3
    hp_bg.corner_radius_bottom_left = 3
    hp_bg.corner_radius_bottom_right = 3
    var hp_fill := StyleBoxFlat.new()
    hp_fill.bg_color = Color(0.16, 0.72, 0.88, 0.98)
    hp_fill.corner_radius_top_left = 3
    hp_fill.corner_radius_top_right = 3
    hp_fill.corner_radius_bottom_left = 3
    hp_fill.corner_radius_bottom_right = 3
    hp_bar.add_theme_stylebox_override("background", hp_bg)
    hp_bar.add_theme_stylebox_override("fill", hp_fill)
    vital_stack.add_child(hp_bar)
    health_bar = hp_bar

    health_value_label = Label.new()
    health_value_label.name = "HealthValueLabel"
    health_value_label.text = "HP 100 / 100"
    health_value_label.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    health_value_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    health_value_label.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
    health_value_label.add_theme_font_size_override("font_size", 10)
    health_value_label.add_theme_color_override("font_color", Color(0.94, 0.98, 1.0))
    health_value_label.add_theme_color_override("font_shadow_color", Color(0.0, 0.0, 0.0, 0.82))
    health_value_label.add_theme_constant_override("shadow_offset_x", 1)
    health_value_label.add_theme_constant_override("shadow_offset_y", 1)
    health_value_label.mouse_filter = Control.MOUSE_FILTER_IGNORE
    hp_bar.add_child(health_value_label)

    xp_bar = ProgressBar.new()
    xp_bar.name = "XpBar"
    xp_bar.custom_minimum_size = Vector2(320, 8)
    xp_bar.show_percentage = false
    var xp_bg := StyleBoxFlat.new()
    xp_bg.bg_color = Color(0.045, 0.055, 0.065, 0.90)
    xp_bg.corner_radius_top_left = 2
    xp_bg.corner_radius_top_right = 2
    xp_bg.corner_radius_bottom_left = 2
    xp_bg.corner_radius_bottom_right = 2
    var xp_fill := StyleBoxFlat.new()
    xp_fill.bg_color = Color(0.52, 0.36, 0.92, 0.96)
    xp_fill.corner_radius_top_left = 2
    xp_fill.corner_radius_top_right = 2
    xp_fill.corner_radius_bottom_left = 2
    xp_fill.corner_radius_bottom_right = 2
    xp_bar.add_theme_stylebox_override("background", xp_bg)
    xp_bar.add_theme_stylebox_override("fill", xp_fill)
    vital_stack.add_child(xp_bar)

    status_label = Label.new()
    status_label.name = "CombatStatus"
    status_label.text = "LV 1   KILLS 0"
    status_label.add_theme_font_size_override("font_size", 15)
    status_label.modulate = Color(0.82, 0.90, 0.94)
    vital_stack.add_child(status_label)

    var wave_panel := PanelContainer.new()
    wave_panel.name = "WavePanel"
    wave_panel.set_anchors_preset(Control.PRESET_CENTER_TOP)
    wave_panel.position = Vector2(-170, 18)
    wave_panel.size = Vector2(340, 42)
    wave_panel.mouse_filter = Control.MOUSE_FILTER_IGNORE
    root.add_child(wave_panel)
    var wave_style := StyleBoxFlat.new()
    wave_style.bg_color = Color(0.010, 0.020, 0.028, 0.76)
    wave_style.border_color = Color(0.16, 0.56, 0.72, 0.46)
    wave_style.border_width_bottom = 2
    wave_style.corner_radius_bottom_left = 7
    wave_style.corner_radius_bottom_right = 7
    wave_panel.add_theme_stylebox_override("panel", wave_style)

    wave_label = Label.new()
    wave_label.name = "WaveLabel"
    wave_label.text = "QUARANTINE YARD"
    wave_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    wave_label.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
    wave_label.add_theme_font_size_override("font_size", 21)
    wave_label.modulate = Color(0.90, 0.95, 0.98)
    wave_panel.add_child(wave_label)

    pause_button = Button.new()
    pause_button.name = "PauseButton"
    pause_button.text = "Ⅱ"
    pause_button.set_anchors_preset(Control.PRESET_TOP_RIGHT)
    pause_button.position = Vector2(-68, 18)
    pause_button.size = Vector2(46, 46)
    pause_button.add_theme_font_size_override("font_size", 18)
    var pause_normal := StyleBoxFlat.new()
    pause_normal.bg_color = Color(0.010, 0.020, 0.028, 0.90)
    pause_normal.border_color = Color(0.16, 0.56, 0.72, 0.46)
    pause_normal.set_border_width_all(1)
    pause_normal.corner_radius_top_left = 6
    pause_normal.corner_radius_top_right = 6
    pause_normal.corner_radius_bottom_left = 6
    pause_normal.corner_radius_bottom_right = 6
    var pause_hover := pause_normal.duplicate()
    pause_hover.bg_color = Color(0.025, 0.075, 0.10, 0.96)
    pause_hover.border_color = Color(0.24, 0.78, 1.0, 0.78)
    pause_button.add_theme_stylebox_override("normal", pause_normal)
    pause_button.add_theme_stylebox_override("hover", pause_hover)
    pause_button.add_theme_stylebox_override("pressed", pause_hover)
    pause_button.add_theme_color_override("font_color", Color(0.78, 0.90, 0.96))
    pause_button.pressed.connect(func() -> void: pause_requested.emit())
    add_child(pause_button)

    pause_panel = PanelContainer.new()
    pause_panel.name = "PausePanel"
    pause_panel.set_anchors_preset(Control.PRESET_CENTER)
    pause_panel.position = Vector2(-250, -210)
    pause_panel.size = Vector2(500, 420)
    pause_panel.visible = false
    add_child(pause_panel)
    var pause_box := VBoxContainer.new()
    pause_box.alignment = BoxContainer.ALIGNMENT_CENTER
    pause_box.add_theme_constant_override("separation", 18)
    pause_panel.add_child(pause_box)
    var pause_title := Label.new()
    pause_title.text = "SYSTEM PAUSED"
    pause_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    pause_title.add_theme_font_size_override("font_size", 30)
    pause_title.modulate = Color(0.72, 0.92, 1.0)
    pause_box.add_child(pause_title)
    var master_label := Label.new()
    master_label.text = "MASTER VOLUME"
    master_label.add_theme_font_size_override("font_size", 16)
    pause_box.add_child(master_label)
    master_volume = HSlider.new()
    master_volume.name = "MasterVolume"
    master_volume.min_value = 0.0
    master_volume.max_value = 1.0
    master_volume.step = 0.05
    master_volume.value = 0.85
    master_volume.custom_minimum_size = Vector2(360, 42)
    master_volume.value_changed.connect(func(value: float) -> void: master_volume_changed.emit(value))
    pause_box.add_child(master_volume)
    var sfx_label := Label.new()
    sfx_label.text = "SFX VOLUME"
    sfx_label.add_theme_font_size_override("font_size", 16)
    pause_box.add_child(sfx_label)
    sfx_volume = HSlider.new()
    sfx_volume.name = "SfxVolume"
    sfx_volume.min_value = 0.0
    sfx_volume.max_value = 1.0
    sfx_volume.step = 0.05
    sfx_volume.value = 0.90
    sfx_volume.custom_minimum_size = Vector2(360, 42)
    sfx_volume.value_changed.connect(func(value: float) -> void: sfx_volume_changed.emit(value))
    pause_box.add_child(sfx_volume)
    var resume_button := Button.new()
    resume_button.name = "ResumeButton"
    resume_button.text = "RESUME"
    resume_button.custom_minimum_size = Vector2(280, 62)
    resume_button.add_theme_font_size_override("font_size", 21)
    resume_button.pressed.connect(func() -> void: resume_requested.emit())
    pause_box.add_child(resume_button)
    var pause_style := StyleBoxFlat.new()
    pause_style.bg_color = Color(0.018, 0.028, 0.038, 0.98)
    pause_style.border_color = Color(0.20, 0.78, 1.0, 0.72)
    pause_style.set_border_width_all(2)
    pause_style.corner_radius_top_left = 12
    pause_style.corner_radius_top_right = 12
    pause_style.corner_radius_bottom_left = 12
    pause_style.corner_radius_bottom_right = 12
    pause_panel.add_theme_stylebox_override("panel", pause_style)

    low_health_panel = PanelContainer.new()
    low_health_panel.name = "LowHealthPanel"
    low_health_panel.set_anchors_preset(Control.PRESET_CENTER_BOTTOM)
    low_health_panel.position = Vector2(-210, -92)
    low_health_panel.size = Vector2(420, 52)
    low_health_panel.visible = false
    low_health_panel.mouse_filter = Control.MOUSE_FILTER_IGNORE
    root.add_child(low_health_panel)
    low_health_label = Label.new()
    low_health_label.name = "LowHealthLabel"
    low_health_label.text = "CRITICAL INTEGRITY"
    low_health_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    low_health_label.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
    low_health_label.add_theme_font_size_override("font_size", 19)
    low_health_label.modulate = Color(1.0, 0.58, 0.44)
    low_health_panel.add_child(low_health_label)
    var low_health_style := StyleBoxFlat.new()
    low_health_style.bg_color = Color(0.16, 0.015, 0.01, 0.96)
    low_health_style.border_color = Color(1.0, 0.18, 0.08, 0.98)
    low_health_style.set_border_width_all(2)
    low_health_style.corner_radius_top_left = 8
    low_health_style.corner_radius_top_right = 8
    low_health_style.corner_radius_bottom_left = 8
    low_health_style.corner_radius_bottom_right = 8
    low_health_panel.add_theme_stylebox_override("panel", low_health_style)

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
    threat_label.text = "→  ELITE  18m"
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

    game_over_panel = PanelContainer.new()
    game_over_panel.set_anchors_preset(Control.PRESET_CENTER)
    game_over_panel.position = Vector2(-270, -120)
    game_over_panel.size = Vector2(540, 240)
    game_over_panel.visible = false
    root.add_child(game_over_panel)
    var game_over_box := VBoxContainer.new()
    game_over_box.alignment = BoxContainer.ALIGNMENT_CENTER
    game_over_box.add_theme_constant_override("separation", 16)
    game_over_panel.add_child(game_over_box)
    var game_over_title := Label.new()
    game_over_title.text = "SIGNAL LOST"
    game_over_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    game_over_title.add_theme_font_size_override("font_size", 34)
    game_over_title.modulate = Color(1.0, 0.34, 0.20)
    game_over_box.add_child(game_over_title)
    game_over_summary = Label.new()
    game_over_summary.text = "LEVEL 1   •   KILLS 0   •   00:00"
    game_over_summary.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    game_over_summary.add_theme_font_size_override("font_size", 18)
    game_over_summary.modulate = Color(0.82, 0.88, 0.92)
    game_over_box.add_child(game_over_summary)
    var restart_button := Button.new()
    restart_button.name = "RestartButton"
    restart_button.text = "REDEPLOY"
    restart_button.custom_minimum_size = Vector2(260, 58)
    restart_button.add_theme_font_size_override("font_size", 21)
    restart_button.pressed.connect(func() -> void: restart_requested.emit())
    game_over_box.add_child(restart_button)
    var game_over_style := StyleBoxFlat.new()
    game_over_style.bg_color = Color(0.018, 0.026, 0.034, 0.97)
    game_over_style.border_color = Color(1.0, 0.22, 0.10, 0.78)
    game_over_style.set_border_width_all(2)
    game_over_style.corner_radius_top_left = 10
    game_over_style.corner_radius_top_right = 10
    game_over_style.corner_radius_bottom_left = 10
    game_over_style.corner_radius_bottom_right = 10
    game_over_panel.add_theme_stylebox_override("panel", game_over_style)

    boss_panel = PanelContainer.new()
    boss_panel.name = "BossPanel"
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
    upgrade_panel.name = "UpgradePanel"
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
        "berserker": return "✦"
        "overclock": return "⚡"
        "fortress": return "⬢"
        "scatter_protocol": return "⋰"
        "rail_protocol": return "━"
        "inferno_protocol": return "▲"
        "cryo_protocol": return "◇"
        "arc_protocol": return "⌁"
        _: return "◆"

func _upgrade_color(id: String) -> Color:
    match id:
        "damage", "multishot": return Color(1.0, 0.66, 0.18)
        "rate", "speed": return Color(0.18, 0.86, 1.0)
        "health": return Color(0.32, 0.94, 0.52)
        "projectile": return Color(0.76, 0.82, 1.0)
        "berserker": return Color(1.0, 0.22, 0.12)
        "overclock": return Color(1.0, 0.82, 0.18)
        "fortress": return Color(0.38, 0.86, 0.72)
        "scatter_protocol": return Color(1.0, 0.56, 0.18)
        "rail_protocol": return Color(0.72, 0.58, 1.0)
        "inferno_protocol": return Color(1.0, 0.24, 0.035)
        "cryo_protocol": return Color(0.30, 0.90, 1.0)
        "arc_protocol": return Color(0.64, 0.42, 1.0)
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
