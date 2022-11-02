package org.szemeremajax.backend.services;

import org.szemeremajax.backend.models.Board;

import java.util.Optional;

public interface BoardService {
    Optional<Board> lookupBoard(String id);
    void addBoard(String id, Board board);
}