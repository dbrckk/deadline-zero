class_name DZHud
extends CanvasLayer

signal upgrade_chosen(index: int)
signal restart_requested
signal pause_requested
signal resume_requested
signal master_volume_changed(value: float)
signal sfx_volume_changed(value: float)

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
    var ratio: float = clampf(value / max(1.0, maximum), 0.0, 1.0)
    low_health_panel.visible = value > 0.0 and ratio <= 0.30
    if low_health_panel.visible:
        low_health_label.text = "CRITICAL INTEGRITY  •  %d%%" % int(round(ratio * 100.0))

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

    pause_button = Button.new()
    pause_button.name = "PauseButton"
    pause_button.text = "Ⅱ"
    pause_button.set_anchors_preset(Control.PRESET_TOP_RIGHT)
    pause_button.position = Vector2(-86, 24)
    pause_button.size = Vector2(58, 58)
    pause_button.add_theme_font_size_override("font_size", 22)
    pause_button.pressed.connect(func() -> void:
        pause_requested.emit()
    )
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
    master_volume.value_changed.connect(func(value: float) -> void:
        master_volume_changed.emit(value)
    )
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
    sfx_volume.value_changed.connect(func(value: float) -> void:
        sfx_volume_changed.emit(value)
    )
    pause_box.add_child(sfx_volume)

    var resume_button := Button.new()
    resume_button.name = "ResumeButton"
    resume_button.text = "RESUME"
    resume_button.custom_minimum_size = Vector2(280, 62)
    resume_button.add_theme_font_size_override("font_size", 21)
    resume_button.pressed.connect(func() -> void:
        resume_requested.emit()
    )
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
    low_health_style.bg_color = Color(0.16, 0.015, 0.01, 0.88)
    low_health_style.border_color = Color(1.0, 0.18, 0.08, 0.92)
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
    restart_button.text = "REDEPLOY"
    restart_button.custom_minimum_size = Vector2(280, 64)
    restart_button.add_theme_font_size_override("font_size", 21)
    restart_button.pressed.connect(func() -> void:
        restart_requested.emit()
    )
    game_over_box.add_child(restart_button)

    boss_panel = PanelContainer.new()
    boss_panel.name = "BossPanel"
    boss_panel.set_anchors_preset(Control.PRESET_CENTER_TOP)
    boss_panel.position = Vector2(-320, 82)
    boss_panel.size = Vector2(640, 92)
    boss_panel.visible = false
    root.add_child(boss_panel)

    var boss_box := VBoxContainer.new()
    boss_box.add_theme_constant_override("separation", 4)
    boss_panel.add_child(boss_box)

    boss_name_label = Label.new()
    boss_name_label.name = "BossName"
    boss_name_label.text = "REVENANT PRIME"
    boss_name_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    boss_name_label.add_theme_font_size_override("font_size", 20)
    boss_name_label.modulate = Color(1.0, 0.58, 0.34)
    boss_box.add_child(boss_name_label)

    boss_phase_label = Label.new()
    boss_phase_label.name = "BossPhase"
    boss_phase_label.text = "THREAT LOCK"
    boss_phase_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    boss_phase_label.add_theme_font_size_override("font_size", 13)
    boss_phase_label.modulate = Color(1.0, 0.76, 0.54)
    boss_box.add_child(boss_phase_label)

    boss_hp_bar = ProgressBar.new()
    boss_hp_bar.name = "BossHP"
    boss_hp_bar.show_percentage = false
    boss_hp_bar.custom_minimum_size = Vector2(600, 18)
    boss_box.add_child(boss_hp_bar)

    upgrade_panel = PanelContainer.new()
    upgrade_panel.set_anchors_preset(Control.PRESET_CENTER)
    upgrade_panel.position = Vector2(-520, -210)
    upgrade_panel.size = Vector2(1040, 420)
    upgrade_panel.visible = false
    root.add_child(upgrade_panel)

    var upgrade_box := VBoxContainer.new()
    upgrade_box.add_theme_constant_override("separation", 16)
    upgrade_panel.add_child(upgrade_box)

    var title := Label.new()
    title.text = "TACTICAL UPLINK // SELECT ONE"
    title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
    title.add_theme_font_size_override("font_size", 24)
    title.modulate = Color(0.72, 0.92, 1.0)
    upgrade_box.add_child(title)

    var choices := HBoxContainer.new()
    choices.alignment = BoxContainer.ALIGNMENT_CENTER
    choices.add_theme_constant_override("separation", 18)
    upgrade_box.add_child(choices)

    for i in range(3):
        var card := VBoxContainer.new()
        card.name = "UpgradeCard%d" % i
        card.custom_minimum_size = Vector2(300, 250)
        card.add_theme_constant_override("separation", 10)
        choices.add_child(card)
        upgrade_cards.append(card)

        var family := Label.new()
        family.name = "Family"
        family.text = "UPGRADE"
        family.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        family.add_theme_font_size_override("font_size", 14)
        family.modulate = Color(0.55, 0.82, 1.0)
        card.add_child(family)
        upgrade_family_labels.append(family)

        var button := Button.new()
        button.name = "UpgradeButton%d" % i
        button.text = "◆"
        button.custom_minimum_size = Vector2(260, 92)
        button.add_theme_font_size_override("font_size", 42)
        button.pressed.connect(func() -> void:
            upgrade_chosen.emit(i)
        )
        card.add_child(button)
        upgrade_buttons.append(button)

        var card_title := Label.new()
        card_title.name = "Title"
        card_title.text = "UPGRADE"
        card_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        card_title.add_theme_font_size_override("font_size", 19)
        card.add_child(card_title)
        upgrade_title_labels.append(card_title)

        var detail := Label.new()
        detail.name = "Detail"
        detail.text = ""
        detail.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
        detail.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
        detail.custom_minimum_size = Vector2(280, 52)
        detail.add_theme_font_size_override("font_size", 15)
        detail.modulate = Color(0.78, 0.84, 0.88)
        card.add_child(detail)
        upgrade_detail_labels.append(detail)

func _upgrade_glyph(id: String) -> String:
    match id:
        "damage": return "✦"
        "rate": return "»"
        "speed": return "➤"
        "health": return "✚"
        "projectile": return "➟"
        "multishot": return "⋮"
        "berserker": return "⚡"
        "overclock": return "⟳"
        "fortress": return "⬢"
        "scatter_protocol": return "⋰"
        "rail_protocol": return "━"
        "inferno_protocol": return "▲"
        "cryo_protocol": return "◇"
        "arc_protocol": return "ϟ"
        _: return "◆"

func _style_upgrade_card(index: int, id: String) -> void:
    if index < 0 or index >= upgrade_cards.size():
        return
    var family_color := Color(0.22, 0.78, 1.0)
    if id in ["damage", "berserker", "rail_protocol"]:
        family_color = Color(1.0, 0.35, 0.16)
    elif id in ["rate", "overclock", "multishot", "scatter_protocol"]:
        family_color = Color(1.0, 0.72, 0.18)
    elif id in ["health", "fortress"]:
        family_color = Color(0.30, 1.0, 0.52)
    elif id in ["speed", "projectile"]:
        family_color = Color(0.22, 0.82, 1.0)
    elif id == "inferno_protocol":
        family_color = Color(1.0, 0.24, 0.08)
    elif id == "cryo_protocol":
        family_color = Color(0.22, 0.90, 1.0)
    elif id == "arc_protocol":
        family_color = Color(0.72, 0.46, 1.0)
    upgrade_family_labels[index].modulate = family_color
    upgrade_title_labels[index].modulate = family_color.lightened(0.12)
    upgrade_buttons[index].modulate = family_color
