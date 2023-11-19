package com.library.security;

import com.library.security.exception.JwtSignatureException;
import com.library.security.exception.JwtTokenExpiredException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    public String generateToken(final Authentication authentication) {
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        final Date now = new Date();
        final Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getUsernameFromToken(final String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(final String authToken) throws JwtSignatureException, JwtTokenExpiredException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (final ExpiredJwtException ex) {
            throw new JwtTokenExpiredException("Expired Token", ex);
        } catch (final JwtException ex) {
            throw new JwtSignatureException("Token validation error", ex);
        }
    }

}
