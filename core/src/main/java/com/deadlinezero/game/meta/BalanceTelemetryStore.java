package com.deadlinezero.game.meta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

/** Local-only ring buffer for recent balancing samples. Never transmits data. */
public final class BalanceTelemetryStore {
    static final int CAPACITY = 64;
    private static final String PREFS = "deadline-zero-balance-v1";

    private BalanceTelemetryStore() {}

    public static void append(BalanceRunSample sample) {
        if (sample == null) return;
        try {
            Preferences p = Gdx.app.getPreferences(PREFS);
            int cursor = Math.floorMod(p.getInteger("cursor", 0), CAPACITY);
            String key = "run." + cursor + ".";
            p.putLong(key + "seq", sample.sequence());
            p.putInteger(key + "stage", sample.stage());
            p.putInteger(key + "threat", sample.threatTier());
            p.putInteger(key + "ordinal", sample.runOrdinal());
            p.putBoolean(key + "victory", sample.victory());
            p.putFloat(key + "seconds", sample.seconds());
            p.putInteger(key + "kills", sample.kills());
            p.putFloat(key + "dealt", sample.damageDealt());
            p.putFloat(key + "received", sample.damageReceived());
            p.putFloat(key + "maxDealt", sample.maxHitDealt());
            p.putFloat(key + "maxReceived", sample.maxHitReceived());
            p.putString(key + "contract", sample.contract());
            p.putString(key + "survivor", sample.survivor());
            p.putString(key + "weapon", sample.weaponId());
            p.putInteger(key + "set", sample.ascensionSetPieces());
            p.putBoolean(key + "zeroDay", sample.zeroDayCore());
            p.putInteger("cursor", (cursor + 1) % CAPACITY);
            p.putInteger("count", Math.min(CAPACITY, Math.max(0, p.getInteger("count", 0)) + 1));
            p.flush();
        } catch (Throwable ignored) {
            // Unit tests/headless tools may not have a libGDX application backend.
        }
    }

    public static Array<BalanceRunSample> loadRecent() {
        Array<BalanceRunSample> out = new Array<>(false, CAPACITY);
        try {
            Preferences p = Gdx.app.getPreferences(PREFS);
            int count = Math.min(CAPACITY, Math.max(0, p.getInteger("count", 0)));
            int cursor = Math.floorMod(p.getInteger("cursor", 0), CAPACITY);
            int start = Math.floorMod(cursor - count, CAPACITY);
            for (int i = 0; i < count; i++) {
                int slot = (start + i) % CAPACITY;
                String key = "run." + slot + ".";
                out.add(new BalanceRunSample(
                    p.getLong(key + "seq", 0L),
                    p.getInteger(key + "stage", 1),
                    p.getInteger(key + "threat", 0),
                    p.getInteger(key + "ordinal", 0),
                    p.getBoolean(key + "victory", false),
                    p.getFloat(key + "seconds", 0f),
                    p.getInteger(key + "kills", 0),
                    p.getFloat(key + "dealt", 0f),
                    p.getFloat(key + "received", 0f),
                    p.getFloat(key + "maxDealt", 0f),
                    p.getFloat(key + "maxReceived", 0f),
                    p.getString(key + "contract", "STANDARD"),
                    p.getString(key + "survivor", "REX"),
                    p.getString(key + "weapon", "ar9"),
                    p.getInteger(key + "set", 0),
                    p.getBoolean(key + "zeroDay", false)
                ));
            }
        } catch (Throwable ignored) { }
        return out;
    }
}
