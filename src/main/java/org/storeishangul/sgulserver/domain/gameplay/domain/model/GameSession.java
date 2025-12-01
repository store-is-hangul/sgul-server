package org.storeishangul.sgulserver.domain.gameplay.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Card;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Deck;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Hand;

@Getter
public class GameSession {

    private final String userId;
    private String sessionId;
    private final Deck deck;
    private final Hand hand;
    private int totalScore;
    private LocalDateTime lastModifiedAt;

    private static final int INITIAL_DRAW_COUNT_EACH = 6;

    private GameSession(String userId, String sessionId, Deck deck, Hand hand, int totalScore) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.deck = deck;
        this.hand = hand;
        this.totalScore = totalScore;
        this.lastModifiedAt = LocalDateTime.now();
    }

    public static GameSession init(String userId, String sessionId) {

        return new GameSession(
            userId,
            sessionId,
            Deck.buildNewDeck(),
            Hand.empty(),
            0
        );
    }

    public void updateSessionId(String sessionId) {

        this.sessionId = sessionId;
    }

    public void drawBalancedCards() {
        drawCardsByType(CardType.CONSONANT, INITIAL_DRAW_COUNT_EACH);
        drawCardsByType(CardType.VOWEL, INITIAL_DRAW_COUNT_EACH);
    }

    private void drawCardsByType(CardType type, int count) {
        for (int i = 0; i < count; i++) {
            Card card = this.deck.singleDraw(type);
            if (card != null) {
                this.hand.putNewCard(card);
            }
        }
    }

    public void drawCards(int count) {

        while (count > 0) {

            if (!this.hand.hasMinimumVowel()) {
                Card card = this.deck.singleDraw(CardType.VOWEL);
                this.hand.putNewCard(card);
            } else if (!this.hand.hasMinimumConsonant()) {
                Card card = this.deck.singleDraw(CardType.CONSONANT);
                this.hand.putNewCard(card);
            } else {
                Card card = this.deck.singleDraw();
                this.hand.putNewCard(card);
            }

            count--;
        }
    }

    public void calculatePoints(List<Card> cards, String assembledWord) {

        int sum = cards.stream().mapToInt(c -> c.getPoint().getPoint()).sum();
        this.totalScore += sum * assembledWord.length();
    }
}
