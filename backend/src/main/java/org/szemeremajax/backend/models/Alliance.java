package org.szemeremajax.backend.models;

public enum Alliance {
    WHITE,
    BLACK;

    public static Alliance fromFen(char c) {
        return switch (c) {
            case 'W', 'w' -> WHITE;
            case 'B', 'b' -> BLACK;
            default -> throw new IllegalArgumentException("Unknown alliance: " + c);
        };
    }

    public Alliance opposite() {
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
        };
    }

    public int index() {
        return switch (this) {
            case WHITE -> 0;
            case BLACK -> 1;
        };
    }
}
