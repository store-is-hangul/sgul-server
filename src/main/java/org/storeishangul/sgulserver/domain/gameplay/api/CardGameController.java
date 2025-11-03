package org.storeishangul.sgulserver.domain.gameplay.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.response.GameResponse;
import org.storeishangul.sgulserver.domain.gameplay.application.GamePlayApplicationService;
import org.storeishangul.sgulserver.domain.gameplay.domain.GameSessionService;

@Controller
@RequiredArgsConstructor
public class CardGameController {

    private final GamePlayApplicationService gamePlayApplicationService;

    // TODO:
    @MessageMapping("/game/start")
    @SendTo("/user/queue/game")
    public GameResponse startGame(SimpMessageHeaderAccessor accessor) {

        return gamePlayApplicationService.startGame(
            (String) accessor.getSessionAttributes().get("userId"),
            accessor.getSessionId()
        );
    }
}
