package com.deadlinezero.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.meta.ProfileStore;
import com.deadlinezero.game.meta.RunModifierContext;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.screen.MenuScreen;
import com.deadlinezero.game.screen.RunContractScreen;
import com.deadlinezero.game.screen.RunResultScreen;
import com.deadlinezero.game.services.GameServices;

/**
 * CI-only LWJGL3 runtime smoke. It drives the real game through the M0 desktop
 * vertical-slice wiring: menu -> contract -> combat -> settlement/result -> menu.
 * It renders each screen under a real LWJGL3/OpenGL context and verifies the
 * settlement survives an immediate profile reload. This is still automated
 * runtime proof, not human visual/gameplay QA.
 */
public final class DesktopSmokeLauncher {
    private DesktopSmokeLauncher() {}

    public static void main(String[] args) {
        DeadlineZeroGame game = new DeadlineZeroGame(GameServices.noOp());
        ApplicationListener smoke = new ApplicationListener() {
            private int phaseFrames;
            private int phase;
            private int runsBefore;

            @Override
            public void create() {
                game.create();
                require(game.getScreen() instanceof MenuScreen, "expected menu after create");
                runsBefore = game.profile.totalRuns;
            }

            @Override
            public void resize(int width, int height) {
                game.resize(width, height);
            }

            @Override
            public void render() {
                game.render();
                if (++phaseFrames < 4) return;
                phaseFrames = 0;

                switch (phase++) {
                    case 0 -> {
                        game.startRun();
                        require(game.getScreen() instanceof RunContractScreen, "expected contract screen");
                    }
                    case 1 -> {
                        game.startRunWithContract(RunModifierContext.offers()[0]);
                        require(game.getScreen() instanceof GameScreen, "expected combat screen");
                    }
                    case 2 -> {
                        game.finishRun(12, 30f, false, 1);
                        require(game.getScreen() instanceof RunResultScreen, "expected run result screen");
                        require(game.profile.totalRuns == runsBefore + 1, "settlement must increment runs exactly once");
                        require(ProfileStore.load().totalRuns == game.profile.totalRuns, "settlement must survive profile reload");
                    }
                    case 3 -> {
                        game.showMenu();
                        require(game.getScreen() instanceof MenuScreen, "expected menu after result");
                    }
                    default -> Gdx.app.exit();
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
        config.setTitle("Deadline: Zero — CI Runtime Smoke");
        config.setWindowedMode(640, 360);
        config.useVsync(false);
        config.setForegroundFPS(60);
        new Lwjgl3Application(smoke, config);
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException("Desktop runtime smoke failed: " + message);
    }
}
