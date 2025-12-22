package org.storeishangul.sgulserver.domain.dictionary.domain.exception;

import org.storeishangul.sgulserver.common.exception.ApplicationExceptionType;
import org.storeishangul.sgulserver.common.exception.CustomException;

public class WordNotFoundException extends CustomException {

    public WordNotFoundException() {
        super(ApplicationExceptionType.WORD_NOT_FOUND_EXCEPTION);
    }
}
