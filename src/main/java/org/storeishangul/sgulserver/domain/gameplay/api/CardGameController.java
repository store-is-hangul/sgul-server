package org.storeishangul.sgulserver.domain.gameplay.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CardGameController {

    // TODO:
    /*
    @MessageMapping("/game/start")
    @SendTo("/user/queue/game")
    public GameResponse startGame(Principal principal) {

    }

    @MessageMapping("/game/play")
    @SendTo("/user/queue/game")
    public GameResponse playCards(
            PlayCardRequest request,
            Principal principal
    ) {
        String userId = principal.getName();

        return gameSessionService.playCards(userId, request);
    }


    */
}
