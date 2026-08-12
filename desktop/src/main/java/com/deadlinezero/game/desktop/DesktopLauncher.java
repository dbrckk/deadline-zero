package com.deadlinezero.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.deadlinezero.game.DeadlineZeroGame;
import com.deadlinezero.game.services.GameServices;

public final class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration c=new Lwjgl3ApplicationConfiguration();
        c.setTitle("Deadline: Zero"); c.setWindowedMode(1280,720); c.useVsync(true); c.setForegroundFPS(120);
        new Lwjgl3Application(new DeadlineZeroGame(GameServices.noOp()),c);
    }
}
