package com.aziz.lonelyapp.model;

import com.aziz.lonelyapp.service.CustomUserDetailsService;
import com.aziz.lonelyapp.util.Util;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.aziz.lonelyapp.model.UserEntity;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {

    private String username;
    private String password;
    @Getter
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail(UserEntity user) {
        this.username = user.getName();
        this.email = user.getEmail();
        this.authorities= Util.mapRolesToAuthorities(user.getRoles());
        this.password = user.getPassword();
    }
    public CustomUserDetail( String name,String password, String email, List<Role> roles) {
        this.username = name;
        this.password = password;
        this.email = email;
        this.authorities = Util.mapRolesToAuthorities(roles);
    }
    public CustomUserDetail( String name,String password, String email, Collection<? extends GrantedAuthority> roles) {
        this.username = name;
        this.password = password;
        this.email = email;
        this.authorities = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
