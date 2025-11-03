package org.storeishangul.sgulserver.domain.gameplay.domain.vo;

import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.Consonant;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.Vowel;

@Getter
public class Deck {

    private Deque<Card> deck;

    private static final int COPIES_PER_CARD = 3;
    private static final SecureRandom RANDOM = new SecureRandom();

    private Deck(Deque<Card> deck) {
        this.deck = deck;
    }

    public Card singleDraw() {
        if(deck == null || deck.isEmpty()) return null;

        return deck.removeFirst();
    }

    public static Deck buildNewDeck() {

        return new Deck(buildNewDeck(COPIES_PER_CARD));
    }

    private static Deque<Card> buildNewDeck(int copiesPerCard) {

        List<Card> newDeck = new ArrayList<>();

        // 자음카드 생성
        for (Consonant c : Consonant.values()) {
            for (int i = 0; i < copiesPerCard; i++) {
                newDeck.add(new Card(CardType.CONSONANT, c.name()));
            }
        }

        // 모음카드 생성
        for (Vowel v : Vowel.values()) {
            for (int i = 0; i < copiesPerCard; i++) {
                newDeck.add(new Card(CardType.VOWEL, v.name()));
            }
        }

        // 무작위 셔플
        Collections.shuffle(newDeck, RANDOM);
        return new ArrayDeque<>(newDeck);
    }
}
