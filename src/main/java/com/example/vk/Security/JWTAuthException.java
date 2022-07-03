package com.example.vk.Security;


import org.springframework.security.core.AuthenticationException;

public class JWTAuthException extends AuthenticationException {

    public JWTAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JWTAuthException(String msg) {
        super(msg);
    }
}
