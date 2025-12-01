package org.storeishangul.sgulserver.domain.gameplay.domain.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;

@Getter
public class Hand {

    List<Card> vowelCards;
    List<Card> consonantCards;

    private final static int MINIMUM_CARD_COUNT_EACH = 3;

    public Hand(List<Card> vowelCards, List<Card> consonantCards) {
        this.vowelCards = vowelCards != null ? vowelCards : new ArrayList<>();
        this.consonantCards = consonantCards != null ? consonantCards : new ArrayList<>();
    }

    public static Hand empty() {

        return new Hand(new ArrayList<>(), new ArrayList<>());
    }

    public void putNewCard(Card card) {

        if (card == null) {
            return;
        }

        if (card.getCardType() == CardType.VOWEL) {
            this.vowelCards.add(card);
        } else {
            this.consonantCards.add(card);
        }
    }

    public boolean hasMinimumVowel() {

        return this.vowelCards != null && this.vowelCards.size() >= MINIMUM_CARD_COUNT_EACH;
    }

    public boolean hasMinimumConsonant() {

        return this.consonantCards != null && this.consonantCards.size() >= MINIMUM_CARD_COUNT_EACH;
    }

    public List<Card> getCards() {

        return Stream.of(vowelCards, consonantCards).flatMap(List::stream).toList();
    }
}

