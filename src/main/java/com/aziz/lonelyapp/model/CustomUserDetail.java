package com.aziz.lonelyapp.model;

import com.aziz.lonelyapp.service.CustomUserDetailsService;
import com.aziz.lonelyapp.util.Util;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.aziz.lonelyapp.model.UserEntity;
import java.util.Collection;
import java.util.List;

/**
 * CustomUserDetail class represents a user's details that implements the
 * UserDetails
 * interface from Spring Security.
 *
 * @author Aziz
 */
public class CustomUserDetail implements UserDetails {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The email of the user.
     */
    @Getter
    private String email;

    /**
     * The authorities of the user.
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructor that takes a UserEntity object to initialize the details.
     *
     * @param user the UserEntity object containing the user's details
     */
    public CustomUserDetail(UserEntity user) {
        this.username = user.getName();
        this.email = user.getEmail();
        this.authorities = Util.mapRolesToAuthorities(user.getRoles());
        this.password = user.getPassword();
    }

    /**
     * Constructor that takes the user's details.
     *
     * @param name     the name of the user
     * @param password the password of the user
     * @param email    the email of the user
     * @param roles    the roles of the user
     */
    public CustomUserDetail(String name, String password, String email, List<Role> roles) {
        this.username = name;
        this.password = password;
        this.email = email;
        this.authorities = Util.mapRolesToAuthorities(roles);
    }

    /**
     * Constructor that takes the user's details.
     *
     * @param name     the name of the user
     * @param password the password of the user
     * @param email    the email of the user
     * @param roles    the roles of the user
     */
    public CustomUserDetail(String name, String password, String email,
            Collection<? extends GrantedAuthority> roles) {
        this.username = name;
        this.password = password;
        this.email = email;
        this.authorities = roles;
    }

    /**
     * Returns the authorities of the user.
     *
     * @return the authorities of the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    @Override
    public String getUsername() {
        return username;
    }
}
