package org.storeishangul.sgulserver.domain.gameplay.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.dictionary.domain.DictionaryService;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.request.DrawRequest;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.request.OnDeskRequest;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.request.OnDeskRequest.JobsOnDeskType;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.response.CalculatePointsResponse;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.response.GameResponse;
import org.storeishangul.sgulserver.domain.gameplay.domain.GameSessionService;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;

@Service
@RequiredArgsConstructor
public class GamePlayApplicationService {

    private final GameSessionService gameSessionService;
    private final DictionaryService dictionaryService;

    public GameResponse startGame(String userId, String sessionId) {

        GameSession gameSession = gameSessionService.startNewGameSession(userId, sessionId);
        gameSessionService.saveSession(gameSession);

        return GameResponse.from(gameSession);
    }

    public GameResponse drawCards(String userId, String sessionId, DrawRequest request) {

        GameSession gameSession = gameSessionService.findSessionAndUpdateByUserIdOrElseThrow(userId, sessionId);
        gameSession.drawCards(request.getCounts());
        gameSessionService.saveSession(gameSession);

        return GameResponse.from(gameSession);
    }

    public CalculatePointsResponse calculatePoint(String userId, String sessionId) {

        GameSession gameSession = gameSessionService.findSessionAndUpdateByUserIdOrElseThrow(userId, sessionId);
        String assembledWord = dictionaryService.makeWordAndValidate(gameSession.getDesk().getCards());
        int points = gameSession.calculatePoints(gameSession.getDesk().getCards(), assembledWord);
        gameSession.addPoints(points);
        String mathematicalExpression = gameSession.generateMathematicalExpression(gameSession.getDesk().getCards(), assembledWord);
        gameSession.clearDesk();

        gameSessionService.saveSession(gameSession);

        return CalculatePointsResponse.of(gameSession, assembledWord, mathematicalExpression);
    }

    public GameResponse processJobsOnDesk(String userId, String sessionId, OnDeskRequest request) {

        GameSession gameSession = gameSessionService.findSessionAndUpdateByUserIdOrElseThrow(userId,
            sessionId);

        if (JobsOnDeskType.PUT == request.getType()) {
            gameSession.putCardOnDesk(request.getCardId());
        } else {
            gameSession.removeCardFromDesk(request.getCardId());
        }

        gameSessionService.saveSession(gameSession);

        return GameResponse.from(gameSession);
    }

    public GameResponse finishTheGame(String userId, String sessionId) {

        GameSession gameSession = gameSessionService.findSessionAndUpdateByUserIdOrElseThrow(userId,
            sessionId);

        gameSession.closeSession();
        gameSessionService.saveSession(gameSession);

        return GameResponse.from(gameSession);
    }
}
