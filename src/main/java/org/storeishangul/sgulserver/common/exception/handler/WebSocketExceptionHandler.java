package org.storeishangul.sgulserver.common.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;
import org.storeishangul.sgulserver.common.exception.CustomException;
import org.storeishangul.sgulserver.common.exception.dto.response.WebsocketExceptionResponse;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class WebSocketExceptionHandler extends StompSubProtocolErrorHandler {

    // 특정 예외 처리
    @MessageExceptionHandler(CustomException.class)
    public WebsocketExceptionResponse handleIllegalArgument(CustomException e) {

        log.error("Custom Exception: {}", e.getMessage(), e);
        return WebsocketExceptionResponse.from(e.getType());
    }

    @MessageExceptionHandler(RuntimeException.class)
    public WebsocketExceptionResponse handleIllegalArgument(RuntimeException e) {

        log.error("Internal Server Error: {}", e.getMessage(), e);
        return WebsocketExceptionResponse.internalServerError();
    }
}
