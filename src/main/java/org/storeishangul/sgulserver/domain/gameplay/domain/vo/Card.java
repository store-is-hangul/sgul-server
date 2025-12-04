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

    private static final String CARD_ID_FORMAT = "%s_%s";

    public Card(CardType cardType, String value, CardPoint point) {
        this.id = String.format(CARD_ID_FORMAT, cardType.name(), UUID.randomUUID());
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
        return Objects.equals(id, card.id) && cardType == card.cardType && Objects.equals(value, card.value)
            && point == card.point;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardType, value, point);
    }
}
