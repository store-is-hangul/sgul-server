package org.storeishangul.sgulserver.domain.gameplay.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Card;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Deck;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Hand;

public class GameResponse {

    private String userId;
    private String sessionId;
    private DeckApiDto deck;
    private HandApiDto hand;
    private int totalScore;
    private LocalDateTime lastModifiedAt;

    public GameResponse(String userId, String sessionId, DeckApiDto deck, HandApiDto hand, int totalScore,
        LocalDateTime lastModifiedAt) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.deck = deck;
        this.hand = hand;
        this.totalScore = totalScore;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static GameResponse from(GameSession gameSession) {

        if(gameSession == null) {
            return null;
        }

        return new GameResponse(
            gameSession.getUserId(),
            gameSession.getSessionId(),
            DeckApiDto.fromDeck(gameSession.getDeck()),
            HandApiDto.fromHand(gameSession.getHand()),
            gameSession.getTotalScore(),
            gameSession.getLastModifiedAt()
        );
    }

    public static class DeckApiDto {

        private List<CardApiDto> deck;

        public DeckApiDto(List<CardApiDto> deck) {
            this.deck = deck;
        }

        public static DeckApiDto fromDeck(Deck deck) {

            return new DeckApiDto(deck.getDeck().stream().map(CardApiDto::fromCard).toList());
        }
    }

    public static class HandApiDto {

        private List<CardApiDto> cards;

        public HandApiDto(List<CardApiDto> cards) {
            this.cards = cards;
        }

        public static HandApiDto fromHand(Hand hand) {

            return new HandApiDto(hand.getCards().stream().map(CardApiDto::fromCard).toList());
        }
    }

    public static class CardApiDto {

        private CardType cardType;
        private String value;

        public CardApiDto(CardType cardType, String value) {
            this.cardType = cardType;
            this.value = value;
        }

        public static CardApiDto fromCard(Card card) {
            return new CardApiDto(card.getCardType(), card.getValue());
        }
    }
}
