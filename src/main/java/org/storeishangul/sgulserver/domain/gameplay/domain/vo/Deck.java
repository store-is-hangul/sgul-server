package org.storeishangul.sgulserver.domain.gameplay.domain.vo;

import jakarta.annotation.Nullable;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardPoint;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.Consonant;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.Vowel;

@Getter
public class Deck {

    private Deque<Card> consonantDeck;
    private Deque<Card> vowelDeck;

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Random random = new Random();

    private Deck(Deque<Card> consonantDeck, Deque<Card> vowelDeck) {
        this.consonantDeck = consonantDeck;
        this.vowelDeck = vowelDeck;
    }

    @Nullable
    public Card singleDraw() {
        if(validateDeckEmpty())
            return null;

        if(random.nextInt() % 2 == 1 && (vowelDeck != null && !vowelDeck.isEmpty())) {
            return vowelDeck.removeFirst();
        } else {
            return (consonantDeck != null && !consonantDeck.isEmpty()) ? consonantDeck.removeFirst() : null;
        }
    }

    @Nullable
    public Card singleDraw(CardType type) {
        if (validateDeckEmpty()) {
            return null;
        }

        if(CardType.VOWEL == type) {
            return (vowelDeck != null && !vowelDeck.isEmpty()) ? vowelDeck.removeFirst() : null;
        } else {
            return (consonantDeck != null && !consonantDeck.isEmpty()) ? consonantDeck.removeFirst() : null;
        }
    }

    public static Deck buildNewDeck() {

        List<Card> newConDeck = new ArrayList<>();
        List<Card> newVowDeck = new ArrayList<>();


        for (CardPoint cp : CardPoint.values()) {

            // 자음카드 생성
            for (Consonant c : Consonant.values()) {
                for (int i = 0; i < cp.getCardCopyCount(); i++) {
                    newConDeck.add(new Card(CardType.CONSONANT, c.getCode(), cp));
                }
            }

            // 모음카드 생성
            for (Vowel v : Vowel.values()) {
                for (int i = 0; i < cp.getCardCopyCount(); i++) {
                    newVowDeck.add(new Card(CardType.VOWEL, v.getCode(), cp));
                }
            }
        }

        // 무작위 셔플
        Collections.shuffle(newConDeck, RANDOM);
        Collections.shuffle(newVowDeck, RANDOM);

        return new Deck(new ArrayDeque<>(newConDeck), new ArrayDeque<>(newVowDeck));
    }

    private boolean validateDeckEmpty() {

        return (consonantDeck == null || consonantDeck.isEmpty()) && (vowelDeck == null || vowelDeck.isEmpty());
    }

    public int getCardsCount() {

        return (consonantDeck == null ? 0 :consonantDeck.size()) + (vowelDeck == null ? 0 : vowelDeck.size());
    }
}
