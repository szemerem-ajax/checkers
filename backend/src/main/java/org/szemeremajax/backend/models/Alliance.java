package org.szemeremajax.backend.models;

/**
 * Describes the sides of the checkers game.
 */
public enum Alliance {
    /**
     * The white side.
     */
    WHITE,
    /**
     * The black side.
     */
    BLACK;

    /**
     * Gets the opposite side.
     * @return The opposite side.
     */
    public Alliance opposite() {
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
        };
    }

    /**
     * Gets the side's index into the pre-calculated move database.
     * @return The side's index (white: 0, black: 1).
     */
    public int index() {
        return switch (this) {
            case WHITE -> 0;
            case BLACK -> 1;
        };
    }
}
