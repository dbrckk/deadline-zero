# Google Play release procedure

Use this procedure only for a Godot bundle intended for Play Console upload.

## Current production path

The shipping Android runtime is the Godot project under `godot/`.

Use the `Android Play Release` export preset. It is configured for:

- package `com.deadlinezero.game`
- app name `Deadline: Zero`
- AAB output
- arm64-v8a only
- min SDK 26
- target SDK 36
- version code 1 / version name 0.1.0

The `Godot Play AAB Verify` workflow proves the mechanical Gradle/AAB path using an ephemeral CI-only keystore. That CI key is never a production credential.

## Production signing

Never commit the Play upload keystore or passwords.

For a production Godot export, supply:

- `GODOT_ANDROID_KEYSTORE_RELEASE_PATH`
- `GODOT_ANDROID_KEYSTORE_RELEASE_USER`
- `GODOT_ANDROID_KEYSTORE_RELEASE_PASSWORD`

The keystore password and key password must satisfy Godot/Android signing requirements for the exact release toolchain.

## Build environment

Godot AAB export requires the Gradle Android build template. Install it before release export:

```bash
godot --headless --path godot --editor --install-android-build-template --quit
```

Then export:

```bash
godot --headless --path godot \
  --export-release "Android Play Release" \
  build/godot-play/deadline-zero-play-release.aab
```

The build machine must provide Android 16 / API 36 platform support and the Java/Android SDK versions required by Godot 4.7.2.

## Store graphics

Final authored listing assets are defined in `docs/STORE_RELEASE.md` and `play/store/README.md`.

The `Godot Play Screenshots` workflow generates:

- three 1920×1080 gameplay screenshot candidates;
- a 512×512 authored 3D icon candidate;
- a 1024×500 authored 3D feature-graphic candidate.

These outputs are candidates only until visual review. Do not promote temporary/mock content to the final Store filenames.

## Current services/data scope

The current Godot release candidate contains no advertising, consent SDK, Play Billing, Play Games Services, In-App Review, analytics, account, cloud-save, or developer-operated backend integration.

Use `play/store/DATA_SAFETY.md` and `play/store/PLAY_CONSOLE.md` for the exact current declaration scope. Do not reuse legacy libGDX monetization/service assumptions for the Godot AAB.

## Versioning

Increment the Godot preset `version/code` before every Play upload. Google Play requires each uploaded artifact for the same package to use a strictly newer version code.

Keep `version/name`, `play/store/RELEASE_NOTES.md`, and release evidence synchronized with the exact AAB.

## Pre-upload checks

Before Play Console upload:

1. Require green Godot runtime, rendered-frame, Android emulator, and Play AAB verification workflows for the exact commit.
2. Review and promote only visually approved icon, feature graphic, and screenshots.
3. Verify the AAB package ID, target SDK, ABI, checksum, and signing certificate.
4. Run a complete player-controlled session on representative physical Android hardware.
5. Validate background/foreground lifecycle, audio, rotation lock, and process recreation.
6. Confirm the public privacy policy matches the current no-SDK/no-backend Godot data flows.
7. Complete Play Data Safety, app access, target audience, and IARC declarations from the exact shipping build.
8. Upload to the intended testing track and review Play pre-launch findings.
9. Increment the version code for every subsequent artifact.
10. Back up the upload keystore securely outside the repository.
