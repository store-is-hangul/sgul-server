package org.storeishangul.sgulserver.domain.gameplay.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.request.CalculatePointRequest;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.request.DrawRequest;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.response.GameResponse;
import org.storeishangul.sgulserver.domain.gameplay.application.GamePlayApplicationService;
import org.storeishangul.sgulserver.domain.gameplay.domain.GameSessionService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CardGameController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GamePlayApplicationService gamePlayApplicationService;

    @MessageMapping("/game/start")
    public void startGame(SimpMessageHeaderAccessor accessor) {

        String userId = (String) accessor.getSessionAttributes().get("userId");
        log.warn("CONNECTED: {}", userId);

        messagingTemplate.convertAndSendToUser(
            userId,
            "/queue/game",
            gamePlayApplicationService.startGame(
                userId,
                accessor.getSessionId()
            )
        );
    }

    @MessageMapping("/game/draw")
    public void drawCards(SimpMessageHeaderAccessor accessor, DrawRequest request) {

        String userId = (String) accessor.getSessionAttributes().get("userId");

        messagingTemplate.convertAndSendToUser(
            userId,
            "/queue/game",
            gamePlayApplicationService.drawCards(
                userId,
                accessor.getSessionId(),
                request
            )
        );
    }

    @MessageMapping("/game/point")
    @SendToUser("/queue/point")
    public void calculatePoints(SimpMessageHeaderAccessor accessor, CalculatePointRequest request) {

        String userId = (String) accessor.getSessionAttributes().get("userId");

        messagingTemplate.convertAndSendToUser(
            userId,
            "/queue/point",
            gamePlayApplicationService.calculatePoint(
                userId,
                accessor.getSessionId(),
                request
            )
        );
    }
}
