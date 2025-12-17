package org.storeishangul.sgulserver.common.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.storeishangul.sgulserver.common.exception.ApplicationExceptionType;
import org.storeishangul.sgulserver.common.exception.CustomException;

@Getter
public class RestResponse<T> {

    private final int code;
    private final String message;
    private final T value;

    public RestResponse(int code, String message, T value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public RestResponse(T value) {
        this.code = 0;
        this.message = null;
        this.value = value;
    }

    public RestResponse(ApplicationExceptionType e) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.value = null;
    }

    public RestResponse(HttpStatus httpStatus, T value) {
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.value = value;
    }

    public static <T> RestResponse<T> ok(T value) {

        return new RestResponse<>(value);
    }

    public static <T> RestResponse<T> error(ApplicationExceptionType e) {

        return new RestResponse<>(e);
    }
}
