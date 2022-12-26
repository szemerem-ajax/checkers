package org.szemeremajax.backend.models;

public class Board implements Cloneable {
    private final Piece[] board;
    private Alliance sideToMove;

    public Board() {
        this(new Piece[50], Alliance.WHITE);
    }

    public Board(Piece[] board, Alliance sideToMove) {
        if (board.length != 50)
            throw new IllegalArgumentException("Board has to be 10x10");

        this.board = board;
        this.sideToMove = sideToMove;
    }

    public boolean isSquareEmpty(int num) {
        return board[num - 1] == null;
    }

    public boolean isSquareOccupied(int num) {
        return !isSquareEmpty(num);
    }

    public Piece getPiece(int num) {
        return board[num - 1];
    }

    public Piece setPiece(int num, Piece piece) {
        var tmp = getPiece(num);
        board[num - 1] = piece;

        return tmp;
    }

    public Alliance getSideToMove() {
        return sideToMove;
    }

    public Piece[] getPiecesRaw() {
        return board;
    }

    public void setSideToMove(Alliance sideToMove) {
        this.sideToMove = sideToMove;
    }

    public void setOppositeSideToMove() {
        setSideToMove(getSideToMove().opposite());
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Board clone() {
        var cloned = board.clone();
        return new Board(cloned, sideToMove);
    }
}
