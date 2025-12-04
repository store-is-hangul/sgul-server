package org.storeishangul.sgulserver.domain.gameplay.domain.exception;

import org.storeishangul.sgulserver.common.exception.ApplicationExceptionType;
import org.storeishangul.sgulserver.common.exception.CustomException;

public class MaxCardInHandException extends CustomException {

    public MaxCardInHandException() {

        super(ApplicationExceptionType.MAX_CARD_IN_HAND_EXCEPTION);
    }
}
