package org.storeishangul.sgulserver.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.storeishangul.sgulserver.common.exception.handler.WebSocketExceptionHandler;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketExceptionHandler webSocketExceptionHandler;
    private final WebSocketAuthConfig webSocketAuthConfig;
    private final WebSocketLoggingInterceptorConfig stompLoggingInterceptor;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketAuthConfig);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        // 나가는 메시지 로깅
        registration.interceptors(stompLoggingInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 클라이언트가 구독할 prefix
        registry.enableSimpleBroker("/topic", "/queue")
            .setHeartbeatValue(new long[]{10000, 10000})
                .setTaskScheduler(heartBeatScheduler());

        // 클라이언트가 메시지 보낼 prefix (클라이언트가 @MessageMapping이 붙은 컨트롤러 메서드를 호출할 때 사용)
        registry.setApplicationDestinationPrefixes("/app");

        // 특정 사용자에게 메시지를 보낼 때 사용하는 prefix
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws-sgul")
            .setAllowedOriginPatterns("*")
            .withSockJS();

        registry.setErrorHandler(webSocketExceptionHandler);
    }

    // 하트비트 스케줄러
    @Bean
    public ThreadPoolTaskScheduler heartBeatScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("stomp-heartbeat-thread-");
        scheduler.initialize();

        return scheduler;
    }
}
