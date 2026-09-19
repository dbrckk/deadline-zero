package com.deadlinezero.game.services;

/** Platform boundary for optional store-managed in-app review prompts. */
public interface ReviewService {
    void requestReview();

    static ReviewService noOp() {
        return () -> { };
    }
}
