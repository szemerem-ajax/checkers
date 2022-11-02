package org.szemeremajax.backend.factories;

import org.szemeremajax.backend.models.Alliance;
import org.szemeremajax.backend.models.Piece;
import org.szemeremajax.backend.models.PieceKind;

public class PieceFactory {
    public static PieceConstructionResult fromFen(Alliance alliance, String fen) {
        var kind = isKing(fen) ? PieceKind.KING : PieceKind.MAN;
        var rest = kind == PieceKind.KING ? fen.substring(1) : fen;
        var piece = new Piece(alliance, kind);
        int pos = Integer.parseInt(rest);

        return new PieceConstructionResult(pos, piece);
    }

    private static boolean isKing(String fen) {
        return fen.charAt(0) == 'K' || fen.charAt(1) == 'k';
    }

    public record PieceConstructionResult(int pos, Piece piece) {
    }
}
