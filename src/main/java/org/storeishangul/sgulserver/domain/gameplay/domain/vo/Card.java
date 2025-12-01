package org.storeishangul.sgulserver.domain.gameplay.domain.vo;

import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardPoint;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;

@Getter
public class Card {

    private final CardType cardType;
    private final String value;
    private final CardPoint point;

    public Card(CardType cardType, String value, CardPoint point) {
        this.cardType = cardType;
        this.value = value;
        this.point = point;
    }
}
