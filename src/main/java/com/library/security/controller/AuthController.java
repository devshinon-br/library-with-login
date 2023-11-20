package com.library.security.controller;

import com.library.security.JwtAuthenticationResponse;
import com.library.security.model.dto.LoginDTO;
import com.library.security.model.dto.UserDTO;
import com.library.security.service.AuthService;
import com.library.security.service.CustomUserDetailsService;
import com.library.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CustomUserDetailsService userDetailsService;

    private final UserService userService;

    private final AuthService authService;


    @Autowired
    public AuthController(final CustomUserDetailsService userDetailsService,
                          final UserService userService,
                          final AuthService authService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody final LoginDTO loginRequest) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        if (authService.passwordMatches(loginRequest.getPassword(), userDetails.getPassword())) {
            final Authentication authentication = authService.createAuthenticationToken(userDetails);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final String jwt = authService.generateJwtToken(authentication);

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody final UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.ok("User created successfully");
    }
}
