package org.szemeremajax.backend.models;

public record Move(int from, int to, MoveKind kind) {
    public static Move createNormal(int from, int to) {
        return new Move(from, to, MoveKind.NORMAL);
    }

    public static Move createCapture(int from, int to) {
        return new Move(from, to, MoveKind.CAPTURE);
    }

    public static Move createPromotion(int from, int to) {
        return new Move(from, to, MoveKind.PROMOTION);
    }

    public static Move createPromotionWithCapture(int from, int to) {
        return new Move(from, to, MoveKind.PROMOTION_WITH_CAPTURE);
    }
}
