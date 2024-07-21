package com.aziz.lonelyapp.security;

import com.aziz.lonelyapp.model.CustomUserDetail;
import com.aziz.lonelyapp.model.Role;
import com.aziz.lonelyapp.repository.RoleRepository;
import com.aziz.lonelyapp.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Security configuration class for the application.
 */
@Configuration
@EnableWebSecurity
public class Config {
    private final JwtAuthEntryPoint entryPoint;
    private CustomUserDetailsService userDetailsService;

    /**
     * Constructor for Config class.
     *
     * @param userDetailsService the CustomUserDetailsService object
     * @param entryPoint         the JwtAuthEntryPoint object
     */
    @Autowired
    public Config(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint entryPoint) {
        this.userDetailsService = userDetailsService;
        this.entryPoint = entryPoint;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object used to configure the filter chain
     * @return the SecurityFilterChain object representing the configured filter
     *         chain
     * @throws Exception if an error occurs during the configuration process
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection
        http.csrf(csrf -> csrf.disable())
                // Disable session creation
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configure the authorized requests
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/tasks/**").hasAuthority("USER")
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                // Configure the exception handling
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(entryPoint));

        // Add the JWT authentication filter to the filter chain
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Returns the AuthenticationManager instance from the given
     * AuthenticationConfiguration.
     *
     * @param authenticationConfiguration the AuthenticationConfiguration object
     * @return the AuthenticationManager instance
     * @throws Exception if an error occurs during the retrieval
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Returns the BCryptPasswordEncoder instance for password encoding.
     *
     * @return the BCryptPasswordEncoder instance
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns the JWTAuthenticationFilter instance.
     *
     * @return the JWTAuthenticationFilter instance
     */
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
