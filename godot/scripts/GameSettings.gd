class_name DZGameSettings
extends RefCounted

const DEFAULTS := {
    "master_volume": 0.85,
    "sfx_volume": 0.90,
    "haptics_enabled": true,
    "reduced_flashes": false
}

static func save(path: String, settings: Dictionary) -> Error:
    var config := ConfigFile.new()
    config.set_value("audio", "master_volume", clampf(float(settings.get("master_volume", DEFAULTS["master_volume"])), 0.0, 1.0))
    config.set_value("audio", "sfx_volume", clampf(float(settings.get("sfx_volume", DEFAULTS["sfx_volume"])), 0.0, 1.0))
    config.set_value("comfort", "haptics_enabled", bool(settings.get("haptics_enabled", DEFAULTS["haptics_enabled"])))
    config.set_value("comfort", "reduced_flashes", bool(settings.get("reduced_flashes", DEFAULTS["reduced_flashes"])))
    return config.save(path)

static func load_settings(path: String) -> Dictionary:
    var result := DEFAULTS.duplicate(true)
    var config := ConfigFile.new()
    if config.load(path) != OK:
        return result
    result["master_volume"] = clampf(float(config.get_value("audio", "master_volume", DEFAULTS["master_volume"])), 0.0, 1.0)
    result["sfx_volume"] = clampf(float(config.get_value("audio", "sfx_volume", DEFAULTS["sfx_volume"])), 0.0, 1.0)
    result["haptics_enabled"] = bool(config.get_value("comfort", "haptics_enabled", DEFAULTS["haptics_enabled"]))
    result["reduced_flashes"] = bool(config.get_value("comfort", "reduced_flashes", DEFAULTS["reduced_flashes"]))
    return result
