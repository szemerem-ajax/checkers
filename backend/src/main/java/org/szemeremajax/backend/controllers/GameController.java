package org.szemeremajax.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.szemeremajax.backend.factories.BoardFactory;
import org.szemeremajax.backend.services.BoardService;

import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    BoardService boardService;

    @PostMapping("/create")
    String create() {
        var id = UUID.randomUUID().toString();
        var board = BoardFactory.defaultPosition();
        boardService.addBoard(id, board);

        return id;
    }

    @GetMapping("/find/{id}")
    String find(@PathVariable String id) {
        var exists = boardService.lookupBoard(id).isPresent();
        return Boolean.toString(exists);
    }
}
