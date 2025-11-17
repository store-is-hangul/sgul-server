package org.storeishangul.sgulserver.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    protected ApplicationExceptionType type;
    protected HttpStatus httpStatus;

    public CustomException(ApplicationExceptionType type) {
        super(type.getMessage());
        this.type = type;
        this.httpStatus = HttpStatus.OK;
    }

    public CustomException(ApplicationExceptionType type, HttpStatus httpStatus) {
        super(type.getMessage());
        this.type = type;
        this.httpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return this.type.getMessage();
    }
}
