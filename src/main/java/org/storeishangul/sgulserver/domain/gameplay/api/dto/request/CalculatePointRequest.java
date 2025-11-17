package org.storeishangul.sgulserver.domain.gameplay.api.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class CalculatePointRequest {

    private List<Character> characters;
}
