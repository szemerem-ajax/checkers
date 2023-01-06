package org.szemeremajax.backend.models;

public enum MoveKind {
    NORMAL,
    PROMOTION,
    CAPTURE,
    PROMOTION_WITH_CAPTURE;

    public boolean isCapture() {
        return this == CAPTURE || this == PROMOTION_WITH_CAPTURE;
    }
}
