package org.szemeremajax.backend.dtos;

import org.szemeremajax.backend.models.Alliance;
import org.szemeremajax.backend.models.Board;
import org.szemeremajax.backend.models.Piece;
import org.szemeremajax.backend.models.PieceKind;

import java.util.Arrays;

/**
 * A simple DTO containing board update information.
 */
public class BoardUpdateDto {
    private final Alliance sideToMove;
    private final RawPieceDto[] pieces;

    /**
     * Creates a new {@link BoardUpdateDto} from the given board.
     * @param board The board.
     */
    public BoardUpdateDto(Board board) {
        this.sideToMove = board.getSideToMove();
        this.pieces = new RawPieceDto[50];
        var list = Arrays.stream(board.getPiecesRaw()).map(p -> p == null ? null : new RawPieceDto(p)).toList();
        list.toArray(pieces);
    }

    /**
     * Gets the side to move.
     * @return The side to move.
     */
    public Alliance getSideToMove() {
        return sideToMove;
    }

    /**
     * Gets the pieces currently on the board.
     * @return The pieces.
     */
    public RawPieceDto[] getPieces() {
        return pieces;
    }

    /**
     * A simple DTO describing a {@link Piece} on the board.
     */
    public static class RawPieceDto {
        private final Alliance alliance;
        private final PieceKind kind;

        /**
         * Creates a new {@link RawPieceDto}.
         * @param piece The {@link Piece} to create the DTO from.
         */
        public RawPieceDto(Piece piece) {
            this.alliance = piece.alliance();
            this.kind = piece.kind();
        }

        /**
         * Gets the piece's {@link Alliance}.
         * @return The piece's {@link Alliance}.
         */
        public Alliance getAlliance() {
            return alliance;
        }

        /**
         * Gets the piece's {@link PieceKind}
         * @return The piece's {@link PieceKind}.
         */
        public PieceKind getKind() {
            return kind;
        }
    }
}
