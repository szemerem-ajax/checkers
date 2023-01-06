package org.szemeremajax.backend.models;

/**
 * This class holds information about a game of checkers. Like all the pieces and which side is to move next.
 */
public class Board implements Cloneable {
    private final Piece[] board;
    private Alliance sideToMove;
    private int lastJump = -1;

    /**
     * Creates an empty board with white to move.
     */
    public Board() {
        this(new Piece[50], Alliance.WHITE);
    }

    /**
     * Creates a board with the given pieces and side to move.
     * @param board The pieces.
     * @param sideToMove The side to move next.
     */
    public Board(Piece[] board, Alliance sideToMove) {
        if (board.length != 50)
            throw new IllegalArgumentException("Board has to be 10x10");

        this.board = board;
        this.sideToMove = sideToMove;
    }

    /**
     * Determines whether the given square is empty.
     * @param num The square.
     * @return Whether the given square is empty.
     */
    public boolean isSquareEmpty(int num) {
        return board[num - 1] == null;
    }

    /**
     * Determines whether the given square is empty.
     * @param num The square.
     * @return Whether the given square is empty.
     */
    public boolean isSquareOccupied(int num) {
        return !isSquareEmpty(num);
    }

    /**
     * Gets the piece at the given square.
     * @param num The square.
     * @return The piece at the given square.
     */
    public Piece getPiece(int num) {
        return board[num - 1];
    }

    /**
     * Sets the piece at the given square to the given piece and returns the piece that was there before.
     * @param num The square.
     * @return The piece that was replaced.
     */
    public Piece setPiece(int num, Piece piece) {
        var tmp = getPiece(num);
        board[num - 1] = piece;

        return tmp;
    }

    /**
     * Gets the side that has to move next.
     * @return The side to move.
     */
    public Alliance getSideToMove() {
        return sideToMove;
    }

    /**
     * Gets a raw array of the pieces.
     * @return The raw array of pieces.
     */
    public Piece[] getPiecesRaw() {
        return board;
    }

    /**
     * Sets the side that has to move next.
     * @param sideToMove The side to move.
     */
    public void setSideToMove(Alliance sideToMove) {
        this.sideToMove = sideToMove;
    }

    /**
     * Sets the opposite side to move next.
     */
    public void setOppositeSideToMove() {
        setSideToMove(getSideToMove().opposite());
    }

    /**
     * Gets the last landing square of a capture move (if the previous move wasn't a capture, this will return -1).
     * @return The last landing square of a capture move.
     */
    public int getLastJump() {
        return lastJump;
    }

    /**
     * Sets the last landing square of a capture move.
     */
    public void setLastJump(int lastJump) {
        this.lastJump = lastJump;
    }

    /**
     * Performs a normal move.
     * @param from The originating square.
     * @param to The landing square.
     * @return The board transition resulting from the move.
     */
    public BoardTransition makeNormalMove(int from, int to) {
        var clone = clone();
        var move = Move.createNormal(from, to);

        clone.setPiece(to, clone.setPiece(from, null));
        clone.setOppositeSideToMove();
        return new BoardTransition(move, this, clone);
    }

    /**
     * Performs a capture move.
     * @param from The originating square.
     * @param via The square on which the piece was captured.
     * @param to The landing square.
     * @return The board transition resulting from the move.
     */
    public BoardTransition makeCaptureMove(int from, int via, int to) {
        var clone = clone();
        var move = Move.createCapture(from, to);

        clone.setPiece(via, null);
        clone.setPiece(to, clone.setPiece(from, null));
        clone.setLastJump(to);
        // Whether this ends the turn will be decided in the move generator
        return new BoardTransition(move, this, clone);
    }

    /**
     * Performs a promotion move.
     * @param from The originating square.
     * @param to The landing square.
     * @return The board transition resulting from the move.
     */
    public BoardTransition makePromotionMove(int from, int to) {
        var clone = clone();
        var move = Move.createPromotion(from, to);

        var alliance = clone.setPiece(from, null).alliance();
        clone.setPiece(to, new Piece(alliance, PieceKind.KING));
        clone.setOppositeSideToMove();
        return new BoardTransition(move, this, clone);
    }

    /**
     * Performs a promotion-via-capture move.
     * @param from The originating square.
     * @param via The square of the piece that was captured.
     * @param to The landing square.
     * @return The board transition resulting from the move.
     */
    public BoardTransition makePromotionMove(int from, int via, int to) {
        var clone = clone();
        var move = Move.createPromotionWithCapture(from, to);

        var alliance = clone.setPiece(from, null).alliance();
        clone.setPiece(via, null);
        clone.setPiece(to, new Piece(alliance, PieceKind.KING));
        clone.setOppositeSideToMove();
        return new BoardTransition(move, this, clone);
    }

    /**
     * Creates a clone of the current board.
     * @return The newly cloned board.
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Board clone() {
        var cloned = board.clone();
        return new Board(cloned, sideToMove);
    }
}
