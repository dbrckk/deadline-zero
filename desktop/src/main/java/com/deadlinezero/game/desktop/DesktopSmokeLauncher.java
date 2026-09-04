package com.deadlinezero.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.services.GameServices;

/**
 * CI-only LWJGL3 smoke launcher. It boots the real game, renders a small number
 * of frames on the menu, then exits cleanly. This proves desktop startup,
 * asset loading, screen creation and render/dispose wiring without pretending
 * to be a complete playable-run validation.
 */
public final class DesktopSmokeLauncher {
    private static final int EXIT_AFTER_FRAMES = 12;

    private DesktopSmokeLauncher() {}

    public static void main(String[] args) {
        DeadlineZeroGame game = new DeadlineZeroGame(GameServices.noOp());
        ApplicationListener smoke = new ApplicationListener() {
            private int frames;

            @Override
            public void create() {
                game.create();
            }

            @Override
            public void resize(int width, int height) {
                game.resize(width, height);
            }

            @Override
            public void render() {
                game.render();
                if (++frames >= EXIT_AFTER_FRAMES) {
                    Gdx.app.exit();
                }
            }

            @Override
            public void pause() {
                game.pause();
            }

            @Override
            public void resume() {
                game.resume();
            }

            @Override
            public void dispose() {
                game.dispose();
            }
        };

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Deadline: Zero — CI Smoke");
        config.setWindowedMode(640, 360);
        config.useVsync(false);
        config.setForegroundFPS(60);
        new Lwjgl3Application(smoke, config);
    }
}
