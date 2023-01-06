package org.szemeremajax.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.szemeremajax.backend.services.BoardService;
import org.springframework.web.bind.annotation.*;
import org.szemeremajax.backend.models.*;

import java.util.UUID;

/**
 * Provides endpoints for creating and finding games.
 */
@RestController
@RequestMapping("/game")
@CrossOrigin(originPatterns = "*")
public class GameController {
    @Autowired
    private BoardService boardService;

    /**
     * Creates a new game.
     * @return The created game's id.
     */
    @PostMapping("/create")
    String create() {
        var id = UUID.randomUUID().toString();
        var board = defaultPosition();
        boardService.setBoard(id, board);

        return id;
    }

    /**
     * Determines whether a game exists with the given id.
     * @param id The game's id.
     * @return Whether the game exists.
     */
    @GetMapping("/find/{id}")
    boolean find(@PathVariable String id) {
        return boardService.lookupBoard(id).isPresent();
    }

    private static Board defaultPosition() {
        var pieces = new Piece[50];
        for (int i = 0; i < 20; i++) {
            pieces[i] = new Piece(Alliance.BLACK, PieceKind.MAN);
        }

        for (int i = 30; i < 50; i++) {
            pieces[i] = new Piece(Alliance.WHITE, PieceKind.MAN);
        }

        return new Board(pieces, Alliance.WHITE);
    }
}
