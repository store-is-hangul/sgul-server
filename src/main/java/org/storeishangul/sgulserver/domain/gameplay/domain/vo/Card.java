package org.storeishangul.sgulserver.domain.gameplay.domain.vo;

import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardPoint;
import org.storeishangul.sgulserver.domain.gameplay.domain.support.CardType;

@Getter
public class Card {

    private final String id;
    private final CardType cardType;
    private final String value;
    private final CardPoint point;

    public Card(CardType cardType, String value, CardPoint point) {
        this.id = UUID.randomUUID().toString();
        this.cardType = cardType;
        this.value = value;
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return cardType == card.cardType && Objects.equals(value, card.value)
            && point == card.point;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardType, value, point);
    }
}
