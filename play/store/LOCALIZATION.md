# Deadline: Zero — Localization readiness contract

Deadline: Zero currently has no repository-level i18n/localization resource system. User-facing text is still primarily embedded directly in Java UI code.

This file exists to make that release risk explicit and to prevent the project from being described as localization-ready before the text pipeline is actually implemented.

## Current status

- [x] Default shipping language is English.
- [ ] Centralized translatable string catalog exists for core UI.
- [ ] Locale selection/fallback behavior is implemented.
- [ ] Text expansion is validated on representative screens.
- [ ] Store listing localization strategy is defined.
- [ ] At least one non-English locale has passed in-game QA.

## Minimum localization architecture

Before claiming localization support:

- Move user-facing UI text out of gameplay/screen classes into locale resources.
- Use stable message keys rather than screen-specific string concatenation where practical.
- Define an explicit default locale and fallback.
- Keep gameplay data identifiers separate from localized display names.
- Support UTF-8 text and fonts for every declared locale.
- Verify plural/number formatting rules where dynamic counts are displayed.
- Do not localize protocol IDs, SKU IDs, save-schema keys or other machine identifiers.

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

Until a centralized localization pipeline and at least one translated locale are implemented and validated, Deadline: Zero must be released and marketed as English-only. The existence of this contract does not count as localization completion.
