package org.storeishangul.sgulserver.domain.gameplay.domain;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;
import org.storeishangul.sgulserver.domain.gameplay.infra.GameSessionRepository;

@Service
@RequiredArgsConstructor
public class GameSessionService {

    private final GameSessionRepository gameSessionRepository;

    public GameSession startNewGameSession(String userId, String sessionId) {

        GameSession gameSession = GameSession.init(userId, sessionId);
        gameSession.drawCards();
        gameSessionRepository.save(gameSession);

        return gameSession;
    }

    public GameSession initializeOrRestoreSession(String userId, String sessionId) {

        GameSession gameSession = gameSessionRepository.findByUserId(userId);

        if(gameSession == null) {
            return startNewGameSession(userId, sessionId);
        }

        gameSession.updateSessionId(sessionId);

        return gameSession;
    }


    public GameSession saveSession(GameSession gameSession) {

        return gameSessionRepository.save(gameSession);
    }
}
