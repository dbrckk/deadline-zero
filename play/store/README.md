# Play Store export directory

Place final authored Google Play listing graphics here before running `:android:bundlePlayRelease`.

Required paths:

- `icon.png` — exact 512 x 512 Play icon, <= 1 MiB.
- `feature-graphic.png` — exact 1024 x 500 PNG without alpha.
- `phone-screenshots/` — at least three final gameplay screenshots.

For this game, the production gate intentionally requires recommendation-grade phone screenshots rather than only the publication minimum: each screenshot must be PNG/JPEG without alpha, <= 8 MiB, exact 16:9 landscape, at least 1920 x 1080, and no dimension may exceed 3840 px.

Do not commit temporary mockups under the final filenames. `verifyPlayStoreAssets` treats anything at these paths as publication-ready output.
