package org.szemeremajax.backend.messages;

import org.szemeremajax.backend.models.BoardTransition;
import org.szemeremajax.backend.models.Move;

public class MoveMessage {
    private String uuid;
    private int from, to;

    public String getUuid() {
        return uuid;
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
