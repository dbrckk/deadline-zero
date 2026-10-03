extends SceneTree

func _initialize() -> void:
    var listing := FileAccess.get_file_as_string("../play/store/LISTING.md")
    if listing.is_empty():
        push_error("Godot Play listing contract is missing")
        quit(1)
        return

    var required := [
        "8 enemy archetypes",
        "three-phase boss",
        "6 weapon profiles and protocols",
        "14 run upgrades",
        "Vanguard, Scatter, Rail, Inferno, Cryo, and Arc"
    ]
    for token in required:
        if not listing.contains(token):
            push_error("Godot Play listing is missing verified runtime claim: %s" % token)
            quit(1)
            return

    var forbidden := [
        "Multiple survivors",
        "12+ weapons",
        "50+ run upgrades",
        "5 biomes",
        "20+ enemy gameplay profiles",
        "6 multi-phase bosses",
        "Persistent progression"
    ]
    for token in forbidden:
        if listing.contains(token):
            push_error("Godot Play listing still contains unsupported legacy claim: %s" % token)
            quit(1)
            return

    var data_safety := FileAccess.get_file_as_string("../play/store/DATA_SAFETY.md")
    if not data_safety.contains("not present in the current Godot release candidate"):
        push_error("Data Safety contract does not distinguish legacy SDKs from Godot release")
        quit(1)
        return
    if not data_safety.contains("no Google Mobile Ads") and not data_safety.contains("No Google Mobile Ads"):
        push_error("Data Safety contract does not state the current no-ads SDK state")
        quit(1)
        return

    print("Deadline Zero Godot Play Store claims: OK")
    quit(0)
