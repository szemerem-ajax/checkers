package org.szemeremajax.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.szemeremajax.backend.models.Alliance;
import org.szemeremajax.backend.models.Board;
import org.szemeremajax.backend.models.Piece;
import org.szemeremajax.backend.services.BoardService;

import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    BoardService boardService;

    @GetMapping("/create")
    String create() {
        var id = UUID.randomUUID().toString();
        var board = new Board(new Piece[50], Alliance.WHITE);
        boardService.addBoard(id, board);

        return id;
    }

    @GetMapping("/find/[id]")
    String find(@RequestParam String id) {
        var exists = boardService.lookupBoard(id).isPresent();
        return Boolean.toString(exists);
    }
}
