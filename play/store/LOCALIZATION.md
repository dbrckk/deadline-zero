# Deadline: Zero — Localization readiness contract

Deadline: Zero has a repository-level localization architecture with a centralized English catalog at `assets/i18n/messages.properties` and a runtime `Localization` facade backed by libGDX `I18NBundle`.

The shipping build is still English-only. This contract distinguishes having a localization-ready architecture from actually shipping additional validated locales.

## Current status

- [x] Default shipping language is English.
- [x] Centralized translatable string catalog exists for core UI.
- [x] Stable localization keys are used by the main presentation surfaces.
- [x] Missing static localization keys fail CI.
- [x] Duplicate keys and malformed MessageFormat patterns fail CI.
- [x] Direct user-facing BitmapFont literals on guarded presentation surfaces fail CI.
- [x] UTF-8 catalog content and glyph sanitization are covered by tests.
- [ ] Runtime locale selection is implemented.
- [ ] Locale selection/fallback behavior beyond English is implemented.
- [ ] Text expansion is validated on representative translated screens.
- [ ] Store listing localization strategy is defined.
- [ ] At least one non-English locale has passed in-game QA.

## Minimum localization architecture

The current architecture already provides:

- a centralized runtime localization facade;
- a source-controlled English message catalog;
- stable message keys for core UI and data-driven content;
- fallback behavior that returns the key rather than crashing when a resource is missing;
- CI guardrails for missing/duplicate/malformed catalog entries and direct presentation literals.

Before claiming support for any additional locale:

- add a locale-specific resource catalog compatible with the stable key set;
- implement explicit runtime/system locale selection and documented fallback behavior;
- keep gameplay data identifiers separate from localized display names;
- provide fonts/glyph handling for every character required by the locale;
- verify plural/number formatting rules where dynamic counts are displayed;
- do not localize protocol IDs, SKU IDs, save-schema keys or other machine identifiers.

## Release QA

For every locale advertised in the Play listing:

- [ ] Launch/menu/settings/loadout/run/upgrade/result/store flows contain no unintended fallback text.
- [ ] Long labels do not overlap or clip at minimum and maximum UI scale.
- [ ] Dynamic values remain readable after translation.
- [ ] Fonts contain every required glyph.
- [ ] Screenshots and localized Play listing copy match the shipping UI.
- [ ] Privacy, purchase and consent wording is reviewed for the locale.
- [ ] Locale changes persist or follow the documented system-language behavior.

## Release rule

Deadline: Zero must be released and marketed as English-only until at least one additional locale is implemented, selectable or system-resolved, and passes the release QA above.

The existence of the localization architecture does not by itself mean the game is multilingual.
