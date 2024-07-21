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

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public CustomUserDetailsService(UserRepository usersRepository){
        this.repository=usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return  new User(user.getName(),user.getPassword(), Util.mapRolesToAuthorities((user.getRoles())));
    }


    public UserDetails loadUserByEmail(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return  new User(user.getName(),user.getPassword(),Util.mapRolesToAuthorities(user.getRoles()));
    }
}
