package com.library.security.service;

import com.library.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(final JwtTokenProvider jwtTokenProvider,
                       final PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication createAuthenticationToken(final UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(),
            userDetails.getPassword(),
            userDetails.getAuthorities()
        );
    }

    public String generateJwtToken(final Authentication authentication) {
        final String jwt = jwtTokenProvider.generateToken(authentication);
        jwtTokenProvider.validateToken(jwt);
        return jwt;
    }

    public boolean passwordMatches(final String rawPassword, final String password) {
        return passwordEncoder.matches(rawPassword,  password);
    }
}
