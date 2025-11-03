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

    public void drawCards(int count) {

        //TODO: 자음 모음 최소 개수 보장
        while (count > 0) {
            Card card = this.deck.singleDraw();

            if(card == null) {
                return;
            }

            this.hand.putNewCard(card);
            count--;
        }
    }
}
