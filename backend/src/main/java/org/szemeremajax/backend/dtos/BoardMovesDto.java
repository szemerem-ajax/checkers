package org.szemeremajax.backend.dtos;

import org.szemeremajax.backend.models.BoardTransition;
import org.szemeremajax.backend.models.Move;
import org.szemeremajax.backend.models.MoveKind;

import java.util.List;

/**
 * A simple DTO containing a list of moves from a given position.
 */
public class BoardMovesDto {
    private final List<RawMoveDto> moves;

    /**
     * Creates a new {@link BoardMovesDto}.
     * @param bt The list of possible moves.
     */
    public BoardMovesDto(List<BoardTransition> bt) {
        this.moves = bt.stream().map(t -> new RawMoveDto(t.move())).toList();
    }

    /**
     * Gets the available moves.
     * @return The available moves.
     */
    public List<RawMoveDto> getMoves() {
        return moves;
    }

    /**
     * A simple DTO containing a move.
     */
    public static class RawMoveDto {
        private final int from, to;
        private final MoveKind kind;

        /**
         * Creates a new {@link RawMoveDto}.
         * @param move The move.
         */
        public RawMoveDto(Move move) {
            this.from = move.from();
            this.to = move.to();
            this.kind = move.kind();
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
         * Gets the move's type.
         * @return The move's type.
         */
        public MoveKind getKind() {
            return kind;
        }
    }
}
