package com.tibame.group1.web.config;

import com.tibame.group1.web.service.impl.webSocketMemberChatInit;
import com.tibame.group1.web.service.impl.webSocketHelperService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.tibame.group1.web.service.impl.ChatWebCocketServiceImpl;

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
        return new ChatWebCocketServiceImpl();
    }

    // 客服聊天室
    @Bean
    public WebSocketHandler webSocketService() {
        return new webSocketHelperService();
    }

    // 初始化聊天室
    @Bean
    WebSocketHandler webSocketMemberChatInit() {
        return new webSocketMemberChatInit();
    }

    @Bean
    WebSocketHandler webSocketHelperService() {
        return new webSocketHelperService();
    }
}
