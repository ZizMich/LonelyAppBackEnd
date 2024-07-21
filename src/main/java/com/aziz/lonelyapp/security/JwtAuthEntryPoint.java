package com.aziz.lonelyapp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    /**
     * This method is called when a user tries to access a protected resource
     * without having provided the
     * necessary authentication credentials.
     *
     * @param request       The HTTP request that resulted in an unauthorized access
     *                      attempt.
     * @param response      The HTTP response to be sent back to the client.
     * @param authException The exception that caused the authorization failure.
     * @throws IOException      If there is an error writing to the response.
     * @throws ServletException If there is an error setting the response status.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // Send an error response with the appropriate status code and message.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
