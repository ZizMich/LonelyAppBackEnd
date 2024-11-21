package com.aziz.lonelyapp.messanger.websockets;

import com.aziz.lonelyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


import java.util.Map;

/**
 * CustomHandshakeInterceptor is a class that implements the HandshakeInterceptor interface from Spring's WebSocket support.
 * It is designed to intercept WebSocket handshake requests and add custom attributes to the handshake attributes map.
 *
 * @author Aziz
 * @since 1.0
 */
public class CustomHandshakeInterceptor implements HandshakeInterceptor {
    private final UserRepository rep;

    /**
     * Constructor for CustomHandshakeInterceptor.
     *
     * @param rep UserRepository instance used to retrieve user information.
     */
    public CustomHandshakeInterceptor(UserRepository rep) {
        this.rep = rep;
    }

    /**
     * This method is called before the WebSocket handshake is performed.
     * It retrieves the current user's authentication and adds their ID to the handshake attributes map.
     *
     * @param request The incoming HTTP request.
     * @param response The outgoing HTTP response.
     * @param wsHandler The WebSocket handler.
     * @param attributes The handshake attributes map.
     * @return true if the handshake should proceed, false otherwise.
     * @throws Exception If an error occurs during the handshake.
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null) {
            String sender = authentication.getName();
            String id = rep.findByName(sender).get().getId();
            attributes.put("USER_ID", id);
        }
        return true;
    }

    /**
     * This method is called after the WebSocket handshake is performed.
     * It can be used to perform any necessary cleanup or post-handshake actions.
     *
     * @param request The incoming HTTP request.
     * @param response The outgoing HTTP response.
     * @param wsHandler The WebSocket handler.
     * @param exception The exception that occurred during the handshake (or null if no exception).
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}

