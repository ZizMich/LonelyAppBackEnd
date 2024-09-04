package com.aziz.lonelyapp.messanger.websockets;

import com.aziz.lonelyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.aziz.lonelyapp.messanger.MessageController;
import com.aziz.lonelyapp.messanger.websockets.CustomHandshakeInterceptor;
@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {
    @Autowired
    UserRepository rep;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/ws/messages").setAllowedOrigins("*").addInterceptors(new CustomHandshakeInterceptor(rep));
    }

    @Bean
    public WebSocketHandler myWebSocketHandler() {
        return new MessageController();
    }
}