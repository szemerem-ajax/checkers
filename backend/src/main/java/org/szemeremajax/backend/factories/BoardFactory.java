package org.szemeremajax.backend.factories;

import org.szemeremajax.backend.models.Board;
import org.szemeremajax.backend.services.FenParser;

public class BoardFactory {
    public static Board fromPdn(String pdn) {
        return null;
    }

    public static Board fromFen(String fen) {
        var parser = new FenParser(fen);

        return parser.parseFen();
    }
}
