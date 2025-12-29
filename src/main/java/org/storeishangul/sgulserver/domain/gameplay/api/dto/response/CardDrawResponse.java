package org.storeishangul.sgulserver.domain.gameplay.api.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;

public class CardDrawResponse extends GameResponse {

    private boolean success;

    public CardDrawResponse(String userId, String sessionId, DeskApiDto desk, int deckCardsCount,
        HandApiDto hand, int totalScore, LocalDateTime lastModifiedAt, boolean success) {

        super(userId, sessionId, desk, deckCardsCount, hand, totalScore, lastModifiedAt);
        this.success = success;
    }

    public static CardDrawResponse from(GameSession gameSession, boolean success) {

        return new CardDrawResponse(
            gameSession.getUserId(),
            gameSession.getSessionId(),
            DeskApiDto.fromDesk(gameSession.getDesk()),
            gameSession.getDeck() == null ? 0 : gameSession.getDeck().getCardsCount(),
            HandApiDto.fromHand(gameSession.getHand()),
            gameSession.getTotalScore(),
            gameSession.getLastModifiedAt(),
            success
        );
    }
}
