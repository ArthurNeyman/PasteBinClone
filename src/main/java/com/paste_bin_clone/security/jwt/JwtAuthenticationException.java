package com.paste_bin_clone.security.jwt;

import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String explanation) {
        super(explanation);
    }

    public JwtAuthenticationException() {
    }
}
