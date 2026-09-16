package com.deadlinezero.game.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/** Shared logical viewport for rendering and touch conversion. */
public final class UiViewport {
    private final OrthographicCamera camera = new OrthographicCamera();
    private final ExtendViewport viewport = new ExtendViewport(UiLayout.BASE_WIDTH, UiLayout.BASE_HEIGHT, camera);
    private UiLayout.Metrics metrics = UiLayout.compute((int) UiLayout.BASE_WIDTH, (int) UiLayout.BASE_HEIGHT);

    public void resize(int screenWidth, int screenHeight) {
        int safeWidth = Math.max(1, screenWidth);
        int safeHeight = Math.max(1, screenHeight);
        viewport.update(safeWidth, safeHeight, true);
        metrics = UiLayout.compute(screenWidth, screenHeight);
    }

    public void apply(SpriteBatch batch, ShapeRenderer shapes) {
        viewport.apply(true);
        if (batch != null) batch.setProjectionMatrix(camera.combined);
        if (shapes != null) shapes.setProjectionMatrix(camera.combined);
    }

    public void apply(SpriteBatch batch) {
        apply(batch, null);
    }

    public void apply(ShapeRenderer shapes) {
        apply(null, shapes);
    }

    public Vector2 unproject(float screenX, float screenY, Vector2 out) {
        if (out == null) throw new IllegalArgumentException("out");
        out.set(screenX, screenY);
        viewport.unproject(out);
        return out;
    }

    public float width() { return viewport.getWorldWidth(); }
    public float height() { return viewport.getWorldHeight(); }
    public UiLayout.Metrics metrics() { return metrics; }
    public OrthographicCamera camera() { return camera; }
}
