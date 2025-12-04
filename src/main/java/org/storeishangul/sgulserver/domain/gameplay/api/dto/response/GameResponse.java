package org.storeishangul.sgulserver.domain.gameplay.api.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.model.GameSession;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Card;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Deck;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Hand;

@Getter
public class GameResponse {

    private String userId;
    private String sessionId;
    private int deckCardsCount;
    private HandApiDto hand;
    private int totalScore;
    private LocalDateTime lastModifiedAt;

    public GameResponse(String userId, String sessionId, int deckCardsCount, HandApiDto hand, int totalScore,
        LocalDateTime lastModifiedAt) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.deckCardsCount = deckCardsCount;
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
            gameSession.getDeck() == null ? 0 : gameSession.getDeck().getCardsCount(),
            HandApiDto.fromHand(gameSession.getHand()),
            gameSession.getTotalScore(),
            gameSession.getLastModifiedAt()
        );
    }

    @Getter
    public static class DeckApiDto {

        private List<CardApiDto> consonantDeck;
        private List<CardApiDto> vowelDeck;

        public DeckApiDto(List<CardApiDto> consonantDeck, List<CardApiDto> vowelDeck) {
            this.consonantDeck = consonantDeck;
            this.vowelDeck = vowelDeck;
        }

        public static DeckApiDto fromDeck(Deck deck) {

            return new DeckApiDto(
                deck.getConsonantDeck().stream().map(CardApiDto::fromCard).toList(),
                deck.getVowelDeck().stream().map(CardApiDto::fromCard).toList()
            );
        }
    }

    @Getter
    public static class HandApiDto {

        private List<CardApiDto> cards;

        public HandApiDto(List<CardApiDto> cards) {
            this.cards = cards;
        }

        public static HandApiDto fromHand(Hand hand) {

            return new HandApiDto(hand.getCards().stream().map(CardApiDto::fromCard).toList());
        }
    }

    @Getter
    public static class CardApiDto {

        private String id;
        private CardType cardType;
        private String value;
        private int point;

        public CardApiDto(String id, CardType cardType, String value, int point) {

            this.id = id;
            this.cardType = cardType;
            this.value = value;
            this.point = point;
        }

        public static CardApiDto fromCard(Card card) {

            return new CardApiDto(card.getId(), card.getCardType(), card.getValue(), card.getPoint().getPoint());
        }
    }
}
