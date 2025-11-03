package org.storeishangul.sgulserver.domain.gameplay.domain.vo;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Hand {

    List<Card> cards;

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public static Hand empty() {

        return new Hand(new ArrayList<>());
    }

    public void putNewCard(Card card) {

        if(cards == null) {
            cards = new ArrayList<>();
        }

        cards.add(card);
    }
}

