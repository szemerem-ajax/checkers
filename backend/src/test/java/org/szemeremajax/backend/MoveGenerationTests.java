package org.szemeremajax.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.szemeremajax.backend.factories.BoardFactory;
import org.szemeremajax.backend.models.*;
import org.szemeremajax.backend.services.MoveGenerationService;
import org.szemeremajax.backend.services.MoveGenerationServiceImpl;

public class MoveGenerationTests {
    private final MoveGenerationService generator = new MoveGenerationServiceImpl();

    @Test
    public void emptyBoard() {
        var board = new Board();
        var moves = generator.generateMoves(board);

        Assertions.assertTrue(moves.isEmpty());
    }

    @Test
    public void whiteMan() {
        var board = new Board();
        board.setPiece(32, new Piece(Alliance.WHITE, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(2, moves.size());
        var left = moves.get(0);
        Assertions.assertEquals(27, left.move().to());
        Assertions.assertNull(left.to().getPiece(32));
        Assertions.assertNotNull(left.to().getPiece(27));

        var right = moves.get(1);
        Assertions.assertEquals(28, right.move().to());
        Assertions.assertNull(right.to().getPiece(32));
        Assertions.assertNotNull(right.to().getPiece(28));
    }

    @Test
    public void whiteMan2() {
        var board = new Board();
        board.setPiece(28, new Piece(Alliance.WHITE, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(2, moves.size());
        var left = moves.get(0);
        Assertions.assertEquals(22, left.move().to());
        Assertions.assertNull(left.to().getPiece(28));
        Assertions.assertNotNull(left.to().getPiece(22));

        var right = moves.get(1);
        Assertions.assertEquals(23, right.move().to());
        Assertions.assertNull(right.to().getPiece(28));
        Assertions.assertNotNull(right.to().getPiece(23));
    }

    @Test
    public void blackMan() {
        var board = new Board();
        board.setPiece(28, new Piece(Alliance.BLACK, PieceKind.MAN));
        board.setSideToMove(Alliance.BLACK);
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(2, moves.size());
        var left = moves.get(0);
        Assertions.assertEquals(32, left.move().to());
        Assertions.assertNull(left.to().getPiece(28));
        Assertions.assertNotNull(left.to().getPiece(32));

        var right = moves.get(1);
        Assertions.assertEquals(33, right.move().to());
        Assertions.assertNull(right.to().getPiece(28));
        Assertions.assertNotNull(right.to().getPiece(33));
    }

    @Test
    public void blackMan2() {
        var board = new Board();
        board.setPiece(32, new Piece(Alliance.BLACK, PieceKind.MAN));
        board.setSideToMove(Alliance.BLACK);
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(2, moves.size());
        var left = moves.get(0);
        Assertions.assertEquals(37, left.move().to());
        Assertions.assertNull(left.to().getPiece(32));
        Assertions.assertNotNull(left.to().getPiece(37));

        var right = moves.get(1);
        Assertions.assertEquals(38, right.move().to());
        Assertions.assertNull(right.to().getPiece(32));
        Assertions.assertNotNull(right.to().getPiece(38));
    }

    @Test
    public void whiteLeftEdge() {
        var board = new Board();
        board.setPiece(16, new Piece(Alliance.WHITE, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
    }

    @Test
    public void whiteRightEdge() {
        var board = new Board();
        board.setSideToMove(Alliance.BLACK);
        board.setPiece(35, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
    }

    @Test
    public void blackLeftEdge() {
        var board = new Board();
        board.setPiece(26, new Piece(Alliance.BLACK, PieceKind.MAN));
        board.setSideToMove(Alliance.BLACK);
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
    }

    @Test
    public void blackRightEdge() {
        var board = new Board();
        board.setPiece(15, new Piece(Alliance.WHITE, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
    }

    @Test
    public void whiteBlocked() {
        var board = new Board();
        board.setPiece(32, new Piece(Alliance.WHITE, PieceKind.MAN));
        board.setPiece(28, new Piece(Alliance.WHITE, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(3, moves.size());
    }

    @Test
    public void blackBlocked() {
        var board = new Board();
        board.setSideToMove(Alliance.BLACK);
        board.setPiece(28, new Piece(Alliance.BLACK, PieceKind.MAN));
        board.setPiece(32, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(3, moves.size());
    }

    @Test
    public void whiteCaptureLeft() {
        var board = new Board();
        board.setPiece(25, new Piece(Alliance.WHITE, PieceKind.MAN));
        board.setPiece(20, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
        var move = moves.get(0);
        board = move.to();
        Assertions.assertEquals(MoveKind.CAPTURE, move.move().kind());
        Assertions.assertNull(board.getPiece(20));
        Assertions.assertNull(board.getPiece(25));
        Assertions.assertNotNull(board.getPiece(14));
    }

    @Test
    public void whiteCaptureLeft2() {
        var board = new Board();
        board.setPiece(32, new Piece(Alliance.WHITE, PieceKind.MAN));
        board.setPiece(28, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
        var move = moves.get(0);
        Assertions.assertEquals(MoveKind.CAPTURE, move.move().kind());
    }

    @Test
    public void blackCaptureLeft() {
        var board = new Board();
        board.setOppositeSideToMove();
        board.setPiece(24, new Piece(Alliance.WHITE, PieceKind.MAN));
        board.setPiece(19, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
        var move = moves.get(0);
        Assertions.assertEquals(MoveKind.CAPTURE, move.move().kind());
    }

    @Test
    public void blackCaptureLeft2() {
        var board = new Board();
        board.setOppositeSideToMove();
        board.setPiece(32, new Piece(Alliance.WHITE, PieceKind.MAN));
        board.setPiece(28, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
        var move = moves.get(0);
        Assertions.assertEquals(MoveKind.CAPTURE, move.move().kind());
    }

    @Test
    public void whitePromotion() {
        var board = new Board();
        board.setPiece(7, new Piece(Alliance.WHITE, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(2, moves.size());
        var left = moves.get(0);
        Assertions.assertEquals(MoveKind.PROMOTION, left.move().kind());
        Assertions.assertNull(left.to().getPiece(7));
        Assertions.assertNotNull(left.to().getPiece(1));
        Assertions.assertEquals(PieceKind.KING, left.to().getPiece(1).kind());

        var right = moves.get(1);
        Assertions.assertEquals(MoveKind.PROMOTION, right.move().kind());
        Assertions.assertNull(right.to().getPiece(7));
        Assertions.assertNotNull(right.to().getPiece(2));
        Assertions.assertEquals(PieceKind.KING, right.to().getPiece(2).kind());
    }

    @Test
    public void blackPromotion() {
        var board = new Board();
        board.setOppositeSideToMove();
        board.setPiece(41, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(2, moves.size());
        var left = moves.get(0);
        Assertions.assertEquals(MoveKind.PROMOTION, left.move().kind());
        Assertions.assertNull(left.to().getPiece(41));
        Assertions.assertNotNull(left.to().getPiece(46));
        Assertions.assertEquals(PieceKind.KING, left.to().getPiece(46).kind());

        var right = moves.get(1);
        Assertions.assertEquals(MoveKind.PROMOTION, right.move().kind());
        Assertions.assertNull(right.to().getPiece(41));
        Assertions.assertNotNull(right.to().getPiece(47));
        Assertions.assertEquals(PieceKind.KING, right.to().getPiece(47).kind());
    }

    @Test
    public void whiteCaptureWithPromotion() {
        var board = new Board();
        board.setPiece(13, new Piece(Alliance.WHITE, PieceKind.MAN));
        board.setPiece(8, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
        var move = moves.get(0);
        Assertions.assertEquals(MoveKind.PROMOTION_WITH_CAPTURE, move.move().kind());
        var result = move.to();
        Assertions.assertNull(result.getPiece(13));
        Assertions.assertNull(result.getPiece(8));
        Assertions.assertNotNull(result.getPiece(2));
        var piece = result.getPiece(2);
        Assertions.assertEquals(Alliance.WHITE, piece.alliance());
        Assertions.assertEquals(PieceKind.KING, piece.kind());
    }

    @Test
    public void blackCaptureWithPromotion() {
        var board = new Board();
        board.setOppositeSideToMove();
        board.setPiece(42, new Piece(Alliance.WHITE, PieceKind.MAN));
        board.setPiece(38, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
        var move = moves.get(0);
        Assertions.assertEquals(MoveKind.PROMOTION_WITH_CAPTURE, move.move().kind());
        var result = move.to();
        Assertions.assertNull(result.getPiece(42));
        Assertions.assertNull(result.getPiece(38));
        Assertions.assertNotNull(result.getPiece(47));
        var piece = result.getPiece(47);
        Assertions.assertEquals(Alliance.BLACK, piece.alliance());
        Assertions.assertEquals(PieceKind.KING, piece.kind());
    }

    @Test
    public void whiteKing() {
        var board = new Board();
        board.setPiece(34, new Piece(Alliance.WHITE, PieceKind.KING));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(13, moves.size());
        var to = moves.stream().map(m -> m.move().to()).sorted().toList();
        var buf = new Integer[13];
        to.toArray(buf);

        Assertions.assertArrayEquals(new Integer[] { 1, 7, 12, 18, 23, 25, 29, 30, 39, 40, 43, 45, 48 }, buf);
    }

    @Test
    public void whiteKingBlocked() {
        var board = new Board();
        board.setPiece(34, new Piece(Alliance.WHITE, PieceKind.KING));
        board.setPiece(12, new Piece(Alliance.WHITE, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        var to = moves.stream().filter(m -> m.move().from() == 34).map(m -> m.move().to()).sorted().toList();
        Assertions.assertEquals(10, to.size());
        var buf = new Integer[10];
        to.toArray(buf);

        Assertions.assertArrayEquals(new Integer[] { 18, 23, 25, 29, 30, 39, 40, 43, 45, 48 }, buf);
    }

    @Test
    public void whiteKingCapture() {
        var board = new Board();
        board.setPiece(29, new Piece(Alliance.WHITE, PieceKind.KING));
        board.setPiece(12, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
        var move = moves.get(0);
        Assertions.assertEquals(MoveKind.CAPTURE, move.move().kind());
        Assertions.assertEquals(29, move.move().from());
        Assertions.assertEquals(7, move.move().to());
        Assertions.assertNull(move.to().getPiece(12));
        Assertions.assertNull(move.to().getPiece(29));
        Assertions.assertNotNull(move.to().getPiece(7));
    }

    @Test
    public void blackKing() {
        var board = new Board();
        board.setOppositeSideToMove();
        board.setPiece(34, new Piece(Alliance.BLACK, PieceKind.KING));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(13, moves.size());
        var to = moves.stream().map(m -> m.move().to()).sorted().toList();
        var buf = new Integer[13];
        to.toArray(buf);

        Assertions.assertArrayEquals(new Integer[] { 1, 7, 12, 18, 23, 25, 29, 30, 39, 40, 43, 45, 48 }, buf);
    }

    @Test
    public void blackKingBlocked() {
        var board = new Board();
        board.setOppositeSideToMove();
        board.setPiece(34, new Piece(Alliance.BLACK, PieceKind.KING));
        board.setPiece(12, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        var to = moves.stream().filter(m -> m.move().from() == 34).map(m -> m.move().to()).sorted().toList();
        Assertions.assertEquals(10, to.size());
        var buf = new Integer[10];
        to.toArray(buf);

        Assertions.assertArrayEquals(new Integer[] { 18, 23, 25, 29, 30, 39, 40, 43, 45, 48 }, buf);
    }

    @Test
    public void blackKingCapture() {
        var board = new Board();
        board.setOppositeSideToMove();
        board.setPiece(29, new Piece(Alliance.BLACK, PieceKind.KING));
        board.setPiece(12, new Piece(Alliance.WHITE, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
        var move = moves.get(0);
        Assertions.assertEquals(MoveKind.CAPTURE, move.move().kind());
        Assertions.assertEquals(29, move.move().from());
        Assertions.assertEquals(7, move.move().to());
        Assertions.assertNull(move.to().getPiece(12));
        Assertions.assertNull(move.to().getPiece(29));
        Assertions.assertNotNull(move.to().getPiece(7));
    }

    @Test
    public void startingPosition() {
        var board = BoardFactory.defaultPosition();
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(9, moves.size());
    }

    @Test
    public void blackEdgeBug() {
        var board = new Board();
        board.setOppositeSideToMove();
        board.setPiece(16, new Piece(Alliance.BLACK, PieceKind.MAN));
        var moves = generator.generateMoves(board);

        Assertions.assertEquals(1, moves.size());
        var move = moves.get(0);
        Assertions.assertEquals(21, move.move().to());
    }
}
