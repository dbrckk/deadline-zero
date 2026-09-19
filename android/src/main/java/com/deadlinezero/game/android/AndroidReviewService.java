package com.deadlinezero.game.android;

import com.deadlinezero.game.services.ReviewService;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public final class AndroidReviewService implements ReviewService {
    private final AndroidLauncher activity;
    private final ReviewManager manager;
    private boolean requestInFlight;
    private boolean requestedThisSession;

    public AndroidReviewService(AndroidLauncher activity) {
        this.activity = activity;
        this.manager = ReviewManagerFactory.create(activity);
    }

    @Override public void requestReview() {
        if (requestInFlight || requestedThisSession) return;
        requestInFlight = true;
        manager.requestReviewFlow().addOnCompleteListener(request -> {
            if (!request.isSuccessful()) {
                requestInFlight = false;
                return;
            }
            manager.launchReviewFlow(activity, request.getResult()).addOnCompleteListener(flow -> {
                requestInFlight = false;
                requestedThisSession = true;
            });
        });
    }
}
