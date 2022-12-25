package org.szemeremajax.backend.services;

import org.szemeremajax.backend.models.Board;
import org.szemeremajax.backend.models.BoardTransition;

import java.util.List;

public interface MoveGenerationService {
    List<BoardTransition> generateMoves(Board board);
}
