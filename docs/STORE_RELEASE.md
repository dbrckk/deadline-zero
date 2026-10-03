# Google Play Store release contract

This document defines the source-of-truth requirements for the Deadline: Zero Play Store listing and current Godot Android release.

## Current shipping runtime

The release candidate is exported from the Godot project under `godot/` using the `Android Play Release` preset.

Current contract:

- application ID: `com.deadlinezero.game`
- app name: `Deadline: Zero`
- AAB distribution format
- arm64-v8a production ABI
- minimum SDK 26
- target SDK 36
- production signing supplied outside the repository

The legacy libGDX Android project remains useful as historical/reference tooling, but its Ads/Billing/Play Games integrations and release tasks do not describe the contents of the current Godot AAB.

## Required graphics

Store final, authored exports under `play/store/` before publication:

- `icon.png`: 512×512 PNG, <= 1 MiB.
- `feature-graphic.png`: 1024×500 PNG without alpha.
- `phone-screenshots/`: at least three final gameplay screenshots, each PNG/JPEG without alpha, <= 8 MiB, exact 16:9 landscape, at least 1920×1080, with no dimension above 3840 px.

The `Godot Play Screenshots` workflow generates real-runtime screenshot candidates plus authored 3D branding candidates. Review them visually before copying approved outputs into the final Store paths.

Do not use ranking claims, price claims, fake awards, download-count claims, or misleading UI in Store graphics. Screenshots must show the real shipped game experience.

## Listing metadata

The canonical Play Console copy is versioned in `play/store/LISTING.md`. The current copy is intentionally limited to features present in the Godot runtime.

The short description must explain the actual gameplay without ranking claims, calls to action, fake urgency, or unverifiable superlatives. Numerical feature counts must be backed by runtime contracts/tests.

## Privacy and Data Safety

Use `play/store/DATA_SAFETY.md` as the source of truth for the exact Godot AAB.

The current Godot candidate contains no Ads, UMP, Play Billing, Play Games Services, In-App Review, analytics, account, or developer-operated backend integration. Do not carry legacy libGDX SDK declarations into the Godot Play Console submission unless those SDKs are deliberately reintroduced.

Re-check the final merged manifest and dependency graph before every submission.

## Versioning and signing

The Godot Play preset currently uses version code `1` and version name `0.1.0`. Every Play upload must use a version code greater than all previous artifacts for `com.deadlinezero.game`.

The real upload keystore must never be committed. Supply release signing through protected environment/secrets using the Godot Android release-keystore variables documented in `godot/PLAY_RELEASE.md`.

## Release evidence

Archive, for the exact candidate commit:

- successful `Godot 3D Verify` results;
- successful `Godot Android First Playable` emulator evidence;
- successful `Godot Play AAB Verify` bundle/signature/ABI evidence;
- final reviewed Store graphics;
- physical-device checks;
- Play pre-launch report;
- final Play Console declarations.

## Final Play Console checks

Before production rollout:

1. Verify the uploaded AAB package is `com.deadlinezero.game`.
2. Verify target API 36 or newer.
3. Verify version code is strictly newer than every prior Play artifact.
4. Verify the bundle is signed with the intended production upload key.
5. Confirm Store copy/screenshots match the exact Godot runtime.
6. Confirm Data Safety reflects the exact shipping dependency/manifest state.
7. Complete app access, target audience, IARC content rating, and testing-track requirements.
8. Review Play pre-launch crashes, ANRs, rendering, and compatibility findings.
9. Complete representative physical-device checks.
10. Keep the upload key backed up securely outside the repository.
