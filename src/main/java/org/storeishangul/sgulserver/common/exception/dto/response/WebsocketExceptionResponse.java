package org.storeishangul.sgulserver.common.exception.dto.response;

import lombok.Getter;
import org.storeishangul.sgulserver.common.exception.ApplicationExceptionType;

@Getter
public class WebsocketExceptionResponse {

    private String message;
    private int code;

    public WebsocketExceptionResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public static WebsocketExceptionResponse from(ApplicationExceptionType ex) {

        return new WebsocketExceptionResponse(ex.getMessage(), ex.getCode());
    }

    public static WebsocketExceptionResponse internalServerError() {

        return WebsocketExceptionResponse.from(ApplicationExceptionType.INTERNAL_SERVER_ERROR);
    }
}
