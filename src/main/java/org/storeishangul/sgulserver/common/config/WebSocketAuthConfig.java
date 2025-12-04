package org.storeishangul.sgulserver.common.config;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
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

        log.info("STOMP HEADER: {}", message.getHeaders());
        log.info("STOMP PAYLOAD: {} [{}] INBOUND payload: {}", accessor.getCommand(), accessor.getSessionId(), payload);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String userId = accessor.getFirstNativeHeader("userId");
            Authentication auth =
                new UsernamePasswordAuthenticationToken(userId, null, List.of());

            accessor.setUser(auth);
            log.info("STOMP CONNECTED: {}", userId);
        }

        return message;
    }
}
