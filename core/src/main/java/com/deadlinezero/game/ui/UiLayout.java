package com.deadlinezero.game.ui;

/** Pure responsive layout math shared by all production-facing UI. */
public final class UiLayout {
    public static final float BASE_WIDTH = 1280f;
    public static final float BASE_HEIGHT = 720f;
    public static final float MIN_TOUCH_TARGET = 56f;
    private static final float MIN_CONTENT_WIDTH = 1180f;
    private static final float MIN_SAFE_MARGIN = 24f;
    private static final float HEADER_HEIGHT = 88f;
    private static final float FOOTER_HEIGHT = 92f;
    private static final float CONTENT_GAP = 16f;

    public record Metrics(
        float width,
        float height,
        float safeLeft,
        float safeRight,
        float safeBottom,
        float safeTop,
        float headerBottom,
        float footerTop,
        float contentBottom,
        float contentTop,
        float touchTarget,
        boolean compact,
        boolean wide
    ) {
        public float contentWidth() { return safeRight - safeLeft; }
        public float contentHeight() { return contentTop - contentBottom; }
        public float centerX() { return width * .5f; }
        public float centerY() { return height * .5f; }
    }

    private UiLayout() {}

    public static Metrics compute(int screenWidth, int screenHeight) {
        float aspect = screenWidth > 0 && screenHeight > 0
            ? screenWidth / (float) screenHeight
            : BASE_WIDTH / BASE_HEIGHT;
        float baseAspect = BASE_WIDTH / BASE_HEIGHT;

        float width = BASE_WIDTH;
        float height = BASE_HEIGHT;
        if (aspect > baseAspect) width = BASE_HEIGHT * aspect;
        else if (aspect > 0f && aspect < baseAspect) height = BASE_WIDTH / aspect;

        float extraX = Math.max(0f, width - BASE_WIDTH);
        float extraY = Math.max(0f, height - BASE_HEIGHT);
        float horizontalMargin = Math.max(MIN_SAFE_MARGIN, MIN_SAFE_MARGIN + extraX * .04f);
        horizontalMargin = Math.min(horizontalMargin, Math.max(MIN_SAFE_MARGIN, (width - MIN_CONTENT_WIDTH) * .5f));
        float verticalMargin = Math.max(MIN_SAFE_MARGIN, MIN_SAFE_MARGIN + extraY * .04f);

        float safeLeft = horizontalMargin;
        float safeRight = width - horizontalMargin;
        float safeBottom = verticalMargin;
        float safeTop = height - verticalMargin;
        float headerBottom = safeTop - HEADER_HEIGHT;
        float footerTop = safeBottom + FOOTER_HEIGHT;
        float contentBottom = footerTop + CONTENT_GAP;
        float contentTop = headerBottom - CONTENT_GAP;

        boolean compact = width < 1400f;
        boolean wide = width >= 1500f;
        return new Metrics(
            width,
            height,
            safeLeft,
            safeRight,
            safeBottom,
            safeTop,
            headerBottom,
            footerTop,
            contentBottom,
            contentTop,
            MIN_TOUCH_TARGET,
            compact,
            wide
        );
    }
}
