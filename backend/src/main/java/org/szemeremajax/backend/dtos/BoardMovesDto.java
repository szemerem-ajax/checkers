package org.szemeremajax.backend.dtos;

import org.szemeremajax.backend.models.BoardTransition;
import org.szemeremajax.backend.models.Move;
import org.szemeremajax.backend.models.MoveKind;

import java.util.List;

public class BoardMovesDto {
    private final List<RawMove> moves;

    public BoardMovesDto(List<BoardTransition> bt) {
        this.moves = bt.stream().map(t -> new RawMove(t.move())).toList();
    }

    public List<RawMove> getMoves() {
        return moves;
    }

    public static class RawMove {
        private final int from, to;
        private final MoveKind kind;

        public RawMove(Move move) {
            this.from = move.from();
            this.to = move.to();
            this.kind = move.kind();
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public MoveKind getKind() {
            return kind;
        }
    }
}
