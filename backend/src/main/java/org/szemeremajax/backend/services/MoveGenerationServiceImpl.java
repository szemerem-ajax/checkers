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
            int remainder = i % 10;
            if (remainder >= 1 && remainder <= 5) {
                whiteManAttackMap[i][0] = i - 5;
                whiteManAttackMap[i][1] = i - 4;
                blackManAttackMap[i][0] = i + 5;
                blackManAttackMap[i][1] = i + 6;

                if (remainder == 5) {
                    whiteManAttackMap[i][1] = -1;
                    blackManAttackMap[i][1] = -1;
                }
            } else {
                whiteManAttackMap[i][0] = i - 6;
                whiteManAttackMap[i][1] = i - 5;
                blackManAttackMap[i][0] = i + 4;
                blackManAttackMap[i][1] = i + 5;

                if (remainder == 6) {
                    whiteManAttackMap[i][0] = -1;
                    blackManAttackMap[i][0] = -1;
                }
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

        return moves;
    }

    private void iterateBoard(Board board, List<BoardTransition> moves) {
        if (board.getLastJump() != -1) {
            visitTile(board, board.getLastJump(), moves);
            removeNonCaptures(moves);
        } else {
            for (int i = 1; i <= 50; i++)
                visitTile(board, i, moves);

            if (moves.stream().anyMatch(t -> t.move().kind().isCapture()))
                removeNonCaptures(moves);
        }
    }

    private void visitTile(Board board, int tile, List<BoardTransition> moves) {
        if (board.isSquareEmpty(tile))
            return;

        var piece = board.getPiece(tile);
        var alliance = piece.alliance();
        if (board.getSideToMove() != alliance)
            return;

        var kind = piece.kind();
        if (kind == PieceKind.MAN) {
            visitMan(board, tile, alliance, moves);
        } else {
            visitKing(board, tile, alliance, moves);
        }
    }

    private void visitMan(Board board, int tile, Alliance us, List<BoardTransition> moves) {
        // Normal & promotion
        var map = manAttackMap[us.index()][tile];
        int left = map[0];
        int right = map[1];

        if (left != -1) {
            visitManNormal(board, tile, left, moves);
            visitManCapture(board, tile, left, us, us.index(), moves);
        }
        if (right != -1) {
            visitManNormal(board, tile, right, moves);
            visitManCapture(board, tile, right, us, us.index(), moves);
        }

        // Capture in other direction
        map = manAttackMap[us.opposite().index()][tile];
        left = map[0];
        right = map[1];
        if (left != -1)
            visitManCapture(board, tile, left, us, us.opposite().index(), moves);
        if (right != -1)
            visitManCapture(board, tile, right, us, us.opposite().index(), moves);
    }

    private void visitManNormal(Board board, int from, int to, List<BoardTransition> moves) {
        if (board.isSquareOccupied(to))
            return;

        var transition = isFinishingRank(to)
                ? board.makePromotionMove(from, to)
                : board.makeNormalMove(from, to);
        moves.add(transition);
    }

    private void visitManCapture(Board board, int from, int to, Alliance us, int y, List<BoardTransition> moves) {
        if (board.isSquareEmpty(to) || board.getPiece(to).alliance() == us)
            return;

        var map = manAttackMap[y][from];
        int x = to == map[0] ? 0 : 1;
        int landing = manAttackMap[y][to][x];
        if (landing == -1 || board.isSquareOccupied(landing))
            return;

        var transition = board.makeCaptureMove(from, to, landing);
        var newBoard = transition.to();
        var newMoves = generateMoves(newBoard);
        if (newMoves.isEmpty()) {
            newBoard.setLastJump(-1);
            newBoard.setOppositeSideToMove();

            if (isFinishingRank(landing)) {
                newBoard.setPiece(landing, new Piece(us, PieceKind.KING));
                transition = board.makePromotionMove(from, to, landing);
            }
        }

        moves.add(transition);
    }

    private void visitKing(Board board, int tile, Alliance us, List<BoardTransition> moves) {
        visitKingDirection(board, tile, 0, 0, us, moves);
        visitKingDirection(board, tile, 0, 1, us, moves);
        visitKingDirection(board, tile, 1, 0, us, moves);
        visitKingDirection(board, tile, 1, 1, us, moves);
    }

    private void visitKingDirection(Board board, int tile, int x, int y, Alliance us, List<BoardTransition> moves) {
        int pos = manAttackMap[y][tile][x];
        while (pos != -1 && board.isSquareEmpty(pos)) {
            var transition = board.makeNormalMove(tile, pos);
            moves.add(transition);
            pos = manAttackMap[y][pos][x];
        }

        if (pos == -1)
            return;
        if (board.isSquareEmpty(pos) || board.getPiece(pos).alliance() == us)
            return;

        int landing = manAttackMap[y][pos][x];
        if (landing == -1 || board.isSquareOccupied(landing))
            return;

        var transition = board.makeCaptureMove(tile, pos, landing);
        var newBoard = transition.to();
        var newMoves = generateMoves(newBoard);
        if (newMoves.isEmpty()) {
            newBoard.setLastJump(-1);
            newBoard.setOppositeSideToMove();
        }
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

    private static void removeNonCaptures(List<BoardTransition> moves) {
        moves.removeIf(t -> !t.move().kind().isCapture());
    }
}
