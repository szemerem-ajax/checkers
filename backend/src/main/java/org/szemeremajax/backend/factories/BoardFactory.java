package org.szemeremajax.backend.factories;

import org.szemeremajax.backend.models.Alliance;
import org.szemeremajax.backend.models.Board;
import org.szemeremajax.backend.models.Piece;
import org.szemeremajax.backend.models.PieceKind;
import org.szemeremajax.backend.services.FenParser;

public class BoardFactory {
    public static Board defaultPosition() {
        var pieces = new Piece[50];
        for (int i = 0; i < 20; i++) {
            pieces[i] = new Piece(Alliance.BLACK, PieceKind.MAN);
        }

        for (int i = 30; i < 50; i++) {
            pieces[i] = new Piece(Alliance.WHITE, PieceKind.MAN);
        }

        return new Board(pieces, Alliance.WHITE);
    }

    public static Board fromPdn(String pdn) {
        return null;
    }

    public static Board fromFen(String fen) {
        var parser = new FenParser(fen);

        return parser.parseFen();
    }
}
