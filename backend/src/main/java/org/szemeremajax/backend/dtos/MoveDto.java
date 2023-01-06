package org.szemeremajax.backend.dtos;

import org.szemeremajax.backend.models.BoardTransition;
import org.szemeremajax.backend.models.Move;

public class MoveDto {
    private String boardUuid, authUuid;
    private int from, to;

    public String getBoardUuid() {
        return boardUuid;
    }

    public String getAuthUuid() {
        return authUuid;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean matches(BoardTransition transition) {
        return matches(transition.move());
    }

    public boolean matches(Move move) {
        return from == move.from() && to == move.to();
    }
}
