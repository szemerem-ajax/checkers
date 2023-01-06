package org.szemeremajax.backend.services;

import org.szemeremajax.backend.models.Board;
import org.szemeremajax.backend.models.BoardTransition;

import java.util.List;

/**
 * Provides a contract for a service which can generate moves from a given board.
 */
public interface MoveGenerationService {
    /**
     * Returns a list of all possible moves from a given position.
     * @param board The board to generate moves from.
     * @return The list of all possible moves.
     */
    List<BoardTransition> generateMoves(Board board);
}
