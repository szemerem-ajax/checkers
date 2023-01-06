package org.szemeremajax.backend.models;

/**
 * Holds information about a move, the board before and after.
 * @param move The move.
 * @param from The board before the move.
 * @param to The board after the move.
 */
public record BoardTransition(Move move, Board from, Board to) {
}
