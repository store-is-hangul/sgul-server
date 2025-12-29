package org.storeishangul.sgulserver.common.config;

import io.micrometer.common.util.StringUtils;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSocketAuthConfig implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
            message, StompHeaderAccessor.class
        );

        if (accessor.getCommand() == null) {
            return message;
        }

        String payload = "";
        if (message.getPayload() instanceof byte[]) {
            payload = new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
        }

        //log.info("STOMP HEADER: {}", message.getHeaders());
        log.info(
            ">>>>> STOMP INBOUND PAYLOAD: [{}] sessionId: {}, dest: {}, user: {}, payload: {}",
            accessor.getCommand(),
            accessor.getSessionId(),
            accessor.getDestination(),
            accessor.getUser() != null ? accessor.getUser().getName() : "NULL",
            StringUtils.isBlank(payload) ? "NONE" : payload
        );

        // CONNECT: 헤더에서 userId 추출 → 세션에 저장 → Principal 설정
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userId = accessor.getFirstNativeHeader("userId");

            if (userId != null) {
                // 세션 속성에 userId 저장
                Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
                if (sessionAttributes != null) {
                    sessionAttributes.put("userId", userId);
                }

                // Principal 설정
                Authentication auth = new UsernamePasswordAuthenticationToken(
                    userId, null, List.of()
                );
                accessor.setUser(auth);
                log.info(">>>>> STOMP CONNECTED: {}", userId);
            }
        }
        // MESSAGE, SUBSCRIBE 등: 세션에서 userId 복원 → Principal 설정
        else if (accessor.getUser() == null) {
            Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
            if (sessionAttributes != null) {
                String userId = (String) sessionAttributes.get("userId");

                if (userId != null) {
                    Authentication auth = new UsernamePasswordAuthenticationToken(
                        userId, null, List.of()
                    );
                    accessor.setUser(auth);
                } else {
                    log.warn(">>>>> No userId in session for sessionId: {}",
                        accessor.getSessionId());
                }
            }
        }

        return message;
    }
}
