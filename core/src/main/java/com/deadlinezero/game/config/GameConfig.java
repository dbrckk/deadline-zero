package com.deadlinezero.game.config;

public final class GameConfig {
    private GameConfig() {}
    public static final float WORLD_WIDTH = 32f;
    public static final float WORLD_HEIGHT = 18f;
    public static final int TARGET_FPS = 60;
    public static final float FIXED_STEP = 1f / 60f;
    public static final int MAX_ENEMIES = 320;
    public static final int MAX_PROJECTILES = 900;
    public static final float PLAYER_SPEED = 7.4f;
    public static final float PLAYER_MAX_HP = 100f;
    public static final float PLAYER_FIRE_INTERVAL = 0.22f;
    public static final float PLAYER_DAMAGE = 22f;
    public static final float PROJECTILE_SPEED = 18f;
    public static final String TITLE = "DEADLINE: ZERO";
}
