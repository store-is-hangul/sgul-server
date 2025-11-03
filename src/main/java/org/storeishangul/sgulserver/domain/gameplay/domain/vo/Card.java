package org.storeishangul.sgulserver.domain.gameplay.domain.vo;

import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;

@Getter
public class Card {

    private final CardType cardType;
    private final String value;

    public Card(CardType cardType, String value) {
        this.cardType = cardType;
        this.value = value;
    }
}
