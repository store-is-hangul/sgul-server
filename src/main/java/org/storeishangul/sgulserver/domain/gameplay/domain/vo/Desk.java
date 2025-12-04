package org.storeishangul.sgulserver.domain.gameplay.domain.vo;

import java.util.ArrayList;
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
}
