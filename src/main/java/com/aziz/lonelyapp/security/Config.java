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
 * This class is responsible for configuring Spring Security for the application.
 * It sets up authentication, authorization, and CSRF protection.
 *
 * @author YourName
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
public class Config {
    private CustomUserDetailsService userDetailsService;

    /**
     * Constructor for Config class.
     *
     * @param userDetailsService The custom user details service to load user-specific data.
     */
    @Autowired
    public Config(CustomUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    /**
     * This method creates a security filter chain for the application.
     * It configures CSRF protection, session management, and authorization rules.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during security configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("api/v1/tasks/**").hasAuthority("USER")
                                .requestMatchers("api/v1/auth/**").permitAll()
                                .requestMatchers("api/v1/tokens/refresh").permitAll()
                                //.requestMatchers("ws").permitAll()
                                .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * This method creates an authentication manager for the application.
     *
     * @param authenticationConfiguration The authentication configuration object.
     * @return The configured AuthenticationManager.
     * @throws Exception If an error occurs during authentication manager creation.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * This method creates a password encoder for the application.
     *
     * @return The configured PasswordEncoder.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method creates a JWT authentication filter for the application.
     *
     * @return The configured JWTAuthenticationFilter.
     */
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
}
