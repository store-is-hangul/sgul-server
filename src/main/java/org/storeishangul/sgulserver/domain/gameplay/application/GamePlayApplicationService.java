package org.storeishangul.sgulserver.domain.gameplay.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.response.GameResponse;
import org.storeishangul.sgulserver.domain.gameplay.domain.GameSessionService;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;

@Service
@RequiredArgsConstructor
public class GamePlayApplicationService {

    private final GameSessionService gameSessionService;

    public GameResponse startGame(String userId, String sessionId) {

        GameSession gameSession = gameSessionService.startNewGameSession(userId, sessionId);

        return GameResponse.from(gameSession);
    }
}
