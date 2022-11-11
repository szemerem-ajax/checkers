package org.szemeremajax.backend.services;

import org.szemeremajax.backend.models.Alliance;
import org.szemeremajax.backend.models.Board;
import org.szemeremajax.backend.models.Piece;

import java.io.IOException;
import java.io.StringReader;

public class FenParser {
    private final StringReader reader;

    public FenParser(String fen) {
        this.reader = new StringReader(fen);
    }

    public Board parseFen() {
        var sideToMove = getAlliance();
        var pieces = new Piece[50];
        expectChar(':');

        return new Board(pieces, sideToMove);
    }

    private Alliance getAlliance() {
        var alliance = nextChar();
        try {
            return Alliance.fromFen(alliance);
        } catch (Exception e) {
            throw new InvalidFenException("Unknown alliance: " + alliance, e);
        }
    }

    private char expectChar(char expected) {
        var next = nextChar();
        if (next != expected) {
            throw new InvalidFenException("Expected '" + expected + "', but got '" + next + "'");
        }

        return next;
    }

    private char nextChar() {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            throw new InvalidFenException("Unexpected end of input", e);
        }
    }

}
