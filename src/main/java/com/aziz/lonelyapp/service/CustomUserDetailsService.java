package com.aziz.lonelyapp.service;

import com.aziz.lonelyapp.model.UserEntity;
import com.aziz.lonelyapp.repository.UserRepository;
import com.aziz.lonelyapp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * CustomUserDetailsService is a service class that implements the UserDetailsService interface.
 * It is responsible for loading user-specific data for authentication and authorization purposes.
 *
 * @author YourName
 * @version 1.0
 * @since 2023-01-01
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    /**
     * UserRepository instance for database operations.
     */
    private final UserRepository repository;
    /**
     * Constructor for CustomUserDetailsService.
     *
     * @param usersRepository UserRepository instance for dependency injection.
     */

    @Autowired
    public CustomUserDetailsService(UserRepository usersRepository){
        this.repository=usersRepository;
    }

    /**
     * Loads the user details by username.
     *
     * @param username The username of the user to be loaded.
     * @return UserDetails object containing the user's information.
     * @throws UsernameNotFoundException If the username is not found in the database.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(user.getName(), user.getPassword(), Util.mapRolesToAuthorities((user.getRoles())));
    }
    /**
     * Loads the user details by email.
     *
     * This method is an additional functionality to load user details by email.
     * It is used in cases where the username is not available but the email is.
     *
     * @param username The email of the user to be loaded.
     * @return UserDetails object containing the user's information.
     * @throws UsernameNotFoundException If the email is not found in the database.
     */
    public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return  new User(user.getName(),user.getPassword(),Util.mapRolesToAuthorities(user.getRoles()));
    }
}
