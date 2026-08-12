package com.deadlinezero.game.entities;

public final class Enemy extends ActorState {
    public enum Type { SHAMBLER, RUNNER, BRUTE, ELITE, BOSS }
    public Type type;
    public float speed;
    public float contactDamage;
    public int xpValue;
    public float hitFlash;
    public Enemy(Type type, float x, float y, float hp, float speed, float radius, float damage, int xp) {
        super(x, y, radius, hp); this.type = type; this.speed = speed; this.contactDamage = damage; this.xpValue = xp;
    }
}
