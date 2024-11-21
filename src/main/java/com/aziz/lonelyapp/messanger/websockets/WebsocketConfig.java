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
/**
 * Configuration class for WebSocket communication in the LonelyApp application.
 * This class enables WebSocket support and registers a custom WebSocket handler.
 * It also sets up the allowed origins and adds a custom handshake interceptor.
 */
@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    /**
     * Autowired UserRepository instance to be used in the custom handshake interceptor.
     */
    @Autowired
    UserRepository rep;

    /**
     * Registers the custom WebSocket handler and sets up the WebSocket endpoint.
     *
     * @param registry The WebSocketHandlerRegistry to register the handler with.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/ws/messages")
                .setAllowedOrigins("*")
                .addInterceptors(new CustomHandshakeInterceptor(rep));
    }

    /**
     * Creates and returns an instance of the custom WebSocket handler.
     *
     * @return An instance of the custom WebSocket handler.
     */
    @Bean
    public WebSocketHandler myWebSocketHandler() {
        return new MessageController();
    }
}