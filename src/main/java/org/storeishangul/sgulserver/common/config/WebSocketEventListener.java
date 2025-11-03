package org.storeishangul.sgulserver.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.storeishangul.sgulserver.domain.gameplay.domain.GameSessionService;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final GameSessionService gameSessionService;

    // 연결 시
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = headerAccessor.getFirstNativeHeader("userId");
        String sessionId = headerAccessor.getSessionId();

        log.info("User connected: {} with session: {}", userId, sessionId);

        // 게임 세션 초기화 또는 복구
        gameSessionService.initializeOrRestoreSession(userId, sessionId);
    }

    // 아직 불필요
    // @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        if (event.getSessionId() == null) {

            return;
        }
    }
}
