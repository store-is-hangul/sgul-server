package org.storeishangul.sgulserver.domain.gameplay.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.gameplay.domain.exception.GameSessionNotFoundException;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;
import org.storeishangul.sgulserver.domain.gameplay.infra.GameSessionRepository;

@Service
@RequiredArgsConstructor
public class GameSessionService {

    private final GameSessionRepository gameSessionRepository;

    public GameSession startNewGameSession(String userId, String sessionId) {

        GameSession gameSession = GameSession.init(userId, sessionId);
        gameSession.drawBalancedCards();
        gameSessionRepository.save(gameSession);

        return gameSession;
    }

    public GameSession initializeOrRestoreSession(String userId, String sessionId) {

        GameSession gameSession = gameSessionRepository.findActivatingByUserId(userId);

        if(gameSession == null) {
            return startNewGameSession(userId, sessionId);
        }

        gameSession.updateSessionId(sessionId);

        return gameSession;
    }

    public GameSession findSessionAndUpdateByUserIdOrElseThrow(String userId, String sessionId) {

        GameSession gameSession = gameSessionRepository.findActivatingByUserId(userId);

        if(gameSession == null) {
            throw new GameSessionNotFoundException();
        }

        gameSession.updateSessionId(sessionId);

        return gameSession;
    }

    public void validateGameSessionExistByUserIdOrElseThrow(String userId) {

        GameSession gameSession = gameSessionRepository.findActivatingByUserId(userId);

        if(gameSession == null) {
            throw new GameSessionNotFoundException();
        }
    }


    public GameSession saveSession(GameSession gameSession) {

        return gameSessionRepository.save(gameSession);
    }
}
