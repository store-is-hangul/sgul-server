package org.storeishangul.sgulserver.domain.gameplay.domain.exception;

import org.storeishangul.sgulserver.common.exception.ApplicationExceptionType;
import org.storeishangul.sgulserver.common.exception.CustomException;

public class GameSessionNotFoundException extends CustomException {

    public GameSessionNotFoundException() {

        super(ApplicationExceptionType.GAME_SESSION_NOT_FOUND_EXCEPTION);
    }
}
