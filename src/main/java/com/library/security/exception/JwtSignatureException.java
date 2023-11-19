package com.library.security.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtSignatureException extends AuthenticationException {

    public JwtSignatureException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public JwtSignatureException(final String msg) {
        super(msg);
    }
}
