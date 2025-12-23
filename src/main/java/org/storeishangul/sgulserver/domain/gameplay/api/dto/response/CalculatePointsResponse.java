package org.storeishangul.sgulserver.domain.gameplay.api.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;

@Getter
public class CalculatePointsResponse extends GameResponse{

    private boolean success;
    private String mathematicalExpression;

    public CalculatePointsResponse(String userId, String sessionId, DeskApiDto desk, int deckCardsCount,
        HandApiDto hand, int totalScore, LocalDateTime lastModifiedAt, boolean success, String mathematicalExpression) {

        super(userId, sessionId, desk, deckCardsCount, hand, totalScore, lastModifiedAt);
        this.success = success;
        this.mathematicalExpression = mathematicalExpression;
    }

    public static CalculatePointsResponse of(GameSession gameSession, String assembledWord, String mathematicalExpression) {

        return new CalculatePointsResponse(
            gameSession.getUserId(),
            gameSession.getSessionId(),
            DeskApiDto.fromDesk(gameSession.getDesk()),
            gameSession.getDeck() == null ? 0 : gameSession.getDeck().getCardsCount(),
            HandApiDto.fromHand(gameSession.getHand()),
            gameSession.getTotalScore(),
            gameSession.getLastModifiedAt(),
            assembledWord != null && !assembledWord.isBlank(),
            mathematicalExpression
        );
    }

    public static CalculatePointsResponse fail(GameSession gameSession) {

        return new CalculatePointsResponse(
            gameSession.getUserId(),
            gameSession.getSessionId(),
            DeskApiDto.fromDesk(gameSession.getDesk()),
            gameSession.getDeck() == null ? 0 : gameSession.getDeck().getCardsCount(),
            HandApiDto.fromHand(gameSession.getHand()),
            gameSession.getTotalScore(),
            gameSession.getLastModifiedAt(),
            false,
            null
        );
    }
}
