package com.library.security.controller;

import com.library.security.JwtAuthenticationResponse;
import com.library.security.JwtTokenProvider;
import com.library.security.model.dto.LoginDTO;
import com.library.security.model.dto.UserDTO;
import com.library.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;


    @Autowired
    public AuthController(final AuthenticationManager authenticationManager,
                          final JwtTokenProvider jwtTokenProvider,
                          final UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody final LoginDTO loginRequest) {
//        final Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(
//                loginRequest.getUsername(),
//                loginRequest.getPassword()
//            )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final String jwt = jwtTokenProvider.generateToken(authentication);
//        jwtTokenProvider.validateToken(jwt);
//
//        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody final UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.ok("User created successfully");
    }
}
