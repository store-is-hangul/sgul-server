package org.storeishangul.sgulserver.domain.gameplay.api.dto.request;

import java.util.List;
import lombok.Getter;
import org.storeishangul.sgulserver.domain.gameplay.domain.vo.Card;

@Getter
public class CalculatePointRequest {

    private List<Card> cards;
}
