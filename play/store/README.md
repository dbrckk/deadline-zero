# Play Store export directory

Final authored Google Play listing graphics live here. The current shipping runtime is the Godot project under `godot/`.

Required final paths before production upload:

- `icon.png` — exact 512×512 Play icon, <= 1 MiB.
- `feature-graphic.png` — exact 1024×500 PNG without alpha.
- `phone-screenshots/` — final gameplay screenshots; the current candidate workflow produces five representative scenes.

For this game, the production gate intentionally requires recommendation-grade phone screenshots rather than only the publication minimum: each screenshot must be PNG/JPEG without alpha, <= 8 MiB, exact 16:9 landscape, at least 1920×1080, and no dimension may exceed 3840 px.

The `Godot Play Screenshots` workflow produces five real 1920×1080 gameplay candidates (opening combat, mid-run pressure, boss encounter, upgrade choice, run end) plus authored branding candidates. Those artifacts are review inputs only. Do not promote them to the final filenames until they pass visual review.

Do not commit temporary mockups under the final filenames. Store copy and graphics must match the exact Godot AAB being submitted.
