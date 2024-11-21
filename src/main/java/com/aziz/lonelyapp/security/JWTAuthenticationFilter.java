package com.aziz.lonelyapp.security;

import com.aziz.lonelyapp.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/**
 * This class extends {@link OncePerRequestFilter} and is responsible for authenticating users based on JWT tokens.
 * It overrides the {@link #doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)} method to perform the authentication process.
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    /**
     * An instance of {@link JWTGenerator} used to generate and validate JWT tokens.
     */
    @Autowired
    private JWTGenerator tokenGenerator;

    /**
     * An instance of {@link CustomUserDetailsService} used to load user details by username.
     */
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    /**
     * This method is called for each request and performs the JWT authentication process.
     * It extracts the JWT token from the request header, validates it, and sets the user's authentication context.
     *
     * @param request The incoming HTTP request.
     * @param response The outgoing HTTP response.
     * @param filterChain The filter chain to be executed after authentication.
     * @throws ServletException If an error occurs during the authentication process.
     * @throws IOException If an error occurs while reading or writing to the request or response.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = getJWTFromRequest(request);
            if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
                String username = tokenGenerator.getUsernameFormJWT(token);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                Thread currentThread = Thread.currentThread();
                System.out.println("token thread: " + currentThread.getName());
                System.out.println("the security context has been set");
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the request header.
     *
     * @param request The incoming HTTP request.
     * @return The JWT token extracted from the request header.
     */
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}