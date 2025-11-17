package org.storeishangul.sgulserver.domain.gameplay.domain.model;

import java.time.LocalDateTime;
import lombok.Getter;
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

    //TODO: 첫 드로우 카드수  다시 고려
    private static final int INITIAL_DRAW_COUNT = 8;

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

    public void drawCards() {

        drawCards(INITIAL_DRAW_COUNT);
    }

    public void drawBalancedCards(int count) {

        //TODO: 첫 드로우는 밸런스있게 드로우

    }

    public void drawCards(int count) {

        while (count > 0) {
            Card card = this.deck.singleDraw();

            if (card == null) {
                return;
            }

            this.hand.putNewCard(card);
            count--;
        }
    }

    public void calculatePoints(String assembledWord) {

        //TODO: 카드별 점수 고려 (카드 별 점수 합산 * 글자수)
        this.totalScore += assembledWord.length();
    }
}
