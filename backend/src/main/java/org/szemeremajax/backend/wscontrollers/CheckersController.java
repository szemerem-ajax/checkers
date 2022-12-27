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
import org.szemeremajax.backend.messages.RequestMovesMessage;
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

    @MessageMapping("/getboard")
    public void getBoard(@Payload GetBoardMessage message) {
        var board = boardService.lookupBoard(message.getUuid()).orElseThrow();
        template.convertAndSend("/game/" + message.getUuid() + "/board", new BoardUpdateMessage(board));
    }

    @MessageMapping("/getmoves")
    public void getMoves(@Payload RequestMovesMessage message) {
        var board = boardService.lookupBoard(message.getUuid()).orElseThrow();
        var moves = moveGenerationService.generateMoves(board);
        template.convertAndSend("/game/" + message.getUuid() + "/moves", new BoardMovesMessage(moves));
    }

    @MessageMapping("/move")
    public void move(@Payload MoveMessage message) {
        var board = boardService.lookupBoard(message.getUuid()).orElseThrow();
        var moves = moveGenerationService.generateMoves(board);
        var choice = moves.stream().filter(m -> m.move().from() == message.getFrom() && m.move().to() == message.getTo()).findFirst().orElseThrow();
        var newBoard = choice.to();
        boardService.setBoard(message.getUuid(), newBoard);
        template.convertAndSend("/game/" + message.getUuid() + "/board", new BoardUpdateMessage(newBoard));
        template.convertAndSend("/game/" + message.getUuid() + "/moves", new BoardMovesMessage(moveGenerationService.generateMoves(newBoard)));
    }

    public static class GetBoardMessage {
        private String uuid;

        public String getUuid() {
            return uuid;
        }
    }
}
