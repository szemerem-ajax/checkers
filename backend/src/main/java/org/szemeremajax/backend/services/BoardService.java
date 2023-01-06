package org.szemeremajax.backend.services;

import org.szemeremajax.backend.models.Board;

import java.util.Optional;

/**
 * Provides a contract for a service which can keep track of all ongoing games.
 */
public interface BoardService {
    /**
     * Tries to look up the game with the given id.
     * @param id The game's id.
     * @return The board, if found.
     */
    Optional<Board> lookupBoard(String id);

    /**
     * Registers/overwrites the game as the given id.
     * @param id The game's id.
     * @param board The game's current board/position.
     */
    void setBoard(String id, Board board);
}
