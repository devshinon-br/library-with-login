package com.library.security.service;

import com.library.security.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;

    @Autowired
    public UserService(final UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    public void createUser(final UserDTO userDTO) {
        final UserDetails user = User.withDefaultPasswordEncoder()
            .username(userDTO.getUsername())
            .password(userDTO.getPassword())
            .roles("USER")
            .build();

        userDetailsManager.createUser(user);
    }
}
