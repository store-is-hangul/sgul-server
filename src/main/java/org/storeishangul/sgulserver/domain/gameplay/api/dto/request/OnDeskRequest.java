package org.storeishangul.sgulserver.domain.gameplay.api.dto.request;

import lombok.Getter;

@Getter
public class OnDeskRequest {

    private JobsOnDeskType type;

    private String cardId;

    public enum JobsOnDeskType {
        PUT,
        REMOVE
    }
}
