package org.szemeremajax.backend.messages;

import org.szemeremajax.backend.models.BoardTransition;
import org.szemeremajax.backend.models.Move;
import org.szemeremajax.backend.models.MoveKind;

import java.util.List;

public class BoardMovesMessage {
    private List<RawMove> moves;

    public BoardMovesMessage(List<BoardTransition> bt) {
        this.moves = bt.stream().map(t -> new RawMove(t.move())).toList();
    }

    public List<RawMove> getMoves() {
        return moves;
    }

    public static class RawBoardTransition {
        //private BoardUpdateMessage from;
        //private BoardUpdateMessage to;
        private RawMove move;

        public RawBoardTransition(BoardTransition bt) {
            //this.from = new BoardUpdateMessage(bt.from());
            //this.to = new BoardUpdateMessage(bt.to());
            this.move = new RawMove(bt.move());
        }

//        public BoardUpdateMessage getFrom() {
//            return from;
//        }
//
//        public BoardUpdateMessage getTo() {
//            return to;
//        }

        public RawMove getMove() {
            return move;
        }
    }

    public static class RawMove {
        private int from, to;
        private MoveKind kind;

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
