package org.szemeremajax.backend.models;

public enum Alliance {
    WHITE,
    BLACK;

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
