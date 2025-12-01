package org.storeishangul.sgulserver.domain.gameplay.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.storeishangul.sgulserver.domain.dictionary.domain.DictionaryService;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.request.CalculatePointRequest;
import org.storeishangul.sgulserver.domain.gameplay.api.dto.request.DrawRequest;
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

        return GameResponse.from(gameSession);
    }

    public GameResponse drawCards(String userId, String sessionId, DrawRequest request) {

        GameSession gameSession = gameSessionService.findSessionByUserIdOrElseThrow(userId, sessionId);
        gameSession.drawCards(request.getCounts());

        return GameResponse.from(gameSession);
    }

    public CalculatePointsResponse calculatePoint(String userId, String sessionId,
        CalculatePointRequest request) {

        GameSession gameSession = gameSessionService.findSessionByUserIdOrElseThrow(userId, sessionId);
        String assembledWord = dictionaryService.makeWordAndValidate(request.getCards());
        gameSession.calculatePoints(request.getCards(), assembledWord);
        gameSession.discardCardsFromHand(request.getCards());

        return CalculatePointsResponse.of(gameSession, assembledWord);
    }
}
