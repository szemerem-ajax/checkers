package org.szemeremajax.backend.dtos;

import org.szemeremajax.backend.models.BoardTransition;
import org.szemeremajax.backend.models.Move;

/**
 * A simple DTO containing auth information and move information.
 */
public class MoveDto {
    private String boardUuid, authUuid;
    private int from, to;

    /**
     * Gets the game's id which the user wants to make a move on.
     * @return The game's id.
     */
    public String getBoardUuid() {
        return boardUuid;
    }

    /**
     * Gets the auth id.
     * @return The auth id.
     */
    public String getAuthUuid() {
        return authUuid;
    }

    /**
     * Gets the square which the move originates from.
     * @return The originating square.
     */
    public int getFrom() {
        return from;
    }

    /**
     * Gets the square which the move lands on.
     * @return The landing square.
     */
    public int getTo() {
        return to;
    }

    /**
     * A convenience method which lines up the DTO with the normal board transition object.
     * @param transition The normal board transition object.
     * @return Whether this DTO represents the given board transition.
     */
    public boolean matches(BoardTransition transition) {
        return matches(transition.move());
    }

    /**
     * A convenience method which lines up the DTO with the normal move object.
     * @param move The normal move object.
     * @return Whether this DTO represents the given move.
     */
    public boolean matches(Move move) {
        return from == move.from() && to == move.to();
    }
}
