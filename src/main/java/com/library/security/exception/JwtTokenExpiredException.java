package com.library.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenExpiredException extends AuthenticationException {

    public JwtTokenExpiredException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public JwtTokenExpiredException(final String msg) {
        super(msg);
    }
}
