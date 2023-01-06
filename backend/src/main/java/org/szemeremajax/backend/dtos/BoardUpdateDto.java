package org.szemeremajax.backend.dtos;

import org.szemeremajax.backend.models.Alliance;
import org.szemeremajax.backend.models.Board;
import org.szemeremajax.backend.models.Piece;
import org.szemeremajax.backend.models.PieceKind;

import java.util.Arrays;

public class BoardUpdateDto {
    private final Alliance sideToMove;
    private final RawPiece[] pieces;

    public BoardUpdateDto(Board board) {
        this.sideToMove = board.getSideToMove();
        this.pieces = new RawPiece[50];
        var list = Arrays.stream(board.getPiecesRaw()).map(p -> p == null ? null : new RawPiece(p)).toList();
        list.toArray(pieces);
    }

    public Alliance getSideToMove() {
        return sideToMove;
    }

    public RawPiece[] getPieces() {
        return pieces;
    }

    public static class RawPiece {
        private final Alliance alliance;
        private final PieceKind kind;

        public RawPiece(Piece piece) {
            this.alliance = piece.alliance();
            this.kind = piece.kind();
        }

        public Alliance getAlliance() {
            return alliance;
        }

        public PieceKind getKind() {
            return kind;
        }
    }
}
