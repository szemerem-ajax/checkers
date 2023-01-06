package org.szemeremajax.backend.models;

/**
 * Describes a move.
 * @param from The square to move the piece from.
 * @param to The square to move the piece to.
 * @param kind The move's type.
 */
public record Move(int from, int to, MoveKind kind) {
    /**
     * Creates a normal move.
     * @param from The originating square.
     * @param to The landing square.
     * @return The move.
     */
    public static Move createNormal(int from, int to) {
        return new Move(from, to, MoveKind.NORMAL);
    }

    /**
     * Creates a capture move.
     * @param from The originating square.
     * @param to The landing square.
     * @return The move.
     */
    public static Move createCapture(int from, int to) {
        return new Move(from, to, MoveKind.CAPTURE);
    }

    /**
     * Creates a promotion move.
     * @param from The originating square.
     * @param to The landing square.
     * @return The move.
     */
    public static Move createPromotion(int from, int to) {
        return new Move(from, to, MoveKind.PROMOTION);
    }

    /**
     * Creates a promotion-via-capture move.
     * @param from The originating square.
     * @param to The landing square.
     * @return The move.
     */
    public static Move createPromotionWithCapture(int from, int to) {
        return new Move(from, to, MoveKind.PROMOTION_WITH_CAPTURE);
    }
}
