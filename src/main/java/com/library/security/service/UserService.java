package com.library.security.service;

import com.library.security.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserDetailsManager userDetailsManager,
                       final PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(final UserDTO userDTO) {
        final UserDetails user = new User(
            userDTO.getUsername(),
            passwordEncoder.encode(userDTO.getPassword()),
            Collections.singletonList(new SimpleGrantedAuthority("USER")));

        userDetailsManager.createUser(user);
    }
}
