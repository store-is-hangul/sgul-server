package org.storeishangul.sgulserver.common.config;

import io.micrometer.common.util.StringUtils;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

@Slf4j
@Configuration
public class WebSocketLoggingInterceptorConfig implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        String payload = "";
        if (message.getPayload() instanceof byte[]) {
            payload = new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
        }

        log.info("<<<<< STOMP OUTBOUND [{}] payload: {}", message.getHeaders().getOrDefault("simpMessageType", "UNKNOWN COMMAND"),
            StringUtils.isBlank(payload) ? "NONE" : payload);

        return message;
    }
}
