package com.aziz.lonelyapp.service;

import com.aziz.lonelyapp.model.Role;
import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.UserRepository;
import com.aziz.lonelyapp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom implementation of UserDetailsService for loading users by username or
 * email.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    /**
     * Constructs a new CustomUserDetailsService instance.
     *
     * @param usersRepository the user repository to use for loading users
     */
    @Autowired
    public CustomUserDetailsService(UserRepository usersRepository) {
        this.repository = usersRepository;
    }

    /**
     * Loads a user by username.
     *
     * @param username the username of the user to load
     * @return the UserDetails object representing the user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(user.getName(), user.getPassword(), Util.mapRolesToAuthorities((user.getRoles())));
    }

    /**
     * Loads a user by email address.
     *
     * @param username the email address of the user
     * @return the UserDetails object representing the user
     * @throws UsernameNotFoundException if the user is not found
     */
    public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(user.getName(), user.getPassword(), Util.mapRolesToAuthorities(user.getRoles()));
    }
}
