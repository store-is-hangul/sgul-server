package org.storeishangul.sgulserver.common.exception;

import lombok.Getter;

@Getter
public enum ApplicationExceptionType {

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // 1000: GAME SESSION
    GAME_SESSION_NOT_FOUND_EXCEPTION(1000, "Game Session Not Found"),
    ;

    private final int code;
    private final String message;

    ApplicationExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
