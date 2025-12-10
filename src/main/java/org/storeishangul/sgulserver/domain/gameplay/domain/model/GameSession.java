package org.storeishangul.sgulserver.domain.gameplay.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.exception.MaxCardInHandException;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Card;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Deck;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Desk;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Hand;

@Getter
public class GameSession {

    private final String userId;
    private String sessionId;
    private final Desk desk;
    private final Deck deck;
    private final Hand hand;
    private int totalScore;
    private boolean isActive;
    private LocalDateTime lastModifiedAt;

    private static final int INITIAL_DRAW_COUNT_EACH = 6;
    private static final int MAX_HAND_CARD_COUNT = 12;

    private GameSession(String userId, String sessionId, Desk desk, Deck deck, Hand hand, int totalScore, boolean isActive) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.desk = desk;
        this.deck = deck;
        this.hand = hand;
        this.totalScore = totalScore;
        this.isActive = isActive;
        this.lastModifiedAt = LocalDateTime.now();
    }

    public static GameSession init(String userId, String sessionId) {

        return new GameSession(
            userId,
            sessionId,
            Desk.empty(),
            Deck.buildNewDeck(),
            Hand.empty(),
            0,
            true
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

        if (getHandAndDeskCardCount() >= MAX_HAND_CARD_COUNT) {

            throw new MaxCardInHandException();
        }

        while (count > 0) {

            System.out.println("count: " + count);
            if (!this.hand.hasMinimumVowel()) {
                System.out.println("\n not hasMinimumVowel !!!\n");
                Card card = this.deck.singleDraw(CardType.VOWEL);
                System.out.println("\n drawed card : " + card + "\n");
                this.hand.putNewCard(card);
            } else if (!this.hand.hasMinimumConsonant()) {
                System.out.println("\n not hasMinimumConsonant !!!\n");
                Card card = this.deck.singleDraw(CardType.CONSONANT);
                System.out.println("\n drawed card : " + card + "\n");
                this.hand.putNewCard(card);
            } else {
                Card card = this.deck.singleDraw();
                System.out.println("\n random card draw !!!\n");
                System.out.println("\n drawed card : " + card + "\n");
                this.hand.putNewCard(card);
            }

            count--;
        }
    }

    public void addPoints(int points) {

        this.totalScore += points;
    }

    public int calculatePoints(List<Card> cards, String assembledWord) {

        int sum = cards.stream().mapToInt(c -> c.getPoint().getPoint()).sum();
        return sum * assembledWord.length();
    }

    public String generateMathematicalExpression(List<Card> cards, String assembledWord) {

        String pointsExpression = cards.stream()
            .map(card -> String.valueOf(card.getPoint().getPoint()))
            .collect(Collectors.joining(" + ", "(", ")"));

        int sumOfPoint = cards.stream()
            .mapToInt(card -> card.getPoint().getPoint())
            .sum();

        int length = assembledWord.length();

        int totalScore = sumOfPoint * length;

        return String.format("%s * %d = %d", pointsExpression, length, totalScore);
    }

    public void discardCardsFromHand(List<Card> cards) {

        this.hand.discard(cards);
    }

    public void clearDesk() {

        this.desk.clear();
    }

    public void putCardOnDesk(String cardId) {

        Card targetCard = this.hand.remove(cardId);

        if (targetCard == null) {
            return;
        }

        this.desk.putCard(targetCard);
    }

    public void removeCardFromDesk(String cardId) {

        Card targetCard = this.desk.remove(cardId);

        if (targetCard == null) {
            return;
        }

        this.hand.putNewCard(targetCard);
    }

    public int getHandAndDeskCardCount() {

        return this.hand.getCards().size() + this.desk.getCards().size();
    }

    public void closeSession() {

        this.isActive = false;
    }
}
