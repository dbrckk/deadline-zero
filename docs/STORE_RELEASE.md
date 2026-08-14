# Google Play store release contract

This document defines the source-of-truth requirements for the Deadline: Zero Play Store listing. Keep the listing synchronized with the shipped binary and `docs/PLAY_RELEASE.md`.

## Required graphics

Store final, authored exports outside runtime assets under `play/store/` before publication:

- `icon.png`: 512 x 512, 32-bit PNG, alpha allowed, <= 1024 KB.
- `feature-graphic.png`: 1024 x 500, JPEG or 24-bit PNG without alpha.
- phone screenshots: at least two representative gameplay screenshots. Prefer a coherent landscape set showing combat, progression and a boss encounter rather than menus only.

Do not use ranking claims, price claims, fake awards, download-count claims or misleading UI in store graphics.

The Android launcher icon is a separate asset contract and lives in `android/src/main/res/`. It must remain visually consistent with the high-resolution Play icon.

## Listing metadata

Use the exact app name `Deadline: Zero` unless a deliberate product rename is applied in both source and Play Console.

The short description must explain the actual gameplay without ranking claims, calls to action, fake urgency or unverifiable superlatives. Screenshots and descriptions must depict functionality that exists in the uploaded build.

## Privacy and Data safety

The Play Console privacy-policy URL must exactly match the value passed to the production build as `-PprivacyPolicyUrl=https://...`. `bundlePlayRelease` rejects missing, non-HTTPS and obvious placeholder URLs. The same URL is exposed in the in-app Settings screen.

Complete the Data safety form from the behavior of the exact production build, including data handled by Google Mobile Ads, User Messaging Platform and Play Billing SDKs. Re-check SDK provider disclosures whenever these dependencies change.

Do not claim that the app collects or shares no data merely because the game code has no account system. Third-party SDK behavior is part of the declaration.

## Final Play Console checks

Before production rollout, verify the uploaded AAB package name is `com.deadlinezero.game`, version code is strictly newer than every prior Play artifact, the four Billing product IDs match the source catalog, production AdMob IDs are active, the content rating and target audience are accurate, ads are declared, the privacy policy is public without authentication, and the Data safety answers match the shipping SDK configuration.
