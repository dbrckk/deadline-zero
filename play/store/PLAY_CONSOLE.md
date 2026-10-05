# Deadline: Zero — Google Play Console release contract

Repository-side checklist for Play Console declarations that cannot be inferred safely from the Android binary alone. Complete this against the exact Godot production AAB before rollout.

## App access

- [ ] Confirm the current Godot build remains fully playable without login, membership, or reviewer credentials.
- [ ] If restricted access is introduced, provide working review instructions in Play Console.

## Ads

- [ ] Current Godot release candidate: declare **no ads** because no advertising SDK is integrated.
- [ ] If ads are added later, update the binary, Data Safety contract, privacy flow, store declaration, and this checklist together.

## Target audience and content

- [ ] Select intended target age groups deliberately.
- [ ] Re-check Families policy applicability after the target audience is selected.
- [ ] Confirm Store copy/screenshots match the current arena combat and do not imply unavailable legacy features.

## Content rating

- [ ] Complete the IARC questionnaire using the exact shipping combat and violence presentation.
- [ ] Current Godot candidate has no in-app purchases or advertising integration; update the questionnaire if that changes.
- [ ] Review the resulting rating before rollout and re-run it after material content changes.

## Data Safety and privacy

- [ ] Complete Play Data Safety using `play/store/DATA_SAFETY.md` and the exact Godot AAB dependency/manifest evidence.
- [ ] Confirm the public privacy-policy URL is valid, HTTPS, and matches the current Godot data flows. Source is ready in `public/privacy/`; hosting still requires one-time GitHub Pages enablement or another connected host.
- [ ] Do not reuse legacy Ads/Billing/Play Games declarations for the Godot AAB unless those SDKs are actually reintroduced.

## Monetization

- [ ] Current Godot release candidate contains no Play Billing or ad monetization.
- [ ] Do not configure or advertise in-app products that are unavailable in the shipping binary.
- [ ] If monetization is added, validate purchase/restore/refund behavior and update Data Safety before release.

## Store presence

- [ ] Use `play/store/LISTING.md` as canonical copy for the Godot release.
- [ ] Upload the visually approved 512×512 icon.
- [ ] Upload the visually approved 1024×500 feature graphic.
- [ ] Upload three or more final 16:9 gameplay screenshots generated from the shipping runtime.
- [ ] Verify no screenshot or description advertises unavailable functionality.

## Release and device coverage

- [ ] Upload the signed production AAB from the `Android Play Release` Godot preset using the protected production upload key.
- [ ] Confirm the submitted AAB targets Android 16 / API 36 or higher.
- [ ] Review Play pre-launch report findings.
- [ ] Review Android vitals/pre-launch crashes, ANRs, rendering, and compatibility issues.
- [ ] Complete representative physical-device checks for low/mid/high Android hardware.
- [ ] Complete the required Play testing track for the developer account before production rollout.

## Final rollout gate

Production rollout is blocked until every applicable item above has been reviewed against the exact AAB being submitted. Account-state, policy questionnaires, physical-device checks, visual approval, and production signing remain manual gates.
