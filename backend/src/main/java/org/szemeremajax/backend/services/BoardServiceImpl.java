package org.szemeremajax.backend.services;

import org.springframework.stereotype.Component;
import org.szemeremajax.backend.models.Board;

import java.util.HashMap;
import java.util.Optional;

@Component
public class BoardServiceImpl extends HashMap<String, Board> implements BoardService {
    @Override
    public Optional<Board> lookupBoard(String id) {
        if (containsKey(id))
            return Optional.of(get(id));

        return Optional.empty();
    }

    @Override
    public void setBoard(String id, Board board) {
        put(id, board);
    }
}
