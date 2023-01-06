package org.szemeremajax.backend.models;

/**
 * Describes the different move types.
 */
public enum MoveKind {
    /**
     * A normal move.
     */
    NORMAL,
    /**
     * A promotion move.
     */
    PROMOTION,
    /**
     * A capture move.
     */
    CAPTURE,
    /**
     * A promotion via capture move.
     */
    PROMOTION_WITH_CAPTURE;

    /**
     * Determines whether the move type involves a capture.
     * @return If the move type is a capture or a promotion-via-capture.
     */
    public boolean isCapture() {
        return this == CAPTURE || this == PROMOTION_WITH_CAPTURE;
    }
}
