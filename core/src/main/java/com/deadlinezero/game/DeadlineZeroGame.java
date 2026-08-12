package com.deadlinezero.game;

import com.badlogic.gdx.Game;
import com.deadlinezero.game.screen.GameScreen;
import com.deadlinezero.game.screen.MenuScreen;
import com.deadlinezero.game.services.GameServices;

public final class DeadlineZeroGame extends Game {
    public final GameServices services;
    public DeadlineZeroGame(GameServices services){ this.services = services == null ? GameServices.noOp() : services; }
    @Override public void create(){ services.billing.initialize(); services.ads.preload(); showMenu(); }
    public void showMenu(){ setScreen(new MenuScreen(this)); }
    public void startRun(){ setScreen(new GameScreen(this)); }
    @Override public void setScreen(com.badlogic.gdx.Screen screen){ if(getScreen()!=null)getScreen().dispose(); super.setScreen(screen); }
}
