package org.szemeremajax.backend.wscontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.szemeremajax.backend.dtos.BoardMovesDto;
import org.szemeremajax.backend.dtos.BoardUpdateDto;
import org.szemeremajax.backend.dtos.MoveDto;
import org.szemeremajax.backend.dtos.UuidDto;
import org.szemeremajax.backend.services.AuthService;
import org.szemeremajax.backend.services.BoardService;
import org.szemeremajax.backend.services.MoveGenerationService;

/**
 * Provides websocket endpoints for gameplay.
 */
@Controller
@CrossOrigin(originPatterns = "*")
public class CheckersController {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MoveGenerationService moveGenerationService;

    @Autowired
    private AuthService authService;

    /**
     * Signals to the server that a board update packet should be sent.
     * @param message The payload containing the game's id.
     */
    @MessageMapping("/getboard")
    public void getBoard(@Payload UuidDto message) {
        sendBoardUpdate(message.getUuid());
    }

    @MessageMapping("/getmoves")
    public void getMoves(@Payload UuidDto message) {
        sendMovesUpdate(message.getUuid());
    }

    @MessageMapping("/move")
    public void move(@Payload MoveDto message) {
        var board = boardService.lookupBoard(message.getBoardUuid()).orElseThrow();
        if (!authService.isAuthorized(message.getBoardUuid(), message.getAuthUuid(), board.getSideToMove())) {
            throw new IllegalArgumentException();
        }

        var moves = moveGenerationService.generateMoves(board);
        var choice = moves.stream().filter(message::matches).findFirst().orElseThrow();
        var newBoard = choice.to();
        boardService.setBoard(message.getBoardUuid(), newBoard);
        sendBoardUpdate(message.getBoardUuid());
        sendMovesUpdate(message.getBoardUuid());
    }

    private void sendBoardUpdate(String uuid) {
        var board = boardService.lookupBoard(uuid).orElseThrow();
        var url = "/game/" + uuid + "/board";
        template.convertAndSend(url, new BoardUpdateDto(board));
    }

    private void sendMovesUpdate(String uuid) {
        var board = boardService.lookupBoard(uuid).orElseThrow();
        var moves = moveGenerationService.generateMoves(board);
        var url = "/game/" + uuid + "/moves";
        template.convertAndSend(url, new BoardMovesDto(moves));
    }
}
