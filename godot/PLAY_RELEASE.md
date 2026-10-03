# Godot Play release contract

The Godot runtime now has a dedicated Google Play export preset named `Android Play Release`.

## Mechanical release configuration

- Package ID: `com.deadlinezero.game`
- Display name: `Deadline: Zero`
- Version code: `1`
- Version name: `0.1.0`
- Minimum SDK: 26
- Target SDK: 36
- Distribution format: Android App Bundle (AAB)
- Production ABI: arm64-v8a only
- Gradle export: required/enabled
- Backup: disabled
- Immersive/edge-to-edge: enabled

The `Godot Play AAB Verify` workflow installs the Godot Gradle build template at CI time, signs the bundle with an ephemeral CI-only keystore, and checks the package ID, AAB signature, native ABI contents and checksum.

## Production signing rule

The real Play upload keystore must never be committed to this repository. Production export must supply the release keystore path, alias and password through protected release secrets/environment variables.

## Remaining visual release gate

Do **not** publish the Godot AAB as a final Play release until authored premium launcher assets are present and wired into `export_presets.cfg`:

- main launcher icon, 192x192 source
- adaptive foreground, 432x432
- adaptive background, 432x432
- adaptive monochrome, 432x432

Godot can fall back to project/default icons, but that fallback is acceptable only for mechanical CI verification, not for the production store build.
