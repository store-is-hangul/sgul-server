package org.storeishangul.sgulserver.domain.gameplay.domain.support;

import lombok.Getter;

@Getter
public enum CardPoint {

    TWO_POINT(2, 2),
    THREE_POINT(3, 1),
    FOUR_POINT(4, 1),
    ;

    private int point;
    private int cardCopyCount;

    CardPoint(int point, int cardCopyCount) {
        this.point = point;
        this.cardCopyCount = cardCopyCount;
    }
}
