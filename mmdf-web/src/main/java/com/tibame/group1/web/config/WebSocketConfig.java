package com.tibame.group1.web.config;

import com.tibame.group1.web.handler.WebSocketChatroomFunction;
import com.tibame.group1.web.handler.WebSocketHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.tibame.group1.web.handler.WebSocketChatroomMessage;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/message").setAllowedOrigins("*");
        registry.addHandler(webSocketService(), "/service").setAllowedOrigins("*");
        registry.addHandler(webSocketMemberChatInit(), "/chatroom").setAllowedOrigins("*");
        registry.addHandler(webSocketHelperService(), "/helper").setAllowedOrigins("*");
    }

    // 聊天室功能
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketChatroomMessage();
    }

    // 客服聊天室
    @Bean
    public WebSocketHandler webSocketService() {
        return new WebSocketHelper();
    }

    // 初始化聊天室
    @Bean
    WebSocketHandler webSocketMemberChatInit() {
        return new WebSocketChatroomFunction();
    }

    @Bean
    WebSocketHandler webSocketHelperService() {
        return new WebSocketHelper();
    }
}
