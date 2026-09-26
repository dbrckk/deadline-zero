class_name DZGameSettings
extends RefCounted

const DEFAULTS := {
    "master_volume": 0.85,
    "sfx_volume": 0.90
}

static func save(path: String, settings: Dictionary) -> Error:
    var config := ConfigFile.new()
    config.set_value("audio", "master_volume", clampf(float(settings.get("master_volume", DEFAULTS["master_volume"])), 0.0, 1.0))
    config.set_value("audio", "sfx_volume", clampf(float(settings.get("sfx_volume", DEFAULTS["sfx_volume"])), 0.0, 1.0))
    return config.save(path)

static func load_settings(path: String) -> Dictionary:
    var result := DEFAULTS.duplicate(true)
    var config := ConfigFile.new()
    if config.load(path) != OK:
        return result
    result["master_volume"] = clampf(float(config.get_value("audio", "master_volume", DEFAULTS["master_volume"])), 0.0, 1.0)
    result["sfx_volume"] = clampf(float(config.get_value("audio", "sfx_volume", DEFAULTS["sfx_volume"])), 0.0, 1.0)
    return result
