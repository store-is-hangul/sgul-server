package org.storeishangul.sgulserver.common.exception;

import lombok.Getter;

@Getter
public enum ApplicationExceptionType {

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // 1000: GAME SESSION
    GAME_SESSION_NOT_FOUND_EXCEPTION(1000, "Game Session Not Found"),
    MAX_CARD_IN_HAND_EXCEPTION(1001, "Max Card In Hand"),

    // 2000: LEADERBOARD
    LEADERBOARD_ALREADY_EXIST_EXCEPTION(2000, "Leaderboard Already Exist"),


    // 3000: WORD
    WORD_NOT_FOUND_EXCEPTION(3000, "Word Not Found"),

    ;
    private final int code;
    private final String message;

    ApplicationExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
