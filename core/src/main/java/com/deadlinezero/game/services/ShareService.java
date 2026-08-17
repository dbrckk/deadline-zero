package com.deadlinezero.game.services;

/** Platform-native, opt-in text sharing. No analytics, account or social SDK required. */
public interface ShareService {
    boolean available();
    void shareText(String text);

    static ShareService noOp() {
        return new ShareService() {
            public boolean available() { return false; }
            public void shareText(String text) { }
        };
    }
}
