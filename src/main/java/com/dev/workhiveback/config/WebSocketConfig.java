package com.dev.workhiveback.config;

import com.dev.workhiveback.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final TokenProvider tokenProvider;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 프론트에서 SockJS로 붙을 거면 withSockJS()를 유지
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트 -> 서버로 보내는 prefix
        registry.setApplicationDestinationPrefixes("/app");

        // 서버 -> 클라이언트로 뿌리는(구독) prefix
        registry.enableSimpleBroker("/topic", "/queue");

        // /user/queue/** 형태로 개인 큐를 쓰고 싶을 때 필요
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (accessor == null) {
                    return message;
                }

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String auth = accessor.getFirstNativeHeader("Authorization");

                    if (auth == null || auth.isBlank()) {
                        throw new IllegalArgumentException("Missing Authorization header");
                    }

                    if (!auth.startsWith("Bearer ")) {
                        throw new IllegalArgumentException("Invalid Authorization header format");
                    }

                    String token = auth.substring("Bearer ".length()).trim();
                    if (token.isEmpty()) {
                        throw new IllegalArgumentException("Empty token");
                    }

                    String empId = tokenProvider.validateAndGetUserEmpId(token);
                    accessor.setUser(() -> empId);
                }

                return message;
            }
        });
    }
}