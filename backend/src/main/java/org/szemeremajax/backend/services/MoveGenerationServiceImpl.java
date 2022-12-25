package org.szemeremajax.backend.services;

import org.springframework.stereotype.Component;
import org.szemeremajax.backend.models.*;

import java.util.*;
import java.util.stream.IntStream;

@Component
public class MoveGenerationServiceImpl implements MoveGenerationService {
    private static final int[][] whiteManAttackMap = new int[51][2];
    private static final int[][] blackManAttackMap = new int[51][2];
    private static final int[][][] manAttackMap = new int[2][][];

    static {
        // Initialize all moves to invalid
        for (int i = 0; i < 51; i++) {
            whiteManAttackMap[i][0] = -1;
            whiteManAttackMap[i][1] = -1;
            blackManAttackMap[i][0] = -1;
            blackManAttackMap[i][1] = -1;
        }

        // Generate normal moves, disregarding finishing ranks
        for (int i = 1; i <= 50; i++) {
            if (i % 10 < 6 && i % 10 > 0) {
                whiteManAttackMap[i][0] = i - 5;
                blackManAttackMap[i][1] = i + 6;

                if (i % 10 != 5) {
                    whiteManAttackMap[i][1] = i - 4;
                    blackManAttackMap[i][0] = i + 5;
                }
            } else {
                if (i % 10 != 6) {
                    whiteManAttackMap[i][0] = i - 6;
                    blackManAttackMap[i][1] = i + 5;
                }

                whiteManAttackMap[i][1] = i - 5;
                blackManAttackMap[i][0] = i + 4;
            }
        }

        // Make moves from finishing ranks illegal
        for (int i = 1; i <= 5; i++) {
            whiteManAttackMap[i][0] = -1;
            whiteManAttackMap[i][1] = -1;
        }
        for (int i = 46; i <= 50; i++) {
            blackManAttackMap[i][0] = -1;
            blackManAttackMap[i][1] = -1;
        }

        // Combine attack maps into one for easier use
        manAttackMap[0] = whiteManAttackMap;
        manAttackMap[1] = blackManAttackMap;

        // Remove invalid moves (< 1 || > 50)
        for (int i = 1; i <= 50; i++) {
            if (whiteManAttackMap[i][0] < 1 || whiteManAttackMap[i][0] > 50)
                whiteManAttackMap[i][0] = -1;
            if (whiteManAttackMap[i][1] < 1 || whiteManAttackMap[i][1] > 50)
                whiteManAttackMap[i][1] = -1;
            if (blackManAttackMap[i][0] < 1 || blackManAttackMap[i][0] > 50)
                blackManAttackMap[i][0] = -1;
            if (blackManAttackMap[i][1] < 1 || blackManAttackMap[i][1] > 50)
                blackManAttackMap[i][1] = -1;
        }
    }

    @Override
    public List<BoardTransition> generateMoves(Board board) {
        if (countPieces(board, board.getSideToMove()) == 0)
            return List.of();

        List<BoardTransition> moves = new ArrayList<>();
        iterateBoard(board, moves);

        // Check if we have any captures and if so, only keep those
        if (moves.stream().anyMatch(t -> t.move().kind().isCapture())) {
            moves.removeIf(t -> !t.move().kind().isCapture());
        }

        return moves;
    }

    private static void iterateBoard(Board board, List<BoardTransition> moves) {
        for (int i = 1; i <= 50; i++) {
            if (board.isSquareEmpty(i))
                continue;

            var piece = board.getPiece(i);
            var toMove = board.getSideToMove();
            if (piece.alliance() != toMove)
                continue;

            if (piece.kind() == PieceKind.MAN) {
                visitMan(board, i, piece.alliance(), moves);
            } else {
                visitKing(board, i, piece.alliance(), moves);
            }
        }
    }

    private static void visitKing(Board board, int position, Alliance us, List<BoardTransition> moves) {
        visitSliding(board, us, position, 0, 0, moves);
        visitSliding(board, us, position, 0, 1, moves);
        visitSliding(board, us, position, 1, 0, moves);
        visitSliding(board, us, position, 1, 1, moves);
    }

    private static void visitSliding(Board board, Alliance us, int pos, int dirY, int dirX, List<BoardTransition> moves) {
        int next = manAttackMap[dirY][pos][dirX];
        while (next != -1 && board.isSquareEmpty(next)) {
            var move = Move.createNormal(pos, next);
            var newBoard = board.clone();
            newBoard.setOppositeSideToMove();
            newBoard.setPiece(next, newBoard.setPiece(pos, null));
            moves.add(new BoardTransition(move, board, newBoard));

            next = manAttackMap[dirY][next][dirX];
        }

        if (next == -1)
            return;
        if (board.getPiece(next).alliance() == us)
            return;

        int landing = manAttackMap[dirY][next][dirX];
        if (landing == -1 || board.isSquareOccupied(landing))
            return;

        var move = Move.createCapture(pos, landing);
        var newBoard = board.clone();
        newBoard.setPiece(next, null);
        newBoard.setPiece(landing, newBoard.setPiece(pos, null));
        moves.add(new BoardTransition(move, board, newBoard));
    }

    private static void visitMan(Board board, int position, Alliance alliance, List<BoardTransition> moves) {
        var moveMap = manAttackMap[alliance.index()][position];
        int left = moveMap[0];
        int right = moveMap[1];
        if (left == -1 && right == -1)
            return;

        if (left != -1)
            visitMove(board, alliance, position, left, moves);
        if (right != -1)
            visitMove(board, alliance, position, right, moves);
    }

    private static void visitMove(Board board, Alliance alliance, int from, int to, List<BoardTransition> moves) {
        if (isFinishingRank(to)) {
            visitPromotion(board, alliance, from, to, moves);
            return;
        }
        if (board.isSquareOccupied(to)) {
            visitCapture(board, alliance, from, to, moves);
            return;
        }

        var move = Move.createNormal(from, to);
        var newBoard = board.clone();
        newBoard.setPiece(to, newBoard.setPiece(from, null));
        newBoard.setSideToMove(alliance.opposite());
        var transition = new BoardTransition(move, board, newBoard);
        moves.add(transition);
    }

    private static void visitPromotion(Board board, Alliance alliance, int from, int to, List<BoardTransition> moves) {
        if (board.isSquareOccupied(to))
            return;

        var move = Move.createPromotion(from, to);
        var newBoard = board.clone();
        newBoard.setSideToMove(alliance.opposite());
        newBoard.setPiece(from, null);
        newBoard.setPiece(to, new Piece(alliance, PieceKind.KING));
        var transition = new BoardTransition(move, board, newBoard);
        moves.add(transition);
    }

    private static void visitCapture(Board board, Alliance alliance, int from, int to, List<BoardTransition> moves) {
        if (alliance == board.getPiece(to).alliance())
            return;

        var moveMap = manAttackMap[alliance.index()][from];
        int dir = moveMap[0] == to ? 0 : 1;
        var landing = manAttackMap[alliance.index()][to][dir];
        if (landing != -1 && board.isSquareOccupied(landing))
            return;

        Move move;
        var newBoard = board.clone();
        newBoard.setPiece(to, null);
        if (isFinishingRank(landing)) {
            move = Move.createPromotionWithCapture(from, landing);
            newBoard.setSideToMove(alliance.opposite());
            newBoard.setPiece(from, null);
            newBoard.setPiece(landing, new Piece(alliance, PieceKind.KING));
        } else {
            move = Move.createCapture(from, landing);
            newBoard.setPiece(landing, newBoard.setPiece(from, null));
        }

        var transition = new BoardTransition(move, board, newBoard);
        moves.add(transition);
    }

    private static boolean isFinishingRank(int pos) {
        return (1 <= pos && pos <= 4) // white
                || (46 <= pos && pos <= 50); // black
    }

    private static int countPieces(Board board, Alliance side) {
        return (int) IntStream.rangeClosed(1, 50)
                .filter(board::isSquareOccupied)
                .mapToObj(board::getPiece)
                .filter(p -> p.alliance() == side)
                .count();
    }
}
