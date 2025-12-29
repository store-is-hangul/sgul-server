package org.storeishangul.sgulserver.domain.gameplay.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.request.DrawRequest;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.request.OnDeskRequest;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.response.GameResponse;
import org.storeishangul.sgulserver.domain.gameplay.application.GamePlayApplicationService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CardGameController {

    private final GamePlayApplicationService gamePlayApplicationService;

    @MessageMapping("/game/start")
    @SendToUser("/queue/game")
    public GameResponse startGame(Principal principal, SimpMessageHeaderAccessor accessor) {

        log.info(">>>>> startGame called - principal: {}, sessionId: {}",
            principal != null ? principal.getName() : "NULL",
            accessor.getSessionId());

        String userId = principal.getName();

        return gamePlayApplicationService.startGame(
            userId,
            accessor.getSessionId()
        );
    }

    @MessageMapping("/game/draw")
    @SendToUser("/queue/draw")
    public GameResponse drawCards(Principal principal, SimpMessageHeaderAccessor accessor, DrawRequest request) {

        String userId = principal.getName();

        return gamePlayApplicationService.drawCards(
            userId,
            accessor.getSessionId(),
            request
        );
    }

    @MessageMapping("/game/desk")
    @SendToUser("/queue/desk")
    public GameResponse processJobsOnDesk(Principal principal, SimpMessageHeaderAccessor accessor, OnDeskRequest request) {

        String userId = principal.getName();

        return gamePlayApplicationService.processJobsOnDesk(
            userId,
            accessor.getSessionId(),
            request
        );
    }

    @MessageMapping("/game/point")
    @SendToUser("/queue/point")
    public GameResponse calculatePoints(Principal principal, SimpMessageHeaderAccessor accessor) {

        String userId = principal.getName();

        return gamePlayApplicationService.calculatePoint(
            userId,
            accessor.getSessionId()
        );
    }

    @MessageMapping("/game/over")
    @SendToUser("/queue/over")
    public GameResponse finishTheGame(Principal principal, SimpMessageHeaderAccessor accessor) {

        String userId = principal.getName();

        return gamePlayApplicationService.finishTheGame(
            userId,
            accessor.getSessionId()
        );
    }
}
