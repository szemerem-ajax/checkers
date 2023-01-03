package org.szemeremajax.backend.wscontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.szemeremajax.backend.messages.BoardMovesMessage;
import org.szemeremajax.backend.messages.BoardUpdateMessage;
import org.szemeremajax.backend.messages.MoveMessage;
import org.szemeremajax.backend.messages.BoardMessage;
import org.szemeremajax.backend.services.AuthService;
import org.szemeremajax.backend.services.BoardService;
import org.szemeremajax.backend.services.MoveGenerationService;

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

    @MessageMapping("/getboard")
    public void getBoard(@Payload BoardMessage message) {
        sendBoardUpdate(message.getUuid());
    }

    @MessageMapping("/getmoves")
    public void getMoves(@Payload BoardMessage message) {
        sendMovesUpdate(message.getUuid());
    }

    @MessageMapping("/move")
    public void move(@Payload MoveMessage message) {
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
        template.convertAndSend(url, new BoardUpdateMessage(board));
    }

    private void sendMovesUpdate(String uuid) {
        var board = boardService.lookupBoard(uuid).orElseThrow();
        var moves = moveGenerationService.generateMoves(board);
        var url = "/game/" + uuid + "/moves";
        template.convertAndSend(url, new BoardMovesMessage(moves));
    }
}
