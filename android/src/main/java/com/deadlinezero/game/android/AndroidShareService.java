package com.deadlinezero.game.android;

import android.content.Intent;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.deadlinezero.game.services.ShareService;

/** Android ACTION_SEND bridge. Shares only text explicitly requested by the player. */
public final class AndroidShareService implements ShareService {
    private final AndroidApplication activity;

    public AndroidShareService(AndroidApplication activity) {
        this.activity = activity;
    }

    @Override public boolean available() { return activity != null; }

    @Override public void shareText(String text) {
        if (activity == null || text == null || text.isBlank()) return;
        activity.runOnUiThread(() -> {
            Intent send = new Intent(Intent.ACTION_SEND);
            send.setType("text/plain");
            send.putExtra(Intent.EXTRA_TEXT, text);
            Intent chooser = Intent.createChooser(send, "Share Deadline: Zero run");
            activity.startActivity(chooser);
        });
    }
}
