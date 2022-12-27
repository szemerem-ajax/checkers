package org.szemeremajax.backend.models;

public class Board implements Cloneable {
    private final Piece[] board;
    private Alliance sideToMove;
    private int lastJump = -1;

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

    public int getLastJump() {
        return lastJump;
    }

    public void setLastJump(int lastJump) {
        this.lastJump = lastJump;
    }

    public BoardTransition makeNormalMove(int from, int to) {
        var clone = clone();
        var move = Move.createNormal(from, to);

        clone.setPiece(to, clone.setPiece(from, null));
        clone.setOppositeSideToMove();
        return new BoardTransition(move, this, clone);
    }

    public BoardTransition makeCaptureMove(int from, int via, int to) {
        var clone = clone();
        var move = Move.createCapture(from, to);

        clone.setPiece(via, null);
        clone.setPiece(to, clone.setPiece(from, null));
        clone.setLastJump(to);
        // Whether this ends the turn will be decided in the move generator
        return new BoardTransition(move, this, clone);
    }

    public BoardTransition makePromotionMove(int from, int to) {
        var clone = clone();
        var move = Move.createPromotion(from, to);

        var alliance = clone.setPiece(from, null).alliance();
        clone.setPiece(to, new Piece(alliance, PieceKind.KING));
        clone.setOppositeSideToMove();
        return new BoardTransition(move, this, clone);
    }

    public BoardTransition makePromotionMove(int from, int via, int to) {
        var clone = clone();
        var move = Move.createPromotionWithCapture(from, to);

        var alliance = clone.setPiece(from, null).alliance();
        clone.setPiece(via, null);
        clone.setPiece(to, new Piece(alliance, PieceKind.KING));
        clone.setOppositeSideToMove();
        return new BoardTransition(move, this, clone);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Board clone() {
        var cloned = board.clone();
        return new Board(cloned, sideToMove);
    }
}
