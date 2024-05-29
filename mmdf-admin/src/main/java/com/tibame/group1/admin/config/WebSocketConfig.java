package com.tibame.group1.admin.config;

import com.tibame.group1.admin.handler.WebSocketServiceLive;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(WebSocketServiceLive(), "/service-live").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler WebSocketServiceLive() {
        return new WebSocketServiceLive();
    }
}
