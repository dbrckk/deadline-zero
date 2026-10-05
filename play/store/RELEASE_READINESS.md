# Deadline: Zero — Release readiness matrix

Single source of truth for the final path from green CI to Google Play rollout.

## Current Godot release scope

The current Godot candidate contains one complete survival arena, 8 enemy archetypes including one three-phase boss, 6 weapon profiles/protocols, and 14 run upgrades. Store claims must stay within this verified scope until the runtime expands.

The Godot candidate currently contains no Ads, UMP, Play Billing, Play Games Services, In-App Review, analytics, account, cloud-save, or developer-operated backend integration.

## Automated gates

These can be proven by repository CI/build tooling:

- [x] Godot project import/parser validation.
- [x] Headless gameplay/runtime regression suite.
- [x] Real rendered-frame QA for first-playable, pressure, and archetype readability.
- [x] Android native debug APK build/emulator smoke workflow.
- [x] Dedicated Google Play AAB preset with final package ID `com.deadlinezero.game`.
- [x] Android 16 / target API 36 export contract.
- [x] arm64-only Play release architecture contract.
- [x] Ephemeral-keystore AAB verification workflow for package/signature/ABI evidence.
- [x] 1920×1080 real-runtime Play screenshot candidate workflow.
- [x] Authored vector icon and 3D feature-graphic candidate workflow.
- [x] Store listing copy aligned with current Godot runtime scope.
- [x] Godot-specific Data Safety and Play Console declaration contracts.
- [x] Mobile combat performance safeguards and deterministic runtime tests.

Legacy libGDX release tooling remains in the repository but does not prove the contents of the Godot AAB.

## Manual platform gates

These require real account state, visual approval, licensed Play state, or physical hardware and must not be auto-marked complete:

- [ ] Complete player-controlled run on representative physical Android hardware.
- [ ] Physical lifecycle/background/foreground/process-death matrix.
- [ ] Low/mid/high device performance and thermal calibration.
- [ ] Physical accessibility/readability pass across touch targets and combat readability.
- [ ] Public production privacy-policy URL reviewed in browser.
- [ ] Play Data Safety form completed against the exact Godot AAB.
- [ ] App access, ads, target audience, and IARC content-rating declarations completed in Play Console.
- [ ] Final authored icon, feature graphic, and representative screenshots reviewed visually and promoted from candidates.
- [ ] Production upload keystore configured through protected release secrets.
- [ ] Signed production AAB uploaded to the intended testing track.
- [ ] Play pre-launch report reviewed with no unresolved release blocker.
- [ ] Android vitals / crash / ANR / compatibility findings reviewed.
- [ ] Required closed/open testing policy for the developer account completed.
- [ ] Final rollout checklist signed off against the exact uploaded AAB.

## Release states

### Development-ready

Repository CI is green and automated gameplay/runtime gates pass.

### Closed-test candidate

Requires:
- automated Godot gates green;
- production signing configuration available;
- final Store assets visually approved;
- no known P0 blocker.

### Production candidate

Requires the closed-test candidate plus the applicable physical-device and Play Console gates above.

### Production-ready

The exact AAB intended for rollout has passed repository gates, physical-device validation, Play Console declarations, pre-launch review, visual asset approval, and final release sign-off.

## Rule

A green CI build is necessary but is not equivalent to Play Store production readiness. Manual gates stay unchecked until evidence exists for the exact release candidate.
