package org.storeishangul.sgulserver.common.exception.handler;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.storeishangul.sgulserver.common.exception.CustomException;
import org.storeishangul.sgulserver.common.exception.dto.response.WebsocketExceptionResponse;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class WebSocketExceptionHandler {

    private final SimpMessagingTemplate messagingTemplate;

    // 특정 예외 처리
    @MessageExceptionHandler(CustomException.class)
    public void handleCustomException(CustomException e, Principal principal) {
        log.error("Custom Exception: {}", e.getMessage(), e);
        
        WebsocketExceptionResponse response = WebsocketExceptionResponse.from(e.getType());
        sendErrorToUser(principal, response);
    }

    @MessageExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException e, Principal principal) {
        log.error("Internal Server Error: {}", e.getMessage(), e);

        WebsocketExceptionResponse response = WebsocketExceptionResponse.internalServerError();
        sendErrorToUser(principal, response);
    }

    private void sendErrorToUser(Principal principal, WebsocketExceptionResponse response) {
        if (principal != null) {
            log.info("Sending error response to user: {}", principal.getName());
            messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/errors",
                response
            );
        } else {
            log.warn("Cannot send error response: Principal is null");
        }
    }
}
