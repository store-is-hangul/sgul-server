package org.storeishangul.sgulserver.domain.gameplay.domain.vo;

import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Getter;

@Getter
public class Desk {

    private List<Card> cards;

    private Desk(List<Card> cards) {
        this.cards = cards;
    }

    public static Desk empty() {

        return new Desk(new ArrayList<>());
    }

    public void clear() {

        cards.clear();
    }

    public void putCard(Card card) {

        cards.add(card);
    }

    @Nullable
    public Card remove(String cardId) {

        Iterator<Card> iterator = cards.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getId().equals(cardId)) {
                iterator.remove();
                return card;
            }
        }

        return null;
    }
}
