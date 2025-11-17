package org.storeishangul.sgulserver.domain.gameplay.api.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;

@Getter
public class CalculatePointsResponse extends GameResponse{

    private boolean success;

    public CalculatePointsResponse(String userId, String sessionId, int deckCardsCount,
        HandApiDto hand, int totalScore, LocalDateTime lastModifiedAt, boolean success) {

        super(userId, sessionId, deckCardsCount, hand, totalScore, lastModifiedAt);
        this.success = success;
    }

    public static CalculatePointsResponse of(GameSession gameSession, String assembledWord) {

        return new CalculatePointsResponse(
            gameSession.getUserId(),
            gameSession.getSessionId(),
            gameSession.getDeck() == null ? 0 : gameSession.getDeck().getCardsCount(),
            HandApiDto.fromHand(gameSession.getHand()),
            gameSession.getTotalScore(),
            gameSession.getLastModifiedAt(),
            assembledWord != null && !assembledWord.isBlank()
        );
    }
}
